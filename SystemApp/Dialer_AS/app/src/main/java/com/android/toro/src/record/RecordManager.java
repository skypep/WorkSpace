package com.android.toro.src.record;

import android.content.Context;
import android.preference.PreferenceManager;

import com.android.dialer.R;
import com.android.toro.src.utils.ToroLocalDataManager;
import com.android.toro.src.utils.ToroUtils;
import com.google.wireless.gdata.data.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By liujia
 * on 2018/11/22.
 **/
public class RecordManager {

    private static RecordManager instance;

    private ToroCallRecord callRecord;
    private boolean isRecording = false;
    private List<CallRecordListener> listeners;

    public static RecordManager getInstance() {
        if(instance == null) {
            instance = new RecordManager();
        }
        return instance;
    }

    private RecordManager() {
        listeners = new ArrayList<>();
    }

    public boolean isRecording() {
        return isRecording;
    }

    public void init(Context context,String phoneNum) {
        if(callRecord == null) {
            callRecord = new SkCallRecorder(context,phoneNum);
        }
    }

    public void start(Context context,String phoneNum) {
        if(isRecording) {
            return;
        }
        try {
            isRecording = true;
            init(context,phoneNum);
            callRecord.start();
            onStart();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        isRecording = false;
    }

    public void stop() {
        if (callRecord != null && isRecording) {
            callRecord.stop();
            onStop();
            callRecord = null;
        }
        isRecording = false;
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

    public boolean needOpenRecord(Context context,String phoneNum) {
        if(StringUtils.isEmpty(phoneNum)) {
            return false;
        }
        if(PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(R.string.call_recording_key),false)){
            // 打开通话录音
            if(PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(R.string.call_recording_all_key),false)){
                //全部通话都录音
                return true;
            } else{
                //自定义
                String customNum = ToroLocalDataManager.getInstance(context).getStringByKey(context.getString(R.string.call_recording_designated_contact_key),"");
                if(ToroUtils.getToroPhoneNum(phoneNum).equals(customNum)) { // 是否为指定联系人
                    return true;
                }
                if(PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(R.string.call_recording_strange_number_key),false)){
                    // 是否陌生号码
                    if(!ToroUtils.isContact(context,phoneNum)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
