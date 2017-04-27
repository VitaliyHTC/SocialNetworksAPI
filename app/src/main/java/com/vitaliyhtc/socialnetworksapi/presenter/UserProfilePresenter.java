package com.vitaliyhtc.socialnetworksapi.presenter;

import com.vitaliyhtc.socialnetworksapi.model.IntentWrap;

public interface UserProfilePresenter extends BasePresenter {
    void getUserProfile(int providerId);
    void onLogOutButtonClick(int providerId);
    void onActivityResult(int requestCode, int resultCode, IntentWrap data);
}
