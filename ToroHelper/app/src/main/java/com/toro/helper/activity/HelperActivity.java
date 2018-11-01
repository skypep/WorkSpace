package com.toro.helper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.toro.helper.R;
import com.toro.helper.base.ToroActivity;
import com.toro.helper.modle.FamilyMemberInfo;
import com.toro.helper.utils.ImageLoad;
import com.toro.helper.view.MainActionBar;
import com.toro.helper.view.RoundnessImageView;

/**
 * Create By liujia
 * on 2018/11/1.
 **/
public class HelperActivity extends ToroActivity implements View.OnClickListener {

    private static final String EXTRA_MEMBER_INFO = "extra_user_info";

    private MainActionBar mainActionBar;
    private FamilyMemberInfo userInfo;

    private RoundnessImageView headImageView;
    private TextView nameText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helper_activity);
        userInfo = (FamilyMemberInfo) getIntent().getSerializableExtra(EXTRA_MEMBER_INFO);

        String titile = "";
        if(userInfo != null) {
            titile = userInfo.getDisplayName();
        }

        initView();

        mainActionBar = findViewById(R.id.main_action_view);
        mainActionBar.updateView(titile, R.mipmap.action_back_icon, R.mipmap.icon_personal, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void initView() {
        headImageView = findViewById(R.id.head_img);
        nameText = findViewById(R.id.name_text);

        nameText.setText(userInfo.getDisplayName());

        View setting1 = findViewById(R.id.setting_item1);
        View setting2 = findViewById(R.id.setting_item2);
        View setting3 = findViewById(R.id.setting_item3);
        View setting4 = findViewById(R.id.setting_item4);

        setItemView(setting1,R.string.action_menu_health,R.mipmap.action_menu_health,null);
        setItemView(setting2,R.string.action_menu_location,R.mipmap.action_menu_localtion,null);
        setItemView(setting3,R.string.action_menu_call,R.mipmap.action_menu_call,null);
        setItemView(setting4,R.string.action_menu_helper,R.mipmap.action_menu_helper,null);

        setting1.setOnClickListener(this);
        setting2.setOnClickListener(this);
        setting3.setOnClickListener(this);
        setting4.setOnClickListener(this);

        if(userInfo != null && userInfo.getUserInfo() != null && userInfo.getUserInfo().getHeadPhoto() != null) {
            ImageLoad.GlidLoad(headImageView,userInfo.getUserInfo().getHeadPhoto(),R.mipmap.default_head);
        }else {
            headImageView.setImageResource(R.mipmap.default_head);
        }
    }

    private void setItemView(View itemView, int stringID, int imgID, View.OnClickListener listener) {
        ((ImageView)itemView.findViewById(R.id.title_icon)).setImageResource(imgID);
        ((TextView)itemView.findViewById(R.id.title_text)).setText(stringID);
        itemView.setOnClickListener(listener);
    }

    public static Intent createIntent(Context context, FamilyMemberInfo memberInfo) {
        Intent intent = new Intent();
        intent.setClass(context,HelperActivity.class);
        intent.putExtra(EXTRA_MEMBER_INFO,memberInfo);
        return intent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_item1:
                break;
            case R.id.setting_item2:
                startActivity(MapActivity.createIntent(this));
                break;
            case R.id.setting_item3:
                break;
            case R.id.setting_item4:
                break;
        }
    }
}
