package com.toro.helper.modle.data;

import com.toro.helper.modle.photo.PhotoData;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By liujia
 * on 2018/10/31.
 **/
public class FamilyPhotoData {
    private List<PhotoData> photoDatas;
    public int pageCount = 10;// 一页多少条数据

    public List<PhotoData> getPhotoDatas() {
        return photoDatas;
    }

    public void setPhotoDatas(List<PhotoData> datas) {
        this.photoDatas = datas;
    }

    public void appendPhotoDatas(List<PhotoData> datas) {
        this.photoDatas.addAll(datas);
    }

    public int getLimit() {
        if(photoDatas == null || photoDatas.size() < 1){
            return pageCount;
        }else {
            return photoDatas.size()>pageCount?photoDatas.size():pageCount;
        }
    }

//    public void setPhotoDatasAppend(List<PhotoData> datas) {
//        if(photoDatas == null || photoDatas.size() < 1) {
//            return;
//        }
//        if(photoDatas == null) {
//            photoDatas = new ArrayList<>();
//        }
//        if(photoDatas.size() > 0) {
//            for(PhotoData newData:datas) {
//                for(int i = 0;i < this.photoDatas.size(); i ++) {
//                    PhotoData oldData = this.photoDatas.get(i);
//                    if(newData.getId() == oldData.getId()) {
//                        break;
//                    }
//                    if(i == this.photoDatas.size() - 1) {
//                        this.photoDatas.add(newData);
//                    }
//                }
//            }
//        } else{
//            photoDatas.addAll(datas);
//        }
//    }
}
