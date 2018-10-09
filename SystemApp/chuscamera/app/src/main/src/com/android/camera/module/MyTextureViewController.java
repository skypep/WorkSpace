package com.android.camera.module;

import android.graphics.SurfaceTexture;
import android.view.View;

import com.cs.camera.magicfilter.filter.FilterType;

import javax.microedition.khronos.egl.EGLContext;

/**
 * Created by THINK on 2017/7/17.
 */

public interface MyTextureViewController {

    public void setOnClickListener(View.OnClickListener l);     // this is impl in super View

    public void setFilterType(FilterType type);
    public FilterType getFilterType();

    public interface OnDrawCallback {
        public void onDrawFrame();
    }
    public void setOnDrawCallback(OnDrawCallback callback);

    public void setCameraPreviewInfo(int width, int height, int orientation, boolean isFront);
    public void setmAspectRatio_ext(float ratio);
    public void createThread__();
    public void createThread_withContext(EGLContext eglContext);
    public void onSurfaceTextureAvaiable__(final int textureId, final SurfaceTexture st, final float[] matrix);

    public void onPause();
	public void onResume();
	public void requestExitAndWait();
    public void destroyRendererThread();
}
