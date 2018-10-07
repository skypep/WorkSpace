package com.android.contacts.toro.activity;

import android.content.Context;
import android.graphics.Color;
import android.provider.CallLog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.contacts.R;

public class ToroPhoneNumberItemViewHolder extends RecyclerView.ViewHolder {

    private TextView phoneNumberView;
    private final Context context;
    private View itemView;

    public ToroPhoneNumberItemViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        phoneNumberView = itemView.findViewById(R.id.toro_phone_number_text);
        this.itemView = itemView;
    }

    public void setCallDetails(
            String phoneNumber) {
        phoneNumberView.setText(phoneNumber);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }
}
