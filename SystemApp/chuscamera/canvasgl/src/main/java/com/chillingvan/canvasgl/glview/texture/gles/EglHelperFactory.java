package com.chillingvan.canvasgl.glview.texture.gles;

import android.os.Build;
import android.util.Log;

/**
 * Created by Chilling on 2017/1/2.
 */

public class EglHelperFactory {
    final static String TAG = "EglHelperFactory";
    public static IEglHelper create(GLThread.EGLConfigChooser configChooser, GLThread.EGLContextFactory eglContextFactory
            , GLThread.EGLWindowSurfaceFactory eglWindowSurfaceFactory) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Log.v(TAG, "Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR");
            //return new EglHelperAPI17(configChooser, eglContextFactory, eglWindowSurfaceFactory); // frankie, remove
            return new EglHelper(configChooser, eglContextFactory, eglWindowSurfaceFactory);
        } else {
            Log.v(TAG, "Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR else !!!");
            return new EglHelper(configChooser, eglContextFactory, eglWindowSurfaceFactory);
        }
    }

}
