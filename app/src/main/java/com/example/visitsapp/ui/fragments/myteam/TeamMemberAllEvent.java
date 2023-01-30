package com.example.visitsapp.ui.fragments.myteam;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.visitsapp.model.PlanningDateObject;
import com.example.visitsapp.model.responce.PlansData;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.adapter.TimelineAdapter;
import com.example.visitsapp.ui.dialoguefragmens.CreatePlanDialogue;
import com.example.visitsapp.ui.fragments.BaseFragment;
import com.example.visitsapp.utils.alert.AlertUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class TeamMemberAllEvent extends BaseFragment {

    private RecyclerView recyclerView;
    private MainActivity context;
    List<String> data = new ArrayList<>();
    private RelativeLayout rlcplan;
    private LinearLayout llcplan;
    private TextView tvmonthtext;
    private String id;
    private BottomNavigationView navigationView;


    public TeamMemberAllEvent(MainActivity context,String id, LinearLayout llcplan, BottomNavigationView navigationView) {
        this.context = context;
        this.llcplan = llcplan;
        this.id = id;
        this.navigationView = navigationView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        llcplan.setVisibility(View.GONE);
        navigationView.setVisibility(View.GONE);
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_team_member_all_event, container, false);

        tvmonthtext = view.findViewById(R.id.monthtext);
        Date date = new Date();
        tvmonthtext.setText(new SimpleDateFormat("MMMM yyyy").format(date));

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        getPlans(id);

        return view;
    }

    private void getPlans(String id) {
        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }




        Business serviceImp = new Business() ;
        serviceImp.planswithId(id, new ResponseCallBack<List<PlansData>>() {
            @Override
            public void onSuccess(List<PlansData> body) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                try {
                    ArrayList<PlansData> list = new ArrayList<PlansData>(body);
                    setData(list);
                } catch (ParseException e) {
                    e.printStackTrace();
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


    private void setData(ArrayList<PlansData> data) throws ParseException {
        ArrayList<PlanningDateObject> dateList = new ArrayList<>();
        HashMap<String, ArrayList<MapDateForPlanning>> datePlan = new HashMap<String, ArrayList<MapDateForPlanning>>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());


//        PlanningDateObject planningDateObjectt = new PlanningDateObject();
//        planningDateObjectt.setDate(sdf.format(c.getTime()));
//        planningDateObjectt.setDateDay(new SimpleDateFormat("dd").format(c.getTime()));
//        planningDateObjectt.setDay(new SimpleDateFormat("MMM").format(c.getTime()));
//        dateList.add(planningDateObjectt);
        int count =0;
        do{
            ArrayList<MapDateForPlanning> list = new ArrayList<>();
            PlanningDateObject planningDateObject = new PlanningDateObject();
            planningDateObject.setDate(sdf.format(c.getTime()));
            planningDateObject.setDateDay(new SimpleDateFormat("dd").format(c.getTime()));
            planningDateObject.setDay(new SimpleDateFormat("MMM").format(c.getTime()));


            for(int j=0; j<data.size(); j++){
                MapDateForPlanning mapDateForPlanning = new MapDateForPlanning();
                String sDate1=data.get(j).planned_on;
                Date date1=new SimpleDateFormat("dd-MM-yyyy").parse(sDate1);
                if(sdf.format(c.getTime()).equals(sdf.format(date1))){
                    mapDateForPlanning.event = data.get(j).event;
                    mapDateForPlanning.planned_on = data.get(j).planned_on;
                    mapDateForPlanning.event_purpose = data.get(j).event_purpose;
                    if(c.get(Calendar.DAY_OF_WEEK) == 1){

                        mapDateForPlanning.haveEventStatus = 2;
                    } else {

                        mapDateForPlanning.haveEventStatus = 0;
                    }
                    mapDateForPlanning.id = data.get(j).id;
                    mapDateForPlanning.purpose_child = data.get(j).purpose_child;
                    mapDateForPlanning.status = data.get(j).status;
                    mapDateForPlanning.region =data.get(j).region;
                    mapDateForPlanning.area = data.get(j).area;
                    mapDateForPlanning.time = data.get(j).time;

                    list.add(mapDateForPlanning);
                }

            }
            if(list.size() == 0){
                MapDateForPlanning mapDateForPlanning = new MapDateForPlanning();
                mapDateForPlanning.event = "";
                mapDateForPlanning.planned_on = "";
                mapDateForPlanning.event_purpose = "";
                if(c.get(Calendar.DAY_OF_WEEK) == 1){

                    mapDateForPlanning.haveEventStatus = 2;
                } else {

                    mapDateForPlanning.haveEventStatus = 1;
                }
                mapDateForPlanning.purpose_child = "";
                mapDateForPlanning.status = "";
                mapDateForPlanning.region="";
                mapDateForPlanning.area="";
                mapDateForPlanning.time = "";

                list.add(mapDateForPlanning);
            }

            datePlan.put(sdf.format(c.getTime()), list );
            dateList.add(planningDateObject);
            c.add(Calendar.DATE, 1);


            count++;

        } while(count <= 30);


//        for(int i=0; i<=28; i++){
//
//            ArrayList<MapDateForPlanning> list = new ArrayList<>();
//            PlanningDateObject planningDateObject = new PlanningDateObject();
//            c.add(Calendar.DATE, 1);
//            planningDateObject.setDate(sdf.format(c.getTime()));
//            planningDateObject.setDateDay(new SimpleDateFormat("dd").format(c.getTime()));
//            planningDateObject.setDay(new SimpleDateFormat("MMM").format(c.getTime()));
//
//
//                for(int j=0; j<data.size(); j++){
//                    MapDateForPlanning mapDateForPlanning = new MapDateForPlanning();
//                    if(sdf.format(c.getTime()).equals(sdf.format(data.get(j).planned_on))){
//                        mapDateForPlanning.event = data.get(j).event;
//                        mapDateForPlanning.planned_on = data.get(j).planned_on;
//                        mapDateForPlanning.event_purpose = data.get(j).event_purpose;
//                        mapDateForPlanning.haveEventStatus = true;
//                        mapDateForPlanning.purpose_child = data.get(j).purpose_child;
//                        mapDateForPlanning.status = data.get(i).status;
//                        mapDateForPlanning.time = data.get(i).time;
//
//                        list.add(mapDateForPlanning);
//                    }
//
//                }
//                if(list.size() == 0){
//                    MapDateForPlanning mapDateForPlanning = new MapDateForPlanning();
//                    mapDateForPlanning.event = "";
//                    mapDateForPlanning.planned_on = "";
//                    mapDateForPlanning.event_purpose = "";
//                    mapDateForPlanning.haveEventStatus = false;
//                    mapDateForPlanning.purpose_child = "";
//                    mapDateForPlanning.status = "";
//                    mapDateForPlanning.time = "";
//
//                    list.add(mapDateForPlanning);
//                }
//
//                datePlan.put(sdf.format(c.getTime()), list );
//
//
//            dateList.add(planningDateObject);
//        }

        recyclerView.setItemViewCacheSize(dateList.size());
        recyclerView.setHasFixedSize(true);
        TeamMemberTimeLineAdapter adapter = new TeamMemberTimeLineAdapter(context, dateList, datePlan);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onBackPressed() {

        return true;
    }
}