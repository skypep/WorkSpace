// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: com/android/dialer/logging/ui_action.proto

#define INTERNAL_SUPPRESS_PROTOBUF_FIELD_DEPRECATION
#include "com/android/dialer/logging/ui_action.pb.h"

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

void protobuf_ShutdownFile_com_2fandroid_2fdialer_2flogging_2fui_5faction_2eproto() {
  UiAction_default_instance_.Shutdown();
}

void protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2fui_5faction_2eproto_impl() {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  ::google::protobuf::internal::GetEmptyString();
  UiAction_default_instance_.DefaultConstruct();
  UiAction_default_instance_.get_mutable()->InitAsDefaultInstance();
}

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2fui_5faction_2eproto_once_);
void protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2fui_5faction_2eproto() {
  ::google::protobuf::GoogleOnceInit(&protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2fui_5faction_2eproto_once_,
                 &protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2fui_5faction_2eproto_impl);
}
void protobuf_AddDesc_com_2fandroid_2fdialer_2flogging_2fui_5faction_2eproto_impl() {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2fui_5faction_2eproto();
  ::google::protobuf::internal::OnShutdown(&protobuf_ShutdownFile_com_2fandroid_2fdialer_2flogging_2fui_5faction_2eproto);
}

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AddDesc_com_2fandroid_2fdialer_2flogging_2fui_5faction_2eproto_once_);
void protobuf_AddDesc_com_2fandroid_2fdialer_2flogging_2fui_5faction_2eproto() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AddDesc_com_2fandroid_2fdialer_2flogging_2fui_5faction_2eproto_once_,
                 &protobuf_AddDesc_com_2fandroid_2fdialer_2flogging_2fui_5faction_2eproto_impl);
}
#ifdef GOOGLE_PROTOBUF_NO_STATIC_INITIALIZER
// Force AddDescriptors() to be called at static initialization time.
struct StaticDescriptorInitializer_com_2fandroid_2fdialer_2flogging_2fui_5faction_2eproto {
  StaticDescriptorInitializer_com_2fandroid_2fdialer_2flogging_2fui_5faction_2eproto() {
    protobuf_AddDesc_com_2fandroid_2fdialer_2flogging_2fui_5faction_2eproto();
  }
} static_descriptor_initializer_com_2fandroid_2fdialer_2flogging_2fui_5faction_2eproto_;
#endif  // GOOGLE_PROTOBUF_NO_STATIC_INITIALIZER

namespace {

static void MergeFromFail(int line) GOOGLE_ATTRIBUTE_COLD GOOGLE_ATTRIBUTE_NORETURN;
static void MergeFromFail(int line) {
  ::google::protobuf::internal::MergeFromFail(__FILE__, line);
}

}  // namespace


// ===================================================================

static ::std::string* MutableUnknownFieldsForUiAction(
    UiAction* ptr) {
  return ptr->mutable_unknown_fields();
}

bool UiAction_Type_IsValid(int value) {
  switch (value) {
    case 0:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    case 100:
    case 101:
    case 102:
    case 103:
    case 104:
    case 200:
    case 201:
    case 202:
    case 300:
    case 301:
    case 302:
    case 400:
    case 401:
      return true;
    default:
      return false;
  }
}

#if !defined(_MSC_VER) || _MSC_VER >= 1900
const UiAction_Type UiAction::UNKNOWN;
const UiAction_Type UiAction::CHANGE_TAB_TO_FAVORITE;
const UiAction_Type UiAction::CHANGE_TAB_TO_CALL_LOG;
const UiAction_Type UiAction::CHANGE_TAB_TO_CONTACTS;
const UiAction_Type UiAction::CHANGE_TAB_TO_VOICEMAIL;
const UiAction_Type UiAction::PRESS_ANDROID_BACK_BUTTON;
const UiAction_Type UiAction::TEXT_CHANGE_WITH_INPUT;
const UiAction_Type UiAction::SCROLL;
const UiAction_Type UiAction::CLICK_CALL_LOG_ITEM;
const UiAction_Type UiAction::OPEN_CALL_DETAIL;
const UiAction_Type UiAction::CLOSE_CALL_DETAIL_WITH_CANCEL_BUTTON;
const UiAction_Type UiAction::COPY_NUMBER_IN_CALL_DETAIL;
const UiAction_Type UiAction::EDIT_NUMBER_BEFORE_CALL_IN_CALL_DETAIL;
const UiAction_Type UiAction::OPEN_DIALPAD;
const UiAction_Type UiAction::CLOSE_DIALPAD;
const UiAction_Type UiAction::PRESS_CALL_BUTTON_WITHOUT_CALLING;
const UiAction_Type UiAction::OPEN_SEARCH;
const UiAction_Type UiAction::HIDE_KEYBOARD_IN_SEARCH;
const UiAction_Type UiAction::CLOSE_SEARCH_WITH_HIDE_BUTTON;
const UiAction_Type UiAction::OPEN_CALL_HISTORY;
const UiAction_Type UiAction::CLOSE_CALL_HISTORY_WITH_CANCEL_BUTTON;
const UiAction_Type UiAction::Type_MIN;
const UiAction_Type UiAction::Type_MAX;
const int UiAction::Type_ARRAYSIZE;
#endif  // !defined(_MSC_VER) || _MSC_VER >= 1900
#if !defined(_MSC_VER) || _MSC_VER >= 1900
#endif  // !defined(_MSC_VER) || _MSC_VER >= 1900

UiAction::UiAction()
  : ::google::protobuf::MessageLite(), _arena_ptr_(NULL) {
  if (this != internal_default_instance()) protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2fui_5faction_2eproto();
  SharedCtor();
  // @@protoc_insertion_point(constructor:com.android.dialer.logging.UiAction)
}

void UiAction::InitAsDefaultInstance() {
}

UiAction::UiAction(const UiAction& from)
  : ::google::protobuf::MessageLite(),
    _arena_ptr_(NULL) {
  SharedCtor();
  UnsafeMergeFrom(from);
  // @@protoc_insertion_point(copy_constructor:com.android.dialer.logging.UiAction)
}

void UiAction::SharedCtor() {
  _cached_size_ = 0;
  _unknown_fields_.UnsafeSetDefault(
      &::google::protobuf::internal::GetEmptyStringAlreadyInited());
}

UiAction::~UiAction() {
  // @@protoc_insertion_point(destructor:com.android.dialer.logging.UiAction)
  SharedDtor();
}

void UiAction::SharedDtor() {
  _unknown_fields_.DestroyNoArena(
      &::google::protobuf::internal::GetEmptyStringAlreadyInited());
}

void UiAction::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const UiAction& UiAction::default_instance() {
  protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2fui_5faction_2eproto();
  return *internal_default_instance();
}

::google::protobuf::internal::ExplicitlyConstructed<UiAction> UiAction_default_instance_;

UiAction* UiAction::New(::google::protobuf::Arena* arena) const {
  UiAction* n = new UiAction;
  if (arena != NULL) {
    arena->Own(n);
  }
  return n;
}

void UiAction::Clear() {
// @@protoc_insertion_point(message_clear_start:com.android.dialer.logging.UiAction)
  _has_bits_.Clear();
  _unknown_fields_.ClearToEmptyNoArena(
      &::google::protobuf::internal::GetEmptyStringAlreadyInited());
}

bool UiAction::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!GOOGLE_PREDICT_TRUE(EXPRESSION)) goto failure
  ::google::protobuf::uint32 tag;
  ::google::protobuf::io::LazyStringOutputStream unknown_fields_string(
      ::google::protobuf::NewPermanentCallback(
          &MutableUnknownFieldsForUiAction, this));
  ::google::protobuf::io::CodedOutputStream unknown_fields_stream(
      &unknown_fields_string, false);
  // @@protoc_insertion_point(parse_start:com.android.dialer.logging.UiAction)
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
  // @@protoc_insertion_point(parse_success:com.android.dialer.logging.UiAction)
  return true;
failure:
  // @@protoc_insertion_point(parse_failure:com.android.dialer.logging.UiAction)
  return false;
#undef DO_
}

void UiAction::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // @@protoc_insertion_point(serialize_start:com.android.dialer.logging.UiAction)
  output->WriteRaw(unknown_fields().data(),
                   static_cast<int>(unknown_fields().size()));
  // @@protoc_insertion_point(serialize_end:com.android.dialer.logging.UiAction)
}

size_t UiAction::ByteSizeLong() const {
// @@protoc_insertion_point(message_byte_size_start:com.android.dialer.logging.UiAction)
  size_t total_size = 0;

  total_size += unknown_fields().size();

  int cached_size = ::google::protobuf::internal::ToCachedSize(total_size);
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = cached_size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
  return total_size;
}

void UiAction::CheckTypeAndMergeFrom(
    const ::google::protobuf::MessageLite& from) {
  MergeFrom(*::google::protobuf::down_cast<const UiAction*>(&from));
}

void UiAction::MergeFrom(const UiAction& from) {
// @@protoc_insertion_point(class_specific_merge_from_start:com.android.dialer.logging.UiAction)
  if (GOOGLE_PREDICT_TRUE(&from != this)) {
    UnsafeMergeFrom(from);
  } else {
    MergeFromFail(__LINE__);
  }
}

void UiAction::UnsafeMergeFrom(const UiAction& from) {
  GOOGLE_DCHECK(&from != this);
  if (!from.unknown_fields().empty()) {
    mutable_unknown_fields()->append(from.unknown_fields());
  }
}

void UiAction::CopyFrom(const UiAction& from) {
// @@protoc_insertion_point(class_specific_copy_from_start:com.android.dialer.logging.UiAction)
  if (&from == this) return;
  Clear();
  UnsafeMergeFrom(from);
}

bool UiAction::IsInitialized() const {

  return true;
}

void UiAction::Swap(UiAction* other) {
  if (other == this) return;
  InternalSwap(other);
}
void UiAction::InternalSwap(UiAction* other) {
  _unknown_fields_.Swap(&other->_unknown_fields_);
  std::swap(_cached_size_, other->_cached_size_);
}

::std::string UiAction::GetTypeName() const {
  return "com.android.dialer.logging.UiAction";
}

#if PROTOBUF_INLINE_NOT_IN_HEADERS
// UiAction

inline const UiAction* UiAction::internal_default_instance() {
  return &UiAction_default_instance_.get();
}
#endif  // PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)

}  // namespace logging
}  // namespace dialer
}  // namespace android
}  // namespace com

// @@protoc_insertion_point(global_scope)
