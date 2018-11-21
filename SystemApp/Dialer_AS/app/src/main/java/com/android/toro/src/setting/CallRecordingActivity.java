package com.android.toro.src.setting;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

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

    public static class CallRecordingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.call_recording);
        }


        @Override
        public boolean onPreferenceClick(Preference preference) {

            return false;
        }
    }
}
