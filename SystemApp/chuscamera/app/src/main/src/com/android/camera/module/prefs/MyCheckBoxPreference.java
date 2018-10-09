package com.android.camera.module.prefs;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by THINK on 2017/8/15.
 */

public class MyCheckBoxPreference extends android.preference.CheckBoxPreference {

    public MyCheckBoxPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyCheckBoxPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyCheckBoxPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCheckBoxPreference(Context context) {
        super(context);
    }
}
