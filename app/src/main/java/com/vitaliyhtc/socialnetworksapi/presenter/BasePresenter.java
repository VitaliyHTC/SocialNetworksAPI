package com.vitaliyhtc.socialnetworksapi.presenter;

import com.vitaliyhtc.socialnetworksapi.view.BaseView;

public interface BasePresenter {

    void onAttachView(BaseView baseView);

    void onDetachView();
}
