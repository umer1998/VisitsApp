package com.example.visitsapp.ui.fragments.leaves;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.visitsapp.R;
import com.example.visitsapp.business.impl.Business;
import com.example.visitsapp.delegate.ResponseCallBack;
import com.example.visitsapp.model.responce.GetLeavesResponce;
import com.example.visitsapp.model.responce.GetReportingTeamResponce;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.adapter.GetReportingTeamAdapter;
import com.example.visitsapp.ui.adapter.LeavesAdapter;
import com.example.visitsapp.ui.fragments.BaseFragment;
import com.example.visitsapp.utils.OnItemClickListener;
import com.example.visitsapp.utils.alert.AlertUtils;

import java.util.ArrayList;


public class ApproveLeavesFrag extends BaseFragment {

    private RecyclerView recyclerView;
    private MainActivity context;
    private TextView tvNorecord;
    private ArrayList<GetLeavesResponce> plans = new ArrayList<>();

    public ApproveLeavesFrag(MainActivity context, ArrayList<GetLeavesResponce> approveLeaves) {
        this.context = context;
        this.plans = approveLeaves;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_approve_leaves, container, false);

        tvNorecord = view.findViewById(R.id.norecord);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        setApdapter();

        return view;
    }

    private void setApdapter() {

        if(plans.size()> 0){
            tvNorecord.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            LeavesAdapter leavesAdapter = new LeavesAdapter(context, plans);
            recyclerView.setAdapter(leavesAdapter);

        } else {
            tvNorecord.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }


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



                for(int i =0 ; i< body.size(); i++){
                    if(body.get(i).status.equalsIgnoreCase("pending")){
                    } else {
                        plans.add(body.get(i));
                    }
                }
                setApdapter();

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