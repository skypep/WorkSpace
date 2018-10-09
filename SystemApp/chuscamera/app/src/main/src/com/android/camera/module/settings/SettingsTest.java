package com.android.camera.module.settings;

import android.app.Activity;
import android.hardware.Camera;
import android.util.Log;

import com.chus.camera.R;

/**
 * Created by THINK on 2017/7/1.
*/

public class SettingsTest {
    final static String TAG = "CameraSettings.test";

    public static void test(Activity activity, Camera.Parameters parameters, int cameraId, Camera.CameraInfo[] cameraInfo) {
        Log.v(TAG, "SettingsTest.class " + SettingsTest.class.getPackage().getName());

        ComboPreferences comboPreferences = new ComboPreferences(activity);     // must new first !
        comboPreferences.setLocalId(activity, cameraId);                        // must set localId to init the Local preference

        CameraSettings cameraSettings = new CameraSettings(activity, parameters, cameraId, cameraInfo);
        PreferenceGroup group = cameraSettings.getPreferenceGroup(R.xml.chus_photo_preferences);
        Log.v(TAG, "group.size() : " + group.size());
        for(int i=0;i<group.size();i++) {
            ListPreference lp = (ListPreference)group.get(i);
            Log.v(TAG, "  :" + lp.getKey() + " = " + lp.getValue());
        }
    }
}
