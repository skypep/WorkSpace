package com.android.toro.src.setting;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.android.contacts.common.model.Contact;
import com.android.dialer.R;
import com.android.toro.src.utils.ToroLocalDataManager;
import com.android.toro.src.utils.ToroUtils;
import com.google.wireless.gdata.data.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

    public static class CallRecordingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener,Preference.OnPreferenceClickListener {

        private static final int REQUEST_CODE_PICK = 0x12;
        private PreferenceCategory recordObjPreference,customObjPreference;
        private SwitchPreference recordPreference;
        private CheckBoxPreference allRecordPreference,customRecordPreference;
        private Preference desContactPreference;
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
            desContactPreference = findPreference(getString(R.string.call_recording_designated_contact_key));
            recordPreference.setOnPreferenceChangeListener(this);
            allRecordPreference.setOnPreferenceChangeListener(this);
            customRecordPreference.setOnPreferenceChangeListener(this);
            desContactPreference.setOnPreferenceClickListener(this);
            if(!StringUtils.isEmpty(ToroLocalDataManager.getInstance(getContext()).getStringByKey(getString(R.string.call_recording_designated_contact_key),""))){
                desContactPreference.setSummary(ToroLocalDataManager.getInstance(getContext()).getStringByKey(getString(R.string.call_recording_designated_contact_key),""));
            } else {
                desContactPreference.setSummary(getString(R.string.un_des_contact));
            }
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
        public boolean onPreferenceClick(Preference preference) {
            if(preference.getKey().equals(getString(R.string.call_recording_designated_contact_key))){
                launchMultiplePhonePicker();
            }
            return false;
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

        private void setDesContact(String phoneNum) {
            if(StringUtils.isEmpty(phoneNum)) {
                return;
            }
            ToroLocalDataManager.getInstance(getContext()).setStringByKey(getString(R.string.call_recording_designated_contact_key),phoneNum);
            desContactPreference.setSummary(phoneNum);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == REQUEST_CODE_PICK && data != null) {
                try{
                    Uri uri = data.getData();
                    Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
                    if (null != cursor && cursor.moveToFirst()) {
                        String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        setDesContact(ToroUtils.getToroPhoneNum(phoneNumber));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), R.string.get_contact_failed, Toast.LENGTH_SHORT).show();
                }
            }
        }

        private void launchMultiplePhonePicker() {
            Intent intent=new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(intent, REQUEST_CODE_PICK);
        }
    }
}
