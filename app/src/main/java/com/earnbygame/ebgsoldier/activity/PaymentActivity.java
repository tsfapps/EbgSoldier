package com.earnbygame.ebgsoldier.activity;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.earnbygame.ebgsoldier.R;
import com.earnbygame.ebgsoldier.api.Api;
import com.earnbygame.ebgsoldier.api.ApiClients;
import com.earnbygame.ebgsoldier.model.ModelChecksum;
import com.earnbygame.ebgsoldier.model.ModelTransactionHistory;
import com.earnbygame.ebgsoldier.model.login.User;
import com.earnbygame.ebgsoldier.utils.Constant;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    private Button mPaymentBtn;
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
    private List<User> mList;
    private TextView mWalletAmountTV;
    private String mMatchId;
    private int mEntryFee;
    private TextView mJoinAmountTV;
    private EditText mFeeAmountET;
    private ImageView mBackBtn;
    private String mMatchName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        init();
    }

    private void init() {
        mWalletAmountTV = findViewById(R.id.wallet_amount);
        mJoinAmountTV = findViewById(R.id.join_amount);
        mFeeAmountET = findViewById(R.id.et_fee_amount);
        mPaymentBtn = findViewById(R.id.btn_payment);
        mBackBtn = findViewById(R.id.iv_back);
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
        mList = User.listAll(User.class);
        if (mList.size() > 0) {
            mCustId = mList.get(0).getUserId();
            mEmail = mList.get(0).getEmail();
            mMobile = mList.get(0).getPhoneNo();
            String mTemp= mCustId.substring(0, Math.min(mCustId.length(), 6));
            mOrderId = mTemp + getOrderId();
            mIndustryId = "Retail";
            mChannelId = "WAP";
            mWebsite = "DEFAULT";
            mCallBackUrl = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID="+mOrderId;
            updateWalletAmount();
        }
        mPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("danny","onStartPayment amount to pay : "+mAmount);
                if (mAmount != null) {
                    if (mPaymentBtn.getText().toString().equalsIgnoreCase("Add")) {
                        generateChecksumApi();
                    } else {
                        callAmountDeductionApi();
                    }
                }
            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    private void updateWalletAmount() {
        mList.clear();
        mList = User.listAll(User.class);
        int wallet = Integer.parseInt(mList.get(0).getWalletAmount());
        mWalletAmountTV.setText(String.valueOf(wallet));
        mJoinAmountTV.setText(String.valueOf(mEntryFee));
        if (mEntryFee > wallet){
            mFeeAmountET.setVisibility(View.VISIBLE);
            mAmount = String.valueOf(mEntryFee - wallet);
            mFeeAmountET.setText("₹ "+ mAmount);
            mPaymentBtn.setText("Add");
        } else {
            mAmount = String.valueOf(mEntryFee);
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
                Log.d("danny","callTransactionHistoryApi onResonse :"+response.body().toString());
                if (!response.body().getError()){
                    Toast.makeText(getApplicationContext(), "Amount added in wallet successfully", Toast.LENGTH_LONG).show();
                    Log.d("danny","callTransactionHistoryApi onResonse success ,, previous wallet amount :"+mList.get(0).getWalletAmount());
                    User user= mList.get(0);
                     user.setWalletAmount(String.valueOf(response.body().getWallet()));
                     user.save();
                     Log.d("danny","callTransactionHistoryApi onResonse success ,, current wallet amount :"+response.body().getWallet());
                     updateWalletAmount();
                }
            }

            @Override
            public void onFailure(Call<ModelTransactionHistory> call, Throwable t) {

            }
        });
    }

    private void callAmountDeductionApi() {

        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelTransactionHistory> call = api.amountDeductionApi(mMatchId, mAmount, mCustId);
        call.enqueue(new Callback<ModelTransactionHistory>() {
            @Override
            public void onResponse(Call<ModelTransactionHistory> call, Response<ModelTransactionHistory> response) {
                assert response.body() != null;
                Log.d("danny","callTransactionHistoryApi onResonse :"+response.body().toString());
                if (!response.body().getError()){
                    Toast.makeText(getApplicationContext(), "Game joined successfully", Toast.LENGTH_LONG).show();
                    Log.d("danny","callTransactionHistoryApi onResonse success ,, previous wallet amount :"+mList.get(0).getWalletAmount());
                    User user= mList.get(0);
                    user.setWalletAmount(String.valueOf(response.body().getWallet()));
                    user.save();
                    Log.d("danny","callTransactionHistoryApi onResonse success ,, current wallet amount :"+response.body().getWallet());
                    Intent intent = new Intent(PaymentActivity.this, MatchDetailActivity.class);
                    intent.putExtra("match_id",mMatchId);
                    intent.putExtra("match_name",mMatchName);
                    intent.putExtra("join_status","1");
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ModelTransactionHistory> call, Throwable t) {

            }
        });
    }
}
