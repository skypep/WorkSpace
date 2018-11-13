package com.android.toro.src.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Create By liujia
 * on 2018/11/13.
 **/
public class AssistantLocalManager {

    private boolean openLoundspeaker;

    private static AssistantLocalManager instance;
    private SharedPreferences pre;

    public static AssistantLocalManager getInstance(Context context) {
        if(instance == null) {
            instance = new AssistantLocalManager(context);
        }
        return instance;
    }

    private AssistantLocalManager(Context context) {
        pre = context.getSharedPreferences(this.getClass().getName(),Context.MODE_PRIVATE);
    }

    public boolean isOpenLoundspeaker() {
        openLoundspeaker = pre.getBoolean("openLoundspeaker",openLoundspeaker);
        return openLoundspeaker;
    }

    public void setOpenLoundspeaker(boolean openLoundspeaker) {
        this.openLoundspeaker = openLoundspeaker;
        pre.edit().putBoolean("openLoundspeaker",openLoundspeaker).apply();
    }
}
