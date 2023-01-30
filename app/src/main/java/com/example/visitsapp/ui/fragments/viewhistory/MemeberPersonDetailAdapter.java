package com.example.visitsapp.ui.fragments.viewhistory;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.request.Question;
import com.example.visitsapp.model.responce.MultiinputResponce;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.feedback.OnItemClickListener;

import java.util.ArrayList;


public class MemeberPersonDetailAdapter extends RecyclerView.Adapter<MemeberPersonDetailAdapter.ViewHolder> {

    private MainActivity context;

    private OnItemClickListener mItemClickListener;
    private MultiinputResponce multiinputResponce;

    public MemeberPersonDetailAdapter(MainActivity context, MultiinputResponce multiinputResponce) {
        this.context = context;
        this.multiinputResponce = multiinputResponce;
    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.add_member_layout;

    }

    @Override
    public MemeberPersonDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new MemeberPersonDetailAdapter.ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MemeberPersonDetailAdapter.ViewHolder holder, int position) {

        holder.name.setText(multiinputResponce.result.get(position).name);
        holder.desg.setText(multiinputResponce.result.get(position).designation);
    }

    @Override
    public int getItemCount() {
        return multiinputResponce.result.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {


        private TextView name, desg;
        ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name);
            desg = view.findViewById(R.id.desg);

        }
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


}

