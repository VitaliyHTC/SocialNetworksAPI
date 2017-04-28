package com.vitaliyhtc.socialnetworksapi.auth;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.vitaliyhtc.socialnetworksapi.MyApplication;

abstract class GoogleAuthProviderBase implements AuthProviderBase {

    static final String TAG = "GoogleAuthProvider";

    static final int RC_SIGN_IN = 9001;

    GoogleApiClient mGoogleApiClient;

    Context mContext;
    Fragment mFragment;

    GoogleAuthProviderBase(Context context, Fragment fragment) {
        mContext = context;
        mFragment = fragment;
    }

    @Override
    public void onCreate() {
        initGoogle();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void initGoogle() {
        mGoogleApiClient = MyApplication.getGoogleApiClient();
    }

    abstract void handleSignInResult(GoogleSignInResult result);
}
