package com.appslelo.ebgsoldier.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.appslelo.ebgsoldier.R;
import com.appslelo.ebgsoldier.storage.SharedPrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    protected TextView mPubGIdTv;
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
    @BindView(R.id.tv_profile_dob)
    protected TextView mDOBTv;
    @BindView(R.id.tv_profile_name)
    protected TextView mUserNameTv;
    @BindView(R.id.iv_profile_pic)
    protected ImageView mProfilePicIv;
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
            mPubGIdTv.setText(tSharedPrefManager.getPubgName());
            mUserTypeTV.setText("Player");
            mPhoneNoTv.setText(tSharedPrefManager.getUserPhone());
            mUserIdTv.setText(tSharedPrefManager.getUserId());
            mEmailTv.setText(tSharedPrefManager.getUserEmail());
            mDOBTv.setText(tSharedPrefManager.getUserDob());
            mJoinDateTv.setText(tSharedPrefManager.getUserDoj());
            mUserNameTv.setText(tSharedPrefManager.getUserName());

            Glide.with(this)
                    .load(tSharedPrefManager.getUserProPic())
                    .apply(new RequestOptions().placeholder(R.drawable.ebg_logo).error(R.drawable.ebg_logo))
                    .into(mProfilePicIv);


    }
}
