package com.toro.helper.modle.photo;

/**
 * Create By liujia
 * on 2018/10/23.
 **/
public class PhotoUserInfo {
    private String uid;
    private String phone;
    private String name;
    private PhotoItem headPhoto;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PhotoItem getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(PhotoItem headPhoto) {
        this.headPhoto = headPhoto;
    }
}
