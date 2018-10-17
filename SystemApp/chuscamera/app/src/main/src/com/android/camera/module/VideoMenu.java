/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.camera.module;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.android.camera.module.prefs.SettingsFragment2;
import com.android.camera.ui.CameraControls;
import com.android.camera.ui.ListMenu;
import com.android.camera.ui.ListSubMenu;
import com.android.camera.ui.RotateTextToast;
import com.android.camera.ui.TimeIntervalPopup;
import com.toro.camera.R;

import java.util.Locale;

public class VideoMenu extends MenuController
        implements 
        //ListMenu.Listener,        // frankie, rm
        ListSubMenu.Listener,
        TimeIntervalPopup.Listener 
        {

    private static String TAG = "VideoMenu";

    private VideoUI mUI;
    private String[] mOtherKeys1;
    private String[] mOtherKeys2;

	private ViewGroup mListMenuContainer = null; // frankie, add 
    private ListMenu mListMenu;
    private ListSubMenu mListSubMenu;
    private View mPreviewMenu;
    private static final int POPUP_NONE = 0;
    private static final int POPUP_FIRST_LEVEL = 1;
    private static final int POPUP_SECOND_LEVEL = 2;
    private static final int POPUP_IN_ANIMATION_SLIDE = 3;
    private static final int POPUP_IN_ANIMATION_FADE = 4;
    private static final int PREVIEW_MENU_NONE = 0;
    private static final int PREVIEW_MENU_IN_ANIMATION = 1;
    private static final int PREVIEW_MENU_ON = 2;
    private static final int MODE_FILTER = 1;
    private int mSceneStatus;
    private View mFrontBackSwitcher;
    private View pauseButton;
    //private View mFilterModeSwitcher;
    private int mPopupStatus;
    private int mPreviewMenuStatus;
    private CameraActivity mActivity;
    private String mPrevSavedVideoCDS;
    private boolean mIsVideoTNREnabled = false;
    private boolean mIsVideoCDSUpdated = false;
    private static final int ANIMATION_DURATION = 300;
    private static final int CLICK_THRESHOLD = 200;
    private int previewMenuSize;
    private boolean mIsFrontCamera = false;

    private static final boolean PERSIST_4K_NO_LIMIT =
            android.os.SystemProperties.getBoolean("persist.camcorder.4k.nolimit", false);

	private View mSceneModeSwitcher; // frankie, add 

	FragmentManager mFragmentManager = null;
    private FrameLayout mSettingsFragmentContainerLayout = null;
    private View mSettingsLayout = null;
    private TextView titleTv = null;
    private TextView backTv = null;
	private SettingsFragment2 mChusSettingsFragment; // frankie, add for replace of mListMenu

    public VideoMenu(CameraActivity activity, VideoUI ui) {
        super(activity);
        mUI = ui;
        mActivity = activity;
		mFragmentManager = mActivity.getFragmentManager();
		
        mFrontBackSwitcher = ui.getRootView().findViewById(R.id.front_back_switcher);
        pauseButton = ui.getRootView().findViewById(R.id.pause_video);
        //mFilterModeSwitcher = ui.getRootView().findViewById(R.id.filter_mode_switcher);
        mSceneModeSwitcher = ui.getRootView().findViewById(R.id.scene_mode_switcher);

		mSettingsLayout = ui.getRootView().findViewById(R.id.prefs_setting_layout);
        mSettingsFragmentContainerLayout = (FrameLayout) ui.getRootView().findViewById(R.id.prefs_container_4video);
        backTv = (TextView) ui.getRootView().findViewById(R.id.pref_header_backward_tv);
        titleTv = (TextView) ui.getRootView().findViewById(R.id.text_title);
        titleTv.setText(R.string.chus_settings_fragment_title_bar_title);
    }

    public void initialize(PreferenceGroup group) {
        super.initialize(group);
		        // settings popup
        mOtherKeys1 = new String[] {
                //CameraSettings.KEY_VIDEOCAMERA_FLASH_MODE, // frankie, rm
                CameraSettings.KEY_VIDEO_QUALITY,
//                CameraSettings.KEY_VIDEO_DURATION,
                CameraSettings.KEY_RECORD_LOCATION,
                //CameraSettings.KEY_CAMERA_SAVEPATH, // frankie, 2017.07.28, rm
//                CameraSettings.KEY_WHITE_BALANCE,			// frankie, 2017.08.18, rm
                //CameraSettings.KEY_VIDEO_HIGH_FRAME_RATE,	// frankie, 2017.08.18, rm
                CameraSettings.KEY_DIS
        };
        mOtherKeys2 = new String[] {
                //CameraSettings.KEY_VIDEOCAMERA_FLASH_MODE, // frankie, rm
                CameraSettings.KEY_VIDEO_QUALITY,
                CameraSettings.KEY_VIDEO_DURATION,
                CameraSettings.KEY_RECORD_LOCATION,
                //CameraSettings.KEY_CAMERA_SAVEPATH, // frankie, 2017.07.28, rm
                CameraSettings.KEY_WHITE_BALANCE,
                CameraSettings.KEY_FACE_DETECTION,
                CameraSettings.KEY_VIDEO_HIGH_FRAME_RATE,
                CameraSettings.KEY_SEE_MORE,
                CameraSettings.KEY_NOISE_REDUCTION,
                CameraSettings.KEY_DIS,
                CameraSettings.KEY_VIDEO_EFFECT,
                CameraSettings.KEY_VIDEO_TIME_LAPSE_FRAME_INTERVAL,
                CameraSettings.KEY_VIDEO_ENCODER,
                CameraSettings.KEY_AUDIO_ENCODER,
                CameraSettings.KEY_VIDEO_HDR,
                CameraSettings.KEY_POWER_MODE,
                CameraSettings.KEY_VIDEO_ROTATION,
                CameraSettings.KEY_VIDEO_CDS_MODE,
                CameraSettings.KEY_VIDEO_TNR_MODE,
                CameraSettings.KEY_VIDEO_SNAPSHOT_SIZE
        };

		mListMenuContainer = null;
        mListMenu = null;
        mListSubMenu = null;
        mPopupStatus = POPUP_NONE;
        mPreviewMenuStatus = POPUP_NONE;
        
        mFrontBackSwitcher.setVisibility(View.INVISIBLE);
        pauseButton.setVisibility(View.GONE);

		initFlashlightButton(mSceneModeSwitcher);
		//initFilterModeButton(mFilterModeSwitcher);
        initSwitchItem_CameraId(CameraSettings.KEY_CAMERA_ID, mFrontBackSwitcher);

		if(backTv != null) { // frankie, add
            backTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSettingFragment();
                }
            });
        }
        setIsFrontCamera();
    }

    private void setIsFrontCamera(){
        CameraModule curModule = mActivity.getCurrentModule();
        if(curModule instanceof VideoModule){
           int cur = ((VideoModule)curModule).currentFrontCamera();
            if(cur == 1){
                mIsFrontCamera = true;
            }else {
                mIsFrontCamera = false;
            }
        }
    }

    /*public*/private void initSwitchItem_CameraId(final String prefKey, View switcher) {
        final IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(prefKey);
        if (pref == null) {
            return;
		}

        int[] iconIds = pref.getLargeIconIds();
        int resid = -1;
        int index = pref.findIndexOfValue(pref.getValue());
        if (!pref.getUseSingleIcon() && iconIds != null) {
            if (index == -1) {
                return;
            }
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
        switcher.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(prefKey);
                if (pref == null) {
                    return;
				}
                int index = pref.findIndexOfValue(pref.getValue());
                CharSequence[] values = pref.getEntryValues();
                index = (index + 1) % values.length;
                pref.setValueIndex(index);
				
                //((ImageView) v).setImageResource(((IconListPreference) pref).getLargeIconIds()[index]);
				
                //if (prefKey.equals(CameraSettings.KEY_CAMERA_ID)) {
                    mFrontBackSwitcher.setEnabled(false);
                    mListener.onCameraPickerClicked(index);
				//}
				
                reloadPreference(pref);
                onSettingChanged(pref);
            }
        });
    }
    private void initFlashlightButton(View button) { // frankie, add
        Log.v(TAG, "+ initFlashlightButton");
        button.setVisibility(View.INVISIBLE);
        final IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_VIDEOCAMERA_FLASH_MODE);
        if (pref == null) {
            Log.v(TAG, "- initFlashlightButton 0");
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
        button.setVisibility(View.VISIBLE);

        String flashMode = pref.getValue();
        Log.d(TAG, "initFlashlightButton, flashMode : " + flashMode);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_VIDEOCAMERA_FLASH_MODE);
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
        Log.v(TAG, "- initFlashlightButton done");
	}

    public boolean handleBackKey() {
		if(hideSettingFragment()) { // frankie, 
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

    public void tryToCloseSubList() {
        if (mListMenuContainer != null && mListMenu != null)
            ((ListMenu) mListMenu).resetHighlight();

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
        vp.setListener(new AnimatorListener() {
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

        vp.setListener(new AnimatorListener() {
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
        vp.setDuration(ANIMATION_DURATION).start();
    }

    public void animateFadeIn(final View v) { // frankie, (final ListView v) {
        ViewPropertyAnimator vp = v.animate();
		int duration_ = ANIMATION_DURATION;
		//duration_ = 800; // frankie, 
		v.setAlpha(0.0f); // frankie, add 
        vp.setDuration(duration_)
		//.alpha(0.85f);
		.alpha(1.0f); // frankie,
		
        vp.start();
    }

    public void animateSlideIn(final View v, int delta, boolean adjustOrientation) {
        int orientation = mUI.getOrientation();
        if (!adjustOrientation)
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
        vp.setListener(new AnimatorListener() {
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
        vp.setDuration(ANIMATION_DURATION).start();
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

    public void addModeBack() {
//        if (mSceneStatus == MODE_FILTER) {
//            addFilterMode();
//        }
    }

//
//    /*public*/private void initFilterModeButton(View button) {
//        button.setVisibility(View.INVISIBLE);
//        final IconListPreference pref = (IconListPreference) mPreferenceGroup
//                .findPreference(CameraSettings.KEY_COLOR_EFFECT);
//        if (pref == null || pref.getValue() == null)
//            return;
//
//        changeFilterModeControlIcon(pref.getValue());
//        button.setVisibility(View.VISIBLE);
//        button.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            	if(true) {
//					mUI.onFilterButtonClicked(); // frankie, add
//				} else {
//
//                addFilterMode();
//                ViewGroup menuLayout = mUI.getPreviewMenuLayout();
//                if (menuLayout != null) {
//                    View view = menuLayout.getChildAt(0);
//                    mUI.adjustOrientation();
//                    animateSlideIn(view, previewMenuSize, false);
//                }
//
//				}
//            }
//        });
//    }
//    /*public*/private void addFilterMode() {
//        final IconListPreference pref = (IconListPreference) mPreferenceGroup
//                .findPreference(CameraSettings.KEY_COLOR_EFFECT);
//        if (pref == null)
//            return;
//
//        int rotation = CameraUtil.getDisplayRotation(mActivity);
//        boolean mIsDefaultToPortrait = CameraUtil.isDefaultToPortrait(mActivity);
//        if (!mIsDefaultToPortrait) {
//            rotation = (rotation + 90) % 360;
//        }
//        WindowManager wm = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//        CharSequence[] entries = pref.getEntries();
//
//        Resources r = mActivity.getResources();
//        int height = (int) (r.getDimension(R.dimen.filter_mode_height) + 2
//                * r.getDimension(R.dimen.filter_mode_padding) + 1);
//        int width = (int) (r.getDimension(R.dimen.filter_mode_width) + 2
//                * r.getDimension(R.dimen.filter_mode_padding) + 1);
//
//        int gridRes = 0;
//        boolean portrait = (rotation == 0) || (rotation == 180);
//        int size = height;
//        if (portrait) {
//            gridRes = R.layout.vertical_grid;
//            size = width;
//        } else {
//            gridRes = R.layout.horiz_grid;
//        }
//        previewMenuSize = size;
//        mUI.hideUI();
//        mPreviewMenuStatus = PREVIEW_MENU_ON;
//        mSceneStatus = MODE_FILTER;
//
//        int[] thumbnails = pref.getThumbnailIds();
//
//        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(
//                Context.LAYOUT_INFLATER_SERVICE);
//        FrameLayout basic = (FrameLayout) inflater.inflate(
//                gridRes, null, false);
//
//        mUI.dismissSceneModeMenu();
//        LinearLayout previewMenuLayout = new LinearLayout(mActivity);
//        mUI.setPreviewMenuLayout(previewMenuLayout);
//        ViewGroup.LayoutParams params = null;
//
//		Point _displaySize = new Point();
//		mActivity.getWindowManager().getDefaultDisplay().getSize(_displaySize);
//        Log.v(TAG, "_displaySize:" + _displaySize.x + "*" + _displaySize.y); // 1080*1776
//
//        if (portrait) {
//            //params = new ViewGroup.LayoutParams(size, LayoutParams.MATCH_PARENT);
//			params = new ViewGroup.LayoutParams(size, _displaySize.y - 150); // frankie,
//            previewMenuLayout.setLayoutParams(params);
//            ((ViewGroup) mUI.getRootView()).addView(previewMenuLayout);
//        } else {
//            params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, size);
//            previewMenuLayout.setLayoutParams(params);
//            ((ViewGroup) mUI.getRootView()).addView(previewMenuLayout);
//            previewMenuLayout.setY(display.getHeight() - size);
//        }
//        basic.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
//                LayoutParams.MATCH_PARENT));
//        LinearLayout layout = (LinearLayout) basic.findViewById(R.id.layout);
//
//        final View[] views = new View[entries.length];
//        int init = pref.getCurrentIndex();
//        for (int i = 0; i < entries.length; i++) {
//            RotateLayout layout2 = (RotateLayout) inflater.inflate(
//                    R.layout.filter_mode_view, null, false);
//
//            ImageView imageView = (ImageView) layout2.findViewById(R.id.image);
//            final int j = i;
//
//            layout2.setOnTouchListener(new View.OnTouchListener() {
//                private long startTime;
//
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                        startTime = System.currentTimeMillis();
//                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
//                        if (System.currentTimeMillis() - startTime < CLICK_THRESHOLD) {
//                            pref.setValueIndex(j);
//                            changeFilterModeControlIcon(pref.getValue());
//                            for (View v1 : views) {
//                                v1.setBackground(null);
//                            }
//                            ImageView image = (ImageView) v.findViewById(R.id.image);
//                            image.setBackgroundColor(0xff33b5e5);
//                            onSettingChanged(pref);
//                        }
//
//                    }
//                    return true;
//                }
//            });
//
//            views[j] = imageView;
//            if (i == init)
//                imageView.setBackgroundColor(0xff33b5e5);
//            TextView label = (TextView) layout2.findViewById(R.id.label);
//            imageView.setImageResource(thumbnails[i]);
//            label.setText(entries[i]);
//            layout.addView(layout2);
//        }
//        previewMenuLayout.addView(basic);
//        mPreviewMenu = basic;
//    }
//    private void changeFilterModeControlIcon(String value) {
//        if(!value.equals("")) {
//            if(value.equalsIgnoreCase("none")) {
//                value = "Off";
//            } else {
//                value = "On";
//            }
//            final IconListPreference pref = (IconListPreference) mPreferenceGroup
//                    .findPreference(CameraSettings.KEY_FILTER_MODE);
//            pref.setValue(value);
//            int index = pref.getCurrentIndex();
//            ImageView iv = (ImageView) mFilterModeSwitcher;
//            iv.setImageResource(((IconListPreference) pref).getLargeIconIds()[index]);
//        }
//    }

    public void openFirstLevel() {
        if (isMenuBeingShown() || CameraControls.isAnimating())
            return;
        if (mListMenuContainer == null // frankie, add 
			|| mListMenu == null
			|| mPopupStatus != POPUP_FIRST_LEVEL) {
            initializePopup();
            mPopupStatus = POPUP_FIRST_LEVEL;
        }
        mUI.showPopup(mListMenuContainer, 1, true);
    }

    public void setPreference(String key, String value) {
        ListPreference pref = mPreferenceGroup.findPreference(key);
        if (pref != null && !value.equals(pref.getValue())) {
            pref.setValue(value);
            reloadPreferences();
        }
    }

    private void overridePreferenceAccessibility() {
        overrideMenuForLocation();
        overrideMenuFor4K();
        overrideMenuForCDSMode();
        overrideMenuForSeeMore();
    }
    private void overrideMenuForLocation() {
        if (mActivity.isSecureCamera()) {
            // Prevent location preference from getting changed in secure camera
            // mode
            mListMenu.setPreferenceEnabled(CameraSettings.KEY_RECORD_LOCATION, false);
        }
    }
    private void overrideMenuFor4K() {
        if(mUI.is4KEnabled() && !PERSIST_4K_NO_LIMIT) {
            mListMenu.setPreferenceEnabled(CameraSettings.KEY_DIS,false);
            mListMenu.overrideSettings(CameraSettings.KEY_DIS, "disable");
            mListMenu.setPreferenceEnabled(CameraSettings.KEY_SEE_MORE, false);
            mListMenu.overrideSettings(CameraSettings.KEY_SEE_MORE, mActivity.getString(R.string.pref_camera_see_more_value_off));
        }
    }
    private void overrideMenuForCDSMode() {
        ListPreference pref_tnr = mPreferenceGroup.findPreference(CameraSettings.KEY_VIDEO_TNR_MODE);
        ListPreference pref_cds = mPreferenceGroup.findPreference(CameraSettings.KEY_VIDEO_CDS_MODE);
        String tnr = (pref_tnr != null) ? pref_tnr.getValue() : null;
        String cds = (pref_cds != null) ? pref_cds.getValue() : null;

        if (mPrevSavedVideoCDS == null && cds != null) {
            mPrevSavedVideoCDS = cds;
        }
        if ((tnr != null) && !tnr.equals("off")) { 
            mListMenu.setPreferenceEnabled(CameraSettings.KEY_VIDEO_CDS_MODE,false);
            mListMenu.overrideSettings(CameraSettings.KEY_VIDEO_CDS_MODE,
				mActivity.getString(R.string.pref_camera_video_cds_value_off));
            mIsVideoTNREnabled = true;
            if (!mIsVideoCDSUpdated) {
                if (cds != null) {
                    mPrevSavedVideoCDS = cds;
                }
                mIsVideoCDSUpdated = true;
            }
        } else if (tnr != null) {
            mListMenu.setPreferenceEnabled(CameraSettings.KEY_VIDEO_CDS_MODE,true);
            if (mIsVideoTNREnabled) {
                mListMenu.overrideSettings(CameraSettings.KEY_VIDEO_CDS_MODE, mPrevSavedVideoCDS);
                mIsVideoTNREnabled = false;
                mIsVideoCDSUpdated = false;
            }
        }
    }
    private void overrideMenuForSeeMore() {
        ListPreference pref_SeeMore = mPreferenceGroup.findPreference(CameraSettings.KEY_SEE_MORE);
        if(pref_SeeMore != null && pref_SeeMore.getValue() != null
                && pref_SeeMore.getValue().equals("on")) {
            mListMenu.setPreferenceEnabled(CameraSettings.KEY_VIDEO_CDS_MODE,false);
            mListMenu.setPreferenceEnabled(CameraSettings.KEY_VIDEO_TNR_MODE, false);
            mListMenu.setPreferenceEnabled(CameraSettings.KEY_NOISE_REDUCTION, false);
            mListMenu.overrideSettings(
				CameraSettings.KEY_VIDEO_CDS_MODE,mActivity.getString(R.string.pref_camera_video_cds_value_off),
                CameraSettings.KEY_VIDEO_TNR_MODE,mActivity.getString(R.string.pref_camera_video_tnr_value_off),
                CameraSettings.KEY_NOISE_REDUCTION,mActivity.getString(R.string.pref_camera_noise_reduction_value_off));
        }
    }


	// frankie, 2017.08.15, add start
    private void overridePreferenceAccessibility_i() {
        overrideMenuForLocation_i();
        overrideMenuFor4K_i();
        overrideMenuForCDSMode_i();
        overrideMenuForSeeMore_i();
    }
    private void overrideMenuForLocation_i() {
        if (mActivity.isSecureCamera()) {
            // Prevent location preference from getting changed in secure camera
            // mode
            mChusSettingsFragment.setPreferenceEnabled(CameraSettings.KEY_RECORD_LOCATION, false);
        }
    }
    private void overrideMenuFor4K_i() {
        if(mUI.is4KEnabled() && !PERSIST_4K_NO_LIMIT) {
            mChusSettingsFragment.setPreferenceEnabled(CameraSettings.KEY_DIS,false);
            mChusSettingsFragment.overrideSettings(CameraSettings.KEY_DIS, "disable");
            mChusSettingsFragment.setPreferenceEnabled(CameraSettings.KEY_SEE_MORE, false);
            mChusSettingsFragment.overrideSettings(CameraSettings.KEY_SEE_MORE, mActivity.getString(R.string.pref_camera_see_more_value_off));
        }
    }
    private void overrideMenuForCDSMode_i() {
        ListPreference pref_tnr = mPreferenceGroup.findPreference(CameraSettings.KEY_VIDEO_TNR_MODE);
        ListPreference pref_cds = mPreferenceGroup.findPreference(CameraSettings.KEY_VIDEO_CDS_MODE);
        String tnr = (pref_tnr != null) ? pref_tnr.getValue() : null;
        String cds = (pref_cds != null) ? pref_cds.getValue() : null;

        if (mPrevSavedVideoCDS == null && cds != null) {
            mPrevSavedVideoCDS = cds;
        }
        if ((tnr != null) && !tnr.equals("off")) { 
            mChusSettingsFragment.setPreferenceEnabled(CameraSettings.KEY_VIDEO_CDS_MODE,false);
            mChusSettingsFragment.overrideSettings(CameraSettings.KEY_VIDEO_CDS_MODE,
				mActivity.getString(R.string.pref_camera_video_cds_value_off));
            mIsVideoTNREnabled = true;
            if (!mIsVideoCDSUpdated) {
                if (cds != null) {
                    mPrevSavedVideoCDS = cds;
                }
                mIsVideoCDSUpdated = true;
            }
        } else if (tnr != null) {
            mChusSettingsFragment.setPreferenceEnabled(CameraSettings.KEY_VIDEO_CDS_MODE,true);
            if (mIsVideoTNREnabled) {
                mChusSettingsFragment.overrideSettings(CameraSettings.KEY_VIDEO_CDS_MODE, mPrevSavedVideoCDS);
                mIsVideoTNREnabled = false;
                mIsVideoCDSUpdated = false;
            }
        }
    }
    private void overrideMenuForSeeMore_i() {
        ListPreference pref_SeeMore = mPreferenceGroup.findPreference(CameraSettings.KEY_SEE_MORE);
        if(pref_SeeMore != null && pref_SeeMore.getValue() != null
                && pref_SeeMore.getValue().equals("on")) {
            mChusSettingsFragment.setPreferenceEnabled(CameraSettings.KEY_VIDEO_CDS_MODE,false);
            mChusSettingsFragment.setPreferenceEnabled(CameraSettings.KEY_VIDEO_TNR_MODE, false);
            mChusSettingsFragment.setPreferenceEnabled(CameraSettings.KEY_NOISE_REDUCTION, false);
            mChusSettingsFragment.overrideSettings(
				CameraSettings.KEY_VIDEO_CDS_MODE,mActivity.getString(R.string.pref_camera_video_cds_value_off),
                CameraSettings.KEY_VIDEO_TNR_MODE,mActivity.getString(R.string.pref_camera_video_tnr_value_off),
                CameraSettings.KEY_NOISE_REDUCTION,mActivity.getString(R.string.pref_camera_noise_reduction_value_off));
        }
    }
	// frankie, 2017.08.15, add end
	
    @Override
    public void overrideSettings(final String... keyvalues) {
        super.overrideSettings(keyvalues);
        if (mListMenuContainer == null || mListMenu == null) { // frankie, 
            initializePopup();
        } else {
            overridePreferenceAccessibility();
        }
        mListMenu.overrideSettings(keyvalues);

		// frankie, 2017.08.15, add start 
		if(AGlobalConfig.config_module_VIDEO_MODULE_use_new_settings_en) {
	        if(mChusSettingsFragment == null) {
	            createSettingFragment(); // fm.commitAllowingStateLoss -> __lifeCycleCallback.onCreate -> overridePreferenceAccessibility_i
	        }
			else {
				overridePreferenceAccessibility_i();
			}
			if(mChusSettingsFragment != null) {
				mChusSettingsFragment.overrideSettings(keyvalues);
			}
		}
		// frankie, 2017.08.15, add end
		
    }

    private ListMenu.Listener _mListMenuListener = new ListMenu.Listener() {
        @Override
        public void onSettingChanged(ListPreference pref) {
            //VideoMenu.this.onSettingChanged(pref); // frankie, nothing to call this
        }
        @Override
        public void onPreferenceClicked(ListPreference pref, int y) {
            VideoMenu.this.onPreferenceClicked(pref, y);
        }
        @Override
        public void onListMenuTouched() {
            VideoMenu.this.onListMenuTouched();
        }
    };
    /*protected*/private void initializePopup() {
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //ListMenu popup1 = (ListMenu) inflater.inflate(R.layout.list_menu_1, null, false);
		ListMenu popup1 = null;
		ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.list_menu_1, null, false);
		popup1 = (ListMenu)viewGroup.findViewById(R.id.list_menu_1);
		
        //popup1.setSettingChangedListener(this);
        popup1.setSettingChangedListener(_mListMenuListener);
        String[] keys = mOtherKeys1;
        if (mActivity.isDeveloperMenuEnabled()) {
            keys = mOtherKeys2;
		}
        popup1.initialize(mPreferenceGroup, keys);
        mListMenu = popup1;
		mListMenuContainer = viewGroup;
        overridePreferenceAccessibility();
    }

    public void popupDismissed(boolean topPopupOnly) {
        // if the 2nd level popup gets dismissed
        if (mPopupStatus == POPUP_SECOND_LEVEL) {
            initializePopup();
            mPopupStatus = POPUP_FIRST_LEVEL;
            if (topPopupOnly) {
                mUI.showPopup(mListMenuContainer, 1, false);
            }
        } else {
            initializePopup();
        }
    }

    public void hideUI() {
        mFrontBackSwitcher.setVisibility(View.INVISIBLE);
        if(!AGlobalConfig.disable_video_pause) {
            pauseButton.setVisibility(View.VISIBLE);
        }
        //mFilterModeSwitcher.setVisibility(View.INVISIBLE);
        mSceneModeSwitcher.setVisibility(View.INVISIBLE); // frankie, add
    }

    public void showUI() {
        mFrontBackSwitcher.setVisibility(View.VISIBLE);
        pauseButton.setVisibility(View.GONE);
        //final IconListPreference pref = (IconListPreference) mPreferenceGroup.findPreference(CameraSettings.KEY_FILTER_MODE);
        //if (pref != null) {
        //    mFilterModeSwitcher.setVisibility(View.VISIBLE);
        //}
        mSceneModeSwitcher.setVisibility(View.VISIBLE); // frankie, add
    }
	public void showUI_for_special_capture_video() { // frankie, 2017.10.30, add
        mSceneModeSwitcher.setVisibility(View.VISIBLE);
		mFrontBackSwitcher.setVisibility(View.VISIBLE);
		pauseButton.setVisibility(View.GONE);
	}

	/////////////////////////////// ListMenu.Listener impl start
	//@Override
	public void onSettingChanged(ListPreference pref) {
		Log.v("yang", "+++ onSettingChanged: " + pref.getKey() + " +++ ");
		if (notSame(pref, CameraSettings.KEY_VIDEO_TIME_LAPSE_FRAME_INTERVAL,
				mActivity.getString(R.string.pref_video_time_lapse_frame_interval_default))) {
			ListPreference hfrPref = mPreferenceGroup.findPreference(CameraSettings.KEY_VIDEO_HIGH_FRAME_RATE);
			if (hfrPref != null && !"off".equals(hfrPref.getValue())) {
				RotateTextToast.makeText(mActivity, R.string.error_app_unsupported_hfr_selection,Toast.LENGTH_LONG).show();
			}
			setPreference(CameraSettings.KEY_VIDEO_HIGH_FRAME_RATE, "off");
		}
		if (notSame(pref, CameraSettings.KEY_VIDEO_HIGH_FRAME_RATE, "off")) {
			String defaultValue = mActivity.getString(R.string.pref_video_time_lapse_frame_interval_default);
			ListPreference lapsePref = mPreferenceGroup.findPreference(CameraSettings.KEY_VIDEO_TIME_LAPSE_FRAME_INTERVAL);
			if (lapsePref != null && !defaultValue.equals(lapsePref.getValue())) {
				RotateTextToast.makeText(mActivity, R.string.error_app_unsupported_hfr_selection,Toast.LENGTH_LONG).show();
			}
			setPreference(CameraSettings.KEY_VIDEO_TIME_LAPSE_FRAME_INTERVAL, defaultValue);
		}
		if (notSame(pref, CameraSettings.KEY_RECORD_LOCATION, "off")) {
			mActivity.requestLocationPermission();
		}
		super.onSettingChanged(pref);	// call VideoModule's CameraPreference.OnPreferenceChangedListener
		Log.v("yang", "--- onSettingChanged: " + pref.getKey() + " --- " );
	}
//    @Override // frankie, remove
//    // Hit when an item in the first-level popup gets selected, then bring up
//    // the second-level popup
//    public void onPreferenceClicked(ListPreference pref) {
//        onPreferenceClicked(pref, 0);
//    }

    //@Override
    // Hit when an item in the first-level popup gets selected, then bring up
    // the second-level popup
    public void onPreferenceClicked(ListPreference pref, int y) {
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ListSubMenu basic = (ListSubMenu) inflater.inflate(R.layout.list_sub_menu, null, false);
        basic.initialize(pref, y);
        basic.setSettingChangedListener(this);      // frankie, >>>
        mUI.removeLevel2();
        mListSubMenu = basic;
        if (mPopupStatus == POPUP_SECOND_LEVEL) {
            mUI.showPopup(mListSubMenu, 2, false);
        } else {
            mUI.showPopup(mListSubMenu, 2, true);
        }
        mPopupStatus = POPUP_SECOND_LEVEL;
    }
	//@Override
    public void onListMenuTouched() {
        mUI.removeLevel2();
        mPopupStatus = POPUP_FIRST_LEVEL;
    }
	/////////////////////////////// ListMenu.Listener impl start
	
    @Override // ListSubMenu.Listener IMPL		&& TimeIntervalPopup.Listener IMPL 
    // Hit when an item in the second-level popup gets selected
    public void onListPrefChanged(ListPreference pref) {
        onSettingChanged(pref);
        //closeView();
    }

    public void closeAllView() {
        if (mUI != null)
            mUI.removeLevel2();

        if (mListMenuContainer != null && mListMenu != null) {
            //animateSlideOut(mListMenu, 1);
			animateFadeOut(mListMenuContainer, 1); // frankie, add 
		}
        animateSlideOutPreviewMenu();
    }

    public void closeView() {
        if (mUI != null)
            mUI.removeLevel2();

        if (mListMenuContainer != null && mListMenu != null) {
            //animateSlideOut(mListMenu, 1);
			animateFadeOut(mListMenuContainer, 1); // frankie, add 
		}
    }

    // Return true if the preference has the specified key but not the value.
    private static boolean notSame(ListPreference pref, String key, String value) {
        return (key.equals(pref.getKey()) && !value.equals(pref.getValue()));
    }



    public int getOrientation() {
        return mUI == null ? 0 : mUI.getOrientation();
    }

	// frankie, 2017.08.15, add start 
	
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
        	VideoMenu.this.onSettingChanged(pref);
        }
    };
	private void initializeSettingsFragment2() {
		Log.v(TAG, "+ initializeSettingsFragment2");
		
        //LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //ListMenu popup1 = (ListMenu) inflater.inflate(R.layout.list_menu_1, null, false);
		//ListMenu popup1 = null;
		//ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.list_menu_1, null, false);
		//popup1 = (ListMenu)viewGroup.findViewById(R.id.list_menu_1);
		
        //popup1.setSettingChangedListener(this);
        //popup1.setSettingChangedListener(_mListMenuListener);

		SettingsFragment2 popup1 = mChusSettingsFragment;
		if(popup1 == null) {
			Log.v(TAG, "- initializeSettingsFragment2 not created at this time!");
			return ;
		}
		popup1.setSettingChangedListener(mSettingsFragment2_listener);
		
        String[] keys = mOtherKeys1;
        if (mActivity.isDeveloperMenuEnabled()) {
            keys = mOtherKeys2;
		}
        popup1.initialize(mPreferenceGroup, keys,mIsFrontCamera);
        //mListMenu = popup1;
		//mListMenuContainer = viewGroup;
        overridePreferenceAccessibility_i();
		
		Log.v(TAG, "- initializeSettingsFragment2 done");
	}

	private SettingsFragment2.SettingsFragment2LifeCycleCallback __lifeCycleCallback = new SettingsFragment2.SettingsFragment2LifeCycleCallback() {
		 @Override
		 public void onCreate(PreferenceFragment preferenceFragment) {
			if(mSettingsFragment2 == preferenceFragment) {
				mChusSettingsFragment = (SettingsFragment2) preferenceFragment; // save
				// we treat  SettingsFragment2 as ListMenu , so do the same as openFirstLevel() ->	initializePopup() -> 
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
//			 SettingsFragment2 settingsFragment = new SettingsFragment2(mActivity, __lifeCycleCallback);
             SettingsFragment2 settingsFragment = new SettingsFragment2();
			 settingsFragment.setArguments(fragment_args);
             settingsFragment.setLifeCycleCallback(__lifeCycleCallback);

			 mFragmentManager.beginTransaction()
					 .add(R.id.prefs_container_4video,settingsFragment,SettingsFragment2.FRAGMENT_TAG_4VIDEO)
					 .addToBackStack(null)
					 .commitAllowingStateLoss();
			 Log.v(SettingsFragment2.TAG, "--- SettingsFragment2 commit done!");
			 mSettingsFragment2 = settingsFragment;

//			   Fragment fragment = mFragmentManager.findFragmentById(R.id.settings_fragment2);
//			   if(fragment instanceof  SettingsFragment2) {
//				   mSettingsFragment2 = (SettingsFragment2)fragment;
//			   }
		 }
	 }
	 private void showSettingFragment(){
//		  // rm old
//		  Fragment fragment = mFragmentManager.findFragmentByTag(SettingsFragment2.FRAGMENT_TAG);
//		  if(fragment != null && fragment instanceof SettingsFragment2) {
//			  mFragmentManager.beginTransaction()
//					  .remove(fragment)
//					  .commitAllowingStateLoss();
//		  }

		// create new
		 createSettingFragment();
		 initializeSettingsFragment2(); // to init every time, if first , mChusSettingsFragment == null postpone init when fragment is created !

		 //mSettingsFragmentContainerLayout.setBackgroundColor(Color.parseColor("#fff3f8fc"));
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
		 vp.setListener(new AnimatorListener() {
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
		PreferenceFragment f = (PreferenceFragment)m.findFragmentByTag(SettingsFragment2.FRAGMENT_TAG_4VIDEO);
		if(mSettingsLayout != null && f != null ){
//		   m.beginTransaction()
//				   .remove(f)
//				   .commitAllowingStateLoss();		 // i keep this fragment

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
				vp.setListener(new AnimatorListener() {
					@Override
					public void onAnimationStart(Animator animation) {
					}
					@Override
					public void onAnimationRepeat(Animator animation) {
					}
					@Override
					public void onAnimationEnd(Animator animation) {
						v.setVisibility(View.INVISIBLE);
					}
					@Override
					public void onAnimationCancel(Animator animation) {
						v.setVisibility(View.INVISIBLE);
					}
				});
				vp.start();
				return true;
			}
		}
		return false;
	 }
     public boolean isShowSettingsFragment(){
         return mChusSettingsFragment == null ? false:true;
      }
	public void openSettings_i() {
		showSettingFragment();
	}
	// frankie, 2017.08.15, add end


	
}

