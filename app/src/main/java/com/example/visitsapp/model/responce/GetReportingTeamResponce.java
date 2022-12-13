package com.example.visitsapp.model.responce;

import com.google.gson.annotations.SerializedName;

public class GetReportingTeamResponce {


    @SerializedName("user_id")
    public int user_id;

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

}
