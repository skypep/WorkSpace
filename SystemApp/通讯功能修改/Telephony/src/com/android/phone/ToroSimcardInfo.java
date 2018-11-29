package com.android.phone;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.preference.TwoStatePreference;
import android.telephony.CarrierConfigManager;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;

/**
 * Create By liujia
 * on 2018/11/27.
 **/
public class ToroSimcardInfo extends Activity {

    private static int slotIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.network_setting);
        ((TextView)findViewById(R.id.tv_title)).setText(getString(R.string.sim_card_info));
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.network_setting_content);
        if (fragment == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.network_setting_content, new SimcardInfoFragment())
                    .commit();
        }
    }

    public static class SimcardInfoFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{

        private SubscriptionManager mSubscriptionManager;
        private Phone mPhone;
        private ListPreference mButtonPreferredNetworkMode;
        private TwoStatePreference mAutoSelect;
        static final int preferredNetworkMode = Phone.PREFERRED_NT_MODE;
        private MyHandler mHandler;
        private ProgressDialog mProgressDialog;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mHandler = new MyHandler();
            mSubscriptionManager = SubscriptionManager.from(getActivity());
            updatePhone(slotIndex);
            mProgressDialog = new ProgressDialog(getContext());
            addPreferencesFromResource(R.xml.sim_card_info);
            mButtonPreferredNetworkMode = (ListPreference) findPreference("preferred_network_mode_key");
            mAutoSelect = (TwoStatePreference) findPreference("button_auto_select_key");
            initNetworkType();
            initAutoSelect();
        }

        private void initAutoSelect() {
            mAutoSelect.setOnPreferenceChangeListener(this);
            getNetworkSelectionMode();
        }

        private void initNetworkType() {

            int settingsNetworkMode = android.provider.Settings.Global.getInt(
                    mPhone.getContext().getContentResolver(),
                    android.provider.Settings.Global.PREFERRED_NETWORK_MODE + mPhone.getSubId(),
                    preferredNetworkMode);
            PersistableBundle carrierConfig =
                    PhoneGlobals.getInstance().getCarrierConfigForSubId(mPhone.getSubId());
            if (carrierConfig.getBoolean(CarrierConfigManager
                    .KEY_HIDE_PREFERRED_NETWORK_TYPE_BOOL)
                    && !mPhone.getServiceState().getRoaming()
                    && mPhone.getServiceState().getDataRegState()
                    == ServiceState.STATE_IN_SERVICE) {
                settingsNetworkMode = preferredNetworkMode;
            }
            mButtonPreferredNetworkMode.setValue(Integer.toString(settingsNetworkMode));
            mButtonPreferredNetworkMode.setOnPreferenceChangeListener(this);
        }

        private void updatePhone(int slotId) {
            final SubscriptionInfo sir = mSubscriptionManager
                    .getActiveSubscriptionInfoForSimSlotIndex(slotId);
            if (sir != null) {
                mPhone = PhoneFactory.getPhone(
                        SubscriptionManager.getPhoneId(sir.getSubscriptionId()));
            }
            if (mPhone == null) {
                // Do the best we can
                mPhone = PhoneGlobals.getPhone();
            }
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object objValue) {
            final int phoneSubId = mPhone.getSubId();
            if (preference == mButtonPreferredNetworkMode) {
                //NOTE onPreferenceChange seems to be called even if there is no change
                //Check if the button value is changed from the System.Setting
                mButtonPreferredNetworkMode.setValue((String) objValue);
                int buttonNetworkMode;
                buttonNetworkMode = Integer.parseInt((String) objValue);
                int settingsNetworkMode = android.provider.Settings.Global.getInt(
                        mPhone.getContext().getContentResolver(),
                        android.provider.Settings.Global.PREFERRED_NETWORK_MODE + phoneSubId,
                        preferredNetworkMode);
                if (buttonNetworkMode != settingsNetworkMode) {
                    int modemNetworkMode;
                    // if new mode is invalid ignore it
                    switch (buttonNetworkMode) {
                        case Phone.NT_MODE_WCDMA_PREF:
                        case Phone.NT_MODE_GSM_ONLY:
                        case Phone.NT_MODE_WCDMA_ONLY:
                        case Phone.NT_MODE_GSM_UMTS:
                        case Phone.NT_MODE_CDMA:
                        case Phone.NT_MODE_CDMA_NO_EVDO:
                        case Phone.NT_MODE_EVDO_NO_CDMA:
                        case Phone.NT_MODE_GLOBAL:
                        case Phone.NT_MODE_LTE_CDMA_AND_EVDO:
                        case Phone.NT_MODE_LTE_GSM_WCDMA:
                        case Phone.NT_MODE_LTE_CDMA_EVDO_GSM_WCDMA:
                        case Phone.NT_MODE_LTE_ONLY:
                        case Phone.NT_MODE_LTE_WCDMA:
                        case Phone.NT_MODE_TDSCDMA_ONLY:
                        case Phone.NT_MODE_TDSCDMA_WCDMA:
                        case Phone.NT_MODE_LTE_TDSCDMA:
                        case Phone.NT_MODE_TDSCDMA_GSM:
                        case Phone.NT_MODE_LTE_TDSCDMA_GSM:
                        case Phone.NT_MODE_TDSCDMA_GSM_WCDMA:
                        case Phone.NT_MODE_LTE_TDSCDMA_WCDMA:
                        case Phone.NT_MODE_LTE_TDSCDMA_GSM_WCDMA:
                        case Phone.NT_MODE_TDSCDMA_CDMA_EVDO_GSM_WCDMA:
                        case Phone.NT_MODE_LTE_TDSCDMA_CDMA_EVDO_GSM_WCDMA:
                            // This is one of the modes we recognize
                            modemNetworkMode = buttonNetworkMode;
                            break;
                        default:
                            return true;
                    }

                    android.provider.Settings.Global.putInt(
                            mPhone.getContext().getContentResolver(),
                            android.provider.Settings.Global.PREFERRED_NETWORK_MODE + phoneSubId,
                            buttonNetworkMode );
                    //Set the modem network mode
                    mPhone.setPreferredNetworkType(modemNetworkMode, mHandler
                            .obtainMessage(MyHandler.MESSAGE_SET_PREFERRED_NETWORK_TYPE));
                }
                return true;
            } else if(preference == mAutoSelect){
                boolean autoSelect = (Boolean) objValue;
                selectNetworkAutomatic(autoSelect);
                return true;
            }
            return true;
        }

        protected void getNetworkSelectionMode() {
            final int phoneSubId = mPhone.getSubId();
            int mPhoneId = SubscriptionManager.getPhoneId(phoneSubId);
            Message msg = mHandler.obtainMessage(MyHandler.EVENT_GET_NETWORK_SELECTION_MODE_DONE);
            Phone phone = PhoneFactory.getPhone(mPhoneId);
            if (phone != null) {
                phone.getNetworkSelectionMode(msg);
            }
        }

        private void selectNetworkAutomatic(boolean autoSelect) {
            final int phoneSubId = mPhone.getSubId();
            int mPhoneId = SubscriptionManager.getPhoneId(phoneSubId);
            if (autoSelect) {
                showAutoSelectProgressBar();
                mAutoSelect.setEnabled(false);
                Message msg = mHandler.obtainMessage(MyHandler.EVENT_AUTO_SELECT_DONE);
                Phone phone = PhoneFactory.getPhone(mPhoneId);
                if (phone != null) {
                    phone.setNetworkSelectionModeAutomatic(msg);
                }
            }
        }

        private void showAutoSelectProgressBar() {
            mProgressDialog.setMessage(
                    getContext().getResources().getString(R.string.register_automatically));
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.show();
        }

        private void dismissProgressBar() {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }

        private class MyHandler extends Handler {

            static final int MESSAGE_SET_PREFERRED_NETWORK_TYPE = 0;
            private static final int EVENT_AUTO_SELECT_DONE = 100;
            private static final int EVENT_GET_NETWORK_SELECTION_MODE_DONE = 200;

            @Override
            public void handleMessage(Message msg) {
                AsyncResult ar;
                switch (msg.what) {
                    case MESSAGE_SET_PREFERRED_NETWORK_TYPE:
                        handleSetPreferredNetworkTypeResponse(msg);
                        break;
                    case EVENT_AUTO_SELECT_DONE:
                        mAutoSelect.setEnabled(true);
                        dismissProgressBar();

                        ar = (AsyncResult) msg.obj;
                        if (ar.exception != null) {
                            displayNetworkSelectionFailed(ar.exception);
                        } else {
                            displayNetworkSelectionSucceeded();
                        }
                        break;
                    case EVENT_GET_NETWORK_SELECTION_MODE_DONE:
                        ar = (AsyncResult) msg.obj;
                        if (ar.exception != null) {
                        } else if (ar.result != null) {
                            try {
                                int[] modes = (int[]) ar.result;
                                boolean autoSelect = (modes[0] == 0);
                                if (mAutoSelect != null) {
                                    mAutoSelect.setChecked(autoSelect);
                                }
                            } catch (Exception e) {
                            }
                        }
                        break;
                }
            }

            private void handleSetPreferredNetworkTypeResponse(Message msg) {
                final Activity activity = getActivity();
                if (activity == null || activity.isDestroyed()) {
                    // Access preferences of activity only if it is not destroyed
                    // or if fragment is not attached to an activity.
                    return;
                }

                AsyncResult ar = (AsyncResult) msg.obj;
                final int phoneSubId = mPhone.getSubId();

                if (ar.exception == null) {
                    int networkMode;
                    if (mButtonPreferredNetworkMode != null)  {
                        networkMode =  Integer.parseInt(mButtonPreferredNetworkMode.getValue());
                        android.provider.Settings.Global.putInt(
                                mPhone.getContext().getContentResolver(),
                                android.provider.Settings.Global.PREFERRED_NETWORK_MODE
                                        + phoneSubId,
                                networkMode );
                    }

                } else {
                    updatePreferredNetworkUIFromDb();
                }
            }
        }

        protected void displayNetworkSelectionFailed(Throwable ex) {
            String status;
            final int phoneSubId = mPhone.getSubId();
            int mPhoneId = SubscriptionManager.getPhoneId(phoneSubId);
            if ((ex != null && ex instanceof CommandException)
                    && ((CommandException) ex).getCommandError()
                    == CommandException.Error.ILLEGAL_SIM_OR_ME) {
                status = getContext().getResources().getString(R.string.not_allowed);
            } else {
                status = getContext().getResources().getString(R.string.connect_later);
            }

            final PhoneGlobals app = PhoneGlobals.getInstance();
            app.notificationMgr.postTransientNotification(
                    NotificationMgr.NETWORK_SELECTION_NOTIFICATION, status);

            TelephonyManager tm = (TelephonyManager) app.getSystemService(Context.TELEPHONY_SERVICE);
            Phone phone = PhoneFactory.getPhone(mPhoneId);
            if (phone != null) {
                ServiceState ss = tm.getServiceStateForSubscriber(phone.getSubId());
                if (ss != null) {
                    app.notificationMgr.updateNetworkSelection(ss.getState(), phone.getSubId());
                }
            }
        }

        // Used by both mAutoSelect and mNetworkSelect buttons.
        protected void displayNetworkSelectionSucceeded() {
            String status = getContext().getResources().getString(R.string.registration_done);

            final PhoneGlobals app = PhoneGlobals.getInstance();
            app.notificationMgr.postTransientNotification(
                    NotificationMgr.NETWORK_SELECTION_NOTIFICATION, status);
        }

        private void updatePreferredNetworkUIFromDb() {
            final int phoneSubId = mPhone.getSubId();

            int settingsNetworkMode = android.provider.Settings.Global.getInt(
                    mPhone.getContext().getContentResolver(),
                    android.provider.Settings.Global.PREFERRED_NETWORK_MODE + phoneSubId,
                    preferredNetworkMode);

            // changes the mButtonPreferredNetworkMode accordingly to settingsNetworkMode
            mButtonPreferredNetworkMode.setValue(Integer.toString(settingsNetworkMode));
        }

    }

    public static Intent createIntent(Context context,int slotIndex) {
        Intent intent = new Intent();
        intent.setClass(context,ToroSimcardInfo.class);
        ToroSimcardInfo.slotIndex = slotIndex;
        return intent;
    }
}
