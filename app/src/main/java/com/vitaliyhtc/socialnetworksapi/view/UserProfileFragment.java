package com.vitaliyhtc.socialnetworksapi.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vitaliyhtc.socialnetworksapi.R;
import com.vitaliyhtc.socialnetworksapi.interfaces.AuthProcess;
import com.vitaliyhtc.socialnetworksapi.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfileFragment extends Fragment
        implements UserProfileView {

    @BindView(R.id.tv_user_name)
    TextView mUserNameTextView;
    @BindView(R.id.tv_user_email)
    TextView mEmailTextView;
    @BindView(R.id.tv_user_birth_date)
    TextView mBirthDateTextView;
    @BindView(R.id.userImageView)
    ImageView mUserImageView;
    private AuthProcess mAuthProcess;
    private User mUser;
    private boolean isFragmentStarted;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        isFragmentStarted = true;

        if (mUser != null) {
            updateDataOnUi();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        isFragmentStarted = false;
    }

    @Override
    public void setAuthProcess(AuthProcess authProcess) {
        mAuthProcess = authProcess;
    }

    @Override
    public void setUser(User user) {
        mUser = user;
        if (isFragmentStarted) {
            updateDataOnUi();
        }
    }

    private void updateDataOnUi() {
        mUserNameTextView.setText(mUser.getUserName());
        mEmailTextView.setText(mUser.getUserEmail());
        mBirthDateTextView.setText(mUser.getAdditionalDataByKey(User.USER_BIRTH_DATE));
        Picasso.with(getContext()).load(mUser.getUserPhotoUrl())
                .into(mUserImageView);
    }

    @OnClick(R.id.btn_logout)
    void onLogOut() {
        mAuthProcess.onLogOut();
    }
}
