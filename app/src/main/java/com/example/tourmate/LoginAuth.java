package com.example.tourmate;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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



public class LoginAuth extends Fragment {
    private EditText emailET,passET;
    private Button signinbtn;
    private TextView signuptv;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentuser;
    private UserAuthListener listener;
    private UserRegListener regListener;


    public LoginAuth() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        listener = (UserAuthListener) context;
        regListener = (UserRegListener) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_auth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        emailET = view.findViewById(R.id.emailET);
        passET = view.findViewById(R.id.passET);
        signinbtn = view.findViewById(R.id.signinbtn);
        signuptv = view.findViewById(R.id.signupTV);

        signuptv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regListener.onRegSuccesfull();

            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailET.getText().toString();
                String pass = passET.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            if (firebaseAuth.getCurrentUser().isEmailVerified()){
                                currentuser= firebaseAuth.getCurrentUser();
                                Toast.makeText(context, "SignIn Successfull", Toast.LENGTH_SHORT).show();
                                listener.onLoginSuccessfull();
                            }else {
                                Toast.makeText(context, "Verify your account first!", Toast.LENGTH_SHORT).show();
                            }

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
    }

    public interface UserAuthListener{
        void onLoginSuccessfull();
    }

    public interface UserRegListener{
        void onRegSuccesfull();
    }

}
