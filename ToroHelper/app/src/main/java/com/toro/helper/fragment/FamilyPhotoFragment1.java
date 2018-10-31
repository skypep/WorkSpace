package com.toro.helper.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toro.helper.R;
import com.toro.helper.base.ToroListAdapter;
import com.toro.helper.base.ToroListFragment;
import com.toro.helper.fragment.photo.PhotoAdapter1;
import com.toro.helper.modle.DataModleParser;
import com.toro.helper.modle.ToroUserManager;
import com.toro.helper.modle.photo.PhotoData;
import com.toro.helper.utils.ConnectManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By liujia
 * on 2018/10/29.
 **/
public class FamilyPhotoFragment1 extends ToroListFragment {

    private List<PhotoData> photoDatas;
    private PhotoAdapter1 adpter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        photoDatas = new ArrayList<>();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected ToroListAdapter getAdapter() {
        if(adpter == null) {
            adpter = new PhotoAdapter1(getContext(),photoDatas);
        }
        return adpter;
    }

    @Override
    protected List<?> getDatas() {
        return photoDatas;
    }

    @Override
    public void doanloadDatas(boolean isBackgroud) {
        super.doanloadDatas(isBackgroud);
        ConnectManager.getInstance().updatePhotoList(this,0,10, ToroUserManager.getInstance(getContext()).getToken());
    }

    @Override
    protected void updateDatas(String result) {
        photoDatas = DataModleParser.parserPhotoDatas(result);
    }

    @Override
    protected String getEmptyHintString() {
        return getString(R.string.empty_photo_hint);
    }

    @Override
    protected void setItemDecoration() {
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }
}
