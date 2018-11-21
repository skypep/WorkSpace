package com.android.toro.src.setting;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.dialer.R;

/**
 * Create By liujia
 * on 2018/11/21.
 **/
public abstract class ToroSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.rgb(0xF5,0xF7,0xFE));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.toro_settings_activity);

        ((TextView)findViewById(R.id.tv_title)).setText(getString(getTitleResId()));
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.toro_setting_content);
        if (fragment == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.toro_setting_content, createFragment())
                    .commit();
        }
    }

    public Fragment getFragment() {
        return getFragmentManager().findFragmentById(R.id.toro_setting_content);
    }

    protected abstract int getTitleResId();
    protected abstract Fragment createFragment();
}
