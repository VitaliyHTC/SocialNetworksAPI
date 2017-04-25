package com.vitaliyhtc.socialnetworksapi.model;

import android.content.Context;

public class ContextWrap {
    private Context context;

    public ContextWrap(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
