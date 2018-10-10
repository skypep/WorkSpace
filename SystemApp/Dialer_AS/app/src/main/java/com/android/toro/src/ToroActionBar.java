package com.android.toro.src;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.dialer.R;
import com.android.dialer.animation.AnimUtils;
import com.android.dialer.util.DialerUtils;

public class ToroActionBar extends FrameLayout {

    private TextView titleView;
    private Button leftButton;
    private Button rightButton;
    private ImageButton rightImageButton;

    protected boolean mIsFadedOut = false;
    private static final int ANIMATION_DURATION = 200;

    public ToroActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        titleView = findViewById(R.id.toro_action_title);
        leftButton = findViewById(R.id.toro_action_left_button);
        rightButton = findViewById(R.id.toro_action_right_button);
        rightImageButton = findViewById(R.id.toro_action_right_image_button);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        super.onFinishInflate();
    }

    public void setTitleText(String text) {
        titleView.setText(text);
    }

    public void setLeftButton(String text,OnClickListener listener) {
        leftButton.setText(text);
        if(listener != null) {
            leftButton.setOnClickListener(listener);
        }
    }

    public void setRightButton(String text,OnClickListener listener) {
        rightButton.setVisibility(View.VISIBLE);
        rightImageButton.setVisibility(View.GONE);
        int wid = rightButton.getWidth();
        rightButton.setText(text);
        if(listener != null) {
            rightButton.setOnClickListener(listener);
        }
    }

    public void setRightImageButton(int imgRes,OnClickListener listener) {
        rightImageButton.setVisibility(View.VISIBLE);
        rightButton.setVisibility(View.GONE);
        rightImageButton.setImageResource(imgRes);
        if(listener != null) {
            rightImageButton.setOnClickListener(listener);
        }
    }

    public boolean isFadedOut() {
        return mIsFadedOut;
    }

    public void fadeOut() {
        fadeOut(null);
    }

    public void fadeOut(AnimUtils.AnimationCallback callback) {
        AnimUtils.fadeOut(this, ANIMATION_DURATION, callback);
        mIsFadedOut = true;
    }

    public void fadeIn() {
        AnimUtils.fadeIn(this, ANIMATION_DURATION);
        mIsFadedOut = false;
    }

    public void fadeIn(AnimUtils.AnimationCallback callback) {
        AnimUtils.fadeIn(this, ANIMATION_DURATION, AnimUtils.NO_DELAY, callback);
        mIsFadedOut = false;
    }

    public void setVisible(boolean visible) {
        if (visible) {
            setAlpha(1);
            setVisibility(View.VISIBLE);
            mIsFadedOut = false;
        } else {
            setAlpha(0);
            setVisibility(View.GONE);
            mIsFadedOut = true;
        }
    }
}
