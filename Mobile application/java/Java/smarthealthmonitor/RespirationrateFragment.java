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

public class RespirationrateFragment extends Fragment {

    private RecyclerView respirationraterecords;
    private FirebaseFirestore fstore;
    private FirestoreRecyclerAdapter adapter;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    private android.os.Handler Handler;
    SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_respirationrate, container, false);
        this.Handler = new Handler();
        this.Handler.postDelayed(refresh, 2000);
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshlayout);
        respirationraterecords = (RecyclerView)view.findViewById(R.id.respirationraterecords);
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();

        Query query = (Query) fstore.collection("Users").document(user.getEmail()).collection("All_health_parameters").orderBy("Date", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<respirationraterecords> options = new FirestoreRecyclerOptions.Builder<respirationraterecords>()
                .setQuery(query, respirationraterecords.class).build();
        adapter = new FirestoreRecyclerAdapter<respirationraterecords, RespirationrateFragment.respirationraterecordsholder>(options) {
            @NonNull
            @Override
            public RespirationrateFragment.respirationraterecordsholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_respirationraterecords, parent, false);
                return new respirationraterecordsholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull RespirationrateFragment.respirationraterecordsholder holder, int position, @NonNull com.example.smarthealthmonitor.respirationraterecords model) {
                holder.RRReading.setText(model.getRRReading() + "bpm");
                holder.Month.setText(model.getMonth());
                holder.Date.setText(model.getDate());
                holder.status.setText(model.getRrStatus());
            }
        };
        respirationraterecords.setHasFixedSize(true);
        respirationraterecords.setLayoutManager(new LinearLayoutManager(getContext()));
        respirationraterecords.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                respirationraterecords.setHasFixedSize(true);
                respirationraterecords.setLayoutManager(new LinearLayoutManager(getContext()));
                respirationraterecords.setAdapter(adapter);

                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    public class respirationraterecordsholder extends RecyclerView.ViewHolder {

        private TextView RRReading, Month, Date, status;

        public respirationraterecordsholder(@NonNull View itemView) {
            super(itemView);
            RRReading = itemView.findViewById(R.id.respirationratereading);
            Month = itemView.findViewById(R.id.respirationratemonth);
            Date = itemView.findViewById(R.id.respirationratedate);
            status = itemView.findViewById(R.id.respirationratestatus);
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