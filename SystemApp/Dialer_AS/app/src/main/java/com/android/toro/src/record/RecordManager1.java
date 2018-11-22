package com.android.toro.src.record;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;

import com.aykuttasil.callrecord.CallRecord;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By liujia
 * on 2018/11/22.
 **/
public class RecordManager1 {

    private static RecordManager1 instance;

    private CallRecord callRecord;
    private boolean isRecording = false;
    private List<CallRecordListener> listeners;

    public static RecordManager1 getInstance() {
        if(instance == null) {
            instance = new RecordManager1();
        }
        return instance;
    }

    private RecordManager1() {
        listeners = new ArrayList<>();
    }

    public boolean isRecording() {
        return isRecording;
    }

    public void start(Context context,String phoneNum) {
        if(isRecording) {
            return;
        }
        try {
            isRecording = true;

            String path = Environment.getExternalStorageDirectory().getPath();
            StringBuilder fileName = new StringBuilder();
            fileName.append(phoneNum).append("_")
                    .append(System.currentTimeMillis() + "")
                    .append(".amr");
            File file = new File(path + "/CallRecord");
            if(!file.exists()){
                file.mkdir();
            }
            callRecord = new CallRecord.Builder(context)
                    .setRecordFileName("CallRecorderTestFile")
                    .setRecordDirName("CallRecorderTest")
                    .setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)
                    .setShowSeed(true)
                    .build();
            callRecord.startCallReceiver();
            onStart();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        isRecording = false;
    }

    public void stop() {
        isRecording = false;
        if (callRecord != null) {
            callRecord.stopCallReceiver();
            onStop();
            callRecord = null;
        }
    }

    public void addRecordListener(CallRecordListener listener) {
        listeners.add(listener);
    }

    public void removeRecordListener(CallRecordListener listener) {
        listeners.remove(listener);
    }

    private void onStart() {
        for(CallRecordListener listener : listeners) {
            listener.onRecordStart();
        }
    }

    private void onStop() {
        for(CallRecordListener listener : listeners) {
            listener.onRecordStop();
        }
    }

}
