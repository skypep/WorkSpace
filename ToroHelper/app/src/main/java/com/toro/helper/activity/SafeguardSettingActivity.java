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
import com.toro.helper.modle.data.LocationInfo;
import com.toro.helper.modle.data.ToroDataModle;
import com.toro.helper.utils.StringUtils;
import com.toro.helper.view.MainActionBar;
import com.toro.helper.view.SafeguardRadiusItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By liujia
 * on 2018/11/3.
 **/
public class SafeguardSettingActivity extends ToroActivity implements View.OnClickListener {

    private TextView poiTextView;
    private ImageView editLocation;
    private MainActionBar mainActionBar;
    private LocationInfo locationInfo;
    private SafeguardRadiusRGroups radiusRGroups;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.safeguard_setting_activity);
        mainActionBar = findViewById(R.id.main_action_view);
        mainActionBar.updateView(getString(R.string.safeguard_setting), R.mipmap.action_back_icon, 0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, null);

        poiTextView = findViewById(R.id.poi_title);
        findViewById(R.id.change_location).setOnClickListener(this);

        SafeguardRadiusItem radius_500 = findViewById(R.id.radius_500);
        SafeguardRadiusItem radius_1000 = findViewById(R.id.radius_1000);
        SafeguardRadiusItem radius_1500 = findViewById(R.id.radius_1500);
        radiusRGroups = new SafeguardRadiusRGroups();
        radiusRGroups.add(radius_500,R.string.radius_500,500);
        radiusRGroups.add(radius_1000,R.string.radius_1000,1000);
        radiusRGroups.add(radius_1500,R.string.radius_1500,1500);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView();
    }

    private void updateView() {
        locationInfo = ToroDataModle.getInstance().getLocalData().getHomeLocation();
        if(locationInfo == null) {
            poiTextView.setText(R.string.home_location_empty);
        } else if(StringUtils.isEmpty(locationInfo.getPoiTitle())) {
            poiTextView.setText(R.string.home_location_unknow);
        }else {
            poiTextView.setText(locationInfo.getPoiTitle());
        }

        int radius = ToroDataModle.getInstance().getLocalData().getSafeguardRadius();
        if(radius == 0) {
            radiusRGroups.setRadius(500);
        } else {
            radiusRGroups.setRadius(radius);
        }

    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,SafeguardSettingActivity.class);
        return intent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_location:
                startActivity(SettingHomeLoactionActivity.createIntent(this));
                break;
        }
    }

    private class SafeguardRadiusRGroups implements View.OnClickListener {
        List<SafeguardRadiusItem> radius;

        SafeguardRadiusRGroups() {
            radius = new ArrayList<>();
        }

        public void add(SafeguardRadiusItem childView,int titleRes,int value) {
//            SafeguardRadiusItem itemView = childView.findViewById(R.id.radius_layout);
//            SafeguardRadiusItem itemView = childView
            childView.init(titleRes,value);
            childView.setTag(value);
            childView.setOnClickListener(this);
            radius.add(childView);
        }

        public void setRadius(int value) {
            for(SafeguardRadiusItem radiu:radius) {
                if(radiu.getValue() == value) {
                    radiu.setSelected(true);
                    ToroDataModle.getInstance().getLocalData().setSafeguardRadius(value);
                }else {
                    radiu.setSelected(false);
                }
            }
        }

        @Override
        public void onClick(View v) {
            setRadius((Integer) v.getTag());
        }
    }
}
