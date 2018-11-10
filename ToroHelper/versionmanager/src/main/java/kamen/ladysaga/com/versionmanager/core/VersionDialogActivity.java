package kamen.ladysaga.com.versionmanager.core;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import kamen.ladysaga.com.versionmanager.R;
import kamen.ladysaga.com.versionmanager.callback.APKDownloadListener;
import kamen.ladysaga.com.versionmanager.callback.CommitClickListener;
import kamen.ladysaga.com.versionmanager.callback.DialogDismissListener;
import kamen.ladysaga.com.versionmanager.callback.DownloadListener;
import kamen.ladysaga.com.versionmanager.utils.ALog;
import kamen.ladysaga.com.versionmanager.utils.AppUtils;

public class VersionDialogActivity extends Activity implements DownloadListener, DialogInterface.OnDismissListener {

    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0x123;
    protected Dialog versionDialog;
    protected Dialog loadingDialog;
    protected Dialog failDialog;
    private String downloadUrl;
    private VersionParams versionParams;
    private String title;
    private String updateMsg;
    private Bundle paramBundle;

    private CommitClickListener commitListener;
    private DialogDismissListener cancelListener;
    private APKDownloadListener apkDownloadListener;

    public static void setTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        transparentStatusBar(activity);
        setRootView(activity);
    }
    /**
     * 使状态栏透明
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void transparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    /**
     * 设置根布局参数
     */
    private static void setRootView(Activity activity) {
        ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(true);
                ((ViewGroup) childView).setClipToPadding(true);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTransparent(this);
        boolean isRetry = getIntent().getBooleanExtra("isRetry", false);
        Log.e("isRetry", isRetry + "");
        if (isRetry) {
            retryDownload(getIntent());
        } else
            initialize();
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public VersionParams getVersionParams() {
        return versionParams;
    }

    public String getVersionTitle() {
        return title;
    }

    public String getVersionUpdateMsg() {
        return updateMsg;
    }

    public Bundle getVersionParamBundle() {
        return paramBundle;
    }


    /**
     * url msg versionField
     */
    private void initialize() {
        title = getIntent().getStringExtra("title");
        updateMsg = getIntent().getStringExtra("text");
        versionParams = getIntent().getParcelableExtra(AVersionService.VERSION_PARAMS_KEY);
        downloadUrl = getIntent().getStringExtra("downloadUrl");
        paramBundle = getIntent().getBundleExtra(AVersionService.VERSION_PARAMS_EXTRA_KEY);
        //判断是否是静默下载
        //静默下载直接在后台下载不显示版本信息 只有下载完成之后在显示版本信息
        if (title != null && updateMsg != null && downloadUrl != null && versionParams != null) {
            showVersionDialog();
        }


    }

    protected void showVersionDialog() {
        versionDialog = new AlertDialog.Builder(this).setTitle(title).setMessage(updateMsg).setPositiveButton(getString(R.string.versionchecklib_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (commitListener != null)
                    commitListener.onCommitClick();
                dealAPK();
            }
        }).setNegativeButton(getString(R.string.versionchecklib_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).create();

        versionDialog.setOnDismissListener(this);
        versionDialog.setCanceledOnTouchOutside(false);
        versionDialog.setCancelable(false);
        versionDialog.show();

    }

    View loadingView;

    public void showLoadingDialog(int currentProgress) {
        ALog.e("show default downloading dialog");
        if (loadingDialog == null) {
            loadingView = LayoutInflater.from(this).inflate(R.layout.downloading_layout, null);
            loadingDialog = new AlertDialog.Builder(this).setTitle("").setView(loadingView).create();
            loadingDialog.setCancelable(false);
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
//            loadingDialog.setOnDismissListener(dismissListener);
        }
        ProgressBar pb = (ProgressBar) loadingView.findViewById(R.id.pb);
        TextView tvProgress = (TextView) loadingView.findViewById(R.id.tv_progress);
        tvProgress.setText(String.format(getString(R.string.versionchecklib_progress), currentProgress));
        pb.setProgress(currentProgress);
        loadingDialog.show();
    }

    public void showFailDialog() {
        if (failDialog == null) {
            failDialog = new AlertDialog.Builder(this).setMessage(getString(R.string.versionchecklib_download_fail_retry)).setPositiveButton(getString(R.string.versionchecklib_confirm), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (commitListener != null)
                        commitListener.onCommitClick();
                    dealAPK();
                }
            }).setNegativeButton(getString(R.string.versionchecklib_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).create();
//            failDialog.setOnDismissListener(dismissListener);
            failDialog.setCanceledOnTouchOutside(false);
            failDialog.setCancelable(false);
        }
        failDialog.show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        boolean isRetry = intent.getBooleanExtra("isRetry", false);
        Log.e("isRetry", isRetry + "");
        if (isRetry)
            retryDownload(intent);
    }

    private void retryDownload(Intent intent) {

        versionParams = intent.getParcelableExtra(AVersionService.VERSION_PARAMS_KEY);
        downloadUrl = intent.getStringExtra("downloadUrl");
        paramBundle = intent.getBundleExtra(AVersionService.VERSION_PARAMS_EXTRA_KEY);
        requestPermissionAndDownloadFile();

    }

    public void setApkDownloadListener(APKDownloadListener apkDownloadListener) {
        this.apkDownloadListener = apkDownloadListener;
    }

    public void setCommitClickListener(CommitClickListener commitListner) {
        this.commitListener = commitListner;
    }

    public void setDialogDimissListener(final DialogDismissListener cancelListener) {
        this.cancelListener = cancelListener;

    }

    public void dealAPK() {
        if (versionParams.isSilentDownload()) {
            String downloadPath = versionParams.getDownloadAPKPath() + getString(R.string.versionchecklib_download_apkname, getPackageName());
            AppUtils.installApk(VersionDialogActivity.this, new File(downloadPath));
            finish();
        } else {
            showLoadingDialog(0);
            requestPermissionAndDownloadFile();

        }
    }
//    int lastProgress = 0;

    protected void downloadFile() {
        //提前让loadingDialog实例化
        showLoadingDialog(0);
        DownloadManager.downloadAPK(this, downloadUrl, versionParams, this, paramBundle);
    }


    protected void requestPermissionAndDownloadFile() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
//                if(!downloadUrl.isEmpty())
//               downloadAPK(downloadUrl,null);
            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            downloadFile();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    downloadFile();
                } else {
                    Toast.makeText(this, getString(R.string.versionchecklib_write_permission_deny), Toast.LENGTH_LONG).show();
                    finish();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    public void onCheckerDownloading(int progress) {
        showLoadingDialog(progress);
        if (apkDownloadListener != null)
            apkDownloadListener.onDownloading(progress);
    }

    @Override
    public void onCheckerDownloadSuccess(File file) {
        if (apkDownloadListener != null)
            apkDownloadListener.onDownloadSuccess(file);
        dismissAllDialog();


    }

    @Override
    public void onCheckerDownloadFail() {
        if (apkDownloadListener != null)
            apkDownloadListener.onDownloadFail();
        dismissAllDialog();
        showFailDialog();

    }

    private void dismissAllDialog() {
        if (loadingDialog != null && loadingDialog.isShowing())
            loadingDialog.dismiss();
        if (versionDialog != null && versionDialog.isShowing())
            versionDialog.dismiss();
        if (failDialog != null && failDialog.isShowing())
            failDialog.dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        if (cancelListener != null) {
            //通过loadingdialog是否显示来判断是取消还是继续加载
            if (versionParams.isSilentDownload()
                    || (!versionParams.isSilentDownload() && loadingDialog == null)
                    || (!versionParams.isSilentDownload() && loadingDialog != null && !loadingDialog.isShowing())) {
                cancelListener.dialogDismiss(dialogInterface);
                finish();
            }
        }
    }
}
