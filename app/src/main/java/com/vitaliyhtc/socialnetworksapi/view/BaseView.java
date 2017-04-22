package com.vitaliyhtc.socialnetworksapi.view;

import android.content.Context;

import com.vitaliyhtc.socialnetworksapi.interfaces.AuthProcess;

public interface BaseView {

    Context getContext();
    void setAuthProcess(AuthProcess authProcess);
}
