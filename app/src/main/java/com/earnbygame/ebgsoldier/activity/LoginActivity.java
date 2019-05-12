package com.earnbygame.ebgsoldier.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.earnbygame.ebgsoldier.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.tv_login_newRegister)
    public void onNewRegister(){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
    @OnClick(R.id.btn_login_submit)
    public void onLoginSuccess(){
        startActivity(new Intent(LoginActivity.this, DashActivity.class));
    }

}
