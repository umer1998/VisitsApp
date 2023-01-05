package com.example.visitsapp.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Multiinput {
    @SerializedName("id")
    public int id;

    @SerializedName("questions")
    public ArrayList<Question> questions;
}
