package com.example.visitsapp.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChangedPlan {
    @SerializedName("new_event")
    public CreatePlanRequest new_event;

    @SerializedName("plannerEventId")
    public int plannerEventId;

    @SerializedName("feedbacks")
    public ArrayList<FeedbackReplace> feedbacks;
}
