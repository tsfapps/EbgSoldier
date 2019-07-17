package com.appslelo.ebgsoldier.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appslelo.ebgsoldier.R;
import com.appslelo.ebgsoldier.model.ModelFaq;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.FaqViewHolder> {

    private List<ModelFaq> tModels;

    public FaqAdapter(List<ModelFaq> tModels) {
        this.tModels = tModels;
    }
    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_faq_item, viewGroup, false);
        return new FaqViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder faqViewHolder, int i) {

        ModelFaq tModel = tModels.get(i);
        faqViewHolder.tvFaqQuestion.setText(tModel.getQuestion());
        faqViewHolder.tvFaqAnswer.setText(tModel.getAnswer());
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class FaqViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvFaqQuestion)
        protected TextView tvFaqQuestion;
        @BindView(R.id.tvFaqAnswer)
        protected TextView tvFaqAnswer;
        public FaqViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
