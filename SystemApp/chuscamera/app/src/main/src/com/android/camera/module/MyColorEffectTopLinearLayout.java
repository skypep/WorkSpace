package com.android.camera.module;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cs.camera.magicfilter.filter.FilterFactory;
import com.cs.camera.magicfilter.filter.FilterType;

import javax.microedition.khronos.egl.EGLContext;

import com.toro.camera.R;

/**
 * Created by THINK on 2017/6/26.
 */

public class MyColorEffectTopLinearLayout extends RelativeLayout {
    final static String TAG = "mytest.MyColorEffect";

    MyTextureViewController myTextureView01;
    MyTextureViewController myTextureView02;
    MyTextureViewController myTextureView03;
    MyTextureViewController myTextureView04;
    MyTextureViewController myTextureView05;
    MyTextureViewController myTextureView06;
    MyTextureViewController myTextureView07;
    MyTextureViewController myTextureView08;
    MyTextureViewController myTextureView09;

	MyRelativeLayout mRelativeLayout01;
	MyRelativeLayout mRelativeLayout02;
	MyRelativeLayout mRelativeLayout03;
	MyRelativeLayout mRelativeLayout04;
	MyRelativeLayout mRelativeLayout05;
	MyRelativeLayout mRelativeLayout06;
	MyRelativeLayout mRelativeLayout07;
	MyRelativeLayout mRelativeLayout08;
	MyRelativeLayout mRelativeLayout09;
	MyRelativeLayout mRelativeLayouts[] = new MyRelativeLayout[9];

    // copy from FilterType.java
//    FAIRYTALE,SUNRISE,SUNSET,WHITECAT,BLACKCAT,       SKINWHITEN,HEALTHY,SWEETS,ROMANCE,SAKURA,
//    WARM,ANTIQUE,NOSTALGIA,CALM,LATTE,                TENDER,COOL,EMERALD,EVERGREEN,CRAYON,
//    SKETCH,AMARO,BRANNAN,BROOKLYN,EARLYBIRD,          FREUD,HEFE,HUDSON,INKWELL,KEVIN,
//    LOMO,N1977,NASHVILLE,PIXAR,RISE,                  SIERRA,SUTRO,TOASTER2,VALENCIA,WALDEN,
//    XPROII,

    public static int mTextureFilterGroupIndex = 0; // 0~4  // 5 groups !
    final FilterType[] myTextureFilterTypes = new FilterType[] {

            FilterType.FAIRYTALE, FilterType.SUNRISE, FilterType.SUNSET,
            FilterType.WHITECAT, FilterType.NONE, FilterType.BLACKCAT,
            FilterType.SKINWHITEN, FilterType.HEALTHY, FilterType.SWEETS,

            FilterType.ROMANCE, FilterType.SAKURA, FilterType.WARM,
            FilterType.ANTIQUE, FilterType.NONE, FilterType.NOSTALGIA,
            FilterType.CALM, FilterType.LATTE, FilterType.TENDER,

            FilterType.COOL, FilterType.EMERALD, FilterType.EVERGREEN,
            FilterType.CRAYON, FilterType.NONE, FilterType.SKETCH,
            FilterType.AMARO, FilterType.BRANNAN, FilterType.BROOKLYN,

            FilterType.EARLYBIRD, FilterType.FREUD, FilterType.HEFE,
            FilterType.HUDSON, FilterType.NONE, FilterType.INKWELL,
            FilterType.KEVIN, FilterType.XPROII/*LOMO*/, FilterType.N1977,

            FilterType.NASHVILLE, FilterType.PIXAR, FilterType.RISE,
            FilterType.SIERRA, FilterType.NONE, FilterType.SUTRO,
            FilterType.TOASTER2, FilterType.VALENCIA, FilterType.WALDEN,
    };


    MyTextureViewController[] myTextureViews = new MyTextureViewController[9];

    public MyColorEffectTopLinearLayout(Context context) {
        super(context);
        onInstantiate();
    }
    public MyColorEffectTopLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInstantiate();
    }
    public MyColorEffectTopLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInstantiate();
    }
    public MyColorEffectTopLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        onInstantiate();
    }
    private void onInstantiate() {
        for(int i=0;i<9;i++) {
            textureFrameCounts[i] = 0;
        }
        textureDisplayReady = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }

    private Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0) {
                if(mTextureFrameReadyCallback != null) {
                    mTextureFrameReadyCallback.onReady();
                }
            }
        }
    };

    private Object textureFrameLock = new Object();
    private boolean textureDisplayReady = false;
    private int[] textureFrameCounts = new int[9];

	private static int CHECK_RENDERED_FRAME_COUNT_MIN = 1; // 2;

//    private boolean checkFrameReadyLocked(final int index) { // check if all 9 textureView is rendered at least once !
//        if(index >= 0 && index < 9) {
//            if (textureFrameCounts[index] < CHECK_RENDERED_FRAME_COUNT_MIN) {
//                return false;
//            }
//            myTextureViews[index].setOnDrawCallback(null);
//        }
//        return true;
//    }
//    private boolean checkFrameReadyLocked_2(final int index) {
//        if(textureDisplayReady) {
//            return true;
//        }
//        return checkFrameReadyLocked(index);
//    }
    private boolean checkFrameReadyLocked___All() {
        for(int i=0;i<9;i++) {
            if (textureFrameCounts[i] < CHECK_RENDERED_FRAME_COUNT_MIN) {
                return false;
            }
        }
        return true;
    }
    private void checkFrameReadyLocked___All_2() {
        if(textureDisplayReady) {
            return ;
        }
        if(checkFrameReadyLocked___All()) {
            mainHandler.sendEmptyMessageDelayed(0,10);
            textureDisplayReady = true;
        }
    }

    public interface OnFilterClickListener {
        public void onClick(int typeIndex);
    }
    private OnFilterClickListener mOnFilterClickListener = null;
    public void setOnFilterClickListener(OnFilterClickListener listener) {
        mOnFilterClickListener = listener;
    }
	private int mCurrentFilterType = 0;
	public void setCurrentFilterType(int filterType) {
		mCurrentFilterType = filterType;
		
		for(int i=0;i<mRelativeLayouts.length;i++) {
            mRelativeLayouts[i].setFilterType(myTextureFilterTypes[mTextureFilterGroupIndex*9 + i]);
			if(FilterFactory.getIntFromFilterType(myTextureFilterTypes[mTextureFilterGroupIndex*9 + i])
				== mCurrentFilterType) {
				mRelativeLayouts[i].setSelected(true);
			} else {
				mRelativeLayouts[i].setSelected(false);
			}
        }
	}

	private void initTextViews() {
        mRelativeLayout01 = (MyRelativeLayout)this.findViewById(R.id.color_effect_text_1);
        mRelativeLayout02 = (MyRelativeLayout)this.findViewById(R.id.color_effect_text_2);
        mRelativeLayout03 = (MyRelativeLayout)this.findViewById(R.id.color_effect_text_3);
        mRelativeLayout04 = (MyRelativeLayout)this.findViewById(R.id.color_effect_text_4);
        mRelativeLayout05 = (MyRelativeLayout)this.findViewById(R.id.color_effect_text_5);
        mRelativeLayout06 = (MyRelativeLayout)this.findViewById(R.id.color_effect_text_6);
        mRelativeLayout07 = (MyRelativeLayout)this.findViewById(R.id.color_effect_text_7);
        mRelativeLayout08 = (MyRelativeLayout)this.findViewById(R.id.color_effect_text_8);
        mRelativeLayout09 = (MyRelativeLayout)this.findViewById(R.id.color_effect_text_9);
        mRelativeLayouts[0] = mRelativeLayout01;
        mRelativeLayouts[1] = mRelativeLayout02;
        mRelativeLayouts[2] = mRelativeLayout03;
        mRelativeLayouts[3] = mRelativeLayout04;
        mRelativeLayouts[4] = mRelativeLayout05;
        mRelativeLayouts[5] = mRelativeLayout06;
        mRelativeLayouts[6] = mRelativeLayout07;
        mRelativeLayouts[7] = mRelativeLayout08;
        mRelativeLayouts[8] = mRelativeLayout09;

	}
    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        Log.v(TAG, "onFinishInflate");

        myTextureView01 = (MyTextureViewController)this.findViewById(R.id.color_effect_1);
        myTextureView02 = (MyTextureViewController)this.findViewById(R.id.color_effect_2);
        myTextureView03 = (MyTextureViewController)this.findViewById(R.id.color_effect_3);
        myTextureView04 = (MyTextureViewController)this.findViewById(R.id.color_effect_4);
        myTextureView05 = (MyTextureViewController)this.findViewById(R.id.color_effect_5);
        myTextureView06 = (MyTextureViewController)this.findViewById(R.id.color_effect_6);
        myTextureView07 = (MyTextureViewController)this.findViewById(R.id.color_effect_7);
        myTextureView08 = (MyTextureViewController)this.findViewById(R.id.color_effect_8);
        myTextureView09 = (MyTextureViewController)this.findViewById(R.id.color_effect_9);
        myTextureViews[0] = myTextureView01;
        myTextureViews[1] = myTextureView02;
        myTextureViews[2] = myTextureView03;
        myTextureViews[3] = myTextureView04;
        myTextureViews[4] = myTextureView05;
        myTextureViews[5] = myTextureView06;
        myTextureViews[6] = myTextureView07;
        myTextureViews[7] = myTextureView08;
        myTextureViews[8] = myTextureView09;

		initTextViews();

//        myTextureView01.setFilterType(FilterType.AMARO);
//        myTextureView02.setFilterType(FilterType.ANTIQUE);
//        myTextureView03.setFilterType(FilterType.BLACKCAT);
//        myTextureView04.setFilterType(FilterType.BRANNAN);
//        myTextureView05.setFilterType(FilterType.CALM);
//        myTextureView06.setFilterType(FilterType.BROOKLYN);
//        myTextureView07.setFilterType(FilterType.COOL);
//        myTextureView08.setFilterType(FilterType.EARLYBIRD);
//        myTextureView09.setFilterType(FilterType.INKWELL);

        for(int i=0;i<myTextureViews.length;i++) {
            myTextureViews[i].setFilterType(myTextureFilterTypes[mTextureFilterGroupIndex*9 + i]);
        }
		for(int i=0;i<mRelativeLayouts.length;i++) {
            mRelativeLayouts[i].setFilterType(myTextureFilterTypes[mTextureFilterGroupIndex*9 + i]);
			if(FilterFactory.getIntFromFilterType(myTextureFilterTypes[mTextureFilterGroupIndex*9 + i])
				== mCurrentFilterType) {
				mRelativeLayouts[i].setSelected(true);
			} else {
				mRelativeLayouts[i].setSelected(false);
			}
        }
        for(int i=0;i<myTextureViews.length;i++) {
            final int current_index = i;
            if(myTextureViews[i] instanceof MyTextureView) {
                Log.v(TAG, "myTextureViews:" + i + " instanceof MyTextureView");
                MyTextureView myTexture_ = (MyTextureView)myTextureViews[i];
                myTexture_.setOnDrawCallback(new MyTextureViewController.OnDrawCallback() {
                    @Override
                    public void onDrawFrame() {
                        Log.v(TAG, "callback onDrawFrame, " + current_index);
                        synchronized (textureFrameLock) {
                            if(textureFrameCounts[current_index] < CHECK_RENDERED_FRAME_COUNT_MIN) {
                                textureFrameCounts[current_index]++;
                            }
                            checkFrameReadyLocked___All_2();
                        }
                    }
                });
            }
			else if(myTextureViews[i] instanceof  MyTextureView2) {
                Log.v(TAG, "myTextureViews:" + i + " instanceof MyTextureView2");
                MyTextureView2 myTexture_ = (MyTextureView2)myTextureViews[i];
                myTexture_.setOnDrawCallback(new MyTextureViewController.OnDrawCallback() {
                    @Override
                    public void onDrawFrame() {
                        Log.v(TAG, "callback onDrawFrame, " + current_index);
                        synchronized (textureFrameLock) {
                            if(textureFrameCounts[current_index] < CHECK_RENDERED_FRAME_COUNT_MIN) {
                                textureFrameCounts[current_index]++;
                            }
                            checkFrameReadyLocked___All_2();
                        }
                    }
                });
            }

        }
        for(int i=0;i<myTextureViews.length;i++) {
            final int current_index = i;
            myTextureViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnFilterClickListener != null) {
						mCurrentFilterType = FilterFactory.getIntFromFilterType(
                                	myTextureFilterTypes[mTextureFilterGroupIndex*9 + current_index]);
                        mOnFilterClickListener.onClick(mCurrentFilterType);
						// 
						for(int i=0;i<mRelativeLayouts.length;i++) {
							mRelativeLayouts[i].setFilterType(myTextureFilterTypes[mTextureFilterGroupIndex*9 + i]);
							if(FilterFactory.getIntFromFilterType(myTextureFilterTypes[mTextureFilterGroupIndex*9 + i])
								== mCurrentFilterType) {
								mRelativeLayouts[i].setSelected(true);
							} else {
								mRelativeLayouts[i].setSelected(false);
							}
						}

                    }
                }
            });
        }
    }

    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.v(TAG, "onLayout l:" + l + " t:" + t + " r:" + r + " b:" + b);
        super.onLayout(changed, l, t, r, b);
    }

	////////////////// operate on all textureView

    public void setCameraPreviewInfo(int width, int height, int orientation, boolean isFront) {
        Log.v(TAG, "setCameraPreviewInfo:" + width + " * " + height + " o:" + orientation + " front:" + isFront);
        for(int i=0;i<myTextureViews.length;i++) {
            myTextureViews[i].setCameraPreviewInfo(width, height, orientation, isFront);
        }
    }
    public void setAspectRatio_ext(float ratio) {
        Log.v(TAG, "setAspectRatio_ext:" + ratio);
        for(int i=0;i<myTextureViews.length;i++) {
            myTextureViews[i].setmAspectRatio_ext(ratio);
        }
		for(int i=0;i<mRelativeLayouts.length;i++) {
            mRelativeLayouts[i].setmAspectRatio_ext(ratio);
        }
    }
    public void createThread__() {
        Log.v(TAG, "createThread__");
        for(int i=0;i<myTextureViews.length;i++) {
            myTextureViews[i].createThread__();
        }
    }
    public void createThread_withContext(EGLContext eglContext) {
        Log.v(TAG, "createThread_withContext");
        for(int i=0;i<myTextureViews.length;i++) {
            myTextureViews[i].createThread_withContext(eglContext);
        }
    }
    public interface TextureFrameReadyCallback {
        public void onReady();
    }
    private TextureFrameReadyCallback mTextureFrameReadyCallback = null;
    public void setTextureFrameReadyCallback(TextureFrameReadyCallback callback) {
        mTextureFrameReadyCallback = callback;
    }
    public void onSurfaceTextureAvaiable__(final int textureId, final SurfaceTexture st, final float[] matrix) {
        //Log.v(TAG, "onSurfaceTextureAvaiable__: " + textureId);
        for(int i=0;i<myTextureViews.length;i++) {
            myTextureViews[i].onSurfaceTextureAvaiable__(textureId, st, matrix);
        }
    }
	public void onResume() {
        Log.v(TAG, "onResume");
        for(int i=0;i<myTextureViews.length;i++) {
            myTextureViews[i].onResume();
        }
    }
    public void onPause() {
        Log.v(TAG, "onPause");
        for(int i=0;i<myTextureViews.length;i++) {
            myTextureViews[i].onPause();
        }
    }
	public void requestExitAndWait() {
        Log.v(TAG, "requestExitAndWait");
        for(int i=0;i<myTextureViews.length;i++) {
            myTextureViews[i].requestExitAndWait();
        }
    }
    public void destroyRendererThread() {
        Log.v(TAG, "destroyRendererThread");
        for(int i=0;i<myTextureViews.length;i++) {
            myTextureViews[i].destroyRendererThread();
        }
    }

}
