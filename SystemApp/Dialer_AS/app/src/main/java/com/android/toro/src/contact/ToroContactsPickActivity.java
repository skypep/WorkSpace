/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.toro.src.contact;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;
import android.view.View.OnScrollChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.contacts.common.preference.ContactsPreferences;
import com.android.contacts.common.preference.ContactsPreferences.ChangeListener;
import com.android.dialer.common.Assert;
import com.android.dialer.performancereport.PerformanceReport;
import com.android.dialer.util.DialerUtils;
import com.android.dialer.util.IntentUtil;
import com.android.dialer.util.PermissionsUtil;
import com.android.dialer.widget.EmptyContentView;
import com.android.dialer.widget.EmptyContentView.OnEmptyViewActionButtonClickedListener;

import com.android.dialer.R;

import java.util.ArrayList;
import java.util.List;

/** Fragment containing a list of all contacts. */
public class ToroContactsPickActivity extends Activity
    implements LoaderCallbacks<Cursor>,
        OnScrollChangeListener,
        OnEmptyViewActionButtonClickedListener,
        ChangeListener {

  public static final int READ_CONTACTS_PERMISSION_REQUEST_CODE = 1;
  public static final String PICK_CONTACT_EXTRA = "pick_result";

  private ToroFastScroller toroFastScroller;
  private TextView anchoredHeader;
  private RecyclerView recyclerView;
  private LinearLayoutManager manager;
  private ToroContactsAdapter adapter;
  private EmptyContentView emptyContentView;

  private ContactsPreferences contactsPrefs;
  private TextView selectFinishView;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    contactsPrefs = new ContactsPreferences(this);
    contactsPrefs.registerChangeListener(this);
    Window window = getWindow();
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    window.setStatusBarColor(Color.rgb(0xF5,0xF7,0xFE));
    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    setContentView(R.layout.toro_contacts_activity);
    ((TextView)findViewById(R.id.tv_title)).setText(getString(R.string.toro_contact_pick));
    findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });
    toroFastScroller = findViewById(R.id.fast_scroller);
    anchoredHeader = findViewById(R.id.header);
    recyclerView = findViewById(R.id.recycler_view);
    recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));// liujia add

    emptyContentView = findViewById(R.id.empty_list_view);
    emptyContentView.setImage(R.drawable.empty_contacts);
    emptyContentView.setActionClickedListener(this);

    selectFinishView = findViewById(R.id.toro_select_finish);

    if (PermissionsUtil.hasContactsReadPermissions(this)) {
      getLoaderManager().initLoader(0, null, this);
    } else {
      emptyContentView.setDescription(R.string.permission_no_contacts);
      emptyContentView.setActionLabel(R.string.permission_single_turn_on);
      emptyContentView.setVisibility(View.VISIBLE);
    }

  }

  @Override
  public void onChange() {
    getLoaderManager().restartLoader(0, null, this);
  }

  /** @return a loader according to sort order and display order. */
  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    boolean sortOrderPrimary =
        (contactsPrefs.getSortOrder() == ContactsPreferences.SORT_ORDER_PRIMARY);
    boolean displayOrderPrimary =
        (contactsPrefs.getDisplayOrder() == ContactsPreferences.DISPLAY_ORDER_PRIMARY);

    String sortKey = sortOrderPrimary ? Contacts.SORT_KEY_PRIMARY : Contacts.SORT_KEY_ALTERNATIVE;
    return displayOrderPrimary
        ? ToroContactsCursorLoader.createInstanceDisplayNamePrimary(this, sortKey)
        : ToroContactsCursorLoader.createInstanceDisplayNameAlternative(this, sortKey);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    if (cursor == null || (cursor != null && cursor.getCount() == 0)) {
      emptyContentView.setDescription(R.string.all_contacts_empty);
      emptyContentView.setActionLabel(R.string.all_contacts_empty_add_contact_action);
      emptyContentView.setVisibility(View.VISIBLE);
      recyclerView.setVisibility(View.GONE);
    } else {
      emptyContentView.setVisibility(View.GONE);
      recyclerView.setVisibility(View.VISIBLE);
      adapter = new ToroContactsAdapter(this, cursor,onSelectChangeListener,getOldList());
      manager =
          new LinearLayoutManager(this) {
            @Override
            public void onLayoutChildren(Recycler recycler, State state) {
              super.onLayoutChildren(recycler, state);
              int itemsShown = findLastVisibleItemPosition() - findFirstVisibleItemPosition() + 1;
              if (adapter.getItemCount() > itemsShown) {
                toroFastScroller.setVisibility(View.VISIBLE);
                recyclerView.setOnScrollChangeListener(ToroContactsPickActivity.this);
              } else {
                toroFastScroller.setVisibility(View.GONE);
              }
            }
          };

      adapter.setOnSearchClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//          ((DialtactsActivity)getActivity()).onToroSearchClick();
        }
      });
      recyclerView.setLayoutManager(manager);
      recyclerView.setAdapter(adapter);
      PerformanceReport.logOnScrollStateChange(recyclerView);
      toroFastScroller.setup(adapter, manager);
    }
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
    recyclerView.setAdapter(null);
    recyclerView.setOnScrollChangeListener(null);
    adapter = null;
    contactsPrefs.unregisterChangeListener();
  }

  /*
   * When our recycler view updates, we need to ensure that our row headers and anchored header
   * are in the correct state.
   *
   * The general rule is, when the row headers are shown, our anchored header is hidden. When the
   * recycler view is scrolling through a sublist that has more than one element, we want to show
   * out anchored header, to create the illusion that our row header has been anchored. In all
   * other situations, we want to hide the anchor because that means we are transitioning between
   * two sublists.
   */
  @Override
  public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
    toroFastScroller.updateContainerAndScrollBarPosition(recyclerView);
    int firstVisibleItem = manager.findFirstVisibleItemPosition();
    int firstCompletelyVisible = manager.findFirstCompletelyVisibleItemPosition();
    if (firstCompletelyVisible == RecyclerView.NO_POSITION) {
      // No items are visible, so there are no headers to update.
      return;
    }
    String anchoredHeaderString = adapter.getHeaderString(firstCompletelyVisible);

    // If the user swipes to the top of the list very quickly, there is some strange behavior
    // between this method updating headers and adapter#onBindViewHolder updating headers.
    // To overcome this, we refresh the headers to ensure they are correct.
    if (firstVisibleItem == firstCompletelyVisible && firstVisibleItem == 0) {
      adapter.refreshHeaders();
      anchoredHeader.setVisibility(View.INVISIBLE);
    } else if (firstVisibleItem != 0) { // skip the add contact row
      if (adapter.getHeaderString(firstVisibleItem).equals(anchoredHeaderString)) {
        anchoredHeader.setText(anchoredHeaderString);
        anchoredHeader.setVisibility(View.VISIBLE);
        getContactHolder(firstVisibleItem).getHeaderView().setVisibility(View.INVISIBLE);
        getContactHolder(firstCompletelyVisible).getHeaderView().setVisibility(View.INVISIBLE);
      } else {
        anchoredHeader.setVisibility(View.INVISIBLE);
        getContactHolder(firstVisibleItem).getHeaderView().setVisibility(View.VISIBLE);
        getContactHolder(firstCompletelyVisible).getHeaderView().setVisibility(View.VISIBLE);
      }
    }
  }

  private ToroContactViewHolder getContactHolder(int position) {
    return ((ToroContactViewHolder) recyclerView.findViewHolderForAdapterPosition(position));
  }

  @Override
  public void onEmptyViewActionButtonClicked() {
    if (emptyContentView.getActionLabel() == R.string.permission_single_turn_on) {
      String[] deniedPermissions =
          PermissionsUtil.getPermissionsCurrentlyDenied(
              this, PermissionsUtil.allContactsGroupPermissionsUsedInDialer);
      if (deniedPermissions.length > 0) {
//        LogUtil.i(
//            "ToroContactsPickActivity.onEmptyViewActionButtonClicked",
//            "Requesting permissions: " + Arrays.toString(deniedPermissions));
//        FragmentCompat.requestPermissions(
//            this, deniedPermissions, READ_CONTACTS_PERMISSION_REQUEST_CODE);
      }

    } else if (emptyContentView.getActionLabel()
        == R.string.all_contacts_empty_add_contact_action) {
      // Add new contact
      DialerUtils.startActivityWithErrorToast(
          this, IntentUtil.getNewContactIntent(), R.string.add_contact_not_available);
    } else {
      throw Assert.createIllegalStateFailException("Invalid empty content view action label.");
    }
  }

  @Override
  public void onRequestPermissionsResult(
      int requestCode, String[] permissions, int[] grantResults) {
    if (requestCode == READ_CONTACTS_PERMISSION_REQUEST_CODE) {
      if (grantResults.length >= 1 && PackageManager.PERMISSION_GRANTED == grantResults[0]) {
        // Force a refresh of the data since we were missing the permission before this.
        emptyContentView.setVisibility(View.GONE);
        getLoaderManager().initLoader(0, null, this);
      }
    }
  }

  private ToroContactsAdapter.OnSelectChangeListener onSelectChangeListener = new ToroContactsAdapter.OnSelectChangeListener() {

    @Override
    public void OnSelectChange(int count) {
      selectFinishView.setVisibility(View.VISIBLE);
      selectFinishView.setText(getString(R.string.toro_select_finish_format) + " (" + count + ")");
      selectFinishView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          finish();
        }
      });
    }
  };

  @Override
  public void finish() {
    ArrayList<ToroContact> resultList;
    if(adapter == null) {
      resultList = getOldList();
    } else {
      resultList = adapter.getSelectedContacts();
    }
    Intent intent = new Intent();
    Bundle bundle = new Bundle();
    bundle.putParcelableArrayList(PICK_CONTACT_EXTRA,resultList);
    intent.putExtras(bundle);
    setResult(RESULT_OK, intent);
    super.finish();
  }

  private ArrayList<ToroContact> getOldList() {
    return getIntent().getParcelableArrayListExtra(PICK_CONTACT_EXTRA);
  }

  private List<ToroContact> oldList;

  public static Intent createIntent(Context context, ArrayList<ToroContact> contacts) {
    Intent intent = new Intent();
    Bundle bundle = new Bundle();
    bundle.putParcelableArrayList(PICK_CONTACT_EXTRA, contacts);
    intent.setClass(context,ToroContactsPickActivity.class);
    intent.putExtras(bundle);
    return intent;
  }
}
