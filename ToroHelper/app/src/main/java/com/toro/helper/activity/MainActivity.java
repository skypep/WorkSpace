package com.toro.helper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.toro.helper.R;
import com.toro.helper.base.ToroNetworkActivity;
import com.toro.helper.fragment.NetWorkFragment;
import com.toro.helper.fragment.PhotoFragment;
import com.toro.helper.view.ChangeColorIconWithTextView;
import com.toro.helper.view.MainActionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By liujia
 * on 2018/10/19.
 **/
public class MainActivity extends ToroNetworkActivity implements
        ViewPager.OnPageChangeListener, View.OnClickListener {

    public static final int MAIN_PHOTO_FRAGMENT = 0;
    public static final int MAIN_HELPER_FRAGMENT = 1;
    public static final int MAIN_MARKET_FRAGMENT = 2;
    public static final int MAIN_ME_FRAGMENT = 3;

    private ViewPager mViewPager;
    private List<Fragment> mTabs = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;
    private MainActionBar mainActionBar;

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

        PhotoFragment photoFragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putString("title", "photo");
        photoFragment.setArguments(args);
        mTabs.add(photoFragment);

        NetWorkFragment tabFragment1 = new NetWorkFragment();
        Bundle args1 = new Bundle();
        args.putString("title", "title1");
        tabFragment1.setArguments(args1);
        mTabs.add(tabFragment1);

        NetWorkFragment tabFragment2 = new NetWorkFragment();
        Bundle args2 = new Bundle();
        args.putString("title", "title2");
        tabFragment2.setArguments(args2);
        mTabs.add(tabFragment2);

        NetWorkFragment tabFragment3 = new NetWorkFragment();
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

    public static Intent newIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }
}
