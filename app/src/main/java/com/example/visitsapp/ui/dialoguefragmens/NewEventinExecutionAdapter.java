package com.example.visitsapp.ui.dialoguefragmens;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

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
import com.example.visitsapp.model.request.ChangedPlan;
import com.example.visitsapp.model.request.CreateChangedPlan;
import com.example.visitsapp.model.request.CreateEventFeedback;
import com.example.visitsapp.model.request.CreatePlanRequest;
import com.example.visitsapp.model.request.FeedbackReplace;
import com.example.visitsapp.model.request.QuesAnswer;
import com.example.visitsapp.model.request.ReplaceEventRequest;
import com.example.visitsapp.model.responce.CreatePlanResponce;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.utils.SharedPrefrences;
import com.example.visitsapp.utils.alert.AlertUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class NewEventinExecutionAdapter extends DialogFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener  {

 private MainActivity context;
    private Spinner eventSpinner;
    private Spinner eventPurpose;
    private Spinner locationSpinner, regionSpinner, areaSpinner, branchSpinner;
    private ConfigurationResponse configurationResponse;
    private CreatePlanRequest createPlanRequest = new CreatePlanRequest();
    private ArrayList<FeedbackQuestionnaire> feedbackQuestionnaire = new ArrayList<>();
    private LinearLayout llDateTime;

    private RelativeLayout rlCancel, rlSchedule;
    private TextView tvDate;

    private LinearLayout llArea, llBranch, llLocation, llRegion;
    private String dateTime = "", date ="", time ="";
    private String region ="", area ="";

    public NewEventinExecutionAdapter(MainActivity context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_eventin_execution_adapter, container, false);
        this.getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        configurationResponse = SharedPrefrences.getInstance().getConfig();

        llDateTime = view.findViewById(R.id.datetime);

        llArea = view.findViewById(R.id.llarea);
        llBranch = view.findViewById(R.id.llbranch);
        llLocation = view.findViewById(R.id.lllocation);
        llRegion = view.findViewById(R.id.llregion);

        rlCancel = view.findViewById(R.id.cancel);
        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.homeFrag();
                NewEventinExecutionAdapter.this.getDialog().dismiss();
            }
        });
        rlSchedule = view.findViewById(R.id.schedule);
        rlSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.homeFrag();
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
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        tvDate.setText(date);
        createPlanRequest.setPlanned_on(new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date()));

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



        if(createPlanRequest.getEvent_id().equalsIgnoreCase("office_work")
                || createPlanRequest.getEvent_id().equalsIgnoreCase("leave")){
            replaceEventAndPost(-1);
            context.homeFrag();
            NewEventinExecutionAdapter.this.getDialog().dismiss();
        } else {
            getQuestionaire2();
        }




    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        tvDate.setError(null);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("yy-MM-dd").parse( new SimpleDateFormat("yy-MM-dd").
                    format(new Date(i, i1,i2))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if(c.get(Calendar.DAY_OF_WEEK) == 1){
            AlertUtils.showAlert(context, "Can't create event on sunday.");
        } else {
            tvDate.setText((new SimpleDateFormat("dd MMM, yy").format(new Date(i, i1,i2))));

            date = new SimpleDateFormat("yy-MM-dd").format(new Date(i, i1,i2));
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(context, NewEventinExecutionAdapter.this, hour, minute, false);

            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }



    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        Time t = new Time(i,i1,0);
        time = new SimpleDateFormat("HH:mm:ss").format(t);
        tvDate.setText(" "+tvDate.getText().toString() + " "+ new SimpleDateFormat("HH: mm a").format(t));
        createPlanRequest.setPlanned_on(date+" "+time );
    }

    private void replaceEventAndPost(int id) {


        CreateEventFeedback replaceEventRequest1 = new CreateEventFeedback();
        CreateChangedPlan changedPlan = new CreateChangedPlan();
        ArrayList<QuesAnswer> questionaire1 = new ArrayList<>();
        ArrayList<FeedbackReplace> feedbackReplaces = new ArrayList<>();
        FeedbackReplace feedbackReplace = new FeedbackReplace();
        feedbackReplace.questionaire = questionaire1;
        feedbackReplaces.add(feedbackReplace);
        changedPlan.feedbacks = feedbackReplaces;
        changedPlan.new_event = createPlanRequest;

        ArrayList<QuesAnswer> questionaire = new ArrayList<>();

//        request.changedPlan.add(changedPlan);
        ArrayList<CreateChangedPlan> changedPlanArrayList = new ArrayList<>();
        changedPlanArrayList.add(changedPlan);
        replaceEventRequest1.changedPlan = changedPlanArrayList;

        final AlertDialog dialog = AlertUtils.showLoader(context);
        if (dialog != null) {
            dialog.show();
        }
        Business serviceImp = new Business() ;
        serviceImp.createFeedback(replaceEventRequest1, new ResponseCallBack<String>() {
            @Override
            public void onSuccess(String body) {


                if (dialog != null) {
                    dialog.dismiss();
                }

                AlertUtils.showAlert(context, "Feedback submitted successfully.");
                context.executedCompletedEvent();
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

    private void getQuestionaire2() {
        for(Events events: configurationResponse.events){
            if(events.event_name_code.equalsIgnoreCase(createPlanRequest.getEvent_id())){
                for(Purpose purpose: events.purposes){
                    if(purpose.purpose_code.equalsIgnoreCase(createPlanRequest.getPurpose_id())){
                        feedbackQuestionnaire = purpose.feedback_questionnaire;
                        context.getQuestionaireForm(feedbackQuestionnaire, context, -1, createPlanRequest);
                        NewEventinExecutionAdapter.this.getDialog().dismiss();
                    }
                }
            }

        }
    }
}