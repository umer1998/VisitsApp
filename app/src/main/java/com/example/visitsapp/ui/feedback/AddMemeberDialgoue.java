package com.example.visitsapp.ui.feedback;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.visitsapp.R;
import com.example.visitsapp.model.configuration.MeetingPlace;
import com.example.visitsapp.ui.MainActivity;

import java.util.ArrayList;


public class AddMemeberDialgoue extends DialogFragment {

    private MainActivity context;
    EditText edNAme;
    RelativeLayout llContinue, rlCross;
    private OnAddMemberClick onAddMemberClick;
    private Spinner spDesg;
    private String designation = "UM";

    public AddMemeberDialgoue(MainActivity context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View view = inflater.inflate(R.layout.fragment_add_memeber_dialgoue2, container, false);


        edNAme = view.findViewById(R.id.name);
        spDesg = view.findViewById(R.id.eventspinner);

        rlCross = view.findViewById(R.id.cross);
        rlCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddMemeberDialgoue.this.getDialog().dismiss();
            }
        });

        llContinue = view.findViewById(R.id.contin);
        llContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edNAme.getText().toString().equalsIgnoreCase("") ||
                        edNAme.getText().toString().isEmpty()){
                    Toast.makeText(context, "Enter Name!", Toast.LENGTH_SHORT);
                } else {
                    AddMemeberDialgoue.this.getDialog().dismiss();
                    onAddMemberClick.onItemClick(edNAme.getText().toString(), designation);
                }
            }
        });

        setDesignation();

        return view;
    }

    public void setOnItemClickListener(final OnAddMemberClick mItemClickListener) {
        this.onAddMemberClick = mItemClickListener;
    }
    private void setDesignation(){

       ArrayList<String> list = new ArrayList<>();
        list.add("UM");
       list.add("BM");
       list.add("AM");
       list.add("RM");
       list.add("PM");
       list.add("DM");


        ArrayAdapter<String> eventSpinnerAdapter = new ArrayAdapter<>(context, R.layout.spinner_item, list);
        eventSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDesg.setAdapter(eventSpinnerAdapter);


        spDesg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                designation = list.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

}