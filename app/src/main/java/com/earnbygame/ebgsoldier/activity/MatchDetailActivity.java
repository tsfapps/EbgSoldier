package com.earnbygame.ebgsoldier.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.earnbygame.ebgsoldier.R;
import com.earnbygame.ebgsoldier.adapter.JoinedUserAdapter;
import com.earnbygame.ebgsoldier.api.Api;
import com.earnbygame.ebgsoldier.api.ApiClients;
import com.earnbygame.ebgsoldier.model.ModelMatchDetail;
import com.earnbygame.ebgsoldier.model.ModelMatchUserJoined;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchDetailActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager tLayoutManager;
    private JoinedUserAdapter tAdapter;
    private List<ModelMatchUserJoined> tListUserJoined;

    private String mMatchId;
    private String mMatchName;
    private TextView mMatchNameTV;
    private Button mBtnJoin;
    private String mJoinedStatus;
    private ImageView mBackIV;
    private Button mBtnLoadUserJoined;

    @BindView(R.id.rvJoinedUser)
    protected RecyclerView rvJoinedUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);
        ButterKnife.bind(this);
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

        tLayoutManager = new LinearLayoutManager(this);
        rvJoinedUser.setLayoutManager(tLayoutManager);
    }

    private void callMatchUserJoinedApi(String mMatchId) {
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelMatchUserJoined>> call = api.matchUserJoined(mMatchId);
        call.enqueue(new Callback<List<ModelMatchUserJoined>>() {
            @Override
            public void onResponse(Call<List<ModelMatchUserJoined>> call, Response<List<ModelMatchUserJoined>> response) {
                Log.d("danny","callMatchUserJoinedApi onResponse :"+ response.body().toString());
                rvJoinedUser.setVisibility(View.VISIBLE);
                tListUserJoined = response.body();
                tAdapter = new JoinedUserAdapter(tListUserJoined, getApplicationContext());
                rvJoinedUser.setAdapter(tAdapter);

            }

            @Override
            public void onFailure(Call<List<ModelMatchUserJoined>> call, Throwable t) {
                Log.d("danny","callMatchUserJoinedApi onFailure : "+ t);
                t.printStackTrace();
            }
        });
    }

    private void callMatchDetailApi() {
        Log.d("danny","callMatchDetailApi matchId :"+mMatchId);
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelMatchDetail> call = api.matchDetailApi("");
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
