package com.android.contacts.toro.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.contacts.R;

public class ToroContactActionItemViewHolder extends RecyclerView.ViewHolder {

    private int[] actionTexts = {
            R.string.toro_send_message,
            R.string.toro_send_location,
    };

    private int[] actionIcons = {
            R.drawable.toro_details_send_message,
            R.drawable.toro_details_send_loction,
    };
    private final ImageView actionIcon;
    private final TextView actionText;
    private final Context context;
    private View itemView;

    public ToroContactActionItemViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        actionIcon = itemView.findViewById(R.id.toro_detail_action_icon);
        actionText = itemView.findViewById(R.id.toro_detail_action_text);
        this.itemView = itemView;
    }

    public void setActionDetailsPosition(int position, View.OnClickListener sendMessageListener, View.OnClickListener sendLocaltionListener){
        actionIcon.setImageResource(actionIcons[position]);
        actionText.setText(actionTexts[position]);
        if(actionTexts[position] == R.string.toro_send_message) {
            itemView.setOnClickListener(sendMessageListener);
        }else if(actionTexts[position] == R.string.toro_send_location) {
            itemView.setOnClickListener(sendLocaltionListener);
        }
    }



}
