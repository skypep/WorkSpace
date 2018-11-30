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

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.annotation.IntDef;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.contacts.common.ContactPhotoManager;
import com.android.contacts.common.lettertiles.LetterTileDrawable;
import com.android.dialer.R;
import com.android.dialer.common.Assert;
import com.android.toro.src.ToroSearchContactViewHolder;
import com.android.toro.src.utils.ToroUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/** List adapter for the union of all contacts associated with every account on the device. */
final class ToroContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final int UNKNOWN_VIEW_TYPE = 0;
  private static final int ADD_CONTACT_VIEW_TYPE = 1;
  private static final int CONTACT_VIEW_TYPE = 2;

  /** An Enum for the different row view types shown by this adapter. */
  @Retention(RetentionPolicy.SOURCE)
  @IntDef({UNKNOWN_VIEW_TYPE, ADD_CONTACT_VIEW_TYPE, CONTACT_VIEW_TYPE})
  @interface ContactsViewType {}

  private final ArrayMap<ToroContactViewHolder, Integer> holderMap = new ArrayMap<>();
  private final Context context;
  private final Cursor cursor;

  // List of contact sublist headers
  private final String[] headers;

  // Number of contacts that correspond to each header in {@code headers}.
  private final int[] counts;

  private View.OnClickListener searchOnclickListener;
  private ArrayList<ToroContact> selectedContacts;

  ToroContactsAdapter(Context context, Cursor cursor,OnSelectChangeListener onSelectChangeListener,List<ToroContact> oldList) {
    this.context = context;
    this.cursor = cursor;
    this.onSelectChangeListener = onSelectChangeListener;
    headers = cursor.getExtras().getStringArray(Contacts.EXTRA_ADDRESS_BOOK_INDEX_TITLES);
    counts = cursor.getExtras().getIntArray(Contacts.EXTRA_ADDRESS_BOOK_INDEX_COUNTS);
    selectedContacts = new ArrayList<>();
    updateOldList(oldList);
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(
      ViewGroup parent, @ContactsViewType int viewType) {
    switch (viewType) {
      case ADD_CONTACT_VIEW_TYPE:
        return new ToroAddContactViewHolder(
            LayoutInflater.from(context).inflate(R.layout.add_contact_row, parent, false));
      case CONTACT_VIEW_TYPE:
        return new ToroContactViewHolder(
            LayoutInflater.from(context).inflate(R.layout.toro_contact_row, parent, false));
      case UNKNOWN_VIEW_TYPE:
      default:
        throw Assert.createIllegalStateFailException("Invalid view type: " + viewType);
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    if (viewHolder instanceof ToroAddContactViewHolder) {
      return;
    }
    if(viewHolder instanceof ToroSearchContactViewHolder) {
      if(searchOnclickListener != null) {
        ((ToroSearchContactViewHolder)viewHolder).setOnclicListener(searchOnclickListener);
      }
      return;
    }

    ToroContactViewHolder contactViewHolder = (ToroContactViewHolder) viewHolder;
    holderMap.put(contactViewHolder, position);
    // Cursor should be offset by 1 because of add contact row
    cursor.moveToPosition(position - 1);

    String name = getDisplayName(cursor);
    String header = getHeaderString(position);
    Uri contactUri = getContactUri(cursor);
    long contactID = cursor.getLong(ToroContactsCursorLoader.CONTACT_ID);

    ContactPhotoManager.getInstance(context)
        .loadDialerThumbnailOrPhoto(
            contactViewHolder.getPhoto(),
            contactUri,
            getPhotoId(cursor),
            getPhotoUri(cursor),
            name,
            LetterTileDrawable.TYPE_DEFAULT);

    String photoDescription =
        context.getString(com.android.dialer.R.string.description_quick_contact_for, name);// fixed by liujia com.android.contacts.common.R
    contactViewHolder.getPhoto().setContentDescription(photoDescription);

    // Always show the view holder's header if it's the first item in the list. Otherwise, compare
    // it to the previous element and only show the anchored header if the row elements fall into
    // the same sublists.
    boolean showHeader = position == 0 || !header.equals(getHeaderString(position - 1));
    contactViewHolder.bind(header, name, contactUri, showHeader);
    contactViewHolder.pudateSelectView(position,isSelected(contactID),selectListener);
  }

  public void setOnSearchClickListener(View.OnClickListener listener) {
    searchOnclickListener = listener;
  }

  @Override
  public @ContactsViewType int getItemViewType(int position) {
    return position == 0 ? ADD_CONTACT_VIEW_TYPE : CONTACT_VIEW_TYPE;
  }

  @Override
  public void onViewRecycled(RecyclerView.ViewHolder contactViewHolder) {
    super.onViewRecycled(contactViewHolder);
    if (contactViewHolder instanceof ToroContactViewHolder) {
      holderMap.remove(contactViewHolder);
    }
  }

  public void refreshHeaders() {
    for (ToroContactViewHolder holder : holderMap.keySet()) {
      int position = holderMap.get(holder);
      boolean showHeader =
          position == 0 || !getHeaderString(position).equals(getHeaderString(position - 1));
      int visibility = showHeader ? View.VISIBLE : View.INVISIBLE;
      holder.getHeaderView().setVisibility(visibility);
    }
  }

  @Override
  public int getItemCount() {
    return (cursor == null ? 0 : cursor.getCount()) + 1; // add contact
  }

  private static String getDisplayName(Cursor cursor) {
    return cursor.getString(ToroContactsCursorLoader.CONTACT_DISPLAY_NAME);
  }

  private static long getPhotoId(Cursor cursor) {
    return cursor.getLong(ToroContactsCursorLoader.CONTACT_PHOTO_ID);
  }

  private static Uri getPhotoUri(Cursor cursor) {
    String photoUri = cursor.getString(ToroContactsCursorLoader.CONTACT_PHOTO_URI);
    return photoUri == null ? null : Uri.parse(photoUri);
  }

  private static Uri getContactUri(Cursor cursor) {
    long contactId = cursor.getLong(ToroContactsCursorLoader.CONTACT_ID);
    String lookupKey = cursor.getString(ToroContactsCursorLoader.CONTACT_LOOKUP_KEY);
    return Contacts.getLookupUri(contactId, lookupKey);
  }

  public String getHeaderString(int position) {
    if (position == 0) {
      return "+";
    }
    position--;

    int index = -1;
    int sum = 0;
    while (sum <= position) {
      if(index == counts.length - 1){ //  liujia add this if  by sim卡存了号码时，进入电话 这里会超出out of index array
        sum += counts[--index];
      }else{
        sum += counts[++index];
      }

    }
    return headers[index];
  }

  public ToroContact getToroContact(Cursor cursor) {
    try{
      ToroContact contact = new ToroContact();
      List<String> phones = new ArrayList<>();
      long contactId = cursor.getLong(ToroContactsCursorLoader.CONTACT_ID);
//      int phoneCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
      Cursor phoneCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID+ "=" + contactId, null, null);
      if(phoneCursor != null) {
        if(phoneCursor.moveToFirst()){
          do{
            //遍历所有的联系人下面所有的电话号码
            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phones.add(ToroUtils.getToroPhoneNum(phoneNumber));
          }while(phoneCursor.moveToNext());
        }
      }
      contact.setContactID(contactId);
      contact.setNumber(phones);
      return contact;
    }catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public ToroContact getToroContact(int position){
    cursor.moveToPosition(position - 1);
    return getToroContact(cursor);
  }

  private View.OnClickListener selectListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      try{
        int position = (int) v.getTag();
        ToroContact contact = getToroContact(position);
        if(contact == null) {
          return;
        }
        if(isSelected(contact.getContactID())){
          removeSelected(contact.getContactID());
        } else {
          selectedContacts.add(contact);
        }
        notifyDataSetChanged();
      }catch (Exception e) {
      }
      if(onSelectChangeListener != null) {
        onSelectChangeListener.OnSelectChange(selectedContacts.size());
      }
    }
  };

  private boolean isSelected(long contactId) {
    try{
      for(ToroContact contact : selectedContacts) {
        if(contact.getContactID() == contactId) {
          return true;
        }
      }
    }catch (Exception e) {

    }
    return false;
  }

  private void removeSelected(long contactId){
    try{
      for(ToroContact contact : selectedContacts) {
        if(contact.getContactID() == contactId) {
          selectedContacts.remove(contact);
          break;
        }
      }
    }catch (Exception e) {

    }
  }

  private void updateOldList(List<ToroContact> oldList) {
      if(oldList == null || oldList.size() < 1) {
          return;
      }
    if(cursor.moveToFirst()){
      do{
        long contactID = cursor.getLong(ToroContactsCursorLoader.CONTACT_ID);
        for(ToroContact contact : oldList) {
          if(contact.getContactID() == contactID) {
            selectedContacts.add(getToroContact(cursor));
          }
        }
      }while(cursor.moveToNext());
    }
  }

  public ArrayList<ToroContact> getSelectedContacts() {
    return selectedContacts;
  }

  private OnSelectChangeListener onSelectChangeListener;

  public interface OnSelectChangeListener{
    public void OnSelectChange(int count);
  }

}
