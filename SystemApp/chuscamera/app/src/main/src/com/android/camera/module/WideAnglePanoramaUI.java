/*
 * Copyright (C) 2013 The Android Open Source Project
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
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.camera.CameraActivity;
import com.android.camera.PanoProgressBar;
import com.android.camera.PreviewGestures;
import com.android.camera.ShutterButton;
import com.android.camera.ui.CameraControls;
import com.android.camera.ui.CameraRootView;
import com.android.camera.ui.ModuleSwitcher;
import com.android.camera.ui.RenderOverlay;
import com.android.camera.ui.RotateLayout;
import com.android.camera.ui.RotateTextToast;
import com.android.camera.util.CameraUtil;
import com.chus.camera.R;

import java.lang.reflect.Method;

/**
 * The UI of {@link WideAnglePanoramaModule}.
 */
public class WideAnglePanoramaUI implements
//        TextureView.SurfaceTextureListener,
//        View.OnLayoutChangeListener,
        ShutterButton.OnShutterButtonListener,
        CameraRootView.MyDisplayListener
        {

    @SuppressWarnings("unused")
    private static final String TAG = "CAM_WidePanoramaUI";

    private CameraActivity mActivity;
    private WideAnglePanoramaController mController;

    private ViewGroup mRootView;
    private ModuleSwitcher mSwitcher;
    private FrameLayout mCaptureLayout;
    private View mReviewLayout;
    private ImageView mReview;
    private View mPreviewBorder;
    private View mLeftIndicator;
    private View mRightIndicator;
    private View mCaptureIndicator;
    private PanoProgressBar mCaptureProgressBar;
    private PanoProgressBar mSavingProgressBar;
    private TextView mTooFastPrompt;
    private RotateLayout indicatorLyt;
    private View mPreviewLayout;
    private ViewGroup mReviewControl;
    private TextureView mTextureView;
    private ShutterButton mShutterButton;
    private CameraControlsPano mCameraControls;
    private ImageView mThumbnail;
    private Bitmap mThumbnailBitmap;

    private Matrix mProgressDirectionMatrix = new Matrix();
    private float[] mProgressAngle = new float[2];

    private DialogHelper mDialogHelper;

    // Color definitions.
    private int mIndicatorColor;
    private int mIndicatorColorFast;
    private int mReviewBackground;
    private SurfaceTexture mSurfaceTexture;
    private View mPreviewCover;
	private boolean mPreviewCover_animation_flag = false;

    private int mOrientation;
    //private int mPreviewYOffset;
    private RotateLayout mWaitingDialog;
    private RotateLayout mPanoFailedDialog;
    private Button mPanoFailedButton;

	// frankie, add 
	private RenderOverlay mRenderOverlay;
	private PreviewGestures mGestures;
	// frankie, end
	View mBottomControlsBackground = null;

	AccSensor mAccSensor = null;
	
    /** Constructor. */
    public WideAnglePanoramaUI(
            CameraActivity activity,
            WideAnglePanoramaController controller,
            ViewGroup root) {
        Log.w(TAG, "WideAnglePanoramaUI new");
        mActivity = activity;
        mController = controller;
        mRootView = root;

        createContentView();

		mAccSensor = new AccSensor(activity, mRootView);
		
        mSwitcher = (ModuleSwitcher) mRootView.findViewById(R.id.camera_switcher);
        mSwitcher.setCurrentIndex(ModuleSwitcher.WIDE_ANGLE_PANO_MODULE_INDEX);
        mSwitcher.setSwitchListener(mActivity);
//        if (!mActivity.isSecureCamera()) {
            mThumbnail = (ImageView) mRootView.findViewById(R.id.preview_thumb);
            mThumbnail.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                	Log.v(TAG, "CameraControls.isAnimating() : " + CameraControls.isAnimating());
                    if (!CameraControls.isAnimating())
                        mActivity.gotoGallery();
                }
            });
//        }

        mSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwitcher.showPopup();
                mSwitcher.setOrientation(mOrientation, false);
            }
        });

        //RotateImageView muteButton = (RotateImageView)mRootView.findViewById(R.id.mute_button);
        //muteButton.setVisibility(View.GONE);
    }
	
    private void createContentView() {
        Log.w(TAG, "createContentView");
        LayoutInflater inflator = (LayoutInflater) mActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflator.inflate(R.layout.panorama_module_1, mRootView, true);

        Resources appRes = mActivity.getResources();
        mIndicatorColor = appRes.getColor(R.color.pano_progress_indication);
        mReviewBackground = appRes.getColor(R.color.review_background);
        mIndicatorColorFast = appRes.getColor(R.color.pano_progress_indication_fast);

		mRenderOverlay = (RenderOverlay) mRootView.findViewById(R.id.render_overlay); // frankie, add 
        mPreviewCover = mRootView.findViewById(R.id.preview_cover);
        
		// pano_module_capture.xml 
        mCaptureLayout = (FrameLayout) mRootView.findViewById(R.id.panorama_capture_layout);

		mPreviewLayout = mRootView.findViewById(R.id.pano_preview_layout);
        mTextureView = (TextureView) mRootView.findViewById(R.id.pano_preview_textureview);
        //mTextureView.setSurfaceTextureListener(this);
		TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
			@Override
		    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
		    	Log.v(TAG, "onSurfaceTextureAvailable");
				mPreviewCover_animation_flag = false;
		        mSurfaceTexture = surfaceTexture;
		        mController.onPreviewUIReady();
		        mActivity.updateThumbnail_ui_ready(mThumbnail);
		    }
		    @Override
		    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
				Log.v(TAG, "onSurfaceTextureSizeChanged");
		    }
		    @Override
		    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
		    	Log.v(TAG, "onSurfaceTextureDestroyed");
		        mController.onPreviewUIDestroyed();
		        mSurfaceTexture = null;
		        Log.d(TAG, "surfaceTexture is destroyed");
		        return true;
		    }
		    @Override
		    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
		        // Make sure preview cover is hidden if preview data is available.
		        if (mPreviewCover.getVisibility() != View.GONE) {
					//mPreviewCover.setVisibility(View.GONE);
                    mActivity.updateThumbnail_ui_ready(mThumbnail);
					if(!mPreviewCover_animation_flag) {
						mPreviewCover_animation_flag = true;

						if(true) {
							mPreviewCover.setAlpha(0.00001f);
							mPreviewCover.setVisibility(View.GONE);
							mPreviewCover_animation_flag = false;
						} else {
							ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mPreviewCover, "alpha", 0.00001f, 0f);
				            alphaAnimator.setDuration(700);
				            alphaAnimator.addListener(new AnimatorListenerAdapter() {
				                @Override
				                public void onAnimationCancel(Animator animation) {
				                    Log.v(TAG, "hidePreviewCover,onAnimationCancel");
				                    mPreviewCover.setVisibility(View.GONE);
									mPreviewCover_animation_flag = false;
				                }
				                @Override
				                public void onAnimationEnd(Animator animation) {
				                    Log.v(TAG, "hidePreviewCover,onAnimationEnd");
				                    mPreviewCover.setVisibility(View.GONE);
									mPreviewCover_animation_flag = false;
				                }
				            });
				            alphaAnimator.start();
						}
					}
		        }
		    }
		};
		mTextureView.setSurfaceTextureListener(textureListener);
		
        //mTextureView.addOnLayoutChangeListener(this);
		View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener()  {
		    @Override
		    public void onLayoutChange(View v, int l, int t, int r, int b,
		            int oldl, int oldt, int oldr, int oldb) {
		        mController.onPreviewUILayoutChange(l, t, r, b);
		    }
		};
		mTextureView.addOnLayoutChangeListener(onLayoutChangeListener);
			
		mPreviewBorder = mCaptureLayout.findViewById(R.id.pano_preview_area_border);

		mCaptureIndicator = mRootView.findViewById(R.id.pano_capture_indicator);
		
        mCaptureProgressBar = (PanoProgressBar) mRootView.findViewById(R.id.pano_pan_progress_bar);
        mCaptureProgressBar.setBackgroundColor(appRes.getColor(R.color.pano_progress_empty));
        mCaptureProgressBar.setDoneColor(appRes.getColor(R.color.pano_progress_done));
        mCaptureProgressBar.setIndicatorColor(mIndicatorColor);
        mCaptureProgressBar.setIndicatorWidth(20);
        mLeftIndicator = mRootView.findViewById(R.id.pano_pan_left_indicator);
        mRightIndicator = mRootView.findViewById(R.id.pano_pan_right_indicator);
        mLeftIndicator.setEnabled(false);
        mRightIndicator.setEnabled(false);
        mTooFastPrompt = (TextView) mRootView.findViewById(R.id.pano_capture_too_fast_textview);
        indicatorLyt = (RotateLayout)mRootView.findViewById(R.id.indicator_lyt);
        indicatorLyt.setVisibility(View.INVISIBLE);
		// waitingDialog
        mWaitingDialog = (RotateLayout) mRootView.findViewById(R.id.waitingDialog);


		// pano_module_review_1.xml
		mReviewLayout = mRootView.findViewById(R.id.pano_review_layout);
        mReview = (ImageView) mRootView.findViewById(R.id.pano_reviewarea);
		mReview.setBackgroundColor(mReviewBackground);
		mReviewControl = (ViewGroup) mRootView.findViewById(R.id.pano_review_control);

        mShutterButton = (ShutterButton) mRootView.findViewById(R.id.shutter_button);
        //mShutterButton.setImageResource(R.drawable.btn_new_shutter); // frankie, rm 
        mShutterButton.setOnShutterButtonListener(this);

		mPanoFailedDialog = (RotateLayout) mRootView.findViewById(R.id.pano_dialog_layout);
        mPanoFailedButton = (Button) mRootView.findViewById(R.id.pano_dialog_button1);
		
        // Hide menu and indicators.
        mRootView.findViewById(R.id.menu).setVisibility(View.GONE);
        mRootView.findViewById(R.id.on_screen_indicators).setVisibility(View.GONE);

        // TODO: set display change listener properly.
        ((CameraRootView) mRootView).setDisplayChangeListener(null);

        mCameraControls = (CameraControlsPano) mRootView.findViewById(R.id.camera_controls);
		mBottomControlsBackground = (View) mRootView.findViewById(R.id.bottom_background_mask_1);
		
        setPanoramaPreviewView();

        mDialogHelper = new DialogHelper();
		
        setViews(appRes);
    }
    private void setPanoramaPreviewView() { // frankie, set the TextureView to the center for fixed 4/3 ratio
    	Log.v(TAG, "+ setPanoramaPreviewView");
        int rotation = mActivity.getWindowManager().getDefaultDisplay().getRotation();
        Display display = mActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int width = size.x;
        int height = size.y;
        int xOffset = 0;
        int yOffset = 0;
        int w = width;
        int h = height;
		Log.v(TAG, "  getDefaultDisplay, " + w + " * " + h);

        h = w * 4 / 3;
        yOffset = (height - h) / 2;

		Log.v(TAG, "  textureView: " + w + "" + h);
        FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(w, h);

		param = new FrameLayout.LayoutParams(w, h, Gravity.CENTER);
		param.setMargins(0,0,0,100);

        mTextureView.setLayoutParams(param);
        //mTextureView.setX(xOffset);
        //mTextureView.setY(yOffset);
        mPreviewBorder.setLayoutParams(param);
        //mPreviewBorder.setX(xOffset);
        //mPreviewBorder.setY(yOffset);
		
        //mPreviewYOffset = yOffset;

        //int t = mPreviewYOffset;
        //int b1 = mTextureView.getBottom() - mPreviewYOffset;
        //int r = mTextureView.getRight();
        //int b2 = mTextureView.getBottom();

        mCameraControls.setPreviewRatio(1.0f, true);
    }
    private void setViews(Resources appRes) {
        int weight = appRes.getInteger(R.integer.SRI_pano_layout_weight);

        mSavingProgressBar = (PanoProgressBar) mRootView.findViewById(R.id.pano_saving_progress_bar);
        mSavingProgressBar.setIndicatorWidth(0);
        mSavingProgressBar.setMaxProgress(100);
        mSavingProgressBar.setBackgroundColor(appRes.getColor(R.color.pano_progress_empty));
        mSavingProgressBar.setDoneColor(appRes.getColor(R.color.pano_progress_indication));

        View cancelButton = mRootView.findViewById(R.id.pano_review_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mController.cancelHighResStitching();
            }
        });
    }
    public Point getPreviewAreaSize() {
        return new Point(mTextureView.getWidth(), mTextureView.getHeight());
    }

	public void onResume_1() { // frankie, add 
	    Log.w(TAG, "onResume_1");
		if(mAccSensor != null) {
			mAccSensor.onResume();
		}
            registerTestReceiver();
	}
	public void onPause_1() { // frankie, add 
	    Log.w(TAG, "onPause_1");
		if(mAccSensor != null) {
			mAccSensor.onPause();
		}
            unregisterTestReceiver();
	}

	//////////////////////////////////////////// ShutterButton.OnShutterButtonListener impl start
	@Override
    public void onShutterButtonFocus(boolean pressed) {
        // Do nothing.
    }

    @Override
    public void onShutterButtonClick() {
        mController.onShutterButtonClick();
    }

    @Override
    public void onShutterButtonLongClick() {}
	//////////////////////////////////////////// ShutterButton.OnShutterButtonListener impl end
	
	public void onCameraOpened() { // frankie, add 
        if (mGestures == null) {
            // this will handle gesture disambiguation and dispatching
            mGestures = new PreviewGestures(mActivity, new PreviewGestures.SingleTapListener() {
                    @Override
                    public void onSingleTapUp(View v, int x, int y) {
                    }
                }, null, null);
            mRenderOverlay.setGestures(mGestures);
        }
		mGestures.setRenderOverlay(mRenderOverlay);
		mGestures.setZoomEnabled(false);
		mRenderOverlay.requestLayout();
		mGestures.setWideAnglePanoramaUI(this); // frankie, add 
		mActivity.setPreviewGestures(mGestures);
	}
	public void enableGestures(boolean enable) { // frankie, add 
        if (mGestures != null) {
            mGestures.setEnabled(enable);
        }
	}
		
    public void onStartCapture() {
        hideSwitcher();
        mShutterButton.setImageResource(R.drawable.shutter_button_stop);
        mCaptureIndicator.setVisibility(View.VISIBLE);
        indicatorLyt.setVisibility(View.VISIBLE);
        showDirectionIndicators(PanoProgressBar.DIRECTION_NONE);
    }

    public void showPreviewUI() {
        mCaptureLayout.setVisibility(View.VISIBLE);
        showUI();
    }

    public void onStopCapture() {
        indicatorLyt.setVisibility(View.INVISIBLE);
        mCaptureIndicator.setVisibility(View.INVISIBLE);
        hideTooFastIndication();
        hideDirectionIndicators();
    }

    public void hideSwitcher() {
        mSwitcher.closePopup();
        mSwitcher.setVisibility(View.INVISIBLE);
    }

    public void hideUI() {
        hideSwitcher();
        mCameraControls.setVisibility(View.INVISIBLE);
		mBottomControlsBackground.setVisibility(View.INVISIBLE); // frankie, add
    }

    public void showUI() {
        showSwitcher();
        mCameraControls.setVisibility(View.VISIBLE);
		mBottomControlsBackground.setVisibility(View.VISIBLE); // frankie, add
    }

    public void onPreviewFocusChanged(boolean previewFocused) {
		Log.v(TAG, "onPreviewFocusChanged: " + previewFocused); // frankie, 
		// frankie, 2017.06.16, start
	    if (mGestures != null) {
            mGestures.setEnabled(previewFocused);
        }
        if (mRenderOverlay != null) {
            // this can not happen in capture mode
            mRenderOverlay.setVisibility(previewFocused ? View.VISIBLE : View.GONE);
        }
		// frankie, 2017.06.16, end
		
        if (previewFocused) {
            showUI();
        } else {
            hideUI();
        }
    }

    public boolean arePreviewControlsVisible() {
        return (mCameraControls.getVisibility() == View.VISIBLE);
    }

    public void showSwitcher() {
        mSwitcher.setVisibility(View.VISIBLE);
    }

    public void setSwitcherIndex() {
        mSwitcher.setCurrentIndex(ModuleSwitcher.WIDE_ANGLE_PANO_MODULE_INDEX);
    }

    public void setCaptureProgressOnDirectionChangeListener(
            PanoProgressBar.OnDirectionChangeListener listener) {
        mCaptureProgressBar.setOnDirectionChangeListener(listener);
    }

    public void resetCaptureProgress() {
        mCaptureProgressBar.reset();
    }

    public void setMaxCaptureProgress(int max) {
        mCaptureProgressBar.setMaxProgress(max);
    }

    public void showCaptureProgress() {
        mCaptureProgressBar.setVisibility(View.VISIBLE);
    }

    public void updateCaptureProgress(
            float panningRateXInDegree, float panningRateYInDegree,
            float progressHorizontalAngle, float progressVerticalAngle,
            float maxPanningSpeed) {

        if ((Math.abs(panningRateXInDegree) > maxPanningSpeed)
                || (Math.abs(panningRateYInDegree) > maxPanningSpeed)) {
            showTooFastIndication();
        } else {
            hideTooFastIndication();
        }

        // progressHorizontalAngle and progressVerticalAngle are relative to the
        // camera. Convert them to UI direction.
        mProgressAngle[0] = progressHorizontalAngle;
        mProgressAngle[1] = progressVerticalAngle;
        mProgressDirectionMatrix.mapPoints(mProgressAngle);

        int angleInMajorDirection =
                (Math.abs(mProgressAngle[0]) > Math.abs(mProgressAngle[1]))
                        ? (int) mProgressAngle[0]
                        : (int) mProgressAngle[1];
        mCaptureProgressBar.setProgress((angleInMajorDirection));
    }

    public void setProgressOrientation(int orientation) {
        mProgressDirectionMatrix.reset();
        mProgressDirectionMatrix.postRotate(orientation);
    }

    public void showDirectionIndicators(int direction) {
        switch (direction) {
            case PanoProgressBar.DIRECTION_NONE:
                mLeftIndicator.setVisibility(View.VISIBLE);
                mRightIndicator.setVisibility(View.VISIBLE);
                indicatorLyt.findViewById(R.id.indicator_left_iv).setVisibility(View.VISIBLE);
                indicatorLyt.findViewById(R.id.indicator_right_iv).setVisibility(View.VISIBLE);
                break;
            case PanoProgressBar.DIRECTION_LEFT:
                mLeftIndicator.setVisibility(View.VISIBLE);
                mRightIndicator.setVisibility(View.INVISIBLE);
                indicatorLyt.findViewById(R.id.indicator_left_iv).setVisibility(View.VISIBLE);
                indicatorLyt.findViewById(R.id.indicator_right_iv).setVisibility(View.INVISIBLE);
                break;
            case PanoProgressBar.DIRECTION_RIGHT:
                mLeftIndicator.setVisibility(View.INVISIBLE);
                mRightIndicator.setVisibility(View.VISIBLE);
                indicatorLyt.findViewById(R.id.indicator_left_iv).setVisibility(View.INVISIBLE);
                indicatorLyt.findViewById(R.id.indicator_right_iv).setVisibility(View.VISIBLE);
                break;
        }
    }

    public SurfaceTexture getSurfaceTexture() {
        return mSurfaceTexture;
    }

    private void hideDirectionIndicators() {
        mLeftIndicator.setVisibility(View.INVISIBLE);
        mRightIndicator.setVisibility(View.INVISIBLE);
    }
    public void reset() {
        mShutterButton.setImageResource(R.drawable.btn_new_shutter_panorama);
		mShutterButton.setImageResource(R.drawable.chus_shutter_button_photo); // frankie, add 
        mReviewLayout.setVisibility(View.GONE);
        mCaptureProgressBar.setVisibility(View.INVISIBLE);
    }

    public void setFinalMosaic(Bitmap bitmap, int orientation) {
        if (bitmap != null && orientation != 0) {
            Matrix rotateMatrix = new Matrix();
            rotateMatrix.setRotate(orientation);
            bitmap = Bitmap.createBitmap(
                    bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                    rotateMatrix, false);
        }

        mReview.setImageBitmap(bitmap);
        mCaptureLayout.setVisibility(View.GONE);
        mReviewLayout.setVisibility(View.VISIBLE);
        // If capture is stopped by device rotation, the rendering progress bar
        // is sometimes not shown due to wrong layout result. It's likely to be
        // a framework bug. Call requestLayout() as a workaround.
        mSavingProgressBar.requestLayout();

        mThumbnailBitmap = bitmap;
    }

    public void showFinalMosaic() {
        if (mThumbnailBitmap == null) return;
        mActivity.updateThumbnail(mThumbnailBitmap);
        mThumbnailBitmap.recycle();
        mThumbnailBitmap = null;
    }

    public void onConfigurationChanged(
            Configuration newConfig, boolean threadRunning) {
        Log.w(TAG, "onConfigurationChanged !"); // frankie, 
        
        Drawable lowResReview = null;
        if (threadRunning) lowResReview = mReview.getDrawable();

        // Change layout in response to configuration change
        LayoutInflater inflater = (LayoutInflater)
                mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mReviewControl.removeAllViews();
        ((ViewGroup) mReviewControl).clearDisappearingChildren();
        inflater.inflate(R.layout.pano_review_control, mReviewControl, true);

        mRootView.bringChildToFront(mCameraControls);
        setViews(mActivity.getResources());
        if (threadRunning) {
            mReview.setImageDrawable(lowResReview);
            mCaptureLayout.setVisibility(View.GONE);
            mReviewLayout.setVisibility(View.VISIBLE);
        }
    }

    public void resetSavingProgress() {
        mSavingProgressBar.reset();
        mSavingProgressBar.setRightIncreasing(true);
    }

    public void updateSavingProgress(int progress) {
        mSavingProgressBar.setProgress(progress);
    }


//    @Override
//    public void onLayoutChange(View v, int l, int t, int r, int b, int oldl, int oldt, int oldr, int oldb) {
//        mController.onPreviewUILayoutChange(l, t, r, b);
//    }

    public void showAlertDialog(
            String title, String failedString,
            String OKString, Runnable runnable) {
        mDialogHelper.showAlertDialog(title, failedString, OKString, runnable);
    }

    public void showWaitingDialog(String title) {
        mDialogHelper.showWaitingDialog(title);
    }

    public void dismissAllDialogs() {
        mDialogHelper.dismissAll();
    }
    private void showTooFastIndication() {
        mTooFastPrompt.setVisibility(View.VISIBLE);
        // The PreviewArea also contains the border for "too fast" indication.
        mPreviewBorder.setVisibility(View.VISIBLE);
        mCaptureProgressBar.setIndicatorColor(mIndicatorColorFast);
        mLeftIndicator.setEnabled(true);
        mRightIndicator.setEnabled(true);
    }

    private void hideTooFastIndication() {
        mTooFastPrompt.setVisibility(View.GONE);
        mPreviewBorder.setVisibility(View.INVISIBLE);
        mCaptureProgressBar.setIndicatorColor(mIndicatorColor);
        mLeftIndicator.setEnabled(false);
        mRightIndicator.setEnabled(false);
    }

    public void flipPreviewIfNeeded() {
        // Rotation needed to display image correctly clockwise
        int cameraOrientation = mController.getCameraOrientation();
        // Display rotated counter-clockwise
        int displayRotation = CameraUtil.getDisplayRotation(mActivity);
        // Rotation needed to display image correctly on current display
        int rotation = (cameraOrientation - displayRotation + 360) % 360;
        if (rotation >= 180) {
            mTextureView.setRotation(180);
        } else {
            mTextureView.setRotation(0);
        }
    }

    @Override // CameraRootView.MyDisplayListener impl
    public void onDisplayChanged() {
        Log.w(TAG, "onDisplayChanged");
        
        mCameraControls.checkLayoutFlip();
        flipPreviewIfNeeded();
    }

    public void initDisplayChangeListener() {
        ((CameraRootView) mRootView).setDisplayChangeListener(this);
    }

    public void removeDisplayChangeListener() {
        ((CameraRootView) mRootView).removeDisplayChangeListener();
    }

    public void showPreviewCover() {
		Log.v(TAG, "showPreviewCover"); // frankie,
		if(true) {
			mPreviewCover.setAlpha(0.00001f);
			mPreviewCover.setVisibility(View.VISIBLE);
			return ;
		}
        //mPreviewCover.setVisibility(View.VISIBLE);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mPreviewCover, "alpha", 0f, 0.00001f);
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

    private class DialogHelper {

        DialogHelper() {
        }

        public void dismissAll() {
            if (mPanoFailedDialog != null) {
                mPanoFailedDialog.setVisibility(View.INVISIBLE);
            }
            if (mWaitingDialog != null) {
                mWaitingDialog.setVisibility(View.INVISIBLE);
            }
        }

        public void showAlertDialog(
                CharSequence title, CharSequence message,
                CharSequence buttonMessage, final Runnable buttonRunnable) {
            dismissAll();
            mPanoFailedButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonRunnable.run();
                    mPanoFailedDialog.setVisibility(View.INVISIBLE);
                }
            });
            mPanoFailedDialog.setVisibility(View.VISIBLE);
        }

        public void showWaitingDialog(CharSequence message) {
            dismissAll();
            mWaitingDialog.setVisibility(View.VISIBLE);
        }
    }

//    private static class FlipBitmapDrawable extends BitmapDrawable {
//
//        public FlipBitmapDrawable(Resources res, Bitmap bitmap) {
//            super(res, bitmap);
//        }
//
//        @Override
//        public void draw(Canvas canvas) {
//            Rect bounds = getBounds();
//            int cx = bounds.centerX();
//            int cy = bounds.centerY();
//            canvas.save(Canvas.MATRIX_SAVE_FLAG);
//            canvas.rotate(180, cx, cy);
//            super.draw(canvas);
//            canvas.restore();
//        }
//    }

    public boolean hideSwitcherPopup() {
        if (mSwitcher != null && mSwitcher.showsPopup()) {
            mSwitcher.closePopup();
            return true;
        }
        return false;
   }

    public void setOrientation(int orientation, boolean animation) {
        mOrientation = orientation;
        Log.w(TAG, "setOrientation:" + orientation + " animation:" + animation);
        // '---------`
        // |    0    |
        // |---------| =t
        // | |     | |
        // |1|     |2|
        // | |     | |
        // |---------| =b1
        // |    3    |
        // `---------' =b2
        //          =r
        final View dummy = mRootView.findViewById(R.id.pano_dummy_layout);
        int t = dummy.getTop();
        int b1 = dummy.getBottom();
        int r = dummy.getRight();
        //int b2 = dummy.getBottom();

        // frankie, note: progressLayout height = (total_height / 6.5 ) * 3, 
        // but progressLayout.getChildAt(0)'s height is wrap_content so is a small value !!!
        final FrameLayout progressLayout = (FrameLayout)
                mRootView.findViewById(R.id.pano_progress_layout);
        int pivotY = ((ViewGroup) progressLayout).getPaddingTop()
                + progressLayout.getChildAt(0).getHeight() / 2;

        Log.v(TAG, "t=" + t + " b=" + b1 + " r=" + r + " pivotY=" + pivotY);
        
        int[] x = { r / 2, r / 10, r * 9 / 10, r / 2 };
        int[] y = { t / 2 + pivotY, (t + b1) / 2, (t + b1) / 2, b1 + pivotY };

        int idx1, idx2;
        int g;
        switch (orientation) {
            case 90:
                idx1 = 1;
                idx2 = 2;
                g = Gravity.TOP | Gravity.RIGHT;
                break;
            case 180:
                idx1 = 3;
                idx2 = 0;
                g = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
                break;
            case 270:
                idx1 = 2;
                idx2 = 1;
                g = Gravity.TOP | Gravity.RIGHT;
                break;
            default:
                idx1 = 0;
                idx2 = 3;
                g = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
                break;
        }

        final View[] views1 = { // frankie, tow header
            (View) mCaptureIndicator.getParent(),           // frankie, is FrameLayout weight 1/(1 + 2.5 + 3) // header 
            mRootView.findViewById(R.id.pano_review_indicator)
        };
        for (final View v : views1) {
            v.setTranslationX(x[idx1] - x[0]);
            v.setTranslationY(y[idx1]- y[0]);
            // use relection here to build on Kitkat
            if (Build.VERSION.SDK_INT >= 21) {
                try {
                    final Class cls = Class.forName("android.view.View");
                    final Method method = cls.getMethod("setTranslationZ", float.class);
                    method.invoke(v, 1);
                } catch (Exception e) {
                    // ignore
                }
            }
            v.setRotation(-orientation);
        }

        final View[] views2 = { progressLayout, mReviewControl };   // frankie, two bottom weight 3/(1+2.5+3) 
        for (final View v : views2) {
            v.setPivotX(r / 2);
            v.setPivotY(pivotY);
            v.setTranslationX(x[idx2] - x[3]);
            v.setTranslationY(y[idx2] - y[3]);
            v.setRotation(-orientation);
        }

        final View button = mReviewControl.findViewById(R.id.pano_review_cancel_button);
        /// frankie, FrameLayout -> RelativeLayout
        //FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) button.getLayoutParams();
        //lp.gravity = g; //
        //button.setLayoutParams(lp);


        mWaitingDialog.setRotation(-orientation);
        indicatorLyt.setRotation(-orientation);
        mPanoFailedDialog.setRotation(-orientation);
        mReview.setRotation(-orientation);
        mTooFastPrompt.setRotation(-orientation);
        mCameraControls.setOrientation(orientation, animation);
        RotateTextToast.setOrientation(orientation);
    }

	public int getOrientation() { // frankie, add 
        return mOrientation;
    }

    // test ui
    static String getIntentAction(Intent intent) {
        String action = intent != null ? intent.getAction() : "";
        if(action == null || action.isEmpty()) {
            return "";
        }
        return action;
    }
    static String getExtraString(Intent intent, String key, String def) {
                if(intent != null && intent.getExtras() != null) {
                    Bundle b = intent.getExtras();
                    return b.getString(key, def);
                }
                return null;
            }
            static int getExtraInt(Intent intent, String key, int def) {
                if(intent != null && intent.getExtras() != null) {
                    Bundle b = intent.getExtras();
                    return b.getInt(key, def);
                }
                return -1;
            }
            private static final String TEST_DIALOG_ACTION = "com.chus.camera.test.ACTION.panoui";
            private BroadcastReceiver mTestReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if(getIntentAction(intent).equals(TEST_DIALOG_ACTION)) {
                        String cmd = getExtraString(intent, "cmd", "");
                        int param = getExtraInt(intent, "param", -1);
                        if(cmd.equals("alert")) {
                            if(param == 1) {
                                mDialogHelper.dismissAll();
                                mDialogHelper.showAlertDialog("title", "failed_str", "ok_str", new Runnable() {
                                    @Override
                                    public void run() {
                                    }
                                });
                            } else if(param == 0) {
                                mDialogHelper.dismissAll();
                            }
                        } else if(cmd.equals("waiting")) {
                            if(param == 1) {
                                mDialogHelper.dismissAll();
                                mDialogHelper.showWaitingDialog("message!");
                            } else if(param == 0) {
                                mDialogHelper.dismissAll();
                            }
                        }
                    }
                }
            };

	private boolean mTestReceiverRegistered = false;
    private void registerTestReceiver() {
        if(mActivity != null) {
			if(mTestReceiverRegistered == false) {
				mTestReceiverRegistered = true;
	            IntentFilter intentFilter = new IntentFilter();
	            intentFilter.addAction(TEST_DIALOG_ACTION);
	            mActivity.registerReceiver(mTestReceiver, intentFilter);
			}
        }
    }
    private void unregisterTestReceiver() {
        if(mActivity != null)  {
			if(mTestReceiverRegistered) {
				mTestReceiverRegistered = false;
            	mActivity.unregisterReceiver(mTestReceiver);
			}
        }
    }

}
