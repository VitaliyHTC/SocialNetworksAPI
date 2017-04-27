package com.vitaliyhtc.socialnetworksapi.auth;

public interface OnSignInResultListener {
    void onSignInSuccess();
    void onSignInError(String message);
}
