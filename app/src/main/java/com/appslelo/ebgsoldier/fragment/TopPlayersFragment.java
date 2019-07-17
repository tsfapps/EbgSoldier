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
import com.appslelo.ebgsoldier.adapter.TopPlayersAdapter;
import com.appslelo.ebgsoldier.api.Api;
import com.appslelo.ebgsoldier.api.ApiClients;
import com.appslelo.ebgsoldier.model.ModelTopPlayers;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopPlayersFragment extends Fragment {

    private RecyclerView.LayoutManager tManager;
    private TopPlayersAdapter tAdapter;
    private Context tContext;


    @BindView(R.id.rvTopPlayers)
    protected RecyclerView rvTopPlayers;

    @BindView(R.id.swipeTopPlayers)
    protected SwipeRefreshLayout swipeTopPlayers;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_top_players, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        tContext = getContext();
        tManager = new LinearLayoutManager(tContext);
        rvTopPlayers.setLayoutManager(tManager);
        callApiFaq();
        swipeTopPlayers.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        swipeTopPlayers.setRefreshing(false);
                        callApiFaq();

                    }
                }, 3000);}
        });

    }

    private void callApiFaq(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelTopPlayers>> call = api.topPlayers();
       call.enqueue(new Callback<List<ModelTopPlayers>>() {
           @Override
           public void onResponse(Call<List<ModelTopPlayers>> call, Response<List<ModelTopPlayers>> response) {
               List<ModelTopPlayers> tModels = response.body();
               tAdapter = new TopPlayersAdapter(tModels);
               rvTopPlayers.setAdapter(tAdapter);
           }

           @Override
           public void onFailure(Call<List<ModelTopPlayers>> call, Throwable t) {

           }
       });
    }
}
