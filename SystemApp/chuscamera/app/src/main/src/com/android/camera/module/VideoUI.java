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
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera.Face;
import android.hardware.Camera.Parameters;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.camera.AGlobalConfig;
import com.android.camera.AnimationManager;
import com.android.camera.CameraActivity;
import com.android.camera.CameraManager;
import com.android.camera.CameraManager.CameraProxy;
import com.android.camera.CameraPreference;
import com.android.camera.ComboPreferences;
import com.android.camera.PauseButton;
import com.android.camera.PhotoUI.SurfaceTextureSizeChangedListener;
import com.android.camera.PreferenceGroup;
import com.android.camera.PreviewGestures;
import com.android.camera.ShutterButton;
import com.android.camera.module.ui.PieMenuRenderer;
import com.android.camera.ui.CameraControls;
import com.android.camera.ui.CameraRootView;
import com.android.camera.ui.FaceView;
import com.android.camera.ui.ListSubMenu;
import com.android.camera.ui.PieRenderer;
import com.android.camera.ui.RenderOverlay;
import com.android.camera.ui.RotateImageView;
import com.android.camera.ui.RotateLayout;
import com.android.camera.ui.RotateTextToast;
import com.android.camera.ui.ZoomRenderer;
import com.android.camera.util.CameraUtil;
import com.toro.camera.R;

import java.util.List;

import javax.microedition.khronos.egl.EGLContext;

//import android.view.View.OnLayoutChangeListener;
//import com.android.camera.CameraPreference.OnPreferenceChangedListener;

public class VideoUI implements 
		//PieRenderer.PieListener, // frankie, rm
        PreviewGestures.SingleTapListener,
        CameraRootView.MyDisplayListener,
        //SurfaceHolder.Callback,
        PauseButton.OnPauseButtonListener,
        CameraManager.CameraFaceDetectionCallback {
    private static final String TAG = "CAM_VideoUI";

    private static final boolean MENU_BACKGROUND_VIEW_ENABLE = false; // true;

    // module fields
    private CameraActivity mActivity;
    private View mRootView;
    private SurfaceHolder mSurfaceHolder;
    // An review image having same size as preview. It is displayed when
    // recording is stopped in capture intent.
    private ImageView mReviewImage;
    private View mReviewCancelButton;
    private View mReviewDoneButton;
    private View mReviewPlayButton;
    private ShutterButton mShutterButton;
    private PauseButton mPauseButton;
    //private ModuleSwitcher mSwitcher;
    private TextView mRecordingTimeView;
    private ImageView redDotImg;
    private ObjectAnimator redObjectAnimator;
    private LinearLayout mLabelsLinearLayout;
    private View mTimeLapseLabel;
    private RenderOverlay mRenderOverlay;
    //private PieRenderer mPieRenderer;
    private PieMenuRenderer mPieRenderer; // frankie, add
    private VideoMenu mVideoMenu;
    private CameraControlsVideo mCameraControls;
    private SettingsPopup mPopup;
    private ZoomRenderer mZoomRenderer;
    private PreviewGestures mGestures;
    private View mMenuButton;
    //private OnScreenIndicators mOnScreenIndicators;
    //private RotateLayout mRecordingTimeRect;
    private ViewGroup mRecordingTimeRect; // frankie, change
    private boolean mRecordingStarted = false;
    private VideoController mController;
    private int mZoomMax;
    private List<Integer> mZoomRatios;
    private ImageView mThumbnail;
    private View mFlashOverlay;
    private boolean mOrientationResize;
    private boolean mPrevOrientationResize;
    private boolean mIsTimeLapse = false;
    //private RotateLayout mMenuLayout;
    private ViewGroup mMenuLayout;
    private View mMenuLayoutView = null; // frankie, debug
    //private RotateLayout mSubMenuLayout;
    private ViewGroup mSubMenuLayout;
    private LinearLayout mPreviewMenuLayout;

    private View mPreviewCover;
    private SurfaceView mSurfaceView = null;
    private int mMaxPreviewWidth = 0;
    private int mMaxPreviewHeight = 0;
    private float mAspectRatio = 4f / 3f;
    private boolean mAspectRatioResize;
    private final AnimationManager mAnimationManager;
    private boolean mUIhidden = false;
    private int mPreviewOrientation = -1;
    private int mOrientation;

    private int mScreenRatio = CameraUtil.RATIO_UNKNOWN;
    private int mTopMargin = 0;
    private int mBottomMargin = 0;
    private RotateImageView mMuteButton;

    //Face detection
    private FaceView mFaceView;
    private SurfaceTextureSizeChangedListener mSurfaceTextureSizeListener;
    private float mSurfaceTextureUncroppedWidth;
    private float mSurfaceTextureUncroppedHeight;

    MyFrameLayoutVideo myFrameLayout;    // frankie, add
    private SurfaceTexture mySurfaceTexture;

    int mBubbleSeekProgress = 0;    // frankie, add

    public enum SURFACE_STATUS {
        HIDE,
        SURFACE_VIEW;
    }

    ;

    public void showPreviewCover() {
        Log.v(TAG, "showPreviewCover"); // frankie,
        if(true) {
			mPreviewCover.setAlpha(0.00001f);
			mPreviewCover.setVisibility(View.VISIBLE);
			return ;
		}
//        mPreviewCover.setVisibility(View.VISIBLE);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mPreviewCover, "alpha", 0.00001f, 1f);
        alphaAnimator.setDuration(300);
        alphaAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                Log.v(TAG, "showPreviewCover,onAnimationCancel");
                mPreviewCover.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.v(TAG, "showPreviewCover,onAnimationEnd");
                mPreviewCover.setVisibility(View.VISIBLE);
            }
        });
        alphaAnimator.start();
    }

    public void hidePreviewCover() {
        Log.v(TAG, "hidePreviewCover"); // frankie,
        if(true) {
			mPreviewCover.setAlpha(0.00001f);
			mPreviewCover.setVisibility(View.GONE);
			return ;
		}
        if (mPreviewCover != null && mPreviewCover.getVisibility() != View.GONE) {
//            mPreviewCover.setVisibility(View.GONE);
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mPreviewCover, "alpha", 1f, 0.00001f);
            alphaAnimator.setDuration(300);
            alphaAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    Log.v(TAG, "hidePreviewCover,onAnimationCancel");
                    mPreviewCover.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.v(TAG, "hidePreviewCover,onAnimationEnd");
                    mPreviewCover.setVisibility(View.GONE);
                }
            });
            alphaAnimator.start();
        }
    }

    public boolean isPreviewCoverVisible() {
        if ((mPreviewCover != null) &&
                (mPreviewCover.getVisibility() == View.VISIBLE)) {
            return true;
        } else {
            return false;
        }
    }

    private class SettingsPopup extends PopupWindow {
        public SettingsPopup(View popup) {
            super(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            setOutsideTouchable(true);
            setFocusable(true);
            popup.setVisibility(View.VISIBLE);
            setContentView(popup);
            showAtLocation(mRootView, Gravity.CENTER, 0, 0);
        }
        public void dismiss(boolean topLevelOnly) {
            super.dismiss();
            popupDismissed();
            showUI();
            // mVideoMenu.popupDismissed(topLevelOnly);

            // Switch back into fullscreen/lights-out mode after popup
            // is dimissed.
            mActivity.setSystemBarsVisibility(false);
        }
        @Override
        public void dismiss() {
            // Called by Framework when touch outside the popup or hit back key
            dismiss(true);
        }
    }

    public synchronized void applySurfaceChange(SURFACE_STATUS status) {
        if (status == SURFACE_STATUS.HIDE) {
            mSurfaceView.setVisibility(View.GONE);
            return;
        }
        mSurfaceView.setVisibility(View.VISIBLE);
    }

	private Handler mOnLayoutChangeHandler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			if(message.what == 0) {
				layoutPreview(mAspectRatio);
			}
		}
	};
    public VideoUI(CameraActivity activity, VideoController controller, View parent) {
        mActivity = activity;
        mController = controller;
        mRootView = parent;
        mActivity.getLayoutInflater().inflate(R.layout.video_module_1, (ViewGroup) mRootView, true);
        mPreviewCover = mRootView.findViewById(R.id.preview_cover);
        // display the view
        mSurfaceView = (SurfaceView) mRootView.findViewById(R.id.mdp_preview_content);
        mSurfaceView.setVisibility(View.VISIBLE);
        mSurfaceHolder = mSurfaceView.getHolder();
        //mSurfaceHolder.addCallback(this);
        SurfaceHolder.Callback surfaceHolder_callback_ = new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.v(TAG, "surfaceCreated");
                mSurfaceHolder = holder;
                mController.onPreviewUIReady();
                mActivity.updateThumbnail_ui_ready(mThumbnail);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.v(TAG, "surfaceChanged: width = " + width + ", height = " + height);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.v(TAG, "surfaceDestroyed");
                mSurfaceHolder = null;
                mController.onPreviewUIDestroyed();
            }
        };
        if (AGlobalConfig.config_module_VIDEO_MODULE_PREVIEW_TYPE == 0) {
            mSurfaceHolder.addCallback(surfaceHolder_callback_);
        }

        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        Log.v(TAG, "Using mdp_preview_content (MDP path)");
        View surfaceContainer = mRootView.findViewById(R.id.preview_container);

        View.OnLayoutChangeListener __mOnLayoutChangeListener = new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
				Log.v(TAG, "+ onLayoutChange");
                int width = right - left;
                int height = bottom - top;

				Log.v(TAG, "width*height = " + width + "*" + height);
                tryToCloseSubList();
                if (mMaxPreviewWidth == 0 && mMaxPreviewHeight == 0) {
                    mMaxPreviewWidth = width;
                    mMaxPreviewHeight = height;
                }

                int orientation = mActivity.getResources().getConfiguration().orientation;
				Log.v(TAG, "  orientation:" + orientation + " portrait:" + Configuration.ORIENTATION_PORTRAIT
						+ " landscape:" + Configuration.ORIENTATION_LANDSCAPE);
                if ((orientation == Configuration.ORIENTATION_PORTRAIT && width > height)
                        || (orientation == Configuration.ORIENTATION_LANDSCAPE && width < height)) {
                    // The screen has rotated; swap SurfaceView width & height
                    // to ensure correct preview
                    int oldWidth = width;
                    width = height;
                    height = oldWidth;
                    Log.d(TAG, "Swapping SurfaceView width & height dimensions");
                    if (mMaxPreviewWidth != 0 && mMaxPreviewHeight != 0) {
                        int temp = mMaxPreviewWidth;
                        mMaxPreviewWidth = mMaxPreviewHeight;
                        mMaxPreviewHeight = temp;
                    }
                }
				Log.v(TAG, "mMaxPreview Width*Height = " + mMaxPreviewWidth + " * " + mMaxPreviewHeight);
				if(myFrameLayout != null) {
					myFrameLayout.setMaxPreviewSize(mMaxPreviewWidth, mMaxPreviewHeight);
				}
					
				Log.v(TAG, " mOrientationResize:" + mOrientationResize + " mPrevOrientationResize:" + mPrevOrientationResize);
				Log.v(TAG, " mAspectRatioResize:" + mAspectRatioResize);
                if (mOrientationResize != mPrevOrientationResize
                        || mAspectRatioResize) {
                    //layoutPreview(mAspectRatio);
					mOnLayoutChangeHandler.sendEmptyMessageDelayed(0, 5); // Must post to handler
                    mAspectRatioResize = false;
                }
				Log.v(TAG, "- onLayoutChange");
            }
        };
        if (AGlobalConfig.config_module_VIDEO_MODULE_PREVIEW_TYPE == 0) {
            surfaceContainer.addOnLayoutChangeListener(__mOnLayoutChangeListener);
            //mSurfaceView.addOnLayoutChangeListener(__mOnLayoutChangeListener);
        }

        myFrameLayout = (MyFrameLayoutVideo) mRootView.findViewById(R.id.my_framelayout_1); // frankie, add
        if (myFrameLayout != null) {
            if (AGlobalConfig.config_module_VIDEO_MODULE_PREVIEW_TYPE == 1) {
                //View surfaceContainer_1 = mRootView.findViewById(R.id.preview_container_1);
                //surfaceContainer_1.addOnLayoutChangeListener(__mOnLayoutChangeListener);
				myFrameLayout.addOnLayoutChangeListener(__mOnLayoutChangeListener);	// watch myFrameLayout is enough

				MyFrameLayoutVideo.Callback callback_ = new MyFrameLayoutVideo.Callback() {
					@Override
					public void surfaceCreated(SurfaceTexture surfaceTexture) {
						Log.v(TAG, "---------- surfaceCreated");
						mySurfaceTexture = surfaceTexture;
						mController.onPreviewUIReady();
						mActivity.updateThumbnail_ui_ready(mThumbnail);
					}
				
					@Override
					public void surfaceChanged(SurfaceTexture surfaceTexture, int format, int width, int height) {
						Log.v(TAG, "---------- surfaceChanged: width =" + width + ", height = " + height);
						//Rect r = myFrameLayout.getPreviewTextviewRect();  // frankie, rm not used
						//if (r != null) {
							///mController.onPreviewRectChanged(CameraUtil.rectFToRect(r));
						//}
					}
				
					@Override
					public void surfaceDestroyed(SurfaceTexture surfaceTexture) {
						Log.v(TAG, "---------- surfaceDestroyed");
						mySurfaceTexture = null;
						mController.onPreviewUIDestroyed();
					}
				};
                myFrameLayout.setCallback(callback_);
            }
            if (AGlobalConfig.config_module_VIDEO_MODULE_PREVIEW_TYPE == 0) {
                myFrameLayout.setCallback(new MyFrameLayoutVideo.Callback() { // dummy callback
                    @Override
                    public void surfaceCreated(SurfaceTexture surfaceTexture) {
                        Log.v(TAG, "---------- surfaceCreated");
                    }

                    @Override
                    public void surfaceChanged(SurfaceTexture surfaceTexture, int format, int width, int height) {
                        Log.v(TAG, "---------- surfaceChanged: width =" + width + ", height = " + height);
                    }

                    @Override
                    public void surfaceDestroyed(SurfaceTexture surfaceTexture) {
                        Log.v(TAG, "---------- surfaceDestroyed");
                    }
                });
            }
        }
        if (AGlobalConfig.config_module_VIDEO_MODULE_PREVIEW_TYPE == 0) { // for debug MyFrameLayoutVideo
            //ViewGroup vg = (ViewGroup)mRootView;
            //vg.removeView(myFrameLayout);
            //myFrameLayout.setVisibility(View.INVISIBLE);

            myFrameLayout.setAlpha(0.8f);
            Button testButton = (Button) mRootView.findViewById(R.id.aspect_ratio_test);
            testButton.setVisibility(View.VISIBLE);
            if (testButton != null) {
                testButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myFrameLayout.test1();
                    }
                });
            }
        }

        mBubbleSeekProgress = 0; // video shut the beauty level
        if (myFrameLayout != null) {    // set initial beauty level
            myFrameLayout.setBeautyLevel(mBubbleSeekProgress);
        }


        mFlashOverlay = mRootView.findViewById(R.id.flash_overlay);
        mShutterButton = (ShutterButton) mRootView.findViewById(R.id.shutter_button);

//        mSwitcher = (ModuleSwitcher) mRootView.findViewById(R.id.camera_switcher);
//        mSwitcher.setCurrentIndex(ModuleSwitcher.VIDEO_MODULE_INDEX);
//        mSwitcher.setSwitchListener(mActivity);
//        mSwitcher.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mSwitcher.showPopup();
//                mSwitcher.setOrientation(mOrientation, false);
//            }
//        });

        mMuteButton = (RotateImageView) mRootView.findViewById(R.id.mute_button);
        if (AGlobalConfig.config_module_VIDEO_MODULE_mute_button_en) {
            mMuteButton.setVisibility(View.VISIBLE);
        }
        if (!((VideoModule) mController).isAudioMute()) {
            mMuteButton.setImageResource(R.drawable.ic_unmuted_button);
        } else {
            mMuteButton.setImageResource(R.drawable.ic_muted_button);
        }
        mMuteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEnabled = !((VideoModule) mController).isAudioMute();
                ((VideoModule) mController).setMute(isEnabled, true);
                if (!isEnabled)
                    mMuteButton.setImageResource(R.drawable.ic_unmuted_button);
                else
                    mMuteButton.setImageResource(R.drawable.ic_muted_button);
            }
        });

        initializeMiscControls();
        initializeControlByIntent();
        initializeOverlay();
        initializePauseButton();

        mCameraControls = (CameraControlsVideo) mRootView.findViewById(R.id.camera_controls);
        ViewStub faceViewStub = (ViewStub) mRootView
                .findViewById(R.id.face_view_stub);
        if (faceViewStub != null) {
            faceViewStub.inflate();
            mFaceView = (FaceView) mRootView.findViewById(R.id.face_view);
            setSurfaceTextureSizeChangedListener(mFaceView);
        }
        mAnimationManager = new AnimationManager();
        mOrientationResize = false;
        mPrevOrientationResize = false;

        Point size = new Point();
        mActivity.getWindowManager().getDefaultDisplay().getSize(size);
        mScreenRatio = CameraUtil.determineRatio(size.x, size.y);
        Log.v(TAG, "mScreenRatio:" + mScreenRatio + " getDefaultDisplay size:" + size.x + "*" + size.y);    // 0 - unknown, 1 - 16:9 , 2- 4:3, 3 - 3:2
        // 0, 1080*1776
        calculateMargins(size);        // frankie,
        mCameraControls.setMargins(mTopMargin, mBottomMargin);

//        ((ViewGroup) mRootView).removeView(mRecordingTimeRect);

		hideUI_1(); // frankie, add
    }

    private void calculateMargins(Point size) {
        int l = size.x > size.y ? size.x : size.y;
        int tm = mActivity.getResources().getDimensionPixelSize(R.dimen.preview_top_margin);
        int bm = mActivity.getResources().getDimensionPixelSize(R.dimen.preview_bottom_margin);
        mTopMargin = l / 4 * tm / (tm + bm);
        mBottomMargin = l / 4 - mTopMargin;
    }

    private void initializeMiscControls() {
        mReviewImage = (ImageView) mRootView.findViewById(R.id.review_image);
		
//        mShutterButton.setImageResource(R.drawable.btn_new_shutter_video);
        mShutterButton.setImageResource(R.drawable.chus_shutter_button_video_start); // frankie, add
        mShutterButton.setImageResource(R.drawable.toro_shutter_button_video_start); // liujia add
        mShutterButton.setOnShutterButtonListener(mController);
        mShutterButton.setVisibility(View.VISIBLE);
        mShutterButton.requestFocus();
        mShutterButton.enableTouch(true);
        mRecordingTimeView = (TextView) mRootView.findViewById(R.id.recording_time);
        redDotImg = (ImageView) mRootView.findViewById(R.id.reddot_img);
        redObjectAnimator = ObjectAnimator.ofFloat(redDotImg, "alpha", 1f, 0f);
        //mRecordingTimeRect = (RotateLayout) mRootView.findViewById(R.id.recording_time_rect);
        mRecordingTimeRect = (ViewGroup) mRootView.findViewById(R.id.recording_time_rect);
        //mTimeLapseLabel = mRootView.findViewById(R.id.time_lapse_label);
        // The R.id.labels can only be found in phone layout.
        // That is, mLabelsLinearLayout should be null in tablet layout.
        mLabelsLinearLayout = (LinearLayout) mRootView.findViewById(R.id.labels);
    }

    private void initializeControlByIntent() {
        mMenuButton = mRootView.findViewById(R.id.menu);
        mMenuButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.setSlideSwitcherShow(false, true); // frankie, add
                if (mVideoMenu != null) {
					if(AGlobalConfig.config_module_VIDEO_MODULE_use_new_settings_en) {
						mVideoMenu.openSettings_i();
					} else {
                    	mVideoMenu.openFirstLevel();
					}
                }
            }
        });

		mCameraControls = (CameraControlsVideo) mRootView.findViewById(R.id.camera_controls);
        //mOnScreenIndicators = new OnScreenIndicators(mActivity, mRootView.findViewById(R.id.on_screen_indicators));
        //mOnScreenIndicators.resetToDefault();
        if (mController.isVideoCaptureIntent()) {
            hideSwitcher();
            mActivity.getLayoutInflater().inflate(R.layout.review_module_control, (ViewGroup) mCameraControls);
            // Cannot use RotateImageView for "done" and "cancel" button because
            // the tablet layout uses RotateLayout, which cannot be cast to
            // RotateImageView.
            mReviewDoneButton = mRootView.findViewById(R.id.btn_done);
            mReviewCancelButton = mRootView.findViewById(R.id.btn_cancel);
            mReviewPlayButton = mRootView.findViewById(R.id.btn_play);
//            mReviewCancelButton.setVisibility(View.VISIBLE);
            mReviewDoneButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mController.onReviewDoneClicked(v);
                }
            });
            mReviewCancelButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mController.onReviewCancelClicked(v);
                }
            });
            mReviewPlayButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mController.onReviewPlayClicked(v);
                }
            });
        }


		
    }

	private PieMenuRenderer.PieListener _pieListener = new PieMenuRenderer.PieListener() {
		// PieListener start
	    @Override
	    public void onPieOpened(int centerX, int centerY) {
	        setSwipingEnabled(false);
	        // Close module selection menu when pie menu is opened.
	        //mSwitcher.closePopup();
	    }
	    @Override
	    public void onPieClosed() {
	        setSwipingEnabled(true);
	    }
	    // PieListener end
    };
    
    private void initializeOverlay() {
        mRenderOverlay = (RenderOverlay) mRootView.findViewById(R.id.render_overlay);
        if (mPieRenderer == null) {
            mPieRenderer = new PieRenderer(mActivity);
            // mVideoMenu = new VideoMenu(mActivity, this, mPieRenderer);
            //mPieRenderer.setPieListener(this);
            mPieRenderer.setPieListener(_pieListener); // frankie, mv here
        }
        if (mVideoMenu == null) {
            mVideoMenu = new VideoMenu(mActivity, this);
        }
        mRenderOverlay.addRenderer(mPieRenderer);
        if (mZoomRenderer == null) {
            mZoomRenderer = new ZoomRenderer(mActivity);
        }
        mRenderOverlay.addRenderer(mZoomRenderer);
        if (mGestures == null) {
            mGestures = new PreviewGestures(mActivity, this, mZoomRenderer, mPieRenderer);
            mRenderOverlay.setGestures(mGestures);
        }
        mGestures.setVideoMenu(mVideoMenu);

        mGestures.setRenderOverlay(mRenderOverlay);

//        if (!mActivity.isSecureCamera()) {
            mThumbnail = (ImageView) mRootView.findViewById(R.id.preview_thumb);
            mThumbnail.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(TAG, "mThumbnail onClick ");
                    // Do not allow navigation to filmstrip during video recording
                    if (!mRecordingStarted && !CameraControls.isAnimating()) {
                        mActivity.gotoGallery();
                    }else{
                        mController.onSingleTapUp(null, 0, 0);
                    }
                }
            });
//        }
    }

    private void initializePauseButton() {
        mPauseButton = (PauseButton) mRootView.findViewById(R.id.pause_video);//R.id.video_pause
        mPauseButton.setOnPauseButtonListener(this);
    }

    public void onResume_1() {
        if (myFrameLayout != null) {
            myFrameLayout.onResume();
        }
    }

    public void onPause_1() { // frankie, add
    	mOnLayoutChangeHandler.removeMessages(0);
		
        if (myFrameLayout != null) {
            myFrameLayout.onPause();
        }
		mUIhidden = false;	// frankie, fix
    }

    public void cameraOrientationPreviewResize(boolean orientation) {
        mPrevOrientationResize = mOrientationResize;
        mOrientationResize = orientation;
    }

    public void setSurfaceTextureSizeChangedListener(SurfaceTextureSizeChangedListener listener) {
        mSurfaceTextureSizeListener = listener;
    }

//    public void initializeSurfaceView() {
//        if (mSurfaceView == null) {
//            mSurfaceView = new SurfaceView(mActivity);
//            ((ViewGroup) mRootView).addView(mSurfaceView, 0);
//            mSurfaceHolder = mSurfaceView.getHolder();
//            mSurfaceHolder.addCallback(this);
//        }
//    }


    public void setPreviewSize(int width, int height) {
        Log.v(TAG, "+ setPreviewSize: " + width + " * " + height);
        if (width == 0 || height == 0) {
            Log.w(TAG, "Preview size should not be 0.");
            Log.v(TAG, "- setPreviewSize 0");
            return;
        }
        float ratio;
        if (width > height) {
            ratio = (float) width / height;
        } else {
            ratio = (float) height / width;
        }
		setAspectRatio(ratio);
/*
        if (mOrientationResize &&
                mActivity.getResources().getConfiguration().orientation
                        != Configuration.ORIENTATION_PORTRAIT) {
            ratio = 1 / ratio;
        }

        if (ratio != mAspectRatio) {
            mAspectRatioResize = true;
            mAspectRatio = ratio;
        }

        mCameraControls.setPreviewRatio(mAspectRatio, false);
        layoutPreview((float) ratio);
*/
        Log.v(TAG, "- setPreviewSize done");
    }
    public void setAspectRatio(float ratio) {
		Log.v(TAG, "+ setAspectRatio, ratio:" + ratio 
			+ " orientation:" + mActivity.getResources().getConfiguration().orientation); // portrait(1) landscape(2)
        if (mOrientationResize &&
                mActivity.getResources().getConfiguration().orientation
                        != Configuration.ORIENTATION_PORTRAIT) {
            ratio = 1 / ratio;
        }

        if (ratio != mAspectRatio) {
            mAspectRatioResize = true;
            mAspectRatio = (float) ratio;
        }
        if (AGlobalConfig.config_module_VIDEO_MODULE_PREVIEW_TYPE == 1) {
            if (myFrameLayout != null) { // frankie, hook
                myFrameLayout.setAspectRatio(ratio);
            }
        }

        mCameraControls.setPreviewRatio(mAspectRatio, false);
        layoutPreview((float) ratio);
		Log.v(TAG, "- setAspectRatio, done");
    }

    private void layoutPreview(float ratio) {
        Log.v(TAG, "+ layoutPreview:" + ratio);
        if (AGlobalConfig.config_module_VIDEO_MODULE_PREVIEW_TYPE == 1) {
            if (myFrameLayout != null) { // frankie, hook
                myFrameLayout.layoutPreview(ratio);
            }
        }

        FrameLayout.LayoutParams lp = null;

        float scaledTextureWidth, scaledTextureHeight;
        int rotation = CameraUtil.getDisplayRotation(mActivity);
        mScreenRatio = CameraUtil.determineRatio(ratio);
		Log.v(TAG, "  getDisplayRotation : " + rotation);
        if (mScreenRatio == CameraUtil.RATIO_16_9
                && CameraUtil.determinCloseRatio(ratio) == CameraUtil.RATIO_4_3) {
            Log.v(TAG, "screen 16:9 where ration 4:3 hit ");
            int l = (mTopMargin + mBottomMargin) * 4;
            int s = l * 9 / 16;
            switch (rotation) {
                case 90:
                    lp = new FrameLayout.LayoutParams(l * 3 / 4, s);
                    lp.setMargins(mTopMargin, 0, mBottomMargin, 0);
                    scaledTextureWidth = l * 3 / 4;
                    scaledTextureHeight = s;
                    break;
                case 180:
                    lp = new FrameLayout.LayoutParams(s, l * 3 / 4);
                    lp.setMargins(0, mBottomMargin, 0, mTopMargin);
                    scaledTextureWidth = s;
                    scaledTextureHeight = l * 3 / 4;
                    break;
                case 270:
                    lp = new FrameLayout.LayoutParams(l * 3 / 4, s);
                    lp.setMargins(mBottomMargin, 0, mTopMargin, 0);
                    scaledTextureWidth = l * 3 / 4;
                    scaledTextureHeight = s;
                    break;
                default:
                    lp = new FrameLayout.LayoutParams(s, l * 3 / 4);
                    lp.setMargins(0, mTopMargin, 0, mBottomMargin);
                    scaledTextureWidth = s;
                    scaledTextureHeight = l * 3 / 4;
                    break;
            }
        } else {
        	Log.v(TAG, "screen 16:9 where ration 4:3 NOT hit ! ");
            float width = mMaxPreviewWidth, height = mMaxPreviewHeight;
            if (width == 0 || height == 0) {
				Log.v(TAG, "- layoutPreview, parent layout not ready!");
				return;
			}
            if (mScreenRatio == CameraUtil.RATIO_4_3)
                height -= (mTopMargin + mBottomMargin);
            if (mOrientationResize) {
                scaledTextureWidth = height * mAspectRatio;
                if (scaledTextureWidth > width) {
                    scaledTextureWidth = width;
                    scaledTextureHeight = scaledTextureWidth / mAspectRatio;
                } else {
                    scaledTextureHeight = height;
                }
            } else {
                if (width > height) {
                    if (Math.max(width, height * mAspectRatio) > width) {
                        scaledTextureWidth = width;
                        scaledTextureHeight = width / mAspectRatio;
                    } else {
                        scaledTextureWidth = height * mAspectRatio;
                        scaledTextureHeight = height;
                    }
                } else {
                    if (Math.max(height, width * mAspectRatio) > height) {
                        scaledTextureWidth = height / mAspectRatio;
                        scaledTextureHeight = height;
                    } else {
                        scaledTextureWidth = width;
                        scaledTextureHeight = width * mAspectRatio;
                    }
                }
            }

            Log.v(TAG, "setTransformMatrix: scaledTextureWidth = " + scaledTextureWidth
                    + ", scaledTextureHeight = " + scaledTextureHeight);

            if (((rotation == 0 || rotation == 180) && scaledTextureWidth > scaledTextureHeight)
                    || ((rotation == 90 || rotation == 270)
                    && scaledTextureWidth < scaledTextureHeight)) {
                lp = new FrameLayout.LayoutParams((int) scaledTextureHeight,
                        (int) scaledTextureWidth, Gravity.CENTER);
            } else {
                lp = new FrameLayout.LayoutParams((int) scaledTextureWidth,
                        (int) scaledTextureHeight, Gravity.CENTER);
            }
        }

        if (mSurfaceTextureUncroppedWidth != scaledTextureWidth ||
                mSurfaceTextureUncroppedHeight != scaledTextureHeight) {
            mSurfaceTextureUncroppedWidth = scaledTextureWidth;
            mSurfaceTextureUncroppedHeight = scaledTextureHeight;
            if (mSurfaceTextureSizeListener != null) {
                mSurfaceTextureSizeListener.onSurfaceTextureSizeChanged(
                        (int) mSurfaceTextureUncroppedWidth,
                        (int) mSurfaceTextureUncroppedHeight);
                Log.d(TAG, "mSurfaceTextureUncroppedWidth=" + mSurfaceTextureUncroppedWidth
                        + "mSurfaceTextureUncroppedHeight=" + mSurfaceTextureUncroppedHeight);
            }
        }


        //if(lp != null) {
        mSurfaceView.setLayoutParams(lp);
        //mSurfaceView.requestLayout();

        if (mFaceView != null) {
            mFaceView.setLayoutParams(lp);
        }
        //}
        Log.v(TAG, "- layoutPreview done");
    }

    /**
     * Starts a flash animation
     */
    public void animateFlash() {
        mAnimationManager.startFlashAnimation(mFlashOverlay);
    }

    /**
     * Starts a capture animation
     */
    public void animateCapture() {
        Bitmap bitmap = null;
        animateCapture(bitmap);
    }

    /**
     * Starts a capture animation
     *
     * @param bitmap the captured image that we shrink and slide in the animation
     */
    public void animateCapture(Bitmap bitmap) {
        if (bitmap == null) {
            Log.e(TAG, "No valid bitmap for capture animation.");
            return;
        }
        mActivity.updateThumbnail(bitmap);
        mAnimationManager.startCaptureAnimation(mThumbnail);
    }

    /**
     * Cancels on-going animations
     */
    public void cancelAnimations() {
        mAnimationManager.cancelAnimations();
    }

    public void hideUI() {
		Log.v(TAG, "hideUI, mUIhidden:" + mUIhidden);
        //mSwitcher.closePopup();
        if (mUIhidden)
            return;
        mUIhidden = true;
        mCameraControls.hideUI();
    }

    public void showUI() {
		Log.v(TAG, "showUI, mUIhidden:" + mUIhidden);
        if (!mUIhidden || (mVideoMenu != null && mVideoMenu.isMenuBeingShown()))
            return;
        mUIhidden = false;
        mCameraControls.showUI();
    }

	// frankie, 2017.08.25, add start
	public void hideUI_1() {
		Log.v(TAG, "hideUI_1");
		hideUI();
	}
	public void showUI_1() {
		Log.v(TAG, "showUI_1");
		showUI();
	}
	// frankie, 2017.08.25, add end
		
    public boolean arePreviewControlsVisible() {
        return !mUIhidden;
    }

    public void hideSwitcher() {
        //mSwitcher.closePopup();
        //mSwitcher.setVisibility(View.INVISIBLE);
    }

    public void showSwitcher() {
        //mSwitcher.setVisibility(View.VISIBLE);
    }

    public void setSwitcherIndex() {
        //mSwitcher.setCurrentIndex(ModuleSwitcher.VIDEO_MODULE_INDEX);
    }

    public boolean isShowFragment(){
        return mVideoMenu.isShowSettingsFragment();
    }

    public void dismissPopup(boolean topLevelOnly) {
        // In review mode, we do not want to bring up the camera UI
        if (mController.isInReviewMode()) return;
        if (mPopup != null) {
            mPopup.dismiss(topLevelOnly);
        }
    }
    public boolean collapseCameraControls() {
        boolean ret = false;
        //mSwitcher.closePopup();
        if (mVideoMenu != null) {
            mVideoMenu.closeAllView();
        }
        if (mPopup != null) {
            dismissPopup(false);
            ret = true;
        }
        return ret;
    }
    public boolean removeTopLevelPopup() {
        if (mPopup != null) {
            dismissPopup(true);
            return true;
        }
        return false;
    }
    public void enableCameraControls(boolean enable) {
        if (mGestures != null) {
            mGestures.setZoomOnly(!enable);
        }
        if (mPieRenderer != null && mPieRenderer.showsItems()) {
            mPieRenderer.hide();
        }
    }
    public void initDisplayChangeListener() {
        ((CameraRootView) mRootView).setDisplayChangeListener(this);
    }

    public void setDisplayOrientation(int orientation) {
        if (mFaceView != null) {
            mFaceView.setDisplayOrientation(orientation);
        }

        if ((mPreviewOrientation == -1 || mPreviewOrientation != orientation)
                && mVideoMenu != null && mVideoMenu.isPreviewMenuBeingShown()) {
            dismissSceneModeMenu();
            mVideoMenu.addModeBack();
        }
        mPreviewOrientation = orientation;
    }

    public void removeDisplayChangeListener() {
        ((CameraRootView) mRootView).removeDisplayChangeListener();
    }

    // no customvideo?
    public void overrideSettings(final String... keyvalues) {
        if (mVideoMenu != null) {
            mVideoMenu.overrideSettings(keyvalues);
        }
    }

    public void setOrientationIndicator(int orientation, boolean animation) {
        // We change the orientation of the linearlayout only for phone UI
        // because when in portrait the width is not enough.
        if (mLabelsLinearLayout != null) {
//            if (((orientation / 90) & 1) == 0) {
//                mLabelsLinearLayout.setOrientation(LinearLayout.VERTICAL);
//            } else {
//                mLabelsLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
//            }
            mLabelsLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        }
    }

    public SurfaceHolder getSurfaceHolder() {
        return mSurfaceHolder;
    }

    public SurfaceTexture getSurfaceTexture_1() { // frankie,
        return mySurfaceTexture;
    }

    public void hideSurfaceView() {
        mSurfaceView.setVisibility(View.INVISIBLE);
    }

    public void showSurfaceView() {
        mSurfaceView.setVisibility(View.VISIBLE);
    }

    public void enableGestures(boolean enable) { // frankie, add
        if (mGestures != null) {
            mGestures.setEnabled(enable);
        }
    }

    public void setPreviewGesturesVideoUI() {
        mActivity.setPreviewGestures(mGestures);
    }

    public void setPrefChangedListener(CameraPreference.OnPreferenceChangedListener listener) {
        mVideoMenu.setListener(listener);
    }

    public void updateOnScreenIndicators(Parameters param, ComboPreferences prefs) {
        // frankie, rm
        //mOnScreenIndicators.updateFlashOnScreenIndicator(param.getFlashMode());
        //boolean location = RecordLocationPreference.get(prefs);
        //mOnScreenIndicators.updateLocationIndicator(location);

    }

    public void showTimeLapseUI(boolean enable) {
        if (mTimeLapseLabel != null) {
            mTimeLapseLabel.setVisibility(enable ? View.VISIBLE : View.GONE);
        }
        mIsTimeLapse = enable;
    }



    public boolean is4KEnabled() {
        if (mController != null)
            return ((VideoModule) mController).is4KEnabled();
        else
            return false;
    }

    private void popupDismissed() {
        mPopup = null;
    }

    public boolean onBackPressed() {
        if (onBackPressed_FiltersView()) { // frankie,
            return true;
        }
        if (mVideoMenu != null && mVideoMenu.handleBackKey()) {
            return true;
        }
        if (hidePieRenderer()) {
            return true;
        } else {
            return removeTopLevelPopup();
        }
    }

    public void cleanupListview() {
		Log.v(TAG, "cleanupListview");
		if(mController instanceof VideoModule) { // frankie, add 
			VideoModule vm = (VideoModule)mController;
			if(!vm.mVideoIsCapturedDone) {
	        	showUI();
			}
		}
        mActivity.setSystemBarsVisibility(false);
    }

    public void dismissLevel1() {
        if (mMenuLayout != null) {
            ((ViewGroup) mRootView).removeView(mMenuLayout);
            if (mMenuLayoutView != null) {
                ((ViewGroup) mRootView).removeView(mMenuLayoutView); // frankie, add
                mMenuLayoutView = null; // frankie,
            }
            mMenuLayout = null;
        }
        if(!mVideoMenu.isShowSettingsFragment()){
           mActivity.setSlideSwitcherShow(true, false); // frankie, add
        }
    }

    public void dismissLevel2() {
        if (mSubMenuLayout != null) {
            ((ViewGroup) mRootView).removeView(mSubMenuLayout);
            mSubMenuLayout = null;
        }
    }

    public boolean sendTouchToPreviewMenu(MotionEvent ev) {
        return mPreviewMenuLayout.dispatchTouchEvent(ev);
    }

    public boolean sendTouchToMenu(MotionEvent ev) {
        if (mMenuLayout != null) {
            View v = mMenuLayout.getChildAt(0);    // frankie, child 0 is LinearLayout
            return v.dispatchTouchEvent(ev);
        }
        return false;
    }

    public void dismissSceneModeMenu() {
        if (mPreviewMenuLayout != null) {
            ((ViewGroup) mRootView).removeView(mPreviewMenuLayout);
            mPreviewMenuLayout = null;
        }
    }

    public void removeSceneModeMenu() {
        if (mPreviewMenuLayout != null) {
            ((ViewGroup) mRootView).removeView(mPreviewMenuLayout);
            mPreviewMenuLayout = null;
        }
        cleanupListview();
    }

    public void removeLevel2() {
        if (mSubMenuLayout != null) {
            //View v = mSubMenuLayout.getChildAt(0);
            //mSubMenuLayout.removeView(v);
            mSubMenuLayout.removeAllViews();
        }
    }

    public void showPopup(View popup, int level, boolean animate) {
        int orientation_ = mOrientation;
        orientation_ = 0; // frankie, fix

        FrameLayout.LayoutParams layoutParams;
        hideUI();

        popup.setVisibility(View.VISIBLE);
        if (level == 1) {
            if (mSubMenuLayout != null) { // frankie, ensure menu level 2 is on top of level 1
                ((ViewGroup) mRootView).removeView(mSubMenuLayout);
                mSubMenuLayout = null;
            }
            if (mMenuLayout == null) {
                //mMenuLayout = new RotateLayout(mActivity, null);	// this layout will measure child view to determine its own size !
                mMenuLayout = new FrameLayout(mActivity, null);
                if (mRootView.getLayoutDirection() != View.LAYOUT_DIRECTION_RTL) {
                    layoutParams = new FrameLayout.LayoutParams(
                            //CameraActivity.SETTING_LIST_WIDTH_1, LayoutParams.WRAP_CONTENT,
                            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
                            Gravity.LEFT | Gravity.TOP);
                } else {
                    layoutParams = new FrameLayout.LayoutParams(
                            //CameraActivity.SETTING_LIST_WIDTH_1, LayoutParams.WRAP_CONTENT,
                            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
                            Gravity.RIGHT | Gravity.TOP);
                }
                mMenuLayout.setBackgroundColor(0xff000000); // frankie, black
                mMenuLayout.setLayoutParams(layoutParams);

                if (MENU_BACKGROUND_VIEW_ENABLE) {
                    mMenuLayoutView = new View(mActivity);
                    mMenuLayoutView.setBackgroundColor(0xff000000); // frankie, black
                    ((ViewGroup) mRootView).addView(mMenuLayoutView);
                }

                ((ViewGroup) mRootView).addView(mMenuLayout);
            }
            //mMenuLayout.setOrientation(orientation_, true);
            mMenuLayout.addView(popup);
        }
        if (level == 2) {
            if (mSubMenuLayout == null) {
                //mSubMenuLayout = new RotateLayout(mActivity, null);
                mSubMenuLayout = new FrameLayout(mActivity, null);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        CameraActivity.SETTING_LIST_WIDTH_2, LayoutParams.WRAP_CONTENT);
                mSubMenuLayout.setLayoutParams(params);

                ((ViewGroup) mRootView).addView(mSubMenuLayout);
            }
            if (mRootView.getLayoutDirection() != View.LAYOUT_DIRECTION_RTL) {
                layoutParams = new FrameLayout.LayoutParams(
                        CameraActivity.SETTING_LIST_WIDTH_2, LayoutParams.WRAP_CONTENT,
                        Gravity.LEFT | Gravity.TOP);
            } else {
                layoutParams = new FrameLayout.LayoutParams(
                        CameraActivity.SETTING_LIST_WIDTH_2, LayoutParams.WRAP_CONTENT,
                        Gravity.RIGHT | Gravity.TOP);
            }

            int screenHeight = (orientation_ == 0 || orientation_ == 180) ? mRootView.getHeight() : mRootView.getWidth();
            int height = ((ListSubMenu) popup).getPreCalculatedHeight();
            int yBase = ((ListSubMenu) popup).getYBase();

            int y = Math.max(0, yBase);
            if (yBase + height > screenHeight)
                y = Math.max(0, screenHeight - height);
            int margin___ = CameraActivity.SETTING_LIST_WIDTH_1;
            margin___ = 180; // frankie,
            if (mRootView.getLayoutDirection() != View.LAYOUT_DIRECTION_RTL) {
                layoutParams.setMargins(margin___, y, 0, 0);
            } else {
                layoutParams.setMargins(0, y, margin___, 0);
            }

            mSubMenuLayout.setLayoutParams(layoutParams);
            mSubMenuLayout.addView(popup);
            //mSubMenuLayout.setOrientation(orientation_, true);
        }
        if (animate) {
            if (level == 1) {
                //mVideoMenu.animateSlideIn(mMenuLayout, CameraActivity.SETTING_LIST_WIDTH_1, true);
                //mVideoMenu.animateSlideIn(mMenuLayout, mRootView.getWidth(), true);
                mVideoMenu.animateFadeIn(mMenuLayout); // frankie,
                if (mMenuLayoutView != null) {
                    mVideoMenu.animateFadeIn(mMenuLayoutView);
                }
            }
            if (level == 2) {
                mVideoMenu.animateFadeIn(popup);
            }
        } else {
            //popup.setAlpha(0.85f);
            popup.setAlpha(1.0f); // frankie,
        }
    }

    public ViewGroup getMenuLayout() {
        return mMenuLayout;
    }

    public void setPreviewMenuLayout(LinearLayout layout) {
        mPreviewMenuLayout = layout;
    }

    public ViewGroup getPreviewMenuLayout() {
        return mPreviewMenuLayout;
    }

//    public void showPopup(AbstractSettingPopup popup) {
//        hideUI();
//
//        if (mPopup != null) {
//            mPopup.dismiss(false);
//        }
//        mPopup = new SettingsPopup(popup);
//    }

    public void onShowSwitcherPopup() {
        hidePieRenderer();
    }

    public boolean hidePieRenderer() {
        if (mPieRenderer != null && mPieRenderer.showsItems()) {
            mPieRenderer.hide();
            return true;
        }
        return false;
    }

    // disable preview gestures after shutter is pressed
    public void setShutterPressed(boolean pressed) {
        if (mGestures == null) return;
        mGestures.setEnabled(!pressed);
    }

    public void enableShutter(boolean enable) {
        if (mShutterButton != null) {
            if (enable) {
                Log.v(TAG, "Shutter Button enabled !!");
            } else {
                Log.v(TAG, "Shutter Button disabled !!");
            }
            mShutterButton.setEnabled(enable);
        }
    }
	
    public void setSwipingEnabled(boolean enable) {
        mActivity.setSwipingEnabled(enable);
    }

    public void showPreviewBorder(boolean enable) {
        // TODO: mPreviewFrameLayout.showBorder(enable);
    }

    // SingleTapListener
    // Preview area is touched. Take a picture.
    @Override
    public void onSingleTapUp(View view, int x, int y) {
        mController.onSingleTapUp(view, x, y);
    }

    public void setFrontBackEnable(boolean b){
//        if(mMenu != null){
//        }
        mCameraControls.setFrontBackSwitcherEnable(b);
    }

    public void showRecordingUI(boolean recording) {
        mRecordingStarted = recording;
        mMenuButton.setVisibility(recording ? View.GONE : View.VISIBLE);
        //mOnScreenIndicators.setVisibility(recording ? View.GONE : View.VISIBLE);
        if (recording) {
//            mShutterButton.setImageResource(R.drawable.shutter_button_video_stop);
//            mShutterButton.setImageResource(R.drawable.chus_shutter_button_video_stop); // frankie, add
            mShutterButton.setImageResource(R.drawable.toro_shutter_button_video_stop); // liujia add
            hideSwitcher();
            redDotImg.setVisibility(View.VISIBLE);
//            ((ViewGroup) mRootView).addView(mRecordingTimeRect);
        } else {
//            mShutterButton.setImageResource(R.drawable.btn_new_shutter_video);
//            mShutterButton.setImageResource(R.drawable.chus_shutter_button_video_start); // frankie, add
            mShutterButton.setImageResource(R.drawable.toro_shutter_button_video_start); // liujia, add
            if (!mController.isVideoCaptureIntent()) {
                showSwitcher();
            }
            redDotImg.setVisibility(View.GONE);
//            ((ViewGroup) mRootView).removeView(mRecordingTimeRect);
        }
        mRecordingTimeView.setText(R.string.initial_recording_seconds);
    }

    public void hideUIwhileRecording() {
        mCameraControls.setWillNotDraw(true);
        mVideoMenu.hideUI();
    }

    public void showUIafterRecording() {
        mCameraControls.setWillNotDraw(false);
        if (!mController.isVideoCaptureIntent()) {
            mVideoMenu.showUI();
        }
    }
    public void showUIafterStartRecordingFailed() {
        mCameraControls.setWillNotDraw(false);
        if (!mController.isVideoCaptureIntent()) {
            mVideoMenu.showUI();
        }
		if(mController.isVideoCaptureIntent()) { // frankie, 2017.10.30, handle this special case !!!
			mVideoMenu.showUI_for_special_capture_video();
		}
    }

    public void showReviewImage(Bitmap bitmap) {
        mReviewImage.setImageBitmap(bitmap);
        mReviewImage.setVisibility(View.VISIBLE);
    }

    public void showReviewControls() {
        //CameraUtil.fadeOut(mShutterButton);	// frankie, cause the animation need time to take effect
		mShutterButton.setVisibility(View.INVISIBLE);
        CameraUtil.fadeIn(mReviewDoneButton);
        CameraUtil.fadeIn(mReviewPlayButton);
        CameraUtil.fadeIn(mReviewCancelButton);//
        mActivity.setSlideSwitcherShow(false,true);
        mReviewImage.setVisibility(View.VISIBLE);
        mMenuButton.setVisibility(View.GONE);
        mPauseButton.setVisibility(View.GONE);
        //mOnScreenIndicators.setVisibility(View.GONE);
    }
	/*
    public void hideReviewUI() {
        mReviewImage.setVisibility(View.GONE);
        mShutterButton.setEnabled(true);
        mMenuButton.setVisibility(View.VISIBLE);
        //mOnScreenIndicators.setVisibility(View.VISIBLE);
        CameraUtil.fadeOut(mReviewDoneButton);
        CameraUtil.fadeOut(mReviewPlayButton);
        CameraUtil.fadeOut(mReviewCancelButton);//
        mActivity.setSlideSwitcherShow(true,false);
        CameraUtil.fadeIn(mShutterButton);
    }
    */

    private void setShowMenu(boolean show) {
        if (mController.isVideoCaptureIntent())
            return;
        //if (mOnScreenIndicators != null) { // frankie, rm
        //    mOnScreenIndicators.setVisibility(show ? View.VISIBLE : View.GONE);
        //}
    }

    public void onPreviewFocusChanged(boolean previewFocused) {
		Log.v(TAG, "onPreviewFocusChanged: " + previewFocused);
        if (previewFocused) {
			if(mController instanceof VideoModule) { // frankie, add 
				VideoModule vm = (VideoModule)mController;
				if(!vm.mVideoIsCapturedDone) {
					showUI();
				}
			}
        } else {
            hideUI();
        }
        if (mGestures != null) {
            mGestures.setEnabled(previewFocused);
        }
        if (mRenderOverlay != null) {
            // this can not happen in capture mode
            mRenderOverlay.setVisibility(previewFocused ? View.VISIBLE : View.GONE);
        }
        setShowMenu(previewFocused);
    }

    public void initializePopup(PreferenceGroup pref) {
        mVideoMenu.initialize(pref);
    }

    public void initializeZoom(Parameters param) {
        if (param == null || !param.isZoomSupported()) {
            mGestures.setZoomEnabled(false);
            return;
        }
        mGestures.setZoomEnabled(true);
        mZoomMax = param.getMaxZoom();
        mZoomRatios = param.getZoomRatios();
        // Currently we use immediate zoom for fast zooming to get better UX and
        // there is no plan to take advantage of the smooth zoom.
        mZoomRenderer.setZoomMax(mZoomMax);
        mZoomRenderer.setZoom(param.getZoom());
        mZoomRenderer.setZoomValue(mZoomRatios.get(param.getZoom()));
        mZoomRenderer.setOnZoomChangeListener(new ZoomChangeListener());
    }

    public void clickShutter() {
        mShutterButton.performClick();
    }

    public void pressShutter(boolean pressed) {
        mShutterButton.setPressed(pressed);
    }

    public View getShutterButton() {
        return mShutterButton;
    }

    public void setRecordingTime(String text) {
        mRecordingTimeView.setText(text);
    }

    public void setRecordingTimeTextColor(int color) {
        mRecordingTimeView.setTextColor(color);
    }

    public boolean isVisible() {
        return mCameraControls.getVisibility() == View.VISIBLE;
    }

    @Override
    public void onDisplayChanged() {
        mCameraControls.checkLayoutFlip();
        mController.updateCameraOrientation();
    }

    private class ZoomChangeListener implements ZoomRenderer.OnZoomChangedListener {
        @Override
        public void onZoomValueChanged(int index) {
            int newZoom = mController.onZoomChanged(index);
            if (mZoomRenderer != null) {
                mZoomRenderer.setZoomValue(mZoomRatios.get(newZoom));
            }
        }

        @Override
        public void onZoomStart() {
            if (mPieRenderer != null) {
                if (!mRecordingStarted) mPieRenderer.hide();
                mPieRenderer.setBlockFocus(true);
            }
        }

        @Override
        public void onZoomEnd() {
            if (mPieRenderer != null) {
                mPieRenderer.setBlockFocus(false);
            }
        }

        @Override
        public void onZoomValueChanged(float value) {

        }
    }


    public View getRootView() {
        return mRootView;
    }

    @Override
    public void onButtonPause() {
//        mRecordingTimeView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pausing_indicator, 0, 0, 0);
        stopRed();
        mController.onButtonPause();
    }

    public void startRed(){
        redObjectAnimator.setDuration(800);
        redObjectAnimator.setRepeatCount(-1);
        redObjectAnimator.start();
    }

    public void stopRed(){
        redObjectAnimator.cancel();
    }

    @Override
    public void onButtonContinue() {
//        mRecordingTimeView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_recording_indicator, 0, 0, 0);
        startRed();
        mController.onButtonContinue();
    }

    public void resetPauseButton() {
//        mRecordingTimeView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_recording_indicator, 0, 0, 0);
        mPauseButton.setPaused(false);
    }

    public void setPreference(String key, String value) {
        mVideoMenu.setPreference(key, value);
    }

    public boolean hideSwitcherPopup() {
        //if (mSwitcher != null && mSwitcher.showsPopup()) {
        //    mSwitcher.closePopup();
        //    return true;
        //}
        return false;
    }

    public void setOrientation(int orientation, boolean animation) {
        Log.v(TAG, "setOrientation, orientation:" + orientation + " animation:" + animation); // frankie,
        mOrientation = orientation;
        mCameraControls.setOrientation(orientation, animation);

        if (mMenuLayout != null) { // frankie, rm
            //mMenuLayout.setOrientation(orientation, animation);
        }
        if (mSubMenuLayout != null) { // frankie, rm
            //mSubMenuLayout.setOrientation(orientation, animation);
        }

        if (mRecordingTimeRect != null) { // frankie rm
            if (orientation == 180) {
                //mRecordingTimeRect.setOrientation(0, false);
                //mRecordingTimeView.setRotation(180);
            } else {
                //mRecordingTimeView.setRotation(0);
                //mRecordingTimeRect.setOrientation(orientation, false);
            }
        }
        if (mPreviewMenuLayout != null) {
            ViewGroup vg = (ViewGroup) mPreviewMenuLayout.getChildAt(0);
            if (vg != null)
                vg = (ViewGroup) vg.getChildAt(0);
            if (vg != null) {
                for (int i = vg.getChildCount() - 1; i >= 0; --i) {
                    RotateLayout l = (RotateLayout) vg.getChildAt(i);
                    l.setOrientation(orientation, animation);
                }
            }
        }
        if (mZoomRenderer != null) {
            mZoomRenderer.setOrientation(orientation);
        }
        RotateTextToast.setOrientation(orientation);
    }

    public void tryToCloseSubList() {
        if (mVideoMenu != null)
            mVideoMenu.tryToCloseSubList();
    }

    public int getOrientation() {
        return mOrientation;
    }

    public void adjustOrientation() {
        setOrientation(mOrientation, false);
    }

    @Override
    public void onFaceDetection(Face[] faces, CameraProxy camera) {
        Log.d(TAG, "onFacedetectopmn");
        mFaceView.setFaces(faces);
    }

    public void pauseFaceDetection() {
        if (mFaceView != null) mFaceView.pause();
    }

    public void resumeFaceDetection() {
        if (mFaceView != null) mFaceView.resume();
    }

    public void onStartFaceDetection(int orientation, boolean mirror) {
        mFaceView.setBlockDraw(false);
        mFaceView.clear();
        mFaceView.setVisibility(View.VISIBLE);
        mFaceView.setDisplayOrientation(orientation);
        mFaceView.setMirror(mirror);
        mFaceView.resume();
    }

    public void onStopFaceDetection() {
        if (mFaceView != null) {
            mFaceView.setBlockDraw(true);
            mFaceView.clear();
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    private static final int FILTER_GRIDE_SHOW_DURATION = 500;
    private static final int FILTER_GRIDE_HIDE_DURATION = 500;

    private MyColorEffectTopLinearLayout.OnFilterClickListener mOnFilterClickListener = new
            MyColorEffectTopLinearLayout.OnFilterClickListener() {
                @Override
                public void onClick(int typeIndex) {
                    if (mTopColorEffectAnimating || mTopColorEffectContainer == null) {
                        return;
                    }
                    if (myFrameLayout != null) {
                        myFrameLayout.setFilterType(typeIndex);
                    }
                    hideFiltersView(true);
                }
            };

    public void onFilterButtonClicked() {
        if (mTopColorEffectAnimating || mTopColorEffectContainer != null) {
            return;
        }
        showFiltersView();

        if (mTopColorEffectContainer != null) {
            final MyColorEffectTopLinearLayout colorViewGroup = (MyColorEffectTopLinearLayout) mTopColorEffectContainer;
            colorViewGroup.setOnFilterClickListener(mOnFilterClickListener);
            ImageView exit_button = (ImageView) mTopColorEffectContainer.findViewById(R.id.exit_button_01);
            exit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideFiltersView(true);
                }
            });

            Button group_button = (Button) mTopColorEffectContainer.findViewById(R.id.change_group_button_01);
            group_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyColorEffectTopLinearLayout.mTextureFilterGroupIndex++;
                    if (MyColorEffectTopLinearLayout.mTextureFilterGroupIndex > 4) {
                        MyColorEffectTopLinearLayout.mTextureFilterGroupIndex = 0;
                    }
                    Toast.makeText(mActivity, "" + MyColorEffectTopLinearLayout.mTextureFilterGroupIndex,
                            Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    ViewGroup mTopColorEffectContainer = null;
    boolean mTopColorEffectAnimating = false;

    private boolean onBackPressed_FiltersView() {
        if (mTopColorEffectContainer != null && !mTopColorEffectAnimating) {
            mTopColorEffectAnimating = true;
            hideFiltersView(true);
            return true;
        }
        if (mTopColorEffectAnimating) {
            return true;
        }
        return false;
    }

    private void showFiltersView() {
        mActivity.setSlideSwitcherShow(false, true); // frankie, add
        if (mTopColorEffectContainer == null) {
            final FrameLayout llayout = (FrameLayout) mRootView.findViewById(R.id.color_effect_root_1);
            Log.v(TAG, "llayout " + (llayout != null ? "!= null" : " == null"));

            LayoutInflater layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewGroup topColorEffectContainer = (ViewGroup) layoutInflater.inflate(R.layout.color_effect_top_inc, null, false);
            if (topColorEffectContainer instanceof MyColorEffectTopLinearLayout) {
                Log.v(TAG, "topColorEffectContainer instanceof MyColorEffectTopLinearLayout");
            } else {
                Log.v(TAG, "topColorEffectContainer NOT instanceof MyColorEffectTopLinearLayout");
            }
            topColorEffectContainer.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                           int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    Log.v(TAG, "topColorEffectContainer, onLayoutChange :" + (right - left) + " * " + (bottom - top));
                }
            });

            final MyColorEffectTopLinearLayout colorViewGroup = (MyColorEffectTopLinearLayout) topColorEffectContainer;

            if (myFrameLayout != null) {
                MyFrameLayoutVideo.CameraPreviewInformation cameraPreviewInfo = myFrameLayout.getCameraPreviewInformation(); // *** always hang here !
                Log.v(TAG, "cameraPreviewInfo:" + cameraPreviewInfo.width + " * " + cameraPreviewInfo.height
                        + " ori:" + cameraPreviewInfo.orientation + " front:" + cameraPreviewInfo.isFront);
                colorViewGroup.setCameraPreviewInfo(cameraPreviewInfo.width, cameraPreviewInfo.height, cameraPreviewInfo.orientation, cameraPreviewInfo.isFront);

                if (cameraPreviewInfo.width > cameraPreviewInfo.height) {
                    float ratio = (float) cameraPreviewInfo.width / (float) cameraPreviewInfo.height;
                    colorViewGroup.setAspectRatio_ext(ratio);
                } else {
                    float ratio = (float) cameraPreviewInfo.height / (float) cameraPreviewInfo.width;
                    colorViewGroup.setAspectRatio_ext(ratio);
                }
                EGLContext r = myFrameLayout.get__eglContext();
                Log.v(TAG, "get__eglContext:" + (r != null ? " != null" : " == null"));
                if (r != null) {
                    colorViewGroup.createThread_withContext(r);
                } else {
                    colorViewGroup.createThread__();
                }
            }

            topColorEffectContainer.setVisibility(View.VISIBLE);
            topColorEffectContainer.setAlpha(0.00001f);
            mTopColorEffectContainer = topColorEffectContainer;
            mTopColorEffectContainer.setLayoutParams(
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,
                    Gravity.CENTER);
            llayout.addView(mTopColorEffectContainer, lp);

            if (myFrameLayout != null) {
                myFrameLayout.setFrameAvaiableCallback(new MyFrameLayoutVideo.FrameAvaiableCallback() {
                    @Override
                    public void onFrameAvaiable(int texture_id, SurfaceTexture surfaceTexture, float[] matrix) {
                        Log.v(TAG, "FrameAvaiableCallback: " + texture_id);
                        colorViewGroup.onSurfaceTextureAvaiable__(texture_id, surfaceTexture, matrix);
                    }
                });
            }
            colorViewGroup.setTextureFrameReadyCallback(new MyColorEffectTopLinearLayout.TextureFrameReadyCallback() {
                @Override
                public void onReady() {
                    ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(colorViewGroup, "alpha", 0f, 1f);
                    alphaAnimator.setDuration(FILTER_GRIDE_SHOW_DURATION);
                    alphaAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationCancel(Animator animation) {
                            Log.v(TAG, "onAnimationCancel");
                            colorViewGroup.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            Log.v(TAG, "onAnimationEnd");
                            colorViewGroup.setAlpha(1.0f);
                            colorViewGroup.setVisibility(View.VISIBLE);
                            mTopColorEffectAnimating = false;
                        }
                    });

                    ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(colorViewGroup, "scaleX", 10.0f, 1.0f).setDuration(1000);
                    ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(colorViewGroup, "scaleY", 10.0f, 1.0f).setDuration(1000);
                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator);
                    set.setDuration(FILTER_GRIDE_SHOW_DURATION);
                    set.start();
                }
            });
            mTopColorEffectAnimating = true;

        }
    }

    private void hideFiltersViewDone() {
        final FrameLayout llayout = (FrameLayout) mActivity.findViewById(R.id.color_effect_root_1);
        final MyColorEffectTopLinearLayout colorViewGroup = (MyColorEffectTopLinearLayout) mTopColorEffectContainer;
        colorViewGroup.onPause();
        colorViewGroup.destroyRendererThread();
        colorViewGroup.setVisibility(View.INVISIBLE);
        //colorViewGroup.removeAllViews();
        colorViewGroup.setVisibility(View.GONE);
        if (myFrameLayout != null) {
            myFrameLayout.setFrameAvaiableCallback(null);
        }
        llayout.removeAllViews();
        mTopColorEffectContainer = null;
        mTopColorEffectAnimating = false;
        mActivity.setSlideSwitcherShow(true, false); // frankie, add

    }

    private void hideFiltersView(boolean withAnimation) {
        if (mTopColorEffectContainer != null) {
            final FrameLayout llayout = (FrameLayout) mActivity.findViewById(R.id.color_effect_root_1);
            final MyColorEffectTopLinearLayout colorViewGroup = (MyColorEffectTopLinearLayout) mTopColorEffectContainer;

            if (withAnimation) {
                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(colorViewGroup, "alpha", 1f, 0f);
                alphaAnimator.setDuration(FILTER_GRIDE_HIDE_DURATION);
                alphaAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        Log.v(TAG, "onAnimationCancel");
                        hideFiltersViewDone();
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Log.v(TAG, "onAnimationEnd");
                        hideFiltersViewDone();
                    }
                });

                ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(colorViewGroup, "scaleX", 1.0f, 10.0f).setDuration(1000);
                ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(colorViewGroup, "scaleY", 1.0f, 10.0f).setDuration(1000);
                AnimatorSet set = new AnimatorSet();
                set.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator);
                set.setDuration(FILTER_GRIDE_HIDE_DURATION);
                set.start();
            } else {
                hideFiltersViewDone();
            }

        }
    }

//    private void toggleFiltersView() {
//        if (mTopColorEffectContainer == null) {
//            showFiltersView();
//        } else {
//            hideFiltersView(true);
//            mTopColorEffectContainer = null;
//        }
//    }
	
	public void showPreviewThumbnail(boolean show) { // frankie, add
		if(show) { mThumbnail.setVisibility(View.VISIBLE); }
		else { mThumbnail.setVisibility(View.INVISIBLE); }
	}


}
