package com.toro.helper;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.RongIMClient.OnReceiveMessageListener;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

/**
 * Create By liujia
 * on 2018/10/30.
 **/
public class RongyunManager {

    private static RongyunManager instance;
    private Context appContext;
    private List<RongYunListener.OnReceiveMessageListener> onReceiveMessageListeners;
    private List<RongYunListener.ConnectionStatusListener> connectionStatusListeners;
    private boolean isFirst = true;

    public static RongyunManager getInstance() {
        if(instance == null) {
            instance = new RongyunManager();
        }
        return instance;
    }

    private RongyunManager() {
        onReceiveMessageListeners = new ArrayList<RongYunListener.OnReceiveMessageListener>();
        connectionStatusListeners = new ArrayList<RongYunListener.ConnectionStatusListener>();
    }

    public void init(Context context) {
        this.appContext = context;
        RongIMClient.init(context);
        RongIMClient.setOnReceiveMessageListener(onReceiveMessageListener);
        RongIMClient.setConnectionStatusListener(connectionStatusListener);
    }

    public void connect(String token,final RongYunListener.RongYunConnectCallback callback) {
        if(!isFirst) {
            return;
        }
        isFirst = false;
        RongIMClient.getInstance().connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                if(callback != null) {
                    callback.onTokenIncorrect();
                }
            }

            @Override
            public void onSuccess(String userid) {
                if(callback != null) {
                    callback.onSuccess(userid);
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                if(callback != null) {
                    callback.onError(errorCode.getValue());
                }
            }
        });
    }

    public void addOnReceiveMessageListener(RongYunListener.OnReceiveMessageListener listener){
        if(listener instanceof RongYunListener.OnReceiveMessageListener) {
            onReceiveMessageListeners.add(listener);
        }
    }

    public void removeOnReceiveMessageListener(RongYunListener.OnReceiveMessageListener listener){
        if(listener instanceof RongYunListener.OnReceiveMessageListener) {
            onReceiveMessageListeners.remove(listener);
        }
    }

    public void addConnectionStatusListener(RongYunListener.ConnectionStatusListener listener) {
        connectionStatusListeners.add(listener);
    }

    public void removeConnectionStatusListener(RongYunListener.ConnectionStatusListener listener) {
        connectionStatusListeners.remove(listener);
    }

    private OnReceiveMessageListener onReceiveMessageListener = new OnReceiveMessageListener() {
        @Override
        public boolean onReceived(Message message, int i) {
            String result = "";
            try{
                TextMessage tm = (TextMessage) message.getContent();
                result = tm.getContent();
            }catch (Exception e) {
                return false;
            }
            boolean flag = false;
            if(onReceiveMessageListeners != null){
                for(RongYunListener.OnReceiveMessageListener listener : onReceiveMessageListeners) {
                    if(listener.onReceived(result)){
                        flag = true;
                    }
                }
            }
            return flag;
        }
    };

    private RongIMClient.ConnectionStatusListener connectionStatusListener = new RongIMClient.ConnectionStatusListener() {
        @Override
        public void onChanged(ConnectionStatus connectionStatus) {
            if(connectionStatusListeners != null){
                for(RongYunListener.ConnectionStatusListener listener : connectionStatusListeners) {
                    listener.onChanged(connectionStatus.name());
                }
            }
        }
    };
}
