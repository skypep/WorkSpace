package com.toro.helper.utils;

import android.util.JsonReader;

import com.toro.helper.modle.photo.PhotoItem;
import com.toro.helper.utils.okhttp.OkHttp;
import com.toro.helper.utils.okhttp.mutifile.listener.impl.UIProgressListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By liujia
 * on 2018/10/22.
 **/
public class ConnectManager {

    private static final int USER_TYPE = 2; // 0：管理员,1：老人机用户，2：子女端用户，3：未注册用户|

    public static final int GET_SCODE_FOR_LOGIN = 2;
    public static final int QUICK_LOGIN = 3;
    public static final int GET_SCODE_FOR_REGISTER = 4;
    public static final int REGISTER = 5;
    public static final int GET_NUMBER_CAPTCHAR = 6;
    public static final int LOGIN = 7;
    public static final int GET_SCODE_FOR_RESET_PWD = 8;
    public static final int RESET_PWD = 9;
    public static final int GET_PHOTO_LIST = 10;
    public static final int UPLOAD_PHOTO_LIST = 11;
    public static final int SUBMIT_PHOTO_LIST = 12;
    public static final int DOWNLOAD_IMAGE = 13;
    public static final int FAMILY_MENBER_LIST = 14;
    public static final int ADD_FAMILY_MENBER = 15;
    public static final int GET_LOGIN_USERE_INFO = 16;

    private static final String mainUrl = "http://192.168.8.106:8888/";

    /**
     * 使用用户手机号获取短信验证码
     */
    private static final String scodeForLoginAction = "kinship-api/smsLoginValidateCode";

    /**
     * 使用手机号和短信验证码快速登陆
     */
    private static final String quickLoginAction = "kinship-api/quickLogin";

    /**
     * 使用用户手机号获取短信注册验证码
     */
    private static final String scodeForRegisterAction = "kinship-api/smsGetValidateCode";

    /**
     * 注册接口
     */
    private static final String registerAction = "kinship-api/register";

    /**
     * 获取纯数字验证码
     */
    private static final String getNumberCaptchar = "kinship-api/getValidateCode";

    /**
     * 登陆
     */
    private static final String loginAction = "kinship-api/login";

    /**
     * 使用用户手机号获取重置密码验证码
     */
    private static final String getScodeForResetPwdAction = "kinship-api/smsResetValidateCode";

    /**
     * 重置密码
     */
    private static final String resetPwdAction = "kinship-api/reset";

    /**
     * 获取照片列表
     */
    private static final String getPhotoListAction = "kinship-api/photograph/list";

    /**
     * 图片上传
     */
    private static final String uploadPhotoListAction = "kinship-api/upload/photos";

    /**
     * 发布照片
     */
    private static final String submitPhotoListAction = "kinship-api/photograph";

    /**
     * 下载图片
     */
    private static final String downloadImageAction = "kinship-api";

    /**
     * 家庭成员列表
     */
    private static final String familyMenberListAction = "kinship-api/member/list";

    /**
     * 增加家庭成员
     */
    private static final String addFamilyMenberAction = "kinship-api/member/add";

    /**
     * 获取登陆用户信息
     */
    private static final String getLoginUserInfo = "kinship-api/getLoginUser";

    private static ConnectManager instance;

    public static ConnectManager getInstance() {
        if(instance == null) {
            instance = new ConnectManager();
        }
        return instance;
    }

    public boolean getScodeForLogin(OnHttpDataUpdateListener listener, String phoneNum) {
        try{
            JSONObject obj = new JSONObject();
            obj.put("phone",phoneNum);
            new NetWorkTask().execute(listener, GET_SCODE_FOR_LOGIN,mainUrl + scodeForLoginAction,obj);
            return true;
        } catch (Exception e){

        }
        return false;
    }

    public boolean quickLogin(OnHttpDataUpdateListener listener, String phoneNum,String scode) {
        try{
            JSONObject obj = new JSONObject();
            obj.put("phone",phoneNum);
            obj.put("captcha",scode);
            new NetWorkTask().execute(listener,QUICK_LOGIN,mainUrl + quickLoginAction,obj);
            return true;
        } catch (Exception e){

        }
        return false;
    }

    public boolean getScodeForRegister(OnHttpDataUpdateListener listener, String phoneNum) {
        try{
            JSONObject obj = new JSONObject();
            obj.put("phone",phoneNum);
            new NetWorkTask().execute(listener, GET_SCODE_FOR_REGISTER,mainUrl + scodeForRegisterAction,obj);
            return true;
        } catch (Exception e){

        }
        return false;
    }

    public boolean register(OnHttpDataUpdateListener listener, String phoneNum,String scode,String password) {
        try{
            JSONObject obj = new JSONObject();
            obj.put("phone",phoneNum);
            obj.put("captcha",scode);
            obj.put("userType",USER_TYPE);
            obj.put("password",password);
            new NetWorkTask().execute(listener,REGISTER,mainUrl + registerAction,obj);
            return true;
        } catch (Exception e){

        }
        return false;
    }

    public boolean getNumberCaptchar(OnHttpDataUpdateListener listener) {
        try{
            JSONObject obj = new JSONObject();
            new NetWorkTask().execute(listener,GET_NUMBER_CAPTCHAR,mainUrl + getNumberCaptchar,obj);
            return true;
        } catch (Exception e){

        }
        return false;
    }

    public boolean login(OnHttpDataUpdateListener listener, String phoneNum,String pwd,String captcha){
        try{
            JSONObject obj = new JSONObject();
            obj.put("phone",phoneNum);
            obj.put("captcha",captcha);
            obj.put("password",pwd);
            new NetWorkTask().execute(listener,LOGIN,mainUrl + loginAction,obj);
            return true;
        } catch (Exception e){

        }
        return false;
    }

    public boolean getScodeForResetPwd(OnHttpDataUpdateListener listener, String phoneNum) {
        try{
            JSONObject obj = new JSONObject();
            obj.put("phone",phoneNum);
            new NetWorkTask().execute(listener, GET_SCODE_FOR_RESET_PWD,mainUrl + getScodeForResetPwdAction,obj);
            return true;
        } catch (Exception e){

        }
        return false;
    }

    public boolean resetPwd(OnHttpDataUpdateListener listener, String phoneNum,String pwd,String scode) {
        try{
            JSONObject obj = new JSONObject();
            obj.put("phone",phoneNum);
            obj.put("password",pwd);
            obj.put("captcha",scode);
            new NetWorkTask().execute(listener, RESET_PWD,mainUrl + resetPwdAction,obj);
            return true;
        } catch (Exception e){

        }
        return false;
    }

    public boolean updatePhotoList(OnHttpDataUpdateListener listener,int offset,int limit,String token) {
        try{
            JSONObject obj = new JSONObject();
            obj.put("offset",offset);
            obj.put("limit",limit);
            new NetWorkTask().execute(listener, GET_PHOTO_LIST,mainUrl + getPhotoListAction,obj,token);
            return true;
        } catch (Exception e){

        }
        return false;
    }

    public boolean uploadPhotos(OnHttpDataUpdateListener listener, ArrayList<String>photos, String token, UIProgressListener progressListener) {
        if(HttpUtils.useOkHttp) {
//            OkHttp.upLoadFile(UPLOAD_PHOTO_LIST,mainUrl + uploadPhotoListAction,photos.get(0),token,progressListener,listener);
            OkHttp.upLoadFiles(UPLOAD_PHOTO_LIST,mainUrl + uploadPhotoListAction,photos,token,progressListener,listener);
//            OkHttp.upLoadFile(UPLOAD_PHOTO_LIST,mainUrl + uploadPhotoListAction,photos,token,listener);
//            OkHttp.postFile(UPLOAD_PHOTO_LIST,mainUrl + uploadPhotoListAction,photos.get(0),token,listener);
            return true;
        } else {
            try{
                JSONObject obj = new JSONObject();
                new NetWorkTask().execute(listener, UPLOAD_PHOTO_LIST,mainUrl + uploadPhotoListAction,photos,token);
                return true;
            } catch (Exception e){

            }
            return false;
        }

    }

    public boolean submitPhotoList(OnHttpDataUpdateListener listener, List<PhotoItem> fileUrls, int[] uids, String message, int mode, String token) {
        try{
            JSONObject obj = new JSONObject();
            JSONArray photoArray = new JSONArray();
            for(PhotoItem item : fileUrls) {
                photoArray.put(item.json());
            }
            obj.put("fileUrls",photoArray);
            obj.put("message",message);
            obj.put("mode",mode);
            JSONArray uidArray = new JSONArray();
            for(int uid : uids) {
                uidArray.put(uid);
            }
            obj.put("uids",uidArray);
            new NetWorkTask().execute(listener, SUBMIT_PHOTO_LIST,mainUrl + submitPhotoListAction,obj,token);
            return true;
        } catch (Exception e){

        }
        return false;
    }

    public String getPhotoUrl(PhotoItem photo) {
        String url = mainUrl + downloadImageAction + "/" + photo.getFolder() + "/" + photo.getName();
        return url;
    }

    public boolean getFamilyMemberList(OnHttpDataUpdateListener listener,int offset,int limit,String token) {
        try{
            JSONObject obj = new JSONObject();
            obj.put("offset",offset);
            obj.put("limit",limit);
            new NetWorkTask().execute(listener, FAMILY_MENBER_LIST,mainUrl + familyMenberListAction,obj,token);
            return true;
        } catch (Exception e){

        }
        return false;
    }

    public boolean addFamilyMember(OnHttpDataUpdateListener listener,String phone,String name,String token) {
        try{
            JSONObject obj = new JSONObject();

            JSONArray jArray = new JSONArray();
            JSONObject childObj = new JSONObject();
            childObj.put("phone",phone);
            childObj.put("remarkName",name);
            jArray.put(childObj);

            obj.put("memberList",jArray);

            new NetWorkTask().execute(listener, ADD_FAMILY_MENBER,mainUrl + addFamilyMenberAction,obj,token);
            return true;
        } catch (Exception e){

        }
        return false;
    }

    public boolean getLoginUserInfo(OnHttpDataUpdateListener listener,String token) {
        JSONObject obj = new JSONObject();
        new NetWorkTask().execute(listener, GET_LOGIN_USERE_INFO,mainUrl + getLoginUserInfo,token);
        return true;
    }
}
