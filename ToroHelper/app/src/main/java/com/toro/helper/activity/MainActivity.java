package com.toro.helper.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.toro.helper.R;
import com.toro.helper.base.ToroActivity;
import com.toro.helper.base.ToroFragment;
import com.toro.helper.fragment.PhotoFragment;
import com.toro.helper.utils.CameraUtils;
import com.toro.helper.view.ChangeColorIconWithTextView;
import com.toro.helper.view.MainActionBar;
import com.toro.helper.view.iphone.IphoneDialogBottomMenu;
import com.toro.helper.view.iphone.MenuItemOnClickListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import helper.phone.toro.com.imageselector.utils.ImageSelector;

/**
 * Create By liujia
 * on 2018/10/19.
 **/
public class MainActivity extends ToroActivity implements
        ViewPager.OnPageChangeListener, View.OnClickListener {

    public static final int MAIN_PHOTO_FRAGMENT = 0;
    public static final int MAIN_HELPER_FRAGMENT = 1;
    public static final int MAIN_MARKET_FRAGMENT = 2;
    public static final int MAIN_ME_FRAGMENT = 3;

    private static final int PHOTO_REQUEST_CODE = 0x00000011;
    private static final int PERMISSION_CAMERA_REQUEST_CODE = 0x00000012;
    private static final int CAMERA_REQUEST_CODE = 0x00000013;

    private ViewPager mViewPager;
    private List<Fragment> mTabs = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;
    private MainActionBar mainActionBar;

    private PhotoFragment photoFragment;
    private String mPhotoPath;

    private List<ChangeColorIconWithTextView> mTabIndicator = new ArrayList<ChangeColorIconWithTextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

        initDatas();

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);

        mainActionBar = findViewById(R.id.main_action_view);

        mTabIndicator.get(MAIN_PHOTO_FRAGMENT).setIconAlpha(1.0f);
        mViewPager.setCurrentItem(MAIN_PHOTO_FRAGMENT, false);
        changeActionView(MAIN_PHOTO_FRAGMENT);
    }

    private void initDatas()
    {

        photoFragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putString("title", "photo");
        photoFragment.setArguments(args);
        mTabs.add(photoFragment);

        ToroFragment tabFragment1 = new ToroFragment();
        Bundle args1 = new Bundle();
        args.putString("title", "title1");
        tabFragment1.setArguments(args1);
        mTabs.add(tabFragment1);

        ToroFragment tabFragment2 = new ToroFragment();
        Bundle args2 = new Bundle();
        args.putString("title", "title2");
        tabFragment2.setArguments(args2);
        mTabs.add(tabFragment2);

        ToroFragment tabFragment3 = new ToroFragment();
        Bundle args3 = new Bundle();
        args.putString("title", "title3");
        tabFragment3.setArguments(args3);
        mTabs.add(tabFragment3);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {

            @Override
            public int getCount()
            {
                return mTabs.size();
            }

            @Override
            public Fragment getItem(int arg0)
            {
                return mTabs.get(arg0);
            }
        };

        initTabIndicator();

    }

    private void initTabIndicator()
    {
        ChangeColorIconWithTextView one = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_one);
        ChangeColorIconWithTextView two = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_two);
        ChangeColorIconWithTextView three = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_three);
        ChangeColorIconWithTextView four = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_four);

        mTabIndicator.add(one);
        mTabIndicator.add(two);
        mTabIndicator.add(three);
        mTabIndicator.add(four);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);

    }

    @Override
    public void onPageSelected(int arg0)
    {
        resetOtherTabs();
        mTabIndicator.get(arg0).setIconAlpha(1.0f);
        mViewPager.setCurrentItem(arg0, false);
        changeActionView(arg0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels)
    {
        if (positionOffset > 0)
        {
            ChangeColorIconWithTextView left = mTabIndicator.get(position);
            ChangeColorIconWithTextView right = mTabIndicator.get(position + 1);

            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state)
    {
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.id_indicator_one:
                mViewPager.setCurrentItem(MAIN_PHOTO_FRAGMENT, false);
                break;
            case R.id.id_indicator_two:
                mViewPager.setCurrentItem(MAIN_HELPER_FRAGMENT, false);
                break;
            case R.id.id_indicator_three:
                mViewPager.setCurrentItem(MAIN_MARKET_FRAGMENT, false);
                break;
            case R.id.id_indicator_four:
                mViewPager.setCurrentItem(MAIN_ME_FRAGMENT, false);
                break;

        }

    }

    /**
     * 重置其他的Tab
     */
    private void resetOtherTabs()
    {
        for (int i = 0; i < mTabIndicator.size(); i++)
        {
            mTabIndicator.get(i).setIconAlpha(0);
        }
    }

    public void changeActionView(int index) {
        switch (index){
            case MainActivity.MAIN_PHOTO_FRAGMENT:
                setupPhotoActionBar();
                break;
            case MainActivity.MAIN_HELPER_FRAGMENT:
                setupHelpeerActionBar();
                break;
            case MainActivity.MAIN_MARKET_FRAGMENT:
                setupMarketActionBar();
                break;
            case MainActivity.MAIN_ME_FRAGMENT:
                setupMeActionBar();
                break;
        }
    }

    private void setupPhotoActionBar() {
        mainActionBar.updateView(getResources().getString(R.string.app_name), 0, R.mipmap.icon_action_camera, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> menus = new ArrayList<>();
                menus.add(getString(R.string.take_photo_by_camera));
                menus.add(getString(R.string.choose_photo_from_album));
                IphoneDialogBottomMenu dialog = new IphoneDialogBottomMenu(MainActivity.this,menus,new MenuItemOnClickListener() {
                    @Override
                    public void onClickMenuItem(View v, int item_index, String item) {
                        if(item.equals(getString(R.string.take_photo_by_camera))) {
                            CameraUtils.checkPermissionAndCamera(MainActivity.this, PERMISSION_CAMERA_REQUEST_CODE, new CameraUtils.OnCameraPermissionListener() {
                                @Override
                                public void onHasePermission() {
                                    openCamera();
                                }
                            });
                        } else if(item.equals(getString(R.string.choose_photo_from_album))){
                            ImageSelector.builder()
                                    .useCamera(true) // 设置是否使用拍照
                                    .setSingle(false)  //设置是否单选
                                    .setViewImage(true) //是否点击放大图片查看,，默认为true
                                    .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
                                    .start(MainActivity.this, PHOTO_REQUEST_CODE); // 打开相册
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    private void setupHelpeerActionBar() {
        mainActionBar.updateView(getResources().getString(R.string.app_name), 0, R.mipmap.icon_action_more, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setupMarketActionBar() {
        mainActionBar.updateView(getResources().getString(R.string.main_market_title), 0, 0, null, null);
    }

    private void setupMeActionBar() {
        mainActionBar.updateView(getResources().getString(R.string.app_name), 0, 0, null, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_CODE && data != null) {
            ArrayList<String> images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            startActivity(UploadPhotoActivity.newIntent(this,images));
        }else if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> images = new ArrayList<>();
                images.add(mPhotoPath);
                startActivity(UploadPhotoActivity.newIntent(this,images));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //允许权限，有调起相机拍照。
                openCamera();
            } else {
                //拒绝权限，弹出提示框。
                showExceptionDialog(false);
            }
        }
    }

    private void showExceptionDialog(final boolean applyLoad) {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(getString(helper.phone.toro.com.imageselector.R.string.hint))
                .setMessage(getString(helper.phone.toro.com.imageselector.R.string.p_message))
                .setNegativeButton(getString(helper.phone.toro.com.imageselector.R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                }).setPositiveButton(getString(helper.phone.toro.com.imageselector.R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                CameraUtils.startAppSettings(MainActivity.this);
            }
        }).show();
    }

    /**
     * 调起相机拍照
     */
    private void openCamera() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (captureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = CameraUtils.createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null) {
                mPhotoPath = photoFile.getAbsolutePath();
                //通过FileProvider创建一个content类型的Uri
                Uri photoUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(captureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }


    public static Intent newIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }
}
