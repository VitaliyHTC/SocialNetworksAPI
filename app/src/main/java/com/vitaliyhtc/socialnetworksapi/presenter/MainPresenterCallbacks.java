package com.vitaliyhtc.socialnetworksapi.presenter;

import com.vitaliyhtc.socialnetworksapi.model.User;

public interface MainPresenterCallbacks {

    void onUserSignedIn(User user);
    void onSignInError(String message);
    void onUserLoggedOut();
}
