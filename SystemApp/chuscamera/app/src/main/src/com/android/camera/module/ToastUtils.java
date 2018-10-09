package com.android.camera.module;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chus.camera.R;

/**
 * Created by THINK on 2017/8/23.
 */

public class ToastUtils {
    private ToastUtils() {

    }

    public static void showToast(Context context, String toastInfo) {
        if (null == context || TextUtils.isEmpty(toastInfo)) {
            return;
        }
        if (null == mToast) {
            mToast = Toast.makeText(context, toastInfo, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 380);
            mToast.setText(toastInfo);
        } else {
            mToast.setText(toastInfo);
        }
        mToast.show();
    }

    public static void hideToast() {
        mToast.cancel();
    }

    private static Toast mToast = null;
}
