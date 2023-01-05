package com.example.visitsapp.utils;

import android.view.View;

import com.example.visitsapp.model.Addmember;
import com.example.visitsapp.model.request.Question;

import java.util.ArrayList;

public interface ArrayListListener {
    public void onItemClick(int id, ArrayList<Question> list);
}
