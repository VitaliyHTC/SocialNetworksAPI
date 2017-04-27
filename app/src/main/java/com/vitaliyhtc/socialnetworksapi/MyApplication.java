package com.vitaliyhtc.socialnetworksapi;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

public class MyApplication extends Application {
    private static MyApplication sMyApplication;
    private static GoogleApiClient sGoogleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        sMyApplication = this;
        initGoogleApi();
    }

    private void initGoogleApi(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        sGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public static GoogleApiClient getGoogleApiClient() {
        return sGoogleApiClient;
    }

    public static Context getAppContext() {
        return sMyApplication;
    }
}
