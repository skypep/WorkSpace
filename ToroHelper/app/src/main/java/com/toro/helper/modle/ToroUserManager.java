package com.toro.helper.modle;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

/**
 * Create By liujia
 * on 2018/10/22.
 **/
public class ToroUserManager {
    private String token;
    private String phone;
    private String pwd;
    private long loginTime;

    private static ToroUserManager instance;

    private SharedPreferences pre;

    public static ToroUserManager getInstance(Context context) {
        if(instance == null) {
            instance = new ToroUserManager(context.getApplicationContext());
        }
        return instance;
    }

    private ToroUserManager(Context context) {
        pre = context.getSharedPreferences(this.getClass().getName(),Context.MODE_PRIVATE);
    }

    public void login(String phone,String token) {
        try{
            JSONObject obj = new JSONObject(token);
            token = obj.getString("login");
        }catch (Exception e) {

        }
        setLoginTime(System.currentTimeMillis());
        setPhone(phone);
        setToken(token);
    }

    public String getToken() {
        token = pre.getString("token",token);
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        pre.edit().putString("token",token).apply();
    }

    public String getPhone() {
        phone = pre.getString("phone",phone);
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        pre.edit().putString("phone",phone).apply();
    }

    public String getPwd() {
        pwd = pre.getString("pwd",pwd);
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
        pre.edit().putString("pwd",pwd).apply();
    }

    public long getLoginTime() {
        loginTime = pre.getLong("loginTime",loginTime);
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
        pre.edit().putLong("loginTime",loginTime).apply();
    }
}
