package com.example.visitsapp.ui.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
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
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.example.visitsapp.model.responce.GetLeavesResponce;
import com.example.visitsapp.model.responce.LoginResponce;
import com.example.visitsapp.model.responce.PlansData;
import com.example.visitsapp.model.responce.TodayPlans;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.activities.Login;
import com.example.visitsapp.ui.adapter.TodaysPlanAdapter;
import com.example.visitsapp.ui.dialoguefragmens.CreateEventDialogue2;
import com.example.visitsapp.utils.OnItemClickListener;
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


    private RelativeLayout rlPendingforapproval, rlLeaves, rlPendingforExecution, rlVisitExecute, rlVisitNotExecute;

    private TextView tvPlanned, tvPendingforapproval, tvLeaves, tvPendingforExecution, tvVisitExecute, tvVisitNotExecute, yvName, tvDesgniation;
    private RecyclerView recyclerView;
    private LoginResponce loginResponce;
    private CircleImageView profileImage;

    private RelativeLayout rlDrawer;




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


        yvName = view.findViewById(R.id.name);
        tvDesgniation = view.findViewById(R.id.designation);

        LoginResponce responce = SharedPrefrences.getInstance().getloginResponse();
        yvName.setText(responce.fullname);
        tvDesgniation.setText(responce.designation);

        rlLeaves = view.findViewById(R.id.rlLeaves);
        rlLeaves.setOnClickListener(this);

        rlPendingforapproval = view.findViewById(R.id.rlPendingforapproval);
        rlPendingforapproval.setOnClickListener(this);

        rlPendingforExecution = view.findViewById(R.id.rlPendingforExecution);
        rlPendingforExecution.setOnClickListener(this);

        rlVisitExecute = view.findViewById(R.id.rlVisitExecute);
        rlVisitExecute.setOnClickListener(this);

        rlVisitNotExecute = view.findViewById(R.id.rlVisitNotExecute);
        rlVisitNotExecute.setOnClickListener(this);

        rlDrawer = view.findViewById(R.id.drawer);
        rlDrawer.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                DrawerLayout navDrawer = context.drawer;
                // If the navigation drawer is not open then open it, if its already open then close it.
                if(!navDrawer.isDrawerOpen(Gravity.START)){
                    navDrawer.openDrawer(Gravity.START);
                } else {
                    navDrawer.closeDrawer(Gravity.END);
                }
            }

        });

        tvLeaves = view.findViewById(R.id.tvLeaves);
        tvPendingforapproval = view.findViewById(R.id.tvPendingforapproval);
        tvPlanned = view.findViewById(R.id.planned);
        tvPendingforExecution = view.findViewById(R.id.tvPendingforExecution);
        tvVisitExecute = view.findViewById(R.id.tvVisitExecute);
        tvVisitNotExecute = view.findViewById(R.id.tvVisitNotExecute);

        swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getExecutionEvent();
                getDashBoardData();
                context.bottomNavigationView.setVisibility(View.VISIBLE);
                context.llcplan.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        loginResponce = SharedPrefrences.getInstance().getloginResponse();



        profileImage = view.findViewById(R.id.profile_image);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                selectimage();
            }
        });



        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));


        setProfileImage();
        getDashBoardData();
        getExecutionEvent();



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
//
        TodaysPlanAdapter todaysPlanAdapter = new TodaysPlanAdapter(context, plans);
        recyclerView.setAdapter(todaysPlanAdapter);

        todaysPlanAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                PlansData plansData = new PlansData();
                plansData.planned_on = plans.get(position).planned_on;
                plansData.event = plans.get(position).event;
                plansData.id = plans.get(position).id;
                plansData.time = plans.get(position).time;
                plansData.purpose_child = plans.get(position).purposechild;
                plansData.area = plans.get(position).area;
                plansData.region = plans.get(position).region;
                plansData.event_purpose = plans.get(position).event_purpose;
                plansData.status = plans.get(position).status;


                CreateEventDialogue2 dialog = new CreateEventDialogue2(context,  plansData);
                dialog.show(context.getSupportFragmentManager(), "MyDialogFragment");

            }
        });


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


                tvPendingforapproval.setText(String.valueOf(body.event_summary.pending_approval));
                tvLeaves.setText(String.valueOf(body.event_summary.leaves));
                tvPendingforExecution.setText(String.valueOf(body.event_summary.pending_executed));
                tvVisitExecute.setText(String.valueOf(body.event_summary.visits_executed));
                tvVisitNotExecute.setText(String.valueOf(body.event_summary.unexecuted));

                tvPlanned.setText(String.valueOf(body.event_summary.pending_approval + body.event_summary.pending_executed+
                        body.event_summary.leaves + body.event_summary.unexecuted + body.event_summary.visits_executed));


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
            case R.id.rlLeaves:
                getLeaves();
                break;

            case R.id.rlPendingforapproval:
                context.calenderViewFrag();
                break;

            case R.id.rlPendingforExecution:
                context.executionFrag();
                break;

            case R.id.rlVisitExecute:
                context.executedCompletedEvent();
                break;

            case R.id.rlVisitNotExecute:
                context.getUnExecutedEvents();
                break;
        }
    }
    public void getLeaves() {
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
                context.leavesFrag(pendingLeaves, approveLeaves);

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