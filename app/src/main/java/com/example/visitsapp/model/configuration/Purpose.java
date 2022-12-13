package com.example.visitsapp.model.configuration;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Purpose {

    @SerializedName("purpose_id")
    public int purpose_id;

    @SerializedName("purpose_name")
    public String purpose_name;

    @SerializedName("purpose_code")
    public String purpose_code;

    @SerializedName("purpose_color_code")
    public String purpose_color_code;

    @SerializedName("feedback_questionnaire")
    public ArrayList<FeedbackQuestionnaire> feedback_questionnaire;
}
