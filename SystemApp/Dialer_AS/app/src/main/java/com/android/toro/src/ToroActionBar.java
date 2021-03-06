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
import com.android.toro.src.view.ActionButton;

public class ToroActionBar extends FrameLayout {

    private TextView titleView;
    private ActionButton leftButton;
    private ActionButton rightButton;
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
        rightButton.setText(text);
        if(listener != null) {
            rightButton.setOnClickListener(listener);
        }else {
            rightButton.setVisibility(View.GONE);
        }
    }

    public void setRightImageButton(int imgRes,OnClickListener listener) {
        rightImageButton.setVisibility(View.VISIBLE);
        rightButton.setVisibility(View.GONE);
        rightImageButton.setImageResource(imgRes);
        if(listener != null) {
            rightImageButton.setOnClickListener(listener);
        }else{
            rightImageButton.setVisibility(View.GONE);
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
