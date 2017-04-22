package com.vitaliyhtc.socialnetworksapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.vitaliyhtc.socialnetworksapi.auth.AuthManager;
import com.vitaliyhtc.socialnetworksapi.auth.AuthManagerImpl;
import com.vitaliyhtc.socialnetworksapi.auth.OnLogOutResultListener;
import com.vitaliyhtc.socialnetworksapi.auth.OnSignInResultListener;
import com.vitaliyhtc.socialnetworksapi.fragment.RetainedFragment;
import com.vitaliyhtc.socialnetworksapi.interfaces.AuthProcess;
import com.vitaliyhtc.socialnetworksapi.interfaces.ViewChangeProcess;
import com.vitaliyhtc.socialnetworksapi.model.User;
import com.vitaliyhtc.socialnetworksapi.view.BaseView;
import com.vitaliyhtc.socialnetworksapi.view.LoginFragment;
import com.vitaliyhtc.socialnetworksapi.view.UserProfileFragment;
import com.vitaliyhtc.socialnetworksapi.view.UserProfileView;

public class MainActivity extends AppCompatActivity
        implements AuthProcess, ViewChangeProcess {

    private static final String KEY_DISPLAYED_FRAGMENT_ID = "displayedFragmentId";
    private static final int VALUE_FRAGMENT_LOGIN = 0x01;
    private static final int VALUE_FRAGMENT_USER_PROFILE = 0x02;

    private static final String TAG_RETAINED_FRAGMENT = "RetainedFragment";


    // move to AuthManager.
    private RetainedFragment mRetainedFragment;

    private FragmentManager mFragmentManager;
    private int mLastDisplayedFragment;

    private AuthManager mAuthManager;

    private OnSignInResultListener mOnSignInResultListener;
    private OnLogOutResultListener mOnLogOutResultListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();

        mAuthManager = new AuthManagerImpl(MainActivity.this);
        mAuthManager.onCreate(savedInstanceState);

        initListeners();

        initRetainedFragment();

        restoreFromSavedInstanceState(savedInstanceState);

        initFragments();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (isFinishing()) {
            mFragmentManager.beginTransaction().remove(mRetainedFragment).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_DISPLAYED_FRAGMENT_ID, mLastDisplayedFragment);
        mAuthManager.onSaveInstanceState(outState);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mAuthManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showUserProfile(User user) {
        mLastDisplayedFragment = VALUE_FRAGMENT_USER_PROFILE;
        Fragment fragment = new UserProfileFragment();
        replaceFragment(fragment);
        ((UserProfileFragment) fragment).setUser(user);
    }

    private void restoreFromSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mLastDisplayedFragment = savedInstanceState.getInt(KEY_DISPLAYED_FRAGMENT_ID, VALUE_FRAGMENT_LOGIN);
        } else {
            mLastDisplayedFragment = VALUE_FRAGMENT_LOGIN;
        }
    }

    private void initListeners() {
        mOnSignInResultListener = new OnSignInResultListener() {
            @Override
            public void onSignInSuccess(User user) {
                onUserSignedIn(user);
            }

            @Override
            public void onSignInError(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        };

        mOnLogOutResultListener = new OnLogOutResultListener() {
            @Override
            public void onLogOut() {
                onUserLoggedOut();
            }
        };
    }

    private void initRetainedFragment() {
        // find the retained fragment on activity restarts
        mRetainedFragment = (RetainedFragment) mFragmentManager.findFragmentByTag(TAG_RETAINED_FRAGMENT);

        // create the fragment and data the first time
        if (mRetainedFragment == null) {
            // add the fragment
            mRetainedFragment = new RetainedFragment();
            mFragmentManager.beginTransaction().add(mRetainedFragment, TAG_RETAINED_FRAGMENT).commit();
            // load data from a data source or perform any calculation
            //mRetainedFragment.setData(loadMyData());
        }
    }

    private void initFragments() {
        Fragment fragment;
        if (mLastDisplayedFragment == VALUE_FRAGMENT_LOGIN) {
            fragment = new LoginFragment();
            ((BaseView) fragment).setAuthProcess(MainActivity.this);
            replaceFragment(fragment);
        } else if (mLastDisplayedFragment == VALUE_FRAGMENT_USER_PROFILE &&
                mRetainedFragment.getUser() != null) {
            fragment = new UserProfileFragment();
            ((UserProfileView) fragment).setAuthProcess(MainActivity.this);
            ((UserProfileView) fragment).setUser(mRetainedFragment.getUser());
            replaceFragment(fragment);
        } else {
            fragment = new LoginFragment();
            ((BaseView) fragment).setAuthProcess(MainActivity.this);
            replaceFragment(fragment);
        }
    }

    private void replaceFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().replace(R.id.container_view, fragment).commit();
    }


    private void onUserSignedIn(User user) {
        mRetainedFragment.setUser(user);

        Toast.makeText(MainActivity.this,
                "SignIn successful.\r\n" +
                        "UserUid: " + user.getUserUid()
                        + "; Username: " + user.getUserName()
                        + "; Email: " + user.getUserEmail() + ";"
                , Toast.LENGTH_SHORT).show();

        showUserProfile(user);
    }

    private void onUserLoggedOut() {
        mRetainedFragment.setUser(null);

        mLastDisplayedFragment = VALUE_FRAGMENT_LOGIN;
        Fragment fragment = new LoginFragment();
        ((LoginFragment) fragment).setAuthProcess(MainActivity.this);
        replaceFragment(fragment);
    }
}
