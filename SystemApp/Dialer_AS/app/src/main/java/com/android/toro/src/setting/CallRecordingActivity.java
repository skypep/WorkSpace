package com.android.toro.src.setting;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;

import com.android.dialer.R;

/**
 * Create By liujia
 * on 2018/11/21.
 **/
public class CallRecordingActivity extends ToroSettingActivity {
    @Override
    protected int getTitleResId() {
        return R.string.call_recording_title;
    }

    @Override
    protected Fragment createFragment() {
        return new CallRecordingFragment();
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,CallRecordingActivity.class);
        return intent;
    }

    public static class CallRecordingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        private PreferenceCategory recordObjPreference,customObjPreference;
        private SwitchPreference recordPreference;
        private CheckBoxPreference allRecordPreference,customRecordPreference;
        private boolean recordObjIsShow = true,customObjIsShow = true;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.call_recording);
            recordObjPreference = (PreferenceCategory) findPreference("recording_object");
            customObjPreference = (PreferenceCategory) findPreference("custom_object");
            recordPreference = (SwitchPreference) findPreference(getString(R.string.call_recording_key));
            allRecordPreference = (CheckBoxPreference) findPreference(getString(R.string.call_recording_all_key));
            customRecordPreference = (CheckBoxPreference) findPreference(getString(R.string.call_recording_custom_key));
            recordPreference.setOnPreferenceChangeListener(this);
            allRecordPreference.setOnPreferenceChangeListener(this);
            customRecordPreference.setOnPreferenceChangeListener(this);
            updateHidePreference();
        }

        private void updateHidePreference() {
            if(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(getString(R.string.call_recording_key),false)){
                if(!recordObjIsShow){
                    getPreferenceScreen().addPreference(recordObjPreference);
                    recordObjIsShow = true;
                }
                if(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(getString(R.string.call_recording_custom_key),false)){
                    if(!customObjIsShow){
                        getPreferenceScreen().addPreference(customObjPreference);
                        customObjIsShow = true;
                    }
                } else {
                    if(customObjIsShow){
                        getPreferenceScreen().removePreference(customObjPreference);
                        customObjIsShow = false;
                    }
                }
            }else {
                if(recordObjIsShow){
                    getPreferenceScreen().removePreference(recordObjPreference);
                    recordObjIsShow = false;
                }
                if(customObjIsShow){
                    getPreferenceScreen().removePreference(customObjPreference);
                    customObjIsShow = false;
                }
            }
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if(preference.getKey().equals(getString(R.string.call_recording_key))) {
                recordPreference.setChecked((boolean)newValue);
                updateHidePreference();
                return false;
            } else if(preference.getKey().equals(getString(R.string.call_recording_all_key))) {
                allRecordPreference.setChecked(true);
                customRecordPreference.setChecked(false);
                updateHidePreference();
                return false;
            } else if(preference.getKey().equals(getString(R.string.call_recording_custom_key))) {
                customRecordPreference.setChecked(true);
                allRecordPreference.setChecked(false);
                updateHidePreference();
                return false;
            }
            return true;
        }
    }
}
