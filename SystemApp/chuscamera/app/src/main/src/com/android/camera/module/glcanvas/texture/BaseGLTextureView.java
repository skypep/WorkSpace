

package com.android.camera.module.glcanvas.texture;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.TextureView;

import com.android.camera.module.glcanvas.ICanvasGL;
import com.android.camera.module.glcanvas.texture.gles.EglContextWrapper;
import com.android.camera.module.glcanvas.texture.gles.GLThread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chilling on 2016/10/31.
 * Can be used in ScrollView or ListView.
 * Can make it not opaque by setOpaque(false).
 */
public abstract class BaseGLTextureView extends TextureView implements TextureView.SurfaceTextureListener {

    protected GLThread mGLThread;
    protected GLThread.Builder glThreadBuilder;
    private List<Runnable> cacheEvents = new ArrayList<>();
    private SurfaceTextureListener surfaceTextureListener;
    private GLThread.OnCreateGLContextListener onCreateGLContextListener;

    private boolean hasCreateGLThreadCalledOnce = false;
    private boolean surfaceAvailable = false;
    private GLViewRenderer mRenderer;

    public BaseGLTextureView(Context context) {
        super(context);
        init();
    }

    public BaseGLTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseGLTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

	public int mId___;
	public void set_id(int id) {
		mId___ = id;
	}
	//public void get_id() {
	//	return mId;
	//}
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //Loggers.d("BaseGLTextureView", "onSizeChanged: ");
        super.onSizeChanged(w, h, oldw, oldh);
        if (mGLThread != null) {
            mGLThread.onWindowResize(w, h);
        }
    }

    public void onPause() {
        if (mGLThread != null) {
            mGLThread.onPause();
        }
    }

    public void onResume() {
        if (mGLThread != null) {
            mGLThread.onResume();
        }
    }

    public void queueEvent(Runnable r) {
        if (mGLThread == null) {
            cacheEvents.add(r);
            return;
        }
        mGLThread.queueEvent(r);
    }

    public void requestRender() {
        if (mGLThread != null) {
            mGLThread.requestRender();
        }
    }

    public void requestRenderAndWait() {
        if (mGLThread != null) {
            mGLThread.requestRenderAndWait();
        }
    }
	public void requestExitAndWait() { // frankie, add
		if (mGLThread != null) {
			mGLThread.requestExitAndWait();
		}
	}

    @Override
    protected void onDetachedFromWindow() {
        //Loggers.d("BaseGLTextureView", "onDetachedFromWindow: ");
        if (mGLThread != null) {
            mGLThread.requestExitAndWait();
        }
        super.onDetachedFromWindow();
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if (mGLThread != null) {
                // GLThread may still be running if this view was never
                // attached to a window.
                mGLThread.requestExitAndWait();
            }
        } finally {
            super.finalize();
        }
    }

    protected void init() {
        super.setSurfaceTextureListener(this);
    }

    /**
     *
     * @return If the context is not created, then EGL10.EGL_NO_CONTEXT will be returned.
     */
    @Nullable
    public EglContextWrapper getCurrentEglContext() {
        return mGLThread == null ? null : mGLThread.getEglContext();
    }

    public void setOnCreateGLContextListener(GLThread.OnCreateGLContextListener onCreateGLContextListener) {
        this.onCreateGLContextListener = onCreateGLContextListener;
    }

    public void setSurfaceTextureListener(SurfaceTextureListener surfaceTextureListener) {
        this.surfaceTextureListener = surfaceTextureListener;
    }

    protected int getRenderMode() {
        return GLThread.RENDERMODE_WHEN_DIRTY;
    }


    //protected abstract void onGLDraw(ICanvasGL canvas);

    public void setRenderer(GLViewRenderer renderer) {
        this.mRenderer = renderer;
    }

    protected void createGLThread() {
        //Loggers.d("BaseGLTextureView", "createGLThread: ");
        hasCreateGLThreadCalledOnce = true;
        if (!surfaceAvailable) {
            return;
        }
        mGLThread = glThreadBuilder.createGLThread();
        mGLThread.setOnCreateGLContextListener(new GLThread.OnCreateGLContextListener() {
            @Override
            public void onCreate(final EglContextWrapper eglContext) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        if (onCreateGLContextListener != null) {
                            onCreateGLContextListener.onCreate(eglContext);
                        }
                    }
                });
            }
        });
        mGLThread.start();
        freshSurface(getWidth(), getHeight());
        for (Runnable cacheEvent : cacheEvents) {
            mGLThread.queueEvent(cacheEvent);
        }
        cacheEvents.clear();
    }

    private void freshSurface(int width, int height) {
        surfaceCreated();
        surfaceChanged(width, height);
        surfaceRedrawNeeded();
    }
	
    protected void surfaceCreated() {
        mGLThread.surfaceCreated();
    }

    protected void surfaceChanged(int w, int h) {
        mGLThread.onWindowResize(w, h);
    }

    protected void surfaceRedrawNeeded() {
        if (mGLThread != null) {
            mGLThread.requestRenderAndWait();
        }
    }

    protected void surfaceDestroyed() {
        // Surface will be destroyed when we return
        mGLThread.surfaceDestroyed();
        mGLThread.requestExitAndWait();
        hasCreateGLThreadCalledOnce = false;
        surfaceAvailable = false;
        mGLThread = null;
    }

	// ------------------------------------------------- TextureView.SurfaceTextureListener start
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        //Loggers.d("BaseGLTextureView", "onSurfaceTextureAvailable: ");
        surfaceAvailable = true;
        if(glThreadBuilder == null) { // frankie, add
            glThreadBuilder = new GLThread.Builder();
        }
        if (mGLThread == null) {
            glThreadBuilder.setRenderMode(getRenderMode())
                    .setSurface(surface)
                    .setRenderer(mRenderer);
            if (hasCreateGLThreadCalledOnce) {
                createGLThread();
            }

        } else {
            mGLThread.setSurface(surface);
            freshSurface(width, height);
        }
		
        if (surfaceTextureListener != null) {
            surfaceTextureListener.onSurfaceTextureAvailable(surface, width, height);
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        //Loggers.d("BaseGLTextureView", "onSurfaceTextureSizeChanged: ");
        surfaceChanged(width, height);
        surfaceRedrawNeeded();
		
        if (surfaceTextureListener != null) {
            surfaceTextureListener.onSurfaceTextureSizeChanged(surface, width, height);
        }
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        //Loggers.d("BaseGLTextureView", "onSurfaceTextureDestroyed: ");
        surfaceDestroyed();
		
        if (surfaceTextureListener != null) {
            surfaceTextureListener.onSurfaceTextureDestroyed(surface);
        }
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    	
        if (surfaceTextureListener != null) {
            surfaceTextureListener.onSurfaceTextureUpdated(surface);
        }
    }
	// ------------------------------------------------- TextureView.SurfaceTextureListener end
	
}
