package com.earnbygame.ebgsoldier.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.earnbygame.ebgsoldier.R;
import com.earnbygame.ebgsoldier.model.User;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private List<User> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();
    }

    private void init() {
        mList = User.listAll(User.class);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mList.size() > 0){
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
