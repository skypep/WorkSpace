package com.android.camera.module.prefs;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.chus.camera.R;

/**
 * Created by THINK on 2017/7/11.
 */

public class SettingsActivity1 extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_fragment1_preferences);
    }
}
