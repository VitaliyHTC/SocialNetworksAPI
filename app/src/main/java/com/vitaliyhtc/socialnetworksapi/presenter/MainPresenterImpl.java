package com.vitaliyhtc.socialnetworksapi.presenter;

import com.vitaliyhtc.socialnetworksapi.auth.AuthManager;
import com.vitaliyhtc.socialnetworksapi.auth.AuthManagerImpl;
import com.vitaliyhtc.socialnetworksapi.auth.OnLogOutResultListener;
import com.vitaliyhtc.socialnetworksapi.auth.OnSignInResultListener;
import com.vitaliyhtc.socialnetworksapi.model.BundleWrap;
import com.vitaliyhtc.socialnetworksapi.model.ContextWrap;
import com.vitaliyhtc.socialnetworksapi.model.IntentWrap;
import com.vitaliyhtc.socialnetworksapi.model.User;

// TODO: 26/04/17 did you read about MVP on confluence?
public class MainPresenterImpl implements MainPresenter {

    private AuthManager mAuthManager;
    private MainPresenterCallbacks mMainPresenterCallbacks;

    private OnSignInResultListener mOnSignInResultListener;
    private OnLogOutResultListener mOnLogOutResultListener;

    public MainPresenterImpl(ContextWrap context, MainPresenterCallbacks mainPresenterCallbacks) {
        mAuthManager = new AuthManagerImpl(context.getContext());
        mMainPresenterCallbacks = mainPresenterCallbacks;
    }


    @Override
    public void onCreate(BundleWrap savedInstanceState) {
        initListeners();
        mAuthManager.onCreate(savedInstanceState.getBundle());
    }

    @Override
    public void onPause() {
        mAuthManager.onPause();
    }

    @Override
    public void onSaveInstanceState(BundleWrap outState) {
        mAuthManager.onSaveInstanceState(outState.getBundle());
    }

    @Override
    public void trySilentSignIn() {
        mAuthManager.trySilentSignIn(mOnSignInResultListener);
    }

    @Override
    public void onGoogleSignInButtonClick() {
        mAuthManager.signInWith(AuthManagerImpl.AUTH_BY_GOOGLE, mOnSignInResultListener);
    }

    @Override
    public void onFacebookSignInButtonClick() {
        mAuthManager.signInWith(AuthManagerImpl.AUTH_BY_FACEBOOK, mOnSignInResultListener);
    }

    @Override
    public void onLogOut() {
        mAuthManager.logOut(mOnLogOutResultListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, IntentWrap data) {
        mAuthManager.onActivityResult(requestCode, resultCode, data.getIntent());
    }

    @Override
    public User getRetainedUser() {
        return mAuthManager.getRetainedUser();
    }

    private void initListeners() {
        mOnSignInResultListener = new OnSignInResultListener() {
            @Override
            public void onSignInSuccess(User user) {
                mMainPresenterCallbacks.onUserSignedIn(user);
            }

            @Override
            public void onSignInError(String message) {
                mMainPresenterCallbacks.onSignInError(message);
            }
        };

        mOnLogOutResultListener = new OnLogOutResultListener() {
            @Override
            public void onLogOut() {
                mMainPresenterCallbacks.onUserLoggedOut();
            }
        };
    }
}
