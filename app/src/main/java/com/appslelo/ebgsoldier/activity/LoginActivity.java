package com.appslelo.ebgsoldier.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.appslelo.ebgsoldier.R;
import com.appslelo.ebgsoldier.model.login.ModelLogin;
import com.appslelo.ebgsoldier.presenter.PresenterLogin;
import com.appslelo.ebgsoldier.storage.SharedPrefManager;
import com.appslelo.ebgsoldier.utils.CustomToast;
import com.appslelo.ebgsoldier.utils.Validate;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";

    private SharedPrefManager tSharedPrefManager;

    @BindView(R.id.etPhoneNumber)
    protected EditText etPhoneNumber;
    @BindView(R.id.et_login_pass)
    protected EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        tSharedPrefManager = new SharedPrefManager(getApplicationContext());
    }

    private void onNewRegister(){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

//    private void onLoginSuccess(){
//        if (User.listAll(User.class).size() > 0) {
//
//            finish();
//        }
//    }

    @OnClick({ R.id.tv_login_newRegister, R.id.btn_login_submit, R.id.tv_login_forgotPass})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_login_newRegister:
                onNewRegister();
                break;
            case R.id.btn_login_submit:
                callLoginApi();
                break;
         case R.id.tv_login_forgotPass:
                sendMail();
                break;
        }
    }

    private void callLoginApi(){
        String strPhoneNumber = etPhoneNumber.getText().toString();
        String strPassword = etPassword.getText().toString();
        if (strPhoneNumber.equalsIgnoreCase("")){
            etPhoneNumber.setError("Enter mobile number");

            CustomToast.tToastTop(getApplicationContext(), "Enter mobile number");
        }
       else if (!Validate.isValidMobile(strPhoneNumber)){
            etPhoneNumber.setError("Enter a valid mobile number");
            CustomToast.tToastTop(getApplicationContext(), "Enter a valid mobile number");
        }
        else if (strPassword.equalsIgnoreCase(""))
        {
            etPassword.setError("Enter the password");

            CustomToast.tToastTop(getApplicationContext(), "Enter the password");

        }else {
            new PresenterLogin(LoginActivity.this, strPhoneNumber, strPassword);

        }
    }
    private void sendMail(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","ebgsoldier@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Forgot Password");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Write your Pubg user name or Phone Number");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }


    public void onLoginApiResponse(Response<ModelLogin> response) {
        Log.d(TAG,"onLoginApiResponse response :"+response);
        assert response.body() != null;
        if (response.body().getError()){
            Toast.makeText(this,""+response.body().getMessage(),Toast.LENGTH_SHORT).show();
        } else {
            String strWallet = response.body().getUser().getWalletAmount();
            String strUserId = response.body().getUser().getUserId();
            String strUserName = response.body().getUser().getUserName();
            String strPubgName = response.body().getUser().getPubgUserName();
            String strDob = response.body().getUser().getDob();
            String strUserPhone = response.body().getUser().getPhoneNo();
            String strEmail = response.body().getUser().getEmail();
            String strReferralCode = response.body().getUser().getReferralCode();
            String strNoOfReferral = response.body().getUser().getNoOfReferrals();
            String strMatchPlayed = response.body().getUser().getNoOfMatchPlayed();
            String strEarned = response.body().getUser().getTotalEarnedMatch();
            String strBonus = response.body().getUser().getTotalEarnedRefferals();
            String strAddedAmount = response.body().getUser().getTotalAddedAmount();
            String strTotalKill = response.body().getUser().getTotalKills();
            String strRank = response.body().getUser().getRank();
            String strLevel = response.body().getUser().getPubgLevel();
            String strDoj = response.body().getUser().getDoj();
            String strProPic = response.body().getUser().getProfilePic();
            String strStatus = response.body().getUser().getStatus();
            tSharedPrefManager.setUserData(strUserId, strUserName, strPubgName, strUserPhone, strEmail, strDob, strReferralCode,
                    strNoOfReferral, strAddedAmount, strWallet, strMatchPlayed, strEarned, strBonus, strTotalKill,
                    strRank, strLevel, strDoj, strProPic, strStatus);

            startActivity(new Intent(LoginActivity.this, DashActivity.class));
            finish();
        }
    }

    public void onLoginApiFailure(Call<ModelLogin> call) {
        Log.d(TAG,"onLoginApiFailure call :"+call);
    }
}
