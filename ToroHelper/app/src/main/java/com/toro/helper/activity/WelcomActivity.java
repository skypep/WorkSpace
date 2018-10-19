package com.toro.helper.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.toro.helper.R;

public class WelcomActivity extends AppCompatActivity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcom_activity);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isFinishing()) {
                    return;
                }
                startActivity(LoginActivity.newIntent(WelcomActivity.this));
                finish();
            }
        },2000);
    }
}
