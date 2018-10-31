package com.toro.helper.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.toro.helper.R;
import com.toro.helper.activity.MainActivity;
import com.toro.helper.base.ToroListAdapter;
import com.toro.helper.base.ToroListFragment;
import com.toro.helper.fragment.family.FamilyMemberAdapter;
import com.toro.helper.fragment.photo.PhotoAdapter1;
import com.toro.helper.modle.DataModleParser;
import com.toro.helper.modle.FamilyMemberData;
import com.toro.helper.modle.ToroUserManager;
import com.toro.helper.utils.ConnectManager;

import java.util.List;

/**
 * Create By liujia
 * on 2018/10/27.
 **/
public class FamilyMemberFragment extends ToroListFragment {

    List<FamilyMemberData> familys;
    private FamilyMemberAdapter adpter;

    @Override
    public void doanloadDatas(boolean isBackgroud) {
        super.doanloadDatas(isBackgroud);
        ConnectManager.getInstance().getFamilyMemberList(this,0,10, ToroUserManager.getInstance(getContext()).getToken());
    }

    @Override
    protected ToroListAdapter getAdapter() {
        if(adpter == null) {
            adpter = new FamilyMemberAdapter(getContext(),familys);
            adpter.setAgreenListener(agreenListener);
        }
        return adpter;
    }

    @Override
    protected List<?> getDatas() {
        return familys;
    }

    @Override
    protected void updateDatas(String result) {
        familys = DataModleParser.parserFamilyMemberDatas(result);
    }

    @Override
    protected String getEmptyHintString() {
        return getString(R.string.empty_family_hint);
    }

    @Override
    protected void setItemDecoration() {
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    private View.OnClickListener agreenListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = (int) v.getTag();
            ConnectManager.getInstance().agreenMember((MainActivity)getActivity(),familys.get(index).getId(),ToroUserManager.getInstance(getContext()).getToken());
        }
    };

}
