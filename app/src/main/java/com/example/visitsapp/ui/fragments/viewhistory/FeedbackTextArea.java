package com.example.visitsapp.ui.fragments.viewhistory;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.responce.Other;
import com.example.visitsapp.ui.MainActivity;

public class FeedbackTextArea extends RecyclerView.Adapter<FeedbackTextArea.ViewHolder> {

    private MainActivity context;
    private Other other;

    public FeedbackTextArea(MainActivity context, Other other) {
        this.context = context;
        this.other = other;

    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.feedback_textarea_adapter_layout;

    }

    @Override
    public FeedbackTextArea.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new FeedbackTextArea.ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(FeedbackTextArea.ViewHolder holder, int position) {

        holder.tvQuestion.setText(other.question);
        holder.edAnswer.setText(other.result);
    }

    @Override
    public int getItemCount() {
        return 1;
    }



    class ViewHolder extends RecyclerView.ViewHolder {


        private TextView tvQuestion;
        private EditText edAnswer;

        ViewHolder(View view) {
            super(view);

            tvQuestion = view.findViewById(R.id.question);
            edAnswer = view.findViewById(R.id.answer);

        }
    }




}

