package com.example.weather;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;

public class SignUp extends AppCompatActivity {
  private LottieAnimationView loading;
  TextView Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        loading=findViewById(R.id.lottieAnimationView);
        loading.playAnimation();
        Login=findViewById(R.id.login);
        Login.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SignIn.class));
        });
    }
}