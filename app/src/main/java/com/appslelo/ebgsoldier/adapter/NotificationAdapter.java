package com.appslelo.ebgsoldier.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appslelo.ebgsoldier.R;
import com.appslelo.ebgsoldier.model.ModelNotification;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<ModelNotification> tModels;
    public NotificationAdapter(List<ModelNotification> tModels) {
        this.tModels = tModels;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_notification_item, viewGroup, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder notificationViewHolder, int i) {

        ModelNotification tModel = tModels.get(i);
        notificationViewHolder.tvNotificationHeading.setText(tModel.getNotHeading());
        notificationViewHolder.tvNotificationContent.setText(tModel.getNotContent());
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder{
@BindView(R.id.tvNotificationHeading)
protected TextView tvNotificationHeading;
@BindView(R.id.tvNotificationContent)
protected TextView tvNotificationContent;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
