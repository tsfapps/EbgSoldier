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
import com.appslelo.ebgsoldier.adapter.MegaAdapter;
import com.appslelo.ebgsoldier.api.Api;
import com.appslelo.ebgsoldier.api.ApiClients;
import com.appslelo.ebgsoldier.model.ModelJoinMegaMatch;
import com.appslelo.ebgsoldier.storage.SharedPrefManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MegaFragment extends Fragment {
    private SharedPrefManager tSharedPrefManager;

    private List<ModelJoinMegaMatch> tModels;
    private RecyclerView.LayoutManager tLayoutManager;
    private MegaAdapter tAdapter;
    private Context tContext;
    @BindView(R.id.rv_mega)
    protected RecyclerView rvMega;
    private String mUserId;
    @BindView(R.id.swipeMega)
    protected SwipeRefreshLayout swipeMega;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_mega, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
            mUserId = tSharedPrefManager.getUserId();
        tLayoutManager = new LinearLayoutManager(tContext);
        rvMega.setLayoutManager(tLayoutManager);

        swipeMega.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeMega.setRefreshing(false);
                        callApi();
                    }
                },3000);


            }
        });


    }
    private void callApi(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelJoinMegaMatch>> call = api.joinMegaMatch(mUserId);
        call.enqueue(new Callback<List<ModelJoinMegaMatch>>() {
            @Override
            public void onResponse(Call<List<ModelJoinMegaMatch>> call, Response<List<ModelJoinMegaMatch>> response) {
                tModels = response.body();
                tAdapter = new MegaAdapter(getContext(), tModels);
                rvMega.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<List<ModelJoinMegaMatch>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        callApi();
    }


}
