package com.earnbygame.ebgsoldier.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.earnbygame.ebgsoldier.R;
import com.earnbygame.ebgsoldier.adapter.JoinAdapter;
import com.earnbygame.ebgsoldier.adapter.ResultAdapter;
import com.earnbygame.ebgsoldier.api.Api;
import com.earnbygame.ebgsoldier.api.ApiClients;
import com.earnbygame.ebgsoldier.model.ModelMatchResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultFragment extends Fragment {
    private FragmentManager tFragmentManager;
    private RecyclerView.LayoutManager tLayoutManager;
    private ResultAdapter tAdapter;
    private Context tContext;
    @BindView(R.id.rv_result)
    protected RecyclerView rvResult;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_result, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        tContext = getContext();
        tFragmentManager = getFragmentManager();
        tLayoutManager = new LinearLayoutManager(tContext);
        rvResult.setLayoutManager(tLayoutManager);

        callApi();
    }

    private void callApi(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelMatchResult>> call = api.matchResult();
        call.enqueue(new Callback<List<ModelMatchResult>>() {
            @Override
            public void onResponse(Call<List<ModelMatchResult>> call, Response<List<ModelMatchResult>> response) {

                List<ModelMatchResult> tModels = response.body();

                tAdapter = new ResultAdapter(tModels, tContext, tFragmentManager);
                rvResult.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<List<ModelMatchResult>> call, Throwable t) {

            }
        });
    }
}
