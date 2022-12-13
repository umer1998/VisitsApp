package com.example.visitsapp.ui.fragments.leaves;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.visitsapp.R;
import com.example.visitsapp.model.responce.GetLeavesResponce;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.adapter.LeavesAdapter;
import com.example.visitsapp.ui.fragments.BaseFragment;

import java.util.ArrayList;


public class PendingLeavesFrag extends BaseFragment {

    private RecyclerView recyclerView;
    private MainActivity context;
    private ArrayList<GetLeavesResponce> plans = new ArrayList<>();

    public PendingLeavesFrag(MainActivity context, ArrayList<GetLeavesResponce> plans) {
        this.context = context;
        this.plans =plans;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pending_leaves, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        setApdapter();

        return view;
    }

    private void setApdapter() {
        LeavesAdapter leavesAdapter = new LeavesAdapter(context, plans);
        recyclerView.setAdapter(leavesAdapter);


    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}