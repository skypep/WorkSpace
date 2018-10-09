package com.android.camera.module;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/1/25.
 */

public class MyBackground extends View {

    public MyBackground(Context context) {
        super(context);
    }

    public MyBackground(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBackground(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyBackground(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private boolean mSelected = false;
    public void setSelected(boolean selected) {
        mSelected = selected;
        this.invalidate();
    }
    private int getSize__(int defaultSize, int measureSpec) {
        int rSize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {
                rSize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {
                rSize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {
                rSize = size;
                break;
            }
        }
        return rSize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getSize__(0, widthMeasureSpec);
        int height = getSize__(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        int r = getMeasuredWidth() / 2;
//        Paint paint = new Paint();
//        paint.setColor(Color.GREEN);
//        canvas.drawCircle(getLeft() + r, getTop() + r, r, paint);
        if(mSelected) {
            //canvas.drawARGB(128, 255, 255, 0);
            Paint paint = new Paint();
            paint.setStrokeWidth(6);
            paint.setColor(Color.RED);
            //Rect rect = new Rect(0,0, getRight() - getLeft(), getBottom() - getTop());
            //canvas.drawRect(rect, paint);
            canvas.drawLine(0,0, getRight() - getLeft(), 0, paint);
            canvas.drawLine(getRight() - getLeft(),0, getRight() - getLeft(), getBottom() - getTop(), paint);
            canvas.drawLine(0,0, 0, getBottom() - getTop(), paint);
            canvas.drawLine(0,getBottom() - getTop(), getRight() - getLeft(), getBottom() - getTop(), paint);
        } else {
            canvas.drawARGB(0, 0, 0, 0);
        }
    }

}
