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
import com.earnbygame.ebgsoldier.activity.PaymentActivity;

import butterknife.ButterKnife;

public class JoinAdapter extends RecyclerView.Adapter<JoinAdapter.JoinViewHolder> {

    private final Context mContext;

    public JoinAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public JoinViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_join_item, viewGroup, false);
        return new JoinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final JoinViewHolder joinViewHolder, int i) {
        joinViewHolder.mJoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, PaymentActivity.class);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class JoinViewHolder extends RecyclerView.ViewHolder{
        protected Button mJoinBtn;

        public JoinViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mJoinBtn = itemView.findViewById(R.id.btn_join);
        }
    }
}
