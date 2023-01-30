package com.example.visitsapp.ui.adapter;

import android.nfc.NfcAdapter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.example.visitsapp.R;
import com.example.visitsapp.ui.MainActivity;


public class HistoryFeedbackFrag extends Fragment {

    private RecyclerView recyclerView;
    private TextView tvNorecord;
    private MainActivity context;

    public HistoryFeedbackFrag(MainActivity context) {
        this.context = context;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_feedback, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        tvNorecord = view.findViewById(R.id.norecord);

        setAdapter();
        return view;
    }

    private void setAdapter() {

    }
}