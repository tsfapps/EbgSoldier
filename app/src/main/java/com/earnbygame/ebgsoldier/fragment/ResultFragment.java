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
import com.earnbygame.ebgsoldier.adapter.ResultAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultFragment extends Fragment {
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
        tLayoutManager = new LinearLayoutManager(tContext);
        rvResult.setLayoutManager(tLayoutManager);
        tAdapter = new ResultAdapter(tContext);
        rvResult.setAdapter(tAdapter);
    }

}
