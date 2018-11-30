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
import android.net.Uri;
import android.provider.ContactsContract.QuickContact;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.android.dialer.R;
import com.android.dialer.common.Assert;
import com.android.dialer.logging.InteractionEvent;
import com.android.dialer.logging.Logger;

/** View holder for a contact. */
final class ToroContactViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

  private final TextView header;
  private final TextView name;
  private final QuickContactBadge photo;
  private final Context context;

  private String headerText;
  private Uri contactUri;
  private View rootView;

  private ImageView selectImg;

  public ToroContactViewHolder(View itemView) {
    super(itemView);
    context = itemView.getContext();
    rootView = itemView;
//    itemView.findViewById(com.android.dialer.contactsfragment.R.id.click_target).setOnClickListener(this);
    header = (TextView) itemView.findViewById(R.id.header);
    name = (TextView) itemView.findViewById(R.id.contact_name);
    photo = (QuickContactBadge) itemView.findViewById(R.id.photo);
    selectImg = itemView.findViewById(R.id.select_img);
    photo.setClickable(false);
  }

  /**
   * Binds the ViewHolder with relevant data.
   *
   * @param headerText populates the header view.
   * @param displayName populates the name view.
   * @param contactUri to be shown by the contact card on photo click.
   * @param showHeader if header view should be shown {@code True}, {@code False} otherwise.
   */
  public void bind(String headerText, String displayName, Uri contactUri, boolean showHeader) {
    Assert.checkArgument(!TextUtils.isEmpty(displayName));
    this.contactUri = contactUri;
    this.headerText = headerText;

    name.setText(displayName);
    header.setText(headerText);
    header.setVisibility(showHeader ? View.VISIBLE : View.INVISIBLE);

    Logger.get(context)
        .logQuickContactOnTouch(
            photo, InteractionEvent.Type.OPEN_QUICK_CONTACT_FROM_CONTACTS_FRAGMENT_BADGE, true);
  }

  public QuickContactBadge getPhoto() {
    return photo;
  }

  public String getHeader() {
    return headerText;
  }

  public TextView getHeaderView() {
    return header;
  }

  public void pudateSelectView(int position,boolean isSelected,OnClickListener selectListener) {
    rootView.setTag(position);
    rootView.setOnClickListener(selectListener);
    if(isSelected) {
      selectImg.setImageResource(R.drawable.toro_radio_selected);
    } else {
      selectImg.setImageResource(R.drawable.toro_radio_un_selected);
    }
  }

  @Override
  public void onClick(View v) {
    Logger.get(context)
        .logInteraction(InteractionEvent.Type.OPEN_QUICK_CONTACT_FROM_CONTACTS_FRAGMENT_ITEM);
    QuickContact.showQuickContact(
        photo.getContext(), photo, contactUri, QuickContact.MODE_LARGE, null /* excludeMimes */);
  }
}
