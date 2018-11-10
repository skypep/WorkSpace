// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: com/android/dialer/callintent/call_initiation_type.proto

#ifndef PROTOBUF_com_2fandroid_2fdialer_2fcallintent_2fcall_5finitiation_5ftype_2eproto__INCLUDED
#define PROTOBUF_com_2fandroid_2fdialer_2fcallintent_2fcall_5finitiation_5ftype_2eproto__INCLUDED

#include <string>

#include <google/protobuf/stubs/common.h>

#if GOOGLE_PROTOBUF_VERSION < 3001000
#error This file was generated by a newer version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please update
#error your headers.
#endif
#if 3001000 < GOOGLE_PROTOBUF_MIN_PROTOC_VERSION
#error This file was generated by an older version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please
#error regenerate this file with a newer version of protoc.
#endif

#include <google/protobuf/arena.h>
#include <google/protobuf/arenastring.h>
#include <google/protobuf/generated_message_util.h>
#include <google/protobuf/message_lite.h>
#include <google/protobuf/repeated_field.h>
#include <google/protobuf/extension_set.h>
#include <google/protobuf/generated_enum_util.h>
// @@protoc_insertion_point(includes)

namespace com {
namespace android {
namespace dialer {
namespace callintent {

// Internal implementation detail -- do not call these.
void protobuf_AddDesc_com_2fandroid_2fdialer_2fcallintent_2fcall_5finitiation_5ftype_2eproto();
void protobuf_InitDefaults_com_2fandroid_2fdialer_2fcallintent_2fcall_5finitiation_5ftype_2eproto();
void protobuf_AssignDesc_com_2fandroid_2fdialer_2fcallintent_2fcall_5finitiation_5ftype_2eproto();
void protobuf_ShutdownFile_com_2fandroid_2fdialer_2fcallintent_2fcall_5finitiation_5ftype_2eproto();

class CallInitiationType;

enum CallInitiationType_Type {
  CallInitiationType_Type_UNKNOWN_INITIATION = 0,
  CallInitiationType_Type_INCOMING_INITIATION = 1,
  CallInitiationType_Type_DIALPAD = 2,
  CallInitiationType_Type_SPEED_DIAL = 3,
  CallInitiationType_Type_REMOTE_DIRECTORY = 4,
  CallInitiationType_Type_SMART_DIAL = 5,
  CallInitiationType_Type_REGULAR_SEARCH = 6,
  CallInitiationType_Type_CALL_LOG = 7,
  CallInitiationType_Type_CALL_LOG_FILTER = 8,
  CallInitiationType_Type_VOICEMAIL_LOG = 9,
  CallInitiationType_Type_CALL_DETAILS = 10,
  CallInitiationType_Type_QUICK_CONTACTS = 11,
  CallInitiationType_Type_EXTERNAL_INITIATION = 12,
  CallInitiationType_Type_LAUNCHER_SHORTCUT = 13,
  CallInitiationType_Type_CALL_COMPOSER = 14,
  CallInitiationType_Type_MISSED_CALL_NOTIFICATION = 15,
  CallInitiationType_Type_CALL_SUBJECT_DIALOG = 16,
  CallInitiationType_Type_IMS_VIDEO_BLOCKED_FALLBACK_TO_VOICE = 17
};
bool CallInitiationType_Type_IsValid(int value);
const CallInitiationType_Type CallInitiationType_Type_Type_MIN = CallInitiationType_Type_UNKNOWN_INITIATION;
const CallInitiationType_Type CallInitiationType_Type_Type_MAX = CallInitiationType_Type_IMS_VIDEO_BLOCKED_FALLBACK_TO_VOICE;
const int CallInitiationType_Type_Type_ARRAYSIZE = CallInitiationType_Type_Type_MAX + 1;

// ===================================================================

class CallInitiationType : public ::google::protobuf::MessageLite /* @@protoc_insertion_point(class_definition:com.android.dialer.callintent.CallInitiationType) */ {
 public:
  CallInitiationType();
  virtual ~CallInitiationType();

  CallInitiationType(const CallInitiationType& from);

  inline CallInitiationType& operator=(const CallInitiationType& from) {
    CopyFrom(from);
    return *this;
  }

  inline const ::std::string& unknown_fields() const {
    return _unknown_fields_.GetNoArena(
        &::google::protobuf::internal::GetEmptyStringAlreadyInited());
  }

  inline ::std::string* mutable_unknown_fields() {
    return _unknown_fields_.MutableNoArena(
        &::google::protobuf::internal::GetEmptyStringAlreadyInited());
  }

  static const CallInitiationType& default_instance();

  static const CallInitiationType* internal_default_instance();

  void Swap(CallInitiationType* other);

  // implements Message ----------------------------------------------

  inline CallInitiationType* New() const { return New(NULL); }

  CallInitiationType* New(::google::protobuf::Arena* arena) const;
  void CheckTypeAndMergeFrom(const ::google::protobuf::MessageLite& from);
  void CopyFrom(const CallInitiationType& from);
  void MergeFrom(const CallInitiationType& from);
  void Clear();
  bool IsInitialized() const;

  size_t ByteSizeLong() const;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input);
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const;
  void DiscardUnknownFields();
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const;
  void InternalSwap(CallInitiationType* other);
  void UnsafeMergeFrom(const CallInitiationType& from);
  private:
  inline ::google::protobuf::Arena* GetArenaNoVirtual() const {
    return _arena_ptr_;
  }
  inline ::google::protobuf::Arena* MaybeArenaPtr() const {
    return _arena_ptr_;
  }
  public:

  ::std::string GetTypeName() const;

  // nested types ----------------------------------------------------

  typedef CallInitiationType_Type Type;
  static const Type UNKNOWN_INITIATION =
    CallInitiationType_Type_UNKNOWN_INITIATION;
  static const Type INCOMING_INITIATION =
    CallInitiationType_Type_INCOMING_INITIATION;
  static const Type DIALPAD =
    CallInitiationType_Type_DIALPAD;
  static const Type SPEED_DIAL =
    CallInitiationType_Type_SPEED_DIAL;
  static const Type REMOTE_DIRECTORY =
    CallInitiationType_Type_REMOTE_DIRECTORY;
  static const Type SMART_DIAL =
    CallInitiationType_Type_SMART_DIAL;
  static const Type REGULAR_SEARCH =
    CallInitiationType_Type_REGULAR_SEARCH;
  static const Type CALL_LOG =
    CallInitiationType_Type_CALL_LOG;
  static const Type CALL_LOG_FILTER =
    CallInitiationType_Type_CALL_LOG_FILTER;
  static const Type VOICEMAIL_LOG =
    CallInitiationType_Type_VOICEMAIL_LOG;
  static const Type CALL_DETAILS =
    CallInitiationType_Type_CALL_DETAILS;
  static const Type QUICK_CONTACTS =
    CallInitiationType_Type_QUICK_CONTACTS;
  static const Type EXTERNAL_INITIATION =
    CallInitiationType_Type_EXTERNAL_INITIATION;
  static const Type LAUNCHER_SHORTCUT =
    CallInitiationType_Type_LAUNCHER_SHORTCUT;
  static const Type CALL_COMPOSER =
    CallInitiationType_Type_CALL_COMPOSER;
  static const Type MISSED_CALL_NOTIFICATION =
    CallInitiationType_Type_MISSED_CALL_NOTIFICATION;
  static const Type CALL_SUBJECT_DIALOG =
    CallInitiationType_Type_CALL_SUBJECT_DIALOG;
  static const Type IMS_VIDEO_BLOCKED_FALLBACK_TO_VOICE =
    CallInitiationType_Type_IMS_VIDEO_BLOCKED_FALLBACK_TO_VOICE;
  static inline bool Type_IsValid(int value) {
    return CallInitiationType_Type_IsValid(value);
  }
  static const Type Type_MIN =
    CallInitiationType_Type_Type_MIN;
  static const Type Type_MAX =
    CallInitiationType_Type_Type_MAX;
  static const int Type_ARRAYSIZE =
    CallInitiationType_Type_Type_ARRAYSIZE;

  // accessors -------------------------------------------------------

  // @@protoc_insertion_point(class_scope:com.android.dialer.callintent.CallInitiationType)
 private:

  ::google::protobuf::internal::ArenaStringPtr _unknown_fields_;
  ::google::protobuf::Arena* _arena_ptr_;

  ::google::protobuf::internal::HasBits<1> _has_bits_;
  mutable int _cached_size_;
  friend void  protobuf_InitDefaults_com_2fandroid_2fdialer_2fcallintent_2fcall_5finitiation_5ftype_2eproto_impl();
  friend void  protobuf_AddDesc_com_2fandroid_2fdialer_2fcallintent_2fcall_5finitiation_5ftype_2eproto_impl();
  friend void protobuf_AssignDesc_com_2fandroid_2fdialer_2fcallintent_2fcall_5finitiation_5ftype_2eproto();
  friend void protobuf_ShutdownFile_com_2fandroid_2fdialer_2fcallintent_2fcall_5finitiation_5ftype_2eproto();

  void InitAsDefaultInstance();
};
extern ::google::protobuf::internal::ExplicitlyConstructed<CallInitiationType> CallInitiationType_default_instance_;

// ===================================================================


// ===================================================================

#if !PROTOBUF_INLINE_NOT_IN_HEADERS
// CallInitiationType

inline const CallInitiationType* CallInitiationType::internal_default_instance() {
  return &CallInitiationType_default_instance_.get();
}
#endif  // !PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)

}  // namespace callintent
}  // namespace dialer
}  // namespace android
}  // namespace com

#ifndef SWIG
namespace google {
namespace protobuf {

template <> struct is_proto_enum< ::com::android::dialer::callintent::CallInitiationType_Type> : ::google::protobuf::internal::true_type {};

}  // namespace protobuf
}  // namespace google
#endif  // SWIG

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_com_2fandroid_2fdialer_2fcallintent_2fcall_5finitiation_5ftype_2eproto__INCLUDED