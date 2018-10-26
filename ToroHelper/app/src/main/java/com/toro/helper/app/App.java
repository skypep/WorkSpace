package com.toro.helper.app;

import android.app.Application;

import com.toro.helper.utils.ImageCache;

/**
 * Create By liujia
 * on 2018/10/26.
 **/
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageCache.getInstance().init(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
