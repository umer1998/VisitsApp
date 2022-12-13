package com.example.visitsapp.ui.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.configuration.FeedbackQuestionnaire;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.utils.OnRadioButtonClickListener;

import java.util.ArrayList;

public class QuestionaireAdapter extends RecyclerView.Adapter<QuestionaireAdapter.ViewHolder> {


    private MainActivity context;
    private ArrayList<FeedbackQuestionnaire> feedbackQuestionnaires = new ArrayList<>();
    private OnRadioButtonClickListener onRadioButtonClickListener;


    public QuestionaireAdapter(MainActivity context, ArrayList<FeedbackQuestionnaire> feedbackQuestionnaires) {
        this.context = context;
        this.feedbackQuestionnaires = feedbackQuestionnaires;

    }



    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements OnRadioButtonClickListener {

        private LinearLayout linearLayout, mainLayout;

        public ViewHolder(View v) {
            super(v);

            linearLayout = v.findViewById(R.id.llLayout);
        }


        @Override
        public void OnRadioItemClickListener(int id, String value) {
            onRadioButtonClickListener.OnRadioItemClickListener(id, value);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.questionaire_adapter, parent, false);

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        holder.linearLayout.removeAllViews();
        RadioGroup radioGroup = new RadioGroup(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(20, 20, 40, 8);
        radioGroup.setLayoutParams(params);
        for (int i = 0; i < feedbackQuestionnaires.get(position).mcqs.size(); i++) {

            RadioButton radioButton1 = new RadioButton(context);
            RelativeLayout.LayoutParams parameters = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            parameters.setMargins(30, 15, 40, 8);
            parameters.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            radioButton1.setLayoutParams(parameters);
            radioButton1.setText(feedbackQuestionnaires.get(position).mcqs.get(i));
            radioButton1.setId(i);
            radioButton1.setTextSize(20f);
            radioGroup.addView(radioButton1);
        }

        TextView tvtext = new TextView(context);
        tvtext.setText(String.valueOf(position) + ". " + feedbackQuestionnaires.get(position).question);
        tvtext.setTextSize(20f);
        LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        par.setMargins(40, 20, 40, 8);
        tvtext.setLayoutParams(par);
        tvtext.setTypeface(tvtext.getTypeface(), Typeface.BOLD);
        tvtext.setTextColor(Color.parseColor("#000000"));
        holder.linearLayout.addView(tvtext);

        holder.linearLayout.addView(radioGroup);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = radioGroup.findViewById(i);
                onRadioButtonClickListener.OnRadioItemClickListener(feedbackQuestionnaires.get(position).id, radioButton.getText().toString());

            }
        });



    }




    @Override
    public int getItemCount() {
        return feedbackQuestionnaires.size();
    }

    public void setOnRadioButtonClickListener(final OnRadioButtonClickListener mItemClickListener) {
        this.onRadioButtonClickListener = mItemClickListener;
    }

}

