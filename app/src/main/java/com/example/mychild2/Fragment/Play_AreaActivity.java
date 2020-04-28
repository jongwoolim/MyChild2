package com.example.mychild2.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.mychild2.R;
import com.google.android.material.tabs.TabLayout;

public class Play_AreaActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private  Fragment[] fragment = new Fragment[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play__area);

        tabLayout = (TabLayout)findViewById(R.id.tl_tabs);
        viewPager = (ViewPager)findViewById(R.id.v_pager);

        fragment[0] = new ManipulationFrament();
        fragment[1] = new ScienceFragment();
        fragment[2] = new StackingFragment();
        fragment[3] = new TuneFragment();

        Intent intent = getIntent();
        int fragment_index = intent.getIntExtra("fragment_Index", 0);

        MyViewPager myViewPager = new MyViewPager(getSupportFragmentManager(), fragment);

        viewPager.setAdapter(myViewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private class MyViewPager extends FragmentPagerAdapter{

        Fragment[] fragments;

        public MyViewPager(@NonNull FragmentManager fm, Fragment[] fragment ) {
            super(fm);
            this.fragments = fragment;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "수조작 영역";
                case 1:
                    return "과학 영역";
                case 2:
                    return "쌓기 영역";
                case 3:
                    return "음률 영역";

                default:
                    return "";

            }
        }
    }


}
