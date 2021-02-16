package com.example.smarthealthmonitor;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registerFragment extends Fragment {
    EditText rEmail, rPassword, rConfirmpassword;
    Button rProceed1;
    ProgressBar ProgressBar;
    FirebaseAuth fAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        rEmail = (EditText)view.findViewById(R.id.remail);
        rPassword = (EditText)view.findViewById(R.id.rpassword);
        rConfirmpassword = (EditText)view.findViewById(R.id.rconfirmpassword);
        rProceed1 = (Button)view.findViewById(R.id.rproceed1);
        ProgressBar = (ProgressBar)view.findViewById(R.id.progressbar);
        fAuth = FirebaseAuth.getInstance();

        rProceed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBar.setVisibility(View.VISIBLE);
                String email = rEmail.getText().toString();
                String password = rPassword.getText().toString();
                String confirm_password = rConfirmpassword.getText().toString();
                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(confirm_password)){
                    if(password.equals(confirm_password)){
                        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(getActivity(), RegistrationActivity.class);
                                            startActivity(intent);
                                        }
                                    }, 3000);
                                    ProgressBar.setVisibility(View.INVISIBLE);
                                }else{
                                    ProgressBar.setVisibility(View.INVISIBLE);
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), "Error :"+error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        ProgressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getActivity(), "User Already Exists", Toast.LENGTH_SHORT).show();
                                    }
                                }, 3000);
                                ProgressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }else {
                        ProgressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), "Password and confirm password is not matching", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}