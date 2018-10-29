package com.toro.helper.fragment.family;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toro.helper.R;
import com.toro.helper.modle.FamilyMemberData;
import com.toro.helper.utils.ImageLoad;
import com.toro.helper.view.RoundnessImageView;

/**
 * Create By liujia
 * on 2018/10/29.
 **/
public class FamilyMemberViewHolder extends RecyclerView.ViewHolder {

    private View rootView;
    private RoundnessImageView headImageView;
    private TextView nameTextView;
    private TextView statusTextView;
    private LinearLayout rightLayout;

    public FamilyMemberViewHolder(@NonNull View itemView) {
        super(itemView);
        this.rootView = itemView;
        this.headImageView = rootView.findViewById(R.id.head_img);
        this.nameTextView = rootView.findViewById(R.id.text_name);
        this.statusTextView = rootView.findViewById(R.id.text_status);
        this.rightLayout = rootView.findViewById(R.id.right_layout);
    }

    public void init(boolean needLoad, FamilyMemberData data) {
        if(needLoad) {
            ImageLoad.GlidLoad(headImageView,data.getUserInfo().getHeadPhoto(),R.mipmap.default_head);
        }else {
            headImageView.setImageResource(R.mipmap.default_head);
        }
        nameTextView.setText(data.getRemarkName());
        statusTextView.setText(data.getStatusStringRes());
        rightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
