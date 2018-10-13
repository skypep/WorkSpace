package com.android.camera.module;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.camera.CameraActivity;
import com.android.camera.CameraHolder;
import com.android.camera.CameraManager;
import com.android.camera.CameraModule;
import com.android.camera.MediaSaveService;
import com.android.camera.libzxing.zxing.activity.ActivityController;
import com.android.camera.libzxing.zxing.decode.DecodeThread;
import com.android.camera.libzxing.zxing.utils.BeepManager;
import com.android.camera.module.ui.PreviewDebugDialog;
import com.android.camera.util.CameraUtil;
import com.android.camera.util.MiscUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import com.chus.camera.R;

import com.android.camera.AGlobalConfig;

import java.io.ByteArrayOutputStream;
import java.lang.ref.SoftReference;
import java.util.List;

/**
 * Created by THINK on 2017/6/15.
 */

public class ScanModule implements CameraModule {
    private static final String TAG = "CAM_Scan.Module";
	
    //private static final int PREVIEW_STOPPED = 0;
    //private static final int PREVIEW_ACTIVE = 1;

    private CameraActivity mActivity;
    private View mRootView;
    private CameraManager.CameraProxy mCameraDevice;
	private Camera.Parameters mInitialParameter = null;
    private boolean mPaused;
    private int mOrientation = OrientationEventListener.ORIENTATION_UNKNOWN;

    private boolean mUsingFrontCamera;
    private int mCameraPreviewWidth;
    private int mCameraPreviewHeight;
    //private int mCameraState;
    private Handler mMainHandler;
    private MyHandler myHandler;
    private ScanUI mUI;
    private boolean mSurfacePrepared = false;
    private SurfaceHolder mSurfaceHolder = null;
    private boolean mPreviewStarted = false;

    private int mCameraOrientation;

    //private String mTargetFocusMode = Camera.Parameters.FOCUS_MODE_INFINITY;
    private String mTargetFocusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO;
    //private float mHorizontalViewAngle;
    //private float mVerticalViewAngle;

    private BeepManager beepManager;

    private HandlerThread mPreviewDebugThread = null;
	private PreviewDebugHandler mPreviewDebugHandler = null;
    private Bitmap mPreviewDebugBitmap = null;

    private class MyHandler extends Handler {
        public MyHandler() {
            super();
        }
        @Override
        public void handleMessage(Message message) {

        }
    }

	@Override // frankie, 
    public void reinit() {
        Log.v(TAG, "++++++ reinit");

    }
    @Override
    public void init(CameraActivity activity, View parent) {
        Log.v(TAG, "++++++ init");
        mActivity = activity;
        mRootView = parent;

        mUI = new ScanUI(mActivity, this, (ViewGroup) mRootView);

        myHandler = new MyHandler();
        mMainHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
            }
        };
        beepManager = new BeepManager(mActivity);
    }
	@Override // frankie, add 
	public void onCameraIdToggle() {

	}

    @Override
    public void onPreviewFocusChanged(boolean previewFocused) {
        Log.v(TAG, "onPreviewFocusChanged:" + previewFocused);
        mUI.onPreviewFocusChanged(previewFocused);
    }

    //
    @Override
    public void onResumeBeforeSuper() {
        Log.v(TAG, "++++++ onResumeBeforeSuper");
        mPaused = false;
    }
    @Override
    public void onResumeAfterSuper() {
        Log.v(TAG, "++++++ onResumeAfterSuper");
        mUI.showSurfaceView();					// frankie, at this time, the surfaceView start to be created 
        if (!setupCamera()) {
            CameraUtil.showErrorAndFinish(mActivity, R.string.cannot_connect_camera);
            Log.e(TAG, "Failed to open camera, aborting");
            return;
        }
        Log.v(TAG, "    mSurfacePrepared:" + mSurfacePrepared);
        if(mSurfacePrepared) {
            if(!mPreviewStarted) {
                startPreview();
            }
        }

        mUI.onResume();
        mUI.enableGestures(true); // frankie, add
        mActivity.setSlideSwitcherShow(true, false); // frankie, add 
    }
    //
    @Override
    public void onPauseBeforeSuper() {
        Log.v(TAG, "------ onPauseBeforeSuper");
        mPaused = true;
        mUI.onPauseBeforeSuper();
    }
    @Override
    public void onPauseAfterSuper() {
        Log.v(TAG, "------ onPauseAfterSuper");
        mUI.showPreviewCover();
        mUI.hideSurfaceView();		// frankie, if this result the surfaceView to be destroyed ...
        mUI.enableGestures(false); // frankie, add
        beepManager.close();
        mUI.onPause();

        if (mCameraDevice == null) {
            // Camera open failed. Nothing should be done here.
            return;
        }
        if(mPreviewStarted) {
            stopPreview();
        }
        releaseCamera();

        System.gc();
    }

    public void onUISurfacePrepared(SurfaceHolder holder) {
        Log.v(TAG, "onUISurfacePrepared");
        if(mCameraDevice!=null && mPreviewStarted) {
            //stopPreview(); // when already not do start again 
        }
        mSurfacePrepared = true;
        mSurfaceHolder = holder;
		if(mCameraDevice == null || mPaused) {
			return ;
		}
        if(!mPaused && mCameraDevice != null && !mPreviewStarted) {
            startPreview();
        }
    }
    public void onUISurfaceDestroy() {
        Log.v(TAG, "onUISurfaceDestroy");
        mSurfacePrepared = false;
        if(mCameraDevice != null && mPreviewStarted) {
            stopPreview();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration config) {

    }

    @Override
    public void onStop() {

    }
    @Override // frankie, add
    public void onDestroy() {

    }
    //@Override
    public void installIntentFilter() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onSingleTapUp(View view, int x, int y) {

    }

    @Override
    public void onPreviewTextureCopied() {

    }

    @Override
    public void onCaptureTextureCopied() {

    }

    @Override
    public void onUserInteraction() {

    }

    @Override
    public boolean updateStorageHintOnResume() {
        return false;
    }

    @Override
    public void onOrientationChanged(int orientation) { // frankie, is called every sensor event comming 
        // We keep the last known orientation. So if the user first orient
        // the camera then point the camera to floor or sky, we still have
        // the correct orientation.
        if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) return;
        int oldOrientation = mOrientation;
        mOrientation = CameraUtil.roundOrientation(orientation, mOrientation);
        if (oldOrientation != mOrientation) {
			Log.v(TAG, "onOrientationChanged:" + mOrientation);
            //mUI.onOrientationChanged();
            mUI.setOrientation(mOrientation, true);
        }
    }

    @Override
    public void onShowSwitcherPopup() {

    }

    @Override
    public void onMediaSaveServiceConnected(MediaSaveService s) {

    }

    @Override
    public boolean arePreviewControlsVisible() {
        return false;
    }

    @Override
    public void resizeForPreviewAspectRatio() {

    }

    @Override
    public void onSwitchSavePath() {

    }

    @Override
    public void waitingLocationPermissionResult(boolean waiting) {

    }

    @Override
    public void enableRecordingLocation(boolean enable) {

    }

    public void setFlashLightEnable(boolean enable) {
        if(mCameraDevice != null && mInitialParameter != null) {
            if(enable) {
                mInitialParameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCameraDevice.setParameters(mInitialParameter);
            } else {
                mInitialParameter.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCameraDevice.setParameters(mInitialParameter);
            }
        }

    }
    //////////////////////////////////////////////////////////////////////////////////
    static final boolean DEBUG_FINAL_THUMBNAIL_EN = true; // false;
    final private ActivityController mControllerCallback = new ActivityController() {
        @Override
        public Handler getHandler() {	// return the handler to process the decode result !
            return mScanHandler;
        }
        @Override
        public Point getPreviewResolution() {
            return new Point(mCameraPreviewWidth, mCameraPreviewHeight);
        }
        @Override
        public Rect getCropRect() {
            return mUI.getCropRect();
        }
        /**
         * A valid barcode has been found, so give an indication of success and show
         * the results.
         *
         * @param rawResult The contents of the barcode.
         * @param bundle    The extras
         */
        public void handleDecode(Result rawResult, Bundle bundle) {
            beepManager.playBeepSoundAndVibrate();
            // liujia mark 二维码扫码结果
            //Intent resultIntent = new Intent();
            //bundle.putInt("width", getCropRect().width());
            //bundle.putInt("height", getCropRect().height());
            //bundle.putString("result", rawResult.getText());
            //resultIntent.putExtras(bundle);

            BarcodeFormat resultFormat = rawResult.getBarcodeFormat();
            Log.v(TAG, "resultFormat:" + resultFormat.toString());
            Log.v(TAG, "[" + rawResult.getText() + "]");

            final ImageView imageView = (ImageView) mRootView.findViewById(R.id.preview_debug_imageview_1);
            if(DEBUG_FINAL_THUMBNAIL_EN) {
                byte[] jpeg_thumbnail = bundle.getByteArray(DecodeThread.BARCODE_JPEG);
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeByteArray(jpeg_thumbnail, 0, jpeg_thumbnail.length, options);
                if (imageView != null) {
                    imageView.setBackground(new BitmapDrawable(bitmap));
                }
            }


			// frankie, debug show result on dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle(mActivity.getString(R.string.zxing_bar_name));
            builder.setMessage("Result:[" + rawResult.getText() + "]");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(DEBUG_FINAL_THUMBNAIL_EN) {
                        if(imageView != null) {
                            imageView.setBackground(null);
                        }
                    }
                    if(mScanHandler!=null) {
                        mScanHandler.restartPreviewAndDecodeChecked();
                    }
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if(DEBUG_FINAL_THUMBNAIL_EN) {
                        if(imageView != null) {
                            imageView.setBackground(null);
                        }
                    }
                    if(mScanHandler!=null) {
                        mScanHandler.restartPreviewAndDecodeChecked();
                    }
                }
            });
            builder.show();
			
        }
        @Override
        public void setResult_(int resultCode, Intent data) {
        }
        @Override
        public void finish_() {
        }
    };


    private ScanHandler mScanHandler = null;
    /*public*/private class PreviewCallback implements CameraManager.CameraPreviewDataCallback {
        private Handler previewHandler;
        private int previewMessage_what;
        private int previewFrameCount = 0;
        public PreviewCallback() {
        }
        public void setHandler(Handler previewHandler, int previewMessage) {
            this.previewHandler = previewHandler;
            this.previewMessage_what = previewMessage;
        }
        @Override
        public void onPreviewFrame(byte[] data, CameraManager.CameraProxy camera) {
            //Log.v(TAG, "onPreviewFrame:" + (++previewFrameCount));
            Point cameraResolution = new Point();
            cameraResolution.x = mCameraPreviewWidth;
            cameraResolution.y = mCameraPreviewHeight;
            Handler thePreviewHandler = previewHandler;
            if (cameraResolution != null && thePreviewHandler != null) {
                Message message = thePreviewHandler.obtainMessage(previewMessage_what, cameraResolution.x, cameraResolution.y, data);
                message.sendToTarget();
                previewHandler = null;
            } else {
                Log.d(TAG, "Got preview callback, but no handler or resolution available");
            }
        }

    }
    private enum State {
        SCAN_HANDLER_PREVIEW, SCAN_HANDLER_SUCCESS, SCAN_HANDLER_DONE
    };
    private class ScanHandler extends Handler {
        private State state;
        private final DecodeThread decodeThread;
        private final PreviewCallback previewCallback;
        public ScanHandler() {
            previewCallback = new PreviewCallback();
            decodeThread = new DecodeThread(mControllerCallback, DecodeThread.ALL_MODE);
            decodeThread.start();
			decodeThread.getHandler(); // frankie, this wait the looper started
            state = State.SCAN_HANDLER_SUCCESS;	// default success for start 
        }
        public void restartPreviewAndDecodeChecked() {
            if (state == State.SCAN_HANDLER_SUCCESS) {
                state = State.SCAN_HANDLER_PREVIEW;
                requestPreviewFrame(decodeThread.getHandler(), R.id.decode);
            }
        }
        private void requestPreviewFrame(Handler handler, int message_what) {
            if (mCameraDevice != null && mPreviewStarted) {
				if(AGlobalConfig.config_module_SCAN_preview_data_debug_en) {
                    state = State.SCAN_HANDLER_SUCCESS; // force sucess
                    mCameraDevice.setOneShotPreviewCallback(myHandler, mPreviewDataDebugCallback);
				} else {
					previewCallback.setHandler(handler, message_what);
                	mCameraDevice.setOneShotPreviewCallback(myHandler, previewCallback);
				}
            }
        }
        @Override
        public void handleMessage(Message message) {
			if(R.id.decode_some_infomation == message.what) { // frankie, add , if the luminance is low, to open the flashlight
				// 
			}
			if (message.what == R.id.decode_succeeded) {
                Log.v(TAG, "decode_succeeded");
                state = State.SCAN_HANDLER_SUCCESS;
                Bundle bundle = message.getData();
                mControllerCallback.handleDecode((Result) message.obj, bundle);

                //restartPreviewAndDecodeChecked();
            }
            else if (message.what == R.id.decode_failed) {// We're decoding as fast as possible, so when one
                Log.v(TAG, "decode_failed");

				// decode fails,start another.
                state = State.SCAN_HANDLER_PREVIEW;
				state = State.SCAN_HANDLER_SUCCESS;
                restartPreviewAndDecodeChecked();
            }
			//else if (message.what == R.id.restart_preview) {
            //    restartPreviewAndDecodeChecked();
            //}
        }

        public void quitSynchronously() {
            state = State.SCAN_HANDLER_DONE;
            Message quit = Message.obtain(decodeThread.getHandler(), R.id.quit);
            quit.sendToTarget();
            try {
                // Wait at most half a second; should be enough time, and onPause()
                // will timeout quickly
                decodeThread.join(500L);
            } catch (InterruptedException e) {
                // continue
            }

            // Be absolutely sure we don't send any queued up messages
            removeMessages(R.id.decode_succeeded);
            removeMessages(R.id.decode_failed);
        }


		
    }
	
    /////////////////////////////////// preview data debug start
    private Handler debugUIHandler = new Handler() {
        @Override
        public void handleMessage(Message message) {
        	if(mPaused) {
				return ;
			}
            final Bitmap bitmap = (Bitmap)message.obj;
            final ImageView imageView = (ImageView)mRootView.findViewById(R.id.preview_debug_imageview_1);
            if(imageView != null) {
                imageView.setBackground(new BitmapDrawable((Bitmap)message.obj));
            }
            if(message.what == 0) {
                PreviewDebugDialog selectDialog = new PreviewDebugDialog(mActivity, R.style.preview_debug_dialog_style);
                Window win = selectDialog.getWindow();
                WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                //params.x = 0;
                //params.y = 0;// (0,0) is in center of screen
                params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                win.setAttributes(params);
                selectDialog.setCanceledOnTouchOutside(true);
                selectDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        //debugUIHandler.postDelayed(new StartDebugProcessRunnable(imageView, null), 500);
                        if(imageView != null) {
                            imageView.setBackground(null);
                        }
                        if (mScanHandler != null) {
                            mScanHandler.restartPreviewAndDecodeChecked();
                        }
                    }
                });
                selectDialog.show();
            }
        }
    };
    private class PreviewDebugHandler extends Handler {
        public PreviewDebugHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message message) {
            if(message.what == 0) {
                // process the preview data, then send it to ui thread for display
                byte[] preview_data = (byte[])message.obj;
                int width_ = mCameraPreviewWidth;
                int height_ = mCameraPreviewHeight;

                if(true) { // ** rotate NV21 90
                    byte[] rotatedData = new byte[preview_data.length];
                    int Y_size = width_ * height_;
					int new_width = height_;
					int new_height = width_;
					for(int y = 0; y < height_; y++) {
						for(int x = 0; x < width_; x++) {
                            int uv_x = x / 2;
                            uv_x *= 2;
							byte Y = preview_data[y*width_ + x];
							byte Cr = preview_data[Y_size + (y >> 1) * (width_) + uv_x + 0];
							byte Cb = preview_data[Y_size + (y >> 1) * (width_) + uv_x + 1];

							// new location 
							int new_x = height_ - 1 - y;
							int new_y = x;
                            int new_uv_x = new_x / 2;
                            new_uv_x *= 2;
							rotatedData[new_y*new_width + new_x] = Y;
							rotatedData[Y_size + (new_y>>1) * (new_width) + new_uv_x + 0] = Cr;
							rotatedData[Y_size + (new_y>>1) * (new_width) + new_uv_x + 1] = Cb;
						}
					}
					
                    width_ = new_width;
                    height_ = new_height;
                    preview_data = rotatedData;
                }

                Log.v(TAG, "preview_data : " + preview_data.length);    // 3110400 = 1920*1080 * 1.5 
                // NV21 format into Bitmap

                YuvImage yuvimage = new YuvImage(preview_data, ImageFormat.NV21, width_, height_, null);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                yuvimage.compressToJpeg( new Rect(0, 0,yuvimage.getWidth(), yuvimage.getHeight()), 100, baos);
                byte[] rawJpegImage =baos.toByteArray();
                BitmapFactory.Options options = new BitmapFactory.Options();
//                SoftReference<Bitmap> softRef = new SoftReference<Bitmap>(
//                        BitmapFactory.decodeByteArray(rawJpegImage, 0, rawJpegImage.length,options));
//                Bitmap bitmap = (Bitmap) softRef.get();
                Bitmap bitmap = BitmapFactory.decodeByteArray(rawJpegImage, 0, rawJpegImage.length,options);

                if(false) { // rotate bitmap
                    Matrix matrix = new Matrix();
                    matrix.postScale(1f, 1f);
                    matrix.postRotate(90); // clockwise
                    Bitmap dstbmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    bitmap = dstbmp;
                }
                if(true) {
                    Log.v(TAG, "getConfig: " + bitmap.getConfig().toString());
                    Bitmap new_bitmap = bitmap.copy(bitmap.getConfig(), true); // copy a new one
                    bitmap.recycle();

                    // draw the effect rectangle
                    Canvas canvas = new Canvas(new_bitmap);
                    // 01-08 03:59:59.378: E/AndroidRuntime(29124): java.lang.IllegalStateException: Immutable bitmap passed to Canvas constructor
                    Paint paint = new Paint();
                    paint.setStrokeWidth(2);
                    paint.setColor(0xffff0000);

                    Rect rect = mControllerCallback.getCropRect();
                    //rect = new Rect(0,0,100,100);
                    canvas.drawRect(rect, paint);
                    canvas.drawLine(0, 0, bitmap.getWidth(), bitmap.getHeight(), paint);

                    bitmap = new_bitmap;
                }

                if(mPreviewDebugBitmap != null) {
                    mPreviewDebugBitmap.recycle();
                }
                mPreviewDebugBitmap = bitmap;
                Message resultMessage = debugUIHandler.obtainMessage(0, -1, -1, bitmap);
                debugUIHandler.sendMessageDelayed(resultMessage, 2000);
            }
        }
    }
    private CameraManager.CameraPreviewDataCallback mPreviewDataDebugCallback =
            new CameraManager.CameraPreviewDataCallback() {
                @Override
                public void onPreviewFrame(byte[] data, CameraManager.CameraProxy camera) {
                    if(mPreviewDebugHandler != null) {
                        mPreviewDebugHandler.obtainMessage(0, -1,-1,data).sendToTarget();
                    }
                }
            };
    /////////////////////////////////// preview data debug end
    
    private boolean startPreview() {
        mCameraDevice.setPreviewDisplay(mSurfaceHolder);
        mCameraDevice.setOneShotPreviewCallback(myHandler, new CameraManager.CameraPreviewDataCallback() {
            @Override
            public void onPreviewFrame(byte[] data, CameraManager.CameraProxy camera) {
                mUI.hidePreviewCover();
                mUI.initCrop(); // at this time , view is ready !!!
                if(mScanHandler != null) {
					if(AGlobalConfig.config_module_SCAN_start_preview_data_process_en) {
                    	mScanHandler.restartPreviewAndDecodeChecked();
					}
                }
            }
        });
		if(AGlobalConfig.config_module_SCAN_preview_data_debug_en) {
			if(mPreviewDebugThread == null && mPreviewDebugHandler == null) {
				mPreviewDebugThread = new HandlerThread("preview_debug");
				mPreviewDebugThread.start();
                mPreviewDebugHandler = new PreviewDebugHandler(mPreviewDebugThread.getLooper());
			}
		}
		if(mScanHandler == null) {
            mScanHandler = new ScanHandler();
        }
		mCameraDevice.startPreview();

        mPreviewStarted = true;
        return true;
    }
    private boolean stopPreview() {
        if(mScanHandler != null) {
            mScanHandler.quitSynchronously();
            mScanHandler = null;
        }
		if(AGlobalConfig.config_module_SCAN_preview_data_debug_en) {
            debugUIHandler.removeMessages(0);
            if(mPreviewDebugBitmap != null) {
                mPreviewDebugBitmap.recycle();
                mPreviewDebugBitmap = null;
            }
            if(mPreviewDebugHandler != null) {
                mPreviewDebugHandler.removeMessages(0);
                mPreviewDebugHandler = null;
            }
            if(mPreviewDebugThread == null) {
                mPreviewDebugThread.quitSafely();
                mPreviewDebugThread = null;
            }
		}
		
        mCameraDevice.stopPreview();
        mPreviewStarted = false;
        return true;
    }

    /**
     * Opens camera and sets the parameters.
     *
     * @return Whether the camera was opened successfully.
     */
    private boolean setupCamera() {
    	Log.v(TAG, "+ setupCamera");
        if (!openCamera()) {
            Log.v(TAG, "setupCamera error");
            return false;
        }
        Camera.Parameters parameters = mCameraDevice.getParameters();
        setupPreviewParams(parameters);
        configureCamera(parameters);
		mInitialParameter = parameters;
		
		mActivity.setSlideSwitcherShow(true, false); // frankie, add 

			/*****/
        mCameraDevice.setDisplayOrientation(90); // frankie, add // why 
        mUI.onCameraOpened(); // frankie, add

        Log.v(TAG, "- setupCamera done");
        return true;
    }

    private void releaseCamera() {
        Log.v(TAG, "releaseCamera mCameraDevice" + (mCameraDevice!=null ? "!=null" : "==null"));
        if (mCameraDevice != null) {
			mInitialParameter = null;
            Log.v(TAG, "    isForceReleaseCamera:" + mActivity.isForceReleaseCamera());
            if (mActivity.isForceReleaseCamera())
            //if(true) // frankie, use this result camera open/close error !
            {
                CameraHolder.instance().strongRelease();
            } else {
                CameraHolder.instance().release();
            }
            mCameraDevice.setErrorCallback(null);
            mCameraDevice = null;
            //mCameraState = PREVIEW_STOPPED;
        }
    }
    /**
     * Opens the camera device. The back camera has priority over the front
     * one.
     *
     * @return Whether the camera was opened successfully.
     */

    private boolean openCamera() {
        int cameraId = CameraHolder.instance().getBackCameraId();
        // If there is no back camera, use the first camera. Camera id starts
        // from 0. Currently if a camera is not back facing, it is front facing.
        // This is also forward compatible if we have a new facing other than
        // back or front in the future.
        if (cameraId == -1) cameraId = 0;

        // If mCameraDevice has already exist,there is no need to obtain again
        if (mCameraDevice == null) {
            mCameraDevice = CameraUtil.openCamera(mActivity, cameraId,
                    mMainHandler, mActivity.getCameraOpenErrorCallback());
            if (mCameraDevice == null) {
                return false;
            }
        }
        mCameraOrientation = CameraUtil.getCameraOrientation(cameraId);
        if (cameraId == CameraHolder.instance().getFrontCameraId()) {
            mUsingFrontCamera = true;
        }
        return true;
    }
    //public static final int DEFAULT_CAPTURE_PIXELS = 960 * 720;
    public static final int DEFAULT_CAPTURE_PIXELS = 1920 * 1080;
    private boolean findBestPreviewSize(List<Camera.Size> supportedSizes, boolean need4To3, boolean needSmaller) {
        int pixelsDiff = DEFAULT_CAPTURE_PIXELS;
        boolean hasFound = false;
        for (Camera.Size size : supportedSizes) {
            int h = size.height;
            int w = size.width;
            // we only want 4:3 format.
            int d = DEFAULT_CAPTURE_PIXELS - h * w;
            if (needSmaller && d < 0) { // no bigger preview than 960x720.
                continue;
            }
            if (need4To3 && (h * 4 != w * 3)) {
                continue;
            }
            d = Math.abs(d);
            if (d < pixelsDiff) {
                mCameraPreviewWidth = w;
                mCameraPreviewHeight = h;
                pixelsDiff = d;
                hasFound = true;
            }
        }
        return hasFound;
    }

    private void setupPreviewParams(Camera.Parameters parameters) {
        List<Camera.Size> supportedSizes = parameters.getSupportedPreviewSizes();
//        if (!findBestPreviewSize(supportedSizes, true, true)) {
//            Log.w(TAG, "No 4:3 ratio preview size supported.");
//            if (!findBestPreviewSize(supportedSizes, false, true)) {
//                Log.w(TAG, "Can't find a supported preview size smaller than 960x720.");
//                findBestPreviewSize(supportedSizes, false, false);
//            }
//        }
        if (!findBestPreviewSize(supportedSizes, false, true)) {
            Log.w(TAG, "Can't find a supported preview size smaller than 960x720.");
            findBestPreviewSize(supportedSizes, false, false);
        }

        //mCameraPreviewHeight = 1920; mCameraPreviewWidth = 1080; // frankie, portrait error, must be landscape !!!
        Log.d(TAG, "camera preview h = " + mCameraPreviewHeight + 
        	" , w = " + mCameraPreviewWidth); // h = 1080 , w = 1920
        parameters.setPreviewSize(mCameraPreviewWidth, mCameraPreviewHeight);

        List<Integer> supportedPreviewFormats = parameters.getSupportedPreviewFormats();
        Log.v(TAG, "getSupportedPreviewFormats:");
        for(Integer format_ : supportedPreviewFormats) {
            Log.v(TAG, "  " + MiscUtil.int2Hex(format_));
        }
        // YV12(0x32315659), NV21(0x11)
        Log.v(TAG, "getPreviewFormat:" + MiscUtil.int2Hex(parameters.getPreviewFormat())); // = 0x11

        List<int[]> frameRates = parameters.getSupportedPreviewFpsRange();
        int last = frameRates.size() - 1;
        int minFps = (frameRates.get(last))[Camera.Parameters.PREVIEW_FPS_MIN_INDEX];
        int maxFps = (frameRates.get(last))[Camera.Parameters.PREVIEW_FPS_MAX_INDEX];
        parameters.setPreviewFpsRange(minFps, maxFps);
        Log.d(TAG, "preview fps: " + minFps + ", " + maxFps);

        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        if (supportedFocusModes.indexOf(mTargetFocusMode) >= 0) {
            parameters.setFocusMode(mTargetFocusMode);
        } else {
            // Use the default focus mode and log a message
            Log.w(TAG, "Cannot set the focus mode to " + mTargetFocusMode +
                    " becuase the mode is not supported.");
        }

        parameters.set(CameraUtil.RECORDING_HINT, CameraUtil.FALSE);

		float mHorizontalViewAngle = parameters.getHorizontalViewAngle();
		float mVerticalViewAngle =  parameters.getVerticalViewAngle();
		
		Log.v(TAG, "mHorizontalViewAngle:" + mHorizontalViewAngle);
		Log.v(TAG, "mVerticalViewAngle:" + mVerticalViewAngle);
		
    }
    private void configureCamera(Camera.Parameters parameters) {
        mCameraDevice.setParameters(parameters);
    }
    public Point getPreviewSize_() {
        return new Point(mCameraPreviewWidth, mCameraPreviewHeight);
    }

    @Override
    public void updateThumbnail(boolean show) { } // frankie, add
    @Override
    public void updateThumbnail_condition() { } // frankie, add

}
