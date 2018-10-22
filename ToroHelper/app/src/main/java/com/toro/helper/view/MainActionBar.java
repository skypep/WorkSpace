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
    private ImageView rightImageView,leftImageView;

    public MainActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        titleView = findViewById(R.id.action_title);
        rightImageView = findViewById(R.id.action_right_img);
        leftImageView = findViewById(R.id.action_left_img);
    }

    public void updateView(String title,int leftRes,int rightRes,OnClickListener leftListener,OnClickListener rightListener) {
        setTitle(title);
        setLeftImage(leftRes,leftListener);
        setRightImage(rightRes,rightListener);
    }

    private void setTitle(String title) {
        titleView.setText(title);
    }

    private void setLeftImage(int imgRes,OnClickListener listener) {
        if(listener != null) {
            leftImageView.setVisibility(View.VISIBLE);
            leftImageView.setImageResource(imgRes);
            leftImageView.setOnClickListener(listener);
        } else {
            leftImageView.setVisibility(View.GONE);
        }
    }

    private void setRightImage(int imgRes,OnClickListener listener) {
        if(listener != null) {
            rightImageView.setVisibility(View.VISIBLE);
            rightImageView.setImageResource(imgRes);
            rightImageView.setOnClickListener(listener);
        } else {
            rightImageView.setVisibility(View.GONE);
        }
    }
}
