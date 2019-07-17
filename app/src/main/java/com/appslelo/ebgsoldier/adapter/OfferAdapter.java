package com.appslelo.ebgsoldier.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.appslelo.ebgsoldier.R;
import com.appslelo.ebgsoldier.activity.DashActivity;
import com.appslelo.ebgsoldier.api.Api;
import com.appslelo.ebgsoldier.api.ApiClients;
import com.appslelo.ebgsoldier.model.ModelOffer;
import com.appslelo.ebgsoldier.model.ModelOfferRedeem;
import com.appslelo.ebgsoldier.utils.Constant;
import com.appslelo.ebgsoldier.utils.CustomLog;
import com.appslelo.ebgsoldier.utils.CustomToast;
import com.appslelo.ebgsoldier.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder> {

    private List<ModelOffer> tModels;
    private Context tContext;
    private String strUserId;

    public OfferAdapter(List<ModelOffer> tModels, Context tContext, String strUserId) {
        this.tModels = tModels;
        this.tContext = tContext;
        this.strUserId = strUserId;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_offer_item, viewGroup, false);
        return new OfferViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final OfferViewHolder offerViewHolder, int i) {

        ModelOffer tModel = tModels.get(i);
        String strDate = DateUtils.ddMMMMyyyy(tModel.getDateTime());
        final String strOfferAmount = tModel.getOfferAmnt();
        final String strOfferId = tModel.getId();
        String strStatus = tModel.getStatus();
        if (strStatus.equalsIgnoreCase("1")){
            offerViewHolder.btnOfferRedeem.setBackgroundResource(R.drawable.bg_btn_selected);
            offerViewHolder.btnOfferRedeem.setEnabled(false);
            offerViewHolder.btnOfferRedeem.setText("Redeemed");
        }
      offerViewHolder.tvOfferDate.setText(strDate);
      offerViewHolder.tvOfferContent.setText(tModel.getOffer());
      offerViewHolder.tvOfferAmount.setText("₹"+strOfferAmount);
      offerViewHolder.btnOfferRedeem.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              Api api = ApiClients.getApiClients().create(Api.class);
              Call<ModelOfferRedeem> call = api.offerRedeem(strUserId, strOfferAmount, strOfferId);
              call.enqueue(new Callback<ModelOfferRedeem>() {
                  @Override
                  public void onResponse(Call<ModelOfferRedeem> call, Response<ModelOfferRedeem> response) {
                      ModelOfferRedeem tModel = response.body();
                      if (!tModel.getError()){
                          offerViewHolder.btnOfferRedeem.setEnabled(false);
                          offerViewHolder.btnOfferRedeem.setBackgroundResource(R.drawable.bg_btn_selected);
                          offerViewHolder.btnOfferRedeem.setText("Redeemed");
                          CustomToast.tToastTop(tContext, tModel.getMessage());
                          tContext.startActivity(new Intent(tContext, DashActivity.class));
                      }else {
                          CustomToast.tToastTop(tContext, tModel.getMessage());

                      }
                  }

                  @Override
                  public void onFailure(Call<ModelOfferRedeem> call, Throwable t) {

                      CustomLog.e(Constant.TAG, "Redeem Error : "+t);
                  }
              });
          }
      });
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder{
@BindView(R.id.tvOfferDate)
protected TextView tvOfferDate;
@BindView(R.id.tvOfferContent)
protected TextView tvOfferContent;
@BindView(R.id.tvOfferAmount)
protected TextView tvOfferAmount;
@BindView(R.id.btnOfferRedeem)
protected Button btnOfferRedeem;
        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



}
