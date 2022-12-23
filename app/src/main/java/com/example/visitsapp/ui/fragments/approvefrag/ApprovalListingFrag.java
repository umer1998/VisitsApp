package com.example.visitsapp.ui.fragments.approvefrag;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.visitsapp.R;
import com.example.visitsapp.business.impl.Business;
import com.example.visitsapp.delegate.ResponseCallBack;
import com.example.visitsapp.model.request.LoginRequest;
import com.example.visitsapp.model.responce.ApproveEventRequest;
import com.example.visitsapp.model.responce.ApproveEventResponce;
import com.example.visitsapp.model.responce.EventIds;
import com.example.visitsapp.model.responce.GetPendingApproval;
import com.example.visitsapp.model.responce.GetReportingTeamResponce;
import com.example.visitsapp.model.responce.LoginResponce;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.activities.Login;
import com.example.visitsapp.ui.adapter.PendingApprovalAdapter;
import com.example.visitsapp.ui.fragments.BaseFragment;
import com.example.visitsapp.utils.OnApproveListClick;
import com.example.visitsapp.utils.OnItemClickListener;
import com.example.visitsapp.utils.SharedPrefrences;
import com.example.visitsapp.utils.alert.AlertUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;


public class ApprovalListingFrag extends BaseFragment {

    private RecyclerView recyclerView;
    private MainActivity context;
    private GetReportingTeamResponce getReportingTeamResponce;
    private LinearLayout  llApprove;
    ArrayList<Integer> list = new ArrayList<Integer>();
    private ApproveEventRequest approveEventRequest = new ApproveEventRequest();
    private LinearLayout llcplan;
    private NavigationView navigationView;

    public ApprovalListingFrag(MainActivity context, GetReportingTeamResponce getReportingTeamResponce) {
        this.context = context;
        this.getReportingTeamResponce = getReportingTeamResponce;

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_approval_listing, container, false);

        BottomNavigationView navigationView = getActivity().findViewById(R.id.bottomNavigationView);
        navigationView.setVisibility(View.GONE);
        LinearLayout llcplan = getActivity().findViewById(R.id.cplan);
        llcplan.setVisibility(View.GONE);

        llApprove = view.findViewById(R.id.approve);
        llApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                approveEvent(approveEventRequest);
            }
        });


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        getPendingApproval();
        return view;
    }

    private void approveEvent(ApproveEventRequest approveEventRequest) {
        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }



        Business serviceImp = new Business() ;
        serviceImp.approveEvent(approveEventRequest, new ResponseCallBack<ApproveEventResponce>() {
            @Override
            public void onSuccess(ApproveEventResponce body) {

                if (dialog != null) {
                    dialog.dismiss();
                }


                context.homeFrag();
                context.clearBackStack();



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

    private void setApdapter(ArrayList<GetPendingApproval> body) {
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i< body.size(); i++){
            list.add(body.get(i).id);
        }
        recyclerView.setItemViewCacheSize(body.size());
        PendingApprovalAdapter pendingApprovalAdapter = new PendingApprovalAdapter(context, body, list);
        recyclerView.setAdapter(pendingApprovalAdapter);

        pendingApprovalAdapter.setOnItemClickListener(new OnApproveListClick() {
            @Override
            public void onItemClick(View view, ArrayList<Integer> list) {
                if(list.size() > 0){
                    llApprove.setVisibility(View.VISIBLE);
                } else{
                    llApprove.setVisibility(View.GONE);
                }
                ArrayList<EventIds> eventIds = new ArrayList<>();
                for(int i =0; i< list.size();i++){
                    EventIds eventIds1 = new EventIds();

                    eventIds1.setIds(list.get(i));
                    eventIds.add(eventIds1);
                }
                approveEventRequest.event_status = "1";
                approveEventRequest.ids = eventIds;
            }
        });
    }


    private void getPendingApproval() {
        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }



        Business serviceImp = new Business() ;
        serviceImp.getPendingApproval(getReportingTeamResponce.user_id, new ResponseCallBack<ArrayList<GetPendingApproval>>() {
            @Override
            public void onSuccess(ArrayList<GetPendingApproval> body) {

                setApdapter(body);

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
        return false;
    }
}