package com.example.weather;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HandlerFragment extends AppCompatActivity {
    private BottomNavigationView bottomNavbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_fragment);
        bottomNavbar=findViewById(R.id.bottom_navigation);
    }
}