package com.android.toro.src.calllog;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.dialer.R;
import com.android.dialer.app.calllog.CallLogListItemViewHolder;
import com.android.dialer.blocking.FilteredNumberAsyncQueryHandler;
import com.android.dialer.calldetails.CallDetailsEntries;
import com.android.dialer.calllogutils.CallTypeHelper;
import com.android.dialer.calllogutils.PhoneCallDetails;
import com.android.dialer.common.Assert;
import com.android.dialer.common.LogUtil;
import com.android.dialer.common.concurrent.AsyncTaskExecutor;
import com.android.dialer.common.concurrent.AsyncTaskExecutors;
import com.android.dialer.dialercontact.DialerContact;
import com.android.dialer.logging.ContactSource;
import com.android.dialer.spam.Spam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ToroCallDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ToroCallDetailsActionItemViewHolder.BlockListener {

    private final DialerContact contact;
    private List<ToroCallDetailsEntries> toroCallDetailsEntries;
    private final CallTypeHelper callTypeHelper;

    private final static int FOOTER_VIEW_TYPE = 1;
    private final static int CALL_ENTRY_VIEW_TYPE = 2;

    private final boolean contactIsSaved;
    private int footCounts = 0;

    private PhoneCallDetails mCallDetails;

    private ToroBlockReportSpamListener mBlockReportSpamListener;
    private FilteredNumberAsyncQueryHandler mFilteredNumberAsyncQueryHandler;

    private Integer blockId;

    private final AsyncTaskExecutor mAsyncTaskExecutor = AsyncTaskExecutors.createAsyncTaskExecutor();
    public static final String LOAD_DATA_TASK_IDENTIFIER = "load_data";

    @Override
    public void onBlockComplete(Uri uri) {
        loadAndRender();
//        notifyDataSetChanged();
    }

    @Override
    public void onUnblockComplete(int rows, ContentValues values) {
        loadAndRender();
//        notifyDataSetChanged();
    }

    class ToroCallDetailsEntries{
        CallDetailsEntries.CallDetailsEntry callDetailsEntries;
        boolean needShowDate;
        String dateString;
        String timeString;
    }


    ToroCallDetailsAdapter(
            Activity activity,
            @NonNull DialerContact contact,
            @NonNull List<CallDetailsEntries.CallDetailsEntry> callDetailsEntries,Integer blockId,PhoneCallDetails callDetails) {
        this.mCallDetails = callDetails;
        this.blockId = blockId;
        this.contact = Assert.isNotNull(contact);
        callTypeHelper = new CallTypeHelper(activity.getResources());
        if(TextUtils.isEmpty(contact.getDisplayNumber())) {
            contactIsSaved = false;
            footCounts = 4;
        }else {
            contactIsSaved = true;
            footCounts = 3;
        }
        mFilteredNumberAsyncQueryHandler = new FilteredNumberAsyncQueryHandler(activity);
        mBlockReportSpamListener = new ToroBlockReportSpamListener(
                activity,
                this,
                ((Activity) activity).getFragmentManager(),
                mFilteredNumberAsyncQueryHandler);
        initDateGroup(callDetailsEntries);
    }


    private void initDateGroup(List<CallDetailsEntries.CallDetailsEntry> callDetailsEntries) {
        toroCallDetailsEntries = new ArrayList<>();
        String dateString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        for(CallDetailsEntries.CallDetailsEntry detail : callDetailsEntries){
            ToroCallDetailsEntries toroEntries = new ToroCallDetailsEntries();
            toroEntries.callDetailsEntries = detail;
            String timeString = timeFormat.format(detail.getDate());
            toroEntries.timeString = timeString;
            String detailDateString = dateFormat.format(detail.getDate());
            if(!detailDateString.equals(dateString)){
                dateString = detailDateString;
                toroEntries.dateString = detailDateString;
                toroEntries.needShowDate = true;
            }else {
                toroEntries.needShowDate = false;
            }
            toroCallDetailsEntries.add(toroEntries);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case FOOTER_VIEW_TYPE:
                return new ToroCallDetailsActionItemViewHolder(
                        inflater.inflate(R.layout.toro_call_details_item_foot, parent, false));
            case CALL_ENTRY_VIEW_TYPE:
                return new ToroCallDetailsItemViewHolder(
                        inflater.inflate(R.layout.toro_call_details_item, parent, false));
                default:
                    throw Assert.createIllegalStateFailException(
                            "No ViewHolder available for viewType: " + viewType);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position >= toroCallDetailsEntries.size()) {
            ToroCallDetailsActionItemViewHolder viewHolder = (ToroCallDetailsActionItemViewHolder)holder;
            int itemSize = toroCallDetailsEntries.size();
            viewHolder.setActionDetailsPosition(position - itemSize,contactIsSaved,contact,blockId,mCallDetails,mBlockReportSpamListener);

        } else {
            ToroCallDetailsItemViewHolder viewHolder = (ToroCallDetailsItemViewHolder) holder;
            ToroCallDetailsEntries entry = toroCallDetailsEntries.get(position);
            viewHolder.setCallDetails(
                    entry,
                    callTypeHelper,
                    !entry.callDetailsEntries.getHistoryResultsList().isEmpty() && position != getItemCount() - footCounts);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= toroCallDetailsEntries.size()) {
            return FOOTER_VIEW_TYPE;
        } else {
            return CALL_ENTRY_VIEW_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return toroCallDetailsEntries.size() + footCounts;
    }

    public void clearFilteredNumbersCache() {
        mFilteredNumberAsyncQueryHandler.clearCache();
    }

    private void loadAndRender() {
        final AsyncTask<Void, Void, Boolean> loadDataTask =
                new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... params) {
                        blockId =
                                mFilteredNumberAsyncQueryHandler.getBlockedIdSynchronous(
                                        contact.getNumber(), mCallDetails.countryIso);
                        mCallDetails.isBlocked = blockId != null;
                        if (isCancelled()) {
                            return false;
                        }

                        return true;
                    }

                    @Override
                    protected void onPostExecute(Boolean success) {
                        notifyDataSetChanged();
                    }
                };
        mAsyncTaskExecutor.submit(LOAD_DATA_TASK_IDENTIFIER, loadDataTask);
    }

}
