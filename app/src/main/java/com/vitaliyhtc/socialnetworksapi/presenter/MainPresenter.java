package com.vitaliyhtc.socialnetworksapi.presenter;

import com.vitaliyhtc.socialnetworksapi.model.BundleWrap;
import com.vitaliyhtc.socialnetworksapi.model.IntentWrap;
import com.vitaliyhtc.socialnetworksapi.model.User;

public interface MainPresenter {

    void onCreate(BundleWrap savedInstanceState);
    void onPause();
    void onSaveInstanceState(BundleWrap outState);
    void trySilentSignIn();
    void onGoogleSignInButtonClick();
    void onFacebookSignInButtonClick();
    void onLogOut();
    void onActivityResult(int requestCode, int resultCode, IntentWrap data);
    User getRetainedUser();
}
