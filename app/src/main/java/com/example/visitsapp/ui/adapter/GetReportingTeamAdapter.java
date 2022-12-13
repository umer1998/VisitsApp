package com.example.visitsapp.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.responce.GetReportingTeamResponce;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.utils.OnItemClickListener;

import java.util.ArrayList;


public class GetReportingTeamAdapter extends RecyclerView.Adapter<GetReportingTeamAdapter.ViewHolder> {

    private MainActivity context;
    private OnItemClickListener mItemClickListener;
    private ArrayList<GetReportingTeamResponce> body = new ArrayList<GetReportingTeamResponce>();


    public GetReportingTeamAdapter(MainActivity context, ArrayList<GetReportingTeamResponce> body) {
        this.context = context;
        this.body = body;

    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.reporting_team_card;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.rlSeeplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(view, holder.getAdapterPosition());
            }
        });
        holder.tvEventType.setText(body.get(position).fullname);
        holder.tvLocation.setText(body.get(position).designation);


    }

    @Override
    public int getItemCount() {
        return body.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder  {


        private RelativeLayout rlSeeplan;
        private TextView tvLocation, tvEventType;


        ViewHolder(View view) {
            super(view);

            rlSeeplan = view.findViewById(R.id.seeplan);
            tvEventType = view.findViewById(R.id.eventType);
            tvLocation = view.findViewById(R.id.location);


        }


    }
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {

        this.mItemClickListener = mItemClickListener;

    }

}

