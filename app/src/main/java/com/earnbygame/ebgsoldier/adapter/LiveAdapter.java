package com.earnbygame.ebgsoldier.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.earnbygame.ebgsoldier.R;

import butterknife.ButterKnife;

public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.LiveViewHolder> {

    @NonNull
    @Override
    public LiveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_live_item, viewGroup, false);
        return new LiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LiveViewHolder liveViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class LiveViewHolder extends RecyclerView.ViewHolder{

        public LiveViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
