package com.toro.helper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.toro.helper.R;
import com.toro.helper.modle.BaseResponeData;
import com.toro.helper.modle.DataModleParser;
import com.toro.helper.modle.UserManager;
import com.toro.helper.utils.ConnectManager;
import com.toro.helper.utils.OnHttpDataUpdateListener;
import com.toro.helper.utils.StringUtils;
import com.toro.helper.view.MainActionBar;

/**
 * Create By liujia
 * on 2018/10/19.
 **/
public class QuickLoginActivity extends AppCompatActivity implements View.OnClickListener,OnHttpDataUpdateListener {

    public static final int UPDATE_SCODE_TIME = 1;

    private boolean isCanGetScode = true;
    private TextView getScodeView;
    private long scodentervalTime = 60 * 1000;
    private long sCodeGetTime;
    private String sCode;

    private MainActionBar actionBar;
    private Button submitBt;
    private EditText phoneEdit,sCodeEdit;

    private String phoneText,sCodeText;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_SCODE_TIME:
                    long curTime = System.currentTimeMillis();
                    long passTime = curTime - sCodeGetTime;
                    if(passTime > scodentervalTime){
                        resetScode();
                    } else {
                        String string = getResources().getString(R.string.get_s_code_format_time);
                        String stringFormat = String.format(string, (scodentervalTime - passTime)/1000);
                        getScodeView.setTextColor(getResources().getColor(R.color.gray_text_color));
                        getScodeView.setText(stringFormat);
                        sendEmptyMessageDelayed(UPDATE_SCODE_TIME,500);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quick_login_activity);
        getScodeView = findViewById(R.id.get_s_code);
        getScodeView.setOnClickListener(this);
        submitBt = findViewById(R.id.submit_button);
        submitBt.setOnClickListener(this);
        actionBar = findViewById(R.id.main_action_view);
        actionBar.updateView(getString(R.string.login_quick_login),R.mipmap.action_back_icon, 0,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginActivity.newIntent(QuickLoginActivity.this));
                finish();
            }
        },null);
        phoneEdit = findViewById(R.id.edit_phone);
        sCodeEdit = findViewById(R.id.scode_edit);
        sCodeEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sCodeText = s.toString();
                checkSubmitEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        phoneEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phoneText = s.toString();
                checkSubmitEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_s_code:
                getScode();
                break;
            case R.id.submit_button:
                submit();
                break;
        }
    }

    private void getScode() {
        if(isCanGetScode) {
            String phoneNum = phoneEdit.getText().toString();
            if(StringUtils.isEmpty(phoneNum)) {
                Toast.makeText(this,getString(R.string.please_input_phone),Toast.LENGTH_LONG).show();
                return;
            }
            isCanGetScode = false;
            sCodeGetTime = System.currentTimeMillis();
            handler.sendEmptyMessage(UPDATE_SCODE_TIME);

            ConnectManager.getInstance().getScodeForLogin(this,phoneNum);
        } else {
            return;

        }
    }

    private void submit() {
        ConnectManager.getInstance().quickLogin(this,phoneText,sCodeText);
    }

    private void resetScode() {
        getScodeView.setTextColor(getResources().getColor(R.color.blue_text_color));
        if(isCanGetScode){
            getScodeView.setText(getResources().getString(R.string.get_s_code));
        }else {
            getScodeView.setText(getResources().getString(R.string.reget_s_code));
        }
        isCanGetScode = true;
    }


    @Override
    public void bindData(int tag, Object object) {
        try{
            String result = (String) object;
            BaseResponeData data = DataModleParser.parserBaseResponeData(result);
            if(!data.isStatus()) {
                if(StringUtils.isEmpty(data.getMessage())) {
                    Toast.makeText(this,getString(R.string.unknow_error),Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(this,data.getMessage(),Toast.LENGTH_LONG).show();
                }
                resetScode();
                Toast.makeText(this,getString(R.string.unknow_error),Toast.LENGTH_LONG).show();
                return;
            }
            switch (tag) {
                case ConnectManager.GET_SCODE_FOR_LOGIN:
                    sCode = data.getEntry();
                    return;
                case ConnectManager.QUICK_LOGIN:
                    UserManager.getInstance().setToken(data.getEntry());
                    startActivity(MainActivity.newIntent(this));
                    finish();
                    return;
            }
        }catch (Exception e) {

        }
        resetScode();
        Toast.makeText(this,getString(R.string.unknow_error),Toast.LENGTH_LONG).show();

    }

    private void checkSubmitEnable() {
        if(!StringUtils.isEmpty(phoneText) && !StringUtils.isEmpty(sCodeText)) {
            submitBt.setEnabled(true);
        } else {
            submitBt.setEnabled(false);
        }
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,QuickLoginActivity.class);
        return intent;
    }
}
