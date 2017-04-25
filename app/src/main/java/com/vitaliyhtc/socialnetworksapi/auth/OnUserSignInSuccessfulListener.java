package com.vitaliyhtc.socialnetworksapi.auth;

import com.vitaliyhtc.socialnetworksapi.model.User;

public interface OnUserSignInSuccessfulListener {
    void onUserSignInSuccess(User user);
}
