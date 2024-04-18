package com.example.weather;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CustomDialog extends DialogFragment {
    private android.content.Context context;
    private boolean isEmail;
    private Animation shakeAnim;
    private String titleDialogs;
    private FirebaseFirestore db;
    private DocumentReference docRef;
    private String Emails,Names;
    private FirebaseUser mUser;
    CustomDialog(Context contexts, boolean IsEmail, String title,String names,String emails){
        this.context=contexts;
        this.isEmail=IsEmail;
        this.titleDialogs=title;
        this.Names=names;
        this.Emails=emails;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v=LayoutInflater.from(context).inflate(R.layout.dialogs_custom_ui,null);
        db=FirebaseFirestore.getInstance();
        mUser=FirebaseAuth.getInstance().getCurrentUser();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shakeAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.shake);

        EditText text = view.findViewById(R.id.texts);
        Button buttonOk = view.findViewById(R.id.Ok);
        Button buttonCancel = view.findViewById(R.id.Cancel);
        TextView Heading=view.findViewById(R.id.titleDialog);
        Heading.setText(titleDialogs);
        docRef=db.collection("user").document(mUser.getDisplayName()+1);
        if(isEmail){
            text.setText(Emails);
        }
        else{
            text.setText(Names);
        }
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmail){
                    String email=text.getText().toString();
                    Heading.setText(titleDialogs);
                    if(EmailVaild.isValidEmail(email)){
//                        Toast.makeText(context, "vaild", Toast.LENGTH_SHORT).show();
                        docRef.update("email",email)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                dismiss();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                    Log.e("DBERROR",e.getLocalizedMessage());
                                    }
                                });
                    }
                    else{
                        shakeAnimation(text,shakeAnim,"email is not vaild",requireActivity());
                    }
                }
                else{
                    String name=text.getText().toString();
                    Heading.setText(titleDialogs);
                  if(!name.isEmpty()){
//                      Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
                      docRef.update("name",name);
                      dismiss();
                  }
                  else{
                      Toast.makeText(context, "empty", Toast.LENGTH_SHORT).show();
                  }
                }
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
    public void shakeAnimation(EditText getEdit, Animation Anim, String Error, android.content.Context context) {

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
    private void changeBorderColor(EditText editText, int color) {
        GradientDrawable drawable = (GradientDrawable) editText.getBackground();
        drawable.setStroke(2, color); // 2 is the border width
    }

}
