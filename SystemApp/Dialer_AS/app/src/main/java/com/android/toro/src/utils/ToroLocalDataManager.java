package com.android.toro.src.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.dialer.R;
import com.android.toro.src.contact.ToroContact;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By liujia
 * on 2018/11/13.
 **/
public class ToroLocalDataManager {

    private boolean assintantOpenLoundspeaker;

    private static ToroLocalDataManager instance;
    private SharedPreferences pre;
    private Context mContext;

    public static ToroLocalDataManager getInstance(Context context) {
        if(instance == null) {
            instance = new ToroLocalDataManager(context);
        }
        return instance;
    }

    private ToroLocalDataManager(Context context) {
        mContext = context.getApplicationContext();
        pre = context.getSharedPreferences(this.getClass().getName(),Context.MODE_PRIVATE);
    }

    public boolean isAssintantOpenLoundspeaker() {
        assintantOpenLoundspeaker = pre.getBoolean("assintantOpenLoundspeaker", assintantOpenLoundspeaker);
        return assintantOpenLoundspeaker;
    }

    public void setAssintantOpenLoundspeaker(boolean assintantOpenLoundspeaker) {
        this.assintantOpenLoundspeaker = assintantOpenLoundspeaker;
        pre.edit().putBoolean("assintantOpenLoundspeaker", assintantOpenLoundspeaker).apply();
    }

    public boolean isReceiverMode() {
        boolean value = PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean(mContext.getString(R.string.receiver_model_key),true);
        return value;
    }

    public String getStringByKey(String key,String defaultValue) {
        return pre.getString(key,defaultValue);
    }

    public void setStringByKey(String key,String value) {
        pre.edit().putString(key,value).apply();
    }

    public void setContactsByKey(String key,List<ToroContact> contacts) {
        try{
            JSONArray array = new JSONArray();
            for(int i = 0; i < contacts.size(); i ++) {
                ToroContact contact = contacts.get(i);
                JSONObject obj = new JSONObject();
                obj.put("contactID",contact.getContactID());
                JsonArray phones = new JsonArray();
                for(String phone : contact.getNumber()){
                    phones.add(phone);
                }
                obj.put("phones",phones);
                array.put(i,obj.toString());
            }
            pre.edit().putString(key,array.toString()).apply();
        }catch (Exception e) {

        }

    }

    public List<ToroContact> getContactsByKey(String key) {
        List<ToroContact> contacts = new ArrayList<>();
        try{
            String result = pre.getString(key,"");
            JSONArray array = new JSONArray(result);
            for(int i = 0; i < array.length(); i ++) {
                ToroContact contact = new ToroContact();
                JSONObject obj = new JSONObject(array.getString(i));
                JSONArray numberAray = new JSONArray(obj.getString("phones"));
                List<String> phones = new ArrayList<>();
                for(int j = 0; j < numberAray.length(); j ++) {
                    String phone = numberAray.getString(j);
                    phones.add(phone);
                }
                contact.setContactID(obj.getLong("contactID"));
                contact.setNumber(phones);
                contacts.add(contact);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return contacts;
    }
}
