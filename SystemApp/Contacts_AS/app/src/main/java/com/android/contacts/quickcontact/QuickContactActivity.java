package com.android.contacts.quickcontact;

import android.Manifest;
import android.accounts.Account;
import android.app.Activity;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Trace;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.android.contacts.CallUtil;
import com.android.contacts.Collapser;
import com.android.contacts.ContactPhotoManager;
import com.android.contacts.ContactSaveService;
import com.android.contacts.ContactsActivity;
import com.android.contacts.ContactsUtils;
import com.android.contacts.DynamicShortcuts;
import com.android.contacts.NfcHandler;
import com.android.contacts.R;
import com.android.contacts.activities.ContactEditorActivity;
import com.android.contacts.activities.RequestPermissionsActivity;
import com.android.contacts.compat.CompatUtils;
import com.android.contacts.compat.MultiWindowCompat;
import com.android.contacts.detail.ContactDisplayUtils;
import com.android.contacts.editor.ContactEditorFragment;
import com.android.contacts.editor.EditorIntents;
import com.android.contacts.editor.EditorUiUtils;
import com.android.contacts.interactions.CalendarInteractionsLoader;
import com.android.contacts.interactions.CallLogInteractionsLoader;
import com.android.contacts.interactions.ContactDeletionInteraction;
import com.android.contacts.interactions.ContactInteraction;
import com.android.contacts.interactions.SmsInteractionsLoader;
import com.android.contacts.logging.Logger;
import com.android.contacts.logging.QuickContactEvent;
import com.android.contacts.logging.ScreenEvent;
import com.android.contacts.model.AccountTypeManager;
import com.android.contacts.model.Contact;
import com.android.contacts.model.ContactLoader;
import com.android.contacts.model.RawContact;
import com.android.contacts.model.ValuesDelta;
import com.android.contacts.model.account.AccountType;
import com.android.contacts.model.dataitem.CustomDataItem;
import com.android.contacts.model.dataitem.DataItem;
import com.android.contacts.model.dataitem.DataKind;
import com.android.contacts.model.dataitem.EmailDataItem;
import com.android.contacts.model.dataitem.PhoneDataItem;
import com.android.contacts.model.dataitem.SipAddressDataItem;
import com.android.contacts.toro.ToroActionBar;
import com.android.contacts.toro.activity.ToroCallDetailsAdapter;
import com.android.contacts.toro.activity.ToroContactDetailsToolbar;
import com.android.contacts.toro.common.dialog.ToroCommonBottomDialog;
import com.android.contacts.toro.common.dialog.ToroCommonBottomDialogEntity;
import com.android.contacts.util.ImplicitIntentsUtil;
import com.android.contacts.util.MaterialColorMapUtils;
import com.android.contacts.util.PermissionsUtil;
import com.android.contacts.util.PhoneCapabilityTester;
import com.android.contacts.util.SchedulingUtils;
import com.android.contacts.widget.QuickContactImageView;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.android.contacts.util.MaterialColorMapUtils.getDefaultPrimaryAndSecondaryColors;

public class QuickContactActivity extends ContactsActivity {

    /**
          * QuickContacts immediately takes up the full screen. All possible information is shown.
          * This value for {@link android.provider.ContactsContract.QuickContact#EXTRA_MODE}
          * should only be used by the Contacts app.
          */
    public static final int MODE_FULLY_EXPANDED = 4;

    /** Used to pass the screen where the user came before launching this Activity. */
    public static final String EXTRA_PREVIOUS_SCREEN_TYPE = "previous_screen_type";
    /** Used to pass the Contact card action. */
    public static final String EXTRA_ACTION_TYPE = "action_type";
    public static final String EXTRA_THIRD_PARTY_ACTION = "third_party_action";

    /** Used to tell the QuickContact that the previous contact was edited, so it can return an
     * activity result back to the original Activity that launched it. */
    public static final String EXTRA_CONTACT_EDITED = "contact_edited";

    private static final String TAG = "QuickContact";

    // Set true in {@link #onCreate} after orientation change for later use in processIntent().
    private boolean mIsRecreatedInstance;
    private boolean mShortcutUsageReported = false;

    private long mPreviousContactId = 0;

    /**
     * {@link #LEADING_MIMETYPES} is used to sort MIME-types.
     *
     * <p>The MIME-types in {@link #LEADING_MIMETYPES} appear in the front of the dialog,
     * in the order specified here.</p>
     */
    private static final List<String> LEADING_MIMETYPES = Lists.newArrayList(
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, ContactsContract.CommonDataKinds.SipAddress.CONTENT_ITEM_TYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE);

    private static final List<String> SORTED_ABOUT_CARD_MIMETYPES = Lists.newArrayList(
            ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE,
            // Phonetic name is inserted after nickname if it is available.
            // No mimetype for phonetic name exists.
            ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Relation.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Identity.CONTENT_ITEM_TYPE,
            CustomDataItem.MIMETYPE_CUSTOM_FIELD,
            ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE);

    /**
     * Used to stop the ExpandingEntry cards from adjusting between an entry click and the intent
     * being launched.
     */
    private boolean mHasIntentLaunched;

    // Phone specific option menu items
    private boolean mSendToVoicemailState;
    private boolean mArePhoneOptionsChangable;
    private String mCustomRingtone;

    private static final String KEY_PREVIOUS_CONTACT_ID = "previous_contact_id";
    private static final String KEY_SEND_TO_VOICE_MAIL_STATE = "sendToVoicemailState";
    private static final String KEY_ARE_PHONE_OPTIONS_CHANGEABLE = "arePhoneOptionsChangable";
    private static final String KEY_CUSTOM_RINGTONE = "customRingtone";

    public static final String ACTION_SPLIT_COMPLETED = "splitCompleted";

    @SuppressWarnings("deprecation")
    private static final String LEGACY_AUTHORITY = android.provider.Contacts.AUTHORITY;

    public static final String MIMETYPE_TACHYON =
            "vnd.android.cursor.item/com.google.android.apps.tachyon.phone";
    private static final String TACHYON_CALL_ACTION =
            "com.google.android.apps.tachyon.action.CALL";
    private static final String MIMETYPE_GPLUS_PROFILE =
            "vnd.android.cursor.item/vnd.googleplus.profile";
    private static final String GPLUS_PROFILE_DATA_5_VIEW_PROFILE = "view";
    private static final String MIMETYPE_HANGOUTS =
            "vnd.android.cursor.item/vnd.googleplus.profile.comm";
    private static final String HANGOUTS_DATA_5_VIDEO = "hangout";
    private static final String HANGOUTS_DATA_5_MESSAGE = "conversation";
    private static final String CALL_ORIGIN_QUICK_CONTACTS_ACTIVITY =
            "com.android.contacts.quickcontact.QuickContactActivity";

    // Used to store and log the referrer package name and the contact type.
    private String mReferrer;
    private int mContactType;

    /**
     * The last copy of Cp2DataCardModel that was passed to {@link #populateContactAndAboutCard}.
     */
    private Cp2DataCardModel mCachedCp2DataCardModel;


    /** Id for the background contact loader */
    private static final int LOADER_CONTACT_ID = 0;

    /** Id for the background Sms Loader */
    private static final int LOADER_SMS_ID = 1;
    private static final int MAX_SMS_RETRIEVE = 3;

    /** Id for the back Calendar Loader */
    private static final int LOADER_CALENDAR_ID = 2;
    private static final String KEY_LOADER_EXTRA_EMAILS =
            QuickContactActivity.class.getCanonicalName() + ".KEY_LOADER_EXTRA_EMAILS";
    private static final int MAX_PAST_CALENDAR_RETRIEVE = 3;
    private static final int MAX_FUTURE_CALENDAR_RETRIEVE = 3;
    private static final long PAST_MILLISECOND_TO_SEARCH_LOCAL_CALENDAR =
            1L * 24L * 60L * 60L * 1000L /* 1 day */;
    private static final long FUTURE_MILLISECOND_TO_SEARCH_LOCAL_CALENDAR =
            7L * 24L * 60L * 60L * 1000L /* 7 days */;

    /** Id for the background Call Log Loader */
    private static final int LOADER_CALL_LOG_ID = 3;
    private static final int MAX_CALL_LOG_RETRIEVE = 3;
    private static final int MIN_NUM_CONTACT_ENTRIES_SHOWN = 3;
    private static final int MIN_NUM_COLLAPSED_RECENT_ENTRIES_SHOWN = 3;
    private static final int CARD_ENTRY_ID_EDIT_CONTACT = -2;
    private static final int CARD_ENTRY_ID_REQUEST_PERMISSION = -3;
    private static final String KEY_LOADER_EXTRA_PHONES =
            QuickContactActivity.class.getCanonicalName() + ".KEY_LOADER_EXTRA_PHONES";
    private static final String KEY_LOADER_EXTRA_SIP_NUMBERS =
            QuickContactActivity.class.getCanonicalName() + ".KEY_LOADER_EXTRA_SIP_NUMBERS";

    private static final int CURRENT_API_VERSION = android.os.Build.VERSION.SDK_INT;

    private static final int[] mRecentLoaderIds = new int[]{
            LOADER_SMS_ID,
            LOADER_CALENDAR_ID,
            LOADER_CALL_LOG_ID};

    private SaveServiceListener mListener;

    private AsyncTask<Void, Void, Cp2DataCardModel> mEntriesAndActionsTask;
    private AsyncTask<Void, Void, Void> mRecentDataTask;

    private static final int ANIMATION_STATUS_BAR_COLOR_CHANGE_DURATION = 150;
    private static final int REQUEST_CODE_CONTACT_EDITOR_ACTIVITY = 1;
    private static final int SCRIM_COLOR = Color.argb(0xC8, 0, 0, 0);
    private static final int REQUEST_CODE_CONTACT_SELECTION_ACTIVITY = 2;
    private static final String MIMETYPE_SMS = "vnd.android-dir/mms-sms";
    private static final int REQUEST_CODE_JOIN = 3;
    private static final int REQUEST_CODE_PICK_RINGTONE = 4;

    /**
     * ConcurrentHashMap constructor params: 4 is initial table size, 0.9f is
     * load factor before resizing, 1 means we only expect a single thread to
     * write to the map so make only a single shard
     */
    private Map<Integer, List<ContactInteraction>> mRecentLoaderResults =
            new ConcurrentHashMap<>(4, 0.9f, 1);

    /**
     * The URI used to load the the Contact. Once the contact is loaded, use Contact#getLookupUri()
     * instead of referencing this URI.
     */
    private Uri mLookupUri;
    private String[] mExcludeMimes;
    private int mExtraMode;
    private String mExtraPrioritizedMimeType;
    private boolean mHasAlreadyBeenOpened;
    private boolean mOnlyOnePhoneNumber;
    private ProgressDialog mProgressDialog;

    private Contact mContactData;
    private ContactLoader mContactLoader;

    protected void onCreate(Bundle savedInstanceState) {
        Trace.beginSection("onCreate()");
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        if (RequestPermissionsActivity.startPermissionActivityIfNeeded(this)) {
            return;
        }

        mIsRecreatedInstance = savedInstanceState != null;
        if (mIsRecreatedInstance) {
            mPreviousContactId = savedInstanceState.getLong(KEY_PREVIOUS_CONTACT_ID);

            // Phone specific options menus
            mSendToVoicemailState = savedInstanceState.getBoolean(KEY_SEND_TO_VOICE_MAIL_STATE);
            mArePhoneOptionsChangable =
                    savedInstanceState.getBoolean(KEY_ARE_PHONE_OPTIONS_CHANGEABLE);
            mCustomRingtone = savedInstanceState.getString(KEY_CUSTOM_RINGTONE);
        }

        mListener = new SaveServiceListener();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ContactSaveService.BROADCAST_LINK_COMPLETE);
        intentFilter.addAction(ContactSaveService.BROADCAST_UNLINK_COMPLETE);
        LocalBroadcastManager.getInstance(this).registerReceiver(mListener,
                intentFilter);

        // There're 3 states for each permission:
        // 1. App doesn't have permission, not asked user yet.
        // 2. App doesn't have permission, user denied it previously.
        // 3. App has permission.
        // Permission explanation card is displayed only for case 1.
        final boolean hasTelephonyFeature =
                getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY);

        final boolean hasCalendarPermission = PermissionsUtil.hasPermission(
                this, Manifest.permission.READ_CALENDAR);
        final boolean hasSMSPermission = hasTelephonyFeature
                && PermissionsUtil.hasPermission(this, Manifest.permission.READ_SMS);

        final boolean wasCalendarPermissionDenied =
                ActivityCompat.shouldShowRequestPermissionRationale(
                        this, Manifest.permission.READ_CALENDAR);
        final boolean wasSMSPermissionDenied =
                hasTelephonyFeature && ActivityCompat.shouldShowRequestPermissionRationale(
                        this, Manifest.permission.READ_SMS);

        final boolean shouldDisplayCalendarMessage =
                !hasCalendarPermission && !wasCalendarPermissionDenied;
        final boolean shouldDisplaySMSMessage =
                hasTelephonyFeature && !hasSMSPermission && !wasSMSPermissionDenied;
        mShouldShowPermissionExplanation = shouldDisplayCalendarMessage || shouldDisplaySMSMessage;

        if (shouldDisplayCalendarMessage && shouldDisplaySMSMessage) {
            mPermissionExplanationCardSubHeader =
                    getString(R.string.permission_explanation_subheader_calendar_and_SMS);
        } else if (shouldDisplayCalendarMessage) {
            mPermissionExplanationCardSubHeader =
                    getString(R.string.permission_explanation_subheader_calendar);
        } else if (shouldDisplaySMSMessage) {
            mPermissionExplanationCardSubHeader =
                    getString(R.string.permission_explanation_subheader_SMS);
        }

        final int previousScreenType = getIntent().getIntExtra
                (EXTRA_PREVIOUS_SCREEN_TYPE, ScreenEvent.ScreenType.UNKNOWN);
        Logger.logScreenView(this, ScreenEvent.ScreenType.QUICK_CONTACT, previousScreenType);

        mReferrer = getCallingPackage();
        if (mReferrer == null && CompatUtils.isLollipopMr1Compatible() && getReferrer() != null) {
            mReferrer = getReferrer().getAuthority();
        }
        mContactType = QuickContactEvent.ContactType.UNKNOWN_TYPE;

        if (CompatUtils.isLollipopCompatible()) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        processIntent(getIntent());

        // Show QuickContact in front of soft input
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        setContentView(R.layout.toro_contact_details_activity);

        setupToroView();
        Trace.endSection();

    }

    @Override
    protected void onResume() {
        super.onResume();
        // If returning from a launched activity, repopulate the contact and about card
        if (mHasIntentLaunched) {
            mHasIntentLaunched = false;
            populateContactAndAboutCard(mCachedCp2DataCardModel, /* shouldAddPhoneticName */ false);
        }

        // When exiting the activity and resuming, we want to force a full reload of all the
        // interaction data in case something changed in the background. On screen rotation,
        // we don't need to do this. And, mCachedCp2DataCardModel will be null, so we won't.
        if (mCachedCp2DataCardModel != null) {
            destroyInteractionLoaders();
            startInteractionLoaders(mCachedCp2DataCardModel);
        }
        maybeShowProgressDialog();
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mListener);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissProgressBar();
    }

    private void maybeShowProgressDialog() {
        if (ContactSaveService.getState().isActionPending(
                ContactSaveService.ACTION_SPLIT_CONTACT)) {
            showUnlinkProgressBar();
        } else if (ContactSaveService.getState().isActionPending(
                ContactSaveService.ACTION_JOIN_CONTACTS)) {
            showLinkProgressBar();
        }
    }

    private void showUnlinkProgressBar() {
        mProgressDialog.setMessage(getString(R.string.contacts_unlinking_progress_bar));
        mProgressDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final boolean deletedOrSplit = requestCode == REQUEST_CODE_CONTACT_EDITOR_ACTIVITY &&
                (resultCode == ContactDeletionInteraction.RESULT_CODE_DELETED ||
                        resultCode == ContactEditorActivity.RESULT_CODE_SPLIT);
        setResult(resultCode, data);
        if (deletedOrSplit) {
            finish();
        } else if (requestCode == REQUEST_CODE_CONTACT_SELECTION_ACTIVITY &&
                resultCode != RESULT_CANCELED) {
            processIntent(data);
        } else if (requestCode == REQUEST_CODE_JOIN) {
            // Ignore failed requests
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            if (data != null) {
                joinAggregate(ContentUris.parseId(data.getData()));
            }
        } else if (requestCode == REQUEST_CODE_PICK_RINGTONE && data != null) {
            final Uri pickedUri = data.getParcelableExtra(
                    RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            onRingtonePicked(pickedUri);
        }
    }

    private void onRingtonePicked(Uri pickedUri) {
        mCustomRingtone = EditorUiUtils.getRingtoneStringFromUri(pickedUri, CURRENT_API_VERSION);
        Intent intent = ContactSaveService.createSetRingtone(
                this, mLookupUri, mCustomRingtone);
        this.startService(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mHasAlreadyBeenOpened = true;
        processIntent(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putLong(KEY_PREVIOUS_CONTACT_ID, mPreviousContactId);

        // Phone specific options
        savedInstanceState.putBoolean(KEY_SEND_TO_VOICE_MAIL_STATE, mSendToVoicemailState);
        savedInstanceState.putBoolean(KEY_ARE_PHONE_OPTIONS_CHANGEABLE, mArePhoneOptionsChangable);
        savedInstanceState.putString(KEY_CUSTOM_RINGTONE, mCustomRingtone);
    }

    private void processIntent(Intent intent) {
        if (intent == null) {
            finish();
            return;
        }
        if (ACTION_SPLIT_COMPLETED.equals(intent.getAction())) {
            Toast.makeText(this, R.string.contactUnlinkedToast, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Uri lookupUri = intent.getData();
        if (intent.getBooleanExtra(EXTRA_CONTACT_EDITED, false)) {
            setResult(ContactEditorActivity.RESULT_CODE_EDITED);
        }

        // Check to see whether it comes from the old version.
        if (lookupUri != null && LEGACY_AUTHORITY.equals(lookupUri.getAuthority())) {
            final long rawContactId = ContentUris.parseId(lookupUri);
            lookupUri = ContactsContract.RawContacts.getContactLookupUri(getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.RawContacts.CONTENT_URI, rawContactId));
        }
        mExtraMode = getIntent().getIntExtra(ContactsContract.QuickContact.EXTRA_MODE, ContactsContract.QuickContact.MODE_LARGE);
        if (isMultiWindowOnPhone()) {
            mExtraMode = ContactsContract.QuickContact.MODE_LARGE;
        }
        mExtraPrioritizedMimeType =
                getIntent().getStringExtra(ContactsContract.QuickContact.EXTRA_PRIORITIZED_MIMETYPE);
        final Uri oldLookupUri = mLookupUri;


        if (lookupUri == null) {
            finish();
            return;
        }
        mLookupUri = lookupUri;
        mExcludeMimes = intent.getStringArrayExtra(ContactsContract.QuickContact.EXTRA_EXCLUDE_MIMES);
        if (oldLookupUri == null) {
            // Should not log if only orientation changes.
            mContactLoader = (ContactLoader) getLoaderManager().initLoader(
                    LOADER_CONTACT_ID, null, mLoaderContactCallbacks);
        } else if (oldLookupUri != mLookupUri) {
            // After copying a directory contact, the contact URI changes. Therefore,
            // we need to reload the new contact.
            destroyInteractionLoaders();
            mContactLoader = (ContactLoader) (Loader<?>) getLoaderManager().getLoader(
                    LOADER_CONTACT_ID);
            mContactLoader.setNewLookup(mLookupUri);
            mCachedCp2DataCardModel = null;
        }
        mContactLoader.forceLoad();

        NfcHandler.register(this, mLookupUri);
    }

    /**
     * Performs aggregation with the contact selected by the user from suggestions or A-Z list.
     */
    private void joinAggregate(final long contactId) {
        final Intent intent = ContactSaveService.createJoinContactsIntent(
                this, mPreviousContactId, contactId, QuickContactActivity.class,
                Intent.ACTION_VIEW);
        this.startService(intent);
        showLinkProgressBar();
    }

    private void showLinkProgressBar() {
        mProgressDialog.setMessage(getString(R.string.contacts_linking_progress_bar));
        mProgressDialog.show();
    }

    private void destroyInteractionLoaders() {
        for (int interactionLoaderId : mRecentLoaderIds) {
            getLoaderManager().destroyLoader(interactionLoaderId);
        }
    }

    private final LoaderManager.LoaderCallbacks<Contact> mLoaderContactCallbacks =
            new LoaderManager.LoaderCallbacks<Contact>() {
                @Override
                public void onLoaderReset(Loader<Contact> loader) {
                    mContactData = null;
                }

                @Override
                public void onLoadFinished(Loader<Contact> loader, Contact data) {
                    Trace.beginSection("onLoadFinished()");
                    try {

                        if (isFinishing()) {
                            return;
                        }
                        if (data.isError()) {
                            // This means either the contact is invalid or we had an
                            // internal error such as an acore crash.
                            Log.i(TAG, "Failed to load contact: " + ((ContactLoader)loader).getLookupUri());
                            Toast.makeText(QuickContactActivity.this, R.string.invalidContactMessage,
                                    Toast.LENGTH_LONG).show();
                            finish();
                            return;
                        }
                        if (data.isNotFound()) {
                            Log.i(TAG, "No contact found: " + ((ContactLoader)loader).getLookupUri());
                            Toast.makeText(QuickContactActivity.this, R.string.invalidContactMessage,
                                    Toast.LENGTH_LONG).show();
                            finish();
                            return;
                        }

                        if (!mIsRecreatedInstance && !mShortcutUsageReported && data != null) {
                            mShortcutUsageReported = true;
                            DynamicShortcuts.reportShortcutUsed(QuickContactActivity.this,
                                    data.getLookupKey());
                        }
                        bindContactData(data);

                    } finally {
                        Trace.endSection();
                    }
                }

                @Override
                public Loader<Contact> onCreateLoader(int id, Bundle args) {
                    if (mLookupUri == null) {
                        Log.wtf(TAG, "Lookup uri wasn't initialized. Loader was started too early");
                    }
                    // Load all contact data. We need loadGroupMetaData=true to determine whether the
                    // contact is invisible. If it is, we need to display an "Add to Contacts" MenuItem.
                    return new ContactLoader(getApplicationContext(), mLookupUri,
                            true /*loadGroupMetaData*/, true /*postViewNotification*/,
                            true /*computeFormattedPhoneNumber*/);
                }
            };

    private boolean isMultiWindowOnPhone() {
        return MultiWindowCompat.isInMultiWindowMode(this) && PhoneCapabilityTester.isPhone(this);
    }

    private void dismissProgressBar() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * Returns true if it is possible to edit the current contact.
     */
    private boolean isContactEditable() {
        return mContactData != null && !mContactData.isDirectoryEntry();
    }

    private void setStateForPhoneMenuItems(Contact contact) {
        if (contact != null) {
            mSendToVoicemailState = contact.isSendToVoicemail();
            mCustomRingtone = contact.getCustomRingtone();
            mArePhoneOptionsChangable = isContactEditable()
                    && PhoneCapabilityTester.isPhone(this);
        }
    }

    /**
     * Handle the result from the ContactLoader
     */
    private void bindContactData(final Contact data) {
        Trace.beginSection("bindContactData");

        final int actionType = mContactData == null ? QuickContactEvent.ActionType.START : QuickContactEvent.ActionType.UNKNOWN_ACTION;
        mContactData = data;

        final int newContactType;
        if (DirectoryContactUtil.isDirectoryContact(mContactData)) {
            newContactType = QuickContactEvent.ContactType.DIRECTORY;
        } else if (InvisibleContactUtil.isInvisibleAndAddable(mContactData, this)) {
            newContactType = QuickContactEvent.ContactType.INVISIBLE_AND_ADDABLE;
        } else if (isContactEditable()) {
            newContactType = QuickContactEvent.ContactType.EDITABLE;
        } else {
            newContactType = QuickContactEvent.ContactType.UNKNOWN_TYPE;
        }

        mContactType = newContactType;

        setStateForPhoneMenuItems(mContactData);
        invalidateOptionsMenu();

        Trace.endSection();
        Trace.beginSection("Set display photo & name");

        updateToroView();

        getWindow().setStatusBarColor(getColor(R.color.toro_statu_bar_bg));

        Trace.endSection();

        mEntriesAndActionsTask = new AsyncTask<Void, Void, Cp2DataCardModel>() {

            @Override
            protected Cp2DataCardModel doInBackground(
                    Void... params) {
                return generateDataModelFromContact(data);
            }

            @Override
            protected void onPostExecute(Cp2DataCardModel cardDataModel) {
                super.onPostExecute(cardDataModel);
                // Check that original AsyncTask parameters are still valid and the activity
                // is still running before binding to UI. A new intent could invalidate
                // the results, for example.
                if (data == mContactData && !isCancelled()) {
                    bindDataToCards(cardDataModel);
                    showActivity();
                }
            }
        };
        mEntriesAndActionsTask.execute();
    }

    private void showActivity() {
//        if (mScroller != null) {
//            mScroller.setVisibility(View.VISIBLE);
//            SchedulingUtils.doOnPreDraw(mScroller, /* drawNextFrame = */ false,
//                    new Runnable() {
//                        @Override
//                        public void run() {
//                            runEntranceAnimation();
//                        }
//                    });
//        }
    }

    private void bindDataToCards(Cp2DataCardModel cp2DataCardModel) {
        updateToroNumber(cp2DataCardModel);
        startInteractionLoaders(cp2DataCardModel);
        populateContactAndAboutCard(cp2DataCardModel, /* shouldAddPhoneticName */ true);
    }

    private void startInteractionLoaders(Cp2DataCardModel cp2DataCardModel) {
        final Map<String, List<DataItem>> dataItemsMap = cp2DataCardModel.dataItemsMap;
        final List<DataItem> phoneDataItems = dataItemsMap.get(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        final List<DataItem> sipCallDataItems = dataItemsMap.get(ContactsContract.CommonDataKinds.SipAddress.CONTENT_ITEM_TYPE);
        if (phoneDataItems != null && phoneDataItems.size() == 1) {
            mOnlyOnePhoneNumber = true;
        }
        String[] phoneNumbers = null;
        if (phoneDataItems != null) {
            phoneNumbers = new String[phoneDataItems.size()];
            for (int i = 0; i < phoneDataItems.size(); ++i) {
                phoneNumbers[i] = ((PhoneDataItem) phoneDataItems.get(i)).getNumber();
            }
        }
        String[] sipNumbers = null;
        if (sipCallDataItems != null) {
            sipNumbers = new String[sipCallDataItems.size()];
            for (int i = 0; i < sipCallDataItems.size(); ++i) {
                sipNumbers[i] = ((SipAddressDataItem) sipCallDataItems.get(i)).getSipAddress();
            }
        }
        final Bundle phonesExtraBundle = new Bundle();
        phonesExtraBundle.putStringArray(KEY_LOADER_EXTRA_PHONES, phoneNumbers);
        phonesExtraBundle.putStringArray(KEY_LOADER_EXTRA_SIP_NUMBERS, sipNumbers);

        Trace.beginSection("start sms loader");
        getLoaderManager().initLoader(
                LOADER_SMS_ID,
                phonesExtraBundle,
                mLoaderInteractionsCallbacks);
        Trace.endSection();

        Trace.beginSection("start call log loader");
        getLoaderManager().initLoader(
                LOADER_CALL_LOG_ID,
                phonesExtraBundle,
                mLoaderInteractionsCallbacks);
        Trace.endSection();


        Trace.beginSection("start calendar loader");
        final List<DataItem> emailDataItems = dataItemsMap.get(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
        if (emailDataItems != null && emailDataItems.size() == 1) {
        }
        String[] emailAddresses = null;
        if (emailDataItems != null) {
            emailAddresses = new String[emailDataItems.size()];
            for (int i = 0; i < emailDataItems.size(); ++i) {
                emailAddresses[i] = ((EmailDataItem) emailDataItems.get(i)).getAddress();
            }
        }
        final Bundle emailsExtraBundle = new Bundle();
        emailsExtraBundle.putStringArray(KEY_LOADER_EXTRA_EMAILS, emailAddresses);
        getLoaderManager().initLoader(
                LOADER_CALENDAR_ID,
                emailsExtraBundle,
                mLoaderInteractionsCallbacks);
        Trace.endSection();
    }

    private void populateContactAndAboutCard(Cp2DataCardModel cp2DataCardModel,
                                             boolean shouldAddPhoneticName) {
        mCachedCp2DataCardModel = cp2DataCardModel;
        if (mHasIntentLaunched || cp2DataCardModel == null) {
            return;
        }
        Trace.beginSection("bind contact card");

        final List<List<ExpandingEntryCardView.Entry>> contactCardEntries = cp2DataCardModel.contactCardEntries;
        final List<List<ExpandingEntryCardView.Entry>> aboutCardEntries = cp2DataCardModel.aboutCardEntries;
        final String customAboutCardName = cp2DataCardModel.customAboutCardName;
//        liujia mark
//        if (contactCardEntries.size() > 0) {
//            mContactCard.initialize(contactCardEntries,
//                    /* numInitialVisibleEntries = */ MIN_NUM_CONTACT_ENTRIES_SHOWN,
//                    /* isExpanded = */ mContactCard.isExpanded(),
//                    /* isAlwaysExpanded = */ true,
//                    mExpandingEntryCardViewListener,
//                    mScroller);
//            if (mContactCard.getVisibility() == View.GONE && mShouldLog) {
//                Logger.logQuickContactEvent(mReferrer, mContactType, QuickContactEvent.CardType.CONTACT,
//                        QuickContactEvent.ActionType.UNKNOWN_ACTION, /* thirdPartyAction */ null);
//            }
//            mContactCard.setVisibility(View.VISIBLE);
//        } else {
//            mContactCard.setVisibility(View.GONE);
//        }
        Trace.endSection();

        Trace.beginSection("bind about card");
        // Phonetic name is not a data item, so the entry needs to be created separately
        // But if mCachedCp2DataCardModel is passed to this method (e.g. returning from editor
        // without saving any changes), then it should include phoneticName and the phoneticName
        // shouldn't be changed. If this is the case, we shouldn't add it again. b/27459294
        final String phoneticName = mContactData.getPhoneticName();
        if (shouldAddPhoneticName && !TextUtils.isEmpty(phoneticName)) {
            ExpandingEntryCardView.Entry phoneticEntry = new ExpandingEntryCardView.Entry(/* viewId = */ -1,
                    /* icon = */ null,
                    getResources().getString(R.string.name_phonetic),
                    phoneticName,
                    /* subHeaderIcon = */ null,
                    /* text = */ null,
                    /* textIcon = */ null,
                    /* primaryContentDescription = */ null,
                    /* intent = */ null,
                    /* alternateIcon = */ null,
                    /* alternateIntent = */ null,
                    /* alternateContentDescription = */ null,
                    /* shouldApplyColor = */ false,
                    /* isEditable = */ false,
                    /* EntryContextMenuInfo = */ new ExpandingEntryCardView.EntryContextMenuInfo(phoneticName,
                    getResources().getString(R.string.name_phonetic),
                    /* mimeType = */ null, /* id = */ -1, /* isPrimary = */ false),
                    /* thirdIcon = */ null,
                    /* thirdIntent = */ null,
                    /* thirdContentDescription = */ null,
                    /* thirdAction = */ ExpandingEntryCardView.Entry.ACTION_NONE,
                    /* thirdExtras = */ null,
                    /* shouldApplyThirdIconColor = */ true,
                    /* iconResourceId = */  0);
            List<ExpandingEntryCardView.Entry> phoneticList = new ArrayList<>();
            phoneticList.add(phoneticEntry);
            // Phonetic name comes after nickname. Check to see if the first entry type is nickname
            if (aboutCardEntries.size() > 0 && aboutCardEntries.get(0).get(0).getHeader().equals(
                    getResources().getString(R.string.header_nickname_entry))) {
                aboutCardEntries.add(1, phoneticList);
            } else {
                aboutCardEntries.add(0, phoneticList);
            }
        }

        if (contactCardEntries.size() == 0 && aboutCardEntries.size() == 0) {
//            initializeNoContactDetailCard(cp2DataCardModel.areAllRawContactsSimAccounts);
        } else {
//            mNoContactDetailsCard.setVisibility(View.GONE);
        }

        // If the Recent card is already initialized (all recent data is loaded), show the About
        // card if it has entries. Otherwise About card visibility will be set in bindRecentData()
        if (aboutCardEntries.size() > 0) {
//            if (mAboutCard.getVisibility() == View.GONE && mShouldLog) {
//                Logger.logQuickContactEvent(mReferrer, mContactType, QuickContactEvent.CardType.ABOUT,
//                        QuickContactEvent.ActionType.UNKNOWN_ACTION, /* thirdPartyAction */ null);
//            }
//            if (isAllRecentDataLoaded()) {
//                mAboutCard.setVisibility(View.VISIBLE);
//            }
        }
        Trace.endSection();
    }

    /**
     * Builds the {@link DataItem}s Map out of the Contact.
     * @param data The contact to build the data from.
     * @return A pair containing a list of data items sorted within mimetype and sorted
     *  amongst mimetype. The map goes from mimetype string to the sorted list of data items within
     *  mimetype
     */
    private Cp2DataCardModel generateDataModelFromContact(
            Contact data) {
        Trace.beginSection("Build data items map");

        final Map<String, List<DataItem>> dataItemsMap = new HashMap<>();
        final boolean tachyonEnabled = CallUtil.isTachyonEnabled(this);

        for (RawContact rawContact : data.getRawContacts()) {
            for (DataItem dataItem : rawContact.getDataItems()) {
                dataItem.setRawContactId(rawContact.getId());

                final String mimeType = dataItem.getMimeType();
                if (mimeType == null) continue;

                if (!MIMETYPE_TACHYON.equals(mimeType)) {
                    // Only validate non-Tachyon mimetypes.
                    final AccountType accountType = rawContact.getAccountType(this);
                    final DataKind dataKind = AccountTypeManager.getInstance(this)
                            .getKindOrFallback(accountType, mimeType);
                    if (dataKind == null) continue;

                    dataItem.setDataKind(dataKind);

                    final boolean hasData = !TextUtils.isEmpty(dataItem.buildDataString(this,
                            dataKind));

                    if (isMimeExcluded(mimeType) || !hasData) continue;
                } else if (!tachyonEnabled) {
                    // If tachyon isn't enabled, skip its mimetypes.
                    continue;
                }

                List<DataItem> dataItemListByType = dataItemsMap.get(mimeType);
                if (dataItemListByType == null) {
                    dataItemListByType = new ArrayList<>();
                    dataItemsMap.put(mimeType, dataItemListByType);
                }
                dataItemListByType.add(dataItem);
            }
        }
        Trace.endSection();
        bindReachability(dataItemsMap);

        Trace.beginSection("sort within mimetypes");
        /*
         * Sorting is a multi part step. The end result is to a have a sorted list of the most
         * used data items, one per mimetype. Then, within each mimetype, the list of data items
         * for that type is also sorted, based off of {super primary, primary, times used} in that
         * order.
         */
        final List<List<DataItem>> dataItemsList = new ArrayList<>();
        for (List<DataItem> mimeTypeDataItems : dataItemsMap.values()) {
            // Remove duplicate data items
            Collapser.collapseList(mimeTypeDataItems, this);
            // Sort within mimetype
            Collections.sort(mimeTypeDataItems, mWithinMimeTypeDataItemComparator);
            // Add to the list of data item lists
            dataItemsList.add(mimeTypeDataItems);
        }
        Trace.endSection();

        Trace.beginSection("sort amongst mimetypes");
        // Sort amongst mimetypes to bubble up the top data items for the contact card
        Collections.sort(dataItemsList, mAmongstMimeTypeDataItemComparator);
        Trace.endSection();

        Trace.beginSection("cp2 data items to entries");

        final List<List<ExpandingEntryCardView.Entry>> contactCardEntries = new ArrayList<>();
        final List<List<ExpandingEntryCardView.Entry>> aboutCardEntries = buildAboutCardEntries(dataItemsMap);
        final MutableString aboutCardName = new MutableString();

        for (int i = 0; i < dataItemsList.size(); ++i) {
            final List<DataItem> dataItemsByMimeType = dataItemsList.get(i);
            final DataItem topDataItem = dataItemsByMimeType.get(0);
            if (SORTED_ABOUT_CARD_MIMETYPES.contains(topDataItem.getMimeType())) {
                // About card mimetypes are built in buildAboutCardEntries, skip here
                continue;
            } else {
                List<ExpandingEntryCardView.Entry> contactEntries = dataItemsToEntries(dataItemsList.get(i),
                        aboutCardName);
                if (contactEntries.size() > 0) {
                    contactCardEntries.add(contactEntries);
                }
            }
        }

        Trace.endSection();

        final Cp2DataCardModel dataModel = new Cp2DataCardModel();
        dataModel.customAboutCardName = aboutCardName.value;
        dataModel.aboutCardEntries = aboutCardEntries;
        dataModel.contactCardEntries = contactCardEntries;
        dataModel.dataItemsMap = dataItemsMap;
        dataModel.areAllRawContactsSimAccounts = data.areAllRawContactsSimAccounts(this);
        return dataModel;
    }

    private List<List<ExpandingEntryCardView.Entry>> buildAboutCardEntries(Map<String, List<DataItem>> dataItemsMap) {
        final List<List<ExpandingEntryCardView.Entry>> aboutCardEntries = new ArrayList<>();
        for (String mimetype : SORTED_ABOUT_CARD_MIMETYPES) {
            final List<DataItem> mimeTypeItems = dataItemsMap.get(mimetype);
            if (mimeTypeItems == null) {
                continue;
            }
            // Set aboutCardTitleOut = null, since SORTED_ABOUT_CARD_MIMETYPES doesn't contain
            // the name mimetype.
            final List<ExpandingEntryCardView.Entry> aboutEntries = dataItemsToEntries(mimeTypeItems,
                    /* aboutCardTitleOut = */ null);
            if (aboutEntries.size() > 0) {
                aboutCardEntries.add(aboutEntries);
            }
        }
        return aboutCardEntries;
    }

    private List<ExpandingEntryCardView.Entry> dataItemsToEntries(List<DataItem> dataItems,
                                                                  MutableString aboutCardTitleOut) {
        // Hangouts and G+ use two data items to create one entry.
        if (dataItems.get(0).getMimeType().equals(MIMETYPE_GPLUS_PROFILE)) {
            return gPlusDataItemsToEntries(dataItems);
        } else if (dataItems.get(0).getMimeType().equals(MIMETYPE_HANGOUTS)) {
            return hangoutsDataItemsToEntries(dataItems);
        } else {
            final List<ExpandingEntryCardView.Entry> entries = new ArrayList<>();
            for (DataItem dataItem : dataItems) {
                final ExpandingEntryCardView.Entry entry = dataItemToEntry(dataItem, /* secondDataItem = */ null,
                        this, mContactData, aboutCardTitleOut);
                if (entry != null) {
                    entries.add(entry);
                }
            }
            return entries;
        }
    }

    /**
     * For Hangouts entries, a single ExpandingEntryCardView.Entry consists of two data items. This
     * method attempts to build each entry using the two data items if they are available. If there
     * are more or less than two data items, a fall back is used and each data item gets its own
     * entry.
     */
    private List<ExpandingEntryCardView.Entry> hangoutsDataItemsToEntries(List<DataItem> dataItems) {
        final List<ExpandingEntryCardView.Entry> entries = new ArrayList<>();

        // Use the buckets to build entries. If a bucket contains two data items, build the special
        // entry, otherwise fall back to the normal entry.
        for (List<DataItem> bucket : dataItemsToBucket(dataItems).values()) {
            if (bucket.size() == 2) {
                // Use the pair to build an entry
                final ExpandingEntryCardView.Entry entry = dataItemToEntry(bucket.get(0),
                        /* secondDataItem = */ bucket.get(1), this, mContactData,
                        /* aboutCardName = */ null);
                if (entry != null) {
                    entries.add(entry);
                }
            } else {
                for (DataItem dataItem : bucket) {
                    final ExpandingEntryCardView.Entry entry = dataItemToEntry(dataItem, /* secondDataItem = */ null,
                            this, mContactData, /* aboutCardName = */ null);
                    if (entry != null) {
                        entries.add(entry);
                    }
                }
            }
        }
        return entries;
    }

    private  ExpandingEntryCardView.Entry dataItemToEntry(DataItem dataItem, DataItem secondDataItem,
                                                          Context context, Contact contactData,
                                                          final MutableString aboutCardName){
        return null;
    }

    /**
     * Put the data items into buckets based on the raw contact id
     */
    private Map<Long, List<DataItem>> dataItemsToBucket(List<DataItem> dataItems) {
        final Map<Long, List<DataItem>> buckets = new HashMap<>();
        for (DataItem dataItem : dataItems) {
            List<DataItem> bucket = buckets.get(dataItem.getRawContactId());
            if (bucket == null) {
                bucket = new ArrayList<>();
                buckets.put(dataItem.getRawContactId(), bucket);
            }
            bucket.add(dataItem);
        }
        return buckets;
    }

    /**
     * For G+ entries, a single ExpandingEntryCardView.Entry consists of two data items. This
     * method use only the View profile to build entry.
     */
    private List<ExpandingEntryCardView.Entry> gPlusDataItemsToEntries(List<DataItem> dataItems) {
        final List<ExpandingEntryCardView.Entry> entries = new ArrayList<>();

        for (List<DataItem> bucket : dataItemsToBucket(dataItems).values()) {
            for (DataItem dataItem : bucket) {
                if (GPLUS_PROFILE_DATA_5_VIEW_PROFILE.equals(
                        dataItem.getContentValues().getAsString(ContactsContract.Data.DATA5))) {
                    final ExpandingEntryCardView.Entry entry = dataItemToEntry(dataItem, /* secondDataItem = */ null,
                            this, mContactData, /* aboutCardName = */ null);
                    if (entry != null) {
                        entries.add(entry);
                    }
                }
            }
        }
        return entries;
    }

    /**
     * Sorts among different mimetypes based off:
     * 1. Whether one of the mimetypes is the prioritized mimetype
     * 2. Number of times used
     * 3. Last time used
     * 4. Statically defined
     */
    private final Comparator<List<DataItem>> mAmongstMimeTypeDataItemComparator =
            new Comparator<List<DataItem>> () {
                @Override
                public int compare(List<DataItem> lhsList, List<DataItem> rhsList) {
                    final DataItem lhs = lhsList.get(0);
                    final DataItem rhs = rhsList.get(0);
                    final String lhsMimeType = lhs.getMimeType();
                    final String rhsMimeType = rhs.getMimeType();

                    // 1. Whether one of the mimetypes is the prioritized mimetype
                    if (!TextUtils.isEmpty(mExtraPrioritizedMimeType) && !lhsMimeType.equals(rhsMimeType)) {
                        if (rhsMimeType.equals(mExtraPrioritizedMimeType)) {
                            return 1;
                        }
                        if (lhsMimeType.equals(mExtraPrioritizedMimeType)) {
                            return -1;
                        }
                    }

                    // 2. Number of times used
                    final int lhsTimesUsed = lhs.getTimesUsed() == null ? 0 : lhs.getTimesUsed();
                    final int rhsTimesUsed = rhs.getTimesUsed() == null ? 0 : rhs.getTimesUsed();
                    final int timesUsedDifference = rhsTimesUsed - lhsTimesUsed;
                    if (timesUsedDifference != 0) {
                        return timesUsedDifference;
                    }

                    // 3. Last time used
                    final long lhsLastTimeUsed =
                            lhs.getLastTimeUsed() == null ? 0 : lhs.getLastTimeUsed();
                    final long rhsLastTimeUsed =
                            rhs.getLastTimeUsed() == null ? 0 : rhs.getLastTimeUsed();
                    final long lastTimeUsedDifference = rhsLastTimeUsed - lhsLastTimeUsed;
                    if (lastTimeUsedDifference > 0) {
                        return 1;
                    } else if (lastTimeUsedDifference < 0) {
                        return -1;
                    }

                    // 4. Resort to a statically defined mimetype order.
                    if (!lhsMimeType.equals(rhsMimeType)) {
                        for (String mimeType : LEADING_MIMETYPES) {
                            if (lhsMimeType.equals(mimeType)) {
                                return -1;
                            } else if (rhsMimeType.equals(mimeType)) {
                                return 1;
                            }
                        }
                    }
                    return 0;
                }
            };

    /**
     * Data items are compared to the same mimetype based off of three qualities:
     * 1. Super primary
     * 2. Primary
     * 3. Times used
     */
    private final Comparator<DataItem> mWithinMimeTypeDataItemComparator =
            new Comparator<DataItem>() {
                @Override
                public int compare(DataItem lhs, DataItem rhs) {
                    if (!lhs.getMimeType().equals(rhs.getMimeType())) {
                        Log.wtf(TAG, "Comparing DataItems with different mimetypes lhs.getMimeType(): " +
                                lhs.getMimeType() + " rhs.getMimeType(): " + rhs.getMimeType());
                        return 0;
                    }

                    if (lhs.isSuperPrimary()) {
                        return -1;
                    } else if (rhs.isSuperPrimary()) {
                        return 1;
                    } else if (lhs.isPrimary() && !rhs.isPrimary()) {
                        return -1;
                    } else if (!lhs.isPrimary() && rhs.isPrimary()) {
                        return 1;
                    } else {
                        final int lhsTimesUsed =
                                lhs.getTimesUsed() == null ? 0 : lhs.getTimesUsed();
                        final int rhsTimesUsed =
                                rhs.getTimesUsed() == null ? 0 : rhs.getTimesUsed();

                        return rhsTimesUsed - lhsTimesUsed;
                    }
                }
            };

    /**
     * Bind the custom data items to each {@link PhoneDataItem} that is Tachyon reachable, the data
     * will be needed when creating the {@link ExpandingEntryCardView.Entry} for the {@link PhoneDataItem}.
     */
    private void bindReachability(Map<String, List<DataItem>> dataItemsMap) {
        final List<DataItem> phoneItems = dataItemsMap.get(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        final List<DataItem> tachyonItems = dataItemsMap.get(MIMETYPE_TACHYON);
        if (phoneItems != null && tachyonItems != null) {
            for (DataItem phone : phoneItems) {
                if (phone instanceof PhoneDataItem && ((PhoneDataItem) phone).getNumber() != null) {
                    for (DataItem tachyonItem : tachyonItems) {
                        if (((PhoneDataItem) phone).getNumber().equals(
                                tachyonItem.getContentValues().getAsString(ContactsContract.Data.DATA1))) {
                            ((PhoneDataItem) phone).setTachyonReachable(true);
                            ((PhoneDataItem) phone).setReachableDataItem(tachyonItem);
                        }
                    }
                }
            }
        }
    }

    /**
     * Check if the given MIME-type appears in the list of excluded MIME-types
     * that the most-recent caller requested.
     */
    private boolean isMimeExcluded(String mimeType) {
        if (mExcludeMimes == null) return false;
        for (String excludedMime : mExcludeMimes) {
            if (TextUtils.equals(excludedMime, mimeType)) {
                return true;
            }
        }
        return false;
    }

    private class SaveServiceListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "Got broadcast from save service " + intent);
            }
            if (ContactSaveService.BROADCAST_LINK_COMPLETE.equals(intent.getAction())
                    || ContactSaveService.BROADCAST_UNLINK_COMPLETE.equals(intent.getAction())) {
                dismissProgressBar();
                if (ContactSaveService.BROADCAST_UNLINK_COMPLETE.equals(intent.getAction())) {
                    finish();
                }
            }
        }
    }

    /**
     * Class used to hold the About card and Contact cards' data model that gets generated
     * on a background thread. All data is from CP2.
     */
    private static class Cp2DataCardModel {
        /**
         * A map between a mimetype string and the corresponding list of data items. The data items
         * are in sorted order using mWithinMimeTypeDataItemComparator.
         */
        public Map<String, List<DataItem>> dataItemsMap;
        public List<List<ExpandingEntryCardView.Entry>> aboutCardEntries;
        public List<List<ExpandingEntryCardView.Entry>> contactCardEntries;
        public String customAboutCardName;
        public boolean areAllRawContactsSimAccounts;
    }

    private boolean isAllRecentDataLoaded() {
        return mRecentLoaderResults.size() == mRecentLoaderIds.length;
    }

    private List<ExpandingEntryCardView.Entry> contactInteractionsToEntries(List<ContactInteraction> interactions) {
        final List<ExpandingEntryCardView.Entry> entries = new ArrayList<>();
        for (ContactInteraction interaction : interactions) {
            if (interaction == null) {
                continue;
            }
            entries.add(new ExpandingEntryCardView.Entry(/* id = */ -1,
                    interaction.getIcon(this),
                    interaction.getViewHeader(this),
                    interaction.getViewBody(this),
                    interaction.getBodyIcon(this),
                    interaction.getViewFooter(this),
                    interaction.getFooterIcon(this),
                    interaction.getContentDescription(this),
                    interaction.getIntent(),
                    /* alternateIcon = */ null,
                    /* alternateIntent = */ null,
                    /* alternateContentDescription = */ null,
                    /* shouldApplyColor = */ true,
                    /* isEditable = */ false,
                    /* EntryContextMenuInfo = */ null,
                    /* thirdIcon = */ null,
                    /* thirdIntent = */ null,
                    /* thirdContentDescription = */ null,
                    /* thirdAction = */ ExpandingEntryCardView.Entry.ACTION_NONE,
                    /* thirdActionExtras = */ null,
                    /* shouldApplyThirdIconColor = */ true,
                    interaction.getIconResourceId()));
        }
        return entries;
    }
    // Permission explanation card.
    private boolean mShouldShowPermissionExplanation = false;
    private String mPermissionExplanationCardSubHeader = "";
    private void bindRecentData() {
        final List<ContactInteraction> allInteractions = new ArrayList<>();
        final List<List<ExpandingEntryCardView.Entry>> interactionsWrapper = new ArrayList<>();

        // Serialize mRecentLoaderResults into a single list. This should be done on the main
        // thread to avoid races against mRecentLoaderResults edits.
        for (List<ContactInteraction> loaderInteractions : mRecentLoaderResults.values()) {
            allInteractions.addAll(loaderInteractions);
        }

        mRecentDataTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Trace.beginSection("sort recent loader results");

                // Sort the interactions by most recent
                Collections.sort(allInteractions, new Comparator<ContactInteraction>() {
                    @Override
                    public int compare(ContactInteraction a, ContactInteraction b) {
                        if (a == null && b == null) {
                            return 0;
                        }
                        if (a == null) {
                            return 1;
                        }
                        if (b == null) {
                            return -1;
                        }
                        if (a.getInteractionDate() > b.getInteractionDate()) {
                            return -1;
                        }
                        if (a.getInteractionDate() == b.getInteractionDate()) {
                            return 0;
                        }
                        return 1;
                    }
                });

                Trace.endSection();
                Trace.beginSection("contactInteractionsToEntries");

                // Wrap each interaction in its own list so that an icon is displayed for each entry
                for (ExpandingEntryCardView.Entry contactInteraction : contactInteractionsToEntries(allInteractions)) {
                    List<ExpandingEntryCardView.Entry> entryListWrapper = new ArrayList<>(1);
                    entryListWrapper.add(contactInteraction);
                    interactionsWrapper.add(entryListWrapper);
                }

                Trace.endSection();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Trace.beginSection("initialize recents card");

                if (allInteractions.size() > 0) {

                } else {
                }

                Trace.endSection();
                Trace.beginSection("initialize permission explanation card");

                final Drawable historyIcon = ResourcesCompat.getDrawable(getResources(),
                        R.drawable.quantum_ic_history_vd_theme_24, null);

                final ExpandingEntryCardView.Entry permissionExplanationEntry = new ExpandingEntryCardView.Entry(CARD_ENTRY_ID_REQUEST_PERMISSION,
                        historyIcon, getString(R.string.permission_explanation_header),
                        mPermissionExplanationCardSubHeader, /* subHeaderIcon = */ null,
                        /* text = */ null, /* textIcon = */ null,
                        /* primaryContentDescription = */ null, getIntent(),
                        /* alternateIcon = */ null, /* alternateIntent = */ null,
                        /* alternateContentDescription = */ null, /* shouldApplyColor = */ true,
                        /* isEditable = */ false, /* EntryContextMenuInfo = */ null,
                        /* thirdIcon = */ null, /* thirdIntent = */ null,
                        /* thirdContentDescription = */ null, /* thirdAction = */ ExpandingEntryCardView.Entry.ACTION_NONE,
                        /* thirdExtras = */ null,
                        /* shouldApplyThirdIconColor = */ true,
                        R.drawable.quantum_ic_history_vd_theme_24);

                final List<List<ExpandingEntryCardView.Entry>> permissionExplanationEntries = new ArrayList<>();
                permissionExplanationEntries.add(new ArrayList<ExpandingEntryCardView.Entry>());
                permissionExplanationEntries.get(0).add(permissionExplanationEntry);

                final int subHeaderTextColor = getResources().getColor(android.R.color.white);
                final PorterDuffColorFilter whiteColorFilter =
                        new PorterDuffColorFilter(subHeaderTextColor, PorterDuff.Mode.SRC_ATOP);



                Trace.endSection();

                // About card is initialized along with the contact card, but since it appears after
                // the recent card in the UI, we hold off until making it visible until the recent
                // card is also ready to avoid stuttering.

                mRecentDataTask = null;
            }
        };
        mRecentDataTask.execute();
    }

    private final LoaderManager.LoaderCallbacks<List<ContactInteraction>> mLoaderInteractionsCallbacks =
            new LoaderManager.LoaderCallbacks<List<ContactInteraction>>() {

                @Override
                public Loader<List<ContactInteraction>> onCreateLoader(int id, Bundle args) {
                    Loader<List<ContactInteraction>> loader = null;
                    switch (id) {
                        case LOADER_SMS_ID:
                            loader = new SmsInteractionsLoader(
                                    QuickContactActivity.this,
                                    args.getStringArray(KEY_LOADER_EXTRA_PHONES),
                                    MAX_SMS_RETRIEVE);
                            break;
                        case LOADER_CALENDAR_ID:
                            final String[] emailsArray = args.getStringArray(KEY_LOADER_EXTRA_EMAILS);
                            List<String> emailsList = null;
                            if (emailsArray != null) {
                                emailsList = Arrays.asList(args.getStringArray(KEY_LOADER_EXTRA_EMAILS));
                            }
                            loader = new CalendarInteractionsLoader(
                                    QuickContactActivity.this,
                                    emailsList,
                                    MAX_FUTURE_CALENDAR_RETRIEVE,
                                    MAX_PAST_CALENDAR_RETRIEVE,
                                    FUTURE_MILLISECOND_TO_SEARCH_LOCAL_CALENDAR,
                                    PAST_MILLISECOND_TO_SEARCH_LOCAL_CALENDAR);
                            break;
                        case LOADER_CALL_LOG_ID:
                            loader = new CallLogInteractionsLoader(
                                    QuickContactActivity.this,
                                    args.getStringArray(KEY_LOADER_EXTRA_PHONES),
                                    args.getStringArray(KEY_LOADER_EXTRA_SIP_NUMBERS),
                                    MAX_CALL_LOG_RETRIEVE);
                    }
                    return loader;
                }

                @Override
                public void onLoadFinished(Loader<List<ContactInteraction>> loader,
                                           List<ContactInteraction> data) {
                    mRecentLoaderResults.put(loader.getId(), data);

                    if (isAllRecentDataLoaded()) {
                        bindRecentData();
                    }
                }

                @Override
                public void onLoaderReset(Loader<List<ContactInteraction>> loader) {
                    mRecentLoaderResults.remove(loader.getId());
                }
            };

    private static class MutableString {
        public String value;
    }

    /****************** liujia add *******************/
    private ToroContactDetailsToolbar toroBar;
    private ImageView toroPhoto,toroCallAction;
    private TextView toroName;
    private RecyclerView recyclerView;

    private void setupToroView(){
        toroBar = findViewById(R.id.toolbar);
        toroPhoto = findViewById(R.id.toro_photo);
        toroCallAction = findViewById(R.id.toro_all_action);
        toroName = findViewById(R.id.toro_detail_name);
        recyclerView = findViewById(R.id.recycler_view);
    }

    private void updateToroView(){
        final String displayName = ContactDisplayUtils.getDisplayName(this, mContactData).toString();

        toroBar.setLeftButton(getString(R.string.toro_back), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toroBar.setRightButton(getString(R.string.toro_more), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreDialogShow();
            }
        });
        setToroPhotoView();
        toroName.setText(displayName);

    }

    private void updateToroNumber(Cp2DataCardModel cp2DataCardModel) {
        final Map<String, List<DataItem>> dataItemsMap = cp2DataCardModel.dataItemsMap;
        final List<DataItem> phoneDataItems = dataItemsMap.get(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        final List<DataItem> sipCallDataItems = dataItemsMap.get(ContactsContract.CommonDataKinds.SipAddress.CONTENT_ITEM_TYPE);
        if (phoneDataItems != null && phoneDataItems.size() == 1) {
            mOnlyOnePhoneNumber = true;
        }
        List<String> phoneNumbers = new ArrayList<>();
        if (phoneDataItems != null) {
            for (int i = 0; i < phoneDataItems.size(); ++i) {
                phoneNumbers.add(((PhoneDataItem) phoneDataItems.get(i)).getNumber());
            }
        }
        ToroCallDetailsAdapter adapter = new ToroCallDetailsAdapter(this,phoneNumbers);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        toroCallAction.setOnClickListener(new ToroOnclickListener(phoneNumbers,ToroOnclickListener.ACTION_OUT_CALL));
    }

    private void setToroPhotoView() {
        ContactPhotoManager mContactPhotoManager;
        mContactPhotoManager = ContactPhotoManager.getInstance(this);
        if (mContactData.getPhotoId() != 0) {
            mContactPhotoManager.loadThumbnail(toroPhoto, mContactData.getPhotoId(), false, false,
                    null);
        } else {
            final Uri photoUri = mContactData.getPhotoUri() == null ? null : Uri.parse(mContactData.getPhotoUri());
            ContactPhotoManager.DefaultImageRequest request = null;
            if (photoUri == null) {
                request = new ContactPhotoManager.DefaultImageRequest(mContactData.getDisplayName(), mContactData.getLookupKey(), true);
            }
            mContactPhotoManager.loadDirectoryPhoto(toroPhoto, photoUri, false, false,
                    request);
        }
        toroBar.setTitleText(getString(R.string.toro_contact_details));
    }

    private Intent getEditContactIntent() {
        return EditorIntents.createEditContactIntent(QuickContactActivity.this,
                mContactData.getLookupUri(),
                null,
                mContactData.getPhotoId());
    }

    private void editContact() {
        mHasIntentLaunched = true;
        mContactLoader.cacheResult();
        startActivityForResult(getEditContactIntent(), REQUEST_CODE_CONTACT_EDITOR_ACTIVITY);
    }

    private void moreDialogShow() {
        ToroCommonBottomDialog dialog;
        final ToroCommonBottomDialogEntity de1 = new ToroCommonBottomDialogEntity(getString(R.string.toro_edit));
        final ToroCommonBottomDialogEntity de2 = new ToroCommonBottomDialogEntity(getString(R.string.menu_deleteContact));
        dialog = new ToroCommonBottomDialog(this, de1,de2);

        dialog.setOnItemClickListener(new ToroCommonBottomDialog.OnItemClickListener()
        {
            @Override
            public void onItemClick(String text, int listSize, int position)
            {
                if(text.equals(getString(R.string.toro_edit))) {
                    totoEdit();
                    dialog.dismiss();
                }else if(text.equals(getString(R.string.menu_deleteContact))){
                    Logger.logQuickContactEvent(mReferrer, mContactType, QuickContactEvent.CardType.UNKNOWN_CARD,
                            QuickContactEvent.ActionType.REMOVE, /* thirdPartyAction */ null);
                    if (isContactEditable()) {
                        deleteContactDialogShow();
                    }
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    private void totoEdit() {
        if (DirectoryContactUtil.isDirectoryContact(mContactData)) {
            Logger.logQuickContactEvent(mReferrer, mContactType, QuickContactEvent.CardType.UNKNOWN_CARD,
                    QuickContactEvent.ActionType.ADD, /* thirdPartyAction */ null);

            // This action is used to launch the contact selector, with the option of
            // creating a new contact. Creating a new contact is an INSERT, while selecting
            // an exisiting one is an edit. The fields in the edit screen will be
            // prepopulated with data.

            final Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
            intent.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);

            ArrayList<ContentValues> values = mContactData.getContentValues();

            // Only pre-fill the name field if the provided display name is an nickname
            // or better (e.g. structured name, nickname)
            if (mContactData.getDisplayNameSource() >= ContactsContract.DisplayNameSources.NICKNAME) {
                intent.putExtra(ContactsContract.Intents.Insert.NAME, mContactData.getDisplayName());
            } else if (mContactData.getDisplayNameSource()
                    == ContactsContract.DisplayNameSources.ORGANIZATION) {
                // This is probably an organization. Instead of copying the organization
                // name into a name entry, copy it into the organization entry. This
                // way we will still consider the contact an organization.
                final ContentValues organization = new ContentValues();
                organization.put(ContactsContract.CommonDataKinds.Organization.COMPANY, mContactData.getDisplayName());
                organization.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE);
                values.add(organization);
            }

            // Last time used and times used are aggregated values from the usage stat
            // table. They need to be removed from data values so the SQL table can insert
            // properly
            for (ContentValues value : values) {
                value.remove(ContactsContract.Data.LAST_TIME_USED);
                value.remove(ContactsContract.Data.TIMES_USED);
            }
            intent.putExtra(ContactsContract.Intents.Insert.DATA, values);

            // If the contact can only export to the same account, add it to the intent.
            // Otherwise the ContactEditorFragment will show a dialog for selecting
            // an account.
            if (mContactData.getDirectoryExportSupport() ==
                    ContactsContract.Directory.EXPORT_SUPPORT_SAME_ACCOUNT_ONLY) {
                intent.putExtra(ContactsContract.Intents.Insert.EXTRA_ACCOUNT,
                        new Account(mContactData.getDirectoryAccountName(),
                                mContactData.getDirectoryAccountType()));
                intent.putExtra(ContactsContract.Intents.Insert.EXTRA_DATA_SET,
                        mContactData.getRawContacts().get(0).getDataSet());
            }

            // Add this flag to disable the delete menu option on directory contact joins
            // with local contacts. The delete option is ambiguous when joining contacts.
            intent.putExtra(
                    ContactEditorFragment.INTENT_EXTRA_DISABLE_DELETE_MENU_OPTION,
                    true);

            intent.setPackage(getPackageName());
            startActivityForResult(intent, REQUEST_CODE_CONTACT_SELECTION_ACTIVITY);
        } else if (InvisibleContactUtil.isInvisibleAndAddable(mContactData, QuickContactActivity.this)) {
            Logger.logQuickContactEvent(mReferrer, mContactType, QuickContactEvent.CardType.UNKNOWN_CARD,
                    QuickContactEvent.ActionType.ADD, /* thirdPartyAction */ null);
            InvisibleContactUtil.addToDefaultGroup(mContactData, QuickContactActivity.this);
        } else if (isContactEditable()) {
            Logger.logQuickContactEvent(mReferrer, mContactType, QuickContactEvent.CardType.UNKNOWN_CARD,
                    QuickContactEvent.ActionType.EDIT, /* thirdPartyAction */ null);
            editContact();
        }
    }

    private void deleteContactDialogShow() {
        final ToroCommonBottomDialogEntity del = new ToroCommonBottomDialogEntity(getResources().getString(R.string.menu_deleteContact));
        del.setColor(Color.RED);
        ToroCommonBottomDialog dialog = new ToroCommonBottomDialog(this, del);
        dialog.setOnItemClickListener(new ToroCommonBottomDialog.OnItemClickListener()
        {
            @Override
            public void onItemClick(String text, int listSize, int position)
            {
                if(text.equals(getResources().getString(R.string.menu_deleteContact))) {
                    final Uri contactUri = mContactData.getLookupUri();
                    startService(ContactSaveService.createDeleteContactIntent(QuickContactActivity.this, contactUri));

                    setResult(ContactDeletionInteraction.RESULT_CODE_DELETED);
                    finish();
                    final String displayName = ContactDisplayUtils.getDisplayName(QuickContactActivity.this, mContactData).toString();
                    String deleteToastMessage;
                    if (TextUtils.isEmpty(displayName)) {
                        deleteToastMessage = getResources().getQuantityString(
                                R.plurals.contacts_deleted_toast, /* quantity */ 1);
                    } else {
                        deleteToastMessage = getResources().getString(
                                R.string.contacts_deleted_one_named_toast, displayName);
                    }
                    Toast.makeText(QuickContactActivity.this, deleteToastMessage, Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public View.OnClickListener getNumbersMessageListener(List<String> numbers) {
        return new ToroOnclickListener(numbers,ToroOnclickListener.ACTION_SEND_MESSAGE);
    }

    public View.OnClickListener getNumbersLocationListener(List<String> numbers) {
        return new ToroOnclickListener(numbers,ToroOnclickListener.ACTION_SEND_LOCALTION);
    }

    public View.OnClickListener getNumberCallListener(String number) {
        return new ToroOnclickListener(number,ToroOnclickListener.ACTION_OUT_CALL);
    }

    class ToroOnclickListener implements View.OnClickListener{

        public static final String ACTION_SEND_MESSAGE = "send message";
        public static final String ACTION_SEND_LOCALTION = "send location";
        public static final String ACTION_OUT_CALL = "out call";

        private List<String> numbers;
        private String action;

        public ToroOnclickListener(String number,String action) {
            numbers = new ArrayList<>();
            numbers.add(number);
            this.action = action;
        }

        public ToroOnclickListener(List<String> numbers,String action) {
            this.numbers = numbers;
            this.action = action;
        }

        @Override
        public void onClick(View v) {
            if(numbers == null || numbers.size() < 1) {
                totoEdit();
            } else if(numbers.size() == 1) {
                doAction(numbers.get(0));
            }else {
                ToroCommonBottomDialog dialog;
                List<ToroCommonBottomDialogEntity> entitys = new ArrayList<>();
                for(String number:numbers) {
                    ToroCommonBottomDialogEntity entity = new ToroCommonBottomDialogEntity(number);
                    entitys.add(entity);
                }
                dialog = new ToroCommonBottomDialog(QuickContactActivity.this, entitys);

                dialog.setOnItemClickListener(new ToroCommonBottomDialog.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(String text, int listSize, int position)
                    {
                        doAction(text);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        }

        private void doAction(String number){
            Intent intent;
            if(ACTION_SEND_MESSAGE.equals(action)) {
                intent = new Intent(Intent.ACTION_SENDTO,
                        Uri.fromParts(ContactsUtils.SCHEME_SMSTO, number, null));
                intent.putExtra(EXTRA_ACTION_TYPE, QuickContactEvent.ActionType.SMS);
            } else if(ACTION_SEND_LOCALTION.equals(action)) {
                intent = new Intent(Intent.ACTION_SENDTO,
                        Uri.fromParts(ContactsUtils.SCHEME_SMSTO, number, null));
                intent.putExtra(EXTRA_ACTION_TYPE, QuickContactEvent.ActionType.SMS);
            } else if(ACTION_OUT_CALL.equals(action)) {
                intent = CallUtil.getCallIntent(number);
                intent.putExtra(EXTRA_ACTION_TYPE, QuickContactEvent.ActionType.CALL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ImplicitIntentsUtil.startActivityInAppIfPossible(QuickContactActivity.this,
                        intent);
            }else {
                return;
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ImplicitIntentsUtil.startActivityInAppIfPossible(QuickContactActivity.this,
                    intent);
        }
    }
}
