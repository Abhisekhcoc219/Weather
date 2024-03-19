package com.example.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class conditionListAdapter extends RecyclerView.Adapter<conditionListAdapter.ViewHolder> {
    private ArrayList<conditionDataClass>conditionDataClassesList=new ArrayList<>();
    conditionListAdapter(ArrayList<conditionDataClass>conditionDataClassesLists){
        this.conditionDataClassesList=conditionDataClassesLists;
    }
    @NonNull
    @Override
    public conditionListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull conditionListAdapter.ViewHolder holder, int position) {
     holder.firstCondition.setText(conditionDataClassesList.get(position).getFirstCondition());
     holder.imageCondition.setImageResource(conditionDataClassesList.get(position).getImageCondition());
     holder.secondCondition.setText(conditionDataClassesList.get(position).getSecondCondtion());
    }

    @Override
    public int getItemCount() {
        return conditionDataClassesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView firstCondition,secondCondition;
        private ImageView imageCondition;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstCondition= itemView.findViewById(R.id.first_condition);
            imageCondition=itemView.findViewById(R.id.image_condition);
            secondCondition=itemView.findViewById(R.id.second_condition);
        }
    }
}
