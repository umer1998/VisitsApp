package com.example.visitsapp.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.responce.PlansData;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.utils.OnItemClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class UnApprovedEventsAdapter extends RecyclerView.Adapter<UnApprovedEventsAdapter.ViewHolder> {

    private MainActivity context;
    private ArrayList<PlansData> body;
    private OnItemClickListener mItemClickListener;


    public UnApprovedEventsAdapter(MainActivity context, ArrayList<PlansData> body) {
        this.context = context;
        this.body = body;
    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.execution_adapter;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(body.get(position).event.equalsIgnoreCase("leave")
                || body.get(position).event.equalsIgnoreCase("Office work")){
            holder.llLayout.setVisibility(View.GONE);
        } else {
            holder.llLayout.setVisibility(View.VISIBLE);

        }

        try {
            holder.tvTime.setText(new SimpleDateFormat("HH:mm a").
                    format(new SimpleDateFormat("hh:mm:ss").
                            parse(body.get(position).time)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvPurposeChild.setText(body.get(position).purpose_child);
        holder.tvPurpose.setText(body.get(position).event_purpose);
        holder.tvEvent.setText(body.get(position).event);
//        holder.card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                mItemClickListener.onItemClick(view, holder.getAdapterPosition());
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return body.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {


        RelativeLayout card;
        private LinearLayout llLayout;
        private TextView tvEvent, tvPurpose, tvTime, tvPurposeChild;

        ViewHolder(View view) {
            super(view);

            card =  view.findViewById(R.id.card);
            tvEvent = view.findViewById(R.id.eventType);
            tvPurpose = view.findViewById(R.id.eventPurpose);
            tvTime = view.findViewById(R.id.time);
            llLayout = view.findViewById(R.id.lllocation);
            tvPurposeChild = view.findViewById(R.id.child);

        }
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


}

