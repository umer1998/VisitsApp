package com.example.visitsapp.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Feedback {

    @SerializedName("planner_event_id")
    public int planner_event_id;

    @SerializedName("questionaire")
    public ArrayList<QuesAnswer> questionaire;
}
