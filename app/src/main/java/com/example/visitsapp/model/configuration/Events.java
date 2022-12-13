package com.example.visitsapp.model.configuration;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Events {

    @SerializedName("event_id")
    public int event_id;

    @SerializedName("event_name_label")
    public String event_name_label;

    @SerializedName("event_name_code")
    public String event_name_code;

    @SerializedName("event_color_code")
    public String event_color_code;

    @SerializedName("purposes")
    public ArrayList<Purpose> purposes;
}
