package com.example.visitsapp.ui.feedback;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.Addmember;
import com.example.visitsapp.model.request.Question;
import com.example.visitsapp.ui.MainActivity;

import java.util.ArrayList;


public class AddmemberAdapter extends RecyclerView.Adapter<AddmemberAdapter.ViewHolder> {

    private MainActivity context;

    private OnItemClickListener mItemClickListener;
    private ArrayList<Question> arrayList;

    public AddmemberAdapter(MainActivity context, ArrayList<Question> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.add_member_layout;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.name.setText(arrayList.get(position).name);
        holder.desg.setText(arrayList.get(position).designation +",  ");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
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

