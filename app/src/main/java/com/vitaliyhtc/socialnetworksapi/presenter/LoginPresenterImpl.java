package com.vitaliyhtc.socialnetworksapi.presenter;

import com.vitaliyhtc.socialnetworksapi.view.BaseView;
import com.vitaliyhtc.socialnetworksapi.view.LoginView;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView mLoginView;

    public LoginPresenterImpl() {
    }

    @Override
    public void onAttachView(BaseView baseView) {
        mLoginView = (LoginView) baseView;
    }

    @Override
    public void onDetachView() {
        mLoginView = null;
        // other release actions do here.
    }

    @Override
    public void onGoogleSignInButtonClick() {

    }

    @Override
    public void onFacebookSignInButtonClick() {

    }
}
