package com.earnbygame.ebgsoldier.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.earnbygame.ebgsoldier.R;
import com.earnbygame.ebgsoldier.api.Api;
import com.earnbygame.ebgsoldier.api.ApiClients;
import com.earnbygame.ebgsoldier.model.ModelMatchDetail;
import com.earnbygame.ebgsoldier.model.ModelMatchUserJoined;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchDetailActivity extends AppCompatActivity {

    private String mMatchId;
    private String mMatchName;
    private TextView mMatchNameTV;
    private Button mBtnJoin;
    private String mJoinedStatus;
    private ImageView mBackIV;
    private Button mBtnLoadUserJoined;

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
        mBtnLoadUserJoined = findViewById(R.id.btn_load_user);
        mMatchNameTV.setText(mMatchName);
        if (mJoinedStatus.equals("1")){
            mBtnJoin.setText("Joined");
        } else {
            mBtnJoin.setText("Join");
        }

        callMatchDetailApi();

        mBackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mBtnLoadUserJoined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMatchId != null) {
                    callMatchUserJoinedApi(mMatchId);
                }
            }
        });
    }

    private void callMatchUserJoinedApi(String mMatchId) {
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelMatchUserJoined> call = api.matchUserJoined(mMatchId);
        call.enqueue(new Callback<ModelMatchUserJoined>() {
            @Override
            public void onResponse(Call<ModelMatchUserJoined> call, Response<ModelMatchUserJoined> response) {
                Log.d("danny","callMatchUserJoinedApi onResponse :"+ response.body().toString());
            }

            @Override
            public void onFailure(Call<ModelMatchUserJoined> call, Throwable t) {
                Log.d("danny","callMatchUserJoinedApi onFailure :"+ call.toString());
                t.printStackTrace();
            }
        });
    }

    private void callMatchDetailApi() {
        Log.d("danny","callMatchDetailApi matchId :"+mMatchId);
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelMatchDetail> call = api.matchDetailApi(mMatchId);
        call.enqueue(new Callback<ModelMatchDetail>() {
            @Override
            public void onResponse(Call<ModelMatchDetail> call, Response<ModelMatchDetail> response) {
                Log.d("danny","MatchDetails onResponse :"+ response.body().toString());
            }

            @Override
            public void onFailure(Call<ModelMatchDetail> call, Throwable t) {
                Log.d("danny","MatchDetails onFailure :"+ call.toString());
                t.printStackTrace();
            }
        });
    }
}
