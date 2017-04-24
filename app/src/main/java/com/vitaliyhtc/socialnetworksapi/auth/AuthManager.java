package com.vitaliyhtc.socialnetworksapi.auth;

import android.content.Intent;
import android.os.Bundle;

public interface AuthManager {

    void onCreate(Bundle savedInstanceState);
    void onSaveInstanceState(Bundle outState);
    void onActivityResult(int requestCode, int resultCode, Intent data);
    void signInWith(int authProvider, OnSignInResultListener onSignInResultListener);
    void trySilentSignIn(OnSignInResultListener onSignInResultListener);
    void logOut(OnLogOutResultListener onLogOutResultListener);
}
