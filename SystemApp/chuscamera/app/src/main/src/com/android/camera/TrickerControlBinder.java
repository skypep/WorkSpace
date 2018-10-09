//package org.fra.camera.test;
package com.android.camera;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import org.fra.camera.tricker.intf.IMyTrickerCallback;
import org.fra.camera.tricker.intf.IMyTrickerController;

/**
 * Created by Administrator on 2018/1/30.
 */

public class TrickerControlBinder {
    final static String TAG = "Main.ControlBinder";
   public final static boolean TrickerControlBinder__ENABLE = false ;//true; fix 4619

    public static class MyTrickerControlWrapper {
        static final String TAG = "Main.ControlWrapper";

        private IMyTrickerController mTarget;

        // to see what the wrapper should impl !!!
        class IMyTrickerControllerWrapper extends IMyTrickerController.Stub {
            @Override
            public int setBeautyLevel(int session_type, int level) throws RemoteException {
                return 0;
            }

            @Override
            public int getBeautyLevel(int session_type) throws RemoteException {
                return 0;
            }

            @Override
            public int setFilterIndex(int session_type, int index) throws RemoteException {
                return 0;
            }

            @Override
            public int getFilterIndex(int session_type) throws RemoteException {
                return 0;
            }

            @Override
            public void setCallback(IMyTrickerCallback cb) throws RemoteException {

            }

            @Override
            public void sanityCheck() throws RemoteException {

            }
        }


        public MyTrickerControlWrapper(IMyTrickerController target) {
            mTarget = target;
        }

        public int setBeautyLevel(int session_type, int level) {
            try {
                return mTarget.setBeautyLevel(session_type, level);
            } catch(RemoteException re) {
                Log.e(TAG, "RE:" + re.getMessage());
            }
            return -1;
        }
        public int getBeautyLevel(int session_type) {
            try {
                return mTarget.getBeautyLevel(session_type);
            } catch(RemoteException re) {
                Log.e(TAG, "RE:" + re.getMessage());
            }
            return -1;
        }
        public int setFilterIndex(int session_type, int index) {
            try {
                return mTarget.setFilterIndex(session_type, index);
            } catch(RemoteException re) {
                Log.e(TAG, "RE:" + re.getMessage());
            }
            return -1;
        }
        public int getFilterIndex(int session_type) {
            try {
                return mTarget.getFilterIndex(session_type);
            } catch(RemoteException re) {
                Log.e(TAG, "RE:" + re.getMessage());
            }
            return -1;
        }
        public void setCallback(IMyTrickerCallback cb) {
            try {
                mTarget.setCallback(cb);
            } catch(RemoteException re) {
                Log.e(TAG, "RE:" + re.getMessage());
            }
        }
        public void sanityCheck() {
            try {
                mTarget.sanityCheck();
            } catch(RemoteException re) {
                Log.e(TAG, "RE:" + re.getMessage());
            }
        }
    }


    static final ComponentName mTrickerComponent = new ComponentName("org.fra.camera.tricker",
            "org.fra.camera.tricker.TrickerWrapperService");

    private Context mContext;
    private boolean mServiceBounding = false;
    private boolean mPauseFlagService = true;
    private boolean mBoundService = false;
    private MyTrickerControlWrapper mIMyTrickerControlWrapper = null;
    private IMyTrickerCallback.Stub mIMyTrickerCallback = new IMyTrickerCallback.Stub() {
        @Override
        public int onTrickerCommandExecutedDone(String command, String[] args) throws RemoteException {
            Log.v(TAG, "[" + command + "] args.length:" + (args != null ? "" + args.length : "==null"));
            return 0;
        }
        @Override
        public int onTrickerEvent(int code, String[] cooked) throws RemoteException {
            Log.v(TAG, "{" + code + "} coocked.length:" + (cooked != null ? "" + cooked.length : "==null"));
            return 0;
        }
    };

    private ServiceConnection mSc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mServiceBounding = false;
            mBoundService = true;
            mIMyTrickerControlWrapper = new MyTrickerControlWrapper(IMyTrickerController.Stub.asInterface(iBinder));
            Log.w(TAG, "** onServiceConnected");
            if(mPauseFlagService == false) { // is resumed !
                mIMyTrickerControlWrapper.setCallback(mIMyTrickerCallback);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.w(TAG, "** onServiceDisconnected");
            mServiceBounding = false;
            mBoundService = false;
            mIMyTrickerControlWrapper = null;
        }
    };
    private void bindTrickerService(Context context) {
        if(TrickerControlBinder__ENABLE == false) {return ; } //

        if(mServiceBounding == false) {
            mServiceBounding = true;
            Intent intent = new Intent();
            intent.setComponent(mTrickerComponent);
            context.bindService(intent, mSc, Context.BIND_AUTO_CREATE);
        }
    }
    private void unbindTrickerService(Context context) {
        if(TrickerControlBinder__ENABLE == false) {return ; } //

        if(mServiceBounding == false && mBoundService && mIMyTrickerControlWrapper != null) {
            context.unbindService(mSc);
        }
    }

    public TrickerControlBinder(Context context) {
        Log.v(TAG, "-> new");
        mContext = context;
        bindTrickerService(mContext);
    }

    public MyTrickerControlWrapper getControlWrapper() {
        return mIMyTrickerControlWrapper;
    }
    public MyTrickerControlWrapper getWrapper() {
        return mIMyTrickerControlWrapper;
    }

    public void onResume() {
        Log.v(TAG, "-> onResume");
        bindTrickerService(mContext);
        mPauseFlagService = false;
        if(mBoundService && mIMyTrickerControlWrapper != null) {
            mIMyTrickerControlWrapper.setCallback(mIMyTrickerCallback);
        }
    }
    public void onPause() {
        Log.v(TAG, "-> onPause");
        mPauseFlagService = true;
    }
    public void onDestroy() {
        Log.v(TAG, "-> onDestroy");
        unbindTrickerService(mContext);
    }

}
