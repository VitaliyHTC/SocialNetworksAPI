package com.vitaliyhtc.socialnetworksapi.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vitaliyhtc.socialnetworksapi.R;
import com.vitaliyhtc.socialnetworksapi.model.ContextWrap;
import com.vitaliyhtc.socialnetworksapi.model.FragmentWrap;
import com.vitaliyhtc.socialnetworksapi.model.IntentWrap;
import com.vitaliyhtc.socialnetworksapi.model.User;
import com.vitaliyhtc.socialnetworksapi.presenter.UserProfilePresenter;
import com.vitaliyhtc.socialnetworksapi.presenter.UserProfilePresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfileFragment extends Fragment
        implements UserProfileView {

    public static final String KEY_PROVIDER_ID = "providerId";
    public static final String KEY_USER = "User";

    @BindView(R.id.tv_user_name)
    TextView mUserNameTextView;
    @BindView(R.id.tv_user_email)
    TextView mEmailTextView;
    @BindView(R.id.tv_user_birth_date)
    TextView mBirthDateTextView;
    @BindView(R.id.userImageView)
    ImageView mUserImageView;

    private User mUser;

    private int mProviderId;

    private FragmentCallbacks mFragmentCallbacks;

    private UserProfilePresenter mUserProfilePresenter;


    public static UserProfileFragment newInstance(int providerId) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_PROVIDER_ID, providerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProviderId = getArguments().getInt(KEY_PROVIDER_ID);
        }
        if (savedInstanceState != null) {
            mUser = savedInstanceState.getParcelable(KEY_USER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUserProfilePresenter = new UserProfilePresenterImpl(new ContextWrap(getContext()), new FragmentWrap(UserProfileFragment.this));
        mUserProfilePresenter.onAttachView(UserProfileFragment.this);
        mUserProfilePresenter.onCreate();
        if (mUser == null) {
            mUserProfilePresenter.getUserProfile(mProviderId);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mUser != null) {
            outState.putParcelable(KEY_USER, mUser);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mUserProfilePresenter.onDetachView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mUserProfilePresenter.onActivityResult(requestCode, resultCode, new IntentWrap(data));
    }

    @Override
    public void setFragmentCallbacks(FragmentCallbacks fragmentCallbacks) {
        mFragmentCallbacks = fragmentCallbacks;
    }

    @Override
    public void onUserInfoSuccess(User user) {
        mUser = user;
        updateDataOnUi();
    }

    @Override
    public void onUserInfoError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLogOut() {
        mFragmentCallbacks.onUserLoggedOut();
    }

    private void updateDataOnUi() {
        mUserNameTextView.setText(mUser.getUserName());
        mEmailTextView.setText(mUser.getUserEmail());
        mBirthDateTextView.setText(mUser.getAdditionalDataByKey(User.KEY_USER_BIRTH_DATE));
        Picasso.with(getContext()).load(mUser.getUserPhotoUrl())
                .into(mUserImageView);
    }

    @OnClick(R.id.btn_logout)
    void onLogOutButtonClick() {
        mUserProfilePresenter.onLogOutButtonClick(mProviderId);
    }
}
