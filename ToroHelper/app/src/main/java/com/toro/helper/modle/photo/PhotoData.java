package com.toro.helper.modle.photo;

import java.util.List;

/**
 * Create By liujia
 * on 2018/10/23.
 **/
public class PhotoData {
    private int id;
    private String message;
    private int mode;
    private String gmtCreated;
    private String gmtUpdated;
    private PubTime pubTime;
    private PhotoUserInfo userinfo;
    private List<PhotoItem> photos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(String gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public String getGmtUpdated() {
        return gmtUpdated;
    }

    public void setGmtUpdated(String gmtUpdated) {
        this.gmtUpdated = gmtUpdated;
    }

    public PubTime getPubTime() {
        return pubTime;
    }

    public void setPubTime(PubTime pubTime) {
        this.pubTime = pubTime;
    }

    public PhotoUserInfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(PhotoUserInfo userinfo) {
        this.userinfo = userinfo;
    }

    public List<PhotoItem> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoItem> photos) {
        this.photos = photos;
    }
}
