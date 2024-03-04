package com.example.weather;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private ImageView weatherIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        weatherIcons=findViewById(R.id.weather_icon);
       zoomInAnimation();
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               startActivity(new Intent(getApplicationContext(), SignUp.class));
               finish();
           }
       },1950);
    }
    private void zoomInAnimation() {
        Animation animation = new ScaleAnimation(
                0.5f, 1.5f, // Start and end scale X
                0.5f, 1.5f, // Start and end scale Y
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point X
                Animation.RELATIVE_TO_SELF, 0.5f // Pivot point Y
        );
        animation.setDuration(2000); // Duration in milliseconds
        animation.setFillAfter(true); // Keep the result of the animation
        weatherIcons.startAnimation(animation);
    }
}