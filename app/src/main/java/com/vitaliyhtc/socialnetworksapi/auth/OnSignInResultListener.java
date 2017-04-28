package com.vitaliyhtc.socialnetworksapi.auth;

public interface OnSignInResultListener {
    void onSignInSuccess(int providerId);
    void onSignInError(String message);
}
