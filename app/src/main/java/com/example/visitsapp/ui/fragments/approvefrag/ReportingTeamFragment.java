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
import com.example.visitsapp.model.responce.GetReportingTeamResponce;
import com.example.visitsapp.model.responce.LoginResponce;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.activities.Login;
import com.example.visitsapp.ui.adapter.GetReportingTeamAdapter;
import com.example.visitsapp.ui.adapter.PendingApprovalAdapter;
import com.example.visitsapp.ui.fragments.BaseFragment;
import com.example.visitsapp.utils.OnItemClickListener;
import com.example.visitsapp.utils.SharedPrefrences;
import com.example.visitsapp.utils.alert.AlertUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class ReportingTeamFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private MainActivity context;
    private LinearLayout llcplan;

    private ArrayList<GetReportingTeamResponce> reportingTeamResponces = new ArrayList<>();


    public ReportingTeamFragment(MainActivity context, ArrayList<GetReportingTeamResponce> reportingTeamResponces ) {
        this.context = context;
        this.reportingTeamResponces = reportingTeamResponces;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reporting_team, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        getReportingTeam();


        return view;
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

    private void setApdapter(ArrayList<GetReportingTeamResponce> body) {
        GetReportingTeamAdapter getReportingTeamAdapter = new GetReportingTeamAdapter(context, body);
        recyclerView.setAdapter(getReportingTeamAdapter);

        getReportingTeamAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                context.getPendingApproval(body.get(position));
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}