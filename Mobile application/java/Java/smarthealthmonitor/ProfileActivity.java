package com.example.smarthealthmonitor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {
    TextView name, dob, age, gender, bloodgroup, height, weight, asthma, alcoholic, bronchiectasis, chd, diabetes, hypoxemia, otherillness, currentmedication, drugallergies;
    ImageView photo;
    ImageButton backbtn;
    DocumentReference reference;
    FirebaseAuth fAuth;
    FirebaseUser user;
    FirebaseFirestore fstore;
    FirebaseStorage storage;
    ImageButton edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        edit = (ImageButton)findViewById(R.id.edit);
        backbtn = findViewById(R.id.back_btn);
        photo = findViewById(R.id.proimage);
        name = findViewById(R.id.proname);
        dob = findViewById(R.id.prodob);
        age = findViewById(R.id.proage);
        gender = findViewById(R.id.progender);
        bloodgroup = findViewById(R.id.probloodgroup);
        height = findViewById(R.id.proheight);
        weight = findViewById(R.id.proweight);
        asthma = findViewById(R.id.proasthma);
        alcoholic = findViewById(R.id.proalcoholic);
        bronchiectasis = findViewById(R.id.probronchiectasis);
        chd = findViewById(R.id.prochd);
        diabetes = findViewById(R.id.prodiabetes);
        hypoxemia = findViewById(R.id.prohypoxemia);
        currentmedication = findViewById(R.id.procurrentmedication);
        otherillness = findViewById(R.id.prootherillness);
        drugallergies = findViewById(R.id.prodrugallergies);

        storage = FirebaseStorage.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });
        reference = fstore.collection("Users").document(user.getEmail());
        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.getResult().exists()){
                            String nameresult = task.getResult().getString("Name");
                            String dobresult = task.getResult().getString("Birthdate");
                            Long ageresult = (Long) task.getResult().get("Age");
                            String genderresult = task.getResult().getString("Gender");
                            String bloodgroupresult = task.getResult().getString("Blood_Group");
                            Long heightresult = (Long) task.getResult().get("Height");
                            Long weightresult = (Long) task.getResult().get("Weight");
                            String asthmaresult = task.getResult().getString("Asthma");
                            String alcoholicresult = task.getResult().getString("Alcoholic");
                            String bronchiectasisresult = task.getResult().getString("Bronchiectasis");
                            String chdresult = task.getResult().getString("Coronary_Artery_Disease");
                            String diabetesresult = task.getResult().getString("Diabetes");
                            String hypoxemiaresult = task.getResult().getString("Hypoxemia");
                            String currentmedicationresult = task.getResult().getString("Current_medication");
                            String otherillnessresult = task.getResult().getString("Other_Illness");
                            String drugallergiesresult = task.getResult().getString("Drug_allergies");

                            name.setText(nameresult);
                            dob.setText(dobresult);
                            age.setText(ageresult+"");
                            gender.setText(genderresult);
                            bloodgroup.setText(bloodgroupresult);
                            height.setText(heightresult+"");
                            weight.setText(weightresult+"");
                            asthma.setText(asthmaresult);
                            alcoholic.setText(alcoholicresult);
                            bronchiectasis.setText(bronchiectasisresult);
                            chd.setText(chdresult);
                            diabetes.setText(diabetesresult);
                            hypoxemia.setText(hypoxemiaresult);
                            currentmedication.setText(currentmedicationresult);
                            otherillness.setText(otherillnessresult);
                            drugallergies.setText(drugallergiesresult);
                        }
                        else{
                            Toast.makeText(ProfileActivity.this, "Error in loading Profile", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Profile Images/" + user.getEmail() + "/profile.jpg");

        try {
            final File file = File.createTempFile("profile", "jpg");
            storageReference.getFile(file)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            ((ImageView) findViewById(R.id.proimage)).setImageBitmap(bitmap);
                        }
                    });
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}