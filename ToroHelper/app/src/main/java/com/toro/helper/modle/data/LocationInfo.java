package com.toro.helper.modle.data;

import com.amap.api.maps2d.model.LatLng;

import org.json.JSONObject;

/**
 * Create By liujia
 * on 2018/11/3.
 **/
public class LocationInfo {
    private LatLng latLng;
    private String poiTitle;
    private String time;

    public static LocationInfo newInstance(JSONObject obj) {
        LocationInfo instance = new LocationInfo();
        try{
            double lat = obj.getDouble("latitude");
            double lng = obj.getDouble("longitude");
            instance.latLng = new LatLng(lat,lng);
            instance.poiTitle = obj.getString("poi");
            instance.time = obj.getString("time");
            return instance;
        }catch (Exception e) {

        }
        return null;
    }

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
