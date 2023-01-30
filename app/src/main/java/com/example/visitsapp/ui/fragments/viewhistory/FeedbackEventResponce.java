package com.example.visitsapp.ui.fragments.viewhistory;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.visitsapp.R;
import com.example.visitsapp.business.impl.Business;
import com.example.visitsapp.delegate.ResponseCallBack;
import com.example.visitsapp.model.responce.FeedbackResponce;
import com.example.visitsapp.model.responce.ReportingTeam;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.fragments.BaseFragment;
import com.example.visitsapp.utils.alert.AlertUtils;

import java.util.ArrayList;


public class FeedbackEventResponce extends BaseFragment {

    private MainActivity context;
    private String id;
    private RecyclerView recyclerView, recyclerView1;
    private LinearLayout llmain;

    public FeedbackEventResponce(MainActivity context, String id) {
        this.context = context;
        this.id = id;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_feedback_event_responce, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        llmain = view.findViewById(R.id.llmain);

        recyclerView1 = view.findViewById(R.id.recyclerView1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));



        getFeedbackResponce(id);

        return view;
    }

    private void getFeedbackResponce(String id) {
        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }



        Business serviceImp = new Business() ;
        serviceImp.getFeedbackAnswers(id,  new ResponseCallBack<FeedbackResponce>() {
            @Override
            public void onSuccess(FeedbackResponce body) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                setAdpater(body);

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

    private void setAdpater(FeedbackResponce body) {
        MianAdapterClass historyEventAdapter = new MianAdapterClass(context, body.other , body.multiinputResponces);
        recyclerView.setAdapter(historyEventAdapter);

//        if(body.multiinputResponces.size() > 0){
//            llmain.setVisibility(View.VISIBLE);
//            MemeberPersonDetailAdapter todaysPlanAdapter = new MemeberPersonDetailAdapter(context, body.multiinputResponces.get(0));
//            recyclerView1.setAdapter(todaysPlanAdapter);
//
//        } else {
//            llmain.setVisibility(View.GONE);
//        }
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}