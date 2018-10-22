package com.toro.helper.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;

import com.toro.helper.R;
import com.toro.helper.view.CustomEditText;
import com.toro.helper.view.MainActionBar;

/**
 * Create By liujia
 * on 2018/10/22.
 **/
public class ResetPassword extends AppCompatActivity implements View.OnClickListener {

    private MainActionBar actionBar;
    private CustomEditText pwdEdit1,pwdEdit2;
    private boolean showPwd1,showPwd2;
    private Button submitBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_reset_activity);
        actionBar = findViewById(R.id.main_action_view);
        actionBar.updateView(getString(R.string.setting_pwd),R.mipmap.action_back_icon, 0,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        },null);
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
        submitBt = findViewById(R.id.submit_button);
        submitBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button:
                startActivity(MainActivity.newIntent(this));
                finish();
                break;
        }
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,ResetPassword.class);
        return intent;
    }
}
