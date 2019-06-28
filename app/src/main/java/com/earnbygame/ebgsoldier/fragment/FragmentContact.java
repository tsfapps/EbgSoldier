package com.earnbygame.ebgsoldier.fragment;

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


import com.earnbygame.ebgsoldier.R;
import com.earnbygame.ebgsoldier.activity.DashActivity;
import com.earnbygame.ebgsoldier.model.login.User;
import com.earnbygame.ebgsoldier.utils.CustomToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentContact extends Fragment {
    private Context tContext;
    private List<User> mUserList;
    private String mUserId;


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
        mUserList = User.listAll(User.class);
        if (mUserList.size() > 0){
            mUserId = mUserList.get(0).getUserId();
        }
        tContext = getContext();
//        tSharedPrefManager = new SharedPrefManager(tContext);

    }

    @OnClick(R.id.btn_submitContact)
    public void submitContactClicked(View view){

        String strMessage = et_contactUs.getText().toString().trim();
        if (!strMessage.equals("")) {
           //CallApi
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
