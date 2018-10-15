package com.android.camera.module.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.toro.camera.R;

/**
 * Created by THINK on 2017/7/11.
 */

public class PreviewDebugDialog extends AlertDialog {
    public PreviewDebugDialog(Context context) {
        super(context);
    }
    public PreviewDebugDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
    public PreviewDebugDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_debug_dialog);
    }


}
