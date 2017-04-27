package com.vitaliyhtc.socialnetworksapi.auth;

import com.vitaliyhtc.socialnetworksapi.model.User;

public interface OnUserInfoListener {
    void onUserInfoSuccess(User user);
    void onUserInfoError(String message);
}
