package com.android.camera.module;

/**
 * Created by THINK on 2017/6/24.
 */

public interface InnerCameraController {
    public static class CameraPreviewInfo {
        public int width;
        public int height;
        public int orientation;
        public boolean is_front;
    }

    public CameraPreviewInfo getCameraPreviewInfo();

}
