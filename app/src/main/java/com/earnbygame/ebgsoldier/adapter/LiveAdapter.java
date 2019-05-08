package com.earnbygame.ebgsoldier.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.earnbygame.ebgsoldier.R;
import com.earnbygame.ebgsoldier.activity.YouTubeActivity;

import butterknife.ButterKnife;

public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.LiveViewHolder> {

    private final Context mContext;

    public LiveAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public LiveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_live_item, viewGroup, false);
        return new LiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LiveViewHolder liveViewHolder, int i) {
        liveViewHolder.mWatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,YouTubeActivity.class);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class LiveViewHolder extends RecyclerView.ViewHolder{
        protected Button mWatchButton;

        public LiveViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mWatchButton = itemView.findViewById(R.id.watch_btn);
        }
    }
}
