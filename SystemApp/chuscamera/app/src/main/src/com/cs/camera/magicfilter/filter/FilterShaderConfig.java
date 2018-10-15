package com.cs.camera.magicfilter.filter;

/**
 * Created by THINK on 2017/6/8.
 */



//import com.android.camera2.R;
import com.toro.camera.R;

public class FilterShaderConfig {

    public static int getShaderFromClassName(String className, int shader_type_flag) {
        if(className.equals(FilterCameraInput.class.getSimpleName())) {
            if(shader_type_flag == 0) {return R.raw.default_vertex;}
            else if(shader_type_flag == 1) {return R.raw.default_fragment;}
            return -1;
        }
        else if(className.equals(MagicAmaroFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.amaro;}
            return -1;
        }
        else if(className.equals(MagicAntiqueFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.antique;}
            return -1;
        }
        else if(className.equals(MagicBeautyFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.beauty;}
            return -1;
        }
        else if(className.equals(MagicBlackCatFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.blackcat;}
            return -1;
        }
        else if(className.equals(MagicBrannanFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.brannan;}
            return -1;
        }
        else if(className.equals(MagicBrooklynFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.brooklyn;}
            return -1;
        }
        else if(className.equals(MagicCalmFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.calm;}
            return -1;
        }
        else if(className.equals(MagicCoolFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.cool;}
            return -1;
        }
        else if(className.equals(MagicCrayonFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.crayon;}
            return -1;
        }
        else if(className.equals(MagicEarlyBirdFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.earlybird;}
            return -1;
        }
        else if(className.equals(MagicEmeraldFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.emerald;}
            return -1;
        }
        else if(className.equals(MagicEvergreenFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.evergreen;}
            return -1;
        }
        else if(className.equals(MagicFreudFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.freud;}
            return -1;
        }
        else if(className.equals(MagicHealthyFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.healthy;}
            return -1;
        }
        else if(className.equals(MagicHefeFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.hefe;}
            return -1;
        }
        else if(className.equals(MagicHudsonFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.hudson;}
            return -1;
        }
        else if(className.equals(MagicInkwellFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.inkwell;}
            return -1;
        }
        else if(className.equals(MagicKevinFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.kevin_new;}
            return -1;
        }
        else if(className.equals(MagicLatteFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.latte;}
            return -1;
        }
        else if(className.equals(MagicLomoFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.lomo;}
            return -1;
        }
        else if(className.equals(MagicN1977Filter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.n1977;}
            return -1;
        }
        else if(className.equals(MagicNashvilleFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.nashville;}
            return -1;
        }
        else if(className.equals(MagicNostalgiaFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.nostalgia;}
            return -1;
        }
        else if(className.equals(MagicPixarFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.pixar;}
            return -1;
        }
        else if(className.equals(MagicRiseFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.rise;}
            return -1;
        }
        else if(className.equals(MagicRomanceFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.romance;}
            return -1;
        }
        else if(className.equals(MagicSakuraFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.sakura;}
            return -1;
        }
        else if(className.equals(MagicSierraFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.sierra;}
            return -1;
        }
        else if(className.equals(MagicSketchFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.sketch;}
            return -1;
        }
        else if(className.equals(MagicSkinWhitenFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.skinwhiten;}
            return -1;
        }
        else if(className.equals(MagicSunriseFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.sunrise;}
            return -1;
        }
        else if(className.equals(MagicSunsetFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.sunset;}
            return -1;
        }
        else if(className.equals(MagicSutroFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.sutro;}
            return -1;
        }
        else if(className.equals(MagicSweetsFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.sweets;}
            return -1;
        }
        else if(className.equals(MagicTenderFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.tender;}
            return -1;
        }
        else if(className.equals(MagicToasterFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.toaster2_filter_shader;}
            return -1;
        }
        else if(className.equals(MagicValenciaFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.valencia;}
            return -1;
        }
        else if(className.equals(MagicWaldenFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.walden;}
            return -1;
        }
        else if(className.equals(MagicWarmFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.warm;}
            return -1;
        }
        else if(className.equals(MagicWhiteCatFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.whitecat;}
            return -1;
        }
        else if(className.equals(MagicXproIIFilter.class.getSimpleName())) {
            if(shader_type_flag == 0) {return -1;}
            else if(shader_type_flag == 1) {return R.raw.xproii_filter_shader;}
            return -1;
        }

        return -1;
    }

}
