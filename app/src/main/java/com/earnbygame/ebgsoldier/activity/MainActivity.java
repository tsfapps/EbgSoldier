package com.earnbygame.ebgsoldier.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.earnbygame.ebgsoldier.R;
import com.earnbygame.ebgsoldier.fragment.EarnFragment;
import com.earnbygame.ebgsoldier.fragment.JoinFragment;
import com.earnbygame.ebgsoldier.fragment.LiveFragment;
import com.earnbygame.ebgsoldier.fragment.ProfileFragment;
import com.earnbygame.ebgsoldier.fragment.ResultFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
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
}
