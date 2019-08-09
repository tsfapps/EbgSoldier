package com.appslelo.ebgsoldier.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appslelo.ebgsoldier.R;
import com.appslelo.ebgsoldier.api.Api;
import com.appslelo.ebgsoldier.api.ApiClients;
import com.appslelo.ebgsoldier.model.ModelChecksum;
import com.appslelo.ebgsoldier.model.ModelTransactionHistory;
import com.appslelo.ebgsoldier.storage.SharedPrefManager;
import com.appslelo.ebgsoldier.utils.Constant;
import com.appslelo.ebgsoldier.utils.CustomLog;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {


    private SharedPrefManager tSharedPrefManager;
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
    private TextView mWalletAmountTV;
    private String mMatchId;
    private int mEntryFee;
    private TextView mJoinAmountTV;
    private EditText mFeeAmountET;
    private String mMatchName;
    private TextView mBonusAmountTV;
    private TextView mTotalAmountTV;
    private double bonus;
    private double bonusAmount;

    @BindView(R.id.btn_payment)
    protected Button mPaymentBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        tSharedPrefManager = new SharedPrefManager(getApplicationContext());
        mWalletAmountTV = findViewById(R.id.wallet_amount);
        mJoinAmountTV = findViewById(R.id.join_amount);
        mFeeAmountET = findViewById(R.id.et_fee_amount);
        mBonusAmountTV = findViewById(R.id.bonus_amount);
        mTotalAmountTV = findViewById(R.id.total_amount);
        checkReadSmsPermission();
        mMatchId = getIntent().getStringExtra("match_id");
        mMatchName = getIntent().getStringExtra("match_name");
        try {
            mEntryFee = Integer.parseInt(getIntent().getStringExtra("entry_fee"));
            Log.d("danny", "PaymentActivity init called,, mMatchId :" + mMatchId + " entryFee :" + mEntryFee);
        } catch (NumberFormatException e){
            if (getIntent().getStringExtra("entry_fee").equalsIgnoreCase("Free")) {
                mEntryFee = 0;
            } else {
                e.printStackTrace();
                Toast.makeText(this, "Entry Fees is not correct !", Toast.LENGTH_LONG).show();
                finish();
            }
        }

            mCustId = tSharedPrefManager.getUserId();
            mEmail = tSharedPrefManager.getUserEmail();
            mMobile = tSharedPrefManager.getMobile();
            String mTemp= mCustId.substring(0, Math.min(mCustId.length(), 6));
            mOrderId = mTemp + getOrderId();
            mIndustryId = "Retail";
            mChannelId = "WAP";
            mWebsite = "DEFAULT";
            mCallBackUrl = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID="+mOrderId;
            updateWalletAmount();


    }

    @OnClick(R.id.btn_payment)
    public void btn_paymentClicked(View view){
        if (mAmount != null) {
            if (mPaymentBtn.getText().toString().equalsIgnoreCase("Add")) {
                generateChecksumApi();
            } else {
                callAmountDeductionApi();

            }
        }
    }
    @OnClick(R.id.iv_back)
    public void iv_backClicked(View view){
        onBackPressed();
    }

    private void updateWalletAmount() {

        double wallet = Double.parseDouble(tSharedPrefManager.getUserWallet());
        bonus = Double.parseDouble(tSharedPrefManager.getUserBonus());
        bonusAmount = mEntryFee * 0.20;
        mWalletAmountTV.setText(String.valueOf(wallet));
        mJoinAmountTV.setText(String.valueOf(mEntryFee));
        mBonusAmountTV.setText(String.valueOf(bonus));
        double mNewEntryFee;
        if (bonus >= bonusAmount) {
            mNewEntryFee = mEntryFee - bonusAmount;
        } else {
            mNewEntryFee = mEntryFee - bonus;
        }
        mTotalAmountTV.setText(String.valueOf(wallet - mNewEntryFee));
        if (mNewEntryFee > wallet){
            mFeeAmountET.setVisibility(View.VISIBLE);
            mAmount = String.valueOf(mNewEntryFee - wallet);
            mFeeAmountET.setText("₹ "+ mAmount);
            mPaymentBtn.setText("Add");
        } else {
            mAmount = String.valueOf(mNewEntryFee);
            mFeeAmountET.setVisibility(View.GONE);
            mPaymentBtn.setText("Join");
        }
    }

    private void checkReadSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }
    }

    private void generateChecksumApi() {
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelChecksum> call = api.generateCheckSum(MID, mOrderId,mCustId,mIndustryId,mChannelId,
                mAmount,mWebsite,mEmail,mMobile);
        call.enqueue(new Callback<ModelChecksum>() {
            @Override
            public void onResponse(Call<ModelChecksum> call, Response<ModelChecksum> response) {
                assert response.body() != null;
                mCheckSum = response.body().getCHECKSUMHASH();
                Log.d("danny","generate checksum onResponse :"+ mCheckSum);
                initPaymentMethod();
            }

            @Override
            public void onFailure(Call<ModelChecksum> call, Throwable t) {
                Log.d("danny","generate checksum onFailure :"+ call.toString());
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

        mPaymentService.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {
            /*Call Backs*/
            public void someUIErrorOccurred(String inErrorMessage) {
                /*Display the error message as below */
                Toast.makeText(getApplicationContext(), "UI Error " + inErrorMessage , Toast.LENGTH_LONG).show();
            }
            public void onTransactionResponse(Bundle inResponse) {
                /*Display the message as below */
                Log.d("danny","onTransactionResponse inResponse :"+inResponse.toString());
                if (inResponse.get(Constant.STATUS).equals(Constant.TXN_SUCCESS) && inResponse.get(Constant.RESPMSG).equals(Constant.Txn_Success)) {
                    callTansactionHistroyApi(inResponse);
                } else {
                    Toast.makeText(getApplicationContext(), "Payment Transaction Error occur", Toast.LENGTH_LONG).show();
                }
            }
            public void networkNotAvailable() {
                /*Display the message as below */
                Toast.makeText(getApplicationContext(), "Network connection error: Check your internet connectivity", Toast.LENGTH_LONG).show();
            }
            public void clientAuthenticationFailed(String inErrorMessage) {
                /*Display the message as below */
                Toast.makeText(getApplicationContext(), "Authentication failed: Server error" + inErrorMessage.toString(), Toast.LENGTH_LONG).show();

            }
            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                /*Display the message as below */
                Toast.makeText(getApplicationContext(), "Unable to load webpage " + inErrorMessage.toString(), Toast.LENGTH_LONG).show();
            }
            public void onBackPressedCancelTransaction() {
                /*Display the message as below */
                Toast.makeText(getApplicationContext(), "Transaction cancelled back press" , Toast.LENGTH_LONG).show();
            }
            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                /*Display the message as below */
                Toast.makeText(getApplicationContext(), "Payment Transaction cancel " + inResponse.toString(), Toast.LENGTH_LONG).show();
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
        Log.d("danny","callTransactionHistoryApi send data :"+ inResponse.toString());

        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelTransactionHistory> call = api.paymentTransactionHistory(status, checksum, bankName, orderId, txnAmount, txnDate, mid,
                txnId, respcode, paymentMode, banktxnid, currency, gatewayname, respmsg, mCustId);
        call.enqueue(new Callback<ModelTransactionHistory>() {
            @Override
            public void onResponse(Call<ModelTransactionHistory> call, Response<ModelTransactionHistory> response) {
                assert response.body() != null;
                if (!response.body().getError()){
                    Toast.makeText(getApplicationContext(), "Amount added in wallet successfully", Toast.LENGTH_LONG).show();
                    tSharedPrefManager.setUserWallet(String.valueOf(response.body().getWallet()));
                     updateWalletAmount();
                }
            }

            @Override
            public void onFailure(Call<ModelTransactionHistory> call, Throwable t) {

            }
        });
    }

    private void callAmountDeductionApi() {
        String bonusTxnAmnt;
        if(bonus >= bonusAmount) {
            bonusTxnAmnt = String.valueOf(bonusAmount);
        } else {
            bonusTxnAmnt = String.valueOf(bonus);
        }

        CustomLog.e(Constant.TAG, "Amount : "+ mAmount+"\nbonusTxnAmnt : "+ bonusTxnAmnt);
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelTransactionHistory> call = api.amountDeductionApi(mMatchId, mAmount, bonusTxnAmnt, mCustId);
        call.enqueue(new Callback<ModelTransactionHistory>() {
            @Override
            public void onResponse(Call<ModelTransactionHistory> call, Response<ModelTransactionHistory> response) {
                assert response.body() != null;
                Log.d(Constant.TAG,"callTransactionHistoryApi onResonse :"+response.body().toString());
                if (!response.body().getError()){
                    Toast.makeText(getApplicationContext(), "Game joined successfully", Toast.LENGTH_LONG).show();
                    tSharedPrefManager.setUserWallet(String.valueOf(response.body().getWallet()));
                    tSharedPrefManager.setUserBonus(String.valueOf(response.body().getBonus()));
//                    user.setWalletAmount(String.valueOf(response.body().getWallet()));
//                    user.setTotalEarnedRefferals(String.valueOf(response.body().getBonus()));// TODO get bonus amount in onresponse
                    Log.d(Constant.TAG,"callTransactionHistoryApi onResonse success ,, current wallet amount :"+response.body().getWallet());
                    Intent intent = new Intent(PaymentActivity.this, MatchDetailActivity.class);
                    intent.putExtra("match_id",mMatchId);
                    intent.putExtra("match_name",mMatchName);
                    intent.putExtra("join_status","1");
                    startActivity(intent);
                    finish();
                }else {
                    Log.d(Constant.TAG, "Error : "+ response.body().getMessage());


                }
            }

            @Override
            public void onFailure(Call<ModelTransactionHistory> call, Throwable t) {
                Log.d(Constant.TAG,"Payment Not Responding :"+t);

            }
        });
    }
}
