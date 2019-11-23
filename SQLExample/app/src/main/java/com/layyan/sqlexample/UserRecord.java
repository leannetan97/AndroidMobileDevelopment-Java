package com.layyan.sqlexample;

import android.os.Parcel;
import android.os.Parcelable;

public class UserRecord implements Parcelable {
    private String id;
    private String phone;
    private String name;
    private String email;

    public UserRecord(){

    }

    protected UserRecord(Parcel in) {
        id = in.readString();
        phone = in.readString();
        name = in.readString();
        email = in.readString();
    }

    public static final Creator<UserRecord> CREATOR = new Creator<UserRecord>() {
        @Override
        public UserRecord createFromParcel(Parcel in) {
            return new UserRecord(in);
        }

        @Override
        public UserRecord[] newArray(int size) {
            return new UserRecord[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return UserContract.User.COLUMN_PHONE + " : " + phone + "," + UserContract.User.COLUMN_NAME + " : " + name + "," + UserContract.User.COLUMN_EMAIL + " : " + email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(phone);
        parcel.writeString(name);
        parcel.writeString(email);
    }
}

