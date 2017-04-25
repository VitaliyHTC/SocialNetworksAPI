package com.vitaliyhtc.socialnetworksapi.auth;

import android.content.Intent;
import android.os.Bundle;

import com.vitaliyhtc.socialnetworksapi.model.User;

public interface AuthManager {

    void onCreate(Bundle savedInstanceState);
    void onPause();
    void onSaveInstanceState(Bundle outState);
    void onActivityResult(int requestCode, int resultCode, Intent data);
    void signInWith(int authProvider, OnSignInResultListener onSignInResultListener);
    void trySilentSignIn(OnSignInResultListener onSignInResultListener);
    void logOut(OnLogOutResultListener onLogOutResultListener);
    User getRetainedUser();
}
