package com.android.toro.src.record;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;

/**
 * Create By liujia
 * on 2018/11/23.
 **/
public class SkCallRecorder extends ToroCallRecord {

    private MediaRecorder recorder;

    public SkCallRecorder(Context context,String phoneNum) {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 设置音频采集原
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); // 内容输出格式
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); // 音频编码方式

        String rootPath;
        // 判断外部存储器是否存在
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            // 外部存储器存在，返回其目录
            // Environment.getExternalStorageDirectory().getAbsolutePath();
            rootPath = Environment.getExternalStorageDirectory().getPath();
        } else {
            rootPath = context.getFilesDir().getPath();
        }

        StringBuilder fileName = new StringBuilder();
        fileName.append(phoneNum).append("_")
                .append(System.currentTimeMillis() + "")
                .append(".amr");

        String path = rootPath + "/CallRecord" + File.separator
                + fileName.toString();

        File file = new File(rootPath + "/CallRecord");
        if(!file.exists()) {
            file.mkdirs();
        }

        recorder.setOutputFile(path);

        try{
            recorder.prepare(); // 预期准备
        }catch (Exception e) {

        }

    }

    public void start() {
        if(recorder != null) {
            recorder.start();
        }
    }

    public void stop() {
        try{
            if (recorder != null) {
                recorder.stop(); // 停止刻录
                recorder.reset(); // 重设
                recorder.release(); // 刻录完成一定要释放资源
                recorder = null;
            }
        }catch (Exception e){
            recorder = null;
        }

    }

}
