package com.earnbygame.ebgsoldier.fragment;

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
import com.earnbygame.ebgsoldier.R;
import com.earnbygame.ebgsoldier.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {

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
    private List<User> mUserList;
    private View mView;

    public static ProfileFragment newInstance(List<User> list) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.mUserList = list;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.frag_profile, container, false);
        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    private void init() {
       // mUserList = User.listAll(User.class);
        if (mUserList.size() > 0) {
            mMatchPlayedTv.setText(String.valueOf(mUserList.get(0).getNoOfMatchPlayed()));
            mTotalKillTv.setText(String.valueOf(mUserList.get(0).getTotalKills()));
            mTotalEarnTv.setText("₹ "+String.valueOf(mUserList.get(0).getTotalEarnedRefferals()));
            mPubGIdTv.setText(mUserList.get(0).getPubgUserName());
            mUserTypeTV.setText("Player");
            mPhoneNoTv.setText(mUserList.get(0).getPhoneNo());
            mUserIdTv.setText(mUserList.get(0).getUserId());
            mEmailTv.setText(mUserList.get(0).getEmail());
            mDOBTv.setText(String.valueOf(mUserList.get(0).getDob()));
            mJoinDateTv.setText(String.valueOf(mUserList.get(0).getDoj()));
            mUserNameTv.setText(mUserList.get(0).getUserName());
            Glide.with(this).load(mUserList.get(0).getProfilePic()).placeholder(R.drawable.ebg_logo).into(mProfilePicIv);
        }

    }
}
