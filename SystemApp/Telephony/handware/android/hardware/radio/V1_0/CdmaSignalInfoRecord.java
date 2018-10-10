package android.hardware.radio.V1_0;


public final class CdmaSignalInfoRecord {
    public boolean isPresent;
    public byte signalType;
    public byte alertPitch;
    public byte signal;

    @Override
    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null) {
            return false;
        }
        if (otherObject.getClass() != android.hardware.radio.V1_0.CdmaSignalInfoRecord.class) {
            return false;
        }
        android.hardware.radio.V1_0.CdmaSignalInfoRecord other = (android.hardware.radio.V1_0.CdmaSignalInfoRecord)otherObject;
        if (this.isPresent != other.isPresent) {
            return false;
        }
        if (this.signalType != other.signalType) {
            return false;
        }
        if (this.alertPitch != other.alertPitch) {
            return false;
        }
        if (this.signal != other.signal) {
            return false;
        }
        return true;
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(
                android.os.HidlSupport.deepHashCode(this.isPresent), 
                android.os.HidlSupport.deepHashCode(this.signalType), 
                android.os.HidlSupport.deepHashCode(this.alertPitch), 
                android.os.HidlSupport.deepHashCode(this.signal));
    }

    @Override
    public final String toString() {
        java.lang.StringBuilder builder = new java.lang.StringBuilder();
        builder.append("{");
        builder.append(".isPresent = ");
        builder.append(this.isPresent);
        builder.append(", .signalType = ");
        builder.append(this.signalType);
        builder.append(", .alertPitch = ");
        builder.append(this.alertPitch);
        builder.append(", .signal = ");
        builder.append(this.signal);
        builder.append("}");
        return builder.toString();
    }

    public final void readFromParcel(android.os.HwParcel parcel) {
        android.os.HwBlob blob = parcel.readBuffer(4/* size */);
        readEmbeddedFromParcel(parcel, blob, 0 /* parentOffset */);
    }

    public static final java.util.ArrayList<CdmaSignalInfoRecord> readVectorFromParcel(android.os.HwParcel parcel) {
        java.util.ArrayList<CdmaSignalInfoRecord> _hidl_vec = new java.util.ArrayList();
        android.os.HwBlob _hidl_blob = parcel.readBuffer(16 /* sizeof hidl_vec<T> */);

        {
            int _hidl_vec_size = _hidl_blob.getInt32(0 + 8 /* offsetof(hidl_vec<T>, mSize) */);
            android.os.HwBlob childBlob = parcel.readEmbeddedBuffer(
                    _hidl_vec_size * 4,_hidl_blob.handle(),
                    0 + 0 /* offsetof(hidl_vec<T>, mBuffer) */,true /* nullable */);

            _hidl_vec.clear();
            for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; ++_hidl_index_0) {
                final android.hardware.radio.V1_0.CdmaSignalInfoRecord _hidl_vec_element = new android.hardware.radio.V1_0.CdmaSignalInfoRecord();
                _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 4);
                _hidl_vec.add(_hidl_vec_element);
            }
        }

        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(
            android.os.HwParcel parcel, android.os.HwBlob _hidl_blob, long _hidl_offset) {
        isPresent = _hidl_blob.getBool(_hidl_offset + 0);
        signalType = _hidl_blob.getInt8(_hidl_offset + 1);
        alertPitch = _hidl_blob.getInt8(_hidl_offset + 2);
        signal = _hidl_blob.getInt8(_hidl_offset + 3);
    }

    public final void writeToParcel(android.os.HwParcel parcel) {
        android.os.HwBlob _hidl_blob = new android.os.HwBlob(4 /* size */);
        writeEmbeddedToBlob(_hidl_blob, 0 /* parentOffset */);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(
            android.os.HwParcel parcel, java.util.ArrayList<CdmaSignalInfoRecord> _hidl_vec) {
        android.os.HwBlob _hidl_blob = new android.os.HwBlob(16 /* sizeof(hidl_vec<T>) */);
        {
            int _hidl_vec_size = _hidl_vec.size();
            _hidl_blob.putInt32(0 + 8 /* offsetof(hidl_vec<T>, mSize) */, _hidl_vec_size);
            _hidl_blob.putBool(0 + 12 /* offsetof(hidl_vec<T>, mOwnsBuffer) */, false);
            android.os.HwBlob childBlob = new android.os.HwBlob((int)(_hidl_vec_size * 4));
            for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; ++_hidl_index_0) {
                _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 4);
            }
            _hidl_blob.putBlob(0 + 0 /* offsetof(hidl_vec<T>, mBuffer) */, childBlob);
        }

        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(
            android.os.HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putBool(_hidl_offset + 0, isPresent);
        _hidl_blob.putInt8(_hidl_offset + 1, signalType);
        _hidl_blob.putInt8(_hidl_offset + 2, alertPitch);
        _hidl_blob.putInt8(_hidl_offset + 3, signal);
    }
};

