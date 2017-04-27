package com.vitaliyhtc.socialnetworksapi.auth;

public interface AuthProvider {
    void signIn(OnSignInResultListener onSignInResultListener);
    boolean trySilentSignIn(OnSignInResultListener onSignInResultListener);
}
