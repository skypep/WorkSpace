// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: com/android/dialer/dialercontact/dialer_contact.proto

#ifndef PROTOBUF_com_2fandroid_2fdialer_2fdialercontact_2fdialer_5fcontact_2eproto__INCLUDED
#define PROTOBUF_com_2fandroid_2fdialer_2fdialercontact_2fdialer_5fcontact_2eproto__INCLUDED

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
// @@protoc_insertion_point(includes)

namespace com {
namespace android {
namespace dialer {
namespace callcomposer {

// Internal implementation detail -- do not call these.
void protobuf_AddDesc_com_2fandroid_2fdialer_2fdialercontact_2fdialer_5fcontact_2eproto();
void protobuf_InitDefaults_com_2fandroid_2fdialer_2fdialercontact_2fdialer_5fcontact_2eproto();
void protobuf_AssignDesc_com_2fandroid_2fdialer_2fdialercontact_2fdialer_5fcontact_2eproto();
void protobuf_ShutdownFile_com_2fandroid_2fdialer_2fdialercontact_2fdialer_5fcontact_2eproto();

class DialerContact;
class SimDetails;

// ===================================================================

class DialerContact : public ::google::protobuf::MessageLite /* @@protoc_insertion_point(class_definition:com.android.dialer.callcomposer.DialerContact) */ {
 public:
  DialerContact();
  virtual ~DialerContact();

  DialerContact(const DialerContact& from);

  inline DialerContact& operator=(const DialerContact& from) {
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

  static const DialerContact& default_instance();

  static const DialerContact* internal_default_instance();

  void Swap(DialerContact* other);

  // implements Message ----------------------------------------------

  inline DialerContact* New() const { return New(NULL); }

  DialerContact* New(::google::protobuf::Arena* arena) const;
  void CheckTypeAndMergeFrom(const ::google::protobuf::MessageLite& from);
  void CopyFrom(const DialerContact& from);
  void MergeFrom(const DialerContact& from);
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
  void InternalSwap(DialerContact* other);
  void UnsafeMergeFrom(const DialerContact& from);
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

  // accessors -------------------------------------------------------

  // optional fixed64 photo_id = 1;
  bool has_photo_id() const;
  void clear_photo_id();
  static const int kPhotoIdFieldNumber = 1;
  ::google::protobuf::uint64 photo_id() const;
  void set_photo_id(::google::protobuf::uint64 value);

  // optional string photo_uri = 2;
  bool has_photo_uri() const;
  void clear_photo_uri();
  static const int kPhotoUriFieldNumber = 2;
  const ::std::string& photo_uri() const;
  void set_photo_uri(const ::std::string& value);
  void set_photo_uri(const char* value);
  void set_photo_uri(const char* value, size_t size);
  ::std::string* mutable_photo_uri();
  ::std::string* release_photo_uri();
  void set_allocated_photo_uri(::std::string* photo_uri);

  // optional string contact_uri = 3;
  bool has_contact_uri() const;
  void clear_contact_uri();
  static const int kContactUriFieldNumber = 3;
  const ::std::string& contact_uri() const;
  void set_contact_uri(const ::std::string& value);
  void set_contact_uri(const char* value);
  void set_contact_uri(const char* value, size_t size);
  ::std::string* mutable_contact_uri();
  ::std::string* release_contact_uri();
  void set_allocated_contact_uri(::std::string* contact_uri);

  // optional string name_or_number = 4;
  bool has_name_or_number() const;
  void clear_name_or_number();
  static const int kNameOrNumberFieldNumber = 4;
  const ::std::string& name_or_number() const;
  void set_name_or_number(const ::std::string& value);
  void set_name_or_number(const char* value);
  void set_name_or_number(const char* value, size_t size);
  ::std::string* mutable_name_or_number();
  ::std::string* release_name_or_number();
  void set_allocated_name_or_number(::std::string* name_or_number);

  // optional string number = 6;
  bool has_number() const;
  void clear_number();
  static const int kNumberFieldNumber = 6;
  const ::std::string& number() const;
  void set_number(const ::std::string& value);
  void set_number(const char* value);
  void set_number(const char* value, size_t size);
  ::std::string* mutable_number();
  ::std::string* release_number();
  void set_allocated_number(::std::string* number);

  // optional string display_number = 7;
  bool has_display_number() const;
  void clear_display_number();
  static const int kDisplayNumberFieldNumber = 7;
  const ::std::string& display_number() const;
  void set_display_number(const ::std::string& value);
  void set_display_number(const char* value);
  void set_display_number(const char* value, size_t size);
  ::std::string* mutable_display_number();
  ::std::string* release_display_number();
  void set_allocated_display_number(::std::string* display_number);

  // optional string number_label = 8;
  bool has_number_label() const;
  void clear_number_label();
  static const int kNumberLabelFieldNumber = 8;
  const ::std::string& number_label() const;
  void set_number_label(const ::std::string& value);
  void set_number_label(const char* value);
  void set_number_label(const char* value, size_t size);
  ::std::string* mutable_number_label();
  ::std::string* release_number_label();
  void set_allocated_number_label(::std::string* number_label);

  // optional int32 contact_type = 9;
  bool has_contact_type() const;
  void clear_contact_type();
  static const int kContactTypeFieldNumber = 9;
  ::google::protobuf::int32 contact_type() const;
  void set_contact_type(::google::protobuf::int32 value);

  // optional .com.android.dialer.callcomposer.SimDetails sim_details = 10;
  bool has_sim_details() const;
  void clear_sim_details();
  static const int kSimDetailsFieldNumber = 10;
  const ::com::android::dialer::callcomposer::SimDetails& sim_details() const;
  ::com::android::dialer::callcomposer::SimDetails* mutable_sim_details();
  ::com::android::dialer::callcomposer::SimDetails* release_sim_details();
  void set_allocated_sim_details(::com::android::dialer::callcomposer::SimDetails* sim_details);

  // @@protoc_insertion_point(class_scope:com.android.dialer.callcomposer.DialerContact)
 private:
  inline void set_has_photo_id();
  inline void clear_has_photo_id();
  inline void set_has_photo_uri();
  inline void clear_has_photo_uri();
  inline void set_has_contact_uri();
  inline void clear_has_contact_uri();
  inline void set_has_name_or_number();
  inline void clear_has_name_or_number();
  inline void set_has_number();
  inline void clear_has_number();
  inline void set_has_display_number();
  inline void clear_has_display_number();
  inline void set_has_number_label();
  inline void clear_has_number_label();
  inline void set_has_contact_type();
  inline void clear_has_contact_type();
  inline void set_has_sim_details();
  inline void clear_has_sim_details();

  ::google::protobuf::internal::ArenaStringPtr _unknown_fields_;
  ::google::protobuf::Arena* _arena_ptr_;

  ::google::protobuf::internal::HasBits<1> _has_bits_;
  mutable int _cached_size_;
  ::google::protobuf::internal::ArenaStringPtr photo_uri_;
  ::google::protobuf::internal::ArenaStringPtr contact_uri_;
  ::google::protobuf::internal::ArenaStringPtr name_or_number_;
  ::google::protobuf::internal::ArenaStringPtr number_;
  ::google::protobuf::internal::ArenaStringPtr display_number_;
  ::google::protobuf::internal::ArenaStringPtr number_label_;
  ::com::android::dialer::callcomposer::SimDetails* sim_details_;
  ::google::protobuf::uint64 photo_id_;
  ::google::protobuf::int32 contact_type_;
  friend void  protobuf_InitDefaults_com_2fandroid_2fdialer_2fdialercontact_2fdialer_5fcontact_2eproto_impl();
  friend void  protobuf_AddDesc_com_2fandroid_2fdialer_2fdialercontact_2fdialer_5fcontact_2eproto_impl();
  friend void protobuf_AssignDesc_com_2fandroid_2fdialer_2fdialercontact_2fdialer_5fcontact_2eproto();
  friend void protobuf_ShutdownFile_com_2fandroid_2fdialer_2fdialercontact_2fdialer_5fcontact_2eproto();

  void InitAsDefaultInstance();
};
extern ::google::protobuf::internal::ExplicitlyConstructed<DialerContact> DialerContact_default_instance_;

// -------------------------------------------------------------------

class SimDetails : public ::google::protobuf::MessageLite /* @@protoc_insertion_point(class_definition:com.android.dialer.callcomposer.SimDetails) */ {
 public:
  SimDetails();
  virtual ~SimDetails();

  SimDetails(const SimDetails& from);

  inline SimDetails& operator=(const SimDetails& from) {
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

  static const SimDetails& default_instance();

  static const SimDetails* internal_default_instance();

  void Swap(SimDetails* other);

  // implements Message ----------------------------------------------

  inline SimDetails* New() const { return New(NULL); }

  SimDetails* New(::google::protobuf::Arena* arena) const;
  void CheckTypeAndMergeFrom(const ::google::protobuf::MessageLite& from);
  void CopyFrom(const SimDetails& from);
  void MergeFrom(const SimDetails& from);
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
  void InternalSwap(SimDetails* other);
  void UnsafeMergeFrom(const SimDetails& from);
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

  // accessors -------------------------------------------------------

  // optional string network = 1;
  bool has_network() const;
  void clear_network();
  static const int kNetworkFieldNumber = 1;
  const ::std::string& network() const;
  void set_network(const ::std::string& value);
  void set_network(const char* value);
  void set_network(const char* value, size_t size);
  ::std::string* mutable_network();
  ::std::string* release_network();
  void set_allocated_network(::std::string* network);

  // optional int32 color = 2;
  bool has_color() const;
  void clear_color();
  static const int kColorFieldNumber = 2;
  ::google::protobuf::int32 color() const;
  void set_color(::google::protobuf::int32 value);

  // @@protoc_insertion_point(class_scope:com.android.dialer.callcomposer.SimDetails)
 private:
  inline void set_has_network();
  inline void clear_has_network();
  inline void set_has_color();
  inline void clear_has_color();

  ::google::protobuf::internal::ArenaStringPtr _unknown_fields_;
  ::google::protobuf::Arena* _arena_ptr_;

  ::google::protobuf::internal::HasBits<1> _has_bits_;
  mutable int _cached_size_;
  ::google::protobuf::internal::ArenaStringPtr network_;
  ::google::protobuf::int32 color_;
  friend void  protobuf_InitDefaults_com_2fandroid_2fdialer_2fdialercontact_2fdialer_5fcontact_2eproto_impl();
  friend void  protobuf_AddDesc_com_2fandroid_2fdialer_2fdialercontact_2fdialer_5fcontact_2eproto_impl();
  friend void protobuf_AssignDesc_com_2fandroid_2fdialer_2fdialercontact_2fdialer_5fcontact_2eproto();
  friend void protobuf_ShutdownFile_com_2fandroid_2fdialer_2fdialercontact_2fdialer_5fcontact_2eproto();

  void InitAsDefaultInstance();
};
extern ::google::protobuf::internal::ExplicitlyConstructed<SimDetails> SimDetails_default_instance_;

// ===================================================================


// ===================================================================

#if !PROTOBUF_INLINE_NOT_IN_HEADERS
// DialerContact

// optional fixed64 photo_id = 1;
inline bool DialerContact::has_photo_id() const {
  return (_has_bits_[0] & 0x00000001u) != 0;
}
inline void DialerContact::set_has_photo_id() {
  _has_bits_[0] |= 0x00000001u;
}
inline void DialerContact::clear_has_photo_id() {
  _has_bits_[0] &= ~0x00000001u;
}
inline void DialerContact::clear_photo_id() {
  photo_id_ = GOOGLE_ULONGLONG(0);
  clear_has_photo_id();
}
inline ::google::protobuf::uint64 DialerContact::photo_id() const {
  // @@protoc_insertion_point(field_get:com.android.dialer.callcomposer.DialerContact.photo_id)
  return photo_id_;
}
inline void DialerContact::set_photo_id(::google::protobuf::uint64 value) {
  set_has_photo_id();
  photo_id_ = value;
  // @@protoc_insertion_point(field_set:com.android.dialer.callcomposer.DialerContact.photo_id)
}

// optional string photo_uri = 2;
inline bool DialerContact::has_photo_uri() const {
  return (_has_bits_[0] & 0x00000002u) != 0;
}
inline void DialerContact::set_has_photo_uri() {
  _has_bits_[0] |= 0x00000002u;
}
inline void DialerContact::clear_has_photo_uri() {
  _has_bits_[0] &= ~0x00000002u;
}
inline void DialerContact::clear_photo_uri() {
  photo_uri_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  clear_has_photo_uri();
}
inline const ::std::string& DialerContact::photo_uri() const {
  // @@protoc_insertion_point(field_get:com.android.dialer.callcomposer.DialerContact.photo_uri)
  return photo_uri_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void DialerContact::set_photo_uri(const ::std::string& value) {
  set_has_photo_uri();
  photo_uri_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:com.android.dialer.callcomposer.DialerContact.photo_uri)
}
inline void DialerContact::set_photo_uri(const char* value) {
  set_has_photo_uri();
  photo_uri_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:com.android.dialer.callcomposer.DialerContact.photo_uri)
}
inline void DialerContact::set_photo_uri(const char* value, size_t size) {
  set_has_photo_uri();
  photo_uri_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:com.android.dialer.callcomposer.DialerContact.photo_uri)
}
inline ::std::string* DialerContact::mutable_photo_uri() {
  set_has_photo_uri();
  // @@protoc_insertion_point(field_mutable:com.android.dialer.callcomposer.DialerContact.photo_uri)
  return photo_uri_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* DialerContact::release_photo_uri() {
  // @@protoc_insertion_point(field_release:com.android.dialer.callcomposer.DialerContact.photo_uri)
  clear_has_photo_uri();
  return photo_uri_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void DialerContact::set_allocated_photo_uri(::std::string* photo_uri) {
  if (photo_uri != NULL) {
    set_has_photo_uri();
  } else {
    clear_has_photo_uri();
  }
  photo_uri_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), photo_uri);
  // @@protoc_insertion_point(field_set_allocated:com.android.dialer.callcomposer.DialerContact.photo_uri)
}

// optional string contact_uri = 3;
inline bool DialerContact::has_contact_uri() const {
  return (_has_bits_[0] & 0x00000004u) != 0;
}
inline void DialerContact::set_has_contact_uri() {
  _has_bits_[0] |= 0x00000004u;
}
inline void DialerContact::clear_has_contact_uri() {
  _has_bits_[0] &= ~0x00000004u;
}
inline void DialerContact::clear_contact_uri() {
  contact_uri_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  clear_has_contact_uri();
}
inline const ::std::string& DialerContact::contact_uri() const {
  // @@protoc_insertion_point(field_get:com.android.dialer.callcomposer.DialerContact.contact_uri)
  return contact_uri_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void DialerContact::set_contact_uri(const ::std::string& value) {
  set_has_contact_uri();
  contact_uri_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:com.android.dialer.callcomposer.DialerContact.contact_uri)
}
inline void DialerContact::set_contact_uri(const char* value) {
  set_has_contact_uri();
  contact_uri_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:com.android.dialer.callcomposer.DialerContact.contact_uri)
}
inline void DialerContact::set_contact_uri(const char* value, size_t size) {
  set_has_contact_uri();
  contact_uri_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:com.android.dialer.callcomposer.DialerContact.contact_uri)
}
inline ::std::string* DialerContact::mutable_contact_uri() {
  set_has_contact_uri();
  // @@protoc_insertion_point(field_mutable:com.android.dialer.callcomposer.DialerContact.contact_uri)
  return contact_uri_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* DialerContact::release_contact_uri() {
  // @@protoc_insertion_point(field_release:com.android.dialer.callcomposer.DialerContact.contact_uri)
  clear_has_contact_uri();
  return contact_uri_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void DialerContact::set_allocated_contact_uri(::std::string* contact_uri) {
  if (contact_uri != NULL) {
    set_has_contact_uri();
  } else {
    clear_has_contact_uri();
  }
  contact_uri_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), contact_uri);
  // @@protoc_insertion_point(field_set_allocated:com.android.dialer.callcomposer.DialerContact.contact_uri)
}

// optional string name_or_number = 4;
inline bool DialerContact::has_name_or_number() const {
  return (_has_bits_[0] & 0x00000008u) != 0;
}
inline void DialerContact::set_has_name_or_number() {
  _has_bits_[0] |= 0x00000008u;
}
inline void DialerContact::clear_has_name_or_number() {
  _has_bits_[0] &= ~0x00000008u;
}
inline void DialerContact::clear_name_or_number() {
  name_or_number_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  clear_has_name_or_number();
}
inline const ::std::string& DialerContact::name_or_number() const {
  // @@protoc_insertion_point(field_get:com.android.dialer.callcomposer.DialerContact.name_or_number)
  return name_or_number_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void DialerContact::set_name_or_number(const ::std::string& value) {
  set_has_name_or_number();
  name_or_number_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:com.android.dialer.callcomposer.DialerContact.name_or_number)
}
inline void DialerContact::set_name_or_number(const char* value) {
  set_has_name_or_number();
  name_or_number_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:com.android.dialer.callcomposer.DialerContact.name_or_number)
}
inline void DialerContact::set_name_or_number(const char* value, size_t size) {
  set_has_name_or_number();
  name_or_number_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:com.android.dialer.callcomposer.DialerContact.name_or_number)
}
inline ::std::string* DialerContact::mutable_name_or_number() {
  set_has_name_or_number();
  // @@protoc_insertion_point(field_mutable:com.android.dialer.callcomposer.DialerContact.name_or_number)
  return name_or_number_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* DialerContact::release_name_or_number() {
  // @@protoc_insertion_point(field_release:com.android.dialer.callcomposer.DialerContact.name_or_number)
  clear_has_name_or_number();
  return name_or_number_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void DialerContact::set_allocated_name_or_number(::std::string* name_or_number) {
  if (name_or_number != NULL) {
    set_has_name_or_number();
  } else {
    clear_has_name_or_number();
  }
  name_or_number_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), name_or_number);
  // @@protoc_insertion_point(field_set_allocated:com.android.dialer.callcomposer.DialerContact.name_or_number)
}

// optional string number = 6;
inline bool DialerContact::has_number() const {
  return (_has_bits_[0] & 0x00000010u) != 0;
}
inline void DialerContact::set_has_number() {
  _has_bits_[0] |= 0x00000010u;
}
inline void DialerContact::clear_has_number() {
  _has_bits_[0] &= ~0x00000010u;
}
inline void DialerContact::clear_number() {
  number_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  clear_has_number();
}
inline const ::std::string& DialerContact::number() const {
  // @@protoc_insertion_point(field_get:com.android.dialer.callcomposer.DialerContact.number)
  return number_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void DialerContact::set_number(const ::std::string& value) {
  set_has_number();
  number_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:com.android.dialer.callcomposer.DialerContact.number)
}
inline void DialerContact::set_number(const char* value) {
  set_has_number();
  number_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:com.android.dialer.callcomposer.DialerContact.number)
}
inline void DialerContact::set_number(const char* value, size_t size) {
  set_has_number();
  number_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:com.android.dialer.callcomposer.DialerContact.number)
}
inline ::std::string* DialerContact::mutable_number() {
  set_has_number();
  // @@protoc_insertion_point(field_mutable:com.android.dialer.callcomposer.DialerContact.number)
  return number_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* DialerContact::release_number() {
  // @@protoc_insertion_point(field_release:com.android.dialer.callcomposer.DialerContact.number)
  clear_has_number();
  return number_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void DialerContact::set_allocated_number(::std::string* number) {
  if (number != NULL) {
    set_has_number();
  } else {
    clear_has_number();
  }
  number_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), number);
  // @@protoc_insertion_point(field_set_allocated:com.android.dialer.callcomposer.DialerContact.number)
}

// optional string display_number = 7;
inline bool DialerContact::has_display_number() const {
  return (_has_bits_[0] & 0x00000020u) != 0;
}
inline void DialerContact::set_has_display_number() {
  _has_bits_[0] |= 0x00000020u;
}
inline void DialerContact::clear_has_display_number() {
  _has_bits_[0] &= ~0x00000020u;
}
inline void DialerContact::clear_display_number() {
  display_number_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  clear_has_display_number();
}
inline const ::std::string& DialerContact::display_number() const {
  // @@protoc_insertion_point(field_get:com.android.dialer.callcomposer.DialerContact.display_number)
  return display_number_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void DialerContact::set_display_number(const ::std::string& value) {
  set_has_display_number();
  display_number_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:com.android.dialer.callcomposer.DialerContact.display_number)
}
inline void DialerContact::set_display_number(const char* value) {
  set_has_display_number();
  display_number_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:com.android.dialer.callcomposer.DialerContact.display_number)
}
inline void DialerContact::set_display_number(const char* value, size_t size) {
  set_has_display_number();
  display_number_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:com.android.dialer.callcomposer.DialerContact.display_number)
}
inline ::std::string* DialerContact::mutable_display_number() {
  set_has_display_number();
  // @@protoc_insertion_point(field_mutable:com.android.dialer.callcomposer.DialerContact.display_number)
  return display_number_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* DialerContact::release_display_number() {
  // @@protoc_insertion_point(field_release:com.android.dialer.callcomposer.DialerContact.display_number)
  clear_has_display_number();
  return display_number_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void DialerContact::set_allocated_display_number(::std::string* display_number) {
  if (display_number != NULL) {
    set_has_display_number();
  } else {
    clear_has_display_number();
  }
  display_number_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), display_number);
  // @@protoc_insertion_point(field_set_allocated:com.android.dialer.callcomposer.DialerContact.display_number)
}

// optional string number_label = 8;
inline bool DialerContact::has_number_label() const {
  return (_has_bits_[0] & 0x00000040u) != 0;
}
inline void DialerContact::set_has_number_label() {
  _has_bits_[0] |= 0x00000040u;
}
inline void DialerContact::clear_has_number_label() {
  _has_bits_[0] &= ~0x00000040u;
}
inline void DialerContact::clear_number_label() {
  number_label_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  clear_has_number_label();
}
inline const ::std::string& DialerContact::number_label() const {
  // @@protoc_insertion_point(field_get:com.android.dialer.callcomposer.DialerContact.number_label)
  return number_label_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void DialerContact::set_number_label(const ::std::string& value) {
  set_has_number_label();
  number_label_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:com.android.dialer.callcomposer.DialerContact.number_label)
}
inline void DialerContact::set_number_label(const char* value) {
  set_has_number_label();
  number_label_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:com.android.dialer.callcomposer.DialerContact.number_label)
}
inline void DialerContact::set_number_label(const char* value, size_t size) {
  set_has_number_label();
  number_label_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:com.android.dialer.callcomposer.DialerContact.number_label)
}
inline ::std::string* DialerContact::mutable_number_label() {
  set_has_number_label();
  // @@protoc_insertion_point(field_mutable:com.android.dialer.callcomposer.DialerContact.number_label)
  return number_label_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* DialerContact::release_number_label() {
  // @@protoc_insertion_point(field_release:com.android.dialer.callcomposer.DialerContact.number_label)
  clear_has_number_label();
  return number_label_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void DialerContact::set_allocated_number_label(::std::string* number_label) {
  if (number_label != NULL) {
    set_has_number_label();
  } else {
    clear_has_number_label();
  }
  number_label_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), number_label);
  // @@protoc_insertion_point(field_set_allocated:com.android.dialer.callcomposer.DialerContact.number_label)
}

// optional int32 contact_type = 9;
inline bool DialerContact::has_contact_type() const {
  return (_has_bits_[0] & 0x00000080u) != 0;
}
inline void DialerContact::set_has_contact_type() {
  _has_bits_[0] |= 0x00000080u;
}
inline void DialerContact::clear_has_contact_type() {
  _has_bits_[0] &= ~0x00000080u;
}
inline void DialerContact::clear_contact_type() {
  contact_type_ = 0;
  clear_has_contact_type();
}
inline ::google::protobuf::int32 DialerContact::contact_type() const {
  // @@protoc_insertion_point(field_get:com.android.dialer.callcomposer.DialerContact.contact_type)
  return contact_type_;
}
inline void DialerContact::set_contact_type(::google::protobuf::int32 value) {
  set_has_contact_type();
  contact_type_ = value;
  // @@protoc_insertion_point(field_set:com.android.dialer.callcomposer.DialerContact.contact_type)
}

// optional .com.android.dialer.callcomposer.SimDetails sim_details = 10;
inline bool DialerContact::has_sim_details() const {
  return (_has_bits_[0] & 0x00000100u) != 0;
}
inline void DialerContact::set_has_sim_details() {
  _has_bits_[0] |= 0x00000100u;
}
inline void DialerContact::clear_has_sim_details() {
  _has_bits_[0] &= ~0x00000100u;
}
inline void DialerContact::clear_sim_details() {
  if (sim_details_ != NULL) sim_details_->::com::android::dialer::callcomposer::SimDetails::Clear();
  clear_has_sim_details();
}
inline const ::com::android::dialer::callcomposer::SimDetails& DialerContact::sim_details() const {
  // @@protoc_insertion_point(field_get:com.android.dialer.callcomposer.DialerContact.sim_details)
  return sim_details_ != NULL ? *sim_details_
                         : *::com::android::dialer::callcomposer::SimDetails::internal_default_instance();
}
inline ::com::android::dialer::callcomposer::SimDetails* DialerContact::mutable_sim_details() {
  set_has_sim_details();
  if (sim_details_ == NULL) {
    sim_details_ = new ::com::android::dialer::callcomposer::SimDetails;
  }
  // @@protoc_insertion_point(field_mutable:com.android.dialer.callcomposer.DialerContact.sim_details)
  return sim_details_;
}
inline ::com::android::dialer::callcomposer::SimDetails* DialerContact::release_sim_details() {
  // @@protoc_insertion_point(field_release:com.android.dialer.callcomposer.DialerContact.sim_details)
  clear_has_sim_details();
  ::com::android::dialer::callcomposer::SimDetails* temp = sim_details_;
  sim_details_ = NULL;
  return temp;
}
inline void DialerContact::set_allocated_sim_details(::com::android::dialer::callcomposer::SimDetails* sim_details) {
  delete sim_details_;
  sim_details_ = sim_details;
  if (sim_details) {
    set_has_sim_details();
  } else {
    clear_has_sim_details();
  }
  // @@protoc_insertion_point(field_set_allocated:com.android.dialer.callcomposer.DialerContact.sim_details)
}

inline const DialerContact* DialerContact::internal_default_instance() {
  return &DialerContact_default_instance_.get();
}
// -------------------------------------------------------------------

// SimDetails

// optional string network = 1;
inline bool SimDetails::has_network() const {
  return (_has_bits_[0] & 0x00000001u) != 0;
}
inline void SimDetails::set_has_network() {
  _has_bits_[0] |= 0x00000001u;
}
inline void SimDetails::clear_has_network() {
  _has_bits_[0] &= ~0x00000001u;
}
inline void SimDetails::clear_network() {
  network_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  clear_has_network();
}
inline const ::std::string& SimDetails::network() const {
  // @@protoc_insertion_point(field_get:com.android.dialer.callcomposer.SimDetails.network)
  return network_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void SimDetails::set_network(const ::std::string& value) {
  set_has_network();
  network_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:com.android.dialer.callcomposer.SimDetails.network)
}
inline void SimDetails::set_network(const char* value) {
  set_has_network();
  network_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:com.android.dialer.callcomposer.SimDetails.network)
}
inline void SimDetails::set_network(const char* value, size_t size) {
  set_has_network();
  network_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:com.android.dialer.callcomposer.SimDetails.network)
}
inline ::std::string* SimDetails::mutable_network() {
  set_has_network();
  // @@protoc_insertion_point(field_mutable:com.android.dialer.callcomposer.SimDetails.network)
  return network_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* SimDetails::release_network() {
  // @@protoc_insertion_point(field_release:com.android.dialer.callcomposer.SimDetails.network)
  clear_has_network();
  return network_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void SimDetails::set_allocated_network(::std::string* network) {
  if (network != NULL) {
    set_has_network();
  } else {
    clear_has_network();
  }
  network_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), network);
  // @@protoc_insertion_point(field_set_allocated:com.android.dialer.callcomposer.SimDetails.network)
}

// optional int32 color = 2;
inline bool SimDetails::has_color() const {
  return (_has_bits_[0] & 0x00000002u) != 0;
}
inline void SimDetails::set_has_color() {
  _has_bits_[0] |= 0x00000002u;
}
inline void SimDetails::clear_has_color() {
  _has_bits_[0] &= ~0x00000002u;
}
inline void SimDetails::clear_color() {
  color_ = 0;
  clear_has_color();
}
inline ::google::protobuf::int32 SimDetails::color() const {
  // @@protoc_insertion_point(field_get:com.android.dialer.callcomposer.SimDetails.color)
  return color_;
}
inline void SimDetails::set_color(::google::protobuf::int32 value) {
  set_has_color();
  color_ = value;
  // @@protoc_insertion_point(field_set:com.android.dialer.callcomposer.SimDetails.color)
}

inline const SimDetails* SimDetails::internal_default_instance() {
  return &SimDetails_default_instance_.get();
}
#endif  // !PROTOBUF_INLINE_NOT_IN_HEADERS
// -------------------------------------------------------------------


// @@protoc_insertion_point(namespace_scope)

}  // namespace callcomposer
}  // namespace dialer
}  // namespace android
}  // namespace com

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_com_2fandroid_2fdialer_2fdialercontact_2fdialer_5fcontact_2eproto__INCLUDED