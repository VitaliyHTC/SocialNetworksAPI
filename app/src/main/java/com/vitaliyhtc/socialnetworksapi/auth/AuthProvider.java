package com.vitaliyhtc.socialnetworksapi.auth;

import android.content.Intent;

public interface AuthProvider {

    void onCreate();
    void onActivityResult(int requestCode, int resultCode, Intent data);
    void signIn(OnSignInResultListener onSignInResultListener);
    void logOut(OnLogOutResultListener onLogOutResultListener);
}
