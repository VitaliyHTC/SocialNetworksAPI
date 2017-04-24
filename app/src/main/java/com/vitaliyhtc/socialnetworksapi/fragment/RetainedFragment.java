package com.vitaliyhtc.socialnetworksapi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.vitaliyhtc.socialnetworksapi.model.User;

public class RetainedFragment extends Fragment {

    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
