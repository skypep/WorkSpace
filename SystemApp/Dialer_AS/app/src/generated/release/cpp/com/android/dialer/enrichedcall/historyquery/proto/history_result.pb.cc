// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: com/android/dialer/enrichedcall/historyquery/proto/history_result.proto

#define INTERNAL_SUPPRESS_PROTOBUF_FIELD_DEPRECATION
#include "com/android/dialer/enrichedcall/historyquery/proto/history_result.pb.h"

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
namespace enrichedcall {
namespace historyquery {
namespace proto {

void protobuf_ShutdownFile_com_2fandroid_2fdialer_2fenrichedcall_2fhistoryquery_2fproto_2fhistory_5fresult_2eproto() {
  HistoryResult_default_instance_.Shutdown();
}

void protobuf_InitDefaults_com_2fandroid_2fdialer_2fenrichedcall_2fhistoryquery_2fproto_2fhistory_5fresult_2eproto_impl() {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  ::google::protobuf::internal::GetEmptyString();
  HistoryResult_default_instance_.DefaultConstruct();
  HistoryResult_default_instance_.get_mutable()->InitAsDefaultInstance();
}

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_InitDefaults_com_2fandroid_2fdialer_2fenrichedcall_2fhistoryquery_2fproto_2fhistory_5fresult_2eproto_once_);
void protobuf_InitDefaults_com_2fandroid_2fdialer_2fenrichedcall_2fhistoryquery_2fproto_2fhistory_5fresult_2eproto() {
  ::google::protobuf::GoogleOnceInit(&protobuf_InitDefaults_com_2fandroid_2fdialer_2fenrichedcall_2fhistoryquery_2fproto_2fhistory_5fresult_2eproto_once_,
                 &protobuf_InitDefaults_com_2fandroid_2fdialer_2fenrichedcall_2fhistoryquery_2fproto_2fhistory_5fresult_2eproto_impl);
}
void protobuf_AddDesc_com_2fandroid_2fdialer_2fenrichedcall_2fhistoryquery_2fproto_2fhistory_5fresult_2eproto_impl() {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  protobuf_InitDefaults_com_2fandroid_2fdialer_2fenrichedcall_2fhistoryquery_2fproto_2fhistory_5fresult_2eproto();
  ::google::protobuf::internal::OnShutdown(&protobuf_ShutdownFile_com_2fandroid_2fdialer_2fenrichedcall_2fhistoryquery_2fproto_2fhistory_5fresult_2eproto);
}

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AddDesc_com_2fandroid_2fdialer_2fenrichedcall_2fhistoryquery_2fproto_2fhistory_5fresult_2eproto_once_);
void protobuf_AddDesc_com_2fandroid_2fdialer_2fenrichedcall_2fhistoryquery_2fproto_2fhistory_5fresult_2eproto() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AddDesc_com_2fandroid_2fdialer_2fenrichedcall_2fhistoryquery_2fproto_2fhistory_5fresult_2eproto_once_,
                 &protobuf_AddDesc_com_2fandroid_2fdialer_2fenrichedcall_2fhistoryquery_2fproto_2fhistory_5fresult_2eproto_impl);
}
#ifdef GOOGLE_PROTOBUF_NO_STATIC_INITIALIZER
// Force AddDescriptors() to be called at static initialization time.
struct StaticDescriptorInitializer_com_2fandroid_2fdialer_2fenrichedcall_2fhistoryquery_2fproto_2fhistory_5fresult_2eproto {
  StaticDescriptorInitializer_com_2fandroid_2fdialer_2fenrichedcall_2fhistoryquery_2fproto_2fhistory_5fresult_2eproto() {
    protobuf_AddDesc_com_2fandroid_2fdialer_2fenrichedcall_2fhistoryquery_2fproto_2fhistory_5fresult_2eproto();
  }
} static_descriptor_initializer_com_2fandroid_2fdialer_2fenrichedcall_2fhistoryquery_2fproto_2fhistory_5fresult_2eproto_;
#endif  // GOOGLE_PROTOBUF_NO_STATIC_INITIALIZER

namespace {

static void MergeFromFail(int line) GOOGLE_ATTRIBUTE_COLD GOOGLE_ATTRIBUTE_NORETURN;
static void MergeFromFail(int line) {
  ::google::protobuf::internal::MergeFromFail(__FILE__, line);
}

}  // namespace


// ===================================================================

static ::std::string* MutableUnknownFieldsForHistoryResult(
    HistoryResult* ptr) {
  return ptr->mutable_unknown_fields();
}

bool HistoryResult_Type_IsValid(int value) {
  switch (value) {
    case 1:
    case 2:
    case 3:
    case 4:
      return true;
    default:
      return false;
  }
}

#if !defined(_MSC_VER) || _MSC_VER >= 1900
const HistoryResult_Type HistoryResult::INCOMING_CALL_COMPOSER;
const HistoryResult_Type HistoryResult::OUTGOING_CALL_COMPOSER;
const HistoryResult_Type HistoryResult::INCOMING_POST_CALL;
const HistoryResult_Type HistoryResult::OUTGOING_POST_CALL;
const HistoryResult_Type HistoryResult::Type_MIN;
const HistoryResult_Type HistoryResult::Type_MAX;
const int HistoryResult::Type_ARRAYSIZE;
#endif  // !defined(_MSC_VER) || _MSC_VER >= 1900
#if !defined(_MSC_VER) || _MSC_VER >= 1900
const int HistoryResult::kTypeFieldNumber;
const int HistoryResult::kTextFieldNumber;
const int HistoryResult::kImageUriFieldNumber;
const int HistoryResult::kImageContentTypeFieldNumber;
const int HistoryResult::kTimestampFieldNumber;
#endif  // !defined(_MSC_VER) || _MSC_VER >= 1900

HistoryResult::HistoryResult()
  : ::google::protobuf::MessageLite(), _arena_ptr_(NULL) {
  if (this != internal_default_instance()) protobuf_InitDefaults_com_2fandroid_2fdialer_2fenrichedcall_2fhistoryquery_2fproto_2fhistory_5fresult_2eproto();
  SharedCtor();
  // @@protoc_insertion_point(constructor:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult)
}

void HistoryResult::InitAsDefaultInstance() {
}

HistoryResult::HistoryResult(const HistoryResult& from)
  : ::google::protobuf::MessageLite(),
    _arena_ptr_(NULL) {
  SharedCtor();
  UnsafeMergeFrom(from);
  // @@protoc_insertion_point(copy_constructor:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult)
}

void HistoryResult::SharedCtor() {
  _cached_size_ = 0;
  _unknown_fields_.UnsafeSetDefault(
      &::google::protobuf::internal::GetEmptyStringAlreadyInited());
  text_.UnsafeSetDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  image_uri_.UnsafeSetDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  image_content_type_.UnsafeSetDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  timestamp_ = GOOGLE_LONGLONG(0);
  type_ = 1;
}

HistoryResult::~HistoryResult() {
  // @@protoc_insertion_point(destructor:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult)
  SharedDtor();
}

void HistoryResult::SharedDtor() {
  _unknown_fields_.DestroyNoArena(
      &::google::protobuf::internal::GetEmptyStringAlreadyInited());
  text_.DestroyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  image_uri_.DestroyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  image_content_type_.DestroyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}

void HistoryResult::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const HistoryResult& HistoryResult::default_instance() {
  protobuf_InitDefaults_com_2fandroid_2fdialer_2fenrichedcall_2fhistoryquery_2fproto_2fhistory_5fresult_2eproto();
  return *internal_default_instance();
}

::google::protobuf::internal::ExplicitlyConstructed<HistoryResult> HistoryResult_default_instance_;

HistoryResult* HistoryResult::New(::google::protobuf::Arena* arena) const {
  HistoryResult* n = new HistoryResult;
  if (arena != NULL) {
    arena->Own(n);
  }
  return n;
}

void HistoryResult::Clear() {
// @@protoc_insertion_point(message_clear_start:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult)
  if (_has_bits_[0 / 32] & 31u) {
    type_ = 1;
    if (has_text()) {
      text_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
    }
    if (has_image_uri()) {
      image_uri_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
    }
    if (has_image_content_type()) {
      image_content_type_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
    }
    timestamp_ = GOOGLE_LONGLONG(0);
  }
  _has_bits_.Clear();
  _unknown_fields_.ClearToEmptyNoArena(
      &::google::protobuf::internal::GetEmptyStringAlreadyInited());
}

bool HistoryResult::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!GOOGLE_PREDICT_TRUE(EXPRESSION)) goto failure
  ::google::protobuf::uint32 tag;
  ::google::protobuf::io::LazyStringOutputStream unknown_fields_string(
      ::google::protobuf::NewPermanentCallback(
          &MutableUnknownFieldsForHistoryResult, this));
  ::google::protobuf::io::CodedOutputStream unknown_fields_stream(
      &unknown_fields_string, false);
  // @@protoc_insertion_point(parse_start:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult)
  for (;;) {
    ::std::pair< ::google::protobuf::uint32, bool> p = input->ReadTagWithCutoff(127);
    tag = p.first;
    if (!p.second) goto handle_unusual;
    switch (::google::protobuf::internal::WireFormatLite::GetTagFieldNumber(tag)) {
      // optional .com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.Type type = 1;
      case 1: {
        if (tag == 8) {
          int value;
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   int, ::google::protobuf::internal::WireFormatLite::TYPE_ENUM>(
                 input, &value)));
          if (::com::android::dialer::enrichedcall::historyquery::proto::HistoryResult_Type_IsValid(value)) {
            set_type(static_cast< ::com::android::dialer::enrichedcall::historyquery::proto::HistoryResult_Type >(value));
          } else {
            unknown_fields_stream.WriteVarint32(8);
            unknown_fields_stream.WriteVarint32(value);
          }
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(18)) goto parse_text;
        break;
      }

      // optional string text = 2;
      case 2: {
        if (tag == 18) {
         parse_text:
          DO_(::google::protobuf::internal::WireFormatLite::ReadString(
                input, this->mutable_text()));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(34)) goto parse_image_uri;
        break;
      }

      // optional string image_uri = 4;
      case 4: {
        if (tag == 34) {
         parse_image_uri:
          DO_(::google::protobuf::internal::WireFormatLite::ReadString(
                input, this->mutable_image_uri()));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(42)) goto parse_image_content_type;
        break;
      }

      // optional string image_content_type = 5;
      case 5: {
        if (tag == 42) {
         parse_image_content_type:
          DO_(::google::protobuf::internal::WireFormatLite::ReadString(
                input, this->mutable_image_content_type()));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(56)) goto parse_timestamp;
        break;
      }

      // optional int64 timestamp = 7;
      case 7: {
        if (tag == 56) {
         parse_timestamp:
          set_has_timestamp();
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int64, ::google::protobuf::internal::WireFormatLite::TYPE_INT64>(
                 input, &timestamp_)));
        } else {
          goto handle_unusual;
        }
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
  // @@protoc_insertion_point(parse_success:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult)
  return true;
failure:
  // @@protoc_insertion_point(parse_failure:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult)
  return false;
#undef DO_
}

void HistoryResult::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // @@protoc_insertion_point(serialize_start:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult)
  // optional .com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.Type type = 1;
  if (has_type()) {
    ::google::protobuf::internal::WireFormatLite::WriteEnum(
      1, this->type(), output);
  }

  // optional string text = 2;
  if (has_text()) {
    ::google::protobuf::internal::WireFormatLite::WriteStringMaybeAliased(
      2, this->text(), output);
  }

  // optional string image_uri = 4;
  if (has_image_uri()) {
    ::google::protobuf::internal::WireFormatLite::WriteStringMaybeAliased(
      4, this->image_uri(), output);
  }

  // optional string image_content_type = 5;
  if (has_image_content_type()) {
    ::google::protobuf::internal::WireFormatLite::WriteStringMaybeAliased(
      5, this->image_content_type(), output);
  }

  // optional int64 timestamp = 7;
  if (has_timestamp()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt64(7, this->timestamp(), output);
  }

  output->WriteRaw(unknown_fields().data(),
                   static_cast<int>(unknown_fields().size()));
  // @@protoc_insertion_point(serialize_end:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult)
}

size_t HistoryResult::ByteSizeLong() const {
// @@protoc_insertion_point(message_byte_size_start:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult)
  size_t total_size = 0;

  if (_has_bits_[0 / 32] & 31u) {
    // optional .com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.Type type = 1;
    if (has_type()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::EnumSize(this->type());
    }

    // optional string text = 2;
    if (has_text()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::StringSize(
          this->text());
    }

    // optional string image_uri = 4;
    if (has_image_uri()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::StringSize(
          this->image_uri());
    }

    // optional string image_content_type = 5;
    if (has_image_content_type()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::StringSize(
          this->image_content_type());
    }

    // optional int64 timestamp = 7;
    if (has_timestamp()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::Int64Size(
          this->timestamp());
    }

  }
  total_size += unknown_fields().size();

  int cached_size = ::google::protobuf::internal::ToCachedSize(total_size);
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = cached_size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
  return total_size;
}

void HistoryResult::CheckTypeAndMergeFrom(
    const ::google::protobuf::MessageLite& from) {
  MergeFrom(*::google::protobuf::down_cast<const HistoryResult*>(&from));
}

void HistoryResult::MergeFrom(const HistoryResult& from) {
// @@protoc_insertion_point(class_specific_merge_from_start:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult)
  if (GOOGLE_PREDICT_TRUE(&from != this)) {
    UnsafeMergeFrom(from);
  } else {
    MergeFromFail(__LINE__);
  }
}

void HistoryResult::UnsafeMergeFrom(const HistoryResult& from) {
  GOOGLE_DCHECK(&from != this);
  if (from._has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    if (from.has_type()) {
      set_type(from.type());
    }
    if (from.has_text()) {
      set_has_text();
      text_.AssignWithDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), from.text_);
    }
    if (from.has_image_uri()) {
      set_has_image_uri();
      image_uri_.AssignWithDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), from.image_uri_);
    }
    if (from.has_image_content_type()) {
      set_has_image_content_type();
      image_content_type_.AssignWithDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), from.image_content_type_);
    }
    if (from.has_timestamp()) {
      set_timestamp(from.timestamp());
    }
  }
  if (!from.unknown_fields().empty()) {
    mutable_unknown_fields()->append(from.unknown_fields());
  }
}

void HistoryResult::CopyFrom(const HistoryResult& from) {
// @@protoc_insertion_point(class_specific_copy_from_start:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult)
  if (&from == this) return;
  Clear();
  UnsafeMergeFrom(from);
}

bool HistoryResult::IsInitialized() const {

  return true;
}

void HistoryResult::Swap(HistoryResult* other) {
  if (other == this) return;
  InternalSwap(other);
}
void HistoryResult::InternalSwap(HistoryResult* other) {
  std::swap(type_, other->type_);
  text_.Swap(&other->text_);
  image_uri_.Swap(&other->image_uri_);
  image_content_type_.Swap(&other->image_content_type_);
  std::swap(timestamp_, other->timestamp_);
  std::swap(_has_bits_[0], other->_has_bits_[0]);
  _unknown_fields_.Swap(&other->_unknown_fields_);
  std::swap(_cached_size_, other->_cached_size_);
}

::std::string HistoryResult::GetTypeName() const {
  return "com.android.dialer.enrichedcall.historyquery.proto.HistoryResult";
}

#if PROTOBUF_INLINE_NOT_IN_HEADERS
// HistoryResult

// optional .com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.Type type = 1;
bool HistoryResult::has_type() const {
  return (_has_bits_[0] & 0x00000001u) != 0;
}
void HistoryResult::set_has_type() {
  _has_bits_[0] |= 0x00000001u;
}
void HistoryResult::clear_has_type() {
  _has_bits_[0] &= ~0x00000001u;
}
void HistoryResult::clear_type() {
  type_ = 1;
  clear_has_type();
}
::com::android::dialer::enrichedcall::historyquery::proto::HistoryResult_Type HistoryResult::type() const {
  // @@protoc_insertion_point(field_get:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.type)
  return static_cast< ::com::android::dialer::enrichedcall::historyquery::proto::HistoryResult_Type >(type_);
}
void HistoryResult::set_type(::com::android::dialer::enrichedcall::historyquery::proto::HistoryResult_Type value) {
  assert(::com::android::dialer::enrichedcall::historyquery::proto::HistoryResult_Type_IsValid(value));
  set_has_type();
  type_ = value;
  // @@protoc_insertion_point(field_set:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.type)
}

// optional string text = 2;
bool HistoryResult::has_text() const {
  return (_has_bits_[0] & 0x00000002u) != 0;
}
void HistoryResult::set_has_text() {
  _has_bits_[0] |= 0x00000002u;
}
void HistoryResult::clear_has_text() {
  _has_bits_[0] &= ~0x00000002u;
}
void HistoryResult::clear_text() {
  text_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  clear_has_text();
}
const ::std::string& HistoryResult::text() const {
  // @@protoc_insertion_point(field_get:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.text)
  return text_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
void HistoryResult::set_text(const ::std::string& value) {
  set_has_text();
  text_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.text)
}
void HistoryResult::set_text(const char* value) {
  set_has_text();
  text_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.text)
}
void HistoryResult::set_text(const char* value, size_t size) {
  set_has_text();
  text_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.text)
}
::std::string* HistoryResult::mutable_text() {
  set_has_text();
  // @@protoc_insertion_point(field_mutable:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.text)
  return text_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
::std::string* HistoryResult::release_text() {
  // @@protoc_insertion_point(field_release:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.text)
  clear_has_text();
  return text_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
void HistoryResult::set_allocated_text(::std::string* text) {
  if (text != NULL) {
    set_has_text();
  } else {
    clear_has_text();
  }
  text_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), text);
  // @@protoc_insertion_point(field_set_allocated:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.text)
}

// optional string image_uri = 4;
bool HistoryResult::has_image_uri() const {
  return (_has_bits_[0] & 0x00000004u) != 0;
}
void HistoryResult::set_has_image_uri() {
  _has_bits_[0] |= 0x00000004u;
}
void HistoryResult::clear_has_image_uri() {
  _has_bits_[0] &= ~0x00000004u;
}
void HistoryResult::clear_image_uri() {
  image_uri_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  clear_has_image_uri();
}
const ::std::string& HistoryResult::image_uri() const {
  // @@protoc_insertion_point(field_get:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.image_uri)
  return image_uri_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
void HistoryResult::set_image_uri(const ::std::string& value) {
  set_has_image_uri();
  image_uri_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.image_uri)
}
void HistoryResult::set_image_uri(const char* value) {
  set_has_image_uri();
  image_uri_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.image_uri)
}
void HistoryResult::set_image_uri(const char* value, size_t size) {
  set_has_image_uri();
  image_uri_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.image_uri)
}
::std::string* HistoryResult::mutable_image_uri() {
  set_has_image_uri();
  // @@protoc_insertion_point(field_mutable:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.image_uri)
  return image_uri_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
::std::string* HistoryResult::release_image_uri() {
  // @@protoc_insertion_point(field_release:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.image_uri)
  clear_has_image_uri();
  return image_uri_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
void HistoryResult::set_allocated_image_uri(::std::string* image_uri) {
  if (image_uri != NULL) {
    set_has_image_uri();
  } else {
    clear_has_image_uri();
  }
  image_uri_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), image_uri);
  // @@protoc_insertion_point(field_set_allocated:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.image_uri)
}

// optional string image_content_type = 5;
bool HistoryResult::has_image_content_type() const {
  return (_has_bits_[0] & 0x00000008u) != 0;
}
void HistoryResult::set_has_image_content_type() {
  _has_bits_[0] |= 0x00000008u;
}
void HistoryResult::clear_has_image_content_type() {
  _has_bits_[0] &= ~0x00000008u;
}
void HistoryResult::clear_image_content_type() {
  image_content_type_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  clear_has_image_content_type();
}
const ::std::string& HistoryResult::image_content_type() const {
  // @@protoc_insertion_point(field_get:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.image_content_type)
  return image_content_type_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
void HistoryResult::set_image_content_type(const ::std::string& value) {
  set_has_image_content_type();
  image_content_type_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.image_content_type)
}
void HistoryResult::set_image_content_type(const char* value) {
  set_has_image_content_type();
  image_content_type_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.image_content_type)
}
void HistoryResult::set_image_content_type(const char* value, size_t size) {
  set_has_image_content_type();
  image_content_type_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.image_content_type)
}
::std::string* HistoryResult::mutable_image_content_type() {
  set_has_image_content_type();
  // @@protoc_insertion_point(field_mutable:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.image_content_type)
  return image_content_type_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
::std::string* HistoryResult::release_image_content_type() {
  // @@protoc_insertion_point(field_release:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.image_content_type)
  clear_has_image_content_type();
  return image_content_type_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
void HistoryResult::set_allocated_image_content_type(::std::string* image_content_type) {
  if (image_content_type != NULL) {
    set_has_image_content_type();
  } else {
    clear_has_image_content_type();
  }
  image_content_type_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), image_content_type);
  // @@protoc_insertion_point(field_set_allocated:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.image_content_type)
}

// optional int64 timestamp = 7;
bool HistoryResult::has_timestamp() const {
  return (_has_bits_[0] & 0x00000010u) != 0;
}
void HistoryResult::set_has_timestamp() {
  _has_bits_[0] |= 0x00000010u;
}
void HistoryResult::clear_has_timestamp() {
  _has_bits_[0] &= ~0x00000010u;
}
void HistoryResult::clear_timestamp() {
  timestamp_ = GOOGLE_LONGLONG(0);
  clear_has_timestamp();
}
::google::protobuf::int64 HistoryResult::timestamp() const {
  // @@protoc_insertion_point(field_get:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.timestamp)
  return timestamp_;
}
void HistoryResult::set_timestamp(::google::protobuf::int64 value) {
  set_has_timestamp();
  timestamp_ = value;
  // @@protoc_insertion_point(field_set:com.android.dialer.enrichedcall.historyquery.proto.HistoryResult.timestamp)
}

inline const HistoryResult* HistoryResult::internal_default_instance() {
  return &HistoryResult_default_instance_.get();
}
#endif  // PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)

}  // namespace proto
}  // namespace historyquery
}  // namespace enrichedcall
}  // namespace dialer
}  // namespace android
}  // namespace com

// @@protoc_insertion_point(global_scope)