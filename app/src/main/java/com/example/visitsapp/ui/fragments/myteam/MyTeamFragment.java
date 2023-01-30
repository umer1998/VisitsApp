package com.example.visitsapp.ui.fragments.myteam;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.visitsapp.R;
import com.example.visitsapp.business.impl.Business;
import com.example.visitsapp.delegate.ResponseCallBack;
import com.example.visitsapp.model.responce.GetReportingTeamResponce;
import com.example.visitsapp.model.responce.ReportingTeam;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.fragments.BaseFragment;
import com.example.visitsapp.utils.OnItemClickListener;
import com.example.visitsapp.utils.alert.AlertUtils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;


public class MyTeamFragment extends BaseFragment {

    private MainActivity context;
    private RecyclerView recyclerView;


    private LinearLayout ll1, ll2, ll3, ll4, ll5, ll6, ll7;
    private View v1,v2,v3,v4,v5,v6,v7;

    private TextView tvDate1, tvDate2, tvDate3, tvDate4, tvDate5, tvDate6, tvDate7;
    private TextView tvday1, tvday2, tvday3, tvday4, tvday5, tvday6, tvday7;


    private LocalDate currentdate;

    public MyTeamFragment(MainActivity mainActivity) {
        this.context = mainActivity;
    }
    Format f = new SimpleDateFormat("EEE");



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_team, container, false);

        currentdate = LocalDate.now();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        tvDate1 = view.findViewById(R.id.date1);
        tvDate1.setText(String.valueOf(currentdate.getDayOfMonth()));
        tvDate2 = view.findViewById(R.id.date2);
        tvDate2.setText(String.valueOf(currentdate.plusDays(1).getDayOfMonth()));
        tvDate3 = view.findViewById(R.id.date3);
        tvDate3.setText(String.valueOf(currentdate.plusDays(2).getDayOfMonth()));
        tvDate4 = view.findViewById(R.id.date4);
        tvDate4.setText(String.valueOf(currentdate.plusDays(3).getDayOfMonth()));
        tvDate5 = view.findViewById(R.id.date5);
        tvDate5.setText(String.valueOf(currentdate.plusDays(4).getDayOfMonth()));
        tvDate6 = view.findViewById(R.id.date6);
        tvDate6.setText(String.valueOf(currentdate.plusDays(5).getDayOfMonth()));
        tvDate7 = view.findViewById(R.id.date7);
        tvDate7.setText(String.valueOf(currentdate.plusDays(6).getDayOfMonth()));

        tvday1 = view.findViewById(R.id.day1);
        tvday1.setText(String.valueOf(currentdate.getDayOfWeek()).substring(0,3));
        tvday2 = view.findViewById(R.id.day2);
        tvday2.setText(String.valueOf(currentdate.plusDays(1).getDayOfWeek()).substring(0,3));
        tvday3 = view.findViewById(R.id.day3);
        tvday3.setText(String.valueOf(currentdate.plusDays(2).getDayOfWeek()).substring(0,3));
        tvday4 = view.findViewById(R.id.day4);
        tvday4.setText(String.valueOf(currentdate.plusDays(3).getDayOfWeek()).substring(0,3));
        tvday5 = view.findViewById(R.id.day5);
        tvday5.setText(String.valueOf(currentdate.plusDays(4).getDayOfWeek()).substring(0,3));
        tvday6 = view.findViewById(R.id.day6);
        tvday6.setText(String.valueOf(currentdate.plusDays(5).getDayOfWeek()).substring(0,3));
        tvday7 = view.findViewById(R.id.day7);
        tvday7.setText(String.valueOf(currentdate.plusDays(6).getDayOfWeek()).substring(0,3));




        setWeekView(view);
        getReportingTeam(String.valueOf(currentdate));

        return view;
    }

    private void setAdapter(ArrayList<ReportingTeam> body) {
        TeamDetailAdapter teamDetailAdapter = new TeamDetailAdapter(context, body);
        recyclerView.setAdapter(teamDetailAdapter);

        teamDetailAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                context.teamMemberAllEvent(String.valueOf(position));
            }
        });

    }

    private void getReportingTeam(String date) {
        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }



        Business serviceImp = new Business() ;
        serviceImp.reportingTeam(date,  new ResponseCallBack<ArrayList<ReportingTeam>>() {
            @Override
            public void onSuccess(ArrayList<ReportingTeam> body) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                setAdapter(body);

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
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeekView(View view) {

        v1 = view.findViewById(R.id.v1);
        v2 = view.findViewById(R.id.v2);
        v3 = view.findViewById(R.id.v3);
        v4 = view.findViewById(R.id.v4);
        v5 = view.findViewById(R.id.v5);
        v6 = view.findViewById(R.id.v6);
        v7 = view.findViewById(R.id.v7);


        ll1 = view.findViewById(R.id.one);
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.GONE);
                v3.setVisibility(View.GONE);
                v4.setVisibility(View.GONE);
                v5.setVisibility(View.GONE);
                v6.setVisibility(View.GONE);
                v7.setVisibility(View.GONE);

                ll1.setBackground(context.getDrawable(R.drawable.team_date_select_bg));
                ll2.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll3.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll4.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll5.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll6.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll7.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));

                getReportingTeam(String.valueOf(currentdate));

            }
        });

        ll2 = view.findViewById(R.id.two);
        ll2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                v1.setVisibility(View.GONE);
                v2.setVisibility(View.VISIBLE);
                v3.setVisibility(View.GONE);
                v4.setVisibility(View.GONE);
                v5.setVisibility(View.GONE);
                v6.setVisibility(View.GONE);
                v7.setVisibility(View.GONE);

                ll1.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll2.setBackground(context.getDrawable(R.drawable.team_date_select_bg));
                ll3.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll4.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll5.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll6.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll7.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));

                getReportingTeam(String.valueOf(currentdate.plusDays(1)));
            }
        });

        ll3 = view.findViewById(R.id.three);
        ll3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                v1.setVisibility(View.GONE);
                v2.setVisibility(View.GONE);
                v3.setVisibility(View.VISIBLE);
                v4.setVisibility(View.GONE);
                v5.setVisibility(View.GONE);
                v6.setVisibility(View.GONE);
                v7.setVisibility(View.GONE);

                ll1.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll2.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll3.setBackground(context.getDrawable(R.drawable.team_date_select_bg));
                ll4.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll5.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll6.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll7.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));

                getReportingTeam(String.valueOf(currentdate.plusDays(2)));
            }
        });

        ll4 = view.findViewById(R.id.four);
        ll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v1.setVisibility(View.GONE);
                v2.setVisibility(View.GONE);
                v3.setVisibility(View.GONE);
                v4.setVisibility(View.VISIBLE);
                v5.setVisibility(View.GONE);
                v6.setVisibility(View.GONE);
                v7.setVisibility(View.GONE);

                ll1.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll2.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll3.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll4.setBackground(context.getDrawable(R.drawable.team_date_select_bg));
                ll5.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll6.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll7.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                getReportingTeam(String.valueOf(currentdate.plusDays(3)));
            }
        });

        ll5 = view.findViewById(R.id.five);
        ll5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                v1.setVisibility(View.GONE);
                v2.setVisibility(View.GONE);
                v3.setVisibility(View.GONE);
                v4.setVisibility(View.GONE);
                v5.setVisibility(View.VISIBLE);
                v6.setVisibility(View.GONE);
                v7.setVisibility(View.GONE);

                ll1.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll2.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll3.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll4.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll5.setBackground(context.getDrawable(R.drawable.team_date_select_bg));
                ll6.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll7.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));

                getReportingTeam(String.valueOf(currentdate.plusDays(4)));
            }
        });

        ll6 = view.findViewById(R.id.six);
        ll6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v1.setVisibility(View.GONE);
                v2.setVisibility(View.GONE);
                v3.setVisibility(View.GONE);
                v4.setVisibility(View.GONE);
                v5.setVisibility(View.GONE);
                v6.setVisibility(View.VISIBLE);
                v7.setVisibility(View.GONE);

                ll1.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll2.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll3.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll4.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll5.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll6.setBackground(context.getDrawable(R.drawable.team_date_select_bg));
                ll7.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));

                getReportingTeam(String.valueOf(currentdate.plusDays(5)));
            }
        });

        ll7 = view.findViewById(R.id.seven);
        ll7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v1.setVisibility(View.GONE);
                v2.setVisibility(View.GONE);
                v3.setVisibility(View.GONE);
                v4.setVisibility(View.GONE);
                v5.setVisibility(View.GONE);
                v6.setVisibility(View.GONE);
                v7.setVisibility(View.VISIBLE);

                ll1.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll2.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll3.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll4.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll5.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll6.setBackground(context.getDrawable(R.drawable.team_date_unselect_bg));
                ll7.setBackground(context.getDrawable(R.drawable.team_date_select_bg));

                getReportingTeam(String.valueOf(currentdate.plusDays(6)));
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}