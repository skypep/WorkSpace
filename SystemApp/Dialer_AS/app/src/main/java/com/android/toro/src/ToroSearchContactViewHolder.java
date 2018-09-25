package com.android.toro.src;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.android.dialer.R;
import com.android.dialer.util.DialerUtils;
import com.android.dialer.util.IntentUtil;

public class ToroSearchContactViewHolder extends RecyclerView.ViewHolder {

    private View view;
    private EditText editText;
    public ToroSearchContactViewHolder(View view) {
        super(view);
        this.view = view;
        this.editText = view.findViewById(R.id.toro_search_edit);
    }

    public void setOnclicListener(View.OnClickListener listener) {
        view.setOnClickListener(listener);
        editText.setOnClickListener(listener);
    }
}
