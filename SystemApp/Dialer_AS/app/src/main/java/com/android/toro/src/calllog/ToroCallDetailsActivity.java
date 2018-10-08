package com.android.toro.src.calllog;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.QuickContactBadge;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.contacts.common.ContactPhotoManager;
import com.android.contacts.common.lettertiles.LetterTileDrawable;
import com.android.dialer.R;
import com.android.dialer.app.calllog.CallLogGroupBuilder;
import com.android.dialer.app.calllog.CallLogListItemViewHolder;
import com.android.dialer.blocking.FilteredNumberAsyncQueryHandler;
import com.android.dialer.calldetails.CallDetailsActivity;
import com.android.dialer.calldetails.CallDetailsEntries;
import com.android.dialer.callintent.CallInitiationType;
import com.android.dialer.callintent.CallIntentBuilder;
import com.android.dialer.calllogutils.PhoneCallDetails;
import com.android.dialer.common.Assert;
import com.android.dialer.compat.AppCompatConstants;
import com.android.dialer.database.CallLogQueryHandler;
import com.android.dialer.dialercontact.DialerContact;
import com.android.dialer.logging.ContactSource;
import com.android.dialer.logging.InteractionEvent;
import com.android.dialer.logging.Logger;
import com.android.dialer.logging.UiAction;
import com.android.dialer.performancereport.PerformanceReport;
import com.android.dialer.phonenumbercache.CallLogQuery;
import com.android.dialer.phonenumbercache.ContactInfo;
import com.android.dialer.phonenumbercache.ContactInfoHelper;
import com.android.dialer.phonenumberutil.PhoneNumberHelper;
import com.android.dialer.postcall.PostCall;
import com.android.dialer.protos.ProtoParsers;
import com.android.dialer.spam.Spam;
import com.android.dialer.util.DialerUtils;
import com.android.toro.src.utils.ToroUtils;

import java.util.List;
import java.util.Objects;

/** Displays the details of a specific call log entry. */
public class ToroCallDetailsActivity extends AppCompatActivity implements View.OnClickListener,CallLogQueryHandler.Listener {

    private static final String EXTRA_CALL_DETAILS_ENTRIES = "call_details_entries";
    private static final String EXTRA_CONTACT = "contact";

    private static Integer mBlockId;

    private List<CallDetailsEntries.CallDetailsEntry> entries;
    private DialerContact contact;
    private QuickContactBadge photo;
    private static PhoneCallDetails mCallDetails;

    private static final int NO_LOG_LIMIT = -1;
    private static final int NO_DATE_LIMIT = 0;
    private CallLogQueryHandler mCallLogQueryHandler;
    private static long firstCallLogQueryID;

    private ToroCallDetailsAdapter adapter;


    public static boolean isLaunchIntent(Intent intent) {
        return intent.getComponent() != null
                && ToroCallDetailsActivity.class.getName().equals(intent.getComponent().getClassName());
    }

    public static Intent newInstance(
            Context context,
            @NonNull CallDetailsEntries details,
            @NonNull DialerContact contact,Integer blockId,PhoneCallDetails callDetails,long startRowID) {
        Assert.isNotNull(details);
        Assert.isNotNull(contact);
        Intent intent = new Intent(context, ToroCallDetailsActivity.class);
        ProtoParsers.put(intent, EXTRA_CONTACT, contact);
        ProtoParsers.put(intent, EXTRA_CALL_DETAILS_ENTRIES, details);
        mCallDetails = callDetails;
        mBlockId = blockId;
        firstCallLogQueryID = startRowID;
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toro_call_details_activity);
//        ToroUtils.setStatusBarColor(this,R.color.toro_statu_bar_bg);
        ToroCallDetailsToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.call_details);
        toolbar.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToroCallDetailsActivity.this.finish();
            }
        });

        findViewById(R.id.toro_all_action).setOnClickListener(this);

        onHandleIntent(getIntent());

//        final ContentResolver resolver = this.getContentResolver();
//        mCallLogQueryHandler = new CallLogQueryHandler(this, resolver, this, NO_LOG_LIMIT);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Some calls may not be recorded (eg. from quick contact),
        // so we should restart recording after these calls. (Recorded call is stopped)
        PostCall.restartPerformanceRecordingIfARecentCallExist(this);
        if (!PerformanceReport.isRecording()) {
            PerformanceReport.startRecording();
        }

        PostCall.promptUserForMessageIfNecessary(this, findViewById(R.id.recycler_view));
        adapter.clearFilteredNumbersCache();
//        mCallLogQueryHandler.fetchCalls(CallLogQueryHandler.CALL_TYPE_ALL, NO_DATE_LIMIT);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        onHandleIntent(intent);
    }

    private void onHandleIntent(Intent intent) {
        contact = ProtoParsers.getTrusted(intent, EXTRA_CONTACT, DialerContact.getDefaultInstance());
        entries =
                ProtoParsers.getTrusted(
                        intent, EXTRA_CALL_DETAILS_ENTRIES, CallDetailsEntries.getDefaultInstance())
                        .getEntriesList();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new ToroCallDetailsAdapter(this, contact, entries,mBlockId,mCallDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        PerformanceReport.logOnScrollStateChange(recyclerView);

        photo = findViewById(R.id.toro_photo);
        ContactPhotoManager.getInstance(this)
                .loadDialerThumbnailOrPhoto(
                        photo,
                        Uri.parse(contact.getContactUri()),
                        contact.getPhotoId(),
                        Uri.parse(contact.getPhotoUri()),
                        contact.getNameOrNumber(),
                        LetterTileDrawable.TYPE_DEFAULT);

        String photoDescription =
                getString(com.android.dialer.R.string.description_quick_contact_for, contact.getNameOrNumber());// fixed by liujia com.android.contacts.common.R
        photo.setContentDescription(photoDescription);

        ((TextView)findViewById(R.id.toro_detail_name)).setText(contact.getNameOrNumber());
    }

    @Override
    public void onBackPressed() {
        PerformanceReport.recordClick(UiAction.Type.PRESS_ANDROID_BACK_BUTTON);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toro_all_action:
                DialerUtils.startActivityWithErrorToast(
                        v.getContext(),
                        new CallIntentBuilder(contact.getNumber(), CallInitiationType.Type.CALL_DETAILS).build());
                break;
        }
    }

    @Override
    public void onVoicemailStatusFetched(Cursor statusCursor) {

    }

    @Override
    public void onVoicemailUnreadCountFetched(Cursor cursor) {

    }

    @Override
    public void onMissedCallsUnreadCountFetched(Cursor cursor) {

    }

    private int startCursorPostion;
    @Override
    public boolean onCallsFetched(Cursor combinedCursor) {
        if(isFinishing()) {
            return false;
        }
        final int count = combinedCursor.getCount();
        if (count == 0) {
            return false;
        }

        int groupSize = getGroupSize(combinedCursor);
        if(groupSize < 1) {
            return false;
        }
        combinedCursor.moveToPosition(startCursorPostion);
        CallDetailsEntries callDetailsEntries = createCallDetailsEntries(combinedCursor, groupSize);
        combinedCursor.moveToPosition(startCursorPostion);
        PhoneCallDetails details = createPhoneCallDetails(combinedCursor, groupSize);

        return true;
    }

    private int getGroupSize(Cursor cursor) {
        boolean findCallLogQueryID = false;
        int groupSize = 1;
        cursor.moveToFirst();
        startCursorPostion = cursor.getPosition();
        long callLogQueryID = cursor.getLong(CallLogQuery.ID);
        if(callLogQueryID == firstCallLogQueryID) {
            findCallLogQueryID = true;
        }
        String groupNumber = cursor.getString(CallLogQuery.NUMBER);
        String groupPostDialDigits =
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) ? cursor.getString(CallLogQuery.POST_DIAL_DIGITS) : "";
        String groupViaNumbers =
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) ? cursor.getString(CallLogQuery.VIA_NUMBER) : "";
        int groupCallType = cursor.getInt(CallLogQuery.CALL_TYPE);
        String groupAccountComponentName = cursor.getString(CallLogQuery.ACCOUNT_COMPONENT_NAME);
        String groupAccountId = cursor.getString(CallLogQuery.ACCOUNT_ID);

        String number;
        String numberPostDialDigits;
        String numberViaNumbers;
        int callType;
        String accountComponentName;
        String accountId;
        while (cursor.moveToNext()) {
            // Obtain the values for the current call to group.

            callLogQueryID = cursor.getLong(CallLogQuery.ID);
            if(callLogQueryID == firstCallLogQueryID) {
                findCallLogQueryID = true;
            }
            number = cursor.getString(CallLogQuery.NUMBER);
            numberPostDialDigits =
                    (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            ? cursor.getString(CallLogQuery.POST_DIAL_DIGITS)
                            : "";
            numberViaNumbers =
                    (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) ? cursor.getString(CallLogQuery.VIA_NUMBER) : "";
            callType = cursor.getInt(CallLogQuery.CALL_TYPE);
            accountComponentName = cursor.getString(CallLogQuery.ACCOUNT_COMPONENT_NAME);
            accountId = cursor.getString(CallLogQuery.ACCOUNT_ID);

            final boolean isSameNumber = equalNumbers(groupNumber, number);
            final boolean isSamePostDialDigits = groupPostDialDigits.equals(numberPostDialDigits);
            final boolean isSameViaNumbers = groupViaNumbers.equals(numberViaNumbers);
            final boolean isSameAccount =
                    isSameAccount(groupAccountComponentName, accountComponentName, groupAccountId, accountId);
            //liujia add
            final boolean isSameType = callType == groupCallType;

            // Group with the same number and account. Never group voicemails. Only group blocked
            // calls with other blocked calls.
            if (isSameNumber
                    && isSameType
                    && isSameAccount
                    && isSamePostDialDigits
                    && isSameViaNumbers
                    && areBothNotVoicemail(callType, groupCallType)
                    && (areBothNotBlocked(callType, groupCallType)
                    || areBothBlocked(callType, groupCallType))) {
                // Increment the size of the group to include the current call, but do not create
                // the group until finding a call that does not match.
                groupSize++;
            } else {
                if(findCallLogQueryID) {
                    break;
                } else {
                    startCursorPostion = cursor.getPosition();
                }
                groupSize = 1;

                // Update the group values to those of the current call.
                groupNumber = number;
                groupPostDialDigits = numberPostDialDigits;
                groupViaNumbers = numberViaNumbers;
                groupCallType = callType;
                groupAccountComponentName = accountComponentName;
                groupAccountId = accountId;
            }
        }

        if(findCallLogQueryID) {
            return groupSize;
        }
        return 0;
    }

    private boolean equalNumbers(String number1, String number2) {
        if (PhoneNumberHelper.isUriNumber(number1) || PhoneNumberHelper.isUriNumber(number2)) {
            return compareSipAddresses(number1, number2);
        } else {
            return PhoneNumberUtils.compare(number1, number2);
        }
    }

    boolean compareSipAddresses(@Nullable String number1, @Nullable String number2) {
        if (number1 == null || number2 == null) {
            return Objects.equals(number1, number2);
        }

        int index1 = number1.indexOf('@');
        final String userinfo1;
        final String rest1;
        if (index1 != -1) {
            userinfo1 = number1.substring(0, index1);
            rest1 = number1.substring(index1);
        } else {
            userinfo1 = number1;
            rest1 = "";
        }

        int index2 = number2.indexOf('@');
        final String userinfo2;
        final String rest2;
        if (index2 != -1) {
            userinfo2 = number2.substring(0, index2);
            rest2 = number2.substring(index2);
        } else {
            userinfo2 = number2;
            rest2 = "";
        }

        return userinfo1.equals(userinfo2) && rest1.equalsIgnoreCase(rest2);
    }

    private boolean isSameAccount(String name1, String name2, String id1, String id2) {
        return TextUtils.equals(name1, name2) && TextUtils.equals(id1, id2);
    }

    private CallDetailsEntries.CallDetailsEntry.Builder getCallDetailsEntrieForcursor(Cursor cursor) {
        return CallDetailsEntries.CallDetailsEntry.newBuilder()
                .setCallId(cursor.getLong(CallLogQuery.ID))
                .setCallType(cursor.getInt(CallLogQuery.CALL_TYPE))
                .setDataUsage(cursor.getLong(CallLogQuery.DATA_USAGE))
                .setDate(cursor.getLong(CallLogQuery.DATE))
                .setDuration(cursor.getLong(CallLogQuery.DURATION))
                .setFeatures(cursor.getInt(CallLogQuery.FEATURES));
    }

    private static CallDetailsEntries createCallDetailsEntries(Cursor cursor, int count) {
        Assert.isMainThread();
        int position = cursor.getPosition();
        CallDetailsEntries.Builder entries = CallDetailsEntries.newBuilder();
        for (int i = 0; i < count; i++) {
            CallDetailsEntries.CallDetailsEntry.Builder entry =
                    CallDetailsEntries.CallDetailsEntry.newBuilder()
                            .setCallId(cursor.getLong(CallLogQuery.ID))
                            .setCallType(cursor.getInt(CallLogQuery.CALL_TYPE))
                            .setDataUsage(cursor.getLong(CallLogQuery.DATA_USAGE))
                            .setDate(cursor.getLong(CallLogQuery.DATE))
                            .setDuration(cursor.getLong(CallLogQuery.DURATION))
                            .setFeatures(cursor.getInt(CallLogQuery.FEATURES));
            entries.addEntries(entry.build());
            cursor.moveToNext();
        }
        cursor.moveToPosition(position);
        return entries.build();
    }

    private PhoneCallDetails createPhoneCallDetails(
            Cursor cursor, int count) {
        Assert.isMainThread();
        final String number = cursor.getString(CallLogQuery.NUMBER);
        final String postDialDigits =
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) ? cursor.getString(CallLogQuery.POST_DIAL_DIGITS) : "";
        final String viaNumber =
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) ? cursor.getString(CallLogQuery.VIA_NUMBER) : "";
        final int numberPresentation = cursor.getInt(CallLogQuery.NUMBER_PRESENTATION);
        final ContactInfo cachedContactInfo = ContactInfoHelper.getContactInfo(cursor);
        final PhoneCallDetails details =
                new PhoneCallDetails(number, numberPresentation, postDialDigits);
        details.viaNumber = viaNumber;
        details.countryIso = cursor.getString(CallLogQuery.COUNTRY_ISO);
        details.date = cursor.getLong(CallLogQuery.DATE);
        details.duration = cursor.getLong(CallLogQuery.DURATION);
        details.features = getCallFeatures(cursor, count);
        details.geocode = cursor.getString(CallLogQuery.GEOCODED_LOCATION);
        details.transcription = cursor.getString(CallLogQuery.TRANSCRIPTION);
        details.callTypes = getCallTypes(cursor, count);

        details.accountComponentName = cursor.getString(CallLogQuery.ACCOUNT_COMPONENT_NAME);
        details.accountId = cursor.getString(CallLogQuery.ACCOUNT_ID);
        details.cachedContactInfo = cachedContactInfo;

        if (!cursor.isNull(CallLogQuery.DATA_USAGE)) {
            details.dataUsage = cursor.getLong(CallLogQuery.DATA_USAGE);
        }

//        views.rowId = cursor.getLong(CallLogQuery.ID);
//        details.previousGroup = getPreviousDayGroup(cursor);

        if (details.callTypes[0] == CallLog.Calls.VOICEMAIL_TYPE
                || details.callTypes[0] == CallLog.Calls.MISSED_TYPE) {
            details.isRead = cursor.getInt(CallLogQuery.IS_READ) == 1;
        }

        return details;
    }

    private boolean areBothNotVoicemail(int callType, int groupCallType) {
        return callType != AppCompatConstants.CALLS_VOICEMAIL_TYPE
                && groupCallType != AppCompatConstants.CALLS_VOICEMAIL_TYPE;
    }

    private boolean areBothNotBlocked(int callType, int groupCallType) {
        return callType != AppCompatConstants.CALLS_BLOCKED_TYPE
                && groupCallType != AppCompatConstants.CALLS_BLOCKED_TYPE;
    }

    private boolean areBothBlocked(int callType, int groupCallType) {
        return callType == AppCompatConstants.CALLS_BLOCKED_TYPE
                && groupCallType == AppCompatConstants.CALLS_BLOCKED_TYPE;
    }

    /**
     * Determine the features which were enabled for any of the calls that make up a call log entry.
     *
     * @param cursor The cursor.
     * @param count The number of calls for the current call log entry.
     * @return The features.
     */
    private int getCallFeatures(Cursor cursor, int count) {
        int features = 0;
        int position = cursor.getPosition();
        for (int index = 0; index < count; ++index) {
            features |= cursor.getInt(CallLogQuery.FEATURES);
            cursor.moveToNext();
        }
        cursor.moveToPosition(position);
        return features;
    }

    /**
     * Returns the call types for the given number of items in the cursor.
     *
     * <p>It uses the next {@code count} rows in the cursor to extract the types.
     *
     * <p>It position in the cursor is unchanged by this function.
     */
    private static int[] getCallTypes(Cursor cursor, int count) {
        int position = cursor.getPosition();
        int[] callTypes = new int[count];
        for (int index = 0; index < count; ++index) {
            callTypes[index] = cursor.getInt(CallLogQuery.CALL_TYPE);
            cursor.moveToNext();
        }
        cursor.moveToPosition(position);
        return callTypes;
    }

}
