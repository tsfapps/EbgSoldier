package com.earnbygame.ebgsoldier.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.earnbygame.ebgsoldier.R;
import com.earnbygame.ebgsoldier.activity.MatchDetailActivity;
import com.earnbygame.ebgsoldier.activity.PaymentActivity;
import com.earnbygame.ebgsoldier.model.ModelJoinMatch;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JoinAdapter extends RecyclerView.Adapter<JoinAdapter.JoinViewHolder> {

    private final Context mContext;
    private List<ModelJoinMatch> tModels;

    public JoinAdapter(Context mContext, List<ModelJoinMatch> tModels) {
        this.mContext = mContext;
        this.tModels = tModels;
    }

    @NonNull
    @Override
    public JoinViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_join_item, viewGroup, false);
        return new JoinViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final JoinViewHolder joinViewHolder, int i) {

        final ModelJoinMatch tModel = tModels.get(i);
        joinViewHolder.tv_matchJoin_gameName.setText(tModel.getMatchName());
        joinViewHolder.tv_matchJoin_date.setText("Date : "+tModel.getMatchDate());
        joinViewHolder.tv_matchJoin_time.setText("Time : "+tModel.getMatchTime());
        joinViewHolder.tv_matchJoin_prizeLabel.setText(tModel.getPrizeLabel());
        joinViewHolder.tv_matchJoin_prize.setText("₹ "+tModel.getFirstPrize());
        joinViewHolder.tv_matchJoin_killLabel.setText(tModel.getKillLabel());
        joinViewHolder.tv_matchJoin_kill.setText("₹ "+tModel.getPerKillPrize());
        joinViewHolder.tv_matchJoin_entryFeeLabel.setText(tModel.getFeeLabel());
        joinViewHolder.tv_matchJoin_entryFee.setText("₹ "+tModel.getEntryFee());
        joinViewHolder.tv_matchJoin_type.setText(tModel.getMatchType());
        joinViewHolder.tv_matchJoin_version.setText(tModel.getMatchVersion());
        joinViewHolder.tv_matchJoin_map.setText(tModel.getMatchMap());
        joinViewHolder.tv_matchJoin_totalCount.setText(tModel.getTotalPlayers());
        joinViewHolder.tv_matchJoin_joinedCount.setText(tModel.getTotalJoined());
        joinViewHolder.tv_matchJoin_remainCount.setText(tModel.getRemainToJoin());

        if (tModel.getJoinedStatus().equals("1")){
            joinViewHolder.mJoinBtn.setText("Joined");
            joinViewHolder.mJoinBtn.setTextColor(mContext.getResources().getColor(R.color.white));
            joinViewHolder.mJoinBtn.setBackgroundResource(R.drawable.bg_btn_selected);
            joinViewHolder.mJoinBtn.setEnabled(false);
        } else {
            joinViewHolder.mJoinBtn.setText("Join");
            joinViewHolder.mJoinBtn.setTextColor(mContext.getResources().getColor(R.color.golden));
            joinViewHolder.mJoinBtn.setBackgroundResource(R.drawable.bg_btn_main);
            joinViewHolder.mJoinBtn.setEnabled(true);
        }


        joinViewHolder.mJoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tModel.getJoinedStatus().equals("0")) {
                    Intent i = new Intent(mContext, PaymentActivity.class);
                    i.putExtra("match_id", tModel.getMatchId());
                    i.putExtra("match_name", tModel.getMatchName());
                    i.putExtra("entry_fee", tModel.getEntryFee());
                    mContext.startActivity(i);
                }

            }
        });

        joinViewHolder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, MatchDetailActivity.class);
                i.putExtra("match_id",tModel.getMatchId());
                i.putExtra("match_name",tModel.getMatchName());
                i.putExtra("join_status",tModel.getJoinedStatus());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class JoinViewHolder extends RecyclerView.ViewHolder{
        protected Button mJoinBtn;

        @BindView(R.id.iv_matchJoin_icon)
        protected ImageView iv_matchJoin_icon;
 @BindView(R.id.tv_matchJoin_gameName)
        protected TextView tv_matchJoin_gameName;
 @BindView(R.id.tv_matchJoin_date)
        protected TextView tv_matchJoin_date;
 @BindView(R.id.tv_matchJoin_time)
        protected TextView tv_matchJoin_time;
 @BindView(R.id.tv_matchJoin_prizeLabel)
        protected TextView tv_matchJoin_prizeLabel;
 @BindView(R.id.tv_matchJoin_killLabel)
        protected TextView tv_matchJoin_killLabel;
 @BindView(R.id.tv_matchJoin_entryFeeLabel)
        protected TextView tv_matchJoin_entryFeeLabel;
 @BindView(R.id.tv_matchJoin_prize)
        protected TextView tv_matchJoin_prize;
@BindView(R.id.tv_matchJoin_kill)
        protected TextView tv_matchJoin_kill;
@BindView(R.id.tv_matchJoin_entryFee)
        protected TextView tv_matchJoin_entryFee;
@BindView(R.id.tv_matchJoin_type)
        protected TextView tv_matchJoin_type;
@BindView(R.id.tv_matchJoin_version)
        protected TextView tv_matchJoin_version;
@BindView(R.id.tv_matchJoin_map)
        protected TextView tv_matchJoin_map;
@BindView(R.id.tv_matchJoin_totalCount)
        protected TextView tv_matchJoin_totalCount;
@BindView(R.id.tv_matchJoin_joinedCount)
        protected TextView tv_matchJoin_joinedCount;
@BindView(R.id.tv_matchJoin_remainCount)
        protected TextView tv_matchJoin_remainCount;
@BindView(R.id.main_ll)
        protected LinearLayout mainLayout;

        public JoinViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mJoinBtn = itemView.findViewById(R.id.btn_join);
        }
    }
}
