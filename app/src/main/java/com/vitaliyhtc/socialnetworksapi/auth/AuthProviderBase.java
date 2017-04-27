package com.vitaliyhtc.socialnetworksapi.auth;

import android.content.Intent;

public interface AuthProviderBase {
    void onCreate();
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
