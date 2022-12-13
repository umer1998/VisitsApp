package com.example.visitsapp.model.configuration;

import com.google.gson.annotations.SerializedName;

public class MeetingPlace {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("code")
    public String code;
}
