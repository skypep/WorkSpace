//package com.android.toro.src.record;
//
//import android.content.Context;
//import android.media.MediaRecorder;
//import android.os.Environment;
//
//import com.aykuttasil.callrecord.CallRecord;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Create By liujia
// * on 2018/11/22.
// **/
//public class RecordManager1 {
//
//    private static RecordManager1 instance;
//
//    private MediaRecorder recorder;
//    private boolean isRecording = false;
//    private List<CallRecordListener> listeners;
//
//    public static RecordManager1 getInstance() {
//        if(instance == null) {
//            instance = new RecordManager1();
//        }
//        return instance;
//    }
//
//    private RecordManager1() {
//        listeners = new ArrayList<>();
//    }
//
//    public boolean isRecording() {
//        return isRecording;
//    }
//
//    public void start(Context context,String phoneNum) {
//        if(isRecording) {
//            return;
//        }
//        try {
//            isRecording = true;
//            recorder = new MediaRecorder();
//            recorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 设置音频采集原
//            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); // 内容输出格式
//            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); // 音频编码方式
//
//            String rootPath;
//            // 判断外部存储器是否存在
//            if (android.os.Environment.getExternalStorageState().equals(
//                    android.os.Environment.MEDIA_MOUNTED)) {
//                // 外部存储器存在，返回其目录
//                // Environment.getExternalStorageDirectory().getAbsolutePath();
//                rootPath = Environment.getExternalStorageDirectory().getPath();
//            } else {
//                rootPath = context.getFilesDir().getPath();
//            }
//
//            StringBuilder fileName = new StringBuilder();
//            fileName.append(phoneNum).append("_")
//                    .append(System.currentTimeMillis() + "")
//                    .append(".amr");
//
//            String path = rootPath + "/CallRecord" + File.separator
//                    + fileName.toString();
//
//            File file = new File(rootPath + "/CallRecord");
//            if(!file.exists()) {
//                file.mkdirs();
//            }
//
//            recorder.setOutputFile(path);
//
//            recorder.prepare(); // 预期准备
//            recorder.start();
//            onStart();
//            return;
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        isRecording = false;
//    }
//
//    public void stop() {
//        if (recorder != null && isRecording) {
//            recorder.stop(); // 停止刻录
//            recorder.reset(); // 重设
//            recorder.release(); // 刻录完成一定要释放资源
//            recorder = null;
//            isRecording = false;
//            onStop();
//        }
//    }
//
//    public void addRecordListener(CallRecordListener listener) {
//        listeners.add(listener);
//    }
//
//    public void removeRecordListener(CallRecordListener listener) {
//        listeners.remove(listener);
//    }
//
//    private void onStart() {
//        for(CallRecordListener listener : listeners) {
//            listener.onRecordStart();
//        }
//    }
//
//    private void onStop() {
//        for(CallRecordListener listener : listeners) {
//            listener.onRecordStop();
//        }
//    }
//
//}
