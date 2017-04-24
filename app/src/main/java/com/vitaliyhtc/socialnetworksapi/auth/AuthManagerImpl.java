package com.vitaliyhtc.socialnetworksapi.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AuthManagerImpl implements AuthManager {

    public static final int AUTH_BY_GOOGLE = 0x00F1;
    public static final int AUTH_BY_FACEBOOK = 0x00F2;
    private static final int AUTH_BY_NOT_SELECTED = 0x0000;

    private static final String KEY_AUTH_PROVIDER = "authProvider";

    private int mCurrentAuthProvider;

    private AuthProvider mGoogleAuthProvider;
    private AuthProvider mFacebookAuthProvider;

    private AuthProvider mAuthProvider;


    public AuthManagerImpl(Context context) {
        mGoogleAuthProvider = new GoogleAuthProvider(context);
        mFacebookAuthProvider = new FacebookAuthProvider(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mGoogleAuthProvider.onCreate();
        mFacebookAuthProvider.onCreate();

        if (savedInstanceState != null) {
            mCurrentAuthProvider = savedInstanceState.getInt(KEY_AUTH_PROVIDER, AUTH_BY_NOT_SELECTED);
            if (mCurrentAuthProvider == AUTH_BY_GOOGLE) {
                mAuthProvider = mGoogleAuthProvider;
            }
            if (mCurrentAuthProvider == AUTH_BY_FACEBOOK) {
                mAuthProvider = mFacebookAuthProvider;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_AUTH_PROVIDER, mCurrentAuthProvider);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mGoogleAuthProvider.onActivityResult(requestCode, resultCode, data);
        mFacebookAuthProvider.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void signInWith(int authProvider, OnSignInResultListener onSignInResultListener) {
        if (authProvider == AUTH_BY_GOOGLE) {
            mAuthProvider = mGoogleAuthProvider;
            mCurrentAuthProvider = AUTH_BY_GOOGLE;
        } else if (authProvider == AUTH_BY_FACEBOOK) {
            mAuthProvider = mFacebookAuthProvider;
            mCurrentAuthProvider = AUTH_BY_FACEBOOK;
        }
        if (mAuthProvider != null) {
            mAuthProvider.signIn(onSignInResultListener);
        } else {
            throw new IllegalArgumentException("No AuthProvider found for given id.");
        }
    }

    @Override
    public void trySilentSignIn(OnSignInResultListener onSignInResultListener) {
        if (mGoogleAuthProvider.trySilentSignIn(onSignInResultListener)) {
            mAuthProvider = mGoogleAuthProvider;
        } else if (mFacebookAuthProvider.trySilentSignIn(onSignInResultListener)) {
            mAuthProvider = mFacebookAuthProvider;
        }
    }

    @Override
    public void logOut(OnLogOutResultListener onLogOutResultListener) {
        if (mAuthProvider != null) {
            mAuthProvider.logOut(onLogOutResultListener);
            mCurrentAuthProvider = AUTH_BY_NOT_SELECTED;
            mAuthProvider = null;
        }
    }


}
