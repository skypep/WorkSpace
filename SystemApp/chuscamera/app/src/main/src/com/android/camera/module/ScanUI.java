package com.android.camera.module;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.camera.CameraActivity;
import com.android.camera.PreviewGestures;

import com.android.camera.ShutterButton;
import com.android.camera.libzxing.zxing.utils.BeepManager;
import com.android.camera.libzxing.zxing.utils.InactivityTimer;
import com.android.camera.ui.RenderOverlay;

import com.toro.camera.R;

import java.lang.reflect.Field;

/**
 * Created by THINK on 2017/6/16.
 */

public class ScanUI implements SurfaceHolder.Callback
        //ActivityController
{
    private static final String TAG = "CAM_Scan.UI";

    private CameraActivity mActivity;
    private ScanModule mController;
    private ViewGroup mRootView;
    private int mOrientation;
    private CameraControlsScan mCameraControls;

    private View mPreviewCover;

    private RenderOverlay mRenderOverlay;
    private PreviewGestures mGestures;

    private SurfaceView scanPreview = null;
    private ImageView scanLine;
    private RelativeLayout scanContainer;
    private RelativeLayout scanCropView;

    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    private Rect mCropRect = null;
    private boolean isHasSurface = false;

    private ImageView mFlashLightButton = null;
    private boolean mFlashLightOnOff = false;

	private ShutterButton mShutterButton;

    public ScanUI(CameraActivity activity, final ScanModule controller, ViewGroup root) {
        mActivity = activity;
        mController = controller;
        mRootView = root;
        mActivity.getLayoutInflater().inflate(R.layout.scan_module_1, (ViewGroup) mRootView, true);

		mShutterButton = (ShutterButton) mRootView.findViewById(R.id.shutter_button); // frankie, add
		mShutterButton.setImageResource(R.drawable.btn_new_shutter_video);	
		mShutterButton.setImageResource(R.drawable.toro_shutter_button_photo);
		//mShutterButton.setImageResource(R.drawable.chus_shutter_button_photo_normal);	
		mShutterButton.setVisibility(View.VISIBLE);
		
        mPreviewCover = mRootView.findViewById(R.id.preview_cover);
        mRenderOverlay = (RenderOverlay) mRootView.findViewById(R.id.render_overlay);

        inactivityTimer = new InactivityTimer(mActivity);
        beepManager = new BeepManager(mActivity);

        scanPreview = (SurfaceView) mRootView.findViewById(R.id.capture_preview);
        scanPreview.setVisibility(View.VISIBLE);
        scanPreview.getHolder().addCallback(this);
        scanPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        scanContainer = (RelativeLayout) mRootView.findViewById(R.id.capture_container);
        scanCropView = (RelativeLayout) mRootView.findViewById(R.id.capture_crop_view);
        scanLine = (ImageView) mRootView.findViewById(R.id.capture_scan_line);

        mCameraControls = (CameraControlsScan) mRootView.findViewById(R.id.camera_controls);

        mFlashLightButton = (ImageView) mRootView.findViewById(R.id.flashlight_1);
        setFlashLightImage();
        if(mFlashLightButton != null) {
            mFlashLightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mFlashLightOnOff) { mFlashLightOnOff = false; }
                    else { mFlashLightOnOff = true; }
                    setFlashLightImage();
                    mController.setFlashLightEnable(mFlashLightOnOff);
                }
            });
        }

        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.9f);
        animation.setDuration(4500);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
        scanLine.startAnimation(animation);

        mCameraControls.hideCameraSettings();
        mCameraControls.enableTouch_ext(false);
    }
    private void setFlashLightImage() {
        if(mFlashLightButton != null) {
            if (mFlashLightOnOff == false) {
                mFlashLightButton.setBackgroundResource(R.drawable.toro_flashlight_enable);
            } else {
                mFlashLightButton.setBackgroundResource(R.drawable.toro_flashlight_disable);
            }
        }
    }
    public void onPreviewFocusChanged(boolean previewFocused) {
        Log.v(TAG, "onPreviewFocusChanged: " + previewFocused); // frankie,
        if (mGestures != null) {
            mGestures.setEnabled(previewFocused);
        }
        if (mRenderOverlay != null) {
            // this can not happen in capture mode
            mRenderOverlay.setVisibility(previewFocused ? View.VISIBLE : View.GONE);
        }
    }

    //
    public void onResume() {
    }

    //
    public void onPauseBeforeSuper() {
    }
    public void onPause() {
    }

    public void onCameraOpened() {
        if (mGestures == null) {
            // this will handle gesture disambiguation and dispatching
            mGestures = new PreviewGestures(mActivity, new PreviewGestures.SingleTapListener() {
                @Override
                public void onSingleTapUp(View v, int x, int y) {
                }
            }, null, null);
            mRenderOverlay.setGestures(mGestures);
        }
        mGestures.setZoomEnabled(false);
        mGestures.setRenderOverlay(mRenderOverlay);
        mRenderOverlay.requestLayout();

        mGestures.setScanUI(this);

        mActivity.setPreviewGestures(mGestures);

        mController.setFlashLightEnable(mFlashLightOnOff);
    }
    public void enableGestures(boolean enable) { // frankie, add
        if (mGestures != null) {
            mGestures.setEnabled(enable);
        }
    }
    public void showPreviewCover() {
        Log.v(TAG, "showPreviewCover"); // frankie,
        //mPreviewCover.setVisibility(View.VISIBLE);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mPreviewCover, "alpha", 0f, 1f);
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
        // Hide the preview cover if need.
        if (mPreviewCover.getVisibility() != View.GONE) {
            //mPreviewCover.setVisibility(View.GONE);

            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mPreviewCover, "alpha", 1f, 0f);
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
    public void hideSurfaceView() {
        scanPreview.setVisibility(View.INVISIBLE);
    }

    public void showSurfaceView() {
        scanPreview.setVisibility(View.VISIBLE);
    }
    public void setOrientation(int orientation, boolean animation) {
        mOrientation = orientation;
        mCameraControls.setOrientation(orientation, animation);
    }
    public int getOrientation() {
        return mOrientation;
    }

    public Rect getCropRect() {
        return mCropRect;
    }

    ///////////////////////////////////////////////////////////////////// SurfaceHolder.Callback IMPL start
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    	Log.v(TAG, "surfaceCreated");
        mController.onUISurfacePrepared(holder);
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    	Log.v(TAG, "surfaceChanged");
    	/* surfaceView layout ok */
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    	Log.v(TAG, "surfaceDestroyed");
        mController.onUISurfaceDestroy();
    }
    ///////////////////////////////////////////////////////////////////// SurfaceHolder.Callback IMPL end

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return mActivity.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void initCrop() {
        Point p = mController.getPreviewSize_();
        int cameraWidth = p.y;
        int cameraHeight = p.x;
        Log.v(TAG, "initCrop, camera preview size:" + cameraWidth + "*" + cameraHeight);

        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();
        Log.v(TAG, "container size:" + containerWidth + "*" + containerHeight);
		
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);
        int cropLeft = location[0];
        //int cropTop = location[1] - getStatusBarHeight();
        int cropTop = location[1];
		Log.v(TAG, "crop left:" + cropLeft + " right:" + cropTop);
        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();
        Log.v(TAG, "crop size:" + cropWidth + "*" + cropHeight);
		

        int x = cropLeft * cameraWidth / containerWidth;
        int y = cropTop * cameraHeight / containerHeight;

        int width = cropWidth * cameraWidth / containerWidth;
        int height = cropHeight * cameraHeight / containerHeight;

        mCropRect = new Rect(x, y, width + x, height + y);
    }

	
}

