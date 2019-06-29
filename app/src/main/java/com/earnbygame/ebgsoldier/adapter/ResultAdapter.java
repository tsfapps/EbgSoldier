package com.earnbygame.ebgsoldier.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import com.earnbygame.ebgsoldier.R;
import com.earnbygame.ebgsoldier.fragment.ResultDetailFragment;
import com.earnbygame.ebgsoldier.model.ModelMatchDetail;
import com.earnbygame.ebgsoldier.model.ModelMatchResult;
import com.earnbygame.ebgsoldier.activity.YouTubeActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {

    private List<ModelMatchResult> tModels;
    private Context tContext;
    private FragmentManager tFragmentManager;
    public ResultAdapter(List<ModelMatchResult> tModels, Context tContext, FragmentManager tFragmentManager) {
        this.tModels = tModels;
        this.tContext = tContext;
        this.tFragmentManager = tFragmentManager;
    }

    private final Context mContext;

    public ResultAdapter(Context tContext) {
        this.mContext = tContext;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_result_item, viewGroup, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder resultViewHolder, int i) {
        resultViewHolder.mWatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, YouTubeActivity.class);
                mContext.startActivity(i);
            }
        });

        final ModelMatchResult tModel = tModels.get(i);

        resultViewHolder.tvResultName.setText(tModel.getMatchName());
        resultViewHolder.tvResultDate.setText(tModel.getMatchDate());
        resultViewHolder.tvResultTime.setText(tModel.getMatchTime());
        resultViewHolder.tvResultFirstPrize.setText(tModel.getFirstPrize());
        resultViewHolder.tvResultPerKill.setText(tModel.getPerKillPrize());
        resultViewHolder.tvResultEntryFees.setText(tModel.getEntryFee());
        resultViewHolder.tvResultType.setText(tModel.getMatchType());
        resultViewHolder.tvResultVersion.setText(tModel.getMatchVersion());
        resultViewHolder.tvResultMap.setText(tModel.getMatchMap());

        String strStatus = tModel.getStatus();
        if (strStatus.equals("1")){
            resultViewHolder.btnResultNotJoined.setText("Played");
            resultViewHolder.btnResultNotJoined.setBackgroundResource(R.drawable.bg_btn_selected);
            resultViewHolder.btnResultNotJoined.setTextColor(Color.parseColor("#ffffff"));
        }

        resultViewHolder.llMatchResultMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tFragmentManager.beginTransaction().replace(R.id.frame_container, ResultDetailFragment.newInstance(tModel.getMatchId())).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.btn_watch)
        protected Button mWatchBtn;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
