package com.android.toro.src.setting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.toro.src.R;
import com.android.toro.src.utils.ToroLocalDataManager;
import com.android.toro.src.view.CustomEditText;
import com.google.wireless.gdata.data.StringUtils;

/**
 * Create By liujia
 * on 2018/11/21.
 **/
public class ToroTransferDialogPreference extends DialogPreference {

    private TextView stop,cancel,sure;
    private CustomEditText phoneEdit;
    private View.OnClickListener contactListener;

    public ToroTransferDialogPreference(Context context) {
        this(context,null);
        // TODO Auto-generated constructor stub
        super.setDialogLayoutResource(R.layout.transfer_dialog);
    }

    public ToroTransferDialogPreference(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        // TODO Auto-generated constructor stub
        super.setDialogLayoutResource(R.layout.transfer_dialog);
    }

    public ToroTransferDialogPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        super.setDialogLayoutResource(R.layout.transfer_dialog);
    }

    @Override
    protected View onCreateDialogView() {
        // TODO Auto-generated method stub
        View view = super.onCreateDialogView();
        stop = view.findViewById(R.id.stop);
        cancel = view.findViewById(R.id.cancel);
        sure = view.findViewById(R.id.sure);
        phoneEdit = view.findViewById(R.id.phone_edit);
        phoneEdit.setDrawableRightListener(new CustomEditText.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                if(contactListener != null) {
                    contactListener.onClick(view);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToroTransferDialogPreference.this.getDialog().dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToroTransferDialogPreference.this.getDialog().dismiss();
                transfer(phoneEdit.getText().toString());
            }
        });
        return view;
    }

    public void setContactOnclickListener(View.OnClickListener listener) {
        this.contactListener = listener;
    }

    public void updatePhoneNum(String phoneNum) {
        phoneEdit.setText(phoneNum);
    }

    private void updateView() {
        String phone = ToroLocalDataManager.getInstance(getContext()).getStringByKey(getKey(),"");
        if(StringUtils.isEmpty(phone)){
            phoneEdit.setText("");
            stop.setEnabled(false);
            setSummary(getContext().getString(R.string.call_transfer_stop));
        }else{
            phoneEdit.setText(phone);
            stop.setEnabled(true);
            stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToroTransferDialogPreference.this.getDialog().dismiss();
                    unTransfer();
                }
            });
            setSummary(phone);
        }
    }

    private void transfer(String phoneNum) {
        if(StringUtils.isEmpty(phoneNum)) {
            Toast.makeText(getContext(),R.string.erro_number,Toast.LENGTH_LONG).show();
            return;
        }
        String key = getKey();
        Intent intent;
        if(key.equals(getContext().getString(com.android.dialer.R.string.all_calls_transferred_key))) {
            intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:**21*" + phoneNum + "%23"));// 始终进行呼叫转移
        }else if(key.equals(getContext().getString(com.android.dialer.R.string.no_answers_calls_transferred_key))) {
            intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:**61*" + phoneNum + "%23"));// 无应答时进行呼叫转移
        }else if(key.equals(getContext().getString(com.android.dialer.R.string.busy_calls_transferred_key))) {
            intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:**67*" + phoneNum + "%23"));// 占线时进行呼叫转移
        }else if(key.equals(getContext().getString(com.android.dialer.R.string.unconnect_calls_transferred_key))) {
            intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:**62*" + phoneNum + "%23"));// 无法接通时进行呼叫转移
        }else{
            return;
        }
        getContext().startActivity(intent);
        ToroLocalDataManager.getInstance(getContext()).setStringByKey(key,phoneNum);
        updateView();
    }

    private void unTransfer() {
        String key = getKey();
        Intent intent;
        if(key.equals(getContext().getString(com.android.dialer.R.string.all_calls_transferred_key))) {
            intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:##21#"));// 始终进行呼叫转移
        }else if(key.equals(getContext().getString(com.android.dialer.R.string.no_answers_calls_transferred_key))) {
            intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:##61#"));// 无应答时进行呼叫转移
        }else if(key.equals(getContext().getString(com.android.dialer.R.string.busy_calls_transferred_key))) {
            intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:##67#"));// 占线时进行呼叫转移
        }else if(key.equals(getContext().getString(com.android.dialer.R.string.unconnect_calls_transferred_key))) {
            intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:##62#"));// 无法接通时进行呼叫转移
        }else{
            return;
        }
        getContext().startActivity(intent);
        ToroLocalDataManager.getInstance(getContext()).setStringByKey(key,"");
        updateView();
    }

    public void clear() {
        ToroLocalDataManager.getInstance(getContext()).setStringByKey(getKey(),"");
    }
}
