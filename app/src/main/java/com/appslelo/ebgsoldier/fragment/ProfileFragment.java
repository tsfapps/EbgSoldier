package com.appslelo.ebgsoldier.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appslelo.ebgsoldier.api.Api;
import com.appslelo.ebgsoldier.api.ApiClients;
import com.appslelo.ebgsoldier.model.ModelUpdateUserName;
import com.appslelo.ebgsoldier.utils.CustomToast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.appslelo.ebgsoldier.R;
import com.appslelo.ebgsoldier.storage.SharedPrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private SharedPrefManager tSharedPrefManager;
    private Context tContext;


    @BindView(R.id.tv_match_played)
    protected TextView mMatchPlayedTv;
    @BindView(R.id.tv_total_kill)
    protected TextView mTotalKillTv;
    @BindView(R.id.tv_total_earn)
    protected TextView mTotalEarnTv;
    @BindView(R.id.tv_pub_g_id)
    protected TextView tvPubId;
    @BindView(R.id.tv_profile_user_type)
    protected TextView mUserTypeTV;
    @BindView(R.id.tv_profile_phone)
    protected TextView mPhoneNoTv;
    @BindView(R.id.tv_profile_user_id)
    protected TextView mUserIdTv;
    @BindView(R.id.tv_profile_email)
    protected TextView mEmailTv;
    @BindView(R.id.tv_profile_join_date)
    protected TextView mJoinDateTv;
    @BindView(R.id.tv_profile_name)
    protected TextView mUserNameTv;
    @BindView(R.id.iv_profile_pic)
    protected ImageView mProfilePicIv;
    @BindView(R.id.etUpdateUserName)
    protected TextView etUpdateUserName;
    @BindView(R.id.btnUpdateUserName)
    protected Button btnUpdateUserName;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.frag_profile, container, false);
        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    private void init() {

        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);

            mMatchPlayedTv.setText(String.valueOf(tSharedPrefManager.getUserNoOfMatch()));
            mTotalKillTv.setText(String.valueOf(tSharedPrefManager.getUserTotalKill()));
            mTotalEarnTv.setText("₹ "+String.valueOf(tSharedPrefManager.getUserTotalEarned()));
            tvPubId.setText(tSharedPrefManager.getPubgName());
            mUserTypeTV.setText("Player");
            mPhoneNoTv.setText(tSharedPrefManager.getMobile());
            mUserIdTv.setText(tSharedPrefManager.getUserId());
            mEmailTv.setText(tSharedPrefManager.getUserEmail());
            mJoinDateTv.setText(tSharedPrefManager.getUserDoj());
            mUserNameTv.setText(tSharedPrefManager.getUserName());

            Glide.with(this)
                    .load(tSharedPrefManager.getUserProPic())
                    .apply(new RequestOptions().placeholder(R.drawable.ebg_logo).error(R.drawable.ebg_logo))
                    .into(mProfilePicIv);


    }
    @OnClick(R.id.tvUpdateUserName)
    public void tvUpdateUserNameClicked(View view){
        etUpdateUserName.setVisibility(View.VISIBLE);
        btnUpdateUserName.setVisibility(View.VISIBLE);
        tvPubId.setVisibility(View.GONE);
    }

    private void updateApi(final String strUserName){

        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelUpdateUserName> call = api.updateUserName(strUserName, tSharedPrefManager.getUserId());
        call.enqueue(new Callback<ModelUpdateUserName>() {
            @Override
            public void onResponse(Call<ModelUpdateUserName> call, Response<ModelUpdateUserName> response) {
                ModelUpdateUserName tModel = response.body();
                if (!tModel.getError()) {
                    etUpdateUserName.setVisibility(View.GONE);
                    btnUpdateUserName.setVisibility(View.GONE);
                    tvPubId.setVisibility(View.VISIBLE);
                    tSharedPrefManager.clearPubgName();
                    tSharedPrefManager.setPubgName(strUserName);
                    // Reload current fragment
                    Fragment currentFragment =getFragmentManager().findFragmentById(R.id.frame_container);
                    if (currentFragment instanceof ProfileFragment) {
                        FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
                        fragTransaction.detach(currentFragment);
                        fragTransaction.attach(currentFragment);
                        fragTransaction.commit();
                }
                    CustomToast.tToastTop(tContext, tModel.getMessage());
                }else {
                    CustomToast.tToastTop(tContext, tModel.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ModelUpdateUserName> call, Throwable t) {

            }
        });
    }
    @OnClick(R.id.btnUpdateUserName)
    public void btnUpdateUserNameClicked(View view){
        String strNewUserName = etUpdateUserName.getText().toString().trim();
        if (!strNewUserName.equalsIgnoreCase("")) {
            updateApi(strNewUserName);
        }else {
            etUpdateUserName.setError("Please enter your pubg user name...");
        }
    }
}
