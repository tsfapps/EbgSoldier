package com.earnbygame.ebgsoldier.activity;

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
import android.widget.TextView;

import com.earnbygame.ebgsoldier.BuildConfig;
import com.earnbygame.ebgsoldier.R;
import com.earnbygame.ebgsoldier.fragment.EarnFragment;
import com.earnbygame.ebgsoldier.fragment.JoinFragment;
import com.earnbygame.ebgsoldier.fragment.LiveFragment;
import com.earnbygame.ebgsoldier.fragment.ProfileFragment;
import com.earnbygame.ebgsoldier.fragment.ResultFragment;
import com.earnbygame.ebgsoldier.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager mFragmentManager;
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
    protected TextView mWalletMoneyTV;
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

    @Override
    protected void onResume() {
        super.onResume();
        mUserList = User.listAll(User.class);
        if (mUserList.size() > 0){
            mWalletMoneyTV.setText("₹ "+String.valueOf(mUserList.get(0).getWalletAmount()));
        }
    }

    private void init() {
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.nav_join);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.nav_ref_earn:
                    mFragmentManager.beginTransaction().replace(R.id.frame_container,new EarnFragment()).commit();
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
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            mFragmentManager.beginTransaction().replace(R.id.frame_container,ProfileFragment.newInstance(mUserList)).commit();

        } else if (id == R.id.nav_wallet) {

        } else if (id == R.id.nav_top_players) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

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


        } else if (id == R.id.nav_logout) {
            logoutUser();
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
