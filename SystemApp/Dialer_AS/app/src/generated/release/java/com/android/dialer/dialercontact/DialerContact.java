// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: com/android/dialer/dialercontact/dialer_contact.proto

package com.android.dialer.dialercontact;

/**
 * Protobuf type {@code com.android.dialer.callcomposer.DialerContact}
 */
public  final class DialerContact extends
    com.google.protobuf.GeneratedMessageLite<
        DialerContact, DialerContact.Builder> implements
    // @@protoc_insertion_point(message_implements:com.android.dialer.callcomposer.DialerContact)
    DialerContactOrBuilder {
  private DialerContact() {
    photoUri_ = "";
    contactUri_ = "";
    nameOrNumber_ = "";
    number_ = "";
    displayNumber_ = "";
    numberLabel_ = "";
  }
  private int bitField0_;
  public static final int PHOTO_ID_FIELD_NUMBER = 1;
  private long photoId_;
  /**
   * <code>optional fixed64 photo_id = 1;</code>
   */
  public boolean hasPhotoId() {
    return ((bitField0_ & 0x00000001) == 0x00000001);
  }
  /**
   * <code>optional fixed64 photo_id = 1;</code>
   */
  public long getPhotoId() {
    return photoId_;
  }
  /**
   * <code>optional fixed64 photo_id = 1;</code>
   */
  private void setPhotoId(long value) {
    bitField0_ |= 0x00000001;
    photoId_ = value;
  }
  /**
   * <code>optional fixed64 photo_id = 1;</code>
   */
  private void clearPhotoId() {
    bitField0_ = (bitField0_ & ~0x00000001);
    photoId_ = 0L;
  }

  public static final int PHOTO_URI_FIELD_NUMBER = 2;
  private java.lang.String photoUri_;
  /**
   * <code>optional string photo_uri = 2;</code>
   */
  public boolean hasPhotoUri() {
    return ((bitField0_ & 0x00000002) == 0x00000002);
  }
  /**
   * <code>optional string photo_uri = 2;</code>
   */
  public java.lang.String getPhotoUri() {
    return photoUri_;
  }
  /**
   * <code>optional string photo_uri = 2;</code>
   */
  public com.google.protobuf.ByteString
      getPhotoUriBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(photoUri_);
  }
  /**
   * <code>optional string photo_uri = 2;</code>
   */
  private void setPhotoUri(
      java.lang.String value) {
    if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
    photoUri_ = value;
  }
  /**
   * <code>optional string photo_uri = 2;</code>
   */
  private void clearPhotoUri() {
    bitField0_ = (bitField0_ & ~0x00000002);
    photoUri_ = getDefaultInstance().getPhotoUri();
  }
  /**
   * <code>optional string photo_uri = 2;</code>
   */
  private void setPhotoUriBytes(
      com.google.protobuf.ByteString value) {
    if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
    photoUri_ = value.toStringUtf8();
  }

  public static final int CONTACT_URI_FIELD_NUMBER = 3;
  private java.lang.String contactUri_;
  /**
   * <code>optional string contact_uri = 3;</code>
   */
  public boolean hasContactUri() {
    return ((bitField0_ & 0x00000004) == 0x00000004);
  }
  /**
   * <code>optional string contact_uri = 3;</code>
   */
  public java.lang.String getContactUri() {
    return contactUri_;
  }
  /**
   * <code>optional string contact_uri = 3;</code>
   */
  public com.google.protobuf.ByteString
      getContactUriBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(contactUri_);
  }
  /**
   * <code>optional string contact_uri = 3;</code>
   */
  private void setContactUri(
      java.lang.String value) {
    if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000004;
    contactUri_ = value;
  }
  /**
   * <code>optional string contact_uri = 3;</code>
   */
  private void clearContactUri() {
    bitField0_ = (bitField0_ & ~0x00000004);
    contactUri_ = getDefaultInstance().getContactUri();
  }
  /**
   * <code>optional string contact_uri = 3;</code>
   */
  private void setContactUriBytes(
      com.google.protobuf.ByteString value) {
    if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000004;
    contactUri_ = value.toStringUtf8();
  }

  public static final int NAME_OR_NUMBER_FIELD_NUMBER = 4;
  private java.lang.String nameOrNumber_;
  /**
   * <code>optional string name_or_number = 4;</code>
   */
  public boolean hasNameOrNumber() {
    return ((bitField0_ & 0x00000008) == 0x00000008);
  }
  /**
   * <code>optional string name_or_number = 4;</code>
   */
  public java.lang.String getNameOrNumber() {
    return nameOrNumber_;
  }
  /**
   * <code>optional string name_or_number = 4;</code>
   */
  public com.google.protobuf.ByteString
      getNameOrNumberBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(nameOrNumber_);
  }
  /**
   * <code>optional string name_or_number = 4;</code>
   */
  private void setNameOrNumber(
      java.lang.String value) {
    if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000008;
    nameOrNumber_ = value;
  }
  /**
   * <code>optional string name_or_number = 4;</code>
   */
  private void clearNameOrNumber() {
    bitField0_ = (bitField0_ & ~0x00000008);
    nameOrNumber_ = getDefaultInstance().getNameOrNumber();
  }
  /**
   * <code>optional string name_or_number = 4;</code>
   */
  private void setNameOrNumberBytes(
      com.google.protobuf.ByteString value) {
    if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000008;
    nameOrNumber_ = value.toStringUtf8();
  }

  public static final int NUMBER_FIELD_NUMBER = 6;
  private java.lang.String number_;
  /**
   * <code>optional string number = 6;</code>
   */
  public boolean hasNumber() {
    return ((bitField0_ & 0x00000010) == 0x00000010);
  }
  /**
   * <code>optional string number = 6;</code>
   */
  public java.lang.String getNumber() {
    return number_;
  }
  /**
   * <code>optional string number = 6;</code>
   */
  public com.google.protobuf.ByteString
      getNumberBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(number_);
  }
  /**
   * <code>optional string number = 6;</code>
   */
  private void setNumber(
      java.lang.String value) {
    if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000010;
    number_ = value;
  }
  /**
   * <code>optional string number = 6;</code>
   */
  private void clearNumber() {
    bitField0_ = (bitField0_ & ~0x00000010);
    number_ = getDefaultInstance().getNumber();
  }
  /**
   * <code>optional string number = 6;</code>
   */
  private void setNumberBytes(
      com.google.protobuf.ByteString value) {
    if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000010;
    number_ = value.toStringUtf8();
  }

  public static final int DISPLAY_NUMBER_FIELD_NUMBER = 7;
  private java.lang.String displayNumber_;
  /**
   * <code>optional string display_number = 7;</code>
   */
  public boolean hasDisplayNumber() {
    return ((bitField0_ & 0x00000020) == 0x00000020);
  }
  /**
   * <code>optional string display_number = 7;</code>
   */
  public java.lang.String getDisplayNumber() {
    return displayNumber_;
  }
  /**
   * <code>optional string display_number = 7;</code>
   */
  public com.google.protobuf.ByteString
      getDisplayNumberBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(displayNumber_);
  }
  /**
   * <code>optional string display_number = 7;</code>
   */
  private void setDisplayNumber(
      java.lang.String value) {
    if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000020;
    displayNumber_ = value;
  }
  /**
   * <code>optional string display_number = 7;</code>
   */
  private void clearDisplayNumber() {
    bitField0_ = (bitField0_ & ~0x00000020);
    displayNumber_ = getDefaultInstance().getDisplayNumber();
  }
  /**
   * <code>optional string display_number = 7;</code>
   */
  private void setDisplayNumberBytes(
      com.google.protobuf.ByteString value) {
    if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000020;
    displayNumber_ = value.toStringUtf8();
  }

  public static final int NUMBER_LABEL_FIELD_NUMBER = 8;
  private java.lang.String numberLabel_;
  /**
   * <code>optional string number_label = 8;</code>
   */
  public boolean hasNumberLabel() {
    return ((bitField0_ & 0x00000040) == 0x00000040);
  }
  /**
   * <code>optional string number_label = 8;</code>
   */
  public java.lang.String getNumberLabel() {
    return numberLabel_;
  }
  /**
   * <code>optional string number_label = 8;</code>
   */
  public com.google.protobuf.ByteString
      getNumberLabelBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(numberLabel_);
  }
  /**
   * <code>optional string number_label = 8;</code>
   */
  private void setNumberLabel(
      java.lang.String value) {
    if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000040;
    numberLabel_ = value;
  }
  /**
   * <code>optional string number_label = 8;</code>
   */
  private void clearNumberLabel() {
    bitField0_ = (bitField0_ & ~0x00000040);
    numberLabel_ = getDefaultInstance().getNumberLabel();
  }
  /**
   * <code>optional string number_label = 8;</code>
   */
  private void setNumberLabelBytes(
      com.google.protobuf.ByteString value) {
    if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000040;
    numberLabel_ = value.toStringUtf8();
  }

  public static final int CONTACT_TYPE_FIELD_NUMBER = 9;
  private int contactType_;
  /**
   * <code>optional int32 contact_type = 9;</code>
   */
  public boolean hasContactType() {
    return ((bitField0_ & 0x00000080) == 0x00000080);
  }
  /**
   * <code>optional int32 contact_type = 9;</code>
   */
  public int getContactType() {
    return contactType_;
  }
  /**
   * <code>optional int32 contact_type = 9;</code>
   */
  private void setContactType(int value) {
    bitField0_ |= 0x00000080;
    contactType_ = value;
  }
  /**
   * <code>optional int32 contact_type = 9;</code>
   */
  private void clearContactType() {
    bitField0_ = (bitField0_ & ~0x00000080);
    contactType_ = 0;
  }

  public static final int SIM_DETAILS_FIELD_NUMBER = 10;
  private com.android.dialer.dialercontact.SimDetails simDetails_;
  /**
   * <code>optional .com.android.dialer.callcomposer.SimDetails sim_details = 10;</code>
   */
  public boolean hasSimDetails() {
    return ((bitField0_ & 0x00000100) == 0x00000100);
  }
  /**
   * <code>optional .com.android.dialer.callcomposer.SimDetails sim_details = 10;</code>
   */
  public com.android.dialer.dialercontact.SimDetails getSimDetails() {
    return simDetails_ == null ? com.android.dialer.dialercontact.SimDetails.getDefaultInstance() : simDetails_;
  }
  /**
   * <code>optional .com.android.dialer.callcomposer.SimDetails sim_details = 10;</code>
   */
  private void setSimDetails(com.android.dialer.dialercontact.SimDetails value) {
    if (value == null) {
      throw new NullPointerException();
    }
    simDetails_ = value;
    bitField0_ |= 0x00000100;
    }
  /**
   * <code>optional .com.android.dialer.callcomposer.SimDetails sim_details = 10;</code>
   */
  private void setSimDetails(
      com.android.dialer.dialercontact.SimDetails.Builder builderForValue) {
    simDetails_ = builderForValue.build();
    bitField0_ |= 0x00000100;
  }
  /**
   * <code>optional .com.android.dialer.callcomposer.SimDetails sim_details = 10;</code>
   */
  private void mergeSimDetails(com.android.dialer.dialercontact.SimDetails value) {
    if (simDetails_ != null &&
        simDetails_ != com.android.dialer.dialercontact.SimDetails.getDefaultInstance()) {
      simDetails_ =
        com.android.dialer.dialercontact.SimDetails.newBuilder(simDetails_).mergeFrom(value).buildPartial();
    } else {
      simDetails_ = value;
    }
    bitField0_ |= 0x00000100;
  }
  /**
   * <code>optional .com.android.dialer.callcomposer.SimDetails sim_details = 10;</code>
   */
  private void clearSimDetails() {  simDetails_ = null;
    bitField0_ = (bitField0_ & ~0x00000100);
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (((bitField0_ & 0x00000001) == 0x00000001)) {
      output.writeFixed64(1, photoId_);
    }
    if (((bitField0_ & 0x00000002) == 0x00000002)) {
      output.writeString(2, getPhotoUri());
    }
    if (((bitField0_ & 0x00000004) == 0x00000004)) {
      output.writeString(3, getContactUri());
    }
    if (((bitField0_ & 0x00000008) == 0x00000008)) {
      output.writeString(4, getNameOrNumber());
    }
    if (((bitField0_ & 0x00000010) == 0x00000010)) {
      output.writeString(6, getNumber());
    }
    if (((bitField0_ & 0x00000020) == 0x00000020)) {
      output.writeString(7, getDisplayNumber());
    }
    if (((bitField0_ & 0x00000040) == 0x00000040)) {
      output.writeString(8, getNumberLabel());
    }
    if (((bitField0_ & 0x00000080) == 0x00000080)) {
      output.writeInt32(9, contactType_);
    }
    if (((bitField0_ & 0x00000100) == 0x00000100)) {
      output.writeMessage(10, getSimDetails());
    }
    unknownFields.writeTo(output);
  }

  public int getSerializedSize() {
    int size = memoizedSerializedSize;
    if (size != -1) return size;

    size = 0;
    if (((bitField0_ & 0x00000001) == 0x00000001)) {
      size += com.google.protobuf.CodedOutputStream
        .computeFixed64Size(1, photoId_);
    }
    if (((bitField0_ & 0x00000002) == 0x00000002)) {
      size += com.google.protobuf.CodedOutputStream
        .computeStringSize(2, getPhotoUri());
    }
    if (((bitField0_ & 0x00000004) == 0x00000004)) {
      size += com.google.protobuf.CodedOutputStream
        .computeStringSize(3, getContactUri());
    }
    if (((bitField0_ & 0x00000008) == 0x00000008)) {
      size += com.google.protobuf.CodedOutputStream
        .computeStringSize(4, getNameOrNumber());
    }
    if (((bitField0_ & 0x00000010) == 0x00000010)) {
      size += com.google.protobuf.CodedOutputStream
        .computeStringSize(6, getNumber());
    }
    if (((bitField0_ & 0x00000020) == 0x00000020)) {
      size += com.google.protobuf.CodedOutputStream
        .computeStringSize(7, getDisplayNumber());
    }
    if (((bitField0_ & 0x00000040) == 0x00000040)) {
      size += com.google.protobuf.CodedOutputStream
        .computeStringSize(8, getNumberLabel());
    }
    if (((bitField0_ & 0x00000080) == 0x00000080)) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(9, contactType_);
    }
    if (((bitField0_ & 0x00000100) == 0x00000100)) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(10, getSimDetails());
    }
    size += unknownFields.getSerializedSize();
    memoizedSerializedSize = size;
    return size;
  }

  public static com.android.dialer.dialercontact.DialerContact parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static com.android.dialer.dialercontact.DialerContact parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static com.android.dialer.dialercontact.DialerContact parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static com.android.dialer.dialercontact.DialerContact parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static com.android.dialer.dialercontact.DialerContact parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static com.android.dialer.dialercontact.DialerContact parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static com.android.dialer.dialercontact.DialerContact parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }
  public static com.android.dialer.dialercontact.DialerContact parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static com.android.dialer.dialercontact.DialerContact parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static com.android.dialer.dialercontact.DialerContact parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.android.dialer.dialercontact.DialerContact prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  /**
   * Protobuf type {@code com.android.dialer.callcomposer.DialerContact}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageLite.Builder<
        com.android.dialer.dialercontact.DialerContact, Builder> implements
      // @@protoc_insertion_point(builder_implements:com.android.dialer.callcomposer.DialerContact)
      com.android.dialer.dialercontact.DialerContactOrBuilder {
    // Construct using com.android.dialer.dialercontact.DialerContact.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }


    /**
     * <code>optional fixed64 photo_id = 1;</code>
     */
    public boolean hasPhotoId() {
      return instance.hasPhotoId();
    }
    /**
     * <code>optional fixed64 photo_id = 1;</code>
     */
    public long getPhotoId() {
      return instance.getPhotoId();
    }
    /**
     * <code>optional fixed64 photo_id = 1;</code>
     */
    public Builder setPhotoId(long value) {
      copyOnWrite();
      instance.setPhotoId(value);
      return this;
    }
    /**
     * <code>optional fixed64 photo_id = 1;</code>
     */
    public Builder clearPhotoId() {
      copyOnWrite();
      instance.clearPhotoId();
      return this;
    }

    /**
     * <code>optional string photo_uri = 2;</code>
     */
    public boolean hasPhotoUri() {
      return instance.hasPhotoUri();
    }
    /**
     * <code>optional string photo_uri = 2;</code>
     */
    public java.lang.String getPhotoUri() {
      return instance.getPhotoUri();
    }
    /**
     * <code>optional string photo_uri = 2;</code>
     */
    public com.google.protobuf.ByteString
        getPhotoUriBytes() {
      return instance.getPhotoUriBytes();
    }
    /**
     * <code>optional string photo_uri = 2;</code>
     */
    public Builder setPhotoUri(
        java.lang.String value) {
      copyOnWrite();
      instance.setPhotoUri(value);
      return this;
    }
    /**
     * <code>optional string photo_uri = 2;</code>
     */
    public Builder clearPhotoUri() {
      copyOnWrite();
      instance.clearPhotoUri();
      return this;
    }
    /**
     * <code>optional string photo_uri = 2;</code>
     */
    public Builder setPhotoUriBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setPhotoUriBytes(value);
      return this;
    }

    /**
     * <code>optional string contact_uri = 3;</code>
     */
    public boolean hasContactUri() {
      return instance.hasContactUri();
    }
    /**
     * <code>optional string contact_uri = 3;</code>
     */
    public java.lang.String getContactUri() {
      return instance.getContactUri();
    }
    /**
     * <code>optional string contact_uri = 3;</code>
     */
    public com.google.protobuf.ByteString
        getContactUriBytes() {
      return instance.getContactUriBytes();
    }
    /**
     * <code>optional string contact_uri = 3;</code>
     */
    public Builder setContactUri(
        java.lang.String value) {
      copyOnWrite();
      instance.setContactUri(value);
      return this;
    }
    /**
     * <code>optional string contact_uri = 3;</code>
     */
    public Builder clearContactUri() {
      copyOnWrite();
      instance.clearContactUri();
      return this;
    }
    /**
     * <code>optional string contact_uri = 3;</code>
     */
    public Builder setContactUriBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setContactUriBytes(value);
      return this;
    }

    /**
     * <code>optional string name_or_number = 4;</code>
     */
    public boolean hasNameOrNumber() {
      return instance.hasNameOrNumber();
    }
    /**
     * <code>optional string name_or_number = 4;</code>
     */
    public java.lang.String getNameOrNumber() {
      return instance.getNameOrNumber();
    }
    /**
     * <code>optional string name_or_number = 4;</code>
     */
    public com.google.protobuf.ByteString
        getNameOrNumberBytes() {
      return instance.getNameOrNumberBytes();
    }
    /**
     * <code>optional string name_or_number = 4;</code>
     */
    public Builder setNameOrNumber(
        java.lang.String value) {
      copyOnWrite();
      instance.setNameOrNumber(value);
      return this;
    }
    /**
     * <code>optional string name_or_number = 4;</code>
     */
    public Builder clearNameOrNumber() {
      copyOnWrite();
      instance.clearNameOrNumber();
      return this;
    }
    /**
     * <code>optional string name_or_number = 4;</code>
     */
    public Builder setNameOrNumberBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setNameOrNumberBytes(value);
      return this;
    }

    /**
     * <code>optional string number = 6;</code>
     */
    public boolean hasNumber() {
      return instance.hasNumber();
    }
    /**
     * <code>optional string number = 6;</code>
     */
    public java.lang.String getNumber() {
      return instance.getNumber();
    }
    /**
     * <code>optional string number = 6;</code>
     */
    public com.google.protobuf.ByteString
        getNumberBytes() {
      return instance.getNumberBytes();
    }
    /**
     * <code>optional string number = 6;</code>
     */
    public Builder setNumber(
        java.lang.String value) {
      copyOnWrite();
      instance.setNumber(value);
      return this;
    }
    /**
     * <code>optional string number = 6;</code>
     */
    public Builder clearNumber() {
      copyOnWrite();
      instance.clearNumber();
      return this;
    }
    /**
     * <code>optional string number = 6;</code>
     */
    public Builder setNumberBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setNumberBytes(value);
      return this;
    }

    /**
     * <code>optional string display_number = 7;</code>
     */
    public boolean hasDisplayNumber() {
      return instance.hasDisplayNumber();
    }
    /**
     * <code>optional string display_number = 7;</code>
     */
    public java.lang.String getDisplayNumber() {
      return instance.getDisplayNumber();
    }
    /**
     * <code>optional string display_number = 7;</code>
     */
    public com.google.protobuf.ByteString
        getDisplayNumberBytes() {
      return instance.getDisplayNumberBytes();
    }
    /**
     * <code>optional string display_number = 7;</code>
     */
    public Builder setDisplayNumber(
        java.lang.String value) {
      copyOnWrite();
      instance.setDisplayNumber(value);
      return this;
    }
    /**
     * <code>optional string display_number = 7;</code>
     */
    public Builder clearDisplayNumber() {
      copyOnWrite();
      instance.clearDisplayNumber();
      return this;
    }
    /**
     * <code>optional string display_number = 7;</code>
     */
    public Builder setDisplayNumberBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setDisplayNumberBytes(value);
      return this;
    }

    /**
     * <code>optional string number_label = 8;</code>
     */
    public boolean hasNumberLabel() {
      return instance.hasNumberLabel();
    }
    /**
     * <code>optional string number_label = 8;</code>
     */
    public java.lang.String getNumberLabel() {
      return instance.getNumberLabel();
    }
    /**
     * <code>optional string number_label = 8;</code>
     */
    public com.google.protobuf.ByteString
        getNumberLabelBytes() {
      return instance.getNumberLabelBytes();
    }
    /**
     * <code>optional string number_label = 8;</code>
     */
    public Builder setNumberLabel(
        java.lang.String value) {
      copyOnWrite();
      instance.setNumberLabel(value);
      return this;
    }
    /**
     * <code>optional string number_label = 8;</code>
     */
    public Builder clearNumberLabel() {
      copyOnWrite();
      instance.clearNumberLabel();
      return this;
    }
    /**
     * <code>optional string number_label = 8;</code>
     */
    public Builder setNumberLabelBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setNumberLabelBytes(value);
      return this;
    }

    /**
     * <code>optional int32 contact_type = 9;</code>
     */
    public boolean hasContactType() {
      return instance.hasContactType();
    }
    /**
     * <code>optional int32 contact_type = 9;</code>
     */
    public int getContactType() {
      return instance.getContactType();
    }
    /**
     * <code>optional int32 contact_type = 9;</code>
     */
    public Builder setContactType(int value) {
      copyOnWrite();
      instance.setContactType(value);
      return this;
    }
    /**
     * <code>optional int32 contact_type = 9;</code>
     */
    public Builder clearContactType() {
      copyOnWrite();
      instance.clearContactType();
      return this;
    }

    /**
     * <code>optional .com.android.dialer.callcomposer.SimDetails sim_details = 10;</code>
     */
    public boolean hasSimDetails() {
      return instance.hasSimDetails();
    }
    /**
     * <code>optional .com.android.dialer.callcomposer.SimDetails sim_details = 10;</code>
     */
    public com.android.dialer.dialercontact.SimDetails getSimDetails() {
      return instance.getSimDetails();
    }
    /**
     * <code>optional .com.android.dialer.callcomposer.SimDetails sim_details = 10;</code>
     */
    public Builder setSimDetails(com.android.dialer.dialercontact.SimDetails value) {
      copyOnWrite();
      instance.setSimDetails(value);
      return this;
      }
    /**
     * <code>optional .com.android.dialer.callcomposer.SimDetails sim_details = 10;</code>
     */
    public Builder setSimDetails(
        com.android.dialer.dialercontact.SimDetails.Builder builderForValue) {
      copyOnWrite();
      instance.setSimDetails(builderForValue);
      return this;
    }
    /**
     * <code>optional .com.android.dialer.callcomposer.SimDetails sim_details = 10;</code>
     */
    public Builder mergeSimDetails(com.android.dialer.dialercontact.SimDetails value) {
      copyOnWrite();
      instance.mergeSimDetails(value);
      return this;
    }
    /**
     * <code>optional .com.android.dialer.callcomposer.SimDetails sim_details = 10;</code>
     */
    public Builder clearSimDetails() {  copyOnWrite();
      instance.clearSimDetails();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:com.android.dialer.callcomposer.DialerContact)
  }
  protected final Object dynamicMethod(
      com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
      Object arg0, Object arg1) {
    switch (method) {
      case NEW_MUTABLE_INSTANCE: {
        return new com.android.dialer.dialercontact.DialerContact();
      }
      case IS_INITIALIZED: {
        return DEFAULT_INSTANCE;
      }
      case MAKE_IMMUTABLE: {
        return null;
      }
      case NEW_BUILDER: {
        return new Builder();
      }
      case VISIT: {
        Visitor visitor = (Visitor) arg0;
        com.android.dialer.dialercontact.DialerContact other = (com.android.dialer.dialercontact.DialerContact) arg1;
        photoId_ = visitor.visitLong(
            hasPhotoId(), photoId_,
            other.hasPhotoId(), other.photoId_);
        photoUri_ = visitor.visitString(
            hasPhotoUri(), photoUri_,
            other.hasPhotoUri(), other.photoUri_);
        contactUri_ = visitor.visitString(
            hasContactUri(), contactUri_,
            other.hasContactUri(), other.contactUri_);
        nameOrNumber_ = visitor.visitString(
            hasNameOrNumber(), nameOrNumber_,
            other.hasNameOrNumber(), other.nameOrNumber_);
        number_ = visitor.visitString(
            hasNumber(), number_,
            other.hasNumber(), other.number_);
        displayNumber_ = visitor.visitString(
            hasDisplayNumber(), displayNumber_,
            other.hasDisplayNumber(), other.displayNumber_);
        numberLabel_ = visitor.visitString(
            hasNumberLabel(), numberLabel_,
            other.hasNumberLabel(), other.numberLabel_);
        contactType_ = visitor.visitInt(
            hasContactType(), contactType_,
            other.hasContactType(), other.contactType_);
        simDetails_ = visitor.visitMessage(simDetails_, other.simDetails_);
        if (visitor == com.google.protobuf.GeneratedMessageLite.MergeFromVisitor
            .INSTANCE) {
          bitField0_ |= other.bitField0_;
        }
        return this;
      }
      case MERGE_FROM_STREAM: {
        com.google.protobuf.CodedInputStream input =
            (com.google.protobuf.CodedInputStream) arg0;
        com.google.protobuf.ExtensionRegistryLite extensionRegistry =
            (com.google.protobuf.ExtensionRegistryLite) arg1;
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              default: {
                if (!parseUnknownField(tag, input)) {
                  done = true;
                }
                break;
              }
              case 9: {
                bitField0_ |= 0x00000001;
                photoId_ = input.readFixed64();
                break;
              }
              case 18: {
                String s = input.readString();
                bitField0_ |= 0x00000002;
                photoUri_ = s;
                break;
              }
              case 26: {
                String s = input.readString();
                bitField0_ |= 0x00000004;
                contactUri_ = s;
                break;
              }
              case 34: {
                String s = input.readString();
                bitField0_ |= 0x00000008;
                nameOrNumber_ = s;
                break;
              }
              case 50: {
                String s = input.readString();
                bitField0_ |= 0x00000010;
                number_ = s;
                break;
              }
              case 58: {
                String s = input.readString();
                bitField0_ |= 0x00000020;
                displayNumber_ = s;
                break;
              }
              case 66: {
                String s = input.readString();
                bitField0_ |= 0x00000040;
                numberLabel_ = s;
                break;
              }
              case 72: {
                bitField0_ |= 0x00000080;
                contactType_ = input.readInt32();
                break;
              }
              case 82: {
                com.android.dialer.dialercontact.SimDetails.Builder subBuilder = null;
                if (((bitField0_ & 0x00000100) == 0x00000100)) {
                  subBuilder = simDetails_.toBuilder();
                }
                simDetails_ = input.readMessage(com.android.dialer.dialercontact.SimDetails.parser(), extensionRegistry);
                if (subBuilder != null) {
                  subBuilder.mergeFrom(simDetails_);
                  simDetails_ = subBuilder.buildPartial();
                }
                bitField0_ |= 0x00000100;
                break;
              }
            }
          }
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw new RuntimeException(e.setUnfinishedMessage(this));
        } catch (java.io.IOException e) {
          throw new RuntimeException(
              new com.google.protobuf.InvalidProtocolBufferException(
                  e.getMessage()).setUnfinishedMessage(this));
        } finally {
        }
      }
      case GET_DEFAULT_INSTANCE: {
        return DEFAULT_INSTANCE;
      }
      case GET_PARSER: {
        if (PARSER == null) {    synchronized (com.android.dialer.dialercontact.DialerContact.class) {
            if (PARSER == null) {
              PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
            }
          }
        }
        return PARSER;
      }
    }
    throw new UnsupportedOperationException();
  }


  // @@protoc_insertion_point(class_scope:com.android.dialer.callcomposer.DialerContact)
  private static final com.android.dialer.dialercontact.DialerContact DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new DialerContact();
    DEFAULT_INSTANCE.makeImmutable();
  }

  public static com.android.dialer.dialercontact.DialerContact getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<DialerContact> PARSER;

  public static com.google.protobuf.Parser<DialerContact> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}

