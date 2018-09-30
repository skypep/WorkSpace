//package com.android.contacts.toro.activity;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.provider.CallLog;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.TextView;
//
//import com.android.dialer.R;
//import com.android.dialer.calldetails.CallDetailsEntries;
//import com.android.dialer.calllogutils.CallEntryFormatter;
//import com.android.dialer.calllogutils.CallTypeHelper;
//
//public class ToroCallDetailsItemViewHolder extends RecyclerView.ViewHolder {
//
//    private TextView dateView,timeView,typeView,callTimeView;
//    private final Context context;
//
//    public ToroCallDetailsItemViewHolder(View itemView) {
//        super(itemView);
//        context = itemView.getContext();
//        dateView = itemView.findViewById(R.id.call_details_item_date);
//        timeView = itemView.findViewById(R.id.toro_detail_item_time);
//        typeView = itemView.findViewById(R.id.toro_detail_item_type);
//        callTimeView = itemView.findViewById(R.id.toro_detail_item_calltime);
//    }
//
//    public void setCallDetails(
//            ToroCallDetailsAdapter.ToroCallDetailsEntries toroEntry,
//            CallTypeHelper callTypeHelper,
//            boolean showMultimediaDivider) {
//        CallDetailsEntries.CallDetailsEntry entry = toroEntry.callDetailsEntries;
//        int callType = entry.getCallType();
//        boolean isVideoCall = (entry.getFeatures() & CallLog.Calls.FEATURES_VIDEO) == CallLog.Calls.FEATURES_VIDEO;
//        boolean isPulledCall =
//                (entry.getFeatures() & CallLog.Calls.FEATURES_PULLED_EXTERNALLY)
//                        == CallLog.Calls.FEATURES_PULLED_EXTERNALLY;
//
//        if(toroEntry.needShowDate) {
//            dateView.setVisibility(View.VISIBLE);
//            dateView.setText(toroEntry.dateString);
//        } else {
//            dateView.setVisibility(View.GONE);
//        }
//
//        if(entry.getCallType() == CallLog.Calls.MISSED_TYPE) {
//            typeView.setTextColor(Color.RED);
//        } else {
//            typeView.setTextColor(context.getResources().getColor(R.color.toro_call_details_item_text_color));
//        }
//
//        timeView.setText(toroEntry.timeString);
//        typeView.setText(callTypeHelper.getCallTypeText(callType, isVideoCall, isPulledCall));
//        callTimeView.setText(
//                CallEntryFormatter.formatDurationAndDataUsage(
//                        context, entry.getDuration(), entry.getDataUsage()));
//    }
//}
