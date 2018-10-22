package com.toro.helper.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import com.toro.helper.view.CustomEditText;
import com.toro.helper.view.MainActionBar;

/**
 * Create By liujia
 * on 2018/10/22.
 **/
public class RegisterActivity  extends AppCompatActivity implements View.OnClickListener,OnHttpDataUpdateListener {

    public static final int UPDATE_SCODE_TIME = 1;

    private boolean isCanGetScode = true;
    private TextView getScodeView;
    private long scodentervalTime = 60 * 1000;
    private long sCodeGetTime;
    private String sCode;

    private MainActionBar actionBar;
    private Button submitBt;
    private EditText phoneEdit,sCodeEdit;
    private CustomEditText pwdEdit1,pwdEdit2;
    private boolean showPwd1,showPwd2;

    private String phoneText,sCodeText,pwdText1,pwdText2;

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
        setContentView(R.layout.register_activity);
        getScodeView = findViewById(R.id.get_s_code);
        getScodeView.setOnClickListener(this);
        submitBt = findViewById(R.id.submit_button);
        submitBt.setOnClickListener(this);
        actionBar = findViewById(R.id.main_action_view);
        actionBar.updateView(getString(R.string.login_register),R.mipmap.action_back_icon, 0,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginActivity.newIntent(RegisterActivity.this));
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
        pwdEdit1 = findViewById(R.id.pwd_edit1);
        pwdEdit2 = findViewById(R.id.pwd_edit2);
        pwdEdit1.setDrawableRightListener(new CustomEditText.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                if(showPwd1) {
                    pwdEdit1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    Drawable rightDrawable = getResources().getDrawable(R.mipmap.pwd_trans_display);
                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    pwdEdit1.setCompoundDrawables(null, null, rightDrawable, null);
                } else {
                    pwdEdit1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Drawable rightDrawable = getResources().getDrawable(R.mipmap.pwd_trans_hide);
                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    pwdEdit1.setCompoundDrawables(null, null, rightDrawable, null);
                }
                showPwd1 = !showPwd1;
            }
        });
        pwdEdit2.setDrawableRightListener(new CustomEditText.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                if(showPwd2) {
                    pwdEdit2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    Drawable rightDrawable = getResources().getDrawable(R.mipmap.pwd_trans_display);
                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    pwdEdit2.setCompoundDrawables(null, null, rightDrawable, null);
                } else {
                    pwdEdit2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Drawable rightDrawable = getResources().getDrawable(R.mipmap.pwd_trans_hide);
                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    pwdEdit2.setCompoundDrawables(null, null, rightDrawable, null);
                }
                showPwd2 = !showPwd2;
            }
        });
        pwdEdit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pwdText1 = s.toString();
                checkSubmitEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pwdEdit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pwdText2 = s.toString();
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

            ConnectManager.getInstance().getScodeForRegister(this,phoneNum);
        } else {
            return;

        }
    }

    private void submit() {
        if(pwdText1.equals(pwdText2)) {
            ConnectManager.getInstance().register(this,phoneText,sCodeText,pwdText1);
        } else {
            Toast.makeText(this,getString(R.string.pwd_confirm_erro),Toast.LENGTH_LONG).show();
        }

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
                case ConnectManager.GET_SCODE_FOR_REGISTER:
                    sCode = data.getEntry();
                    return;
                case ConnectManager.REGISTER:
                    startActivity(LoginActivity.newIntent(this));
                    finish();
                    return;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        resetScode();
        Toast.makeText(this,getString(R.string.unknow_error),Toast.LENGTH_LONG).show();

    }

    private void checkSubmitEnable() {
        if(!StringUtils.isEmpty(phoneText) && !StringUtils.isEmpty(sCodeText) && !StringUtils.isEmpty(pwdText1) && !StringUtils.isEmpty(pwdText2)) {
            submitBt.setEnabled(true);
        } else {
            submitBt.setEnabled(false);
        }
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,RegisterActivity.class);
        return intent;
    }

}
