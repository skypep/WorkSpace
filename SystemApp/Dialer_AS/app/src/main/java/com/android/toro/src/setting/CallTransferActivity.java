package com.android.toro.src.setting;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.provider.ContactsContract;
import android.view.View;

import com.android.dialer.R;
import com.android.dialer.app.calllog.CallLogListItemViewHolder;
import com.android.dialer.logging.ContactSource;

/**
 * Create By liujia
 * on 2018/11/21.
 **/
public class CallTransferActivity extends ToroSettingActivity {

    private static final int CONTACT_REQUEST_FOR_ALL = 0x11;
    private static final int CONTACT_REQUEST_FOR_BUSY = 0x12;
    private static final int CONTACT_REQUEST_FOR_NOANSWER = 0x13;
    private static final int CONTACT_REQUEST_FOR_UNCONNECT = 0x14;

    @Override
    protected int getTitleResId() {
        return R.string.call_transfer_title;
    }

    @Override
    protected Fragment createFragment() {
        return new CallTransferFragment();
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,CallTransferActivity.class);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode >= CONTACT_REQUEST_FOR_ALL && requestCode <= CONTACT_REQUEST_FOR_UNCONNECT) {
            String phoneNumber = "";
            if(data != null) {
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (null != cursor && cursor.moveToFirst()){
                    phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    //得到纯数字电话号码
                    if (phoneNumber.startsWith("+86")) {
                        phoneNumber = phoneNumber.replace("+86", "");
                    }
                    phoneNumber = phoneNumber.replace(" ", "");
                    phoneNumber = phoneNumber.replace("-", "");
                    cursor.close();
                    CallTransferFragment fragment = (CallTransferFragment) getFragment();
                    fragment.updatePhoneNumber(phoneNumber,requestCode);
                }
            }
        }
    }

    public static class CallTransferFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener,Preference.OnPreferenceChangeListener {

        private PreferenceCategory transTypePerference;
        private SwitchPreference transferPreference;
        private boolean transferTypeIsShow = true;
        private ToroTransferDialogPreference typeAllPreference,typeBusyPreference,typeNoAnswerPreference,typeUnconnectPreference;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.call_transfer);
            transTypePerference = (PreferenceCategory) findPreference("transfer_type");
            transferPreference = (SwitchPreference) findPreference(getString(R.string.call_transfer_key));
            typeAllPreference = (ToroTransferDialogPreference) findPreference(getString(R.string.all_calls_transferred_key));
            typeAllPreference.setContactOnclickListener(new ContactOnclickListener(getString(R.string.all_calls_transferred_key)));

            typeBusyPreference = (ToroTransferDialogPreference) findPreference(getString(R.string.busy_calls_transferred_key));
            typeBusyPreference.setContactOnclickListener(new ContactOnclickListener(getString(R.string.busy_calls_transferred_key)));

            typeNoAnswerPreference = (ToroTransferDialogPreference) findPreference(getString(R.string.no_answers_calls_transferred_key));
            typeNoAnswerPreference.setContactOnclickListener(new ContactOnclickListener(getString(R.string.no_answers_calls_transferred_key)));

            typeUnconnectPreference = (ToroTransferDialogPreference) findPreference(getString(R.string.unconnect_calls_transferred_key));
            typeUnconnectPreference.setContactOnclickListener(new ContactOnclickListener(getString(R.string.unconnect_calls_transferred_key)));

            updateTransferTypePreference();
            transferPreference.setOnPreferenceChangeListener(this);
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            return false;
        }

        private void updateTransferTypePreference() {
            if(getTransferValue()) {
                if(!transferTypeIsShow){
                    getPreferenceScreen().addPreference(transTypePerference);
                    transferTypeIsShow = true;
                }
            } else {
                if(transferTypeIsShow){
                    getPreferenceScreen().removePreference(transTypePerference);
                    transferTypeIsShow = false;
                }
            }
            typeAllPreference.updateSummary();
            typeBusyPreference.updateSummary();
            typeNoAnswerPreference.updateSummary();
            typeUnconnectPreference.updateSummary();
        }

        private boolean getTransferValue() {
            return PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(getString(R.string.call_transfer_key),false);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if(preference.getKey().equals(getString(R.string.call_transfer_key))) {
                transferPreference.setChecked((boolean)newValue);
                if(!(boolean)newValue){
                    typeAllPreference.clear();
                    typeBusyPreference.clear();
                    typeNoAnswerPreference.clear();
                    typeUnconnectPreference.clear();
                    Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:%23%23002%23"));
                    startActivity(intent);
                }
                updateTransferTypePreference();
            }
            return false;
        }

        public void updatePhoneNumber(String phoneNumber,int code) {
            switch (code) {
                case CONTACT_REQUEST_FOR_ALL:
                    typeAllPreference.updatePhoneNum(phoneNumber);
                    break;
                case CONTACT_REQUEST_FOR_BUSY:
                    typeBusyPreference.updatePhoneNum(phoneNumber);
                    break;
                case CONTACT_REQUEST_FOR_NOANSWER:
                    typeNoAnswerPreference.updatePhoneNum(phoneNumber);
                    break;
                case CONTACT_REQUEST_FOR_UNCONNECT:
                    typeUnconnectPreference.updatePhoneNum(phoneNumber);
                    break;
            }
        }

        class ContactOnclickListener implements View.OnClickListener {

            private String key;

            public ContactOnclickListener(String key) {
                this.key = key;
            }

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                int requestCode = 0;
                if(key.equals(getString(R.string.all_calls_transferred_key))) {
                    requestCode = CONTACT_REQUEST_FOR_ALL;
                }else if(key.equals(getString(R.string.no_answers_calls_transferred_key))) {
                    requestCode = CONTACT_REQUEST_FOR_NOANSWER;
                }else if(key.equals(getString(R.string.busy_calls_transferred_key))) {
                    requestCode = CONTACT_REQUEST_FOR_BUSY;
                }else if(key.equals(getString(R.string.unconnect_calls_transferred_key))) {
                    requestCode = CONTACT_REQUEST_FOR_UNCONNECT;
                }
                getActivity().startActivityForResult(intent, requestCode);
            }
        }
    }



}
