package com.vitaliyhtc.socialnetworksapi.auth;

import android.content.Intent;

public interface AuthProvider {

    void onCreate();
    void onActivityResult(int requestCode, int resultCode, Intent data);
    void signIn(OnSignInResultListener onSignInResultListener);
    boolean trySilentSignIn(OnSignInResultListener onSignInResultListener);
    void logOut(OnLogOutResultListener onLogOutResultListener);
    void addOnUserSignInSuccessfulListener(OnUserSignInSuccessfulListener listener);
}
