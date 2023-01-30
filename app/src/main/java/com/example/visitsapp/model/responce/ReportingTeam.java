package com.example.visitsapp.model.responce;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReportingTeam {

    @SerializedName("id")
    public int id;

    @SerializedName("username")
    public String username;

    @SerializedName("fullname")
    public String fullname;

    @SerializedName("emp_code")
    public String emp_code;

    @SerializedName("cnic")
    public String cnic;

    @SerializedName("designation")
    public String designation;

    @SerializedName("place")
    public String place;

    @SerializedName("events")
    public ArrayList<PlansData> events;
}
