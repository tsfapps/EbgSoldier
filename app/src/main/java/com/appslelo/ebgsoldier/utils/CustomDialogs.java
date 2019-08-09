package com.appslelo.ebgsoldier.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.appslelo.ebgsoldier.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CustomDialogs{

    public static void customDialogSuccess(Context tContext, String strTitle, String strMsg, String strBtnTxt) {
        final SweetAlertDialog alertDialog = new SweetAlertDialog(tContext,  SweetAlertDialog.SUCCESS_TYPE);
        alertDialog.setTitleText(strTitle);
        alertDialog.setConfirmText(strBtnTxt);
        alertDialog.setConfirmClickListener( new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                alertDialog.dismissWithAnimation();
            }
        });
        alertDialog.setContentText(strMsg);
        alertDialog.show();
        Button btn = alertDialog.findViewById(R.id.confirm_button);
        btn.setBackgroundColor(ContextCompat.getColor(tContext, R.color.colorPrimary));

    }
    public static void customDialogError(Context tContext, String strTitle, String strMsg, String strBtnTxt) {
        final SweetAlertDialog alertDialog = new SweetAlertDialog(tContext,  SweetAlertDialog.ERROR_TYPE);
        alertDialog.setTitleText(strTitle);
        alertDialog.setConfirmText(strBtnTxt);
        alertDialog.setConfirmClickListener( new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                alertDialog.dismissWithAnimation();
            }
        });
        alertDialog.setContentText(strMsg);
        alertDialog.show();
        Button btn = alertDialog.findViewById(R.id.confirm_button);
        btn.setBackgroundColor(ContextCompat.getColor(tContext, R.color.colorPrimary));

    }


    public static void showLoginCustomAlertDialog(String strPos, String strNeg, final Context tContext, String strTitle, String strMsg, int dialogType) {
        SweetAlertDialog alertDialog = new SweetAlertDialog(tContext, dialogType);
        alertDialog.setTitleText(strTitle);
        alertDialog.setCancelText(strNeg);
        alertDialog.setCancelClickListener( new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                Config.moveTo(context, LoginActivity.class);

            }
        });
        alertDialog.setConfirmText(strPos);
        alertDialog.setConfirmClickListener( new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                Config.moveTo(context, SignUp.class);

            }
        });
        if (strMsg.length() > 0)
            alertDialog.setContentText(strMsg);
        alertDialog.show();
        Button btn = (Button) alertDialog.findViewById(R.id.confirm_button);
        btn.setBackgroundColor(ContextCompat.getColor(tContext, R.color.colorPrimary));
        Button btn1 = (Button) alertDialog.findViewById(R.id.cancel_button);
        btn1.setBackgroundColor(ContextCompat.getColor(tContext, R.color.colorPrimary));

    }


}
