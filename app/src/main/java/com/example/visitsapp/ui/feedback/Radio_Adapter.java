package com.example.visitsapp.ui.feedback;

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
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.utils.OnRadioButtonClickListener;


public class Radio_Adapter extends RecyclerView.Adapter<Radio_Adapter.ViewHolder> {

    private MainActivity context;

    private OnRadioButtonClickListener onRadioButtonClickListener;

    private FeedbackQuestionnaire feedbackQuestionnaire;

    public Radio_Adapter(MainActivity context, FeedbackQuestionnaire feedbackQuestionnaire) {
        this.context = context;
        this.feedbackQuestionnaire = feedbackQuestionnaire;

    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.adapter_layout;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        holder.tvQuestion.setText(feedbackQuestionnaire.question);
        holder.tvYes.setText(feedbackQuestionnaire.mcqs.get(0));
        holder.tvNo.setText(feedbackQuestionnaire.mcqs.get(1));

        holder.rlyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View vieww = context.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

//                holder.rlNo.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                holder.rlyes.setBackgroundColor(Color.parseColor("#96c949"));
                holder.ivYEs.setImageResource(R.drawable.select);
                holder.ivNo.setImageResource(R.drawable.unselect);

                holder.tvYes.setTextColor(Color.parseColor("#FFFFFF"));
                holder.tvNo.setTextColor(Color.parseColor("#000000"));
                holder.rlyes.setBackground(context.getDrawable(R.drawable.radio_yes));
                holder.rlNo.setBackground(context.getDrawable(R.drawable.radio_unselect_no));

                onRadioButtonClickListener.OnRadioItemClickListener(feedbackQuestionnaire.id, feedbackQuestionnaire.mcqs.get(0));
            }
        });


        holder.rlNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// View vieww = context.getCurrentFocus();
//                if (view != null) {
//                    InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                }
//                holder.rlNo.setBackgroundColor(Color.parseColor("#ef5350"));
//                holder.rlyes.setBackgroundColor(Color.parseColor("#FFFFFF"));

                holder.ivYEs.setImageResource(R.drawable.unselect);
                holder.ivNo.setImageResource(R.drawable.select);
                holder.tvYes.setTextColor(Color.parseColor("#000000"));
                holder.tvNo.setTextColor(Color.parseColor("#FFFFFF"));
                holder.rlyes.setBackground(context.getDrawable(R.drawable.radio_unselect_yes));
                holder.rlNo.setBackground(context.getDrawable(R.drawable.radio_no));
                onRadioButtonClickListener.OnRadioItemClickListener(feedbackQuestionnaire.id, feedbackQuestionnaire.mcqs.get(1));
            }
        });

    }

    @Override
    public int getItemCount() {
        return 1;
    }



    class ViewHolder extends RecyclerView.ViewHolder {



        RelativeLayout rlyes, rlNo;
        ImageView ivYEs,ivNo;
        RelativeLayout rlmain;

        TextView tvNo, tvYes, tvQuestion;


        ViewHolder(View view) {
            super(view);



            tvNo = view.findViewById(R.id.tvno);
            tvYes = view.findViewById(R.id.tvyes);

            tvQuestion = view.findViewById(R.id.question);

            rlyes = view.findViewById(R.id.rlyes);
            rlNo = view.findViewById(R.id.rlno);

            rlmain = view.findViewById(R.id.rlmain);

            ivNo = view.findViewById(R.id.no);
            ivYEs = view.findViewById(R.id.yes);

        }
    }

    public void setOnRadioButtonClickListener(final OnRadioButtonClickListener mItemClickListener) {
        this.onRadioButtonClickListener = mItemClickListener;
    }

}
