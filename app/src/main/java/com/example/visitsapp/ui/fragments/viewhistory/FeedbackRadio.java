package com.example.visitsapp.ui.fragments.viewhistory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.configuration.FeedbackQuestionnaire;
import com.example.visitsapp.model.responce.Other;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.utils.OnRadioButtonClickListener;



public class FeedbackRadio extends RecyclerView.Adapter<FeedbackRadio.ViewHolder> {

    private MainActivity context;
    private Other other;

    public FeedbackRadio(MainActivity context, Other other) {
        this.context = context;
        this.other = other;

    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.feedback_adapter_layout;


    }

    @Override
    public FeedbackRadio.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new FeedbackRadio.ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(FeedbackRadio.ViewHolder holder, int position) {


        holder.tvQuestion.setText(other.question);
        holder.tvYes.setText(other.result);
        holder.rlmain.setBackground(context.getDrawable(R.drawable.radio_green_round_bg));

//        if(1 ==1){
            holder.tvYes.setTextColor(Color.parseColor("#000000"));
//        } else {
//
//            holder.rlmain.setBackgroundColor(Color.parseColor("#96c949"));
//            holder.tvYes.setTextColor(Color.parseColor("#000000"));
//
//        }
//        holder.tvNo.setText(feedbackQuestionnaire.mcqs.get(1));
//
//        holder.rlyes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                View vieww = context.getCurrentFocus();
//                if (view != null) {
//                    InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                }
//
////                holder.rlNo.setBackgroundColor(Color.parseColor("#FFFFFF"));
////                holder.rlyes.setBackgroundColor(Color.parseColor("#96c949"));
//                holder.ivYEs.setImageResource(R.drawable.select);
//                holder.ivNo.setImageResource(R.drawable.unselect);
//
//                holder.tvYes.setTextColor(Color.parseColor("#FFFFFF"));
//                holder.tvNo.setTextColor(Color.parseColor("#000000"));
//                holder.rlyes.setBackground(context.getDrawable(R.drawable.radio_yes));
//                holder.rlNo.setBackground(context.getDrawable(R.drawable.radio_unselect_no));
//
//                onRadioButtonClickListener.OnRadioItemClickListener(feedbackQuestionnaire.id, feedbackQuestionnaire.mcqs.get(0));
//            }
//        });
//
//
//        holder.rlNo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//// View vieww = context.getCurrentFocus();
////                if (view != null) {
////                    InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
////                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
////                }
////                holder.rlNo.setBackgroundColor(Color.parseColor("#ef5350"));
////                holder.rlyes.setBackgroundColor(Color.parseColor("#FFFFFF"));
//
//                holder.ivYEs.setImageResource(R.drawable.unselect);
//                holder.ivNo.setImageResource(R.drawable.select);
//                holder.tvYes.setTextColor(Color.parseColor("#000000"));
//                holder.tvNo.setTextColor(Color.parseColor("#FFFFFF"));
//                holder.rlyes.setBackground(context.getDrawable(R.drawable.radio_unselect_yes));
//                holder.rlNo.setBackground(context.getDrawable(R.drawable.radio_no));
//                onRadioButtonClickListener.OnRadioItemClickListener(feedbackQuestionnaire.id, feedbackQuestionnaire.mcqs.get(1));
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlmain;
        TextView  tvYes, tvQuestion;

        ViewHolder(View view) {
            super(view);

            tvYes = view.findViewById(R.id.tvyes);
            tvQuestion = view.findViewById(R.id.question);
            rlmain = view.findViewById(R.id.rlmain);

        }
    }

}
