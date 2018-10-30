package com.toro.helper;

import android.content.Context;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;

/**
 * Create By liujia
 * on 2018/10/30.
 **/
public class RongyunManager {

    public interface RongYunConnectCallback {
        /**
         * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
         *                            2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
         */
        public void onTokenIncorrect();

        /**
         * 连接融云成功
         * @param userid 当前 token 对应的用户 id
         */
        public void onSuccess(String userid);

        /**
         * 连接融云失败
         * @param errorCode 错误码，可到官网 查看错误码对应的注释
         */
        public void onError(int errorCode);
    }

    public interface OnReceiveMessageListener{
        public boolean onReceived(String message, int i);
    }

    public interface ConnectionStatusListener{
        public void onChanged(String message);
    }

    public static void init(Context context) {
        RongIMClient.init(context);
    }

    public static void connect(Context context,String token,final RongYunConnectCallback callback) {
        RongIMClient.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                            2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {
                callback.onTokenIncorrect();
            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                callback.onSuccess(userid);
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                callback.onError(errorCode.getValue());
            }
        });
    }

    public static void setOnReceiveMessageListener(final OnReceiveMessageListener listener) {
        RongIMClient.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                return listener.onReceived(message.toString(),i);
            }
        });
    }

    public static void setConnectionStatusListener(final ConnectionStatusListener listener){
        RongIMClient.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
            @Override
            public void onChanged(ConnectionStatus connectionStatus) {
                listener.onChanged(connectionStatus.getMessage());
            }
        });
    }
}
