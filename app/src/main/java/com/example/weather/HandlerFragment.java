package com.example.weather;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class HandlerFragment extends AppCompatActivity {
    private AHBottomNavigation bottomNavbar;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_fragment);
        bottomNavbar=findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem home=new AHBottomNavigationItem(R.string.home,R.drawable.home,R.color.light_blue);
        AHBottomNavigationItem search=new AHBottomNavigationItem(R.string.home,R.drawable.home,R.color.light_blue);
        AHBottomNavigationItem setting=new AHBottomNavigationItem(R.string.home,R.drawable.home,R.color.light_blue);
      bottomNavbar.addItem(home);
      bottomNavbar.addItem(search);
      bottomNavbar.addItem(setting);
      bottomNavbar.setDefaultBackgroundColor(R.color.light_blue);
      bottomNavbar.setAccentColor(Color.parseColor("#F63D2B"));
      bottomNavbar.setInactiveColor(Color.parseColor("#747474"));
      fragementTractions(new HomeFragment());
      bottomNavbar.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
          @Override
          public boolean onTabSelected(int position, boolean wasSelected) {
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
          }
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