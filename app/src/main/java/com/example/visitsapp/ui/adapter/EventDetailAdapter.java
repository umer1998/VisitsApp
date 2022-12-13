package com.example.visitsapp.ui.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.MapDateForPlanning;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.utils.OnItemClickListener;
import com.example.visitsapp.utils.OnRadioButtonClickListener;
import com.example.visitsapp.utils.UpdateEventClickListener;
import com.example.visitsapp.utils.UpdateListener;
import com.example.visitsapp.utils.alert.AlertUtils;
import com.repsly.library.timelineview.TimelineView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class EventDetailAdapter extends RecyclerView.Adapter<EventDetailAdapter.ViewHolder> {

    private MainActivity context;
    ArrayList<MapDateForPlanning> mapDateForPlannings;
    private UpdateListener mItemCLickListener ;

    public EventDetailAdapter(MainActivity context, ArrayList<MapDateForPlanning> mapDateForPlannings) {
        this.context = context;
        this.mapDateForPlannings = mapDateForPlannings;
    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.event_detail_adapter;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(mapDateForPlannings.get(position).status.equalsIgnoreCase("pending")){

            if(mapDateForPlannings.get(position).event.equalsIgnoreCase("leave")){
                holder.leaveCard.setCardBackgroundColor(Color.parseColor("#999999"));
                holder.tvLeaveType.setTextColor(Color.parseColor("#FFFFFF"));
                holder.tvLeavePurpose.setTextColor(Color.parseColor("#FFFFFF"));
                holder.noEventCard.setVisibility(View.GONE);
                holder.leaveCard.setVisibility(View.VISIBLE);
                holder.sundayCard.setVisibility(View.GONE);
                holder.cardView.setVisibility(View.GONE);
                holder.tvLeavePurpose.setText(mapDateForPlannings.get(position).event_purpose);
                holder.eventnooCard.setVisibility(View.GONE);

            } else {
                holder.noEventCard.setVisibility(View.VISIBLE);
                holder.sundayCard.setVisibility(View.GONE);
                holder.cardView.setVisibility(View.GONE);
                holder.leaveCard.setVisibility(View.GONE);
                holder.eventnooCard.setVisibility(View.GONE);

                holder.tvNEventType.setText(mapDateForPlannings.get(position).event);
                try {
                    holder.tvNTime.setText(new SimpleDateFormat("HH:mm a").
                            format(new SimpleDateFormat("hh:mm:ss").
                                    parse(mapDateForPlannings.get(position).time)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                holder.tvNLocation.setText(mapDateForPlannings.get(position).event_purpose);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemCLickListener.updateListener(mapDateForPlannings.get(position).id, mapDateForPlannings.get(position));
                }
            });

//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    try {
//                        mItemCLickListener.updateEventClickListener(mapDateForPlannings.get(position).id,
//                                new SimpleDateFormat("yyyy-MM-dd").
//                                        format(new SimpleDateFormat("dd-MM-yyyy").
//                                                parse(mapDateForPlannings.get(position).planned_on))+
//                                " "+mapDateForPlannings.get(position).time);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });


        } else if(mapDateForPlannings.get(position).status.equalsIgnoreCase("approved")
                    || mapDateForPlannings.get(position).status.equalsIgnoreCase("completed")){

            if(mapDateForPlannings.get(position).event.equalsIgnoreCase("leave")){
                holder.leaveCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.tvLeaveType.setTextColor(Color.parseColor("#000000"));
                holder.tvLeavePurpose.setTextColor(Color.parseColor("#000000"));
                holder.noEventCard.setVisibility(View.GONE);
                holder.leaveCard.setVisibility(View.VISIBLE);
                holder.sundayCard.setVisibility(View.GONE);
                holder.cardView.setVisibility(View.GONE);
                holder.eventnooCard.setVisibility(View.GONE);

                holder.tvLeavePurpose.setText(mapDateForPlannings.get(position).event_purpose);


            } else {

                holder.noEventCard.setVisibility(View.GONE);
                holder.sundayCard.setVisibility(View.GONE);
                holder.cardView.setVisibility(View.VISIBLE);
                holder.leaveCard.setVisibility(View.GONE);
                holder.eventnooCard.setVisibility(View.GONE);

                if (mapDateForPlannings.get(position).event.equalsIgnoreCase("Field Visit")) {
                    holder.cardView.setCardBackgroundColor(Color.parseColor("#FFF0BC"));
                } else if (mapDateForPlannings.get(position).event.equalsIgnoreCase("Meeting")) {
                    holder.cardView.setCardBackgroundColor(Color.parseColor("#D2F3DB"));
                } else if (mapDateForPlannings.get(position).event.equalsIgnoreCase("Leave")) {
                    holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                } else {
                    holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                }

                holder.tvEventType.setText(mapDateForPlannings.get(position).event);
                try {
                    holder.tvTime.setText(new SimpleDateFormat("HH:mm a").
                            format(new SimpleDateFormat("hh:mm:ss").
                                    parse(mapDateForPlannings.get(position).time)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                holder.tvLocation.setText(mapDateForPlannings.get(position).event_purpose);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemCLickListener.updateListener(-1, mapDateForPlannings.get(position));
                }
            });
        }
//        else if(mapDateForPlannings.get(position).haveEventStatus == 0) {
//            holder.noEventCard.setVisibility(View.GONE);
//            holder.sundayCard.setVisibility(View.GONE);
//            holder.cardView.setVisibility(View.VISIBLE);
//
//            if(mapDateForPlannings.get(position).event.equalsIgnoreCase("Field Visit")){
//                holder.cardView.setCardBackgroundColor(Color.parseColor("#FFF0BC"));
//            } else if(mapDateForPlannings.get(position).event.equalsIgnoreCase("Meeting")) {
//                holder.cardView.setCardBackgroundColor(Color.parseColor("#D2F3DB"));
//            } else if(mapDateForPlannings.get(position).event.equalsIgnoreCase("Leave")){
//                holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
//            } else {
//                holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
//            }
//
//        }
        else if(mapDateForPlannings.get(position).haveEventStatus == 2)  {
            holder.noEventCard.setVisibility(View.GONE);
            holder.sundayCard.setVisibility(View.VISIBLE);
            holder.cardView.setVisibility(View.GONE);
            holder.eventnooCard.setVisibility(View.GONE);
            holder.leaveCard.setVisibility(View.GONE);
        }
        else {
            holder.noEventCard.setVisibility(View.GONE);
            holder.sundayCard.setVisibility(View.GONE);
            holder.cardView.setVisibility(View.GONE);
            holder.eventnooCard.setVisibility(View.VISIBLE);
            holder.leaveCard.setVisibility(View.GONE);
        }




    }

    @Override
    public int getItemCount() {
        return mapDateForPlannings.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        TimelineView timelineView;
        CardView cardView, sundayCard,noEventCard, leaveCard;
        private CardView eventnooCard;
        TextView tvEventType, tvLocation, tvTime;
        TextView tvSEventType, tvSLocation, tvSTime, tvLeavePurpose, tvLeaveType;
        TextView tvNEventType, tvNLocation, tvNTime;
        private ImageView ivLocation;

        ViewHolder(View view) {
            super(view);
            timelineView = (TimelineView) view.findViewById(R.id.timeline);
            tvEventType = view.findViewById(R.id.eventType);
            tvTime = view.findViewById(R.id.time);
            tvLocation = view.findViewById(R.id.location);
            cardView = view.findViewById(R.id.card);

            eventnooCard = view.findViewById(R.id.eventnoo);

            tvLeaveType = view.findViewById(R.id.leaveType);

            leaveCard = view.findViewById(R.id.leave);
            tvLeavePurpose = view.findViewById(R.id.leavepurpose);
//
//            tvSEventType = view.findViewById(R.id.seventType);
//            tvSTime = view.findViewById(R.id.stime);
//            tvSLocation = view.findViewById(R.id.slocation);
            noEventCard = view.findViewById(R.id.noevent);

            tvNEventType = view.findViewById(R.id.neventType);
            tvNTime = view.findViewById(R.id.ntime);
            tvNLocation = view.findViewById(R.id.nlocation);
            sundayCard = view.findViewById(R.id.sunday);

        }
    }

    public void setOnItemClickListener(final UpdateListener mItemClickListener) {
        this.mItemCLickListener = mItemClickListener;
    }

}
