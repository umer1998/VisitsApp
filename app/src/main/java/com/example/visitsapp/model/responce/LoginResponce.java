package com.example.visitsapp.model.responce;

import com.google.gson.annotations.SerializedName;

public class LoginResponce {
    @SerializedName("fullname")
    public String fullname;

    @SerializedName("designation")
    public String designation;

    @SerializedName("token")
    public String token;
}
