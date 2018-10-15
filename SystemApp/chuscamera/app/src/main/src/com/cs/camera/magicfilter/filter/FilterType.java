package com.cs.camera.magicfilter.filter;

import com.toro.camera.R;

/**
 * Created by why8222 on 2016/2/25.
 */
public enum  FilterType {

    NONE(R.string.chus_filter_type_name_none),
    FAIRYTALE(R.string.chus_filter_type_name_fairytale),
    SUNRISE(R.string.chus_filter_type_name_sunrise),
    SUNSET(R.string.chus_filter_type_name_sunset),
    WHITECAT(R.string.chus_filter_type_name_whitecat),
    BLACKCAT(R.string.chus_filter_type_name_blackcat),
    SKINWHITEN(R.string.chus_filter_type_name_skinwhiten),
    HEALTHY(R.string.chus_filter_type_name_healthy),
    SWEETS(R.string.chus_filter_type_name_sweets),
    ROMANCE(R.string.chus_filter_type_name_romance),
    SAKURA(R.string.chus_filter_type_name_sakura),
    WARM(R.string.chus_filter_type_name_warm),
    ANTIQUE(R.string.chus_filter_type_name_antique),
    NOSTALGIA(R.string.chus_filter_type_name_nostalgia),
    CALM(R.string.chus_filter_type_name_calm),
    LATTE(R.string.chus_filter_type_name_latte),
    TENDER(R.string.chus_filter_type_name_tender),
    COOL(R.string.chus_filter_type_name_cool),
    EMERALD(R.string.chus_filter_type_name_emerald),
    EVERGREEN(R.string.chus_filter_type_name_evergreen),
    CRAYON(R.string.chus_filter_type_name_crayon),
    SKETCH(R.string.chus_filter_type_name_sketch),
    AMARO(R.string.chus_filter_type_name_amaro),
    BRANNAN(R.string.chus_filter_type_name_brannan),
    BROOKLYN(R.string.chus_filter_type_name_brooklyn),
    EARLYBIRD(R.string.chus_filter_type_name_earlybird),
    FREUD(R.string.chus_filter_type_name_freud),
    HEFE(R.string.chus_filter_type_name_hefe),
    HUDSON(R.string.chus_filter_type_name_hudson),
    INKWELL(R.string.chus_filter_type_name_inkwell),
    KEVIN(R.string.chus_filter_type_name_kevin),
    LOMO(R.string.chus_filter_type_name_lomo),
    N1977(R.string.chus_filter_type_name_n1977),
    NASHVILLE(R.string.chus_filter_type_name_nashville),
    PIXAR(R.string.chus_filter_type_name_pixar),
    RISE(R.string.chus_filter_type_name_rise),
    SIERRA(R.string.chus_filter_type_name_sierra),
    SUTRO(R.string.chus_filter_type_name_sutro),
    TOASTER2(R.string.chus_filter_type_name_toaster2),
    VALENCIA(R.string.chus_filter_type_name_valencia),
    WALDEN(R.string.chus_filter_type_name_walden),
    XPROII(R.string.chus_filter_type_name_xproii),
    //image adjust
    CONTRAST(R.string.chus_filter_type_name_contrast),
    BRIGHTNESS(R.string.chus_filter_type_name_brightness),
    EXPOSURE(R.string.chus_filter_type_name_exposure),
    HUE(R.string.chus_filter_type_name_hue),
    SATURATION(R.string.chus_filter_type_name_saturation),
    SHARPEN(R.string.chus_filter_type_name_sharpen),
    IMAGE_ADJUST(R.string.chus_filter_type_name_imageadjust),

    MAX_TYPE(R.string.chus_filter_type_name_max_type);

    private int nameId;
    private FilterType(int id) {
        this.nameId = id;
    }
    public int getNameId(){
        return nameId;
    }
}

