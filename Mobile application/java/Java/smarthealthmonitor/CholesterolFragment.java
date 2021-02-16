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


public class CholesterolFragment extends Fragment {

    private RecyclerView cholesterolrecords;
    private FirebaseFirestore fstore;
    private FirestoreRecyclerAdapter adapter;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    private android.os.Handler Handler;
    SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cholesterol, container, false);
        this.Handler = new Handler();
        this.Handler.postDelayed(refresh, 1000);
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshlayout);
        cholesterolrecords = (RecyclerView) view.findViewById(R.id.cholesterolrecords);
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();

        Query query = (Query) fstore.collection("Users").document(user.getEmail()).collection("All_health_parameters").orderBy("Date", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Cholesterolrecords> options = new FirestoreRecyclerOptions.Builder<Cholesterolrecords>()
                .setQuery(query, Cholesterolrecords.class).build();

        adapter = new FirestoreRecyclerAdapter<Cholesterolrecords, CholesterolFragment.Cholesterolrecordsholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Cholesterolrecordsholder holder, int position, @NonNull com.example.smarthealthmonitor.Cholesterolrecords model) {

                holder.CReading.setText(model.getCReading() + "mg/dl");
                holder.Month.setText(model.getMonth());
                holder.Date.setText(model.getDate());
                holder.status.setText(model.getCStatus());
            }

            @NonNull
            @Override
            public CholesterolFragment.Cholesterolrecordsholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_cholesterolrecords, parent, false);
                return new CholesterolFragment.Cholesterolrecordsholder(view);
            }
        };
        cholesterolrecords.setHasFixedSize(true);
        cholesterolrecords.setLayoutManager(new LinearLayoutManager(getActivity()));
        cholesterolrecords.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cholesterolrecords.setHasFixedSize(true);
                cholesterolrecords.setLayoutManager(new LinearLayoutManager(getActivity()));
                cholesterolrecords.setAdapter(adapter);

                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });

        return view;
    }
    private class Cholesterolrecordsholder extends RecyclerView.ViewHolder{

        private TextView CReading, Month, Date, status;

        public Cholesterolrecordsholder(@NonNull View itemView) {
            super(itemView);
            CReading = itemView.findViewById(R.id.creading);
            Month = itemView.findViewById(R.id.cmonth);
            Date = itemView.findViewById(R.id.cdate);
            status = itemView.findViewById(R.id.c_status);
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