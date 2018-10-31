package com.toro.helper.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.toro.helper.RongyunManager;
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
    }

}
