package com.example.kusitmsresult.Second;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.kusitmsresult.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private MainHome_Fragment fragment1 = new MainHome_Fragment();
    private Travel_Fragment fragment2 = new Travel_Fragment();
    private Search_Fragment fragment3 = new Search_Fragment();
    private Scrap_Fragment fragment4 = new Scrap_Fragment();
    private MyPage_Fragment fragment5 = new MyPage_Fragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment1).commitAllowingStateLoss();
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListenter());

    }



    private class ItemSelectedListenter implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_one:
                    transaction.replace(R.id.frameLayout, fragment1).commitAllowingStateLoss();
                    break;
                case R.id.navigation_two:
                    transaction.replace(R.id.frameLayout, fragment2).commitAllowingStateLoss();
                    break;
                case R.id.navigation_three:
                    transaction.replace(R.id.frameLayout, fragment3).commitAllowingStateLoss();
                    break;
                case R.id.navigation_four:
                    transaction.replace(R.id.frameLayout, fragment4).commitAllowingStateLoss();
                    break;
                case R.id.navigation_five:
                    transaction.replace(R.id.frameLayout, fragment5).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }





}