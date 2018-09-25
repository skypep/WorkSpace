// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: com/android/dialer/callintent/call_specific_app_data.proto

package com.android.dialer.callintent;

public interface CallSpecificAppDataOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.android.dialer.callintent.CallSpecificAppData)
    com.google.protobuf.MessageLiteOrBuilder {

  /**
   * <code>optional .com.android.dialer.callintent.CallInitiationType.Type call_initiation_type = 1;</code>
   */
  boolean hasCallInitiationType();
  /**
   * <code>optional .com.android.dialer.callintent.CallInitiationType.Type call_initiation_type = 1;</code>
   */
  com.android.dialer.callintent.CallInitiationType.Type getCallInitiationType();

  /**
   * <code>optional int32 position_of_selected_search_result = 2;</code>
   */
  boolean hasPositionOfSelectedSearchResult();
  /**
   * <code>optional int32 position_of_selected_search_result = 2;</code>
   */
  int getPositionOfSelectedSearchResult();

  /**
   * <code>optional int32 characters_in_search_string = 3;</code>
   */
  boolean hasCharactersInSearchString();
  /**
   * <code>optional int32 characters_in_search_string = 3;</code>
   */
  int getCharactersInSearchString();

  /**
   * <code>repeated .com.android.dialer.callintent.SpeedDialContactType.Type speed_dial_contact_type = 4;</code>
   */
  java.util.List<com.android.dialer.callintent.SpeedDialContactType.Type> getSpeedDialContactTypeList();
  /**
   * <code>repeated .com.android.dialer.callintent.SpeedDialContactType.Type speed_dial_contact_type = 4;</code>
   */
  int getSpeedDialContactTypeCount();
  /**
   * <code>repeated .com.android.dialer.callintent.SpeedDialContactType.Type speed_dial_contact_type = 4;</code>
   */
  com.android.dialer.callintent.SpeedDialContactType.Type getSpeedDialContactType(int index);

  /**
   * <code>optional int32 speed_dial_contact_position = 5;</code>
   */
  boolean hasSpeedDialContactPosition();
  /**
   * <code>optional int32 speed_dial_contact_position = 5;</code>
   */
  int getSpeedDialContactPosition();

  /**
   * <code>optional int64 time_since_app_launch = 6;</code>
   */
  boolean hasTimeSinceAppLaunch();
  /**
   * <code>optional int64 time_since_app_launch = 6;</code>
   */
  long getTimeSinceAppLaunch();

  /**
   * <code>optional int64 time_since_first_click = 7;</code>
   */
  boolean hasTimeSinceFirstClick();
  /**
   * <code>optional int64 time_since_first_click = 7;</code>
   */
  long getTimeSinceFirstClick();

  /**
   * <pre>
   * The following two list should be of the same length
   * (adding another message is not allowed here)
   * </pre>
   *
   * <code>repeated .com.android.dialer.logging.UiAction.Type ui_actions_since_app_launch = 8;</code>
   */
  java.util.List<com.android.dialer.logging.UiAction.Type> getUiActionsSinceAppLaunchList();
  /**
   * <pre>
   * The following two list should be of the same length
   * (adding another message is not allowed here)
   * </pre>
   *
   * <code>repeated .com.android.dialer.logging.UiAction.Type ui_actions_since_app_launch = 8;</code>
   */
  int getUiActionsSinceAppLaunchCount();
  /**
   * <pre>
   * The following two list should be of the same length
   * (adding another message is not allowed here)
   * </pre>
   *
   * <code>repeated .com.android.dialer.logging.UiAction.Type ui_actions_since_app_launch = 8;</code>
   */
  com.android.dialer.logging.UiAction.Type getUiActionsSinceAppLaunch(int index);

  /**
   * <code>repeated int64 ui_action_timestamps_since_app_launch = 9;</code>
   */
  java.util.List<java.lang.Long> getUiActionTimestampsSinceAppLaunchList();
  /**
   * <code>repeated int64 ui_action_timestamps_since_app_launch = 9;</code>
   */
  int getUiActionTimestampsSinceAppLaunchCount();
  /**
   * <code>repeated int64 ui_action_timestamps_since_app_launch = 9;</code>
   */
  long getUiActionTimestampsSinceAppLaunch(int index);

  /**
   * <code>optional int32 starting_tab_index = 10;</code>
   */
  boolean hasStartingTabIndex();
  /**
   * <code>optional int32 starting_tab_index = 10;</code>
   */
  int getStartingTabIndex();

  /**
   * <pre>
   * For recording the appearance of video call button
   * </pre>
   *
   * <code>optional int32 lightbringer_button_appear_in_expanded_call_log_item_count = 11;</code>
   */
  boolean hasLightbringerButtonAppearInExpandedCallLogItemCount();
  /**
   * <pre>
   * For recording the appearance of video call button
   * </pre>
   *
   * <code>optional int32 lightbringer_button_appear_in_expanded_call_log_item_count = 11;</code>
   */
  int getLightbringerButtonAppearInExpandedCallLogItemCount();

  /**
   * <code>optional int32 lightbringer_button_appear_in_collapsed_call_log_item_count = 12;</code>
   */
  boolean hasLightbringerButtonAppearInCollapsedCallLogItemCount();
  /**
   * <code>optional int32 lightbringer_button_appear_in_collapsed_call_log_item_count = 12;</code>
   */
  int getLightbringerButtonAppearInCollapsedCallLogItemCount();

  /**
   * <code>optional int32 lightbringer_button_appear_in_search_count = 13;</code>
   */
  boolean hasLightbringerButtonAppearInSearchCount();
  /**
   * <code>optional int32 lightbringer_button_appear_in_search_count = 13;</code>
   */
  int getLightbringerButtonAppearInSearchCount();
}
