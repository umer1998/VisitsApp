package com.example.visitsapp.ui.feedback;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.configuration.FeedbackQuestionnaire;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.utils.OnRadioButtonClickListener;


public class TextAreaAdapter extends RecyclerView.Adapter<TextAreaAdapter.ViewHolder> {

    private MainActivity context;
    private OnRadioButtonClickListener onRadioButtonClickListener;
    private FeedbackQuestionnaire feedbackQuestionnaire;

    public TextAreaAdapter(MainActivity context, FeedbackQuestionnaire feedbackQuestionnaire) {
        this.context = context;
        this.feedbackQuestionnaire = feedbackQuestionnaire;

    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.textarea_adapter_layout;

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
        holder.edAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                onRadioButtonClickListener.OnRadioItemClickListener(feedbackQuestionnaire.id, editable.toString());

            }
        });
//        holder.edAnswer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if(!b){
//                    onRadioButtonClickListener.OnRadioItemClickListener(feedbackQuestionnaire.id, holder.edAnswer.getText().toString());
//                }
//            }
//        });
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


    public void setOnRadioButtonClickListener(final OnRadioButtonClickListener mItemClickListener) {
        this.onRadioButtonClickListener = mItemClickListener;
    }


}

