package com.earnbygame.ebgsoldier.api;

import com.earnbygame.ebgsoldier.model.ModelLogin;
import com.earnbygame.ebgsoldier.model.ModelRegister;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("api/api_registration.php")
    Call<ModelRegister> userRegistration(
            @Field("user_name") String userName,
            @Field("pubg_user_name") String pgUserName,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone_no") String phoneNo,
            @Field("referral_code") String referCode,
            @Field("profile_pic") String profilePic
    );

    @FormUrlEncoded
    @POST("api/api_login.php")
    Call<ModelLogin> userLogin(
            @Field("pubg_user_name") String pgUserName,
            @Field("password") String password
    );

}
