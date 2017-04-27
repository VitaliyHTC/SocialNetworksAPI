package com.vitaliyhtc.socialnetworksapi.auth;

import android.app.Activity;
import android.content.Context;

import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.vitaliyhtc.socialnetworksapi.R;

import java.util.Arrays;

public class FacebookAuthProvider extends FacebookAuthProviderBase
        implements AuthProvider {

    private OnSignInResultListener mOnSignInResultListener;

    public FacebookAuthProvider(Context context) {
        super(context);
    }

    @Override
    public void signIn(OnSignInResultListener onSignInResultListener) {
        mOnSignInResultListener = onSignInResultListener;
        LoginManager.getInstance().logInWithReadPermissions((Activity) mContext, Arrays.asList(FB_PERMISSIONS));
    }

    @Override
    public boolean trySilentSignIn(OnSignInResultListener onSignInResultListener) {
        mOnSignInResultListener = onSignInResultListener;

        if (AccessToken.getCurrentAccessToken() != null) {
            mOnSignInResultListener.onSignInSuccess();
            return true;
        }
        return false;
    }

    @Override
    public void onSuccessCall(LoginResult loginResult) {
        mOnSignInResultListener.onSignInSuccess();
    }

    @Override
    public void onCancelCall() {
        mOnSignInResultListener.onSignInError(mContext.getString(R.string.fb_error_login_action_is_canceled));
    }

    @Override
    public void onErrorCall(FacebookException exception) {
        mOnSignInResultListener.onSignInError(exception.getMessage());
    }
}
