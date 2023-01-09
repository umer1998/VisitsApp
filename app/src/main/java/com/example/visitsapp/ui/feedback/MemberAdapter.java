package com.example.visitsapp.ui.feedback;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.Addmember;
import com.example.visitsapp.model.configuration.FeedbackQuestionnaire;
import com.example.visitsapp.model.request.Question;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.utils.ArrayListListener;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private MainActivity context;
    private ArrayList<Question> arrayList = new ArrayList<>();
    AddmemberAdapter todaysPlanAdapter;
    private ArrayListListener mItemClickListener;

    private FeedbackQuestionnaire feedbackQuestionnaire;

    public MemberAdapter(MainActivity context, FeedbackQuestionnaire feedbackQuestionnaire) {
        this.context = context;
        this.feedbackQuestionnaire = feedbackQuestionnaire;

    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.fragment_blank;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tvQues.setText(feedbackQuestionnaire.question);
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View vieww = context.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                AddMemeberDialgoue dialog = new AddMemeberDialgoue(context );
                dialog.show(context.getSupportFragmentManager(), "MyDialogFragment");

                dialog.setOnItemClickListener(new OnAddMemberClick() {
                    @Override
                    public void onItemClick(String name, String desg) {
                        Question addmember = new Question();
                        addmember.designation= desg;
                        addmember.name = name;
                        arrayList.add(addmember);
                        todaysPlanAdapter =null;
                        setAdapter(holder.recyclerView ,arrayList);
                        mItemClickListener.onItemClick(feedbackQuestionnaire.id,arrayList);
                    }
                });
            }
        });
    }

    private void setAdapter(RecyclerView recyclerView, ArrayList<Question> arrayList) {
        todaysPlanAdapter = new AddmemberAdapter(context, arrayList);
        recyclerView.setAdapter(todaysPlanAdapter);
    }

    @Override
    public int getItemCount() {
        return 1;
    }



    class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView ivAdd;
        private TextView tvQues;
        private RecyclerView recyclerView;


        ViewHolder(View view) {
            super(view);
            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

            tvQues = view.findViewById(R.id.ques);
            ivAdd = view.findViewById(R.id.plus);


        }
    }

    public void setOnItemClickListener(final ArrayListListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


}

