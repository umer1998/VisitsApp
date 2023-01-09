package com.example.visitsapp.ui.fragments.drawerfrag;

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
import com.example.visitsapp.model.responce.PlansData;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.adapter.UnexecutedEventAdapter;
import com.example.visitsapp.ui.dialoguefragmens.CreateEventDialogue2;
import com.example.visitsapp.ui.fragments.BaseFragment;
import com.example.visitsapp.utils.OnItemClickListener;
import com.example.visitsapp.utils.alert.AlertUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class UnExecutedEvent extends BaseFragment {

    private RecyclerView recyclerView;
    private MainActivity context;
    private NavigationView navigationView;
    private TextView tvNoRecord;

    public UnExecutedEvent(MainActivity context) {
        this.context = context;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_un_executed_event, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        tvNoRecord = view.findViewById(R.id.norecord);
        getUnExecutedEvent();

        return view;
    }

    private void getUnExecutedEvent() {
        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }



        Business serviceImp = new Business() ;
        serviceImp.unExecutedEvent( new ResponseCallBack<ArrayList<PlansData>>() {
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
        UnexecutedEventAdapter executionAdapter = new UnexecutedEventAdapter(context, body);
        recyclerView.setAdapter(executionAdapter);
        recyclerView.setItemViewCacheSize(body.size());



    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}