package com.example.smarthealthmonitor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class BloodpressureFragment extends Fragment {
    private RecyclerView bprecords;
    private FirebaseFirestore fstore;
    private FirestoreRecyclerAdapter adapter;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    private Handler Handler;
    SwipeRefreshLayout refreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bloodpressure, container, false);
        this.Handler = new Handler();
        this.Handler.postDelayed(refresh, 1000);
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshlayout);
        bprecords = (RecyclerView) view.findViewById(R.id.bprecords);
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();

        Query query = (Query) fstore.collection("Users").document(user.getEmail()).collection("All_health_parameters").orderBy("Date", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Bprecords> options = new FirestoreRecyclerOptions.Builder<Bprecords>()
                .setQuery(query, Bprecords.class).build();

        adapter = new FirestoreRecyclerAdapter<Bprecords, BloodpressureFragment.Bprecordsholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BloodpressureFragment.Bprecordsholder holder, int position, @NonNull com.example.smarthealthmonitor.Bprecords model) {

                holder.BpReading.setText(model.getSReading() + "/" + model.getDReading() + "mmHg");
                holder.Month.setText(model.getMonth());
                holder.Date.setText(model.getDate());
                holder.status.setText(model.getBpStatus());
            }

            @NonNull
            @Override
            public BloodpressureFragment.Bprecordsholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_bprecords, parent, false);
                return new BloodpressureFragment.Bprecordsholder(view);
            }
        };
        bprecords.setHasFixedSize(true);
        bprecords.setLayoutManager(new LinearLayoutManager(getActivity()));
        bprecords.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bprecords.setHasFixedSize(true);
                bprecords.setLayoutManager(new LinearLayoutManager(getActivity()));
                bprecords.setAdapter(adapter);

                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    private class Bprecordsholder extends RecyclerView.ViewHolder{

        private TextView BpReading, Month, Date, status;

        public Bprecordsholder(@NonNull View itemView) {
            super(itemView);
            BpReading = itemView.findViewById(R.id.bpreading);
            Month = itemView.findViewById(R.id.bpmonth);
            Date = itemView.findViewById(R.id.bpdate);
            status = itemView.findViewById(R.id.bp_status);
        }
    }
    private final Runnable refresh = new Runnable() {
        public void run() {
            adapter.notifyDataSetChanged();
            Handler.postDelayed(refresh, 1000);
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