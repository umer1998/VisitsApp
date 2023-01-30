package com.example.visitsapp.ui.fragments.viewhistory;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.configuration.FeedbackQuestionnaire;
import com.example.visitsapp.model.responce.MultiinputResponce;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.feedback.AddmemberAdapter;

import java.util.ArrayList;


public class MemberDetailFrag extends RecyclerView.Adapter<MemberDetailFrag.ViewHolder> {

    private MainActivity context;
    private ArrayList<MultiinputResponce> feedbackQuestionnaire;
    MemeberPersonDetailAdapter todaysPlanAdapter;

    public MemberDetailFrag(MainActivity context, ArrayList<MultiinputResponce> feedbackQuestionnaire) {
        this.context = context;
        this.feedbackQuestionnaire = feedbackQuestionnaire;

    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.member_detail_adapter_layout;

    }

    @Override
    public MemberDetailFrag.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new MemberDetailFrag.ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MemberDetailFrag.ViewHolder holder, int position) {
        todaysPlanAdapter = new MemeberPersonDetailAdapter(context, feedbackQuestionnaire.get(position));
        holder.recyclerView.setAdapter(todaysPlanAdapter);

    }

    @Override
    public int getItemCount() {
        return 1;
    }



    class ViewHolder extends RecyclerView.ViewHolder {


        private TextView tvQuestion;
        private RecyclerView recyclerView;

        ViewHolder(View view) {
            super(view);

            tvQuestion = view.findViewById(R.id.question);
            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));



        }
    }




}

