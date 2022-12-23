package com.example.visitsapp.model.responce;

import com.google.gson.annotations.SerializedName;

public class TodayPlans {

    @SerializedName("event")
    public String event;

    @SerializedName("event_purpose")
    public String event_purpose;

    @SerializedName("purpose_child")
    public String purposechild;

    @SerializedName("id")
    public int id;

    @SerializedName("planned_on")
    public String planned_on;

    @SerializedName("time")
    public String time;

    @SerializedName("area")
    public String area;

    @SerializedName("region")
    public String region;

    @SerializedName("status")
    public String status;
}
