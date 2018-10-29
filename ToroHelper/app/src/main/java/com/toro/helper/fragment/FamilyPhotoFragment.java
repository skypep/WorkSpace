//package com.toro.helper.fragment;
//
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.toro.helper.R;
//import com.toro.helper.base.ToroListFragment;
//import com.toro.helper.fragment.photo.PhotoAdapter;
//import com.toro.helper.modle.BaseResponeData;
//import com.toro.helper.modle.DataModleParser;
//import com.toro.helper.modle.photo.PhotoData;
//import com.toro.helper.modle.ToroUserManager;
//import com.toro.helper.utils.ConnectManager;
//import com.toro.helper.utils.OnHttpDataUpdateListener;
//import com.toro.helper.view.AutoLoadRecyclerView;
//import com.toro.helper.view.RecyclerItemDecoration;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Create By liujia
// * on 2018/10/23.
// **/
//public class FamilyPhotoFragment extends Fragment implements OnHttpDataUpdateListener {
//
//    private AutoLoadRecyclerView recyclerView;
//    private TextView emptyHint;
//    private ProgressBar loadingProgress;
//    private PhotoAdapter adapter;
//    private List<PhotoData> photoDatas;
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view =   inflater.inflate(
//                R.layout.photo_fragment, container, false
//        );
//        photoDatas = new ArrayList<>();
//
//        initView(view);
//        updatePhotos();
//        return view;
//    }
//
//    private void initView(View rootView) {
//        adapter = new PhotoAdapter(getContext(),photoDatas);
//        recyclerView = rootView.findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.addItemDecoration(new RecyclerItemDecoration(getContext().getResources().getDimensionPixelOffset(R.dimen.photo_list_photo_offset)));
//        recyclerView.setAdapter(adapter);
//        recyclerView.setOnPauseListenerParams(new AutoLoadRecyclerView.OnLoadImageSwitchListener() {
//            @Override
//            public void onLoadImageSwitch(boolean flag) {
//                adapter.onLoadImageSwitch(flag);
//            }
//        },false,false);
//
//        emptyHint = rootView.findViewById(R.id.empty_hint);
//        loadingProgress = rootView.findViewById(R.id.loading_progress);
//    }
//
//    public void updatePhotos() {
//        showProgress();
//        ConnectManager.getInstance().updatePhotoList(this,0,10, ToroUserManager.getInstance(getContext()).getToken());
//    }
//
//    private void showEmptyHint() {
//        recyclerView.setVisibility(View.GONE);
//        loadingProgress.setVisibility(View.GONE);
//        emptyHint.setVisibility(View.VISIBLE);
//        emptyHint.setText(getString(R.string.empty_photo_hint));
//    }
//
//    private void showProgress() {
//        recyclerView.setVisibility(View.GONE);
//        loadingProgress.setVisibility(View.VISIBLE);
//        emptyHint.setVisibility(View.GONE);
//    }
//
//    private void showPhotoList() {
//        recyclerView.setVisibility(View.VISIBLE);
//        loadingProgress.setVisibility(View.GONE);
//        emptyHint.setVisibility(View.GONE);
//        adapter.updatePhotoDatas(photoDatas);
//    }
//
//    @Override
//    public boolean bindData(int tag, Object object) {
//        boolean status = true;//super.bindData(tag,object);
//        if(!status) {
//            showEmptyHint();
//            return status;
//        } else {
//            BaseResponeData data = DataModleParser.parserBaseResponeData((String) object);
//            photoDatas = DataModleParser.parserPhotoDatas(data.getEntry());
//            showPhotoList();
//            return true;
//        }
//    }
//
//
//}
