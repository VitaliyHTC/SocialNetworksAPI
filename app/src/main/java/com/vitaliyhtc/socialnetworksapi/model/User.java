package com.vitaliyhtc.socialnetworksapi.model;

import java.util.HashMap;
import java.util.Map;

public class User {

    public static final String USER_BIRTH_DATE = "userBirthDate";

    private String userUid;
    private String userName;
    private String userEmail;
    private String userPhotoUrl;
    private Map<String, String> additionalData = new HashMap<>();

    public User() {
    }

    public User(String userUid, String userName, String userEmail, String userPhotoUrl) {
        this.userUid = userUid;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhotoUrl = userPhotoUrl;
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

    public String getAdditionalDataByKey(String key){
        return additionalData.get(key);
    }

    public void putAdditionalData(String key, String value){

    }
}
