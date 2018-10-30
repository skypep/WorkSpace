package com.toro.helper.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.toro.helper.RongyunManager;
import com.toro.helper.utils.ImageCache;

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
//        ImageCache.getInstance().init(this);
        RongyunManager.init(this);
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
            RongyunManager.connect(getApplicationContext(), token, new RongyunManager.RongYunConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                    Log.d(Tag,"onTokenIncorrect");
                }

                @Override
                public void onSuccess(String userid) {
                    Log.d(Tag,"onSuccess");
                }

                @Override
                public void onError(int errorCode) {
                    Log.d(Tag,"errorCode(" + errorCode + ")");
                }
            });

            RongyunManager.setOnReceiveMessageListener(new RongyunManager.OnReceiveMessageListener() {
                @Override
                public boolean onReceived(String message, int i) {
                    return false;
                }
            });

            RongyunManager.setConnectionStatusListener(new RongyunManager.ConnectionStatusListener() {
                @Override
                public void onChanged(String message) {
                    String me = message;
                }
            });
        }
    }


}
