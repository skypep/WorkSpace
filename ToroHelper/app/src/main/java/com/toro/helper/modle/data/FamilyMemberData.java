package com.toro.helper.modle.data;

import com.toro.helper.modle.FamilyMemberInfo;
import com.toro.helper.modle.photo.PhotoData;

import java.util.List;

/**
 * Create By liujia
 * on 2018/10/31.
 **/
public class FamilyMemberData {
    List<FamilyMemberInfo> familyMemberDatas;
    public int pageCount = 10;// 一页多少条数据

    public List<FamilyMemberInfo> getFamilyMemberDatas() {
        return familyMemberDatas;
    }

    public void setFamilyMemberDatas(List<FamilyMemberInfo> familyMemberDatas) {
        this.familyMemberDatas = familyMemberDatas;
    }

    public void appendPhotoDatas(List<FamilyMemberInfo> datas) {
        this.familyMemberDatas.addAll(datas);
    }

    public int getLimit() {
        if(familyMemberDatas == null || familyMemberDatas.size() < 1){
            return pageCount;
        }else {
            return familyMemberDatas.size()>pageCount?familyMemberDatas.size():pageCount;
        }
    }

    public FamilyMemberInfo getMemberInfoByid(int id) {
        try{
            for(FamilyMemberInfo info :familyMemberDatas){
                if(info.getId() == id) {
                    return info;
                }
            }
        }catch (Exception e){

        }

        return null;
    }
}
