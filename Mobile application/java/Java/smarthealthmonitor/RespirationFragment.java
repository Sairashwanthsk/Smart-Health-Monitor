package com.example.smarthealthmonitor;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RespirationFragment extends Fragment {
    private FirebaseFirestore fstore;
    private FirestoreRecyclerAdapter adapter;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    private android.os.Handler Handler;
    TextView respirationstatus;
    RespirationrateFragment respirationrateFragment;
    Spo2Fragment spo2Fragment;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_respiration, container, false);
        this.Handler = new Handler();
        this.Handler.postDelayed(refresh, 1000);
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        respirationstatus = (TextView)view.findViewById(R.id.respiration_status);

        viewPager = (ViewPager)view.findViewById(R.id.respiration_view_pager);
        tabLayout = (TabLayout)view.findViewById(R.id.respiration_tab_layout);

        respirationrateFragment = new RespirationrateFragment();
        spo2Fragment = new Spo2Fragment();

        Query reference = fstore.collection("Users").document(user.getEmail()).collection("All_health_parameters").orderBy("Date", Query.Direction.DESCENDING).limit(1);
        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(getContext(), "Error loading", Toast.LENGTH_SHORT).show();
                }
                for (QueryDocumentSnapshot doc : value){
                    if(doc.exists()){
                        Long oreading = doc.getLong("O2Reading");
                        Long rreading = doc.getLong("RRReading");

                        if((oreading >= 98 && oreading <= 100) && (rreading >= 12 && rreading <= 20)){
                            respirationstatus.setTextColor(Color.GREEN);
                            respirationstatus.setText("Healthy");
                        } else {
                            respirationstatus.setTextColor(Color.RED);
                            respirationstatus.setText("Unhealthy");
                        }
                    }
                }
            }
        });

        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), 0);
        viewPagerAdapter.addFragment(spo2Fragment, "SpO2");
        viewPagerAdapter.addFragment(respirationrateFragment, "Respiration rate");
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
            Handler.postDelayed(refresh, 1000);;
        }
    };
}