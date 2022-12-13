package com.example.visitsapp.model.responce;

import com.google.gson.annotations.SerializedName;

public class EventSummary {

    @SerializedName("visit_planned")
    public int visit_planned;

    @SerializedName("visits_executed")
    public int visits_executed;

    @SerializedName("visit_pending")
    public int visit_pending;

    @SerializedName("leaves")
    public int leaves;
}
