package com.example.visitsapp.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CreateEventFeedback {

    @SerializedName("changedPlan")
    public ArrayList<CreateChangedPlan> changedPlan;
}
