package com.vitaliyhtc.socialnetworksapi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.vitaliyhtc.socialnetworksapi.view.LoginFragment;
import com.vitaliyhtc.socialnetworksapi.view.FragmentCallbacks;
import com.vitaliyhtc.socialnetworksapi.view.UserProfileFragment;

public class MainActivity extends AppCompatActivity
        implements FragmentCallbacks {

    private static final String KEY_DISPLAYED_FRAGMENT_ID = "displayedFragmentId";
    private static final int VALUE_FRAGMENT_LOGIN = 0x01;
    private static final int VALUE_FRAGMENT_USER_PROFILE = 0x02;

    private static final String KEY_IS_USER_SIGNED_IN = "isUserSignedIn";
    private static final String KEY_CURRENT_AUTH_PROVIDER_ID = "currentAuthProviderId";

    private int mLastDisplayedFragment;
    private boolean isUserSignedIn;
    private int mCurrentAuthProviderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restoreFromSavedInstanceState(savedInstanceState);

        initFragments();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_DISPLAYED_FRAGMENT_ID, mLastDisplayedFragment);
        outState.putBoolean(KEY_IS_USER_SIGNED_IN, isUserSignedIn);
        outState.putInt(KEY_CURRENT_AUTH_PROVIDER_ID, mCurrentAuthProviderId);
    }

    @Override
    public void onUserSignedIn(int providerId) {
        if (Constants.isProviderPresentById(providerId)) {
            mCurrentAuthProviderId = providerId;
            isUserSignedIn = true;
            showUserProfileFragment(providerId);
        } else {
            mCurrentAuthProviderId = Constants.AUTH_BY_NOT_SELECTED;
            isUserSignedIn = false;
            showLoginFragment();
        }
    }

    @Override
    public void onUserLoggedOut() {
        isUserSignedIn = false;
        mCurrentAuthProviderId = Constants.AUTH_BY_NOT_SELECTED;
        showLoginFragment();
    }

    private void restoreFromSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mLastDisplayedFragment = savedInstanceState.getInt(KEY_DISPLAYED_FRAGMENT_ID, VALUE_FRAGMENT_LOGIN);
            isUserSignedIn = savedInstanceState.getBoolean(KEY_IS_USER_SIGNED_IN, false);
            mCurrentAuthProviderId = savedInstanceState.getInt(KEY_CURRENT_AUTH_PROVIDER_ID, Constants.AUTH_BY_NOT_SELECTED);
        } else {
            mLastDisplayedFragment = VALUE_FRAGMENT_LOGIN;
            isUserSignedIn = false;
            mCurrentAuthProviderId = Constants.AUTH_BY_NOT_SELECTED;
        }
    }

    private void initFragments() {
        if (mLastDisplayedFragment == VALUE_FRAGMENT_LOGIN) {
            showLoginFragment();
        } else if (mLastDisplayedFragment == VALUE_FRAGMENT_USER_PROFILE) {
            showUserProfileFragment(mCurrentAuthProviderId);
        } else {
            showLoginFragment();
        }
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.come_in_anim, R.anim.go_out_anim)
                .replace(R.id.container_view, fragment).commit();
    }

    private void showUserProfileFragment(int providerId) {
        if (Constants.isProviderPresentById(providerId)) {
            mLastDisplayedFragment = VALUE_FRAGMENT_USER_PROFILE;
            UserProfileFragment fragment = UserProfileFragment.newInstance(providerId);
            fragment.setFragmentCallbacks(MainActivity.this);
            replaceFragment(fragment);
        } else {
            showLoginFragment();
        }
    }

    private void showLoginFragment() {
        mLastDisplayedFragment = VALUE_FRAGMENT_LOGIN;
        LoginFragment fragment = new LoginFragment();
        fragment.setFragmentCallbacks(MainActivity.this);
        replaceFragment(fragment);
    }

}
