package com.example.visitsapp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.visitsapp.R;
import com.example.visitsapp.business.impl.Business;
import com.example.visitsapp.delegate.ResponseCallBack;
import com.example.visitsapp.model.configuration.FeedbackQuestionnaire;
import com.example.visitsapp.model.request.ChangedPlan;
import com.example.visitsapp.model.request.CreatePlanRequest;
import com.example.visitsapp.model.request.Feedback;
import com.example.visitsapp.model.request.FeedbackReplace;
import com.example.visitsapp.model.request.LoginRequest;
import com.example.visitsapp.model.request.PostFeedBackRequest;
import com.example.visitsapp.model.request.QuesAnswer;
import com.example.visitsapp.model.request.ReplaceEventRequest;
import com.example.visitsapp.model.responce.LoginResponce;
import com.example.visitsapp.model.responce.PlansData;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.activities.Login;
import com.example.visitsapp.ui.adapter.PendingApprovalAdapter;
import com.example.visitsapp.ui.adapter.QuestionaireAdapter;
import com.example.visitsapp.utils.OnRadioButtonClickListener;
import com.example.visitsapp.utils.SharedPrefrences;
import com.example.visitsapp.utils.alert.AlertUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class QuestionaireReplaceEventFragment extends BaseFragment {

    private MainActivity context;
    private RecyclerView recyclerView;
    private ArrayList<FeedbackQuestionnaire> feedbackQuestionnaires;
    private RelativeLayout rlSubmit;
    HashMap<Integer, String> answersmap = new HashMap<Integer, String>();
    private int id;
    private CreatePlanRequest createPlanRequest;

    public QuestionaireReplaceEventFragment(MainActivity context, ArrayList<FeedbackQuestionnaire> feedbackQuestionnaires,
                                            int id, CreatePlanRequest createPlanRequest) {
        this.context = context;
        this.feedbackQuestionnaires = feedbackQuestionnaires;
        this.id = id;
        this.createPlanRequest = createPlanRequest;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_questionaire, container, false);
        BottomNavigationView navigationView = getActivity().findViewById(R.id.bottomNavigationView);
        navigationView.setVisibility(View.GONE);
        LinearLayout llcplan = getActivity().findViewById(R.id.cplan);
        llcplan.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        rlSubmit = view.findViewById(R.id.submit);
        rlSubmit.setVisibility(View.VISIBLE);
        rlSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(feedbackQuestionnaires.size() != answersmap.size()){
                    AlertUtils.showAlert(context, "Answer all question !");
                } else {

                        replaceEventAndPost();
                    context.homeFrag();
                }

            }
        });
        setApadter();
        return view;

    }

    private void replaceEventAndPost() {





        ReplaceEventRequest request = new ReplaceEventRequest();
        ArrayList<ChangedPlan> changedPlansList = new ArrayList<>();
        ChangedPlan changedPlan = new ChangedPlan();
        changedPlan.new_event = createPlanRequest;
        changedPlan.plannerEventId = id;
        ArrayList<QuesAnswer> quesAnswers = new ArrayList<>();
        ArrayList<FeedbackReplace> feedbacks = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : answersmap.entrySet()) {
            QuesAnswer quesAnswer = new QuesAnswer();
            quesAnswer.setAnswer(entry.getValue());
            quesAnswer.setId(entry.getKey());

            quesAnswers.add(quesAnswer);
        }
        FeedbackReplace feedbackReplace = new FeedbackReplace();
        feedbackReplace.questionaire = quesAnswers;
        feedbacks.add(feedbackReplace);

        changedPlan.feedbacks = feedbacks;
        changedPlansList.add(changedPlan);
        request.changedPlan = changedPlansList;

//        request.changedPlan.add(changedPlan);

        if(isNetworkAvailable()){
            final AlertDialog dialog = AlertUtils.showLoader(context);
            if (dialog != null) {
                dialog.show();
            }
            Business serviceImp = new Business() ;
            serviceImp.replaceevent(request, new ResponseCallBack<String>() {
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



    private void setApadter() {
        QuestionaireAdapter questionaireAdapter = new QuestionaireAdapter(context, feedbackQuestionnaires);
        recyclerView.setAdapter(questionaireAdapter);

        questionaireAdapter.setOnRadioButtonClickListener(new OnRadioButtonClickListener() {
            @Override
            public void OnRadioItemClickListener(int id, String value) {
                answersmap.put(id, value);

            }
        });

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}