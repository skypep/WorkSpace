package com.toro.helper.modle.data;

import com.toro.helper.modle.FamilyMemberInfo;

import java.util.List;

/**
 * Create By liujia
 * on 2018/10/31.
 **/
public class FamilyMemberData {
    List<FamilyMemberInfo> familyMemberDatas;

    public List<FamilyMemberInfo> getFamilyMemberDatas() {
        return familyMemberDatas;
    }

    public void setFamilyMemberDatas(List<FamilyMemberInfo> familyMemberDatas) {
        this.familyMemberDatas = familyMemberDatas;
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
