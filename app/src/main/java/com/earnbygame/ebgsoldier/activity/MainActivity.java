package com.earnbygame.ebgsoldier.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.earnbygame.ebgsoldier.R;
import com.earnbygame.ebgsoldier.fragment.JoinFragment;

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
                    return true;
               case R.id.nav_live:
                    return true;
               case R.id.nav_join:
                    mFragmentManager.beginTransaction().replace(R.id.frame_container,new JoinFragment()).commit();
                    return true;
               case R.id.nav_result:
                    return true;
               case R.id.nav_profile:
                    return true;

           }
           return false;
        }
    };
}
