package com.example.weather;

import static androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode;

import android.content.Context;
import android.content.Intent;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {
    private LottieAnimationView loading;
    TextView Login;
    private EditText Name, Email, Password;
    private String name, email, password;
    private AppCompatButton signUp;
    private static Animation shakeAnim;
    private FirebaseFirestore db;

    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 101;
    private ImageView gIcon;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if(currentUser!=null){
            startActivity(new Intent(getApplicationContext(), HandlerFragmentActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_signup);
        viewInit();
        mAuth = FirebaseAuth.getInstance();
        gIcon = findViewById(R.id.googleIconBtn);
        db=FirebaseFirestore.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Use Web client ID
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        gIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        signIn();
                    }
                },2000);

            }
        });
        shakeAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        signUp.setOnClickListener(v -> {
            name = Name.getText().toString();
            email = Email.getText().toString();
            password = Password.getText().toString();
            if (!name.isEmpty()) {
                if (!email.isEmpty()) {
                    if (!password.isEmpty() && (password.length() > 7 && password.length() < 19)) {
                        if (EmailVaild.isValidEmail(email)) {
                            loading.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    AuthenticationWithCreaditonal(name.trim(), email, password);
                                    loading.setVisibility(View.GONE);
                                }
                            }, 2000);

                        } else {


//                             Toast.makeText(this, "Email not vaild", Toast.LENGTH_SHORT).show();
                            shakeAnimation(Email, shakeAnim, "Email not vaild", SignUpActivity.this);
                        }
                    } else {
                        if (password.equals("")) {
                            shakeAnimation(Password, shakeAnim, "Password is blank", getApplicationContext());

                        } else {
                            shakeAnimation(Password, shakeAnim, "password minimum character 8 to 16", getApplicationContext());
                        }
                    }
                } else {
                    shakeAnimation(Email, shakeAnim, "Email is blank", getApplicationContext());
                }
            } else {
                shakeAnimation(Name, shakeAnim, "Name is blank", getApplicationContext());
            }
        });
        Login.setOnClickListener(v -> {
            startActivity(new Intent(this, SignInActivity.class));
        });
    }

    public void shakeAnimation(EditText getEdit, Animation Anim, String Error, Context context) {

        getEdit.startAnimation(Anim);
        changeBorderColor(getEdit, context.getResources().getColor(R.color.red));
        getEdit.setError(Error);
        Anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Remove the red border after animation ends
                changeBorderColor(getEdit, context.getResources().getColor(R.color.original_edittext_color));
                // Revert the EditText to its normal state
                getEdit.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private void viewInit() {
        loading = findViewById(R.id.lottieAnimationView);
        loading.setVisibility(View.GONE);
        Login = findViewById(R.id.login);
        Name = findViewById(R.id.name_sign_up);
        Email = findViewById(R.id.email_sign_up);
        Password = findViewById(R.id.password_sign_up);
        signUp = findViewById(R.id.SignUpBtn);
        gIcon=findViewById(R.id.googleIconBtn);

    }

    private void changeBorderColor(EditText editText, int color) {
        GradientDrawable drawable = (GradientDrawable) editText.getBackground();
        drawable.setStroke(2, color); // 2 is the border width
    }

    private void AuthenticationWithCreaditonal(String userName, String userEmail, String userPassword) {
        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            FirebaseUser user = mAuth.getCurrentUser();

                                            // Set the display name
                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(userName)
                                                    // You can also set other properties here if needed, like photo URL
                                                    .build();
                                            user.updateProfile(profileUpdates)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
//                                                                Log.d(TAG, "User profile updated.");
                                                            }
                                                        }
                                                    });
                                            Intent i = new Intent(getApplicationContext(), EmailVerificationActivity.class);
                                            i.putExtra("name", userName);
                                            i.putExtra("email", userEmail);
                                            startActivity(i);
                                            finish();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("eeee", e.getLocalizedMessage());
                                    }
                                });
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "account is exist already please login", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("eeee", e.getLocalizedMessage());
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken(),account.getEmail());
            } catch (ApiException e) {
                // Google Sign In failed, update UI accordingly
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken,String emails) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Update UI
                        if(user!=null){
                            isExistEmail(emails,user.getDisplayName());
                            startActivity(new Intent(getApplicationContext(), HandlerFragmentActivity.class));
                            finish();
                            loading.setVisibility(View.GONE);
                        }else{
                            Toast.makeText(this, "user null", Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAGERROR", "signInWithCredential:failure", task.getException());
                        Toast.makeText(getApplicationContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        // Update UI
                    }
                });
    }

    private void isExistEmail(String email,String userName){
        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                     if(task.isSuccessful()){
                         if(task.getResult().getSignInMethods()!=null&&task.getResult().getSignInMethods().size()>0){
                             //if already email is existing
                             Toast.makeText(SignUpActivity.this, "already register", Toast.LENGTH_SHORT).show();
                         }
                         else{
                             // if email not exist that's why we create database
                          StoreInDb(userName,email);
                         }
                     }
                    }
                });
    }
    private void StoreInDb(String nameCred,String emailCred){
        User user=new User(nameCred,emailCred);
        db.collection("user").document(nameCred+"1")
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(SignUpActivity.this, "done", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("errordbs",e.getLocalizedMessage());
                    }
                });
    }
}