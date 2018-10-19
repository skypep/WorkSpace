package com.toro.helper.activity;

import android.app.AppComponentFactory;
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
public class RegisterActivity extends AppCompatActivity {


    private CustomEditText pwdEdit;
    private boolean showPwd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
    }


    public static Intent newIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,RegisterActivity.class);
        return intent;
    }

}
