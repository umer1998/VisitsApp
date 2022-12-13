package com.example.visitsapp.model.configuration;

import com.example.visitsapp.model.configuration.Area;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Networks {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("code")
    public String code;

    @SerializedName("latitude")
    public Object latitude;

    @SerializedName("longitude")
    public Object longitude;

    @SerializedName("areas")
    public ArrayList<Area> areas;
}
