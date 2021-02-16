package com.example.smarthealthmonitor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
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


public class Spo2Fragment extends Fragment {
    private RecyclerView spo2records;
    private FirebaseFirestore fstore;
    private FirestoreRecyclerAdapter adapter;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    private android.os.Handler Handler;
    SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spo2, container, false);
        this.Handler = new Handler();
        this.Handler.postDelayed(refresh, 1000);
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshlayout);
        spo2records = (RecyclerView) view.findViewById(R.id.spo2records);
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();

        Query query = (Query) fstore.collection("Users").document(user.getEmail()).collection("All_health_parameters").orderBy("Date", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<spo2records> options = new FirestoreRecyclerOptions.Builder<spo2records>()
                .setQuery(query, spo2records.class).build();
        adapter = new FirestoreRecyclerAdapter<spo2records, Spo2Fragment.spo2recordsholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull spo2recordsholder holder, int position, @NonNull com.example.smarthealthmonitor.spo2records model) {
                holder.O2Reading.setText(model.getO2Reading() + "%");
                holder.Month.setText(model.getMonth());
                holder.Date.setText(model.getDate());
                holder.status.setText(model.getO2Status());
            }

            @NonNull
            @Override
            public Spo2Fragment.spo2recordsholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_spo2records, parent, false);
                return new spo2recordsholder(view);
            }
        };
        spo2records.setHasFixedSize(true);
        spo2records.setLayoutManager(new LinearLayoutManager(getActivity()));
        spo2records.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                spo2records.setHasFixedSize(true);
                spo2records.setLayoutManager(new LinearLayoutManager(getActivity()));
                spo2records.setAdapter(adapter);

                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    private final Runnable refresh = new Runnable() {
        public void run() {
            adapter.notifyDataSetChanged();
            Handler.postDelayed(refresh, 2000);
        }
    };

    public class spo2recordsholder extends RecyclerView.ViewHolder{

        private TextView O2Reading, Month, Date, status;

        public spo2recordsholder(@NonNull View itemView) {
            super(itemView);
            O2Reading = itemView.findViewById(R.id.spo2reading);
            Month = itemView.findViewById(R.id.spo2month);
            Date = itemView.findViewById(R.id.spo2date);
            status = itemView.findViewById(R.id.spo2status);
        }
    }
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