package com.example.visitsapp.ui.fragments.approvefrag;

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
import com.example.visitsapp.model.responce.PlansData;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.adapter.UnexecutedEventAdapter;
import com.example.visitsapp.utils.alert.AlertUtils;

import java.util.ArrayList;


public class SelfPendingApprovalEvent extends Fragment {

    private RecyclerView recyclerView;
    private MainActivity context;
    private TextView tvNoRecord;
    private ArrayList<PlansData> body;

    public SelfPendingApprovalEvent(MainActivity context,ArrayList<PlansData> body) {
        this.context = context;
        this.body = body;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_self_pending_approval_event, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        tvNoRecord = view.findViewById(R.id.norecord);
        if(body.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            tvNoRecord.setVisibility(View.GONE);
            setApdapter(body);
        } else {
            recyclerView.setVisibility(View.GONE);
            tvNoRecord.setVisibility(View.VISIBLE);
        }

        return view;
    }




    private void setApdapter(ArrayList<PlansData> body) {
        UnexecutedEventAdapter executionAdapter = new UnexecutedEventAdapter(context, body);
        recyclerView.setAdapter(executionAdapter);
        recyclerView.setItemViewCacheSize(body.size());
    }
}