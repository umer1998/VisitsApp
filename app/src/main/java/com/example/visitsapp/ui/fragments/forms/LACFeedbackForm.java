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
import com.example.visitsapp.business.PostFeedBackResponce;
import com.example.visitsapp.business.impl.Business;
import com.example.visitsapp.db.DBHelper;
import com.example.visitsapp.db.myDbAdapter;
import com.example.visitsapp.delegate.ResponseCallBack;
import com.example.visitsapp.model.configuration.FeedbackQuestionnaire;
import com.example.visitsapp.model.request.CreateChangedPlan;
import com.example.visitsapp.model.request.CreateEventFeedback;
import com.example.visitsapp.model.request.CreatePlanRequest;
import com.example.visitsapp.model.request.Feedback;
import com.example.visitsapp.model.request.FeedbackReplace;
import com.example.visitsapp.model.request.Multiinput;
import com.example.visitsapp.model.request.PostFeedBackRequest;
import com.example.visitsapp.model.request.QuesAnswer;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.feedback.OnItemClickListener;
import com.example.visitsapp.ui.feedback.mainadapter;
import com.example.visitsapp.ui.fragments.BaseFragment;
import com.example.visitsapp.utils.OnMapListener;
import com.example.visitsapp.utils.alert.AlertUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


public class LACFeedbackForm extends BaseFragment {


    private MainActivity context;
    private RecyclerView recyclerView;
    private ArrayList<FeedbackQuestionnaire> feedbackQuestionnaires;
    private RelativeLayout rlSubmit;
    HashMap<Integer, String> answersmap = new HashMap<Integer, String>();
    private int id;
    private CreatePlanRequest createPlanRequest;

    public LACFeedbackForm(MainActivity context, ArrayList<FeedbackQuestionnaire> feedbackQuestionnaires, int id) {
        this.context = context;
        this.feedbackQuestionnaires = feedbackQuestionnaires;

        this.id = id;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_l_a_c_feedback_form, container, false);
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
        mainadapter todaysPlanAdapter = new mainadapter(context, feedbackQuestionnaires);
        recyclerView.setAdapter(todaysPlanAdapter);

        todaysPlanAdapter.setOnRadioButtonClickListener(new OnMapListener() {
            @Override
            public void onMapListener(HashMap<Integer, String> hashMap, Multiinput multiinput) {
                answersmap = hashMap;
                if(id == -1){
                    createEventandFeedback(multiinput);
                } else {
                    postFeedBack(hashMap, multiinput);
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


    private void postFeedBack(HashMap<Integer, String> hashMap, Multiinput multiinput) {


        ArrayList<Feedback> feedbacks = new ArrayList<>();
        PostFeedBackRequest postFeedBackRequest = new PostFeedBackRequest();
        ArrayList<QuesAnswer> quesAnswers = new ArrayList<>();


        for (Map.Entry<Integer, String> entry : hashMap.entrySet()) {
            QuesAnswer quesAnswer = new QuesAnswer();
            quesAnswer.setAnswer(entry.getValue());
            quesAnswer.setId(entry.getKey());

            quesAnswers.add(quesAnswer);
        }

        Feedback feedback = new Feedback();
        feedback.planner_event_id = id;
        feedback.questionaire = quesAnswers;

        ArrayList<Multiinput> list = new ArrayList<>();
        list.add(multiinput);
        feedback.multiinput = list;
        ArrayList<Multiinput> multiinputs = new ArrayList<>();
        feedback.multiinput = multiinputs;

        feedbacks.add(feedback);

        postFeedBackRequest.feedbacks = feedbacks;
        if (isNetworkAvailable()) {

            final AlertDialog dialog = AlertUtils.showLoader(context);

            if (dialog != null) {
                dialog.show();
            }

            Business serviceImp = new Business();
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
            DBHelper.getInstance().insertQuesPostFeedback(postFeedBackRequest.feedbacks.get(0).planner_event_id);

            for (int i = 0; i < postFeedBackRequest.feedbacks.get(0).questionaire.size(); i++) {
                DBHelper.getInstance().insertQuestionaire(postFeedBackRequest.feedbacks.get(0).planner_event_id,
                        postFeedBackRequest.feedbacks.get(0).questionaire.get(i).getAnswer(),
                        String.valueOf(postFeedBackRequest.feedbacks.get(0).questionaire.get(i).getId()));
            }

            DBHelper.getInstance().insertMultinput( postFeedBackRequest.feedbacks.get(0).multiinput.get(0).id);

            for(int i =0; i< postFeedBackRequest.feedbacks.get(0).multiinput.size(); i++){
                DBHelper.getInstance().insertMultinputQuestions(postFeedBackRequest.feedbacks.get(0).planner_event_id,
                        postFeedBackRequest.feedbacks.get(0).multiinput.get(0).id,
                        postFeedBackRequest.feedbacks.get(0).multiinput.get(0).questions.get(i).name,
                        postFeedBackRequest.feedbacks.get(0).multiinput.get(0).questions.get(i).designation);
            }

            context.homeFrag();
        }
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
//        else {
//            myDbAdapter dbHelper = new myDbAdapter(context);
//            for(int i = 0; i< quesAnswers.size(); i++){
//                dbHelper.insertQuestionaire(id, quesAnswers.get(i).getAnswer(), String.valueOf(quesAnswers.get(i).getId()));
//            }
//            dbHelper.insertQuesPostFeedback(id);
//
//        }
//            PostFeedBackRequest postFeedBackRequest1 = new PostFeedBackRequest();
//            if(SharedPrefrences.getInstance().getPostFeedBack() != null &&
//                  SharedPrefrences.getInstance().getPostFeedBack().feedbacks.size() > 0){
//
//                postFeedBackRequest1 = SharedPrefrences.getInstance().getPostFeedBack();
//                postFeedBackRequest1.feedbacks.add(feedback);
//                SharedPrefrences.getInstance().setPostFeedBack(postFeedBackRequest1);
//
//                ArrayList<PlansData> arrayList = new ArrayList<>();
//                arrayList = SharedPrefrences.getInstance().getExecutedEvent();
//                PlansData removeDataObj = new PlansData();
//                for(PlansData data: arrayList){
//                    if(data.id == feedback.planner_event_id){
//                        removeDataObj = data;
//                    }
//                }
//                arrayList.remove(removeDataObj);
//                SharedPrefrences.getInstance().setExecutedEvent(arrayList);
//
//            } else {
//
//
//                ArrayList<Feedback> feedbackArrayList1 = new ArrayList<>();
//                feedbackArrayList1.add(feedback);
//                postFeedBackRequest1.feedbacks = feedbackArrayList1;
//                SharedPrefrences.getInstance().setPostFeedBack(postFeedBackRequest1);
//
//                ArrayList<PlansData> arrayList = new ArrayList<>();
//                arrayList = SharedPrefrences.getInstance().getExecutedEvent();
//                PlansData removeDataObj = new PlansData();
//                for(PlansData data: arrayList){
//                    if(data.id == feedback.planner_event_id){
//                        removeDataObj = data;
//                    }
//                }
//                arrayList.remove(removeDataObj);
//                SharedPrefrences.getInstance().setExecutedEvent(arrayList);
//
//
//
//            }
//
//        }


}