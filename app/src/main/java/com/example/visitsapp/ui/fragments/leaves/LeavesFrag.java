package com.example.visitsapp.ui.fragments.leaves;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.visitsapp.R;
import com.example.visitsapp.business.impl.Business;
import com.example.visitsapp.delegate.ResponseCallBack;
import com.example.visitsapp.model.request.LoginRequest;
import com.example.visitsapp.model.responce.GetLeavesResponce;
import com.example.visitsapp.model.responce.LoginResponce;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.activities.Login;
import com.example.visitsapp.ui.fragments.BaseFragment;
import com.example.visitsapp.utils.SharedPrefrences;
import com.example.visitsapp.utils.alert.AlertUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class LeavesFrag extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MainActivity context;

    public LeavesFrag(MainActivity context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leaves, container, false);

        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Pending \nLeaves"));
        tabLayout.addTab(tabLayout.newTab().setText("Approved \nLeaves"));
        getLeaves();

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

        return view;
    }

    private void getLeaves() {
        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }




        Business serviceImp = new Business() ;
        serviceImp.getLeaves( new ResponseCallBack<ArrayList<GetLeavesResponce>>() {
            @Override
            public void onSuccess(ArrayList<GetLeavesResponce> body) {


                if (dialog != null) {
                    dialog.dismiss();
                }

                ArrayList<GetLeavesResponce> pendingLeaves = new ArrayList<>();
                ArrayList<GetLeavesResponce> approveLeaves = new ArrayList<>();

                for(int i =0 ; i< body.size(); i++){
                    if(body.get(i).status.equalsIgnoreCase("pending")){
                        pendingLeaves.add(body.get(i));
                    } else {
                        approveLeaves.add(body.get(i));
                    }
                }
                addTabs(pendingLeaves, approveLeaves);

            }

            @Override
            public void onFailure(String message) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                AlertUtils.showAlert(context, message);


            }
        });
    }

    private void addTabs(ArrayList<GetLeavesResponce> pendingLeaves, ArrayList<GetLeavesResponce> approveLeaves) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(context.getSupportFragmentManager());
        viewPagerAdapter.addFrag(new PendingLeavesFrag(context, pendingLeaves),"Pending");
        viewPagerAdapter.addFrag(new ApproveLeavesFrag(context, approveLeaves), "Approved");

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