package com.example.visitsapp.model.responce;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MultiinputResponce {

    @SerializedName("question")
    public String question;

    @SerializedName("type")
    public String type;

    @SerializedName("result")
    public ArrayList<ResultResponce> result;
}
