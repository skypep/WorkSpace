// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: com/android/dialer/phonenumberproto/dialer_phone_number.proto

package com.android.dialer;

public interface DialerInternalPhoneNumberOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.android.dialer.DialerInternalPhoneNumber)
    com.google.protobuf.MessageLiteOrBuilder {

  /**
   * <pre>
   * The country calling code for this number, as defined by the International
   * Telecommunication Union (ITU). For example, this would be 1 for NANPA
   * countries, and 33 for France.
   * </pre>
   *
   * <code>required int32 country_code = 1;</code>
   */
  boolean hasCountryCode();
  /**
   * <pre>
   * The country calling code for this number, as defined by the International
   * Telecommunication Union (ITU). For example, this would be 1 for NANPA
   * countries, and 33 for France.
   * </pre>
   *
   * <code>required int32 country_code = 1;</code>
   */
  int getCountryCode();

  /**
   * <pre>
   * The National (significant) Number, as defined in International
   * Telecommunication Union (ITU) Recommendation E.164, without any leading
   * zero. The leading-zero is stored separately if required, since this is an
   * uint64 and hence cannot store such information. Do not use this field
   * directly: if you want the national significant number, call the
   * getNationalSignificantNumber method of PhoneNumberUtil.
   * For countries which have the concept of an "area code" or "national
   * destination code", this is included in the National (significant) Number.
   * Although the ITU says the maximum length should be 15, we have found longer
   * numbers in some countries e.g. Germany.
   * Note that the National (significant) Number does not contain the National
   * (trunk) prefix. Obviously, as a uint64, it will never contain any
   * formatting (hyphens, spaces, parentheses), nor any alphanumeric spellings.
   * </pre>
   *
   * <code>required uint64 national_number = 2 [jstype = JS_NUMBER];</code>
   */
  boolean hasNationalNumber();
  /**
   * <pre>
   * The National (significant) Number, as defined in International
   * Telecommunication Union (ITU) Recommendation E.164, without any leading
   * zero. The leading-zero is stored separately if required, since this is an
   * uint64 and hence cannot store such information. Do not use this field
   * directly: if you want the national significant number, call the
   * getNationalSignificantNumber method of PhoneNumberUtil.
   * For countries which have the concept of an "area code" or "national
   * destination code", this is included in the National (significant) Number.
   * Although the ITU says the maximum length should be 15, we have found longer
   * numbers in some countries e.g. Germany.
   * Note that the National (significant) Number does not contain the National
   * (trunk) prefix. Obviously, as a uint64, it will never contain any
   * formatting (hyphens, spaces, parentheses), nor any alphanumeric spellings.
   * </pre>
   *
   * <code>required uint64 national_number = 2 [jstype = JS_NUMBER];</code>
   */
  long getNationalNumber();

  /**
   * <pre>
   * Extension is not standardized in ITU recommendations, except for being
   * defined as a series of numbers with a maximum length of 40 digits. It is
   * defined as a string here to accommodate for the possible use of a leading
   * zero in the extension (organizations have complete freedom to do so, as
   * there is no standard defined). Other than digits, some other dialling
   * characters such as "," (indicating a wait) may be stored here.
   * </pre>
   *
   * <code>optional string extension = 3;</code>
   */
  boolean hasExtension();
  /**
   * <pre>
   * Extension is not standardized in ITU recommendations, except for being
   * defined as a series of numbers with a maximum length of 40 digits. It is
   * defined as a string here to accommodate for the possible use of a leading
   * zero in the extension (organizations have complete freedom to do so, as
   * there is no standard defined). Other than digits, some other dialling
   * characters such as "," (indicating a wait) may be stored here.
   * </pre>
   *
   * <code>optional string extension = 3;</code>
   */
  java.lang.String getExtension();
  /**
   * <pre>
   * Extension is not standardized in ITU recommendations, except for being
   * defined as a series of numbers with a maximum length of 40 digits. It is
   * defined as a string here to accommodate for the possible use of a leading
   * zero in the extension (organizations have complete freedom to do so, as
   * there is no standard defined). Other than digits, some other dialling
   * characters such as "," (indicating a wait) may be stored here.
   * </pre>
   *
   * <code>optional string extension = 3;</code>
   */
  com.google.protobuf.ByteString
      getExtensionBytes();

  /**
   * <pre>
   * In some countries, the national (significant) number starts with one or
   * more "0"s without this being a national prefix or trunk code of some kind.
   * For example, the leading zero in the national (significant) number of an
   * Italian phone number indicates the number is a fixed-line number.  There
   * have been plans to migrate fixed-line numbers to start with the digit two
   * since December 2000, but it has not happened yet. See
   * http://en.wikipedia.org/wiki/%2B39 for more details.
   * These fields can be safely ignored (there is no need to set them) for most
   * countries. Some limited number of countries behave like Italy - for these
   * cases, if the leading zero(s) of a number would be retained even when
   * dialling internationally, set this flag to true, and also set the number of
   * leading zeros.
   * Clients who use the parsing or conversion functionality of the i18n phone
   * number libraries (go/phonenumbers) will have these fields set if necessary
   * automatically.
   * </pre>
   *
   * <code>optional bool italian_leading_zero = 4;</code>
   */
  boolean hasItalianLeadingZero();
  /**
   * <pre>
   * In some countries, the national (significant) number starts with one or
   * more "0"s without this being a national prefix or trunk code of some kind.
   * For example, the leading zero in the national (significant) number of an
   * Italian phone number indicates the number is a fixed-line number.  There
   * have been plans to migrate fixed-line numbers to start with the digit two
   * since December 2000, but it has not happened yet. See
   * http://en.wikipedia.org/wiki/%2B39 for more details.
   * These fields can be safely ignored (there is no need to set them) for most
   * countries. Some limited number of countries behave like Italy - for these
   * cases, if the leading zero(s) of a number would be retained even when
   * dialling internationally, set this flag to true, and also set the number of
   * leading zeros.
   * Clients who use the parsing or conversion functionality of the i18n phone
   * number libraries (go/phonenumbers) will have these fields set if necessary
   * automatically.
   * </pre>
   *
   * <code>optional bool italian_leading_zero = 4;</code>
   */
  boolean getItalianLeadingZero();

  /**
   * <code>optional int32 number_of_leading_zeros = 8 [default = 1];</code>
   */
  boolean hasNumberOfLeadingZeros();
  /**
   * <code>optional int32 number_of_leading_zeros = 8 [default = 1];</code>
   */
  int getNumberOfLeadingZeros();

  /**
   * <pre>
   * This field is used to store the raw input string containing phone numbers
   * before it was canonicalized by the library. For example, it could be used
   * to store alphanumerical numbers such as "1-800-GOOG-411".
   * </pre>
   *
   * <code>optional string raw_input = 5;</code>
   */
  boolean hasRawInput();
  /**
   * <pre>
   * This field is used to store the raw input string containing phone numbers
   * before it was canonicalized by the library. For example, it could be used
   * to store alphanumerical numbers such as "1-800-GOOG-411".
   * </pre>
   *
   * <code>optional string raw_input = 5;</code>
   */
  java.lang.String getRawInput();
  /**
   * <pre>
   * This field is used to store the raw input string containing phone numbers
   * before it was canonicalized by the library. For example, it could be used
   * to store alphanumerical numbers such as "1-800-GOOG-411".
   * </pre>
   *
   * <code>optional string raw_input = 5;</code>
   */
  com.google.protobuf.ByteString
      getRawInputBytes();

  /**
   * <pre>
   * The source from which the country_code is derived.
   * </pre>
   *
   * <code>optional .com.android.dialer.DialerInternalPhoneNumber.CountryCodeSource country_code_source = 6;</code>
   */
  boolean hasCountryCodeSource();
  /**
   * <pre>
   * The source from which the country_code is derived.
   * </pre>
   *
   * <code>optional .com.android.dialer.DialerInternalPhoneNumber.CountryCodeSource country_code_source = 6;</code>
   */
  com.android.dialer.DialerInternalPhoneNumber.CountryCodeSource getCountryCodeSource();

  /**
   * <pre>
   * The carrier selection code that is preferred when calling this phone number
   * domestically. This also includes codes that need to be dialed in some
   * countries when calling from landlines to mobiles or vice versa. For
   * example, in Columbia, a "3" needs to be dialed before the phone number
   * itself when calling from a mobile phone to a domestic landline phone and
   * vice versa.
   * Note this is the "preferred" code, which means other codes may work as
   * well.
   * </pre>
   *
   * <code>optional string preferred_domestic_carrier_code = 7;</code>
   */
  boolean hasPreferredDomesticCarrierCode();
  /**
   * <pre>
   * The carrier selection code that is preferred when calling this phone number
   * domestically. This also includes codes that need to be dialed in some
   * countries when calling from landlines to mobiles or vice versa. For
   * example, in Columbia, a "3" needs to be dialed before the phone number
   * itself when calling from a mobile phone to a domestic landline phone and
   * vice versa.
   * Note this is the "preferred" code, which means other codes may work as
   * well.
   * </pre>
   *
   * <code>optional string preferred_domestic_carrier_code = 7;</code>
   */
  java.lang.String getPreferredDomesticCarrierCode();
  /**
   * <pre>
   * The carrier selection code that is preferred when calling this phone number
   * domestically. This also includes codes that need to be dialed in some
   * countries when calling from landlines to mobiles or vice versa. For
   * example, in Columbia, a "3" needs to be dialed before the phone number
   * itself when calling from a mobile phone to a domestic landline phone and
   * vice versa.
   * Note this is the "preferred" code, which means other codes may work as
   * well.
   * </pre>
   *
   * <code>optional string preferred_domestic_carrier_code = 7;</code>
   */
  com.google.protobuf.ByteString
      getPreferredDomesticCarrierCodeBytes();
}
