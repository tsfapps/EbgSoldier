package com.earnbygame.ebgsoldier.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.earnbygame.ebgsoldier.R;
import com.earnbygame.ebgsoldier.model.login.ModelLogin;
import com.earnbygame.ebgsoldier.model.login.User;
import com.earnbygame.ebgsoldier.presenter.PresenterLogin;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";

    @BindView(R.id.et_user_name)
    protected EditText mEtUserName;
    @BindView(R.id.et_login_pass)
    protected EditText mEtPassword;

    private String mUserName = null;
    private String mPassword = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    private void onNewRegister(){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    private void onLoginSuccess(){
        if (User.listAll(User.class).size() > 0) {
            startActivity(new Intent(LoginActivity.this, DashActivity.class));
            finish();
        }
    }

    @OnClick({ R.id.tv_login_newRegister, R.id.btn_login_submit})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_login_newRegister:
                onNewRegister();
                break;
            case R.id.btn_login_submit:
                callLoginApi();
                break;
        }
    }

    private void callLoginApi(){
        mUserName = mEtUserName.getText().toString();
        mPassword = mEtPassword.getText().toString();
        if (mUserName != null && mPassword != null) {
            new PresenterLogin(LoginActivity.this,mUserName,mPassword);
        }
    }


    public void onLoginApiResponse(Response<ModelLogin> response) {
        Log.d(TAG,"onLoginApiResponse response :"+response);
        assert response.body() != null;
        if (response.body().getError()){
            Toast.makeText(this,""+response.body().getMessage(),Toast.LENGTH_SHORT).show();
        } else {
            User.deleteAll(User.class);
            User user = new User();
            user = response.body().getUser();
            user.save();
            onLoginSuccess();
        }
    }

    public void onLoginApiFailure(Call<ModelLogin> call) {
        Log.d(TAG,"onLoginApiFailure call :"+call);
    }
}
