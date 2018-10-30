package com.toro.helper.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.toro.helper.R;
import com.toro.helper.app.AppConfig;
import com.toro.helper.base.ToroActivity;
import com.toro.helper.fragment.photo.PhotoEditAdapter;
import com.toro.helper.modle.BaseResponeData;
import com.toro.helper.modle.DataModleParser;
import com.toro.helper.modle.ToroUserManager;
import com.toro.helper.utils.CameraUtils;
import com.toro.helper.utils.ConnectManager;
import com.toro.helper.utils.StringUtils;
import com.toro.helper.utils.okhttp.mutifile.listener.impl.UIProgressListener;
import com.toro.helper.view.MainActionBar;
import com.toro.helper.view.RecyclerItemDecoration;
import com.toro.helper.view.ToroProgressView;
import com.toro.helper.view.iphone.IphoneDialogBottomMenu;
import com.toro.helper.view.iphone.MenuItemOnClickListener;

import java.util.ArrayList;

import helper.phone.toro.com.imageselector.utils.ImageSelector;

/**
 * Create By liujia
 * on 2018/10/24.
 **/
public class UploadPhotoActivity extends ToroActivity implements View.OnClickListener {

    private static final String EXTRA_IMAGES = "extra_images";

    private static final int PHOTO_REQUEST_CODE = 0x00000011;
    private static final int PERMISSION_CAMERA_REQUEST_CODE = 0x00000012;
    private static final int CAMERA_REQUEST_CODE = 0x00000013;

    private MainActionBar actionBar;
    private Button submitBt;
    private RecyclerView recyclerView;
    private ArrayList<String> images;
    private PhotoEditAdapter adapter;

    private ToroProgressView progressView;
    private String mPhotoPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_photo_activity);
        actionBar = findViewById(R.id.main_action_view);
        actionBar.updateView(getString(R.string.upload_photo),R.mipmap.action_back_icon, 0,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        },null);
        progressView = findViewById(R.id.toro_progress);
        submitBt = findViewById(R.id.submit_button);
        submitBt.setOnClickListener(this);
        recyclerView = findViewById(R.id.recycler_view);
        images = new ArrayList<>();
        updateImages(getIntent().getStringArrayListExtra(EXTRA_IMAGES));
        if(images.size() > AppConfig.PhotoMaxCoun) {
            images = (ArrayList<String>) images.subList(0,AppConfig.PhotoMaxCoun - 1);
        }
        adapter = new PhotoEditAdapter(this,images,plusOnclickListener,deleteOnclickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, AppConfig.PhotoSpanCount));
        recyclerView.addItemDecoration(new RecyclerItemDecoration(this.getResources().getDimensionPixelOffset(R.dimen.photo_list_photo_offset)));
    }

    private void updateImages(ArrayList<String> images) {
        if(images == null || images.size() < 1) {
            submitBt.setEnabled(false);
        } else {
            this.images = images;
            submitBt.setEnabled(true);
        }
    }

    public static Intent newIntent(Context context, ArrayList<String> images) {
        Intent intent = new Intent();
        intent.setClass(context,UploadPhotoActivity.class);
        intent.putStringArrayListExtra(EXTRA_IMAGES,images);
        return intent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button:
                submit();
                break;
        }
    }

    private void submit() {
        showProgress(0);
        ConnectManager.getInstance().uploadPhotos(this, images, ToroUserManager.getInstance(this).getToken(), new UIProgressListener() {
            @Override
            public void onUIProgress(long currentBytes, long contentLength, boolean done) {
                showProgress((int) (currentBytes * 100 / contentLength));
            }
        });
    }

    private void showProgress(int progress) {
        progressView.show(this);
        String formatString = getResources().getString(R.string.upload_progress_text_format);
        String text = String.format(formatString, progress + "%");
        progressView.setProgressText(text);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_CODE && data != null) {
            ArrayList<String> images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            for(String image : images) {
                this.images.add(image);
            }
            adapter.updateListData(this.images);
        }else if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if(StringUtils.isNotEmpty(mPhotoPath)) {
                    this.images.add(mPhotoPath);
                    adapter.updateListData(this.images);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //允许权限，有调起相机拍照。
                mPhotoPath = CameraUtils.openCamera(this,CAMERA_REQUEST_CODE);
            } else {
                //拒绝权限，弹出提示框。
                CameraUtils.showExceptionDialog(this,false);
            }
        }
    }

    private View.OnClickListener plusOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArrayList<String> menus = new ArrayList<>();
            menus.add(getString(R.string.take_photo_by_camera));
            menus.add(getString(R.string.choose_photo_from_album));
            IphoneDialogBottomMenu dialog = new IphoneDialogBottomMenu(UploadPhotoActivity.this,menus,new MenuItemOnClickListener() {
                @Override
                public void onClickMenuItem(View v, int item_index, String item) {
                    if(item.equals(getString(R.string.take_photo_by_camera))) {
                        CameraUtils.checkPermissionAndCamera(UploadPhotoActivity.this, PERMISSION_CAMERA_REQUEST_CODE, new CameraUtils.OnCameraPermissionListener() {
                            @Override
                            public void onHasePermission() {
                                mPhotoPath = CameraUtils.openCamera(UploadPhotoActivity.this,CAMERA_REQUEST_CODE);
                            }
                        });
                    } else if(item.equals(getString(R.string.choose_photo_from_album))){
                        ImageSelector.builder()
                                .useCamera(true) // 设置是否使用拍照
                                .setSingle(false)  //设置是否单选
                                .setViewImage(true) //是否点击放大图片查看,，默认为true
                                .setMaxSelectCount(AppConfig.PhotoMaxCoun - images.size()) // 图片的最大选择数量，小于等于0时，不限数量。
                                .start(UploadPhotoActivity.this, PHOTO_REQUEST_CODE); // 打开相册
                    }
                }
            });
            dialog.show();
        }
    };

    private View.OnClickListener deleteOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                int index = (int) v.getTag();
                if(index >=0 && index < images.size()) {
                    images.remove(index);
                    adapter.updateListData(images);
                }
            }catch (Exception e){

            }

        }
    };

    @Override
    public boolean bindData(final int tag, final Object object) {
        switch (tag) {
            case ConnectManager.UPLOAD_PHOTO_LIST:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = (String) object;
                        BaseResponeData data = DataModleParser.parserBaseResponeData(result);
                        if(data.isStatus()) {
                            // 上传成功
                            progressView.setProgressText(getString(R.string.upload_finish_submit_progress_text));
                            ConnectManager.getInstance().submitPhotoList(UploadPhotoActivity.this,DataModleParser.parserPhotoItems(data.getEntry()),new int[0],"",1,ToroUserManager.getInstance(UploadPhotoActivity.this).getToken());
                        } else {
                            progressView.hide(UploadPhotoActivity.this);
                            Toast.makeText(UploadPhotoActivity.this,getString(R.string.submit_failed),Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
            case ConnectManager.SUBMIT_PHOTO_LIST:
                progressView.hide(this);
                boolean status = super.bindData(tag,object);
                if(!status) {
                    Toast.makeText(UploadPhotoActivity.this,getString(R.string.submit_failed),Toast.LENGTH_LONG).show();
                    return status;
                } else {
                    Toast.makeText(UploadPhotoActivity.this,getString(R.string.submit_sucsses),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.NEED_REFRESH_PHOTO_LIST_RESULT, true);
                    setResult(RESULT_OK, intent);
                    finish();
                    return true;
                }
        }
        return false;
    }
}
