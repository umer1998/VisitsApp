package com.example.visitsapp.model.responce;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApproveEventRequest {

    @SerializedName("event_status")
    public String event_status;

    @SerializedName("ids")
    public ArrayList<EventIds> ids;
}
