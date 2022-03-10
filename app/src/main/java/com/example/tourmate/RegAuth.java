package com.example.tourmate;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegAuth extends Fragment {
    private Context context;
    private EditText nameET,emailET,passET;
    private Button signupbtn;
    private TextView regsigninTV;
    private FirebaseAuth firebaseAuth;
    private Task<Void> user;
    private UserRegListener regListener;
    private UserLoginListener loginListener;




    public RegAuth() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        regListener = (UserRegListener) context;
        loginListener = (UserLoginListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reg_auth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        nameET = view.findViewById(R.id.nameET);
        emailET = view.findViewById(R.id.emailET);
        passET = view.findViewById(R.id.passET);
        signupbtn = view.findViewById(R.id.signupbtn);
        regsigninTV = view.findViewById(R.id.regsigninTV);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameET.getText().toString();
                final String email = emailET.getText().toString();
                String pass = passET.getText().toString();
                firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            user = firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(context, "SignUp Successfull", Toast.LENGTH_SHORT).show();
                                        nameET.setText("");
                                        passET.setText("");
                                        emailET.setText("");
                                        regListener.onregSuccessfullsignup();
                                    }else{
                                        Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                            
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        regsigninTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginListener.onlogSuccessfullsignup();

            }
        });

    }


    public interface UserRegListener{
        void onregSuccessfullsignup();

    }
    public interface UserLoginListener{
        void onlogSuccessfullsignup();
    }

}
