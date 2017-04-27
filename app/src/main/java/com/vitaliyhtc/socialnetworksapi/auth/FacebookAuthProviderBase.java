package com.vitaliyhtc.socialnetworksapi.auth;

import android.content.Context;
import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

abstract class FacebookAuthProviderBase implements AuthProviderBase {

    static final String[] FB_PERMISSIONS = {"public_profile", "email", "user_birthday"};
    static final String FB_KEY_FIELDS = "fields";
    static final String FB_VALUE_FIELDS = "id,name,email,birthday,picture";

    Context mContext;

    CallbackManager mCallbackManager;

    FacebookAuthProviderBase(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        initFacebook();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void initFacebook() {
        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        onSuccessCall(loginResult);
                    }

                    @Override
                    public void onCancel() {
                        onCancelCall();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        onErrorCall(exception);
                    }
                });
    }

    abstract void onSuccessCall(LoginResult loginResult);

    abstract void onCancelCall();

    abstract void onErrorCall(FacebookException exception);
}
