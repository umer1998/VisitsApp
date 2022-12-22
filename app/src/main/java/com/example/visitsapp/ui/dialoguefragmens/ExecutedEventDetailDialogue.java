package com.example.visitsapp.ui.dialoguefragmens;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.responce.PlansData;
import com.example.visitsapp.ui.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class ExecutedEventDetailDialogue extends DialogFragment {


    private MainActivity context;
    private TextView tvType, tvPurpose, tvRegion, tvArea, tvBranch, tvPlannedOn, tvTime;
    private PlansData plansData;


    public ExecutedEventDetailDialogue(MainActivity context, PlansData plansData) {
        this.context = context;
        this.plansData = plansData;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_executed_event_detail_dialogue, container, false);

        this.getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        tvTime = view.findViewById(R.id.time);
        tvBranch = view.findViewById(R.id.branch);
        tvRegion = view.findViewById(R.id.region);
        tvArea = view.findViewById(R.id.area);
        tvPlannedOn = view.findViewById(R.id.plannedon);
        tvType = view.findViewById(R.id.type);
        tvPurpose = view.findViewById(R.id.purpose);

        setData();

        return view;
    }

    private void setData() {
        tvType.setText(plansData.event);
        tvPurpose.setText(plansData.event_purpose);
        tvBranch.setText(plansData.purpose_child);
        tvRegion.setText(plansData.region);
        tvArea.setText(plansData.area);
        tvPlannedOn.setText(plansData.planned_on);
        try {
            tvTime.setText(new SimpleDateFormat("HH:mm a").
                    format(new SimpleDateFormat("hh:mm:ss").
                            parse(plansData.time)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}