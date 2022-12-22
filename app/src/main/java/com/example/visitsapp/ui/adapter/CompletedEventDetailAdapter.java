package com.example.visitsapp.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.responce.PlansData;
import com.example.visitsapp.ui.MainActivity;

import java.util.ArrayList;


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

        holder.tvTime.setText(list.get(position).time);
        holder.tvLocation.setText(list.get(position).event_purpose);
        holder.tvEventType.setText(list.get(position).event);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTime, tvLocation, tvEventType;


        ViewHolder(View view) {
            super(view);

            tvEventType = view.findViewById(R.id.eventType);
            tvTime = view.findViewById(R.id.time);
            tvLocation = view.findViewById(R.id.location);

        }
    }



}
