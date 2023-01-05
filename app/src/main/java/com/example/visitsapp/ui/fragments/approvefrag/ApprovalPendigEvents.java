package com.example.visitsapp.ui.fragments.approvefrag;

import android.app.Activity;
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
import com.example.visitsapp.model.responce.GetReportingTeamResponce;
import com.example.visitsapp.model.responce.PlansData;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.fragments.BaseFragment;
import com.example.visitsapp.ui.fragments.leaves.ApproveLeavesFrag;
import com.example.visitsapp.ui.fragments.leaves.LeavesFrag;
import com.example.visitsapp.ui.fragments.leaves.PendingLeavesFrag;
import com.example.visitsapp.utils.alert.AlertUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ApprovalPendigEvents extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MainActivity context;
    ViewPagerAdapter viewPagerAdapter;

    private ArrayList<PlansData> seltPendApproveList = new ArrayList<>();
    ArrayList<GetReportingTeamResponce> reportingTeamResponces = new ArrayList<>();

    public ApprovalPendigEvents(MainActivity context) {
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
        View view = inflater.inflate(R.layout.approval_pending_event, container, false);
        context.bottomNavigationView.setVisibility(View.GONE);
        context.llcplan.setVisibility(View.GONE);

        getReportingTeam();
        getPendingEvents();

        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("My Events"));
        tabLayout.addTab(tabLayout.newTab().setText("Team Events"));
        addTabs();

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

    private void addTabs() {

        viewPagerAdapter.addFrag(new SelfPendingApprovalEvent(context, seltPendApproveList),"Pending");
        viewPagerAdapter.addFrag(new ReportingTeamFragment(context, reportingTeamResponces), "Approved");

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

    private void getPendingEvents() {
        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }



        Business serviceImp = new Business() ;
        serviceImp.getRejectedEvent( new ResponseCallBack<ArrayList<PlansData>>() {
            @Override
            public void onSuccess(ArrayList<PlansData> body) {

               seltPendApproveList = body;


                if (dialog != null) {
                    dialog.dismiss();
                }


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

    private void getReportingTeam() {
        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }



        Business serviceImp = new Business() ;
        serviceImp.getReportingTeam(new ResponseCallBack<ArrayList<GetReportingTeamResponce>>() {
            @Override
            public void onSuccess(ArrayList<GetReportingTeamResponce> body) {


                reportingTeamResponces = body;

                if (dialog != null) {
                    dialog.dismiss();
                }


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
}