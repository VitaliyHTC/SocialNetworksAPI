package com.vitaliyhtc.socialnetworksapi.auth;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.vitaliyhtc.socialnetworksapi.R;
import com.vitaliyhtc.socialnetworksapi.model.User;

public class GoogleAuthUserInfoProvider extends GoogleAuthProviderBase
        implements AuthUserInfoProvider {

    private OnUserInfoListener mOnUserInfoListener;
    private OnLogOutResultListener mOnLogOutResultListener;

    public GoogleAuthUserInfoProvider(Context context, Fragment fragment) {
        super(context, fragment);
    }

    @Override
    public void getUserInfo(OnUserInfoListener listener) {
        mOnUserInfoListener = listener;
        OptionalPendingResult<GoogleSignInResult> pendingResult =
                Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (pendingResult.isDone()) {
            handleSignInResult(pendingResult.get());
        }
        // See https://developers.google.com/android/reference/com/google/android/gms/auth/api/signin/GoogleSignInApi
        // silentSignIn(GoogleApiClient client)
    }

    @Override
    public void logOut(final OnLogOutResultListener onLogOutResultListener) {
        mOnLogOutResultListener = onLogOutResultListener;
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
            mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                @Override
                public void onConnected(@Nullable Bundle bundle) {
                    signOut();
                }

                @Override
                public void onConnectionSuspended(int i) {
                    Log.d(TAG, "Google API Client Connection Suspended");
                }
            });
        } else {
            signOut();
        }
    }

    @Override
    public void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            User user = new User(acct.getId(), acct.getDisplayName(), acct.getEmail(), acct.getPhotoUrl().toString());
            mOnUserInfoListener.onUserInfoSuccess(user);
        } else {
            mOnUserInfoListener.onUserInfoError(mContext.getString(R.string.google_error_signin_failed));
        }
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        mOnLogOutResultListener.onLogOut();
                    }
                });
    }
}
