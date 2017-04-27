package com.vitaliyhtc.socialnetworksapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class User implements Parcelable {

    public static final String KEY_USER_BIRTH_DATE = "userBirthDate";

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private String userUid;
    private String userName;
    private String userEmail;
    private String userPhotoUrl;
    private Map<String, String> additionalData = new HashMap<>();

    public User() {
    }

    private User(Parcel source) {
        String[] data = new String[5];
        source.readStringArray(data);
        userUid = data[0];
        userName = data[1];
        userEmail = data[2];
        userPhotoUrl = data[3];
        additionalData.put(KEY_USER_BIRTH_DATE, data[4]);
    }

    public User(String userUid, String userName, String userEmail, String userPhotoUrl) {
        this.userUid = userUid;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhotoUrl = userPhotoUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                userUid,
                userName,
                userEmail,
                userPhotoUrl,
                additionalData.get(KEY_USER_BIRTH_DATE)
        });
    }


    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getAdditionalDataByKey(String key) {
        return additionalData.get(key);
    }

    public void putAdditionalData(String key, String value) {
        additionalData.put(key, value);
    }
}
