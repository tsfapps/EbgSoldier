package com.appslelo.ebgsoldier.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appslelo.ebgsoldier.R;
import com.appslelo.ebgsoldier.adapter.JoinedUserAdapter;
import com.appslelo.ebgsoldier.api.Api;
import com.appslelo.ebgsoldier.api.ApiClients;
import com.appslelo.ebgsoldier.model.ModelMatchDetail;
import com.appslelo.ebgsoldier.model.ModelMatchUserJoined;
import com.appslelo.ebgsoldier.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    private String mJoinedStatus;
    private ImageView mBackIV;

    @BindView(R.id.rvJoinedUser)
    protected RecyclerView rvJoinedUser;
    @BindView(R.id.tv_contentMatch)
    protected TextView tv_contentMatch;
    @BindView(R.id.tvRoomId)
    protected TextView tvRoomId;
    @BindView(R.id.tvRoomPassword)
    protected TextView tvRoomPassword;
    @BindView(R.id.llRoomIdPass)
    protected LinearLayout llRoomIdPass;
    @BindView(R.id.tvLoadUser)
    protected TextView tvLoadUser;
    @BindView(R.id.btn_join)
    protected Button mBtnJoin;
    @BindView(R.id.iv_back)
    protected ImageView iv_back;


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
        mMatchNameTV.setText(mMatchName);
        if (mJoinedStatus.equals("1")){
            mBtnJoin.setText("Joined");
        } else {
            mBtnJoin.setText("Join");
        }
        callMatchDetailApi();

        tLayoutManager = new LinearLayoutManager(this);
        rvJoinedUser.setLayoutManager(tLayoutManager);
    }


    @OnClick(R.id.iv_back)
    public void iv_backClicked(View view){
       onBackPressed();
    }
    @OnClick(R.id.tvLoadUser)
    public void tvLoadUserClicked(View view){
        if (mMatchId != null) {
            callMatchUserJoinedApi(mMatchId);
        }
    }

    private void callMatchUserJoinedApi(String mMatchId) {
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelMatchUserJoined>> call = api.matchUserJoined(mMatchId);
        call.enqueue(new Callback<List<ModelMatchUserJoined>>() {
            @Override
            public void onResponse(Call<List<ModelMatchUserJoined>> call, Response<List<ModelMatchUserJoined>> response) {
                Log.d(Constant.TAG,"callMatchUserJoinedApi onResponse :"+ response.body().toString());
                rvJoinedUser.setVisibility(View.VISIBLE);
                tListUserJoined = response.body();
                tAdapter = new JoinedUserAdapter(tListUserJoined, getApplicationContext());
                rvJoinedUser.setAdapter(tAdapter);

            }

            @Override
            public void onFailure(Call<List<ModelMatchUserJoined>> call, Throwable t) {
                Log.d(Constant.TAG,"callMatchUserJoinedApi onFailure : "+ t);
                t.printStackTrace();
            }
        });
    }

    private void callMatchDetailApi() {
        Log.d(Constant.TAG,"callMatchDetailApi matchId :"+mMatchId);
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelMatchDetail> call = api.matchDetailApi(mMatchId);
        call.enqueue(new Callback<ModelMatchDetail>() {
            @Override
            public void onResponse(Call<ModelMatchDetail> call, Response<ModelMatchDetail> response) {
                ModelMatchDetail tModel = response.body();
                tv_contentMatch.setText(tModel.getContent());

                if (mJoinedStatus.equals("1")){
                    llRoomIdPass.setVisibility(View.VISIBLE);
                    tvRoomId.setText(tModel.getRoomId());
                    tvRoomPassword.setText(tModel.getRoomPassword());
                }
                else {
                    llRoomIdPass.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<ModelMatchDetail> call, Throwable t) {
                Log.d(Constant.TAG,"MatchDetails onFailure :"+ t);
                t.printStackTrace();
            }
        });
    }
}
