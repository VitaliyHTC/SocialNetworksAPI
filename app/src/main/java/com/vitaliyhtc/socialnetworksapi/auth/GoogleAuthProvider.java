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
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.vitaliyhtc.socialnetworksapi.model.User;

public class GoogleAuthProvider implements AuthProvider {

    private static final String TAG = "GoogleAuthProvider";

    private static final int RC_SIGN_IN = 9001;

    private Context mContext;

    private GoogleApiClient mGoogleApiClient;

    private OnSignInResultListener mOnSignInResultListener;


    public GoogleAuthProvider(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        initGoogle();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
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
    public void logOut(final OnLogOutResultListener onLogOutResultListener) {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        onLogOutResultListener.onLogOut();
                    }
                });
    }


    private void initGoogle() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                // An unresolvable error has occurred and Google APIs (including Sign-In) will not
                // be available.
                Log.d(TAG, "onConnectionFailed:" + connectionResult);
            }
        };

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage(
                        (AppCompatActivity) mContext /* FragmentActivity */,
                        onConnectionFailedListener /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            User user = new User(acct.getId(), acct.getDisplayName(), acct.getEmail(), acct.getPhotoUrl().toString());

            mOnSignInResultListener.onSignInSuccess(user);
        } else {
            // Signed out, show unauthenticated UI.
            mOnSignInResultListener.onSignInError("SignIn failed!");
        }
    }
}
