package com.vitaliyhtc.socialnetworksapi.view;

public interface LoginView extends BaseView {
    void onSignInSuccess(int providerId);
    void onSignInError(String message);
}
