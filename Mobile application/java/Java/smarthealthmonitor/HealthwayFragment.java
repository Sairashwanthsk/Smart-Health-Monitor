package com.example.smarthealthmonitor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

public class HealthwayFragment extends Fragment {
    ImageButton logbtn;
    TextView status, hhealth, rhealth, temperature, pulse, spo2, glucose, respirationrate, cholesterol, bp;
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_healthway, container, false);
        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        logbtn = (ImageButton) view.findViewById(R.id.logbtn);
        status = (TextView) view.findViewById(R.id.health_status);
        hhealth = (TextView) view.findViewById(R.id.heart_health);
        rhealth = (TextView) view.findViewById(R.id.respiratory_health);
        temperature = (TextView) view.findViewById(R.id.temperature);
        pulse = (TextView) view.findViewById(R.id.pulse);
        spo2 = (TextView) view.findViewById(R.id.spo2);
        glucose = (TextView) view.findViewById(R.id.glucose);
        respirationrate = (TextView) view.findViewById(R.id.respiration_rate);
        cholesterol = (TextView) view.findViewById(R.id.cholesterol);
        bp = (TextView) view.findViewById(R.id.bp);

        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LogActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(), "Log Panel", Toast.LENGTH_SHORT).show();
            }
        });

        Query reference = fstore.collection("Users").document(user.getEmail()).collection("All_health_parameters").orderBy("Date", Query.Direction.DESCENDING).limit(1);
        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(getContext(), "Error loading", Toast.LENGTH_SHORT).show();
                }
                for (QueryDocumentSnapshot doc : value) {
                    if (doc.exists()) {
                        Long sreading = doc.getLong("SReading");
                        Long dreading = doc.getLong("DReading");
                        Long preading = doc.getLong("PReading");
                        Long creading = doc.getLong("CReading");
                        Long rreading = doc.getLong("RRReading");
                        Long oreading = doc.getLong("O2Reading");
                        Long greading = doc.getLong("GReading");
                        Long treading = doc.getLong("TReading");

                        if(treading > 98){
                            temperature.setTextColor(Color.RED);
                            temperature.setText("High Temperature");
                        } else if (treading < 96){
                            temperature.setTextColor(Color.RED);
                            temperature.setText("Low Temperature");
                        } else {
                            temperature.setTextColor(Color.GREEN);
                            temperature.setText("Normal");
                        }

                        if(preading < 60){
                            pulse.setTextColor(Color.RED);
                            pulse.setText("Low pulse");
                        }else if(preading > 100){
                            pulse.setTextColor(Color.RED);
                            pulse.setText("High pulse");
                        } else {
                            pulse.setTextColor(Color.GREEN);
                            pulse.setText("Normal");
                        }

                        if(oreading < 98){
                            spo2.setTextColor(Color.RED);
                            spo2.setText("Hypoxemia");
                        } else {
                            spo2.setTextColor(Color.GREEN);
                            spo2.setText("Normal");
                        }

                        if((greading > 140 && greading <= 199)){
                            glucose.setTextColor(Color.RED);
                            glucose.setText("Pre-diabetes");
                        }else if(greading >= 200){
                            glucose.setTextColor(Color.RED);
                            glucose.setText("Diabetes");
                        } else {
                            glucose.setTextColor(Color.GREEN);
                            glucose.setText("Normal");
                        }

                        if(rreading > 20){
                            respirationrate.setTextColor(Color.RED);
                            respirationrate.setText("High Respiration rate");
                        }else if(rreading < 12){
                            respirationrate.setTextColor(Color.RED);
                            respirationrate.setText("Low Respiration rate");
                        } else {
                            respirationrate.setTextColor(Color.GREEN);
                            respirationrate.setText("Normal");
                        }

                        if(creading > 200){
                            cholesterol.setTextColor(Color.RED);
                            cholesterol.setText("High cholesterol");
                        }else if(creading < 125){
                            cholesterol.setTextColor(Color.RED);
                            cholesterol.setText("Low cholesterol");
                        } else {
                            cholesterol.setTextColor(Color.GREEN);
                            cholesterol.setText("Normal");
                        }

                        if((sreading > 120 && sreading < 130) && (dreading <= 90)){
                            bp.setTextColor(Color.RED);
                            bp.setText("Elevated BP");
                        }else if((sreading >= 130 && sreading < 140) && (dreading <= 100)){
                            bp.setTextColor(Color.RED);
                            bp.setText("High BP stage-I");
                        }else if((sreading >= 140 && sreading < 180) && (dreading <= 120)){
                            bp.setTextColor(Color.RED);
                            bp.setText("High BP stage-II");
                        }else if(sreading > 180 && dreading > 120){
                            bp.setTextColor(Color.RED);
                            bp.setText("High BP stage-III");
                        } else {
                            bp.setTextColor(Color.GREEN);
                            bp.setText("Normal");
                        }

                        if ((sreading <= 140 && dreading <= 80) && (preading >= 60 && preading <= 100) && (creading >= 125 && creading <= 200)) {
                            hhealth.setTextColor(Color.GREEN);
                            hhealth.setText("Healthy");
                        } else {
                            hhealth.setTextColor(Color.RED);
                            hhealth.setText("Unhealthy");
                        }
                        if ((oreading >= 98) && (preading >= 60 && preading <= 100) && (rreading >= 12 && rreading <= 20)) {
                            rhealth.setTextColor(Color.GREEN);
                            rhealth.setText("Healthy");
                        } else {
                            if ((oreading >= 92 && oreading <= 95) && (preading > 100) && (rreading > 20)) {
                                rhealth.setTextColor(Color.RED);
                                rhealth.setText("Asthma");
                            } else {
                                rhealth.setTextColor(Color.RED);
                                rhealth.setText("unhealthy");
                            }
                        }

                        if ((greading <= 140) && (sreading <= 120 && dreading <= 80) && (preading >= 60 && preading <= 100) && (creading >= 125 && creading <= 200) && (oreading >= 98) && (rreading >= 12 && rreading <= 20)) {
                            status.setTextColor(Color.GREEN);
                            status.setText("Healthy");
                        } else {
                            if (greading > 200 && !((sreading <= 140 && dreading <= 80) && (preading >= 60 && preading <= 100) && (creading >= 125 && creading <= 200)) && ((oreading >= 92 && oreading <= 95) && (preading > 100) && (rreading > 20))) {
                                status.setTextColor(Color.RED);
                                status.setText("Diabetes" + "," + "\n" + "Coronary Artery Disease" + "," + "\n" + "Asthma");
                            } else if ((greading > 140 && greading <= 200) && !((sreading <= 140 && dreading <= 80) && (preading >= 60 && preading <= 100) && (creading >= 125 && creading <= 200)) && ((oreading >= 92 && oreading <= 95) && (preading > 100) && (rreading > 20))) {
                                status.setTextColor(Color.RED);
                                status.setText("Pre-Diabetes" + "," + "\n" + "Coronary Artery Disease" + "," + "\n" + "Asthma");
                            } else if (greading > 200 && !((sreading <= 140 && dreading <= 80) && (preading >= 60 && preading <= 100) && (creading >= 125 && creading <= 200))) {
                                status.setTextColor(Color.RED);
                                status.setText("Diabetes" + "," + "\n" + "Coronary Artery Disease");
                            } else if ((greading > 140 && greading <= 200) && !((sreading <= 140 && dreading <= 80) && (preading >= 60 && preading <= 100) && (creading >= 125 && creading <= 200))) {
                                status.setTextColor(Color.RED);
                                status.setText("Pre-Diabetes" + "," + "\n" + "Coronary Artery Disease");
                            } else if (!((sreading <= 140 && dreading <= 80) && (preading >= 60 && preading <= 100) && (creading >= 125 && creading <= 200)) && ((oreading >= 92 && oreading <= 95) && (preading > 100) && (rreading > 20))) {
                                status.setTextColor(Color.RED);
                                status.setText("CoronaryArtery Disease" + "," + "\n" + "Asthma");
                            } else if ((greading > 140 && greading <= 200) && ((oreading >= 92 && oreading <= 95) && (preading > 100) && (rreading > 20))) {
                                status.setTextColor(Color.RED);
                                status.setText("Pre-Diabetes" + "," + "\n" + "Asthma");
                            } else if (greading > 200 && ((oreading >= 92 && oreading <= 95) && (preading > 100) && (rreading > 20))) {
                                status.setTextColor(Color.RED);
                                status.setText("Diabetes" + "," + "\n" + "Asthma");
                            } else {
                                status.setTextColor(Color.RED);
                                status.setText("Unhealthy");
                            }
                        }
                    }
                }
            }
        });
        return view;
    }
}