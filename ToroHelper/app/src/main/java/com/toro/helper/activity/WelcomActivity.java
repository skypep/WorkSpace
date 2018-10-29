package com.toro.helper.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.toro.helper.R;
import com.toro.helper.base.ToroActivity;
import com.toro.helper.modle.BaseResponeData;
import com.toro.helper.modle.DataModleParser;
import com.toro.helper.modle.LoginUserData;
import com.toro.helper.modle.ToroUserManager;
import com.toro.helper.utils.ConnectManager;
import com.toro.helper.utils.StringUtils;

public class WelcomActivity extends ToroActivity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcom_activity);
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(isFinishing()) {
//                    return;
//                }
//                if(StringUtils.isEmpty(ToroUserManager.getInstance(WelcomActivity.this).getToken())){
//                    startActivity(LoginActivity.newIntent(WelcomActivity.this));
//                } else {
//                    startActivity(MainActivity.newIntent(WelcomActivity.this));
//                }
//
//                finish();
//            }
//        },2000);
        String token = ToroUserManager.getInstance(this).getToken();
        String phone = ToroUserManager.getInstance(this).getPhone();
        String password = ToroUserManager.getInstance(this).getPwd();
        boolean isQuickLogin = ToroUserManager.getInstance(this).isQuickLogin();
        /**
         * 如果没有登陆过，手动登陆
         * 如果正常登陆过，自动登陆
         * 如果快捷登陆过，检查token是否有效
         * 如果自动登陆失败，手动登陆
         * 自动登陆成功，获取登陆用户信息
         */
        if(StringUtils.isEmpty(token)) {
            delayToLoginActivity();
        } else if(isQuickLogin) {

        } else {
            startActivity(MainActivity.newIntent(WelcomActivity.this));
        }
//        ConnectManager.getInstance().getLoginUserInfo(this,token);

    }

    private void delayToLoginActivity() {
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

    @Override
    public boolean bindData(int tag, Object object) {
        boolean staus = super.bindData(tag, object);
        BaseResponeData data = DataModleParser.parserBaseResponeData((String) object);
        if(staus) {
            switch (tag){
                case ConnectManager.GET_LOGIN_USERE_INFO:
                    LoginUserData loginUserData = DataModleParser.parserLoginUserData(data.getEntry());
                    ToroUserManager.getInstance(this).setLoginUserData(loginUserData);
                    break;
            }
        }
        return staus;
    }
}
