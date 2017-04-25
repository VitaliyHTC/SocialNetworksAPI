package com.vitaliyhtc.socialnetworksapi.auth;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.vitaliyhtc.socialnetworksapi.model.User;

import java.util.ArrayList;
import java.util.List;

public class GoogleAuthProvider implements AuthProvider {

    private static final String TAG = "GoogleAuthProvider";

    private static final int RC_SIGN_IN = 9001;

    private Context mContext;

    private GoogleApiClient mGoogleApiClient;

    private OnSignInResultListener mOnSignInResultListener;

    private List<OnUserSignInSuccessfulListener> mOnUserSignInSuccessfulListeners;

    public GoogleAuthProvider(Context context) {
        mContext = context;
        mOnUserSignInSuccessfulListeners = new ArrayList<>();
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

    @Override
    public void logOut(final OnLogOutResultListener onLogOutResultListener) {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        onLogOutResultListener.onLogOut();
                    }
                });
    }

    @Override
    public void addOnUserSignInSuccessfulListener(OnUserSignInSuccessfulListener listener) {
        mOnUserSignInSuccessfulListeners.add(listener);
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

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            User user = new User(acct.getId(), acct.getDisplayName(), acct.getEmail(), acct.getPhotoUrl().toString());

            mOnSignInResultListener.onSignInSuccess(user);
            for (OnUserSignInSuccessfulListener listener : mOnUserSignInSuccessfulListeners) {
                listener.onUserSignInSuccess(user);
            }
        } else {
            mOnSignInResultListener.onSignInError("SignIn failed!");
        }
    }
}
