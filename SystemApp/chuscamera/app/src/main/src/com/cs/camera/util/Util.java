package com.cs.camera.util;

/**
 * Created by THINK on 2017/6/5.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

public class Util {

    static long last_system_milli = -1;
    static long last_fps_measured = 0;
    public static void printFPS(String printTag) {
        if(last_system_milli < 0) {
            last_system_milli = System.currentTimeMillis() + 1000;
            last_fps_measured = 1;
            return ;
        }
        last_fps_measured++;
        long current = System.currentTimeMillis();
        if(current >= last_system_milli) {
            last_system_milli = System.currentTimeMillis() + 1000;
            Log.v(printTag, "fps measured = " + last_fps_measured);
            last_fps_measured = 0;
        }
    }

    public static void printCallStatck(String printTag) {
        Throwable ex = new Throwable();
        StackTraceElement[] stackElements = ex.getStackTrace();
        Log.v(printTag, "----------- callstack start");
        if (stackElements != null) {
            for (int i = 1; i < stackElements.length; i++) {
                StackTraceElement ste = stackElements[i];
                Log.v(printTag, "" + ste.getClassName() + "." + ste.getMethodName() + " line(" + ste.getLineNumber() + ")");
            }
        }
        Log.v(printTag, "----------- callstack end");
    }


    public static void showErrDialog(Context context, String title, String message) {
        if(context == null) {
            throw new RuntimeException("showErrDialog, context == null");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });
        builder.show();
    }
}
