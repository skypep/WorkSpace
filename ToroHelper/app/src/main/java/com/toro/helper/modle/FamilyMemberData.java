package com.toro.helper.modle;

import com.toro.helper.R;
import com.toro.helper.modle.photo.PhotoData;
import com.toro.helper.modle.photo.PhotoUserInfo;
import com.toro.helper.modle.photo.PubTime;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Create By liujia
 * on 2018/10/29.
 **/
public class FamilyMemberData {
    private int id;
    private String remarkName;
    private int status;
    private FamilyUserInfo userInfo;

    public static FamilyMemberData newInstance(JSONObject obj) throws JSONException {
        FamilyMemberData instance = new FamilyMemberData();
        instance.id = obj.getInt("id");
        instance.remarkName = obj.getString("remarkName");
        instance.status = obj.getInt("status");
        instance.userInfo = FamilyUserInfo.newInstance(obj.getString("user"));
        return instance;
    }

    public int getStatusStringRes() {
        switch (status) {
            case 0:
                return R.string.status_accepted;
            case 1:
                return R.string.status_wait_me_agree;
            case 2:
                return R.string.status_wait_other_agree;
                default:
                    return R.string.status_unknow;
        }
    }

    public int getId() {
        return id;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public int getStatus() {
        return status;
    }

    public FamilyUserInfo getUserInfo() {
        return userInfo;
    }
}
