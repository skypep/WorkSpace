package com.toro.helper.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.toro.helper.R;
import com.toro.helper.RongYunListener;
import com.toro.helper.RongyunManager;
import com.toro.helper.activity.LoginActivity;
import com.toro.helper.modle.data.ToroDataModle;

/**
 * Create By liujia
 * on 2018/10/26.
 **/
public class App extends Application {

    private static final String Tag = "App";

    public static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        RongyunManager.getInstance().init(this);
        ToroDataModle.getInstance().init(this);
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    private String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    public void RongYunConnect(String token) {
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
            RongyunManager.getInstance().connect(token,null);
        }
        RongyunManager.getInstance().addConnectionStatusListener(new RongYunListener.ConnectionStatusListener() {
            @Override
            public void onChanged(String name) {
                if(name.equals("KICKED_OFFLINE_BY_OTHER_CLIENT")){
                    // 在别处登陆 强制退出账号
                    ToroDataModle.getInstance().loginOut();
                    Intent intent = LoginActivity.newIntent(instance);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(instance, R.string.coerce_sigin_out,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
