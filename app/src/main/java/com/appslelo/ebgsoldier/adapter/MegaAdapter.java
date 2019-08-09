package com.appslelo.ebgsoldier.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.appslelo.ebgsoldier.R;
import com.appslelo.ebgsoldier.activity.MatchDetailActivity;
import com.appslelo.ebgsoldier.activity.PaymentActivity;
import com.appslelo.ebgsoldier.model.ModelJoinMegaMatch;
import com.appslelo.ebgsoldier.utils.Constant;
import com.appslelo.ebgsoldier.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MegaAdapter extends RecyclerView.Adapter<MegaAdapter.LiveViewHolder> {

    private Context mContext;
    private List<ModelJoinMegaMatch> tModels;

    public MegaAdapter(Context mContext, List<ModelJoinMegaMatch> tModels) {
        this.mContext = mContext;
        this.tModels = tModels;
    }

    @NonNull
    @Override
    public LiveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_mega_item, viewGroup, false);
        return new LiveViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LiveViewHolder liveViewHolder, int i) {
        final ModelJoinMegaMatch tModel = tModels.get(i);
        if (tModel.getMatchImage() != null && !tModel.getMatchImage().equals("")) {
            liveViewHolder.iv_match_pic.setVisibility(View.VISIBLE);
            String strImage = Constant.IMAGE_URL +tModel.getMatchImage();
            Glide.with(mContext).load(strImage).placeholder(R.drawable.ebg_logo).into(liveViewHolder.iv_match_pic);
        } else {
            liveViewHolder.iv_match_pic.setVisibility(View.GONE);
        }


        String strRealDate = tModel.getMatchDate();
        String strCurrentTime = DateUtils.getCurrentDateTime();

        long realDate = DateUtils.dateToMiliSeconds(strRealDate);
        long currentDate = DateUtils.dateToMiliSeconds(strCurrentTime);

        String strDate = DateUtils.ddMMMMyyyy(tModel.getMatchDate());
        String strTime = DateUtils.hhmm(tModel.getMatchDate());
        int joinedNumber = Integer.parseInt(tModel.getTotalJoined());


        liveViewHolder.tv_matchJoin_gameName.setText(tModel.getMatchName());
        liveViewHolder.tv_matchJoin_date.setText("Date : "+strDate);
        liveViewHolder.tv_matchJoin_time.setText("Time : "+strTime);
        liveViewHolder.tv_matchJoin_prizeLabel.setText(tModel.getPrizeLabel());
        liveViewHolder.tv_matchJoin_prize.setText("₹ "+tModel.getFirstPrize());
        liveViewHolder.tv_matchJoin_killLabel.setText(tModel.getKillLabel());
        liveViewHolder.tv_matchJoin_kill.setText("₹ "+tModel.getPerKillPrize());
        liveViewHolder.tv_matchJoin_entryFeeLabel.setText(tModel.getFeeLabel());
        liveViewHolder.tv_matchJoin_entryFee.setText("₹ "+tModel.getEntryFee());
        liveViewHolder.tv_matchJoin_type.setText(tModel.getMatchType());
        liveViewHolder.tv_matchJoin_version.setText(tModel.getMatchVersion());
        liveViewHolder.tv_matchJoin_map.setText(tModel.getMatchMap());
        liveViewHolder.tv_matchJoin_totalCount.setText(tModel.getTotalPlayers());
        liveViewHolder.tv_matchJoin_joinedCount.setText(tModel.getTotalJoined());
        liveViewHolder.tv_matchJoin_remainCount.setText(tModel.getRemainToJoin());

        if (tModel.getJoinedStatus().equals("1")){
            liveViewHolder.btnMegaJoin.setText("Joined");
            liveViewHolder.btnMegaJoin.setTextColor(mContext.getResources().getColor(R.color.black));
            liveViewHolder.btnMegaJoin.setBackgroundResource(R.drawable.bg_btn_selected);
            liveViewHolder.btnMegaJoin.setEnabled(false);
        } else {
            liveViewHolder.btnMegaJoin.setText("Join");
            liveViewHolder.btnMegaJoin.setTextColor(mContext.getResources().getColor(R.color.white));
            liveViewHolder.btnMegaJoin.setEnabled(true);
        }

        if (joinedNumber>=100){
            liveViewHolder.btnMegaJoin.setText("Match Full");
            liveViewHolder.btnMegaJoin.setTextColor(mContext.getResources().getColor(R.color.black));
            liveViewHolder.btnMegaJoin.setBackgroundResource(R.drawable.bg_btn_selected);
            liveViewHolder.btnMegaJoin.setEnabled(false);

        }
        if (currentDate>=realDate){
            liveViewHolder.btnMegaJoin.setText("Match Started");
            liveViewHolder.btnMegaJoin.setTextColor(mContext.getResources().getColor(R.color.black));
            liveViewHolder.btnMegaJoin.setBackgroundResource(R.drawable.bg_btn_selected);
            liveViewHolder.btnMegaJoin.setEnabled(false);

        }

        liveViewHolder.btnMegaJoin.setOnClickListener(new View.OnClickListener() {
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
        liveViewHolder.mainLayout.setOnClickListener(new View.OnClickListener() {
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

    public class LiveViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.btn_mega_join)
        protected Button btnMegaJoin;



        @BindView(R.id.iv_mega_matchJoin_icon)
        protected ImageView iv_matchJoin_icon;
        @BindView(R.id.tv_mega_matchJoin_gameName)
        protected TextView tv_matchJoin_gameName;
        @BindView(R.id.tv_mega_matchJoin_date)
        protected TextView tv_matchJoin_date;
        @BindView(R.id.tv_mega_matchJoin_time)
        protected TextView tv_matchJoin_time;
        @BindView(R.id.tv_mega_matchJoin_prizeLabel)
        protected TextView tv_matchJoin_prizeLabel;
        @BindView(R.id.tv_mega_matchJoin_killLabel)
        protected TextView tv_matchJoin_killLabel;
        @BindView(R.id.tv_mega_matchJoin_entryFeeLabel)
        protected TextView tv_matchJoin_entryFeeLabel;
        @BindView(R.id.tv_mega_matchJoin_prize)
        protected TextView tv_matchJoin_prize;
        @BindView(R.id.tv_mega_matchJoin_kill)
        protected TextView tv_matchJoin_kill;
        @BindView(R.id.tv_mega_matchJoin_entryFee)
        protected TextView tv_matchJoin_entryFee;
        @BindView(R.id.tv_mega_matchJoin_type)
        protected TextView tv_matchJoin_type;
        @BindView(R.id.tv_mega_matchJoin_version)
        protected TextView tv_matchJoin_version;
        @BindView(R.id.tv_mega_matchJoin_map)
        protected TextView tv_matchJoin_map;
        @BindView(R.id.tv_mega_matchJoin_totalCount)
        protected TextView tv_matchJoin_totalCount;
        @BindView(R.id.tv_mega_matchJoin_joinedCount)
        protected TextView tv_matchJoin_joinedCount;
        @BindView(R.id.tv_mega_matchJoin_remainCount)
        protected TextView tv_matchJoin_remainCount;
        @BindView(R.id.main_ll)
        protected LinearLayout mainLayout;
        @BindView(R.id.iv_mega_match_pic)
        protected ImageView iv_match_pic;

        public LiveViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
