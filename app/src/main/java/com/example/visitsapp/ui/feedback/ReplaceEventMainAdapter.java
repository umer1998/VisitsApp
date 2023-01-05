package com.example.visitsapp.ui.feedback;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.Addmember;
import com.example.visitsapp.model.configuration.FeedbackQuestionnaire;
import com.example.visitsapp.model.request.Multiinput;
import com.example.visitsapp.model.request.Question;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.utils.ArrayListListener;
import com.example.visitsapp.utils.OnMapListener;
import com.example.visitsapp.utils.OnRadioButtonClickListener;
import com.example.visitsapp.utils.alert.AlertUtils;

import java.util.ArrayList;
import java.util.HashMap;


public class ReplaceEventMainAdapter extends RecyclerView.Adapter<ReplaceEventMainAdapter.ViewHolder> {

    private MainActivity context;
    private LinearLayout llSubmit;
    private OnMapListener onRadioButtonClickListener;
    private ArrayList<FeedbackQuestionnaire> feedbackQuestionnaires;
    HashMap<Integer, String> answersmap = new HashMap<Integer, String>();
    Multiinput multiinput = new Multiinput();

    public ReplaceEventMainAdapter(MainActivity context, ArrayList<FeedbackQuestionnaire> feedbackQuestionnaires) {
        this.context = context;
        this.feedbackQuestionnaires = feedbackQuestionnaires;

    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.main_adapter_layout;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(position == feedbackQuestionnaires.size()){
            holder.llSubmit.setVisibility(View.VISIBLE);
            holder.submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(answersmap.size() != feedbackQuestionnaires.size()-1){
                        AlertUtils.showAlert(context, "Please answer all questions!");
                    } else {

                        onRadioButtonClickListener.onMapListener(answersmap, multiinput);
                    }
//
                }
            });

        } else {
            if(feedbackQuestionnaires.get(position).type.equalsIgnoreCase("radiobutton")){

                Radio_Adapter todaysPlanAdapter = new Radio_Adapter(context, feedbackQuestionnaires.get(position));
                holder.recyclerView.setHasFixedSize(true);
                holder.recyclerView.setAdapter(todaysPlanAdapter);
                todaysPlanAdapter.setOnRadioButtonClickListener(new OnRadioButtonClickListener() {
                    @Override
                    public void OnRadioItemClickListener(int id, String value) {
                        answersmap.put(id, value);
                    }
                });

            } else if(feedbackQuestionnaires.get(position).type.equalsIgnoreCase("inputfield" )){


                    EditTextAdapter editPlanAdapter = new EditTextAdapter(context, feedbackQuestionnaires.get(position));
                    holder.recyclerView.setAdapter(editPlanAdapter);
                    holder.recyclerView.setHasFixedSize(true);
                    editPlanAdapter.setOnRadioButtonClickListener(new OnRadioButtonClickListener() {
                        @Override
                        public void OnRadioItemClickListener(int id, String value) {
                            answersmap.put(id, value);
                        }
                    });



            } else if(feedbackQuestionnaires.get(position).type.equalsIgnoreCase("multiinput")){
                MemberAdapter todaysPlanAdapter = new MemberAdapter(context, feedbackQuestionnaires.get(position).id);
                holder.recyclerView.setAdapter(todaysPlanAdapter);

                todaysPlanAdapter.setOnItemClickListener(new ArrayListListener() {
                    @Override
                    public void onItemClick(int id, ArrayList<Question> list) {
                        multiinput.id = id;
                        multiinput.questions = list;
                    }
                });
            }
            else {

            }
        }



//        if(position%3 ==1){
//
//
//
//        } else if(position == 9){
//            MemberAdapter todaysPlanAdapter = new MemberAdapter(context);
//            holder.recyclerView.setAdapter(todaysPlanAdapter);
//        }
//        else {
//            EditTextAdapter editPlanAdapter = new EditTextAdapter(context);
//            holder.recyclerView.setAdapter(editPlanAdapter);
//        }


    }

    @Override
    public int getItemCount() {
        return feedbackQuestionnaires.size()+1;
    }



    class ViewHolder extends RecyclerView.ViewHolder {



        private RecyclerView recyclerView;
        private LinearLayout llSubmit;
        private RelativeLayout submit;


        ViewHolder(View view) {
            super(view);

            llSubmit = view.findViewById(R.id.llsubmit);
            submit = view.findViewById(R.id.submit);
            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
            recyclerView.setItemViewCacheSize(1);
        }
    }

    public void setOnRadioButtonClickListener(final OnMapListener mItemClickListener) {
        this.onRadioButtonClickListener = mItemClickListener;
    }


}

