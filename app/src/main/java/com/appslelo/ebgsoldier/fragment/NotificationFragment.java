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
import com.appslelo.ebgsoldier.adapter.NotificationAdapter;
import com.appslelo.ebgsoldier.api.Api;
import com.appslelo.ebgsoldier.api.ApiClients;
import com.appslelo.ebgsoldier.model.ModelNotification;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {

    private RecyclerView.LayoutManager tManager;
    private NotificationAdapter tAdapter;
    private Context tContext;

    @BindView(R.id.rvNotification)
    protected RecyclerView rvNotification;
    @BindView(R.id.swipeNotification)
    protected SwipeRefreshLayout swipeNotification;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_notification, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        tContext = getContext();
        tManager = new LinearLayoutManager(tContext);
        rvNotification.setLayoutManager(tManager);
        callApi();
        swipeNotification.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeNotification.setRefreshing(false);
                        callApi();
                    }
                },3000);


            }
        });

    }

    private void callApi(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelNotification>> call = api.notificationDetail();
        call.enqueue(new Callback<List<ModelNotification>>() {
            @Override
            public void onResponse(Call<List<ModelNotification>> call, Response<List<ModelNotification>> response) {
                List<ModelNotification> tModels = response.body();
                tAdapter = new NotificationAdapter(tModels);
                rvNotification.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<List<ModelNotification>> call, Throwable t) {

            }
        });
    }
}
