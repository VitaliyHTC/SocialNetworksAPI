package com.vitaliyhtc.socialnetworksapi.auth;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.vitaliyhtc.socialnetworksapi.R;

public class GoogleAuthProvider extends GoogleAuthProviderBase
        implements AuthProvider {

    private OnSignInResultListener mOnSignInResultListener;

    public GoogleAuthProvider(Context context) {
        super(context);
    }

    @Override
    public void signIn(OnSignInResultListener onSignInResultListener) {
        mOnSignInResultListener = onSignInResultListener;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        ((AppCompatActivity) mContext).startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public boolean trySilentSignIn(OnSignInResultListener onSignInResultListener) {
        mOnSignInResultListener = onSignInResultListener;

        OptionalPendingResult<GoogleSignInResult> pendingResult =
                Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (pendingResult.isDone()) {
            handleSignInResult(pendingResult.get());
            return true;
        }
        // See https://developers.google.com/android/reference/com/google/android/gms/auth/api/signin/GoogleSignInApi
        // silentSignIn(GoogleApiClient client)
        return false;
    }

    public void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            mOnSignInResultListener.onSignInSuccess();
        } else {
            mOnSignInResultListener.onSignInError(mContext.getString(R.string.google_error_signin_failed));
        }
    }
}
