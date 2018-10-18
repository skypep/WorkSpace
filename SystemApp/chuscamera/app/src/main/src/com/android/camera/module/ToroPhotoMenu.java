package com.android.camera.module;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.camera.AGlobalConfig;
import com.android.camera.CameraActivity;
import com.android.camera.CameraModule;
import com.android.camera.CameraSettings;
import com.android.camera.IconListPreference;
import com.android.camera.ListPreference;
import com.android.camera.MenuController;
import com.android.camera.PreferenceGroup;
import com.android.camera.SettingsManager;
import com.android.camera.module.prefs.SettingsFragment2;
import com.android.camera.ui.CameraControls;
import com.android.camera.ui.CountdownTimerPopup;
import com.android.camera.ui.ListMenu;
import com.android.camera.ui.ListSubMenu;
import com.android.camera.ui.ModuleSwitcher;
import com.android.camera.ui.RotateLayout;
import com.android.camera.ui.RotateTextToast;
import com.android.camera.util.CameraUtil;
import com.toro.camera.R;

import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.Locale;

public class ToroPhotoMenu extends MenuController
        implements
        //ListMenu.Listener,	// frankie, 2017.08.15, for more clearness code structure, rm this
        //SettingsFragment2.Listener,     // frankie, the same as ListMenu.Listener
        CountdownTimerPopup.Listener,
        ListSubMenu.Listener 	// frankie,
{
    private static String TAG = "ToroPhotoMenu";

    private final String mSettingOff;
    private final String mSettingOn;

    private String[] mOtherKeys1;
    private String[] mOtherKeys2;

    private ViewGroup mListMenuContainer = null; // frankie, add
    private ListMenu mListMenu;
    private View mPreviewMenu;
    private static final int POPUP_NONE = 0;
    private static final int POPUP_FIRST_LEVEL = 1;
    private static final int POPUP_SECOND_LEVEL = 2;
    private static final int POPUP_IN_ANIMATION_SLIDE = 3;
    private static final int POPUP_IN_ANIMATION_FADE = 4;
    private static final int POPUP_IN_MAKEUP    = 5;
    private static final int PREVIEW_MENU_NONE = 0;
    private static final int PREVIEW_MENU_IN_ANIMATION = 1;
    private static final int PREVIEW_MENU_ON = 2;
    private static final int MODE_SCENE = 0;
    private static final int MODE_FILTER = 1;
    private static final int MODE_MAKEUP = 2;
    private static final int DEVELOPER_MENU_TOUCH_COUNT = 10;
    private int mSceneStatus;
    private View mCountDownSwitcher;	// frankie, add
    private View mTsMakeupSwitcher;
    private View mFrontBackSwitcher;
    private View mSceneModeSwitcher;
    private View mFlashSwitcher;	// frankie, add
    private View mFlashLightLyt;
    private View mTopMenu;
    //private View mCameraSwitcher;
    private View mPreviewThumbnail;
    private TextView modeTv;
    private PhotoUI mUI;
    private View menuCountdownIv;
    private View moreSettingsView;
    private TextView coundownTV;
    private View flashActionView;
    private View delayActionView;
    private int mPopupStatus;
    private int mPreviewMenuStatus;
    private ListSubMenu mListSubMenu;
    private CameraActivity mActivity;
    private String mPrevSavedCDS;
    private boolean mIsTNREnabled = false;
    private boolean mIsCDSUpdated = false;
    private boolean mIsFrontCamera = false;
    private int privateCounter = 0;
    private static final int ANIMATION_DURATION = 300;
    private static final int CLICK_THRESHOLD = 200;
    private int previewMenuSize;
    private TsMakeupManager mTsMakeupManager;
    private TsMakeupManager.MakeupLevelListener mMakeupListener;
    private MakeupHandler mHandler = new MakeupHandler();
    private static final int MAKEUP_MESSAGE_ID = 0;
    private HashSet<View> mWasVisibleSet = new HashSet<View>();

    private boolean flashEditMode = false;
    private boolean delayEditMode = false;

    FragmentManager mFragmentManager = null;
    private FrameLayout mSettingsFragmentContainerLayout = null;
    private TextView titleTv = null;
    private View mSettingsLayout = null;
    //	private LinearLayout mSettingsBackLyt = null;
    private TextView backTv = null;
    private SettingsFragment2 mChusSettingsFragment; // frankie, add for replace of mListMenu
    public ToroPhotoMenu(CameraActivity activity, PhotoUI ui, TsMakeupManager.MakeupLevelListener makeupListener) {
        super(activity);
        mUI = ui;
        mSettingOff = activity.getString(R.string.setting_off_value);
        mSettingOn = activity.getString(R.string.setting_on_value);
        mActivity = activity;
        mFragmentManager = mActivity.getFragmentManager();
        mMakeupListener = makeupListener;

        mFrontBackSwitcher = ui.getRootView().findViewById(R.id.front_back_switcher);
        mTsMakeupSwitcher = ui.getRootView().findViewById(R.id.ts_makeup_switcher);
        mPreviewThumbnail = ui.getRootView().findViewById(R.id.preview_thumb);

        mSettingsLayout = ui.getRootView().findViewById(R.id.prefs_setting_layout);
        mSettingsFragmentContainerLayout = (FrameLayout) ui.getRootView().findViewById(R.id.prefs_container);
//        mSettingsBackLyt = (LinearLayout) ui.getRootView().findViewById(R.id.pref_header_backward_layout);
        backTv = (TextView) ui.getRootView().findViewById(R.id.pref_header_backward_tv);
        titleTv = (TextView) ui.getRootView().findViewById(R.id.text_title);
        titleTv.setText(R.string.chus_settings_fragment_title_bar_title);
//        initNativeView(ui.getRootView());
        hideNativeTopControlsMenu(ui.getRootView());
        initView(ui.getRootView());
    }

    private void initView(View rootView){
        mFlashLightLyt = rootView.findViewById(R.id.layout_top_flashlight);
        mTopMenu = rootView.findViewById(R.id.layout_top_menu);
        mFlashSwitcher = rootView.findViewById(R.id.flash_mode_iv);
        modeTv = (TextView)rootView.findViewById(R.id.mode_tv);
        modeTv.setVisibility(View.GONE);
        moreSettingsView = rootView.findViewById(R.id.menu_more_iv);
        moreSettingsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AGlobalConfig.config_module_PHOTO_MODULE_use_new_settings_en) {
                        showSettingFragment();
                    } else {
                        openFirstLevel();
                    }
            }
        });

        flashActionView = rootView.findViewById(R.id.top_flash_action_layout);
        delayActionView = rootView.findViewById(R.id.top_delay_layout);
        menuCountdownIv = rootView.findViewById(R.id.menu_countdown_iv);
        coundownTV = (TextView) rootView.findViewById(R.id.countdown_tv);
        menuCountdownIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(delayEditMode) {
                    exitEditView();
                } else {
                    showDelayEditView();
                }
            }
        });

    }

    public void setAspectRatio(float ratio) {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mTopMenu.getLayoutParams();
        int mScreenRatio = CameraUtil.determineRatio(ratio);
        switch (mScreenRatio) {
            case CameraUtil.RATIO_16_9:
                lp.setMargins(0,mActivity.getResources().getDimensionPixelOffset(R.dimen.toro_phone_margin_top_16_9),0,0);
                break;
            case CameraUtil.RATIO_4_3:
                lp.setMargins(0,mActivity.getResources().getDimensionPixelOffset(R.dimen.toro_phone_margin_top_4_3),0,0);
                break;
            default:
                lp.setMargins(0,mActivity.getResources().getDimensionPixelOffset(R.dimen.toro_phone_margin_top_1_1),0,0);
                break;
        }
        mTopMenu.setLayoutParams(lp);
    }

    private void updateToroCountDelay() {
        TextView delayClose,delay3s,delay5s,delay10s;
        delayClose = (TextView) delayActionView.findViewById(R.id.delay_close);
        delay3s = (TextView) delayActionView.findViewById(R.id.delay_3s);
        delay5s = (TextView) delayActionView.findViewById(R.id.delay_5s);
        delay10s = (TextView) delayActionView.findViewById(R.id.delay_10s);
        delayClose.setTextColor(Color.WHITE);
        delay3s.setTextColor(Color.WHITE);
        delay5s.setTextColor(Color.WHITE);
        delay10s.setTextColor(Color.WHITE);
        delayClose.setOnClickListener(delaySelectedListener);
        delay3s.setOnClickListener(delaySelectedListener);
        delay5s.setOnClickListener(delaySelectedListener);
        delay10s.setOnClickListener(delaySelectedListener);
        IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_TIMER);
        if (pref == null)
            return;

        int index = pref.findIndexOfValue(pref.getValue());
        CharSequence[] values = pref.getEntryValues();
        String value = pref.getValue();
        coundownTV.setVisibility(View.VISIBLE);
        if(value.equals("3")){
            coundownTV.setText(mActivity.getString(R.string.pref_camera_shutter_delay_timer_3s));
            delay3s.setTextColor(mActivity.getColor(R.color.toro_action_selected_color));
        }else if(value.equals("5")){
            coundownTV.setText(mActivity.getString(R.string.pref_camera_shutter_delay_timer_5s));
            delay5s.setTextColor(mActivity.getColor(R.color.toro_action_selected_color));
        }else if(value.equals("10")){
            coundownTV.setText(mActivity.getString(R.string.pref_camera_shutter_delay_timer_10s));
            delay10s.setTextColor(mActivity.getColor(R.color.toro_action_selected_color));
        }else {
            coundownTV.setVisibility(View.GONE);
            delayClose.setTextColor(mActivity.getColor(R.color.toro_action_selected_color));
        }

        ((ImageView) menuCountdownIv).setImageResource(((IconListPreference) pref).getLargeIconIds()[index]);

    }

    private void updateFlashButton() {
        TextView flashOff,flashOn,flashAuto;
        flashOff = (TextView) flashActionView.findViewById(R.id.flash_off);
        flashOn = (TextView) flashActionView.findViewById(R.id.flash_on);
        flashAuto = (TextView) flashActionView.findViewById(R.id.flash_auto);
        flashOff.setTextColor(Color.WHITE);
        flashOn.setTextColor(Color.WHITE);
        flashAuto.setTextColor(Color.WHITE);
        flashOff.setOnClickListener(flashSelectedListener);
        flashOn.setOnClickListener(flashSelectedListener);
        flashAuto.setOnClickListener(flashSelectedListener);
        mFlashSwitcher.setVisibility(View.GONE);
        final IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_FLASH_MODE);
        if (pref == null) {
            return;
        }
        int[] iconIds = pref.getLargeIconIds();
        int resid = -1;
        int index = pref.findIndexOfValue(pref.getValue());
        if (!pref.getUseSingleIcon() && iconIds != null) {
            // Each entry has a corresponding icon.
            resid = iconIds[index];
        } else {
            // The preference only has a single icon to represent it.
            resid = pref.getSingleIcon();
        }
        ImageView iv = (ImageView) mFlashSwitcher;
        iv.setImageResource(resid);
        mFlashSwitcher.setVisibility(View.VISIBLE);

        // if KEY_QC_CHROMA_FLASH on, should disable the flashlight button
        IconListPreference chroma_pref =
                (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_QC_CHROMA_FLASH);
        String chroma_value = chroma_pref.getValue();
        if(chroma_value.equalsIgnoreCase("chroma-flash-on")) { // chroma-flash-on, chroma-flash-off
            mFlashSwitcher.setEnabled(false);
        }

        String flashMode = pref.getValue();
        if(flashMode.equals("off")) {
            flashOff.setTextColor(mActivity.getColor(R.color.toro_action_selected_color));
        } else if(flashMode.equals("auto")) {
            flashAuto.setTextColor(mActivity.getColor(R.color.toro_action_selected_color));
        } else if(flashMode.equals("on")) {
            flashOn.setTextColor(mActivity.getColor(R.color.toro_action_selected_color));
        }
            mFlashSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flashEditMode) {
                    exitEditView();
                } else {
                    showFlashEditView();
                }
            }
        });
    }

    private void updateTopMenuView(){
        if(mIsFrontCamera){
            mFlashSwitcher.setVisibility(View.GONE);
        }else {
            mFlashSwitcher.setVisibility(View.VISIBLE);
        }
        mTopMenu.setVisibility(View.VISIBLE);
        mTopMenu.setAlpha(1.0f);
        mFlashLightLyt.setVisibility(View.GONE);
        updateToroCountDelay();
        updateFlashButton();
    }

    private void refreshView(IconListPreference pref){
        if(pref == null){
            return;
        }
        View button = null;
        if(pref.getKey().equalsIgnoreCase(CameraSettings.KEY_FLASH_MODE)){
            button = mFlashSwitcher;
        }else if(pref.getKey().equalsIgnoreCase(CameraSettings.KEY_SELFIE_FLASH)){
        }
        if(button == null){
            return;
        }
        int[] iconIds = pref.getLargeIconIds();
        int resid = -1;
        int index = pref.findIndexOfValue(pref.getValue());
        if (!pref.getUseSingleIcon() && iconIds != null) {
            // Each entry has a corresponding icon.
            resid = iconIds[index];
        } else {
            // The preference only has a single icon to represent it.
            resid = pref.getSingleIcon();
        }
        ImageView iv = (ImageView) button;
        iv.setImageResource(resid);
    }

    private void showDelayEditView() {
        delayEditMode = true;
        flashEditMode = false;
        delayActionView.setVisibility(View.VISIBLE);
        flashActionView.setVisibility(View.GONE);
    }

    private void showFlashEditView() {
        delayEditMode = false;
        flashEditMode = true;
        delayActionView.setVisibility(View.GONE);
        flashActionView.setVisibility(View.VISIBLE);
    }

    private void exitEditView() {
        delayEditMode = false;
        flashEditMode = false;
        delayActionView.setVisibility(View.GONE);
        flashActionView.setVisibility(View.GONE);
    }

    private View.OnClickListener delaySelectedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView text = (TextView)v;
            if(text.getText().equals(mActivity.getString(R.string.pref_camera_shutter_delay_timer_3s))){
                setDelay(mActivity.getString(R.string.pref_camera_shutter_delay_timer_3s_value));
            } else if(text.getText().equals(mActivity.getString(R.string.pref_camera_shutter_delay_timer_5s))){
                setDelay(mActivity.getString(R.string.pref_camera_shutter_delay_timer_5s_value));
            } else if(text.getText().equals(mActivity.getString(R.string.pref_camera_shutter_delay_timer_10s))){
                setDelay(mActivity.getString(R.string.pref_camera_shutter_delay_timer_10s_value));
            } else if(text.getText().equals(mActivity.getString(R.string.pref_camera_shutter_delay_timer_off_value))) {
                setDelay(mActivity.getString(R.string.pref_camera_shutter_delay_timer_off_value));
            }
        }
    };

    private View.OnClickListener flashSelectedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView text = (TextView)v;
            if(text.getText().equals(mActivity.getString(R.string.pref_camera_flashmode_entry_off))){
                setFlashMode(0);
            } else if(text.getText().equals(mActivity.getString(R.string.pref_camera_flashmode_entry_auto))){
                setFlashMode(1);
            } else if(text.getText().equals(mActivity.getString(R.string.pref_camera_flashmode_entry_on))){
                setFlashMode(2);
            }
        }
    };

    private void setFlashMode(int index) {
        if(index >= 0) {
            int selectResId = setPreferenceIndex(CameraSettings.KEY_FLASH_MODE, index);
            Log.v(TAG, "selectResId:" + selectResId);
        }

//        animFlashLightLytFadout();

        if(index == 0) { // should cancel auto focus to let the parameter setup into CameraDevice !
            mUI.cancelAutoFocus__i();
        }
        updateFlashButton();
        exitEditView();
    }

    private void setDelay(String delayValue) {
        IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_TIMER);
        if (pref == null)
            return;

        pref.setValue(delayValue);
        reloadPreference(pref);
        onSettingChanged(pref);
        updateToroCountDelay();
        exitEditView();
    }

    private void initHDRButton(View button){
        IconListPreference hdr_pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_CAMERA_HDR);
        if (hdr_pref == null) {
            return;
        }
        final String hdrValue = hdr_pref.getValue();
        updatePopupImageViewHdr((ImageView) button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // directly change the value
                IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_CAMERA_HDR);
                if (pref == null)
                    return;

                int index = pref.findIndexOfValue(pref.getValue());
                CharSequence[] values = pref.getEntryValues();
                index = (index + 1) % values.length;
                pref.setValueIndex(index);

                ((ImageView) v).setImageResource(((IconListPreference) pref).getLargeIconIds()[index]);

                reloadPreference(pref);
                onSettingChanged(pref);
            }
        });
    }


    private long delay = 0;
    private void showSettingMenu(final View menuView){
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    outAniSetMenu();
                    break;
            }
        }
    };

    private void outAniSetMenu(){

//        AnimatorSet set = new AnimatorSet();
//        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mSettingLyt, "translationX", 0f ,-300f);
//        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mTopMenu, "alpha", 0f ,1.0f);
//        set.play(animator1).with(animator2);
//        set.setDuration(100);
//        set.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//            }
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                mSettingLyt.setVisibility(View.GONE);
//                mTopMenu.setVisibility(View.VISIBLE);
//            }
//            @Override
//            public void onAnimationCancel(Animator animation) {
//            }
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//            }
//        });
//        set.start();
    }

    private void showFlashLightMenu(final View menuView){
        if(mFlashLightLyt == null){
            return;
        }

        IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_FLASH_MODE);
        if(pref == null){
            return;
        }
        int selctIndex = pref.findIndexOfValue(pref.getValue());
        Log.v(TAG, "flashlight value=" + pref.getValue() + " /" + selctIndex);

        final int[] ids = new int[]{ R.id.menu_flashlight_off_iv,R.id.menu_flashlight_auto_iv, R.id.menu_flashlight_on_iv,R.id.menu_flashlight};

        View.OnClickListener onClickListener_flash = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "+ onClick");
                int id_ = v.getId();
                int selc = -1;
                if(id_ == R.id.menu_flashlight_off_iv) {
                    selc = 0;
                } else if(id_ == R.id.menu_flashlight_auto_iv) {
                    selc = 1;
                } else if(id_ == R.id.menu_flashlight_on_iv) {
                    selc = 2;
                }else if(id_ == R.id.menu_flashlight){
                    selc = -1;
                }
                if(selc >= 0) {
                    for(int i=0;i<ids.length;i++){
                        if(i == selc){
                            mFlashLightLyt.findViewById(ids[i]).setSelected(true);
                        }else {
                            mFlashLightLyt.findViewById(ids[i]).setSelected(false);
                        }
                    }
                    int selectResId = setPreferenceIndex(CameraSettings.KEY_FLASH_MODE, selc);
                    Log.v(TAG, "selectResId:" + selectResId);
                    if(selectResId > 0) {
                        ((ImageView) menuView).setImageResource(selectResId);
                    }
                }

                animFlashLightLytFadout();

                if(selc == 0) { // should cancel auto focus to let the parameter setup into CameraDevice !
                    mUI.cancelAutoFocus__i();
                }

                //mTopMenu.setVisibility(View.VISIBLE);
                //mTopMenu.setAlpha(1.0f);
                //mFlashLightLyt.setVisibility(View.GONE);

                Log.v(TAG, "- onClick");
            }
        };
        for(int i=0;i<ids.length;i++) {
            View iv = mFlashLightLyt.findViewById(ids[i]);
            if(iv != null) {
                iv.setOnClickListener(onClickListener_flash);
            }
            if(i == selctIndex){
                mFlashLightLyt.findViewById(ids[i]).setSelected(true);
            }else {
                mFlashLightLyt.findViewById(ids[i]).setSelected(false);
            }
        }
    }

    public void setIsFrontCamera(boolean isFrontCamera){
        this.mIsFrontCamera = isFrontCamera;
    }

    public void hideNativeTopControlsMenu(View rootView){
        rootView.findViewById(R.id.flash_mode_switcher).setVisibility(View.GONE);
        rootView.findViewById(R.id.filter_mode_switcher).setVisibility(View.GONE);
        rootView.findViewById(R.id.countdown_switcher).setVisibility(View.GONE);
        rootView.findViewById(R.id.menu).setVisibility(View.GONE);
    }

    public void initialize(PreferenceGroup group) { // frankie, camera just opened, to initialized setting menu item !
        super.initialize(group);

        mListSubMenu = null;
        mListMenu = null;
        mListMenuContainer = null; // frankie, add
        mPopupStatus = POPUP_NONE;
        mPreviewMenuStatus = POPUP_NONE;

        mOtherKeys1 = new String[] {
//                CameraSettings.KEY_SELFIE_FLASH,
                //CameraSettings.KEY_FLASH_MODE,
                CameraSettings.KEY_RECORD_LOCATION,
                CameraSettings.KEY_AUX_GRID, 			// frankie, add
//                CameraSettings.KEY_CHUS_FACE_BEAUTY, 	// frankie, add
                //CameraSettings.KEY_CHUS_TOUCH_TO_TAKE_PHOTO,// frankie, add
                CameraSettings.KEY_PICTURE_RATIO,	// frankie,
                CameraSettings.KEY_PICTURE_SIZE,
//                CameraSettings.KEY_JPEG_QUALITY,
                //CameraSettings.KEY_TIMER,
                //CameraSettings.KEY_CAMERA_SAVEPATH,	// frankie, 2017.07.28, rm
                CameraSettings.KEY_LONGSHOT,
                CameraSettings.KEY_FACE_DETECTION,
//                CameraSettings.KEY_ISO,					// frankie, 2017.08.18, rm
//                CameraSettings.KEY_EXPOSURE,			// frankie, 2017.08.18, rm
//                CameraSettings.KEY_WHITE_BALANCE,		// frankie, 2017.08.18, rm
                //CameraSettings.KEY_QC_CHROMA_FLASH,	// frankie, 2017.08.18, rm
//                CameraSettings.KEY_REDEYE_REDUCTION,
//                CameraSettings.KEY_SELFIE_MIRROR,
                CameraSettings.KEY_SHUTTER_SOUND,
                CameraSettings.KEY_RESET_DEFAULT_SETTINGS
        };

        mOtherKeys2 = new String[] {
                CameraSettings.KEY_SELFIE_FLASH,
                //CameraSettings.KEY_FLASH_MODE,
                CameraSettings.KEY_RECORD_LOCATION,
                CameraSettings.KEY_AUX_GRID, 			// frankie, add
                CameraSettings.KEY_CHUS_FACE_BEAUTY, 	// frankie, add
                //CameraSettings.KEY_CHUS_TOUCH_TO_TAKE_PHOTO,// frankie, add
                CameraSettings.KEY_PICTURE_RATIO,	// frankie,
                CameraSettings.KEY_PICTURE_SIZE,
                CameraSettings.KEY_JPEG_QUALITY,
                CameraSettings.KEY_TIMER,
                //CameraSettings.KEY_CAMERA_SAVEPATH,	// frankie, 2017.07.28, rm
                CameraSettings.KEY_LONGSHOT,
                CameraSettings.KEY_FACE_DETECTION,
                CameraSettings.KEY_ISO,
                CameraSettings.KEY_EXPOSURE,
                CameraSettings.KEY_WHITE_BALANCE,
                CameraSettings.KEY_QC_CHROMA_FLASH,
                CameraSettings.KEY_REDEYE_REDUCTION,
                CameraSettings.KEY_FOCUS_MODE,
                CameraSettings.KEY_AUTO_HDR,
                CameraSettings.KEY_HDR_MODE,
                CameraSettings.KEY_HDR_NEED_1X,
                CameraSettings.KEY_CDS_MODE,
                CameraSettings.KEY_TNR_MODE,
                CameraSettings.KEY_HISTOGRAM,
                CameraSettings.KEY_ZSL,
                CameraSettings.KEY_TIMER_SOUND_EFFECTS,
                CameraSettings.KEY_FACE_RECOGNITION,
                CameraSettings.KEY_TOUCH_AF_AEC,
                CameraSettings.KEY_SELECTABLE_ZONE_AF,
                CameraSettings.KEY_PICTURE_FORMAT,
                CameraSettings.KEY_SATURATION,
                CameraSettings.KEY_CONTRAST,
                CameraSettings.KEY_SHARPNESS,
                CameraSettings.KEY_AUTOEXPOSURE,
                CameraSettings.KEY_ANTIBANDING,
                CameraSettings.KEY_DENOISE,
                CameraSettings.KEY_ADVANCED_FEATURES,
                CameraSettings.KEY_AE_BRACKET_HDR,
                CameraSettings.KEY_INSTANT_CAPTURE,
                CameraSettings.KEY_MANUAL_EXPOSURE,
                CameraSettings.KEY_MANUAL_WB,
                CameraSettings.KEY_MANUAL_FOCUS,
                CameraSettings.KEY_SELFIE_MIRROR,
                CameraSettings.KEY_SHUTTER_SOUND,
                SettingsManager.KEY_CAMERA2
        };

        final Resources res = mActivity.getResources();
        Locale locale = res.getConfiguration().locale;
        // The order is from left to right in the menu.
        updateTopMenuView();
        updateFlahLightIcon(mPreferenceGroup.findPreference(CameraSettings.KEY_CAMERA_HDR));

        mFrontBackSwitcher.setVisibility(View.INVISIBLE);

        initSwitchItem_camera_id(CameraSettings.KEY_CAMERA_ID, mFrontBackSwitcher);

        setAuxGrid();

        if(backTv != null) { // frankie, add
            backTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSettingFragment();
                }
            });
        }
    }

    public void setAuxGrid(){
        if(mPreferenceGroup == null){
            return;
        }
        // frankie, 2017.07.12, add grid start
        final IconListPreference aux_grid_pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_AUX_GRID);
        if(aux_grid_pref != null) {
            String value_ = aux_grid_pref.getValue();
            Log.v(TAG, "value_:" + value_);
            if(value_.equalsIgnoreCase("on")) {
                mUI.showGridLines();
            } else if(value_.equalsIgnoreCase("off")) {
                mUI.hideGridLines();
            } else {
                mUI.hideGridLines();
            }
        }
        // frankie, 2017.07.12, add grid end
    }

    protected class MakeupHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MAKEUP_MESSAGE_ID:
                    mTsMakeupManager.showMakeupView();
                    mUI.adjustOrientation();
                    break;
            }
        }
    }

    public boolean handleBackKey() {
        Log.v("CAM_b", "ToroPhotoMenu, handleBackKey");

        if(hideSettingFragment()) { // frankie,
            return true;
        }
        if(TsMakeupManager.HAS_TS_MAKEUP && mTsMakeupManager.isShowMakeup()) {
            mTsMakeupManager.dismissMakeupUI();
            closeMakeupMode(true);
            mTsMakeupManager.resetMakeupUIStatus();
            mPopupStatus = POPUP_NONE;
            mPreviewMenuStatus = PREVIEW_MENU_NONE;
            return true;
        }
        if (mPreviewMenuStatus == PREVIEW_MENU_ON) {
            animateSlideOut(mPreviewMenu);
            return true;
        }
        if (mPopupStatus == POPUP_NONE)
            return false;
        if (mPopupStatus == POPUP_FIRST_LEVEL) {
            //animateSlideOut(mListMenu, 1);
            animateFadeOut(mListMenuContainer, 1); // frankie,

        } else if (mPopupStatus == POPUP_SECOND_LEVEL) {
            animateFadeOut(mListSubMenu, 2);
            ((ListMenu) mListMenu).resetHighlight();
        }
        return true;
    }

    public void closeSceneMode() {
        mUI.removeSceneModeMenu();
    }

    public void closeMakeupMode(boolean isMakeup) {
        mUI.removeSceneModeMenu();
    }

    public void tryToCloseSubList() {
        if (mListMenu != null)
            ((ListMenu) mListMenu).resetHighlight();

        // frankie, add start
        if(mChusSettingsFragment != null) {
            mChusSettingsFragment.resetHighlight();
        }
        // frankie, add end

        if (mPopupStatus == POPUP_SECOND_LEVEL) {
            mUI.dismissLevel2();
            mPopupStatus = POPUP_FIRST_LEVEL;
        }
    }

    private void animateFadeOut(final View v, final int level) { // (final ListView v, final int level) {
        if (v == null || mPopupStatus == POPUP_IN_ANIMATION_FADE)
            return;
        mPopupStatus = POPUP_IN_ANIMATION_FADE;

        ViewPropertyAnimator vp = v.animate();
        vp.alpha(0f).setDuration(ANIMATION_DURATION);
        vp.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (level == 1) {
                    mUI.dismissLevel1();
                    initializePopup();
                    mPopupStatus = POPUP_NONE;
                    mUI.cleanupListview();
                }
                else if (level == 2) {
                    mUI.dismissLevel2();
                    mPopupStatus = POPUP_FIRST_LEVEL;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (level == 1) {
                    mUI.dismissLevel1();
                    initializePopup();
                    mPopupStatus = POPUP_NONE;
                    mUI.cleanupListview();
                }
                else if (level == 2) {
                    mUI.dismissLevel2();
                    mPopupStatus = POPUP_FIRST_LEVEL;
                }

            }
        });
        vp.start();
    }

    private void animateSlideOut(final View v, final int level) { // (final ListView v, final int level) {
        if (v == null || mPopupStatus == POPUP_IN_ANIMATION_SLIDE)
            return;
        mPopupStatus = POPUP_IN_ANIMATION_SLIDE;

        ViewPropertyAnimator vp = v.animate();
        if (View.LAYOUT_DIRECTION_RTL == TextUtils
                .getLayoutDirectionFromLocale(Locale.getDefault())) {
            switch (mUI.getOrientation()) {
                case 0:
                    vp.translationXBy(v.getWidth());
                    break;
                case 90:
                    vp.translationYBy(-2 * v.getHeight());
                    break;
                case 180:
                    vp.translationXBy(-2 * v.getWidth());
                    break;
                case 270:
                    vp.translationYBy(v.getHeight());
                    break;
            }
        } else {
            switch (mUI.getOrientation()) {
                case 0:
                    vp.translationXBy(-v.getWidth());
                    break;
                case 90:
                    vp.translationYBy(2 * v.getHeight());
                    break;
                case 180:
                    vp.translationXBy(2 * v.getWidth());
                    break;
                case 270:
                    vp.translationYBy(-v.getHeight());
                    break;
            }
        }
        vp.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (level == 1) {
                    mUI.dismissLevel1();
                    initializePopup();
                    mPopupStatus = POPUP_NONE;
                    mUI.cleanupListview();
                } else if (level == 2) {
                    mUI.dismissLevel2();
                    mPopupStatus = POPUP_FIRST_LEVEL;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (level == 1) {
                    mUI.dismissLevel1();
                    initializePopup();
                    mPopupStatus = POPUP_NONE;
                    mUI.cleanupListview();
                } else if (level == 2) {
                    mUI.dismissLevel2();
                    mPopupStatus = POPUP_FIRST_LEVEL;
                }

            }
        });
        vp.setDuration(ANIMATION_DURATION).start();
    }

    public void animateFadeIn(final View v) { // frankie, // (final ListView v) {
        ViewPropertyAnimator vp = v.animate();
        int duration_ = ANIMATION_DURATION;
        //duration_ = 800; // frankie,
        v.setAlpha(0.0f); // frankie, add
        vp.setDuration(duration_)
                //.alpha(0.85f);
                .alpha(1.0f); // frankie,
        vp.start();
    }

    public void animateSlideIn(final View v, int delta, boolean forcePortrait) {
        int orientation = mUI.getOrientation();
        if (!forcePortrait)
            orientation = 0;

        ViewPropertyAnimator vp = v.animate();
        float dest;
        if (View.LAYOUT_DIRECTION_RTL == TextUtils
                .getLayoutDirectionFromLocale(Locale.getDefault())) {
            switch (orientation) {
                case 0:
                    dest = v.getX();
                    v.setX(-(dest - delta));
                    vp.translationX(dest);
                    break;
                case 90:
                    dest = v.getY();
                    v.setY(-(dest + delta));
                    vp.translationY(dest);
                    break;
                case 180:
                    dest = v.getX();
                    v.setX(-(dest + delta));
                    vp.translationX(dest);
                    break;
                case 270:
                    dest = v.getY();
                    v.setY(-(dest - delta));
                    vp.translationY(dest);
                    break;
            }
        } else {
            switch (orientation) {
                case 0:
                    dest = v.getX();
                    v.setX(dest - delta);
                    vp.translationX(dest);
                    break;
                case 90:
                    dest = v.getY();
                    v.setY(dest + delta);
                    vp.translationY(dest);
                    break;
                case 180:
                    dest = v.getX();
                    v.setX(dest + delta);
                    vp.translationX(dest);
                    break;
                case 270:
                    dest = v.getY();
                    v.setY(dest - delta);
                    vp.translationY(dest);
                    break;
            }
        }
        vp.setDuration(ANIMATION_DURATION).start();
    }

    public void animateSlideOutPreviewMenu() {
        if(TsMakeupManager.HAS_TS_MAKEUP && mTsMakeupManager.isShowMakeup()) {
            mPreviewMenuStatus = PREVIEW_MENU_NONE;
            mTsMakeupManager.dismissMakeupUI();
            closeMakeupMode(true);
            mTsMakeupManager.resetMakeupUIStatus();
        }

        if (mPreviewMenu == null)
            return;
        animateSlideOut(mPreviewMenu);
    }

    private void animateSlideOut(final View v) {
        if (v == null || mPreviewMenuStatus == PREVIEW_MENU_IN_ANIMATION)
            return;
        mPreviewMenuStatus = PREVIEW_MENU_IN_ANIMATION;

        ViewPropertyAnimator vp = v.animate();
        if (View.LAYOUT_DIRECTION_RTL == TextUtils
                .getLayoutDirectionFromLocale(Locale.getDefault())) {
            vp.translationXBy(v.getWidth()).setDuration(ANIMATION_DURATION);
        } else {
            vp.translationXBy(-v.getWidth()).setDuration(ANIMATION_DURATION);
        }
        vp.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                closeSceneMode();
                mPreviewMenuStatus = PREVIEW_MENU_NONE;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                closeSceneMode();
                mPreviewMenuStatus = PREVIEW_MENU_NONE;

            }
        });
        vp.start();
    }

    private void buttonSetEnabled(View v, boolean enable) {
        v.setEnabled(enable);
        if (v instanceof ViewGroup) {
            View v2 = ((ViewGroup) v).getChildAt(0);
            if (v2 != null)
                v2.setEnabled(enable);

        }

    }

    public boolean isOverMenu(MotionEvent ev) {
        if (mPopupStatus == POPUP_NONE
                || mPopupStatus == POPUP_IN_ANIMATION_SLIDE
                || mPopupStatus == POPUP_IN_ANIMATION_FADE)
            return false;
        if (mUI.getMenuLayout() == null)
            return false;
        Rect rec = new Rect();
        mUI.getMenuLayout().getChildAt(0).getHitRect(rec);
        return rec.contains((int) ev.getX(), (int) ev.getY());
    }

    public boolean isOverPreviewMenu(MotionEvent ev) {
        if (mPreviewMenuStatus != PREVIEW_MENU_ON)
            return false;
        if (mUI.getPreviewMenuLayout() == null)
            return false;
        Rect rec = new Rect();
        mUI.getPreviewMenuLayout().getChildAt(0).getHitRect(rec);
        if (View.LAYOUT_DIRECTION_RTL == TextUtils
                .getLayoutDirectionFromLocale(Locale.getDefault())) {
            rec.left = mUI.getRootView().getWidth() - (rec.right-rec.left);
            rec.right = mUI.getRootView().getWidth();
        }
        rec.top += (int) mUI.getPreviewMenuLayout().getY();
        rec.bottom += (int) mUI.getPreviewMenuLayout().getY();
        return rec.contains((int) ev.getX(), (int) ev.getY());
    }

    public boolean isMenuBeingShown() {
        return mPopupStatus != POPUP_NONE;
    }

    public boolean isMenuBeingAnimated() {
        return mPopupStatus == POPUP_IN_ANIMATION_SLIDE || mPopupStatus == POPUP_IN_ANIMATION_FADE;
    }

    public boolean isPreviewMenuBeingShown() {
        return mPreviewMenuStatus == PREVIEW_MENU_ON;
    }

    public boolean isPreviewMenuBeingAnimated() {
        return mPreviewMenuStatus == PREVIEW_MENU_IN_ANIMATION;
    }

    public boolean sendTouchToPreviewMenu(MotionEvent ev) {
        return mUI.sendTouchToPreviewMenu(ev);
    }

    public boolean sendTouchToMenu(MotionEvent ev) {
        return mUI.sendTouchToMenu(ev);
    }

    @Override
    public void overrideSettings(final String... keyvalues) {
        Log.v(TAG, "+ overrideSettings, " + keyvalues.length);
        for (int i = 0; i < keyvalues.length; i += 2) {
            Log.v(TAG, "key:" + keyvalues[i + 0] + " value:" + keyvalues[i + 1]);
        }
        if (mListMenu != null) {
            ListPreference pref_tnr = mPreferenceGroup.findPreference(CameraSettings.KEY_TNR_MODE);
            ListPreference pref_cds = mPreferenceGroup.findPreference(CameraSettings.KEY_CDS_MODE);

            String tnr = (pref_tnr != null) ? pref_tnr.getValue() : null;
            String cds = (pref_cds != null) ? pref_cds.getValue() : null;

            if (mPrevSavedCDS == null && cds != null) {
                mPrevSavedCDS = cds;
            }

            if ((tnr != null) && !mActivity.getString(R.string.pref_camera_tnr_default).equals(tnr)) {
                mListMenu.setPreferenceEnabled(CameraSettings.KEY_CDS_MODE, false);
                mListMenu.overrideSettings(CameraSettings.KEY_CDS_MODE, mActivity.getString(R.string.pref_camera_cds_value_off));
                mIsTNREnabled = true;
                if (!mIsCDSUpdated) {
                    if (cds != null) {
                        mPrevSavedCDS = cds;
                    }
                    mIsCDSUpdated = true;
                }
            } else if (tnr != null) {
                mListMenu.setPreferenceEnabled(CameraSettings.KEY_CDS_MODE, true);
                if (mIsTNREnabled && mPrevSavedCDS != cds) {
                    mListMenu.overrideSettings(CameraSettings.KEY_CDS_MODE, mPrevSavedCDS);
                    mIsTNREnabled = false;
                    mIsCDSUpdated = false;
                }
            }
        }

        // frankie, add start instaniate first , then call overrideSettings
        if(AGlobalConfig.config_module_PHOTO_MODULE_use_new_settings_en) {
            if(mChusSettingsFragment == null) {
                createSettingFragment();
            }
            if(mChusSettingsFragment != null) {
                mChusSettingsFragment.overrideSettings(keyvalues);
            }
            if(mChusSettingsFragment != null) {
                ListPreference pref_tnr = mPreferenceGroup.findPreference(CameraSettings.KEY_TNR_MODE);
                ListPreference pref_cds = mPreferenceGroup.findPreference(CameraSettings.KEY_CDS_MODE);

                String tnr = (pref_tnr != null) ? pref_tnr.getValue() : null;
                String cds = (pref_cds != null) ? pref_cds.getValue() : null;

                if (mPrevSavedCDS == null && cds != null) {
                    mPrevSavedCDS = cds;
                }

                if ((tnr != null) && !mActivity.getString(R.string.pref_camera_tnr_default).equals(tnr)) {
                    mChusSettingsFragment.setPreferenceEnabled(CameraSettings.KEY_CDS_MODE, false);
                    mChusSettingsFragment.overrideSettings(CameraSettings.KEY_CDS_MODE, mActivity.getString(R.string.pref_camera_cds_value_off));
                    mIsTNREnabled = true;
                    if (!mIsCDSUpdated) {
                        if (cds != null) {
                            mPrevSavedCDS = cds;
                        }
                        mIsCDSUpdated = true;
                    }
                } else if (tnr != null) {
                    mChusSettingsFragment.setPreferenceEnabled(CameraSettings.KEY_CDS_MODE, true);
                    if (mIsTNREnabled && mPrevSavedCDS != cds) {
                        mChusSettingsFragment.overrideSettings(CameraSettings.KEY_CDS_MODE, mPrevSavedCDS);
                        mIsTNREnabled = false;
                        mIsCDSUpdated = false;
                    }
                }

            }
        }
        // frankie, add end

        for (int i = 0; i < keyvalues.length; i += 2) {
            if (keyvalues[i].equals(CameraSettings.KEY_SCENE_MODE)) {
                //buttonSetEnabled(mSceneModeSwitcher, keyvalues[i + 1] == null);	// frankie, when KEY_QC_CHROMA_FLASH on, should disable sceneModeButton
            }
        }
        super.overrideSettings(keyvalues);

        if (mListMenuContainer == null || mListMenu == null) { // frankie, add
            initializePopup();
        }
        mListMenu.overrideSettings(keyvalues);

        Log.v(TAG, "- overrideSettings. ");
    }

    /*protected*/private void initializePopup() {
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //ListMenu popup1 = (ListMenu) inflater.inflate(R.layout.list_menu_1, null, false);
        ListMenu popup1 = null;
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.list_menu_1, null, false);
        popup1 = (ListMenu)viewGroup.findViewById(R.id.list_menu_1);
        //popup1.setSettingChangedListener(this);
        popup1.setSettingChangedListener(mListMenuListener);	// frankie, instead of set this as callback

        String[] keys = mOtherKeys1;
        if (mActivity.isDeveloperMenuEnabled()) {
            keys = mOtherKeys2;
        }
        //keys = mOtherKeys2;	// for debug
        popup1.initialize(mPreferenceGroup, keys);
        if (mActivity.isSecureCamera()) {
            // Prevent location preference from getting changed in secure camera
            // mode
            popup1.setPreferenceEnabled(CameraSettings.KEY_RECORD_LOCATION, false);
        }
        mListMenu = popup1;
        mListMenuContainer = viewGroup;

        ListPreference pref = mPreferenceGroup.findPreference(CameraSettings.KEY_SCENE_MODE);
        //updateFilterModeIcon(pref, mPreferenceGroup.findPreference(CameraSettings.KEY_CAMERA_HDR)); // frankie, not necessary
        String sceneMode = (pref != null) ? pref.getValue() : null;

        pref = mPreferenceGroup.findPreference(CameraSettings.KEY_FACE_DETECTION);
        String faceDetection = (pref != null) ? pref.getValue() : null;

        pref = mPreferenceGroup.findPreference(CameraSettings.KEY_ZSL);
        String zsl = (pref != null) ? pref.getValue() : null;

        pref = mPreferenceGroup.findPreference(CameraSettings.KEY_AUTO_HDR);
        String autohdr = (pref != null) ? pref.getValue() : null;

        if (((sceneMode != null) && !Camera.Parameters.SCENE_MODE_AUTO.equals(sceneMode))
                || ((autohdr != null) && autohdr.equals("enable"))) {
            popup1.setPreferenceEnabled(CameraSettings.KEY_FOCUS_MODE, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_AUTOEXPOSURE, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_TOUCH_AF_AEC, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_SATURATION, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_CONTRAST, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_SHARPNESS, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_COLOR_EFFECT, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_FLASH_MODE, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_WHITE_BALANCE, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_EXPOSURE, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_QC_CHROMA_FLASH, false);
        }
        if ((autohdr != null) && autohdr.equals("enable")) {
            popup1.setPreferenceEnabled(CameraSettings.KEY_SCENE_MODE, false);
        }
        if ((zsl != null) && Camera.Parameters.ZSL_ON.equals(zsl)) {
            popup1.setPreferenceEnabled(CameraSettings.KEY_FOCUS_MODE, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_MANUAL_EXPOSURE, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_MANUAL_WB, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_MANUAL_FOCUS, false);
        }
        if ((faceDetection != null) && !Camera.Parameters.FACE_DETECTION_ON.equals(faceDetection)) {
            popup1.setPreferenceEnabled(CameraSettings.KEY_FACE_RECOGNITION, false);
        }
        popup1.setPreferenceEnabled(CameraSettings.KEY_ZSL, !mUI.isCountingDown());

        pref = mPreferenceGroup.findPreference(CameraSettings.KEY_ADVANCED_FEATURES);
        String advancedFeatures = (pref != null) ? pref.getValue() : null;

        String ubiFocusOn = mActivity.getString(R.string.pref_camera_advanced_feature_value_ubifocus_on);
        String reFocusOn = mActivity.getString(R.string.pref_camera_advanced_feature_value_refocus_on);
        String chromaFlashOn = mActivity.getString(R.string.pref_camera_advanced_feature_value_chromaflash_on);
        String optiZoomOn = mActivity.getString(R.string.pref_camera_advanced_feature_value_optizoom_on);
        String fssrOn = mActivity.getString(R.string.pref_camera_advanced_feature_value_FSSR_on);
        String truePortraitOn = mActivity.getString(R.string.pref_camera_advanced_feature_value_trueportrait_on);
        String multiTouchFocusOn = mActivity.getString(R.string.pref_camera_advanced_feature_value_multi_touch_focus_on);

        if ((zsl != null) && Camera.Parameters.ZSL_OFF.equals(zsl)) {
            popup1.overrideSettings(
                    CameraSettings.KEY_ADVANCED_FEATURES,mActivity.getString(R.string.pref_camera_advanced_feature_default));

            popup1.setPreferenceEnabled(CameraSettings.KEY_ADVANCED_FEATURES, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_INSTANT_CAPTURE, false);

            //if(!TsMakeupManager.HAS_TS_MAKEUP) {
            //    if (mHdrSwitcher.getVisibility() == View.VISIBLE) {
            //        buttonSetEnabled(mHdrSwitcher, true);
            //    }
            //}
        } else {
            if ((advancedFeatures != null) &&
                    (advancedFeatures.equals(ubiFocusOn) ||
                            advancedFeatures.equals(chromaFlashOn) ||
                            advancedFeatures.equals(reFocusOn) ||
                            advancedFeatures.equals(optiZoomOn) ||
                            advancedFeatures.equals(fssrOn) ||
                            advancedFeatures.equals(truePortraitOn) ||
                            advancedFeatures.equals(multiTouchFocusOn))) {
                popup1.setPreferenceEnabled(CameraSettings.KEY_FOCUS_MODE, false);
                popup1.setPreferenceEnabled(CameraSettings.KEY_FLASH_MODE, false);
                popup1.setPreferenceEnabled(CameraSettings.KEY_AE_BRACKET_HDR, false);
                popup1.setPreferenceEnabled(CameraSettings.KEY_REDEYE_REDUCTION, false);
                popup1.setPreferenceEnabled(CameraSettings.KEY_EXPOSURE, false);
                popup1.setPreferenceEnabled(CameraSettings.KEY_COLOR_EFFECT, false);
                popup1.setPreferenceEnabled(CameraSettings.KEY_TOUCH_AF_AEC, false);
                popup1.setPreferenceEnabled(CameraSettings.KEY_SCENE_MODE, false);
                popup1.setPreferenceEnabled(CameraSettings.KEY_INSTANT_CAPTURE, false);

                // frankie, should shut hdr settings , disable mHdrSwitcher
                setPreference(CameraSettings.KEY_CAMERA_HDR, mSettingOff);

            } else {

            }
        }

        if (mListener != null) {
            mListener.onSharedPreferenceChanged();
        }
    }

    public void toggleCamera(){
        Log.v("aaa", "++++++++++++++++++++++initSwitchItem_camera_id, camera switch click! ");
        IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_CAMERA_ID);
        if (pref == null) {
            return;
        }
        mUI.hideUI();
        int index = pref.findIndexOfValue(pref.getValue());
        CharSequence[] values = pref.getEntryValues();
        index = (index + 1) % values.length;
        pref.setValueIndex(index);
        mFrontBackSwitcher.setEnabled(false);
        mListener.onCameraPickerClicked(index);
        reloadPreference(pref);
        onSettingChanged(pref);
    }

    /*public*/private void initSwitchItem_camera_id(final String prefKey, View switcher) {
        if(switcher == null){
            return;
        }
        final IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(prefKey);
        if (pref == null)
            return;

        int[] iconIds = pref.getLargeIconIds();
        int resid = -1;
        int index = pref.findIndexOfValue(pref.getValue());
        Log.v(TAG, "initSwitchItem_camera_id:" + index + ", getUseSingleIcon:" + pref.getUseSingleIcon());
        if (!pref.getUseSingleIcon() && iconIds != null) {
            // Each entry has a corresponding icon.
            resid = iconIds[index];
        } else {
            // The preference only has a single icon to represent it.
            resid = pref.getSingleIcon();
        }
        //((ImageView) switcher).setImageResource(resid);
        switcher.setVisibility(View.VISIBLE);
        mPreferences.add(pref);
        mPreferenceMap.put(pref, switcher);
        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "initSwitchItem_camera_id, camera switch click! ");
                IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(prefKey);
                if (pref == null) {
                    return;
                }
                //if (prefKey.equals(CameraSettings.KEY_CAMERA_ID)) {
                // Hide the camera control while switching the camera.
                // The camera control will be added back when
                // onCameraPickerClicked is completed
                mUI.hideUI();
                //}
                int index = pref.findIndexOfValue(pref.getValue());
                CharSequence[] values = pref.getEntryValues();
                index = (index + 1) % values.length;
                pref.setValueIndex(index);
                //((ImageView) v).setImageResource(((IconListPreference) pref).getLargeIconIds()[index]);

                //if (prefKey.equals(CameraSettings.KEY_CAMERA_ID)) { // frankie, camera_id specific
                mFrontBackSwitcher.setEnabled(false);
                mListener.onCameraPickerClicked(index);
                //}
                reloadPreference(pref);
                onSettingChanged(pref);
            }
        });
    }

    private int setPreferenceIndex(String key_, int index) {
        Log.v(TAG, "setPreferenceIndex:" + key_ + " index:" + index);
        IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(key_);
        if (pref == null) {
            return -1;
        }
        CharSequence[] values = pref.getEntryValues();
        Log.v(TAG, "  index/length: " + index + " / " + values.length);
        if(index < 0 || index >= values.length) {
            return -1;
        }
        pref.setValueIndex(index);

        reloadPreference(pref);
        onSettingChanged(pref);

        return ((IconListPreference) pref).getLargeIconIds()[index];
    }

    /*public*/private void initMakeupModeButton(View button) {
        if(!TsMakeupManager.HAS_TS_MAKEUP) {
            return;
        }
        button.setVisibility(View.INVISIBLE);
        final IconListPreference pref = (IconListPreference) mPreferenceGroup
                .findPreference(CameraSettings.KEY_TS_MAKEUP_UILABLE);
        if (pref == null)
            return;

        int[] iconIds = pref.getLargeIconIds();
        int resid = -1;
        int index = pref.findIndexOfValue(pref.getValue());
        if (!pref.getUseSingleIcon() && iconIds != null) {
            // Each entry has a corresponding icon.
            resid = iconIds[index];
        } else {
            // The preference only has a single icon to represent it.
            resid = pref.getSingleIcon();
        }
        ImageView iv = (ImageView) mTsMakeupSwitcher;
        iv.setImageResource(resid);

        button.setVisibility(View.VISIBLE);

        String makeupOn = pref.getValue();
        Log.d(TAG, "ToroPhotoMenu.initMakeupModeButton():current init makeupOn is " + makeupOn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPreference faceDetectPref = mPreferenceGroup.findPreference(CameraSettings.KEY_FACE_DETECTION);
                String faceDetection = (faceDetectPref != null) ? faceDetectPref.getValue() : null;
                Log.d(TAG, "initMakeupModeButton().onClick(): faceDetection is " + faceDetection);
                if ((faceDetection != null) && Camera.Parameters.FACE_DETECTION_OFF.equals(faceDetection)) {
                    showAlertDialog(faceDetectPref);
                } else {
                    toggleMakeupSettings();
                }
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    // frankie, add
    private void initCountdownButton(View button) {
        if(false) {
            return;
        }

        button.setVisibility(View.INVISIBLE);
        final IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_TIMER);
        if (pref == null)
            return;
        if(true) { // frankie, count down timer have no icon array
            int[] iconIds = pref.getLargeIconIds();
            int resid = -1;
            int index = -1;
            do {
                index = pref.findIndexOfValue(pref.getValue());
                Log.v(TAG, "index:" + index + " pref.getValue():" + pref.getValue());
                if (index == -1) {
                    pref.setValueIndex(2);
                    index = pref.findIndexOfValue(pref.getValue());
                }
            } while(index == -1);

            if (!pref.getUseSingleIcon() && iconIds != null) {
                // Each entry has a corresponding icon.
                resid = iconIds[index];
            } else {
                // The preference only has a single icon to represent it.
                resid = pref.getSingleIcon();
            }
            ((ImageView) button).setImageResource(resid);
        }
        button.setVisibility(View.VISIBLE);
    }

    private void showPopupWindow_countdown(final View view) {
        View contentView = LayoutInflater.from(mActivity).inflate(R.layout.popup_countdown_timer_1, null);
        final PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        int[] ll_3 = new int[]{R.id.llayout_01, R.id.llayout_02, R.id.llayout_03};
        View.OnClickListener onClickListener_2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_ = v.getId();
                int index = 0;
                if(id_ == R.id.llayout_01) {
                    index = 0;
                } else if(id_ == R.id.llayout_02) {
                    index = 1;
                } else if(id_ == R.id.llayout_03) {
                    index = 2;
                }
                int selectResId = setPreferenceIndex(CameraSettings.KEY_TIMER, index);
                Log.v(TAG, "selectResId:" + selectResId);
                if(selectResId > 0) {
                    ((ImageView) view).setImageResource(selectResId);
                }
                popupWindow.dismiss();
            }
        };
        for(int i=0;i<ll_3.length;i++) {
            LinearLayout iv = (LinearLayout)contentView.findViewById(ll_3[i]);
            if(iv != null) {
                iv.setOnClickListener(onClickListener_2);
            }
        }

        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "onTouch : ");
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.chus_popup_bg_center));
        int[] locationOnScreen = new int[2];
        view.getLocationOnScreen(locationOnScreen);
        Log.v(TAG, "locationOnScreen: x=" + locationOnScreen[0] + " y=" + locationOnScreen[1]);
        //popupWindow.showAsDropDown(view);
        locationOnScreen[0] -= 180;
        locationOnScreen[1] += 100;
        popupWindow.showAtLocation(view, Gravity.TOP | Gravity.START, locationOnScreen[0], locationOnScreen[1]);
    }

    private void animFlashLightLyt(){
        mFlashLightLyt.setVisibility(View.VISIBLE);
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mFlashLightLyt, "translationX", -300f, 0f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mTopMenu, "alpha", 1.0f, 0f);
        set.play(animator1).with(animator2);
        set.setDuration(100);
        set.start();
    }
    private void animFlashLightLytFadout(){
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mFlashLightLyt, "alpha", 1.0f, 0f);
        animator1.setDuration(200);
        animator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.v(TAG, "mFlashLightLyt alpha = " + mFlashLightLyt.getAlpha());
                mFlashLightLyt.setAlpha(1.0f);
                mFlashLightLyt.setVisibility(View.GONE);

                mTopMenu.setVisibility(View.VISIBLE);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(mTopMenu, "alpha", 0.0f, 1.0f);
                animator2.setDuration(200);
                animator2.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Log.v(TAG, "mTopMenu alpha = " + mTopMenu.getAlpha());
                        mTopMenu.setAlpha(1.0f);
                    }
                });
                animator2.start();
            }
        });
        animator1.start();
    }

    private void setPreferenceTouch2TakePhoto(int value) {
        IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_CHUS_TOUCH_TO_TAKE_PHOTO);
        if (pref == null)
            return;
        String touch_value = pref.getValue();
        if(value == 0 && !touch_value.equals("disable")) {
            pref.setValue("disable");
        } else if(value > 0 && !touch_value.equals("enable")) {
            pref.setValue("enable");
        }
        reloadPreference(pref);
        onSettingChanged(pref);
    }
    private void updatePopupImageViewTouch2TakePhoto(ImageView view) {
        IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_CHUS_TOUCH_TO_TAKE_PHOTO);
        if (pref == null) {
            return;
        }
        String value = pref.getValue();
        if(value != null) {
            if (value.equals("disable")) { // hdr off
                view.setImageResource(R.drawable.chus_ic_touch_take_photo_off);
            } else if (value.equals("enable")) {
                view.setImageResource(R.drawable.chus_ic_touch_take_photo_on);//chus_ic_settings_touch_take_photo_on
            } else {
                view.setImageResource(R.drawable.chus_ic_touch_take_photo_off);//chus_ic_settings_touch_take_photo_off
            }
        }
    }

    private void setPreferenceSelfieMirror(int value) {
        IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_SELFIE_MIRROR);
        if (pref == null)
            return;
        String oldValue = pref.getValue();
        if(value == 0 && !oldValue.equals("disable")) {
            pref.setValue("disable");
        } else if(value > 0 && !oldValue.equals("enable")) {
            pref.setValue("enable");
        }
        reloadPreference(pref);
        onSettingChanged(pref);
    }

    private void setPreferenceFaceBeauty(int value) {
        IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_CHUS_FACE_BEAUTY);
        if (pref == null)
            return;
        String oldValue = pref.getValue();
        if(value == 0 && !oldValue.equals("disable")) {
            pref.setValue("disable");
        } else if(value > 0 && !oldValue.equals("enable")) {
            pref.setValue("enable");
        }
        reloadPreference(pref);
        onSettingChanged(pref);
    }

    private void updatePopupImageViewSelfieMirror(ImageView view) {
        IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_SELFIE_MIRROR);
        if (pref == null) {
            return;
        }
        String value = pref.getValue();
        if(value != null) {
            if (value.equals("disable")) {
                view.setImageResource(R.drawable.chus_ic_mirror_disable);//chus_ic_settings_photo_mirror_off
            } else if (value.equals("enable")) {
                view.setImageResource(R.drawable.chus_ic_mirror_enable);//chus_ic_settings_photo_mirror_on
            } else {
                view.setImageResource(R.drawable.chus_ic_mirror_disable);
            }
        }
    }

    /**
     * add
     * @param view
     */
    private void updatePopupImageViewFaceBeauty(ImageView view) {
        IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_CHUS_FACE_BEAUTY);
        if (pref == null) {
            return;
        }
        String value = pref.getValue();
        if(value != null) {
            if (value.equals("disable")) {
                view.setImageResource(R.drawable.chus_ic_settings_face_beauty_off);
                isFaceBeautyOpen = false;
                ToastUtils.showToast(mActivity,mActivity.getString(R.string.chus_face_beauty_disable_hint));
            } else if (value.equals("enable")) {
                view.setImageResource(R.drawable.chus_ic_settings_face_beauty_on);
                isFaceBeautyOpen = true;
                ToastUtils.showToast(mActivity,mActivity.getString(R.string.chus_face_beauty_enable_hint));
            } else {
                view.setImageResource(R.drawable.chus_ic_settings_face_beauty_off);
                isFaceBeautyOpen = false;
            }
            refreshModeTv();
        }
    }

    private void updatePopupImageViewHDR(ImageView view) {
        IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_CAMERA_HDR);
        if (pref == null) {
            return;
        }
        String value = pref.getValue();
        if(value != null) {
            if (value.equals("off")) {
                isHDROpen = false;
                ToastUtils.showToast(mActivity,mActivity.getString(R.string.chus_hdr_disable_hint));
            } else if (value.equals("on")) {
                isHDROpen = true;
                ToastUtils.showToast(mActivity,mActivity.getString(R.string.chus_hdr_enable_hint));
            } else {
                isHDROpen = false;
            }
            refreshModeTv();
        }
    }
    private boolean isHDROpen = false;
    private boolean isFaceBeautyOpen = false;
    private void refreshModeTv(){
        StringBuilder content = new StringBuilder();
        if(isHDROpen && isFaceBeautyOpen){
            content.delete(0,content.length());
            content.append(mActivity.getString(R.string.chus_hdr_enable_hint));
            content.append(" | ");
            content.append(mActivity.getString(R.string.chus_face_beauty_enable_hint));
        }else if(isHDROpen){
            content.delete(0,content.length());
            content.append(mActivity.getString(R.string.chus_hdr_enable_hint));
        }else if(isFaceBeautyOpen){
            content.delete(0,content.length());
            content.append(mActivity.getString(R.string.chus_face_beauty_enable_hint));
        }
        modeTv.setText(content);
        modeTv.setVisibility(View.GONE);
    }

    public void showPopupWindow_more_settings(final View view) {
        ViewGroup contentView = (ViewGroup)LayoutInflater.from(mActivity).inflate(R.layout.popup_more_settings_1, null);
        final PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        final ImageView touchTakePhotoImageView = (ImageView)contentView.findViewById(R.id.iv01);
        final ImageView faceBeautyImageView = (ImageView)contentView.findViewById(R.id.iv02);
        final ImageView mirrorView = (ImageView)contentView.findViewById(R.id.iv03);
        final ImageView moremageView = (ImageView)contentView.findViewById(R.id.iv04);

        IconListPreference selfie_mirror_pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_SELFIE_MIRROR);
        if (selfie_mirror_pref == null) {
            return;
        }
        IconListPreference touch_2take_photo_pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_CHUS_TOUCH_TO_TAKE_PHOTO);
        if (touch_2take_photo_pref == null) {
            return;
        }

        IconListPreference face_beauty_pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_CHUS_FACE_BEAUTY);
        if (face_beauty_pref == null) {
            return;
        }

        final String faceBeautyValue = face_beauty_pref.getValue();
        updatePopupImageViewFaceBeauty(faceBeautyImageView);

        final String selfieMirrorValue = selfie_mirror_pref.getValue();
        updatePopupImageViewSelfieMirror(mirrorView);

        final String touch2TakePhotoValue = touch_2take_photo_pref.getValue();
        updatePopupImageViewTouch2TakePhoto(touchTakePhotoImageView);

        int[] ll_3 = new int[]{R.id.llayout_01, R.id.llayout_02, R.id.llayout_03};
        int[] ll_4 = new int[]{R.id.llayout_01, R.id.llayout_02, R.id.llayout_03, R.id.llayout_04};
        int[] ll_ids = ll_3;
        if (contentView.getChildCount() > 3) {
            ll_ids = ll_4;
        }
        View.OnClickListener onClickListener_2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                popupWindow.dismiss();
                if(id == R.id.llayout_01) { // touch
                    if(touch2TakePhotoValue != null) {
                        Log.v(TAG, "touch2TakePhotoValue : " + touch2TakePhotoValue);
                        if (touch2TakePhotoValue.equals("disable")) {
                            setPreferenceTouch2TakePhoto(1);
                        } else if (touch2TakePhotoValue.equals("enable")) {
                            setPreferenceTouch2TakePhoto(0);
                        } else {
                            setPreferenceTouch2TakePhoto(1);
                        }
                    }
                    updatePopupImageViewTouch2TakePhoto(touchTakePhotoImageView);
                } else if(id == R.id.llayout_02) { // face beauty
                    if(faceBeautyValue != null) {
                        Log.v(TAG, "faceBeautyValue : " + faceBeautyValue);
                        if (faceBeautyValue.equals("disable")) {
                            setPreferenceFaceBeauty(1);
                        } else if (faceBeautyValue.equals("enable")) {
                            setPreferenceFaceBeauty(0);
                        } else {
                            setPreferenceFaceBeauty(1);
                        }
                    }
                    updatePopupImageViewFaceBeauty(faceBeautyImageView);
                } else if(id == R.id.llayout_03) { // mirror
                    if(selfieMirrorValue != null) {
                        Log.v(TAG, "selfieMirrorValue : " + selfieMirrorValue);
                        if (selfieMirrorValue.equals("disable")) {
                            setPreferenceSelfieMirror(1);
                        } else if (selfieMirrorValue.equals("enable")) {
                            setPreferenceSelfieMirror(0);
                        } else {
                            setPreferenceSelfieMirror(1);
                        }
                    }
                    updatePopupImageViewSelfieMirror(mirrorView);
                } else if(id == R.id.llayout_04) { // more
                    if(AGlobalConfig.config_module_PHOTO_MODULE_use_new_settings_en) {
                        showSettingFragment();
                    } else {
                        openFirstLevel();
                    }
                }
            }
        };
        for (int i = 0; i < ll_ids.length; i++) {
            LinearLayout ll_ = (LinearLayout) contentView.findViewById(ll_ids[i]);
            if (ll_ != null) {
                ll_.setOnClickListener(onClickListener_2);
            }
        }
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "onTouch : ");
                return false;
            }
        });
        //popupWindow.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.chus_popup_bg_right));
        popupWindow.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.chus_popup_bg_right_2));
        int[] locationOnScreen = new int[2];
        view.getLocationOnScreen(locationOnScreen);
        Log.v(TAG, "locationOnScreen: x=" + locationOnScreen[0] + " y=" + locationOnScreen[1]);
        //popupWindow.showAsDropDown(view);
        locationOnScreen[0] -= 450;
        locationOnScreen[1] += 100;
        popupWindow.showAtLocation(view, Gravity.TOP | Gravity.START, locationOnScreen[0], locationOnScreen[1]);
    }

    private void setPreferenceHdr(int value) {
        IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_CAMERA_HDR);
        if (pref == null)
            return;
        String oldValue = pref.getValue();
        if(value == 0 && !oldValue.equals(mSettingOff)) {
            pref.setValue(mSettingOff);
        } else if(value > 0 && !oldValue.equals(mSettingOn)) {
            pref.setValue(mSettingOn);
        }
        reloadPreference(pref);
        onSettingChanged(pref);
    }
    private void updatePopupImageViewHdr(ImageView hdrView) {
        IconListPreference hdr_pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_CAMERA_HDR);
        if (hdr_pref == null) {
            return;
        }
        String value = hdr_pref.getValue();
        if(value != null) {
            if (value.equals(mSettingOff)) { // hdr off
                hdrView.setImageResource(R.drawable.chus_ic_settings_hdr_off);
            } else if (value.equals(mSettingOn)) {
                hdrView.setImageResource(R.drawable.chus_ic_settings_hdr_on);
            } else {
                hdrView.setImageResource(R.drawable.chus_ic_settings_hdr_off);
            }
        }
    }
    public void showPopupWindow_more_settings_back(final View view) {
        ViewGroup contentView = (ViewGroup)LayoutInflater.from(mActivity).inflate(R.layout.popup_more_settings_back, null);
        final PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        final ImageView touchTakePhotoImageView = (ImageView)contentView.findViewById(R.id.iv01);
        final ImageView hdrImageView = (ImageView)contentView.findViewById(R.id.iv02);
        final ImageView moremageView = (ImageView)contentView.findViewById(R.id.iv03);

        IconListPreference hdr_pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_CAMERA_HDR);
        if (hdr_pref == null) {
            return;
        }
        IconListPreference touch_2take_photo_pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_CHUS_TOUCH_TO_TAKE_PHOTO);
        if (touch_2take_photo_pref == null) {
            return;
        }

        final String hdrValue = hdr_pref.getValue();
        updatePopupImageViewHdr(hdrImageView);

        final String touch2TakePhotoValue = touch_2take_photo_pref.getValue();
        updatePopupImageViewTouch2TakePhoto(touchTakePhotoImageView);

        int[] ll_3 = new int[]{R.id.llayout_01, R.id.llayout_02, R.id.llayout_03};
        int[] ll_4 = new int[]{R.id.llayout_01, R.id.llayout_02, R.id.llayout_03, R.id.llayout_04};
        int[] ll_ids = ll_3;
        if (contentView.getChildCount() > 3) {
            ll_ids = ll_4;
        }
        View.OnClickListener onClickListener_2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                popupWindow.dismiss();
                if(id == R.id.llayout_01) { // touch
                    if(touch2TakePhotoValue != null) {
                        Log.v(TAG, "touch2TakePhotoValue : " + touch2TakePhotoValue);
                        if (touch2TakePhotoValue.equals("disable")) {
                            setPreferenceTouch2TakePhoto(1);
                        } else if (touch2TakePhotoValue.equals("enable")) {
                            setPreferenceTouch2TakePhoto(0);
                        } else {
                            setPreferenceTouch2TakePhoto(1);
                        }
                    }
                    updatePopupImageViewTouch2TakePhoto(touchTakePhotoImageView);
                } else if(id == R.id.llayout_02) { // hdr
                    if(hdrValue != null) {
                        Log.v(TAG, "hdrValue : " + hdrValue);
                        if (hdrValue.equals(mSettingOff)) { // hdr off
                            setPreferenceHdr(1); // open
                        } else if (hdrValue.equals(mSettingOn)) {
                            setPreferenceHdr(0); // close
                        } else {
                            setPreferenceHdr(1); // open
                        }
                    }
                    updatePopupImageViewHdr(hdrImageView);
                } else if(id == R.id.llayout_03) { // more
                    if(AGlobalConfig.config_module_PHOTO_MODULE_use_new_settings_en) {
                        showSettingFragment();
                    } else {
                        openFirstLevel();
                    }
                } else if(id == R.id.llayout_04) {
                    if(AGlobalConfig.config_module_PHOTO_MODULE_use_new_settings_en) {
                        showSettingFragment();
                    } else {
                        openFirstLevel();
                    }
                }
            }
        };
        for (int i = 0; i < ll_ids.length; i++) {
            LinearLayout ll_ = (LinearLayout) contentView.findViewById(ll_ids[i]);
            if (ll_ != null) {
                ll_.setOnClickListener(onClickListener_2);
            }
        }
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "onTouch : ");
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.chus_popup_bg_right));
        //popupWindow.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.chus_popup_bg_right_2));
        int[] locationOnScreen = new int[2];
        view.getLocationOnScreen(locationOnScreen);
        Log.v(TAG, "locationOnScreen: x=" + locationOnScreen[0] + " y=" + locationOnScreen[1]);
        //popupWindow.showAsDropDown(view);
        locationOnScreen[0] -= 300;
        locationOnScreen[1] += 100;
        popupWindow.showAtLocation(view, Gravity.TOP | Gravity.START, locationOnScreen[0], locationOnScreen[1]);
    }
    // frankie, add end
    ///////////////////////////////////////////////////////////////////////////////////////
    private SettingsFragment2 mSettingsFragment2 = null;
    private SettingsFragment2.Listener mSettingsFragment2_listener = new SettingsFragment2.Listener() {
        @Override
        public void onSettingChanged(ListPreference pref) { /* do nothing */ }
        @Override
        public void onPreferenceClicked(ListPreference pref, int y) { /* do nothing */ }
        @Override
        public void onListMenuTouched() { /* do nothing */ }
        @Override
        public void onListPrefChanged(ListPreference pref) {
            ToroPhotoMenu.this.onSettingChanged(pref);
        }
    };

    private void initializeSettingsFragment2() {
        Log.v(TAG, "+ initializeSettingsFragment2");
        SettingsFragment2 popup1 = mChusSettingsFragment;
        if(popup1 == null) {
            Log.v(TAG, "- initializeSettingsFragment2 not created at this time!");
            return ;
        }
        //
        popup1.setSettingChangedListener(mSettingsFragment2_listener);

        String[] keys = mOtherKeys1;
        if (mActivity.isDeveloperMenuEnabled()) {
            keys = mOtherKeys2;
        }
        //keys = mOtherKeys2;	// for debug
        popup1.initialize(mPreferenceGroup, keys,mIsFrontCamera);
        if (mActivity.isSecureCamera()) {
            // Prevent location preference from getting changed in secure camera
            // mode
            popup1.setPreferenceEnabled(CameraSettings.KEY_RECORD_LOCATION, false);
        }
//		mListMenu = popup1;
//		mListMenuContainer = viewGroup;

        // following is for change the items enabled state
        ListPreference pref = mPreferenceGroup.findPreference(CameraSettings.KEY_SCENE_MODE);
        //updateFilterModeIcon(pref, mPreferenceGroup.findPreference(CameraSettings.KEY_CAMERA_HDR)); // frankie, not necessary
        String sceneMode = (pref != null) ? pref.getValue() : null;

        pref = mPreferenceGroup.findPreference(CameraSettings.KEY_FACE_DETECTION);
        String faceDetection = (pref != null) ? pref.getValue() : null;

        pref = mPreferenceGroup.findPreference(CameraSettings.KEY_ZSL);
        String zsl = (pref != null) ? pref.getValue() : null;

        pref = mPreferenceGroup.findPreference(CameraSettings.KEY_AUTO_HDR);
        String autohdr = (pref != null) ? pref.getValue() : null;

        if (((sceneMode != null) && !Camera.Parameters.SCENE_MODE_AUTO.equals(sceneMode))
                || ((autohdr != null) && autohdr.equals("enable"))) {
            popup1.setPreferenceEnabled(CameraSettings.KEY_FOCUS_MODE, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_AUTOEXPOSURE, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_TOUCH_AF_AEC, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_SATURATION, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_CONTRAST, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_SHARPNESS, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_COLOR_EFFECT, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_FLASH_MODE, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_WHITE_BALANCE, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_EXPOSURE, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_QC_CHROMA_FLASH, false);
        }
        if ((autohdr != null) && autohdr.equals("enable")) {
            popup1.setPreferenceEnabled(CameraSettings.KEY_SCENE_MODE, false);
        }
        if ((zsl != null) && Camera.Parameters.ZSL_ON.equals(zsl)) {
            popup1.setPreferenceEnabled(CameraSettings.KEY_FOCUS_MODE, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_MANUAL_EXPOSURE, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_MANUAL_WB, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_MANUAL_FOCUS, false);
        }
        if ((faceDetection != null) && !Camera.Parameters.FACE_DETECTION_ON.equals(faceDetection)) {
            popup1.setPreferenceEnabled(CameraSettings.KEY_FACE_RECOGNITION, false);
        }
        popup1.setPreferenceEnabled(CameraSettings.KEY_ZSL, !mUI.isCountingDown());

        pref = mPreferenceGroup.findPreference(CameraSettings.KEY_ADVANCED_FEATURES);
        String advancedFeatures = (pref != null) ? pref.getValue() : null;

        String ubiFocusOn = mActivity.getString(R.string.pref_camera_advanced_feature_value_ubifocus_on);
        String reFocusOn = mActivity.getString(R.string.pref_camera_advanced_feature_value_refocus_on);
        String chromaFlashOn = mActivity.getString(R.string.pref_camera_advanced_feature_value_chromaflash_on);
        String optiZoomOn = mActivity.getString(R.string.pref_camera_advanced_feature_value_optizoom_on);
        String fssrOn = mActivity.getString(R.string.pref_camera_advanced_feature_value_FSSR_on);
        String truePortraitOn = mActivity.getString(R.string.pref_camera_advanced_feature_value_trueportrait_on);
        String multiTouchFocusOn = mActivity.getString(R.string.pref_camera_advanced_feature_value_multi_touch_focus_on);

        if ((zsl != null) && Camera.Parameters.ZSL_OFF.equals(zsl)) {
            popup1.overrideSettings(
                    CameraSettings.KEY_ADVANCED_FEATURES,mActivity.getString(R.string.pref_camera_advanced_feature_default));

            popup1.setPreferenceEnabled(CameraSettings.KEY_ADVANCED_FEATURES, false);
            popup1.setPreferenceEnabled(CameraSettings.KEY_INSTANT_CAPTURE, false);

            //if(!TsMakeupManager.HAS_TS_MAKEUP) {
            //	  if (mHdrSwitcher.getVisibility() == View.VISIBLE) {
            //		  buttonSetEnabled(mHdrSwitcher, true);
            //	  }
            //}
        } else {
            if ((advancedFeatures != null) &&
                    (advancedFeatures.equals(ubiFocusOn) ||
                            advancedFeatures.equals(chromaFlashOn) ||
                            advancedFeatures.equals(reFocusOn) ||
                            advancedFeatures.equals(optiZoomOn) ||
                            advancedFeatures.equals(fssrOn) ||
                            advancedFeatures.equals(truePortraitOn) ||
                            advancedFeatures.equals(multiTouchFocusOn))) {
                popup1.setPreferenceEnabled(CameraSettings.KEY_FOCUS_MODE, false);
                popup1.setPreferenceEnabled(CameraSettings.KEY_FLASH_MODE, false);
                popup1.setPreferenceEnabled(CameraSettings.KEY_AE_BRACKET_HDR, false);
                popup1.setPreferenceEnabled(CameraSettings.KEY_REDEYE_REDUCTION, false);
                popup1.setPreferenceEnabled(CameraSettings.KEY_EXPOSURE, false);
                popup1.setPreferenceEnabled(CameraSettings.KEY_COLOR_EFFECT, false);
                popup1.setPreferenceEnabled(CameraSettings.KEY_TOUCH_AF_AEC, false);
                popup1.setPreferenceEnabled(CameraSettings.KEY_SCENE_MODE, false);
                popup1.setPreferenceEnabled(CameraSettings.KEY_INSTANT_CAPTURE, false);

                // frankie, should shut hdr settings , disable mHdrSwitcher
                setPreference(CameraSettings.KEY_CAMERA_HDR, mSettingOff);
                //if(!TsMakeupManager.HAS_TS_MAKEUP) {
                //	  if (mHdrSwitcher.getVisibility() == View.VISIBLE) {
                //		  buttonSetEnabled(mHdrSwitcher, false);
                //	  }
                //}
            } else {
                //if(!TsMakeupManager.HAS_TS_MAKEUP) {
                //	  if (mHdrSwitcher.getVisibility() == View.VISIBLE) {
                //		  buttonSetEnabled(mHdrSwitcher, true);
                //	  }
                //}
            }
        }

        // following is for change the items enabled state !!!
        if (mListener != null) {
            mListener.onSharedPreferenceChanged();
        }
        Log.v(TAG, "- initializeSettingsFragment2 done");
    }

    private SettingsFragment2.SettingsFragment2LifeCycleCallback __lifeCycleCallback = new SettingsFragment2.SettingsFragment2LifeCycleCallback() {
        @Override
        public void onCreate(PreferenceFragment preferenceFragment) {
            if(mSettingsFragment2 == preferenceFragment) {
                mChusSettingsFragment = (SettingsFragment2) preferenceFragment; // save
                // we treat  SettingsFragment2 as ListMenu , so do the same as openFirstLevel() ->  initializePopup() ->
                initializeSettingsFragment2();
            }
        }
        @Override
        public void onDestroy() {
            mChusSettingsFragment = null;
        }

        @Override
        public void setTitle(String titleTxt) {
            if(titleTxt != null){
                titleTv.setText(titleTxt);
            }
        }
    };
    private void createSettingFragment() {
        if(mSettingsFragment2 == null) {
            Bundle fragment_args = new Bundle();
            //PrefsFragment1 fragment = new PrefsFragment1();

            Log.v(SettingsFragment2.TAG, "+++ SettingsFragment2 start ");
//             SettingsFragment2 settingsFragment = new SettingsFragment2(mActivity, __lifeCycleCallback);
            SettingsFragment2 settingsFragment = new SettingsFragment2();
            settingsFragment.setArguments(fragment_args);
            settingsFragment.setLifeCycleCallback(__lifeCycleCallback);

            mFragmentManager.beginTransaction()
                    .add(R.id.prefs_container,settingsFragment,SettingsFragment2.FRAGMENT_TAG_4PHOTO)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
            Log.v(SettingsFragment2.TAG, "--- SettingsFragment2 commit done!");
            mSettingsFragment2 = settingsFragment;

//             Fragment fragment = mFragmentManager.findFragmentById(R.id.settings_fragment2);
//             if(fragment instanceof  SettingsFragment2) {
//                 mSettingsFragment2 = (SettingsFragment2)fragment;
//             }
        }
    }
    private void showSettingFragment(){
//        // rm old
//        Fragment fragment = mFragmentManager.findFragmentByTag(SettingsFragment2.FRAGMENT_TAG);
//        if(fragment != null && fragment instanceof SettingsFragment2) {
//            mFragmentManager.beginTransaction()
//                    .remove(fragment)
//                    .commitAllowingStateLoss();
//        }

        // create new
        createSettingFragment();
        initializeSettingsFragment2();	// to init every time, if first , mChusSettingsFragment == null postpone init when fragment is created !

//         mSettingsFragmentContainerLayout.setBackgroundColor(Color.parseColor("#fff3f8fc"));
        mSettingsLayout.setVisibility(View.VISIBLE);

        final View v = mSettingsLayout;
        ViewPropertyAnimator vp = v.animate();
        vp.cancel();
        int duration_ = ANIMATION_DURATION;
        //duration_ = 800; // frankie,
        v.setAlpha(0.0f); // frankie, add
        vp.setDuration(duration_)
                //.alpha(0.85f);
                .alpha(1.0f); // frankie,
        vp.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
            }
            @Override
            public void onAnimationCancel(Animator animation) {
                mSettingsLayout.setAlpha(1.0f); // *** must set target
                mSettingsLayout.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
                mSettingsLayout.setAlpha(1.0f); // *** must set target
                mSettingsLayout.setVisibility(View.VISIBLE);
            }
        });
        vp.start();

        mActivity.setSlideSwitcherShow(false,true);
    }
    private boolean hideSettingFragment() {
        mActivity.setSlideSwitcherShow(true,false);
        // frankie, here directly disappear this settings UI , later should change
        FragmentManager m = mActivity.getFragmentManager();
        PreferenceFragment f = (PreferenceFragment)m.findFragmentByTag(SettingsFragment2.FRAGMENT_TAG_4PHOTO);
        if(mSettingsLayout != null && f != null ){
// 		   m.beginTransaction()
// 				   .remove(f)
// 				   .commitAllowingStateLoss();		 // i keep this fragment

            if(mSettingsLayout.getVisibility() == View.VISIBLE) {
                //mSettingsLayout.setVisibility(View.INVISIBLE);
                //mSettingsLayout.setBackgroundColor(Color.TRANSPARENT);
                if(((SettingsFragment2)f).isSecDir()){
                    ((SettingsFragment2)f).showFirDir();
                    mActivity.setSlideSwitcherShow(false,true);
                    titleTv.setText(R.string.chus_settings_fragment_title_bar_title);
                    return  true;
                }
                final View v = mSettingsLayout;
                ViewPropertyAnimator vp = v.animate();
                vp.cancel();
                vp.alpha(0f).setDuration(ANIMATION_DURATION);
                vp.setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }
                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setVisibility(View.INVISIBLE);
                        updateTopMenuView();
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        v.setVisibility(View.INVISIBLE);
                        updateTopMenuView();
                    }
                });
                vp.start();
                return true;
            }
        }
        return false;
    }
    // frankie, end
    public boolean isShowSettingsFragment(){
        return mChusSettingsFragment == null ? false:true;
    }
    private void initMakeupMenu() {
        if(!TsMakeupManager.HAS_TS_MAKEUP) {
            return;
        }
        mPopupStatus = POPUP_NONE;
        mHandler.removeMessages(MAKEUP_MESSAGE_ID);
        mSceneStatus = MODE_MAKEUP;
        mPreviewMenuStatus = PREVIEW_MENU_ON;
        mHandler.sendEmptyMessageDelayed(MAKEUP_MESSAGE_ID, ANIMATION_DURATION);
    }

    private void showAlertDialog(final ListPreference faceDetectPref) {
        if(mActivity.isFinishing()) {
            return;
        }
        new AlertDialog.Builder(mActivity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(R.string.text_tsmakeup_alert_msg)
                .setPositiveButton(R.string.text_tsmakeup_alert_continue, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toggleMakeupSettings();

                        faceDetectPref.setValue(Camera.Parameters.FACE_DETECTION_ON);
                        onSettingChanged(faceDetectPref);
                    }
                })
                .setNegativeButton(R.string.text_tsmakeup_alert_quit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    private void toggleMakeupSettings() {
        mUI.hideUI();
        initMakeupMenu();
    }

    private void closeMakeup() {
        if(TsMakeupManager.HAS_TS_MAKEUP) {
            if(mTsMakeupManager.isShowMakeup()) {
                mTsMakeupManager.hideMakeupUI();
                closeMakeupMode(false);
                mPreviewMenuStatus = PREVIEW_MENU_NONE;
            } else {
                mTsMakeupManager.hideMakeupUI();
            }
        }
    }

    public void addModeBack() {
        //if (mSceneStatus == MODE_SCENE) {
        //    addSceneMode();
        //}
        if (mSceneStatus == MODE_FILTER) {
            addFilterMode();
        }
    }

    /*public*/private void addFilterMode() {
        final IconListPreference pref = (IconListPreference) mPreferenceGroup
                .findPreference(CameraSettings.KEY_COLOR_EFFECT);
        if (pref == null)
            return;

        int rotation = CameraUtil.getDisplayRotation(mActivity);
        boolean mIsDefaultToPortrait = CameraUtil.isDefaultToPortrait(mActivity);
        if (!mIsDefaultToPortrait) {
            rotation = (rotation + 90) % 360;
        }
        WindowManager wm = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        CharSequence[] entries = pref.getEntries();

        Resources r = mActivity.getResources();
        int height = (int) (r.getDimension(R.dimen.filter_mode_height) + 2
                * r.getDimension(R.dimen.filter_mode_padding) + 1);
        int width = (int) (r.getDimension(R.dimen.filter_mode_width) + 2
                * r.getDimension(R.dimen.filter_mode_padding) + 1);

        int gridRes = 0;
        boolean portrait = (rotation == 0) || (rotation == 180);
        int size = height;
        if (portrait) {
            gridRes = R.layout.vertical_grid;
            size = width;
        } else {
            gridRes = R.layout.horiz_grid;
        }
        previewMenuSize = size;
        mUI.hideUI();
        mPreviewMenuStatus = PREVIEW_MENU_ON;
        mSceneStatus = MODE_FILTER;

        int[] thumbnails = pref.getThumbnailIds();

        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        FrameLayout basic = (FrameLayout) inflater.inflate(
                gridRes, null, false);

        mUI.dismissSceneModeMenu();
        LinearLayout previewMenuLayout = new LinearLayout(mActivity);
        mUI.setPreviewMenuLayout(previewMenuLayout);
        ViewGroup.LayoutParams params = null;

        Point _displaySize = new Point();
        mActivity.getWindowManager().getDefaultDisplay().getSize(_displaySize);
        Log.v(TAG, "_displaySize:" + _displaySize.x + "*" + _displaySize.y); // 1080*1776

        if (portrait) {
            //params = new ViewGroup.LayoutParams(size, LayoutParams.MATCH_PARENT);
            params = new ViewGroup.LayoutParams(size, _displaySize.y - 150); // frankie,
            previewMenuLayout.setLayoutParams(params);
            ((ViewGroup) mUI.getRootView()).addView(previewMenuLayout);
        } else {
            params = new ViewGroup.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, size);
            previewMenuLayout.setLayoutParams(params);
            ((ViewGroup) mUI.getRootView()).addView(previewMenuLayout);
            previewMenuLayout.setY(display.getHeight() - size);
        }
        basic.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        LinearLayout layout = (LinearLayout) basic.findViewById(R.id.layout);
        final View[] views = new View[entries.length];
        int init = pref.getCurrentIndex();
        for (int i = 0; i < entries.length; i++) {
            RotateLayout layout2 = (RotateLayout) inflater.inflate(R.layout.filter_mode_view, null, false);
            ImageView imageView = (ImageView) layout2.findViewById(R.id.image);
            final int j = i;

            layout2.setOnTouchListener(new View.OnTouchListener() {
                private long startTime;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        startTime = System.currentTimeMillis();
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (System.currentTimeMillis() - startTime < CLICK_THRESHOLD) {
                            pref.setValueIndex(j);
                            changeFilterModeControlIcon(pref.getValue());
                            onSettingChanged(pref);
                            for (View v1 : views) {
                                v1.setBackground(null);
                            }
                            ImageView image = (ImageView) v.findViewById(R.id.image);
                            image.setBackgroundColor(0xff33b5e5);
                        }
                    }
                    return true;
                }
            });

            views[j] = imageView;
            if (i == init)
                imageView.setBackgroundColor(0xff33b5e5);
            TextView label = (TextView) layout2.findViewById(R.id.label);
            imageView.setImageResource(thumbnails[i]);
            label.setText(entries[i]);
            layout.addView(layout2);
        }
        previewMenuLayout.addView(basic);
        mPreviewMenu = basic;
    }
    public void updateFilterControlIcon(){
        CameraModule cur = mActivity.getCurrentModule();
        PhotoModule pm = null;
        int typeIndex = -1;
        if(cur instanceof PhotoModule){
            pm = (PhotoModule)cur;
            typeIndex = pm.getPersistedChusFilterType();
        }
        String value = "";
        if(typeIndex == -1 || typeIndex == 0) {
            value = "Off";
        } else {
            value = "On";
        }
        final IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_FILTER_MODE);
        pref.setValue(value);
        int index = pref.getCurrentIndex();
    }
    private void changeFilterModeControlIcon(String value) {
        if(!value.equals("")) {
            if(value.equalsIgnoreCase("none")) {
                value = "Off";
            } else {
                value = "On";
            }
            final IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_FILTER_MODE);
            pref.setValue(value);
            int index = pref.getCurrentIndex();
        }
    }

    public void updateFlahLightIcon(ListPreference hdrPref){
        if (hdrPref == null) return;
        if((notSame(hdrPref, CameraSettings.KEY_CAMERA_HDR, mSettingOff))){
            buttonSetEnabled(mFlashSwitcher, false);
        }else {
            buttonSetEnabled(mFlashSwitcher, true);
        }
    }

    public void openFirstLevel() {
        if (isMenuBeingShown() || CameraControls.isAnimating()) {
            return;
        }

        if (mListMenuContainer == null || mListMenu == null || mPopupStatus != POPUP_FIRST_LEVEL) {
            initializePopup();
            mPopupStatus = POPUP_FIRST_LEVEL;
        }

        mActivity.setSlideSwitcherShow(false, true); // frankie, add
        mUI.showPopup(mListMenuContainer, 1, true);
    }

    public void popupDismissed(boolean dismissAll) {
        if (!dismissAll && mPopupStatus == POPUP_SECOND_LEVEL) {
            initializePopup();
            mPopupStatus = POPUP_FIRST_LEVEL;
            mUI.showPopup(mListMenuContainer, 1, false);
            if (mListMenu != null) {
                mListMenu = null;
            }

        } else {
            initializePopup();
        }

    }

    public void removeAllView() {
        if (mUI != null)
            mUI.removeLevel2();

        if (mListMenuContainer != null && mListMenu != null) {
            mUI.dismissLevel1();
            mPopupStatus = POPUP_NONE;
        }
        closeSceneMode();
        mPreviewMenuStatus = PREVIEW_MENU_NONE;
    }

    public void closeAllView() {
        if (mUI != null)
            mUI.removeLevel2();

        if (mListMenuContainer != null && mListMenu != null) {
            //animateSlideOut(mListMenu, 1);
            animateFadeOut(mListMenuContainer, 1); // frankie,
        }
        animateSlideOutPreviewMenu();
    }

    public void closeView() {
        if (mUI != null)
            mUI.removeLevel2();

        if (mListMenuContainer != null && mListMenu != null && mPopupStatus != POPUP_NONE) {
            //animateSlideOut(mListMenu, 1);
            animateFadeOut(mListMenuContainer, 1); // frankie, add
        }
    }

    // Return true if the preference has the specified key but not the value.
    private static boolean notSame(ListPreference pref, String key, String value) {
        return (key.equals(pref.getKey()) && !value.equals(pref.getValue()));
    }

    // Return true if the preference has the specified key and the value.
    private static boolean same(ListPreference pref, String key, String value) {
        return (key.equals(pref.getKey()) && value.equals(pref.getValue()));
    }

    public void setPreference(String key, String value) {
        Log.v(TAG, "setPreference, key:" + key + " value:" + value);
        ListPreference pref = mPreferenceGroup.findPreference(key);
        if (pref != null && !value.equals(pref.getValue())) {
            pref.setValue(value);
            reloadPreferences();
        }
    }

    public int getOrientation() {
        return mUI == null ? 0 : mUI.getOrientation();
    }

    public void hideTopMenu(boolean hide) {
        Log.v(TAG, "hideTopMenu: " + hide);
        if (hide) {
            mFlashSwitcher.setVisibility(View.GONE);
            mFrontBackSwitcher.setVisibility(View.GONE);
            mTsMakeupSwitcher.setVisibility(View.GONE);
        } else {
            mFlashSwitcher.setVisibility(View.VISIBLE);
            mFrontBackSwitcher.setVisibility(View.VISIBLE);
            mTsMakeupSwitcher.setVisibility(View.VISIBLE);
        }
//         hideNativeTopControlsMenu();
    }

    public void hideCameraControls(boolean hide) {
        Log.v(TAG, "hideCameraControls: " + hide);
        if(hide && (mFlashLightLyt.getVisibility() == View.VISIBLE)){
            mFlashLightLyt.setVisibility(View.GONE);
            mTopMenu.setVisibility(View.VISIBLE);
            mTopMenu.setAlpha(1.0f);
        }
        final int status = (hide) ? View.INVISIBLE : View.VISIBLE;
        if(mIsFrontCamera){
            mFlashSwitcher.setVisibility(View.GONE);
        }else {
            mFlashSwitcher.setVisibility(status);
        }
        mFrontBackSwitcher.setVisibility(status);
        mPreviewThumbnail.setVisibility(status);
    }


    // Hit when an item in a popup gets selected
    @Override // CountdownTimerPopup.Listener IMPL,  // ListSubMenu.Listener IMPL
    public void onListPrefChanged(ListPreference pref) {
        Log.v("CAM_PreferenceChanged","onListPrefChanged: " + pref.getKey());
        onSettingChanged(pref);
        //closeView();
    }


    // ----------------------------------------------- ListMenu.Listener IMPL start
    private ListMenu.Listener mListMenuListener = new ListMenu.Listener() {
        @Override
        public void onSettingChanged(ListPreference pref) {
            //ToroPhotoMenu.this.onSettingChanged(pref); // frankie, not callbacked , rm it
        }
        @Override
        public void onPreferenceClicked(ListPreference pref, int y) {
            ToroPhotoMenu.this.onPreferenceClicked(pref, y);
        }
        @Override
        public void onListMenuTouched() {
            ToroPhotoMenu.this.onListMenuTouched();
        }
    };

    //@Override
    public void onSettingChanged(ListPreference pref) {	// also override super's // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        Log.v(TAG, "++++++++++++++++++++++ onSettingChanged, pref.key:" + pref.getKey());
        Log.v("CAM_PreferenceChanged", "++++++++++++++++++++++ onSettingChanged, pref.key:" + pref.getKey());
        if(CameraSettings.KEY_RESET_DEFAULT_SETTINGS.equals(pref.getKey())) {
            String[] resetDefaultPrefs = new String[] {
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

            for(String pref_key : resetDefaultPrefs) {
                ListPreference pref11 = mPreferenceGroup.findPreference(pref_key);
                if(pref11 != null) {
                    String defaultValue = pref11.findSupportedDefaultValue();
                    String offValue_ = pref11.getOffValue();
                    setPreference(pref_key, defaultValue != null ? defaultValue : offValue_);

                    onSettingChanged(pref11);
                    if(pref11 instanceof IconListPreference){
                        refreshView((IconListPreference)pref11);
                    }
                }
            }
//            hideSettingFragment();
            Toast.makeText(mActivity,R.string.pref_reset_default_hint,Toast.LENGTH_SHORT).show();
        }
        // Reset the scene mode if HDR is set to on. Reset HDR if scene mode is
        // set to non-auto.
        if (same(pref, CameraSettings.KEY_SCENE_MODE, Camera.Parameters.SCENE_MODE_HDR)) {
            ListPreference hdrPref =
                    mPreferenceGroup.findPreference(CameraSettings.KEY_CAMERA_HDR);
            if (hdrPref != null && same(hdrPref, CameraSettings.KEY_CAMERA_HDR, mSettingOff)) {
                setPreference(CameraSettings.KEY_CAMERA_HDR, mSettingOn);
            }
        } else if (notSame(pref, CameraSettings.KEY_SCENE_MODE, Camera.Parameters.SCENE_MODE_HDR)) {
            ListPreference hdrPref =
                    mPreferenceGroup.findPreference(CameraSettings.KEY_CAMERA_HDR);
            if (hdrPref != null && notSame(hdrPref, CameraSettings.KEY_CAMERA_HDR, mSettingOff)) {
                setPreference(CameraSettings.KEY_CAMERA_HDR, mSettingOff);
            }
        } else if (same(pref, CameraSettings.KEY_CAMERA_HDR, mSettingOff)) {
            ListPreference scenePref =
                    mPreferenceGroup.findPreference(CameraSettings.KEY_SCENE_MODE);
            if (scenePref != null && notSame(scenePref, CameraSettings.KEY_SCENE_MODE, Camera.Parameters.SCENE_MODE_AUTO)) {
                setPreference(CameraSettings.KEY_SCENE_MODE, Camera.Parameters.SCENE_MODE_AUTO);
            }
            //updateSceneModeIcon((IconListPreference) scenePref);
        } else if (same(pref, CameraSettings.KEY_CAMERA_HDR, mSettingOn)) {
            ListPreference scenePref =
                    mPreferenceGroup.findPreference(CameraSettings.KEY_SCENE_MODE);
            if (scenePref != null && notSame(scenePref, CameraSettings.KEY_SCENE_MODE, Camera.Parameters.SCENE_MODE_HDR)) {
                setPreference(CameraSettings.KEY_SCENE_MODE, Camera.Parameters.SCENE_MODE_HDR);
            }
            //updateSceneModeIcon((IconListPreference) scenePref);
        } else if (notSame(pref,CameraSettings.KEY_AE_BRACKET_HDR,"Off")) {
            RotateTextToast.makeText(mActivity,
                    R.string.flash_aebracket_message,Toast.LENGTH_SHORT).show();
            setPreference(CameraSettings.KEY_FLASH_MODE, Camera.Parameters.FLASH_MODE_OFF);
        } else if (notSame(pref,CameraSettings.KEY_FLASH_MODE,"Off")) {
            ListPreference aePref =
                    mPreferenceGroup.findPreference(CameraSettings.KEY_AE_BRACKET_HDR);
            if (notSame(aePref,CameraSettings.KEY_AE_BRACKET_HDR,"Off")) {
                RotateTextToast.makeText(mActivity,
                        R.string.flash_aebracket_message,Toast.LENGTH_SHORT).show();
            }
        } else if (notSame(pref, CameraSettings.KEY_LONGSHOT, mSettingOff)) {
            ListPreference advancefeaturePref =
                    mPreferenceGroup.findPreference(CameraSettings.KEY_ADVANCED_FEATURES);
            if (advancefeaturePref != null) {
                if (notSame(advancefeaturePref, CameraSettings.KEY_ADVANCED_FEATURES,
                        mActivity.getString(R.string.pref_camera_advanced_feature_default))) {
                    RotateTextToast.makeText(mActivity, R.string.longshot_enable_message,
                            Toast.LENGTH_LONG).show();
                }
                setPreference(CameraSettings.KEY_ADVANCED_FEATURES,
                        mActivity.getString(R.string.pref_camera_advanced_feature_default));
            }
        } else if (notSame(pref, CameraSettings.KEY_ADVANCED_FEATURES,
                mActivity.getString(R.string.pref_camera_advanced_feature_default))) {
            ListPreference longshotPref =
                    mPreferenceGroup.findPreference(CameraSettings.KEY_LONGSHOT);
            if (longshotPref != null ) {
                if (notSame(longshotPref, CameraSettings.KEY_LONGSHOT, mSettingOff)) {
                    RotateTextToast.makeText(mActivity, R.string.advance_feature_enable_msg,
                            Toast.LENGTH_LONG).show();
                }
                setPreference(CameraSettings.KEY_LONGSHOT, mSettingOff);
            }
        }

        String refocusOn = mActivity.getString(R.string.pref_camera_advanced_feature_value_refocus_on);
        if (notSame(pref, CameraSettings.KEY_SCENE_MODE, refocusOn)) {
            ListPreference lp = mPreferenceGroup.findPreference(CameraSettings.KEY_ADVANCED_FEATURES);
            if (lp != null && refocusOn.equals(lp.getValue())) {
                setPreference(CameraSettings.KEY_ADVANCED_FEATURES,
                        mActivity.getString(R.string.pref_camera_advanced_feature_default));
            }
        }

        String optizoomOn = mActivity.getString(R.string.pref_camera_advanced_feature_value_optizoom_on);
        if (notSame(pref, CameraSettings.KEY_SCENE_MODE, optizoomOn)) {
            ListPreference lp = mPreferenceGroup.findPreference(CameraSettings.KEY_ADVANCED_FEATURES);
            if (lp != null && optizoomOn.equals(lp.getValue())) {
                setPreference(CameraSettings.KEY_ADVANCED_FEATURES,
                        mActivity.getString(R.string.pref_camera_advanced_feature_default));
            }
        }

        String chromaFlashOn = mActivity.getString(R.string.
                pref_camera_advanced_feature_value_chromaflash_on);
        if (notSame(pref, CameraSettings.KEY_SCENE_MODE, Camera.Parameters.SCENE_MODE_AUTO)) {
            ListPreference lp = mPreferenceGroup
                    .findPreference(CameraSettings.KEY_ADVANCED_FEATURES);
            if (lp != null && chromaFlashOn.equals(lp.getValue())) {
                setPreference(CameraSettings.KEY_QC_CHROMA_FLASH, mSettingOff);
                setPreference(CameraSettings.KEY_ADVANCED_FEATURES,
                        mActivity.getString(R.string.pref_camera_advanced_feature_default));
            }
        }

        if (notSame(pref, CameraSettings.KEY_SCENE_MODE, "auto")) {
            setPreference(CameraSettings.KEY_COLOR_EFFECT,
                    mActivity.getString(R.string.pref_camera_coloreffect_default));
        }

        String stillMoreOn = mActivity.getString(R.string.pref_camera_advanced_feature_value_stillmore_on);
        if (same(pref, CameraSettings.KEY_ADVANCED_FEATURES, stillMoreOn)) {
            setPreference(CameraSettings.KEY_FLASH_MODE, Camera.Parameters.FLASH_MODE_OFF);
        }

        // frankie, if KEY_AUTO_HDR enable , rm mHdrSwitcher
        ListPreference autoHdrPref = mPreferenceGroup.findPreference(CameraSettings.KEY_AUTO_HDR);
        //if (autoHdrPref != null && autoHdrPref.getValue().equalsIgnoreCase("enable")) {
        //    mHdrSwitcher.setVisibility(View.GONE);
        //    mUI.getCameraControls().removeFromViewList(mHdrSwitcher);
        //} else {
        //    mHdrSwitcher.setVisibility(View.VISIBLE);
        //}
        //updateFilterModeIcon(pref, pref);

        if (same(pref, CameraSettings.KEY_RECORD_LOCATION, "on")) {
            mActivity.requestLocationPermission();
        }

        // frankie, add start
        Log.v(TAG, "checks KEY_QC_CHROMA_FLASH if on ");
        if (notSame(pref, CameraSettings.KEY_QC_CHROMA_FLASH, "chroma-flash-on")) {
            mFlashSwitcher.setEnabled(true);
        } else if(same(pref, CameraSettings.KEY_QC_CHROMA_FLASH, "chroma-flash-on")) {
            mFlashSwitcher.setEnabled(false);
        }
        // frankie, add end

        if(pref.getKey().equalsIgnoreCase(CameraSettings.KEY_CAMERA_HDR)){
            if(pref.getValue().equalsIgnoreCase(mSettingOff)){
                buttonSetEnabled(mFlashSwitcher, true);
            }else{
                buttonSetEnabled(mFlashSwitcher, false);
            }
        }

        super.onSettingChanged(pref);	// to call mListener.onSharedPreferenceChanged(ListPreference pref);

        if (same(pref, SettingsManager.KEY_CAMERA2, "enable")) {
            mActivity.onModuleSelected(ModuleSwitcher.CAPTURE_MODULE_INDEX);
        } else if (notSame(pref, SettingsManager.KEY_CAMERA2, "enable")) {
            mActivity.onModuleSelected(ModuleSwitcher.PHOTO_MODULE_INDEX);
        }
        Log.v(TAG, "---------------------- onSettingChanged");
        Log.v("CAM_PreferenceChanged", "---------------------- onSettingChanged");
    }
    // Hit when an item in the first-level popup gets selected, then bring up
    // the second-level popup
    //@Override
    //public void onPreferenceClicked(ListPreference pref) {
    //    onPreferenceClicked(pref, 0);
    //}
    //@Override
    public void onPreferenceClicked(ListPreference pref, int y) {
        Log.v(TAG, "+ onPreferenceClicked, pref.key:" + pref.getKey());
        if (!mActivity.isDeveloperMenuEnabled()) {
            if (pref.getKey().equals(CameraSettings.KEY_REDEYE_REDUCTION)) {
                privateCounter++;
                if (privateCounter >= DEVELOPER_MENU_TOUCH_COUNT) {
                    mActivity.enableDeveloperMenu();
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
                    prefs.edit().putBoolean(CameraSettings.KEY_DEVELOPER_MENU, true).apply();
                    RotateTextToast.makeText(mActivity,
                            "Camera developer option is enabled now", Toast.LENGTH_SHORT).show();
                }
            } else {
                privateCounter = 0;
            }
        }
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ListSubMenu basic = (ListSubMenu) inflater.inflate(R.layout.list_sub_menu, null, false);
        basic.initialize(pref, y);
        basic.setSettingChangedListener(this);
        basic.setAlpha(0f);
        mListSubMenu = basic;
        mUI.removeLevel2();
        if (mPopupStatus == POPUP_SECOND_LEVEL) {
            mUI.showPopup(mListSubMenu, 2, false);
        } else {
            mUI.showPopup(mListSubMenu, 2, true);
        }
        mPopupStatus = POPUP_SECOND_LEVEL;
        Log.v(TAG, "- onPreferenceClicked");
    }
    //@Override
    public void onListMenuTouched() {
        Log.v(TAG, "onListMenuTouched");
        mUI.removeLevel2();
        mPopupStatus = POPUP_FIRST_LEVEL;
    }
    // ----------------------------------------------- ListMenu.Listener IMPL start


}
