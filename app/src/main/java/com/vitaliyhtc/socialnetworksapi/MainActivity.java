package com.vitaliyhtc.socialnetworksapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.vitaliyhtc.socialnetworksapi.interfaces.AuthProcess;
import com.vitaliyhtc.socialnetworksapi.model.BundleWrap;
import com.vitaliyhtc.socialnetworksapi.model.ContextWrap;
import com.vitaliyhtc.socialnetworksapi.model.IntentWrap;
import com.vitaliyhtc.socialnetworksapi.model.User;
import com.vitaliyhtc.socialnetworksapi.presenter.MainPresenter;
import com.vitaliyhtc.socialnetworksapi.presenter.MainPresenterCallbacks;
import com.vitaliyhtc.socialnetworksapi.presenter.MainPresenterImpl;
import com.vitaliyhtc.socialnetworksapi.view.BaseView;
import com.vitaliyhtc.socialnetworksapi.view.LoginFragment;
import com.vitaliyhtc.socialnetworksapi.view.UserProfileFragment;

public class MainActivity extends AppCompatActivity
        implements AuthProcess, MainPresenterCallbacks {

    private static final String KEY_DISPLAYED_FRAGMENT_ID = "displayedFragmentId";
    private static final int VALUE_FRAGMENT_LOGIN = 0x01;
    private static final int VALUE_FRAGMENT_USER_PROFILE = 0x02;

    private static final String KEY_IS_USER_SIGNED_IN = "isUserSignedIn";


    private MainPresenter mMainPresenter;

    private FragmentManager mFragmentManager;
    private int mLastDisplayedFragment;

    private boolean isUserSignedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();

        mMainPresenter = new MainPresenterImpl(new ContextWrap(MainActivity.this), MainActivity.this);
        mMainPresenter.onCreate(new BundleWrap(savedInstanceState));

        restoreFromSavedInstanceState(savedInstanceState);

        initFragments();


        // TODO: 26/04/17 save user login status
        if (!isUserSignedIn) {
            mMainPresenter.trySilentSignIn();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mMainPresenter.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_DISPLAYED_FRAGMENT_ID, mLastDisplayedFragment);
        outState.putBoolean(KEY_IS_USER_SIGNED_IN, isUserSignedIn);
        mMainPresenter.onSaveInstanceState(new BundleWrap(outState));
    }

    @Override
    public void onGoogleSignInButtonClick() {
        mMainPresenter.onGoogleSignInButtonClick();
    }

    @Override
    public void onFacebookSignInButtonClick() {
        mMainPresenter.onFacebookSignInButtonClick();
    }

    @Override
    public void onLogOut() {
        mMainPresenter.onLogOut();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mMainPresenter.onActivityResult(requestCode, resultCode, new IntentWrap(data));
    }

    @Override
    public void onUserSignedIn(User user) {
        isUserSignedIn = true;

        mLastDisplayedFragment = VALUE_FRAGMENT_USER_PROFILE;
        showUserProfileFragment(user);
    }

    @Override
    public void onSignInError(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUserLoggedOut() {
        mLastDisplayedFragment = VALUE_FRAGMENT_LOGIN;
        Fragment fragment = new LoginFragment();
        ((LoginFragment) fragment).setAuthProcess(MainActivity.this);
        replaceFragment(fragment);
    }

    private void restoreFromSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mLastDisplayedFragment = savedInstanceState.getInt(KEY_DISPLAYED_FRAGMENT_ID, VALUE_FRAGMENT_LOGIN);
            isUserSignedIn = savedInstanceState.getBoolean(KEY_IS_USER_SIGNED_IN, false);
        } else {
            isUserSignedIn = false;
            mLastDisplayedFragment = VALUE_FRAGMENT_LOGIN;
        }
    }

    private void initFragments() {
        Fragment fragment;
        if (mLastDisplayedFragment == VALUE_FRAGMENT_LOGIN) {
            fragment = new LoginFragment();
            ((BaseView) fragment).setAuthProcess(MainActivity.this);
            replaceFragment(fragment);
        } else if (mLastDisplayedFragment == VALUE_FRAGMENT_USER_PROFILE &&
                mMainPresenter.getRetainedUser() != null) {
            showUserProfileFragment(mMainPresenter.getRetainedUser());
        } else {
            fragment = new LoginFragment();
            ((BaseView) fragment).setAuthProcess(MainActivity.this);
            replaceFragment(fragment);
        }
    }

    private void replaceFragment(Fragment fragment) {
        mFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.come_in_anim, R.anim.go_out_anim)
                .replace(R.id.container_view, fragment).commit();
    }

    private void showUserProfileFragment(User user) {
        // TODO: 26/04/17 unappropriated fragment initialization
        // TODO: 26/04/17 read about fragment-fragment & fragment-activity connection
        // TODO: 26/04/17 read confluence!!!
        Fragment fragment = new UserProfileFragment();
        ((UserProfileFragment) fragment).setAuthProcess(MainActivity.this);
        ((UserProfileFragment) fragment).setUser(user);
        replaceFragment(fragment);
    }

}
