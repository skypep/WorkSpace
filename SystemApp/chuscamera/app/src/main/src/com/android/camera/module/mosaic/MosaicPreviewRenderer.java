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

package com.android.camera.module.mosaic;

import android.graphics.SurfaceTexture;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import javax.microedition.khronos.opengles.GL10;

// frankie, for SurfaceTextureRenderer start
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.util.Log;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;
// frankie, for SurfaceTextureRenderer end


public class MosaicPreviewRenderer {

    @SuppressWarnings("unused")
    private static final String TAG = "CAM_MosaicPreviewRenderer";

	private static final boolean PREVIEW_WARPING_SHOW_ENABLE = true; // frankie, add 
	
    private int mWidth; // width of the view in UI
    private int mHeight; // height of the view in UI

    private boolean mIsLandscape = true;
    private int mOrientation = 0;
    private final float[] mTransformMatrix = new float[16];

    private ConditionVariable mEglThreadBlockVar = new ConditionVariable();
    private HandlerThread mEglThread;
    private MyHandler mHandler;
    private SurfaceTextureRenderer mSTRenderer;

    private SurfaceTexture mInputSurfaceTexture;

    private boolean mEnableWarpedPanoPreview = false;

    private class MyHandler extends Handler {
        public static final int MSG_INIT_SYNC = 0;
        public static final int MSG_SHOW_PREVIEW_FRAME_SYNC = 1;
        public static final int MSG_SHOW_PREVIEW_FRAME = 2;
        public static final int MSG_ALIGN_FRAME_SYNC = 3;
        public static final int MSG_RELEASE = 4;
        public static final int MSG_DO_PREVIEW_RESET = 5;
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_INIT_SYNC:
                    doInit();
                    mEglThreadBlockVar.open();
                    break;
                case MSG_SHOW_PREVIEW_FRAME_SYNC:
                    doShowPreviewFrame();
                    mEglThreadBlockVar.open();
                    break;
                case MSG_DO_PREVIEW_RESET:
                    doPreviewReset();
                    break;
                case MSG_SHOW_PREVIEW_FRAME:
                    doShowPreviewFrame();
                    break;
                case MSG_ALIGN_FRAME_SYNC:
                    doAlignFrame();
                    mEglThreadBlockVar.open();
                    break;
                case MSG_RELEASE:
                    doRelease();
                    mEglThreadBlockVar.open();
                    break;
            }
        }
        private void doInit() {
            mInputSurfaceTexture = new SurfaceTexture(JniMosaicRenderer.init(mEnableWarpedPanoPreview));
            JniMosaicRenderer.reset(mWidth, mHeight, mIsLandscape, mOrientation);
        }
        private void doShowPreviewFrame() {
            mInputSurfaceTexture.updateTexImage();
            mInputSurfaceTexture.getTransformMatrix(mTransformMatrix);

			if(PREVIEW_WARPING_SHOW_ENABLE) { // frankie, add , *** when open , malfunction !!!
				JniMosaicRenderer.setWarping(false);
			} else {
            	JniMosaicRenderer.setWarping(false);
			}
            // Call preprocess to render it to low-res and high-res RGB textures.
            JniMosaicRenderer.preprocess(mTransformMatrix);
            JniMosaicRenderer.updateMatrix();
            JniMosaicRenderer.step();
        }
        private void doPreviewReset() {
            JniMosaicRenderer.reset(mWidth, mHeight, mIsLandscape, mOrientation);
        }
        private void doAlignFrame() {
            mInputSurfaceTexture.updateTexImage();
            mInputSurfaceTexture.getTransformMatrix(mTransformMatrix);

            // Call setPreviewBackground to render high-res RGB textures to full screen.
            JniMosaicRenderer.setPreviewBackground(true);
            JniMosaicRenderer.preprocess(mTransformMatrix);
            JniMosaicRenderer.step();
            JniMosaicRenderer.setPreviewBackground(false);

            JniMosaicRenderer.setWarping(true);
            JniMosaicRenderer.transferGPUtoCPU();
            JniMosaicRenderer.updateMatrix();
            JniMosaicRenderer.step();
        }
		
        private void releaseSurfaceTexture(SurfaceTexture st) {
            st.release();
        }
        private void doRelease() {
            releaseSurfaceTexture(mInputSurfaceTexture);
            mEglThread.quit();
        }

        // Should be called from other thread.
        public void sendMessageSync(int msg) {
            mEglThreadBlockVar.close();
            sendEmptyMessage(msg);
            mEglThreadBlockVar.block();
        }
    }

    /**
     * Constructor.
     *
     * @param tex The {@link SurfaceTexture} for the final UI output.
     * @param w The width of the UI view.
     * @param h The height of the UI view.
     * @param isLandscape The UI orientation. {@code true} if in landscape,
     *                    false if in portrait.
     */
    public MosaicPreviewRenderer(SurfaceTexture tex, int w, int h, boolean isLandscape,
            int orientation, boolean enableWarpedPanoPreview) {
        mIsLandscape = isLandscape;
        mOrientation = orientation;
        mEnableWarpedPanoPreview = enableWarpedPanoPreview;
        mEglThread = new HandlerThread("PanoramaRealtimeRenderer");
        mEglThread.start();
        mHandler = new MyHandler(mEglThread.getLooper());
        mWidth = w;
        mHeight = h;

        SurfaceTextureRenderer.FrameDrawer dummy = new SurfaceTextureRenderer.FrameDrawer() {
            @Override
            public void onDrawFrame(GL10 gl) {
                // nothing, we have our draw functions.
            }
        };
        mSTRenderer = new SurfaceTextureRenderer(tex, mHandler, dummy);

        // We need to sync this because the generation of surface texture for input is
        // done here and the client will continue with the assumption that the
        // generation is completed.
        mHandler.sendMessageSync(MyHandler.MSG_INIT_SYNC);
    }

    public void previewReset(int w, int h, boolean isLandscape, int orientation) {
        mWidth = w;
        mHeight = h;
        mIsLandscape = isLandscape;
        mOrientation = orientation;
        mHandler.sendEmptyMessage(MyHandler.MSG_DO_PREVIEW_RESET);
        mSTRenderer.draw(false);
    }

    public void release() {
        mSTRenderer.release();
        mHandler.sendMessageSync(MyHandler.MSG_RELEASE);
    }

    public void showPreviewFrameSync() {
        mHandler.sendMessageSync(MyHandler.MSG_SHOW_PREVIEW_FRAME_SYNC);
        mSTRenderer.draw(true);
    }

    public void showPreviewFrame() {
        mHandler.sendEmptyMessage(MyHandler.MSG_SHOW_PREVIEW_FRAME);
        mSTRenderer.draw(false);	// frankie, only post runnable into this renderer thread to do eglSwapBuffers
    }

    public void alignFrameSync() {
        mHandler.sendMessageSync(MyHandler.MSG_ALIGN_FRAME_SYNC);
        mSTRenderer.draw(true);
    }

    public SurfaceTexture getInputSurfaceTexture() {
        return mInputSurfaceTexture;
    }

	//////////////////////////////////////////////////////////////////////////
	public static class SurfaceTextureRenderer {

	    public interface FrameDrawer {
	        public void onDrawFrame(GL10 gl);
	    }

	    private static final String TAG = "CAM_" + SurfaceTextureRenderer.class.getSimpleName();
	    private static final int EGL_CONTEXT_CLIENT_VERSION = 0x3098;

	    private EGLConfig mEglConfig;
	    private EGLDisplay mEglDisplay;
	    private EGLContext mEglContext;
	    private EGLSurface mEglSurface;
	    private EGL10 mEgl;
	    private GL10 mGl;

	    private Handler mEglHandler;
	    private FrameDrawer mFrameDrawer;



	//    public class RenderThread extends Thread {
	//        private Boolean mRenderStopped = false;
	//
	//        @Override
	//        public void run() {
	//            while (true) {
	//                synchronized (mRenderStopped) {
	//                    if (mRenderStopped) return;
	//                }
	//                draw(true);
	//            }
	//        }
	//
	//        public void stopRender() {
	//            synchronized (mRenderStopped) {
	//                mRenderStopped = true;
	//            }
	//        }
	//    }

	    public SurfaceTextureRenderer(SurfaceTexture tex,
	            Handler handler, FrameDrawer renderer) {
	        mEglHandler = handler;
	        mFrameDrawer = renderer;

	        initialize(tex);
	    }

	//    public RenderThread createRenderThread() {
	//        return new RenderThread();
	//    }

	    public void release() {
	        mEglHandler.post(new Runnable() {
	            @Override
	            public void run() {
	                mEgl.eglDestroySurface(mEglDisplay, mEglSurface);
	                mEgl.eglDestroyContext(mEglDisplay, mEglContext);
	                mEgl.eglMakeCurrent(mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE,
	                        EGL10.EGL_NO_CONTEXT);
	                mEgl.eglTerminate(mEglDisplay);
	                mEglSurface = null;
	                mEglContext = null;
	                mEglDisplay = null;
	            }
	        });
	    }

	    private Object mRenderLock = new Object();
	    private Runnable mRenderTask = new Runnable() {
	        @Override
	        public void run() {
	            synchronized (mRenderLock) {
	                if (mEglDisplay != null && mEglSurface != null) {
	                    mFrameDrawer.onDrawFrame(mGl);
	                    mEgl.eglSwapBuffers(mEglDisplay, mEglSurface);
	                }
	                mRenderLock.notifyAll();
	            }
	        }
	    };
	    /**
	     * Posts a render request to the GL thread.
	     * @param sync      set <code>true</code> if the caller needs it to be
	     *                  a synchronous call.
	     */
	    public void draw(boolean sync) {
	        synchronized (mRenderLock) {
	            mEglHandler.post(mRenderTask);
	            if (sync) {
	                try {
	                    mRenderLock.wait();
	                } catch (InterruptedException ex) {
	                    Log.v(TAG, "RenderLock.wait() interrupted");
	                }
	            }
	        }
	    }

	    private void initialize(final SurfaceTexture target) {
	        mEglHandler.post(new Runnable() {
	            @Override
	            public void run() {
	                mEgl = (EGL10) EGLContext.getEGL();
	                mEglDisplay = mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
	                if (mEglDisplay == EGL10.EGL_NO_DISPLAY) {
	                    throw new RuntimeException("eglGetDisplay failed");
	                }
	                int[] version = new int[2];
	                if (!mEgl.eglInitialize(mEglDisplay, version)) {
	                    throw new RuntimeException("eglInitialize failed");
	                } else {
	                    Log.v(TAG, "EGL version: " + version[0] + '.' + version[1]);
	                }
	                int[] attribList = {EGL_CONTEXT_CLIENT_VERSION, 2, EGL10.EGL_NONE };
	                mEglConfig = chooseConfig(mEgl, mEglDisplay);
	                mEglContext = mEgl.eglCreateContext(
	                        mEglDisplay, mEglConfig, EGL10.EGL_NO_CONTEXT, attribList);

	                if (mEglContext == null || mEglContext == EGL10.EGL_NO_CONTEXT) {
	                    throw new RuntimeException("failed to createContext");
	                }
	                mEglSurface = mEgl.eglCreateWindowSurface(
	                        mEglDisplay, mEglConfig, target, null);
	                if (mEglSurface == null || mEglSurface == EGL10.EGL_NO_SURFACE) {
	                    throw new RuntimeException("failed to createWindowSurface");
	                }

	                if (!mEgl.eglMakeCurrent(
	                        mEglDisplay, mEglSurface, mEglSurface, mEglContext)) {
	                    throw new RuntimeException("failed to eglMakeCurrent");
	                }

	                mGl = (GL10) mEglContext.getGL();
	            }
	        });
	        waitDone();
	    }

	    private void waitDone() {
	        final Object lock = new Object();
	        synchronized (lock) {
	            mEglHandler.post(new Runnable() {
	                @Override
	                public void run() {
	                    synchronized (lock) {
	                        lock.notifyAll();
	                    }
	                }
	            });
	            try {
	                lock.wait();
	            } catch (InterruptedException ex) {
	                Log.v(TAG, "waitDone() interrupted");
	            }
	        }
	    }

	    private static void checkEglError(String prompt, EGL10 egl) {
	        int error;
	        while ((error = egl.eglGetError()) != EGL10.EGL_SUCCESS) {
	            Log.e(TAG, String.format("%s: EGL error: 0x%x", prompt, error));
	        }
	    }

	    private static final int EGL_OPENGL_ES2_BIT = 4;
	    private static final int[] CONFIG_SPEC = new int[] {
	            EGL10.EGL_RENDERABLE_TYPE, EGL_OPENGL_ES2_BIT,
	            EGL10.EGL_RED_SIZE, 8,
	            EGL10.EGL_GREEN_SIZE, 8,
	            EGL10.EGL_BLUE_SIZE, 8,
	            EGL10.EGL_ALPHA_SIZE, 0,
	            EGL10.EGL_DEPTH_SIZE, 0,
	            EGL10.EGL_STENCIL_SIZE, 0,
	            EGL10.EGL_NONE
	    };

	    private static EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
	        int[] numConfig = new int[1];
	        if (!egl.eglChooseConfig(display, CONFIG_SPEC, null, 0, numConfig)) {
	            throw new IllegalArgumentException("eglChooseConfig failed");
	        }

	        int numConfigs = numConfig[0];
	        if (numConfigs <= 0) {
	            throw new IllegalArgumentException("No configs match configSpec");
	        }

	        EGLConfig[] configs = new EGLConfig[numConfigs];
	        if (!egl.eglChooseConfig(
	                display, CONFIG_SPEC, configs, numConfigs, numConfig)) {
	            throw new IllegalArgumentException("eglChooseConfig#2 failed");
	        }

	        return configs[0];
	    }
	}


}
