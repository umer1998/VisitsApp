package com.example.visitsapp.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.responce.GetLeavesResponce;
import com.example.visitsapp.model.responce.TodayPlans;
import com.example.visitsapp.ui.MainActivity;

import java.util.ArrayList;



public class LeavesAdapter extends RecyclerView.Adapter<LeavesAdapter.ViewHolder> {

    private MainActivity context;
    private ArrayList<GetLeavesResponce> plans = new ArrayList<>();

    public LeavesAdapter(MainActivity context, ArrayList<GetLeavesResponce> plans) {
        this.context = context;
        this.plans = plans;
    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.todays_plan_layout;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tvTime.setText(plans.get(position).planned_on);

        holder.tvPurpose.setText(plans.get(position).event_purpose);
        holder.tvEvent.setText(plans.get(position).event);

    }

    @Override
    public int getItemCount() {
        return plans.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvEvent, tvPurpose, tvTime;


        ViewHolder(View view) {
            super(view);

            tvEvent = view.findViewById(R.id.eventType);
            tvPurpose = view.findViewById(R.id.eventPurpose);
            tvTime = view.findViewById(R.id.time);

        }
    }

}
