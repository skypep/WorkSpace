// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: com/android/dialer/logging/contact_lookup_result.proto

#ifndef PROTOBUF_com_2fandroid_2fdialer_2flogging_2fcontact_5flookup_5fresult_2eproto__INCLUDED
#define PROTOBUF_com_2fandroid_2fdialer_2flogging_2fcontact_5flookup_5fresult_2eproto__INCLUDED

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
namespace logging {

// Internal implementation detail -- do not call these.
void protobuf_AddDesc_com_2fandroid_2fdialer_2flogging_2fcontact_5flookup_5fresult_2eproto();
void protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2fcontact_5flookup_5fresult_2eproto();
void protobuf_AssignDesc_com_2fandroid_2fdialer_2flogging_2fcontact_5flookup_5fresult_2eproto();
void protobuf_ShutdownFile_com_2fandroid_2fdialer_2flogging_2fcontact_5flookup_5fresult_2eproto();

class ContactLookupResult;

enum ContactLookupResult_Type {
  ContactLookupResult_Type_UNKNOWN_LOOKUP_RESULT_TYPE = 0,
  ContactLookupResult_Type_NOT_FOUND = 1,
  ContactLookupResult_Type_LOCAL_CONTACT = 2,
  ContactLookupResult_Type_LOCAL_CACHE = 3,
  ContactLookupResult_Type_REMOTE = 4,
  ContactLookupResult_Type_EMERGENCY = 5,
  ContactLookupResult_Type_VOICEMAIL = 6,
  ContactLookupResult_Type_REMOTE_PLACES = 7,
  ContactLookupResult_Type_REMOTE_PROFILE = 8,
  ContactLookupResult_Type_LOCAL_CACHE_UNKNOWN = 9,
  ContactLookupResult_Type_LOCAL_CACHE_DIRECTORY = 10,
  ContactLookupResult_Type_LOCAL_CACHE_EXTENDED = 11,
  ContactLookupResult_Type_LOCAL_CACHE_PLACES = 12,
  ContactLookupResult_Type_LOCAL_CACHE_PROFILE = 13,
  ContactLookupResult_Type_LOCAL_CACHE_CNAP = 14,
  ContactLookupResult_Type_LOCAL_CACHE_CEQUINT = 15
};
bool ContactLookupResult_Type_IsValid(int value);
const ContactLookupResult_Type ContactLookupResult_Type_Type_MIN = ContactLookupResult_Type_UNKNOWN_LOOKUP_RESULT_TYPE;
const ContactLookupResult_Type ContactLookupResult_Type_Type_MAX = ContactLookupResult_Type_LOCAL_CACHE_CEQUINT;
const int ContactLookupResult_Type_Type_ARRAYSIZE = ContactLookupResult_Type_Type_MAX + 1;

// ===================================================================

class ContactLookupResult : public ::google::protobuf::MessageLite /* @@protoc_insertion_point(class_definition:com.android.dialer.logging.ContactLookupResult) */ {
 public:
  ContactLookupResult();
  virtual ~ContactLookupResult();

  ContactLookupResult(const ContactLookupResult& from);

  inline ContactLookupResult& operator=(const ContactLookupResult& from) {
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

  static const ContactLookupResult& default_instance();

  static const ContactLookupResult* internal_default_instance();

  void Swap(ContactLookupResult* other);

  // implements Message ----------------------------------------------

  inline ContactLookupResult* New() const { return New(NULL); }

  ContactLookupResult* New(::google::protobuf::Arena* arena) const;
  void CheckTypeAndMergeFrom(const ::google::protobuf::MessageLite& from);
  void CopyFrom(const ContactLookupResult& from);
  void MergeFrom(const ContactLookupResult& from);
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
  void InternalSwap(ContactLookupResult* other);
  void UnsafeMergeFrom(const ContactLookupResult& from);
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

  typedef ContactLookupResult_Type Type;
  static const Type UNKNOWN_LOOKUP_RESULT_TYPE =
    ContactLookupResult_Type_UNKNOWN_LOOKUP_RESULT_TYPE;
  static const Type NOT_FOUND =
    ContactLookupResult_Type_NOT_FOUND;
  static const Type LOCAL_CONTACT =
    ContactLookupResult_Type_LOCAL_CONTACT;
  static const Type LOCAL_CACHE =
    ContactLookupResult_Type_LOCAL_CACHE;
  static const Type REMOTE =
    ContactLookupResult_Type_REMOTE;
  static const Type EMERGENCY =
    ContactLookupResult_Type_EMERGENCY;
  static const Type VOICEMAIL =
    ContactLookupResult_Type_VOICEMAIL;
  static const Type REMOTE_PLACES =
    ContactLookupResult_Type_REMOTE_PLACES;
  static const Type REMOTE_PROFILE =
    ContactLookupResult_Type_REMOTE_PROFILE;
  static const Type LOCAL_CACHE_UNKNOWN =
    ContactLookupResult_Type_LOCAL_CACHE_UNKNOWN;
  static const Type LOCAL_CACHE_DIRECTORY =
    ContactLookupResult_Type_LOCAL_CACHE_DIRECTORY;
  static const Type LOCAL_CACHE_EXTENDED =
    ContactLookupResult_Type_LOCAL_CACHE_EXTENDED;
  static const Type LOCAL_CACHE_PLACES =
    ContactLookupResult_Type_LOCAL_CACHE_PLACES;
  static const Type LOCAL_CACHE_PROFILE =
    ContactLookupResult_Type_LOCAL_CACHE_PROFILE;
  static const Type LOCAL_CACHE_CNAP =
    ContactLookupResult_Type_LOCAL_CACHE_CNAP;
  static const Type LOCAL_CACHE_CEQUINT =
    ContactLookupResult_Type_LOCAL_CACHE_CEQUINT;
  static inline bool Type_IsValid(int value) {
    return ContactLookupResult_Type_IsValid(value);
  }
  static const Type Type_MIN =
    ContactLookupResult_Type_Type_MIN;
  static const Type Type_MAX =
    ContactLookupResult_Type_Type_MAX;
  static const int Type_ARRAYSIZE =
    ContactLookupResult_Type_Type_ARRAYSIZE;

  // accessors -------------------------------------------------------

  // @@protoc_insertion_point(class_scope:com.android.dialer.logging.ContactLookupResult)
 private:

  ::google::protobuf::internal::ArenaStringPtr _unknown_fields_;
  ::google::protobuf::Arena* _arena_ptr_;

  ::google::protobuf::internal::HasBits<1> _has_bits_;
  mutable int _cached_size_;
  friend void  protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2fcontact_5flookup_5fresult_2eproto_impl();
  friend void  protobuf_AddDesc_com_2fandroid_2fdialer_2flogging_2fcontact_5flookup_5fresult_2eproto_impl();
  friend void protobuf_AssignDesc_com_2fandroid_2fdialer_2flogging_2fcontact_5flookup_5fresult_2eproto();
  friend void protobuf_ShutdownFile_com_2fandroid_2fdialer_2flogging_2fcontact_5flookup_5fresult_2eproto();

  void InitAsDefaultInstance();
};
extern ::google::protobuf::internal::ExplicitlyConstructed<ContactLookupResult> ContactLookupResult_default_instance_;

// ===================================================================


// ===================================================================

#if !PROTOBUF_INLINE_NOT_IN_HEADERS
// ContactLookupResult

inline const ContactLookupResult* ContactLookupResult::internal_default_instance() {
  return &ContactLookupResult_default_instance_.get();
}
#endif  // !PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)

}  // namespace logging
}  // namespace dialer
}  // namespace android
}  // namespace com

#ifndef SWIG
namespace google {
namespace protobuf {

template <> struct is_proto_enum< ::com::android::dialer::logging::ContactLookupResult_Type> : ::google::protobuf::internal::true_type {};

}  // namespace protobuf
}  // namespace google
#endif  // SWIG

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_com_2fandroid_2fdialer_2flogging_2fcontact_5flookup_5fresult_2eproto__INCLUDED