package com.example.visitsapp.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.configuration.FeedbackQuestionnaire;
import com.example.visitsapp.model.request.Question;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.feedback.EditTextAdapter;
import com.example.visitsapp.ui.feedback.MemberAdapter;
import com.example.visitsapp.ui.feedback.Radio_Adapter;
import com.example.visitsapp.ui.feedback.TextAreaAdapter;
import com.example.visitsapp.utils.ArrayListListener;
import com.example.visitsapp.utils.OnItemClickListener;
import com.example.visitsapp.utils.OnRadioButtonClickListener;

import java.util.ArrayList;


public class HistoryFeedbackAdapter extends RecyclerView.Adapter<HistoryFeedbackAdapter.ViewHolder> {

    private MainActivity context;
    private OnItemClickListener mItemClickListener;
    private ArrayList<FeedbackQuestionnaire> feedbackQuestionnaires;


    public HistoryFeedbackAdapter(MainActivity context, ArrayList<FeedbackQuestionnaire> feedbackQuestionnaire) {
        this.context = context;
        this.feedbackQuestionnaires = feedbackQuestionnaire;

    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.history_feedback_adapter_layout;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(feedbackQuestionnaires.get(position).type.equalsIgnoreCase("radiobutton")){


        } else if(feedbackQuestionnaires.get(position).type.equalsIgnoreCase("textarea" )){



        } else if(feedbackQuestionnaires.get(position).type.equalsIgnoreCase("inputfield" )){



        } else if(feedbackQuestionnaires.get(position).type.equalsIgnoreCase("multiinput")){

        }
        else {

        }

    }

    @Override
    public int getItemCount() {
        return 10;
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerView;
        ViewHolder(View view) {
            super(view);

            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
            recyclerView.setItemViewCacheSize(1);

        }
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


}

