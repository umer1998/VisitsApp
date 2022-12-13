package com.example.visitsapp.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.visitsapp.R;
import com.example.visitsapp.business.PostFeedBackResponce;
import com.example.visitsapp.business.impl.Business;
import com.example.visitsapp.delegate.ResponseCallBack;
import com.example.visitsapp.model.request.PostFeedBackRequest;
import com.example.visitsapp.model.request.ReplaceEventRequest;
import com.example.visitsapp.model.responce.DashboardResponce;
import com.example.visitsapp.model.responce.LoginResponce;
import com.example.visitsapp.model.responce.PlansData;
import com.example.visitsapp.model.responce.TodayPlans;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.activities.Login;
import com.example.visitsapp.ui.adapter.TodaysPlanAdapter;
import com.example.visitsapp.utils.SharedPrefrences;
import com.example.visitsapp.utils.alert.AlertUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private MainActivity context;
    private  LinearLayout llcplan;
    private NavigationView navigationView;
    private TextView tvPendingCount, tvPlannedCount, tvLeavesCount,
                tvExecutedCount, tvName, tvDesignation;
    private RecyclerView recyclerView;
    private LoginResponce loginResponce;
    private CircleImageView profileImage;

    private RelativeLayout rlplanned, rlExecuted, rlPending, rlLeaves;

    private SwipeRefreshLayout swipeRefreshLayout;

    private CardView cardOdrawer;


    public HomeFragment(MainActivity context, LinearLayout llcplan, NavigationView navigationView) {
        this.context = context;
        this.llcplan = llcplan;
        this.navigationView = navigationView;
    }


    @Override
    public void onResume() {

        BottomNavigationView navBar = getActivity().findViewById(R.id.bottomNavigationView);
        navBar.setVisibility(View.VISIBLE);
        llcplan.setVisibility(View.VISIBLE);

        navBar.setSelectedItemId(R.id.home);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rlExecuted = view.findViewById(R.id.executed);
        rlExecuted.setOnClickListener(this);
        rlplanned = view.findViewById(R.id.planned);
        rlplanned.setOnClickListener(this);
        rlLeaves = view.findViewById(R.id.leaves);
        rlLeaves.setOnClickListener(this);
        rlPending = view.findViewById(R.id.pending);
        rlPending.setOnClickListener(this);
        cardOdrawer = view.findViewById(R.id.odrawer);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getExecutionEvent();
                getDashBoardData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        cardOdrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.drawer.openDrawer(Gravity.LEFT);
            }
        });
        loginResponce = SharedPrefrences.getInstance().getloginResponse();

        tvExecutedCount = view.findViewById(R.id.executedCount);
        tvLeavesCount = view.findViewById(R.id.leavesCount);
        tvPendingCount = view.findViewById(R.id.pendingcount);
        tvPlannedCount = view.findViewById(R.id.plannedCount);


        profileImage = view.findViewById(R.id.profile_image);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                selectimage();
            }
        });

        tvName = view.findViewById(R.id.name);
        tvName.setText(loginResponce.fullname);

        tvDesignation = view.findViewById(R.id.designation);
        tvDesignation.setText(loginResponce.designation);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));


        setProfileImage();
        getDashBoardData();



        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void selectimage() {
        if(!checkCameraPermission()){
            requestCameraPermission();
        } else if(!checkExternalPermission()){
            requestExternalPermission();
        } else {
            pickImage();
        }
    }

    private void pickImage() {

    }

    private void requestExternalPermission() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    private boolean checkExternalPermission() {
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

            return true;
        } else {
            return false;
        }

    }

    private void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkCameraPermission() {
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    private void setProfileImage() {
        if(SharedPrefrences.getInstance().getProfileImage() != null){
            Picasso.get()
                    .load(SharedPrefrences.getInstance().getProfileImage())
                    .into(profileImage);
        }
    }

    private void setAdapter(ArrayList<TodayPlans> plans) {
        TodaysPlanAdapter todaysPlanAdapter = new TodaysPlanAdapter(context, plans);
        recyclerView.setAdapter(todaysPlanAdapter);
    }

    private void getDashBoardData() {


        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }

        Business serviceImp = new Business() ;
        serviceImp.getDashboard( new ResponseCallBack<DashboardResponce>() {
            @Override
            public void onSuccess(DashboardResponce body) {



                tvExecutedCount.setText(String.valueOf(body.event_summary.visits_executed));
                tvPendingCount.setText(String.valueOf(body.event_summary.visit_pending));
                tvPlannedCount.setText(String.valueOf(body.event_summary.visit_planned));
                tvLeavesCount.setText(String.valueOf(body.event_summary.leaves));
                setAdapter(body.plans);
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

    private void getExecutionEvent() {
        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }



        Business serviceImp = new Business() ;
        serviceImp.getExecutionEvent( new ResponseCallBack<ArrayList<PlansData>>() {
            @Override
            public void onSuccess(ArrayList<PlansData> body) {

                if(body.size() > 0){
                    SharedPrefrences.getInstance().setExecutedEvent(body);
//                    recyclerView.setVisibility(View.VISIBLE);

                } else {
                    SharedPrefrences.getInstance().setExecutedEvent(null);
//                    recyclerView.setVisibility(View.GONE);
//                    tvNoRecord.setVisibility(View.VISIBLE);
                }


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


    @Override
    public boolean onBackPressed() {
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.executed:
                context.getExecutedEvent();
                context.bottomNavigationView.setVisibility(View.VISIBLE);
                context.llcplan.setVisibility(View.VISIBLE);
                break;

            case R.id.planned:
                context.calenderViewFrag();
                context.bottomNavigationView.setVisibility(View.GONE);
                context.llcplan.setVisibility(View.GONE);
                break;


            case R.id.leaves:
                context.leavesFrag();
                context.bottomNavigationView.setVisibility(View.GONE);
                context.llcplan.setVisibility(View.GONE);
                break;

            case R.id.pending:
                context.executionFrag();
                context.bottomNavigationView.setVisibility(View.VISIBLE);
                context.llcplan.setVisibility(View.VISIBLE);
                break;
        }
    }
}