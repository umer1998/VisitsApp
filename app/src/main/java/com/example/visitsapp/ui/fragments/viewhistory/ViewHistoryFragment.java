package com.example.visitsapp.ui.fragments.viewhistory;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.visitsapp.R;
import com.example.visitsapp.business.impl.Business;
import com.example.visitsapp.delegate.ResponseCallBack;
import com.example.visitsapp.model.responce.PlansData;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.ui.fragments.BaseFragment;
import com.example.visitsapp.utils.OnItemClickListener;
import com.example.visitsapp.utils.alert.AlertUtils;

import java.util.ArrayList;


public class ViewHistoryFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private TextView tvNoRecord;
    private MainActivity context;
    private String location;


    public ViewHistoryFragment(MainActivity context, String location) {
        this.context = context;
        this.location = location;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_history, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        tvNoRecord = view.findViewById(R.id.norecord);
        getHistoryEvents(location);

        return view;
    }

    private void setAdapter(ArrayList<PlansData> body) {
        HistoryEventAdapter historyEventAdapter = new HistoryEventAdapter(context, body);
        recyclerView.setAdapter(historyEventAdapter);

        historyEventAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                context.getFeedbackResponce(String.valueOf(body.get(position).id));
            }
        });
    }

    private void getHistoryEvents(String location) {
        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }



        Business serviceImp = new Business() ;
        serviceImp.getHistoryEvents( location, new ResponseCallBack<ArrayList<PlansData>>() {
            @Override
            public void onSuccess(ArrayList<PlansData> body) {

                if(body.size()>0){
                    recyclerView.setVisibility(View.VISIBLE);
                    tvNoRecord.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    tvNoRecord.setVisibility(View.VISIBLE);
                }

                if (dialog != null) {
                    dialog.dismiss();
                }
                setAdapter(body);
            }

            @Override
            public void onFailure(String message) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                AlertUtils.showAlert(context, message);


            }
        });
    }

    @Override
    public boolean onBackPressed() {
        context.getExecutedEvent();
        return false;
    }
}