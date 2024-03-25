package com.example.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class todayForecastListCustomAdapter extends RecyclerView.Adapter<todayForecastListCustomAdapter.ViewHolder> {
    ArrayList<todayForecastDataClass>todayForecastListCustomAdapterArrayList;
    todayForecastListCustomAdapter(ArrayList<todayForecastDataClass>todayForecastListCustomAdapterArrayLists){
        this.todayForecastListCustomAdapterArrayList=todayForecastListCustomAdapterArrayLists;
    }
    @NonNull
    @Override
    public todayForecastListCustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.today_forcast_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull todayForecastListCustomAdapter.ViewHolder holder, int position) {
    holder.firstText.setText(todayForecastListCustomAdapterArrayList.get(position).getFirstText());
    holder.secondText.setText(todayForecastListCustomAdapterArrayList.get(position).getSecondText());
    holder.cloudImg.setImageResource(todayForecastListCustomAdapterArrayList.get(position).getCloudImage());
    }

    @Override
    public int getItemCount() {
        return todayForecastListCustomAdapterArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView firstText,secondText;
        private ImageView cloudImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstText=itemView.findViewById(R.id.first_Text);
            secondText=itemView.findViewById(R.id.second_Text);
            cloudImg=itemView.findViewById(R.id.cloud_img);
        }
    }
}