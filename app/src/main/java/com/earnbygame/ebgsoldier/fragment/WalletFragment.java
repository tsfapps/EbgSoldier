package com.earnbygame.ebgsoldier.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.earnbygame.ebgsoldier.R;
import com.earnbygame.ebgsoldier.activity.DashActivity;
import com.earnbygame.ebgsoldier.api.Api;
import com.earnbygame.ebgsoldier.api.ApiClients;
import com.earnbygame.ebgsoldier.model.ModelChecksum;
import com.earnbygame.ebgsoldier.model.ModelTransactionHistory;
import com.earnbygame.ebgsoldier.model.ModelWithdraw;
import com.earnbygame.ebgsoldier.model.login.User;
import com.earnbygame.ebgsoldier.utils.Constant;
import com.earnbygame.ebgsoldier.utils.CustomLog;
import com.earnbygame.ebgsoldier.utils.CustomToast;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletFragment extends Fragment {
    private Context tContext;
    private PaytmPGService mPaymentService;
    private PaytmOrder mOrder;
    private String MID = "rsIFPL71143711550832";//rsIFPL71143711550832//LVNRji48342448716443
    private String mCheckSum = null;
    private String mCallBackUrl = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=";
    //private String mCallBackUrl = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=";
    private String mOrderId = null;
    private String mCustId = null;
    private String mAmount = null;
    private String mIndustryId = null;
    private String mChannelId = null;
    private String mWebsite = null;
    private String mEmail = null;
    private String mMobile = null;

    private FragmentManager fragmentManager;
    private DashActivity tActivity;
    private String strWalletAmount;
    private String strEarnedAmount;
    private String strBonusAmount;
    private List<User> mUserList;





    @BindView(R.id.tv_wallet_amount)
    protected TextView tv_wallet_amount;
    @BindView(R.id.tv_earned_amount)
    protected TextView tv_earned_amount;
    @BindView(R.id.tv_bonus_amount)
    protected TextView tv_bonus_amount;
    @BindView(R.id.et_adding_amount)
    protected EditText et_adding_amount;
    @BindView(R.id.tv_wallet100)
    protected TextView tv_wallet100;
    @BindView(R.id.tv_wallet200)
    protected TextView tv_wallet200;
    @BindView(R.id.tv_wallet500)
    protected TextView tv_wallet500;
    @BindView(R.id.btn_addAmount)
    protected Button btn_addAmount;
    @BindView(R.id.btn_withdraw)
    protected Button btn_withdraw;

    public static WalletFragment newInstance(String strBonusAmount) {

        WalletFragment fragment = new WalletFragment();
        fragment.strBonusAmount = strBonusAmount;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_wallet, container, false);
        ButterKnife.bind(this, view);
        initFrag();

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void initFrag(){
        tContext = getContext();
        tActivity = (DashActivity) getActivity();
        assert tActivity != null;
        fragmentManager = tActivity.getSupportFragmentManager();
        mUserList = User.listAll(User.class);
        if (mUserList.size() > 0){
            strWalletAmount = mUserList.get(0).getWalletAmount();
            strEarnedAmount = mUserList.get(0).getTotalEarnedMatch();
//            strBonusAmount = mUserList.get(0).getTotalEarnedRefferals();
            mCustId =mUserList.get(0).getUserId();
            mEmail = mUserList.get(0).getEmail();
            mMobile = mUserList.get(0).getPhoneNo();
        }
        tv_wallet_amount.setText(strWalletAmount);
        tv_earned_amount.setText(strEarnedAmount);
        tv_bonus_amount.setText(strBonusAmount);
        checkReadSmsPermission();

        String mTemp= mCustId.substring(0, Math.min(mCustId.length(), 6));
        mOrderId = mTemp + getOrderId();
        mIndustryId = "Retail";
        mChannelId = "WAP";
        mWebsite = "DEFAULT";
        mCallBackUrl = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID="+mOrderId;
        updateWalletAmount();
    }

    @OnClick(R.id.tv_wallet100)
    public void tv_wallet100Clicked(View view){
         et_adding_amount.setText("100");
    }
    @OnClick(R.id.tv_wallet200)
    public void tv_wallet200Clicked(View view){
        et_adding_amount.setText("200");
    }
    @OnClick(R.id.tv_wallet500)
    public void tv_wallet500Clicked(View view){
        et_adding_amount.setText("500");
    }
    @OnClick(R.id.tv_walletCustom)
    public void tv_walletCustomClicked(View view){
        et_adding_amount.setEnabled(true);
        et_adding_amount.setText("");
        et_adding_amount.setBackgroundResource(R.drawable.bg_simple_transparent);

    }
    @OnClick(R.id.btn_addAmount)
    public void clickBtnAddAmount(View view){
        mAmount = et_adding_amount.getText().toString().trim();
        CustomLog.d(Constant.TAG,"onStartPayment amount to pay : "+mAmount);
        generateChecksumApi(mAmount);
    }
    private void updateWalletAmount() {

        int wallet = Integer.parseInt(mUserList.get(0).getWalletAmount());
        tv_wallet_amount.setText(String.valueOf(wallet));
        tv_bonus_amount.setText(strBonusAmount);
//        mAmount = et_adding_amount.getText().toString();

    }

    private void callTansactionHistroyApi(Bundle inResponse) {
        String status = inResponse.getString("STATUS");
        String checksum = inResponse.getString("CHECKSUMHASH");
        String bankName = inResponse.getString("BANKNAME");
        String orderId = inResponse.getString("ORDERID");
        String txnAmount = inResponse.getString("TXNAMOUNT");
        String txnDate = inResponse.getString("TXNDATE");
        String mid = inResponse.getString("MID");
        String txnId = inResponse.getString("TXNID");
        String respcode = inResponse.getString("RESPCODE");
        String paymentMode = inResponse.getString("PAYMENTMODE");
        String banktxnid = inResponse.getString("BANKTXNID");
        String currency = inResponse.getString("CURRENCY");
        String gatewayname = inResponse.getString("GATEWAYNAME");
        String respmsg = inResponse.getString("RESPMSG");
        CustomLog.d(Constant.TAG,"callTransactionHistoryApi send data :"+ inResponse.toString());

        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelTransactionHistory> call = api.paymentTransactionHistory(status, checksum, bankName, orderId, txnAmount, txnDate, mid,
                txnId, respcode, paymentMode, banktxnid, currency, gatewayname, respmsg, mCustId);
        call.enqueue(new Callback<ModelTransactionHistory>() {
            @Override
            public void onResponse(Call<ModelTransactionHistory> call, Response<ModelTransactionHistory> response) {
                assert response.body() != null;
                CustomLog.d(Constant.TAG,"callTransactionHistoryApi onResonse :"+response.body().toString());
                if (!response.body().getError()){
                    CustomToast.tToastBottom(tContext, "Amount added in wallet successfully");

                    CustomLog.d(Constant.TAG,"callTransactionHistoryApi onResonse success ,, previous wallet amount :"+mUserList.get(0).getWalletAmount());
                    User user= mUserList.get(0);
                    user.setWalletAmount(String.valueOf(response.body().getWallet()));
                    user.setTotalEarnedRefferals(String.valueOf(response.body().getBonus()));
                    user.save();
//                    tSharedPrefManager.clearWallet();
//                    String strUpdatedWallet = String.valueOf(response.body().getWallet());
//                    tSharedPrefManager.setUserWallet(strUpdatedWallet);

                    CustomLog.d(Constant.TAG,"callTransactionHistoryApi onResonse success ,, current wallet amount :"+response.body().getWallet());
                    updateWalletAmount();
                }
            }
            @Override
            public void onFailure(Call<ModelTransactionHistory> call, Throwable t) {
            }
        });
    }
    private void initPaymentMethod() {
        mPaymentService = PaytmPGService.getProductionService();// TODO for production environment: pro key : rsIFPL71143711550832
        HashMap<String, String> paramMap = new HashMap<String,String>();
        paramMap.put(Constant.MID, MID);
        paramMap.put(Constant.ORDER_ID , mOrderId);
        paramMap.put( Constant.CUST_ID , mCustId);
        paramMap.put( Constant.MOBILE_NO , mMobile);
        paramMap.put( Constant.EMAIL ,mEmail);
        paramMap.put(Constant.CHANNEL_ID , mChannelId);
        paramMap.put(Constant.TXN_AMOUNT , mAmount);
        paramMap.put( Constant.WEBSITE , mWebsite);
        paramMap.put(Constant.INDUSTRY_TYPE_ID ,mIndustryId);
        paramMap.put( Constant.CALLBACK_URL, mCallBackUrl);
        paramMap.put( Constant.CHECKSUMHASH , mCheckSum);
        mOrder = new PaytmOrder(paramMap);
        startPaymentService();
    }

    private void startPaymentService() {
        mPaymentService.initialize(mOrder, null);//TODO pass certificate if have else pass null

        mPaymentService.startPaymentTransaction(tContext, true, true, new PaytmPaymentTransactionCallback() {
            /*Call Backs*/
            public void someUIErrorOccurred(String inErrorMessage) {
                /*Display the error message as below */
                CustomToast.tToastBottom(tContext, "UI Error " + inErrorMessage);
            }
            public void onTransactionResponse(Bundle inResponse) {
                /*Display the message as below */
                CustomLog.d(Constant.TAG,"onTransactionResponse inResponse :"+inResponse.toString());
                if (inResponse.get(Constant.STATUS).equals(Constant.TXN_SUCCESS) && inResponse.get(Constant.RESPMSG).equals(Constant.Txn_Success)) {
                    callTansactionHistroyApi(inResponse);
                } else {
                    CustomToast.tToastBottom(tContext, "Payment Transaction Error occur");
                }
            }
            public void networkNotAvailable() {
                /*Display the message as below */
                CustomToast.tToastBottom(tContext, "Network connection error: Check your internet connectivity");
            }
            public void clientAuthenticationFailed(String inErrorMessage) {
                /*Display the message as below */
                CustomToast.tToastBottom(tContext, "Authentication failed: Server error");
            }
            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                /*Display the message as below */
                CustomToast.tToastBottom(tContext, "Unable to load web page");
            }
            public void onBackPressedCancelTransaction() {
                /*Display the message as below */
                CustomToast.tToastBottom(tContext, "Transaction cancelled back press");

            }
            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                /*Display the message as below */
                CustomToast.tToastBottom(tContext, "Payment Transaction cancel");

            }
        });
    }
    private void checkReadSmsPermission() {
        if (ContextCompat.checkSelfPermission(tContext, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }
    }

    private void generateChecksumApi(String strAmount) {
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelChecksum> call = api.generateCheckSum(MID, mOrderId,mCustId,mIndustryId,mChannelId,
                strAmount,mWebsite,mEmail,mMobile);
        call.enqueue(new Callback<ModelChecksum>() {
            @Override
            public void onResponse(Call<ModelChecksum> call, Response<ModelChecksum> response) {
                assert response.body() != null;
                mCheckSum = response.body().getCHECKSUMHASH();
                CustomLog.d(Constant.TAG,"generate checksum onResponse :"+ mCheckSum);
                initPaymentMethod();
            }
            @Override
            public void onFailure(Call<ModelChecksum> call, Throwable t) {
                CustomLog.d(Constant.TAG,"generate checksum onFailure :"+ call.toString());
            }
        });
    }
    private String getOrderId()
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }

    @OnClick(R.id.btn_withdraw)
    public void btn_withdrawClicked(View view){
        fragmentManager.beginTransaction().replace(R.id.frame_container, new FragmentWithdraw()).addToBackStack(null).commit();
    }
}
