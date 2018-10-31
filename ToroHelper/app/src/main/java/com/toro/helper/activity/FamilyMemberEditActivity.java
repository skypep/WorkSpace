package com.toro.helper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.toro.helper.R;
import com.toro.helper.base.ToroActivity;
import com.toro.helper.modle.FamilyUserInfo;
import com.toro.helper.modle.data.ToroDataModle;
import com.toro.helper.utils.ConnectManager;
import com.toro.helper.utils.StringUtils;
import com.toro.helper.view.MainActionBar;
import com.toro.helper.view.ToroProgressView;

/**
 * Create By liujia
 * on 2018/10/29.
 **/
public class FamilyMemberEditActivity extends ToroActivity implements View.OnClickListener {

    private static final String EXTRA_USER_INFO = "extra_user_info";
    private static final String EXTRA_IS_CREATE = "extra_is_create";

    public static final String EDIT_RESULT = "edit_result";

    private String phoneText,nameText;
    private EditText phontEdit,nameEdit;
    private FamilyUserInfo userInfo;
    private boolean isCreate;
    private Button submitBt;
    private MainActionBar actionBar;
    private ToroProgressView progressView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.family_member_edit_activity);
        userInfo = (FamilyUserInfo) getIntent().getSerializableExtra(EXTRA_USER_INFO);
        isCreate = getIntent().getBooleanExtra(EXTRA_IS_CREATE,true);
        initView();
    }

    private void initView(){
        phontEdit = findViewById(R.id.edit_phone);
        nameEdit = findViewById(R.id.edit_name);
        submitBt = findViewById(R.id.submit_button);
        progressView = findViewById(R.id.toro_progress);
        actionBar = findViewById(R.id.main_action_view);

        phontEdit.addTextChangedListener(new TextWatcher() {
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
        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameText = s.toString();
                checkSubmitEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if(userInfo != null) {
            phontEdit.setText(userInfo.getPhone());
            nameEdit.setText(userInfo.getName());
        }
        submitBt.setOnClickListener(this);

        String title;
        if(isCreate) {
            title = getString(R.string.create_new_member);
        }else {
            title = getString(R.string.edit_member);
        }
        progressView.setProgressText(getString(R.string.submiting));
        actionBar.updateView(title,R.mipmap.action_back_icon, 0,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        },null);
    }

    private void checkSubmitEnable() {
        if(!StringUtils.isEmpty(phoneText) && !StringUtils.isEmpty(nameText)) {
            submitBt.setEnabled(true);
        } else {
            submitBt.setEnabled(false);
        }
    }

    private void submit() {
        progressView.show(this);
        ConnectManager.getInstance().addFamilyMember(this,phoneText,nameText, ToroDataModle.getInstance().getLocalData().getToken());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_button:
                submit();
                break;
        }
    }

    @Override
    public boolean bindData(int tag, Object object) {
        progressView.hide(this);
        boolean status = super.bindData(tag,object);
        if(status) {
            Toast.makeText(this,getString(R.string.create_new_member_sucsses),Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.putExtra(EDIT_RESULT, true);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(this,getString(R.string.create_new_member_failed),Toast.LENGTH_LONG).show();
        }
        return status;
    }

    public static Intent createAddIntent(Context context, FamilyUserInfo userInfo) {
        Intent intent = new Intent();
        intent.setClass(context,FamilyMemberEditActivity.class);
        intent.putExtra(EXTRA_USER_INFO,userInfo);
        intent.putExtra(EXTRA_IS_CREATE,true);
        return intent;
    }
}
