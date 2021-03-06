// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: com/android/dialer/logging/interaction_event.proto

#define INTERNAL_SUPPRESS_PROTOBUF_FIELD_DEPRECATION
#include "com/android/dialer/logging/interaction_event.pb.h"

#include <algorithm>

#include <google/protobuf/stubs/common.h>
#include <google/protobuf/stubs/port.h>
#include <google/protobuf/stubs/once.h>
#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/wire_format_lite_inl.h>
#include <google/protobuf/io/zero_copy_stream_impl_lite.h>
// @@protoc_insertion_point(includes)

namespace com {
namespace android {
namespace dialer {
namespace logging {

void protobuf_ShutdownFile_com_2fandroid_2fdialer_2flogging_2finteraction_5fevent_2eproto() {
  InteractionEvent_default_instance_.Shutdown();
}

void protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2finteraction_5fevent_2eproto_impl() {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  ::google::protobuf::internal::GetEmptyString();
  InteractionEvent_default_instance_.DefaultConstruct();
  InteractionEvent_default_instance_.get_mutable()->InitAsDefaultInstance();
}

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2finteraction_5fevent_2eproto_once_);
void protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2finteraction_5fevent_2eproto() {
  ::google::protobuf::GoogleOnceInit(&protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2finteraction_5fevent_2eproto_once_,
                 &protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2finteraction_5fevent_2eproto_impl);
}
void protobuf_AddDesc_com_2fandroid_2fdialer_2flogging_2finteraction_5fevent_2eproto_impl() {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2finteraction_5fevent_2eproto();
  ::google::protobuf::internal::OnShutdown(&protobuf_ShutdownFile_com_2fandroid_2fdialer_2flogging_2finteraction_5fevent_2eproto);
}

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AddDesc_com_2fandroid_2fdialer_2flogging_2finteraction_5fevent_2eproto_once_);
void protobuf_AddDesc_com_2fandroid_2fdialer_2flogging_2finteraction_5fevent_2eproto() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AddDesc_com_2fandroid_2fdialer_2flogging_2finteraction_5fevent_2eproto_once_,
                 &protobuf_AddDesc_com_2fandroid_2fdialer_2flogging_2finteraction_5fevent_2eproto_impl);
}
#ifdef GOOGLE_PROTOBUF_NO_STATIC_INITIALIZER
// Force AddDescriptors() to be called at static initialization time.
struct StaticDescriptorInitializer_com_2fandroid_2fdialer_2flogging_2finteraction_5fevent_2eproto {
  StaticDescriptorInitializer_com_2fandroid_2fdialer_2flogging_2finteraction_5fevent_2eproto() {
    protobuf_AddDesc_com_2fandroid_2fdialer_2flogging_2finteraction_5fevent_2eproto();
  }
} static_descriptor_initializer_com_2fandroid_2fdialer_2flogging_2finteraction_5fevent_2eproto_;
#endif  // GOOGLE_PROTOBUF_NO_STATIC_INITIALIZER

namespace {

static void MergeFromFail(int line) GOOGLE_ATTRIBUTE_COLD GOOGLE_ATTRIBUTE_NORETURN;
static void MergeFromFail(int line) {
  ::google::protobuf::internal::MergeFromFail(__FILE__, line);
}

}  // namespace


// ===================================================================

static ::std::string* MutableUnknownFieldsForInteractionEvent(
    InteractionEvent* ptr) {
  return ptr->mutable_unknown_fields();
}

bool InteractionEvent_Type_IsValid(int value) {
  switch (value) {
    case 0:
    case 15:
    case 16:
    case 17:
    case 18:
    case 19:
    case 20:
    case 21:
    case 22:
    case 23:
    case 24:
    case 25:
    case 26:
    case 27:
    case 28:
    case 29:
    case 30:
    case 31:
    case 32:
    case 33:
    case 34:
    case 35:
    case 36:
    case 37:
      return true;
    default:
      return false;
  }
}

#if !defined(_MSC_VER) || _MSC_VER >= 1900
const InteractionEvent_Type InteractionEvent::UNKNOWN;
const InteractionEvent_Type InteractionEvent::CALL_BLOCKED;
const InteractionEvent_Type InteractionEvent::BLOCK_NUMBER_CALL_LOG;
const InteractionEvent_Type InteractionEvent::BLOCK_NUMBER_CALL_DETAIL;
const InteractionEvent_Type InteractionEvent::BLOCK_NUMBER_MANAGEMENT_SCREEN;
const InteractionEvent_Type InteractionEvent::UNBLOCK_NUMBER_CALL_LOG;
const InteractionEvent_Type InteractionEvent::UNBLOCK_NUMBER_CALL_DETAIL;
const InteractionEvent_Type InteractionEvent::UNBLOCK_NUMBER_MANAGEMENT_SCREEN;
const InteractionEvent_Type InteractionEvent::IMPORT_SEND_TO_VOICEMAIL;
const InteractionEvent_Type InteractionEvent::UNDO_BLOCK_NUMBER;
const InteractionEvent_Type InteractionEvent::UNDO_UNBLOCK_NUMBER;
const InteractionEvent_Type InteractionEvent::SPEED_DIAL_PIN_CONTACT;
const InteractionEvent_Type InteractionEvent::SPEED_DIAL_REMOVE_CONTACT;
const InteractionEvent_Type InteractionEvent::SPEED_DIAL_OPEN_CONTACT_CARD;
const InteractionEvent_Type InteractionEvent::SPEED_DIAL_CLICK_CONTACT_WITH_AMBIGUOUS_NUMBER;
const InteractionEvent_Type InteractionEvent::SPEED_DIAL_SET_DEFAULT_NUMBER_FOR_AMBIGUOUS_CONTACT;
const InteractionEvent_Type InteractionEvent::OPEN_QUICK_CONTACT_FROM_CALL_LOG;
const InteractionEvent_Type InteractionEvent::OPEN_QUICK_CONTACT_FROM_CALL_DETAILS;
const InteractionEvent_Type InteractionEvent::OPEN_QUICK_CONTACT_FROM_ALL_CONTACTS_GENERAL;
const InteractionEvent_Type InteractionEvent::OPEN_QUICK_CONTACT_FROM_CONTACTS_FRAGMENT_BADGE;
const InteractionEvent_Type InteractionEvent::OPEN_QUICK_CONTACT_FROM_CONTACTS_FRAGMENT_ITEM;
const InteractionEvent_Type InteractionEvent::OPEN_QUICK_CONTACT_FROM_SEARCH;
const InteractionEvent_Type InteractionEvent::OPEN_QUICK_CONTACT_FROM_VOICEMAIL;
const InteractionEvent_Type InteractionEvent::OPEN_QUICK_CONTACT_FROM_CALL_HISTORY;
const InteractionEvent_Type InteractionEvent::Type_MIN;
const InteractionEvent_Type InteractionEvent::Type_MAX;
const int InteractionEvent::Type_ARRAYSIZE;
#endif  // !defined(_MSC_VER) || _MSC_VER >= 1900
#if !defined(_MSC_VER) || _MSC_VER >= 1900
#endif  // !defined(_MSC_VER) || _MSC_VER >= 1900

InteractionEvent::InteractionEvent()
  : ::google::protobuf::MessageLite(), _arena_ptr_(NULL) {
  if (this != internal_default_instance()) protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2finteraction_5fevent_2eproto();
  SharedCtor();
  // @@protoc_insertion_point(constructor:com.android.dialer.logging.InteractionEvent)
}

void InteractionEvent::InitAsDefaultInstance() {
}

InteractionEvent::InteractionEvent(const InteractionEvent& from)
  : ::google::protobuf::MessageLite(),
    _arena_ptr_(NULL) {
  SharedCtor();
  UnsafeMergeFrom(from);
  // @@protoc_insertion_point(copy_constructor:com.android.dialer.logging.InteractionEvent)
}

void InteractionEvent::SharedCtor() {
  _cached_size_ = 0;
  _unknown_fields_.UnsafeSetDefault(
      &::google::protobuf::internal::GetEmptyStringAlreadyInited());
}

InteractionEvent::~InteractionEvent() {
  // @@protoc_insertion_point(destructor:com.android.dialer.logging.InteractionEvent)
  SharedDtor();
}

void InteractionEvent::SharedDtor() {
  _unknown_fields_.DestroyNoArena(
      &::google::protobuf::internal::GetEmptyStringAlreadyInited());
}

void InteractionEvent::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const InteractionEvent& InteractionEvent::default_instance() {
  protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2finteraction_5fevent_2eproto();
  return *internal_default_instance();
}

::google::protobuf::internal::ExplicitlyConstructed<InteractionEvent> InteractionEvent_default_instance_;

InteractionEvent* InteractionEvent::New(::google::protobuf::Arena* arena) const {
  InteractionEvent* n = new InteractionEvent;
  if (arena != NULL) {
    arena->Own(n);
  }
  return n;
}

void InteractionEvent::Clear() {
// @@protoc_insertion_point(message_clear_start:com.android.dialer.logging.InteractionEvent)
  _has_bits_.Clear();
  _unknown_fields_.ClearToEmptyNoArena(
      &::google::protobuf::internal::GetEmptyStringAlreadyInited());
}

bool InteractionEvent::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!GOOGLE_PREDICT_TRUE(EXPRESSION)) goto failure
  ::google::protobuf::uint32 tag;
  ::google::protobuf::io::LazyStringOutputStream unknown_fields_string(
      ::google::protobuf::NewPermanentCallback(
          &MutableUnknownFieldsForInteractionEvent, this));
  ::google::protobuf::io::CodedOutputStream unknown_fields_stream(
      &unknown_fields_string, false);
  // @@protoc_insertion_point(parse_start:com.android.dialer.logging.InteractionEvent)
  for (;;) {
    ::std::pair< ::google::protobuf::uint32, bool> p = input->ReadTagWithCutoff(127);
    tag = p.first;
    if (!p.second) goto handle_unusual;
  handle_unusual:
    if (tag == 0 ||
        ::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
        ::google::protobuf::internal::WireFormatLite::WIRETYPE_END_GROUP) {
      goto success;
    }
    DO_(::google::protobuf::internal::WireFormatLite::SkipField(
        input, tag, &unknown_fields_stream));
  }
success:
  // @@protoc_insertion_point(parse_success:com.android.dialer.logging.InteractionEvent)
  return true;
failure:
  // @@protoc_insertion_point(parse_failure:com.android.dialer.logging.InteractionEvent)
  return false;
#undef DO_
}

void InteractionEvent::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // @@protoc_insertion_point(serialize_start:com.android.dialer.logging.InteractionEvent)
  output->WriteRaw(unknown_fields().data(),
                   static_cast<int>(unknown_fields().size()));
  // @@protoc_insertion_point(serialize_end:com.android.dialer.logging.InteractionEvent)
}

size_t InteractionEvent::ByteSizeLong() const {
// @@protoc_insertion_point(message_byte_size_start:com.android.dialer.logging.InteractionEvent)
  size_t total_size = 0;

  total_size += unknown_fields().size();

  int cached_size = ::google::protobuf::internal::ToCachedSize(total_size);
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = cached_size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
  return total_size;
}

void InteractionEvent::CheckTypeAndMergeFrom(
    const ::google::protobuf::MessageLite& from) {
  MergeFrom(*::google::protobuf::down_cast<const InteractionEvent*>(&from));
}

void InteractionEvent::MergeFrom(const InteractionEvent& from) {
// @@protoc_insertion_point(class_specific_merge_from_start:com.android.dialer.logging.InteractionEvent)
  if (GOOGLE_PREDICT_TRUE(&from != this)) {
    UnsafeMergeFrom(from);
  } else {
    MergeFromFail(__LINE__);
  }
}

void InteractionEvent::UnsafeMergeFrom(const InteractionEvent& from) {
  GOOGLE_DCHECK(&from != this);
  if (!from.unknown_fields().empty()) {
    mutable_unknown_fields()->append(from.unknown_fields());
  }
}

void InteractionEvent::CopyFrom(const InteractionEvent& from) {
// @@protoc_insertion_point(class_specific_copy_from_start:com.android.dialer.logging.InteractionEvent)
  if (&from == this) return;
  Clear();
  UnsafeMergeFrom(from);
}

bool InteractionEvent::IsInitialized() const {

  return true;
}

void InteractionEvent::Swap(InteractionEvent* other) {
  if (other == this) return;
  InternalSwap(other);
}
void InteractionEvent::InternalSwap(InteractionEvent* other) {
  _unknown_fields_.Swap(&other->_unknown_fields_);
  std::swap(_cached_size_, other->_cached_size_);
}

::std::string InteractionEvent::GetTypeName() const {
  return "com.android.dialer.logging.InteractionEvent";
}

#if PROTOBUF_INLINE_NOT_IN_HEADERS
// InteractionEvent

inline const InteractionEvent* InteractionEvent::internal_default_instance() {
  return &InteractionEvent_default_instance_.get();
}
#endif  // PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)

}  // namespace logging
}  // namespace dialer
}  // namespace android
}  // namespace com

// @@protoc_insertion_point(global_scope)
