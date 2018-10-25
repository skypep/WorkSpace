package com.toro.helper.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.toro.helper.R;
import com.toro.helper.modle.ToroUserManager;
import com.toro.helper.utils.StringUtils;

public class WelcomActivity extends AppCompatActivity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcom_activity);
//        ToroUserManager.getInstance(this).setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOjc3LCJpc3MiOiJzdHBob25lIiwidXNlclR5cGUiOjIsImV4cCI6MTU0MDg4OTAyMywiaWF0IjoxNTQwMjg0MjIzLCJqdGkiOiI0Y2VhMzZlOWE1MzA0YWYzYmFmN2YxYjc5NmEyZjZmNCJ9.ECrG3X9r25r6LTLzgIU0zijCi5kQ2spvjhFwPHlmelc");
//        ToroUserManager.getInstance(this).setToken("authorization\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOjc3LCJpc3MiOiJzdHBob25lIiwidXNlclR5cGUiOjIsImV4cCI6MTU0MDg4OTAyMywiaWF0IjoxNTQwMjg0MjIzLCJqdGkiOiI0Y2VhMzZlOWE1MzA0YWYzYmFmN2YxYjc5NmEyZjZmNCJ9.ECrG3X9r25r6LTLzgIU0zijCi5kQ2spvjhFwPHlmelc");
//        ToroUserManager.getInstance(this).setToken("");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isFinishing()) {
                    return;
                }
                if(StringUtils.isEmpty(ToroUserManager.getInstance(WelcomActivity.this).getToken())){
                    startActivity(LoginActivity.newIntent(WelcomActivity.this));
                } else {
                    startActivity(MainActivity.newIntent(WelcomActivity.this));
                }

                finish();
            }
        },2000);
    }
}
