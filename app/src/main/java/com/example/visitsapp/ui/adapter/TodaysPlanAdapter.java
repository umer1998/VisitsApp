package com.example.visitsapp.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.responce.TodayPlans;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.utils.OnItemClickListener;
import com.repsly.library.timelineview.LineType;
import com.repsly.library.timelineview.TimelineView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class TodaysPlanAdapter extends RecyclerView.Adapter<TodaysPlanAdapter.ViewHolder> {

    private MainActivity context;
    private ArrayList<TodayPlans> plans;
    private OnItemClickListener mItemClickListener;

    public TodaysPlanAdapter(MainActivity context, ArrayList<TodayPlans> plans) {
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
        try {
            holder.tvTime.setText(new SimpleDateFormat("HH:mm").
                    format(new SimpleDateFormat("hh:mm:ss").
                            parse(plans.get(position).time)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(view, position);
            }
        });
//        holder.tvTime.setText(plans.get(position).planned_on);

        holder.tvPurpose.setText(plans.get(position).event_purpose);
        holder.tvEvent.setText(plans.get(position).event);

    }

    @Override
    public int getItemCount() {
        return plans.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvEvent, tvPurpose, tvTime;
        private CardView cardView;


        ViewHolder(View view) {
            super(view);

            tvEvent = view.findViewById(R.id.eventType);
            tvPurpose = view.findViewById(R.id.eventPurpose);
            tvTime = view.findViewById(R.id.time);

            cardView = view.findViewById(R.id.card);

        }
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


}
