package com.toro.helper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.toro.helper.R;
import com.toro.helper.base.ToroActivity;
import com.toro.helper.view.ToroInfoWindow;

/**
 * Create By liujia
 * on 2018/11/1.
 **/
public class MapActivity extends ToroActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        MapView mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        AMap aMap = mapView.getMap();
        LatLng latLng = new LatLng(39.906901,116.397972);
        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).snippet("DefaultMarker"));
        aMap.setInfoWindowAdapter(new ToroInfoWindow(this));
        marker.showInfoWindow();
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,MapActivity.class);
        return intent;
    }
}
