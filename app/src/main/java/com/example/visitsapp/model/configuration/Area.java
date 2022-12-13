package com.example.visitsapp.model.configuration;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Area {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("code")
    public String code;

    @SerializedName("latitude")
    public String latitude;

    @SerializedName("longitude")
    public String longitude;

    @SerializedName("branches")
    public ArrayList<Branch> branches;
}
