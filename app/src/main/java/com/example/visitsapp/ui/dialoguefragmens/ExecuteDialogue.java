package com.example.visitsapp.ui.dialoguefragmens;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.DialogFragment;

import com.example.visitsapp.R;
import com.example.visitsapp.model.configuration.ConfigurationResponse;
import com.example.visitsapp.model.configuration.Events;
import com.example.visitsapp.model.configuration.FeedbackQuestionnaire;
import com.example.visitsapp.model.configuration.Purpose;
import com.example.visitsapp.model.responce.PlansData;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.utils.SharedPrefrences;
import com.example.visitsapp.utils.alert.AlertUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ExecuteDialogue extends DialogFragment {

    private RadioGroup radioGroup;
    private MainActivity context;
    private String radiobutton = "Planned";
    private RelativeLayout rlSchedule;
    private RelativeLayout rlcross;
    PlansData plansData;
    private ConfigurationResponse configurationResponse;
    private ArrayList<FeedbackQuestionnaire> feedbackQuestionnaire = new ArrayList<>();


    public ExecuteDialogue(MainActivity context, PlansData plansData) {
        this.context = context;
        this.plansData = plansData;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_execute_dialogue, container, false);
        this.getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        BottomNavigationView navigationView = getActivity().findViewById(R.id.bottomNavigationView);
        navigationView.setVisibility(View.GONE);
        LinearLayout llcplan = getActivity().findViewById(R.id.cplan);
        llcplan.setVisibility(View.GONE);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }


        configurationResponse = SharedPrefrences.getInstance().getConfig();
//        if(configurationResponse == null ||
//                configurationResponse.events == null){
//            context.getConfig();
//        } else {
//            setEventSpinner(configurationResponse.events);
//        }
        rlcross = view.findViewById(R.id.cross);
        rlcross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecuteDialogue.this.getDialog().dismiss();
            }
        });

        radioGroup = view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = view.findViewById(i);
                if (radioButton.getText().toString().equals("Planned")) {
                    radiobutton = "planned";

                } else if (radioButton.getText().toString().equals("Changed")) {
                    radiobutton = "changed";
                }
            }
        });

        rlSchedule = view.findViewById(R.id.schedule);
        rlSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radiobutton.equalsIgnoreCase("Planned")) {
                    if(plansData.event.equalsIgnoreCase("Office Work")){
                        ExecuteDialogue.this.getDialog().dismiss();
                    } else {
                        getQuestionaire();
                        ExecuteDialogue.this.getDialog().dismiss();
                    }
                    ExecuteDialogue.this.getDialog().dismiss();
                } else if (radiobutton.equalsIgnoreCase("Changed")) {
                    CreatePlanInExecutionDialogue dialog = new CreatePlanInExecutionDialogue(context, plansData );
                    dialog.show(context.getSupportFragmentManager(), "MyDialogFragment");
                    ExecuteDialogue.this.getDialog().dismiss();

                }
            }
        });

        return view;
    }



    private void getQuestionaire() {
        for(Events events: configurationResponse.events){
            if(events.event_name_label.equalsIgnoreCase(plansData.event)){
                for(Purpose purpose: events.purposes){
                    if(purpose.purpose_name.equalsIgnoreCase(plansData.event_purpose)){

                        feedbackQuestionnaire = purpose.feedback_questionnaire;
                        context.getQuestionairePostFeed(feedbackQuestionnaire, context, plansData.id);
                        ExecuteDialogue.this.getDialog().dismiss();

                    }
                }
            }

        }
    }
}