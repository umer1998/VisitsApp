package com.example.visitsapp.ui.fragments;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.visitsapp.R;
import com.example.visitsapp.business.PostFeedBackResponce;
import com.example.visitsapp.business.impl.Business;
import com.example.visitsapp.delegate.ResponseCallBack;
import com.example.visitsapp.model.configuration.FeedbackQuestionnaire;
import com.example.visitsapp.model.request.ChangedPlan;
import com.example.visitsapp.model.request.Feedback;
import com.example.visitsapp.model.request.PostFeedBackRequest;
import com.example.visitsapp.model.request.QuesAnswer;
import com.example.visitsapp.model.request.ReplaceEventRequest;
import com.example.visitsapp.model.responce.PlansData;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.adapter.QuestionaireAdapter;
import com.example.visitsapp.utils.OnRadioButtonClickListener;
import com.example.visitsapp.utils.SharedPrefrences;
import com.example.visitsapp.utils.alert.AlertUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class QuestionairePostFeedFrag extends BaseFragment {


    private MainActivity context;
    private RecyclerView recyclerView;
    private ArrayList<FeedbackQuestionnaire> feedbackQuestionnaires;
    private RelativeLayout rlSubmit;
    HashMap<Integer, String> answersmap = new HashMap<Integer, String>();
    private int id;

    public QuestionairePostFeedFrag(MainActivity context, ArrayList<FeedbackQuestionnaire> feedbackQuestionnaires, int id) {
        this.context = context;
        this.feedbackQuestionnaires = feedbackQuestionnaires;
        this.id = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_questionaire_post_feed, container, false);


        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);


        rlSubmit = view.findViewById(R.id.submit);
        rlSubmit.setVisibility(View.VISIBLE);
        rlSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(feedbackQuestionnaires.size() != answersmap.size()){
                    AlertUtils.showAlert(context, "Answer all question !");
                } else {

                        postFeedBack();
                        context.homeFrag();

                }

            }
        });
        setApadter(feedbackQuestionnaires);
        return view;

    }

    private void postFeedBack() {


        ArrayList<Feedback> feedbacks = new ArrayList<>();
        PostFeedBackRequest postFeedBackRequest = new PostFeedBackRequest();
        ArrayList<QuesAnswer> quesAnswers = new ArrayList<>();

        for (Map.Entry<Integer, String> entry : answersmap.entrySet()) {
            QuesAnswer quesAnswer = new QuesAnswer();
            quesAnswer.setAnswer(entry.getValue());
            quesAnswer.setId(entry.getKey());

            quesAnswers.add(quesAnswer);
        }

        Feedback feedback = new Feedback();
        feedback.planner_event_id = id;
        feedback.questionaire = quesAnswers;

        feedbacks.add(feedback);

        postFeedBackRequest.feedbacks = feedbacks;
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
                    AlertUtils.showAlert(context, "FeedBack submitted successfully.");
                    context.homeFrag();

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

    private void setApadter(ArrayList<FeedbackQuestionnaire> feedbackQuestionnaires) {

        recyclerView.setItemViewCacheSize(feedbackQuestionnaires.size());
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