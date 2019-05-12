package com.earnbygame.ebgsoldier.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.earnbygame.ebgsoldier.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.tv_reg_login)
    public void onNewRegister(){

        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}
