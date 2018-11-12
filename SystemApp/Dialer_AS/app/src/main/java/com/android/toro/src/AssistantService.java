package com.android.toro.src;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.android.dialer.callintent.CallInitiationType;
import com.android.dialer.callintent.CallIntentBuilder;
import com.android.dialer.util.DialerUtils;

/**
 * Create By liujia
 * on 2018/11/12.
 **/
public class AssistantService extends Service {

    private static final String CALL_BY_LOUNDSPEAKER = "call_by_loundspeaker";// 使用扬声器打电话

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String command = intent.getStringExtra("command");
        String value = intent.getStringExtra("value");
        switch (command) {
            case CALL_BY_LOUNDSPEAKER:
                call(value);
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void call(String number) {
        DialerUtils.startActivityWithErrorToast(
                this,
                new CallIntentBuilder(number, CallInitiationType.Type.QUICK_CONTACTS).build());
    }
}
