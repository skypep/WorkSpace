package com.toro.helper.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.toro.helper.R;
import com.toro.helper.RongYunListener;
import com.toro.helper.RongyunManager;
import com.toro.helper.app.AppConfig;
import com.toro.helper.base.ToroActivity;
import com.toro.helper.base.ToroListFragment;
import com.toro.helper.fragment.FamilyMemberFragment;
import com.toro.helper.fragment.FamilyPhotoFragment1;
import com.toro.helper.fragment.MineFragment;
import com.toro.helper.modle.FamilyUserInfo;
import com.toro.helper.utils.CameraUtils;
import com.toro.helper.utils.ConnectManager;
import com.toro.helper.utils.StringUtils;
import com.toro.helper.view.ChangeColorIconWithTextView;
import com.toro.helper.view.MainActionBar;
import com.toro.helper.view.iphone.IphoneDialogBottomMenu;
import com.toro.helper.view.iphone.MenuItemOnClickListener;

import java.util.ArrayList;
import java.util.List;

import helper.phone.toro.com.imageselector.utils.ImageSelector;

/**
 * Create By liujia
 * on 2018/10/19.
 **/
public class MainActivity extends ToroActivity implements
        ViewPager.OnPageChangeListener, View.OnClickListener,
        RongYunListener.OnReceiveMessageListener{

    public static final int MAIN_PHOTO_FRAGMENT = 0;
    public static final int MAIN_HELPER_FRAGMENT = 1;
    public static final int MAIN_MARKET_FRAGMENT = 2;
    public static final int MAIN_ME_FRAGMENT = 3;

    public static final int PHOTO_REQUEST_CODE = 0x0011;
    public static final int PERMISSION_CAMERA_REQUEST_CODE = 0x0012;
    public static final int CAMERA_REQUEST_CODE = 0x0013;
    private static final int UPLOAD_REQUEST_CODE = 0x0014;
    private static final int CONTACT_REQUEST_CODE = 0x0015;
    private static final int EDIT_MEMBER_REQUEST_CODE = 0x0016;
    public static final int EDIT_PERSONAL_DETAILS_REQUEST_CODE = 0x0017;

    public static final String NEED_REFRESH_PHOTO_LIST_RESULT = "need_refresh_photo_list_result";
    public static final String NEED_REFRESH_PHOTO_LIST_AND_MINE_RESULT = "need_refresh_photo_list_and_ming_result";

    private ViewPager mViewPager;
    private List<Fragment> mTabs = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;
    private MainActionBar mainActionBar;

    private FamilyPhotoFragment1 photoFragment;
    private FamilyMemberFragment familyFragment;
    private MineFragment mineFragment;
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

        RongyunManager.getInstance().addOnReceiveMessageListener(this);
    }

    @Override
    protected void onDestroy() {
        RongyunManager.getInstance().removeOnReceiveMessageListener(this);
        super.onDestroy();
    }

    private void initDatas()
    {

        photoFragment = new FamilyPhotoFragment1();
        Bundle args = new Bundle();
        args.putString("title", "photo");
        photoFragment.setArguments(args);
        mTabs.add(photoFragment);

        familyFragment = new FamilyMemberFragment();
        Bundle args1 = new Bundle();
        args.putString("title", "family");
        familyFragment.setArguments(args1);
        mTabs.add(familyFragment);

        Fragment tabFragment2 = new Fragment();
        Bundle args2 = new Bundle();
        args.putString("title", "title2");
        tabFragment2.setArguments(args2);
        mTabs.add(tabFragment2);

        mineFragment = new MineFragment();
        Bundle args3 = new Bundle();
        args.putString("title", "mine");
        mineFragment.setArguments(args3);
        mTabs.add(mineFragment);

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
                                    mPhotoPath = CameraUtils.openCamera(MainActivity.this,CAMERA_REQUEST_CODE);
                                }
                            });
                        } else if(item.equals(getString(R.string.choose_photo_from_album))){
                            ImageSelector.builder()
                                    .useCamera(true) // 设置是否使用拍照
                                    .setSingle(false)  //设置是否单选
                                    .setViewImage(true) //是否点击放大图片查看,，默认为true
                                    .setMaxSelectCount(AppConfig.PhotoMaxCoun) // 图片的最大选择数量，小于等于0时，不限数量。
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
                ArrayList<String> menus = new ArrayList<>();
                menus.add(getString(R.string.add_family_from_contact));
                menus.add(getString(R.string.add_family_from_edit));
                IphoneDialogBottomMenu dialog = new IphoneDialogBottomMenu(MainActivity.this,menus,new MenuItemOnClickListener() {
                    @Override
                    public void onClickMenuItem(View v, int item_index, String item) {
                        if(item.equals(getString(R.string.add_family_from_contact))) {
                            Intent intent=new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                            startActivityForResult(intent,CONTACT_REQUEST_CODE);
                        } else if(item.equals(getString(R.string.add_family_from_edit))) {
                            startActivityForResult(FamilyMemberEditActivity.createAddIntent(MainActivity.this,null),EDIT_MEMBER_REQUEST_CODE);
                        }
                    }
                });
                dialog.show();
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
            startActivityForResult(UploadPhotoActivity.newIntent(this,images),UPLOAD_REQUEST_CODE);
        }else if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if(StringUtils.isNotEmpty(mPhotoPath)) {
                    ArrayList<String> images = new ArrayList<>();
                    images.add(mPhotoPath);
                    startActivityForResult(UploadPhotoActivity.newIntent(this,images),UPLOAD_REQUEST_CODE);
                }
            }
        }else if(requestCode == UPLOAD_REQUEST_CODE){
            if(data != null) {
                boolean needUpate = data.getBooleanExtra(NEED_REFRESH_PHOTO_LIST_RESULT,false);
                if(needUpate) {
                    photoFragment.doanloadDatas(true);
                }
            }
        } else if(requestCode == EDIT_PERSONAL_DETAILS_REQUEST_CODE){
            if(data != null) {
                boolean needUpate = data.getBooleanExtra(NEED_REFRESH_PHOTO_LIST_AND_MINE_RESULT,false);
                if(needUpate) {
                    photoFragment.doanloadDatas(true);
                    mineFragment.update();
                }
            }
        } else if(requestCode == CONTACT_REQUEST_CODE) {
            String phoneNumber = "";
            if(data != null) {
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (null != cursor && cursor.moveToFirst()){
                    phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    //得到纯数字电话号码
                    if (phoneNumber.startsWith("+86")) {
                        phoneNumber = phoneNumber.replace("+86", "");
                    }
                    phoneNumber = phoneNumber.replace(" ", "");
                    phoneNumber = phoneNumber.replace("-", "");
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    cursor.close();
                    FamilyUserInfo userInfo = new FamilyUserInfo();
                    userInfo.setPhone(phoneNumber);
                    userInfo.setName(name);
                    startActivityForResult(FamilyMemberEditActivity.createAddIntent(this,userInfo),EDIT_MEMBER_REQUEST_CODE);
                }
            }
        }else if(requestCode == EDIT_MEMBER_REQUEST_CODE) {
            if(data != null) {
                boolean needUpate = data.getBooleanExtra(FamilyMemberEditActivity.EDIT_RESULT,false);
                if(needUpate) {
                    familyFragment.doanloadDatas(true);
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

    public static Intent newIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    public boolean onReceived(String message) {
        if(message.equals("MEMBER_DELETE") || message.equals("MEMBER_ADD") || message.equals("MEMBER_ACCEPT")) {
            familyFragment.doanloadDatas(true);
            return true;
        } else if(message.equals("PHOTO_RELEASE") || message.equals("PHOTO_DELETE")) {
            photoFragment.doanloadDatas(true);
        }
        return false;
    }

    @Override
    public boolean bindData(int tag, Object object) {
        boolean flag = super.bindData(tag, object);
        switch (tag) {
            case ConnectManager.AGREEN_MEMBER:
                familyFragment.doanloadDatas(true);
                if(flag) {
                    photoFragment.doanloadDatas(true);
                }
                break;
        }
        return flag;
    }
}
