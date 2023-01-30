package com.example.visitsapp.model.responce;

import com.example.visitsapp.model.request.Multiinput;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FeedbackResponce {

    @SerializedName("multiinput")
    public ArrayList<MultiinputResponce> multiinputResponces;

    @SerializedName("other")
    public ArrayList<Other> other;
}
