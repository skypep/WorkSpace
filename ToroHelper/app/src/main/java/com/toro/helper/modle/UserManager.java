package com.toro.helper.modle;

/**
 * Create By liujia
 * on 2018/10/22.
 **/
public class UserManager {
    private String token;

    private static UserManager instance;
    public static UserManager getInstance() {
        if(instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
