package com.android.toro.src;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.android.toro.src.utils.ToroLocalDataManager;

/**
 * Create By liujia
 * on 2018/11/14.
 **/
public class ToroDialerSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.rgb(0xF5,0xF7,0xFE));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.toro_settings_activity);
        initView();
    }

    private void initView() {
        Switch modeSwitch = findViewById(R.id.switch_view);
        if(ToroLocalDataManager.getInstance(getApplicationContext()).isLoundspeakerMode()) {
            modeSwitch.setChecked(false);
        } else {
            modeSwitch.setChecked(true);
        }
        modeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ToroLocalDataManager.getInstance(getApplicationContext()).setLoundspeakerMode(!isChecked);
            }
        });
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
