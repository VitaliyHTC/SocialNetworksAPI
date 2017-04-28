package com.vitaliyhtc.socialnetworksapi.presenter;

import com.vitaliyhtc.socialnetworksapi.model.IntentWrap;

public interface LoginPresenter extends BasePresenter {
    void onSignInButtonClick(int providerId);
    void trySilentSignIn();
    void onActivityResult(int requestCode, int resultCode, IntentWrap data);
}
