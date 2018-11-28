package com.android.toro.src.calllog;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dialer.R;
import com.android.toro.src.utils.ToroUtils;
import com.android.toro.src.view.ActionButton;

public class ToroCallDetailsToolbar extends RelativeLayout {

    private final TextView titleView;
    private final ActionButton backButton;

    public ToroCallDetailsToolbar(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        inflate(context, R.layout.toro_dialer_toolbar, this);
        titleView = (TextView) findViewById(R.id.title);
        backButton = findViewById(R.id.toro_toolbar_back);
        backButton.setText(R.string.toro_back);
        backButton.showBackIcon(true);
    }

    public void setBackListener(OnClickListener listener){
        backButton.setOnClickListener(listener);
    }

    public void setTitle(String title){
        titleView.setText(title);
    }

    public void setTitle(int stringID) {
        titleView.setText(stringID);
    }


}
