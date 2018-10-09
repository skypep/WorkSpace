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
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.android.camera.AGlobalConfig;
import com.android.camera.AnimationManager;
import com.android.camera.CameraActivity;
import com.android.camera.CameraManager;
import com.android.camera.CameraPreference;
import com.android.camera.CameraSettings;
import com.android.camera.ComboPreferences;
import com.android.camera.FocusOverlayManager;
import com.android.camera.IconListPreference;
import com.android.camera.OnScreenIndicators;
import com.android.camera.PreferenceGroup;
import com.android.camera.PreviewGestures;
import com.android.camera.ShutterButton;
import com.android.camera.module.prefs.SettingsFragment1;
import com.android.camera.module.ui.PieMenuRenderer;
import com.android.camera.module.ui.PieMenuRendererImpl;
import com.android.camera.module.ui.VerticalSeekBar3;
import com.android.camera.module.ui.VerticalSeekBarLinearLayout;
import com.android.camera.ui.AbstractSettingPopup;
import com.android.camera.ui.CameraControls;
import com.android.camera.ui.CameraRootView;
import com.android.camera.ui.CountDownView;
import com.android.camera.ui.FocusIndicator;
import com.android.camera.ui.ListSubMenu;
import com.android.camera.ui.MenuHelp;
import com.android.camera.ui.RenderOverlay;
import com.android.camera.ui.RotateImageView;
import com.android.camera.ui.RotateLayout;
import com.android.camera.ui.RotateTextToast;
import com.android.camera.ui.SelfieFlashView;
import com.android.camera.ui.ZoomRenderer;
import com.android.camera.util.CameraUtil;
import com.chus.camera.R;

import java.util.List;

import javax.microedition.khronos.egl.EGLContext;

import static java.lang.System.currentTimeMillis;

//import com.android.camera.CameraPreference.OnPreferenceChangedListener;
//import com.android.camera.FocusOverlayManager.FocusUI;
//import com.android.camera.module.TsMakeupManager.MakeupLevelListener;
//import com.android.camera.ui.CountDownView.OnCountDownFinishedListener;
//import com.android.camera.ui.FaceView; // frankie,
//import com.android.camera.ui.PieRenderer.PieListener;


public class PhotoUI implements 
		//PieRenderer.PieListener,
        PreviewGestures.SingleTapListener,
        FocusOverlayManager.FocusUI,
        //SurfaceHolder.Callback,
        CameraRootView.MyDisplayListener,
        CameraManager.CameraFaceDetectionCallback {

    private static final String TAG = "CAM_UI";

	private static final boolean MENU_BACKGROUND_VIEW_ENABLE = false; // true; 
	
    private int mDownSampleFactor = 4;
    private final AnimationManager mAnimationManager;
    private CameraActivity mActivity;
    private PhotoController mController;
    private PreviewGestures mGestures;

    private View mRootView;


    private PopupWindow mPopup;
    private ShutterButton mShutterButton;
    private CountDownView mCountDownView;
    private SelfieFlashView mSelfieView;

    private FaceView mFaceView;
    private RenderOverlay mRenderOverlay;
    private View mReviewCancelButton;
    private View mReviewDoneButton;
//    private View mReviewRetakeButton;
    private ImageView mReviewImage;
    private DecodeImageForReview mDecodeTaskForReview = null;

    private View mMenuButton;
    private PhotoMenu mMenu;
    //private ModuleSwitcher mSwitcher;
    private CameraControlsPhoto mCameraControls;
    private MenuHelp mMenuHelp;
    private AlertDialog mLocationDialog;

    // Small indicators which show the camera settings in the viewfinder.
    private OnScreenIndicators mOnScreenIndicators;

    //private PieRenderer mPieRenderer;
    private PieMenuRenderer mPieRenderer; // frankie, add
    private ZoomRenderer mZoomRenderer;
    private RotateTextToast mNotSelectableToast;

    private int mZoomMax;
    private List<Integer> mZoomRatios;

    private int mMaxPreviewWidth = 0;
    private int mMaxPreviewHeight = 0;

    public boolean mMenuInitialized = false;
    private float mSurfaceTextureUncroppedWidth;
    private float mSurfaceTextureUncroppedHeight;

    private ImageView mThumbnail;
    private View mFlashOverlay;

    private SurfaceTextureSizeChangedListener mSurfaceTextureSizeListener;
    private SurfaceView mSurfaceView = null;
    private SurfaceHolder mSurfaceHolder;
    private float mAspectRatio = 4f / 3f;
    private boolean mAspectRatioResize;

    private boolean mOrientationResize;
    private boolean mPrevOrientationResize;
    private View mPreviewCover;
    //private RotateLayout mMenuLayout;
	private ViewGroup mMenuLayout;
	private View mMenuLayoutView = null; // frankie, debug
    //private RotateLayout mSubMenuLayout;
	private ViewGroup mSubMenuLayout;
    private LinearLayout mPreviewMenuLayout;
    private LinearLayout mMakeupMenuLayout;
    private boolean mUIhidden = false;
    private int mPreviewOrientation = -1;

    private int mScreenRatio = CameraUtil.RATIO_UNKNOWN;
    private int mTopMargin = 0;
    private int mBottomMargin = 0;
    private boolean mIsLayoutInitializedAlready = false;

    private int mOrientation;
    private float mScreenBrightness = 0.0f;

    MyFrameLayout myFrameLayout;	// frankie, add 
    private SurfaceTexture mySurfaceTexture;
	
	//BubbleSeekBar mBubbleSeekBar;
    int mBubbleSeekProgress = 0;

	VerticalSeekBarLinearLayout mVerticalSeekBarLayout;
	int mVerticalSeekBarProgress = 0;
    private boolean isFaceBeautyOpen = false;

	GridLines mGridLines = null;

    public enum SURFACE_STATUS {
        HIDE,
        SURFACE_VIEW;
    };

    public interface SurfaceTextureSizeChangedListener {
        public void onSurfaceTextureSizeChanged(int uncroppedWidth, int uncroppedHeight);
    }

    public CameraControlsPhoto getCameraControls() {
        return mCameraControls;
    }

    public synchronized void applySurfaceChange(SURFACE_STATUS status) {
        if(status == SURFACE_STATUS.HIDE) {
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
    public PhotoUI(CameraActivity activity, PhotoController controller, View parent) {
        mActivity = activity;
        mController = controller;
        mRootView = parent;
        mActivity.getLayoutInflater().inflate(R.layout.photo_module_1,
                (ViewGroup) mRootView, true);
        mPreviewCover = mRootView.findViewById(R.id.preview_cover);
		
        // display the view
        mSurfaceView = (SurfaceView) mRootView.findViewById(R.id.mdp_preview_content);
        mSurfaceView.setVisibility(View.VISIBLE);
        mSurfaceHolder = mSurfaceView.getHolder();
        //mSurfaceHolder.addCallback(this);
        SurfaceHolder.Callback __callback = new SurfaceHolder.Callback() {
            @Override // SurfaceHolder.Callback IMPL
            public void surfaceCreated(SurfaceHolder holder) {
                Log.v(TAG, ">>> surfaceCreated");
                mSurfaceHolder = holder;
                mController.onPreviewUIReady();
                mActivity.updateThumbnail_ui_ready(mThumbnail);
            }
            // SurfaceHolder callbacks
            @Override // SurfaceHolder.Callback IMPL
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.v(TAG, ">>> surfaceChanged: width =" + width + ", height = " + height);
                RectF r = new RectF(mSurfaceView.getLeft(), mSurfaceView.getTop(),
                        mSurfaceView.getRight(), mSurfaceView.getBottom());
                mController.onPreviewRectChanged(CameraUtil.rectFToRect(r));
            }
            @Override // SurfaceHolder.Callback IMPL
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.v(TAG, ">>> surfaceDestroyed");
                mSurfaceHolder = null;
                mController.onPreviewUIDestroyed();
            }
        };
		if(AGlobalConfig.config_module_PHOTO_MODULE_PREVIEW_TYPE == 0) {
			mSurfaceHolder.addCallback(__callback);
		}

        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        Log.v(TAG, "Using mdp_preview_content (MDP path)");

		View.OnLayoutChangeListener __mOnLayoutChangeListener = new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, 
            	int oldLeft, int oldTop, int oldRight, int oldBottom) {
            	
                int width = right - left;
                int height = bottom - top;

				Log.v(TAG, "onLayoutChange, l:" + left + " t:" + top + " r:" + right + " b:" + bottom); // frankie,
                tryToCloseSubList();

                if (mMaxPreviewWidth == 0 && mMaxPreviewHeight == 0) {
                    mMaxPreviewWidth = width;
                    mMaxPreviewHeight = height;
                }

				Log.v(TAG, "mMaxPreview Width*Height = " + mMaxPreviewWidth + " * " + mMaxPreviewHeight);
				if(myFrameLayout != null) {
					myFrameLayout.setMaxPreviewSize(mMaxPreviewWidth, mMaxPreviewHeight);
				}
					
                if (mOrientationResize != mPrevOrientationResize
                        || mAspectRatioResize 
                        || !mIsLayoutInitializedAlready) {
                    //layoutPreview(mAspectRatio);
					mOnLayoutChangeHandler.sendEmptyMessageDelayed(0, 5); // Must post to handler
                    mAspectRatioResize = false;
                }
            }
        };
		if(AGlobalConfig.config_module_PHOTO_MODULE_PREVIEW_TYPE == 0) {
        	mSurfaceView.addOnLayoutChangeListener(__mOnLayoutChangeListener);
		}
		
        myFrameLayout = (MyFrameLayout)mRootView.findViewById(R.id.my_framelayout_1); // frankie, add
        if(myFrameLayout!=null) {
			if(AGlobalConfig.config_module_PHOTO_MODULE_PREVIEW_TYPE == 1) {
				myFrameLayout.addOnLayoutChangeListener(__mOnLayoutChangeListener);	// watch myFrameLayout is enough
				
				MyFrameLayout.Callback callback_ = new MyFrameLayout.Callback() { // same as SurfaceHolder.Callback IMPL
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
						if(myFrameLayout != null) {
							Rect r = myFrameLayout.getPreviewTextviewRect();
							if(r != null) {
								mController.onPreviewRectChanged(r);
							}
						}
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
            if(AGlobalConfig.config_module_PHOTO_MODULE_PREVIEW_TYPE == 0) {
                myFrameLayout.setCallback(new MyFrameLayout.Callback() { // dummy callback
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
		if(AGlobalConfig.config_module_PHOTO_MODULE_PREVIEW_TYPE == 0) { // for debug MyFrameLayout
            //ViewGroup vg = (ViewGroup)mRootView;
            //vg.removeView(myFrameLayout);
            //myFrameLayout.setVisibility(View.INVISIBLE);
            
            myFrameLayout.setAlpha(0.8f);
            Button testButton = (Button)mRootView.findViewById(R.id.aspect_ratio_test);
			testButton.setVisibility(View.VISIBLE);
            if(testButton != null) {
                testButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myFrameLayout.test1();
                    }
                });
            }
		}


        RelativeLayout rl = (RelativeLayout)mRootView.findViewById(R.id.bottom_background_mask_container);
        rl.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            // *** frankie, must add this for BubbleSeekBar layout correct !!! ***
            // but this is triggered every texture frame draw . so prints a lot of info
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                //Log.v(TAG, "bottom container onLayoutChange:" + (right-left) + " * " + (bottom - top));
                //if(mBubbleSeekBar != null) {
                //    mBubbleSeekBar.requestLayout();
                //    mBubbleSeekBar.invalidate();
                //}
            }
        });
		mBubbleSeekProgress = 1;	// fankie,
//		if(myFrameLayout != null) {	// set initial beauty level
//			myFrameLayout.setBeautyLevel(mBubbleSeekProgress);
//		}

        //mBubbleSeekBar = (BubbleSeekBar)mRootView.findViewById(R.id.bubble_seek_bar_1);
        //mBubbleSeekBar.setVisibility(View.VISIBLE);
        //mBubbleSeekBar.setProgress(mBubbleSeekProgress);
        //mBubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
        //    @Override
        //    public void onProgressChanged(int progress) {
        //        if(mBubbleSeekBar != null) {
        //            if(progress != mBubbleSeekProgress) {
        //                mBubbleSeekProgress = progress;
        //                Log.v(TAG, "onProgressChanged : " + progress + " /" + mBubbleSeekBar.getProgress());
		//				if(myFrameLayout != null) {
		//					myFrameLayout.setBeautyLevel(mBubbleSeekProgress);
		//				}
        //            }
        //        }
        //    }
        //    @Override
        //    public void onProgressChanged(float progress) {
        //    }
        //});


		mVerticalSeekBarProgress = 50;
		mVerticalSeekBarLayout = (VerticalSeekBarLinearLayout)mRootView.findViewById(R.id.vertical_seek_bar_layout);
		mVerticalSeekBarLayout.setProgress(mVerticalSeekBarProgress);
		mVerticalSeekBarLayout.setChangeListener__(new VerticalSeekBar3.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				mVerticalSeekBarProgress = progress;
                Log.v(TAG, "onProgressChanged : " + progress + " /" + seekBar.getProgress());
				if(myFrameLayout != null) {
					myFrameLayout.setBeautyLevel_ext(mVerticalSeekBarProgress);
				}
				if(mActivity.mTrickerControlBinder.getControlWrapper() != null) { // frankie, 2018.01.30
                    mActivity.mTrickerControlBinder.getControlWrapper().setBeautyLevel(1, mVerticalSeekBarProgress);
				}
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});

		mGridLines = (GridLines) mRootView.findViewById(R.id.grid_lines); // frankie, 

        mRenderOverlay = (RenderOverlay) mRootView.findViewById(R.id.render_overlay);
        mFlashOverlay = mRootView.findViewById(R.id.flash_overlay);
        mShutterButton = (ShutterButton) mRootView.findViewById(R.id.shutter_button);

//        mSwitcher = (ModuleSwitcher) mRootView.findViewById(R.id.camera_switcher);
//        mSwitcher.setCurrentIndex(ModuleSwitcher.PHOTO_MODULE_INDEX);
//        mSwitcher.setSwitchListener(mActivity);
//        mSwitcher.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mController.getCameraState() == PhotoController.LONGSHOT) {
//                       return;
//                }
//                mSwitcher.showPopup();
//                mSwitcher.setOrientation(mOrientation, false);
//            }
//        });

        mMenuButton = mRootView.findViewById(R.id.menu);
        mMenuButton.setVisibility(View.GONE);
        //RotateImageView muteButton = (RotateImageView)mRootView.findViewById(R.id.mute_button);
        //muteButton.setVisibility(View.GONE);

        mCameraControls = (CameraControlsPhoto) mRootView.findViewById(R.id.camera_controls);
        ViewStub faceViewStub = (ViewStub) mRootView.findViewById(R.id.face_view_stub);
        if (faceViewStub != null) {
            faceViewStub.inflate();
            mFaceView = (FaceView) mRootView.findViewById(R.id.face_view);
            setSurfaceTextureSizeChangedListener(mFaceView);
        }
        //initIndicators();
        mAnimationManager = new AnimationManager();
        mOrientationResize = false;
        mPrevOrientationResize = false;

        Point size = new Point();
        mActivity.getWindowManager().getDefaultDisplay().getSize(size);
        mScreenRatio = CameraUtil.determineRatio(size.x, size.y);
		Log.v(TAG, "mScreenRatio:" + mScreenRatio + " getDefaultDisplay size:" + size.x + "*" + size.y);	// 0 - unknown, 1 - 16:9 , 2- 4:3, 3 - 3:2 
			// 0, 1080*1776 
        calculateMargins(size);	// frankie, 
        mCameraControls.setMargins(mTopMargin, mBottomMargin);
		
        //showFirstTimeHelp();
		hideGridLines(); // frankie, hide on init
    }
	private void calculateMargins(Point size) {
        int l = size.x > size.y ? size.x : size.y;
        int tm = mActivity.getResources().getDimensionPixelSize(R.dimen.preview_top_margin);
        int bm = mActivity.getResources().getDimensionPixelSize(R.dimen.preview_bottom_margin);
        mTopMargin = l / 4 * tm / (tm + bm);
        mBottomMargin = l / 4 - mTopMargin;
    }
    public void cancelAutoFocus__i() { // frankie, add 2017.11.23
        if(mController != null) {
            mController.cancelAutoFocus();
        }
    }
    /*public*/ private void setSurfaceTextureSizeChangedListener(SurfaceTextureSizeChangedListener listener) {
        mSurfaceTextureSizeListener = listener;
    }
	//private void initIndicators() {
    //    mOnScreenIndicators = new OnScreenIndicators(mActivity,
    //            mRootView.findViewById(R.id.on_screen_indicators));
    //}

	public void onPreviewRectChanged_1(Rect previewRect) {
		if(mGridLines != null) {
            RectF f = new RectF();
            f.left = previewRect.left;	f.right = previewRect.right;
            f.top = previewRect.top;	f.bottom = previewRect.bottom;
            mGridLines.onPreviewAreaChanged(f);
		}
	}
    public void showGridLines() {
        if (mGridLines != null) {mGridLines.setVisibility(View.VISIBLE);}
    }
    public void hideGridLines() {
        if (mGridLines != null) {mGridLines.setVisibility(View.INVISIBLE);}
    }


	public void onResume_1() {

		if(myFrameLayout!=null) {
            if(mController instanceof PhotoModule) {
                PhotoModule pm = (PhotoModule)mController;
                int type = pm.getPersistedChusFilterType();
				if(type < 0) { type = 0; }
                myFrameLayout.setFilterType(type);

				if(mActivity.mTrickerControlBinder.getControlWrapper() != null) { // frankie, 2018.01.30
	            	mActivity.mTrickerControlBinder.getControlWrapper().setFilterIndex(1, type);
				}
            }
            myFrameLayout.onResume();
        }
		onColorEffectResume();
	}

    public void setDownFactor(int factor) {
        mDownSampleFactor = factor;
    }
    public void cameraOrientationPreviewResize(boolean orientation){
	    mPrevOrientationResize = mOrientationResize;
	    mOrientationResize = orientation;
    }
    public void setAspectRatio(float ratio) {
        if (ratio <= 0.0) throw new IllegalArgumentException();

        if (mOrientationResize &&
                mActivity.getResources().getConfiguration().orientation
                != Configuration.ORIENTATION_PORTRAIT) {
            ratio = 1 / ratio;
        }

        Log.d(TAG, "setAspectRatio() ratio[" + ratio + "] mAspectRatio[" + mAspectRatio + "]");
        if (ratio != mAspectRatio) {
            mAspectRatioResize = true;
            mAspectRatio = ratio;
        }
		if(AGlobalConfig.config_module_PHOTO_MODULE_PREVIEW_TYPE == 1) {
			if(myFrameLayout!=null) { // frankie, hook 
				myFrameLayout.setAspectRatio(ratio);
			}
		}
        mCameraControls.setPreviewRatio(mAspectRatio, false);
        layoutPreview(ratio);
    }

    /*public*/ private void layoutPreview(float ratio) {
		FrameLayout.LayoutParams myFrameLayout_mPreviewTextureView_lp = null;
		if(AGlobalConfig.config_module_PHOTO_MODULE_PREVIEW_TYPE == 1) {
			if(myFrameLayout!=null) { // frankie, hook 
				myFrameLayout_mPreviewTextureView_lp = // frankie, add
				myFrameLayout.layoutPreview(ratio);
			}
		}
		
        FrameLayout.LayoutParams lp = null;
        float scaledTextureWidth, scaledTextureHeight;
        int rotation = CameraUtil.getDisplayRotation(mActivity);
        mScreenRatio = CameraUtil.determineRatio(ratio);
        if (mScreenRatio == CameraUtil.RATIO_16_9
                && CameraUtil.determinCloseRatio(ratio) == CameraUtil.RATIO_4_3) {
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
            float width = mMaxPreviewWidth, height = mMaxPreviewHeight;
            if (width == 0 || height == 0) return;
            if(mScreenRatio == CameraUtil.RATIO_4_3) {
                height -=  (mTopMargin + mBottomMargin);
			}
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
                    if(Math.max(width, height * mAspectRatio) > width) {
                        scaledTextureWidth = width;
                        scaledTextureHeight = width / mAspectRatio;
                    } else {
                        scaledTextureWidth = height * mAspectRatio;
                        scaledTextureHeight = height;
                    }
                } else {
                    if(Math.max(height, width * mAspectRatio) > height) {
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
            if(mScreenRatio == CameraUtil.RATIO_4_3) {
                lp.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                lp.setMargins(0, mTopMargin, 0, mBottomMargin);
            }
        }

        if (mSurfaceTextureUncroppedWidth != scaledTextureWidth 
				||mSurfaceTextureUncroppedHeight != scaledTextureHeight) {
				
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

        mSurfaceView.setLayoutParams(lp);
        //mRootView.requestLayout();
        if (mFaceView != null) {
			if(myFrameLayout_mPreviewTextureView_lp != null) { // frankie, add should keep the FaceView the same place as the TextureView !
				mFaceView.setLayoutParams(myFrameLayout_mPreviewTextureView_lp);
			} else {
            	mFaceView.setLayoutParams(lp);
			}
        }
        mIsLayoutInitializedAlready = true;
    }

    public View getRootView() {
        return mRootView;
    }

	//private PieRenderer.PieListener _pieListener = new PieRenderer.PieListener() 
	private PieMenuRenderer.PieListener _pieListener = new PieMenuRenderer.PieListener() 
		{
		    @Override
		    public void onPieOpened(int centerX, int centerY) {
		        setSwipingEnabled(false);
		        if (mFaceView != null) {
		            mFaceView.setBlockDraw(true);
		        }
		        // Close module selection menu when pie menu is opened.
		        //mSwitcher.closePopup();
		    }
		    @Override
		    public void onPieClosed() {
		        setSwipingEnabled(true);
		        if (mFaceView != null) {
		            mFaceView.setBlockDraw(false);
		        }
		    }
		};
    public void onCameraOpened(PreferenceGroup prefGroup, 
								ComboPreferences prefs,
                               Camera.Parameters params, 
                               CameraPreference.OnPreferenceChangedListener listener, 
                               TsMakeupManager.MakeupLevelListener makeupListener) {
        if (mPieRenderer == null) {
            //mPieRenderer = new PieRenderer(mActivity);
            mPieRenderer = new PieMenuRendererImpl(mActivity); // frankie, replace of PieRenderer impl
            //mPieRenderer.setPieListener(this);
            mPieRenderer.setPieListener(_pieListener); // frankie, add
            mRenderOverlay.addRenderer(mPieRenderer);
        }

        if (mMenu == null) {
            mMenu = new PhotoMenu(mActivity, this, makeupListener);
            mMenu.setListener(listener);
        }
        if(mController.currentFrontCamera() == 1){
            mMenu.setIsFrontCamera(true);
        }else{
            mMenu.setIsFrontCamera(false);
        }
        mMenu.initialize(prefGroup);
        mMenuInitialized = true;

        if (mZoomRenderer == null) {
            mZoomRenderer = new ZoomRenderer(mActivity);
            mRenderOverlay.addRenderer(mZoomRenderer);
        }

        if (mGestures == null) {
            // this will handle gesture disambiguation and dispatching
            mGestures = new PreviewGestures(mActivity, this, mZoomRenderer, mPieRenderer);
            mRenderOverlay.setGestures(mGestures);
        }
        mGestures.setPhotoMenu(mMenu);

        mGestures.setZoomEnabled(params.isZoomSupported());
        mGestures.setRenderOverlay(mRenderOverlay);
        mRenderOverlay.requestLayout();

        initializeZoom(params);
        //updateOnScreenIndicators(params, prefGroup, prefs);
        mActivity.setPreviewGestures(mGestures);

		mCameraControls.showRemainingPhotos_(false); // frankie, 2017.07.19, always show remaining photos

		// frankie, 2017.07.26, add start
        Log.v(TAG, "currentFrontCamera() : " + mController.currentFrontCamera());
        String camera_id_ = prefs.getString(CameraSettings.KEY_CAMERA_ID, "-1");
        Log.v(TAG, "camera_id_ : " + camera_id_);
        String chus_face_beauty = prefs.getString(CameraSettings.KEY_CHUS_FACE_BEAUTY, "unknown");
        Log.v(TAG, "chus_face_beauty: " + chus_face_beauty);

//        if(mController.currentFrontCamera() == 1) {
            if(chus_face_beauty.equals("enable")) {
                isFaceBeautyOpen = true;
				// mBubbleSeekProgress == read from preference .
                //mBubbleSeekBar.setProgress(mBubbleSeekProgress);
                if(myFrameLayout != null) { // set initial beauty level
//                    myFrameLayout.setBeautyLevel(mBubbleSeekProgress);
                    myFrameLayout.setBeautyLevel_ext(mVerticalSeekBarProgress);
                }
                //mBubbleSeekBar.setVisibility(View.VISIBLE);
                mVerticalSeekBarLayout.setVisibility(View.VISIBLE);
            }
            else {
                isFaceBeautyOpen = false;
                if(myFrameLayout != null) {	// set initial beauty level
//                    myFrameLayout.setBeautyLevel(0);
                    myFrameLayout.setBeautyLevel_ext(0);
                }
                //mBubbleSeekBar.setVisibility(View.INVISIBLE);
                mVerticalSeekBarLayout.setVisibility(View.INVISIBLE);
            }
//        }
//        else {
//            if(myFrameLayout != null) {	// set initial beauty level
//                myFrameLayout.setBeautyLevel(0);
//            }
//            mBubbleSeekBar.setVisibility(View.INVISIBLE);
//        }
		//mBubbleSeekBar.setVisibility(View.INVISIBLE);	// frankie, anyway hide it
		// frankie, 2017.07.26, add end

//		if(myFrameLayout != null) {
//			myFrameLayout.setBeautyLevel_ext(mVerticalSeekBarProgress);
//		}
		
    }
	public void onFaceBeautyEnableChanged(ComboPreferences prefs) { // frankie, add
		String chus_face_beauty = prefs.getString(CameraSettings.KEY_CHUS_FACE_BEAUTY, "unknown");
		Log.v(TAG, "chus_face_beauty: " + chus_face_beauty);
		if(chus_face_beauty.equals("enable")) {
            isFaceBeautyOpen = true;
			// mBubbleSeekProgress == read from preference .
			//mBubbleSeekBar.setProgress(mBubbleSeekProgress);
            if(myFrameLayout != null) { // set initial beauty level
//                myFrameLayout.setBeautyLevel(mBubbleSeekProgress);
                myFrameLayout.setBeautyLevel_ext(mVerticalSeekBarProgress);
            }
			//mBubbleSeekBar.setVisibility(View.VISIBLE);
            mVerticalSeekBarLayout.setVisibility(View.VISIBLE);
		}
		else {
            isFaceBeautyOpen = false;
			if(myFrameLayout != null) { // set initial beauty level
//				myFrameLayout.setBeautyLevel(0);
                myFrameLayout.setBeautyLevel_ext(0);
			}
            //mBubbleSeekBar.setVisibility(View.INVISIBLE);
            mVerticalSeekBarLayout.setVisibility(View.INVISIBLE);
		}
		//mBubbleSeekBar.setVisibility(View.INVISIBLE);	// frankie, anyway hide it
	}

    public void animateCapture(final byte[] jpegData) {
        // Decode jpeg byte array and then animate the jpeg
        mActivity.updateThumbnail_jpeg(jpegData);
    }

    public void showRefocusToast(boolean show) {
        mCameraControls.showRefocusToast(show);
    }

    //private void openMenu() {
    //    if (mPieRenderer != null) {
    //        // If autofocus is not finished, cancel autofocus so that the
    //        // subsequent touch can be handled by PreviewGestures
    //        if (mController.getCameraState() == PhotoController.FOCUSING) {
    //                mController.cancelAutoFocus();
    //        }
    //        mPieRenderer.showInCenter();
    //    }
    //}

    public void initializeControlByIntent() {
//        if (!mActivity.isSecureCamera()) {
            mThumbnail = (ImageView) mRootView.findViewById(R.id.preview_thumb);
            mThumbnail.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!CameraControls.isAnimating()
                            && mController.getCameraState() != PhotoController.SNAPSHOT_IN_PROGRESS)
                        mActivity.gotoGallery();
                }
            });
//        }
        mMenuButton = mRootView.findViewById(R.id.menu);
        mMenuButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	if(false) {
					Bundle bundle = new Bundle();
					SettingsFragment1 fragment = new SettingsFragment1();
					fragment.setArguments(bundle);
					// use commitAllowingStateLoss() instead of commit(),
					// does to "java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState" crash seen on Google Play
					// see http://stackoverflow.com/questions/7575921/illegalstateexception-can-not-perform-this-action-after-onsaveinstancestate-wit
					mActivity.getFragmentManager()
						.beginTransaction()
						.add(R.id.prefs_container, fragment, "PREFERENCE_FRAGMENT")
						.addToBackStack(null)
						.commitAllowingStateLoss();

//                    Intent intent = new Intent();
//                    intent.setClassName("com.chus.camera", "com.android.camera.module.prefs.SettingsActivity1");
//                    mActivity.startActivity(intent);

				} else {
					if(true) { // frankie, popup
                        if(mController instanceof  PhotoModule) {
                            PhotoModule pm = (PhotoModule)mController;
                            if(pm.isCameraFacingFront()) {
                                mMenu.showPopupWindow_more_settings(v);
                            } else {
                                mMenu.showPopupWindow_more_settings_back(v);
                            }
                        }
						return ;
					}
	                if (mMenu != null) {
	                    mMenu.openFirstLevel();
	                }
				}
            }
        });
        if (mController.isImageCaptureIntent()) {
            hideSwitcher();
            mCameraControls.hideRemainingPhotoCnt();
            //mSwitcher.setSwitcherVisibility(false);
            ViewGroup cameraControls = (ViewGroup) mRootView.findViewById(R.id.camera_controls);
            mActivity.getLayoutInflater().inflate(R.layout.review_module_control, cameraControls);

            mReviewDoneButton = mRootView.findViewById(R.id.btn_done);
            mReviewCancelButton = mRootView.findViewById(R.id.btn_cancel);
//            mReviewRetakeButton = mRootView.findViewById(R.id.btn_retake);
            mReviewImage = (ImageView) mRootView.findViewById(R.id.review_image);
//            mReviewCancelButton.setVisibility(View.VISIBLE);

            mReviewDoneButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mController.onCaptureDone();
                }
            });
            mReviewCancelButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mController.onCaptureCancelled();
                    mController.onCaptureRetake();
                    if(mMenu != null){
                        mMenu.setAuxGrid();
                    }
//                    if (mController.isImageCaptureIntent()) {
//                        mCameraControls.setTitleBarVisibility(View.VISIBLE);
//                    }
                }
            });

//            mReviewRetakeButton.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mController.onCaptureRetake();
//                    if (mController.isImageCaptureIntent()) {
//                        mCameraControls.setTitleBarVisibility(View.VISIBLE);
//                    }
//                }
//            });
        }
    }

    public void hideUI() {
        //mSwitcher.closePopup();
        if (mUIhidden)
            return;
        mUIhidden = true;
        mCameraControls.hideUI();
    }

    public void showUI() {
        if (!mUIhidden || (mMenu != null && mMenu.isMenuBeingShown()))
            return;
        mUIhidden = false;
        mCameraControls.showUI();
    }
	public void showPreviewThumbnail(boolean show) { // frankie, add
		if(show) { mThumbnail.setVisibility(View.VISIBLE); }
		else { mThumbnail.setVisibility(View.INVISIBLE); }
	}
	private AnimatorSet mAnimatorSet = null;
    private long  mAnimatorStartTime = -1;
	public void animatorPreviewThumbnail(){
        if(mAnimatorSet != null) {
            if(mAnimatorStartTime != -1) {
                if(System.currentTimeMillis() < mAnimatorStartTime){
                    return ;
                }
            }
            mAnimatorStartTime = System.currentTimeMillis() + 200;
        }
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(mThumbnail, "scaleX", 1f, 1.3f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(mThumbnail, "scaleY", 1f, 1.3f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.play(scaleXAnimator)
			.with(scaleYAnimator);
        set.setDuration(600);
        set.start();
        mAnimatorSet = set;
    }
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
        //mSwitcher.setCurrentIndex(ModuleSwitcher.PHOTO_MODULE_INDEX);
    }

    public void toggleCamera(){
        if(mMenu == null){
            Log.v("aaa", "++++++++++++  mMenu == null  ");
        }
        if(mMenu != null){
            Log.v("aaa", "++++++++++++  UI ++++++initSwitchItem_camera_id, camera switch click! ");
            mMenu.toggleCamera();
        }
    }

    public boolean isShowFragment(){
        return mMenu.isShowSettingsFragment();
    }

    // called from onResume but only the first time
    public void initializeFirstTime() {
        // Initialize shutter button.
        mShutterButton.setImageResource(R.drawable.chus_shutter_button_anim_1);
		mShutterButton.setImageResource(R.drawable.chus_shutter_button_photo);	// frankie, add
        mShutterButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (!CameraControls.isAnimating())
                    doShutterAnimation();
//                    if (mController.isImageCaptureIntent()) {
//                        mCameraControls.setTitleBarVisibility(View.VISIBLE);
//                    }
            }
        });

        mShutterButton.setOnShutterButtonListener(mController);
        mShutterButton.setVisibility(View.VISIBLE);
    }

    public void doShutterAnimation() {
        Drawable shutterButton1 = mShutterButton.getDrawable();
        if(shutterButton1 instanceof  AnimationDrawable) {		// frankie, add 
            AnimationDrawable frameAnimation = (AnimationDrawable) mShutterButton.getDrawable();
            frameAnimation.stop();
            frameAnimation.start();
        } else if(shutterButton1 instanceof LayerDrawable) {	// frankie, add
        }
    }

    // called from onResume every other time
    public void initializeSecondTime(Camera.Parameters params) {
        initializeZoom(params);
        if (mController.isImageCaptureIntent()) {
            hidePostCaptureAlert();
        }
        if (mMenu != null) {
            mMenu.reloadPreferences();
        }
        //RotateImageView muteButton = (RotateImageView)mRootView.findViewById(R.id.mute_button);
        //muteButton.setVisibility(View.GONE);
    }

    public void initializeZoom(Camera.Parameters params) {
        if ((params == null) || !params.isZoomSupported()
                || (mZoomRenderer == null)) return;
        mZoomMax = params.getMaxZoom();
        mZoomRatios = params.getZoomRatios();
        // Currently we use immediate zoom for fast zooming to get better UX and
        // there is no plan to take advantage of the smooth zoom.
        if (mZoomRenderer != null) {
            mZoomRenderer.setZoomMax(mZoomMax);
            mZoomRenderer.setZoom(params.getZoom());
            mZoomRenderer.setZoomValue(mZoomRatios.get(params.getZoom()));
            mZoomRenderer.setOnZoomChangeListener(new ZoomChangeListener());
        }
    }

    public void overrideSettings(final String ... keyvalues) {
        if (mMenu == null)
            return;
        mMenu.overrideSettings(keyvalues);
    }

    public void updateOnScreenIndicators(Camera.Parameters params, PreferenceGroup group, ComboPreferences prefs) {
        // frankie, rm
//        if (params == null || group == null) return;
//
//        mOnScreenIndicators.updateSceneOnScreenIndicator(params.getSceneMode());
//        mOnScreenIndicators.updateExposureOnScreenIndicator(params, CameraSettings.readExposure(prefs));
//        mOnScreenIndicators.updateFlashOnScreenIndicator(params.getFlashMode());
//        int wbIndex = -1;
//        String wb = Camera.Parameters.WHITE_BALANCE_AUTO;
//        if (Camera.Parameters.SCENE_MODE_AUTO.equals(params.getSceneMode())) {
//            wb = params.getWhiteBalance();
//        }
//        ListPreference pref = group.findPreference(CameraSettings.KEY_WHITE_BALANCE);
//        if (pref != null) {
//            wbIndex = pref.findIndexOfValue(wb);
//        }
//        // make sure the correct value was found
//        // otherwise use auto index
//        mOnScreenIndicators.updateWBIndicator(wbIndex < 0 ? 2 : wbIndex);
//        boolean location = RecordLocationPreference.get(prefs);
//        mOnScreenIndicators.updateLocationIndicator(location);
    }

    public void setCameraState(int state) {
    }

    public void animateFlash() {
        mAnimationManager.startFlashAnimation(mFlashOverlay);
    }

    public void enableGestures(boolean enable) {
        if (mGestures != null) {
            mGestures.setEnabled(enable);
        }
    }

    public void setFrontBackEnable(boolean b){
//        if(mMenu != null){
//        }
        mCameraControls.setFrontBackSwitcherEnable(b);
    }

    // forward from preview gestures to controller
    @Override
    public void onSingleTapUp(View view, int x, int y) {
        mController.onSingleTapUp(view, x, y);
    }

    public boolean onBackPressed() {
		Log.v("CAM_b", "PhotoUI, onBackPressed");
		if(onBackPressed_FiltersView()) { // frankie, 
			return true;
		}
        if (mMenu != null && mMenu.handleBackKey()) {
            return true;
        }

        if (mPieRenderer != null && mPieRenderer.showsItems()) {
            mPieRenderer.hide();
            return true;
        }
        // In image capture mode, back button should:
        // 1) if there is any popup, dismiss them, 2) otherwise, get out of
        // image capture
        if (mController.isImageCaptureIntent()) {
            mController.onCaptureCancelled();
            return true;
        } else if (!mController.isCameraIdle()) {
            // ignore backs while we're taking a picture
            return true;
        } 

		//if (mSwitcher != null && mSwitcher.showsPopup()) {
        //    mSwitcher.closePopup();
        //    return true;
        //} else 
        {
            return false;
        }
    }

    public void onPreviewFocusChanged(boolean previewFocused) {
		Log.v(TAG, "onPreviewFocusChanged: " + previewFocused); // frankie, 
        if (previewFocused) {
            showUI();
        } else {
            hideUI();
        }
        if (mFaceView != null) {
            mFaceView.setBlockDraw(!previewFocused);
        }
        if (mGestures != null) {
            mGestures.setEnabled(previewFocused);
        }
        if (mRenderOverlay != null) {
            // this can not happen in capture mode
            mRenderOverlay.setVisibility(previewFocused ? View.VISIBLE : View.GONE);
        }
        if (mPieRenderer != null) {
            mPieRenderer.setBlockFocus(!previewFocused);
        }
        //setShowMenu(previewFocused);
        if (!previewFocused && mCountDownView != null) mCountDownView.cancelCountDown();
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

    public void setMakeupMenuLayout(LinearLayout layout) {
        mMakeupMenuLayout = layout;
    }

    public void showPopup(ViewGroup popup, int level, boolean animate) {
		int orientation_ = mOrientation;
		orientation_ = 0; // frankie, fix 
		
        FrameLayout.LayoutParams params;
        hideUI();

        popup.setVisibility(View.VISIBLE);
        if (level == 1) {
			
			if (mSubMenuLayout != null) { // frankie, add ensure mSubMenuLayout is on top of the first level
				((ViewGroup) mRootView).removeView(mSubMenuLayout);
				mSubMenuLayout = null;
			}

            if (mMenuLayout == null) {
                //mMenuLayout = new RotateLayout(mActivity, null);
				mMenuLayout = new FrameLayout(mActivity, null);
                if (mRootView.getLayoutDirection() != View.LAYOUT_DIRECTION_RTL) {
                    params = new FrameLayout.LayoutParams(
                            //CameraActivity.SETTING_LIST_WIDTH_1, LayoutParams.WRAP_CONTENT,
                            LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT,
                            Gravity.LEFT | Gravity.TOP);
                } else {
                    params = new FrameLayout.LayoutParams(
                            //CameraActivity.SETTING_LIST_WIDTH_1, LayoutParams.WRAP_CONTENT,
                            LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT,
                            Gravity.RIGHT | Gravity.TOP);
                }
                mMenuLayout.setBackgroundColor(0xff000000); // frankie, black
                mMenuLayout.setLayoutParams(params);

				if(MENU_BACKGROUND_VIEW_ENABLE) {
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
                ((ViewGroup) mRootView).addView(mSubMenuLayout);
            }
            if (mRootView.getLayoutDirection() != View.LAYOUT_DIRECTION_RTL) {
                params = new FrameLayout.LayoutParams(
                        CameraActivity.SETTING_LIST_WIDTH_2, LayoutParams.WRAP_CONTENT,
                        Gravity.LEFT | Gravity.TOP);
            } else {
                params = new FrameLayout.LayoutParams(
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
                params.setMargins(margin___, y, 0, 0);
            } else {
                params.setMargins(0, y, margin___, 0);
            }

            mSubMenuLayout.setLayoutParams(params);

            mSubMenuLayout.addView(popup);
            //mSubMenuLayout.setOrientation(orientation_, true);
        }
        if (animate) {
            if (level == 1) {
                //mMenu.animateSlideIn(mMenuLayout, CameraActivity.SETTING_LIST_WIDTH_1, true);
				//mMenu.animateSlideIn(mMenuLayout, mRootView.getWidth(), true);
				mMenu.animateFadeIn(mMenuLayout);
				if(mMenuLayoutView!= null) {
					mMenu.animateFadeIn(mMenuLayoutView);
				}

			}
            if (level == 2) {
                mMenu.animateFadeIn(popup);
			}
        } else {
            //popup.setAlpha(0.85f);
            popup.setAlpha(1.0f); // frankie,
        }
    }

    public void removeLevel2() {
        if (mSubMenuLayout != null) {
            //View v = mSubMenuLayout.getChildAt(0);
            //mSubMenuLayout.removeView(v);
            mSubMenuLayout.removeAllViews();
        }
    }

    public void showPopup(AbstractSettingPopup popup) {
        hideUI();

        if (mPopup == null) {
            mPopup = new PopupWindow(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            mPopup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mPopup.setOutsideTouchable(true);
            mPopup.setFocusable(true);
            mPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mPopup = null;
                    // mMenu.popupDismissed(mDismissAll);
                    mDismissAll = false;
                    showUI();

                    // Switch back into fullscreen/lights-out mode after popup
                    // is dimissed.
                    mActivity.setSystemBarsVisibility(false);
                }
            });
        }
        popup.setVisibility(View.VISIBLE);
        mPopup.setContentView(popup);
        mPopup.showAtLocation(mRootView, Gravity.CENTER, 0, 0);
    }

    public void cleanupListview() {
        showUI();
        mActivity.setSystemBarsVisibility(false);
    }

    public void dismissPopup() {
        if (mPopup != null && mPopup.isShowing()) {
            mPopup.dismiss();
        }
    }

    private boolean mDismissAll = false;
    public void dismissAllPopup() {
        mDismissAll = true;
        if (mPopup != null && mPopup.isShowing()) {
            mPopup.dismiss();
        }
    }

    public void dismissLevel1() {
        if (mMenuLayout != null) {
            ((ViewGroup) mRootView).removeView(mMenuLayout);
			if(mMenuLayoutView != null) {
				((ViewGroup) mRootView).removeView(mMenuLayoutView); // frankie, add
				mMenuLayoutView = null; // frankie, 
			}
            mMenuLayout = null;
        }
    }

    public void dismissLevel2() {
        if (mSubMenuLayout != null) {
            ((ViewGroup) mRootView).removeView(mSubMenuLayout);
            mSubMenuLayout = null;
        }
    }

    public boolean sendTouchToPreviewMenu(MotionEvent ev) {
        if (mPreviewMenuLayout != null) {
            return mPreviewMenuLayout.dispatchTouchEvent(ev);
        }
        return false;
    }

    public boolean sendTouchToMenu(MotionEvent ev) {
        if (mMenuLayout != null) {
            View v = mMenuLayout.getChildAt(0);
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

    public void onShowSwitcherPopup() {
        if (mPieRenderer != null && mPieRenderer.showsItems()) {
            mPieRenderer.hide();
        }
    }

    //private void setShowMenu(boolean show) {
    //    if (mOnScreenIndicators != null) {
    //        mOnScreenIndicators.setVisibility(show ? View.VISIBLE : View.GONE);
    //    }
    //}

    public boolean collapseCameraControls() {
        // TODO: Mode switcher should behave like a popup and should hide itself when there
        // is a touch outside of it.
        //mSwitcher.closePopup();
        // Remove all the popups/dialog boxes
        boolean ret = false;
        if (mMenu != null) {
            mMenu.removeAllView();
        }
        if (mPopup != null) {
            dismissAllPopup();
            ret = true;
        }
        onShowSwitcherPopup();
        mCameraControls.showRefocusToast(false);
        return ret;
    }


    private class DecodeTask extends AsyncTask<Void, Void, Bitmap> {
        private final byte [] mData;
        private int mOrientation;
        private boolean mMirror;
        public DecodeTask(byte[] data, int orientation, boolean mirror) {
            mData = data;
            mOrientation = orientation;
            mMirror = mirror;
        }
        @Override
        protected Bitmap doInBackground(Void... params) {
            // Decode image in background.
            Bitmap bitmap = CameraUtil.downSample(mData, mDownSampleFactor);
            if ((mOrientation != 0 || mMirror) && (bitmap != null)) {
                Matrix m = new Matrix();
                if (mMirror) {
                    // Flip horizontally
                    m.setScale(-1f, 1f);
                }
                m.preRotate(mOrientation);
                return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m,
                        false);
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
        }
    }
    private class DecodeImageForReview extends DecodeTask {
        public DecodeImageForReview(byte[] data, int orientation, boolean mirror) {
            super(data, orientation, mirror);
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                return;
            }
            mReviewImage.setImageBitmap(bitmap);
            mReviewImage.setVisibility(View.VISIBLE);
            mDecodeTaskForReview = null;
        }
    }

    protected void showCapturedImageForReview(byte[] jpegData, int orientation, boolean mirror) {
//        mCameraControls.hideCameraSettings();
        mDecodeTaskForReview = new DecodeImageForReview(jpegData, orientation, mirror);
        mDecodeTaskForReview.execute();
        //mOnScreenIndicators.setVisibility(View.GONE);
//        mMenuButton.setVisibility(View.GONE);
        CameraUtil.fadeIn(mReviewDoneButton);
        mShutterButton.setVisibility(View.INVISIBLE);
//        CameraUtil.fadeIn(mReviewRetakeButton);
        CameraUtil.fadeIn(mReviewCancelButton);
        mActivity.setSlideSwitcherShow(false,true);
//        mMenu.hideTopMenu(true);
        mMenu.hideCameraControls(true);
        hideVerticalSeekBarLyt(true);
        pauseFaceDetection();
    }

    private void hideVerticalSeekBarLyt(boolean isHide){
        final int status = (isHide) ? View.INVISIBLE : View.VISIBLE;
        if(isFaceBeautyOpen){
            mVerticalSeekBarLayout.setVisibility(status);
        }
    }

    protected void hidePostCaptureAlert() {
//        mCameraControls.showCameraSettings();
        if (mDecodeTaskForReview != null) {
            mDecodeTaskForReview.cancel(true);
        }
        mReviewImage.setVisibility(View.GONE);
        //mOnScreenIndicators.setVisibility(View.VISIBLE);
//        mMenuButton.setVisibility(View.VISIBLE);
        if (mMenu != null) {
//            mMenu.hideTopMenu(false);
            mMenu.hideCameraControls(false);
        }
        hideVerticalSeekBarLyt(false);
        CameraUtil.fadeOut(mReviewDoneButton);
        mShutterButton.setVisibility(View.VISIBLE);
//        CameraUtil.fadeOut(mReviewRetakeButton);
        CameraUtil.fadeOut(mReviewCancelButton);
        mActivity.setSlideSwitcherShow(true,false);
        resumeFaceDetection();
    }

    public void setDisplayOrientation(int orientation) {
        if (mFaceView != null) {
            mFaceView.setDisplayOrientation(orientation);
        }
        if ((mPreviewOrientation == -1 || mPreviewOrientation != orientation)
                && mMenu != null && mMenu.isPreviewMenuBeingShown()) {
            dismissSceneModeMenu();
            mMenu.addModeBack();
        }
        mPreviewOrientation = orientation;
    }

    // shutter button handling

    public boolean isShutterPressed() {
        return mShutterButton.isPressed();
    }

    /**
     * Enables or disables the shutter button.
     */
    public void enableShutter(boolean enabled) {
        if (mShutterButton != null) {
            mShutterButton.setEnabled(enabled);
        }
    }

    public void enableThumbnail(boolean enabled) {
        if (mThumbnail != null) {
            Log.v("aaa","++++++++++++++mThumbnail:"+enabled);
            mThumbnail.setClickable(enabled);
//            mThumbnail.setEnabled(enabled);
        }
    }

    public void pressShutterButton() {
        if (mShutterButton.isInTouchMode()) {
            mShutterButton.requestFocusFromTouch();
        } else {
            mShutterButton.requestFocus();
        }
        mShutterButton.setPressed(true);
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
                mPieRenderer.hide();
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

//    @Override
//    public void onPieOpened(int centerX, int centerY) {
//        setSwipingEnabled(false);
//        if (mFaceView != null) {
//            mFaceView.setBlockDraw(true);
//        }
//        // Close module selection menu when pie menu is opened.
//        //mSwitcher.closePopup();
//    }
//    @Override
//    public void onPieClosed() {
//        setSwipingEnabled(true);
//        if (mFaceView != null) {
//            mFaceView.setBlockDraw(false);
//        }
//    }

    public void setSwipingEnabled(boolean enable) {
        mActivity.setSwipingEnabled(enable);
    }

    public SurfaceHolder getSurfaceHolder_1() { // frankie, 
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
    // Countdown timer

    private void initializeCountDown() {
        mActivity.getLayoutInflater().inflate(R.layout.count_down_to_capture,(ViewGroup) mRootView, true);
        mCountDownView = (CountDownView) (mRootView.findViewById(R.id.count_down_to_capture));
        mCountDownView.setCountDownFinishedListener((CountDownView.OnCountDownFinishedListener) mController);
        mCountDownView.bringToFront();
        mCountDownView.setOrientation(mOrientation);
    }

    public boolean isCountingDown() {
        return mCountDownView != null && mCountDownView.isCountingDown();
    }

    public void cancelCountDown() {
        if (mCountDownView == null) { return; }
        mCountDownView.cancelCountDown();
        showUIAfterCountDown();
    }

    public void startCountDown(int sec, boolean playSound) {
        if (mCountDownView == null) { initializeCountDown(); }
        mCountDownView.startCountDown(sec, playSound);
        hideUIWhileCountDown();
    }

    public void startSelfieFlash() {
        if(mSelfieView == null)
            mSelfieView = (SelfieFlashView) (mRootView.findViewById(R.id.selfie_flash));
        mSelfieView.bringToFront();
        mSelfieView.open();
        mScreenBrightness = setScreenBrightness(1F);
    }

    public void stopSelfieFlash() {
        if(mSelfieView == null)
            mSelfieView = (SelfieFlashView) (mRootView.findViewById(R.id.selfie_flash));
        mSelfieView.close();
        if(mScreenBrightness != 0.0f)
            setScreenBrightness(mScreenBrightness);
    }

    private float setScreenBrightness(float brightness) {
        float originalBrightness;
        Window window = mActivity.getWindow();
        WindowManager.LayoutParams layout = window.getAttributes();
        originalBrightness = layout.screenBrightness;
        layout.screenBrightness = brightness;
        window.setAttributes(layout);
        return originalBrightness;
    }

    public void showPreferencesToast() {
        if (mNotSelectableToast == null) {
            String str = mActivity.getResources().getString(R.string.not_selectable_in_scene_mode);
            mNotSelectableToast = RotateTextToast.makeText(mActivity, str, Toast.LENGTH_SHORT);
        }
        mNotSelectableToast.show();
    }

    public void showPreviewCover() {
        Log.v(TAG, "showPreviewCover"); // frankie,
        if(true) {
			mPreviewCover.setAlpha(0.00001f);
			mPreviewCover.setVisibility(View.VISIBLE);
			return ;
		}
        //mPreviewCover.setVisibility(View.VISIBLE);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mPreviewCover, "alpha", 0.00001f, 1f);
        alphaAnimator.setDuration(200);
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
        // Hide the preview cover if need.
        if (mPreviewCover.getVisibility() != View.GONE) {
            //mPreviewCover.setVisibility(View.GONE);

            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mPreviewCover, "alpha", 1f, 0.00001f);
            alphaAnimator.setDuration(200);
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

	public void onPauseBeforeSuper_1() {
		hideFiltersView(false);
	}
    public void onPause() {
        cancelCountDown();

        // Clear UI.
        collapseCameraControls();
        if (mFaceView != null) {
			mFaceView.clear();
		}

        if (mLocationDialog != null && mLocationDialog.isShowing()) {
            mLocationDialog.dismiss();
        }
        mLocationDialog = null;
    }
	public void onPause_1_1() { // frankie, add 
		if(myFrameLayout!=null) {myFrameLayout.onPause();}
	}
	public void onPause_1() { // frankie, add 
		mOnLayoutChangeHandler.removeMessages(0);
		onColorEffectPause();
		if(myFrameLayout!=null) {myFrameLayout.onPause();}
        mUIhidden = false;
	}


    public void initDisplayChangeListener() {
        ((CameraRootView) mRootView).setDisplayChangeListener(this);
    }

    public void removeDisplayChangeListener() {
        ((CameraRootView) mRootView).removeDisplayChangeListener();
    }

	// FocusOverlayManager.FocusUI IMPL start
    // focus UI implementation
    private FocusIndicator getFocusIndicator() {
        return (mFaceView != null && mFaceView.faceExists()) ? mFaceView : mPieRenderer;
    }
    public void clearFaces() {
        if (mFaceView != null) mFaceView.clear();
    }

    @Override
    public boolean hasFaces() {
        return (mFaceView != null && mFaceView.faceExists());
    }
    @Override
    public void clearFocus() {
        FocusIndicator indicator = mPieRenderer;
        if (hasFaces()) {
            mFaceView.showStart();
        }
        if (indicator != null) indicator.clear();
    }
    @Override
    public void setFocusPosition(int x, int y) {
        mPieRenderer.setFocus(x, y);
    }
    @Override
    public void onFocusStarted() {
        FocusIndicator indicator = getFocusIndicator();
        if (indicator != null) indicator.showStart();
    }
    @Override
    public void onFocusSucceeded(boolean timeout) {
        FocusIndicator indicator = getFocusIndicator();
        if (indicator != null) indicator.showSuccess(timeout);
    }
    @Override
    public void onFocusFailed(boolean timeout) {
        FocusIndicator indicator = getFocusIndicator();
        if (indicator != null) indicator.showFail(timeout);
    }
    @Override
    public void pauseFaceDetection() {
        if (mFaceView != null) mFaceView.pause();
    }
    @Override
    public void resumeFaceDetection() {
        if (mFaceView != null) mFaceView.resume();
    }
	// FocusOverlayManager.FocusUI IMPL end
	
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

    /*
    private long lastTime = 0;
    private static final long BRIGHT_SCRERN_DURATION = 7 * 1000;
    private void refresh(Face[] faces){
        long temp = System.currentTimeMillis();
        if(faces != null && faces.length > 0 && temp - lastTime >BRIGHT_SCRERN_DURATION) {
            lastTime = temp;
            RectF mRect = mFaceView.getRectF();
            onSingleTapUp(null, ((int) (mRect.right + mRect.left) / 2), ((int) (mRect.bottom + mRect.top) / 2));
        }
    }
    */

    @Override // impl CameraManager.CameraFaceDetectionCallback
    public void onFaceDetection(Camera.Face[] faces, CameraManager.CameraProxy camera) {
        if(mFaceView != null){
            mFaceView.setFaces(faces);
            
            if(faces != null && faces.length > 0){
                //refresh(faces);
            }
        }
    }


    @Override
    public void onDisplayChanged() {
        Log.d(TAG, "Device flip detected.");
        mCameraControls.checkLayoutFlip();
        mController.updateCameraOrientation();
    }

    public void setPreference(String key, String value) {
        mMenu.setPreference(key, value);
    }

    public void updateRemainingPhotos(int remaining) {
        mCameraControls.updateRemainingPhotos(remaining);
    }

    public void setOrientation(int orientation, boolean animation) {
		Log.v(TAG, "setOrientation, orientation:" + orientation + " animation:" + animation);
        mOrientation = orientation;
        mCameraControls.setOrientation(orientation, animation);
        if (mMenuHelp != null)
            mMenuHelp.setOrientation(orientation, animation);
        if (mMenuLayout != null) { // frankie, rm
            //mMenuLayout.setOrientation(orientation, animation);
		}
        if (mSubMenuLayout != null) { // frankie, rm
            //mSubMenuLayout.setOrientation(orientation, animation);
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
        if(mMakeupMenuLayout != null) {
            View view = mMakeupMenuLayout.getChildAt(0);
            if(view instanceof RotateLayout) {
                for(int i = mMakeupMenuLayout.getChildCount() -1; i >= 0; --i) {
                    RotateLayout l = (RotateLayout) mMakeupMenuLayout.getChildAt(i);
                    l.setOrientation(orientation, animation);
                }
            } else {
                ViewGroup vg = (ViewGroup) mMakeupMenuLayout.getChildAt(1);
                if(vg != null) {
                    for (int i = vg.getChildCount() - 1; i >= 0; --i) {
                        ViewGroup vewiGroup = (ViewGroup) vg.getChildAt(i);
                        if(vewiGroup instanceof RotateLayout) {
                            RotateLayout l = (RotateLayout) vewiGroup;
                            l.setOrientation(orientation, animation);
                        }
                    }
                }
            }

        }
        if (mCountDownView != null)
            mCountDownView.setOrientation(orientation);
        RotateTextToast.setOrientation(orientation);
        if (mFaceView != null) {
            mFaceView.setDisplayRotation(orientation);
        }
        if (mZoomRenderer != null) {
            mZoomRenderer.setOrientation(orientation);
        }
        if (mReviewImage != null) {
            RotateImageView v = (RotateImageView) mReviewImage;
            v.setOrientation(orientation, animation);
        }
    }

    public void tryToCloseSubList() {
        if (mMenu != null)
            mMenu.tryToCloseSubList();
    }

    public int getOrientation() {
        return mOrientation;
    }

    public void adjustOrientation() {
        setOrientation(mOrientation, true);
    }

//    private void showFirstTimeHelp__(int topMargin, int bottomMargin) {
//        mMenuHelp = (MenuHelp) mRootView.findViewById(R.id.menu_help);
//        mMenuHelp.setVisibility(View.VISIBLE);
//        mMenuHelp.setMargins(topMargin, bottomMargin);
//        mMenuHelp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mMenuHelp != null) {
//                    mMenuHelp.setVisibility(View.GONE);
//                    mMenuHelp = null;
//                }
//            }
//        });
//    }
//    /*public*/private void showFirstTimeHelp() {
//        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
//        boolean isMenuShown = prefs.getBoolean(CameraSettings.KEY_SHOW_MENU_HELP, false);
//        if(!isMenuShown) {
//            showFirstTimeHelp__(mTopMargin, mBottomMargin);
//            SharedPreferences.Editor editor = prefs.edit();
//            editor.putBoolean(CameraSettings.KEY_SHOW_MENU_HELP, true);
//            editor.apply();
//        }
//    }

    public void showRefocusDialog() {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
        int prompt = prefs.getInt(CameraSettings.KEY_REFOCUS_PROMPT, 1);
        if (prompt == 1) {
            AlertDialog dialog = new AlertDialog.Builder(mActivity)
                .setTitle(R.string.refocus_prompt_title)
                .setMessage(R.string.refocus_prompt_message)
                .setPositiveButton(R.string.dialog_ok, null)
                .show();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(CameraSettings.KEY_REFOCUS_PROMPT, 0);
                editor.apply();

        }
    }

    private void hideUIWhileCountDown() {
        mMenu.hideCameraControls(true);
        hideVerticalSeekBarLyt(true);
        mGestures.setZoomOnly(true);
    }

    public void showUIAfterCountDown() {
        mMenu.hideCameraControls(false);
        hideVerticalSeekBarLyt(false);
        mGestures.setZoomOnly(false);
    }

	public void onUserInteraction() {	// frankie, add 
        Log.v(TAG, "onUserInteraction");
		
		if(mTopColorEffectAnimating || mTopColorEffectContainer != null) { // is filter mode shown
			if(CONFIG_FILTER_MODE_AUTO_EXIT_EN) {
				mColorMainHandler.removeMessages(MSG_FILTER_MODE_AUTO_EXIT);
				mColorMainHandler.sendEmptyMessageDelayed(MSG_FILTER_MODE_AUTO_EXIT, FILTER_MODE_AUTO_EXIT_TIMEOUT);
			}
        }
		

	}
	//////////////////////////////////////////////////////////////////////////////////////////////
    private static final boolean COLOR_EFFECT_SHOW_HIDE_AUTO_TEST_EN = false; // false; // true;
	private static final int FILTER_GRIDE_SHOW_DURATION = 500;
	private static final int FILTER_GRIDE_HIDE_DURATION = 500;

	private boolean filterShowOnExit = false;

    private static final boolean CONFIG_FILTER_MODE_AUTO_EXIT_EN = true;
	private static final int MSG_FILTER_MODE_AUTO_EXIT = 1;
    private static final int FILTER_MODE_AUTO_EXIT_TIMEOUT = 60*1000; // 60 seconds
    private final Handler mColorMainHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == MSG_FILTER_MODE_AUTO_EXIT) {
				onBackPressed_FiltersView();
				this.removeMessages(MSG_FILTER_MODE_AUTO_EXIT);
			}
		}
	};
	
    private MyColorEffectTopLinearLayout.OnFilterClickListener mOnFilterClickListener = new
            MyColorEffectTopLinearLayout.OnFilterClickListener() {
                @Override
                public void onClick(int typeIndex) {
                    if(mTopColorEffectAnimating || mTopColorEffectContainer == null) {
                        return ;
                    }
                    if(mController instanceof PhotoModule) {
                        PhotoModule pm = (PhotoModule)mController;
                        pm.persistChusFilterType(typeIndex);
                        mMenu.updateFilterControlIcon();
                    }
                    if(myFrameLayout != null) {
                        myFrameLayout.setFilterType(typeIndex);
                    }
					if(mActivity.mTrickerControlBinder.getControlWrapper() != null) { // frankie, 2018.01.30
	                    mActivity.mTrickerControlBinder.getControlWrapper().setFilterIndex(1, typeIndex);
					}
                    hideFiltersView(true);
                }
            };
    public void onFilterButtonClicked() {
        if(mTopColorEffectAnimating || mTopColorEffectContainer != null) {
            return ;
        }
        showFiltersView();

        if(mTopColorEffectContainer != null) {
            final MyColorEffectTopLinearLayout colorViewGroup = (MyColorEffectTopLinearLayout) mTopColorEffectContainer;
            colorViewGroup.setOnFilterClickListener(mOnFilterClickListener);
			if(myFrameLayout != null) {
				int currentFilterType = myFrameLayout.getFilterType();
				Log.w(TAG, "currentFilterType:" + currentFilterType);
				colorViewGroup.setCurrentFilterType(currentFilterType);
			}

            Button group_button = (Button) mTopColorEffectContainer.findViewById(R.id.change_group_button_01);
			if(AGlobalConfig.config_module_PHOTO_MODULE_enable_filter_group_change_button) {
				group_button.setVisibility(View.VISIBLE);
			} else {
				group_button.setVisibility(View.INVISIBLE);
			}
            group_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyColorEffectTopLinearLayout.mTextureFilterGroupIndex++;
                    if(MyColorEffectTopLinearLayout.mTextureFilterGroupIndex > 4) {
                        MyColorEffectTopLinearLayout.mTextureFilterGroupIndex = 0;
                    }
                    //Toast.makeText(mActivity, "" + MyColorEffectTopLinearLayout.mTextureFilterGroupIndex,
                    //        Toast.LENGTH_SHORT).show();
                    filterShowOnExit = true;
					hideFiltersView(true);
                }
            });
            ImageView exit_button = (ImageView) mTopColorEffectContainer.findViewById(R.id.exit_button_01);
            exit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideFiltersView(true);
                }
            });

        }
    }
	ViewGroup mTopColorEffectContainer = null;
    boolean mTopColorEffectAnimating = false;
    private boolean onBackPressed_FiltersView() {
        if(COLOR_EFFECT_SHOW_HIDE_AUTO_TEST_EN) {
            mColorMainHandler.removeCallbacks(testHideRunnable);
            mColorMainHandler.removeCallbacks(testShowRunnable);
        }
        if(CONFIG_FILTER_MODE_AUTO_EXIT_EN) {
            mColorMainHandler.removeMessages(MSG_FILTER_MODE_AUTO_EXIT);
        }

        if(mTopColorEffectContainer != null && !mTopColorEffectAnimating) {
            mTopColorEffectAnimating = true;
            hideFiltersView(true);
            return true;
        }
        if(mTopColorEffectAnimating) {
            return true;
        }
        return false;
    }

    private Runnable testHideRunnable = new Runnable() {
        @Override
        public void run() {
            hideFiltersView(true);
        }
    };
    private Runnable testShowRunnable = new Runnable() {
        @Override
        public void run() {
            onFilterButtonClicked();
        }
    };
	private void onColorEffectResume() {
		Log.v(TAG, "onColorEffectResume");
    }
    private void onColorEffectPause() {
		Log.v(TAG, "onColorEffectPause");
        if(COLOR_EFFECT_SHOW_HIDE_AUTO_TEST_EN) {
            mColorMainHandler.removeCallbacks(testHideRunnable);
            mColorMainHandler.removeCallbacks(testShowRunnable);
        }
        if(CONFIG_FILTER_MODE_AUTO_EXIT_EN) {
            mColorMainHandler.removeMessages(MSG_FILTER_MODE_AUTO_EXIT);
        }
    }
    private void showFiltersViewDone() {
        final MyColorEffectTopLinearLayout colorViewGroup = (MyColorEffectTopLinearLayout) mTopColorEffectContainer;
        if(colorViewGroup != null){
            colorViewGroup.setAlpha(1.0f);
            colorViewGroup.setVisibility(View.VISIBLE);
        }
        mTopColorEffectAnimating = false;

        if(COLOR_EFFECT_SHOW_HIDE_AUTO_TEST_EN) {
            mColorMainHandler.postDelayed(testHideRunnable, 500);
        }
        if(CONFIG_FILTER_MODE_AUTO_EXIT_EN) {
            mColorMainHandler.removeMessages(MSG_FILTER_MODE_AUTO_EXIT);
            mColorMainHandler.sendEmptyMessageDelayed(MSG_FILTER_MODE_AUTO_EXIT, FILTER_MODE_AUTO_EXIT_TIMEOUT);
        }
    }
    private void showFiltersView() {
		filterShowOnExit = false;
		mActivity.setSlideSwitcherShow(false, true); // frankie, add 
        if(mTopColorEffectContainer == null) {
            final FrameLayout llayout = (FrameLayout) mRootView.findViewById(R.id.color_effect_root_1);
            Log.v(TAG, "llayout " + (llayout!=null ? "!= null" : " == null"));

            LayoutInflater layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			int layout_resource = R.layout.color_effect_top_inc;
			if(AGlobalConfig.config_module_PHOTO_MODULE_use_new_color_effect_layout) {
				layout_resource = R.layout.color_effect_top_inc2;	// *** new TextureView sub class impl
			}
            ViewGroup topColorEffectContainer = (ViewGroup) layoutInflater.inflate(layout_resource, null, false);
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
			Log.v(TAG, "myFrameLayout" + (myFrameLayout != null ? " != null" : " == null"));
            if (myFrameLayout != null) {
                MyFrameLayout.CameraPreviewInformation cameraPreviewInfo = myFrameLayout.getCameraPreviewInformation(); // *** always hang here !
                if(cameraPreviewInfo == null){
                    return;
                }
                Log.w(TAG, "cameraPreviewInfo:" + cameraPreviewInfo.width + " * " + cameraPreviewInfo.height
                        + " ori:" + cameraPreviewInfo.orientation + " front:" + cameraPreviewInfo.isFront);
                colorViewGroup.setCameraPreviewInfo(cameraPreviewInfo.width, cameraPreviewInfo.height, cameraPreviewInfo.orientation, cameraPreviewInfo.isFront);

                if(cameraPreviewInfo.width > cameraPreviewInfo.height) {
                    float ratio = (float)cameraPreviewInfo.width / (float)cameraPreviewInfo.height;
                    colorViewGroup.setAspectRatio_ext(ratio);
                } else {
                    float ratio = (float)cameraPreviewInfo.height / (float)cameraPreviewInfo.width;
                    colorViewGroup.setAspectRatio_ext(ratio);
                }
                EGLContext r = myFrameLayout.get__eglContext();
                Log.v(TAG, "get__eglContext for share :" + (r != null ? " != null" : " == null"));
                if (r != null) {
                    colorViewGroup.createThread_withContext(r); // only init the glThreadBuilder , not really create the thread !
                } else {
                    colorViewGroup.createThread__();
                }
				colorViewGroup.onResume(); // frankie, add
				
            }
            colorViewGroup.setTextureFrameReadyCallback(new MyColorEffectTopLinearLayout.TextureFrameReadyCallback() {
                @Override
                public void onReady() {
                    Log.v(TAG, "*** all TextureFrameReadyCallback ***"); // at this time show the colorViewGroup !
                    ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(colorViewGroup, "alpha", 0.00001f, 1f);
                    alphaAnimator.setDuration(FILTER_GRIDE_SHOW_DURATION);
                    alphaAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationCancel(Animator animation) {
                            Log.v(TAG, "onAnimationCancel");
                            showFiltersViewDone();
                        }
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            Log.v(TAG, "onAnimationEnd");
                            showFiltersViewDone();
                        }
                    });

                    ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(colorViewGroup, "scaleX", 10.0f, 1.0f).setDuration(FILTER_GRIDE_SHOW_DURATION);
                    ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(colorViewGroup, "scaleY", 10.0f, 1.0f).setDuration(FILTER_GRIDE_SHOW_DURATION);
                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator);
                    set.setDuration(FILTER_GRIDE_SHOW_DURATION);
                    set.start();
                }
            });
			if (myFrameLayout != null) {
				myFrameLayout.setFrameAvaiableCallback(new MyFrameLayout.FrameAvaiableCallback() {
					@Override
					public void onFrameAvaiable(int texture_id, SurfaceTexture surfaceTexture, float[] matrix) {
						//Log.v(TAG, "FrameAvaiableCallback: " + texture_id);
						colorViewGroup.onSurfaceTextureAvaiable__(texture_id, surfaceTexture, matrix);
					}
				});
			}

            topColorEffectContainer.setVisibility(View.VISIBLE);
            topColorEffectContainer.setAlpha(0.00001f);
            topColorEffectContainer.setLayoutParams(
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,
                    Gravity.CENTER);
			
            llayout.addView(topColorEffectContainer, lp);

            
            mTopColorEffectAnimating = true;
			mTopColorEffectContainer = topColorEffectContainer;
        }
    }
	private void hideFiltersViewDone() {
		final FrameLayout llayout = (FrameLayout) mActivity.findViewById(R.id.color_effect_root_1);
		final MyColorEffectTopLinearLayout colorViewGroup = (MyColorEffectTopLinearLayout) mTopColorEffectContainer;
		if(colorViewGroup != null) {
			colorViewGroup.onPause();
			colorViewGroup.requestExitAndWait();
			colorViewGroup.destroyRendererThread();
			colorViewGroup.setAlpha(0.00001f);
			colorViewGroup.setVisibility(View.INVISIBLE);
			//colorViewGroup.removeAllViews();
			colorViewGroup.setVisibility(View.GONE);
		}
		if (myFrameLayout != null) {
			myFrameLayout.setFrameAvaiableCallback(null);
		}
		llayout.removeAllViews();		// *** 
		
		mTopColorEffectContainer = null;
		mTopColorEffectAnimating = false;
		mActivity.setSlideSwitcherShow(true, false); // frankie, add

		if(COLOR_EFFECT_SHOW_HIDE_AUTO_TEST_EN) {
			mColorMainHandler.postDelayed(testShowRunnable, 500);
		}
		if(filterShowOnExit) {
			filterShowOnExit = false;
			mColorMainHandler.postDelayed(testShowRunnable, 100);
		}
        if(CONFIG_FILTER_MODE_AUTO_EXIT_EN) {
            mColorMainHandler.removeMessages(MSG_FILTER_MODE_AUTO_EXIT);
        }
	}
	
	private void hideFiltersView(boolean withAnimation) {
		if(mTopColorEffectContainer != null) {
			final FrameLayout llayout = (FrameLayout) mActivity.findViewById(R.id.color_effect_root_1);
			final MyColorEffectTopLinearLayout colorViewGroup = (MyColorEffectTopLinearLayout) mTopColorEffectContainer;

			if(withAnimation) {
				ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(colorViewGroup, "alpha", 1f, 0.00001f);
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

				ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(colorViewGroup, "scaleX", 1.0f, 10.0f).setDuration(FILTER_GRIDE_HIDE_DURATION);
				ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(colorViewGroup, "scaleY", 1.0f, 10.0f).setDuration(FILTER_GRIDE_HIDE_DURATION);
				AnimatorSet set = new AnimatorSet();
				set.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator);
				set.setDuration(FILTER_GRIDE_HIDE_DURATION);
				set.start();
			}
			else {
				hideFiltersViewDone();
			}

		}
    }
//    private void toggleFiltersView() {
//        if(mTopColorEffectContainer == null) {
//            showFiltersView();
//        } else {
//            hideFiltersView(true);
//            mTopColorEffectContainer = null;
//        }
//    }
	
}
