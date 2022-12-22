package com.example.visitsapp.ui.fragments.executedevent;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.visitsapp.R;
import com.example.visitsapp.model.responce.GetLeavesResponce;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.fragments.BaseFragment;
import com.example.visitsapp.ui.fragments.leaves.ApproveLeavesFrag;
import com.example.visitsapp.ui.fragments.leaves.LeavesFrag;
import com.example.visitsapp.ui.fragments.leaves.PendingLeavesFrag;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ExecutedEventsFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MainActivity context;
    private ViewPagerAdapter viewPagerAdapter;

    public ExecutedEventsFragment(MainActivity context) {
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_executed_events, container, false);

        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Current \nMonth"));
        tabLayout.addTab(tabLayout.newTab().setText("Previous \nMonth"));
//        tabLayout.addTab(tabLayout.newTab().setText("All"));


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        addTabs();


        return view;
    }

    private void addTabs() {
        viewPagerAdapter.addFrag(new CurrentExecutedFrag(context), "Current Month");
        viewPagerAdapter.addFrag(new PreMonthExeEventFrag(context), "Previous Month");
//        viewPagerAdapter.addFrag(new AllExecutedEventFrag(context), "All");


        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}