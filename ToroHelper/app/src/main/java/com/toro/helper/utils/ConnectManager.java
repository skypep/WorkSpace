package com.toro.helper.utils;

import org.json.JSONObject;

/**
 * Create By liujia
 * on 2018/10/22.
 **/
public class ConnectManager {

    private static final int USER_TYPE = 2; // 0：管理员,1：老人机用户，2：子女端用户，3：未注册用户|

    public static final int REGISTER_ACTION =  1;
    public static final int GET_SCODE_FOR_LOGIN = 2;
    public static final int QUICK_LOGIN = 3;
    public static final int GET_SCODE_FOR_REGISTER = 4;
    public static final int REGISTER = 5;
    public static final int GET_NUMBER_CAPTCHAR = 6;
    public static final int LOGIN = 7;
    public static final int GET_SCODE_FOR_RESET_PWD = 8;
    public static final int RESET_PWD = 9;
    public static final int GET_PHOTO_LIST = 10;

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

}
