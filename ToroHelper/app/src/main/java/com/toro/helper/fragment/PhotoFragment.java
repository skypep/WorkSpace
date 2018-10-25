package com.toro.helper.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toro.helper.R;
import com.toro.helper.app.AppConfig;
import com.toro.helper.base.ToroFragment;
import com.toro.helper.fragment.photo.PhotoAdapter;
import com.toro.helper.modle.photo.PhotoData;
import com.toro.helper.modle.ToroUserManager;
import com.toro.helper.modle.photo.PhotoItem;
import com.toro.helper.modle.photo.PhotoUserInfo;
import com.toro.helper.utils.ConnectManager;
import com.toro.helper.view.RecyclerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By liujia
 * on 2018/10/23.
 **/
public class PhotoFragment extends ToroFragment {

    private RecyclerView recyclerView;
    private PhotoAdapter adapter;
    private List<PhotoData> photoDatas;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =   inflater.inflate(
                R.layout.photo_fragment, container, false
        );
        photoDatas = new ArrayList<>();
        ConnectManager.getInstance().updatePhotoList(this,0,10, ToroUserManager.getInstance(getContext()).getToken());
        initView(view);
        return view;
    }

    private void initView(View rootView) {
        adapter = new PhotoAdapter(getContext(),photoDatas);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new RecyclerItemDecoration(getContext().getResources().getDimensionPixelOffset(R.dimen.photo_list_photo_offset)));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean bindData(int tag, Object object) {
        for(int i = 0; i < 30; i ++) {
            PhotoData data = new PhotoData();
            data.setGmtUpdated("2018-10-08 17:07:31");
            PhotoUserInfo user = new PhotoUserInfo();
            user.setName("老弟");
            data.setUserinfo(user);
            List<PhotoItem> photos = new ArrayList<>();
            for(int j = 0; j < AppConfig.PhotoMaxCoun; j++) {
                PhotoItem photo = new PhotoItem();
                photos.add(photo);
            }
            data.setPhotos(photos);
            photoDatas.add(data);
        }
        adapter = new PhotoAdapter(getContext(),photoDatas);
        recyclerView.setAdapter(adapter);
        return super.bindData(tag, object);
    }


}
