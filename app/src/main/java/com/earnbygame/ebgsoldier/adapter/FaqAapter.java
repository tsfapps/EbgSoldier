package com.earnbygame.ebgsoldier.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.earnbygame.ebgsoldier.R;

import butterknife.ButterKnife;

public class FaqAapter extends RecyclerView.Adapter<FaqAapter.FaqViewHolder> {

    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_faq_item, viewGroup, false);
        return new FaqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder faqViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class FaqViewHolder extends RecyclerView.ViewHolder{

        public FaqViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
