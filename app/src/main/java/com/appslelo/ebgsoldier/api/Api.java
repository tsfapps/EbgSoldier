package com.appslelo.ebgsoldier.api;

import com.appslelo.ebgsoldier.model.ModelApkUrl;
import com.appslelo.ebgsoldier.model.ModelChecksum;
import com.appslelo.ebgsoldier.model.ModelFaq;
import com.appslelo.ebgsoldier.model.ModelJoinMatch;
import com.appslelo.ebgsoldier.model.ModelKillEarn;
import com.appslelo.ebgsoldier.model.ModelMatchResult;
import com.appslelo.ebgsoldier.model.ModelMatchResultDetail;
import com.appslelo.ebgsoldier.model.ModelJoinMegaMatch;
import com.appslelo.ebgsoldier.model.ModelNewVer;
import com.appslelo.ebgsoldier.model.ModelNotification;
import com.appslelo.ebgsoldier.model.ModelOffer;
import com.appslelo.ebgsoldier.model.ModelOfferRedeem;
import com.appslelo.ebgsoldier.model.ModelTopPlayers;
import com.appslelo.ebgsoldier.model.ModelWalletBonus;
import com.appslelo.ebgsoldier.model.ModelWithdraw;
import com.appslelo.ebgsoldier.model.login.ModelLogin;
import com.appslelo.ebgsoldier.model.ModelMatchDetail;
import com.appslelo.ebgsoldier.model.ModelMatchUserJoined;
import com.appslelo.ebgsoldier.model.ModelRegister;
import com.appslelo.ebgsoldier.model.ModelTransactionHistory;
import com.appslelo.ebgsoldier.utils.Constant;

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
    @POST("api/api_join_mega_match.php")
    Call<List<ModelJoinMegaMatch>> joinMegaMatch(
            @Field("user_id") String userId
    );

    @POST("api/api_result_match.php")
    Call<List<ModelMatchResult>> matchResult(

    );
    @POST("api/apk.php")
    Call<ModelApkUrl> apkUrl(
    );

    @FormUrlEncoded
    @POST("api/api_result_match_details.php")
    Call<List<ModelMatchResultDetail>> matchResultDetails(
            @Field("match_id") String match_id
    );

    @FormUrlEncoded
    @POST("api/api_offer.php")
    Call<List<ModelOffer>> offerDetail(
            @Field("user_id") String user_id );

    @FormUrlEncoded
    @POST("api/wallet_bonus.php")
    Call<ModelWalletBonus> walletBonus(
            @Field("user_id") String user_id );

    @FormUrlEncoded
    @POST("api/killed_earned.php")
    Call<ModelKillEarn> killEarn(
            @Field("user_id") String user_id );

    @FormUrlEncoded
    @POST("api/redeem.php")
    Call<ModelOfferRedeem> offerRedeem(
            @Field("user_id") String user_id,
            @Field("r_amount") String r_amount,
            @Field("id") String id
    );

    @POST("api/api_faq.php")
    Call<List<ModelFaq>> faqDetail();

    @POST("api/api_update_ver.php")
    Call<List<ModelNewVer>> updateVersion();

    @POST("api/top_players.php")
    Call<List<ModelTopPlayers>> topPlayers();

    @POST("api/api_notification.php")
    Call<List<ModelNotification>> notificationDetail();

    @FormUrlEncoded
    @POST("api/api_bank_details.php")
    Call<ModelWithdraw> widthdraws(
            @Field("user_id") String user_id,
            @Field("requesting_amnt") String requesting_amnt,
            @Field("account_no") String account_no,
            @Field("ifsc_code") String ifsc_code,
            @Field("upi_id") String upi_id,
            @Field("paytm_no") String paytm_no,
            @Field("google_pay_no") String google_pay_no
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
    Call<ModelTransactionHistory>  amountDeductionApi(
            @Field("match_id") String matchId,
            @Field("txnamount") String txnAmount,
            @Field("txn_bonus_amnt") String txn_bonus_amnt,
            @Field("user_id") String userId
    );
//http://earnbygame.com/api/api_about_match.php
    @FormUrlEncoded
    @POST("api/api_match_details.php")
    Call<ModelMatchDetail> matchDetailApi(
            @Field("match_id") String matchId
    );

    @FormUrlEncoded
    @POST("api/api_about_match.php")
    Call<ModelMatchDetail> matchAboutApi(
            @Field("match_id") String matchId
    );
//http://earnbygame.com/api/api_joined_users.php
    @FormUrlEncoded
    @POST("api/api_joined_users.php")
    Call<List<ModelMatchUserJoined>> matchUserJoined(
            @Field("match_id") String matchId
    );

}
