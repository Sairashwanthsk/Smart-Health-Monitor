package com.example.smarthealthmonitor;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Personalinformation extends Fragment implements AdapterView.OnItemSelectedListener{
    EditText rName, rBirthdate, rAge, rHeight, rWeight;
    Button rProceed2;
    ImageButton birthdatebtn, addphoto;
    ProgressBar progressBar;
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    FirebaseUser user;
    FirebaseStorage storage;
    StorageReference reference;
    DatePickerDialog datepickerdialog;
    Calendar cal;
    RadioButton male, female, others;
    ImageView swipe;
    Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personalinformation, container, false);
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();
        Spinner bloodgroup = (Spinner)view.findViewById(R.id.bloodgroupselector);
        rName = (EditText)view.findViewById(R.id.rname);
        rBirthdate = (EditText)view.findViewById(R.id.rbirthdate);
        rAge = (EditText)view.findViewById(R.id.rage);
        rHeight = (EditText)view.findViewById(R.id.rheight);
        rWeight = (EditText)view.findViewById(R.id.rweight);
        fstore = FirebaseFirestore.getInstance();
        rProceed2 = (Button)view.findViewById(R.id.rproceed2);
        progressBar = (ProgressBar)view.findViewById(R.id.progressbar);
        birthdatebtn = (ImageButton)view.findViewById(R.id.datepicker);
        swipe = (ImageView)view.findViewById(R.id.swipenext);
        addphoto = (ImageButton)view.findViewById(R.id.addphoto);
        male = (RadioButton)view.findViewById(R.id.male);
        female = (RadioButton)view.findViewById(R.id.female);
        others = (RadioButton)view.findViewById(R.id.others);

        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosefile();
            }
        });

        birthdatebtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                datepickerdialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        rBirthdate.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                    }
                }, year, month, day);
                datepickerdialog.show();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.bloodgroup, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodgroup.setAdapter(adapter);
        bloodgroup.setOnItemSelectedListener(this);

        rProceed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = rName.getText().toString();
                String birthdate = rBirthdate.getText().toString();
                String bloodgroupresult = bloodgroup.getSelectedItem().toString();
                String age = male.getText().toString();
                String height = male.getText().toString();
                String weight = male.getText().toString();
                String g1 = male.getText().toString();
                String g2 = female.getText().toString();
                String g3 = others.getText().toString();
                DocumentReference documentReference = fstore.collection("Users").document(user.getEmail());

                if (male != null || female != null || others != null) {
                    if (male.isChecked()) {
                        HashMap<Object, Object> profile = new HashMap<Object, Object>();
                        profile.put("Gender", g1);
                        documentReference.set(profile, SetOptions.merge());
                    } else if (female.isChecked()) {
                        HashMap<Object, Object> profile = new HashMap<Object, Object>();
                        profile.put("Gender", g2);
                        documentReference.set(profile, SetOptions.merge());
                    } else if (others.isChecked()) {
                        HashMap<Object, Object> profile = new HashMap<Object, Object>();
                        profile.put("Gender", g3);
                        documentReference.set(profile, SetOptions.merge());
                    }
                } else {
                    Toast.makeText(getContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
                }
                if (bloodgroupresult != null && age!=null && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(birthdate) && height!=null && weight!=null) {
                    progressBar.setVisibility(View.VISIBLE);
                    Long ageresult = Long.valueOf(rAge.getText().toString());
                    Long heightresult = Long.valueOf(rHeight.getText().toString());
                    Long weightresult = Long.valueOf(rWeight.getText().toString());
                    HashMap<Object, Object> profile = new HashMap<Object, Object>();
                    profile.put("Name", name);
                    profile.put("Birthdate", birthdate);
                    profile.put("Age", ageresult);
                    profile.put("Blood_Group", bloodgroupresult);
                    profile.put("Height", heightresult);
                    profile.put("Weight", weightresult);

                    documentReference.set(profile, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getActivity(), "Personal information updated Successfully", Toast.LENGTH_SHORT).show();

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Healthinformation healthinformation = new Healthinformation();
                                            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                                            transaction.replace(R.id.pilayout, healthinformation);
                                            transaction.commit();
                                            swipe.setVisibility(View.VISIBLE);
                                        }
                                    }, 1000);
                                }
                            });
                } else {
                    Toast.makeText(getContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    private void choosefile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            addphoto.setImageURI(imageUri);
            uploadphoto();
        }
    }

    private void uploadphoto() {
        ProgressDialog pd = new ProgressDialog(getActivity());
        StorageReference profileref = reference.child("Profile Images/" + user.getEmail() + "/profile.jpg");
        pd.setTitle("Uploading Image...");
        pd.show();
        profileref.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Image uploaded", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Percentage: " + (int)progressPercent + "%");
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String bloodgroupresult = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}