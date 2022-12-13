package com.example.visitsapp.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReplaceEventRequest {

    @SerializedName("changedPlan")
    public ArrayList<ChangedPlan> changedPlan;

}
