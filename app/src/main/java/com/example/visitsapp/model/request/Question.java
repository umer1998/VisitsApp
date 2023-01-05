package com.example.visitsapp.model.request;

import com.google.gson.annotations.SerializedName;

public class Question {
    @SerializedName("name")
    public String name;

    @SerializedName("designation")
    public String designation;
}
