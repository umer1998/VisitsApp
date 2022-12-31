package com.example.visitsapp.ui.dialoguefragmens;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.visitsapp.R;
import com.example.visitsapp.business.PostFeedBackResponce;
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
import com.example.visitsapp.model.request.CreatePlanRequest;
import com.example.visitsapp.model.request.Feedback;
import com.example.visitsapp.model.request.FeedbackReplace;
import com.example.visitsapp.model.request.PostFeedBackRequest;
import com.example.visitsapp.model.request.QuesAnswer;
import com.example.visitsapp.model.request.ReplaceEventRequest;
import com.example.visitsapp.model.responce.PlansData;
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
import java.util.Map;


public class CreateEventDialogue2 extends DialogFragment {

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


    private LinearLayout llotherlocation,llotherpurpse;
    private EditText edPurpose, edLocation;
    private LinearLayout llArea, llBranch, llLocation, llRegion;
    private String dateTime = "", date ="", time ="";
    private String region ="", area ="";

    private String eventtype = "field_visit", eventpurpose ="lac";
    private int eventPosition, purposePosition, locationPosition, branchPosition, regionPosition, areaPosition;

    private LinearLayout llafterchange, llchangeevent;
    private RelativeLayout rlContinue;

    private LinearLayout llocation1, llregion1, llarea1, llbranch1;
    private TextView tveventspinner1, tveventPurpose1, tvlocationSpinner1, tvregionspinner1,areaspinner1, tvbranchspinner1;


    public CreateEventDialogue2(MainActivity context, PlansData plansData) {
        this.context = context;
        this.plansData = plansData;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_event_dialogue2, container, false);

        this.getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        BottomNavigationView navigationView = getActivity().findViewById(R.id.bottomNavigationView);
        navigationView.setVisibility(View.GONE);
        LinearLayout llcplan = context.llcplan;
        llcplan.setVisibility(View.GONE);
        configurationResponse = SharedPrefrences.getInstance().getConfig();

        llotherlocation = view.findViewById(R.id.llotherlocation);
        llotherpurpse = view.findViewById(R.id.llotherpurpose);

        edLocation = view.findViewById(R.id.otherlocation);
        edPurpose = view.findViewById(R.id.otherpurpose);

        llArea = view.findViewById(R.id.llarea);
        llBranch = view.findViewById(R.id.llbranch);
        llLocation = view.findViewById(R.id.lllocation);
        llRegion = view.findViewById(R.id.llregion);


        rlCancel = view.findViewById(R.id.cancel);
        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventSpinner.setEnabled(true);
                eventPurpose.setEnabled(true);
                locationSpinner.setEnabled(true);
                regionSpinner.setEnabled(true);
                areaSpinner.setEnabled(true);
                branchSpinner.setEnabled(true);

                eventSpinner.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#44000000")));
                eventPurpose.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#44000000")));
                locationSpinner.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#44000000")));
                regionSpinner.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#44000000")));
                areaSpinner.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#44000000")));
                branchSpinner.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#44000000")));


                llafterchange.setVisibility(View.VISIBLE);
                llchangeevent.setVisibility(View.GONE);
            }
        });
        rlSchedule = view.findViewById(R.id.schedule);
        rlSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(plansData.event.equalsIgnoreCase("Office Work")){
                    postFeedBack(plansData.id);
                    context.homeFrag();
                    CreateEventDialogue2.this.getDialog().dismiss();
                } else {
                    getQuestionaire1();
                    CreateEventDialogue2.this.getDialog().dismiss();
                }

            }
        });


        eventSpinner = view.findViewById(R.id.eventspinner);
        eventPurpose = view.findViewById(R.id.eventPurpose);
        locationSpinner = view.findViewById(R.id.locationSpinner);
        regionSpinner = view.findViewById(R.id.regionspinner);
        areaSpinner = view.findViewById(R.id.areaspinner);
        branchSpinner = view.findViewById(R.id.branchspinner);

        eventSpinner.setEnabled(false);
        eventPurpose.setEnabled(false);
        locationSpinner.setEnabled(false);
        regionSpinner.setEnabled(false);
        areaSpinner.setEnabled(false);
        branchSpinner.setEnabled(false);

        setEventSpinner(configurationResponse.events);

        tvDate = view.findViewById(R.id.date);
        tvDate.setText(plansData.planned_on);


        llchangeevent = view.findViewById(R.id.changeevent);
        llafterchange = view.findViewById(R.id.afterchange);

        rlContinue = view.findViewById(R.id.continueee);
        rlContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cretaePlan();
            }
        });




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


        eventPosition = eventSpinnerAdapter.getPosition(plansData.event);
        eventSpinner.setSelection(eventPosition);



        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setPurposeSpinner(events.get(i).purposes);
                eventPosition = i;
                createPlanRequest.setEvent_id(events.get(i).event_name_code);
                eventtype = events.get(i).event_name_code;
                if(events.get(i).event_name_code.equals("leave") ||
                        events.get(i).event_name_code.equals("office_work")){
                    llRegion.setVisibility(View.GONE);
                    llArea.setVisibility(View.GONE);
                    llBranch.setVisibility(View.GONE);
                    llotherlocation.setVisibility(View.GONE);
                    llLocation.setVisibility(View.GONE);
                    createPlanRequest.setPurpose_child_id("0");
                } else if(events.get(i).event_name_code.equals("meeting")){

                    llRegion.setVisibility(View.GONE);
                    llArea.setVisibility(View.GONE);
                    llBranch.setVisibility(View.GONE);
                    llLocation.setVisibility(View.GONE);
                    llotherlocation.setVisibility(View.VISIBLE);
                    setLocationPurpose();
                } else if(events.get(i).event_name_code.equals("field_visit")){

                    llRegion.setVisibility(View.VISIBLE);
                    llArea.setVisibility(View.VISIBLE);
                    llBranch.setVisibility(View.VISIBLE);
                    llotherlocation.setVisibility(View.GONE);
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

        purposePosition = eventSpinnerAdapter.getPosition(plansData.event_purpose);
        eventPurpose.setSelection(purposePosition);

        eventPurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                purposePosition = i;
                if(purposes.get(i).purpose_code.equalsIgnoreCase("other_leave")
                        || purposes.get(i).purpose_code.equalsIgnoreCase("other_meeting")){
                    llotherpurpse.setVisibility(View.VISIBLE);
                    createPlanRequest.setPurpose_id(purposes.get(i).purpose_code);
                    eventpurpose = purposes.get(i).purpose_code;
                } else {
                    llotherpurpse.setVisibility(View.GONE);
                    createPlanRequest.setPurpose_id(purposes.get(i).purpose_code);
                    eventpurpose = purposes.get(i).purpose_code;
                }

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
        locationPosition = eventSpinnerAdapter.getPosition(plansData.purpose_child);
        locationSpinner.setSelection(locationPosition);

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                locationPosition = i;
                if(meetingPlaces.get(i).code.equalsIgnoreCase("other")){
                    llotherlocation.setVisibility(View.VISIBLE);
                    createPlanRequest.setPurpose_child_id(meetingPlaces.get(i).code);
                } else {
                    llotherlocation.setVisibility(View.GONE);
                    createPlanRequest.setPurpose_child_id(meetingPlaces.get(i).code);
                }

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

        areaPosition = eventSpinnerAdapter.getPosition(plansData.area);
        areaSpinner.setSelection(areaPosition);

        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                areaPosition = i;
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

        branchPosition = eventSpinnerAdapter.getPosition(plansData.purpose_child);
        branchSpinner.setSelection(branchPosition);

        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                branchPosition = i;
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

        regionPosition = eventSpinnerAdapter.getPosition(plansData.region);
        regionSpinner.setSelection(regionPosition);

        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                regionPosition =i;
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



//                TextView tvText = (TextView) locationSpinner.getSelectedView();
//                tvText.setError("Please select location !");
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

        if(createPlanRequest.getPurpose_id().equalsIgnoreCase("other_meeting")
                || createPlanRequest.getPurpose_id().equalsIgnoreCase("other_leave")){
            if(edPurpose.getText().toString().isEmpty() || edPurpose.getText().toString().equalsIgnoreCase("")){
                edPurpose.setError("Please enter purpose.");
                return;
            }
            createPlanRequest.setPurpose_id(edPurpose.getText().toString());
        }
        if(createPlanRequest.getEvent_id().equalsIgnoreCase("meeting")){
            if(edLocation.getText().toString().isEmpty() || edLocation.getText().toString().equals("")){
                edLocation.setError("Please enter location");
                return;
            }
            createPlanRequest.setPurpose_child_id(edLocation.getText().toString());
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
        if(eventtype.equalsIgnoreCase("office_work")){
            replaceEventAndPost(plansData.id);
            context.homeFrag();
            CreateEventDialogue2.this.getDialog().dismiss();
        } else {
            getQuestionaire2();
        }



    }



    private void getQuestionaire1() {
        for(Events events: configurationResponse.events){
            if(events.event_name_code.equalsIgnoreCase(eventtype)){
                for(Purpose purpose: events.purposes){
                    if(purpose.purpose_code.equalsIgnoreCase(eventpurpose)){
                        feedbackQuestionnaire = purpose.feedback_questionnaire;
                        context.getQuestionairePostFeed(feedbackQuestionnaire, context, plansData.id);
                        CreateEventDialogue2.this.getDialog().dismiss();

                    }
                }
            }

        }
    }

    private void getQuestionaire2() {
        for(Events events: configurationResponse.events){
            if(events.event_name_code.equalsIgnoreCase(eventtype)){
                for(Purpose purpose: events.purposes){
                    if(purpose.purpose_code.equalsIgnoreCase(eventpurpose)){
                        feedbackQuestionnaire = purpose.feedback_questionnaire;
                        context.getQuestionaireForm(feedbackQuestionnaire, context, plansData.id, createPlanRequest);
                        CreateEventDialogue2.this.getDialog().dismiss();
                    }
                }
            }

        }
    }

    private void postFeedBack(int id) {

        PostFeedBackRequest postFeedBackRequest = new PostFeedBackRequest();
        Feedback feedback = new Feedback();
        feedback.planner_event_id = id;

        ArrayList<QuesAnswer> quesAnswers = new ArrayList<>();
        feedback.questionaire =quesAnswers;

        ArrayList<Feedback> feedbackArrayList = new ArrayList<>();
        feedbackArrayList.add(feedback);
        postFeedBackRequest.feedbacks = feedbackArrayList;




        if(isNetworkAvailable()){

            final AlertDialog dialog = AlertUtils.showLoader(context);

            if (dialog != null) {
                dialog.show();
            }

            Business serviceImp = new Business() ;
            serviceImp.postFeedBack(postFeedBackRequest, new ResponseCallBack<PostFeedBackResponce>() {
                @Override
                public void onSuccess(PostFeedBackResponce body) {


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
        } else {
            PostFeedBackRequest postFeedBackRequest1 = new PostFeedBackRequest();
            if(SharedPrefrences.getInstance().getPostFeedBack() != null &&
                    SharedPrefrences.getInstance().getPostFeedBack().feedbacks.size() > 0){

                postFeedBackRequest1 = SharedPrefrences.getInstance().getPostFeedBack();
                postFeedBackRequest1.feedbacks.add(feedback);
                SharedPrefrences.getInstance().setPostFeedBack(postFeedBackRequest1);

                ArrayList<PlansData> arrayList = new ArrayList<>();
                arrayList = SharedPrefrences.getInstance().getExecutedEvent();
                PlansData removeDataObj = new PlansData();
                for(PlansData data: arrayList){
                    if(data.id == feedback.planner_event_id){
                        removeDataObj = data;
                    }
                }
                arrayList.remove(removeDataObj);
                SharedPrefrences.getInstance().setExecutedEvent(arrayList);

            } else {


                ArrayList<Feedback> feedbackArrayList1 = new ArrayList<>();
                feedbackArrayList1.add(feedback);
                postFeedBackRequest1.feedbacks = feedbackArrayList1;
                SharedPrefrences.getInstance().setPostFeedBack(postFeedBackRequest1);

                ArrayList<PlansData> arrayList = new ArrayList<>();
                arrayList = SharedPrefrences.getInstance().getExecutedEvent();
                PlansData removeDataObj = new PlansData();
                for(PlansData data: arrayList){
                    if(data.id == feedback.planner_event_id){
                        removeDataObj = data;
                    }
                }
                arrayList.remove(removeDataObj);
                SharedPrefrences.getInstance().setExecutedEvent(arrayList);

            }

        }

    }

    private void replaceEventAndPost(int id) {


        ReplaceEventRequest replaceEventRequest1 = new ReplaceEventRequest();
        ChangedPlan changedPlan = new ChangedPlan();
        changedPlan.plannerEventId = id;
        ArrayList<FeedbackReplace> feedbackReplaces = new ArrayList<>();
        changedPlan.feedbacks = feedbackReplaces;
        changedPlan.new_event = createPlanRequest;
//        request.changedPlan.add(changedPlan);
        ArrayList<ChangedPlan> changedPlanArrayList = new ArrayList<>();
        changedPlanArrayList.add(changedPlan);
        replaceEventRequest1.changedPlan =changedPlanArrayList;

        if(isNetworkAvailable()){
            final AlertDialog dialog = AlertUtils.showLoader(context);

            if (dialog != null) {
                dialog.show();
            }
            Business serviceImp = new Business() ;
            serviceImp.replaceevent(replaceEventRequest1, new ResponseCallBack<String>() {
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
        } else {
            ReplaceEventRequest replaceEventRequest = new ReplaceEventRequest();
            if(SharedPrefrences.getInstance().getReplaceEventFeedBack() != null &&
                    SharedPrefrences.getInstance().getReplaceEventFeedBack().changedPlan.size() > 0){

                replaceEventRequest = SharedPrefrences.getInstance().getReplaceEventFeedBack();
                replaceEventRequest.changedPlan.add(changedPlan);
                SharedPrefrences.getInstance().setReplaceEventFeedBack(replaceEventRequest);

                ArrayList<PlansData> arrayList = new ArrayList<>();
                arrayList = SharedPrefrences.getInstance().getExecutedEvent();
                PlansData removeDataObj = new PlansData();
                for(PlansData data: arrayList){
                    if(data.id == changedPlan.plannerEventId){
                        removeDataObj = data;
                    }
                }
                arrayList.remove(removeDataObj);
                SharedPrefrences.getInstance().setExecutedEvent(arrayList);
            } else {


                ArrayList<ChangedPlan> changedPlanArrayList1 = new ArrayList<>();
                changedPlanArrayList1.add(changedPlan);
                replaceEventRequest.changedPlan = changedPlanArrayList1;
                SharedPrefrences.getInstance().setReplaceEventFeedBack(replaceEventRequest);

                ArrayList<PlansData> arrayList = new ArrayList<>();
                arrayList = SharedPrefrences.getInstance().getExecutedEvent();
                PlansData removeDataObj = new PlansData();
                for(PlansData data: arrayList){
                    if(data.id == changedPlan.plannerEventId){
                       removeDataObj = data;
                    }
                }
                arrayList.remove(removeDataObj);
                SharedPrefrences.getInstance().setExecutedEvent(arrayList);

            }
        }


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}