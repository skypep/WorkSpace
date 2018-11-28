package com.android.toro.src.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dialer.R;

/**
 * Create By liujia
 * on 2018/11/28.
 **/
public class ActionButton extends RelativeLayout {
    private TextView text;
    private ImageView icon;
    private RelativeLayout layout;

    public ActionButton(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        inflate(context, R.layout.toro_action_button, this);
        text = (TextView) findViewById(R.id.text);
        icon = findViewById(R.id.icon);
        layout = findViewById(R.id.button_layout);
    }

    public void setText(int res){
        setText(getResources().getString(res));
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void showBackIcon(boolean flag) {
        if(flag) {
            icon.setVisibility(VISIBLE);
        } else {
            icon.setVisibility(GONE);
        }

    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        layout.setOnClickListener(l);
    }
}
