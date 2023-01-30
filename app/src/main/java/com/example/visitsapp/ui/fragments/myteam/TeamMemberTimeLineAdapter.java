package com.example.visitsapp.ui.fragments.myteam;

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
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.adapter.EventDetailAdapter;
import com.example.visitsapp.ui.dialoguefragmens.UpdateEventDialogue;
import com.example.visitsapp.utils.UpdateListener;
import com.example.visitsapp.utils.alert.AlertUtils;
import com.repsly.library.timelineview.LineType;
import com.repsly.library.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.HashMap;


public class TeamMemberTimeLineAdapter extends RecyclerView.Adapter<TeamMemberTimeLineAdapter.ViewHolder> {


    private final ArrayList<PlanningDateObject> items;
    private MainActivity context;
    private HashMap<String, ArrayList<MapDateForPlanning>> datePlan = new HashMap<String, ArrayList<MapDateForPlanning>>();

    public TeamMemberTimeLineAdapter(MainActivity context, ArrayList<PlanningDateObject> items, HashMap<String, ArrayList<MapDateForPlanning>> datePlan) {
        this.items = items;
        this.context = context;
        this.datePlan = datePlan;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_vertical;
    }

    @Override
    public TeamMemberTimeLineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new TeamMemberTimeLineAdapter.ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TeamMemberTimeLineAdapter.ViewHolder holder, int position) {

        holder.recyclerView.setItemViewCacheSize(datePlan.get(items.get(position).getDate()).size());
        holder.recyclerView.setHasFixedSize(true);

        holder.timelineView.setLineType(getLineType(position));
        EventDetailAdapter eventDetailAdapter = new EventDetailAdapter(context,
                datePlan.get(items.get(position).getDate()));
        holder.recyclerView.setAdapter(eventDetailAdapter);

        holder.tvDay.setText(items.get(position).getDay());
        holder.tvDate.setText(items.get(position).getDateDay());


//
//        eventDetailAdapter.setOnItemClickListener(new UpdateEventClickListener() {
//            @Override
//            public void updateEventClickListener(int id, String value) {
//
//                if(id == -1){
//                    AlertUtils.showAlert(context, "You can't update approved Event !");
//                } else {
//                    UpdateEventDialogue dialog = new UpdateEventDialogue(context, id, value);
//                    dialog.show(context.getSupportFragmentManager(), "MyDialogFragment");
//                }
//
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private LineType getLineType(int position) {
//        if (getItemCount() == 1) {
//            return LineType.ONLYONE;
//
//        } else {
//            if (position == 0) {
//                return LineType.BEGIN;
//
//            } else if (position == getItemCount() - 1) {
//                return LineType.END;
//
//            } else {
        return LineType.NORMAL;
//            }
//        }
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