package com.appslelo.ebgsoldier.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appslelo.ebgsoldier.R;
import com.appslelo.ebgsoldier.storage.SharedPrefManager;
import com.appslelo.ebgsoldier.utils.CustomDialogs;

public class SplashActivity extends AppCompatActivity {



    private SharedPrefManager  tSharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();
    }

    private void init() {
        tSharedPrefManager = new SharedPrefManager(getApplicationContext());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!tSharedPrefManager.getUserId().equalsIgnoreCase("")){
                    Intent intent = new Intent(SplashActivity.this,DashActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },2000);
    }
}
