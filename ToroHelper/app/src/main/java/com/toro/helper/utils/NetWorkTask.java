package com.toro.helper.utils;

import android.os.AsyncTask;

import com.toro.helper.utils.okhttp.OkHttp;

import org.json.JSONObject;

import java.util.ArrayList;

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
        String token;
        JSONObject pobj;
        switch (mTag) {
            case ConnectManager.GET_SCODE_FOR_LOGIN:
            case ConnectManager.QUICK_LOGIN:
            case ConnectManager.GET_SCODE_FOR_REGISTER:
            case ConnectManager.REGISTER:
            case ConnectManager.GET_NUMBER_CAPTCHAR:
            case ConnectManager.LOGIN:
            case ConnectManager.GET_SCODE_FOR_RESET_PWD:
            case ConnectManager.RESET_PWD:
            case ConnectManager.VERIFY_TOKEN:
                JSONObject obj = (JSONObject) params[3];
                return HttpUtils.doPost(url,obj);
            case ConnectManager.GET_PHOTO_LIST:
            case ConnectManager.SUBMIT_PHOTO_LIST:
            case ConnectManager.FAMILY_MENBER_LIST:
            case ConnectManager.ADD_FAMILY_MENBER:
            case ConnectManager.REFRESH_TOKEN:
            case ConnectManager.DELETE_PHOTO_LIST:
                pobj = (JSONObject) params[3];
                token = (String) params[4];
                return HttpUtils.doTokenPost(url,pobj,token);
            case ConnectManager.UPLOAD_PHOTO_LIST: //  此case 无效 直接调用okhttp
                ArrayList<String> images = (ArrayList<String>) params[3];
                token = (String) params[4];
                return ToroHttp.uploadFile(url,images,token);
            case ConnectManager.GET_LOGIN_USERE_INFO:
            case ConnectManager.GET_PHOTO_LIST_BY_UID:
            case ConnectManager.GET_USER_PHONE_STATUS:
                token = (String) params[3];
                return OkHttp.doTokenGet(url,token);
            case ConnectManager.SUBMIT_PERSIONAL_DETAILS:
            case ConnectManager.AGREEN_MEMBER:
            case ConnectManager.FIX_REMARK_NAME:
                pobj = (JSONObject) params[3];
                token = (String) params[4];
                return OkHttp.doTokenPut(url,pobj,token);
            case ConnectManager.DOWNLOAD_IMAGE:// 此case 无效 直接调用okhttp
                return null;
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
