package com.example.visitsapp.model.responce;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EventIds {

    @SerializedName("id")
    int ids;

    public int getIds() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }
}
