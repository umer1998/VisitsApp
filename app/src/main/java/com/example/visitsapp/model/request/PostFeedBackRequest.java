package com.example.visitsapp.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PostFeedBackRequest {

    @SerializedName("feedbacks")
    public ArrayList<Feedback> feedbacks;
}
