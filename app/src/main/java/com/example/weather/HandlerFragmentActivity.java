package com.example.weather;

import static androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class HandlerFragmentActivity extends AppCompatActivity {
    private AHBottomNavigation bottomNavbar;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_handler_fragment);
        bottomNavbar=findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem home=new AHBottomNavigationItem(R.string.home,R.drawable.home,R.color.light_blue);
        AHBottomNavigationItem search=new AHBottomNavigationItem(R.string.search,R.drawable.search,R.color.light_blue);
        AHBottomNavigationItem setting=new AHBottomNavigationItem(R.string.setting,R.drawable.settings,R.color.light_blue);
      bottomNavbar.addItem(home);
      bottomNavbar.addItem(search);
      bottomNavbar.addItem(setting);
      bottomNavbar.setDefaultBackgroundColor(Color.parseColor("#659BFB"));
      bottomNavbar.setAccentColor(Color.parseColor("#0059E7"));
      bottomNavbar.setInactiveColor(Color.parseColor("#0062FF"));
        fragementTractions(new HomeFragment());
      bottomNavbar.setOnTabSelectedListener((position, wasSelected) -> {
          switch (position){
              case 0:
                  fragementTractions(new HomeFragment());
                  break;
              case 1:
                  fragementTractions(new SearchFragment());
                  break;
              case 2:
                  fragementTractions(new SettingsFragment());
                  break;
          }
          return true;
      });

    }
    private void fragementTractions(Fragment fragment){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.mainFragment,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}