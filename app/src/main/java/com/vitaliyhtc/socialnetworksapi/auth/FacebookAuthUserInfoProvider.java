package com.vitaliyhtc.socialnetworksapi.auth;

import android.content.Context;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.vitaliyhtc.socialnetworksapi.R;
import com.vitaliyhtc.socialnetworksapi.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class FacebookAuthUserInfoProvider extends FacebookAuthProviderBase
        implements AuthUserInfoProvider {

    private OnUserInfoListener mOnUserInfoListener;

    public FacebookAuthUserInfoProvider(Context context) {
        super(context);
    }

    @Override
    public void getUserInfo(OnUserInfoListener listener) {
        mOnUserInfoListener = listener;
        if (AccessToken.getCurrentAccessToken() != null) {
            executeGraphRequest(AccessToken.getCurrentAccessToken());
        }
    }

    @Override
    public void logOut(OnLogOutResultListener onLogOutResultListener) {
        LoginManager.getInstance().logOut();
        onLogOutResultListener.onLogOut();
    }

    @Override
    void onSuccessCall(LoginResult loginResult) {
        executeGraphRequest(loginResult.getAccessToken());
    }

    @Override
    void onCancelCall() {
        mOnUserInfoListener.onUserInfoError(mContext.getString(R.string.fb_error_login_action_is_canceled));
    }

    @Override
    void onErrorCall(FacebookException exception) {
        mOnUserInfoListener.onUserInfoError(exception.getMessage());
    }

    //See https://developers.facebook.com/docs/android/graph
    private void executeGraphRequest(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        if (object != null) {
                            User user = getUserFromJSONObject(object);
                            mOnUserInfoListener.onUserInfoSuccess(user);
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString(FB_KEY_FIELDS, FB_VALUE_FIELDS);
        request.setParameters(parameters);
        request.executeAsync();
    }

    private static User getUserFromJSONObject(JSONObject object) {
        User user = new User();
        try {
            user.setUserUid(object.getString("id"));
            user.setUserName(object.getString("name"));
            user.setUserEmail(object.getString("email"));
            user.setUserPhotoUrl(object.getJSONObject("picture").getJSONObject("data").getString("url"));
            user.putAdditionalData(User.USER_BIRTH_DATE, object.getString("birthday"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
