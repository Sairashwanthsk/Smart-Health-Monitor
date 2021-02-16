package com.example.smarthealthmonitor;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HeartFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    PulseFragment pulseFragment;
    BloodpressureFragment bpFragment;
    CholesterolFragment cholesterolFragment;
    private FirestoreRecyclerAdapter adapter;

    FirebaseAuth fAuth;
    FirebaseUser user;
    FirebaseFirestore fstore;
    TextView heart_status;
    private Handler Handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_heart, container, false);
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        this.Handler = new Handler();
        this.Handler.postDelayed(refresh, 3000);
        heart_status = (TextView)view.findViewById(R.id.health_status);

        viewPager = (ViewPager)view.findViewById(R.id.heart_view_pager);
        tabLayout = (TabLayout)view.findViewById(R.id.heart_tab_layout);

        pulseFragment = new PulseFragment();
        bpFragment = new BloodpressureFragment();
        cholesterolFragment = new CholesterolFragment();


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

                        if((sreading <= 140 && dreading <= 80) && (preading >= 60 && preading <= 100) && (creading >= 125 && creading <= 200)){
                            heart_status.setTextColor(Color.GREEN);
                            heart_status.setText("Healthy");
                        } else {
                            heart_status.setTextColor(Color.RED);
                            heart_status.setText("Unhealthy");
                        }
                    }
                }
            }
        });

        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), 0);
        viewPagerAdapter.addFragment(pulseFragment, "Pulse");
        viewPagerAdapter.addFragment(bpFragment, "Blood pressure");
        viewPagerAdapter.addFragment(cholesterolFragment, "Cholesterol");
        viewPager.setAdapter(viewPagerAdapter);

        return view;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }
        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitle.add(title);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
        @Override
        public int getCount() {
            return fragments.size();
        }
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }

    private final Runnable refresh = new Runnable() {
        public void run() {
            Handler.postDelayed(refresh, 3000);
        }
    };
}