package com.example.visitsapp.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CreateChangedPlan {
    @SerializedName("new_event")
    public CreatePlanRequest new_event;



    @SerializedName("feedbacks")
    public ArrayList<FeedbackReplace> feedbacks;
}
