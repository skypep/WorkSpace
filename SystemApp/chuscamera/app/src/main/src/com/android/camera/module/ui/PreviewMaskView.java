package com.android.camera.module.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2018/1/31.
 */

public class PreviewMaskView extends View {
    public PreviewMaskView(Context context) {
        super(context);
    }

    public PreviewMaskView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PreviewMaskView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PreviewMaskView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // consume this event !
        return true;
    }
}
