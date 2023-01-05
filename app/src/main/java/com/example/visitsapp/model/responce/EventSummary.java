package com.example.visitsapp.model.responce;

import com.google.gson.annotations.SerializedName;

public class EventSummary {

    @SerializedName("pending_approval")
    public int pending_approval;

    @SerializedName("pending_executed")
    public int pending_executed;

    @SerializedName("visits_executed")
    public int visits_executed;

    @SerializedName("unexecuted")
    public int unexecuted;

    @SerializedName("leaves")
    public int leaves;
}
