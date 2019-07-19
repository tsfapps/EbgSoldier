package com.appslelo.ebgsoldier.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appslelo.ebgsoldier.model.ModelApkUrl;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.appslelo.ebgsoldier.BuildConfig;
import com.appslelo.ebgsoldier.R;
import com.appslelo.ebgsoldier.api.Api;
import com.appslelo.ebgsoldier.api.ApiClients;
import com.appslelo.ebgsoldier.fragment.FaqFragment;
import com.appslelo.ebgsoldier.fragment.MegaFragment;
import com.appslelo.ebgsoldier.fragment.NotificationFragment;
import com.appslelo.ebgsoldier.fragment.OfferFragment;
import com.appslelo.ebgsoldier.fragment.TermFragment;
import com.appslelo.ebgsoldier.fragment.FragmentContact;
import com.appslelo.ebgsoldier.fragment.ReferralFragment;
import com.appslelo.ebgsoldier.fragment.JoinFragment;
import com.appslelo.ebgsoldier.fragment.ProfileFragment;
import com.appslelo.ebgsoldier.fragment.ResultFragment;
import com.appslelo.ebgsoldier.fragment.TopPlayersFragment;
import com.appslelo.ebgsoldier.fragment.WalletFragment;
import com.appslelo.ebgsoldier.model.ModelKillEarn;
import com.appslelo.ebgsoldier.model.ModelWalletBonus;
import com.appslelo.ebgsoldier.storage.SharedPrefManager;
import com.appslelo.ebgsoldier.utils.CheckUpdate;
import com.appslelo.ebgsoldier.utils.Constant;
import com.appslelo.ebgsoldier.utils.CustomLog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager mFragmentManager;
    private SharedPrefManager tSharedPrefManager;
    private String strBonusAmount;
    private String strWalletAmount;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.tvToolbar)
    protected TextView tvToolbar;
    @BindView(R.id.navigation)
    protected BottomNavigationView navigation;
     @BindView(R.id.drawer_layout)
    protected DrawerLayout drawer;
     @BindView(R.id.nav_view)
    protected NavigationView navigationView;
     @BindView(R.id.tv_walletLabel)
    protected TextView tv_walletLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        CheckUpdate.checkUpdateApi(DashActivity.this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        mFragmentManager = getSupportFragmentManager();

        tSharedPrefManager = new SharedPrefManager(getApplicationContext());
        init();

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();



        String strUserId = tSharedPrefManager.getUserId();
        callApiWalletBonus(strUserId);
        callApiKillEarned(strUserId);
        strWalletAmount = tSharedPrefManager.getUserWallet();
        tv_walletLabel.setText("₹ "+strWalletAmount);
        strBonusAmount = tSharedPrefManager.getUserBonus();

    }

    private void initFireBase(){

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    return;
                }
//                        String userId = tSharedPrefManager.getUserId();
                String strTokenKey = task.getResult().getToken();
                CustomLog.d(Constant.TAG, "Token Key :"+strTokenKey);

//                Api api = ApiClient.getApiClients().create(Api.class);
//                Call<ModelToken> call = api.sendToken(strTokenKey);
//                call.enqueue(new Callback<ModelToken>() {
//                    @Override
//                    public void onResponse(Call<ModelToken> call, Response<ModelToken> response) {
//                        ModelToken tModelToken = response.body();
//                        CustomLog.d(Constant.TAG, "Responding"+tModelToken.getMessage());
//
//                    }
//                    @Override
//                    public void onFailure(Call<ModelToken> call, Throwable t) {
//                        CustomLog.d(Constant.TAG, "Token key Not Responding"+t);
//                    }
//                });
            }
        });
    }



    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    private void callApiWalletBonus(String strUserId){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelWalletBonus> call = api.walletBonus(strUserId);
        call.enqueue(new Callback<ModelWalletBonus>() {
            @Override
            public void onResponse(Call<ModelWalletBonus> call, Response<ModelWalletBonus> response) {
                ModelWalletBonus tModel = response.body();
                String strWallet = tModel.getWalletAmount();
                String strBonus = tModel.getTotalEarnedRefferals();
                tSharedPrefManager.clearBonus();
                tSharedPrefManager.clearWallet();
                tSharedPrefManager.setUserWallet(strWallet);
                tSharedPrefManager.setUserBonus(strBonus);
                strWalletAmount = strWallet;
                tv_walletLabel.setText("₹ "+strWalletAmount);
                strBonusAmount = strBonus;
            }

            @Override
            public void onFailure(Call<ModelWalletBonus> call, Throwable t) {

            }
        });



    }

    private void callApiKillEarned(String strUserId){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelKillEarn> call = api.killEarn(strUserId);
        call.enqueue(new Callback<ModelKillEarn>() {
            @Override
            public void onResponse(Call<ModelKillEarn> call, Response<ModelKillEarn> response) {
                ModelKillEarn tModel = response.body();
                String strTotalKill = tModel.getTotalKills();
                String strEarnedAmount = tModel.getTotalEarnedMatch();
                String strTotalMatch = tModel.getNoOfMatchPlayed();
                tSharedPrefManager.clearKill();
                tSharedPrefManager.clearUserEared();
                tSharedPrefManager.clearUserMatchPlayed();
                tSharedPrefManager.setUserKill(strTotalKill);
                tSharedPrefManager.setUserEarned(strEarnedAmount);
                tSharedPrefManager.setUserMatchPlayed(strTotalMatch);
            }

            @Override
            public void onFailure(Call<ModelKillEarn> call, Throwable t) {

            }
        });

    }

    @OnClick(R.id.tv_walletLabel)
    public void tv_walletLabelClicked(View view){
        mFragmentManager.beginTransaction().replace(R.id.frame_container,new WalletFragment()).addToBackStack(null).commit();

    }

    private void init() {
        String strUserId = tSharedPrefManager.getUserId();
        callApiWalletBonus(strUserId);
        initFireBase();

        strWalletAmount = tSharedPrefManager.getUserWallet();
        tv_walletLabel.setText("₹ "+strWalletAmount);
        strBonusAmount = tSharedPrefManager.getUserBonus();
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.nav_join);
        TextView tvNavHeader = navigationView.getHeaderView(0).findViewById(R.id.tvNavName);
        tvNavHeader.setText(tSharedPrefManager.getUserName());
        TextView tvNavPubName = navigationView.getHeaderView(0).findViewById(R.id.tvNavPubgName);
        tvNavPubName.setText(tSharedPrefManager.getPubgName());
        ImageView ivNavProPic = navigationView.getHeaderView(0).findViewById(R.id.ivNavProPic);
        Glide.with(DashActivity.this)
                .load(tSharedPrefManager.getUserProPic())
                .apply(new RequestOptions().placeholder(R.drawable.ebg_logo).error(R.drawable.ebg_logo))
                .into(ivNavProPic);



    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.nav_ref_earn:
                    mFragmentManager.beginTransaction().replace(R.id.frame_container,new ReferralFragment()).commit();
                    return true;
                case R.id.nav_live:
                    mFragmentManager.beginTransaction().replace(R.id.frame_container,new MegaFragment()).commit();
                    return true;
                case R.id.nav_join:
                    mFragmentManager.beginTransaction().replace(R.id.frame_container,new JoinFragment()).commit();
                    return true;
                case R.id.nav_result:
                    mFragmentManager.beginTransaction().replace(R.id.frame_container,new ResultFragment()).commit();
                    return true;
                case R.id.nav_profile:
                    mFragmentManager.beginTransaction().replace(R.id.frame_container,new ProfileFragment()).commit();
                    return true;

            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_exit) {
            finish();
            System.exit(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        switch (item.getItemId()){
            case R.id.nav_profile:
                mFragmentManager.beginTransaction().replace(R.id.frame_container,new ProfileFragment()).addToBackStack(null).commit();
                break;
                case R.id.nav_wallet:
                    mFragmentManager.beginTransaction().replace(R.id.frame_container,new WalletFragment()).addToBackStack(null).commit();
                break;
                case R.id.nav_top_players:
                mFragmentManager.beginTransaction().replace(R.id.frame_container, new TopPlayersFragment()).addToBackStack(null).commit();
                break;
                case R.id.nav_terms:
                mFragmentManager.beginTransaction().replace(R.id.frame_container, new TermFragment()).addToBackStack(null).commit();
                break;

                case R.id.nav_offer:
                mFragmentManager.beginTransaction().replace(R.id.frame_container, new OfferFragment()).addToBackStack(null).commit();
                break;

                case R.id.nav_contact:
                mFragmentManager.beginTransaction().replace(R.id.frame_container, new FragmentContact()).addToBackStack(null).commit();
                break;

                case R.id.nav_youtube_matches:
                    String strUrl = "https://www.youtube.com/channel/UCaDrPNIDhmB3Z8_wOhFgKkQ";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strUrl));
                    startActivity(intent);
                break;

                case R.id.nav_faq:
                mFragmentManager.beginTransaction().replace(R.id.frame_container, new FaqFragment()).addToBackStack(null).commit();
                break;

                case R.id.nav_notice:
                mFragmentManager.beginTransaction().replace(R.id.frame_container, new NotificationFragment()).addToBackStack(null).commit();
                break;

            case R.id.nav_share:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "EBG Soldier");
                    String shareMessage= "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + ""+"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
                break;

            case R.id.nav_logout:
                logoutUser();
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutUser() {
       tSharedPrefManager.clearUserData();
        Intent intent = new Intent(this,SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
