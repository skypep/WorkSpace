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

package com.android.camera;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.util.Log;

import com.android.camera.module.ScanUI;
//import com.android.camera.ui.PieRenderer;
import com.android.camera.module.ui.PieMenuRenderer; // frankie, add
import com.android.camera.ui.RenderOverlay;
import com.android.camera.ui.ZoomRenderer;

/* PreviewGestures disambiguates touch events received on RenderOverlay
 * and dispatch them to the proper recipient (i.e. zoom renderer or pie renderer).
 * Touch events on CameraControls will be handled by framework.
 * */
public class PreviewGestures
        implements ScaleGestureDetector.OnScaleGestureListener {

    private static final String TAG = "CAM_gestures";

    private static final int MODE_NONE = 0;
    private static final int MODE_ZOOM = 2;

    public static final int DIR_UP = 0;
    public static final int DIR_DOWN = 1;
    public static final int DIR_LEFT = 2;
    public static final int DIR_RIGHT = 3;

	private static final int SCROLL_UP_DOWN_RATIO = 16;
	private static final int SCROLL_LEFT_RIGHT_RATIO = 8;	// 2;

    private SingleTapListener mTapListener;
    private RenderOverlay mOverlay;
    //private PieRenderer mPie;
    private PieMenuRenderer mPie;
    private ZoomRenderer mZoom;
    private MotionEvent mDown;
    private MotionEvent mCurrent;
    private ScaleGestureDetector mScale;
    private int mMode;
    private boolean mZoomEnabled;
    private boolean mEnabled;
    private boolean mZoomOnly;
    private GestureDetector mGestureDetector;
	
    private CaptureUI mCaptureUI;
	private ScanUI mScanUI;
	private WideAnglePanoramaUI mPanoramaUI;
    private PhotoMenu mPhotoMenu;
    private VideoMenu mVideoMenu;
	private com.android.camera.module.WideAnglePanoramaUI mPanoramaUI_1;
    private com.android.camera.module.PhotoMenu mPhotoMenu_1;
    private com.android.camera.module.VideoMenu mVideoMenu_1;
	
    private boolean waitUntilNextDown;
    private boolean setToFalse;

	private CameraActivity mCameraActivity;

    private GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public void onLongPress (MotionEvent e) {
//            // Open pie
//            if (!mZoomOnly && mPie != null && !mPie.showsItems()) {
//                openPie();
//            }
        }

        @Override
        public boolean onSingleTapUp (MotionEvent e) {
            // Tap to focus when pie is not open
            if (mPie == null || !mPie.showsItems()) {
				if(mTapListener != null) { // frankie, add 
                	mTapListener.onSingleTapUp(null, (int) e.getX(), (int) e.getY());
				}
                return true;
            }
            return false;
        }

        @Override
        public boolean onScroll (MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (e1 == null) {
                // e1 can be null if for some cases.
                return false;
            }
            if (mZoomOnly || mMode == MODE_ZOOM) return false;

			///////////////////////////////////// frankie, move from old start 
            int deltaX = (int) (e1.getX() - e2.getX());
            int deltaY = (int) (e1.getY() - e2.getY());

            int orientation = 0;
            
            if (mScanUI != null) {
                orientation = mScanUI.getOrientation();
            }
			else if (mCaptureUI != null) {
				 orientation = mCaptureUI.getOrientation();
			}
			//
            else if (mPanoramaUI != null) {
                orientation = mPanoramaUI.getOrientation();
            }
			else if (mPhotoMenu != null) {
                orientation = mPhotoMenu.getOrientation();
            }
            else if (mVideoMenu != null) {
                orientation = mVideoMenu.getOrientation();
            }
			//
            else if (mPanoramaUI_1 != null) {
                orientation = mPanoramaUI_1.getOrientation();
            }
			else if (mPhotoMenu_1 != null) {
                orientation = mPhotoMenu_1.getOrientation();
            }
            else if (mVideoMenu_1 != null) {
                orientation = mVideoMenu_1.getOrientation();
            }
			
 			//Log.v(TAG, "@" + PreviewGestures.this.hashCode() + "/onScroll, orientation:" 
			//	+ orientation + " deltaX:" + deltaX + " deltaY:" + deltaY);
			
            if (isLeftSwipe(orientation, deltaX, deltaY)) {
                //waitUntilNextDown = true;
                if (mPhotoMenu != null && !mPhotoMenu.isMenuBeingShown()) {/*mPhotoMenu.openFirstLevel();*/}
                else if (mVideoMenu != null && !mVideoMenu.isMenuBeingShown()) {/*mVideoMenu.openFirstLevel();*/}
            	else if (mCaptureUI != null && !mCaptureUI.isMenuBeingShown()) {/*mCaptureUI.showSettingMenu();*/}
				mCameraActivity.onModuleSelectPrev();
                return true;
            }
			else if (isRightSwipe(orientation, deltaX, deltaY)) {
				//waitUntilNextDown = true;
				mCameraActivity.onModuleSelectNext();
				return true;
			}
			else if (isUpSwipe(orientation, deltaX, deltaY)) {
				//waitUntilNextDown = true;
				Log.v(TAG, ">>> isUpSwipe");
				//mCameraActivity.onCameraIdToggled(); // remove by zj fix 3565
				return true;
			}
			else if (isDownSwipe(orientation, deltaX, deltaY)) {
				//waitUntilNextDown = true;
				Log.v(TAG, ">>> isDownSwipe");
				//mCameraActivity.onCameraIdToggled(); // remove by zj fix 3565
				return true;
			}
			else {
                return onSingleTapUp(e2);
            }
			///////////////////////////////////// frankie, move from old end

            //return onSingleTapUp(e2);
        }

        private boolean isLeftSwipe(int orientation, int deltaX, int deltaY) {
            switch (orientation) {
                case 90:
                    return deltaY > 0 && Math.abs(deltaY) > SCROLL_LEFT_RIGHT_RATIO * Math.abs(deltaX);
                case 180:
                    return deltaX > 0 && Math.abs(deltaX) > SCROLL_LEFT_RIGHT_RATIO * Math.abs(deltaY);
                case 270:
                    return deltaY < 0 && Math.abs(deltaY) > SCROLL_LEFT_RIGHT_RATIO * Math.abs(deltaX);
                default:
                    return deltaX < 0 && Math.abs(deltaX) > SCROLL_LEFT_RIGHT_RATIO * Math.abs(deltaY);
            }
        }
	    private boolean isRightSwipe(int orientation, int deltaX, int deltaY) {
            switch (orientation) {
                case 90:
                    return deltaY < 0 && Math.abs(deltaY) > SCROLL_LEFT_RIGHT_RATIO * Math.abs(deltaX);
                case 180:
                    return deltaX < 0 && Math.abs(deltaX) > SCROLL_LEFT_RIGHT_RATIO * Math.abs(deltaY);
                case 270:
                    return deltaY > 0 && Math.abs(deltaY) > SCROLL_LEFT_RIGHT_RATIO * Math.abs(deltaX);
                default:
                    return deltaX > 0 && Math.abs(deltaX) > SCROLL_LEFT_RIGHT_RATIO * Math.abs(deltaY);
            }
        }
		private boolean isUpSwipe(int orientation, int deltaX, int deltaY) {
            switch (orientation) {
                case 90:
                    return deltaX < 0 && Math.abs(deltaX) > SCROLL_UP_DOWN_RATIO * Math.abs(deltaY);
                case 180:
                    return deltaY < 0 && Math.abs(deltaY) > SCROLL_UP_DOWN_RATIO * Math.abs(deltaX);
                case 270:
                    return deltaX > 0 && Math.abs(deltaX) > SCROLL_UP_DOWN_RATIO * Math.abs(deltaY);
                default:
                    return deltaY > 0 && Math.abs(deltaY) > SCROLL_UP_DOWN_RATIO * Math.abs(deltaX);
            }
        }	
		private boolean isDownSwipe(int orientation, int deltaX, int deltaY) {
            switch (orientation) {
                case 90:
                    return deltaX > 0 && Math.abs(deltaX) > SCROLL_UP_DOWN_RATIO * Math.abs(deltaY);
                case 180:
                    return deltaY > 0 && Math.abs(deltaY) > SCROLL_UP_DOWN_RATIO * Math.abs(deltaX);
                case 270:
                    return deltaX < 0 && Math.abs(deltaX) > SCROLL_UP_DOWN_RATIO * Math.abs(deltaY);
                default:
                    return deltaY < 0 && Math.abs(deltaY) > SCROLL_UP_DOWN_RATIO * Math.abs(deltaX);
            }
        }

		
    };

    public interface SingleTapListener {
        public void onSingleTapUp(View v, int x, int y);
    }

    public PreviewGestures(CameraActivity ctx, SingleTapListener tapListener,
			ZoomRenderer zoom, PieMenuRenderer pie) {
        mCameraActivity = ctx; // frankie, add 
        mTapListener = tapListener;
        mPie = pie;
        mZoom = zoom;
        mMode = MODE_NONE;
        mScale = new ScaleGestureDetector(ctx, this);
        mEnabled = true;
        mGestureDetector = new GestureDetector(mGestureListener);
		Log.v(TAG, "@" + PreviewGestures.this.hashCode() + "/new");
    }

    public void setRenderOverlay(RenderOverlay overlay) {
        mOverlay = overlay;
    }

    public void setEnabled(boolean enabled) {
		//Log.v(TAG, "@" + PreviewGestures.this.hashCode() + "/setEnabled:" + enabled);
        mEnabled = enabled;
    }

    public void setZoomEnabled(boolean enable) {
        mZoomEnabled = enable;
    }

    public void setZoomOnly(boolean zoom) {
        mZoomOnly = zoom;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

	// 
    public void setCaptureUI(CaptureUI ui) {
        mCaptureUI = ui;
    }
    public void setScanUI(ScanUI ui) {
        mScanUI = ui;
    }
    public void setWideAnglePanoramaUI(WideAnglePanoramaUI ui) {
        mPanoramaUI = ui;
    }
    public void setPhotoMenu(PhotoMenu menu) {
        mPhotoMenu = menu;
    }
    public void setVideoMenu(VideoMenu menu) {
        mVideoMenu = menu;
    }
    public void setWideAnglePanoramaUI(com.android.camera.module.WideAnglePanoramaUI ui) {
		mPanoramaUI_1 = ui;
	}
    public void setPhotoMenu(com.android.camera.module.PhotoMenu menu) {
        mPhotoMenu_1 = menu;
    }
    public void setVideoMenu(com.android.camera.module.VideoMenu menu) {
        mVideoMenu_1 = menu;
    }

    public PhotoMenu getPhotoMenu() {
        return mPhotoMenu;
    }
    public VideoMenu getVideoMenu() {
        return mVideoMenu;
    }
    public com.android.camera.module.PhotoMenu getPhotoMenu_1() {
        return mPhotoMenu_1;
    }
    public com.android.camera.module.VideoMenu getVideoMenu_1() {
        return mVideoMenu_1;
    }
	//
	
    public boolean dispatchTouch(MotionEvent m) {
        if (setToFalse) {
            waitUntilNextDown = false;
            setToFalse = false;
        }
        if (waitUntilNextDown) {
            if (MotionEvent.ACTION_UP != m.getActionMasked()
                    && MotionEvent.ACTION_CANCEL != m.getActionMasked())
                return true;
            else {
                setToFalse = true;
                return true;
            }
        }
        if (!mEnabled) {
            return false;
        }
        mCurrent = m;
        if (MotionEvent.ACTION_DOWN == m.getActionMasked()) {
            mMode = MODE_NONE;
            mDown = MotionEvent.obtain(m);
        }

		// frankie, 2018.01.10, disable 
//        // If pie is open, redirects all the touch events to pie.
//        if (mPie != null && mPie.isOpen()) {
//            return sendToPie(m);
//        }
//
//        if (mCaptureUI != null) {
//            if (mCaptureUI.isMenuBeingShown()) {
//                if (!mCaptureUI.isMenuBeingAnimated()) {
//                    waitUntilNextDown = true;
//                    mCaptureUI.removeAllSettingMenu(true);
//                }
//                return true;
//            }
//            if (mCaptureUI.isPreviewMenuBeingShown()) {
//                waitUntilNextDown = true;
//                mCaptureUI.removeSceneAndFilterMenu(true);
//                return true;
//            }
//        }
//
//        if (mPhotoMenu != null) {
//            if (mPhotoMenu.isMenuBeingShown()) {
//                if (!mPhotoMenu.isMenuBeingAnimated()) {
//                    waitUntilNextDown = true;
//                    mPhotoMenu.closeView();
//                }
//                return true;
//            }
//            if (mPhotoMenu.isPreviewMenuBeingShown()) {
//                waitUntilNextDown = true;
//                mPhotoMenu.animateSlideOutPreviewMenu();
//                return true;
//            }
//        }
//
//        if (mVideoMenu != null) {
//            if (mVideoMenu.isMenuBeingShown()) {
//                if (!mVideoMenu.isMenuBeingAnimated()) {
//                    waitUntilNextDown = true;
//                    mVideoMenu.closeView();
//                }
//                return true;
//            }
//
//            if (mVideoMenu.isPreviewMenuBeingShown()) {
//                waitUntilNextDown = true;
//                mVideoMenu.animateSlideOutPreviewMenu();
//                return true;
//            }
//        }
//
//		// frankie, add start
//        if (mPhotoMenu_1 != null) {
//            if (mPhotoMenu_1.isMenuBeingShown()) {
//                if (!mPhotoMenu_1.isMenuBeingAnimated()) {
//                    waitUntilNextDown = true;
//                    mPhotoMenu_1.closeView();
//                }
//                return true;
//            }
//            if (mPhotoMenu_1.isPreviewMenuBeingShown()) {
//                waitUntilNextDown = true;
//                mPhotoMenu_1.animateSlideOutPreviewMenu();
//                return true;
//            }
//        }
//        if (mVideoMenu_1 != null) {
//            if (mVideoMenu_1.isMenuBeingShown()) {
//                if (!mVideoMenu_1.isMenuBeingAnimated()) {
//                    waitUntilNextDown = true;
//                    mVideoMenu_1.closeView();
//                }
//                return true;
//            }
//
//            if (mVideoMenu_1.isPreviewMenuBeingShown()) {
//                waitUntilNextDown = true;
//                mVideoMenu_1.animateSlideOutPreviewMenu();
//                return true;
//            }
//        }
//		// frankie, add end
	
        // If pie is not open, send touch events to gesture detector and scale
        // listener to recognize the gesture.
        mGestureDetector.onTouchEvent(m);
        if (mZoom != null) {
            mScale.onTouchEvent(m);
            if (MotionEvent.ACTION_POINTER_DOWN == m.getActionMasked()) {
                mMode = MODE_ZOOM;
                if (mZoomEnabled) {
                    // Start showing zoom UI as soon as there is a second finger down
                    mZoom.onScaleBegin(mScale);
                }
            } else if (MotionEvent.ACTION_POINTER_UP == m.getActionMasked()) {
                mZoom.onScaleEnd(mScale);
            }
        }
        return true;
    }

    public boolean waitUntilNextDown() {
        return waitUntilNextDown;
    }

    private MotionEvent makeCancelEvent(MotionEvent m) {
        MotionEvent c = MotionEvent.obtain(m);
        c.setAction(MotionEvent.ACTION_CANCEL);
        return c;
    }

//    private void openPie() {
//        mGestureDetector.onTouchEvent(makeCancelEvent(mDown));
//        mScale.onTouchEvent(makeCancelEvent(mDown));
//        mOverlay.directDispatchTouch(mDown, mPie);
//    }
//
//    private boolean sendToPie(MotionEvent m) {
//        return mOverlay.directDispatchTouch(m, mPie);
//    }

	//////////////////////////////////////////////////////
    // OnScaleGestureListener implementation
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        return mZoom.onScale(detector);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        if (mPie == null || !mPie.isOpen()) {
            mMode = MODE_ZOOM;
            mGestureDetector.onTouchEvent(makeCancelEvent(mCurrent));
            if (!mZoomEnabled) return false;
            return mZoom.onScaleBegin(detector);
        }
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        mZoom.onScaleEnd(detector);
    }
	//////////////////////////////////////////////////////
	
}

