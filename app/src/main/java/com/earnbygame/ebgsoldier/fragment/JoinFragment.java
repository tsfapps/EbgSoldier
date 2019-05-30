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
import com.earnbygame.ebgsoldier.adapter.JoinAdapter;
import com.earnbygame.ebgsoldier.api.Api;
import com.earnbygame.ebgsoldier.api.ApiClients;
import com.earnbygame.ebgsoldier.model.ModelJoinMatch;
import com.earnbygame.ebgsoldier.model.User;
import com.earnbygame.ebgsoldier.utils.Constant;
import com.earnbygame.ebgsoldier.utils.CustomLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinFragment extends Fragment {

    private List<ModelJoinMatch> tModels;
    private RecyclerView.LayoutManager tLayoutManager;
    private JoinAdapter tAdapter;
    private Context tContext;
    @BindView(R.id.rv_join)
    protected RecyclerView rvJoin;
    private List<User> mUserList;
    private String mUserId;

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
        mUserList = User.listAll(User.class);
        if (mUserList.size() > 0){
            mUserId = mUserList.get(0).getUserId();
        }
        tLayoutManager = new LinearLayoutManager(tContext);
        rvJoin.setLayoutManager(tLayoutManager);

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
