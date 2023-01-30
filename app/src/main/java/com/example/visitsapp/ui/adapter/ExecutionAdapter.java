package com.example.visitsapp.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.responce.PlansData;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.utils.OnApproveListClick;
import com.example.visitsapp.utils.OnItemClickListener;
import com.repsly.library.timelineview.TimelineView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ExecutionAdapter extends RecyclerView.Adapter<ExecutionAdapter.ViewHolder> {

    private MainActivity context;
    private ArrayList<PlansData> body;
    private OnItemClickListener mItemClickListener;


    public ExecutionAdapter(MainActivity context, ArrayList<PlansData> body) {
        this.context = context;
        this.body = body;
    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.event_execution_adapter;

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
            holder.linearLayout.setVisibility(View.GONE);
        } else {
            holder.linearLayout.setVisibility(View.VISIBLE);

        }

        try {
            holder.tvTime.setText(new SimpleDateFormat("HH:mm a").
                    format(new SimpleDateFormat("hh:mm:ss").
                            parse(body.get(position).time)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tvDate.setText(body.get(position).planned_on);

        holder.rlhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.getHistoryEvent(body.get(position).purpose_child);
            }
        });

        holder.tvchild.setText(body.get(position).purpose_child);
        holder.tvPurpose.setText(body.get(position).event_purpose);
        holder.tvEvent.setText(body.get(position).event);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(view, holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return body.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {


        private LinearLayout linearLayout;
        RelativeLayout card, rlhistory;
        private TextView tvEvent, tvPurpose, tvTime, tvchild, tvDate;

        ViewHolder(View view) {
            super(view);

            card =  view.findViewById(R.id.card);
            tvEvent = view.findViewById(R.id.eventType);
            tvPurpose = view.findViewById(R.id.eventPurpose);
            tvTime = view.findViewById(R.id.time);
            linearLayout = view.findViewById(R.id.lllocation);
            tvchild = view.findViewById(R.id.child);
            tvDate = view.findViewById(R.id.date);

            rlhistory = view.findViewById(R.id.history);

        }
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


}

