package com.appslelo.ebgsoldier.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appslelo.ebgsoldier.R;
import com.appslelo.ebgsoldier.model.ModelMatchUserJoined;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JoinedUserAdapter extends RecyclerView.Adapter<JoinedUserAdapter.JoinedUserViewHolder> {

    private List<ModelMatchUserJoined> tModels;
    private Context tContext;

    public JoinedUserAdapter(List<ModelMatchUserJoined> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
    }

    @NonNull
    @Override
    public JoinedUserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_match_user_joined_item, viewGroup, false);
        return new JoinedUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JoinedUserViewHolder joinedUserViewHolder, int i) {

        ModelMatchUserJoined tModel = tModels.get(i);
//        Glide.with(tContext).load(tModel.getProfilePic()).into(joinedUserViewHolder.ivJoinedUserProPic);
        joinedUserViewHolder.tvJoinedUserName.setText(tModel.getUserName());
        joinedUserViewHolder.tvJoinedPubGUserName.setText(tModel.getPubgUserName());
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class JoinedUserViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.ivJoinedUserProPic)
        protected ImageView ivJoinedUserProPic;
        @BindView(R.id.tvJoinedUserName)
        protected TextView tvJoinedUserName;
        @BindView(R.id.tvJoinedPubGUserName)
        protected TextView tvJoinedPubGUserName;

        public JoinedUserViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
