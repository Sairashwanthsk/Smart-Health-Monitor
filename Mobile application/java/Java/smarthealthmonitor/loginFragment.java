package com.example.smarthealthmonitor;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginFragment extends Fragment {
    EditText lEmail, lPassword;
    Button Loginbtn;
    ProgressBar ProgressBar;
    FirebaseAuth fAuth;
    CheckBox lCheckbox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        lEmail = (EditText)view.findViewById(R.id.lemail);
        lPassword = (EditText)view.findViewById(R.id.lpassword);
        Loginbtn = (Button)view.findViewById(R.id.loginbtn);
        ProgressBar = (ProgressBar)view.findViewById(R.id.progressbar);
        fAuth = FirebaseAuth.getInstance();
        lCheckbox = (CheckBox)view.findViewById(R.id.lcheckbox);

        lCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    lPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    lPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = lEmail.getText().toString();
                String password = lPassword.getText().toString();
                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){
                    ProgressBar.setVisibility(View.VISIBLE);
                    fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(getContext(), MainActivity2.class);
                                startActivity(intent);
                                ProgressBar.setVisibility(View.INVISIBLE);
                            }else{
                                String error = task.getException().getMessage();
                                Toast.makeText(getActivity(), "Error :"+error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            Intent intent = new Intent(getContext(), MainActivity2.class);
            startActivity(intent);
        }
    }
}