package com.example.visitsapp.ui.fragments.approvefrag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.business.impl.Business;
import com.example.visitsapp.delegate.ResponseCallBack;
import com.example.visitsapp.model.responce.ApproveEventRequest;
import com.example.visitsapp.model.responce.ApproveEventResponce;
import com.example.visitsapp.model.responce.EventIds;
import com.example.visitsapp.model.responce.GetPendingApproval;
import com.example.visitsapp.model.responce.GetReportingTeamResponce;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.adapter.PendingApprovalAdapter;
import com.example.visitsapp.ui.fragments.BaseFragment;
import com.example.visitsapp.utils.OnApproveListClick;
import com.example.visitsapp.utils.alert.AlertUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class ApprovalListingFrag extends BaseFragment {

    private RecyclerView recyclerView;
    private MainActivity context;
    private GetReportingTeamResponce getReportingTeamResponce;
    private LinearLayout  llApprove, llReject;
    ArrayList<Integer> list = new ArrayList<Integer>();
    private ApproveEventRequest approveEventRequest = new ApproveEventRequest();
    private LinearLayout llcplan;
    private NavigationView navigationView;
    PendingApprovalAdapter pendingApprovalAdapter;
    private TextView selectAll, tvNoRecord;
    ArrayList<GetPendingApproval> getPendingApprovalArrayList = new ArrayList<>();
    private boolean isSelectAll = false;
    private LinearLayout llAppRej;

    public ApprovalListingFrag(MainActivity context, GetReportingTeamResponce getReportingTeamResponce) {
        this.context = context;
        this.getReportingTeamResponce = getReportingTeamResponce;

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_approval_listing, container, false);

        tvNoRecord = view.findViewById(R.id.noRecord);

        llAppRej = view.findViewById(R.id.apprej);
        selectAll = view.findViewById(R.id.selectall);
        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelectAll){
                    selectAll.setText("Select All");
                    isSelectAll = false;
                    pendingApprovalAdapter = null;
                    approveEventRequest= null;
                    setApdapter(getPendingApprovalArrayList, false);
                } else {
                    selectAll.setText("UnSelect All");
                    approveEventRequest = null;
                    setApdapter(getPendingApprovalArrayList, true);
                    isSelectAll = true;
                }
            }
        });
        getPendingApproval();

        BottomNavigationView navigationView = getActivity().findViewById(R.id.bottomNavigationView);
        navigationView.setVisibility(View.GONE);
        LinearLayout llcplan = getActivity().findViewById(R.id.cplan);
        llcplan.setVisibility(View.GONE);

        llReject = view.findViewById(R.id.reject);
        llReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(approveEventRequest != null &&
                        approveEventRequest.ids != null &&
                        approveEventRequest.ids.size()> 0){
                    approveEventRequest.event_status = "2";
                    approveEvent(approveEventRequest);
                } else {
                    AlertUtils.showAlert(context, "Please select event for rejection");
                }
            }
        });

        llApprove = view.findViewById(R.id.approve);
        llApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(approveEventRequest != null &&
                        approveEventRequest.ids != null &&
                        approveEventRequest.ids.size()> 0){
                    approveEvent(approveEventRequest);
                } else {
                    AlertUtils.showAlert(context, "Please select event for approval");
                }

            }
        });


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));


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

    private void setApdapter(ArrayList<GetPendingApproval> body, boolean isSelectAll) {
        approveEventRequest = new ApproveEventRequest();
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i< body.size(); i++){
            list.add(body.get(i).id);
        }
        recyclerView.setItemViewCacheSize(body.size());
        pendingApprovalAdapter = new PendingApprovalAdapter(context, body, list, isSelectAll);
        recyclerView.setAdapter(pendingApprovalAdapter);

        pendingApprovalAdapter.setOnItemClickListener(new OnApproveListClick() {
            @Override
            public void onItemClick(View view, ArrayList<Integer> list) {
                if(list.size() > 0){

                } else{

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

                if(body.size() > 0){
                    llAppRej.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    tvNoRecord.setVisibility(View.GONE);
                } else {
                    llAppRej.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    tvNoRecord.setVisibility(View.VISIBLE);
                }
                getPendingApprovalArrayList = body;
                setApdapter(getPendingApprovalArrayList, false);

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