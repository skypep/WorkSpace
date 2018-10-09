package com.android.camera.module.prefs;

import android.app.CsAlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.camera.CameraSettings;
import com.android.camera.ListPreference;
import com.android.camera.PreferenceGroup;
import com.chus.camera.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by THINK on 2017/8/12.
 */

public class SettingsFragment2 extends PreferenceFragment {
    public final static String TAG = "SettingsFragment2";

    public final static String FRAGMENT_TAG_4PHOTO = "PREFERENCE_FRAGMENT_4PHOTO";
    public final static String FRAGMENT_TAG_4VIDEO = "PREFERENCE_FRAGMENT_4VIDEO";

    static String KEY_CATEGORY_SPECIFIC = "settings_category_specific";
    static String KEY_CATEGORY_GENERAL = "settings_category_general";
    static String KEY_CATEGORY_EMPTY = "settings_category_empty";
    static String KEY_CATEGORY_SEC = "settings_category_sec";

//    private Context mContext;
    private ArrayList<ListPreference> mListItem = new ArrayList<ListPreference>();
    private boolean[] mEnabled;
    private android.preference.PreferenceCategory androidPreferenceCate_specific = null;
    private android.preference.PreferenceCategory androidPreferenceCate_general = null;
    private android.preference.PreferenceCategory androidPreferenceCate_empty = null;
    private android.preference.PreferenceScreen androidPreferenceRoot = null;
    private android.preference.PreferenceCategory androidPreferenceCate_sec = null;
    private int[] ratioResIds = {R.drawable.chus_icon_ratio_full,R.drawable.chus_icon_ratio_other,R.drawable.chus_icon_ratio_square};
	// put all checkbox preference settings here !
	static class CheckInfo {
		public String vTrue;
		public String vFalse;
		public CheckInfo(String v_true, String v_false) {vTrue = v_true; vFalse = v_false;}
		String getBooleanString(boolean value) {if(value){return vTrue;} else {return vFalse;}}
		public boolean isStringBoolean(String v) {
			if(v != null) {
				if(vTrue != null && vTrue.equals(v)) {
					return true;
				}
				else if(vFalse != null && vFalse.equals(v)) {
					return false;
				}
			}
			return false;
		}
	}
    private static HashMap<String, CheckInfo> sPreferenceTypeMap = new HashMap<String, CheckInfo>();
    static {
        sPreferenceTypeMap.put(CameraSettings.KEY_RECORD_LOCATION, 	new CheckInfo("on", "off"));
        sPreferenceTypeMap.put(CameraSettings.KEY_AUX_GRID, 			new CheckInfo("on", "off"));
        sPreferenceTypeMap.put(CameraSettings.KEY_CHUS_FACE_BEAUTY, 	new CheckInfo("enable", "disable"));
        sPreferenceTypeMap.put(CameraSettings.KEY_LONGSHOT, 			new CheckInfo("on", "off"));
        sPreferenceTypeMap.put(CameraSettings.KEY_FACE_DETECTION, 	new CheckInfo("on", "off"));
        sPreferenceTypeMap.put(CameraSettings.KEY_QC_CHROMA_FLASH, 	new CheckInfo("chroma-flash-on", "chroma-flash-off"));
        sPreferenceTypeMap.put(CameraSettings.KEY_SHUTTER_SOUND, 		new CheckInfo("enable", "disable"));
        sPreferenceTypeMap.put(CameraSettings.KEY_DIS, 		new CheckInfo("enable", "disable"));
    }
    public static boolean isSwitchPreference(String key) {
        Object v = sPreferenceTypeMap.get(key);
        if(v != null) {
            return true;
        }
        return false;
    }
	public static boolean isSwitchPreferenceStringBoolean(String key, String value) {
        Object v = sPreferenceTypeMap.get(key);
        if(v != null) {
            CheckInfo ci = (CheckInfo)v;
			return ci.isStringBoolean(value);
        }
		return false;
	}
	public static String getSwitchPreferenceBooleanString(String key, boolean value) {
        Object v = sPreferenceTypeMap.get(key);
        if(v != null) {
            CheckInfo ci = (CheckInfo)v;
			return ci.getBooleanString(value);
        }
		return null;
	}
		
    static public interface Listener {
        public void onSettingChanged(ListPreference pref); // x
        //public void onPreferenceClicked(ListPreference pref);
        public void onPreferenceClicked(ListPreference pref, int y);    // x
        public void onListMenuTouched();    // x

		public void onListPrefChanged(ListPreference pref); // frankie, add 
    }
    private Listener mListener;
    public void setSettingChangedListener(Listener listener) {
        mListener = listener;
    }

    private String[] mDefaultKeys = new String[] {
            CameraSettings.KEY_SELFIE_FLASH,
            CameraSettings.KEY_FLASH_MODE,
            CameraSettings.KEY_RECORD_LOCATION,
            CameraSettings.KEY_PICTURE_SIZE,
            CameraSettings.KEY_PICTURE_RATIO,
//                    CameraSettings.KEY_JPEG_QUALITY,
            CameraSettings.KEY_TIMER,
//                    CameraSettings.KEY_LONGSHOT,
            CameraSettings.KEY_FACE_DETECTION,
            CameraSettings.KEY_AUX_GRID,
            CameraSettings.KEY_SHUTTER_SOUND,
            CameraSettings.KEY_LONGSHOT
    };

    private String[] mSpecificCategoryKeys = new String[] {
            CameraSettings.KEY_PICTURE_RATIO,
            CameraSettings.KEY_PICTURE_SIZE,
//            CameraSettings.KEY_RECORD_LOCATION,
//            CameraSettings.KEY_AUX_GRID,
            CameraSettings.KEY_JPEG_QUALITY,
            CameraSettings.KEY_VIDEO_QUALITY
    };
    private boolean isSpecificKey(String key) {
        for(int i=0;i<mSpecificCategoryKeys.length;i++) {
            if(mSpecificCategoryKeys[i].equals(key)) {
                return true;
            }
        }
        return false;
    }

    private void updateAndroidPreferenceSummary(android.preference.ListPreference listPreference) {
        String value_ = listPreference.getValue();
        int index = listPreference.findIndexOfValue(value_);
        CharSequence[] entries = listPreference.getEntries();
        if(index >= 0 && index < entries.length) {
            listPreference.setSummary(entries[index]);
        }
    }
    private android.preference.ListPreference selListPre = null;
    private List<CheckBoxPreference> listCheckBoxPre = new ArrayList<CheckBoxPreference>();
    private String newString = null;
    private void initSecCatePre(android.preference.ListPreference listPreference){
        androidPreferenceRoot.removePreference(androidPreferenceCate_specific);
        androidPreferenceRoot.removePreference(androidPreferenceCate_general);
        androidPreferenceRoot.removePreference(androidPreferenceCate_empty);
//        androidPreferenceCate_specific.removeAll();
//        androidPreferenceCate_general.removeAll();
        androidPreferenceCate_sec.removeAll();
        listCheckBoxPre.clear();
        selListPre = listPreference;
       CharSequence[] entryValues =  listPreference.getEntryValues();
        String value = listPreference.getValue();
        if(mLifeCycleCallback != null) {
            mLifeCycleCallback.setTitle(listPreference.getTitle().toString());
        }
        for(int i=0;i<entryValues.length;i++){
            android.preference.CheckBoxPreference swPref = new android.preference.CheckBoxPreference(getActivity());//mContext
            swPref.setKey(listPreference.getKey());
            swPref.setTitle(listPreference.getEntries()[i]);
            swPref.setLayoutResource(R.layout.pref_layout_widget_frame);
            swPref.setWidgetLayoutResource(R.layout.pref_checkbox);
            swPref.setPersistent(false);
            if(value.equals(entryValues[i])){
                swPref.setChecked(true);
            }else {
                swPref.setChecked(false);
            }
//            swPref.setChecked(isSwitchPreferenceStringBoolean(pref.getKey(), pref.getValue()));
            swPref.setOnPreferenceClickListener(checkPreferenceClickListener);
//            androidPref = swPref;
            androidPreferenceCate_sec.addPreference(swPref);
            listCheckBoxPre.add(swPref);
        }
        if(listPreference.getKey().equalsIgnoreCase(CameraSettings.KEY_PICTURE_RATIO)){
            for(int i=0;i<listCheckBoxPre.size();i++){
                listCheckBoxPre.get(i).setIcon(ratioResIds[i]);
            }
        }
        androidPreferenceRoot.addPreference(androidPreferenceCate_sec);
    }
    private android.preference.Preference.OnPreferenceClickListener listPreferenceClickListener = new android.preference.Preference.OnPreferenceClickListener(){
        @Override
        public boolean onPreferenceClick(Preference preference) {
//            ToastUtils.showToast(mContext,"listPreferenceClickListener()");
            if(preference instanceof android.preference.ListPreference) {
                ((android.preference.ListPreference) preference).getDialog().hide();
                initSecCatePre((android.preference.ListPreference)preference);
            }else if(preference.getKey().equalsIgnoreCase(CameraSettings.KEY_RESET_DEFAULT_SETTINGS)){
                showResetDefaultDialog(preference);
                return true;
            }
            return true;
        }
    };
    private android.preference.Preference.OnPreferenceClickListener checkPreferenceClickListener = new android.preference.Preference.OnPreferenceClickListener(){
        @Override
        public boolean onPreferenceClick(Preference preference) {
            int count = listCheckBoxPre.size();
            if(count <= 0){
                return true;
            }
            for(int i=0;i<count;i++){
                if(listCheckBoxPre.get(i) == preference){
                    listCheckBoxPre.get(i).setChecked(true);
                    selListPre.setValueIndex(i);
//                     showFirDir();
                    androidPreferenceRoot.addPreference(androidPreferenceCate_specific);
                    androidPreferenceRoot.addPreference(androidPreferenceCate_general);
                    androidPreferenceRoot.addPreference(androidPreferenceCate_empty);
                    androidPreferenceRoot.removePreference(androidPreferenceCate_sec);
                    androidPreferenceCate_sec.removeAll();
                    aPreferenceChangedListener.onPreferenceChange(selListPre,selListPre.getValue());
                    if(mLifeCycleCallback != null) {
                        mLifeCycleCallback.setTitle(getActivity().getString(R.string.chus_settings_fragment_title_bar_title));
                    }
//                    String newString = String.valueOf(selListPre.getEntryValues()[i]);
//                    selListPre.setValue(newString);
                }else {
                    listCheckBoxPre.get(i).setChecked(false);
                }
            }

            return true;
        }
    };
    private android.preference.ListPreference.OnPreferenceChangeListener aPreferenceChangedListener =
            new android.preference.ListPreference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if(preference instanceof android.preference.ListPreference) {
                android.preference.ListPreference listPref = (android.preference.ListPreference)preference;
                String newSetting = (String)newValue;
                Log.v(TAG, "ListPreference.onPreferenceChange," + listPref.getKey() + "=" + newSetting);
                listPref.setValue(newSetting);

                updateAndroidPreferenceSummary(listPref);

                ListPreference targetPref = findItemByKey(listPref.getKey());

                if(newSetting != null) {
                    targetPref.setValue(newSetting);
                    if (mListener != null) {
                        mListener.onListPrefChanged(targetPref);
                    }
                }

                if(listPref.getKey().equals(CameraSettings.KEY_PICTURE_RATIO)) {
                    ListPreference ratioPref = findItemByKey(CameraSettings.KEY_PICTURE_RATIO);

                    ListPreference pref = findItemByKey(CameraSettings.KEY_PICTURE_SIZE);
                    android.preference.ListPreference aPref = (android.preference.ListPreference)
                            androidPreferenceRoot.findPreference(CameraSettings.KEY_PICTURE_SIZE);

                    CharSequence[] entryValues =CameraSettings.getPictureSizeEntryValuesConfinedByRatio(
                            pref.getEntryValues(), ratioPref.getValue());
                    CharSequence[] entries = CameraSettings.getPictureSizeEntriesConfinedByRatio(
                            pref.getEntries(), pref.getEntryValues(), ratioPref.getValue());
                    aPref.setEntries(entries);
                    aPref.setEntryValues(entryValues);
                    aPref.setValue(entryValues.length > 0 ? entryValues[0].toString() : "");
                    //aPref.setTitle(pref.getTitle());
                    //aPref.setDialogTitle(pref.getTitle());

//                    int index_ = aPref.findIndexOfValue(aPref.getValue()); // -1
//                    Log.v(TAG, "  ---------- getValue() = " + aPref.getValue() + " index_:" + index_);
//                    Log.v(TAG, "  ---------- entryValues.length = " + entryValues.length);
//                    for(CharSequence ev : entryValues) {
//                        Log.v(TAG, "  " + ev);
//                    }
//                    Log.v(TAG, "  ---------- entries.length = " + entries.length);
//                    for(CharSequence e : entries) {
//                        Log.v(TAG, "  " + e);
//                    }
                    updateAndroidPreferenceSummary(aPref);

                    pref.setValue(entryValues[0].toString());   //
                    if (mListener != null) {
                        mListener.onListPrefChanged(pref);
                    }
                }
            }
            return true;
        }
    };
    private android.preference.CheckBoxPreference.OnPreferenceChangeListener swPreferenceChangedListener =
            new android.preference.CheckBoxPreference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if(preference instanceof android.preference.CheckBoxPreference) {
                        android.preference.CheckBoxPreference swPreference = (android.preference.CheckBoxPreference)preference;
                        Boolean newSetting = (Boolean)newValue;
                        swPreference.setChecked(newSetting);
						String setting_value = getSwitchPreferenceBooleanString(swPreference.getKey(), newSetting);
                        Log.v(TAG, "CheckBoxPreference.onPreferenceChange," + swPreference.getKey() + "="  + newSetting
							+ " (" + setting_value + ")");
                        if(setting_value != null) {
                            for (int i = 0; i < mListItem.size(); i++) {
                                ListPreference pref = mListItem.get(i);
                                if (pref.getKey().equals(swPreference.getKey())) {
                                    pref.setValue(setting_value);
                                    if (mListener != null) {
                                        mListener.onListPrefChanged(pref);
                                    }
                                }
                            }
                        }
                    }
                    return true;
                }
            };

    private void showResetDefaultDialog(final Preference preference){
        new CsAlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setTitle(R.string.pref_reset_default_title)
                .setMessage(R.string.pref_reset_default_content)
//                .setNeutralButton(R.string.dialog_ok, buttonListener)
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListPreference targetPref = findItemByKey(preference.getKey());
                        if (mListener != null) {
                            mListener.onListPrefChanged(targetPref);
                        }
                        resetDefault();//add zj for fix 5004
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.pref_reset_default_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    /*    new CommomDialog(getActivity(),R.style.dialog,getString(R.string.pref_reset_default_content),new CommomDialog.OnCloseListener(){
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                  if(confirm){
                      ListPreference targetPref = findItemByKey(preference.getKey());
                      if (mListener != null) {
                          mListener.onListPrefChanged(targetPref);
                      }
                      dialog.dismiss();
                  }
            }
        }).show();*/
    }

    private void resetDefault(){
        for (String pref_key: mDefaultKeys) {
            //TODO
            ListPreference preference = findItemByKey(pref_key);
        }

        for(int i=0;i<mListItem.size();i++) {
            ListPreference pref = mListItem.get(i);
            if(pref.getKey().equals(CameraSettings.KEY_PICTURE_SIZE)) {
//                android.preference.ListPreference aPref = new android.preference.ListPreference(getActivity());
                android.preference.ListPreference aPref = (android.preference.ListPreference)androidPreferenceRoot.findPreference(pref.getKey());
                ListPreference ratioPref = findItemByKey(CameraSettings.KEY_PICTURE_RATIO);

                CharSequence[] entryValues =CameraSettings.getPictureSizeEntryValuesConfinedByRatio(
                        pref.getEntryValues(), ratioPref.getValue());
                CharSequence[] entries = CameraSettings.getPictureSizeEntriesConfinedByRatio(
                        pref.getEntries(), pref.getEntryValues(), ratioPref.getValue());
                String aValue = getPreferenceDefaultValue(entryValues, pref.getValue());
                aPref.setKey(pref.getKey());
                aPref.setEntries(entries);
                aPref.setEntryValues(entryValues);
                aPref.setValue(aValue);
                updateAndroidPreferenceSummary(aPref);
                Log.v("aaa","++++resetDefault:picsize:"+pref.getKey()+",default:"+aValue);
            } else if(isSwitchPreference(pref.getKey())){
//                android.preference.CheckBoxPreference swPref = new android.preference.CheckBoxPreference(getActivity());
                android.preference.CheckBoxPreference swPref = (CheckBoxPreference)androidPreferenceRoot.findPreference(pref.getKey());
                swPref.setKey(pref.getKey());
                swPref.setChecked(isSwitchPreferenceStringBoolean(pref.getKey(), pref.getValue()));
                Log.v("aaa","++++resetDefault:checkbox:"+pref.getKey()+",default:"+getPreferenceDefaultValue(pref.getEntryValues(), pref.getValue()));
            }else if(!pref.getKey().equalsIgnoreCase(CameraSettings.KEY_RESET_DEFAULT_SETTINGS)){
//                android.preference.ListPreference aPref = new android.preference.ListPreference(getActivity());
                android.preference.ListPreference aPref = (android.preference.ListPreference)androidPreferenceRoot.findPreference(pref.getKey());
                aPref.setKey(pref.getKey());
                aPref.setValue(getPreferenceDefaultValue(pref.getEntryValues(), pref.getValue()));
                updateAndroidPreferenceSummary(aPref);
                Log.v("aaa","++++resetDefault:list:"+pref.getKey()+",default:"+getPreferenceDefaultValue(pref.getEntryValues(), pref.getValue()));
            }
        }
    }

    private ListPreference findItemByKey(String key) {
        for(int i=0;i<mListItem.size();i++) {
            if(key.equals(mListItem.get(i).getKey())) {
                return mListItem.get(i);
            }
        }
        return null;
    }
    private String getPreferenceDefaultValue(CharSequence[] entryValues, String old_value) {
        String aValue = entryValues[0].toString();
        for(CharSequence c_ : entryValues) {
            String new_c_ = c_.toString();
            if(old_value.equals(new_c_)) {
                aValue = new_c_;
            }
        }
        return aValue;
    }
    public void initialize(PreferenceGroup group, String[] keys,boolean isFrontCamera) {
        Log.v(TAG, "+ initialize");
        this.getPreferenceManager().getSharedPreferences().edit().clear().commit();

        // Prepare the setting items.
        mListItem.clear(); // frankie, add
        for (int i = 0; i < keys.length; ++i) {
            ListPreference pref = group.findPreference(keys[i]);
            if (pref != null) {
                Log.v(TAG, "" + pref.getKey());
                mListItem.add(pref);
            }
        }
        Log.v(TAG, "total " + mListItem.size() + " item added");

//        ArrayAdapter<ListPreference> mListItemAdapter = new MoreSettingAdapter();
//        setAdapter(mListItemAdapter);
//        setOnItemClickListener(this);
//        setSelector(android.R.color.transparent);
        // Initialize mEnabled
        mEnabled = new boolean[mListItem.size()];	// create new list 
        for (int i = 0; i < mEnabled.length; i++) {
            mEnabled[i] = true;
        }

        // frankie, add
        androidPreferenceCate_specific.removeAll();
        androidPreferenceCate_general.removeAll();
        androidPreferenceCate_empty.removeAll();
        androidPreferenceCate_sec.removeAll();
        androidPreferenceRoot.addPreference(androidPreferenceCate_specific);
        androidPreferenceRoot.addPreference(androidPreferenceCate_general);
        androidPreferenceRoot.addPreference(androidPreferenceCate_empty);
        if(isFrontCamera){
            androidPreferenceCate_specific.setTitle(R.string.pref_catogary_specific_title_front);
        }else {
            androidPreferenceCate_specific.setTitle(R.string.pref_catogary_specific_title);
        }
        for(int i=0;i<mListItem.size();i++) {
            android.preference.Preference androidPref = null;
            ListPreference pref = mListItem.get(i);

            if(pref.getKey().equals(CameraSettings.KEY_PICTURE_SIZE)) {
                android.preference.ListPreference aPref = new android.preference.ListPreference(getActivity());

                ListPreference ratioPref = findItemByKey(CameraSettings.KEY_PICTURE_RATIO);

                CharSequence[] entryValues =CameraSettings.getPictureSizeEntryValuesConfinedByRatio(
                        pref.getEntryValues(), ratioPref.getValue());
                CharSequence[] entries = CameraSettings.getPictureSizeEntriesConfinedByRatio(
                        pref.getEntries(), pref.getEntryValues(), ratioPref.getValue());

                String aValue = getPreferenceDefaultValue(entryValues, pref.getValue());
                aPref.setKey(pref.getKey());
                aPref.setLayoutResource(R.layout.pref_layout_list);
                aPref.setEntries(entries);
                aPref.setEntryValues(entryValues);
                aPref.setValue(aValue);
                aPref.setTitle(pref.getTitle());
                aPref.setDialogTitle(pref.getTitle());
                updateAndroidPreferenceSummary(aPref);
                aPref.setOnPreferenceChangeListener(aPreferenceChangedListener);
                aPref.setOnPreferenceClickListener(listPreferenceClickListener);
                androidPref = aPref;
            }else if(pref.getKey().equals(CameraSettings.KEY_RESET_DEFAULT_SETTINGS)){
                android.preference.Preference preference = new android.preference.Preference(getActivity());
                preference.setKey(pref.getKey());
                preference.setTitle(pref.getTitle());
                preference.setLayoutResource(R.layout.pref_layout_common);
                preference.setOnPreferenceClickListener(listPreferenceClickListener);
                androidPref = preference;
            }
            else if(!isSwitchPreference(pref.getKey())) {
                android.preference.ListPreference aPref = new android.preference.ListPreference(getActivity());
                aPref.setKey(pref.getKey());
                aPref.setLayoutResource(R.layout.pref_layout_list);
                aPref.setEntries(pref.getEntries());
                aPref.setEntryValues(pref.getEntryValues());
                aPref.setValue(getPreferenceDefaultValue(pref.getEntryValues(), pref.getValue()));
                aPref.setTitle(pref.getTitle());
                aPref.setDialogTitle(pref.getTitle());
                updateAndroidPreferenceSummary(aPref);
                aPref.setOnPreferenceChangeListener(aPreferenceChangedListener);
                aPref.setOnPreferenceClickListener(listPreferenceClickListener);
                androidPref = aPref;
            } else {
                android.preference.CheckBoxPreference swPref = new android.preference.CheckBoxPreference(getActivity());
                swPref.setKey(pref.getKey());
                swPref.setTitle(pref.getTitle());
                swPref.setLayoutResource(R.layout.pref_layout_widget_frame);
                swPref.setWidgetLayoutResource(R.layout.pref_widget_frame_switch);
                swPref.setChecked(isSwitchPreferenceStringBoolean(pref.getKey(), pref.getValue()));
                swPref.setOnPreferenceChangeListener(swPreferenceChangedListener);
               /* if(swPref.getKey().equalsIgnoreCase(CameraSettings.KEY_DIS)){
                    swPref.setSummary(getActivity().getString(R.string.chus_video_dis_hint));
                }*/
                androidPref = swPref;
            }
            // print debug
            if(pref.getKey().equals(CameraSettings.KEY_PICTURE_RATIO)) {
                android.preference.ListPreference aPref = (android.preference.ListPreference)androidPref;
                CharSequence[] entries = aPref.getEntries();
                Log.v("zhang", "entries.length : " + entries.length);
                for(CharSequence e : entries) {
                    Log.v("zhang", "  " + e);
                }
                CharSequence[] entryValues = aPref.getEntryValues();
                Log.v("zhang", "entryValues.length : " + entryValues.length);
                for(CharSequence e : entryValues) {
                    Log.v("zhang", "  " + e);
                }
                Log.v("zhang", "aPref.getValue : " + aPref.getValue());
            }
            if(mEnabled[i] == false) {
                androidPref.setEnabled(false);
            }
            else {
                androidPref.setEnabled(true);
            }

            if(androidPreferenceRoot.findPreference(androidPref.getKey()) != null) { // rm dup
                androidPreferenceRoot.removePreference(androidPreferenceRoot.findPreference(androidPref.getKey()));
            }

            if(isSpecificKey(pref.getKey())) {
                androidPreferenceCate_specific.addPreference(androidPref);
            }
            else {
                androidPreferenceCate_general.addPreference(androidPref);
            }
        }

//        android.preference.PreferenceCategory androidEmpty1 = new android.preference.PreferenceCategory(mContext);
//        androidEmpty1.setEnabled(false);
//        androidPreferenceCate_empty.addPreference(androidEmpty1);
//        android.preference.PreferenceCategory androidEmpty2 = new android.preference.PreferenceCategory(mContext);
//        androidEmpty2.setEnabled(false);
//        androidPreferenceCate_empty.addPreference(androidEmpty2);

        Log.v(TAG, "- initialize");
    }
	private void refreshPreferenceEnabledState() { // frankie, add to refresh the preference enable state
        int count = mEnabled == null ? 0 : mEnabled.length;
        for (int j = 0; j < count; j++) {
			ListPreference pref = mListItem.get(j);
            android.preference.Preference androidPref = androidPreferenceRoot.findPreference(pref.getKey());
            if(androidPref != null){
                androidPref.setEnabled(mEnabled[j]);
            }
		}

	}
    // When preferences are disabled, we will display them grayed out. Users
    // will not be able to change the disabled preferences, but they can still
    // see
    // the current value of the preferences
    public void setPreferenceEnabled(String key, boolean enable) {
        Log.v(TAG, "setPreferenceEnabled, " + key + "=" + enable);
        int count = mEnabled == null ? 0 : mEnabled.length;
        for (int j = 0; j < count; j++) {
            ListPreference pref = mListItem.get(j);
            if (pref != null && key.equals(pref.getKey())) {
                mEnabled[j] = enable;
                break;
            }
        }
        refreshPreferenceEnabledState();

    }
    // Scene mode can override other camera settings (ex: flash mode).
    public void overrideSettings(final String... keyvalues) {
        Log.v(TAG, "+ overrideSettings");
		for (int j = 0; j < keyvalues.length; j += 2) {
			String key = keyvalues[j];
            String value = keyvalues[j + 1];
			//Log.v(TAG, "  " + key + "=" + value);
		}
        int count = mEnabled == null ? 0 : mEnabled.length;
        for (int i = 0; i < keyvalues.length; i += 2) {
            String key = keyvalues[i];
            String value = keyvalues[i + 1];
            Log.v(TAG, "  " + key + ":" + value);
            for (int j = 0; j < count; j++) {
                ListPreference pref = mListItem.get(j);
                if (pref != null && key.equals(pref.getKey())) {
                    // Change preference
                    if (value != null)
                        pref.setValue(value);
                    // If the preference is overridden, disable the preference
                    boolean enable = value == null;
                    mEnabled[j] = enable;

                    android.preference.Preference aPref = androidPreferenceRoot.findPreference(pref.getKey());
                    if(aPref != null){
                        Log.v(TAG, "  " + pref.getKey() + " enable:" + enable);
                        aPref.setEnabled(enable);
                        if(aPref.getKey().equalsIgnoreCase(CameraSettings.KEY_DIS)){
                            if(!enable){
                                aPref.setSummary(getString(R.string.chus_video_dis_hint));
                            }else{
                                aPref.setSummary("");
                            }
                        }
                    }

                    // frankie, to find the target item , then disable it !!!

//                    int offset = getFirstVisiblePosition();
//                    if (offset >= 0) {
//                        int indexInView = j - offset;
//                        if (getChildCount() > indexInView && indexInView >= 0) {
//                            getChildAt(indexInView).setEnabled(enable);
//                        }
//                    }
                }
            }
        }
        reloadPreference();
        Log.v(TAG, "- overrideSettings done");
    }
    public void reloadPreference() {
        Log.v(TAG, "reloadPreference");
//        int count = getChildCount();
//        for (int i = 0; i < count; i++) {
//            ListPreference pref = mListItem.get(i);
//            if (pref != null) {
//                ListMenuItem listMenuItem = (ListMenuItem) getChildAt(i);
//                listMenuItem.reloadPreference();
//            }
//        }

    }
    public void resetHighlight() {
//        int count = getChildCount();
//        for (int i = 0; i < count; i++) {
//            View v = getChildAt(i);
//            v.setBackground(null);
//        }
//        mHighlighted = -1;
    }

    public interface SettingsFragment2LifeCycleCallback {
        public void onCreate(PreferenceFragment preferenceFragment);
        public void onDestroy();
        public void setTitle(String titleTxt);
    }
    private SettingsFragment2LifeCycleCallback mLifeCycleCallback = null;

    public SettingsFragment2() {

    }
    private static final String ARG_PARAM2 = "param2";
//    public static SettingsFragment2 newInstance(SettingsFragment2LifeCycleCallback callback) {
//        SettingsFragment2 fragment = new SettingsFragment2();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM2, callback);
//        fragment.setArguments(args);
//        return fragment;
//    }

    public void setLifeCycleCallback(SettingsFragment2LifeCycleCallback callback){
        mLifeCycleCallback = callback;
    }

//    public SettingsFragment2(Context context, SettingsFragment2LifeCycleCallback callback) {
//        mContext = context;
//        mLifeCycleCallback = callback;
//    }
    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach");
        super.onAttach(context);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        this.getPreferenceManager().setSharedPreferencesName("settings_fragment2_pref");    // ensure the key-value is saved in another xml file !!!
        
        addPreferencesFromResource(R.xml.settings_fragment2_preferences);

        androidPreferenceRoot = this.getPreferenceScreen();
        androidPreferenceCate_specific = (android.preference.PreferenceCategory) androidPreferenceRoot.findPreference(KEY_CATEGORY_SPECIFIC);
        androidPreferenceCate_general = (android.preference.PreferenceCategory) androidPreferenceRoot.findPreference(KEY_CATEGORY_GENERAL);
        androidPreferenceCate_empty = (android.preference.PreferenceCategory) androidPreferenceRoot.findPreference(KEY_CATEGORY_EMPTY);
        androidPreferenceCate_sec = (android.preference.PreferenceCategory) androidPreferenceRoot.findPreference(KEY_CATEGORY_SEC);

        if(mLifeCycleCallback != null) {
            mLifeCycleCallback.onCreate(this);
        }
    }
    public void initialize(PreferenceGroup group) {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState); // super empty impl

    }
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        ListView listView = (ListView) getView().findViewById(android.R.id.list);
        if(listView != null){
            listView.setDividerHeight(0);
//            listView.setSelector(R.color.transprant);
        }
    }
    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }
    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        super.onDestroyView();
    }
    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        if(mLifeCycleCallback != null) {
            mLifeCycleCallback.onDestroy();
        }
    }
    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
        super.onDetach();
    }
    public boolean isSecDir(){
        if(androidPreferenceCate_sec != null && androidPreferenceCate_sec.getPreferenceCount() == 0){
            return false;
        }
        return true;
    }
    public void showFirDir(){
            androidPreferenceRoot.addPreference(androidPreferenceCate_specific);
            androidPreferenceRoot.addPreference(androidPreferenceCate_general);
            androidPreferenceRoot.addPreference(androidPreferenceCate_empty);
            androidPreferenceRoot.removePreference(androidPreferenceCate_sec);
            androidPreferenceCate_sec.removeAll();
            aPreferenceChangedListener.onPreferenceChange(selListPre,selListPre.getValue());
           if(mLifeCycleCallback != null) {
            mLifeCycleCallback.setTitle(getActivity().getString(R.string.chus_settings_fragment_title_bar_title));
          }
    }
}


