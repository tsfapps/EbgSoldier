package com.appslelo.ebgsoldier.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.appslelo.ebgsoldier.R;
import com.appslelo.ebgsoldier.activity.DashActivity;
import com.appslelo.ebgsoldier.storage.SharedPrefManager;
import com.appslelo.ebgsoldier.utils.CustomToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentContact extends Fragment {
    private Context tContext;
    private SharedPrefManager tSharedPrefManager;

    private String strUserId;


    @BindView(R.id.et_contactUs)
    protected EditText et_contactUs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_contact, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }

    private void initFrag(){
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        strUserId = tSharedPrefManager.getUserId();

    }

    @OnClick(R.id.btn_submitContact)
    public void submitContactClicked(View view){

        String strMessage = et_contactUs.getText().toString().trim();
        if (!strMessage.equals("")) {
           //CallApi
            et_contactUs.setText("");
            tContext.startActivity(new Intent(tContext, DashActivity.class));
        }
        else {
            CustomToast.tToastTop(tContext, "Kindly write your feedback or comments...");
        }
    }
    @OnClick(R.id.tv_contactMail)
    public void mailContactClicked(View view){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","ebgsoldier@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Earn By Game");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Write Message");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
}
