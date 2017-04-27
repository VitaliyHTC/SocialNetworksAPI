package com.vitaliyhtc.socialnetworksapi.presenter;

import com.vitaliyhtc.socialnetworksapi.auth.AuthUserInfoManager;
import com.vitaliyhtc.socialnetworksapi.auth.AuthUserInfoManagerImpl;
import com.vitaliyhtc.socialnetworksapi.auth.OnLogOutResultListener;
import com.vitaliyhtc.socialnetworksapi.auth.OnUserInfoListener;
import com.vitaliyhtc.socialnetworksapi.model.ContextWrap;
import com.vitaliyhtc.socialnetworksapi.model.FragmentWrap;
import com.vitaliyhtc.socialnetworksapi.model.IntentWrap;
import com.vitaliyhtc.socialnetworksapi.model.User;
import com.vitaliyhtc.socialnetworksapi.view.BaseView;
import com.vitaliyhtc.socialnetworksapi.view.UserProfileView;

public class UserProfilePresenterImpl implements UserProfilePresenter {

    private ContextWrap mContext;
    FragmentWrap mFragmentWrap;

    private AuthUserInfoManager mAuthUserInfoManager;
    private UserProfileView mUserProfileView;

    private OnUserInfoListener mOnUserInfoListener;
    private OnLogOutResultListener mOnLogOutResultListener;

    public UserProfilePresenterImpl(ContextWrap context, FragmentWrap fragmentWrap) {
        mContext = context;
        mFragmentWrap = fragmentWrap;
    }

    @Override
    public void onAttachView(BaseView baseView) {
        mUserProfileView = (UserProfileView) baseView;
        mAuthUserInfoManager = new AuthUserInfoManagerImpl(mContext.getContext(), mFragmentWrap.getFragment());
        initListeners();
    }

    @Override
    public void onDetachView() {
        mUserProfileView = null;
    }

    @Override
    public void onCreate() {
        mAuthUserInfoManager.onCreate();
    }

    @Override
    public void getUserProfile(int providerId) {
        mAuthUserInfoManager.getUserInfoByProviderId(mOnUserInfoListener, providerId);
    }

    @Override
    public void onLogOutButtonClick(int providerId) {
        mAuthUserInfoManager.logOutByProviderId(mOnLogOutResultListener, providerId);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, IntentWrap data) {
        mAuthUserInfoManager.onActivityResult(requestCode, resultCode, data.getIntent());
    }

    private void initListeners() {
        mOnUserInfoListener = new OnUserInfoListener() {
            @Override
            public void onUserInfoSuccess(User user) {
                mUserProfileView.onUserInfoSuccess(user);
            }

            @Override
            public void onUserInfoError(String message) {
                mUserProfileView.onUserInfoError(message);
            }
        };
        mOnLogOutResultListener = new OnLogOutResultListener() {
            @Override
            public void onLogOut() {
                mUserProfileView.onLogOut();
            }
        };
    }
}
