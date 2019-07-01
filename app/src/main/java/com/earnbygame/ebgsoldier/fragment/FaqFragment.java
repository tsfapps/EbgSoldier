package com.earnbygame.ebgsoldier.fragment;

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
import android.widget.TextView;

import com.earnbygame.ebgsoldier.R;
import com.earnbygame.ebgsoldier.adapter.FaqAapter;
import com.earnbygame.ebgsoldier.adapter.NotificationAapter;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FaqFragment extends Fragment {

    private RecyclerView.LayoutManager tManager;
    private FaqAapter tAdapter;
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
        tAdapter = new FaqAapter();
        rvFaq.setAdapter(tAdapter);
        swipeFaq.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // cancle the Visual indication of a refresh
                        swipeFaq.setRefreshing(false);

                    }
                }, 3000);            }
        });

    }
}
