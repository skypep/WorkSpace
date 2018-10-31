package com.toro.helper.fragment.family;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toro.helper.R;
import com.toro.helper.base.ToroListAdapter;
import com.toro.helper.fragment.photo.PhotoViewHolder;
import com.toro.helper.modle.FamilyMemberData;
import com.toro.helper.modle.photo.PhotoData;

import java.util.List;

/**
 * Create By liujia
 * on 2018/10/29.
 **/
public class FamilyMemberAdapter extends ToroListAdapter {

    private View.OnClickListener agreenListener;

    public FamilyMemberAdapter(Context context, List<FamilyMemberData> datas) {
        super(context, datas);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateView(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new FamilyMemberViewHolder(inflater.inflate(R.layout.family_member_list_item, viewGroup, false));
    }

    @Override
    public void onBindView(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        FamilyMemberViewHolder familyViewHolder = (FamilyMemberViewHolder) viewHolder;
        FamilyMemberData data = (FamilyMemberData) datas.get(i);
        familyViewHolder.init(needLoad,data,agreenListener,i);
    }

    public void setAgreenListener(View.OnClickListener listener) {
        this.agreenListener = listener;
    }
}
