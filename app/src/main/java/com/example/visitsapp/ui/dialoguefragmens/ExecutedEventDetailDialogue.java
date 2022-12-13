package com.example.visitsapp.ui.dialoguefragmens;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.visitsapp.R;
import com.example.visitsapp.ui.MainActivity;


public class ExecutedEventDetailDialogue extends DialogFragment {


    private MainActivity context;
    public ExecutedEventDetailDialogue(MainActivity context) {
        this.context = context;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_executed_event_detail_dialogue, container, false);

        this.getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        return view;
    }
}