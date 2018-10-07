package com.android.contacts.toro.activity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.contacts.R;
import com.android.contacts.quickcontact.QuickContactActivity;

import java.util.List;

public class ToroCallDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int FOOTER_VIEW_TYPE = 1;
    private final static int PHONE_NUMBER_ENTRY_VIEW_TYPE = 2;

    private int footCounts = 0;
    private List<String> phoneNumbers;
    private Activity activity;

    public ToroCallDetailsAdapter(
            Activity activity,
            @NonNull List<String> numbers
            ) {
        this.phoneNumbers = numbers;
        this.activity = activity;
        footCounts = 2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case FOOTER_VIEW_TYPE:
                return new ToroContactActionItemViewHolder(
                        inflater.inflate(R.layout.toro_contact_details_item_foot, parent, false));
            case PHONE_NUMBER_ENTRY_VIEW_TYPE:
                return new ToroPhoneNumberItemViewHolder(
                        inflater.inflate(R.layout.toro_phone_number_item, parent, false));
                default:
                   return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position >= phoneNumbers.size()) {
            ToroContactActionItemViewHolder viewHolder = (ToroContactActionItemViewHolder)holder;
            int itemSize = phoneNumbers.size();
            viewHolder.setActionDetailsPosition(position - itemSize,
                    ((QuickContactActivity)activity).getNumbersMessageListener(phoneNumbers),
                    ((QuickContactActivity)activity).getNumbersLocationListener(phoneNumbers));
        } else {
            ToroPhoneNumberItemViewHolder viewHolder = (ToroPhoneNumberItemViewHolder) holder;
            viewHolder.setCallDetails(phoneNumbers.get(position));
            viewHolder.setOnClickListener(((QuickContactActivity)activity).getNumberCallListener(phoneNumbers.get(position)));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= phoneNumbers.size()) {
            return FOOTER_VIEW_TYPE;
        } else {
            return PHONE_NUMBER_ENTRY_VIEW_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return phoneNumbers.size() + footCounts;
    }

}
