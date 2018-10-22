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

    private static final String registerAction = "kinship-api/register";

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
}
