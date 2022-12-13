package com.example.visitsapp.model.responce;

import com.google.gson.annotations.SerializedName;

public class PlansData {

    @SerializedName("id")
    public int id;

    @SerializedName("event")
    public String event;

    @SerializedName("area")
    public String area;

    @SerializedName("region")
    public String region;

    @SerializedName("event_purpose")
    public String event_purpose;

    @SerializedName("purpose_child")
    public String purpose_child;

    @SerializedName("planned_on")
    public String planned_on;

    @SerializedName("status")
    public String status;

    @SerializedName("time")
    public String time;
}
