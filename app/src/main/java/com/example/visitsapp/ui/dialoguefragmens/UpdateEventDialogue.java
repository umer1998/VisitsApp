package com.example.visitsapp.ui.dialoguefragmens;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.visitsapp.R;
import com.example.visitsapp.business.impl.Business;
import com.example.visitsapp.delegate.ResponseCallBack;
import com.example.visitsapp.model.MapDateForPlanning;
import com.example.visitsapp.model.configuration.Area;
import com.example.visitsapp.model.configuration.Branch;
import com.example.visitsapp.model.configuration.ConfigurationResponse;
import com.example.visitsapp.model.configuration.Events;
import com.example.visitsapp.model.configuration.MeetingPlace;
import com.example.visitsapp.model.configuration.Networks;
import com.example.visitsapp.model.configuration.Purpose;
import com.example.visitsapp.model.request.CreatePlanRequest;
import com.example.visitsapp.model.request.UpdateEventRequest;
import com.example.visitsapp.model.responce.CreatePlanResponce;
import com.example.visitsapp.model.responce.UpdateEventResponce;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.utils.SharedPrefrences;
import com.example.visitsapp.utils.alert.AlertUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class UpdateEventDialogue extends DialogFragment {


    private MainActivity context;
    private Spinner eventSpinner;
    private Spinner eventPurpose;
    private Spinner locationSpinner, regionSpinner, areaSpinner, branchSpinner;
    private ConfigurationResponse configurationResponse;
    private UpdateEventRequest updateEventRequest = new UpdateEventRequest();
    private int eventPosition, purposePosition, locationPosition, branchPosition, regionPosition, areaPosition;

    private LinearLayout llotherlocation,llotherpurpse;
    private EditText edPurpose, edLocation;


    String date = "";

    private RelativeLayout rlCancel, rlSchedule;
    private TextView tvDate;

    private LinearLayout llArea, llBranch, llLocation, llRegion;
    private String dateTime = "", time ="";
    private String region ="", area ="";

    MapDateForPlanning mapDateForPlanning;

    public UpdateEventDialogue(MainActivity context, MapDateForPlanning mapDateForPlanning) {
        this.context = context;
        this.mapDateForPlanning =mapDateForPlanning;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_event_dialogue, container, false);
        this.getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        configurationResponse = SharedPrefrences.getInstance().getConfig();

        llotherlocation = view.findViewById(R.id.llotherlocation);
        llotherpurpse = view.findViewById(R.id.llotherpurpose);

        edLocation = view.findViewById(R.id.otherlocation);
        edPurpose = view.findViewById(R.id.otherpurpose);


        llArea = view.findViewById(R.id.llarea);
        llBranch = view.findViewById(R.id.llbranch);
        llLocation = view.findViewById(R.id.lllocation);
        llRegion = view.findViewById(R.id.llregion);

        tvDate = view.findViewById(R.id.date);
        try {
          date = new SimpleDateFormat("yyyy-MM-dd").
                    format(new SimpleDateFormat("dd-MM-yyyy").
                            parse(mapDateForPlanning.planned_on));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvDate.setText(date);

        rlCancel = view.findViewById(R.id.cancel);
        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UpdateEventDialogue.this.getDialog().dismiss();
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

        eventPosition = eventSpinnerAdapter.getPosition(mapDateForPlanning.event);
        eventSpinner.setSelection(eventPosition);
        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                setPurposeSpinner(events.get(i).purposes);

                eventPosition = i;
                updateEventRequest.setEvent_id(events.get(i).event_name_code);
                if(events.get(i).event_name_code.equals("leave") ||
                        events.get(i).event_name_code.equals("office_work")){
                    llRegion.setVisibility(View.GONE);
                    llArea.setVisibility(View.GONE);
                    llBranch.setVisibility(View.GONE);
                    llotherlocation.setVisibility(View.GONE);
                    llLocation.setVisibility(View.GONE);
                    updateEventRequest.setPurpose_child_id("0");
                } else if(events.get(i).event_name_code.equals("meeting")){

                    llRegion.setVisibility(View.GONE);
                    llArea.setVisibility(View.GONE);
                    llBranch.setVisibility(View.GONE);
                    llotherlocation.setVisibility(View.VISIBLE);
                    llLocation.setVisibility(View.GONE);
                    setLocationPurpose();
                } else if(events.get(i).event_name_code.equals("field_visit")){

                    llRegion.setVisibility(View.VISIBLE);
                    llArea.setVisibility(View.VISIBLE);
                    llBranch.setVisibility(View.VISIBLE);
                    llLocation.setVisibility(View.GONE);
                    llotherlocation.setVisibility(View.GONE);
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

        purposePosition = eventSpinnerAdapter.getPosition(mapDateForPlanning.event_purpose);
        eventPurpose.setSelection(purposePosition);

        eventPurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                purposePosition = i;
                if(purposes.get(i).purpose_code.equalsIgnoreCase("other_leave")
                        || purposes.get(i).purpose_code.equalsIgnoreCase("other_meeting")){
                    llotherpurpse.setVisibility(View.VISIBLE);
                    updateEventRequest.setPurpose_id(purposes.get(i).purpose_code);
                } else {
                    llotherpurpse.setVisibility(View.GONE);
                    updateEventRequest.setPurpose_id(purposes.get(i).purpose_code);
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

        locationPosition = eventSpinnerAdapter.getPosition(mapDateForPlanning.purpose_child);
        locationSpinner.setSelection(locationPosition);

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                locationPosition = i;

                if(meetingPlaces.get(i).code.equalsIgnoreCase("other")){
                    updateEventRequest.setPurpose_child_id(meetingPlaces.get(i).code);
                    llotherlocation.setVisibility(View.VISIBLE);
                } else {
                    llotherlocation.setVisibility(View.GONE);
                    updateEventRequest.setPurpose_child_id(meetingPlaces.get(i).code);
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

        areaPosition = eventSpinnerAdapter.getPosition(mapDateForPlanning.area);
        areaSpinner.setSelection(areaPosition);
        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                areaPosition = i;
                if(areas.get(i).branches != null && areas.get(i).branches.size() != 0){
                    setBranchSpinner(areas.get(i).branches);
                    llBranch.setVisibility(View.VISIBLE);
                } else {
                    updateEventRequest.setPurpose_child_id(areas.get(i).code);
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


        branchPosition = eventSpinnerAdapter.getPosition(mapDateForPlanning.purpose_child);
        branchSpinner.setSelection(branchPosition);


        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                branchPosition = i;
                updateEventRequest.setPurpose_child_id(branches.get(i).code);
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

        regionPosition = eventSpinnerAdapter.getPosition(mapDateForPlanning.region);
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
        if(updateEventRequest.getEvent_id() == null ||
                updateEventRequest.getEvent_id().isEmpty()){

            TextView tvText = (TextView) eventSpinner.getSelectedView();
            tvText.setError("Please select event type");

            return;

        }  else if(updateEventRequest.getPurpose_child_id() == null ||
                updateEventRequest.getPurpose_child_id().isEmpty()){

            if(updateEventRequest.getEvent_id().equals("leave")
                    || updateEventRequest.getEvent_id().equals("office_work")){

            } else if(updateEventRequest.getEvent_id().equals("meeting")) {



//                TextView tvText = (TextView) locationSpinner.getSelectedView();
//                tvText.setError("Please select location !");
            } else if(updateEventRequest.getEvent_id().equals("field_visit")){

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


        }else if(updateEventRequest.getPurpose_id() == null ||
                updateEventRequest.getPurpose_id().isEmpty()){

            TextView tvText = (TextView) eventPurpose.getSelectedView();
            tvText.setError("Please select event purpose");

            return;
        }

        if(updateEventRequest.getPurpose_id().equalsIgnoreCase("other_meeting")
                || updateEventRequest.getPurpose_id().equalsIgnoreCase("other_leave")){
            if(edPurpose.getText().toString().isEmpty() || edPurpose.getText().toString().equalsIgnoreCase("")){
                edPurpose.setError("Please enter purpose.");
                return;
            }
            updateEventRequest.setPurpose_id(edPurpose.getText().toString());
        }
        if(updateEventRequest.getEvent_id().equalsIgnoreCase("meeting")){
            if(edLocation.getText().toString().isEmpty() || edLocation.getText().toString().equals("")){
                edLocation.setError("Please enter location");
                return;
            }
            updateEventRequest.setPurpose_child_id(edLocation.getText().toString());
        }
        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }


        updateEventRequest.setId(String.valueOf(mapDateForPlanning.id));
        updateEventRequest.setPlanned_on(date);

        Business serviceImp = new Business() ;
        serviceImp.updateEvent(updateEventRequest, new ResponseCallBack<UpdateEventResponce>() {
            @Override
            public void onSuccess(UpdateEventResponce body) {

                if (dialog != null) {
                    dialog.dismiss();
                }
                AlertUtils.showAlertSuccess(context, "Event updated successfully.", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.homeFrag();
                        UpdateEventDialogue.this.dismiss();
                    }
                });

            }

            @Override
            public void onFailure(String message) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                if(message.contains("BEGIN_OBJECT")){

                } else {
                    AlertUtils.showAlert(context, message);
                }

            }
        });

    }

}