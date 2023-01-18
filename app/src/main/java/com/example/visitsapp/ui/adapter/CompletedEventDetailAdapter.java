package com.example.visitsapp.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.responce.PlansData;
import com.example.visitsapp.ui.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.IllformedLocaleException;


public class CompletedEventDetailAdapter extends RecyclerView.Adapter<CompletedEventDetailAdapter.ViewHolder> {

    private MainActivity context;
    private ArrayList<PlansData> list;


    public CompletedEventDetailAdapter(MainActivity context, ArrayList<PlansData> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.complete_event_detail_adapter;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(list.get(position).event.equalsIgnoreCase("leave")
        || list.get(position).event.equalsIgnoreCase("Office work")){
            holder.lllcoation.setVisibility(View.GONE);
        } else {
            holder.lllcoation.setVisibility(View.VISIBLE);

        }
        try {
            holder.tvTime.setText(new SimpleDateFormat("HH:mm a").
                    format(new SimpleDateFormat("hh:mm:ss").
                            parse(list.get(position).time)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvdate.setText(list.get(position).planned_on);
        holder.tvLocation.setText(list.get(position).purpose_child);
        holder.tvEventType.setText(list.get(position).event);

        holder.tvpurpose.setText(list.get(position).event_purpose);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTime, tvLocation, tvEventType, tvpurpose, tvdate;
        private LinearLayout lllcoation;


        ViewHolder(View view) {
            super(view);

            tvdate = view.findViewById(R.id.date);
            tvEventType = view.findViewById(R.id.eventType);
            tvTime = view.findViewById(R.id.time);
            tvLocation = view.findViewById(R.id.location);
            lllcoation = view.findViewById(R.id.lllocation);
            tvpurpose = view.findViewById(R.id.eventPurpose);

        }
    }



}
