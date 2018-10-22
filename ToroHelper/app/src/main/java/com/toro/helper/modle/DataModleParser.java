package com.toro.helper.modle;

/**
 * Create By liujia
 * on 2018/10/22.
 **/
public class DataModleParser {

    public static BaseResponeData parserBaseResponeData(String jsonString) {
        return BaseResponeData.newInstance(jsonString);
    }
}
