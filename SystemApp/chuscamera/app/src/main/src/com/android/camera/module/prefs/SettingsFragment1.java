package com.android.camera.module.prefs;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;

import com.toro.camera.R;

/**
 * Created by THINK on 2017/7/11.
 */

public class SettingsFragment1 extends PreferenceFragment {
    final static String TAG = "SettingsFragment1";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_fragment1_preferences);
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }


}
