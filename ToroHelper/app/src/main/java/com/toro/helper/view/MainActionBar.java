package com.toro.helper.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toro.helper.activity.MainActivity;
import com.toro.helper.R;

/**
 * Create By liujia
 * on 2018/10/19.
 **/
public class MainActionBar extends RelativeLayout {

    private TextView titleView;
    private ImageView rightImageView;

    public MainActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init() {
        titleView = findViewById(R.id.action_title);
        rightImageView = findViewById(R.id.action_right_img);
    }

    public void changeView(int index) {
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
        titleView.setText(getResources().getString(R.string.app_name));
        rightImageView.setVisibility(View.VISIBLE);
        rightImageView.setImageResource(R.mipmap.icon_action_camera);
    }

    private void setupHelpeerActionBar() {
        titleView.setText(getResources().getString(R.string.app_name));
        rightImageView.setVisibility(View.VISIBLE);
        rightImageView.setImageResource(R.mipmap.icon_action_more);
    }

    private void setupMarketActionBar() {
        titleView.setText(getResources().getString(R.string.main_market_title));
        rightImageView.setVisibility(View.GONE);
    }

    private void setupMeActionBar() {
        titleView.setText(getResources().getString(R.string.app_name));
        rightImageView.setVisibility(View.GONE);
    }
}
