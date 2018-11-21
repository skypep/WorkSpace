package com.android.toro.src.setting;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.dialer.R;
import com.android.toro.src.utils.ToroLocalDataManager;

/**
 * Create By liujia
 * on 2018/11/14.
 **/
public class ToroDialerSettingsActivity extends ToroSettingActivity {

    @Override
    protected int getTitleResId() {
        return R.string.call_setting;
    }

    @Override
    protected Fragment createFragment() {
        return new CallSettingFragment();
    }

    public static class CallSettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.call_settings);
            findPreference(getString(R.string.call_transfer_key)).setOnPreferenceClickListener(this);
            findPreference(getString(R.string.call_recording_key)).setOnPreferenceClickListener(this);
        }


        @Override
        public boolean onPreferenceClick(Preference preference) {
            if(preference.getKey().equals(getString(R.string.call_transfer_key))) {
                startActivity(CallTransferActivity.createIntent(getContext()));
                return true;
            }else if(preference.getKey().equals(getString(R.string.call_recording_key))) {
                startActivity(CallRecordingActivity.createIntent(getContext()));
                return true;
            }
            return false;
        }
    }

}
