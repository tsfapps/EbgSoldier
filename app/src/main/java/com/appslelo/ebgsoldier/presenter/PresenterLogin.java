package com.appslelo.ebgsoldier.presenter;

import android.util.Log;

import com.appslelo.ebgsoldier.activity.LoginActivity;
import com.appslelo.ebgsoldier.api.Api;
import com.appslelo.ebgsoldier.api.ApiClients;
import com.appslelo.ebgsoldier.model.login.ModelLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterLogin {

    private final String TAG = "PresenterLogin";
    private final LoginActivity mActivityContext;
    private final String mUserName;
    private final String mPassword;

    public PresenterLogin(LoginActivity loginActivity, String mUserName, String mPassword) {
        this.mActivityContext = loginActivity;
        this.mUserName = mUserName;
        this.mPassword = mPassword;

        inti();
    }

    private void inti() {
        callLoginApi();
    }

    private void callLoginApi() {
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelLogin> call = api.userLogin(mUserName,mPassword);
        call.enqueue(new Callback<ModelLogin>() {
            @Override
            public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {
                Log.d(TAG,"onResponse  response:"+response.toString());
                mActivityContext.onLoginApiResponse(response);
            }

            @Override
            public void onFailure(Call<ModelLogin> call, Throwable t) {
                Log.d(TAG,"onFailure  call:"+call.toString());
                mActivityContext.onLoginApiFailure(call);
            }
        });
    }
}
