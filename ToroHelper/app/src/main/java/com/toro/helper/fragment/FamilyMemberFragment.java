package com.toro.helper.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.toro.helper.R;
import com.toro.helper.activity.HelperActivity;
import com.toro.helper.activity.MainActivity;
import com.toro.helper.base.BaseFragment;
import com.toro.helper.fragment.family.FamilyMemberAdapter;
import com.toro.helper.modle.data.FamilyMemberData;
import com.toro.helper.modle.data.ToroDataModle;
import com.toro.helper.modle.data.listener.FamilyMemberDataOnChangeListener;
import com.toro.helper.utils.ConnectManager;
import com.toro.helper.view.AutoLoadRecyclerView;
import com.toro.helper.view.RecyclerItemDecoration;

import java.util.List;

/**
 * Create By liujia
 * on 2018/10/27.
 **/
public class FamilyMemberFragment extends BaseFragment implements FamilyMemberDataOnChangeListener {

    private FamilyMemberAdapter adapter;

    protected AutoLoadRecyclerView recyclerView;
    private TextView emptyHint;
    private ProgressBar loadingProgress;
    private FamilyMemberData memberData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =   inflater.inflate(
                R.layout.list_fragment, container, false
        );
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ToroDataModle.getInstance().addToroDataModeOnChangeListener(this);
        updateMemberList();
    }

    @Override
    public void onPause() {
        super.onPause();
        ToroDataModle.getInstance().removeToroDataModeOnChangeListener(this);
    }

    protected void initView(View rootView) {
        adapter = new FamilyMemberAdapter(getContext(),null);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new RecyclerItemDecoration(getContext().getResources().getDimensionPixelOffset(R.dimen.photo_list_photo_offset)));
        recyclerView.setAdapter(adapter);
        recyclerView.setOnPauseListenerParams(new AutoLoadRecyclerView.OnLoadImageSwitchListener() {
            @Override
            public void onLoadImageSwitch(boolean flag) {
                adapter.onLoadImageSwitch(flag);
            }
        },false,false);
        adapter.setAgreenListener(agreenListener);
        adapter.setOnItemClickListener(itemOnclickListener);
        emptyHint = rootView.findViewById(R.id.empty_hint);
        loadingProgress = rootView.findViewById(R.id.loading_progress);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    protected void showEmptyHint() {
        recyclerView.setVisibility(View.GONE);
        loadingProgress.setVisibility(View.GONE);
        emptyHint.setVisibility(View.VISIBLE);
        emptyHint.setText(getString(R.string.empty_family_hint));
    }

    protected void updateMemberList() {
        memberData = ToroDataModle.getInstance().getFamilyMemberData();
        if(memberData == null || memberData.getFamilyMemberDatas() == null || memberData.getFamilyMemberDatas().size() < 1) {
            showEmptyHint();
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            loadingProgress.setVisibility(View.GONE);
            emptyHint.setVisibility(View.GONE);
            adapter.updatePhotoDatas(memberData.getFamilyMemberDatas());
        }
    }

    private View.OnClickListener agreenListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = (int) v.getTag();
            ConnectManager.getInstance().agreenMember((MainActivity)getActivity(),memberData.getFamilyMemberDatas().get(index).getId(),ToroDataModle.getInstance().getLocalData().getToken());
        }
    };

    private View.OnClickListener itemOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = (int) v.getTag();
            startActivity(HelperActivity.createIntent(getContext(),memberData.getFamilyMemberDatas().get(index)));
        }
    };

    @Override
    public void onChange(Object obj) {
        updateMemberList();
    }
}
