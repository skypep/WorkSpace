package android.hardware.radio.V1_0;


public final class LteSignalStrength {
    public int signalStrength;
    public int rsrp;
    public int rsrq;
    public int rssnr;
    public int cqi;
    public int timingAdvance;

    @Override
    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null) {
            return false;
        }
        if (otherObject.getClass() != android.hardware.radio.V1_0.LteSignalStrength.class) {
            return false;
        }
        android.hardware.radio.V1_0.LteSignalStrength other = (android.hardware.radio.V1_0.LteSignalStrength)otherObject;
        if (this.signalStrength != other.signalStrength) {
            return false;
        }
        if (this.rsrp != other.rsrp) {
            return false;
        }
        if (this.rsrq != other.rsrq) {
            return false;
        }
        if (this.rssnr != other.rssnr) {
            return false;
        }
        if (this.cqi != other.cqi) {
            return false;
        }
        if (this.timingAdvance != other.timingAdvance) {
            return false;
        }
        return true;
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(
                android.os.HidlSupport.deepHashCode(this.signalStrength), 
                android.os.HidlSupport.deepHashCode(this.rsrp), 
                android.os.HidlSupport.deepHashCode(this.rsrq), 
                android.os.HidlSupport.deepHashCode(this.rssnr), 
                android.os.HidlSupport.deepHashCode(this.cqi), 
                android.os.HidlSupport.deepHashCode(this.timingAdvance));
    }

    @Override
    public final String toString() {
        java.lang.StringBuilder builder = new java.lang.StringBuilder();
        builder.append("{");
        builder.append(".signalStrength = ");
        builder.append(this.signalStrength);
        builder.append(", .rsrp = ");
        builder.append(this.rsrp);
        builder.append(", .rsrq = ");
        builder.append(this.rsrq);
        builder.append(", .rssnr = ");
        builder.append(this.rssnr);
        builder.append(", .cqi = ");
        builder.append(this.cqi);
        builder.append(", .timingAdvance = ");
        builder.append(this.timingAdvance);
        builder.append("}");
        return builder.toString();
    }

    public final void readFromParcel(android.os.HwParcel parcel) {
        android.os.HwBlob blob = parcel.readBuffer(24/* size */);
        readEmbeddedFromParcel(parcel, blob, 0 /* parentOffset */);
    }

    public static final java.util.ArrayList<LteSignalStrength> readVectorFromParcel(android.os.HwParcel parcel) {
        java.util.ArrayList<LteSignalStrength> _hidl_vec = new java.util.ArrayList();
        android.os.HwBlob _hidl_blob = parcel.readBuffer(16 /* sizeof hidl_vec<T> */);

        {
            int _hidl_vec_size = _hidl_blob.getInt32(0 + 8 /* offsetof(hidl_vec<T>, mSize) */);
            android.os.HwBlob childBlob = parcel.readEmbeddedBuffer(
                    _hidl_vec_size * 24,_hidl_blob.handle(),
                    0 + 0 /* offsetof(hidl_vec<T>, mBuffer) */,true /* nullable */);

            _hidl_vec.clear();
            for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; ++_hidl_index_0) {
                final android.hardware.radio.V1_0.LteSignalStrength _hidl_vec_element = new android.hardware.radio.V1_0.LteSignalStrength();
                _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 24);
                _hidl_vec.add(_hidl_vec_element);
            }
        }

        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(
            android.os.HwParcel parcel, android.os.HwBlob _hidl_blob, long _hidl_offset) {
        signalStrength = _hidl_blob.getInt32(_hidl_offset + 0);
        rsrp = _hidl_blob.getInt32(_hidl_offset + 4);
        rsrq = _hidl_blob.getInt32(_hidl_offset + 8);
        rssnr = _hidl_blob.getInt32(_hidl_offset + 12);
        cqi = _hidl_blob.getInt32(_hidl_offset + 16);
        timingAdvance = _hidl_blob.getInt32(_hidl_offset + 20);
    }

    public final void writeToParcel(android.os.HwParcel parcel) {
        android.os.HwBlob _hidl_blob = new android.os.HwBlob(24 /* size */);
        writeEmbeddedToBlob(_hidl_blob, 0 /* parentOffset */);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(
            android.os.HwParcel parcel, java.util.ArrayList<LteSignalStrength> _hidl_vec) {
        android.os.HwBlob _hidl_blob = new android.os.HwBlob(16 /* sizeof(hidl_vec<T>) */);
        {
            int _hidl_vec_size = _hidl_vec.size();
            _hidl_blob.putInt32(0 + 8 /* offsetof(hidl_vec<T>, mSize) */, _hidl_vec_size);
            _hidl_blob.putBool(0 + 12 /* offsetof(hidl_vec<T>, mOwnsBuffer) */, false);
            android.os.HwBlob childBlob = new android.os.HwBlob((int)(_hidl_vec_size * 24));
            for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; ++_hidl_index_0) {
                _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 24);
            }
            _hidl_blob.putBlob(0 + 0 /* offsetof(hidl_vec<T>, mBuffer) */, childBlob);
        }

        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(
            android.os.HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putInt32(_hidl_offset + 0, signalStrength);
        _hidl_blob.putInt32(_hidl_offset + 4, rsrp);
        _hidl_blob.putInt32(_hidl_offset + 8, rsrq);
        _hidl_blob.putInt32(_hidl_offset + 12, rssnr);
        _hidl_blob.putInt32(_hidl_offset + 16, cqi);
        _hidl_blob.putInt32(_hidl_offset + 20, timingAdvance);
    }
};

