package com.example.visitsapp.ui.fragments.viewhistory;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.responce.FeedbackResponce;
import com.example.visitsapp.model.responce.MultiinputResponce;
import com.example.visitsapp.model.responce.Other;
import com.example.visitsapp.model.responce.PlansData;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.utils.OnItemClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class MianAdapterClass extends RecyclerView.Adapter<MianAdapterClass.ViewHolder> {

    private MainActivity context;
    private ArrayList<Other> feedbackResponce;
    private OnItemClickListener mItemClickListener;
    private ArrayList<MultiinputResponce> multiinputResponces;

    public MianAdapterClass(MainActivity context, ArrayList<Other> feedbackResponce, ArrayList<MultiinputResponce> multiinputResponces) {
        this.context = context;
        this.feedbackResponce = feedbackResponce;
        this.multiinputResponces = multiinputResponces;
    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.main_adapter;

    }

    @Override
    public MianAdapterClass.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new MianAdapterClass.ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MianAdapterClass.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if(position < feedbackResponce.size()){
            if( feedbackResponce.get(position).type.equalsIgnoreCase("inputfield")){

                FeedbackEditText feedbackEditText = new FeedbackEditText(context, feedbackResponce.get(position));
                holder.recyclerView.setAdapter(feedbackEditText);


            } else if(feedbackResponce.get(position).type.equalsIgnoreCase("radiobutton")){

                FeedbackRadio feedbackRadio = new FeedbackRadio(context, feedbackResponce.get(position));
                holder.recyclerView.setAdapter(feedbackRadio);

            } else if(feedbackResponce.get(position).type.equalsIgnoreCase("textarea")){

                FeedbackTextArea feedbackTextArea = new FeedbackTextArea(context, feedbackResponce.get(position));
                holder.recyclerView.setAdapter(feedbackTextArea);

            }
        } else {
            if(multiinputResponces.size()> 0){
                MemberDetailFrag memberDetailFrag = new MemberDetailFrag(context, multiinputResponces);
                holder.recyclerView.setAdapter(memberDetailFrag);
            }
        }


    }

    @Override
    public int getItemCount() {
        if(multiinputResponces.size()> 0){
            return feedbackResponce.size()+1;
        } else {
            return feedbackResponce.size();
        }

    }



    class ViewHolder extends RecyclerView.ViewHolder {

       private RecyclerView recyclerView;
        ViewHolder(View view) {
            super(view);

            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        }
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


}
