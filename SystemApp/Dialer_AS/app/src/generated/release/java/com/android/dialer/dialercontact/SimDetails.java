// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: com/android/dialer/dialercontact/dialer_contact.proto

package com.android.dialer.dialercontact;

/**
 * Protobuf type {@code com.android.dialer.callcomposer.SimDetails}
 */
public  final class SimDetails extends
    com.google.protobuf.GeneratedMessageLite<
        SimDetails, SimDetails.Builder> implements
    // @@protoc_insertion_point(message_implements:com.android.dialer.callcomposer.SimDetails)
    SimDetailsOrBuilder {
  private SimDetails() {
    network_ = "";
  }
  private int bitField0_;
  public static final int NETWORK_FIELD_NUMBER = 1;
  private java.lang.String network_;
  /**
   * <pre>
   * Human readable netwrork name displayed to user where relevant
   * </pre>
   *
   * <code>optional string network = 1;</code>
   */
  public boolean hasNetwork() {
    return ((bitField0_ & 0x00000001) == 0x00000001);
  }
  /**
   * <pre>
   * Human readable netwrork name displayed to user where relevant
   * </pre>
   *
   * <code>optional string network = 1;</code>
   */
  public java.lang.String getNetwork() {
    return network_;
  }
  /**
   * <pre>
   * Human readable netwrork name displayed to user where relevant
   * </pre>
   *
   * <code>optional string network = 1;</code>
   */
  public com.google.protobuf.ByteString
      getNetworkBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(network_);
  }
  /**
   * <pre>
   * Human readable netwrork name displayed to user where relevant
   * </pre>
   *
   * <code>optional string network = 1;</code>
   */
  private void setNetwork(
      java.lang.String value) {
    if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
    network_ = value;
  }
  /**
   * <pre>
   * Human readable netwrork name displayed to user where relevant
   * </pre>
   *
   * <code>optional string network = 1;</code>
   */
  private void clearNetwork() {
    bitField0_ = (bitField0_ & ~0x00000001);
    network_ = getDefaultInstance().getNetwork();
  }
  /**
   * <pre>
   * Human readable netwrork name displayed to user where relevant
   * </pre>
   *
   * <code>optional string network = 1;</code>
   */
  private void setNetworkBytes(
      com.google.protobuf.ByteString value) {
    if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
    network_ = value.toStringUtf8();
  }

  public static final int COLOR_FIELD_NUMBER = 2;
  private int color_;
  /**
   * <pre>
   * This value represents a hex representation of a color (i.e. #ffffff)
   * </pre>
   *
   * <code>optional int32 color = 2;</code>
   */
  public boolean hasColor() {
    return ((bitField0_ & 0x00000002) == 0x00000002);
  }
  /**
   * <pre>
   * This value represents a hex representation of a color (i.e. #ffffff)
   * </pre>
   *
   * <code>optional int32 color = 2;</code>
   */
  public int getColor() {
    return color_;
  }
  /**
   * <pre>
   * This value represents a hex representation of a color (i.e. #ffffff)
   * </pre>
   *
   * <code>optional int32 color = 2;</code>
   */
  private void setColor(int value) {
    bitField0_ |= 0x00000002;
    color_ = value;
  }
  /**
   * <pre>
   * This value represents a hex representation of a color (i.e. #ffffff)
   * </pre>
   *
   * <code>optional int32 color = 2;</code>
   */
  private void clearColor() {
    bitField0_ = (bitField0_ & ~0x00000002);
    color_ = 0;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (((bitField0_ & 0x00000001) == 0x00000001)) {
      output.writeString(1, getNetwork());
    }
    if (((bitField0_ & 0x00000002) == 0x00000002)) {
      output.writeInt32(2, color_);
    }
    unknownFields.writeTo(output);
  }

  public int getSerializedSize() {
    int size = memoizedSerializedSize;
    if (size != -1) return size;

    size = 0;
    if (((bitField0_ & 0x00000001) == 0x00000001)) {
      size += com.google.protobuf.CodedOutputStream
        .computeStringSize(1, getNetwork());
    }
    if (((bitField0_ & 0x00000002) == 0x00000002)) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, color_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSerializedSize = size;
    return size;
  }

  public static com.android.dialer.dialercontact.SimDetails parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static com.android.dialer.dialercontact.SimDetails parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static com.android.dialer.dialercontact.SimDetails parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static com.android.dialer.dialercontact.SimDetails parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static com.android.dialer.dialercontact.SimDetails parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static com.android.dialer.dialercontact.SimDetails parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static com.android.dialer.dialercontact.SimDetails parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }
  public static com.android.dialer.dialercontact.SimDetails parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static com.android.dialer.dialercontact.SimDetails parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static com.android.dialer.dialercontact.SimDetails parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.android.dialer.dialercontact.SimDetails prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  /**
   * Protobuf type {@code com.android.dialer.callcomposer.SimDetails}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageLite.Builder<
        com.android.dialer.dialercontact.SimDetails, Builder> implements
      // @@protoc_insertion_point(builder_implements:com.android.dialer.callcomposer.SimDetails)
      com.android.dialer.dialercontact.SimDetailsOrBuilder {
    // Construct using com.android.dialer.dialercontact.SimDetails.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }


    /**
     * <pre>
     * Human readable netwrork name displayed to user where relevant
     * </pre>
     *
     * <code>optional string network = 1;</code>
     */
    public boolean hasNetwork() {
      return instance.hasNetwork();
    }
    /**
     * <pre>
     * Human readable netwrork name displayed to user where relevant
     * </pre>
     *
     * <code>optional string network = 1;</code>
     */
    public java.lang.String getNetwork() {
      return instance.getNetwork();
    }
    /**
     * <pre>
     * Human readable netwrork name displayed to user where relevant
     * </pre>
     *
     * <code>optional string network = 1;</code>
     */
    public com.google.protobuf.ByteString
        getNetworkBytes() {
      return instance.getNetworkBytes();
    }
    /**
     * <pre>
     * Human readable netwrork name displayed to user where relevant
     * </pre>
     *
     * <code>optional string network = 1;</code>
     */
    public Builder setNetwork(
        java.lang.String value) {
      copyOnWrite();
      instance.setNetwork(value);
      return this;
    }
    /**
     * <pre>
     * Human readable netwrork name displayed to user where relevant
     * </pre>
     *
     * <code>optional string network = 1;</code>
     */
    public Builder clearNetwork() {
      copyOnWrite();
      instance.clearNetwork();
      return this;
    }
    /**
     * <pre>
     * Human readable netwrork name displayed to user where relevant
     * </pre>
     *
     * <code>optional string network = 1;</code>
     */
    public Builder setNetworkBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setNetworkBytes(value);
      return this;
    }

    /**
     * <pre>
     * This value represents a hex representation of a color (i.e. #ffffff)
     * </pre>
     *
     * <code>optional int32 color = 2;</code>
     */
    public boolean hasColor() {
      return instance.hasColor();
    }
    /**
     * <pre>
     * This value represents a hex representation of a color (i.e. #ffffff)
     * </pre>
     *
     * <code>optional int32 color = 2;</code>
     */
    public int getColor() {
      return instance.getColor();
    }
    /**
     * <pre>
     * This value represents a hex representation of a color (i.e. #ffffff)
     * </pre>
     *
     * <code>optional int32 color = 2;</code>
     */
    public Builder setColor(int value) {
      copyOnWrite();
      instance.setColor(value);
      return this;
    }
    /**
     * <pre>
     * This value represents a hex representation of a color (i.e. #ffffff)
     * </pre>
     *
     * <code>optional int32 color = 2;</code>
     */
    public Builder clearColor() {
      copyOnWrite();
      instance.clearColor();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:com.android.dialer.callcomposer.SimDetails)
  }
  protected final Object dynamicMethod(
      com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
      Object arg0, Object arg1) {
    switch (method) {
      case NEW_MUTABLE_INSTANCE: {
        return new com.android.dialer.dialercontact.SimDetails();
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
        com.android.dialer.dialercontact.SimDetails other = (com.android.dialer.dialercontact.SimDetails) arg1;
        network_ = visitor.visitString(
            hasNetwork(), network_,
            other.hasNetwork(), other.network_);
        color_ = visitor.visitInt(
            hasColor(), color_,
            other.hasColor(), other.color_);
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
              case 10: {
                String s = input.readString();
                bitField0_ |= 0x00000001;
                network_ = s;
                break;
              }
              case 16: {
                bitField0_ |= 0x00000002;
                color_ = input.readInt32();
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
        if (PARSER == null) {    synchronized (com.android.dialer.dialercontact.SimDetails.class) {
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


  // @@protoc_insertion_point(class_scope:com.android.dialer.callcomposer.SimDetails)
  private static final com.android.dialer.dialercontact.SimDetails DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new SimDetails();
    DEFAULT_INSTANCE.makeImmutable();
  }

  public static com.android.dialer.dialercontact.SimDetails getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<SimDetails> PARSER;

  public static com.google.protobuf.Parser<SimDetails> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}

