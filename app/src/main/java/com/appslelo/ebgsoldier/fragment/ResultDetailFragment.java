package com.appslelo.ebgsoldier.fragment;

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
import android.widget.TextView;

import com.appslelo.ebgsoldier.R;
import com.appslelo.ebgsoldier.adapter.ResultAdapterDetails;
import com.appslelo.ebgsoldier.api.Api;
import com.appslelo.ebgsoldier.api.ApiClients;
import com.appslelo.ebgsoldier.model.ModelMatchResult;
import com.appslelo.ebgsoldier.model.ModelMatchResultDetail;
import com.appslelo.ebgsoldier.utils.DateUtils;

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
    @BindView(R.id.tvResultDetailName)
    protected TextView tvResultDetailName;
    @BindView(R.id.tvResultDetailDate)
    protected TextView tvResultDetailDate;
    @BindView(R.id.tvResultDetailTime)
    protected TextView tvResultDetailTime;
    @BindView(R.id.tvResultDetailFirstPrize)
    protected TextView tvResultDetailFirstPrize;
    @BindView(R.id.tvResultDetailPerKill)
    protected TextView tvResultDetailPerKill;
    @BindView(R.id.tvResultDetailEntryFees)
    protected TextView tvResultDetailEntryFees;
    @BindView(R.id.tvResultDetailVersion)
    protected TextView tvResultDetailVersion;
    @BindView(R.id.tvResultDetailType)
    protected TextView tvResultDetailType;
    @BindView(R.id.tvResultDetailMap)
    protected TextView tvResultDetailMap;

    private ModelMatchResult tModelMatchResult;
    public static ResultDetailFragment newInstance(ModelMatchResult tModelMatchResult) {

        ResultDetailFragment fragment = new ResultDetailFragment();
        fragment.tModelMatchResult = tModelMatchResult;
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

        String strDate = DateUtils.ddMMMMyyyy(tModelMatchResult.getMatchDate());
        String strTime = DateUtils.hhmm(tModelMatchResult.getMatchDate());
        tvResultDetailName.setText(tModelMatchResult.getMatchName());
        tvResultDetailDate.setText(strDate);
        tvResultDetailTime.setText(strTime);
        tvResultDetailFirstPrize.setText(tModelMatchResult.getFirstPrize());
        tvResultDetailPerKill.setText(tModelMatchResult.getPerKillPrize());
        tvResultDetailEntryFees.setText(tModelMatchResult.getEntryFee());
        tvResultDetailVersion.setText(tModelMatchResult.getMatchVersion());
        tvResultDetailType.setText(tModelMatchResult.getMatchType());
        tvResultDetailMap.setText(tModelMatchResult.getMatchMap());
        callApi();

    }

    private void callApi(){
        String strMatchId = tModelMatchResult.getMatchId();
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
