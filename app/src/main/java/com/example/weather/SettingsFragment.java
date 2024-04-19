package com.example.weather;

import static androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SettingsFragment extends Fragment {
    private TextView name,email;
    private ImageView Logout;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String Emails,Names;


    public SettingsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_settings, container, false);
        Init(v);
        dataInit();
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog customDialog=new CustomDialog(getContext(),false,"Change your name",Names,Emails);
                customDialog.show(getChildFragmentManager(), "CustomDialogFragment");
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            CustomDialog customDialog=new CustomDialog(requireActivity(),true,"Change your email",Names,Emails);
            customDialog.show(getChildFragmentManager(),"CustomDialogFragment");
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog;
                AlertDialog.Builder builder=new AlertDialog.Builder(requireActivity());
                final View view=LayoutInflater.from(requireActivity()).inflate(R.layout.logout_alertdialog,null);
                builder.setView(view);
                AppCompatButton Yes=view.findViewById(R.id.yes);
                AppCompatButton No=view.findViewById(R.id.no);
                alertDialog=builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                        if(user==null){
                            startActivity(new Intent(requireActivity(), SignInActivity.class));
                            alertDialog.dismiss();
                        }
                    }
                });
                No.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        return v;
    }
    private void Init(View v){
        name=v.findViewById(R.id.userTextName);
        email=v.findViewById(R.id.userEmail);
        Logout=v.findViewById(R.id.logout);
        db=FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

    }
    private void dataInit(){
        if(mUser!=null){
            String names=mUser.getDisplayName();
//            Toast.makeText(requireContext(), ""+mUser.getDisplayName(), Toast.LENGTH_SHORT).show();
            DocumentReference docRef=db.collection("user").document(names+1);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                        String names=documentSnapshot.getString("name");
                        String emails=documentSnapshot.getString("email");
                        Names=names;
                        Emails=emails;
                        name.setText(names);
                        email.setText(emails);
                    }
                    else{
                        Toast.makeText(requireContext(), "not exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(e -> Toast.makeText(requireActivity(), ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
        }
    }
}