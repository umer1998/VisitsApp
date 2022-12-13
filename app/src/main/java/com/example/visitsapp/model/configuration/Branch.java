package com.example.visitsapp.model.configuration;

import com.google.gson.annotations.SerializedName;

public class Branch {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("code")
    public String code;

    @SerializedName("latitude")
    public double latitude;

    @SerializedName("longitude")
    public double longitude;
}
