package com.example.visitsapp.ui.fragments.executedevent;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.visitsapp.R;
import com.example.visitsapp.business.impl.Business;
import com.example.visitsapp.delegate.ResponseCallBack;
import com.example.visitsapp.model.MapDateForPlanning;
import com.example.visitsapp.model.responce.PlansData;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.adapter.ExecutedEventsAdapter;
import com.example.visitsapp.ui.dialoguefragmens.CreatePlanDialogue;
import com.example.visitsapp.ui.fragments.BaseFragment;
import com.example.visitsapp.utils.alert.AlertUtils;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExecutionCompletedEvents extends BaseFragment {

    private RecyclerView recyclerView;
    private MainActivity context;

    private TextView tvmonthtext;


    public ExecutionCompletedEvents(MainActivity context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_execution_completed_events, container, false);





        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        getExecutionEvent();


        return view;
    }

    private void getExecutionEvent() {
        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }



        Business serviceImp = new Business() ;
        serviceImp.getCompletedEvents("current", new ResponseCallBack<ArrayList<PlansData>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(ArrayList<PlansData> body) {

                setAdapter(body);


                if (dialog != null) {
                    dialog.dismiss();
                }


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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setAdapter(ArrayList<PlansData> body) {

        HashMap<String, ArrayList<PlansData>> datePlanMap = new HashMap<String, ArrayList<PlansData>>();

        for(int i =0; i<body.size() ; i++){
            ArrayList<PlansData> dataPlan = new ArrayList<>();

            if(datePlanMap.get(body.get(i).planned_on) != null){
                dataPlan = datePlanMap.get(body.get(i).planned_on);
                dataPlan.add(body.get(i));
                datePlanMap.replace(body.get(i).planned_on, dataPlan);
            } else {
                dataPlan.add(body.get(i));
                datePlanMap.put(body.get(i).planned_on, dataPlan);
            }


//            if(datePlanMap != null && datePlanMap.size() > 0){
//                for(Map.Entry<String, ArrayList<PlansData>> mapElement: datePlanMap.entrySet()){
//
//
//                    dataPlan = mapElement.getValue();
//
//                    for(int j =0; j< dataPlan.size(); j++){
//                        if(dataPlan.get(j).planned_on.equalsIgnoreCase(body.get(i).planned_on)){
//                            dataPlan.add(body.get(i));
//                            datePlanMap.put(dataPlan.get(j).planned_on, dataPlan);
//                        } else {
//                            ArrayList<PlansData> dataPlan1 = new ArrayList<>();
//                            dataPlan1.add(body.get(i));
//                            datePlanMap.put(body.get(i).planned_on, dataPlan1);
//                        }
//                    }
//
//
//                }
//            } else {
//                dataPlan.add(body.get(i));
//                datePlanMap.put(body.get(i).planned_on, dataPlan);
//            }

        }

        ExecutedEventsAdapter executedEventsAdapter = new ExecutedEventsAdapter(context, datePlanMap);
        recyclerView.setAdapter(executedEventsAdapter);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}