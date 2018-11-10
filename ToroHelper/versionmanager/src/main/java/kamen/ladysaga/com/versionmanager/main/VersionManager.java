package kamen.ladysaga.com.versionmanager.main;

import org.json.JSONException;
import org.json.JSONObject;

import kamen.ladysaga.com.versionmanager.utils.HttpUtils;

/**
 * Created by SK on 2018/7/3.
 */

public class VersionManager {

    private static VersionManager instance;
    private VersionInfo versionInfo;
    private static String versionUrl = "https://raw.githubusercontent.com/2957433126/kamen/master/.gitignore/kamen_update_config_1.5_yipay";

    public static VersionManager getInstance(){
        if(instance == null ) {
            instance = new VersionManager();
        }
        return instance;
    }

    public void checkVersion(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                versionInfo = parserVersionInfo(HttpUtils.getHtml(versionUrl));
            }
        }).start();
    }

    public VersionInfo getVersionInfo() {
        return versionInfo;
    }

    private VersionInfo parserVersionInfo(String content) {
        try{
            JSONObject obj = new JSONObject(content);
            VersionInfo info = new VersionInfo();
            info.setDesc(obj.getString("desc"));
            info.setDownloadUrl(obj.getString("downloadUrl"));
            info.setMust(obj.getBoolean("isMust"));
            info.setVersionCode(obj.getInt("versionCode"));
            return info;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }
}
