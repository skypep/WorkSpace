package com.toro.helper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.toro.helper.R;
import com.toro.helper.base.ToroActivity;
import com.toro.helper.modle.data.ToroDataModle;

/**
 * Create By liujia
 * on 2018/11/12.
 **/
public class PrivacyPolicyActivity extends ToroActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_policy_activity);
        findViewById(R.id.agreen).setOnClickListener(this);
        findViewById(R.id.un_agreen).setOnClickListener(this);
        ((TextView)findViewById(R.id.content)).setText(ToroDataModle.getInstance().getLocalData().getPrivatyPolicyContent(this));
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,PrivacyPolicyActivity.class);
        return intent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.agreen:
                ToroDataModle.getInstance().getLocalData().setAgreenPrivacyPolicy(true);
                finish();
                startActivity(LoginActivity.newIntent(PrivacyPolicyActivity.this));
                break;
            case R.id.un_agreen:
                finish();
        }
    }
}
