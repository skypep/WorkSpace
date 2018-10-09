package com.android.camera.util;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by THINK on 2017/6/13.
 */

public class MiscUtil {

    public static String int2Hex(int i) {
        if(i >= 0) {
            return Integer.toHexString(i);
        }
        return "" + -1;
    }

    /**/
    public static interface InterpolatedTimeListener {
        public void interpolatedTime(float interpolatedTime);
    }
    public static class RotateAnimation extends Animation {
        public static final boolean DEBUG = false;
        public static final boolean ROTATE_DECREASE = true;
        public static final boolean ROTATE_INCREASE = false;
        public static final float DEPTH_Z = 310.0f;
        public static final long DURATION = 500l; // frankie // 800l;
        private final boolean type;
        private final float centerX;
        private final float centerY;
        private Camera camera;
        private InterpolatedTimeListener listener;

        public RotateAnimation(float cX, float cY, boolean type) {
            centerX = cX;
            centerY = cY;
            this.type = type;
            setDuration(DURATION);
        }

        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            camera = new Camera();
        }

        public void setInterpolatedTimeListener(InterpolatedTimeListener listener) {
            this.listener = listener;
        }

        protected void applyTransformation(float interpolatedTime, Transformation transformation) {
            // interpolatedTime:动画进度值，范围为[0.0f,10.f]
            if (listener != null) {
                listener.interpolatedTime(interpolatedTime);
            }
            float from = 0.0f, to = 0.0f;
            if (type == ROTATE_DECREASE) {
                from = 0.0f;
                to = 180.0f;
            } else if (type == ROTATE_INCREASE) {
                from = 360.0f;
                to = 180.0f;
            }
            float degree = from + (to - from) * interpolatedTime;
            boolean overHalf = (interpolatedTime > 0.5f);
            if (overHalf) {
                degree = degree - 180;
            }
            // float depth = 0.0f;
            float depth = (0.5f - Math.abs(interpolatedTime - 0.5f)) * DEPTH_Z;
            final Matrix matrix = transformation.getMatrix();
            camera.save();
            camera.translate(0.0f, 0.0f, depth);
            camera.rotateY(degree);
            camera.getMatrix(matrix);
            camera.restore();
            if (DEBUG) {
                if (overHalf) {
                    matrix.preTranslate(-centerX * 2, -centerY);
                    matrix.postTranslate(centerX * 2, centerY);
                }
            } else {
                matrix.preTranslate(-centerX, -centerY);
                matrix.postTranslate(centerX, centerY);
            }
        }
    }

}
