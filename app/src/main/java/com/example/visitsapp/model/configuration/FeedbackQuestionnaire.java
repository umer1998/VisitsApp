package com.example.visitsapp.model.configuration;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FeedbackQuestionnaire {

    @SerializedName("id")
    public int id;

    @SerializedName("type")
    public String type;

    @SerializedName("question")
    public String question;

    @SerializedName("mcqs")
    public ArrayList<String> mcqs;

    public int status = 0;
}
