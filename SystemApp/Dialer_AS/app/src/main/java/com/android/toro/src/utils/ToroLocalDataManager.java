package com.android.toro.src.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Create By liujia
 * on 2018/11/13.
 **/
public class ToroLocalDataManager {

    private boolean assintantOpenLoundspeaker;
    private boolean loundspeakerMode;// 揚聲器模式

    private static ToroLocalDataManager instance;
    private SharedPreferences pre;

    public static ToroLocalDataManager getInstance(Context context) {
        if(instance == null) {
            instance = new ToroLocalDataManager(context);
        }
        return instance;
    }

    private ToroLocalDataManager(Context context) {
        pre = context.getSharedPreferences(this.getClass().getName(),Context.MODE_PRIVATE);
    }

    public boolean isAssintantOpenLoundspeaker() {
        assintantOpenLoundspeaker = pre.getBoolean("assintantOpenLoundspeaker", assintantOpenLoundspeaker);
        return assintantOpenLoundspeaker;
    }

    public void setAssintantOpenLoundspeaker(boolean assintantOpenLoundspeaker) {
        this.assintantOpenLoundspeaker = assintantOpenLoundspeaker;
        pre.edit().putBoolean("assintantOpenLoundspeaker", assintantOpenLoundspeaker).apply();
    }

    public boolean isLoundspeakerMode() {
        loundspeakerMode = pre.getBoolean("loundspeakerMode",loundspeakerMode);
        return loundspeakerMode;
    }

    public void setLoundspeakerMode(boolean loundspeakerMode) {
        this.loundspeakerMode = loundspeakerMode;
        pre.edit().putBoolean("loundspeakerMode",loundspeakerMode).apply();
    }
}
