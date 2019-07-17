package com.appslelo.ebgsoldier.presenter;

import android.util.Log;

import com.appslelo.ebgsoldier.activity.RegisterActivity;
import com.appslelo.ebgsoldier.api.Api;
import com.appslelo.ebgsoldier.api.ApiClients;
import com.appslelo.ebgsoldier.model.ModelRegister;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterRegister {
    private final String TAG = "PresenterRegister";
    private RegisterActivity mActivityContext;
    private String mName;
    private String mPgUserName;
    private String mPhoneNo;
    private String mEmailId;
    private String mPasswordCheck;
    private String mReferCode;
    private String mProfilePic;

    public PresenterRegister(RegisterActivity registerActivity, String mName, String mPgUserName, String mPhoneNo, String mEmailId, String mPasswordCheck, String mReferCode, String mProfilePic) {
        this.mActivityContext = registerActivity;
        this.mName = mName;
        this.mPgUserName = mPgUserName;
        this.mPhoneNo = mPhoneNo;
        this.mEmailId = mEmailId;
        this.mPasswordCheck = mPasswordCheck;
        this.mReferCode = mReferCode;
        this.mProfilePic = mProfilePic;

        init();
    }

    private void init() {
        callRegisterApi();
    }

    private void callRegisterApi() {
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelRegister> call = api.userRegistration(mName,mPgUserName,mEmailId,mPasswordCheck,mPhoneNo,mReferCode,mProfilePic);
        call.enqueue(new Callback<ModelRegister>() {
            @Override
            public void onResponse(Call<ModelRegister> call, Response<ModelRegister> response) {
                Log.d(TAG,"onResponse  response:"+response.toString());
                mActivityContext.onRegisterApiResponse(response);
            }

            @Override
            public void onFailure(Call<ModelRegister> call, Throwable t) {
                Log.d(TAG,"onFailure  call:"+call.toString());
                mActivityContext.onRegisterApiFailure(call);
            }
        });
    }
}
