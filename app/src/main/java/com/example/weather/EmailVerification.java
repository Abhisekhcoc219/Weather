package com.example.weather;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class EmailVerification extends AppCompatActivity {
    private TextView verifiedMsg;
    private String verify_msg;
    private String emailCred,nameCred;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        verifiedMsg=findViewById(R.id.Msg);
        mAuth=FirebaseAuth.getInstance();
        Intent getData=getIntent();
        db=FirebaseFirestore.getInstance();
        emailCred=getData.getStringExtra("email");
        nameCred=getData.getStringExtra("name");
        verify_msg="A sign-up with email with additional instructions was sent to email. Check your "+emailCred+" to complete sign-up.";
        verifiedMsg.setText(verify_msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUser=mAuth.getCurrentUser();
        if (mUser != null) {
            mUser.reload().addOnCompleteListener(new OnCompleteListener<Void>() {

                @Override
                public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if(mUser.isEmailVerified()){
                        Toast.makeText(EmailVerification.this, "success", Toast.LENGTH_SHORT).show();
                        StoreInDb();
                    }
                    else{
                        Toast.makeText(EmailVerification.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(EmailVerification.this, "is failed", Toast.LENGTH_SHORT).show();
                }
                }
            });
        } else {
            // No user is signed in, handle accordingly
            Toast.makeText(this, "No user is signed in.", Toast.LENGTH_SHORT).show();
        }
    }
    private void StoreInDb(){
        User user=new User(nameCred,emailCred);
        db.collection("user").document(nameCred+"1")
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        startActivity(new Intent(getApplicationContext(), HandlerFragment.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                     Log.e("errordb",e.getLocalizedMessage());
                    }
                });
    }
}