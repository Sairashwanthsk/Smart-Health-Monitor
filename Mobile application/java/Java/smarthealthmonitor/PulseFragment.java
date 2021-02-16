package com.example.smarthealthmonitor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class PulseFragment extends Fragment {

    private RecyclerView pulserecords;
    private FirebaseFirestore fstore;
    private FirestoreRecyclerAdapter adapter;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    private Handler Handler;
    SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pulse, container, false);
        this.Handler = new Handler();
        this.Handler.postDelayed(refresh, 2000);
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshlayout);
        pulserecords = (RecyclerView)view.findViewById(R.id.pulserecords);
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();

        Query query = (Query) fstore.collection("Users").document(user.getEmail()).collection("All_health_parameters").orderBy("Date", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<pulserecords> options = new FirestoreRecyclerOptions.Builder<pulserecords>()
                .setQuery(query, pulserecords.class).build();

        adapter = new FirestoreRecyclerAdapter<pulserecords, PulseFragment.pulserecordsholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull pulserecordsholder holder, int position, @NonNull com.example.smarthealthmonitor.pulserecords model) {

                holder.PReading.setText(model.getPReading() + "bpm");
                holder.Month.setText(model.getMonth());
                holder.Date.setText(model.getDate());
                holder.status.setText(model.getPStatus());
            }

            @NonNull
            @Override
            public PulseFragment.pulserecordsholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_pulserecords, parent, false);
                return new pulserecordsholder(view);
            }
        };
        pulserecords.setHasFixedSize(true);
        pulserecords.setLayoutManager(new LinearLayoutManager(getActivity()));
        pulserecords.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pulserecords.setHasFixedSize(true);
                pulserecords.setLayoutManager(new LinearLayoutManager(getActivity()));
                pulserecords.setAdapter(adapter);

                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });

        return view;
    }



    private class pulserecordsholder extends RecyclerView.ViewHolder{

        private TextView PReading, Month, Date, status;

        public pulserecordsholder(@NonNull View itemView) {
            super(itemView);
            PReading = itemView.findViewById(R.id.pulsereading);
            Month = itemView.findViewById(R.id.pulsemonth);
            Date = itemView.findViewById(R.id.pulsedate);
            status = itemView.findViewById(R.id.pulse_status);
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