package com.toro.helper.utils;

import android.os.AsyncTask;

import org.json.JSONObject;

/**
 * Created by SK on 2017/10/8.
 */

public class NetWorkTask extends AsyncTask<Object, Integer, Object> {
    private int mTag;
    private OnHttpDataUpdateListener mBindData;

    @Override
    protected Object doInBackground(Object[] params) {
        if (params[0] instanceof OnHttpDataUpdateListener) {
            mBindData = (OnHttpDataUpdateListener) params[0];
        }

        mTag = (Integer) params[1];
        String url = (String)params[2];
        switch (mTag) {
            case ConnectManager.GET_SCODE_FOR_LOGIN:
            case ConnectManager.QUICK_LOGIN:
            case ConnectManager.GET_SCODE_FOR_REGISTER:
            case ConnectManager.REGISTER:
            case ConnectManager.GET_NUMBER_CAPTCHAR:
            case ConnectManager.LOGIN:
            case ConnectManager.GET_SCODE_FOR_RESET_PWD:
                JSONObject obj = (JSONObject) params[3];
                return HttpUtils.doPost(url,obj);
            case ConnectManager.GET_PHOTO_LIST:
                JSONObject pobj = (JSONObject) params[3];
                String token = (String) params[4];
                return HttpUtils.doPostForToken(url,pobj,token);
            default:
                break;
        }
            return null;
    }

    @Override
    protected void onPostExecute(Object result) {
        if (mBindData != null) {
            mBindData.bindData(mTag, result);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
