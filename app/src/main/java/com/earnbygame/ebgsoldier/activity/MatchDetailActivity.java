package com.earnbygame.ebgsoldier.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.earnbygame.ebgsoldier.R;

public class MatchDetailActivity extends AppCompatActivity {

    private String mMatchId;
    private String mMatchName;
    private TextView mMatchNameTV;
    private Button mBtnJoin;
    private String mJoinedStatus;
    private ImageView mBackIV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_match_detail);
        init();
    }

    private void init() {
        mMatchId = getIntent().getStringExtra("match_id");
        mMatchName = getIntent().getStringExtra("match_name");
        mJoinedStatus = getIntent().getStringExtra("join_status");

        mMatchNameTV = findViewById(R.id.txt_match_name);
        mBtnJoin = findViewById(R.id.btn_join);
        mBackIV = findViewById(R.id.iv_back);
        mMatchNameTV.setText(mMatchName);
        if (mJoinedStatus.equals("1")){
            mBtnJoin.setText("Joined");
        } else {
            mBtnJoin.setText("Join");
        }

        mBackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
