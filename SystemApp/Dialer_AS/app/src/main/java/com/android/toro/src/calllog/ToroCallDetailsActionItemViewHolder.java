package com.android.toro.src.calllog;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.dialer.R;
import com.android.dialer.app.calllog.BlockReportSpamListener;
import com.android.dialer.app.calllog.IntentProvider;
import com.android.dialer.blocking.BlockedNumbersMigrator;
import com.android.dialer.blocking.FilteredNumberAsyncQueryHandler;
import com.android.dialer.blocking.FilteredNumberCompat;
import com.android.dialer.calllogutils.PhoneCallDetails;
import com.android.dialer.dialercontact.DialerContact;
import com.android.dialer.logging.ContactSource;
import com.android.dialer.logging.DialerImpression;
import com.android.dialer.logging.Logger;
import com.android.dialer.util.DialerUtils;
import com.android.dialer.util.IntentUtil;

public class ToroCallDetailsActionItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //R.string.search_shortcut_add_to_contact  to  toro_add_to_contact添加到已有联系人
    private int[] actionTextsNoSaved = {
            R.string.search_shortcut_create_new_contact,
            R.string.toro_add_to_contact,
            R.string.call_log_action_send_message,
            R.string.call_log_action_block_number
    };

    private int[] actionIconsNosaved = {
            R.drawable.toro_details_add,
            R.drawable.toro_details_add,
            R.drawable.toro_details_send_message,
            R.drawable.toro_details_disable,
    };

    private int[] actionTextsSaved = {
            R.string.call_log_action_send_message,
            R.string.toro_send_location,
            R.string.call_log_action_block_number
    };

    private int[] actionIconssaved = {
            R.drawable.toro_details_send_message,
            R.drawable.toro_details_send_loction,
            R.drawable.toro_details_disable
    };
    private final ImageView actionIcon;
    private final TextView actionText;
    private final Context context;
    private DialerContact contact;
    private ToroBlockReportSpamListener mBlockReportSpamListener;

    private PhoneCallDetails callDetails;
    private Integer blockId;

    public ToroCallDetailsActionItemViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        actionIcon = itemView.findViewById(R.id.toro_detail_action_icon);
        actionText = itemView.findViewById(R.id.toro_detail_action_text);
        itemView.setOnClickListener(this);
    }

    public void setActionDetailsPosition(int position,boolean isSaved,DialerContact contact,Integer blockId,PhoneCallDetails callDetails,ToroBlockReportSpamListener mBlockReportSpamListener){
        this.contact = contact;
        this.callDetails = callDetails;
        this.blockId = blockId;
        int imgID,textID;
        if(isSaved) {
            imgID = actionIconssaved[position];
            textID = actionTextsSaved[position];
        } else {
            imgID = actionIconsNosaved[position];
            textID = actionTextsNoSaved[position];
        }
        if(textID == R.string.call_log_action_block_number) {
            if(callDetails.isBlocked) {
                textID = R.string.call_log_action_unblock_number;
            }
        }
        actionIcon.setImageResource(imgID);
        actionText.setText(textID);
        this.mBlockReportSpamListener = mBlockReportSpamListener;
//        mBlockReportSpamListener = new ToroBlockReportSpamListener(
//                context,
//                new ToroBlockListener(),
//                ((Activity) context).getFragmentManager(),
//                new FilteredNumberAsyncQueryHandler(context));
    }

    private void maybeShowBlockNumberMigrationDialog(BlockedNumbersMigrator.Listener listener) {
        if (!FilteredNumberCompat.maybeShowBlockNumberMigrationDialog(
                context, ((Activity) context).getFragmentManager(), listener)) {
            listener.onComplete();
        }
    }

    @Override
    public void onClick(View v) {
        if(actionText.getText().equals(context.getResources().getString(R.string.call_log_action_send_message))) {
            DialerUtils.startActivityWithErrorToast(context, IntentUtil.getSendSmsIntent(contact.getNumber()));
        }else if(actionText.getText().equals(context.getResources().getString(R.string.toro_send_location))) {
            Intent intent = IntentUtil.getSendSmsIntent(contact.getNumber());
            intent.putExtra("sms_body", "我的位置是：广东省深圳市龙岗区天安数码城A座");
            DialerUtils.startActivityWithErrorToast(context, intent);
        } else if(actionText.getText().equals(context.getResources().getString(R.string.call_log_action_block_number))) {
            Logger.get(context).logImpression(DialerImpression.Type.CALL_LOG_CONTEXT_MENU_BLOCK_NUMBER);
            maybeShowBlockNumberMigrationDialog(
                    new BlockedNumbersMigrator.Listener() {
                        @Override
                        public void onComplete() {
                            mBlockReportSpamListener.onBlock(
                                    contact.getDisplayNumber(), contact.getNumber(), callDetails.countryIso, callDetails.callTypes[0], callDetails.sourceType);
                        }
                    });
        } else if(actionText.getText().equals(context.getResources().getString(R.string.call_log_action_unblock_number))) {
            Logger.get(context)
                    .logImpression(DialerImpression.Type.CALL_LOG_CONTEXT_MENU_UNBLOCK_NUMBER);
            mBlockReportSpamListener.onUnblock(
                    contact.getDisplayNumber(), contact.getNumber(), callDetails.countryIso, callDetails.callTypes[0], callDetails.sourceType, callDetails.isSpam, blockId);
        }else if(actionText.getText().equals(context.getResources().getString(R.string.search_shortcut_create_new_contact))){
            IntentProvider intentProvider = IntentProvider.getAddContactIntentProvider(
                    callDetails.cachedContactInfo.lookupUri, callDetails.cachedContactInfo.name, callDetails.cachedContactInfo.number, callDetails.cachedContactInfo.type, true /* isNewContact */);
            final Intent intent = intentProvider.getIntent(context);
            DialerUtils.startActivityWithErrorToast(context, intent);

        } else if(actionText.getText().equals(context.getResources().getString(R.string.toro_add_to_contact))) {
            IntentProvider intentProvider = IntentProvider.getAddContactIntentProvider(
                    callDetails.cachedContactInfo.lookupUri, callDetails.cachedContactInfo.name, callDetails.cachedContactInfo.number, callDetails.cachedContactInfo.type, false /* isNewContact */);
            final Intent intent = intentProvider.getIntent(context);
            DialerUtils.startActivityWithErrorToast(context, intent);

        }
    }

    class ToroBlockListener implements BlockListener{

        @Override
        public void onBlockComplete(Uri uri) {
            actionText.setText(R.string.call_log_action_unblock_number);
        }

        @Override
        public void onUnblockComplete(int rows, ContentValues values) {
            actionText.setText(R.string.call_log_action_block_number);
        }
    }

    interface BlockListener {
        public void onBlockComplete(Uri uri);
        public void onUnblockComplete(int rows, ContentValues values);
    }
}
