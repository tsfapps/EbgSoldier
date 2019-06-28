package com.earnbygame.ebgsoldier.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.earnbygame.ebgsoldier.R;

import butterknife.ButterKnife;

public class NotificationAapter extends RecyclerView.Adapter<NotificationAapter.NotificationViewHolder> {

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_notification_item, viewGroup, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder notificationViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder{

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
