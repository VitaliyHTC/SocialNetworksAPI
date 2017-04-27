package com.vitaliyhtc.socialnetworksapi.auth;

import android.content.Context;
import android.content.Intent;

import com.vitaliyhtc.socialnetworksapi.Constants;
import com.vitaliyhtc.socialnetworksapi.R;

public class AuthUserInfoManagerImpl implements AuthUserInfoManager {

    private Context mContext;

    private GoogleAuthUserInfoProvider mGoogleAuthUserInfoProvider;
    private FacebookAuthUserInfoProvider mFacebookAuthUserInfoProvider;

    public AuthUserInfoManagerImpl(Context context) {
        mContext = context;
        mGoogleAuthUserInfoProvider = new GoogleAuthUserInfoProvider(context);
        mFacebookAuthUserInfoProvider = new FacebookAuthUserInfoProvider(context);
    }

    @Override
    public void onCreate() {
        mGoogleAuthUserInfoProvider.onCreate();
        mFacebookAuthUserInfoProvider.onCreate();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mGoogleAuthUserInfoProvider.onActivityResult(requestCode, resultCode, data);
        mFacebookAuthUserInfoProvider.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void getUserInfoByProviderId(OnUserInfoListener listener, int providerId) {
        if (providerId == Constants.AUTH_BY_GOOGLE) {
            mGoogleAuthUserInfoProvider.getUserInfo(listener);
        } else if (providerId == Constants.AUTH_BY_FACEBOOK) {
            mFacebookAuthUserInfoProvider.getUserInfo(listener);
        } else {
            throw new IllegalArgumentException(mContext.getString(R.string.auth_error_no_auth_provider_found_by_id));
        }
    }

    @Override
    public void logOutByProviderId(OnLogOutResultListener onLogOutResultListener, int providerId) {
        if (providerId == Constants.AUTH_BY_GOOGLE) {
            mGoogleAuthUserInfoProvider.logOut(onLogOutResultListener);
        } else if (providerId == Constants.AUTH_BY_FACEBOOK) {
            mFacebookAuthUserInfoProvider.logOut(onLogOutResultListener);
        } else {
            throw new IllegalArgumentException(mContext.getString(R.string.auth_error_no_auth_provider_found_by_id));
        }
    }
}
