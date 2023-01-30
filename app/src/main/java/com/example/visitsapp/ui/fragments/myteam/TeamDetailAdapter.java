package com.example.visitsapp.ui.fragments.myteam;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.responce.GetReportingTeamResponce;
import com.example.visitsapp.model.responce.ReportingTeam;
import com.example.visitsapp.model.responce.TodayPlans;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.utils.OnItemClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;



public class TeamDetailAdapter extends RecyclerView.Adapter<TeamDetailAdapter.ViewHolder> {

    private MainActivity context;
    private OnItemClickListener mItemClickListener;
    private ArrayList<ReportingTeam> body;

    public TeamDetailAdapter(MainActivity context, ArrayList<ReportingTeam> body) {
        this.context = context;
        this.body = body;
    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.team_detail_adapter;

    }

    @Override
    public TeamDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new TeamDetailAdapter.ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TeamDetailAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvNAme.setText(body.get(position).fullname);
        holder.tvDesignation.setText(body.get(position).designation);

        if(body.get(position).events.size()> 0){
            holder.recyclerView.setVisibility(View.VISIBLE);
            holder.rlNoRecord.setVisibility(View.GONE);
            TeamTodayEventsAdapter teamTodayEventsAdapter = new TeamTodayEventsAdapter(context, body.get(position).events);
            holder.recyclerView.setAdapter(teamTodayEventsAdapter);
        } else {
            holder.recyclerView.setVisibility(View.GONE);
            holder.rlNoRecord.setVisibility(View.VISIBLE);

        }

        holder.rlAllevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(view, body.get(position).id);
            }
        });


    }

    @Override
    public int getItemCount() {
        return body.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNAme, tvDesignation;
        private RelativeLayout rlAllevent, rlNoRecord;
        private RecyclerView recyclerView;




        ViewHolder(View view) {
            super(view);

            tvDesignation = view.findViewById(R.id.designation);
            tvNAme= view.findViewById(R.id.name);

            rlNoRecord =view.findViewById(R.id.rlnorecord);

            rlAllevent = view.findViewById(R.id.allevent);

            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        }
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


}

