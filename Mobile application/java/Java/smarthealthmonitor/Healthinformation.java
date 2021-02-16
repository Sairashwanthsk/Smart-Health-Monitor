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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class Healthinformation extends Fragment {

    RadioButton asy, asn, aly, aln, brony, bronn, chdy, chdn, diay, dian, hypy, hypn;
    EditText rOtherillness, rCurrentmedication, rDrugallergies;
    Button Registerbtn;
    ProgressBar progressBar;
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_healthinformation, container, false);
        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        Registerbtn = (Button)view.findViewById(R.id.registerbtn);
        rOtherillness = (EditText)view.findViewById(R.id.rotherillness);
        rCurrentmedication = (EditText)view.findViewById(R.id.rcurrentmedication);
        rDrugallergies = (EditText)view.findViewById(R.id.rdrugallergies);
        Registerbtn = (Button)view.findViewById(R.id.registerbtn);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        asy = (RadioButton)view.findViewById(R.id.asthma_yes);
        asn = (RadioButton)view.findViewById(R.id.asthma_no);
        aly = (RadioButton)view.findViewById(R.id.alcoholic_yes);
        aln = (RadioButton)view.findViewById(R.id.alcoholic_no);
        brony = (RadioButton)view.findViewById(R.id.bronchiectasis_yes);
        bronn = (RadioButton)view.findViewById(R.id.bronchiectasis_no);
        chdy = (RadioButton)view.findViewById(R.id.chd_yes);
        chdn = (RadioButton)view.findViewById(R.id.chd_no);
        diay = (RadioButton)view.findViewById(R.id.diabetes_yes);
        dian = (RadioButton)view.findViewById(R.id.diabetes_no);
        hypy = (RadioButton)view.findViewById(R.id.hypoxemia_yes);
        hypn = (RadioButton)view.findViewById(R.id.hypoxemia_no);

        Registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otherillness = rOtherillness.getText().toString();
                String currentmedication = rCurrentmedication.getText().toString();
                String drugallergies = rDrugallergies.getText().toString();
                DocumentReference documentReference = fstore.collection("Users").document(user.getEmail());


                if((asy!=null || asn!=null) && (aly!=null || aln!=null) && (brony!= null || bronn!=null)
                        && (chdy!=null || chdn!=null) && (diay!=null || dian!=null) && (hypy!=null || hypn!=null)) {
                    if (asy.isChecked()) {
                        HashMap<Object, Object> profile = new HashMap<Object, Object>();
                        profile.put("Asthma", asy);
                        documentReference.set(profile, SetOptions.merge());
                    } else if (asn.isChecked()) {
                        HashMap<Object, Object> profile = new HashMap<Object, Object>();
                        profile.put("Asthma", asn);
                        documentReference.set(profile, SetOptions.merge());
                    } else if (aly.isChecked()) {
                        HashMap<Object, Object> profile = new HashMap<Object, Object>();
                        profile.put("Alcoholic", aly);
                        documentReference.set(profile, SetOptions.merge());
                    } else if (aln.isChecked()) {
                        HashMap<Object, Object> profile = new HashMap<Object, Object>();
                        profile.put("Alcoholic", aln);
                        documentReference.set(profile, SetOptions.merge());
                    } else if (brony.isChecked()) {
                        HashMap<Object, Object> profile = new HashMap<Object, Object>();
                        profile.put("Bronchiectasis", brony);
                        documentReference.set(profile, SetOptions.merge());
                    } else if (bronn.isChecked()) {
                        HashMap<Object, Object> profile = new HashMap<Object, Object>();
                        profile.put("Bronchiectasis", bronn);
                        documentReference.set(profile, SetOptions.merge());
                    } else if (chdy.isChecked()) {
                        HashMap<Object, Object> profile = new HashMap<Object, Object>();
                        profile.put("Coronary_Artery_Disease", chdy);
                        documentReference.set(profile, SetOptions.merge());
                    } else if (chdn.isChecked()) {
                        HashMap<Object, Object> profile = new HashMap<Object, Object>();
                        profile.put("Coronary_Artery_Disease", chdn);
                        documentReference.set(profile, SetOptions.merge());
                    } else if (diay.isChecked()) {
                        HashMap<Object, Object> profile = new HashMap<Object, Object>();
                        profile.put("Diabetes", diay);
                        documentReference.set(profile, SetOptions.merge());
                    } else if (dian.isChecked()) {
                        HashMap<Object, Object> profile = new HashMap<Object, Object>();
                        profile.put("Diabetes", dian);
                        documentReference.set(profile, SetOptions.merge());
                    } else if (hypy.isChecked()) {
                        HashMap<Object, Object> profile = new HashMap<Object, Object>();
                        profile.put("Hypoxemia", hypy);
                        documentReference.set(profile, SetOptions.merge());
                    } else if (hypn.isChecked()) {
                        HashMap<Object, Object> profile = new HashMap<Object, Object>();
                        profile.put("Hypoxemia", hypn);
                        documentReference.set(profile, SetOptions.merge());
                    }
                } else {
                    Toast.makeText(getContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
                }
                if(!TextUtils.isEmpty(otherillness) && !TextUtils.isEmpty(currentmedication) && !TextUtils.isEmpty(drugallergies)){
                    progressBar.setVisibility(View.VISIBLE);
                    Map<String, String> profile = new HashMap<String, String>();
                    profile.put("Other_Illness", otherillness);
                    profile.put("Current_medication", currentmedication);
                    profile.put("Drug_allergies", drugallergies);

                    documentReference.set(profile, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getActivity(), "Medical information updated Successfully", Toast.LENGTH_SHORT).show();

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(getContext(), MainActivity2.class);
                                            startActivity(intent);
                                        }
                                    },2000);
                                }
                            });
                }else {
                    Toast.makeText(getContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}