package com.vitaliyhtc.socialnetworksapi.auth;

import android.content.Context;
import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

public class FacebookAuthProvider implements AuthProvider {


    private Context mContext;

    private CallbackManager mCallbackManager;

    public FacebookAuthProvider(Context context) {
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

    @Override
    public void signIn(OnSignInResultListener onSignInResultListener) {

    }

    @Override
    public void logOut(OnLogOutResultListener onLogOutResultListener) {

    }

    private void initFacebook() {
        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }
}
