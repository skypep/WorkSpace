package com.toro.helper.modle;

import com.toro.helper.modle.data.ToroLoginUserData;
import com.toro.helper.modle.photo.PhotoData;
import com.toro.helper.modle.photo.PhotoItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By liujia
 * on 2018/10/22.
 **/
public class DataModleParser {

    public static BaseResponeData parserBaseResponeData(String jsonString) {
        return BaseResponeData.newInstance(jsonString);
    }

    public static List<PhotoItem> parserPhotoItems(String jsonString) {
        List<PhotoItem> photos = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(jsonString);
            for(int i=0;i< jsonArray.length();i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                PhotoItem item = PhotoItem.newInstance(obj);
                photos.add(item);
            }
        } catch (Exception e) {

        }
        return photos;
    }

    public static List<PhotoData> parserPhotoDatas(String jsonString) {
        List<PhotoData> datas = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("rows"));
            for(int i=0;i< jsonArray.length();i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                PhotoData item = PhotoData.newInstance(obj);
                datas.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }

    public static List<FamilyMemberInfo> parserFamilyMemberDatas(String jsonString) {
        List<FamilyMemberInfo> datas = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("rows"));
            for(int i=0;i< jsonArray.length();i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                FamilyMemberInfo item = FamilyMemberInfo.newInstance(obj);
                datas.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }

    public static ToroLoginUserData parserLoginUserData(String jsonString) {
        try {
            return ToroLoginUserData.newInstance(new JSONObject(jsonString));
        }catch (Exception e) {

        }
        return null;

    }
}
