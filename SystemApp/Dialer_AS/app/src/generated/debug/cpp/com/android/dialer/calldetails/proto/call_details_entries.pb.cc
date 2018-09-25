// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: com/android/dialer/calldetails/proto/call_details_entries.proto

#define INTERNAL_SUPPRESS_PROTOBUF_FIELD_DEPRECATION
#include "com/android/dialer/calldetails/proto/call_details_entries.pb.h"

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
namespace calldetails {

void protobuf_ShutdownFile_com_2fandroid_2fdialer_2fcalldetails_2fproto_2fcall_5fdetails_5fentries_2eproto() {
  CallDetailsEntries_default_instance_.Shutdown();
  CallDetailsEntries_CallDetailsEntry_default_instance_.Shutdown();
}

void protobuf_InitDefaults_com_2fandroid_2fdialer_2fcalldetails_2fproto_2fcall_5fdetails_5fentries_2eproto_impl() {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  ::com::android::dialer::enrichedcall::historyquery::proto::protobuf_InitDefaults_com_2fandroid_2fdialer_2fenrichedcall_2fhistoryquery_2fproto_2fhistory_5fresult_2eproto();
  ::google::protobuf::internal::GetEmptyString();
  CallDetailsEntries_default_instance_.DefaultConstruct();
  ::google::protobuf::internal::GetEmptyString();
  CallDetailsEntries_CallDetailsEntry_default_instance_.DefaultConstruct();
  CallDetailsEntries_default_instance_.get_mutable()->InitAsDefaultInstance();
  CallDetailsEntries_CallDetailsEntry_default_instance_.get_mutable()->InitAsDefaultInstance();
}

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_InitDefaults_com_2fandroid_2fdialer_2fcalldetails_2fproto_2fcall_5fdetails_5fentries_2eproto_once_);
void protobuf_InitDefaults_com_2fandroid_2fdialer_2fcalldetails_2fproto_2fcall_5fdetails_5fentries_2eproto() {
  ::google::protobuf::GoogleOnceInit(&protobuf_InitDefaults_com_2fandroid_2fdialer_2fcalldetails_2fproto_2fcall_5fdetails_5fentries_2eproto_once_,
                 &protobuf_InitDefaults_com_2fandroid_2fdialer_2fcalldetails_2fproto_2fcall_5fdetails_5fentries_2eproto_impl);
}
void protobuf_AddDesc_com_2fandroid_2fdialer_2fcalldetails_2fproto_2fcall_5fdetails_5fentries_2eproto_impl() {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  protobuf_InitDefaults_com_2fandroid_2fdialer_2fcalldetails_2fproto_2fcall_5fdetails_5fentries_2eproto();
  ::com::android::dialer::enrichedcall::historyquery::proto::protobuf_AddDesc_com_2fandroid_2fdialer_2fenrichedcall_2fhistoryquery_2fproto_2fhistory_5fresult_2eproto();
  ::google::protobuf::internal::OnShutdown(&protobuf_ShutdownFile_com_2fandroid_2fdialer_2fcalldetails_2fproto_2fcall_5fdetails_5fentries_2eproto);
}

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AddDesc_com_2fandroid_2fdialer_2fcalldetails_2fproto_2fcall_5fdetails_5fentries_2eproto_once_);
void protobuf_AddDesc_com_2fandroid_2fdialer_2fcalldetails_2fproto_2fcall_5fdetails_5fentries_2eproto() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AddDesc_com_2fandroid_2fdialer_2fcalldetails_2fproto_2fcall_5fdetails_5fentries_2eproto_once_,
                 &protobuf_AddDesc_com_2fandroid_2fdialer_2fcalldetails_2fproto_2fcall_5fdetails_5fentries_2eproto_impl);
}
#ifdef GOOGLE_PROTOBUF_NO_STATIC_INITIALIZER
// Force AddDescriptors() to be called at static initialization time.
struct StaticDescriptorInitializer_com_2fandroid_2fdialer_2fcalldetails_2fproto_2fcall_5fdetails_5fentries_2eproto {
  StaticDescriptorInitializer_com_2fandroid_2fdialer_2fcalldetails_2fproto_2fcall_5fdetails_5fentries_2eproto() {
    protobuf_AddDesc_com_2fandroid_2fdialer_2fcalldetails_2fproto_2fcall_5fdetails_5fentries_2eproto();
  }
} static_descriptor_initializer_com_2fandroid_2fdialer_2fcalldetails_2fproto_2fcall_5fdetails_5fentries_2eproto_;
#endif  // GOOGLE_PROTOBUF_NO_STATIC_INITIALIZER

namespace {

static void MergeFromFail(int line) GOOGLE_ATTRIBUTE_COLD GOOGLE_ATTRIBUTE_NORETURN;
static void MergeFromFail(int line) {
  ::google::protobuf::internal::MergeFromFail(__FILE__, line);
}

}  // namespace


// ===================================================================

static ::std::string* MutableUnknownFieldsForCallDetailsEntries(
    CallDetailsEntries* ptr) {
  return ptr->mutable_unknown_fields();
}

static ::std::string* MutableUnknownFieldsForCallDetailsEntries_CallDetailsEntry(
    CallDetailsEntries_CallDetailsEntry* ptr) {
  return ptr->mutable_unknown_fields();
}

#if !defined(_MSC_VER) || _MSC_VER >= 1900
const int CallDetailsEntries_CallDetailsEntry::kCallIdFieldNumber;
const int CallDetailsEntries_CallDetailsEntry::kCallTypeFieldNumber;
const int CallDetailsEntries_CallDetailsEntry::kFeaturesFieldNumber;
const int CallDetailsEntries_CallDetailsEntry::kDateFieldNumber;
const int CallDetailsEntries_CallDetailsEntry::kDurationFieldNumber;
const int CallDetailsEntries_CallDetailsEntry::kDataUsageFieldNumber;
const int CallDetailsEntries_CallDetailsEntry::kHistoryResultsFieldNumber;
#endif  // !defined(_MSC_VER) || _MSC_VER >= 1900

CallDetailsEntries_CallDetailsEntry::CallDetailsEntries_CallDetailsEntry()
  : ::google::protobuf::MessageLite(), _arena_ptr_(NULL) {
  if (this != internal_default_instance()) protobuf_InitDefaults_com_2fandroid_2fdialer_2fcalldetails_2fproto_2fcall_5fdetails_5fentries_2eproto();
  SharedCtor();
  // @@protoc_insertion_point(constructor:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry)
}

void CallDetailsEntries_CallDetailsEntry::InitAsDefaultInstance() {
}

CallDetailsEntries_CallDetailsEntry::CallDetailsEntries_CallDetailsEntry(const CallDetailsEntries_CallDetailsEntry& from)
  : ::google::protobuf::MessageLite(),
    _arena_ptr_(NULL) {
  SharedCtor();
  UnsafeMergeFrom(from);
  // @@protoc_insertion_point(copy_constructor:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry)
}

void CallDetailsEntries_CallDetailsEntry::SharedCtor() {
  _cached_size_ = 0;
  _unknown_fields_.UnsafeSetDefault(
      &::google::protobuf::internal::GetEmptyStringAlreadyInited());
  ::memset(&call_id_, 0, reinterpret_cast<char*>(&data_usage_) -
    reinterpret_cast<char*>(&call_id_) + sizeof(data_usage_));
}

CallDetailsEntries_CallDetailsEntry::~CallDetailsEntries_CallDetailsEntry() {
  // @@protoc_insertion_point(destructor:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry)
  SharedDtor();
}

void CallDetailsEntries_CallDetailsEntry::SharedDtor() {
  _unknown_fields_.DestroyNoArena(
      &::google::protobuf::internal::GetEmptyStringAlreadyInited());
}

void CallDetailsEntries_CallDetailsEntry::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const CallDetailsEntries_CallDetailsEntry& CallDetailsEntries_CallDetailsEntry::default_instance() {
  protobuf_InitDefaults_com_2fandroid_2fdialer_2fcalldetails_2fproto_2fcall_5fdetails_5fentries_2eproto();
  return *internal_default_instance();
}

::google::protobuf::internal::ExplicitlyConstructed<CallDetailsEntries_CallDetailsEntry> CallDetailsEntries_CallDetailsEntry_default_instance_;

CallDetailsEntries_CallDetailsEntry* CallDetailsEntries_CallDetailsEntry::New(::google::protobuf::Arena* arena) const {
  CallDetailsEntries_CallDetailsEntry* n = new CallDetailsEntries_CallDetailsEntry;
  if (arena != NULL) {
    arena->Own(n);
  }
  return n;
}

void CallDetailsEntries_CallDetailsEntry::Clear() {
// @@protoc_insertion_point(message_clear_start:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry)
#if defined(__clang__)
#define ZR_HELPER_(f) \
  _Pragma("clang diagnostic push") \
  _Pragma("clang diagnostic ignored \"-Winvalid-offsetof\"") \
  __builtin_offsetof(CallDetailsEntries_CallDetailsEntry, f) \
  _Pragma("clang diagnostic pop")
#else
#define ZR_HELPER_(f) reinterpret_cast<char*>(\
  &reinterpret_cast<CallDetailsEntries_CallDetailsEntry*>(16)->f)
#endif

#define ZR_(first, last) do {\
  ::memset(&(first), 0,\
           ZR_HELPER_(last) - ZR_HELPER_(first) + sizeof(last));\
} while (0)

  ZR_(call_id_, data_usage_);

#undef ZR_HELPER_
#undef ZR_

  history_results_.Clear();
  _has_bits_.Clear();
  _unknown_fields_.ClearToEmptyNoArena(
      &::google::protobuf::internal::GetEmptyStringAlreadyInited());
}

bool CallDetailsEntries_CallDetailsEntry::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!GOOGLE_PREDICT_TRUE(EXPRESSION)) goto failure
  ::google::protobuf::uint32 tag;
  ::google::protobuf::io::LazyStringOutputStream unknown_fields_string(
      ::google::protobuf::NewPermanentCallback(
          &MutableUnknownFieldsForCallDetailsEntries_CallDetailsEntry, this));
  ::google::protobuf::io::CodedOutputStream unknown_fields_stream(
      &unknown_fields_string, false);
  // @@protoc_insertion_point(parse_start:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry)
  for (;;) {
    ::std::pair< ::google::protobuf::uint32, bool> p = input->ReadTagWithCutoff(127);
    tag = p.first;
    if (!p.second) goto handle_unusual;
    switch (::google::protobuf::internal::WireFormatLite::GetTagFieldNumber(tag)) {
      // optional int64 call_id = 1;
      case 1: {
        if (tag == 8) {
          set_has_call_id();
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int64, ::google::protobuf::internal::WireFormatLite::TYPE_INT64>(
                 input, &call_id_)));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(16)) goto parse_call_type;
        break;
      }

      // optional int32 call_type = 2;
      case 2: {
        if (tag == 16) {
         parse_call_type:
          set_has_call_type();
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int32, ::google::protobuf::internal::WireFormatLite::TYPE_INT32>(
                 input, &call_type_)));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(24)) goto parse_features;
        break;
      }

      // optional int32 features = 3;
      case 3: {
        if (tag == 24) {
         parse_features:
          set_has_features();
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int32, ::google::protobuf::internal::WireFormatLite::TYPE_INT32>(
                 input, &features_)));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(32)) goto parse_date;
        break;
      }

      // optional int64 date = 4;
      case 4: {
        if (tag == 32) {
         parse_date:
          set_has_date();
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int64, ::google::protobuf::internal::WireFormatLite::TYPE_INT64>(
                 input, &date_)));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(40)) goto parse_duration;
        break;
      }

      // optional int64 duration = 5;
      case 5: {
        if (tag == 40) {
         parse_duration:
          set_has_duration();
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int64, ::google::protobuf::internal::WireFormatLite::TYPE_INT64>(
                 input, &duration_)));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(48)) goto parse_data_usage;
        break;
      }

      // optional int64 data_usage = 6;
      case 6: {
        if (tag == 48) {
         parse_data_usage:
          set_has_data_usage();
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int64, ::google::protobuf::internal::WireFormatLite::TYPE_INT64>(
                 input, &data_usage_)));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(58)) goto parse_history_results;
        break;
      }

      // repeated .com.android.dialer.enrichedcall.historyquery.proto.HistoryResult history_results = 7;
      case 7: {
        if (tag == 58) {
         parse_history_results:
          DO_(input->IncrementRecursionDepth());
         parse_loop_history_results:
          DO_(::google::protobuf::internal::WireFormatLite::ReadMessageNoVirtualNoRecursionDepth(
                input, add_history_results()));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(58)) goto parse_loop_history_results;
        input->UnsafeDecrementRecursionDepth();
        if (input->ExpectAtEnd()) goto success;
        break;
      }

      default: {
      handle_unusual:
        if (tag == 0 ||
            ::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_END_GROUP) {
          goto success;
        }
        DO_(::google::protobuf::internal::WireFormatLite::SkipField(
            input, tag, &unknown_fields_stream));
        break;
      }
    }
  }
success:
  // @@protoc_insertion_point(parse_success:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry)
  return true;
failure:
  // @@protoc_insertion_point(parse_failure:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry)
  return false;
#undef DO_
}

void CallDetailsEntries_CallDetailsEntry::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // @@protoc_insertion_point(serialize_start:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry)
  // optional int64 call_id = 1;
  if (has_call_id()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt64(1, this->call_id(), output);
  }

  // optional int32 call_type = 2;
  if (has_call_type()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt32(2, this->call_type(), output);
  }

  // optional int32 features = 3;
  if (has_features()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt32(3, this->features(), output);
  }

  // optional int64 date = 4;
  if (has_date()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt64(4, this->date(), output);
  }

  // optional int64 duration = 5;
  if (has_duration()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt64(5, this->duration(), output);
  }

  // optional int64 data_usage = 6;
  if (has_data_usage()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt64(6, this->data_usage(), output);
  }

  // repeated .com.android.dialer.enrichedcall.historyquery.proto.HistoryResult history_results = 7;
  for (unsigned int i = 0, n = this->history_results_size(); i < n; i++) {
    ::google::protobuf::internal::WireFormatLite::WriteMessage(
      7, this->history_results(i), output);
  }

  output->WriteRaw(unknown_fields().data(),
                   static_cast<int>(unknown_fields().size()));
  // @@protoc_insertion_point(serialize_end:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry)
}

size_t CallDetailsEntries_CallDetailsEntry::ByteSizeLong() const {
// @@protoc_insertion_point(message_byte_size_start:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry)
  size_t total_size = 0;

  if (_has_bits_[0 / 32] & 63u) {
    // optional int64 call_id = 1;
    if (has_call_id()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::Int64Size(
          this->call_id());
    }

    // optional int32 call_type = 2;
    if (has_call_type()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::Int32Size(
          this->call_type());
    }

    // optional int32 features = 3;
    if (has_features()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::Int32Size(
          this->features());
    }

    // optional int64 date = 4;
    if (has_date()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::Int64Size(
          this->date());
    }

    // optional int64 duration = 5;
    if (has_duration()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::Int64Size(
          this->duration());
    }

    // optional int64 data_usage = 6;
    if (has_data_usage()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::Int64Size(
          this->data_usage());
    }

  }
  // repeated .com.android.dialer.enrichedcall.historyquery.proto.HistoryResult history_results = 7;
  {
    unsigned int count = this->history_results_size();
    total_size += 1UL * count;
    for (unsigned int i = 0; i < count; i++) {
      total_size +=
        ::google::protobuf::internal::WireFormatLite::MessageSizeNoVirtual(
          this->history_results(i));
    }
  }

  total_size += unknown_fields().size();

  int cached_size = ::google::protobuf::internal::ToCachedSize(total_size);
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = cached_size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
  return total_size;
}

void CallDetailsEntries_CallDetailsEntry::CheckTypeAndMergeFrom(
    const ::google::protobuf::MessageLite& from) {
  MergeFrom(*::google::protobuf::down_cast<const CallDetailsEntries_CallDetailsEntry*>(&from));
}

void CallDetailsEntries_CallDetailsEntry::MergeFrom(const CallDetailsEntries_CallDetailsEntry& from) {
// @@protoc_insertion_point(class_specific_merge_from_start:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry)
  if (GOOGLE_PREDICT_TRUE(&from != this)) {
    UnsafeMergeFrom(from);
  } else {
    MergeFromFail(__LINE__);
  }
}

void CallDetailsEntries_CallDetailsEntry::UnsafeMergeFrom(const CallDetailsEntries_CallDetailsEntry& from) {
  GOOGLE_DCHECK(&from != this);
  history_results_.MergeFrom(from.history_results_);
  if (from._has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    if (from.has_call_id()) {
      set_call_id(from.call_id());
    }
    if (from.has_call_type()) {
      set_call_type(from.call_type());
    }
    if (from.has_features()) {
      set_features(from.features());
    }
    if (from.has_date()) {
      set_date(from.date());
    }
    if (from.has_duration()) {
      set_duration(from.duration());
    }
    if (from.has_data_usage()) {
      set_data_usage(from.data_usage());
    }
  }
  if (!from.unknown_fields().empty()) {
    mutable_unknown_fields()->append(from.unknown_fields());
  }
}

void CallDetailsEntries_CallDetailsEntry::CopyFrom(const CallDetailsEntries_CallDetailsEntry& from) {
// @@protoc_insertion_point(class_specific_copy_from_start:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry)
  if (&from == this) return;
  Clear();
  UnsafeMergeFrom(from);
}

bool CallDetailsEntries_CallDetailsEntry::IsInitialized() const {

  return true;
}

void CallDetailsEntries_CallDetailsEntry::Swap(CallDetailsEntries_CallDetailsEntry* other) {
  if (other == this) return;
  InternalSwap(other);
}
void CallDetailsEntries_CallDetailsEntry::InternalSwap(CallDetailsEntries_CallDetailsEntry* other) {
  std::swap(call_id_, other->call_id_);
  std::swap(call_type_, other->call_type_);
  std::swap(features_, other->features_);
  std::swap(date_, other->date_);
  std::swap(duration_, other->duration_);
  std::swap(data_usage_, other->data_usage_);
  history_results_.UnsafeArenaSwap(&other->history_results_);
  std::swap(_has_bits_[0], other->_has_bits_[0]);
  _unknown_fields_.Swap(&other->_unknown_fields_);
  std::swap(_cached_size_, other->_cached_size_);
}

::std::string CallDetailsEntries_CallDetailsEntry::GetTypeName() const {
  return "com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry";
}


// -------------------------------------------------------------------

#if !defined(_MSC_VER) || _MSC_VER >= 1900
const int CallDetailsEntries::kEntriesFieldNumber;
#endif  // !defined(_MSC_VER) || _MSC_VER >= 1900

CallDetailsEntries::CallDetailsEntries()
  : ::google::protobuf::MessageLite(), _arena_ptr_(NULL) {
  if (this != internal_default_instance()) protobuf_InitDefaults_com_2fandroid_2fdialer_2fcalldetails_2fproto_2fcall_5fdetails_5fentries_2eproto();
  SharedCtor();
  // @@protoc_insertion_point(constructor:com.android.dialer.calldetails.CallDetailsEntries)
}

void CallDetailsEntries::InitAsDefaultInstance() {
}

CallDetailsEntries::CallDetailsEntries(const CallDetailsEntries& from)
  : ::google::protobuf::MessageLite(),
    _arena_ptr_(NULL) {
  SharedCtor();
  UnsafeMergeFrom(from);
  // @@protoc_insertion_point(copy_constructor:com.android.dialer.calldetails.CallDetailsEntries)
}

void CallDetailsEntries::SharedCtor() {
  _cached_size_ = 0;
  _unknown_fields_.UnsafeSetDefault(
      &::google::protobuf::internal::GetEmptyStringAlreadyInited());
}

CallDetailsEntries::~CallDetailsEntries() {
  // @@protoc_insertion_point(destructor:com.android.dialer.calldetails.CallDetailsEntries)
  SharedDtor();
}

void CallDetailsEntries::SharedDtor() {
  _unknown_fields_.DestroyNoArena(
      &::google::protobuf::internal::GetEmptyStringAlreadyInited());
}

void CallDetailsEntries::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const CallDetailsEntries& CallDetailsEntries::default_instance() {
  protobuf_InitDefaults_com_2fandroid_2fdialer_2fcalldetails_2fproto_2fcall_5fdetails_5fentries_2eproto();
  return *internal_default_instance();
}

::google::protobuf::internal::ExplicitlyConstructed<CallDetailsEntries> CallDetailsEntries_default_instance_;

CallDetailsEntries* CallDetailsEntries::New(::google::protobuf::Arena* arena) const {
  CallDetailsEntries* n = new CallDetailsEntries;
  if (arena != NULL) {
    arena->Own(n);
  }
  return n;
}

void CallDetailsEntries::Clear() {
// @@protoc_insertion_point(message_clear_start:com.android.dialer.calldetails.CallDetailsEntries)
  entries_.Clear();
  _has_bits_.Clear();
  _unknown_fields_.ClearToEmptyNoArena(
      &::google::protobuf::internal::GetEmptyStringAlreadyInited());
}

bool CallDetailsEntries::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!GOOGLE_PREDICT_TRUE(EXPRESSION)) goto failure
  ::google::protobuf::uint32 tag;
  ::google::protobuf::io::LazyStringOutputStream unknown_fields_string(
      ::google::protobuf::NewPermanentCallback(
          &MutableUnknownFieldsForCallDetailsEntries, this));
  ::google::protobuf::io::CodedOutputStream unknown_fields_stream(
      &unknown_fields_string, false);
  // @@protoc_insertion_point(parse_start:com.android.dialer.calldetails.CallDetailsEntries)
  for (;;) {
    ::std::pair< ::google::protobuf::uint32, bool> p = input->ReadTagWithCutoff(127);
    tag = p.first;
    if (!p.second) goto handle_unusual;
    switch (::google::protobuf::internal::WireFormatLite::GetTagFieldNumber(tag)) {
      // repeated .com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry entries = 1;
      case 1: {
        if (tag == 10) {
          DO_(input->IncrementRecursionDepth());
         parse_loop_entries:
          DO_(::google::protobuf::internal::WireFormatLite::ReadMessageNoVirtualNoRecursionDepth(
                input, add_entries()));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(10)) goto parse_loop_entries;
        input->UnsafeDecrementRecursionDepth();
        if (input->ExpectAtEnd()) goto success;
        break;
      }

      default: {
      handle_unusual:
        if (tag == 0 ||
            ::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_END_GROUP) {
          goto success;
        }
        DO_(::google::protobuf::internal::WireFormatLite::SkipField(
            input, tag, &unknown_fields_stream));
        break;
      }
    }
  }
success:
  // @@protoc_insertion_point(parse_success:com.android.dialer.calldetails.CallDetailsEntries)
  return true;
failure:
  // @@protoc_insertion_point(parse_failure:com.android.dialer.calldetails.CallDetailsEntries)
  return false;
#undef DO_
}

void CallDetailsEntries::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // @@protoc_insertion_point(serialize_start:com.android.dialer.calldetails.CallDetailsEntries)
  // repeated .com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry entries = 1;
  for (unsigned int i = 0, n = this->entries_size(); i < n; i++) {
    ::google::protobuf::internal::WireFormatLite::WriteMessage(
      1, this->entries(i), output);
  }

  output->WriteRaw(unknown_fields().data(),
                   static_cast<int>(unknown_fields().size()));
  // @@protoc_insertion_point(serialize_end:com.android.dialer.calldetails.CallDetailsEntries)
}

size_t CallDetailsEntries::ByteSizeLong() const {
// @@protoc_insertion_point(message_byte_size_start:com.android.dialer.calldetails.CallDetailsEntries)
  size_t total_size = 0;

  // repeated .com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry entries = 1;
  {
    unsigned int count = this->entries_size();
    total_size += 1UL * count;
    for (unsigned int i = 0; i < count; i++) {
      total_size +=
        ::google::protobuf::internal::WireFormatLite::MessageSizeNoVirtual(
          this->entries(i));
    }
  }

  total_size += unknown_fields().size();

  int cached_size = ::google::protobuf::internal::ToCachedSize(total_size);
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = cached_size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
  return total_size;
}

void CallDetailsEntries::CheckTypeAndMergeFrom(
    const ::google::protobuf::MessageLite& from) {
  MergeFrom(*::google::protobuf::down_cast<const CallDetailsEntries*>(&from));
}

void CallDetailsEntries::MergeFrom(const CallDetailsEntries& from) {
// @@protoc_insertion_point(class_specific_merge_from_start:com.android.dialer.calldetails.CallDetailsEntries)
  if (GOOGLE_PREDICT_TRUE(&from != this)) {
    UnsafeMergeFrom(from);
  } else {
    MergeFromFail(__LINE__);
  }
}

void CallDetailsEntries::UnsafeMergeFrom(const CallDetailsEntries& from) {
  GOOGLE_DCHECK(&from != this);
  entries_.MergeFrom(from.entries_);
  if (!from.unknown_fields().empty()) {
    mutable_unknown_fields()->append(from.unknown_fields());
  }
}

void CallDetailsEntries::CopyFrom(const CallDetailsEntries& from) {
// @@protoc_insertion_point(class_specific_copy_from_start:com.android.dialer.calldetails.CallDetailsEntries)
  if (&from == this) return;
  Clear();
  UnsafeMergeFrom(from);
}

bool CallDetailsEntries::IsInitialized() const {

  return true;
}

void CallDetailsEntries::Swap(CallDetailsEntries* other) {
  if (other == this) return;
  InternalSwap(other);
}
void CallDetailsEntries::InternalSwap(CallDetailsEntries* other) {
  entries_.UnsafeArenaSwap(&other->entries_);
  std::swap(_has_bits_[0], other->_has_bits_[0]);
  _unknown_fields_.Swap(&other->_unknown_fields_);
  std::swap(_cached_size_, other->_cached_size_);
}

::std::string CallDetailsEntries::GetTypeName() const {
  return "com.android.dialer.calldetails.CallDetailsEntries";
}

#if PROTOBUF_INLINE_NOT_IN_HEADERS
// CallDetailsEntries_CallDetailsEntry

// optional int64 call_id = 1;
bool CallDetailsEntries_CallDetailsEntry::has_call_id() const {
  return (_has_bits_[0] & 0x00000001u) != 0;
}
void CallDetailsEntries_CallDetailsEntry::set_has_call_id() {
  _has_bits_[0] |= 0x00000001u;
}
void CallDetailsEntries_CallDetailsEntry::clear_has_call_id() {
  _has_bits_[0] &= ~0x00000001u;
}
void CallDetailsEntries_CallDetailsEntry::clear_call_id() {
  call_id_ = GOOGLE_LONGLONG(0);
  clear_has_call_id();
}
::google::protobuf::int64 CallDetailsEntries_CallDetailsEntry::call_id() const {
  // @@protoc_insertion_point(field_get:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry.call_id)
  return call_id_;
}
void CallDetailsEntries_CallDetailsEntry::set_call_id(::google::protobuf::int64 value) {
  set_has_call_id();
  call_id_ = value;
  // @@protoc_insertion_point(field_set:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry.call_id)
}

// optional int32 call_type = 2;
bool CallDetailsEntries_CallDetailsEntry::has_call_type() const {
  return (_has_bits_[0] & 0x00000002u) != 0;
}
void CallDetailsEntries_CallDetailsEntry::set_has_call_type() {
  _has_bits_[0] |= 0x00000002u;
}
void CallDetailsEntries_CallDetailsEntry::clear_has_call_type() {
  _has_bits_[0] &= ~0x00000002u;
}
void CallDetailsEntries_CallDetailsEntry::clear_call_type() {
  call_type_ = 0;
  clear_has_call_type();
}
::google::protobuf::int32 CallDetailsEntries_CallDetailsEntry::call_type() const {
  // @@protoc_insertion_point(field_get:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry.call_type)
  return call_type_;
}
void CallDetailsEntries_CallDetailsEntry::set_call_type(::google::protobuf::int32 value) {
  set_has_call_type();
  call_type_ = value;
  // @@protoc_insertion_point(field_set:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry.call_type)
}

// optional int32 features = 3;
bool CallDetailsEntries_CallDetailsEntry::has_features() const {
  return (_has_bits_[0] & 0x00000004u) != 0;
}
void CallDetailsEntries_CallDetailsEntry::set_has_features() {
  _has_bits_[0] |= 0x00000004u;
}
void CallDetailsEntries_CallDetailsEntry::clear_has_features() {
  _has_bits_[0] &= ~0x00000004u;
}
void CallDetailsEntries_CallDetailsEntry::clear_features() {
  features_ = 0;
  clear_has_features();
}
::google::protobuf::int32 CallDetailsEntries_CallDetailsEntry::features() const {
  // @@protoc_insertion_point(field_get:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry.features)
  return features_;
}
void CallDetailsEntries_CallDetailsEntry::set_features(::google::protobuf::int32 value) {
  set_has_features();
  features_ = value;
  // @@protoc_insertion_point(field_set:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry.features)
}

// optional int64 date = 4;
bool CallDetailsEntries_CallDetailsEntry::has_date() const {
  return (_has_bits_[0] & 0x00000008u) != 0;
}
void CallDetailsEntries_CallDetailsEntry::set_has_date() {
  _has_bits_[0] |= 0x00000008u;
}
void CallDetailsEntries_CallDetailsEntry::clear_has_date() {
  _has_bits_[0] &= ~0x00000008u;
}
void CallDetailsEntries_CallDetailsEntry::clear_date() {
  date_ = GOOGLE_LONGLONG(0);
  clear_has_date();
}
::google::protobuf::int64 CallDetailsEntries_CallDetailsEntry::date() const {
  // @@protoc_insertion_point(field_get:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry.date)
  return date_;
}
void CallDetailsEntries_CallDetailsEntry::set_date(::google::protobuf::int64 value) {
  set_has_date();
  date_ = value;
  // @@protoc_insertion_point(field_set:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry.date)
}

// optional int64 duration = 5;
bool CallDetailsEntries_CallDetailsEntry::has_duration() const {
  return (_has_bits_[0] & 0x00000010u) != 0;
}
void CallDetailsEntries_CallDetailsEntry::set_has_duration() {
  _has_bits_[0] |= 0x00000010u;
}
void CallDetailsEntries_CallDetailsEntry::clear_has_duration() {
  _has_bits_[0] &= ~0x00000010u;
}
void CallDetailsEntries_CallDetailsEntry::clear_duration() {
  duration_ = GOOGLE_LONGLONG(0);
  clear_has_duration();
}
::google::protobuf::int64 CallDetailsEntries_CallDetailsEntry::duration() const {
  // @@protoc_insertion_point(field_get:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry.duration)
  return duration_;
}
void CallDetailsEntries_CallDetailsEntry::set_duration(::google::protobuf::int64 value) {
  set_has_duration();
  duration_ = value;
  // @@protoc_insertion_point(field_set:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry.duration)
}

// optional int64 data_usage = 6;
bool CallDetailsEntries_CallDetailsEntry::has_data_usage() const {
  return (_has_bits_[0] & 0x00000020u) != 0;
}
void CallDetailsEntries_CallDetailsEntry::set_has_data_usage() {
  _has_bits_[0] |= 0x00000020u;
}
void CallDetailsEntries_CallDetailsEntry::clear_has_data_usage() {
  _has_bits_[0] &= ~0x00000020u;
}
void CallDetailsEntries_CallDetailsEntry::clear_data_usage() {
  data_usage_ = GOOGLE_LONGLONG(0);
  clear_has_data_usage();
}
::google::protobuf::int64 CallDetailsEntries_CallDetailsEntry::data_usage() const {
  // @@protoc_insertion_point(field_get:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry.data_usage)
  return data_usage_;
}
void CallDetailsEntries_CallDetailsEntry::set_data_usage(::google::protobuf::int64 value) {
  set_has_data_usage();
  data_usage_ = value;
  // @@protoc_insertion_point(field_set:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry.data_usage)
}

// repeated .com.android.dialer.enrichedcall.historyquery.proto.HistoryResult history_results = 7;
int CallDetailsEntries_CallDetailsEntry::history_results_size() const {
  return history_results_.size();
}
void CallDetailsEntries_CallDetailsEntry::clear_history_results() {
  history_results_.Clear();
}
const ::com::android::dialer::enrichedcall::historyquery::proto::HistoryResult& CallDetailsEntries_CallDetailsEntry::history_results(int index) const {
  // @@protoc_insertion_point(field_get:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry.history_results)
  return history_results_.Get(index);
}
::com::android::dialer::enrichedcall::historyquery::proto::HistoryResult* CallDetailsEntries_CallDetailsEntry::mutable_history_results(int index) {
  // @@protoc_insertion_point(field_mutable:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry.history_results)
  return history_results_.Mutable(index);
}
::com::android::dialer::enrichedcall::historyquery::proto::HistoryResult* CallDetailsEntries_CallDetailsEntry::add_history_results() {
  // @@protoc_insertion_point(field_add:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry.history_results)
  return history_results_.Add();
}
::google::protobuf::RepeatedPtrField< ::com::android::dialer::enrichedcall::historyquery::proto::HistoryResult >*
CallDetailsEntries_CallDetailsEntry::mutable_history_results() {
  // @@protoc_insertion_point(field_mutable_list:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry.history_results)
  return &history_results_;
}
const ::google::protobuf::RepeatedPtrField< ::com::android::dialer::enrichedcall::historyquery::proto::HistoryResult >&
CallDetailsEntries_CallDetailsEntry::history_results() const {
  // @@protoc_insertion_point(field_list:com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry.history_results)
  return history_results_;
}

inline const CallDetailsEntries_CallDetailsEntry* CallDetailsEntries_CallDetailsEntry::internal_default_instance() {
  return &CallDetailsEntries_CallDetailsEntry_default_instance_.get();
}
// -------------------------------------------------------------------

// CallDetailsEntries

// repeated .com.android.dialer.calldetails.CallDetailsEntries.CallDetailsEntry entries = 1;
int CallDetailsEntries::entries_size() const {
  return entries_.size();
}
void CallDetailsEntries::clear_entries() {
  entries_.Clear();
}
const ::com::android::dialer::calldetails::CallDetailsEntries_CallDetailsEntry& CallDetailsEntries::entries(int index) const {
  // @@protoc_insertion_point(field_get:com.android.dialer.calldetails.CallDetailsEntries.entries)
  return entries_.Get(index);
}
::com::android::dialer::calldetails::CallDetailsEntries_CallDetailsEntry* CallDetailsEntries::mutable_entries(int index) {
  // @@protoc_insertion_point(field_mutable:com.android.dialer.calldetails.CallDetailsEntries.entries)
  return entries_.Mutable(index);
}
::com::android::dialer::calldetails::CallDetailsEntries_CallDetailsEntry* CallDetailsEntries::add_entries() {
  // @@protoc_insertion_point(field_add:com.android.dialer.calldetails.CallDetailsEntries.entries)
  return entries_.Add();
}
::google::protobuf::RepeatedPtrField< ::com::android::dialer::calldetails::CallDetailsEntries_CallDetailsEntry >*
CallDetailsEntries::mutable_entries() {
  // @@protoc_insertion_point(field_mutable_list:com.android.dialer.calldetails.CallDetailsEntries.entries)
  return &entries_;
}
const ::google::protobuf::RepeatedPtrField< ::com::android::dialer::calldetails::CallDetailsEntries_CallDetailsEntry >&
CallDetailsEntries::entries() const {
  // @@protoc_insertion_point(field_list:com.android.dialer.calldetails.CallDetailsEntries.entries)
  return entries_;
}

inline const CallDetailsEntries* CallDetailsEntries::internal_default_instance() {
  return &CallDetailsEntries_default_instance_.get();
}
#endif  // PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)

}  // namespace calldetails
}  // namespace dialer
}  // namespace android
}  // namespace com

// @@protoc_insertion_point(global_scope)
