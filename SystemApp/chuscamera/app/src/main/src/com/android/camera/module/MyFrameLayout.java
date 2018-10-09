package com.android.camera.module;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.cs.camera.magicfilter.MagicContext;
import com.cs.camera.magicfilter.OpenGlUtils;
import com.cs.camera.magicfilter.Rotation;
import com.cs.camera.magicfilter.TextureRotationUtil;
import com.cs.camera.magicfilter.filter.FilterCameraInput;
import com.cs.camera.magicfilter.filter.FilterFactory;
import com.cs.camera.magicfilter.filter.FilterType;
import com.cs.camera.magicfilter.filter.GPUImageFilter;
import com.cs.camera.magicfilter.filter.MagicBeautyFilter;

import android.opengl.EGL14;
import android.opengl.EGLExt;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGL11;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by THINK on 2017/6/20.
 */

public class MyFrameLayout extends FrameLayout {
    //private final static String TAG = "MainActivity.MyFrameLayout";
    private final static String TAG = "CAM_UI.MyFrameLayout";

    private final static boolean DEBUG_VIEW_EN = false;
    private final static boolean ANIMATION_VIEW_EN = true;
    private final static int ANIMATION_VIEW_BACKGROUND_COLOR = 0x00; // 0x80ff0000


    private static final float ASPECT_RATION_4_3 = 4f / 3f;
    private static final float ASPECT_RATION_16_9 = 16f / 9f;
    private static final float ASPECT_RATION_2_1 = 2f / 1f;
    private static final float[] RATIONS = new float[]{
            ASPECT_RATION_4_3,
            ASPECT_RATION_16_9,
            ASPECT_RATION_2_1,
    };
    private static final int RENDERER_MSG_CREATE = 1;
    private static final int RENDERER_MSG_SIZE_CHANGED = 2;
    private static final int RENDERER_MSG_CAMERA_INFO_CHANGED = 10;
    private static final int RENDERER_MSG_BEAUTY_LEVEL_CHANGED = 20;
    private static final int RENDERER_MSG_FILTER_TYPE_CHANGED = 30;
    private static final int RENDERER_MSG_FRAME_AVAILABLE = 40;
    private static final int RENDERER_MSG_RENDER_BITMAP = 41;
    private static final int RENDERER_MSG_SNAPSHOT_PREVIEW = 42;
    private static final int RENDERER_MSG_PREVIEW_BLOCK = 43;
    private static final int RENDERER_MSG_GET_EGLCONTEXT = 50;
    private static final int RENDERER_MSG_SET_FRAME_CALLBACK = 60;
    private static final int RENDERER_MSG_GET_CAMERA_PREVIEW_INFO = 70;
    private static final int RENDERER_MSG_DESTROY = 100;

    static String getRendererMessageString(int what) {
        if (what == RENDERER_MSG_CREATE) {
            return "RENDERER_MSG_CREATE";
        } else if (what == RENDERER_MSG_SIZE_CHANGED) {
            return "RENDERER_MSG_SIZE_CHANGED";
        } else if (what == RENDERER_MSG_CAMERA_INFO_CHANGED) {
            return "RENDERER_MSG_CAMERA_INFO_CHANGED";
        } else if (what == RENDERER_MSG_BEAUTY_LEVEL_CHANGED) {
            return "RENDERER_MSG_BEAUTY_LEVEL_CHANGED";
        } else if (what == RENDERER_MSG_FILTER_TYPE_CHANGED) {
            return "RENDERER_MSG_FILTER_TYPE_CHANGED";
        } else if (what == RENDERER_MSG_FRAME_AVAILABLE) {
            return "RENDERER_MSG_FRAME_AVAILABLE";
        } else if (what == RENDERER_MSG_RENDER_BITMAP) {
            return "RENDERER_MSG_RENDER_BITMAP";
        } else if (what == RENDERER_MSG_SNAPSHOT_PREVIEW) {
            return "RENDERER_MSG_SNAPSHOT_PREVIEW";
        } else if (what == RENDERER_MSG_PREVIEW_BLOCK) {
            return "RENDERER_MSG_PREVIEW_BLOCK";
        } else if (what == RENDERER_MSG_GET_EGLCONTEXT) {
            return "RENDERER_MSG_GET_EGLCONTEXT";
        } else if (what == RENDERER_MSG_SET_FRAME_CALLBACK) {
            return "RENDERER_MSG_SET_FRAME_CALLBACK";
        } else if (what == RENDERER_MSG_GET_CAMERA_PREVIEW_INFO) {
            return "RENDERER_MSG_GET_CAMERA_PREVIEW_INFO";
        } else if (what == RENDERER_MSG_DESTROY) {
            return "RENDERER_MSG_DESTROY";
        }
        return "***RENDERER_MSG_UNKNOWN***";
    }

    private static final int MAIN_MSG_CREATE = 1;
    private static final int MAIN_MSG_SIZE_CHANGED = 2;
    private static final int MAIN_MSG_DESTROY = 3;
    private static final int MAIN_MSG_ON_BITMAP_RENDERED = 4;
    private static final int MAIN_MSG_ON_PREVIEW_SNAPSHOT = 5;

    static String getMainMessageString(int what) {
        if (what == MAIN_MSG_CREATE) {
            return "MAIN_MSG_CREATE";
        } else if (what == MAIN_MSG_SIZE_CHANGED) {
            return "MAIN_MSG_SIZE_CHANGED";
        } else if (what == MAIN_MSG_DESTROY) {
            return "MAIN_MSG_DESTROY";
        } else if (what == MAIN_MSG_ON_BITMAP_RENDERED) {
            return "MAIN_MSG_ON_BITMAP_RENDERED";
        } else if (what == MAIN_MSG_ON_PREVIEW_SNAPSHOT) {
            return "MAIN_MSG_ON_PREVIEW_SNAPSHOT";
        }
        return "***MAIN_MSG_UNKNOWN***";
    }

    private float mAspectRatio = ASPECT_RATION_16_9;

    private int mMaxPreviewWidth = 0;
    private int mMaxPreviewHeight = 0;
    private boolean mIsTextureAvaiableAlready = false;

    private Context mContext = null;
    private boolean mPaused = true; // false;
    private TextureView mPreviewTextureView;
    private View mTextureCover = null;
    private View mAnimationView = null;             // frankie, debug !
    private HandlerThread mRendererThread = null;
    private RendererHandler mRendererHandler = null;
	private int mBeautyLevel_1 = 0;	// default is 0 
	private int mFilterType_1 = 0;
	
    private Object mCreateLock = new Object();
    private boolean mCreateFlag = false;
    private Object mDestroyLock = new Object();
	private boolean mDestroyFlag = false;
	
    private static Object mCameraPreviewInfoSetupLock = new Object();

    private MainHandler mainHandler;
    private static Object mainDestroyLock = new Object();

    // for simulate the SurfaceView's callback
    public interface Callback {
        public void surfaceCreated(SurfaceTexture surfaceTexture);
        public void surfaceChanged(SurfaceTexture surfaceTexture, int format, int width, int height);
        public void surfaceDestroyed(SurfaceTexture surfaceTexture);
    }
    private Callback mCallback = null;
    public void setCallback(Callback cb) {
        mCallback = cb;
    }

    // for jpeg photo process callback
    public interface RenderBitmapCallback {
        public void onBitmapRendered(Bitmap bitmap);
    }
    private RenderBitmapCallback mRenderBitmapCallback = null;
    public void setRenderBitmapCallback(RenderBitmapCallback c) {
        mRenderBitmapCallback = c;
    }

    // for preview snapshot
    public interface PreviewSnapshotCallback {
        public void onPreviewSnapshotRendered(Bitmap bitmap);
    }
    private PreviewSnapshotCallback mPreviewSnapshotCallback = null;
    public void setPreviewSnapshotCallback(PreviewSnapshotCallback c) {
        mPreviewSnapshotCallback = c;
    }

    class MainHandler extends Handler {
        public MainHandler() {
            super();
        }
        @Override
        public void handleMessage(Message message) {
            Log.v(TAG, "MainHandler.handleMessage(" + message.what + "):" + getMainMessageString(message.what));
            if (message.what == MAIN_MSG_CREATE) {
                if (mCallback != null) {
                    mCallback.surfaceCreated((SurfaceTexture) message.obj);
                }
            }
            else if (message.what == MAIN_MSG_SIZE_CHANGED) {
                if (mCallback != null
                        //&& mPaused == false // frankie, add
                        ) {
                    mCallback.surfaceChanged((SurfaceTexture) message.obj, -1, message.arg1, message.arg2);
                }
            }
            else if (message.what == MAIN_MSG_DESTROY) {
                if (mCallback != null) {
                    mCallback.surfaceDestroyed((SurfaceTexture) message.obj);
                }
//                synchronized (mainDestroyLock) {
//                    mainDestroyLock.notifyAll();
//                }
            }
            else if (message.what == MAIN_MSG_ON_BITMAP_RENDERED) {
                if (mRenderBitmapCallback != null) {
                    mRenderBitmapCallback.onBitmapRendered((Bitmap) message.obj);
                }
            }
            else if (message.what == MAIN_MSG_ON_PREVIEW_SNAPSHOT) {
                if (mPreviewSnapshotCallback != null) {
                    mPreviewSnapshotCallback.onPreviewSnapshotRendered((Bitmap) message.obj);
                }
            }
        }
    }



//    ////////////////////////////////////////////////////////////////////////////////////////////


    public enum ScaleType {CENTER_INSIDE, CENTER_CROP, FIT_XY}

	private static HandlerThread mTextureAvailableThread = null; // frankie, but this notification thread helps nothing !
	static {
		mTextureAvailableThread = new HandlerThread("mTextureAvailableThread", -2);
		mTextureAvailableThread.start();
		mTextureAvailableThread.getLooper();
	}
    class RendererHandler extends Handler {

		private GLHelper mGLHelper_1 = new GLHelper();

        private SurfaceTexture mSurfaceTexture = null;

        int textureId = OpenGlUtils.NO_TEXTURE;
        private SurfaceTexture inputSurfaceTexture;
        private boolean frameComming = false;
        private long frameCounter = 0;
        private boolean mReady = false;

        protected GPUImageFilter filter;
        private FilterCameraInput cameraInputFilter;
		private int mBeautyLevel_r = 0;
		private int mFilterType_r = 0;
        protected int surfaceWidth, surfaceHeight;
        protected int imageWidth, imageHeight;
		
        protected FloatBuffer gLCubeBuffer;
        protected FloatBuffer gLTextureBuffer;

        private boolean mSnapshotPreviewRequest = false;
        private boolean mRenderBlackgroundRequest = false;

        InnerCameraController.CameraPreviewInfo cameraPreviewInfo = new InnerCameraController.CameraPreviewInfo();

		private Handler mTextureAvailableHandler = new Handler(mTextureAvailableThread.getLooper());
        private SurfaceTexture.OnFrameAvailableListener onFrameAvailableListener = new SurfaceTexture.OnFrameAvailableListener() {
            @Override
            public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                //Log.v(TAG, "@" + getTid() + "/" + "onFrameAvailable frameComming:" + frameComming + "/" + frameCounter);
                if (surfaceTexture != null && mReady) {
                    ///surfaceTexture.updateTexImage();
                    frameComming = true;
                    RendererHandler.this.obtainMessage(RENDERER_MSG_FRAME_AVAILABLE, -1, -1, surfaceTexture).sendToTarget();
                }
            }
        };

        public RendererHandler(Looper looper, int beauty_level, int filter_type_) {
            super(looper);

			mBeautyLevel_r = beauty_level;
			mFilterType_r = filter_type_;
            gLCubeBuffer = ByteBuffer.allocateDirect(TextureRotationUtil.CUBE.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            gLCubeBuffer.put(TextureRotationUtil.CUBE).position(0);

            gLTextureBuffer = ByteBuffer.allocateDirect(TextureRotationUtil.TEXTURE_NO_ROTATION.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            gLTextureBuffer.put(TextureRotationUtil.TEXTURE_NO_ROTATION).position(0);
        }

        private void onInitFilter(FilterType type) {
            if (filter != null) {
                filter.destroy();
            }
            filter = FilterFactory.getFilter(type);
            if (filter != null) {
                filter.init();
            }
            Log.v(TAG, "onInitFilter: " + type);
        }

        protected void onFilterChanged() {
            if (filter != null) {
				filter.onInputSizeChanged(imageWidth, imageHeight);
                filter.onDisplaySizeChanged(surfaceWidth, surfaceHeight);
            }
            if(cameraInputFilter != null){
                if (filter != null
                        ) {
                    cameraInputFilter.initCameraFrameBuffer(imageWidth, imageHeight);
                } else {
                    cameraInputFilter.destroyFramebuffers();
                }
            }
        }


        protected ScaleType scaleType = ScaleType.FIT_XY;

        private float addDistance(float coordinate, float distance) {
            return coordinate == 0.0f ? distance : 1 - distance;
        }
        protected void adjustSize(int rotation, boolean flipHorizontal, boolean flipVertical
			, FloatBuffer vertexBuffer, FloatBuffer textureBuffer
			) {
            float[] textureCords = TextureRotationUtil.getRotation(Rotation.fromInt(rotation), flipHorizontal, flipVertical);
            float[] cube = TextureRotationUtil.CUBE;

            float ratio1 = (float) surfaceWidth / imageWidth;
            float ratio2 = (float) surfaceHeight / imageHeight;
            float ratioMax = Math.max(ratio1, ratio2);
            int imageWidthNew = Math.round(imageWidth * ratioMax);
            int imageHeightNew = Math.round(imageHeight * ratioMax);
            float ratioWidth = imageWidthNew / (float) surfaceWidth;
            float ratioHeight = imageHeightNew / (float) surfaceHeight;

            if (scaleType == ScaleType.CENTER_INSIDE) {
                cube = new float[]{
                        TextureRotationUtil.CUBE[0] / ratioHeight, TextureRotationUtil.CUBE[1] / ratioWidth,
                        TextureRotationUtil.CUBE[2] / ratioHeight, TextureRotationUtil.CUBE[3] / ratioWidth,
                        TextureRotationUtil.CUBE[4] / ratioHeight, TextureRotationUtil.CUBE[5] / ratioWidth,
                        TextureRotationUtil.CUBE[6] / ratioHeight, TextureRotationUtil.CUBE[7] / ratioWidth,
                };
            } else if (scaleType == ScaleType.FIT_XY) {

            } else if (scaleType == ScaleType.CENTER_CROP) {
                float distHorizontal = (1 - 1 / ratioWidth) / 2;
                float distVertical = (1 - 1 / ratioHeight) / 2;
                textureCords = new float[]{
                        addDistance(textureCords[0], distVertical), addDistance(textureCords[1], distHorizontal),
                        addDistance(textureCords[2], distVertical), addDistance(textureCords[3], distHorizontal),
                        addDistance(textureCords[4], distVertical), addDistance(textureCords[5], distHorizontal),
                        addDistance(textureCords[6], distVertical), addDistance(textureCords[7], distHorizontal),
                };
            }
			if(false) {
	            gLCubeBuffer.clear();
	            gLCubeBuffer.put(cube).position(0);
	            gLTextureBuffer.clear();
	            gLTextureBuffer.put(textureCords).position(0);
			} else { // 
				vertexBuffer.clear();
	            vertexBuffer.put(cube).position(0);
	            textureBuffer.clear();
	            textureBuffer.put(textureCords).position(0);
			}
			
        }

		@Override
		public void handleMessage(Message message) {
		    if (RENDERER_MSG_FRAME_AVAILABLE != message.what) {
                Log.w(TAG, "@" + getTid() + "/" + "++RendererHandler.handleMessage(" + message.what + "):" + getRendererMessageString(message.what) + " start");
            }
			handleMessage_inner(message);
			if (RENDERER_MSG_FRAME_AVAILABLE != message.what) {
                Log.w(TAG, "@" + getTid() + "/" + "--RendererHandler.handleMessage(" + message.what + "):" + getRendererMessageString(message.what) + " done");
            }
		}

        private void handleMessage_inner(Message message) {
            if (message.what == RENDERER_MSG_CREATE) {
                mSurfaceTexture = (SurfaceTexture) message.obj;
                Log.v(TAG, "    surfaceTexture_disp:" + mSurfaceTexture.hashCode());

				mGLHelper_1.setupDefault();
                mGLHelper_1.start();
                mGLHelper_1.createSurface(mSurfaceTexture);

                GLES20.glDisable(GL10.GL_DITHER);
                GLES20.glEnable(GL10.GL_CULL_FACE);
                GLES20.glEnable(GL10.GL_DEPTH_TEST);

                if (cameraInputFilter == null) {
                    cameraInputFilter = new FilterCameraInput();
                }
                cameraInputFilter.init(); // must recompile shader
                if(cameraInputFilter.mBeautyLevel != mBeautyLevel_r) {
					cameraInputFilter.mBeautyLevel = mBeautyLevel_r;
					cameraInputFilter.onBeautyLevelChanged();
				}

                //onInitFilter(FilterType.AMARO); // test
                //onInitFilter(FilterType.NONE); // test
                onInitFilter(FilterFactory.getFilterTypeFromInt(mFilterType_r));

                Log.v(TAG, "textureId:" + textureId);
                if (textureId == OpenGlUtils.NO_TEXTURE) {
                    textureId = OpenGlUtils.getExternalOESTextureID();
                    if (textureId != OpenGlUtils.NO_TEXTURE) {
                        inputSurfaceTexture = new SurfaceTexture(textureId);
                        //inputSurfaceTexture.setOnFrameAvailableListener(onFrameAvailableListener, this);
                        inputSurfaceTexture.setOnFrameAvailableListener(onFrameAvailableListener, mTextureAvailableHandler);
                    }
                }
                synchronized (mCreateLock) {
                    mCreateFlag = true;
                    mCreateLock.notifyAll();
                }
                mainHandler.obtainMessage(MAIN_MSG_CREATE, -1, -1, inputSurfaceTexture).sendToTarget();
            }
			else if (message.what == RENDERER_MSG_SIZE_CHANGED) {
                int width = message.arg1;
                int height = message.arg2;
                SurfaceTexture surfaceTexture_disp = (SurfaceTexture) message.obj;
                Log.v(TAG, "    surfaceTexture_disp(" + surfaceTexture_disp.hashCode() + ") size:" + width + " * " + height); // the same object !!!

                surfaceWidth = width;
                surfaceHeight = height;

                GLES20.glViewport(0, 0, surfaceWidth, surfaceHeight);

                // 
                if (mInnerCameraController != null) {
                    // if camera switch , the info is already prepared at this time !
                    // if preview size changed, the info is also prepared at this time !
                    // otherwise , wait for camera ready, then get the camera info
                    cameraPreviewInfo = mInnerCameraController.getCameraPreviewInfo();
                }
				
				if(cameraPreviewInfo == null) { // frankie, 2017.09.06, sometimes here get null, repost 
					Log.e(TAG, "camerea preview info not ready!!! mRendererHandler" + (mRendererHandler!=null ? "==null" : "!=null"));
					if(mRendererHandler != null) {
						mRendererHandler.obtainMessage(RENDERER_MSG_SIZE_CHANGED, width, height, surfaceTexture_disp)
							.sendToTarget();
					}
				} else {
				
                Log.v(TAG, "cameraPreviewInfo size:" + cameraPreviewInfo.width + " * " + cameraPreviewInfo.height
                        + " ori:" + cameraPreviewInfo.orientation + " front:" + cameraPreviewInfo.is_front);
                if (cameraPreviewInfo.orientation == 90 || cameraPreviewInfo.orientation == 270) {
                    imageWidth = cameraPreviewInfo.height;
                    imageHeight = cameraPreviewInfo.width;
                } else {
                    imageWidth = cameraPreviewInfo.width;
                    imageHeight = cameraPreviewInfo.height;
                }
                // get input image size and orientation // at this time, the camera should be prepared, or
                cameraInputFilter.onInputSizeChanged(imageWidth, imageHeight);

                // (imageWidth,imageHeight) -> (surfaceWidth,surfaceHeight) to change the pointer position & texture coordinate
                //adjustSize(cameraPreviewInfo.orientation, cameraPreviewInfo.is_front, true, gLCubeBuffer, gLTextureBuffer);
                //adjustSize(0, !cameraPreviewInfo.is_front, true, gLCubeBuffer, gLTextureBuffer);
                adjustSize(0, false, true, gLCubeBuffer, gLTextureBuffer);

                Log.v(TAG, "  image size : " + imageWidth + "*" + imageHeight);
                Log.v(TAG, "  surface size : " + surfaceWidth + "*" + surfaceHeight);

                cameraInputFilter.onDisplaySizeChanged(surfaceWidth, surfaceHeight);
				
                onFilterChanged();

                /***********  when surface changed, nothing to do with GL context */
                mainHandler.obtainMessage(MAIN_MSG_SIZE_CHANGED, message.arg1, message.arg2, inputSurfaceTexture).sendToTarget();
                mReady = true;
				}
            }
			else if (RENDERER_MSG_CAMERA_INFO_CHANGED == message.what) {
                InnerCameraController.CameraPreviewInfo i = (InnerCameraController.CameraPreviewInfo) message.obj;
                synchronized (mCameraPreviewInfo) {
                    cameraPreviewInfo.width = i.width;
                    cameraPreviewInfo.height = i.height;
                    cameraPreviewInfo.orientation = i.orientation;
                    cameraPreviewInfo.is_front = i.is_front;
                }
				Log.v(TAG, "cameraPreviewInfo size:" + cameraPreviewInfo.width + " * " + cameraPreviewInfo.height
                        + " ori:" + cameraPreviewInfo.orientation + " front:" + cameraPreviewInfo.is_front);
                if (mReady) {
                    if (cameraPreviewInfo.orientation == 90 || cameraPreviewInfo.orientation == 270) {
                        imageWidth = cameraPreviewInfo.height;
                        imageHeight = cameraPreviewInfo.width;
                    } else {
                        imageWidth = cameraPreviewInfo.width;
                        imageHeight = cameraPreviewInfo.height;
                    }
                    Log.v(TAG, "  image size : " + imageWidth + "*" + imageHeight);
                    Log.v(TAG, "  surface size : " + surfaceWidth + "*" + surfaceHeight);		// still previous display size 
                    // get input image size and orientation // at this time, the camera should be prepared, or
                    cameraInputFilter.onInputSizeChanged(imageWidth, imageHeight);
					
                    // (imageWidth,imageHeight) -> (surfaceWidth,surfaceHeight) to change the pointer position & texture coordinate
                    //adjustSize(0, !cameraPreviewInfo.is_front, true, gLCubeBuffer, gLTextureBuffer);
                    adjustSize(0, false, true, gLCubeBuffer, gLTextureBuffer);

					onFilterChanged();
                }

                synchronized (mCameraPreviewInfoSetupLock) {
                    mCameraPreviewInfoSetupLock.notifyAll();
                }
            }
			else if (message.what == RENDERER_MSG_BEAUTY_LEVEL_CHANGED) {
				mBeautyLevel_r = message.arg1;
                if (cameraInputFilter != null) {
					if(mBeautyLevel_r != cameraInputFilter.mBeautyLevel) {
						cameraInputFilter.mBeautyLevel = mBeautyLevel_r;
                    	cameraInputFilter.onBeautyLevelChanged();
					}
                }
            }
			else if (message.what == RENDERER_MSG_FILTER_TYPE_CHANGED) {
                FilterType fileType__ = (FilterType) message.obj;
                if (filter != null) {
                    filter.destroy();
                }
                filter = null;
                filter = FilterFactory.getFilter(fileType__);
                Log.v(TAG, "filter" + (filter != null ? "!=null" : "==null"));
                if (filter != null) {
                    filter.init();
                }
                onFilterChanged();
            }
			else if (message.what == RENDERER_MSG_FRAME_AVAILABLE) {
                com.cs.camera.util.Util.printFPS(TAG);

				if(mPaused) { // frankie, 2017.11.01, add
					return ;
				}
                //if(mSnapshotPreviewRequest)
//                {
//                    Log.v(TAG, "    snapshot:" + mSnapshotPreviewRequest
//                            + " renderBlack:" + mRenderBlackgroundRequest);
//                }
				GLES20.glViewport(0, 0, surfaceWidth, surfaceHeight);
				
                if (mRenderBlackgroundRequest && !mSnapshotPreviewRequest) {
                    if (inputSurfaceTexture == null
                        //|| frameComming == false
                            ) {
                        return;
                    }
                    inputSurfaceTexture.updateTexImage();
                    float[] mtx = new float[16];
                    inputSurfaceTexture.getTransformMatrix(mtx);

                    GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

                    mGLHelper_1.__swap__();
                } else {
                    GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

                    if (inputSurfaceTexture == null
                        //|| frameComming == false
                            ) {
                        return;
                    }
                    inputSurfaceTexture.updateTexImage();
                    float[] mtx = new float[16];
                    inputSurfaceTexture.getTransformMatrix(mtx);

                    if (!mSnapshotPreviewRequest) {
                        //////////////////////////////////////////////////////////////////////////
                        FrameAvaiableCallback callback_ext = null;
                        synchronized (mFrameAvaiableCallbackLock) {
                            callback_ext = mFrameAvaiableCallback;
                        }
                        if (callback_ext != null) {
                            callback_ext.onFrameAvaiable(textureId, null, mtx);
                            // frankie, here should wait the other thread finished using it , then go ahead !!!
                        }
                        //////////////////////////////////////////////////////////////////////////
                    }
                    if(cameraInputFilter != null){
                        cameraInputFilter.setTextureTransformMatrix(mtx);
                    }
                    int id = -1;
                    if (filter != null) {
                        if(cameraInputFilter != null){
                            id = cameraInputFilter.onDrawToTexture(textureId);  // id size is (imageWidth, imageHeight) ; //
                        }
                        if(id > 0) {
                        	filter.onDrawFrame(id, gLCubeBuffer, gLTextureBuffer);
						}
                    } else if(cameraInputFilter != null){
                        cameraInputFilter.onDrawFrame(textureId, gLCubeBuffer, gLTextureBuffer);
                    }

                    mGLHelper_1.__swap__();

                    if (mSnapshotPreviewRequest) {
						Log.w(TAG,">>>> mSnapshotPreviewRequest");
                        GLES20.glFinish(); //
                        long time_started = System.currentTimeMillis();
                        Log.v(TAG, "glReadPixels: " + surfaceWidth + " * " + surfaceHeight);

                        // read directly from the draw buffer, sometimes got the all black bitmap !!!
//                        IntBuffer ib = IntBuffer.allocate(surfaceWidth * surfaceHeight);
//                        GLES20.glReadPixels(0, 0, surfaceWidth, surfaceHeight, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, ib);
//                        Bitmap preview_snapshot_bitmap = Bitmap.createBitmap(surfaceWidth, surfaceHeight, Bitmap.Config.ARGB_8888);
//                        preview_snapshot_bitmap.copyPixelsFromBuffer(ib);

                        Bitmap preview_snapshot_bitmap = null;
						if(false) {
                            // frankie, note, here do not works 
							preview_snapshot_bitmap = drawPreviewSnapshot(surfaceWidth, surfaceHeight, textureId, mtx, true);
						} else {
                            Log.v(TAG, "Read snapshot directly from pixel buffer !");
							IntBuffer ib = IntBuffer.allocate(surfaceWidth * surfaceHeight);
				            GLES20.glReadPixels(0, 0, surfaceWidth, surfaceHeight, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, ib);
				            preview_snapshot_bitmap = Bitmap.createBitmap(surfaceWidth, surfaceHeight, Bitmap.Config.ARGB_8888);
				            preview_snapshot_bitmap.copyPixelsFromBuffer(ib);

                            Log.v(TAG, "Flip snapshot bitmap");
                            Matrix m = new Matrix();
                            m.preScale(1, -1);
                            Bitmap dstBitmap = Bitmap.createBitmap(preview_snapshot_bitmap, 0, 0, preview_snapshot_bitmap.getWidth(), preview_snapshot_bitmap.getHeight(), m, true);
                            //preview_snapshot_bitmap.recycle();
                            preview_snapshot_bitmap = dstBitmap;
						}
			
                        time_started = System.currentTimeMillis() - time_started;
                        Log.v(TAG, "glReadPixels profiling:" + time_started);
						
                        mainHandler.obtainMessage(MAIN_MSG_ON_PREVIEW_SNAPSHOT, -1, -1, preview_snapshot_bitmap).sendToTarget();
                        mSnapshotPreviewRequest = false;
                    }
                }

            }
			else if (message.what == RENDERER_MSG_RENDER_BITMAP) {
                Bitmap bitmap = (Bitmap) message.obj;
                Bitmap returned_bitmap = null;
				if(!mPaused) {
					returned_bitmap = renderPhoto(bitmap, message.arg1 > 0 ? true : false);
				}
                mainHandler.obtainMessage(MAIN_MSG_ON_BITMAP_RENDERED, -1, -1, returned_bitmap).sendToTarget();
                GLES20.glViewport(0, 0, surfaceWidth, surfaceHeight);
            }
			else if (message.what == RENDERER_MSG_SNAPSHOT_PREVIEW) {
                mSnapshotPreviewRequest = true;
            }
			else if (message.what == RENDERER_MSG_PREVIEW_BLOCK) {
                if (message.arg1 > 0) {
                    mRenderBlackgroundRequest = true;
                } else {
                    mRenderBlackgroundRequest = false;
                }
                Log.v(TAG, "mRenderBlackgroundRequest:" + mRenderBlackgroundRequest);
            }
			else if (message.what == RENDERER_MSG_GET_EGLCONTEXT) {
                synchronized (__eglContext_get_lock) {
                    __eglContext = mGLHelper_1.get__EGLContext();
                    __eglContext_get_lock.notifyAll();
                }
            }
			else if (message.what == RENDERER_MSG_SET_FRAME_CALLBACK) {
            }
			else if (message.what == RENDERER_MSG_GET_CAMERA_PREVIEW_INFO) {
                __camera_preview_infomation_returned = new CameraPreviewInformation();
                __camera_preview_infomation_returned.width = cameraPreviewInfo.width;
                __camera_preview_infomation_returned.height = cameraPreviewInfo.height;
                __camera_preview_infomation_returned.orientation = cameraPreviewInfo.orientation;
                __camera_preview_infomation_returned.isFront = cameraPreviewInfo.is_front;
                synchronized (__camera_preview_infomation_get_lock) {
                    __camera_preview_infomation_get_lock.notifyAll();
                }
            }
			else if (message.what == RENDERER_MSG_DESTROY) {
                mSnapshotPreviewRequest = false;
                mRenderBlackgroundRequest = false;

                SurfaceTexture surfaceTexture_disp = (SurfaceTexture) message.obj;
                Log.v(TAG, "    surfaceTexture_disp:" + surfaceTexture_disp.hashCode());
                inputSurfaceTexture.setOnFrameAvailableListener(null);

                if (filter != null) {
                    filter.destroy();
                    filter = null;
                }
                if (cameraInputFilter != null) {
                    cameraInputFilter.destroyFramebuffers();
                    cameraInputFilter.destroy();
                    cameraInputFilter = null;
                }


                mGLHelper_1.destroySurface();
                mGLHelper_1.finish();

                textureId = -1; // frankie,

                mainHandler.obtainMessage(MAIN_MSG_DESTROY, -1, -1, null).sendToTarget();

//                Log.v(TAG, "wait mainDestroyLock notify");
//                synchronized (mainDestroyLock) {
//                    try {
//                        mainDestroyLock.wait(3000);
//                    } catch (InterruptedException ex) {
//                    }    // must wait finished
//                }

                mSurfaceTexture = null;
                synchronized (mDestroyLock) {
					mDestroyFlag = true;
                    mDestroyLock.notifyAll();
                }
                mReady = false;
            }
        }

        public void setFilterType(final int type) {
            final FilterType fileType = FilterFactory.getFilterTypeFromInt(type);
            this.obtainMessage(RENDERER_MSG_FILTER_TYPE_CHANGED, 0, 0, fileType).sendToTarget();
        }

        private Object mFrameAvaiableCallbackLock = new Object();
        private FrameAvaiableCallback mFrameAvaiableCallback = null;

        public void setFrameAvaiableCallback(final FrameAvaiableCallback callback) {
            synchronized (mFrameAvaiableCallbackLock) {
                mFrameAvaiableCallback = callback;
            }
        }

        private EGLContext __eglContext = null;
        private Object __eglContext_get_lock = new Object();

        public EGLContext get__eglContext() {
            __eglContext = null;
            this.obtainMessage(RENDERER_MSG_GET_EGLCONTEXT, 0, 0, null).sendToTarget();
            synchronized (__eglContext_get_lock) {
                try {
                    __eglContext_get_lock.wait(3000);
                } catch (InterruptedException ex) {
                }
            }
            return __eglContext;
        }

        private Object __camera_preview_infomation_get_lock = new Object();
        CameraPreviewInformation __camera_preview_infomation_returned = null;

        public CameraPreviewInformation getCameraPreviewInformation() {
            __camera_preview_infomation_returned = null;
            this.obtainMessage(RENDERER_MSG_GET_CAMERA_PREVIEW_INFO, 0, 0, null).sendToTarget();
            synchronized (__camera_preview_infomation_get_lock) {
                try {
                    __camera_preview_infomation_get_lock.wait(3000);
                } catch (InterruptedException ex) {
                }
            }
            return __camera_preview_infomation_returned;
        }

        //
        public void renderBitmap_1(Bitmap bitmap, int rotate) {
            this.obtainMessage(RENDERER_MSG_RENDER_BITMAP, rotate, 0, bitmap).sendToTarget();
        }

        private MagicBeautyFilter mBeautyFilter = null;      //

        public void Matrix_setIdentityM(float[] sm, int smOffset) {
            for (int i=0 ; i<16 ; i++) {
                sm[smOffset + i] = 0;
            }
            for(int i = 0; i < 16; i += 5) {
                sm[smOffset + i] = 1.0f;
            }
        }
        private Bitmap renderPhoto(Bitmap bitmap, boolean isRotated) {
			Log.v(TAG, "+ renderPhoto");
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int[] mFrameBuffers = new int[1];
            int[] mFrameBufferTextures = new int[1];

			if(mBeautyFilter != null) {
			    mBeautyFilter.destroy();
            	mBeautyFilter = null;
			}
            if (mBeautyFilter == null) {
                mBeautyFilter = new MagicBeautyFilter();
            }
            mBeautyFilter.init();
            mBeautyFilter.onDisplaySizeChanged(width, height);
            mBeautyFilter.onInputSizeChanged(width, height);

            if (filter != null) {
                filter.onInputSizeChanged(width, height);
                filter.onDisplaySizeChanged(width, height);
            }

			Log.v(TAG, "mBeautyLevel_r:" + mBeautyLevel_r);
			if(mBeautyLevel_r != mBeautyFilter.mBeautyLevel) {
				mBeautyFilter.mBeautyLevel = mBeautyLevel_r;
                mBeautyFilter.onBeautyLevelChanged();
			}
			
            GLES20.glGenFramebuffers(1, mFrameBuffers, 0);
            GLES20.glGenTextures(1, mFrameBufferTextures, 0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mFrameBufferTextures[0]);
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0,
                    GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffers[0]);
            GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                    GLES20.GL_TEXTURE_2D, mFrameBufferTextures[0], 0);

            GLES20.glViewport(0, 0, width, height);

            long textureUploadTime = System.currentTimeMillis();
            int textureId = OpenGlUtils.loadTexture(bitmap, OpenGlUtils.NO_TEXTURE, true);
            textureUploadTime = System.currentTimeMillis() - textureUploadTime;
            Log.v(TAG, "textureId:" + textureId + ", textureUploadTime: " + textureUploadTime);

            FloatBuffer vertexBuffer = 
				ByteBuffer.allocateDirect(TextureRotationUtil.CUBE.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            FloatBuffer textureBuffer = 
				ByteBuffer.allocateDirect(TextureRotationUtil.TEXTURE_NO_ROTATION.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            vertexBuffer.put(TextureRotationUtil.CUBE).position(0);
            if (isRotated) {
                textureBuffer.put(TextureRotationUtil.getRotation(Rotation.NORMAL, false, false)).position(0);
            } else {
                textureBuffer.put(TextureRotationUtil.getRotation(Rotation.NORMAL, false, true)).position(0);
			}
				
			Log.v(TAG, "filter" + (filter != null ? " != null" : " == null"));
			Log.v(TAG, "bitmap size : " + width + " * " + height);
            if (filter == null) {
                mBeautyFilter.onDrawFrame_n_oes(textureId, vertexBuffer, textureBuffer);
            } else {
            
                //mBeautyFilter.onDrawFrame(textureId);
                //filter.onDrawFrame(mFrameBufferTextures[0], vertexBuffer, textureBuffer); // frankie, error 

				mBeautyFilter.initCameraFrameBuffer(width, height);			// create one fbo(attached with one texture)
				
				int id = mBeautyFilter.onDrawToTexture_non_oes(textureId);	// render the picture into this fbo

				GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffers[0]);	// switch to the previous fbo
				
				if(id > 0) {
					filter.onDrawFrame(id, vertexBuffer, textureBuffer);			// render that texture into current fbo
				}
				
            }

            mGLHelper_1.__swap__();
			GLES20.glFinish(); //

            IntBuffer ib = IntBuffer.allocate(width * height);
            GLES20.glReadPixels(0, 0, width, height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, ib);
            Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            result.copyPixelsFromBuffer(ib);

            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
            GLES20.glDeleteTextures(1, new int[]{textureId}, 0);
            GLES20.glDeleteFramebuffers(mFrameBuffers.length, mFrameBuffers, 0);
            GLES20.glDeleteTextures(mFrameBufferTextures.length, mFrameBufferTextures, 0);

			if(mBeautyFilter != null) {
				mBeautyFilter.destroyFramebuffers();
				
            	mBeautyFilter.destroy();
            	mBeautyFilter = null;
			}
            if (filter != null) {
                filter.onDisplaySizeChanged(surfaceWidth, surfaceHeight);
                filter.onInputSizeChanged(imageWidth, imageHeight);
            }
			Log.v(TAG, "- renderPhoto done ");
            return result;
        }

        private Bitmap drawPreviewSnapshot(int width, int height, int textureId, float[] matrix, boolean isRotated) {
			Log.v(TAG, "drawPreviewSnapshot, " + width + "*" + height + " textureId:" + textureId);
            FloatBuffer vertexBuffer;
            FloatBuffer textureBuffer;
            FloatBuffer textureBuffer_4filter;

            int[] mFrameBuffers = new int[1];
            int[] mFrameBufferTextures = new int[1];

            GLES20.glGenFramebuffers(1, mFrameBuffers, 0);
			
            GLES20.glGenTextures(1, mFrameBufferTextures, 0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mFrameBufferTextures[0]);
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffers[0]);
            GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, mFrameBufferTextures[0], 0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
			//GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);

            GLES20.glViewport(0, 0, width, height);

			vertexBuffer = ByteBuffer.allocateDirect(TextureRotationUtil.CUBE.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
			textureBuffer = ByteBuffer.allocateDirect(TextureRotationUtil.TEXTURE_NO_ROTATION.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
			
			float[] cubeVertex = TextureRotationUtil.CUBE;
			float[] textureCords = TextureRotationUtil.getRotation(Rotation.NORMAL, false, false);
			vertexBuffer.clear();
            vertexBuffer.put(cubeVertex).position(0);
			textureBuffer.clear();
            textureBuffer.put(textureCords).position(0);

			textureBuffer_4filter = ByteBuffer.allocateDirect(TextureRotationUtil.TEXTURE_NO_ROTATION.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
			float[] textureCords_4filter = TextureRotationUtil.getRotation(Rotation.NORMAL, true, true);
			textureBuffer_4filter.clear();
            textureBuffer_4filter.put(textureCords_4filter).position(0);

            if(cameraInputFilter != null){
                cameraInputFilter.setTextureTransformMatrix(matrix);
            }
            int id = -1;
			Log.v(TAG, "filter " + (filter!=null ? "!= null" : " == null"));
            if (filter != null) {
                //id = cameraInputFilter.onDrawToTexture(textureId);  // id size is (imageWidth, imageHeight) ; //
                if(cameraInputFilter != null){
                    id = cameraInputFilter.onDrawToTexture_(textureId, vertexBuffer, textureBuffer_4filter);  // id size is (imageWidth, imageHeight) ; //
                }
                Log.v(TAG, "id=" + id + " textureId=" + textureId);
				if(id > 0) {
                	filter.onDrawFrame(id, vertexBuffer, textureBuffer_4filter);
				}
            } else if(cameraInputFilter != null){
                cameraInputFilter.onDrawFrame(textureId, vertexBuffer, textureBuffer);
            }
            mGLHelper_1.__swap__();
			GLES20.glFinish(); //

            IntBuffer ib = IntBuffer.allocate(width * height);
            GLES20.glReadPixels(0, 0, width, height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, ib);
            Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            result.copyPixelsFromBuffer(ib);

            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
            //GLES20.glDeleteTextures(1, new int[]{textureId}, 0);
            if(id > 0) {
            //	GLES20.glDeleteTextures(1, new int[]{id}, 0);
			}
            GLES20.glDeleteFramebuffers(mFrameBuffers.length, mFrameBuffers, 0);
            GLES20.glDeleteTextures(mFrameBufferTextures.length, mFrameBufferTextures, 0);

			Log.v(TAG, "drawPreviewSnapshot, done");
            return result;
        }

        public void previewSnapshot() {
            this.obtainMessage(RENDERER_MSG_SNAPSHOT_PREVIEW, 0, 0, null).sendToTarget();
        }

        public void previewRenderBlock(boolean blocked) {
            this.obtainMessage(RENDERER_MSG_PREVIEW_BLOCK, blocked ? 1 : 0, 0, null).sendToTarget();
        }

    } // end of class RendererHandler extends Handler {
    ////////////////////////////////////////////////////////////////////////////////////////////

    public MyFrameLayout(Context context) {
        super(context);
        onInstantiate(context);
    }
    public MyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInstantiate(context);
    }
    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInstantiate(context);
    }
    public MyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        onInstantiate(context);
    }
    private void onInstantiate(Context context) {
        Log.v(TAG, "onInstantiate");
        mContext = context;
        mainHandler = new MainHandler();
        if (mPreviewTextureView == null) {
            addTextureView_1(context);
        }
    }

    private int ratio_test = 0;
    private Button testButton = null;
    public void test1() {
        ratio_test++;
        if (ratio_test >= RATIONS.length) {
            ratio_test = 0;
        }
        setAspectRatio(RATIONS[ratio_test]);
    }
    private void addTestButton(Context context) {
        Log.v(TAG, "addTestButton");

        if (testButton != null) {
            this.removeView(testButton);
            testButton = null;
        }
        if (testButton == null) {
            Button button = new Button(context);
            button.setText("AspectRatioTest");
            button.setAlpha(0.8f);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ratio_test++;
                    if (ratio_test >= RATIONS.length) {
                        ratio_test = 0;
                    }
                    setAspectRatio(RATIONS[ratio_test]);
                }
            });
            FrameLayout.LayoutParams lp;
            lp = new FrameLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER);
            this.addView(button, lp);
            testButton = button;
        }
    }

	private Handler mSurfaceTextureListenerHandler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			if(0 == message.what) {
				layoutPreview(mAspectRatio);
			}
		}
	};
    private void addTextureView_1(Context context) {

        FrameLayout.LayoutParams lp;
        lp = new FrameLayout.LayoutParams(
                //LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,
                1, 1,           // *** let onSurfaceTextureSizeChanged be triggered
                Gravity.CENTER);

        TextureView textureView = new TextureView(context);
        this.addView(textureView, lp);
        if (DEBUG_VIEW_EN) {
            View coverView = new View(context);
            coverView.setBackgroundColor(0x80ff0000);
            //coverView.setBackgroundColor(0x00);
            this.addView(coverView, lp);
            mTextureCover = coverView;
        }
        if (ANIMATION_VIEW_EN) {
            FrameLayout.LayoutParams ani_view_lp = new FrameLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
                    Gravity.CENTER);
            View aniView = new View(context);
            aniView.setBackgroundColor(ANIMATION_VIEW_BACKGROUND_COLOR);
            //aniView.setBackgroundColor(0x00);

            this.addView(aniView, lp);
            mAnimationView = aniView;
        }

        //addTestButton(context); // must be upper

        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfacetTexture, int width, int height) {
                Log.v(TAG, "@" + getTid() + "/" + "** +TextureView.onSurfaceTextureAvailable:" + width + " * " + height);
                Log.v(TAG, "    mAspectRatio:" + mAspectRatio);
                if (!mIsTextureAvaiableAlready) {
					mIsTextureAvaiableAlready = true;
                    //layoutPreview(mAspectRatio); // frankie, ofter, this layout does not trigger onSurfaceTextureSizeChanged() !!!
                    mSurfaceTextureListenerHandler.sendEmptyMessageDelayed(0, 5); // post to handler
                }
                if (mRendererThread == null) {
                    mRendererThread = new HandlerThread("RendererThread", -4); // Process.THREAD_PRIORITY_DISPLAY = -4
                    mRendererThread.start();
                    mRendererHandler = new RendererHandler(mRendererThread.getLooper(), mBeautyLevel_1, mFilterType_1);
                }
                synchronized (mCreateLock) {
                    mCreateFlag = false;
                }
                mRendererHandler.obtainMessage(RENDERER_MSG_CREATE, width, height, surfacetTexture).sendToTarget();
                synchronized (mCreateLock) {
                    long timeouts = System.currentTimeMillis() + 3*1000;
                    try {
                        while(timeouts > System.currentTimeMillis() && mCreateFlag == false) {
                            mCreateLock.wait(100);
                        }
                    } catch (InterruptedException ex) {
                    }    // must wait finished
                }
                Log.v(TAG, "@" + getTid() + "/" + "** -TextureView.onSurfaceTextureAvailable done !!!");
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfacetTexture, int width, int height) {
            	// frankie, note, the problem is when this get called, the camera is not sure to be opened already, so the Camera's preview size is not determined 
            	// currently, we wait on the render thread for the preview's size ready .
                Log.v(TAG, "@" + getTid() + "/" + "** +TextureView.onSurfaceTextureSizeChanged:" + width + "*" + height);
				if(mRendererHandler != null) {
                	mRendererHandler.obtainMessage(RENDERER_MSG_SIZE_CHANGED, width, height, surfacetTexture).sendToTarget();
				} else {
					Log.e(TAG, "mRendererHandler == null");
				}
				Log.v(TAG, "@" + getTid() + "/" + "** -TextureView.onSurfaceTextureSizeChanged, done");
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfacetTexture) {
                Log.v(TAG, "@" + getTid() + "/" + "** +TextureView.onSurfaceTextureDestroyed");
                if (mRendererThread != null) {
					if(mRendererHandler != null) {
						synchronized (mDestroyLock) {
							mDestroyFlag = false;
						}
	                    mRendererHandler.obtainMessage(RENDERER_MSG_DESTROY, -1, -1, surfacetTexture).sendToTarget();
	                    synchronized (mDestroyLock) {
							long timeouts = System.currentTimeMillis() + 3*1000;
	                        try {
								while(mDestroyFlag == false && timeouts > System.currentTimeMillis()) {
	                                mDestroyLock.wait(100);
	                            }
	                        } catch (InterruptedException ex) {
	                        }    // must wait do cleanup !
	                        Log.v(TAG, "mDestroyFlag : " + mDestroyFlag);
	                    }
						mRendererHandler.removeMessages(RENDERER_MSG_CREATE);
	                    mRendererHandler.removeMessages(RENDERER_MSG_SIZE_CHANGED);
	                    mRendererHandler.removeMessages(RENDERER_MSG_CAMERA_INFO_CHANGED);
	                    mRendererHandler.removeMessages(RENDERER_MSG_BEAUTY_LEVEL_CHANGED);
	                    mRendererHandler.removeMessages(RENDERER_MSG_FILTER_TYPE_CHANGED);
	                    mRendererHandler.removeMessages(RENDERER_MSG_FRAME_AVAILABLE);
	                    mRendererHandler.removeMessages(RENDERER_MSG_RENDER_BITMAP);
	                    mRendererHandler.removeMessages(RENDERER_MSG_SNAPSHOT_PREVIEW);
	                    mRendererHandler.removeMessages(RENDERER_MSG_PREVIEW_BLOCK);
	                    mRendererHandler.removeMessages(RENDERER_MSG_GET_EGLCONTEXT);
	                    mRendererHandler.removeMessages(RENDERER_MSG_SET_FRAME_CALLBACK);
	                    mRendererHandler.removeMessages(RENDERER_MSG_GET_CAMERA_PREVIEW_INFO);
	                    mRendererHandler.removeMessages(RENDERER_MSG_DESTROY);
					}
					
                    mRendererThread.quit();
                    try {
                        mRendererThread.join(500);
                    } catch (InterruptedException ex) {
                        Log.w(TAG, "mRendererThread quit timeout!");
                    }
                    mRendererThread = null;
                    mRendererHandler = null;
                }
				Log.v(TAG, "@" + getTid() + "/" + "** -TextureView.onSurfaceTextureDestroyed done");
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
                //Log.v(TAG, "@" + getTid() + "/" + "** TextureView.onSurfaceTextureUpdated");
            }
        });

        mPreviewTextureView = textureView;

    }
    public Rect getPreviewTextviewRect() {
		if(mPreviewTextureView != null) {
	        return new Rect(mPreviewTextureView.getLeft(), mPreviewTextureView.getTop(),
	                mPreviewTextureView.getRight(), mPreviewTextureView.getBottom());
		}
		return null;
    }
	
	public void setMaxPreviewSize(int width, int height) {
		mMaxPreviewWidth = width; mMaxPreviewHeight = height;
	}
    /**
     * change the TextureView's layout
     */
    public void setAspectRatio(float ratio) {
        Log.v(TAG, "setAspectRatio:" + ratio);
        if (ratio == mAspectRatio) {
            return;
        }
        mAspectRatio = ratio;

        layoutPreview(mAspectRatio);
    }

    public FrameLayout.LayoutParams layoutPreview(float ratio) {
        Log.v(TAG, "+ layoutPreview, ratio: " + ratio);
        if (mPreviewTextureView == null) {
			Log.v(TAG, "- layoutPreview, texture null!");
            return null;
        }
	    if (mMaxPreviewWidth == 0 || mMaxPreviewHeight == 0) {
			Log.v(TAG, "- layoutPreview, parent layout not ready!");
			return null;
		}
        if(!mIsTextureAvaiableAlready) {
			Log.v(TAG, "- layoutPreview, texture not ready!");
            return null;
        }
		

        FrameLayout.LayoutParams lp;

        double scaledTextureWidth, scaledTextureHeight;
        double width = mMaxPreviewWidth, height = mMaxPreviewHeight;
        Log.v(TAG, "@" + getTid() + "/" + "MaxPreview size:" + width + "*" + height + " /" + ratio);
        if (width > height) {
            if (Math.max(width, height * ratio) > width) {
                scaledTextureWidth = width;
                scaledTextureHeight = width / ratio;
            } else {
                scaledTextureWidth = height * ratio;
                scaledTextureHeight = height;
            }
        } else { // width = 1080, 1704
            if (Math.max(height, width * ratio) > height) {
                scaledTextureWidth = height / ratio;
                scaledTextureHeight = height;
            } else {
                scaledTextureWidth = width;
                scaledTextureHeight = width * ratio;
            }
        }
        Log.v(TAG, "scaledTextureWidth:" + scaledTextureWidth);
        Log.v(TAG, "scaledTextureHeight:" + scaledTextureHeight); // 1080 * 4/3 = 1440

		if(ratio != ASPECT_RATION_16_9) { // should add bottom margin
			lp = new FrameLayout.LayoutParams((int) scaledTextureWidth,
				(int) scaledTextureHeight, Gravity.CENTER);
            lp.setMargins(0,0,0,100);
		} else {
        	lp = new FrameLayout.LayoutParams((int) scaledTextureWidth,
                (int) scaledTextureHeight, Gravity.CENTER);
		}

        mPreviewTextureView.setLayoutParams(lp);
        if (mTextureCover != null) {
            mTextureCover.setLayoutParams(lp);
        }
        if (mAnimationView != null) {
            mAnimationView.setLayoutParams(lp);
        }

		Log.v(TAG, "- layoutPreview, done");
		return lp; // frankie, add 2018.01.08, return our textureview's layout parameter . 
    }

    private static int getTid() {
        return android.os.Process.myTid();
    }

    public void onResume() { // at first of onResume()
        Log.v(TAG, "onResume ... textureView" + (mPreviewTextureView!=null? "!=null" : "==null"));
		if(mPaused) {
	        mPaused = false;
	        if (mPreviewTextureView == null) {
	            addTextureView_1(mContext);
	        }
		}
		Log.v(TAG, "onResume done");
    }

    public void onPause() { // at last of onPause()
        Log.v(TAG, "onPause ...");
		if(!mPaused) {
			mSurfaceTextureListenerHandler.removeMessages(0);
			
	        mPaused = true;
	        if (mPreviewTextureView != null) {
	            this.removeView(mPreviewTextureView);
	            if (mAnimationView != null) {
	                this.removeView(mAnimationView);
	            }
	            if (mTextureCover != null) {
	                this.removeView(mTextureCover);
	            }
	            this.removeAllViews();

	            mPreviewTextureView = null;
	            mTextureCover = null;
	            mMaxPreviewWidth = mMaxPreviewHeight = 0;
	            mIsTextureAvaiableAlready = false;
	        }
		}
		Log.v(TAG, "onPause done!");
    }


    // method 1 , PhotoModule -> here   // when preview size changed, use this to update
    private static InnerCameraController.CameraPreviewInfo mCameraPreviewInfo = new InnerCameraController.CameraPreviewInfo();
    public void setCameraInfo(int width, int height, int orientation, boolean is_front) {
        Log.v(TAG, "setCameraInfo size:" + width + " * " + height + " ori:" + orientation + " is_front:" + is_front);
        synchronized (mCameraPreviewInfo) {
            mCameraPreviewInfo.width = width;
            mCameraPreviewInfo.height = height;
            mCameraPreviewInfo.orientation = orientation;
            mCameraPreviewInfo.is_front = is_front;
        }
        if (mRendererHandler != null) {
            mRendererHandler.obtainMessage(RENDERER_MSG_CAMERA_INFO_CHANGED, -1, -1, mCameraPreviewInfo).sendToTarget();
            synchronized (mCameraPreviewInfoSetupLock) {
                try {
                    mCameraPreviewInfoSetupLock.wait(3000);
                } catch (InterruptedException ex) {
                }
            }
        }
    }

    // method 2 here get from PhotoModule   // when layout size changed, use this to fetch the preview size
    InnerCameraController mInnerCameraController = null;

    public void setInnerCameraController(InnerCameraController c) {
        mInnerCameraController = c;
    }

    public void setBeautyLevel(int level) {
		if(true) { return ; } // frankie, disable this level
		
		mBeautyLevel_1 = level;
        if (mRendererHandler != null) {
            mRendererHandler.obtainMessage(RENDERER_MSG_BEAUTY_LEVEL_CHANGED, level, -1, null).sendToTarget();
        }
    }
	public void setBeautyLevel_ext(int level) {
		mBeautyLevel_1 = level;
        if (mRendererHandler != null) {
            mRendererHandler.obtainMessage(RENDERER_MSG_BEAUTY_LEVEL_CHANGED, level, -1, null).sendToTarget();
        }
	}

    //
    public void setFilterType(final int type) {
    	mFilterType_1 = type;
        if (mRendererHandler != null) {
            mRendererHandler.setFilterType(type);
        }
    }
	public int getFilterType() { // frankie, add
		return mFilterType_1;
	}

    // for support grid filter view
    private EGLContext ___returned_eglContext = null;

    public EGLContext get__eglContext() {
        if (mRendererHandler != null) {
            ___returned_eglContext = mRendererHandler.get__eglContext();
        }
        return ___returned_eglContext;
    }

    public interface FrameAvaiableCallback {
        public void onFrameAvaiable(int texture_id, SurfaceTexture surfaceTexture, float[] matrix);
    }

    public void setFrameAvaiableCallback(final FrameAvaiableCallback callback) {
        if (mRendererHandler != null) {
            mRendererHandler.setFrameAvaiableCallback(callback);
        }
    }

    public static class CameraPreviewInformation {
        int width;
        int height;
        int orientation;
        boolean isFront;

        public CameraPreviewInformation() {
        }

        public CameraPreviewInformation(int w, int h, int o, boolean i) {
            width = w;
            height = h;
            orientation = o;
            isFront = i;
        }
    }

    public CameraPreviewInformation getCameraPreviewInformation() {
        if (mRendererHandler != null) {
            return mRendererHandler.getCameraPreviewInformation();
        }
        return null;
    }

    public boolean renderBitmap_1(Bitmap bitmap, int rotate) {
        if (mRendererHandler != null) {
            mRendererHandler.renderBitmap_1(bitmap, rotate);
            return true;
        }
        return false;
    }

    public boolean previewSnapshot() {
        boolean r = false;
        if (mRendererHandler != null) {
            mRendererHandler.previewSnapshot();
            r = true;
        }
        Log.v(TAG, "previewSnapshot: " + r);
        return r;
    }

    public View getAnimationView() {
        return mAnimationView;
    }

    public boolean previewRenderBlock(boolean blocked) {
        boolean r = false;
        if (mRendererHandler != null) {
            mRendererHandler.previewRenderBlock(blocked);
            r = true;
        }
        Log.v(TAG, "previewRenderBlock: " + r);
        return r;
    }

}
