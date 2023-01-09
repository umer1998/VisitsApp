package com.example.visitsapp.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class UnexecutedEventAdapter extends RecyclerView.Adapter<UnexecutedEventAdapter.ViewHolder> {

    private MainActivity context;
    private ArrayList<PlansData> body;
    private OnItemClickListener mItemClickListener;


    public UnexecutedEventAdapter(MainActivity context, ArrayList<PlansData> body) {
        this.context = context;
        this.body = body;
    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.un_execution_adapter;

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
            holder.tvTime.setText(new SimpleDateFormat("HH:mm a").
                    format(new SimpleDateFormat("hh:mm:ss").
                            parse(body.get(position).time)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvPurpose.setText(body.get(position).event_purpose);
        holder.tvEvent.setText(body.get(position).event);
        holder.tvpurposechild.setText(body.get(position).purpose_child);
        holder.tvdate.setText(body.get(position).planned_on);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mItemClickListener.onItemClick(view, holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return body.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {


        RelativeLayout card;
        private TextView tvEvent, tvPurpose, tvTime, tvdate, tvpurposechild;

        ViewHolder(View view) {
            super(view);

            card =  view.findViewById(R.id.card);
            tvEvent = view.findViewById(R.id.eventType);
            tvPurpose = view.findViewById(R.id.eventpurpose);
            tvTime = view.findViewById(R.id.time);

            tvdate = view.findViewById(R.id.date);
            tvpurposechild = view.findViewById(R.id.eventPurpose);

        }
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


}

