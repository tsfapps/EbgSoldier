package com.appslelo.ebgsoldier.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appslelo.ebgsoldier.R;
import com.appslelo.ebgsoldier.adapter.JoinAdapter;
import com.appslelo.ebgsoldier.api.Api;
import com.appslelo.ebgsoldier.api.ApiClients;
import com.appslelo.ebgsoldier.model.ModelJoinMatch;
import com.appslelo.ebgsoldier.storage.SharedPrefManager;
import com.appslelo.ebgsoldier.utils.CustomDialogs;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinFragment extends Fragment {

    private SharedPrefManager tSharedPrefManager;
    private List<ModelJoinMatch> tModels;
    private RecyclerView.LayoutManager tLayoutManager;
    private JoinAdapter tAdapter;
    private Context tContext;
    @BindView(R.id.rv_join)
    protected RecyclerView rvJoin;
    private String mUserId;
    @BindView(R.id.swipeJoin)
    protected SwipeRefreshLayout swipeJoin;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_join, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }

    private void initFrag(){
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
            mUserId = tSharedPrefManager.getUserId();
        tLayoutManager = new LinearLayoutManager(tContext);
        rvJoin.setLayoutManager(tLayoutManager);
        swipeJoin.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeJoin.setRefreshing(false);
                        callApi();
                    }
                },3000);


            }
        });



    }

    private void callApi(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelJoinMatch>> call = api.joinMatch(mUserId);
        call.enqueue(new Callback<List<ModelJoinMatch>>() {
            @Override
            public void onResponse(Call<List<ModelJoinMatch>> call, Response<List<ModelJoinMatch>> response) {
                tModels = response.body();
                tAdapter = new JoinAdapter(getContext(), tModels);
                rvJoin.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<List<ModelJoinMatch>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        callApi();
    }
}
