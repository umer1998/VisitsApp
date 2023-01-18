package com.example.visitsapp.ui.fragments.forms;

import android.content.Context;
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
import android.widget.RelativeLayout;

import com.example.visitsapp.R;
import com.example.visitsapp.business.impl.Business;
import com.example.visitsapp.db.DBHelper;
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
import com.example.visitsapp.ui.dialoguefragmens.NewEventinExecutionAdapter;
import com.example.visitsapp.ui.feedback.ReplaceEventMainAdapter;
import com.example.visitsapp.ui.feedback.mainadapter;
import com.example.visitsapp.ui.fragments.BaseFragment;
import com.example.visitsapp.utils.OnMapListener;
import com.example.visitsapp.utils.alert.AlertUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


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

        if(isNetworkAvailable()) {
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
        } else {
            int randomNum = ThreadLocalRandom.current().nextInt(123, 134756 + 1);
            DBHelper.getInstance().insertCreateFeedback(randomNum,request.changedPlan.get(0).new_event.getPlanned_on(),
                    request.changedPlan.get(0).new_event.getEvent_id(),
                    request.changedPlan.get(0).new_event.getPurpose_id(),
                    request.changedPlan.get(0).new_event.getPurpose_child_id());

            for(int i=0;i < request.changedPlan.get(0).feedbacks.get(0).questionaire.size(); i++){

                DBHelper.getInstance().insertQuestionaire(randomNum,
                        request.changedPlan.get(0).feedbacks.get(0).questionaire.get(i).getAnswer(),
                        String.valueOf(request.changedPlan.get(0).feedbacks.get(0).questionaire.get(i).getId()));


            }


            for(int i=0;i< request.changedPlan.get(0).feedbacks.get(0).multiinput.size(); i++){
                DBHelper.getInstance().insertMultinputQuestions(randomNum,
                        request.changedPlan.get(0).feedbacks.get(0).multiinput.get(i).id,
                        request.changedPlan.get(0).feedbacks.get(0).multiinput.get(0).questions.get(i).name,
                        request.changedPlan.get(0).feedbacks.get(0).multiinput.get(0).questions.get(i).designation);
            }

            context.homeFrag();
        }
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
        }
        else {
            DBHelper.getInstance().insertQuesReplaceFeedback(request.changedPlan.get(0).plannerEventId,
                    request.changedPlan.get(0).new_event.getPlanned_on(),
                    request.changedPlan.get(0).new_event.getEvent_id(),
                    request.changedPlan.get(0).new_event.getPurpose_id(),
                    request.changedPlan.get(0).new_event.getPurpose_child_id());


            for(int i=0;i < request.changedPlan.get(0).feedbacks.get(0).questionaire.size(); i++){

                DBHelper.getInstance().insertQuestionaire(request.changedPlan.get(0).plannerEventId,
                        request.changedPlan.get(0).feedbacks.get(0).questionaire.get(i).getAnswer(),
                        String.valueOf(request.changedPlan.get(0).feedbacks.get(0).questionaire.get(i).getId()));


            }


            for(int i=0;i< request.changedPlan.get(0).feedbacks.get(0).multiinput.size(); i++){
                DBHelper.getInstance().insertMultinputQuestions(request.changedPlan.get(0).plannerEventId,
                        request.changedPlan.get(0).feedbacks.get(0).multiinput.get(i).id,
                        request.changedPlan.get(0).feedbacks.get(0).multiinput.get(0).questions.get(i).name,
                        request.changedPlan.get(0).feedbacks.get(0).multiinput.get(0).questions.get(i).designation);
            }

            context.homeFrag();
        }





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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}