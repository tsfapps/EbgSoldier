package com.earnbygame.ebgsoldier.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.earnbygame.ebgsoldier.R;

import butterknife.ButterKnife;

public class JoinAdapter extends RecyclerView.Adapter<JoinAdapter.JoinViewHolder> {
    @NonNull
    @Override
    public JoinViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_join_item, viewGroup, false);
        return new JoinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JoinViewHolder joinViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class JoinViewHolder extends RecyclerView.ViewHolder{

        public JoinViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
