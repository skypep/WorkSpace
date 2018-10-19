package com.toro.helper.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.toro.helper.R;
import com.toro.helper.view.CustomEditText;

/**
 * Create By liujia
 * on 2018/10/19.
 **/
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private CustomEditText pwdEdit;
    private boolean showPwd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        pwdEdit = findViewById(R.id.login_pwd_edit);
        pwdEdit.setDrawableRightListener(new CustomEditText.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                if(showPwd) {
                    pwdEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    Drawable rightDrawable = getResources().getDrawable(R.mipmap.pwd_trans_display);
                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    pwdEdit.setCompoundDrawables(null, null, rightDrawable, null);
                } else {
                    pwdEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Drawable rightDrawable = getResources().getDrawable(R.mipmap.pwd_trans_hide);
                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    pwdEdit.setCompoundDrawables(null, null, rightDrawable, null);
                }
                showPwd = !showPwd;
            }
        });

        findViewById(R.id.login_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_register:
                startActivity(RegisterActivity.newIntent(this));
                break;
        }
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,LoginActivity.class);
        return intent;
    }
}
