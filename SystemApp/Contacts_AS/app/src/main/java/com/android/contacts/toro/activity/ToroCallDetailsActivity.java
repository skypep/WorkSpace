package com.android.contacts.toro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.QuickContactBadge;

import com.android.contacts.R;

/** Displays the details of a specific call log entry. */
public class ToroCallDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String EXTRA_CALL_DETAILS_ENTRIES = "call_details_entries";
    private static final String EXTRA_CONTACT = "contact";

    private static Integer mBlockId;

    private QuickContactBadge photo;

//    private ToroCallDetailsAdapter adapter;


    public static boolean isLaunchIntent(Intent intent) {
        return intent.getComponent() != null
                && ToroCallDetailsActivity.class.getName().equals(intent.getComponent().getClassName());
    }

//    public static Intent newInstance(
//            Context context,
//            @NonNull CallDetailsEntries details,
//            @NonNull DialerContact contact,Integer blockId,PhoneCallDetails callDetails) {
//        Assert.isNotNull(details);
//        Assert.isNotNull(contact);
//        Intent intent = new Intent(context, ToroCallDetailsActivity.class);
//        ProtoParsers.put(intent, EXTRA_CONTACT, contact);
//        ProtoParsers.put(intent, EXTRA_CALL_DETAILS_ENTRIES, details);
//        mCallDetails = callDetails;
//        mBlockId = blockId;
//        return intent;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toro_contact_details_activity);
//        ToroUtils.setStatusBarColor(this,R.color.toro_statu_bar_bg);
        ToroContactDetailsToolbar toolbar = findViewById(R.id.toolbar);

        findViewById(R.id.toro_all_action).setOnClickListener(this);

        onHandleIntent(getIntent());

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Some calls may not be recorded (eg. from quick contact),
        // so we should restart recording after these calls. (Recorded call is stopped)
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        onHandleIntent(intent);
    }

    private void onHandleIntent(Intent intent) {
//        contact = ProtoParsers.getTrusted(intent, EXTRA_CONTACT, DialerContact.getDefaultInstance());
//        entries =
//                ProtoParsers.getTrusted(
//                        intent, EXTRA_CALL_DETAILS_ENTRIES, CallDetailsEntries.getDefaultInstance())
//                        .getEntriesList();

//        RecyclerView recyclerView = findViewById(R.id.recycler_view);
//        adapter = new ToroCallDetailsAdapter(this, contact, entries,mBlockId,mCallDetails);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//
//        photo = findViewById(R.id.toro_photo);
//        ContactPhotoManager.getInstance(this)
//                .loadDialerThumbnailOrPhoto(
//                        photo,
//                        Uri.parse(contact.getContactUri()),
//                        contact.getPhotoId(),
//                        Uri.parse(contact.getPhotoUri()),
//                        contact.getNameOrNumber(),
//                        LetterTileDrawable.TYPE_DEFAULT);

//        String photoDescription =
//                getString(com.android.dialer.R.string.description_quick_contact_for, contact.getNameOrNumber());// fixed by liujia com.android.contacts.common.R
//        photo.setContentDescription(photoDescription);
//
//        ((TextView)findViewById(R.id.toro_detail_name)).setText(contact.getNameOrNumber());
    }

    @Override
    public void onBackPressed() {
//        PerformanceReport.recordClick(UiAction.Type.PRESS_ANDROID_BACK_BUTTON);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toro_all_action:
//                DialerUtils.startActivityWithErrorToast(
//                        v.getContext(),
//                        new CallIntentBuilder(contact.getNumber(), CallInitiationType.Type.CALL_DETAILS).build());
                break;
        }
    }
}
