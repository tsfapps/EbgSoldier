package com.earnbygame.ebgsoldier.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.TextView;

import com.earnbygame.ebgsoldier.BuildConfig;
import com.earnbygame.ebgsoldier.R;
import com.earnbygame.ebgsoldier.fragment.FaqFragment;
import com.earnbygame.ebgsoldier.fragment.NotificationFragment;
import com.earnbygame.ebgsoldier.fragment.TermFragment;
import com.earnbygame.ebgsoldier.fragment.FragmentContact;
import com.earnbygame.ebgsoldier.fragment.ReferralFragment;
import com.earnbygame.ebgsoldier.fragment.JoinFragment;
import com.earnbygame.ebgsoldier.fragment.LiveFragment;
import com.earnbygame.ebgsoldier.fragment.ProfileFragment;
import com.earnbygame.ebgsoldier.fragment.ResultFragment;
import com.earnbygame.ebgsoldier.fragment.WalletFragment;
import com.earnbygame.ebgsoldier.model.login.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager mFragmentManager;
    private String strBonusAmount;

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
    private List<User> mUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        mFragmentManager = getSupportFragmentManager();
        init();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        mUserList = User.listAll(User.class);
        if (mUserList.size() > 0){
            tv_walletLabel.setText("₹ "+mUserList.get(0).getWalletAmount());
            strBonusAmount = mUserList.get(0).getTotalEarnedRefferals();
        }
    }

    @OnClick(R.id.tv_walletLabel)
    public void tv_walletLabelClicked(View view){
        mFragmentManager.beginTransaction().replace(R.id.frame_container,WalletFragment.newInstance(strBonusAmount)).addToBackStack(null).commit();

    }
    private void init() {
        mUserList = User.listAll(User.class);
        if (mUserList.size() > 0){
            strBonusAmount = mUserList.get(0).getTotalEarnedRefferals();
        }
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.nav_join);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.nav_ref_earn:
                    mFragmentManager.beginTransaction().replace(R.id.frame_container,new ReferralFragment()).commit();
                    return true;
                case R.id.nav_live:
                    mFragmentManager.beginTransaction().replace(R.id.frame_container,new LiveFragment()).commit();
                    return true;
                case R.id.nav_join:
                    mFragmentManager.beginTransaction().replace(R.id.frame_container,new JoinFragment()).commit();
                    return true;
                case R.id.nav_result:
                    mFragmentManager.beginTransaction().replace(R.id.frame_container,new ResultFragment()).commit();
                    return true;
                case R.id.nav_profile:
                    mFragmentManager.beginTransaction().replace(R.id.frame_container,ProfileFragment.newInstance(mUserList)).commit();
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
                mFragmentManager.beginTransaction().replace(R.id.frame_container,ProfileFragment.newInstance(mUserList)).addToBackStack(null).commit();
                break;
                case R.id.nav_wallet:
                    mFragmentManager.beginTransaction().replace(R.id.frame_container,new WalletFragment()).addToBackStack(null).commit();
                break;
                case R.id.nav_top_players:
//                mFragmentManager.beginTransaction().replace(R.id.frame_container, new TermFragment()).addToBackStack(null).commit();
                break;
                case R.id.nav_terms:
                mFragmentManager.beginTransaction().replace(R.id.frame_container, new TermFragment()).addToBackStack(null).commit();
                break;

                case R.id.nav_contact:
                mFragmentManager.beginTransaction().replace(R.id.frame_container, new FragmentContact()).addToBackStack(null).commit();
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
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
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
        User.deleteAll(User.class);
        Intent intent = new Intent(this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
