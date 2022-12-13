package com.example.visitsapp.model.request;

import com.google.gson.annotations.SerializedName;

public class QuesAnswer {

    @SerializedName("result")
    String answer;

    @SerializedName("question_id")
    int id;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
