package com.example.kusitmsresult.Second;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kusitmsresult.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.ntb_horizontal)
    NavigationTabBar navTab;
    private ViewPager viewPager;
    private MainTabPagerAdapter viewPagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind (this);
        initUI ();
    }

    private void initUI() {
        viewPager = findViewById (R.id.vp_horizontal_ntb);
        viewPagerAdapter = new MainTabPagerAdapter (getSupportFragmentManager ());
        initPages ();
        viewPager.setAdapter (viewPagerAdapter);
        initNavigationBar ();
    }

    private void initPages() {
        viewPagerAdapter.addFragment (MainHome_Fragment.newInstance ());
        viewPagerAdapter.addFragment (Search_Fragment.newInstance());
        viewPagerAdapter.addFragment (Travel_Fragment.newInstance ());
        viewPagerAdapter.addFragment (Scrap_Fragment.newInstance());
        viewPagerAdapter.addFragment (MyPage_Fragment.newInstance ());
    }

    private void initNavigationBar() {

        int activeColor = getResources ().getColor (R.color.black);
        int inactiveColor = getResources ().getColor (R.color.mainColor);

        navTab.setBgColor (Color.WHITE);
        navTab.setActiveColor (activeColor);
        navTab.setInactiveColor (inactiveColor);
        navTab.setIsTitled (true);

        final ArrayList<NavigationTabBar.Model> models = new ArrayList<> ();

        Drawable iMain = new IconicsDrawable (this).icon (FontAwesome.Icon.faw_cog).sizeDp (24);
        Drawable iCog = new IconicsDrawable (this).icon (FontAwesome.Icon.faw_cog).sizeDp (24);
        String modelBgColor = "#ffffff";
        models.add (
                new NavigationTabBar.Model.Builder (
                        getResources().getDrawable(R.drawable.home)
                        ,Color.parseColor (modelBgColor))
                        .title (getString (R.string.main_nav_first))
                        .build ()
        );


        models.add (
                new NavigationTabBar.Model.Builder (
                        getResources().getDrawable(R.drawable.search),
                        Color.parseColor (modelBgColor))
                        .title (getString (R.string.main_nav_second))
                        .build ()
        );

        models.add (
                new NavigationTabBar.Model.Builder (
                        getResources().getDrawable(R.drawable.pin),
                        Color.parseColor (modelBgColor))
                        .title (getString (R.string.main_nav_third))
                        .build ()
        );

        models.add (
                new NavigationTabBar.Model.Builder (
                        getResources().getDrawable(R.drawable.bookmark),
                        Color.parseColor (modelBgColor))
                        .title (getString (R.string.main_nav_forth))
                        .build ()
        );

        models.add (
                new NavigationTabBar.Model.Builder (
                        getResources().getDrawable(R.drawable.mypage),
                        Color.parseColor (modelBgColor))
                        .title (getString (R.string.main_nav_fifth))
                        .build ()
        );

        navTab.setModels (models);
        navTab.setViewPager (viewPager, 0);

        navTab.setBehaviorEnabled (true);
        navTab.setBadgeSize (20);

        navTab.setOnTabBarSelectedIndexListener (new NavigationTabBar.OnTabBarSelectedIndexListener () {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {

            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {

            }
        });

        navTab.setOnPageChangeListener (new ViewPager.OnPageChangeListener () {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {

            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });
        navTab.bringToFront ();
    }
}