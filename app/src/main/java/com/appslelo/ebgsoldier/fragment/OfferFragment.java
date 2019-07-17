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
import com.appslelo.ebgsoldier.adapter.OfferAdapter;
import com.appslelo.ebgsoldier.api.Api;
import com.appslelo.ebgsoldier.api.ApiClients;
import com.appslelo.ebgsoldier.model.ModelOffer;
import com.appslelo.ebgsoldier.storage.SharedPrefManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferFragment extends Fragment {
    private SharedPrefManager tSharedPrefManager;

    private RecyclerView.LayoutManager tManager;
    private OfferAdapter tAdapter;
    private Context tContext;
    private String strUserId;


    @BindView(R.id.rvOffer)
    protected RecyclerView rvOffer;
    @BindView(R.id.swipeOffer)
    protected SwipeRefreshLayout swipeOffer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_offer, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        tContext = getContext();
        tManager = new LinearLayoutManager(tContext);
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        strUserId = tSharedPrefManager.getUserId();
        rvOffer.setLayoutManager(tManager);
        callApi();
        swipeOffer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeOffer.setRefreshing(false);
                        callApi();
                    }
                },3000);


            }
        });

    }

    private void callApi(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelOffer>> call = api.offerDetail(strUserId);
        call.enqueue(new Callback<List<ModelOffer>>() {
            @Override
            public void onResponse(Call<List<ModelOffer>> call, Response<List<ModelOffer>> response) {
                List<ModelOffer> tModels = response.body();
                tAdapter = new OfferAdapter(tModels, tContext, strUserId);
                rvOffer.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<List<ModelOffer>> call, Throwable t) {

            }
        });
    }
}
