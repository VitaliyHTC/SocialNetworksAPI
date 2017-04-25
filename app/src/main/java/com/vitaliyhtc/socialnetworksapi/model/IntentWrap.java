package com.vitaliyhtc.socialnetworksapi.model;

import android.content.Intent;

public class IntentWrap {
    private Intent intent;

    public IntentWrap(Intent intent) {
        this.intent = intent;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
