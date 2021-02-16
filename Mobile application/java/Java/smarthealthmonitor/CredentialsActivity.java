package com.example.smarthealthmonitor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class CredentialsActivity extends AppCompatActivity {

    private ViewPager viewpager;
    private TabLayout tablayout;
    private loginFragment loginFragment;
    private registerFragment registerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credentials);
        viewpager = findViewById(R.id.view_pager);
        tablayout = findViewById(R.id.tabLayout);
        loginFragment = new loginFragment();
        registerFragment = new registerFragment();

        TextView contactlink = (TextView) findViewById(R.id.contactlink);
        contactlink.setText(Html.fromHtml("<a href=\"mailto:support@smarthealthmonitor.com\">support@smarthealthmonitor.com</a>"));
        contactlink.setMovementMethod(LinkMovementMethod.getInstance());

        tablayout.setupWithViewPager(viewpager);
        viewPagerAdapter viewPagerAdapter = new viewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(loginFragment, "LOGIN");
        viewPagerAdapter.addFragment(registerFragment, "REGISTER");
        viewpager.setAdapter(viewPagerAdapter);

        tablayout.getTabAt(0).setIcon(R.drawable.loginlogo);
        tablayout.getTabAt(1).setIcon(R.drawable.createlogo);
    }

    static class viewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public viewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title){
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
}