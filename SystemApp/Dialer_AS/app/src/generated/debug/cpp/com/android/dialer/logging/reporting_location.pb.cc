// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: com/android/dialer/logging/reporting_location.proto

#define INTERNAL_SUPPRESS_PROTOBUF_FIELD_DEPRECATION
#include "com/android/dialer/logging/reporting_location.pb.h"

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

void protobuf_ShutdownFile_com_2fandroid_2fdialer_2flogging_2freporting_5flocation_2eproto() {
  ReportingLocation_default_instance_.Shutdown();
}

void protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2freporting_5flocation_2eproto_impl() {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  ::google::protobuf::internal::GetEmptyString();
  ReportingLocation_default_instance_.DefaultConstruct();
  ReportingLocation_default_instance_.get_mutable()->InitAsDefaultInstance();
}

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2freporting_5flocation_2eproto_once_);
void protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2freporting_5flocation_2eproto() {
  ::google::protobuf::GoogleOnceInit(&protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2freporting_5flocation_2eproto_once_,
                 &protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2freporting_5flocation_2eproto_impl);
}
void protobuf_AddDesc_com_2fandroid_2fdialer_2flogging_2freporting_5flocation_2eproto_impl() {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2freporting_5flocation_2eproto();
  ::google::protobuf::internal::OnShutdown(&protobuf_ShutdownFile_com_2fandroid_2fdialer_2flogging_2freporting_5flocation_2eproto);
}

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AddDesc_com_2fandroid_2fdialer_2flogging_2freporting_5flocation_2eproto_once_);
void protobuf_AddDesc_com_2fandroid_2fdialer_2flogging_2freporting_5flocation_2eproto() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AddDesc_com_2fandroid_2fdialer_2flogging_2freporting_5flocation_2eproto_once_,
                 &protobuf_AddDesc_com_2fandroid_2fdialer_2flogging_2freporting_5flocation_2eproto_impl);
}
#ifdef GOOGLE_PROTOBUF_NO_STATIC_INITIALIZER
// Force AddDescriptors() to be called at static initialization time.
struct StaticDescriptorInitializer_com_2fandroid_2fdialer_2flogging_2freporting_5flocation_2eproto {
  StaticDescriptorInitializer_com_2fandroid_2fdialer_2flogging_2freporting_5flocation_2eproto() {
    protobuf_AddDesc_com_2fandroid_2fdialer_2flogging_2freporting_5flocation_2eproto();
  }
} static_descriptor_initializer_com_2fandroid_2fdialer_2flogging_2freporting_5flocation_2eproto_;
#endif  // GOOGLE_PROTOBUF_NO_STATIC_INITIALIZER

namespace {

static void MergeFromFail(int line) GOOGLE_ATTRIBUTE_COLD GOOGLE_ATTRIBUTE_NORETURN;
static void MergeFromFail(int line) {
  ::google::protobuf::internal::MergeFromFail(__FILE__, line);
}

}  // namespace


// ===================================================================

static ::std::string* MutableUnknownFieldsForReportingLocation(
    ReportingLocation* ptr) {
  return ptr->mutable_unknown_fields();
}

bool ReportingLocation_Type_IsValid(int value) {
  switch (value) {
    case 0:
    case 1:
    case 2:
      return true;
    default:
      return false;
  }
}

#if !defined(_MSC_VER) || _MSC_VER >= 1900
const ReportingLocation_Type ReportingLocation::UNKNOWN_REPORTING_LOCATION;
const ReportingLocation_Type ReportingLocation::CALL_LOG_HISTORY;
const ReportingLocation_Type ReportingLocation::FEEDBACK_PROMPT;
const ReportingLocation_Type ReportingLocation::Type_MIN;
const ReportingLocation_Type ReportingLocation::Type_MAX;
const int ReportingLocation::Type_ARRAYSIZE;
#endif  // !defined(_MSC_VER) || _MSC_VER >= 1900
#if !defined(_MSC_VER) || _MSC_VER >= 1900
#endif  // !defined(_MSC_VER) || _MSC_VER >= 1900

ReportingLocation::ReportingLocation()
  : ::google::protobuf::MessageLite(), _arena_ptr_(NULL) {
  if (this != internal_default_instance()) protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2freporting_5flocation_2eproto();
  SharedCtor();
  // @@protoc_insertion_point(constructor:com.android.dialer.logging.ReportingLocation)
}

void ReportingLocation::InitAsDefaultInstance() {
}

ReportingLocation::ReportingLocation(const ReportingLocation& from)
  : ::google::protobuf::MessageLite(),
    _arena_ptr_(NULL) {
  SharedCtor();
  UnsafeMergeFrom(from);
  // @@protoc_insertion_point(copy_constructor:com.android.dialer.logging.ReportingLocation)
}

void ReportingLocation::SharedCtor() {
  _cached_size_ = 0;
  _unknown_fields_.UnsafeSetDefault(
      &::google::protobuf::internal::GetEmptyStringAlreadyInited());
}

ReportingLocation::~ReportingLocation() {
  // @@protoc_insertion_point(destructor:com.android.dialer.logging.ReportingLocation)
  SharedDtor();
}

void ReportingLocation::SharedDtor() {
  _unknown_fields_.DestroyNoArena(
      &::google::protobuf::internal::GetEmptyStringAlreadyInited());
}

void ReportingLocation::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const ReportingLocation& ReportingLocation::default_instance() {
  protobuf_InitDefaults_com_2fandroid_2fdialer_2flogging_2freporting_5flocation_2eproto();
  return *internal_default_instance();
}

::google::protobuf::internal::ExplicitlyConstructed<ReportingLocation> ReportingLocation_default_instance_;

ReportingLocation* ReportingLocation::New(::google::protobuf::Arena* arena) const {
  ReportingLocation* n = new ReportingLocation;
  if (arena != NULL) {
    arena->Own(n);
  }
  return n;
}

void ReportingLocation::Clear() {
// @@protoc_insertion_point(message_clear_start:com.android.dialer.logging.ReportingLocation)
  _has_bits_.Clear();
  _unknown_fields_.ClearToEmptyNoArena(
      &::google::protobuf::internal::GetEmptyStringAlreadyInited());
}

bool ReportingLocation::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!GOOGLE_PREDICT_TRUE(EXPRESSION)) goto failure
  ::google::protobuf::uint32 tag;
  ::google::protobuf::io::LazyStringOutputStream unknown_fields_string(
      ::google::protobuf::NewPermanentCallback(
          &MutableUnknownFieldsForReportingLocation, this));
  ::google::protobuf::io::CodedOutputStream unknown_fields_stream(
      &unknown_fields_string, false);
  // @@protoc_insertion_point(parse_start:com.android.dialer.logging.ReportingLocation)
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
  // @@protoc_insertion_point(parse_success:com.android.dialer.logging.ReportingLocation)
  return true;
failure:
  // @@protoc_insertion_point(parse_failure:com.android.dialer.logging.ReportingLocation)
  return false;
#undef DO_
}

void ReportingLocation::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // @@protoc_insertion_point(serialize_start:com.android.dialer.logging.ReportingLocation)
  output->WriteRaw(unknown_fields().data(),
                   static_cast<int>(unknown_fields().size()));
  // @@protoc_insertion_point(serialize_end:com.android.dialer.logging.ReportingLocation)
}

size_t ReportingLocation::ByteSizeLong() const {
// @@protoc_insertion_point(message_byte_size_start:com.android.dialer.logging.ReportingLocation)
  size_t total_size = 0;

  total_size += unknown_fields().size();

  int cached_size = ::google::protobuf::internal::ToCachedSize(total_size);
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = cached_size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
  return total_size;
}

void ReportingLocation::CheckTypeAndMergeFrom(
    const ::google::protobuf::MessageLite& from) {
  MergeFrom(*::google::protobuf::down_cast<const ReportingLocation*>(&from));
}

void ReportingLocation::MergeFrom(const ReportingLocation& from) {
// @@protoc_insertion_point(class_specific_merge_from_start:com.android.dialer.logging.ReportingLocation)
  if (GOOGLE_PREDICT_TRUE(&from != this)) {
    UnsafeMergeFrom(from);
  } else {
    MergeFromFail(__LINE__);
  }
}

void ReportingLocation::UnsafeMergeFrom(const ReportingLocation& from) {
  GOOGLE_DCHECK(&from != this);
  if (!from.unknown_fields().empty()) {
    mutable_unknown_fields()->append(from.unknown_fields());
  }
}

void ReportingLocation::CopyFrom(const ReportingLocation& from) {
// @@protoc_insertion_point(class_specific_copy_from_start:com.android.dialer.logging.ReportingLocation)
  if (&from == this) return;
  Clear();
  UnsafeMergeFrom(from);
}

bool ReportingLocation::IsInitialized() const {

  return true;
}

void ReportingLocation::Swap(ReportingLocation* other) {
  if (other == this) return;
  InternalSwap(other);
}
void ReportingLocation::InternalSwap(ReportingLocation* other) {
  _unknown_fields_.Swap(&other->_unknown_fields_);
  std::swap(_cached_size_, other->_cached_size_);
}

::std::string ReportingLocation::GetTypeName() const {
  return "com.android.dialer.logging.ReportingLocation";
}

#if PROTOBUF_INLINE_NOT_IN_HEADERS
// ReportingLocation

inline const ReportingLocation* ReportingLocation::internal_default_instance() {
  return &ReportingLocation_default_instance_.get();
}
#endif  // PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)

}  // namespace logging
}  // namespace dialer
}  // namespace android
}  // namespace com

// @@protoc_insertion_point(global_scope)
