package com.cs.camera.magicfilter;

import android.content.Context;
import android.os.Environment;

import com.cs.camera.magicfilter.widget.MagicBaseView;

/**
 * Created by why8222 on 2016/2/26.
 */
public class MagicContext {

    public static Context context;
    public static int beautyLevel = 5;
    public static MagicBaseView magicBaseView;

    public static String videoPath = Environment.getExternalStorageDirectory().getPath();
    public static String videoName = "MagicCamera_test.mp4";


    private MagicContext() {}
}
