package com.vitaliyhtc.socialnetworksapi.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vitaliyhtc.socialnetworksapi.R;
import com.vitaliyhtc.socialnetworksapi.interfaces.AuthProcess;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends Fragment
        implements LoginView {

    private AuthProcess mAuthProcess;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setAuthProcess(AuthProcess authProcess) {
        mAuthProcess = authProcess;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.sign_in_button)
    protected void onGoogleSignInButtonClick() {
        mAuthProcess.onGoogleSignInButtonClick();

    }

    @OnClick(R.id.login_button)
    protected void onFacebookSignInButtonClick() {
        mAuthProcess.onFacebookSignInButtonClick();
    }

}
