package com.earnbygame.ebgsoldier.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.earnbygame.ebgsoldier.R;
import com.earnbygame.ebgsoldier.api.Api;
import com.earnbygame.ebgsoldier.api.ApiClients;
import com.earnbygame.ebgsoldier.model.ModelWithdraw;
import com.earnbygame.ebgsoldier.model.login.User;
import com.earnbygame.ebgsoldier.utils.Constant;
import com.earnbygame.ebgsoldier.utils.CustomLog;
import com.earnbygame.ebgsoldier.utils.CustomToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentWithdraw extends Fragment {

    private FragmentManager tFragmentManager;
    private List<User> tUser;
    private Context tContext;
    private String strUserId;
    private String strWalletAmount;
    @BindView(R.id.withdrawAmount)
    protected EditText withdrawAmount;
    @BindView(R.id.withdrawAcountNumber)
    protected EditText withdrawAcountNumber;
    @BindView(R.id.withdrawIfsc)
    protected EditText withdrawIfsc;
    @BindView(R.id.withdrawUpiId)
    protected EditText withdrawUpiId;
    @BindView(R.id.withdrawPayTm)
    protected EditText withdrawPayTm;
    @BindView(R.id.withdrawGooglePay)
    protected EditText withdrawGooglePay;
    @BindView(R.id.btnWithdrawSubmit)
    protected Button btnWithdrawSubmit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_withdraw, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }

    private void initFrag(){
        tUser = User.listAll(User.class);
        if (tUser.size() > 0){
            strUserId = tUser.get(0).getUserId();
            strWalletAmount = tUser.get(0).getWalletAmount();
        }
        tContext = getContext();
        tFragmentManager = getFragmentManager();
    }

    private void callApi(String strAmount){

        String strAccount = withdrawAcountNumber.getText().toString().trim();
        String strIfsc = withdrawIfsc.getText().toString().trim();
        String strUpiId = withdrawUpiId.getText().toString().trim();
        String strPaytm = withdrawPayTm.getText().toString().trim();
        String strGooglePay = withdrawGooglePay.getText().toString().trim();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelWithdraw> call = api.widthdraws(strUserId,strAmount,strAccount,strIfsc,strUpiId,strPaytm,strGooglePay);
        call.enqueue(new Callback<ModelWithdraw>() {
            @Override
            public void onResponse(Call<ModelWithdraw> call, Response<ModelWithdraw> response) {
                ModelWithdraw tModel = response.body();
                CustomToast.tToastTop(tContext, tModel.getMessage());
                tFragmentManager.beginTransaction().replace(R.id.frame_container, new FragmentWithdraw()).commit();
            }

            @Override
            public void onFailure(Call<ModelWithdraw> call, Throwable t) {

                CustomLog.d(Constant.TAG, "Withdraw Failure : "+t);
            }
        });
    }

    @OnClick(R.id.btnWithdrawSubmit)
    public void btnWithdrawSubmitClicked(View view){
        String strAmount = withdrawAmount.getText().toString().trim();
        int intAmount = Integer.parseInt(strAmount);
        int intWalletAmount = Integer.parseInt(strWalletAmount);
        if (intAmount>=intWalletAmount){
            withdrawAmount.setError("Request amount must be lesser than wallet amount");
            return;
        } if(intWalletAmount <= 40){
            withdrawAmount.setError("Wallet amount must be greater than ₹40");
            return;
        }
            callApi(strAmount);

    }
}
