package com.android.toro.src.record;

import android.content.Context;
import android.media.MediaRecorder;

import com.aykuttasil.callrecord.CallRecord;

/**
 * Create By liujia
 * on 2018/11/23.
 **/
public class AyCallRecorder extends ToroCallRecord {

    CallRecord callRecord;

    public AyCallRecorder(Context context) {
        callRecord = new CallRecord.Builder(context)
                .setRecordFileName("CallRecorderTestFile")
                .setRecordDirName("CallRecorderTest")
                .setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)
                .setShowSeed(true)
                .build();
    }

    public void start() {
        if(callRecord != null) {
            callRecord.startCallReceiver();
        }
    }

    public void stop() {
        if(callRecord != null) {
            callRecord.stopCallReceiver();
        }
    }
}
