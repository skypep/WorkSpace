package com.android.camera.module.prefs;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by THINK on 2017/8/15.
 */

public class MyListPreference extends android.preference.ListPreference {
    public MyListPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyListPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListPreference(Context context) {
        super(context);
    }





}
