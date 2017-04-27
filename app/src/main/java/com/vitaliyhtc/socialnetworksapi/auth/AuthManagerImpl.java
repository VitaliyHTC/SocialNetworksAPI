package com.vitaliyhtc.socialnetworksapi.auth;

import android.content.Context;
import android.content.Intent;

import com.vitaliyhtc.socialnetworksapi.Constants;
import com.vitaliyhtc.socialnetworksapi.R;

public class AuthManagerImpl implements AuthManager {

    private Context mContext;

    private GoogleAuthProvider mGoogleAuthProvider;
    private FacebookAuthProvider mFacebookAuthProvider;

    public AuthManagerImpl(Context context) {
        mContext = context;
        mGoogleAuthProvider = new GoogleAuthProvider(context);
        mFacebookAuthProvider = new FacebookAuthProvider(context);
    }

    @Override
    public void onCreate() {
        mGoogleAuthProvider.onCreate();
        mFacebookAuthProvider.onCreate();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mGoogleAuthProvider.onActivityResult(requestCode, resultCode, data);
        mFacebookAuthProvider.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void signInWith(OnSignInResultListener onSignInResultListener, int providerId) {
        if (providerId == Constants.AUTH_BY_GOOGLE) {
            mGoogleAuthProvider.signIn(onSignInResultListener);
        } else if (providerId == Constants.AUTH_BY_FACEBOOK) {
            mFacebookAuthProvider.signIn(onSignInResultListener);
        } else {
            throw new IllegalArgumentException(mContext.getString(R.string.auth_error_no_auth_provider_found_by_id));
        }
    }

    @Override
    public int trySilentSignIn(OnSignInResultListener onSignInResultListener) {
        if (mGoogleAuthProvider.trySilentSignIn(onSignInResultListener)) {
            return Constants.AUTH_BY_GOOGLE;
        } else if (mFacebookAuthProvider.trySilentSignIn(onSignInResultListener)) {
            return Constants.AUTH_BY_FACEBOOK;
        }
        return Constants.AUTH_BY_NOT_SELECTED;
    }
}
