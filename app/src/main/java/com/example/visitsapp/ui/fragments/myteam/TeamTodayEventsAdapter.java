package com.example.visitsapp.ui.fragments.myteam;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import com.example.visitsapp.model.responce.PlansData;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.utils.OnItemClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class TeamTodayEventsAdapter extends RecyclerView.Adapter<TeamTodayEventsAdapter.ViewHolder> {

    private MainActivity context;
    private OnItemClickListener mItemClickListener;
    private ArrayList<PlansData> plans = new ArrayList<>();


    public TeamTodayEventsAdapter(MainActivity context, ArrayList<PlansData> events) {
        this.context = context;
        this.plans = events;

    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.team_today_event_adapter;

    }

    @Override
    public TeamTodayEventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new TeamTodayEventsAdapter.ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TeamTodayEventsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if(plans.get(position).event.equalsIgnoreCase("Field Visit")){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFF0BC"));
        } else if(plans.get(position).event.equalsIgnoreCase("Meeting")){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#D2F3DB"));
        }



        try {
            holder.tvTime.setText(new SimpleDateFormat("HH:mm").
                    format(new SimpleDateFormat("hh:mm:ss").
                            parse(plans.get(position).time)));
        } catch (ParseException e) {
            e.printStackTrace();
        }


//        holder.tvTime.setText(plans.get(position).planned_on);

        holder.tvPurpose.setText(plans.get(position).event_purpose);
        holder.tvEvent.setText(plans.get(position).event);
        holder.tvpurpose.setText(plans.get(position).purpose_child);
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {



        private TextView tvEvent, tvPurpose, tvTime, tvpurpose;
        private CardView cardView;
        private LinearLayout llLocation;




        ViewHolder(View view) {
            super(view);
            llLocation = view.findViewById(R.id.lllocation);
            tvEvent = view.findViewById(R.id.eventType);
            tvPurpose = view.findViewById(R.id.eventPurpose);
            tvTime = view.findViewById(R.id.time);
            tvpurpose = view.findViewById(R.id.child);
            cardView = view.findViewById(R.id.card);

        }
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


}

