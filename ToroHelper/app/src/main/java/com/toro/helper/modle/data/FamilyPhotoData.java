package com.toro.helper.modle.data;

import com.toro.helper.modle.photo.PhotoData;

import java.util.List;

/**
 * Create By liujia
 * on 2018/10/31.
 **/
public class FamilyPhotoData {
    private List<PhotoData> photoDatas;
    private int pageCount = 10;// 一页多少条数据

    public List<PhotoData> getPhotoDatas() {
        return photoDatas;
    }

    public void setPhotoDatas(List<PhotoData> photoDatas) {
        this.photoDatas = photoDatas;
    }
}
