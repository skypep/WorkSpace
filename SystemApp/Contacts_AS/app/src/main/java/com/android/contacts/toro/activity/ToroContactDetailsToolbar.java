package com.android.contacts.toro.activity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.contacts.R;

public class ToroContactDetailsToolbar extends RelativeLayout {

    private TextView titleView;
    private Button leftButton;
    private Button rightButton;
    private ImageButton rightImageButton;

    public ToroContactDetailsToolbar(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        inflate(context, R.layout.toro_quick_contact_action_bar, this);
        titleView = findViewById(R.id.toro_action_title);
        leftButton = findViewById(R.id.toro_action_left_button);
        rightButton = findViewById(R.id.toro_action_right_button);
        rightImageButton = findViewById(R.id.toro_action_right_image_button);
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

    public void setVisible(boolean visible) {
        if (visible) {
            setAlpha(1);
            setVisibility(View.VISIBLE);
        } else {
            setAlpha(0);
            setVisibility(View.GONE);
        }
    }


}
