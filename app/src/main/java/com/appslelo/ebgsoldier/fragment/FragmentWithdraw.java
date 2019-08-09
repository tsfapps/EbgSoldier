package com.appslelo.ebgsoldier.fragment;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.appslelo.ebgsoldier.R;
import com.appslelo.ebgsoldier.activity.DashActivity;
import com.appslelo.ebgsoldier.api.Api;
import com.appslelo.ebgsoldier.api.ApiClients;
import com.appslelo.ebgsoldier.model.ModelWithdraw;
import com.appslelo.ebgsoldier.storage.SharedPrefManager;
import com.appslelo.ebgsoldier.utils.Constant;
import com.appslelo.ebgsoldier.utils.CustomDialogs;
import com.appslelo.ebgsoldier.utils.CustomLog;
import com.appslelo.ebgsoldier.utils.CustomToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentWithdraw extends Fragment {

    private FragmentManager tFragmentManager;
    private SharedPrefManager tSharedPrefManager;
    private Context tContext;
    private String strUserId;
    private String strMobile;
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
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        tFragmentManager = getFragmentManager();
        strUserId = tSharedPrefManager.getUserId();
        strMobile = tSharedPrefManager.getMobile();
        strWalletAmount = tSharedPrefManager.getUserWallet();
        withdrawPayTm.setText(strMobile);
        withdrawGooglePay.setText(strMobile);
    }

    private void callApi(String strAmount){

        String strAccount = withdrawAcountNumber.getText().toString().trim();
        String strIfsc = withdrawIfsc.getText().toString().trim();
        String strUpiId = withdrawUpiId.getText().toString().trim();
        String strPaytm = withdrawPayTm.getText().toString().trim();
        String strGooglePay = withdrawGooglePay.getText().toString().trim();

        if (strUpiId.equalsIgnoreCase("")&&strGooglePay.equalsIgnoreCase("")&&strPaytm.equalsIgnoreCase("")){
            CustomDialogs.customDialogError(tContext, "Give at least one of payment mode",
                    "UPI Id, Google Pay, or Paytm Number. If you don't have any of this then mail us on ebgsoldier@gmail.com to withdraw the amount", "Okay");
        }
        else {
            Api api = ApiClients.getApiClients().create(Api.class);
            Call<ModelWithdraw> call = api.widthdraws(strUserId,strMobile, strAmount, strAccount, strIfsc, strUpiId, strPaytm, strGooglePay);
            call.enqueue(new Callback<ModelWithdraw>() {
                @Override
                public void onResponse(Call<ModelWithdraw> call, Response<ModelWithdraw> response) {
                    ModelWithdraw tModel = response.body();
                    CustomToast.tToastTop(tContext, tModel.getMessage());
                    tSharedPrefManager.setUserWallet(tModel.getWallet().toString());

                    final SweetAlertDialog alertDialog = new SweetAlertDialog(tContext,  SweetAlertDialog.SUCCESS_TYPE);
                    alertDialog.setTitleText("Success");
                    alertDialog.setConfirmText("Thanks");
                    alertDialog.setConfirmClickListener( new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            alertDialog.dismissWithAnimation();
                            startActivity(new Intent(tContext, DashActivity.class));
                            getActivity().finishAffinity();
                        }
                    });
                    alertDialog.setContentText("Your request submitted successfully. It will take 24 hrs to update your account.");
                    alertDialog.show();
                    Button btn = alertDialog.findViewById(R.id.confirm_button);
                    btn.setBackgroundColor(ContextCompat.getColor(tContext, R.color.colorPrimary));
                    alertDialog.setCancelable(false);

                }

                @Override
                public void onFailure(Call<ModelWithdraw> call, Throwable t) {

                    CustomLog.d(Constant.TAG, "Withdraw Failure : " + t);
                }
            });
        }
    }

    @OnClick(R.id.btnWithdrawSubmit)
    public void btnWithdrawSubmitClicked(View view){
        final String strAmount = withdrawAmount.getText().toString().trim();
        if (!strAmount.equalsIgnoreCase("")) {
            int intAmount = Integer.parseInt(strAmount);
            int intWalletAmount = Integer.parseInt(strWalletAmount);
            if (intAmount >= intWalletAmount) {
                withdrawAmount.setError("Request amount must be lesser than wallet amount");
                return;
            }
            if (intWalletAmount <= 40) {
                withdrawAmount.setError("Wallet amount must be greater than ₹40");
                return;
            }
            final SweetAlertDialog alertDialog = new SweetAlertDialog(tContext, SweetAlertDialog.WARNING_TYPE);
            alertDialog.setTitleText("Confirm to Submit");
            alertDialog.setCancelText("Cancel");
            alertDialog.setCancelClickListener( new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    alertDialog.dismissWithAnimation();

                }
            });
            alertDialog.setConfirmText("Submit");
            alertDialog.setConfirmClickListener( new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    alertDialog.dismissWithAnimation();
                    callApi(strAmount);
                }
            });

                alertDialog.setContentText("Be sure about the detail you are providing are correct. Once submitted can't be undone.");
            alertDialog.show();
            Button btn =  alertDialog.findViewById(R.id.confirm_button);
            btn.setBackgroundColor(ContextCompat.getColor(tContext, R.color.colorPrimary));
            Button btn1 =  alertDialog.findViewById(R.id.cancel_button);
            btn1.setBackgroundColor(ContextCompat.getColor(tContext, R.color.colorPrimary));
            alertDialog.setCancelable(false);

        }
        else {
            withdrawAmount.setError("Enter the withdraw amount ... ");
        }
    }
}
