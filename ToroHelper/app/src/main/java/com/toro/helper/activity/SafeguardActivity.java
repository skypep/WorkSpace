package com.toro.helper.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.toro.helper.R;
import com.toro.helper.base.ToroActivity;
import com.toro.helper.modle.data.LocationInfo;
import com.toro.helper.modle.data.ToroDataModle;
import com.toro.helper.view.MainActionBar;

/**
 * Create By liujia
 * on 2018/11/3.
 **/
public class SafeguardActivity extends ToroActivity implements View.OnClickListener {

    private static final String EXTRA_UID = "extra_uid";

    private MainActionBar mainActionBar;
    private int uid;
    private LinearLayout settingLayout;
    private RelativeLayout mapLayout;
    private MapView mapView;
    private Marker marker;
    private Circle circle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.safeguard_activity);
        uid = getIntent().getIntExtra(EXTRA_UID,0);
        mainActionBar = findViewById(R.id.main_action_view);
        mainActionBar.updateView(getString(R.string.map_action_safeguard), R.mipmap.action_back_icon, R.mipmap.action_setting_icon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SafeguardSettingActivity.createIntent(SafeguardActivity.this));
            }
        });

        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView();
    }

    private void updateView() {
        if(ToroDataModle.getInstance().getLocalData().getHomeLocation() == null) {
            showSettingLayout();
        } else {
            showMapLayout();
        }
    }

    private void initView() {
        settingLayout = findViewById(R.id.setting_layout);
        mapLayout = findViewById(R.id.map_layout);
        findViewById(R.id.setting_button).setOnClickListener(this);
    }

    private void showSettingLayout() {
        settingLayout.setVisibility(View.VISIBLE);
        mapLayout.setVisibility(View.GONE);
    }

    private void showMapLayout() {
        mapLayout.setVisibility(View.VISIBLE);
        settingLayout.setVisibility(View.GONE);

        AMap aMap = mapView.getMap();
        LatLng latLng = new LatLng(22.7211940000,114.2180350000);
        LatLng homeLatLng = ToroDataModle.getInstance().getLocalData().getHomeLocation().getLatLng();
        if(marker == null) {
            marker = aMap.addMarker(new MarkerOptions().position(latLng).snippet("DefaultMarker").icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(),R.mipmap.map_mark_icon))));
        }else {
            marker.setPosition(latLng);
        }

        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(homeLatLng));

        if(circle == null) {
            circle = aMap.addCircle(new CircleOptions().
                    center(homeLatLng).
                    radius(ToroDataModle.getInstance().getLocalData().getSafeguardRadius()).
                    fillColor(Color.argb(100, 255, 1, 1)).
                    strokeColor(Color.argb(100, 255, 1, 1)).
                    strokeWidth(1));
        } else {
            circle.setCenter(homeLatLng);
            circle.setRadius(ToroDataModle.getInstance().getLocalData().getSafeguardRadius());
        }

    }

    public static Intent createIntent(Context context,int uid) {
        Intent intent = new Intent();
        intent.setClass(context,SafeguardActivity.class);
        intent.putExtra(EXTRA_UID,uid);
        return intent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_button:
                startActivity(SettingHomeLoactionActivity.createIntent(this));
                break;
        }
    }
}
