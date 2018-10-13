package android.hardware.radio.V1_0;


public final class StkCcUnsolSsResult {
    public int serviceType;
    public int requestType;
    public int teleserviceType;
    public int serviceClass;
    public int result;
    public final java.util.ArrayList<android.hardware.radio.V1_0.SsInfoData> ssInfo = new java.util.ArrayList<android.hardware.radio.V1_0.SsInfoData>();
    public final java.util.ArrayList<android.hardware.radio.V1_0.CfData> cfData = new java.util.ArrayList<android.hardware.radio.V1_0.CfData>();

    @Override
    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null) {
            return false;
        }
        if (otherObject.getClass() != android.hardware.radio.V1_0.StkCcUnsolSsResult.class) {
            return false;
        }
        android.hardware.radio.V1_0.StkCcUnsolSsResult other = (android.hardware.radio.V1_0.StkCcUnsolSsResult)otherObject;
        if (this.serviceType != other.serviceType) {
            return false;
        }
        if (this.requestType != other.requestType) {
            return false;
        }
        if (this.teleserviceType != other.teleserviceType) {
            return false;
        }
        if (!android.os.HidlSupport.deepEquals(this.serviceClass, other.serviceClass)) {
            return false;
        }
        if (this.result != other.result) {
            return false;
        }
        if (!android.os.HidlSupport.deepEquals(this.ssInfo, other.ssInfo)) {
            return false;
        }
        if (!android.os.HidlSupport.deepEquals(this.cfData, other.cfData)) {
            return false;
        }
        return true;
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(
                android.os.HidlSupport.deepHashCode(this.serviceType), 
                android.os.HidlSupport.deepHashCode(this.requestType), 
                android.os.HidlSupport.deepHashCode(this.teleserviceType), 
                android.os.HidlSupport.deepHashCode(this.serviceClass), 
                android.os.HidlSupport.deepHashCode(this.result), 
                android.os.HidlSupport.deepHashCode(this.ssInfo), 
                android.os.HidlSupport.deepHashCode(this.cfData));
    }

    @Override
    public final String toString() {
        java.lang.StringBuilder builder = new java.lang.StringBuilder();
        builder.append("{");
        builder.append(".serviceType = ");
        builder.append(android.hardware.radio.V1_0.SsServiceType.toString(this.serviceType));
        builder.append(", .requestType = ");
        builder.append(android.hardware.radio.V1_0.SsRequestType.toString(this.requestType));
        builder.append(", .teleserviceType = ");
        builder.append(android.hardware.radio.V1_0.SsTeleserviceType.toString(this.teleserviceType));
        builder.append(", .serviceClass = ");
        builder.append(android.hardware.radio.V1_0.SuppServiceClass.dumpBitfield(this.serviceClass));
        builder.append(", .result = ");
        builder.append(android.hardware.radio.V1_0.RadioError.toString(this.result));
        builder.append(", .ssInfo = ");
        builder.append(this.ssInfo);
        builder.append(", .cfData = ");
        builder.append(this.cfData);
        builder.append("}");
        return builder.toString();
    }

    public final void readFromParcel(android.os.HwParcel parcel) {
        android.os.HwBlob blob = parcel.readBuffer(56/* size */);
        readEmbeddedFromParcel(parcel, blob, 0 /* parentOffset */);
    }

    public static final java.util.ArrayList<StkCcUnsolSsResult> readVectorFromParcel(android.os.HwParcel parcel) {
        java.util.ArrayList<StkCcUnsolSsResult> _hidl_vec = new java.util.ArrayList();
        android.os.HwBlob _hidl_blob = parcel.readBuffer(16 /* sizeof hidl_vec<T> */);

        {
            int _hidl_vec_size = _hidl_blob.getInt32(0 + 8 /* offsetof(hidl_vec<T>, mSize) */);
            android.os.HwBlob childBlob = parcel.readEmbeddedBuffer(
                    _hidl_vec_size * 56,_hidl_blob.handle(),
                    0 + 0 /* offsetof(hidl_vec<T>, mBuffer) */,true /* nullable */);

            _hidl_vec.clear();
            for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; ++_hidl_index_0) {
                final android.hardware.radio.V1_0.StkCcUnsolSsResult _hidl_vec_element = new android.hardware.radio.V1_0.StkCcUnsolSsResult();
                _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 56);
                _hidl_vec.add(_hidl_vec_element);
            }
        }

        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(
            android.os.HwParcel parcel, android.os.HwBlob _hidl_blob, long _hidl_offset) {
        serviceType = _hidl_blob.getInt32(_hidl_offset + 0);
        requestType = _hidl_blob.getInt32(_hidl_offset + 4);
        teleserviceType = _hidl_blob.getInt32(_hidl_offset + 8);
        serviceClass = _hidl_blob.getInt32(_hidl_offset + 12);
        result = _hidl_blob.getInt32(_hidl_offset + 16);
        {
            int _hidl_vec_size = _hidl_blob.getInt32(_hidl_offset + 24 + 8 /* offsetof(hidl_vec<T>, mSize) */);
            android.os.HwBlob childBlob = parcel.readEmbeddedBuffer(
                    _hidl_vec_size * 16,_hidl_blob.handle(),
                    _hidl_offset + 24 + 0 /* offsetof(hidl_vec<T>, mBuffer) */,true /* nullable */);

            ssInfo.clear();
            for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; ++_hidl_index_0) {
                final android.hardware.radio.V1_0.SsInfoData _hidl_vec_element = new android.hardware.radio.V1_0.SsInfoData();
                _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 16);
                ssInfo.add(_hidl_vec_element);
            }
        }
        {
            int _hidl_vec_size = _hidl_blob.getInt32(_hidl_offset + 40 + 8 /* offsetof(hidl_vec<T>, mSize) */);
            android.os.HwBlob childBlob = parcel.readEmbeddedBuffer(
                    _hidl_vec_size * 16,_hidl_blob.handle(),
                    _hidl_offset + 40 + 0 /* offsetof(hidl_vec<T>, mBuffer) */,true /* nullable */);

            cfData.clear();
            for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; ++_hidl_index_0) {
                final android.hardware.radio.V1_0.CfData _hidl_vec_element = new android.hardware.radio.V1_0.CfData();
                _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 16);
                cfData.add(_hidl_vec_element);
            }
        }
    }

    public final void writeToParcel(android.os.HwParcel parcel) {
        android.os.HwBlob _hidl_blob = new android.os.HwBlob(56 /* size */);
        writeEmbeddedToBlob(_hidl_blob, 0 /* parentOffset */);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(
            android.os.HwParcel parcel, java.util.ArrayList<StkCcUnsolSsResult> _hidl_vec) {
        android.os.HwBlob _hidl_blob = new android.os.HwBlob(16 /* sizeof(hidl_vec<T>) */);
        {
            int _hidl_vec_size = _hidl_vec.size();
            _hidl_blob.putInt32(0 + 8 /* offsetof(hidl_vec<T>, mSize) */, _hidl_vec_size);
            _hidl_blob.putBool(0 + 12 /* offsetof(hidl_vec<T>, mOwnsBuffer) */, false);
            android.os.HwBlob childBlob = new android.os.HwBlob((int)(_hidl_vec_size * 56));
            for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; ++_hidl_index_0) {
                _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 56);
            }
            _hidl_blob.putBlob(0 + 0 /* offsetof(hidl_vec<T>, mBuffer) */, childBlob);
        }

        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(
            android.os.HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putInt32(_hidl_offset + 0, serviceType);
        _hidl_blob.putInt32(_hidl_offset + 4, requestType);
        _hidl_blob.putInt32(_hidl_offset + 8, teleserviceType);
        _hidl_blob.putInt32(_hidl_offset + 12, serviceClass);
        _hidl_blob.putInt32(_hidl_offset + 16, result);
        {
            int _hidl_vec_size = ssInfo.size();
            _hidl_blob.putInt32(_hidl_offset + 24 + 8 /* offsetof(hidl_vec<T>, mSize) */, _hidl_vec_size);
            _hidl_blob.putBool(_hidl_offset + 24 + 12 /* offsetof(hidl_vec<T>, mOwnsBuffer) */, false);
            android.os.HwBlob childBlob = new android.os.HwBlob((int)(_hidl_vec_size * 16));
            for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; ++_hidl_index_0) {
                ssInfo.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 16);
            }
            _hidl_blob.putBlob(_hidl_offset + 24 + 0 /* offsetof(hidl_vec<T>, mBuffer) */, childBlob);
        }
        {
            int _hidl_vec_size = cfData.size();
            _hidl_blob.putInt32(_hidl_offset + 40 + 8 /* offsetof(hidl_vec<T>, mSize) */, _hidl_vec_size);
            _hidl_blob.putBool(_hidl_offset + 40 + 12 /* offsetof(hidl_vec<T>, mOwnsBuffer) */, false);
            android.os.HwBlob childBlob = new android.os.HwBlob((int)(_hidl_vec_size * 16));
            for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; ++_hidl_index_0) {
                cfData.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 16);
            }
            _hidl_blob.putBlob(_hidl_offset + 40 + 0 /* offsetof(hidl_vec<T>, mBuffer) */, childBlob);
        }
    }
};
