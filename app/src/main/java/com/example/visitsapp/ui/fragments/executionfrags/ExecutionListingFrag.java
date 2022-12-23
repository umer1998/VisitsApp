package com.example.visitsapp.ui.fragments.executionfrags;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.visitsapp.R;
import com.example.visitsapp.business.impl.Business;
import com.example.visitsapp.delegate.ResponseCallBack;
import com.example.visitsapp.model.responce.GetPendingApproval;
import com.example.visitsapp.model.responce.PlansData;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.adapter.ExecutionAdapter;
import com.example.visitsapp.ui.adapter.PendingApprovalAdapter;
import com.example.visitsapp.ui.dialoguefragmens.CreateEventDialogue2;
import com.example.visitsapp.ui.dialoguefragmens.CreatePlanDialogue;
import com.example.visitsapp.ui.dialoguefragmens.ExecuteDialogue;
import com.example.visitsapp.ui.dialoguefragmens.NewEventinExecutionAdapter;
import com.example.visitsapp.ui.fragments.BaseFragment;
import com.example.visitsapp.utils.OnItemClickListener;
import com.example.visitsapp.utils.SharedPrefrences;
import com.example.visitsapp.utils.alert.AlertUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class ExecutionListingFrag extends BaseFragment {

    private RecyclerView recyclerView;
    private MainActivity context;
    private NavigationView navigationView;
    private TextView tvNoRecord;
    private RelativeLayout rlClplan;


    public ExecutionListingFrag(MainActivity context) {
        this.context = context;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_execution_listing, container, false);

        BottomNavigationView bottomNavigationView = context.bottomNavigationView;
        bottomNavigationView.setVisibility(View.GONE);
        context.llcplan.setVisibility(View.GONE);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        tvNoRecord = view.findViewById(R.id.norecord);

        if(isNetworkAvailable()){
            getExecutionEvent();
        } else {
            setExecutedEventFromPrefrence();
        }

        rlClplan = view.findViewById(R.id.cplan);
        rlClplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewEventinExecutionAdapter dialog = new NewEventinExecutionAdapter(context);
                dialog.show(context.getSupportFragmentManager(), "MyDialogFragment");
            }
        });

        return view;
    }

    private void setExecutedEventFromPrefrence() {
        if(SharedPrefrences.getInstance().getExecutedEvent()!= null){
            if(SharedPrefrences.getInstance().getExecutedEvent().size()> 0){
                recyclerView.setVisibility(View.VISIBLE);
                tvNoRecord.setVisibility(View.GONE);
                setApdapter(SharedPrefrences.getInstance().getExecutedEvent());
            } else {
                recyclerView.setVisibility(View.GONE);
                tvNoRecord.setVisibility(View.VISIBLE);
            }
        } else {
            recyclerView.setVisibility(View.GONE);
            tvNoRecord.setVisibility(View.VISIBLE);
        }
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
                    recyclerView.setVisibility(View.VISIBLE);
                    tvNoRecord.setVisibility(View.GONE);
                    setApdapter(body);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    tvNoRecord.setVisibility(View.VISIBLE);
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

    private void setApdapter(ArrayList<PlansData> body) {
        ExecutionAdapter executionAdapter = new ExecutionAdapter(context, body);
        recyclerView.setAdapter(executionAdapter);

        executionAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CreateEventDialogue2 dialog = new CreateEventDialogue2(context, body.get(position) );
                dialog.show(context.getSupportFragmentManager(), "MyDialogFragment");
            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public boolean onBackPressed() {
        return false;
    }
}