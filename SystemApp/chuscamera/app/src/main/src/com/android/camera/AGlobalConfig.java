package com.android.camera;

/**
 * Created by THINK on 2017/6/23.
 */

public class AGlobalConfig {
	/*******************/
	/* for global */
	public static final boolean config_disable_scan_module_en = false;
	public static final boolean config_slidetab_reorder_en = true; // false; // true;

	/*******************/
	/* for photo */
	public static final int config_module_PHOTO_MODULE_PREVIEW_TYPE = 1; // 0;
		/**
		* 0, default SurfaceView
		* 1, TextureView
		*/
	public static final float config_module_PHOTO_MODULE_camera_switch_blur_radius = 50; // 20; // 150;
		/**
		* 
		*/
	public static final boolean config_module_PHOTO_MODULE_use_new_color_effect_layout = false; // true;
		/**
		* new MyTextview2 impl
		*/
	public static final int config_module_PHOTO_MODULE_delay_before_camera_switch_do = 50;

	public static final boolean config_module_PHOTO_MODULE_keep_original_picture = true; // false; // true; // frankie, 2018.01.23, // false;
		/**
		* on 7.1 set false
		* on 8.0 set true to prevent jpeg -> bitmap -> re-process -> jpeg
		*/
	public static final boolean config_module_PHOTO_MODULE_enable_filter_group_change_button = true; // false; // frankie, 2018.02.07, 
		/**
		* on 7.1 set false
		* on 8.0 set true
		*/
	public static final boolean config_module_PHOTO_MODULE_use_new_settings_en = true; // false; // true;

	

	/*******************/
	/* for video */
	public static final int config_module_VIDEO_MODULE_PREVIEW_TYPE = 1; // 0;
		/**
		* 0, default SurfaceView
		* 1, TextureView
		*/
	public static final float config_module_VIDEO_MODULE_camera_switch_blur_radius = 50; // 20; // 50; // 150;

	public static final boolean config_module_VIDEO_MODULE_pause_button_en = false;	// disable pause button
	public static final boolean config_module_VIDEO_MODULE_mute_button_en = false;	// disable mute button
	public static final int config_module_VIDEO_MODULE_delay_before_camera_switch_do = 50;
	public static final boolean config_module_VIDEO_MODULE_use_new_settings_en = true; // false; // true;
	

	/*******************/
	/* for panorama */
	public static final boolean config_module_PANO_MODULE_debug_sensor_en = true; // false;

	/*******************/
	/* for scan */
	public static final boolean config_module_SCAN_preview_data_debug_en = false; // false; // true;
	public static final boolean config_module_SCAN_start_preview_data_process_en = true; // false;




	

}
