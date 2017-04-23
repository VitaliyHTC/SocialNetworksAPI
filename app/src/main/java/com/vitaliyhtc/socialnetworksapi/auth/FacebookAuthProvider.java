package com.vitaliyhtc.socialnetworksapi.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.vitaliyhtc.socialnetworksapi.R;
import com.vitaliyhtc.socialnetworksapi.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

public class FacebookAuthProvider implements AuthProvider {

    private static final String FB_PERMISSIONS = "publish_actions";
    private static final String FB_KEY_FIELDS = "fields";
    private static final String FB_VALUE_FIELDS = "id,name,email,birthday,picture";

    private Context mContext;

    private CallbackManager mCallbackManager;

    private OnSignInResultListener mOnSignInResultListener;

    public FacebookAuthProvider(Context context) {
        mContext = context;
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

    @Override
    public void onCreate() {
        initFacebook();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void signIn(OnSignInResultListener onSignInResultListener) {
        mOnSignInResultListener = onSignInResultListener;
        LoginManager.getInstance().logInWithPublishPermissions((Activity) mContext,
                Collections.singletonList(FB_PERMISSIONS));
    }

    @Override
    public void logOut(OnLogOutResultListener onLogOutResultListener) {
        LoginManager.getInstance().logOut();
        onLogOutResultListener.onLogOut();
    }

    private void initFacebook() {
        mCallbackManager = CallbackManager.Factory.create();

        //See https://developers.facebook.com/docs/android/graph
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        mOnSignInResultListener.onSignInSuccess(
                                                getUserFromJSONObject(object)
                                        );
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString(FB_KEY_FIELDS, FB_VALUE_FIELDS);
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        mOnSignInResultListener.onSignInError(mContext.getString(R.string.fb_error_login_action_is_canceled));
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        mOnSignInResultListener.onSignInError(exception.getMessage());
                    }
                });
    }
}
