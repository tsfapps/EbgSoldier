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
import com.appslelo.ebgsoldier.adapter.FaqAdapter;
import com.appslelo.ebgsoldier.api.Api;
import com.appslelo.ebgsoldier.api.ApiClients;
import com.appslelo.ebgsoldier.model.ModelFaq;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaqFragment extends Fragment {

    private RecyclerView.LayoutManager tManager;
    private FaqAdapter tAdapter;
    private Context tContext;


    @BindView(R.id.rvFaq)
    protected RecyclerView rvFaq;

    @BindView(R.id.swipeFaq)
    protected SwipeRefreshLayout swipeFaq;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_faq, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        tContext = getContext();
        tManager = new LinearLayoutManager(tContext);
        rvFaq.setLayoutManager(tManager);
        callApiFaq();
        swipeFaq.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        swipeFaq.setRefreshing(false);
                        callApiFaq();

                    }
                }, 3000);}
        });

    }

    private void callApiFaq(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelFaq>> call = api.faqDetail();
       call.enqueue(new Callback<List<ModelFaq>>() {
           @Override
           public void onResponse(Call<List<ModelFaq>> call, Response<List<ModelFaq>> response) {
               List<ModelFaq> tModels = response.body();
               tAdapter = new FaqAdapter(tModels);
               rvFaq.setAdapter(tAdapter);
           }

           @Override
           public void onFailure(Call<List<ModelFaq>> call, Throwable t) {

           }
       });
    }
}
