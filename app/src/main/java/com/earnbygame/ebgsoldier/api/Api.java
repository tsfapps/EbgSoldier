package com.earnbygame.ebgsoldier.api;

import com.earnbygame.ebgsoldier.model.ModelChecksum;
import com.earnbygame.ebgsoldier.model.ModelJoinMatch;
import com.earnbygame.ebgsoldier.model.login.ModelLogin;
import com.earnbygame.ebgsoldier.model.ModelMatchDetail;
import com.earnbygame.ebgsoldier.model.ModelMatchUserJoined;
import com.earnbygame.ebgsoldier.model.ModelRegister;
import com.earnbygame.ebgsoldier.model.ModelTransactionHistory;
import com.earnbygame.ebgsoldier.utils.Constant;

import java.util.List;

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
    @FormUrlEncoded
    @POST("api/api_join_match.php")
    Call<List<ModelJoinMatch>> joinMatch(
            @Field("user_id") String userId
    );

    @FormUrlEncoded
    @POST("api/paytm_app/generateChecksum.php")
    Call<ModelChecksum> generateCheckSum(
            @Field(Constant.MID) String mid,
            @Field(Constant.ORDER_ID) String orderId,
            @Field(Constant.CUST_ID) String custId,
            @Field(Constant.INDUSTRY_TYPE_ID) String industryId,
            @Field(Constant.CHANNEL_ID) String channelId,
            @Field(Constant.TXN_AMOUNT) String txnAmount,
            @Field(Constant.WEBSITE) String website,
            @Field(Constant.EMAIL) String email,
            @Field(Constant.MOBILE_NO) String mobileNo
    );
    @FormUrlEncoded
    @POST("api/api_transaction_history.php")
    Call<ModelTransactionHistory> paymentTransactionHistory(
            @Field("status") String status,
            @Field("checksumhash") String checksumhash,
            @Field("bankname") String bankname,
            @Field("orderid") String orderid,
            @Field("txnamount") String txnamount,
            @Field("txndate") String txndate,
            @Field("mid") String mid,
            @Field("txnid") String txnid,
            @Field("currency") String currency,
            @Field("respcode") String respcode,
            @Field("paymentmode") String paymentmode,
            @Field("banktxnid") String banktxnid,
            @Field("gatewayname") String gatewayname,
            @Field("respmsg") String respmsg,
            @Field("user_id") String userId
    );
    @FormUrlEncoded
    @POST("api/api_deduction.php" )
    Call<ModelTransactionHistory> amountDeductionApi(
            @Field("match_id") String matchId,
            @Field("txnamount") String txnAmount,
            @Field("user_id") String userId
    );

    @FormUrlEncoded
    @POST("api/api_match_details.php")
    Call<ModelMatchDetail> matchDetailApi(
            @Field("match_id") String matchId
    );

    @FormUrlEncoded
    @POST("api/api_joined_users.php")
    Call<ModelMatchUserJoined> matchUserJoined(
            @Field("match_id") String matchId
    );

}
