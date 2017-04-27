package com.vitaliyhtc.socialnetworksapi.model;

import android.support.v4.app.Fragment;

public class FragmentWrap {

    private Fragment mFragment;

    public FragmentWrap(Fragment fragment) {
        mFragment = fragment;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }
}
