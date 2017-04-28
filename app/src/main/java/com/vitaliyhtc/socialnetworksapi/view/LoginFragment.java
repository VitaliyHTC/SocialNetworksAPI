package com.vitaliyhtc.socialnetworksapi.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vitaliyhtc.socialnetworksapi.Constants;
import com.vitaliyhtc.socialnetworksapi.R;
import com.vitaliyhtc.socialnetworksapi.model.ContextWrap;
import com.vitaliyhtc.socialnetworksapi.model.FragmentWrap;
import com.vitaliyhtc.socialnetworksapi.model.IntentWrap;
import com.vitaliyhtc.socialnetworksapi.presenter.LoginPresenter;
import com.vitaliyhtc.socialnetworksapi.presenter.LoginPresenterImpl;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends Fragment
        implements LoginView {
    public static final String KEY_APP_JUST_STARTED = "appHasJustStarted";
    private static final String TAG = "LoginFragment";
    private FragmentCallbacks mFragmentCallbacks;
    private LoginPresenter mLoginPresenter;

    private boolean mAppHasJustStarted;

    public static LoginFragment newInstance(boolean appHasJustStarted) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putBoolean(KEY_APP_JUST_STARTED, appHasJustStarted);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAppHasJustStarted = getArguments().getBoolean(KEY_APP_JUST_STARTED);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLoginPresenter = new LoginPresenterImpl(new ContextWrap(getContext()), new FragmentWrap(LoginFragment.this));
        mLoginPresenter.onAttachView(LoginFragment.this);
        mLoginPresenter.onCreate();
        if (mAppHasJustStarted) {
            mAppHasJustStarted = false;
            mLoginPresenter.trySilentSignIn();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLoginPresenter.onDetachView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLoginPresenter.onActivityResult(requestCode, resultCode, new IntentWrap(data));
    }

    @Override
    public void setFragmentCallbacks(FragmentCallbacks fragmentCallbacks) {
        mFragmentCallbacks = fragmentCallbacks;
    }

    @Override
    public void onSignInSuccess(int providerId) {
        Log.e(TAG, "onSignInSuccess: " + providerId);
        mFragmentCallbacks.onUserSignedIn(providerId);
    }

    @Override
    public void onSignInError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.sign_in_button)
    protected void onGoogleSignInButtonClick() {
        mLoginPresenter.onSignInButtonClick(Constants.AUTH_BY_GOOGLE);
    }

    @OnClick(R.id.login_button)
    protected void onFacebookSignInButtonClick() {
        mLoginPresenter.onSignInButtonClick(Constants.AUTH_BY_FACEBOOK);
    }
}
