package com.vitaliyhtc.socialnetworksapi.view;

import com.vitaliyhtc.socialnetworksapi.model.User;

public interface UserProfileView extends BaseView {
    void onUserInfoSuccess(User user);
    void onUserInfoError(String message);
    void onLogOut();
}
