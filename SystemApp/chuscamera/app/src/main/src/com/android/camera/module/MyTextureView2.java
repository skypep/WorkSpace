package com.android.camera.module;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.opengl.EGLExt;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;

import com.toro.camera.R;
import com.cs.camera.magicfilter.Rotation;
import com.cs.camera.magicfilter.TextureRotationUtil;
import com.cs.camera.magicfilter.filter.FilterCameraInput;
import com.cs.camera.magicfilter.filter.FilterFactory;
import com.cs.camera.magicfilter.filter.FilterType;
import com.cs.camera.magicfilter.filter.GPUImageFilter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.HashMap;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGL11;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by THINK on 2017/7/17.
 *
 * frankie, 2017.07.20, note:
 * Still have problem on concurrent GL thread use the same texture !!! how to fix ...
 *
 */

public class MyTextureView2 extends TextureView implements MyTextureViewController,
        TextureView.SurfaceTextureListener {
    private String TAG = "MyTextureView2";

    private float mAspectRatio = 16.0f/9.0f; // 4.0f/3.0f; // 16.0f/9.0f;

    private MainHandler mMainHandler = null;
    private OnDrawCallback mOnDrawCallback = null;
    private Object mOnDrawCallback_lock = new Object();

    private Object mRendererLocker = new Object();
    private HandlerThread mRendererThread = null;
    private TextureRendererHandler mTextureRendererHandler = null;
    private boolean mCreateThreadRequest = false;
    private EGLContext mSharedEGLContext = null;
    private boolean mTextureReady = false;
    private SurfaceTexture mSurfacetexture = null;
    private int mTextureWidth = 0;
    private int mTextureHeight = 0;
    private FilterType mFilterType = FilterType.NONE;
    private CameraPreviewInfo__ mCameraPreviewInfo = null;
    //
    private boolean mQuitFlag = false;
    private Object mQuitLock = new Object();

    private boolean mRenderFrameFlag = false;
    private Object mRenderFrameLock = new Object();

    private boolean mRenderInitFlag = false;
    private Object mRenderInitLock = new Object();

    private boolean mRenderDestroyFlag = false;
    private Object mRenderDestroyLock = new Object();

    private static final int RENDER_MSG_STARTUP = 0;
    private static final int RENDER_MSG_TEXTURE_SIZE_CHANGED = 10;
    private static final int RENDER_MSG_INIT = 20;
    private static final int RENDER_MSG_FRAME_AVAIABLE = 30;
    private static final int RENDER_MSG_QUIT = 1000;

    private static final String getRenderMsgWhatStr(int what) {
        if(RENDER_MSG_STARTUP == what) { return "RENDER_MSG_STARTUP"; }
        else if(RENDER_MSG_TEXTURE_SIZE_CHANGED == what) { return "RENDER_MSG_TEXTURE_SIZE_CHANGED"; }
        else if(RENDER_MSG_INIT == what) { return "RENDER_MSG_INIT"; }
        else if(RENDER_MSG_FRAME_AVAIABLE == what) { return "RENDER_MSG_FRAME_AVAIABLE"; }
        else if(RENDER_MSG_QUIT == what) { return "RENDER_MSG_QUIT"; }
        else { return "*** UNKNOWN ***"; }
    }


    private static class InitParam {
        EGLContext shareContext;
        SurfaceTexture surfaceTexture;
        public InitParam(EGLContext c, SurfaceTexture s) {shareContext=c;surfaceTexture=s;}
    }
    private static class CameraPreviewInfo__ {
        public int width;
        public int height;
        public int orientation;
        public boolean is_front;
        public CameraPreviewInfo__(int w, int h, int o, boolean is_front__) {
            width = w; height = h; orientation = o; is_front = is_front__;
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////
    private final static boolean LOG_EGL = true;
    private final static boolean LOG_THREADS = true;

    private EGL10 mEgl;
    private EGLDisplay mEglDisplay = EGL10.EGL_NO_DISPLAY;
    private EGLContext mEglContext = EGL10.EGL_NO_CONTEXT;
    private EGLSurface mEglSurface = EGL10.EGL_NO_SURFACE;
    private EGLConfig mEglConfig;

    private int mEGLContextClientVersion;
    private EGLConfigChooser mEGLConfigChooser;
    private EGLContextFactory mEGLContextFactory;
    private EGLWindowSurfaceFactory mEGLWindowSurfaceFactory;

    private static String getHex(int value) {
        return "0x" + Integer.toHexString(value);
    }

    public static String getErrorString(int error) {
        switch (error) {
            case EGL11.EGL_SUCCESS:
                return "EGL_SUCCESS";
            case EGL11.EGL_NOT_INITIALIZED:
                return "EGL_NOT_INITIALIZED";
            case EGL11.EGL_BAD_ACCESS:
                return "EGL_BAD_ACCESS";
            case EGL11.EGL_BAD_ALLOC:
                return "EGL_BAD_ALLOC";
            case EGL11.EGL_BAD_ATTRIBUTE:
                return "EGL_BAD_ATTRIBUTE";
            case EGL11.EGL_BAD_CONFIG:
                return "EGL_BAD_CONFIG";
            case EGL11.EGL_BAD_CONTEXT:
                return "EGL_BAD_CONTEXT";
            case EGL11.EGL_BAD_CURRENT_SURFACE:
                return "EGL_BAD_CURRENT_SURFACE";
            case EGL11.EGL_BAD_DISPLAY:
                return "EGL_BAD_DISPLAY";
            case EGL11.EGL_BAD_MATCH:
                return "EGL_BAD_MATCH";
            case EGL11.EGL_BAD_NATIVE_PIXMAP:
                return "EGL_BAD_NATIVE_PIXMAP";
            case EGL11.EGL_BAD_NATIVE_WINDOW:
                return "EGL_BAD_NATIVE_WINDOW";
            case EGL11.EGL_BAD_PARAMETER:
                return "EGL_BAD_PARAMETER";
            case EGL11.EGL_BAD_SURFACE:
                return "EGL_BAD_SURFACE";
            case EGL11.EGL_CONTEXT_LOST:
                return "EGL_CONTEXT_LOST";
            default:
                return getHex(error);
        }
    }

    private void throwEglException(String function) {
        throwEglException(function, mEgl.eglGetError());
    }

    public static void throwEglException(String function, int error) {
        String message = formatEglError(function, error);
        if (LOG_THREADS) {
            Log.e("EglHelper", "throwEglException tid=" + Thread.currentThread().getId() + " "
                    + message);
        }
        throw new RuntimeException(message);
    }

    public static void logEglErrorAsWarning(String tag, String function, int error) {
        Log.w(tag, formatEglError(function, error));
    }

    public static String formatEglError(String function, int error) {
        return function + " failed: " + getErrorString(error);
    }

    public void setEGLContextClientVersion(int version) {
        mEGLContextClientVersion = version;
    }

    /**
     * An interface for customizing the eglCreateContext and eglDestroyContext calls.
     * <p>
     * This interface must be implemented by clients wishing to call
     */
    public interface EGLContextFactory {
        EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig eglConfig);
        EGLContext createContext_withShareContext(EGL10 egl, EGLDisplay display, EGLConfig eglConfig, EGLContext shared_context);

        void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context);
    }

    private class DefaultContextFactory implements EGLContextFactory {
        private int EGL_CONTEXT_CLIENT_VERSION = 0x3098;

        public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig config) {
            int[] attrib_list = {
                    EGL_CONTEXT_CLIENT_VERSION, mEGLContextClientVersion,
                    EGL10.EGL_NONE};

            return egl.eglCreateContext(display, config, EGL10.EGL_NO_CONTEXT, mEGLContextClientVersion != 0 ? attrib_list : null);
        }
        public EGLContext createContext_withShareContext(EGL10 egl, EGLDisplay display, EGLConfig config, EGLContext shared_context) {
            int[] attrib_list = {
                    EGL_CONTEXT_CLIENT_VERSION, mEGLContextClientVersion,
                    EGL10.EGL_NONE};

            return egl.eglCreateContext(display, config, shared_context, mEGLContextClientVersion != 0 ? attrib_list : null);
        }

        public void destroyContext(EGL10 egl, EGLDisplay display,
                                   EGLContext context) {
            if (!egl.eglDestroyContext(display, context)) {
                Log.e("DefaultContextFactory", "display:" + display + " context: " + context);
                if (LOG_THREADS) {
                    Log.i("DefaultContextFactory", "tid=" + Thread.currentThread().getId());
                }
                throwEglException("eglDestroyContex", egl.eglGetError());
            }
        }
    }

    /**
     * An interface for customizing the eglCreateWindowSurface and eglDestroySurface calls.
     * <p>
     * This interface must be implemented by clients wishing to call
     */
    public interface EGLWindowSurfaceFactory {
        /**
         * @return null if the surface cannot be constructed.
         */
        EGLSurface createWindowSurface(EGL10 egl, EGLDisplay display, EGLConfig config,
                                       Object nativeWindow);

        void destroySurface(EGL10 egl, EGLDisplay display, EGLSurface surface);
    }

    /*static*/ private class DefaultWindowSurfaceFactory implements EGLWindowSurfaceFactory {

        public EGLSurface createWindowSurface(EGL10 egl, EGLDisplay display,
                                              EGLConfig config, Object nativeWindow) {
            EGLSurface result = null;
            try {
                result = egl.eglCreateWindowSurface(display, config, nativeWindow, null);
            } catch (IllegalArgumentException e) {
                // This exception indicates that the surface flinger surface
                // is not valid. This can happen if the surface flinger surface has
                // been torn down, but the application has not yet been
                // notified via SurfaceHolder.Callback.surfaceDestroyed.
                // In theory the application should be notified first,
                // but in practice sometimes it is not. See b/4588890
                Log.e(TAG, "eglCreateWindowSurface", e);
            }
            return result;
        }

        public void destroySurface(EGL10 egl, EGLDisplay display,
                                   EGLSurface surface) {
            egl.eglDestroySurface(display, surface);
        }
    }

    /**
     * An interface for choosing an EGLConfig configuration from a list of
     * potential configurations.
     * <p>
     * This interface must be implemented by clients wishing to call
     */
    public interface EGLConfigChooser {
        /**
         * Choose a configuration from the list. Implementors typically
         * implement this method by calling
         * {@link EGL10#eglChooseConfig} and iterating through the results. Please consult the
         * EGL specification available from The Khronos Group to learn how to call eglChooseConfig.
         *
         * @param egl     the EGL10 for the current display.
         * @param display the current display.
         * @return the chosen configuration.
         */
        EGLConfig chooseConfig(EGL10 egl, EGLDisplay display);
    }

    private abstract class BaseConfigChooser
            implements EGLConfigChooser {
        public BaseConfigChooser(int[] configSpec) {
            mConfigSpec = filterConfigSpec(configSpec);
        }

        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
            int[] num_config = new int[1];
            if (!egl.eglChooseConfig(display, mConfigSpec, null, 0,
                    num_config)) {
                throw new IllegalArgumentException("eglChooseConfig failed");
            }

            int numConfigs = num_config[0];

            if (numConfigs <= 0) {
                throw new IllegalArgumentException(
                        "No configs match configSpec");
            }

            EGLConfig[] configs = new EGLConfig[numConfigs];
            if (!egl.eglChooseConfig(display, mConfigSpec, configs, numConfigs,
                    num_config)) {
                throw new IllegalArgumentException("eglChooseConfig#2 failed");
            }
            EGLConfig config = chooseConfig(egl, display, configs);
            if (config == null) {
                throw new IllegalArgumentException("No config chosen");
            }
            return config;
        }

        abstract EGLConfig chooseConfig(EGL10 egl, EGLDisplay display,
                                        EGLConfig[] configs);

        protected int[] mConfigSpec;

        private int[] filterConfigSpec(int[] configSpec) {
            if (mEGLContextClientVersion != 2 && mEGLContextClientVersion != 3) {
                return configSpec;
            }
            /* We know none of the subclasses define EGL_RENDERABLE_TYPE.
             * And we know the configSpec is well formed.
             */
            int len = configSpec.length;
            int[] newConfigSpec = new int[len + 2];
            System.arraycopy(configSpec, 0, newConfigSpec, 0, len - 1);
            newConfigSpec[len - 1] = EGL10.EGL_RENDERABLE_TYPE;
            if (mEGLContextClientVersion == 2) {
                newConfigSpec[len] = EGL14.EGL_OPENGL_ES2_BIT;  /* EGL_OPENGL_ES2_BIT */
            } else {
                newConfigSpec[len] = EGLExt.EGL_OPENGL_ES3_BIT_KHR; /* EGL_OPENGL_ES3_BIT_KHR */
            }
            newConfigSpec[len + 1] = EGL10.EGL_NONE;
            return newConfigSpec;
        }
    }

    /**
     * Choose a configuration with exactly the specified r,g,b,a sizes,
     * and at least the specified depth and stencil sizes.
     */
    private class ComponentSizeChooser extends BaseConfigChooser {
        public ComponentSizeChooser(int redSize, int greenSize, int blueSize,
                                    int alphaSize, int depthSize, int stencilSize) {
            super(new int[]{
                    EGL10.EGL_RED_SIZE, redSize,
                    EGL10.EGL_GREEN_SIZE, greenSize,
                    EGL10.EGL_BLUE_SIZE, blueSize,
                    EGL10.EGL_ALPHA_SIZE, alphaSize,
                    EGL10.EGL_DEPTH_SIZE, depthSize,
                    EGL10.EGL_STENCIL_SIZE, stencilSize,
                    EGL10.EGL_NONE});
            mValue = new int[1];
            mRedSize = redSize;
            mGreenSize = greenSize;
            mBlueSize = blueSize;
            mAlphaSize = alphaSize;
            mDepthSize = depthSize;
            mStencilSize = stencilSize;
        }

        @Override
        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display,
                                      EGLConfig[] configs) {
            for (EGLConfig config : configs) {
                int d = findConfigAttrib(egl, display, config,
                        EGL10.EGL_DEPTH_SIZE, 0);
                int s = findConfigAttrib(egl, display, config,
                        EGL10.EGL_STENCIL_SIZE, 0);
                if ((d >= mDepthSize) && (s >= mStencilSize)) {
                    int r = findConfigAttrib(egl, display, config,
                            EGL10.EGL_RED_SIZE, 0);
                    int g = findConfigAttrib(egl, display, config,
                            EGL10.EGL_GREEN_SIZE, 0);
                    int b = findConfigAttrib(egl, display, config,
                            EGL10.EGL_BLUE_SIZE, 0);
                    int a = findConfigAttrib(egl, display, config,
                            EGL10.EGL_ALPHA_SIZE, 0);
                    if ((r == mRedSize) && (g == mGreenSize)
                            && (b == mBlueSize) && (a == mAlphaSize)) {
                        return config;
                    }
                }
            }
            return null;
        }

        private int findConfigAttrib(EGL10 egl, EGLDisplay display,
                                     EGLConfig config, int attribute, int defaultValue) {

            if (egl.eglGetConfigAttrib(display, config, attribute, mValue)) {
                return mValue[0];
            }
            return defaultValue;
        }

        private int[] mValue;
        // Subclasses can adjust these values:
        protected int mRedSize;
        protected int mGreenSize;
        protected int mBlueSize;
        protected int mAlphaSize;
        protected int mDepthSize;
        protected int mStencilSize;
    }

    /**
     * This class will choose a RGB_888 surface with
     * or without a depth buffer.
     */
    private class SimpleEGLConfigChooser extends ComponentSizeChooser {
        public SimpleEGLConfigChooser(boolean withDepthBuffer) {
            super(8, 8, 8, 0, withDepthBuffer ? 16 : 0, 0);
        }
    }

    /**
     * Initialize EGL for a given configuration spec.
     */
    public void start(EGLContext shared_context) {
        if (LOG_EGL) {
            Log.w("EglHelper", "start() tid=" + Thread.currentThread().getId());
        }
            /*
             * Get an EGL instance
             */
        mEgl = (EGL10) EGLContext.getEGL();

            /*
             * Get to the default display.
             */
        mEglDisplay = mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);

        if (mEglDisplay == EGL10.EGL_NO_DISPLAY) {
            throw new RuntimeException("eglGetDisplay failed");
        }

            /*
             * We can now initialize EGL for that display
             */
        int[] version = new int[2];
        if (!mEgl.eglInitialize(mEglDisplay, version)) {
            throw new RuntimeException("eglInitialize failed");
        }
        Log.v(TAG, "EGL version:" + version[0] + "." + version[1]);
//        GLSurfaceView view = mGLSurfaceViewWeakRef.get();
//        if (view == null) {
//            mEglConfig = null;
//            mEglContext = null;
//        } else {
        mEglConfig = mEGLConfigChooser.chooseConfig(mEgl, mEglDisplay);

                /*
                * Create an EGL context. We want to do this as rarely as we can, because an
                * EGL context is a somewhat heavy object.
                */
        if(shared_context == null) {
            mEglContext = mEGLContextFactory.createContext(mEgl, mEglDisplay, mEglConfig);
        } else if(shared_context != null) {
            mEglContext = mEGLContextFactory.createContext_withShareContext(mEgl, mEglDisplay, mEglConfig, shared_context);
        }
//        }
        if (mEglContext == null || mEglContext == EGL10.EGL_NO_CONTEXT) {
            mEglContext = null;
            throwEglException("createContext");
        }
        if (LOG_EGL) {
            Log.w("EglHelper", "createContext " + mEglContext + " tid=" + Thread.currentThread().getId());
        }

        mEglSurface = null;
    }

    /**
     * Create an egl surface for the current SurfaceHolder surface. If a surface
     * already exists, destroy it before creating the new surface.
     *
     * @return true if the surface was created successfully.
     */
    public boolean createSurface(SurfaceTexture surfaceTexture) {
        if (LOG_EGL) {
            Log.w("EglHelper", "createSurface()  tid=" + Thread.currentThread().getId());
        }
            /*
             * Check preconditions.
             */
        if (mEgl == null) {
            throw new RuntimeException("egl not initialized");
        }
        if (mEglDisplay == null) {
            throw new RuntimeException("eglDisplay not initialized");
        }
        if (mEglConfig == null) {
            throw new RuntimeException("mEglConfig not initialized");
        }

            /*
             *  The window size has changed, so we need to create a new
             *  surface.
             */
        destroySurfaceImp();

            /*
             * Create an EGL surface we can render into.
             */
//        GLSurfaceView view = mGLSurfaceViewWeakRef.get();
//        if (view != null) {
        mEglSurface = mEGLWindowSurfaceFactory.createWindowSurface(mEgl, mEglDisplay, mEglConfig, surfaceTexture);
//        } else {
//            mEglSurface = null;
//        }

        if (mEglSurface == null || mEglSurface == EGL10.EGL_NO_SURFACE) {
            int error = mEgl.eglGetError();
            if (error == EGL10.EGL_BAD_NATIVE_WINDOW) {
                Log.e("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
            }
            return false;
        }

            /*
             * Before we can issue GL commands, we need to make sure
             * the context is current and bound to a surface.
             */
        if (!mEgl.eglMakeCurrent(mEglDisplay, mEglSurface, mEglSurface, mEglContext)) {
                /*
                 * Could not make the context current, probably because the underlying
                 * SurfaceView surface has been destroyed.
                 */
            logEglErrorAsWarning("EGLHelper", "eglMakeCurrent", mEgl.eglGetError());
            return false;
        }

        return true;
    }

    /**
     * Display the current render surface.
     *
     * @return the EGL error code from eglSwapBuffers.
     */
    public int swap__() {
        if(mEglSurface != null && mEglContext != null && mEglDisplay != null && mEgl != null) {
            if (!mEgl.eglSwapBuffers(mEglDisplay, mEglSurface)) {
                return mEgl.eglGetError();
            }
        }
        return EGL10.EGL_SUCCESS;
    }

    public void destroySurface() {
        if (LOG_EGL) {
            Log.w("EglHelper", "destroySurface()  tid=" + Thread.currentThread().getId());
        }
        destroySurfaceImp();
    }

    private void destroySurfaceImp() {
        if (mEglSurface != null && mEglSurface != EGL10.EGL_NO_SURFACE) {
            mEgl.eglMakeCurrent(mEglDisplay, EGL10.EGL_NO_SURFACE,
                    EGL10.EGL_NO_SURFACE,
                    EGL10.EGL_NO_CONTEXT);
//            GLSurfaceView view = mGLSurfaceViewWeakRef.get();
//            if (view != null) {
            mEGLWindowSurfaceFactory.destroySurface(mEgl, mEglDisplay, mEglSurface);
//            }
            mEglSurface = null;
        }
    }

    public void finish() {
        if (LOG_EGL) {
            Log.w("EglHelper", "finish() tid=" + Thread.currentThread().getId());
        }
        if (mEglContext != null) {
//            GLSurfaceView view = mGLSurfaceViewWeakRef.get();
//            if (view != null) {
            mEGLContextFactory.destroyContext(mEgl, mEglDisplay, mEglContext);
//            }
            mEglContext = null;
        }
        if (mEglDisplay != null) {
            mEgl.eglTerminate(mEglDisplay);
            mEglDisplay = null;
        }

        mEgl = null; // frankie,
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    public enum  ScaleType{
        CENTER_INSIDE,
        CENTER_CROP,
        FIT_XY;
    }
    private class TextureRendererHandler extends Handler {

        SurfaceTexture rSurfaceTexture = null;
        EGLContext rSharedEGLContext = null;
        int rWidth = 0;
        int rHeight = 0;
        private FilterCameraInput rCameraInputFilter;
        protected GPUImageFilter rFilter;
        private FilterType rFilterType = FilterType.NONE;
        protected FloatBuffer gLCubeBuffer;
        protected FloatBuffer gLTextureBuffer;
        private CameraPreviewInfo__ rCameraPreviewInfo;
        private int rPreviewWidth = 0;
        private int rPreviewHeight = 0;
        private int rPreviewOrientation = 0;
        private boolean rPreviewIsFront = false;

        // gl create / destroy

        public TextureRendererHandler(Looper looper, FilterType filterType, CameraPreviewInfo__ cameraInfo) {
            super(looper);

            rFilterType = filterType;

            rCameraPreviewInfo = cameraInfo;
            rPreviewOrientation = cameraInfo.orientation;
            if (rPreviewOrientation == 90 || rPreviewOrientation == 270) {
                rPreviewHeight = cameraInfo.width;
                rPreviewWidth = cameraInfo.height;
            } else {
                rPreviewWidth = cameraInfo.width;
                rPreviewHeight = cameraInfo.height;
            }
            rPreviewIsFront = cameraInfo.is_front;

            Log.v(TAG, "Camera preview:" + rPreviewWidth + "*" + rPreviewHeight + " o:" + rPreviewOrientation + " front:" + rPreviewIsFront);

            gLCubeBuffer = ByteBuffer.allocateDirect(TextureRotationUtil.CUBE.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            gLCubeBuffer.put(TextureRotationUtil.CUBE).position(0);

            gLTextureBuffer = ByteBuffer.allocateDirect(TextureRotationUtil.TEXTURE_NO_ROTATION.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            gLTextureBuffer.put(TextureRotationUtil.TEXTURE_NO_ROTATION).position(0);
        }

        private void onInitFilter(FilterType type) {
            if (rFilter != null) {
                rFilter.destroy();
            }
            rFilter = FilterFactory.getFilter(type);
            if (rFilter != null) {
                rFilter.init();
            }
            Log.v(TAG, "onInitFilter: " + type);
        }
        protected void onFilterChanged() {
            if (rFilter != null) {
                rFilter.onDisplaySizeChanged(rWidth, rHeight);
                rFilter.onInputSizeChanged(rPreviewWidth, rPreviewHeight);
            }
            if (rFilter != null
                    ) {
                rCameraInputFilter.initCameraFrameBuffer(rPreviewWidth, rPreviewHeight);
            } else {
                rCameraInputFilter.destroyFramebuffers();
            }
        }


        protected ScaleType scaleType = ScaleType.FIT_XY;
        protected void adjustSize(int rotation, boolean flipHorizontal, boolean flipVertical){
            float[] textureCords = TextureRotationUtil.getRotation(Rotation.fromInt(rotation), flipHorizontal, flipVertical);
            float[] cube = TextureRotationUtil.CUBE;

            float ratio1 = (float)rWidth / rPreviewWidth;
            float ratio2 = (float)rHeight / rPreviewHeight;
            float ratioMax = Math.max(ratio1, ratio2);
            int imageWidthNew = Math.round(rPreviewWidth * ratioMax);
            int imageHeightNew = Math.round(rPreviewHeight * ratioMax);
            float ratioWidth = imageWidthNew / (float)rWidth;
            float ratioHeight = imageHeightNew / (float)rHeight;

            if(scaleType == ScaleType.CENTER_INSIDE){
                cube = new float[]{
                        TextureRotationUtil.CUBE[0] / ratioHeight, TextureRotationUtil.CUBE[1] / ratioWidth,
                        TextureRotationUtil.CUBE[2] / ratioHeight, TextureRotationUtil.CUBE[3] / ratioWidth,
                        TextureRotationUtil.CUBE[4] / ratioHeight, TextureRotationUtil.CUBE[5] / ratioWidth,
                        TextureRotationUtil.CUBE[6] / ratioHeight, TextureRotationUtil.CUBE[7] / ratioWidth,
                };
            }else if(scaleType == ScaleType.FIT_XY){

            }else if(scaleType == ScaleType.CENTER_CROP){
                float distHorizontal = (1 - 1 / ratioWidth) / 2;
                float distVertical = (1 - 1 / ratioHeight) / 2;
                textureCords = new float[]{
                        addDistance(textureCords[0], distVertical), addDistance(textureCords[1], distHorizontal),
                        addDistance(textureCords[2], distVertical), addDistance(textureCords[3], distHorizontal),
                        addDistance(textureCords[4], distVertical), addDistance(textureCords[5], distHorizontal),
                        addDistance(textureCords[6], distVertical), addDistance(textureCords[7], distHorizontal),
                };
            }
            gLCubeBuffer.clear();
            gLCubeBuffer.put(cube).position(0);

            gLTextureBuffer.clear();
            gLTextureBuffer.put(textureCords).position(0);
        }
        private float addDistance(float coordinate, float distance) {
            return coordinate == 0.0f ? distance : 1 - distance;
        }
        @Override
        public void handleMessage(Message message) {
            if(RENDER_MSG_FRAME_AVAIABLE != message.what)
            {
                Log.v(TAG, "handleMessage:" + getRenderMsgWhatStr(message.what));
            }

            if(RENDER_MSG_STARTUP == message.what) {
            }
            else if(RENDER_MSG_TEXTURE_SIZE_CHANGED == message.what) {

            }
            else if(RENDER_MSG_INIT == message.what) {
                // craete gl
                InitParam initParam = (InitParam)message.obj;
                EGLContext sharedEGLContext = initParam.shareContext;
                SurfaceTexture surfaceTexture = initParam.surfaceTexture;
                rSharedEGLContext = sharedEGLContext;
                rSurfaceTexture = surfaceTexture;
                rWidth = message.arg1;
                rHeight = message.arg2;

                if (mEGLConfigChooser == null) {
                    mEGLConfigChooser = new SimpleEGLConfigChooser(true);
                }
                if (mEGLContextFactory == null) {
                    mEGLContextFactory = new DefaultContextFactory();
                }
                if (mEGLWindowSurfaceFactory == null) {
                    mEGLWindowSurfaceFactory = new DefaultWindowSurfaceFactory();
                }
                setEGLContextClientVersion(2);

                start(rSharedEGLContext);
                createSurface(rSurfaceTexture);

                GLES20.glDisable(GL10.GL_DITHER);
                GLES20.glDisable(GL10.GL_DEPTH_TEST);
                GLES20.glEnable(GL10.GL_CULL_FACE);

                if(rCameraInputFilter == null) {
                    rCameraInputFilter = new FilterCameraInput();
                }
                rCameraInputFilter.init(); // must recompile shader

                rCameraInputFilter.onInputSizeChanged(rPreviewWidth, rPreviewHeight);

                //onInitFilter(FilterType.NONE);
                onInitFilter(rFilterType);      // init filter

                //adjustSize(0, rPreviewIsFront, true);
                adjustSize(0, false, true);

                onFilterChanged();  // set filter input/output size

                rCameraInputFilter.onInputSizeChanged(rPreviewWidth, rPreviewHeight);
                rCameraInputFilter.onDisplaySizeChanged(rWidth, rHeight);

            }
            else if(RENDER_MSG_FRAME_AVAIABLE == message.what) {
                synchronized (mRendererLocker) {
                    if (mCreateThreadRequest == false || mTextureReady == false) {
                        return ;
                    }
                }
                GLES20.glViewport(0, 0, rWidth, rHeight);
                __glClearColor();

                float[] matrix = (float[]) message.obj;
                int textureId = message.arg1;

                if(textureId > 0 && rCameraInputFilter != null) {

//                    rCameraInputFilter.setTextureTransformMatrix(matrix);
//                    int id = textureId;
//                    if(rFilter != null) {
//                        id = rCameraInputFilter.onDrawToTexture(id);  // id size is (imageWidth, imageHeight) ; //
//                        rFilter.onDrawFrame(id, gLCubeBuffer, gLTextureBuffer);
//                    } else {
//                        rCameraInputFilter.onDrawFrame(id, gLCubeBuffer, gLTextureBuffer);
//                    }

                }

                swap__();

                synchronized (mRenderFrameLock) {
                    mRenderFrameFlag = true;
                    mRenderFrameLock.notifyAll();
                }
//                if(mMainHandler != null) {
//                    mMainHandler.sendEmptyMessageDelayed(0, 0);
//                }
                OnDrawCallback callback_ = null;
                synchronized (mOnDrawCallback_lock) {
                    callback_ = mOnDrawCallback;
                }
                if(callback_ != null) {
                    callback_.onDrawFrame();
                }
            }
            else if(RENDER_MSG_QUIT == message.what) {

                if(rCameraInputFilter != null) {
                    rCameraInputFilter.destroyFramebuffers();
                    rCameraInputFilter.destroy();
                    rCameraInputFilter = null;
                }
                if (rFilter != null) {
                    rFilter.destroy();
                    rFilter = null;
                }

                // destroy gl
                destroySurface();
                finish();

                rSurfaceTexture = null;

                synchronized (mQuitLock) {
                    mQuitFlag = true;
                    mQuitLock.notifyAll();
                }
                this.getLooper().quitSafely();
            }
        }
    }

    private class MainHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            Log.v(TAG, "MainHandler.handleMessage:" + message.what);
            if(message.what == 0) {
                if(mOnDrawCallback != null) {
                    mOnDrawCallback.onDrawFrame();
                }
            }
        }
    }


    ////////////// test draw start
    private static class ColorRGBA {
        public float r,g,b,a;
        public ColorRGBA(float r_, float g_, float b_, float a_) { r=r_; g=g_; b=b_; a=a_;}
    }
    private HashMap<Integer, ColorRGBA> mClearClorMap = new HashMap<Integer, ColorRGBA>();
    ////////////// test draw end

    private void __glClearColor() {
        int id = this.getId();
        ColorRGBA clearColor = mClearClorMap.get(id);
        GLES20.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
        //GLES20.glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
        //GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
    }

    // constructor
    public MyTextureView2(Context context) {
        super(context);
        onInstantiate();
    }
    public MyTextureView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInstantiate();
    }
    public MyTextureView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInstantiate();
    }
    public MyTextureView2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        onInstantiate();
    }
    private void onInstantiate() {
        TAG = TAG + "_" + getIndex_();
        this.setSurfaceTextureListener(this);
        mMainHandler = new MainHandler();

        int id = this.getId();
        ColorRGBA color_ = new ColorRGBA(1.0f, 0.0f, 0.0f, 1.0f);
        if(id == R.id.color_effect_1) {color_=new ColorRGBA(1.0f, 0.0f, 0.0f, 1.0f);}
        else if(id == R.id.color_effect_2) {color_=new ColorRGBA(0.0f, 1.0f, 0.0f, 1.0f);}
        else if(id == R.id.color_effect_3) {color_=new ColorRGBA(0.0f, 0.0f, 1.0f, 1.0f);}
        else if(id == R.id.color_effect_4) {color_=new ColorRGBA(0.0f, 1.0f, 0.0f, 1.0f);}
        else if(id == R.id.color_effect_5) {color_=new ColorRGBA(0.0f, 0.0f, 1.0f, 1.0f);}
        else if(id == R.id.color_effect_6) {color_=new ColorRGBA(1.0f, 0.0f, 0.0f, 1.0f);}
        else if(id == R.id.color_effect_7) {color_=new ColorRGBA(0.0f, 0.0f, 1.0f, 1.0f);}
        else if(id == R.id.color_effect_8) {color_=new ColorRGBA(1.0f, 0.0f, 0.0f, 1.0f);}
        else if(id == R.id.color_effect_9) {color_=new ColorRGBA(0.0f, 1.0f, 0.0f, 1.0f);}
        mClearClorMap.put(id, color_);

    }
    private int getIndex_() {
        int id = this.getId();
        if(id == R.id.color_effect_1) {return 1;}
        else if(id == R.id.color_effect_2) {return 2;}
        else if(id == R.id.color_effect_3) {return 3;}
        else if(id == R.id.color_effect_4) {return 4;}
        else if(id == R.id.color_effect_5) {return 5;}
        else if(id == R.id.color_effect_6) {return 6;}
        else if(id == R.id.color_effect_7) {return 7;}
        else if(id == R.id.color_effect_8) {return 8;}
        else if(id == R.id.color_effect_9) {return 9;}
        return -1;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
//        Log.v(TAG, "onMeasure, width/heightMeasureSpec:" + width + " x " + height + " mAspectRatio:" + mAspectRatio
//                //+ " /"  + width + "x" + (int)(width * mAspectRatio)
//        );
        setMeasuredDimension(width, (int)(width * mAspectRatio));
    }

    //////////////////////////////////// TextureView.SurfaceTextureListener impl
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        synchronized (mRendererLocker) {
            Log.v(TAG, "onSurfaceTextureAvailable: " + width + " * " + height + " @tid:" + android.os.Process.myTid());
            if (mRendererThread == null) {
                mRendererThread = new HandlerThread("#renderer_" + getIndex_());
                mRendererThread.start();
                mTextureRendererHandler = new TextureRendererHandler(mRendererThread.getLooper(), mFilterType, mCameraPreviewInfo);
                mTextureRendererHandler.sendEmptyMessageDelayed(RENDER_MSG_STARTUP, 0);
            }
            mSurfacetexture = surface;
            mTextureReady = true;
            mTextureWidth = width;
            mTextureHeight = height;
            Log.v(TAG, "  mCreateThreadRequest:" + mCreateThreadRequest);
            if (mCreateThreadRequest) {
                if (mTextureRendererHandler != null) {
//                    synchronized (mRenderInitLock) {
//                        mRenderInitFlag = false;
//                    }
                    Message m_ = mTextureRendererHandler.obtainMessage(RENDER_MSG_INIT, width, height, new InitParam(mSharedEGLContext, mSurfacetexture));
                    mTextureRendererHandler.sendMessageDelayed(m_, 0);
//                    synchronized (mRenderInitLock) {
//                        //mRenderFrameFlag = false;
//                        long wait_time = System.currentTimeMillis() + 2000;
//                        try {
//                            while(wait_time < System.currentTimeMillis() && mRenderInitFlag == false) {
//                                mRenderInitLock.wait(100);
//                            }
//                        } catch(InterruptedException ex) {}
//                    }
                }
            }
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        synchronized (mRendererLocker) {
            Log.v(TAG, "onSurfaceTextureSizeChanged: " + width + " * " + height);
            if (mTextureWidth != width || mTextureHeight != height) {
                mTextureWidth = width;
                mTextureHeight = height;
                Log.v(TAG, "  mCreateThreadRequest:" + mCreateThreadRequest);
                if (mCreateThreadRequest) {
                    if (mTextureRendererHandler != null) {
                        Message m_ = mTextureRendererHandler.obtainMessage(RENDER_MSG_TEXTURE_SIZE_CHANGED, width, height, null);
                        mTextureRendererHandler.sendMessageDelayed(m_, 0);
                    }
                }
            }
        }
    }

    private void cleanupRenderer() {
        synchronized (mRendererLocker) {
            if (mTextureRendererHandler != null) {
                mTextureRendererHandler.removeMessages(RENDER_MSG_STARTUP);
                mTextureRendererHandler.removeMessages(RENDER_MSG_INIT);
                mTextureRendererHandler.removeMessages(RENDER_MSG_FRAME_AVAIABLE);
                mTextureRendererHandler.removeMessages(RENDER_MSG_QUIT);
                synchronized (mQuitLock) {
                    mQuitFlag = false;
                }
                long quitTimeProfilling = System.currentTimeMillis();
                mTextureRendererHandler.sendEmptyMessageDelayed(RENDER_MSG_QUIT, 0);
                synchronized (mQuitLock) {
                    long timeout = System.currentTimeMillis() + 3 * 1000;
                    while (!mQuitFlag && timeout > System.currentTimeMillis()) {
                        try {
                            mQuitLock.wait(100);
                        } catch (InterruptedException ex) {
                        }
                    }
                }
                mTextureRendererHandler = null;
                mRendererThread = null;
                Log.w(TAG, "  quitTimeProfilling:" + (System.currentTimeMillis() - quitTimeProfilling));    // *** always timeout here !!!
            }
        }
    }
    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        synchronized (mRendererLocker) {
            Log.v(TAG, "onSurfaceTextureDestroyed," + " @tid:" + android.os.Process.myTid());
            cleanupRenderer();

            mTextureReady = false;
            mSurfacetexture = null;
            return false;
        }
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        //Log.v(TAG, "onSurfaceTextureUpdated");
    }

    /////////////////////////////////////////// MyTextureViewController impl
    @Override
    public void setFilterType(FilterType type) {
        mFilterType = type;
    }
    @Override
    public FilterType getFilterType() {
        return mFilterType;
    }
    @Override
    public void setOnDrawCallback(OnDrawCallback callback) {
        Log.v(TAG, "setOnDrawCallback hashCode:" + callback.hashCode());
        synchronized (mOnDrawCallback_lock) {
            mOnDrawCallback = callback;
        }
    }
    @Override
    public void setCameraPreviewInfo(int width, int height, int orientation, boolean isFront) {
        Log.v(TAG, "setCameraPreviewInfo:" + width + " * " + height + " o:" + orientation + " front:" + isFront);
        mCameraPreviewInfo = new CameraPreviewInfo__(width, height, orientation, isFront);
    }
    @Override
    public void setmAspectRatio_ext(float ratio) {
        synchronized (mRendererLocker) {
            if (ratio != mAspectRatio) {
                Log.v(TAG, "setmAspectRatio_ext:" + ratio);
                mAspectRatio = ratio;
                this.requestLayout();
            }
        }
    }
    @Override
    public void createThread__() {
        synchronized (mRendererLocker) {
            Log.v(TAG, "createThread__");
            mCreateThreadRequest = true;
            mSharedEGLContext = null;
            Log.v(TAG, "  mTextureReady:" + mTextureReady);
            if (mTextureReady) {
                if (mTextureRendererHandler != null) {
//                    synchronized (mRenderInitLock) {
//                        mRenderInitFlag = false;
//                    }
                    Message m_ = mTextureRendererHandler.obtainMessage(RENDER_MSG_INIT, mTextureWidth, mTextureHeight, new InitParam(mSharedEGLContext, mSurfacetexture));
                    mTextureRendererHandler.sendMessageDelayed(m_, 0);
//                    synchronized (mRenderInitLock) {
//                        //mRenderFrameFlag = false;
//                        long wait_time = System.currentTimeMillis() + 2000;
//                        try {
//                            while(wait_time < System.currentTimeMillis() && mRenderInitFlag == false) {
//                                mRenderInitLock.wait(100);
//                            }
//                        } catch(InterruptedException ex) {}
//                    }
                }
            }
        }
    }
    @Override
    public void createThread_withContext(EGLContext eglContext) {
        synchronized (mRendererLocker) {
            Log.v(TAG, "createThread_withContext");
            mCreateThreadRequest = true;
            mSharedEGLContext = eglContext;
            Log.v(TAG, "  mTextureReady:" + mTextureReady);
            if (mTextureReady) {
                if (mTextureRendererHandler != null) {
//                    synchronized (mRenderInitLock) {
//                        mRenderInitFlag = false;
//                    }
                    Message m_ = mTextureRendererHandler.obtainMessage(RENDER_MSG_INIT, mTextureWidth, mTextureHeight, new InitParam(mSharedEGLContext, mSurfacetexture));
                    mTextureRendererHandler.sendMessageDelayed(m_, 0);
//                    synchronized (mRenderInitLock) {
//                        //mRenderFrameFlag = false;
//                        long wait_time = System.currentTimeMillis() + 2000;
//                        try {
//                            while(wait_time < System.currentTimeMillis() && mRenderInitFlag == false) {
//                                mRenderInitLock.wait(100);
//                            }
//                        } catch(InterruptedException ex) {}
//                    }
                }
            }
        }
    }
    @Override
    public void onSurfaceTextureAvaiable__(int textureId, SurfaceTexture st, float[] matrix) { // ### this callback is excuted on the main renderer thread , so
        synchronized (mRendererLocker) {
            if (mCreateThreadRequest && mTextureReady && mTextureRendererHandler != null) {
                //Log.v(TAG, "onSurfaceTextureAvaiable__, textureId=" + textureId + " @tid:" + android.os.Process.myTid());
                synchronized (mRenderFrameLock) {
                    mRenderFrameFlag = false;
                }
                Message m_ = mTextureRendererHandler.obtainMessage(RENDER_MSG_FRAME_AVAIABLE, textureId, -1, matrix);
                mTextureRendererHandler.sendMessageDelayed(m_, 0);
                synchronized (mRenderFrameLock) {
                    //mRenderFrameFlag = false;
                    long wait_time = System.currentTimeMillis() + 2000;
                    try {
                        while(wait_time < System.currentTimeMillis() && mRenderFrameFlag == false) {
                            mRenderFrameLock.wait(100);
                        }
                    } catch(InterruptedException ex) {}
                }
            }
        }
    }
	@Override
	public void onResume() {
		Log.v(TAG, "onPause");
	}
    @Override
    public void onPause() {
        Log.v(TAG, "onPause");
		
    }
	@Override
	public void requestExitAndWait() {
		Log.v(TAG, "requestExitAndWait");
	}

    @Override
    public void destroyRendererThread() {
        synchronized (mRendererLocker) {
            Log.v(TAG, "destroyRendererThread");
            cleanupRenderer();
        }
    }


}
