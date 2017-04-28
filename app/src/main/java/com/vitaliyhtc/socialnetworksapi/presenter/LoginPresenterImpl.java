package com.vitaliyhtc.socialnetworksapi.presenter;

import android.util.Log;

import com.vitaliyhtc.socialnetworksapi.auth.AuthManager;
import com.vitaliyhtc.socialnetworksapi.auth.AuthManagerImpl;
import com.vitaliyhtc.socialnetworksapi.auth.OnSignInResultListener;
import com.vitaliyhtc.socialnetworksapi.model.ContextWrap;
import com.vitaliyhtc.socialnetworksapi.model.FragmentWrap;
import com.vitaliyhtc.socialnetworksapi.model.IntentWrap;
import com.vitaliyhtc.socialnetworksapi.view.BaseView;
import com.vitaliyhtc.socialnetworksapi.view.LoginView;

public class LoginPresenterImpl implements LoginPresenter {

    private static final String TAG = "LoginPresenterImpl";

    private ContextWrap mContext;
    private FragmentWrap mFragmentWrap;

    private AuthManager mAuthManager;
    private LoginView mLoginView;

    private OnSignInResultListener mOnSignInResultListener;

    public LoginPresenterImpl(ContextWrap context, FragmentWrap fragmentWrap) {
        mContext = context;
        mFragmentWrap = fragmentWrap;
    }

    @Override
    public void onAttachView(BaseView baseView) {
        mLoginView = (LoginView) baseView;
        mAuthManager = new AuthManagerImpl(mContext.getContext(), mFragmentWrap.getFragment());
        initListeners();
    }

    @Override
    public void onDetachView() {
        mLoginView = null;
    }

    @Override
    public void onCreate() {
        mAuthManager.onCreate();
    }

    @Override
    public void onSignInButtonClick(final int providerId) {
        mAuthManager.signInWith(mOnSignInResultListener, providerId);
    }

    @Override
    public void trySilentSignIn() {
        mAuthManager.trySilentSignIn(mOnSignInResultListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, IntentWrap data) {
        mAuthManager.onActivityResult(requestCode, resultCode, data.getIntent());
    }

    private void initListeners() {
        mOnSignInResultListener = new OnSignInResultListener() {
            @Override
            public void onSignInSuccess(int providerId) {
                Log.e(TAG, "onSignInSuccess: ");
                if (mLoginView != null) {
                    mLoginView.onSignInSuccess(providerId);
                } else {
                    Log.e(TAG, "onSignInSuccess: mLoginView is null!!!");
                }
            }

            @Override
            public void onSignInError(String message) {
                if (mLoginView != null) {
                    mLoginView.onSignInError(message);
                }
            }
        };
    }
}
