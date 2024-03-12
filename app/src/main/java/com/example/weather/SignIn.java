package com.example.weather;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {
    private EditText signInEmail,signInPassword;
    private LottieAnimationView lottieAnimationView2;
    private AppCompatButton signInBtn;
    private TextView forget,SignIn;
    private String email,password;
    private FirebaseAuth mAuth;

    private Animation shakeAnim;
    private SignUp s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        InitV();
        lottieAnimationView2.setVisibility(View.GONE);
        shakeAnim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=signInEmail.getText().toString();
                password=signInPassword.getText().toString();
                if(!email.isEmpty()){
                    if(!password.isEmpty()){
                      if(EmailVaild.isValidEmail(email)){
                          signInUser(email,password);
                      }
                      else{
                          shakeAnim(signInEmail,"email not vaild");
                      }
                    }
                    else{
                        shakeAnim(signInPassword,"password blank");
                    }
                }
                else{
                    shakeAnim(signInEmail,"email blank");
                }
            }
        });
    }
    private void InitV(){
        signInEmail=findViewById(R.id.signInEmail);
        signInPassword=findViewById(R.id.passwordSignIn);
        lottieAnimationView2=findViewById(R.id.lottieAnimationView2);
        signInBtn=findViewById(R.id.Sign_In_Btn);
        forget=findViewById(R.id.ForgetPassword);
        SignIn=findViewById(R.id.Sign_Up_Btn);
        mAuth=FirebaseAuth.getInstance();
    }
    private void shakeAnim(EditText editText,String Error){
        s=new SignUp();
        s.shakeAnimation(editText,shakeAnim,Error,getApplicationContext());
    }
    private void signInUser(String Email,String Password){
        mAuth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user=mAuth.getCurrentUser();
                            if(user!=null){
                                if(user.isEmailVerified()){
                                    startActivity(new Intent(getApplicationContext(), HandlerFragment.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(SignIn.this, "email not verified", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(SignIn.this, "user not exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(SignIn.this, "password invaild", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("failLogin",e.getLocalizedMessage());
                    }
                });
    }
}