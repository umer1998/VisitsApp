package com.example.visitsapp.ui.fragments.forms;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.visitsapp.R;
import com.example.visitsapp.business.impl.Business;
import com.example.visitsapp.db.myDbAdapter;
import com.example.visitsapp.delegate.ResponseCallBack;
import com.example.visitsapp.model.configuration.FeedbackQuestionnaire;
import com.example.visitsapp.model.request.ChangedPlan;
import com.example.visitsapp.model.request.CreateChangedPlan;
import com.example.visitsapp.model.request.CreateEventFeedback;
import com.example.visitsapp.model.request.CreatePlanRequest;
import com.example.visitsapp.model.request.FeedbackReplace;
import com.example.visitsapp.model.request.Multiinput;
import com.example.visitsapp.model.request.QuesAnswer;
import com.example.visitsapp.model.request.ReplaceEventRequest;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.feedback.ReplaceEventMainAdapter;
import com.example.visitsapp.ui.feedback.mainadapter;
import com.example.visitsapp.ui.fragments.BaseFragment;
import com.example.visitsapp.utils.OnMapListener;
import com.example.visitsapp.utils.alert.AlertUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DisbursementFrag extends BaseFragment {

    private MainActivity context;
    private RecyclerView recyclerView;
    private ArrayList<FeedbackQuestionnaire> feedbackQuestionnaires;
    private RelativeLayout rlSubmit;
    HashMap<Integer, String> answersmap = new HashMap<Integer, String>();
    private int id;
    private CreatePlanRequest createPlanRequest;

    public DisbursementFrag(MainActivity context, ArrayList<FeedbackQuestionnaire> feedbackQuestionnaires,
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
        View view = inflater.inflate(R.layout.fragment_disbursement, container, false);

        context.bottomNavigationView.setVisibility(View.GONE);
        context.llcplan.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        setAdapter();

        return view;
    }

    private void setAdapter() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(feedbackQuestionnaires.size());
        ReplaceEventMainAdapter todaysPlanAdapter = new ReplaceEventMainAdapter(context, feedbackQuestionnaires);
        recyclerView.setAdapter(todaysPlanAdapter);

        todaysPlanAdapter.setOnRadioButtonClickListener(new OnMapListener() {
            @Override
            public void onMapListener(HashMap<Integer, String> hashMap, Multiinput multiinput) {
                answersmap = hashMap;
                if(id == -1){
                    createEventandFeedback(multiinput);
                } else {
                    replaceEventAndPost(multiinput);
                }

            }


        });


    }

    private void createEventandFeedback(Multiinput multiinput) {


        CreateEventFeedback request = new CreateEventFeedback();
        ArrayList<CreateChangedPlan> changedPlansList = new ArrayList<>();
        CreateChangedPlan changedPlan = new CreateChangedPlan();
        changedPlan.new_event = createPlanRequest;

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
        if(multiinput.id == 0){
            ArrayList<Multiinput> list = new ArrayList<>();
            feedbackReplace.multiinput = list;
        } else {
            ArrayList<Multiinput> list = new ArrayList<>();
            list.add(multiinput);
            feedbackReplace.multiinput = list;
        }

        feedbacks.add(feedbackReplace);

        changedPlan.feedbacks = feedbacks;
        changedPlansList.add(changedPlan);
        request.changedPlan = changedPlansList;

//        if(isNetworkAvailable()) {
            final AlertDialog dialog = AlertUtils.showLoader(context);
            if (dialog != null) {
                dialog.show();
            }
            Business serviceImp = new Business();
            serviceImp.createFeedback(request, new ResponseCallBack<String>() {
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
//        } else {
//            myDbAdapter dbHelper = new myDbAdapter(context);
//            for(int i = 0; i< quesAnswers.size(); i++){
//                dbHelper.insertQuestionaire(id, quesAnswers.get(i).getAnswer(), String.valueOf(quesAnswers.get(i).getId()));
//            }
//            dbHelper.insertCreateFeedback(createPlanRequest.getPlanned_on(),
//                    createPlanRequest.getEvent_id(),
//                    createPlanRequest.getPurpose_id(),
//                    createPlanRequest.getPurpose_child_id());
//        }
    }

    private void replaceEventAndPost(Multiinput multiinput) {





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
        ArrayList<Multiinput> list = new ArrayList<>();
        list.add(multiinput);
        feedbackReplace.multiinput = list;
        feedbackReplace.questionaire = quesAnswers;
        feedbacks.add(feedbackReplace);



        changedPlan.feedbacks = feedbacks;
        changedPlansList.add(changedPlan);
        request.changedPlan = changedPlansList;

//        request.changedPlan.add(changedPlan);

//        if(isNetworkAvailable()){
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
//        }
//        else {
//            ReplaceEventRequest replaceEventRequest = new ReplaceEventRequest();
//            if(SharedPrefrences.getInstance().getReplaceEventFeedBack() != null &&
//                    SharedPrefrences.getInstance().getReplaceEventFeedBack().changedPlan.size() > 0){
//
//                replaceEventRequest = SharedPrefrences.getInstance().getReplaceEventFeedBack();
//                replaceEventRequest.changedPlan.add(changedPlan);
//                SharedPrefrences.getInstance().setReplaceEventFeedBack(replaceEventRequest);
//                ArrayList<PlansData> arrayList = new ArrayList<>();
//                arrayList = SharedPrefrences.getInstance().getExecutedEvent();
//                PlansData removeDataObj = new PlansData();
//                for(PlansData data: arrayList){
//                    if(data.id == changedPlan.plannerEventId){
//                        removeDataObj = data;
//                    }
//                }
//                arrayList.remove(removeDataObj);
//                SharedPrefrences.getInstance().setExecutedEvent(arrayList);
//
//            } else {
//
//                ArrayList<ChangedPlan> changedPlanArrayList1 = new ArrayList<>();
//                changedPlanArrayList1.add(changedPlan);
//                replaceEventRequest.changedPlan = changedPlanArrayList1;
//                SharedPrefrences.getInstance().setReplaceEventFeedBack(replaceEventRequest);
//                ArrayList<PlansData> arrayList = new ArrayList<>();
//                arrayList = SharedPrefrences.getInstance().getExecutedEvent();
//                PlansData removeDataObj = new PlansData();
//                for(PlansData data: arrayList){
//                    if(data.id == changedPlan.plannerEventId){
//                        removeDataObj = data;
//                    }
//                }
//                arrayList.remove(removeDataObj);
//                SharedPrefrences.getInstance().setExecutedEvent(arrayList);
//
//            }
//        }


    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}