package com.android.camera.libzxing.zxing.activity;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;

import com.google.zxing.Result;

/**
 * Created by THINK on 2017/6/16.
 */

public interface ActivityController {
    public Handler getHandler();
    public Rect getCropRect();
    public Point getPreviewResolution();

    public void handleDecode(Result rawResult, Bundle bundle);

    public void setResult_(int resultCode, Intent data);
    public void finish_();
}
