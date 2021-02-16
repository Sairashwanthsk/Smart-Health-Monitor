package com.example.smarthealthmonitor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LogActivity extends AppCompatActivity{
    Button log, uploadpres;
    EditText glucose, cholesterol, respiration, systolic, diastolic, pulse, spo2, temperature;
    FirebaseAuth fAuth;
    FirebaseUser user;
    FirebaseFirestore fstore;
    ProgressBar progressBar;
    ImageButton backbtn, addpres;
    Uri imageUri;
    StorageReference storereference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        fAuth = FirebaseAuth.getInstance();
        backbtn = findViewById(R.id.back_btn);
        user = fAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        log = findViewById(R.id.logreading);
        glucose = (EditText) findViewById(R.id.inglucose);
        cholesterol = (EditText) findViewById(R.id.incholesterol);
        respiration = (EditText) findViewById(R.id.inrespiration);
        systolic = (EditText) findViewById(R.id.insystolic);
        diastolic = (EditText) findViewById(R.id.indiastolic);
        pulse = (EditText) findViewById(R.id.inpulse);
        spo2 = (EditText) findViewById(R.id.inspo2);
        temperature = (EditText) findViewById(R.id.intemperature);
        progressBar = findViewById(R.id.progressbar);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String month = new SimpleDateFormat("MMM", Locale.getDefault()).format(Calendar.getInstance().getTime());
                String glu = glucose.getText().toString();
                String cho = cholesterol.getText().toString();
                String res = respiration.getText().toString();
                String sys = systolic.getText().toString();
                String dia = diastolic.getText().toString();
                String pul = pulse.getText().toString();
                String o2 = spo2.getText().toString();
                String tem = temperature.getText().toString();
                DocumentReference profilereference = fstore.collection("Users").document(user.getEmail());
                DocumentReference reference = fstore.collection("Users").document(user.getEmail()).collection("All_health_parameters").document(date);

                if (!TextUtils.isEmpty(glu) || !TextUtils.isEmpty(cho) || !TextUtils.isEmpty(res) || !TextUtils.isEmpty(sys)
                        || !TextUtils.isEmpty(dia) || !TextUtils.isEmpty(pul) || !TextUtils.isEmpty(o2) || !TextUtils.isEmpty(tem)){

                    HashMap<Object, Object> parameters = new HashMap<Object, Object>();
                    parameters.put("Date", date);
                    parameters.put("Month", month);
                    reference.set(parameters, SetOptions.merge());

                    HashMap<Object, Object> profile = new HashMap<>();

                    if (!TextUtils.isEmpty(glu)) {
                        Long gluresult = Long.valueOf(glucose.getText().toString());
                        if (gluresult <= 140) {
                            parameters.put("GReading", gluresult);
                            parameters.put("GStatus", "Normal");
                            profile.put("Diabetes", "No");
                            profilereference.set(profile, SetOptions.merge());
                            reference.set(parameters, SetOptions.merge());
                            Toast.makeText(LogActivity.this, "Glucose parameter updated Successfully", Toast.LENGTH_SHORT).show();
                            glucose.getText().clear();
                        } else if (gluresult > 140 && gluresult <= 200) {
                            parameters.put("GReading", gluresult);
                            parameters.put("GStatus", "Pre-Diabetes");
                            profile.put("Diabetes", "No");
                            profilereference.set(profile, SetOptions.merge());
                            reference.set(parameters, SetOptions.merge());
                            Toast.makeText(LogActivity.this, "Glucose parameter updated Successfully", Toast.LENGTH_SHORT).show();
                            glucose.getText().clear();
                        } else if (gluresult > 200) {
                            parameters.put("GReading", gluresult);
                            parameters.put("GStatus", "Diabetes");
                            profile.put("Diabetes", "Yes");
                            profilereference.set(profile, SetOptions.merge());
                            reference.set(parameters, SetOptions.merge());
                            Toast.makeText(LogActivity.this, "Glucose parameter updated Successfully", Toast.LENGTH_SHORT).show();
                            glucose.getText().clear();
                        }
                    }

                    if (!TextUtils.isEmpty(cho)) {
                        Long choresult = Long.valueOf(cholesterol.getText().toString());
                        if (choresult < 125) {
                            parameters.put("CReading", choresult);
                            parameters.put("CStatus", "Low Cholesterol");
                            reference.set(parameters, SetOptions.merge());
                            Toast.makeText(LogActivity.this, "Cholesterol parameter updated Successfully", Toast.LENGTH_SHORT).show();
                            cholesterol.getText().clear();
                        } else if (choresult >= 125 && choresult <= 200) {
                            parameters.put("CReading", choresult);
                            parameters.put("CStatus", "Normal");
                            reference.set(parameters, SetOptions.merge());
                            Toast.makeText(LogActivity.this, "Cholesterol parameter updated Successfully", Toast.LENGTH_SHORT).show();
                            cholesterol.getText().clear();
                        } else if (choresult > 200) {
                            parameters.put("CReading", choresult);
                            parameters.put("CStatus", "High Cholesterol");
                            reference.set(parameters, SetOptions.merge());
                            Toast.makeText(LogActivity.this, "Cholesterol parameter updated Successfully", Toast.LENGTH_SHORT).show();
                            cholesterol.getText().clear();
                        }
                    }

                    if (!TextUtils.isEmpty(res)) {
                        Long resresult = Long.valueOf(respiration.getText().toString());
                        if (resresult < 12) {
                            parameters.put("RRReading", resresult);
                            parameters.put("RrStatus", "Low Respiration rate");
                            Toast.makeText(LogActivity.this, "Respiration rate parameter updated Successfully", Toast.LENGTH_SHORT).show();
                            reference.set(parameters, SetOptions.merge());
                            respiration.getText().clear();
                        } else if (resresult >= 12 && resresult <= 20) {
                            parameters.put("RRReading", resresult);
                            parameters.put("RrStatus", "Normal");
                            reference.set(parameters, SetOptions.merge());
                            Toast.makeText(LogActivity.this, "Respiration rate parameter updated Successfully", Toast.LENGTH_SHORT).show();
                            respiration.getText().clear();
                        } else if (resresult > 20) {
                            parameters.put("RRReading", resresult);
                            parameters.put("RrStatus", "High Respiration rate");
                            reference.set(parameters, SetOptions.merge());
                            Toast.makeText(LogActivity.this, "Respiration rate parameter updated Successfully", Toast.LENGTH_SHORT).show();
                            respiration.getText().clear();
                        }
                    }


                    if (!TextUtils.isEmpty(sys) && !TextUtils.isEmpty(dia)) {
                        Long sysresult = Long.valueOf(systolic.getText().toString());
                        Long diaresult = Long.valueOf(diastolic.getText().toString());
                        if (sysresult <= 120 && diaresult <= 80) {
                            parameters.put("SReading", sysresult);
                            parameters.put("DReading", diaresult);
                            parameters.put("BpStatus", "Noraml");
                            reference.set(parameters, SetOptions.merge());
                            Toast.makeText(LogActivity.this, "Bloodpressure parameter updated Successfully", Toast.LENGTH_SHORT).show();
                            systolic.getText().clear();
                            diastolic.getText().clear();
                        } else if ((sysresult > 120 && sysresult <= 130) && (diaresult <= 80)) {
                            parameters.put("SReading", sysresult);
                            parameters.put("DReading", diaresult);
                            parameters.put("BpStatus", "Elevated BP");
                            reference.set(parameters, SetOptions.merge());
                            Toast.makeText(LogActivity.this, "Bloodpressure parameter updated Successfully", Toast.LENGTH_SHORT).show();
                            systolic.getText().clear();
                            diastolic.getText().clear();
                        } else if ((sysresult > 130 && sysresult <= 140) && (diaresult > 80 && diaresult <= 90)) {
                            parameters.put("SReading", sysresult);
                            parameters.put("DReading", diaresult);
                            parameters.put("BpStatus", "High BP stage-I");
                            reference.set(parameters, SetOptions.merge());
                            Toast.makeText(LogActivity.this, "Bloodpressure parameter updated Successfully", Toast.LENGTH_SHORT).show();
                            systolic.getText().clear();
                            diastolic.getText().clear();
                        } else if ((sysresult > 140 && sysresult <= 180) && (diaresult > 90 && diaresult <= 120)) {
                            parameters.put("SReading", sysresult);
                            parameters.put("DReading", diaresult);
                            parameters.put("BpStatus", "High BP stage-II");
                            reference.set(parameters, SetOptions.merge());
                            Toast.makeText(LogActivity.this, "Bloodpressure parameter updated Successfully", Toast.LENGTH_SHORT).show();
                            systolic.getText().clear();
                            diastolic.getText().clear();
                        } else if (sysresult > 180 && diaresult > 120) {
                            parameters.put("SReading", sysresult);
                            parameters.put("DReading", diaresult);
                            parameters.put("BpStatus", "High BP stage-III");
                            reference.set(parameters, SetOptions.merge());
                            Toast.makeText(LogActivity.this, "Bloodpressure parameter updated Successfully", Toast.LENGTH_SHORT).show();
                            systolic.getText().clear();
                            diastolic.getText().clear();
                        }
                    }


                    if (!TextUtils.isEmpty(pul)) {
                        Long pulresult = Long.valueOf(pulse.getText().toString());
                        if (pulresult >= 60 && pulresult <= 100) {
                            parameters.put("PReading", pulresult);
                            parameters.put("PStatus", "Normal");
                            reference.set(parameters, SetOptions.merge());
                            Toast.makeText(LogActivity.this, "Pulse parameter updated Successfully", Toast.LENGTH_SHORT).show();
                            pulse.getText().clear();
                        } else if (pulresult > 100) {
                            parameters.put("PReading", pulresult);
                            parameters.put("PStatus", "High Pulse");
                            reference.set(parameters, SetOptions.merge());
                            Toast.makeText(LogActivity.this, "Pulse parameter updated Successfully", Toast.LENGTH_SHORT).show();
                            pulse.getText().clear();
                        } else if (pulresult < 60) {
                            parameters.put("PReading", pulresult);
                            parameters.put("PStatus", "Low Pulse");
                            reference.set(parameters, SetOptions.merge());
                            Toast.makeText(LogActivity.this, "Pulse parameter updated Successfully", Toast.LENGTH_SHORT).show();
                            pulse.getText().clear();
                        }
                    }


                    if (!TextUtils.isEmpty(o2)) {
                        Long o2result = Long.valueOf(spo2.getText().toString());
                        if (o2result >= 98 && o2result <= 100) {
                            parameters.put("O2Reading", o2result);
                            parameters.put("O2Status", "Normal");
                            profile.put("Hypoxemia", "No");
                            profilereference.set(profile, SetOptions.merge());
                            reference.set(parameters, SetOptions.merge());
                            Toast.makeText(LogActivity.this, "SpO2 parameter updated Successfully", Toast.LENGTH_SHORT).show();
                            spo2.getText().clear();
                        } else if (o2result < 98) {
                            parameters.put("O2Reading", o2result);
                            parameters.put("O2Status", "Hypoxemia");
                            profile.put("Hypoxemia", "Yes");
                            profilereference.set(profile, SetOptions.merge());
                            reference.set(parameters, SetOptions.merge());
                            Toast.makeText(LogActivity.this, "SpO2 parameter updated Successfully", Toast.LENGTH_SHORT).show();
                            spo2.getText().clear();
                        }
                    }


                    if (!TextUtils.isEmpty(tem)) {
                        Long temresult = Long.valueOf(temperature.getText().toString());
                        if (temresult >= 96 && temresult <= 98) {
                            parameters.put("TReading", temresult);
                            parameters.put("TStatus", "Normal");
                            reference.set(parameters, SetOptions.merge());
                            Toast.makeText(LogActivity.this, "Temperature parameter updated Successfully", Toast.LENGTH_SHORT).show();
                            temperature.getText().clear();
                        } else if (temresult > 98) {
                            parameters.put("TReading", temresult);
                            parameters.put("TStatus", "High Temperature");
                            reference.set(parameters, SetOptions.merge());
                            Toast.makeText(LogActivity.this, "Temperature parameter updated Successfully", Toast.LENGTH_SHORT).show();
                            temperature.getText().clear();
                        }
                    }
                } else {
                    Toast.makeText(LogActivity.this, "Please give atleast one parameter reading", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogActivity.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });
    }

}