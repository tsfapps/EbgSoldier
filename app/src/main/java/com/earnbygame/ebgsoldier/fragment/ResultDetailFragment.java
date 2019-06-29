package com.earnbygame.ebgsoldier.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.earnbygame.ebgsoldier.R;
import com.earnbygame.ebgsoldier.adapter.ResultAdapter;
import com.earnbygame.ebgsoldier.adapter.ResultAdapterDetails;
import com.earnbygame.ebgsoldier.api.Api;
import com.earnbygame.ebgsoldier.api.ApiClients;
import com.earnbygame.ebgsoldier.model.ModelMatchResult;
import com.earnbygame.ebgsoldier.model.ModelMatchResultDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultDetailFragment extends Fragment {
    private RecyclerView.LayoutManager tLayoutManager;
    private ResultAdapterDetails tAdapter;
    private Context tContext;
    @BindView(R.id.rv_resultDetail)
    protected RecyclerView rv_resultDetail;

    String strMatchId;
    public static ResultDetailFragment newInstance(String strMatchId) {

        ResultDetailFragment fragment = new ResultDetailFragment();
        fragment.strMatchId = strMatchId;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_result_detail, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        tContext = getContext();
        tLayoutManager = new LinearLayoutManager(tContext);
        rv_resultDetail.setLayoutManager(tLayoutManager);
        callApi();

    }

    private void callApi(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelMatchResultDetail>> call = api.matchResultDetails(strMatchId);
        call.enqueue(new Callback<List<ModelMatchResultDetail>>() {
            @Override
            public void onResponse(Call<List<ModelMatchResultDetail>> call, Response<List<ModelMatchResultDetail>> response) {
                List<ModelMatchResultDetail> tModels = response.body();
                tAdapter = new ResultAdapterDetails(tModels);
                rv_resultDetail.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<List<ModelMatchResultDetail>> call, Throwable t) {

            }
        });
    }

}
