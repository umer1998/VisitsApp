package com.example.visitsapp.model.configuration;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ConfigurationResponse {

    @SerializedName("user_image")
    public String user_image;

    @SerializedName("events")
    public ArrayList<Events> events;

    @SerializedName("meeting_places")
    public ArrayList<MeetingPlace> meeting_places;

    @SerializedName("network")
    public ArrayList<Networks> network;
}
