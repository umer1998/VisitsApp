package com.example.visitsapp.model.responce;

import com.google.gson.annotations.SerializedName;

public class TodayPlans {

    @SerializedName("event")
    public String event;

    @SerializedName("event_purpose")
    public String event_purpose;

    @SerializedName("plan")
    public String plan;

    @SerializedName("description")
    public String description;

    @SerializedName("planned_on")
    public String planned_on;
}
