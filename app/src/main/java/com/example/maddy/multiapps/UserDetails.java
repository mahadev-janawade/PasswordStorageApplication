package com.example.maddy.multiapps;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;

/**
 * Created by Maddy on 3/25/2020.
 */

public class UserDetails implements Parcelable{

    public String id;
    public String applicationType;
    public String userName;
    public String email;
    public String password;
    public String number;
    public String description;
    public String securityKey;

    public String passwordCreatedDate;
    public String passwordExpiryDate;

    public UserDetails(){

    }

    protected UserDetails(Parcel in) {
        id = in.readString();
        applicationType = in.readString();
        userName = in.readString();
        email = in.readString();
        password = in.readString();
        number = in.readString();
        description = in.readString();
        passwordCreatedDate = in.readString();
        passwordExpiryDate = in.readString();
        securityKey = in.readString();
    }

    public static final Creator<UserDetails> CREATOR = new Creator<UserDetails>() {
        @Override
        public UserDetails createFromParcel(Parcel in) {
            return new UserDetails(in);
        }

        @Override
        public UserDetails[] newArray(int size) {
            return new UserDetails[size];
        }
    };

    public void setId(String id) {
        this.id = id;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    public void setPasswordCreatedDate(String passwordCreatedDate) {
        this.passwordCreatedDate = passwordCreatedDate;
    }

    public void setPasswordExpiryDate(String passwordExpiryDate) {
        this.passwordExpiryDate = passwordExpiryDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(applicationType);
        parcel.writeString(userName);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(number);
        parcel.writeString(description);
        parcel.writeString(passwordCreatedDate);
        parcel.writeString(passwordExpiryDate);
        parcel.writeString(securityKey);
    }



}
