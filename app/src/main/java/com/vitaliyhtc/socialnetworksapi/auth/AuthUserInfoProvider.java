package com.vitaliyhtc.socialnetworksapi.auth;

public interface AuthUserInfoProvider extends AuthProviderBase {
    void getUserInfo(OnUserInfoListener listener);
    void logOut(OnLogOutResultListener onLogOutResultListener);
}
