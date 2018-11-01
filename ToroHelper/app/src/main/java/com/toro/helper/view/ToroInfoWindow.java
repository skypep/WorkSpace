package com.toro.helper.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.Marker;
import com.toro.helper.R;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Create By liujia
 * on 2018/11/1.
 **/
public class ToroInfoWindow implements AMap.InfoWindowAdapter {

    private Context mContext;

    @Override
    public View getInfoWindow(Marker marker) {
        View infoWindow = LayoutInflater.from(mContext).inflate(
                R.layout.local_info_window, null);
        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View infoWindow = LayoutInflater.from(mContext).inflate(
                R.layout.local_info_window, null);
        return infoWindow;
    }

    public ToroInfoWindow(Context context) {
        this.mContext = context;
    }
}
