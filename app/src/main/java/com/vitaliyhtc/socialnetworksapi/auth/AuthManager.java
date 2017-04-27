package com.vitaliyhtc.socialnetworksapi.auth;

public interface AuthManager extends AuthManagerBase {
    void signInWith(OnSignInResultListener onSignInResultListener, int providerId);
    int trySilentSignIn(OnSignInResultListener onSignInResultListener);
}
