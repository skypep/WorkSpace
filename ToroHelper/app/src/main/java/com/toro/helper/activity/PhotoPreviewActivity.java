package com.toro.helper.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.toro.helper.R;
import com.toro.helper.base.ToroActivity;
import com.toro.helper.modle.photo.PhotoItem;
import com.toro.helper.utils.ImageLoad;

import java.io.Serializable;
import java.util.List;

/**
 * Create By liujia
 * on 2018/10/26.
 **/
public class PhotoPreviewActivity extends ToroActivity {

    private static final String EXTRA_POSITION = "extra_position";
    private static final String EXTRA_LIST = "extra_list";

    private ViewPager viewPager;
    private TextView headText;
    private List<PhotoItem> photos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.photo_preview_activity);
        viewPager = findViewById(R.id.view_pager);
        headText = findViewById(R.id.heard_text);
        int position = getIntent().getIntExtra(EXTRA_POSITION,1);
        photos =  (List<PhotoItem>) getIntent().getSerializableExtra(EXTRA_LIST);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return photos == null?0:photos.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View adView = LayoutInflater.from(PhotoPreviewActivity.this).inflate(R.layout.photo_preview_item, null);
                PhotoView image = (PhotoView) adView.findViewById(R.id.flaw_img);
                image.setBackgroundColor(Color.BLACK);
//                ImageLoad.newInstance(image).load(photos.get(position),R.mipmap.image_loading);
                ImageLoad.GlidLoad(image,photos.get(position),R.mipmap.image_loading);
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                container.addView(adView);
                return adView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                headText.setText(position + 1 + "/" + photos.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

        viewPager.setCurrentItem(position, true);
        headText.setText(position + 1 + "/" + photos.size());
    }

    public static Intent createIntent(Context context,List<PhotoItem> photos,int position) {
        Intent intent = new Intent();
        intent.setClass(context,PhotoPreviewActivity.class);
        intent.putExtra(EXTRA_POSITION,position);
        intent.putExtra(EXTRA_LIST,(Serializable)photos);
        return intent;
    }

}
