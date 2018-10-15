package com.android.camera.module.ui;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import com.toro.camera.R;

/**
 * Created by THINK on 2017/8/23.
 */

public class PieMenuRendererImpl extends PieMenuRenderer {
    final static String TAG = "PieMenuRendererImpl";

    private Context mContext;
    private int mCenterX;
    private int mCenterY;
    private Paint mFocusPaint;
    private int mSuccessColor;
    private int mFailColor;
    private boolean mBlockFocus;
    private int mOuterStroke;
    private int mInnerStroke;
    private int mFocusX;
    private int mFocusY;
    private boolean mFocused;
    private volatile boolean mFocusCancelled;

    // Sometimes continuous autofocus starts and stops several times quickly.
    // These states are used to make sure the animation is run for at least some
    // time.
    private volatile int mState;
    private ValueAnimator mAnimator;
    private static final int STATE_IDLE = 0;
    private static final int STATE_FOCUSING = 1;
    private static final int STATE_FINISHING = 2;
    //private static final int STATE_PIE = 8;			// frankie, is showing pie menu
    private static final int FPS = 30;

    private static final float MATH_PI_2 = (float)(Math.PI / 2);

    private static final int DISAPPEAR_TIMEOUT = 200;

    private Bitmap mImgFocusOutterRing = null;
    private int mImgFocusOutter_ringWidth = 0;
    private int mImgFocusOutter_ringHeight = 0;
    public PieMenuRendererImpl(Context context) {
        mContext = context;
        initilize(context);
    }
    private void initilize(Context context) {
        Resources res = context.getResources();

        setVisible(false);
        mBlockFocus = false;
        mFocusPaint = new Paint();
        mFocusPaint.setAntiAlias(true);
        mFocusPaint.setColor(Color.WHITE);
        mFocusPaint.setStyle(Paint.Style.STROKE);
        mSuccessColor = Color.GREEN;
        mFailColor = Color.RED;
        mOuterStroke = res.getDimensionPixelSize(R.dimen.focus_outer_stroke);
        mInnerStroke = res.getDimensionPixelSize(R.dimen.focus_inner_stroke);

        Bitmap rawBitmap = postScale(BitmapFactory.decodeResource(res, R.drawable.chus_focus_ring_normal));//chus_img_focus_outter_ring
        mImgFocusOutterRing = rawBitmap;
        mImgFocusOutter_ringWidth = rawBitmap.getWidth();
        mImgFocusOutter_ringHeight = rawBitmap.getHeight();
    }

    private Bitmap postScale(Bitmap bitmap){
        Matrix matrix = new Matrix();
        float scale = 0.94f;
        matrix.postScale(scale, scale);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bmp;
    }

    @Override // override parent OverlayRenderer
    public boolean isVisible() {
        return super.isVisible();
    }
    @Override // override parent OverlayRenderer
    public boolean handlesTouch() {
        return true;
    }
    @Override // override parent OverlayRenderer
    public boolean onTouchEvent(MotionEvent evt) {
        return false;
    }
    @Override // override parent OverlayRenderer
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        Log.v(TAG, "layout, l:" + l + " t:" + t + " r:" + r + " b:" + b);
        mCenterX = (r - l) / 2;
        mCenterY = (b - t) / 2;

        mFocusX = mCenterX;
        mFocusY = mCenterY;
    }

    private static final int EXT_SHOW_UP_TIME = 200;
    private static final int EXT_SHOW_DOWN_TIME = 100;
    private static final float EXT_CORNER_RADIUS = 5.0f;
    private static final float EXT_LINE_WIDTH = 20;
    private static final int EXT_FPS = 60;

//    private float extFromBeginningSize = 300;	// ->
//    private float extToTargetSize = 150;
    private float extFromBeginningSize = 300;	// ->
    private float extToTargetSize = 150;

    private float extCurrentSize = extFromBeginningSize;
    /*public*/private void ext_drawFocus(Canvas canvas) {
        if (mBlockFocus) {
            return;
        }
        mFocusPaint.setStrokeWidth(mOuterStroke);

        int color = mFocusPaint.getColor();
        if (mState == STATE_FINISHING) {
            mFocusPaint.setColor(mFocused ? mSuccessColor : mFailColor);
            if(mFocused){
                Bitmap rawBitmap = postScale(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.chus_focus_ring_success));
                mImgFocusOutterRing = rawBitmap;
            }else {
                Bitmap rawBitmap = postScale(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.chus_focus_ring_fail));
                mImgFocusOutterRing = rawBitmap;
            }
        }else {
              Bitmap rawBitmap = postScale(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.chus_focus_ring_normal));
              mImgFocusOutterRing = rawBitmap;
        }

        mFocusPaint.setStrokeWidth(mInnerStroke);

//		 draw a simple rectangle
//        RectF rectF = new RectF();
//        rectF.left = (float)mFocusX - extCurrentSize/2;
//        rectF.right = (float)mFocusX + extCurrentSize/2;
//        rectF.top = (float)mFocusY - extCurrentSize/2;
//        rectF.bottom = (float)mFocusY + extCurrentSize/2;
//        canvas.drawRoundRect(rectF, EXT_CORNER_RADIUS, EXT_CORNER_RADIUS, mFocusPaint);
//        float point_x = (float)mFocusX;
//        float point_y = (float)mFocusY - extCurrentSize/2;
//        float point_2_x = point_x;
//        float point_2_y = point_y + EXT_LINE_WIDTH;
//        canvas.drawLine(point_x, point_y, point_2_x, point_2_y, mFocusPaint);
//        point_x = (float)mFocusX;
//        point_y = (float)mFocusY + extCurrentSize/2;
//        point_2_x = point_x;
//        point_2_y = point_y - EXT_LINE_WIDTH;
//        canvas.drawLine(point_x, point_y, point_2_x, point_2_y, mFocusPaint);
//
//        point_x = (float)mFocusX - extCurrentSize/2;
//        point_y = (float)mFocusY;
//        point_2_x = point_x + EXT_LINE_WIDTH;
//        point_2_y = point_y;
//        canvas.drawLine(point_x, point_y, point_2_x, point_2_y, mFocusPaint);
//        point_x = (float)mFocusX + extCurrentSize/2;
//        point_y = (float)mFocusY;
//        point_2_x = point_x - EXT_LINE_WIDTH;
//        point_2_y = point_y;
//        canvas.drawLine(point_x, point_y, point_2_x, point_2_y, mFocusPaint);

        canvas.save();

        float scale_factor = extCurrentSize/extToTargetSize;
        canvas.scale(scale_factor, scale_factor, mFocusX, mFocusY);
        canvas.drawBitmap(mImgFocusOutterRing, mFocusX - mImgFocusOutter_ringWidth/2, mFocusY-mImgFocusOutter_ringHeight/2, mFocusPaint);
        canvas.restore();

        mFocusPaint.setColor(color);
    }

    private Runnable mDisappear = new Runnable() {
        @Override
        public void run() {
            setVisible(false);
            mFocusX = mCenterX;
            mFocusY = mCenterY;
            mState = STATE_IDLE;
            mFocused = false;
        }
    };
    private void ext_startAnimation(long duration, boolean timeout, float toSize) {
        ext_startAnimation(duration, timeout, extCurrentSize, toSize);
    }
    private void ext_startAnimation(long duration, final boolean timeout, float fromSize, float toSize) {
        setVisible(true);

        if (mAnimator != null) { mAnimator.cancel(); }

        mAnimator = ValueAnimator.ofFloat(fromSize, toSize);
        mAnimator.setDuration(duration);
        mAnimator.setInterpolator(null);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private long frames;
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (frames < animation.getCurrentPlayTime() * EXT_FPS / 1000) {
                    update();
                    ++frames;
                    extCurrentSize = Math.round((Float) animation.getAnimatedValue());
                }
            }
        });
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                if (timeout && !mFocusCancelled) {
                    mOverlay.postDelayed(mDisappear, DISAPPEAR_TIMEOUT);
                }
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
            @Override
            public void onAnimationCancel(Animator arg0) {
            }
        });
        mAnimator.start();

        update();
    }
    private void cancelFocus() {
        mFocusCancelled = true;
        mOverlay.removeCallbacks(mDisappear);
        if (mAnimator != null && mAnimator.isStarted()) {
            mAnimator.cancel();
        }
        mFocusCancelled = false;
        mFocused = false;
        mState = STATE_IDLE;
    }

    @Override
    public void showStart() {
        Log.v(TAG, "showStart");
        cancelFocus();
        ext_startAnimation(EXT_SHOW_UP_TIME, false, extFromBeginningSize, extToTargetSize);
        mState = STATE_FOCUSING;
    }
    @Override
    public void showSuccess(boolean timeout) {
        Log.v(TAG, "showSuccess");
        if (mState == STATE_FOCUSING) {
            ext_startAnimation(EXT_SHOW_DOWN_TIME, timeout, extToTargetSize);
            mState = STATE_FINISHING;
            mFocused = true;
        }
    }
    @Override
    public void showFail(boolean timeout) {
        Log.v(TAG, "showFail");
        if (mState == STATE_FOCUSING) {
            ext_startAnimation(EXT_SHOW_DOWN_TIME, timeout, extToTargetSize);
            mState = STATE_FINISHING;
            mFocused = false;
        }
    }
    @Override
    public void clear() {
        Log.v(TAG, "clear");
        cancelFocus();
        mOverlay.post(mDisappear);
    }

    // following is for menu show/hide
    // mListener.onPieOpened
    // mListener.onPieClosed
    @Override
    public boolean showsItems() {
        return false;
    }
    @Override
    public boolean isOpen() {
        return false;
    }
    @Override
    public void showInCenter() { }
    @Override
    public void hide() { }

    //
    @Override
    public void setBlockFocus(boolean blocked) {
        Log.v(TAG, "setBlockFocus:" + blocked);
        mBlockFocus = blocked;
        if (blocked) {
            clear();
        }
    }
    @Override
    public void setFocus(int x, int y) {
        Log.v(TAG, "setFocus(" + x + "," + y + ")");
        mFocusX = x;
        mFocusY = y;
    }

    @Override
    public void onDraw(Canvas canvas) {
        int state = canvas.save();
        ext_drawFocus(canvas); // frankie,
        if (mState == STATE_FINISHING) {
            canvas.restoreToCount(state);
            return;
        }
        return ;
    }
}
