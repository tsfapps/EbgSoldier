package com.appslelo.ebgsoldier.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appslelo.ebgsoldier.R;
import com.appslelo.ebgsoldier.model.ModelMatchResultDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultAdapterDetails extends RecyclerView.Adapter<ResultAdapterDetails.ResultDetailViewHolder> {

    private List<ModelMatchResultDetail> tModels;
    public ResultAdapterDetails(List<ModelMatchResultDetail> tModels) {
        this.tModels = tModels;
    }

    @NonNull
    @Override
    public ResultDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_result_detail_item, viewGroup, false);
        return new ResultDetailViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ResultDetailViewHolder resultDetailViewHolder, int i) {

        ModelMatchResultDetail tModel = tModels.get(i);

        resultDetailViewHolder.tvResultDetailRank.setText(tModel.getRank());
        resultDetailViewHolder.tvResultDetailName.setText(tModel.getUserName());
        resultDetailViewHolder.tvResultDetailKill.setText(tModel.getTotalKills());
        resultDetailViewHolder.tvResultDetailPrizes.setText(tModel.getPrizeMoney());
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class ResultDetailViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvResultDetailRank)
        protected TextView tvResultDetailRank;
        @BindView(R.id.tvResultDetailName)
        protected TextView tvResultDetailName;
        @BindView(R.id.tvResultDetailKill)
        protected TextView tvResultDetailKill;
        @BindView(R.id.tvResultDetailPrizes)
        protected TextView tvResultDetailPrizes;

        public ResultDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
