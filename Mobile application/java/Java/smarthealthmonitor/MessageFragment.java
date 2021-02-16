package com.example.smarthealthmonitor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.type.Date;

public class MessageFragment extends Fragment {
    private RecyclerView messagelist;
    private FirebaseFirestore fstore;
    private FirestoreRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        messagelist = (RecyclerView)view.findViewById(R.id.message_list);
        fstore = FirebaseFirestore.getInstance();
        Query query = fstore.collection("Notification");
        FirestoreRecyclerOptions<subMessageFragment> options = new FirestoreRecyclerOptions.Builder<subMessageFragment>()
                .setQuery(query, subMessageFragment.class).build();

        adapter = new FirestoreRecyclerAdapter<subMessageFragment, messageviewholder>(options) {
            @NonNull
            @Override
            public messageviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_sub_message, parent, false);
                return new messageviewholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull messageviewholder holder, int position, @NonNull subMessageFragment model) {
                holder.Date.setText(model.getDate());
                holder.Title.setText(model.getTitle());
                holder.Message.setText(model.getMessage());
            }
        };
        messagelist.setHasFixedSize(true);
        messagelist.setLayoutManager(new LinearLayoutManager(getContext()));
        messagelist.setAdapter(adapter);
        return view;
    }



    private class messageviewholder extends RecyclerView.ViewHolder{

        private TextView Date;
        private  TextView Title;
        private TextView Message;
        public messageviewholder(@NonNull View itemView) {
            super(itemView);
            Date = itemView.findViewById(R.id.Date);
            Title = itemView.findViewById(R.id.Title);
            Message = itemView.findViewById(R.id.Message);
        }
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
