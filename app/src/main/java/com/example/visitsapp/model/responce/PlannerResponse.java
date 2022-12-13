package com.example.visitsapp.model.responce;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlannerResponse {

    @SerializedName("data")
    public ArrayList<PlansData> data;
}
