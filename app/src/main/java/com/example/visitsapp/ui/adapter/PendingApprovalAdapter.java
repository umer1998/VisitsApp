package com.example.visitsapp.ui.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.responce.GetPendingApproval;
import com.example.visitsapp.model.responce.GetReportingTeamResponce;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.utils.OnApproveListClick;
import com.example.visitsapp.utils.OnItemClickListener;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class PendingApprovalAdapter extends RecyclerView.Adapter<PendingApprovalAdapter.ViewHolder> {

    private MainActivity context;
    private OnApproveListClick mItemClickListener;
    private ArrayList<GetPendingApproval> body = new ArrayList<GetPendingApproval>();
    private boolean isSelectAll;
    ArrayList<Integer> list = new ArrayList<>();


    public PendingApprovalAdapter(MainActivity context, ArrayList<GetPendingApproval> body, ArrayList<Integer> list, boolean isSelectAll) {
        this.context = context;
        this.body = body;
        this.isSelectAll = isSelectAll;

       list.removeAll(list);

    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.pending_approval_listing_card;

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


        if(isSelectAll){
            list.add(body.get(holder.getAdapterPosition()).id);
            holder.cardView.setBackgroundColor(Color.parseColor("#3EAA92"));

            mItemClickListener.onItemClick(null, list);


        } else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(list.contains(body.get(holder.getAdapterPosition()).id)){
                        list.remove(list.indexOf(body.get(holder.getAdapterPosition()).id));
                        holder.cardView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    } else {
                        list.add(body.get(holder.getAdapterPosition()).id);
                        holder.cardView.setBackgroundColor(Color.parseColor("#3EAA92"));
                    }
                    mItemClickListener.onItemClick(view, list);
                }
            });


        }
        try {

            holder.tvTime.setText(new SimpleDateFormat("HH:mm a").format(new SimpleDateFormat("hh:mm:ss").parse(body.get(position).time)));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.tvEvent.setText(body.get(position).event);
        holder.tvPurpose.setText(body.get(position).event_purpose);

        holder.tvPurposeChild.setText(body.get(position).purpose_child);

        try {
            holder.tvDate.setText(new SimpleDateFormat("dd MMMM yyy").format(
                    new SimpleDateFormat("dd-MM-yyyy").parse(body.get(position).planned_on)));
        } catch (ParseException e) {
            e.printStackTrace();
        }



    }

    @Override
    public int getItemCount() {
        return body.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {

       private TextView tvTime, tvPurpose, tvEvent, tvPurposeChild, tvDate;
       private RelativeLayout cardView;
       private LinearLayout llLayout;

        ViewHolder(View view) {
            super(view);

            tvEvent = view.findViewById(R.id.eventType);
            tvPurpose = view.findViewById(R.id.eventpurpose);
            tvTime = view.findViewById(R.id.time);

            llLayout = view.findViewById(R.id.lllocation);
            tvDate = view.findViewById(R.id.date);
            tvPurposeChild = view.findViewById(R.id.eventPurpose);

            cardView = view.findViewById(R.id.card);


        }
    }

    public void setOnItemClickListener(final OnApproveListClick mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}
