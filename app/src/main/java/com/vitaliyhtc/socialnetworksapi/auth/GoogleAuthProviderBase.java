package com.vitaliyhtc.socialnetworksapi.auth;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

abstract class GoogleAuthProviderBase implements AuthProviderBase {

    static final String TAG = "GoogleAuthProvider";

    static final int RC_SIGN_IN = 9001;

    GoogleApiClient mGoogleApiClient;

    Context mContext;

    GoogleAuthProviderBase(Context context) {
        mContext = context;
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
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Log.d(TAG, "onConnectionFailed:" + connectionResult);
            }
        };

        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage((AppCompatActivity) mContext, onConnectionFailedListener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    abstract void handleSignInResult(GoogleSignInResult result);
}
