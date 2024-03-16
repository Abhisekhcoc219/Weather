package com.example.weather;

import static androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
   private EditText forgetEmail;
   private AppCompatButton recoverySubmitBtn;
    private  Animation shakeAnim;
   private SignUpActivity signUpActivityInstance;
   private FirebaseAuth mAuth;
   private LottieAnimationView loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_forget_password);
        initView();
        loading.setVisibility(View.GONE);
        shakeAnim= AnimationUtils.loadAnimation(this,R.anim.shake);
        recoverySubmitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email=forgetEmail.getText().toString();
                if(!email.isEmpty()){
                    if(EmailVaild.isValidEmail(email)){
                       loading.setVisibility(View.VISIBLE);
                       new Handler().postDelayed(new Runnable() {
                           @Override
                           public void run() {
                            restartPassword(email);
                           }
                       },2500);

                    }
                    else{
                        signUpActivityInstance.shakeAnimation(forgetEmail,shakeAnim,"email is not vaild",getApplicationContext());
                    }
                }
                else{
                    signUpActivityInstance.shakeAnimation(forgetEmail,shakeAnim,"email is blank",getApplicationContext());
                }
            }
        });
    }
    private void initView(){
        forgetEmail=findViewById(R.id.forgetPasswordEmail);
        recoverySubmitBtn=findViewById(R.id.forgetSubmitBtn);
        signUpActivityInstance=new SignUpActivity();
        mAuth=FirebaseAuth.getInstance();
        loading=findViewById(R.id.loads);
    }
    private void restartPassword(String email){
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                     if(task.isSuccessful()){
                         Toast.makeText(getApplicationContext(), "sent to your mail", Toast.LENGTH_SHORT).show();
                         startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                         loading.setVisibility(View.GONE);
                         finish();
                     }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("forgetFail",e.getLocalizedMessage());
                        loading.setVisibility(View.GONE);
                    }
                });
    }
}