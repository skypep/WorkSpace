package com.toro.helper.modle.data;

import com.amap.api.maps2d.model.LatLng;

/**
 * Create By liujia
 * on 2018/11/3.
 **/
public class LocationInfo {
    private LatLng latLng;
    private String poiTitle;

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getPoiTitle() {
        return poiTitle;
    }

    public void setPoiTitle(String poiTitle) {
        this.poiTitle = poiTitle;
    }
}
