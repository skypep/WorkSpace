package com.android.toro.src.contact;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By liujia
 * on 2018/11/30.
 **/
public class ToroContact implements Parcelable {

    private long contactID;
    private List<String> numbers;

    public long getContactID() {
        return contactID;
    }

    public void setContactID(long contactID) {
        this.contactID = contactID;
    }

    public List<String> getNumber() {
        return numbers;
    }

    public void setNumber(List<String> number) {
        this.numbers = number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeLong(contactID);
        out.writeInt(numbers.size());
        for(String number : numbers){
            out.writeString(number);
        }
//        out.writeList(number);
    }

    public static final Parcelable.Creator<ToroContact> CREATOR = new Creator<ToroContact>(){

        @Override
        public ToroContact[] newArray(int size){
            return new ToroContact[size];
        }

        @Override
        public ToroContact createFromParcel(Parcel in){
            return new ToroContact(in);
        }
    };

    public ToroContact(){

    }

    public ToroContact(Parcel in){
        contactID = in.readLong();
        int count = in.readInt();
        numbers = new ArrayList<>();
        for(int i = 0; i < count ; i++) {
            String number = in.readString();
            numbers.add(number);
        }
//        in.readStringList(this.number);
    }
}
