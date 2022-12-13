package com.example.visitsapp.model.responce;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DashboardResponce {

        @SerializedName("event_summary")
        public EventSummary event_summary;

        @SerializedName("plans")
        public ArrayList<TodayPlans> plans;

}
