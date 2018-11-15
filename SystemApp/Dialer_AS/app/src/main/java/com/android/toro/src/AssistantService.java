package com.android.toro.src;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telecom.TelecomManager;

import com.android.dialer.app.DialtactsActivity;
import com.android.dialer.callintent.CallInitiationType;
import com.android.dialer.callintent.CallIntentBuilder;
import com.android.dialer.util.DialerUtils;
import com.android.incallui.InCallActivity;
import com.android.toro.src.utils.ToroLocalDataManager;

import java.lang.reflect.Method;

/**
 * Create By liujia
 * on 2018/11/12.
 **/
public class AssistantService extends Service {

    private static final String CALL_NUMBER = "call_number";// 使用扬声器打电话
    private static final String CALL_NUMBER_BY_LOUNDSPEAKER = "call_number_by_loundspeaker";// 使用扬声器打电话
    private static final String ANSWER_BY_LOUNDSPEAKER = "answer_by_loundspeaker";// 使用扬声器接电话
    private static final String ANSWER_CALL = "answer_call";// 使用扬声器接电话
    private static final String SHOW_CALL_LOG = "show_call_log";// 显示通话记录

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
            case CALL_NUMBER:
                call(value);
                break;
            case CALL_NUMBER_BY_LOUNDSPEAKER:
                callByLoundSpeaker(value);
                break;
            case ANSWER_CALL:
                answer();
                break;
            case ANSWER_BY_LOUNDSPEAKER:
                answerByLoundspeaker();
                break;
            case SHOW_CALL_LOG:
                showCalllog();
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 显示通话记录
     */
    private void showCalllog() {
        startActivity(DialtactsActivity.createIntent(this,DialtactsActivity.ASSISTANT_COMMAND_SHOW_CALL_LOG));
    }

    /**
     * 用扬声器接听电话
     */
    private void answerByLoundspeaker() {
        ToroLocalDataManager.getInstance(this).setAssintantOpenLoundspeaker(true);
        answer();
    }

    /**
     * 接听电话
     */
    private void answer(){
        try{
            TelecomManager telecomManager =
                    (TelecomManager)getSystemService(Context.TELECOM_SERVICE);
            Method method = Class.forName("android.telecom.TelecomManager").getMethod("acceptRingingCall");
            method.invoke(telecomManager);
            Intent intent =
                    InCallActivity.getIntent(
                            this, false /* showDialpad */, false /* newOutgoingCall */, true);
            startActivity(intent);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用扬声器拨打电话
     * @param number
     */
    private void callByLoundSpeaker(String number) {
        ToroLocalDataManager.getInstance(this).setAssintantOpenLoundspeaker(true);
        call(number);
    }

    /**
     * 拨打电话
     * @param number
     */
    private void call(String number) {
        DialerUtils.startActivityWithErrorToast(
                this,
                new CallIntentBuilder(number, CallInitiationType.Type.QUICK_CONTACTS).build());
    }
}
