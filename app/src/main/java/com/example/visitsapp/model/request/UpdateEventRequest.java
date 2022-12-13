package com.example.visitsapp.model.request;

import com.google.gson.annotations.SerializedName;

public class UpdateEventRequest {

    @SerializedName("planned_on")
    private String planned_on;

    @SerializedName("id")
    private String id;

    @SerializedName("event_id")
    private String event_id;

    @SerializedName("purpose_id")
    private String purpose_id;

    @SerializedName("purpose_child_id")
    private String purpose_child_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanned_on() {
        return planned_on;
    }

    public void setPlanned_on(String planned_on) {
        this.planned_on = planned_on;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getPurpose_id() {
        return purpose_id;
    }

    public void setPurpose_id(String purpose_id) {
        this.purpose_id = purpose_id;
    }

    public String getPurpose_child_id() {
        return purpose_child_id;
    }

    public void setPurpose_child_id(String purpose_child_id) {
        this.purpose_child_id = purpose_child_id;
    }
}
