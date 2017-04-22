package com.vitaliyhtc.socialnetworksapi.auth;

import com.vitaliyhtc.socialnetworksapi.model.User;

public interface OnSignInResultListener {
    void onSignInSuccess(User user);
    void onSignInError(String message);
}
