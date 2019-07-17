package com.appslelo.ebgsoldier.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appslelo.ebgsoldier.R;
import com.appslelo.ebgsoldier.model.ModelTopPlayers;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopPlayersAdapter extends RecyclerView.Adapter<TopPlayersAdapter.TopPlayersViewHolder> {

    private List<ModelTopPlayers> tModels;

    public TopPlayersAdapter(List<ModelTopPlayers> tModels) {
        this.tModels = tModels;
    }
    @NonNull
    @Override
    public TopPlayersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_top_players_item, viewGroup, false);
        return new TopPlayersViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TopPlayersViewHolder topPlayersViewHolder, int i) {

        ModelTopPlayers tModel = tModels.get(i);
        topPlayersViewHolder.tvTopPlayersName.setText(tModel.getUserName());
        topPlayersViewHolder.tvTopPlayersPubgId.setText(tModel.getPubgUserName());
        topPlayersViewHolder.tvTopPlayersEarned.setText(tModel.getTotalEarnedMatch());
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class TopPlayersViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvTopPlayersName)
        protected TextView tvTopPlayersName;
        @BindView(R.id.tvTopPlayersPubgId)
        protected TextView tvTopPlayersPubgId;
        @BindView(R.id.tvTopPlayersEarned)
        protected TextView tvTopPlayersEarned;
        public TopPlayersViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
