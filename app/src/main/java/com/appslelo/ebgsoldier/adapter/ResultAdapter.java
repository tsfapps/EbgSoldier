package com.appslelo.ebgsoldier.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appslelo.ebgsoldier.R;
import com.appslelo.ebgsoldier.fragment.ResultDetailFragment;
import com.appslelo.ebgsoldier.model.ModelMatchResult;
import com.appslelo.ebgsoldier.utils.Constant;

import java.util.List;

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
    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_result_item, viewGroup, false);
        return new ResultViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder resultViewHolder, final int i) {
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
        if (strStatus.equalsIgnoreCase("1"))
        {
            resultViewHolder.btnResultNotJoined.setText("Played");
            resultViewHolder.btnResultNotJoined.setBackgroundResource(R.drawable.bg_simple_transparent);
        }
        resultViewHolder.llMatchResultMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tFragmentManager.beginTransaction().replace(R.id.frame_container, ResultDetailFragment.newInstance(tModel)).addToBackStack(null).commit();
            }
        });

        resultViewHolder.btnResultWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUrl = Constant.YOUTUBE_URL+tModel.getYoutube_link();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strUrl));
                tContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.llMatchResultMain)
        protected LinearLayout llMatchResultMain;
        @BindView(R.id.tvResultName)
        protected TextView tvResultName;
        @BindView(R.id.tvResultDate)
        protected TextView tvResultDate;
        @BindView(R.id.tvResultTime)
        protected TextView tvResultTime;
        @BindView(R.id.tvResultFirstPrize)
        protected TextView tvResultFirstPrize;
        @BindView(R.id.tvResultPerKill)
        protected TextView tvResultPerKill;
        @BindView(R.id.tvResultEntryFees)
        protected TextView tvResultEntryFees;
        @BindView(R.id.tvResultType)
        protected TextView tvResultType;
        @BindView(R.id.tvResultVersion)
        protected TextView tvResultVersion;
        @BindView(R.id.tvResultMap)
        protected TextView tvResultMap;
        @BindView(R.id.btnResultNotJoined)
        protected Button btnResultNotJoined;
        @BindView(R.id.btnResultWatch)
        protected Button btnResultWatch;
        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
