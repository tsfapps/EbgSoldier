package com.appslelo.ebgsoldier.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.appslelo.ebgsoldier.R;
import com.appslelo.ebgsoldier.model.ModelRegister;
import com.appslelo.ebgsoldier.presenter.PresenterRegister;
import com.appslelo.ebgsoldier.utils.CustomToast;
import com.appslelo.ebgsoldier.utils.Validate;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private final String TAG = "RegisterActivity";
    @BindView(R.id.et_reg_email)
    protected EditText mRegEmail;
    @BindView(R.id.et_reg_user_name)
    protected EditText mRegPGUserName;
    @BindView(R.id.et_reg_Name)
    protected EditText mRegName;
    @BindView(R.id.et_reg_pass)
    protected EditText mRegPassword;
    @BindView(R.id.et_reg_pass_re)
    protected EditText mRegPasswordCheck;
    @BindView(R.id.et_reg_phone_no)
    protected EditText mRegPhoneNo;
    @BindView(R.id.et_reg_refer_code)
    protected EditText mRegReferCode;

    private String mName;
    private String mPgUserName;
    private String mPhoneNo;
    private String mEmailId;
    private String mPassword;
    private String mPasswordCheck;
    private String mReferCode;
    private String mProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    public void startLoginActivity(){
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    @OnClick({ R.id.tv_reg_login, R.id.btn_reg_submit })
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.tv_reg_login:
               startLoginActivity();
               break;
           case R.id.btn_reg_submit:
               getDataFromEditText();
               break;
       }
    }

    private void getDataFromEditText() {
        mName = mRegName.getText().toString();
        mPgUserName = mRegPGUserName.getText().toString();
        mPhoneNo = mRegPhoneNo.getText().toString();
        mEmailId = mRegEmail.getText().toString();
        mPassword = mRegPassword.getText().toString();
        mPasswordCheck = mRegPasswordCheck.getText().toString();
        mReferCode = mRegReferCode.getText().toString();
        if (mName.equalsIgnoreCase("")){
            mRegName.setError("Enter your Name");
            CustomToast.tToastTop(getApplicationContext(), "Enter your name");
        }
        else if (mPhoneNo.equalsIgnoreCase("")){
            mRegPhoneNo.setError("Enter mobile number");

            CustomToast.tToastTop(getApplicationContext(), "Enter mobile number");
        }
        else if (!Validate.isValidMobile(mPhoneNo)){
            mRegPhoneNo.setError("Enter a valid mobile number");
            CustomToast.tToastTop(getApplicationContext(), "Enter a valid mobile number");
        }
        else if (mEmailId.equalsIgnoreCase("")){
            mRegEmail.setError("Enter email id");

            CustomToast.tToastTop(getApplicationContext(), "Enter mobile number");
        }
        else if (!Validate.isValidMail(mEmailId)){
            mRegEmail.setError("Enter a valid email id");
            CustomToast.tToastTop(getApplicationContext(), "Enter a valid email id");
        }
        else if (mPassword.equalsIgnoreCase("")){
            mRegPassword.setError("Enter Password");

            CustomToast.tToastTop(getApplicationContext(), "Enter Password");
        }
       else if (!mPassword.equals(mPasswordCheck)) {
            mRegPasswordCheck.setError("Password not match");
            CustomToast.tToastTop(getApplicationContext(), "Password not match");

        } else {
            new PresenterRegister(RegisterActivity.this, mName, mPgUserName, mPhoneNo, mEmailId, mPasswordCheck, mReferCode, mProfilePic);

        }
    }

    public void onRegisterApiResponse(Response<ModelRegister> response) {
        Log.d(TAG,"onRegisterApiResponse  response:"+response.toString());
        assert response.body() != null;
        if (response.body().getError()) {
            Toast.makeText(this,""+response.body().getMessage(),Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,""+response.body().getMessage(),Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this,LoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    public void onRegisterApiFailure(Call<ModelRegister> call) {
        Log.d(TAG,"onRegisterApiFailure  call:"+call.toString());
        Toast.makeText(this,"Server error, Please retry again",Toast.LENGTH_SHORT).show();
    }
}
