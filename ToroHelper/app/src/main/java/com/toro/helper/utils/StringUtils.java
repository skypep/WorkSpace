package com.toro.helper.utils;

/**
 * Create By liujia
 * on 2018/10/22.
 **/
public class StringUtils {
    public static boolean isEmpty(String string) {
        if(string == null || string.length() < 1) {
            return true;
        }
        return false;
    }
}
