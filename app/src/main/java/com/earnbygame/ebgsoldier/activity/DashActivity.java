package com.earnbygame.ebgsoldier.activity;

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

import com.earnbygame.ebgsoldier.R;
import com.earnbygame.ebgsoldier.fragment.EarnFragment;
import com.earnbygame.ebgsoldier.fragment.JoinFragment;
import com.earnbygame.ebgsoldier.fragment.LiveFragment;
import com.earnbygame.ebgsoldier.fragment.ProfileFragment;
import com.earnbygame.ebgsoldier.fragment.ResultFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
            // Handle the camera action
        } else if (id == R.id.nav_wallet) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
