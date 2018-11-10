package com.toro.helper.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.toro.helper.R;
import com.toro.helper.app.AppConfig;
import com.toro.helper.app.ToroRequestCode;
import com.toro.helper.base.ToroActivity;
import com.toro.helper.fragment.photo.PhotoAdapter;
import com.toro.helper.modle.BaseResponeData;
import com.toro.helper.modle.DataModleParser;
import com.toro.helper.modle.data.ToroDataModle;
import com.toro.helper.modle.photo.PhotoData;
import com.toro.helper.utils.CameraUtils;
import com.toro.helper.utils.ConnectManager;
import com.toro.helper.utils.StringUtils;
import com.toro.helper.view.AutoLoadRecyclerView;
import com.toro.helper.view.MainActionBar;
import com.toro.helper.view.RecyclerItemDecoration;
import com.toro.helper.view.iphone.IphoneDialogBottomMenu;
import com.toro.helper.view.iphone.MenuItemOnClickListener;

import java.util.ArrayList;
import java.util.List;

import helper.phone.toro.com.imageselector.utils.ImageSelector;

/**
 * Create By liujia
 * on 2018/10/31.
 **/
public class MyPhotoActivity extends ToroActivity {

    public static final String UPLOAD_REQUEST_EXTRA = "upload_request_extra";

    private PhotoAdapter adapter;

    protected AutoLoadRecyclerView recyclerView;
    private TextView emptyHint;
    private ProgressBar loadingProgress;
    private List<PhotoData> photoDatas;
    private MainActionBar mainActionBar;
    private LinearLayout deleteLayout;
    private String mPhotoPath;
    private boolean[] deleteChecks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_photo_activity);
        initView();
        setNormalAction();
        updatePhotoList();
    }

    private void setNormalAction() {
        mainActionBar = findViewById(R.id.main_action_view);
        mainActionBar.updateView(getResources().getString(R.string.family_photo), R.mipmap.action_back_icon, R.mipmap.icon_action_camera, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> menus = new ArrayList<>();
                menus.add(getString(R.string.take_photo_by_camera));
                menus.add(getString(R.string.choose_photo_from_album));
                IphoneDialogBottomMenu dialog = new IphoneDialogBottomMenu(MyPhotoActivity.this, menus, new MenuItemOnClickListener() {
                    @Override
                    public void onClickMenuItem(View v, int item_index, String item) {
                        if (item.equals(getString(R.string.take_photo_by_camera))) {
                            CameraUtils.checkPermissionAndCamera(MyPhotoActivity.this, new CameraUtils.OnCameraPermissionListener() {
                                @Override
                                public void onHasePermission() {
                                    mPhotoPath = CameraUtils.openCamera(MyPhotoActivity.this);
                                }
                            });
                        } else if (item.equals(getString(R.string.choose_photo_from_album))) {
                            ImageSelector.builder()
                                    .useCamera(true) // 设置是否使用拍照
                                    .setSingle(false)  //设置是否单选
                                    .setViewImage(true) //是否点击放大图片查看,，默认为true
                                    .setMaxSelectCount(AppConfig.PhotoMaxCoun) // 图片的最大选择数量，小于等于0时，不限数量。
                                    .start(MyPhotoActivity.this, CameraUtils.PHOTO_REQUEST_CODE); // 打开相册
                        }
                    }
                });
                dialog.show();
            }
        });
        mainActionBar.addRightImage(R.mipmap.edit_my_photo_icon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterEditMode();
            }
        });
        mainActionBar.removeRightText();
    }

    protected void initView() {
        adapter = new PhotoAdapter(this,null,true);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecyclerItemDecoration(getResources().getDimensionPixelOffset(R.dimen.photo_list_photo_offset)));
        recyclerView.setAdapter(adapter);
        recyclerView.setOnPauseListenerParams(new AutoLoadRecyclerView.OnLoadImageSwitchListener() {
            @Override
            public void onLoadImageSwitch(boolean flag) {
                adapter.onLoadImageSwitch(flag);
            }
        },false,false);
        emptyHint = findViewById(R.id.empty_hint);
        deleteLayout = findViewById(R.id.delete_layout);
        loadingProgress = findViewById(R.id.loading_progress);
    }

    private void enterEditMode() {
        mainActionBar.removeAddRightImage();
        mainActionBar.updateView(getResources().getString(R.string.edit), 0, 0, null, null);
        mainActionBar.addRightText(getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitEditMode();
            }
        });
        deleteChecks = new boolean[photoDatas.size()];
        adapter.enterEditMode(deleteChecks,onCheckClickListener);
    }

    private void exitEditMode() {
        setNormalAction();
        adapter.exitEditMode();
        deleteLayout.setVisibility(View.GONE);
    }

    private void showPhotoList() {
        if(photoDatas == null || photoDatas.size() < 1) {
            showEmptyHint();
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            loadingProgress.setVisibility(View.GONE);
            emptyHint.setVisibility(View.GONE);
            adapter.updatePhotoDatas(photoDatas);
        }

    }

    private void updatePhotoList() {
        showProgress();
        ConnectManager.getInstance().getPhotoListByUid(this,ToroDataModle.getInstance().getLoginUserData().getUid(),ToroDataModle.getInstance().getLocalData().getToken());
    }

    private void showEmptyHint() {
        recyclerView.setVisibility(View.GONE);
        loadingProgress.setVisibility(View.GONE);
        emptyHint.setVisibility(View.VISIBLE);
        emptyHint.setText(getString(R.string.empty_photo_hint));
    }

    private void showProgress() {
        recyclerView.setVisibility(View.GONE);
        loadingProgress.setVisibility(View.VISIBLE);
        emptyHint.setVisibility(View.GONE);
    }

    private void changeDeleteLayout() {
        int deleteCount = 0;
        for(boolean flag : deleteChecks) {
            if(flag) {
                deleteCount ++;
            }
        }
        if(deleteCount > 0) {
            deleteLayout.setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.delete_text)).setText(getString(R.string.delete) + "(" + deleteCount + ")");
            deleteLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deletePhotos();
                }
            });
        } else {
            deleteLayout.setVisibility(View.GONE);
        }
    }

    private void deletePhotos() {
        showProgress();
        List<Integer> ids = new ArrayList<>();
        for(int i=0; i< deleteChecks.length;i++) {
            if(deleteChecks[i]) {
                ids.add(photoDatas.get(i).getId());
            }
        }
        ConnectManager.getInstance().deletePhotoList(this,ids,ToroDataModle.getInstance().getLocalData().getToken());
    }

    private View.OnClickListener onCheckClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = (int) v.getTag();
            deleteChecks[index] = !deleteChecks[index];
            adapter.updateEditCheckBox(deleteChecks);
            changeDeleteLayout();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CameraUtils.PHOTO_REQUEST_CODE && data != null) {
            ArrayList<String> images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            startActivityForResult(UploadPhotoActivity.newIntent(this,images), ToroRequestCode.UPLOAD_REQUEST_CODE);
        }else if (requestCode == CameraUtils.CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if(StringUtils.isNotEmpty(mPhotoPath)) {
                    ArrayList<String> images = new ArrayList<>();
                    images.add(mPhotoPath);
                    startActivityForResult(UploadPhotoActivity.newIntent(this,images),ToroRequestCode.UPLOAD_REQUEST_CODE);
                }
            }
        } else if(requestCode == ToroRequestCode.UPLOAD_REQUEST_CODE && data != null) {
            boolean needReload = data.getBooleanExtra(UPLOAD_REQUEST_EXTRA,false);
            if(needReload) {
                updatePhotoList();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CameraUtils.PERMISSION_CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //允许权限，有调起相机拍照。
                mPhotoPath = CameraUtils.openCamera(this);
            } else {
                //拒绝权限，弹出提示框。
                CameraUtils.showExceptionDialog(this,false);
            }
        }
    }

    @Override
    public boolean bindData(int tag, Object object) {
        boolean status = super.bindData(tag, object);
        if(status) {
            BaseResponeData data = DataModleParser.parserBaseResponeData((String) object);
            switch (tag) {
                case ConnectManager.GET_PHOTO_LIST_BY_UID:
                    photoDatas = DataModleParser.parserPhotoDatas(data.getEntry());
                    showPhotoList();
                    break;
                case ConnectManager.DELETE_PHOTO_LIST:
                    ToroDataModle.getInstance().updateToroFamilyPhotoList();
                    exitEditMode();
//                    updatePhotoList();//不必要，可删除
                    break;
            }
        } else {
            switch (tag) {
                case ConnectManager.GET_PHOTO_LIST_BY_UID:
                    showEmptyHint();;
                    break;
                case ConnectManager.DELETE_PHOTO_LIST:
                    showPhotoList();
                    Toast.makeText(this,R.string.delete_failed,Toast.LENGTH_LONG).show();
                    break;
            }

        }
        return status;
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,MyPhotoActivity.class);
        return intent;
    }
}
