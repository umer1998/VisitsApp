package com.example.visitsapp.ui.feedback;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitsapp.R;
import com.example.visitsapp.model.Addmember;
import com.example.visitsapp.model.request.Question;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.utils.ArrayListListener;

import java.util.ArrayList;


public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private MainActivity context;
    private ArrayList<Question> arrayList = new ArrayList<>();
    AddmemberAdapter todaysPlanAdapter;
    private ArrayListListener mItemClickListener;
    private int id;

    public MemberAdapter(MainActivity context, int id) {
        this.context = context;
        this.id = id;

    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.fragment_blank;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddMemeberDialgoue dialog = new AddMemeberDialgoue(context );
                dialog.show(context.getSupportFragmentManager(), "MyDialogFragment");

                dialog.setOnItemClickListener(new OnAddMemberClick() {
                    @Override
                    public void onItemClick(String name, String desg) {
                        Question addmember = new Question();
                        addmember.designation= desg;
                        addmember.name = name;
                        arrayList.add(addmember);
                        todaysPlanAdapter =null;
                        setAdapter(holder.recyclerView ,arrayList);
                        mItemClickListener.onItemClick(id,arrayList);
                    }
                });
            }
        });
    }

    private void setAdapter(RecyclerView recyclerView, ArrayList<Question> arrayList) {
        todaysPlanAdapter = new AddmemberAdapter(context, arrayList);
        recyclerView.setAdapter(todaysPlanAdapter);
    }

    @Override
    public int getItemCount() {
        return 1;
    }



    class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView ivAdd;

        private RecyclerView recyclerView;


        ViewHolder(View view) {
            super(view);
            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

            ivAdd = view.findViewById(R.id.plus);


        }
    }

    public void setOnItemClickListener(final ArrayListListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


}

