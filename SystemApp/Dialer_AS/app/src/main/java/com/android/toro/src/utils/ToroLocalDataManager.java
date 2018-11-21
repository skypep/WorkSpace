package com.android.toro.src.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.dialer.R;

/**
 * Create By liujia
 * on 2018/11/13.
 **/
public class ToroLocalDataManager {

    private boolean assintantOpenLoundspeaker;

    private static ToroLocalDataManager instance;
    private SharedPreferences pre;
    private Context mContext;

    public static ToroLocalDataManager getInstance(Context context) {
        if(instance == null) {
            instance = new ToroLocalDataManager(context);
        }
        return instance;
    }

    private ToroLocalDataManager(Context context) {
        mContext = context.getApplicationContext();
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

    public boolean isReceiverMode() {
        boolean value = PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean(mContext.getString(R.string.receiver_model_key),true);
        return value;
    }

    public String getStringByKey(String key,String defaultValue) {
        return pre.getString(key,defaultValue);
    }

    public void setStringByKey(String key,String value) {
        pre.edit().putString(key,value).apply();
    }
}
