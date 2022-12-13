package com.example.visitsapp.ui.dialoguefragmens;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.visitsapp.R;
import com.example.visitsapp.business.impl.Business;
import com.example.visitsapp.delegate.ResponseCallBack;
import com.example.visitsapp.model.configuration.Area;
import com.example.visitsapp.model.configuration.Branch;
import com.example.visitsapp.model.configuration.ConfigurationResponse;
import com.example.visitsapp.model.configuration.Events;
import com.example.visitsapp.model.configuration.FeedbackQuestionnaire;
import com.example.visitsapp.model.configuration.MeetingPlace;
import com.example.visitsapp.model.configuration.Networks;
import com.example.visitsapp.model.configuration.Purpose;
import com.example.visitsapp.model.request.CreatePlanRequest;
import com.example.visitsapp.model.request.LoginRequest;
import com.example.visitsapp.model.responce.CreatePlanResponce;
import com.example.visitsapp.model.responce.LoginResponce;
import com.example.visitsapp.model.responce.PlansData;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.activities.Login;
import com.example.visitsapp.utils.SharedPrefrences;
import com.example.visitsapp.utils.alert.AlertUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreatePlanInExecutionDialogue extends DialogFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    private MainActivity context;
    private Spinner eventSpinner;
    private Spinner eventPurpose;
    private Spinner locationSpinner, regionSpinner, areaSpinner, branchSpinner;
    private ConfigurationResponse configurationResponse;
    private CreatePlanRequest createPlanRequest = new CreatePlanRequest();

    private RelativeLayout rlCancel, rlSchedule;
    private TextView tvDate;
    PlansData plansData;
    private ArrayList<FeedbackQuestionnaire> feedbackQuestionnaire = new ArrayList<>();

    private LinearLayout llArea, llBranch, llLocation, llRegion;
    private String dateTime = "", date ="", time ="";
    private String region ="", area ="";

    private String eventtype = "field_visit", eventpurpose ="lac";

    public CreatePlanInExecutionDialogue(MainActivity context, PlansData plansData) {
        this.context = context;
        this.plansData = plansData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_create_plan_in_execution_dialogue, container, false);
        this.getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        BottomNavigationView navigationView = getActivity().findViewById(R.id.bottomNavigationView);
        navigationView.setVisibility(View.GONE);
        LinearLayout llcplan = getActivity().findViewById(R.id.cplan);
        llcplan.setVisibility(View.GONE);
        configurationResponse = SharedPrefrences.getInstance().getConfig();

        llArea = view.findViewById(R.id.llarea);
        llBranch = view.findViewById(R.id.llbranch);
        llLocation = view.findViewById(R.id.lllocation);
        llRegion = view.findViewById(R.id.llregion);

        rlCancel = view.findViewById(R.id.cancel);
        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreatePlanInExecutionDialogue.this.getDialog().dismiss();
            }
        });
        rlSchedule = view.findViewById(R.id.schedule);
        rlSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cretaePlan();
            }
        });


        eventSpinner = view.findViewById(R.id.eventspinner);
        eventPurpose = view.findViewById(R.id.eventPurpose);
        locationSpinner = view.findViewById(R.id.locationSpinner);
        regionSpinner = view.findViewById(R.id.regionspinner);
        areaSpinner = view.findViewById(R.id.areaspinner);
        branchSpinner = view.findViewById(R.id.branchspinner);

        setEventSpinner(configurationResponse.events);

        tvDate = view.findViewById(R.id.date);
        tvDate.setText(plansData.planned_on);

        return view;
    }



    private void setEventSpinner(ArrayList<Events> events){


        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i< events.size(); i++){
            list.add(events.get(i).event_name_label);
        }

        ArrayAdapter<String> eventSpinnerAdapter = new ArrayAdapter<>(context, R.layout.spinner_item, list);
        eventSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventSpinner.setAdapter(eventSpinnerAdapter);

        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setPurposeSpinner(events.get(i).purposes);
                createPlanRequest.setEvent_id(events.get(i).event_name_code);
                eventtype = events.get(i).event_name_code;
                if(events.get(i).event_name_code.equals("leave") ||
                        events.get(i).event_name_code.equals("office_work")){
                    llRegion.setVisibility(View.GONE);
                    llArea.setVisibility(View.GONE);
                    llBranch.setVisibility(View.GONE);
                    llLocation.setVisibility(View.GONE);
                    createPlanRequest.setPurpose_child_id("0");
                } else if(events.get(i).event_name_code.equals("meeting")){

                    llRegion.setVisibility(View.GONE);
                    llArea.setVisibility(View.GONE);
                    llBranch.setVisibility(View.GONE);
                    llLocation.setVisibility(View.VISIBLE);
                    setLocationPurpose();
                } else if(events.get(i).event_name_code.equals("field_visit")){

                    llRegion.setVisibility(View.VISIBLE);
                    llArea.setVisibility(View.VISIBLE);
                    llBranch.setVisibility(View.VISIBLE);
                    llLocation.setVisibility(View.GONE);
                    setRegionSpinner();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
    private void setPurposeSpinner(ArrayList<Purpose> purposes){

        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i< purposes.size(); i++){
            list.add(purposes.get(i).purpose_name);
        }


        ArrayAdapter<String> eventSpinnerAdapter = new ArrayAdapter<>(context, R.layout.spinner_item, list);
        eventSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventPurpose.setAdapter(eventSpinnerAdapter);

        eventPurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                createPlanRequest.setPurpose_id(purposes.get(i).purpose_code);
                eventpurpose = purposes.get(i).purpose_code;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void setLocationPurpose(){

        ArrayList<MeetingPlace> meetingPlaces = configurationResponse.meeting_places;
        ArrayList<String> list = new ArrayList<>();
        for(int i =0; i<meetingPlaces.size(); i++){
            list.add(meetingPlaces.get(i).name);
        }

        ArrayAdapter<String> eventSpinnerAdapter = new ArrayAdapter<>(context, R.layout.spinner_item, list);
        eventSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(eventSpinnerAdapter);

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                createPlanRequest.setPurpose_child_id(meetingPlaces.get(i).code);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void setAreaSpinner(ArrayList<Area> areas){

        ArrayList<String> list = new ArrayList<>();
        for(int i =0; i<areas.size(); i++){
            list.add(areas.get(i).name);
        }

        ArrayAdapter<String> eventSpinnerAdapter = new ArrayAdapter<>(context, R.layout.spinner_item, list);
        eventSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(eventSpinnerAdapter);

        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(areas.get(i).branches != null && areas.get(i).branches.size() != 0){
                    setBranchSpinner(areas.get(i).branches);
                    llBranch.setVisibility(View.VISIBLE);
                } else {
                    createPlanRequest.setPurpose_child_id(areas.get(i).code);
                    llBranch.setVisibility(View.GONE);
                }
                area = areas.get(i).code;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void setBranchSpinner(ArrayList<Branch> branches){

        ArrayList<String> list = new ArrayList<>();
        for(int i =0; i<branches.size(); i++){
            list.add(branches.get(i).name);
        }

        ArrayAdapter<String> eventSpinnerAdapter = new ArrayAdapter<>(context, R.layout.spinner_item, list);
        eventSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchSpinner.setAdapter(eventSpinnerAdapter);

        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                createPlanRequest.setPurpose_child_id(branches.get(i).code);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void setRegionSpinner(){

        ArrayList<String> list = new ArrayList<>();
        ArrayList<Networks> networks = configurationResponse.network;
        for(int i =0; i<networks.size(); i++){
            list.add(networks.get(i).name);
        }

        ArrayAdapter<String> eventSpinnerAdapter = new ArrayAdapter<>(context, R.layout.spinner_item, list);
        eventSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(eventSpinnerAdapter);

        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setAreaSpinner(networks.get(i).areas);
                region = networks.get(i).code;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void cretaePlan() {
        if(createPlanRequest.getEvent_id() == null ||
                createPlanRequest.getEvent_id().isEmpty()){

            TextView tvText = (TextView) eventSpinner.getSelectedView();
            tvText.setError("Please select event type");

            return;

        }  else if(createPlanRequest.getPurpose_child_id() == null ||
                createPlanRequest.getPurpose_child_id().isEmpty()){

            if(createPlanRequest.getEvent_id().equals("leave")
                    || createPlanRequest.getEvent_id().equals("office_work")){

            } else if(createPlanRequest.getEvent_id().equals("meeting")) {

                TextView tvText = (TextView) locationSpinner.getSelectedView();
                tvText.setError("Please select location !");
            } else if(createPlanRequest.getEvent_id().equals("field_visit")){

                if(region.equals("")){

                    TextView tvText = (TextView) regionSpinner.getSelectedView();
                    tvText.setError("Please select region !");
                } else if(area.equals("")){

                    TextView tvText = (TextView) areaSpinner.getSelectedView();
                    tvText.setError("Please select area !");
                } else {

                    TextView tvText = (TextView) branchSpinner.getSelectedView();
                    tvText.setError("Please select branch !");
                }
            }


        }else if(createPlanRequest.getPurpose_id() == null ||
                createPlanRequest.getPurpose_id().isEmpty()){

            TextView tvText = (TextView) eventPurpose.getSelectedView();
            tvText.setError("Please select event purpose");

            return;
        }
//        final AlertDialog dialog = AlertUtils.showLoader(context);

//        if (dialog != null) {
//            dialog.show();
//        }
//
//        Business serviceImp = new Business() ;
//        serviceImp.createPlan(createPlanRequest, new ResponseCallBack<CreatePlanResponce>() {
//            @Override
//            public void onSuccess(CreatePlanResponce body) {
//
//                if (dialog != null) {
//                    dialog.dismiss();
//                }
//                getQuestionaire();
//                CreatePlanInExecutionDialogue.this.dismiss();
//            }
//
//            @Override
//            public void onFailure(String message) {
//                if (dialog != null) {
//                    dialog.dismiss();
//                }
//                if(message.contains("BEGIN_OBJECT")){
//
//                } else {
//                    AlertUtils.showAlert(context, message);
//                }
//
//            }
//        });

        try {
            createPlanRequest.setPlanned_on(new SimpleDateFormat("yy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(plansData.planned_on)) +
                    " "+ plansData.time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        getQuestionaire();


    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        tvDate.setText((new SimpleDateFormat("dd MMM, yy").format(new Date(i, i1,i2))));

        date = new SimpleDateFormat("yy-MM-dd").format(new Date(i, i1,i2));
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, CreatePlanInExecutionDialogue.this, hour, minute, true);

        mTimePicker.setTitle("Select Time");
        mTimePicker.show();


    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        Time t = new Time(i,i1,0);
        time = new SimpleDateFormat("HH:mm:ss").format(t);
        tvDate.setText(" "+tvDate.getText().toString() + " "+ new SimpleDateFormat("HH: mm a").format(t));
        createPlanRequest.setPlanned_on(date+" "+time );
    }

    private void getQuestionaire() {
        for(Events events: configurationResponse.events){
            if(events.event_name_code.equalsIgnoreCase(eventtype)){
                for(Purpose purpose: events.purposes){
                    if(purpose.purpose_code.equalsIgnoreCase(eventpurpose)){
                        feedbackQuestionnaire = purpose.feedback_questionnaire;
                        context.getQuestionaireForm(feedbackQuestionnaire, context, plansData.id, createPlanRequest);
                        CreatePlanInExecutionDialogue.this.getDialog().dismiss();
                    }
                }
            }

        }
    }
}