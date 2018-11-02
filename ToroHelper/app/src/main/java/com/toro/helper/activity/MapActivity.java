package com.toro.helper.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.toro.helper.R;
import com.toro.helper.base.ToroActivity;
import com.toro.helper.modle.FamilyMemberInfo;
import com.toro.helper.utils.ConnectManager;
import com.toro.helper.utils.MapUtil;
import com.toro.helper.utils.PhoneUtils;
import com.toro.helper.view.MainActionBar;
import com.toro.helper.view.MapJumpLayout;
import com.toro.helper.view.ToroInfoWindow;

/**
 * Create By liujia
 * on 2018/11/1.
 **/
public class MapActivity extends ToroActivity implements View.OnClickListener {

    private static final String EXTRA_MEMBER_INFO = "extra_user_info";
    private FamilyMemberInfo userInfo;
    private MainActionBar mainActionBar;
    private MapJumpLayout mapJumpLayout;
    private ToroInfoWindow toroInfoWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        userInfo = (FamilyMemberInfo) getIntent().getSerializableExtra(EXTRA_MEMBER_INFO);
        toroInfoWindow = new ToroInfoWindow(this);
        toroInfoWindow.setHeadPhoto(userInfo.getUserInfo().getHeadPhoto());

        MapView mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        AMap aMap = mapView.getMap();
        LatLng latLng = new LatLng(22.7211940000,114.2180350000);
        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).snippet("DefaultMarker").icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(),R.mipmap.map_mark_icon))));
        aMap.setInfoWindowAdapter(toroInfoWindow);
        marker.showInfoWindow();
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        aMap.addCircle(new CircleOptions().
                center(latLng).
                radius(1000).
                fillColor(Color.argb(100, 255, 1, 1)).
                strokeColor(Color.argb(100, 255, 1, 1)).
                strokeWidth(1));

        mainActionBar = findViewById(R.id.main_action_view);
        mainActionBar.updateView(getString(R.string.action_menu_location), R.mipmap.action_back_icon, R.mipmap.refresh_action_icon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        mapJumpLayout = findViewById(R.id.map_jump_layout);
        findViewById(R.id.action_call_layout).setOnClickListener(this);
        findViewById(R.id.action_navigation_layout).setOnClickListener(this);
        findViewById(R.id.action_safeguard_layout).setOnClickListener(this);
        findViewById(R.id.action_trac_layout).setOnClickListener(this);
    }

    public static Intent createIntent(Context context,FamilyMemberInfo memberInfo) {
        Intent intent = new Intent();
        intent.setClass(context,MapActivity.class);
        intent.putExtra(EXTRA_MEMBER_INFO,memberInfo);
        return intent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_call_layout:
                PhoneUtils.call(this,userInfo.getUserInfo().getPhone());
                break;
            case R.id.action_navigation_layout:
                mapJumpLayout.showMapJumpLayout(114.2180350000,22.7211940000,userInfo.getDisplayName());
                break;
            case R.id.action_safeguard_layout:
                break;
            case R.id.action_trac_layout:
                break;
        }
    }

}
