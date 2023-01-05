package com.example.visitsapp.utils;

import com.example.visitsapp.model.request.Multiinput;
import com.example.visitsapp.model.request.Question;

import java.util.ArrayList;
import java.util.HashMap;

public interface OnMapListener {
    public void onMapListener(HashMap<Integer, String> hashMap, Multiinput multiinput);
}
