package com.example.smarthealthmonitor;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class GlucoseFragment extends Fragment {

    private RecyclerView glucorecords;
    private FirebaseFirestore fstore;
    private FirestoreRecyclerAdapter adapter;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    private Handler Handler;
    TextView glucostatus;
    SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_glucose, container, false);
        this.Handler = new Handler();
        this.Handler.postDelayed(refresh, 2000);
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshlayout);
        glucorecords = (RecyclerView)view.findViewById(R.id.glucorecords);
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        glucostatus = (TextView)view.findViewById(R.id.gluco_status);

        Query reference = fstore.collection("Users").document(user.getEmail()).collection("All_health_parameters").orderBy("Date", Query.Direction.DESCENDING).limit(1);
        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(getActivity(), "Error loading", Toast.LENGTH_SHORT).show();
                }
                for (QueryDocumentSnapshot doc : value){
                    if(doc.exists()){
                        Long greading = doc.getLong("GReading");
                        if(greading <= 140){
                            glucostatus.setTextColor(Color.GREEN);
                            glucostatus.setText("Normal");
                        }
                        else if(greading > 140 && greading <= 200){
                            glucostatus.setTextColor(Color.RED);
                            glucostatus.setText("Pre-Diabetes");
                        }
                        else if(greading > 200){
                            glucostatus.setTextColor(Color.RED);
                            glucostatus.setText("Diabetes");
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        Query query = (Query) fstore.collection("Users").document(user.getEmail()).collection("All_health_parameters").orderBy("Date", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<glucorecords> options = new FirestoreRecyclerOptions.Builder<glucorecords>()
                .setQuery(query, glucorecords.class).build();
        adapter = new FirestoreRecyclerAdapter<glucorecords, GlucoseFragment.glucorecordsholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull glucorecordsholder holder, int position, @NonNull com.example.smarthealthmonitor.glucorecords model) {

                holder.GReading.setText(model.getGReading() + "mg/dl");
                holder.Month.setText(model.getMonth());
                holder.Date.setText(model.getDate());
                holder.status.setText(model.getGStatus());
            }

            @NonNull
            @Override
            public GlucoseFragment.glucorecordsholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_glucorecords, parent, false);
                return new glucorecordsholder(view);
            }
        };
        glucorecords.setHasFixedSize(true);
        glucorecords.setLayoutManager(new LinearLayoutManager(getActivity()));
        glucorecords.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                glucorecords.setHasFixedSize(true);
                glucorecords.setLayoutManager(new LinearLayoutManager(getActivity()));
                glucorecords.setAdapter(adapter);

                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    private class glucorecordsholder extends RecyclerView.ViewHolder{

        private TextView GReading, Month, Date, status;

        public glucorecordsholder(@NonNull View itemView) {
            super(itemView);
            GReading = itemView.findViewById(R.id.glucoreading);
            Month = itemView.findViewById(R.id.glucomonth);
            Date = itemView.findViewById(R.id.glucodate);
            status = itemView.findViewById(R.id.gluco_status2);
        }
    }

    private final Runnable refresh = new Runnable() {
        public void run() {
            adapter.notifyDataSetChanged();
            Handler.postDelayed(refresh, 2000);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        Handler.removeCallbacks(refresh);
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}