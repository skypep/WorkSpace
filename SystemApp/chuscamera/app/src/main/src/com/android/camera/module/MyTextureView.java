package com.android.camera.module;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;

import com.android.camera.module.glcanvas.ICanvasGL;
import com.android.camera.module.glcanvas.texture.BaseGLTextureView;
import com.android.camera.module.glcanvas.texture.GLViewRenderer;
import com.android.camera.module.glcanvas.texture.gles.EglContextWrapper;
import com.android.camera.module.glcanvas.texture.gles.GLThread;
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
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;

import com.toro.camera.R;

/**
 * Created by THINK on 2017/6/26.
 
 public interface GLViewRenderer {
 
	 void onSurfaceCreated();
 
	 void onSurfaceChanged(int width, int height);
 
	 void onDrawFrame();
 }

 */

// frankie, BaseGLTextureView test
public class MyTextureView extends BaseGLTextureView implements GLViewRenderer, MyTextureViewController {
    final static String TAG = "MyTextureView";

    private float mAspectRatio = 16.0f/9.0f; // 4.0f/3.0f; // 16.0f/9.0f;

    private static class ColorRGBA {
        public float r,g,b,a;
        public ColorRGBA(float r_, float g_, float b_, float a_) { r=r_; g=g_; b=b_; a=a_;}
    }
    private HashMap<Integer, ColorRGBA> mClearClorMap = new HashMap<Integer, ColorRGBA>();
    private long mFrameCount = 0;

    protected FloatBuffer gLCubeBuffer;
    protected FloatBuffer gLTextureBuffer;
    protected GPUImageFilter filter;
    protected int surfaceWidth, surfaceHeight;
    protected int imageWidth, imageHeight;
    private FilterCameraInput cameraInputFilter;

    private boolean mSurfaceTextureAvaiable = false;
    private Object mSurfaceTextureAvaiableLock = new Object();

    public MyTextureView(Context context) {
        super(context);
        onInitialized(context);
    }
    public MyTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInitialized(context);
    }
    public MyTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInitialized(context);
    }

    @Override
    protected void init() {	// on super construct call 
        super.init();
        setRenderer(this);
    }
    private void onInitialized(Context context) {
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

        gLCubeBuffer = ByteBuffer.allocateDirect(TextureRotationUtil.CUBE.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        gLCubeBuffer.put(TextureRotationUtil.CUBE).position(0);

        gLTextureBuffer = ByteBuffer.allocateDirect(TextureRotationUtil.TEXTURE_NO_ROTATION.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        gLTextureBuffer.put(TextureRotationUtil.TEXTURE_NO_ROTATION).position(0);

		set_id(getIndex_()); // mark id
    }
    //@Override
    //protected void onGLDraw(ICanvasGL canvas) { /* do nothing */ }

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

    @Override
    protected int getRenderMode() {
        //return GLThread.RENDERMODE_CONTINUOUSLY;
        return GLThread.RENDERMODE_WHEN_DIRTY;
    }
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Log.v(TAG, "*** onSurfaceTextureAvailable(" + mId___ + ")");
        synchronized (mSurfaceTextureAvaiableLock) {
            mSurfaceTextureAvaiable = true;
        }
        if(glThreadBuilder == null) {
            glThreadBuilder = new GLThread.Builder();
            glThreadBuilder.setEglContextClientVersion(2);
        }

        super.onSurfaceTextureAvailable(surface, width, height);
    }
    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        Log.v(TAG, "*** onSurfaceTextureSizeChanged(" + mId___ + ")");
        super.onSurfaceTextureSizeChanged(surface, width, height);
    }
    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        Log.v(TAG, "*** onSurfaceTextureDestroyed(" + mId___ + ")");
        synchronized (mSurfaceTextureAvaiableLock) {
            mSurfaceTextureAvaiable = false;
        }
        return super.onSurfaceTextureDestroyed(surface);
    }

    //
    private FilterType mFilterType = FilterType.NONE;

    private int mCameraPreviewWidth = 0;
    private int mCameraPreviewHeight = 0;
    private int mCameraPreviewOrientation = 0;
    private boolean mCameraPreviewIsFront = false;


    private void onInitFilter(FilterType type) {
        if (filter != null) {
            filter.destroy();
        }
        filter = FilterFactory.getFilter(type);
        if (filter != null) {
            filter.init();
        }
        Log.v(TAG, "(" + mId___ + ")onInitFilter: " + type);
    }
    protected void onFilterChanged() {
        if (filter != null) {
            filter.onDisplaySizeChanged(surfaceWidth, surfaceHeight);
            filter.onInputSizeChanged(imageWidth, imageHeight);
        }
        if (filter != null
                //|| nineFilters != null
                ) {
            cameraInputFilter.initCameraFrameBuffer(imageWidth, imageHeight);
        } else {
            cameraInputFilter.destroyFramebuffers();
        }
    }

	//---------------------------------------------------------------------- GLViewRenderer impl start
    @Override
    public void onSurfaceCreated() {
        Log.v(TAG, "(" + mId___ + ")onSurfaceCreated");
        mFrameCount = 0;

        GLES20.glDisable(GL10.GL_DITHER);
        GLES20.glEnable(GL10.GL_CULL_FACE);
        GLES20.glEnable(GL10.GL_DEPTH_TEST);

        if(cameraInputFilter == null) {
            cameraInputFilter = new FilterCameraInput();
        }
        cameraInputFilter.init(); // must recompile shader

        //onInitFilter(FilterType.NONE);
        onInitFilter(mFilterType);
    }

    //
    public enum  ScaleType{
        CENTER_INSIDE,
        CENTER_CROP,
        FIT_XY;
    }
    protected ScaleType scaleType = ScaleType.FIT_XY;
    protected void adjustSize(int rotation, boolean flipHorizontal, boolean flipVertical){
        float[] textureCords = TextureRotationUtil.getRotation(Rotation.fromInt(rotation), flipHorizontal, flipVertical);
        float[] cube = TextureRotationUtil.CUBE;

        float ratio1 = (float)surfaceWidth / imageWidth;
        float ratio2 = (float)surfaceHeight / imageHeight;
        float ratioMax = Math.max(ratio1, ratio2);
        int imageWidthNew = Math.round(imageWidth * ratioMax);
        int imageHeightNew = Math.round(imageHeight * ratioMax);
        float ratioWidth = imageWidthNew / (float)surfaceWidth;
        float ratioHeight = imageHeightNew / (float)surfaceHeight;

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
    public void onSurfaceChanged(int width, int height) {
        Log.v(TAG, "(" + mId___ + ")onSurfaceChanged: " + width + " * " + height);
        if(surfaceWidth != width || surfaceHeight != height) {
            Log.v(TAG, "onSurfaceChanged do *** ");
            surfaceWidth = width;
            surfaceHeight = height;

            if (mCameraPreviewOrientation == 90 || mCameraPreviewOrientation == 270) {
                imageWidth = mCameraPreviewHeight;
                imageHeight = mCameraPreviewWidth;
            } else {
                imageWidth = mCameraPreviewWidth;
                imageHeight = mCameraPreviewHeight;
            }
            Log.v(TAG, "preview size : " + imageWidth + "*" + imageHeight + " front:" + mCameraPreviewIsFront);

            cameraInputFilter.onInputSizeChanged(imageWidth, imageHeight);
            // (imageWidth,imageHeight) -> (surfaceWidth,surfaceHeight) to change the pointer position & texture coordinate
            //adjustSize(mCameraPreviewOrientation, mCameraPreviewIsFront, true);
            adjustSize(0, mCameraPreviewIsFront, true);

            cameraInputFilter.onInputSizeChanged(imageWidth, imageHeight);
            cameraInputFilter.onDisplaySizeChanged(surfaceWidth, surfaceHeight);

            onFilterChanged();
        }
    }

	private Object mCurrentTextureLock = new Object();
    private int currentSurfaceTextureId = -1;
//    private SurfaceTexture currentSurfaceTexture = null;
    private float[] currentTextureMatrix = null;

//    private Object textureLock = new Object();
//    private boolean textureConsumed = false;


    /** frankie, *** the problem is , ShaderCamera's reander thread does not known the other thread
     * when a texture is updated by that thread, and post here, then when current thread using it,
     * but the ShaderCamera's thread update it , the problem occured !!!
     * */


    private OnDrawCallback mOnDrawCallback = null;

    private void clearColor() {
        //int id = this.getId();
        //ColorRGBA clearColor = mClearClorMap.get(id);
        //GLES20.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
        //GLES20.glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void onDrawFrame() {
        mFrameCount++;
        //Log.v(TAG, "(" + mId___ + ")onDrawFrame:" + mFrameCount + " /" + currentSurfaceTextureId);
        clearColor();

        float[] c_matrix;
        int c_textureId;
        synchronized (mCurrentTextureLock) {
            c_textureId = currentSurfaceTextureId;
            c_matrix = currentTextureMatrix;

            if(c_textureId < 0) {
                return ;
            }
            cameraInputFilter.setTextureTransformMatrix(c_matrix);
            int id = c_textureId;
            if(filter!=null) {
                id = cameraInputFilter.onDrawToTexture(c_textureId);  // id size is (imageWidth, imageHeight) ; // draw into texture !!!
                filter.onDrawFrame(id, gLCubeBuffer, gLTextureBuffer);
            } else {
                cameraInputFilter.onDrawFrame(id, gLCubeBuffer, gLTextureBuffer);
            }

            currentSurfaceTextureId = -1;
        }

        if(mOnDrawCallback != null) {
            mOnDrawCallback.onDrawFrame();
        }
//        synchronized (textureLock) {
//            textureConsumed = true;
//            textureLock.notifyAll();
//        }
    }
	//---------------------------------------------------------------------- GLViewRenderer impl end


	// ---------------------------------------------------------------------- MyTextureViewController start
	//public void setOnClickListener(View.OnClickListener l);     // this is impl in super View
	@Override
    public void setFilterType(FilterType type) {
        mFilterType = type;
    }
	@Override
    public FilterType getFilterType() {return mFilterType;}
	@Override
    public void setOnDrawCallback(OnDrawCallback callback) {
        mOnDrawCallback = callback;
    }
	@Override
    public void setCameraPreviewInfo(int width, int height, int orientation, boolean isFront) {
        mCameraPreviewWidth = width;
        mCameraPreviewHeight = height;
        mCameraPreviewOrientation = orientation;
        mCameraPreviewIsFront = isFront;
		mCameraPreviewIsFront = false; // frankie, 2018.01.11, whatever to set the false !!!
		
    }
	@Override
    public void setmAspectRatio_ext(float ratio) {
        if(ratio != mAspectRatio) {
            mAspectRatio = ratio;
            this.requestLayout();
        }
    }
	@Override
    public void createThread__() {
        Log.v(TAG, "createThread__");
        if (mGLThread == null) { // at this time to start the GLThread
            createGLThread();
        }
    }
	@Override
    public void createThread_withContext(EGLContext eglContext) {
        Log.v(TAG, "createThread_withContext");
        if(glThreadBuilder == null) {
            glThreadBuilder = new GLThread.Builder();
            glThreadBuilder.setEglContextClientVersion(2);
        }
        EglContextWrapper wrapper = new EglContextWrapper();
        wrapper.setEglContextOld(eglContext);
        wrapper.setEglContext(null);
        glThreadBuilder.setSharedEglContext(wrapper);
        if (mGLThread == null) { // at this time to start the GLThread
            createGLThread();
        }
    }
	@Override
    public void onSurfaceTextureAvaiable__(final int textureId, final SurfaceTexture st, final float[] matrix) {
        //Log.v(TAG, "(" + mId___ + ")onSurfaceTextureAvaiable__:" + textureId + " start:" + getIndex_() + " :" + mSurfaceTextureAvaiable);
        synchronized (mSurfaceTextureAvaiableLock) {
            if (!mSurfaceTextureAvaiable) {
                return;
            }
        }
//        this.queueEvent(new Runnable() {
//            @Override
//            public void run() {
//                currentSurfaceTextureId = textureId;
////                currentSurfaceTexture = st;
//                currentTextureMatrix = matrix;
//            }
//        });
        synchronized (mCurrentTextureLock) {
            currentSurfaceTextureId = textureId;
            currentTextureMatrix = matrix;
        }
//        synchronized (textureLock) {
//            textureConsumed = false;
//        }
//        this.requestRender();
//        synchronized (textureLock) {
//            try {
//                while(!textureConsumed) {
//                    textureLock.wait(10);
//                    synchronized (mSurfaceTextureAvaiableLock) {
//                        break;
//                    }
//                }
//            }catch(InterruptedException ex) {}
//        }
		this.requestRenderAndWait();
		
        //Log.v(TAG, "onSurfaceTextureAvaiable__:" + textureId + " done!");
    }

	@Override
	public void onResume() { // also override super 
		Log.v(TAG, "onPause(" + mId___ + ")");
		super.onResume();
	}
    @Override
    public void onPause() { // also override super 
        Log.v(TAG, "onPause(" + mId___ + ")");
		super.onPause();
		
    }
	@Override
	public void requestExitAndWait() { // also override super 
		Log.v(TAG, "requestExitAndWait(" + mId___ + ")");
		super.requestExitAndWait();
	}
    @Override
    public void destroyRendererThread() {
        Log.v(TAG, "destroyRendererThread(" + mId___ + ")");
		

    }
	// ---------------------------------------------------------------------- MyTextureViewController end

}
