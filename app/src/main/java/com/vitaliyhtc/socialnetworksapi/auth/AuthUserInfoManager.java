package com.vitaliyhtc.socialnetworksapi.auth;

public interface AuthUserInfoManager extends AuthManagerBase {
    void getUserInfoByProviderId(OnUserInfoListener listener, int providerId);
    void logOutByProviderId(OnLogOutResultListener onLogOutResultListener, int providerId);
}
