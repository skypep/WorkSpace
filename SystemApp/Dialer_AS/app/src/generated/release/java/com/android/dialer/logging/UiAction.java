// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: com/android/dialer/logging/ui_action.proto

package com.android.dialer.logging;

/**
 * Protobuf type {@code com.android.dialer.logging.UiAction}
 */
public  final class UiAction extends
    com.google.protobuf.GeneratedMessageLite<
        UiAction, UiAction.Builder> implements
    // @@protoc_insertion_point(message_implements:com.android.dialer.logging.UiAction)
    UiActionOrBuilder {
  private UiAction() {
  }
  /**
   * Protobuf enum {@code com.android.dialer.logging.UiAction.Type}
   */
  public enum Type
      implements com.google.protobuf.Internal.EnumLite {
    /**
     * <code>UNKNOWN = 0;</code>
     */
    UNKNOWN(0),
    /**
     * <pre>
     * General action starting from 1
     * </pre>
     *
     * <code>CHANGE_TAB_TO_FAVORITE = 1;</code>
     */
    CHANGE_TAB_TO_FAVORITE(1),
    /**
     * <code>CHANGE_TAB_TO_CALL_LOG = 2;</code>
     */
    CHANGE_TAB_TO_CALL_LOG(2),
    /**
     * <code>CHANGE_TAB_TO_CONTACTS = 3;</code>
     */
    CHANGE_TAB_TO_CONTACTS(3),
    /**
     * <code>CHANGE_TAB_TO_VOICEMAIL = 4;</code>
     */
    CHANGE_TAB_TO_VOICEMAIL(4),
    /**
     * <code>PRESS_ANDROID_BACK_BUTTON = 5;</code>
     */
    PRESS_ANDROID_BACK_BUTTON(5),
    /**
     * <code>TEXT_CHANGE_WITH_INPUT = 6;</code>
     */
    TEXT_CHANGE_WITH_INPUT(6),
    /**
     * <code>SCROLL = 7;</code>
     */
    SCROLL(7),
    /**
     * <pre>
     * In call log, starting from 100
     * </pre>
     *
     * <code>CLICK_CALL_LOG_ITEM = 100;</code>
     */
    CLICK_CALL_LOG_ITEM(100),
    /**
     * <code>OPEN_CALL_DETAIL = 101;</code>
     */
    OPEN_CALL_DETAIL(101),
    /**
     * <code>CLOSE_CALL_DETAIL_WITH_CANCEL_BUTTON = 102;</code>
     */
    CLOSE_CALL_DETAIL_WITH_CANCEL_BUTTON(102),
    /**
     * <code>COPY_NUMBER_IN_CALL_DETAIL = 103;</code>
     */
    COPY_NUMBER_IN_CALL_DETAIL(103),
    /**
     * <code>EDIT_NUMBER_BEFORE_CALL_IN_CALL_DETAIL = 104;</code>
     */
    EDIT_NUMBER_BEFORE_CALL_IN_CALL_DETAIL(104),
    /**
     * <pre>
     * In dialpad, starting from 200
     * </pre>
     *
     * <code>OPEN_DIALPAD = 200;</code>
     */
    OPEN_DIALPAD(200),
    /**
     * <code>CLOSE_DIALPAD = 201;</code>
     */
    CLOSE_DIALPAD(201),
    /**
     * <code>PRESS_CALL_BUTTON_WITHOUT_CALLING = 202;</code>
     */
    PRESS_CALL_BUTTON_WITHOUT_CALLING(202),
    /**
     * <pre>
     * In search, starting from 300
     * </pre>
     *
     * <code>OPEN_SEARCH = 300;</code>
     */
    OPEN_SEARCH(300),
    /**
     * <code>HIDE_KEYBOARD_IN_SEARCH = 301;</code>
     */
    HIDE_KEYBOARD_IN_SEARCH(301),
    /**
     * <code>CLOSE_SEARCH_WITH_HIDE_BUTTON = 302;</code>
     */
    CLOSE_SEARCH_WITH_HIDE_BUTTON(302),
    /**
     * <pre>
     * In call history, starting from 400
     * </pre>
     *
     * <code>OPEN_CALL_HISTORY = 400;</code>
     */
    OPEN_CALL_HISTORY(400),
    /**
     * <code>CLOSE_CALL_HISTORY_WITH_CANCEL_BUTTON = 401;</code>
     */
    CLOSE_CALL_HISTORY_WITH_CANCEL_BUTTON(401),
    ;

    /**
     * <code>UNKNOWN = 0;</code>
     */
    public static final int UNKNOWN_VALUE = 0;
    /**
     * <pre>
     * General action starting from 1
     * </pre>
     *
     * <code>CHANGE_TAB_TO_FAVORITE = 1;</code>
     */
    public static final int CHANGE_TAB_TO_FAVORITE_VALUE = 1;
    /**
     * <code>CHANGE_TAB_TO_CALL_LOG = 2;</code>
     */
    public static final int CHANGE_TAB_TO_CALL_LOG_VALUE = 2;
    /**
     * <code>CHANGE_TAB_TO_CONTACTS = 3;</code>
     */
    public static final int CHANGE_TAB_TO_CONTACTS_VALUE = 3;
    /**
     * <code>CHANGE_TAB_TO_VOICEMAIL = 4;</code>
     */
    public static final int CHANGE_TAB_TO_VOICEMAIL_VALUE = 4;
    /**
     * <code>PRESS_ANDROID_BACK_BUTTON = 5;</code>
     */
    public static final int PRESS_ANDROID_BACK_BUTTON_VALUE = 5;
    /**
     * <code>TEXT_CHANGE_WITH_INPUT = 6;</code>
     */
    public static final int TEXT_CHANGE_WITH_INPUT_VALUE = 6;
    /**
     * <code>SCROLL = 7;</code>
     */
    public static final int SCROLL_VALUE = 7;
    /**
     * <pre>
     * In call log, starting from 100
     * </pre>
     *
     * <code>CLICK_CALL_LOG_ITEM = 100;</code>
     */
    public static final int CLICK_CALL_LOG_ITEM_VALUE = 100;
    /**
     * <code>OPEN_CALL_DETAIL = 101;</code>
     */
    public static final int OPEN_CALL_DETAIL_VALUE = 101;
    /**
     * <code>CLOSE_CALL_DETAIL_WITH_CANCEL_BUTTON = 102;</code>
     */
    public static final int CLOSE_CALL_DETAIL_WITH_CANCEL_BUTTON_VALUE = 102;
    /**
     * <code>COPY_NUMBER_IN_CALL_DETAIL = 103;</code>
     */
    public static final int COPY_NUMBER_IN_CALL_DETAIL_VALUE = 103;
    /**
     * <code>EDIT_NUMBER_BEFORE_CALL_IN_CALL_DETAIL = 104;</code>
     */
    public static final int EDIT_NUMBER_BEFORE_CALL_IN_CALL_DETAIL_VALUE = 104;
    /**
     * <pre>
     * In dialpad, starting from 200
     * </pre>
     *
     * <code>OPEN_DIALPAD = 200;</code>
     */
    public static final int OPEN_DIALPAD_VALUE = 200;
    /**
     * <code>CLOSE_DIALPAD = 201;</code>
     */
    public static final int CLOSE_DIALPAD_VALUE = 201;
    /**
     * <code>PRESS_CALL_BUTTON_WITHOUT_CALLING = 202;</code>
     */
    public static final int PRESS_CALL_BUTTON_WITHOUT_CALLING_VALUE = 202;
    /**
     * <pre>
     * In search, starting from 300
     * </pre>
     *
     * <code>OPEN_SEARCH = 300;</code>
     */
    public static final int OPEN_SEARCH_VALUE = 300;
    /**
     * <code>HIDE_KEYBOARD_IN_SEARCH = 301;</code>
     */
    public static final int HIDE_KEYBOARD_IN_SEARCH_VALUE = 301;
    /**
     * <code>CLOSE_SEARCH_WITH_HIDE_BUTTON = 302;</code>
     */
    public static final int CLOSE_SEARCH_WITH_HIDE_BUTTON_VALUE = 302;
    /**
     * <pre>
     * In call history, starting from 400
     * </pre>
     *
     * <code>OPEN_CALL_HISTORY = 400;</code>
     */
    public static final int OPEN_CALL_HISTORY_VALUE = 400;
    /**
     * <code>CLOSE_CALL_HISTORY_WITH_CANCEL_BUTTON = 401;</code>
     */
    public static final int CLOSE_CALL_HISTORY_WITH_CANCEL_BUTTON_VALUE = 401;


    public final int getNumber() {
      return value;
    }

    /**
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static Type valueOf(int value) {
      return forNumber(value);
    }

    public static Type forNumber(int value) {
      switch (value) {
        case 0: return UNKNOWN;
        case 1: return CHANGE_TAB_TO_FAVORITE;
        case 2: return CHANGE_TAB_TO_CALL_LOG;
        case 3: return CHANGE_TAB_TO_CONTACTS;
        case 4: return CHANGE_TAB_TO_VOICEMAIL;
        case 5: return PRESS_ANDROID_BACK_BUTTON;
        case 6: return TEXT_CHANGE_WITH_INPUT;
        case 7: return SCROLL;
        case 100: return CLICK_CALL_LOG_ITEM;
        case 101: return OPEN_CALL_DETAIL;
        case 102: return CLOSE_CALL_DETAIL_WITH_CANCEL_BUTTON;
        case 103: return COPY_NUMBER_IN_CALL_DETAIL;
        case 104: return EDIT_NUMBER_BEFORE_CALL_IN_CALL_DETAIL;
        case 200: return OPEN_DIALPAD;
        case 201: return CLOSE_DIALPAD;
        case 202: return PRESS_CALL_BUTTON_WITHOUT_CALLING;
        case 300: return OPEN_SEARCH;
        case 301: return HIDE_KEYBOARD_IN_SEARCH;
        case 302: return CLOSE_SEARCH_WITH_HIDE_BUTTON;
        case 400: return OPEN_CALL_HISTORY;
        case 401: return CLOSE_CALL_HISTORY_WITH_CANCEL_BUTTON;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<Type>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        Type> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<Type>() {
            public Type findValueByNumber(int number) {
              return Type.forNumber(number);
            }
          };

    private final int value;

    private Type(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:com.android.dialer.logging.UiAction.Type)
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    unknownFields.writeTo(output);
  }

  public int getSerializedSize() {
    int size = memoizedSerializedSize;
    if (size != -1) return size;

    size = 0;
    size += unknownFields.getSerializedSize();
    memoizedSerializedSize = size;
    return size;
  }

  public static com.android.dialer.logging.UiAction parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static com.android.dialer.logging.UiAction parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static com.android.dialer.logging.UiAction parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static com.android.dialer.logging.UiAction parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static com.android.dialer.logging.UiAction parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static com.android.dialer.logging.UiAction parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static com.android.dialer.logging.UiAction parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }
  public static com.android.dialer.logging.UiAction parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static com.android.dialer.logging.UiAction parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static com.android.dialer.logging.UiAction parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.android.dialer.logging.UiAction prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  /**
   * Protobuf type {@code com.android.dialer.logging.UiAction}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageLite.Builder<
        com.android.dialer.logging.UiAction, Builder> implements
      // @@protoc_insertion_point(builder_implements:com.android.dialer.logging.UiAction)
      com.android.dialer.logging.UiActionOrBuilder {
    // Construct using com.android.dialer.logging.UiAction.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }


    // @@protoc_insertion_point(builder_scope:com.android.dialer.logging.UiAction)
  }
  protected final Object dynamicMethod(
      com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
      Object arg0, Object arg1) {
    switch (method) {
      case NEW_MUTABLE_INSTANCE: {
        return new com.android.dialer.logging.UiAction();
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
        com.android.dialer.logging.UiAction other = (com.android.dialer.logging.UiAction) arg1;
        if (visitor == com.google.protobuf.GeneratedMessageLite.MergeFromVisitor
            .INSTANCE) {
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
        if (PARSER == null) {    synchronized (com.android.dialer.logging.UiAction.class) {
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


  // @@protoc_insertion_point(class_scope:com.android.dialer.logging.UiAction)
  private static final com.android.dialer.logging.UiAction DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new UiAction();
    DEFAULT_INSTANCE.makeImmutable();
  }

  public static com.android.dialer.logging.UiAction getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<UiAction> PARSER;

  public static com.google.protobuf.Parser<UiAction> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}

