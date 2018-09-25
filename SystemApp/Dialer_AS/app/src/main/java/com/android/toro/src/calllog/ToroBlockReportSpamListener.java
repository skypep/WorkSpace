package com.android.toro.src.calllog;

import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.android.dialer.app.calllog.CallLogListItemViewHolder;
import com.android.dialer.blocking.BlockReportSpamDialogs;
import com.android.dialer.blocking.FilteredNumberAsyncQueryHandler;
import com.android.dialer.common.LogUtil;
import com.android.dialer.logging.ContactSource;
import com.android.dialer.logging.DialerImpression;
import com.android.dialer.logging.Logger;
import com.android.dialer.logging.ReportingLocation;
import com.android.dialer.spam.Spam;

public class ToroBlockReportSpamListener implements CallLogListItemViewHolder.OnClickListener {
    private final Context mContext;
    private final FragmentManager mFragmentManager;
    private final FilteredNumberAsyncQueryHandler mFilteredNumberAsyncQueryHandler;
    ToroCallDetailsActionItemViewHolder.BlockListener blockListener;

    public ToroBlockReportSpamListener(
            Context context,
            ToroCallDetailsActionItemViewHolder.BlockListener blockListener,
            FragmentManager fragmentManager,
            FilteredNumberAsyncQueryHandler filteredNumberAsyncQueryHandler) {
        mContext = context;
        mFragmentManager = fragmentManager;
        mFilteredNumberAsyncQueryHandler = filteredNumberAsyncQueryHandler;
        this.blockListener = blockListener;
    }

    @Override
    public void onBlockReportSpam(
            String displayNumber,
            final String number,
            final String countryIso,
            final int callType,
            @NonNull final ContactSource.Type contactSourceType) {
        BlockReportSpamDialogs.BlockReportSpamDialogFragment.newInstance(
                displayNumber,
                Spam.get(mContext).isDialogReportSpamCheckedByDefault(),
                new BlockReportSpamDialogs.OnSpamDialogClickListener() {
                    @Override
                    public void onClick(boolean isSpamChecked) {
                        LogUtil.i("BlockReportSpamListener.onBlockReportSpam", "onClick");
                        if (isSpamChecked && Spam.get(mContext).isSpamEnabled()) {
                            Logger.get(mContext)
                                    .logImpression(
                                            DialerImpression.Type
                                                    .REPORT_CALL_AS_SPAM_VIA_CALL_LOG_BLOCK_REPORT_SPAM_SENT_VIA_BLOCK_NUMBER_DIALOG);
                            Spam.get(mContext)
                                    .reportSpamFromCallHistory(
                                            number,
                                            countryIso,
                                            callType,
                                            ReportingLocation.Type.CALL_LOG_HISTORY,
                                            contactSourceType);
                        }
                        mFilteredNumberAsyncQueryHandler.blockNumber(
                                new FilteredNumberAsyncQueryHandler.OnBlockNumberListener() {
                                    @Override
                                    public void onBlockComplete(Uri uri) {
                                        Logger.get(mContext)
                                                .logImpression(DialerImpression.Type.USER_ACTION_BLOCKED_NUMBER);
                                        if(blockListener != null) {
                                            blockListener.onBlockComplete(uri);
                                        }

                                    }
                                },
                                number,
                                countryIso);
                    }
                },
                null)
                .show(mFragmentManager, BlockReportSpamDialogs.BLOCK_REPORT_SPAM_DIALOG_TAG);
    }

    @Override
    public void onBlock(
            String displayNumber,
            final String number,
            final String countryIso,
            final int callType,
            @NonNull final ContactSource.Type contactSourceType) {
        BlockReportSpamDialogs.BlockDialogFragment.newInstance(
                displayNumber,
                Spam.get(mContext).isSpamEnabled(),
                new BlockReportSpamDialogs.OnConfirmListener() {
                    @Override
                    public void onClick() {
                        LogUtil.i("BlockReportSpamListener.onBlock", "onClick");
                        if (Spam.get(mContext).isSpamEnabled()) {
                            Logger.get(mContext)
                                    .logImpression(
                                            DialerImpression.Type
                                                    .DIALOG_ACTION_CONFIRM_NUMBER_SPAM_INDIRECTLY_VIA_BLOCK_NUMBER);
                            Spam.get(mContext)
                                    .reportSpamFromCallHistory(
                                            number,
                                            countryIso,
                                            callType,
                                            ReportingLocation.Type.CALL_LOG_HISTORY,
                                            contactSourceType);
                        }
                        mFilteredNumberAsyncQueryHandler.blockNumber(
                                new FilteredNumberAsyncQueryHandler.OnBlockNumberListener() {
                                    @Override
                                    public void onBlockComplete(Uri uri) {
                                        Logger.get(mContext)
                                                .logImpression(DialerImpression.Type.USER_ACTION_BLOCKED_NUMBER);
                                        if(blockListener != null) {
                                            blockListener.onBlockComplete(uri);
                                        }
                                    }
                                },
                                number,
                                countryIso);
                    }
                },
                null)
                .show(mFragmentManager, BlockReportSpamDialogs.BLOCK_DIALOG_TAG);
    }

    @Override
    public void onUnblock(
            String displayNumber,
            final String number,
            final String countryIso,
            final int callType,
            final ContactSource.Type contactSourceType,
            final boolean isSpam,
            final Integer blockId) {
        BlockReportSpamDialogs.UnblockDialogFragment.newInstance(
                displayNumber,
                isSpam,
                new BlockReportSpamDialogs.OnConfirmListener() {
                    @Override
                    public void onClick() {
                        LogUtil.i("BlockReportSpamListener.onUnblock", "onClick");
                        if (isSpam && Spam.get(mContext).isSpamEnabled()) {
                            Logger.get(mContext)
                                    .logImpression(DialerImpression.Type.REPORT_AS_NOT_SPAM_VIA_UNBLOCK_NUMBER);
                            Spam.get(mContext)
                                    .reportNotSpamFromCallHistory(
                                            number,
                                            countryIso,
                                            callType,
                                            ReportingLocation.Type.CALL_LOG_HISTORY,
                                            contactSourceType);
                        }
                        mFilteredNumberAsyncQueryHandler.unblock(
                                new FilteredNumberAsyncQueryHandler.OnUnblockNumberListener() {
                                    @Override
                                    public void onUnblockComplete(int rows, ContentValues values) {
                                        Logger.get(mContext)
                                                .logImpression(DialerImpression.Type.USER_ACTION_UNBLOCKED_NUMBER);
                                        if(blockListener != null) {
                                            blockListener.onUnblockComplete(rows,values);
                                        }
                                    }
                                },
                                blockId);
                    }
                },
                null)
                .show(mFragmentManager, BlockReportSpamDialogs.UNBLOCK_DIALOG_TAG);
    }

    @Override
    public void onReportNotSpam(
            String displayNumber,
            final String number,
            final String countryIso,
            final int callType,
            final ContactSource.Type contactSourceType) {
        BlockReportSpamDialogs.ReportNotSpamDialogFragment.newInstance(
                displayNumber,
                new BlockReportSpamDialogs.OnConfirmListener() {
                    @Override
                    public void onClick() {
                        LogUtil.i("BlockReportSpamListener.onReportNotSpam", "onClick");
                        if (Spam.get(mContext).isSpamEnabled()) {
                            Logger.get(mContext)
                                    .logImpression(DialerImpression.Type.DIALOG_ACTION_CONFIRM_NUMBER_NOT_SPAM);
                            Spam.get(mContext)
                                    .reportNotSpamFromCallHistory(
                                            number,
                                            countryIso,
                                            callType,
                                            ReportingLocation.Type.CALL_LOG_HISTORY,
                                            contactSourceType);
                        }

                    }
                },
                null)
                .show(mFragmentManager, BlockReportSpamDialogs.NOT_SPAM_DIALOG_TAG);
    }
}
