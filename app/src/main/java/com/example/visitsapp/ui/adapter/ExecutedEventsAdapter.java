package com.example.visitsapp.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.MapDateForPlanning;
import com.example.visitsapp.model.PlanningDateObject;
import com.example.visitsapp.model.responce.PlansData;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.dialoguefragmens.UpdateEventDialogue;
import com.example.visitsapp.utils.UpdateListener;
import com.example.visitsapp.utils.alert.AlertUtils;
import com.repsly.library.timelineview.LineType;
import com.repsly.library.timelineview.TimelineView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;



public class ExecutedEventsAdapter extends RecyclerView.Adapter<ExecutedEventsAdapter.ViewHolder> {



    private MainActivity context;
    private HashMap<String, ArrayList<PlansData>> datePlanMap ;


    public ExecutedEventsAdapter(MainActivity context, HashMap<String, ArrayList<PlansData>> datePlanMap ) {
        this.context = context;
        this.datePlanMap = datePlanMap;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_vertical;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String key = (String) datePlanMap.keySet().toArray()[position];

        holder.timelineView.setLineType(getLineType(position));
        ArrayList<PlansData> list = datePlanMap.get(key);
        CompletedEventDetailAdapter completedEventDetailAdapter = new CompletedEventDetailAdapter(context, list);
        holder.recyclerView.setAdapter(completedEventDetailAdapter);

        try {
            holder.tvDate.setText(new SimpleDateFormat("dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(list.get(0).planned_on)));
            holder.tvDay.setText(new SimpleDateFormat("MMM").format(new SimpleDateFormat("dd-MM-yyyy").parse(list.get(0).planned_on)));
        } catch (ParseException e) {
            e.printStackTrace();
        }





    }

    @Override
    public int getItemCount() {
        return datePlanMap.size();
    }

    private LineType getLineType(int position) {
        if (getItemCount() == 1) {
            return LineType.ONLYONE;

        } else {
            if (position == 0) {
                return LineType.BEGIN;

            } else if (position == getItemCount() - 1) {
                return LineType.END;

            } else {
                return LineType.NORMAL;
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TimelineView timelineView;
        RecyclerView recyclerView;
        TextView tvDate, tvDay;

        ViewHolder(View view) {
            super(view);
            timelineView = (TimelineView) view.findViewById(R.id.timeline);
            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
            tvDate = view.findViewById(R.id.date);
            tvDay = view.findViewById(R.id.day);

        }
    }

}