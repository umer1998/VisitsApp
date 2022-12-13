package com.example.visitsapp.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FeedbackReplace {
    @SerializedName("questionaire")
    public ArrayList<QuesAnswer> questionaire;
}
