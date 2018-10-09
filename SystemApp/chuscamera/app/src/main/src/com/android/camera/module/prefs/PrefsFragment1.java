package com.android.camera.module.prefs;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.android.camera.CameraActivity;
import com.android.camera.CameraModule;
import com.android.camera.CameraSettings;
import com.android.camera.ComboPreferences;
import com.android.camera.PreferenceGroup;
import com.android.camera.module.PhotoModule;
import com.chus.camera.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by THINK on 2017/8/10.
 */

public class PrefsFragment1 extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    public static final String STRING_PICTRUE_SIZE_VALUES = "pictrueSizeValues";
    public static final String STRING_PICTRUE_SIZE = "pictrueSize";
    private CharSequence[] pictrueValues = null;
    private CharSequence[] pictrues = null;

    private CharSequence[] PICTURE_SIZE_4_3_V = null;
    private CharSequence[] PICTURE_SIZE_16_9_V = null;
    private CharSequence[] PICTURE_SIZE_1_1_V = null;
    private CharSequence[] PICTURE_SIZE_4_3_E = null;
    private CharSequence[] PICTURE_SIZE_16_9_E = null;
    private CharSequence[] PICTURE_SIZE_1_1_E = null;
    private CharSequence[] PICTURE_RATIO_E = null;
    private CharSequence[] PICTURE_RATIO_V = null;

    private PreferenceCategory gneCatPreference;
    private PreferenceCategory swiCatPreference;
    private ListPreference listPreference;
    private ListPreference ratioListPreference;
    private CheckBoxPreference switchPreference;
    private SharedPreferences sp;
    private ComboPreferences mPreferences;
    private PreferenceGroup mPreGr;
    private Activity a;
    private ArrayList<com.android.camera.ListPreference> mListItem = new ArrayList<com.android.camera.ListPreference>();
    private String[] mOtherKeys1 = new String[]{
            CameraSettings.KEY_PICTURE_RATIO,
            CameraSettings.KEY_SELFIE_FLASH,
            //CameraSettings.KEY_FLASH_MODE,
            CameraSettings.KEY_RECORD_LOCATION,
            CameraSettings.KEY_AUX_GRID,            // frankie, add
            CameraSettings.KEY_CHUS_FACE_BEAUTY,    // frankie, add
            //CameraSettings.KEY_CHUS_TOUCH_TO_TAKE_PHOTO,// frankie, add
            CameraSettings.KEY_PICTURE_SIZE,
            CameraSettings.KEY_JPEG_QUALITY,
            //CameraSettings.KEY_TIMER,
            //CameraSettings.KEY_CAMERA_SAVEPATH,	// frankie, 2017.07.28, rm
            CameraSettings.KEY_LONGSHOT,
            CameraSettings.KEY_FACE_DETECTION,
            CameraSettings.KEY_ISO,
            CameraSettings.KEY_EXPOSURE,
            CameraSettings.KEY_WHITE_BALANCE,
            CameraSettings.KEY_QC_CHROMA_FLASH,
            CameraSettings.KEY_REDEYE_REDUCTION,
            //CameraSettings.KEY_SELFIE_MIRROR,
            CameraSettings.KEY_SHUTTER_SOUND
    };
    private String[] listKeys = {
            CameraSettings.KEY_PICTURE_RATIO,
            CameraSettings.KEY_PICTURE_SIZE,
            CameraSettings.KEY_JPEG_QUALITY,
            CameraSettings.KEY_ISO,
            CameraSettings.KEY_EXPOSURE,
            CameraSettings.KEY_WHITE_BALANCE,};
    private String[] switchKeys = {
            CameraSettings.KEY_SELFIE_FLASH,
            CameraSettings.KEY_RECORD_LOCATION,
            CameraSettings.KEY_AUX_GRID,
            CameraSettings.KEY_CHUS_FACE_BEAUTY,
            CameraSettings.KEY_LONGSHOT,
            CameraSettings.KEY_FACE_DETECTION,
            CameraSettings.KEY_QC_CHROMA_FLASH,
            CameraSettings.KEY_REDEYE_REDUCTION,
            CameraSettings.KEY_SHUTTER_SOUND};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        swiCatPreference = (PreferenceCategory) findPreference("switch_category");
        gneCatPreference = (PreferenceCategory) findPreference("display_category");
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());

        getPreferenceManager().setSharedPreferencesName("mysetting");
    }

    @Override
    public void onViewCreated(View view, Bundle saveState) {
        super.onViewCreated(view, saveState);
        //
        a = this.getActivity();
        mPreferences = ComboPreferences.get(a);
        if (mPreferences == null) {
            mPreferences = new ComboPreferences(a);
        }
        if (a instanceof CameraActivity) {
            CameraActivity ca = (CameraActivity) a;
            CameraModule cm = ca.getCurrentModule();
            if (cm instanceof PhotoModule) {
                PhotoModule pm = (PhotoModule) cm;
                mPreGr = pm.getPreferenceGroup();
                if (mPreGr != null) {
                    com.android.camera.ListPreference l = mPreGr.findPreference(CameraSettings.KEY_PICTURE_SIZE);
                    pictrues = l.getEntries();
                    pictrueValues = l.getEntryValues();
                    classifyPictruesSize(Arrays.asList(pictrueValues), Arrays.asList(pictrues));
                    initialize(mPreGr, mOtherKeys1);
//                    init();
                    reflshPictrueSize();

                }
            }
        }
    }


    private boolean getCameraListPreferenceBoolean(com.android.camera.ListPreference pref) {
        String value = pref.getValue(); // off on 关 开
        CharSequence[] entriesV = (CharSequence[]) pref.getEntryValues();
        if (pref.getKey().equals(CameraSettings.KEY_RECORD_LOCATION)) {
            if (value.equals(entriesV[0])) {
                return false;
            } else {
                return true;
            }
        } else if (pref.getKey().equals(CameraSettings.KEY_SELFIE_FLASH)) {//off on 关闭 打开
            if (value.equals(entriesV[0])) {
                return false;
            } else {
                return true;
            }
        } else if (pref.getKey().equals(CameraSettings.KEY_AUX_GRID)) {//off on 关闭 打开
            if (value.equals(entriesV[0])) {
                return false;
            } else {
                return true;
            }
        } else if (pref.getKey().equals(CameraSettings.KEY_CHUS_FACE_BEAUTY)) {//disable enable 禁用 使能
            if (value.equals(entriesV[0])) {
                return false;
            } else {
                return true;
            }
        } else if (pref.getKey().equals(CameraSettings.KEY_LONGSHOT)) {//off on 关 开
            if (value.equals(entriesV[0])) {
                return false;
            } else {
                return true;
            }
        } else if (pref.getKey().equals(CameraSettings.KEY_FACE_DETECTION)) {//off on 关 开
            if (value.equals(entriesV[0])) {
                return false;
            } else {
                return true;
            }
        } else if (pref.getKey().equals(CameraSettings.KEY_QC_CHROMA_FLASH)) {//chrome-flash-off chrome-flash-on 关 开
            if (value.equals(entriesV[0])) {
                return false;
            } else {
                return true;
            }
        } else if (pref.getKey().equals(CameraSettings.KEY_REDEYE_REDUCTION)) {//disable enable 关 开
            if (value.equals(entriesV[0])) {
                return false;
            } else {
                return true;
            }
        } else if (pref.getKey().equals(CameraSettings.KEY_SHUTTER_SOUND)) {//disable enable 关 开
            if (value.equals(entriesV[0])) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public void initialize(PreferenceGroup group, String[] keys) {
        // Prepare the setting items.
        mListItem.clear(); // frankie, add
        for (int i = 0; i < keys.length; ++i) {
            com.android.camera.ListPreference pref = group.findPreference(keys[i]);
            if (pref != null) {
                Log.v("zhang", "-----pref.getKey() =  " + pref.getKey() + " pref.getValue()=" + pref.getValue());
                mListItem.add(pref);
            }
        }
        gneCatPreference.removeAll();
        swiCatPreference.removeAll();
//        for(int i= 0;i<4;i++){
//            CheckBoxPreference c = new CheckBoxPreference(a);
//            c.setKey("key_"+i);
//            c.setTitle("key_"+i);
//            c.setChecked(true);
//            c.setPersistent(false);
//            swiCatPreference.addPreference(c);
//        }
        com.android.camera.ListPreference pref = null;
        for (int i = 0; i < mListItem.size(); i++) {
            pref = mListItem.get(i);
            Log.v("keyie", "key:" + pref.getKey());
            if (contains___(switchKeys, pref.getKey())) {
                Log.v("zhang", "----------------------------  ");
                pref.print();
                CheckBoxPreference c = new CheckBoxPreference(a);
                c.setKey(pref.getKey());
                //c.setKey("key_" + i);
                c.setTitle(pref.getTitle());
                c.setPersistent(false);
                c.setLayoutResource(R.layout.pref_layout_widget_frame);
                c.setWidgetLayoutResource(R.layout.pref_widget_frame_switch);
                boolean b = getCameraListPreferenceBoolean(pref);
                c.setChecked(b);
                Log.v("zhang", "----------------------------  " + b);
                swiCatPreference.addPreference(c);
                c.setOnPreferenceChangeListener(this);
                c.setOnPreferenceClickListener(this);
            } else {
                ListPreference l = new ListPreference(a);
                l.setKey(pref.getKey());
                l.setTitle(pref.getTitle());
                l.setLayoutResource(R.layout.pref_layout_list);
                l.setEntries(pref.getEntries());
                l.setEntryValues(pref.getEntryValues());
                l.setValue(pref.getValue());
                l.setSummary(pref.getEntry());
                l.setPersistent(false);
                l.setDialogTitle(pref.getTitle());
                gneCatPreference.addPreference(l);
                l.setOnPreferenceChangeListener(this);
                l.setOnPreferenceClickListener(this);
            }
        }

    }

    public boolean contains___(String[] stringArray, String source) {
        if (stringArray != null && stringArray.length > 0 && source != null) {
            for (int i = 0; i < stringArray.length; i++) {
                if (source.equals(stringArray[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    private void reflshPictrueSize() {
        ListPreference ratio = (ListPreference) findPreference(CameraSettings.KEY_PICTURE_RATIO);
        ListPreference size = (ListPreference) findPreference(CameraSettings.KEY_PICTURE_SIZE);
        com.android.camera.ListPreference ratPre = mPreGr.findPreference(CameraSettings.KEY_PICTURE_RATIO);
        com.android.camera.ListPreference sizePre = mPreGr.findPreference(CameraSettings.KEY_PICTURE_SIZE);
        Log.v("zhang", "reflshPictrueSize --ratPre.getEntry() = " + ratPre.getEntry());
        if (ratPre.getEntry().equals("16:9")) {
            size.setEntries(PICTURE_SIZE_16_9_E);
            size.setEntryValues(PICTURE_SIZE_16_9_V);
            sizePre.setValue(PICTURE_SIZE_16_9_V[0].toString());
            size.setSummary(sizePre.getEntry());
            size.setValue(PICTURE_SIZE_16_9_V[0].toString());
        } else if (ratPre.getEntry().equals("4:3")) {
            size.setEntries(PICTURE_SIZE_4_3_E);
            size.setEntryValues(PICTURE_SIZE_4_3_V);
            sizePre.setValue(PICTURE_SIZE_4_3_V[0].toString());
            size.setSummary(sizePre.getEntry());
            size.setValue(PICTURE_SIZE_4_3_V[0].toString());
        } else if (ratPre.getEntry().equals("1:1")) {
            size.setEntries(PICTURE_SIZE_1_1_E);
            size.setEntryValues(PICTURE_SIZE_1_1_V);
            sizePre.setValue(PICTURE_SIZE_1_1_V[0].toString());
            size.setSummary(sizePre.getEntry());
            size.setValue(PICTURE_SIZE_1_1_V[0].toString());
        }

    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        com.android.camera.ListPreference l = mPreGr.findPreference(key);
        switch (key) {
            case CameraSettings.KEY_PICTURE_RATIO: {
                String value = String.valueOf(newValue);
//                com.android.camera.ListPreference l = mPreGr.findPreference(CameraSettings.KEY_PICTURE_RATIO);
                l.setValue(value);
                ListPreference ratio = (ListPreference) findPreference(CameraSettings.KEY_PICTURE_RATIO);
                ratio.setSummary(l.getEntry());
                ratio.setValue(l.getValue());
                reflshPictrueSize();
                break;
            }
            case CameraSettings.KEY_PICTURE_SIZE: {
                String value = String.valueOf(newValue);
//                com.android.camera.ListPreference l = mPreGr.findPreference(CameraSettings.KEY_PICTURE_SIZE);
                l.setValue(value);
                ListPreference size = (ListPreference) findPreference(CameraSettings.KEY_PICTURE_SIZE);
                size.setSummary(l.getEntry());
                size.setValue(l.getValue());
                break;
            }
            case CameraSettings.KEY_JPEG_QUALITY: {
                String value = String.valueOf(newValue);
//                com.android.camera.ListPreference l = mPreGr.findPreference(CameraSettings.KEY_JPEG_QUALITY);
                l.setValue(value);
                ListPreference quality = (ListPreference) findPreference(CameraSettings.KEY_JPEG_QUALITY);
                quality.setSummary(l.getEntry());
                quality.setValue(l.getValue());
                break;
            }
            case CameraSettings.KEY_ISO: {
                String value = String.valueOf(newValue);
//                com.android.camera.ListPreference l = mPreGr.findPreference(CameraSettings.KEY_ISO);
                l.setValue(value);
                ListPreference iso = (ListPreference) findPreference(CameraSettings.KEY_ISO);
                iso.setSummary(l.getEntry());
                iso.setValue(l.getValue());
                break;
            }
            case CameraSettings.KEY_EXPOSURE: {
                String value = String.valueOf(newValue);
//                com.android.camera.ListPreference l = mPreGr.findPreference(CameraSettings.KEY_EXPOSURE);
                l.setValue(value);
                ListPreference expo = (ListPreference) findPreference(CameraSettings.KEY_EXPOSURE);
                expo.setSummary(l.getEntry());
                expo.setValue(l.getValue());
                break;
            }
            case CameraSettings.KEY_WHITE_BALANCE: {
                String value = String.valueOf(newValue);
//                com.android.camera.ListPreference l = mPreGr.findPreference(CameraSettings.KEY_WHITE_BALANCE);
                l.setValue(value);
                ListPreference white = (ListPreference) findPreference(CameraSettings.KEY_WHITE_BALANCE);
                white.setSummary(l.getEntry());
                white.setValue(l.getValue());
                break;
            }
            case CameraSettings.KEY_AUX_GRID: {
                boolean value = (Boolean) newValue;
                Log.v("zhang", "KEY_AUX_GRID newValue = " + String.valueOf(value));
//                com.android.camera.ListPreference l = mPreGr.findPreference(CameraSettings.KEY_AUX_GRID);
                if (l.getValue().equalsIgnoreCase("on")) {
                    l.setValue("off");
                } else if (l.getValue().equalsIgnoreCase("off")) {
                    l.setValue("on");
                }
//                l.setValue(value);
                CheckBoxPreference grid = (CheckBoxPreference) findPreference(CameraSettings.KEY_AUX_GRID);
                boolean b = getCameraListPreferenceBoolean(l);
                grid.setChecked(b);
                break;
            }
            case CameraSettings.KEY_RECORD_LOCATION: {
                boolean value = (Boolean) newValue;
                Log.v("zhang", " newValue = " + String.valueOf(value));
//                com.android.camera.ListPreference l = mPreGr.findPreference(CameraSettings.KEY_RECORD_LOCATION);
                if (l.getValue().equalsIgnoreCase("on")) {
                    l.setValue("off");
                } else if (l.getValue().equalsIgnoreCase("off")) {
                    l.setValue("on");
                }
//                l.setValue(value);
                CheckBoxPreference loc = (CheckBoxPreference) findPreference(CameraSettings.KEY_RECORD_LOCATION);
                boolean b = getCameraListPreferenceBoolean(l);
                loc.setChecked(b);
                break;
            }
            case CameraSettings.KEY_CHUS_FACE_BEAUTY: {
                boolean value = (Boolean) newValue;
                Log.v("zhang", " newValue = " + String.valueOf(value));
//                com.android.camera.ListPreference l = mPreGr.findPreference(CameraSettings.KEY_CHUS_FACE_BEAUTY);
                if (l.getValue().equalsIgnoreCase("disable")) {
                    l.setValue("enable");
                } else if (l.getValue().equalsIgnoreCase("enable")) {
                    l.setValue("disable");
                }
//                l.setValue(value);
                CheckBoxPreference face = (CheckBoxPreference) findPreference(CameraSettings.KEY_CHUS_FACE_BEAUTY);
                boolean b = getCameraListPreferenceBoolean(l);
                face.setChecked(b);
                break;
            }
            case CameraSettings.KEY_SELFIE_FLASH: {
                boolean value = (Boolean) newValue;
                Log.v("zhang", " newValue = " + String.valueOf(value));
//                com.android.camera.ListPreference l = mPreGr.findPreference(CameraSettings.KEY_SELFIE_FLASH);
                if (l.getValue().equalsIgnoreCase("off")) {
                    l.setValue("on");
                } else if (l.getValue().equalsIgnoreCase("on")) {
                    l.setValue("off");
                }
//                l.setValue(value);
                CheckBoxPreference sel = (CheckBoxPreference) findPreference(CameraSettings.KEY_SELFIE_FLASH);
                boolean b = getCameraListPreferenceBoolean(l);
                sel.setChecked(b);
                break;
            }
            case CameraSettings.KEY_LONGSHOT: {
                boolean value = (Boolean) newValue;
                Log.v("zhang", " newValue = " + String.valueOf(value));
//                com.android.camera.ListPreference l = mPreGr.findPreference(CameraSettings.KEY_LONGSHOT);
                if (l.getValue().equalsIgnoreCase("off")) {
                    l.setValue("on");
                } else if (l.getValue().equalsIgnoreCase("on")) {
                    l.setValue("off");
                }
//                l.setValue(value);
                CheckBoxPreference sel = (CheckBoxPreference) findPreference(CameraSettings.KEY_LONGSHOT);
                boolean b = getCameraListPreferenceBoolean(l);
                sel.setChecked(b);
                break;
            }
            case CameraSettings.KEY_FACE_DETECTION: {
                boolean value = (Boolean) newValue;
                Log.v("zhang", " newValue = " + String.valueOf(value));
//                com.android.camera.ListPreference l = mPreGr.findPreference(CameraSettings.KEY_FACE_DETECTION);
                if (l.getValue().equalsIgnoreCase("off")) {
                    l.setValue("on");
                } else if (l.getValue().equalsIgnoreCase("on")) {
                    l.setValue("off");
                }
//                l.setValue(value);
                CheckBoxPreference detec = (CheckBoxPreference) findPreference(CameraSettings.KEY_FACE_DETECTION);
                boolean b = getCameraListPreferenceBoolean(l);
                detec.setChecked(b);
                break;
            }
            case CameraSettings.KEY_SHUTTER_SOUND: {
                boolean value = (Boolean) newValue;
                Log.v("zhang", " newValue = " + String.valueOf(value));
//                com.android.camera.ListPreference l = mPreGr.findPreference(CameraSettings.KEY_SHUTTER_SOUND);
                if (l.getValue().equalsIgnoreCase("disable")) {
                    l.setValue("enable");
                } else if (l.getValue().equalsIgnoreCase("enable")) {
                    l.setValue("disable");
                }
//                l.setValue(value);
                CheckBoxPreference shutt = (CheckBoxPreference) findPreference(CameraSettings.KEY_SHUTTER_SOUND);
                boolean b = getCameraListPreferenceBoolean(l);
                shutt.setChecked(b);
                break;
            }
        }
        if (mListener != null) {
            mListener.onListPrefChanged(l);
        }
        return true;
    }

    public void classifyPictruesSize(List<CharSequence> values, List<CharSequence> entries) {
        ArrayList<CharSequence> PICTURE_SIZE_4_3 = new ArrayList<CharSequence>();
        ArrayList<CharSequence> PICTURE_SIZE_16_9 = new ArrayList<CharSequence>();
        ArrayList<CharSequence> PICTURE_SIZE_1_1 = new ArrayList<CharSequence>();
        ArrayList<CharSequence> PICTURE_SIZE_4_3_ENTRIES = new ArrayList<CharSequence>();
        ArrayList<CharSequence> PICTURE_SIZE_16_9_ENTRIES = new ArrayList<CharSequence>();
        ArrayList<CharSequence> PICTURE_SIZE_1_1_ENTRIES = new ArrayList<CharSequence>();
        ArrayList<CharSequence> PICTURE_RATIO_ENTRIES = new ArrayList<CharSequence>();
        ArrayList<CharSequence> PICTURE_RATIO = new ArrayList<CharSequence>();
        if (values.size() <= 0) return;
        for (int i = 0; i < values.size(); i++) {
            String candidate = values.get(i).toString();
            int index = candidate.indexOf('x');
            if (index < 0) return;
            int height = Integer.parseInt(candidate.substring(0, index));
            int width = Integer.parseInt(candidate.substring(index + 1));
            double raito = height * 1.0 / width;
            Log.v("zhang", "ratio = " + raito + ",height=" + height + ",width = " + width);
            if (raito == 16 * 1.0 / 9) {
                PICTURE_SIZE_16_9.add(candidate);
                PICTURE_SIZE_16_9_ENTRIES.add(entries.get(i));
            } else if (raito == 4 * 1.0 / 3) {
                PICTURE_SIZE_4_3.add(candidate);
                PICTURE_SIZE_4_3_ENTRIES.add(entries.get(i));
            } else if (raito == 1.0) {
                PICTURE_SIZE_1_1.add(candidate);
                PICTURE_SIZE_1_1_ENTRIES.add(entries.get(i));
            }
        }
        if (PICTURE_SIZE_16_9.size() > 0) {
            PICTURE_RATIO_ENTRIES.add("16:9");
            PICTURE_RATIO.add("0");
        }
        if (PICTURE_SIZE_4_3.size() > 0) {
            PICTURE_RATIO_ENTRIES.add("4:3");
            PICTURE_RATIO.add("1");
        }
        if (PICTURE_SIZE_1_1.size() > 0) {
            PICTURE_RATIO_ENTRIES.add("1:1");
            PICTURE_RATIO.add("2");
        }
        PICTURE_SIZE_16_9_E = PICTURE_SIZE_16_9_ENTRIES.toArray(new CharSequence[PICTURE_SIZE_16_9_ENTRIES.size()]);
        PICTURE_SIZE_4_3_E = PICTURE_SIZE_4_3_ENTRIES.toArray(new CharSequence[PICTURE_SIZE_4_3_ENTRIES.size()]);
        PICTURE_SIZE_1_1_E = PICTURE_SIZE_1_1_ENTRIES.toArray(new CharSequence[PICTURE_SIZE_1_1_ENTRIES.size()]);

        PICTURE_SIZE_16_9_V = PICTURE_SIZE_16_9.toArray(new CharSequence[PICTURE_SIZE_16_9.size()]);
        PICTURE_SIZE_4_3_V = PICTURE_SIZE_4_3.toArray(new CharSequence[PICTURE_SIZE_4_3.size()]);
        PICTURE_SIZE_1_1_V = PICTURE_SIZE_1_1.toArray(new CharSequence[PICTURE_SIZE_1_1.size()]);

        PICTURE_RATIO_E = PICTURE_RATIO_ENTRIES.toArray(new CharSequence[PICTURE_RATIO_ENTRIES.size()]);
        PICTURE_RATIO_V = PICTURE_RATIO.toArray(new CharSequence[PICTURE_RATIO.size()]);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference == ratioListPreference) {
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //对传递进来的Activity进行接口转换
        if (activity instanceof FragmentListener) {
            fListener = ((FragmentListener) activity);
        }
        if (activity instanceof Listener) {
            mListener = ((Listener) activity);
        }
    }

    public FragmentListener fListener;

    public interface FragmentListener {
        void callback();
    }

    private Listener mListener;

    static public interface Listener {
        public void onListPrefChanged(com.android.camera.ListPreference pref);
    }
}
