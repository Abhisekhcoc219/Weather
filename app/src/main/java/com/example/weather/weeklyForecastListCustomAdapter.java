package com.example.weather;

import static com.example.weather.todayForecastListCustomAdapter.*;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class weeklyForecastListCustomAdapter extends RecyclerView.Adapter<weeklyForecastListCustomAdapter.ViewHolder> {
    private ArrayList<weeklyForecastDataClass>weeklyForecastListAdapterArrayLists;

    weeklyForecastListCustomAdapter(ArrayList<weeklyForecastDataClass>weeklyForecastDataClassArrayList){
        this.weeklyForecastListAdapterArrayLists=weeklyForecastDataClassArrayList;
    }
    @NonNull
    @Override
    public weeklyForecastListCustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.weekly_forecast_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull weeklyForecastListCustomAdapter.ViewHolder holder, int position) {
       holder.firstText.setText(weeklyForecastListAdapterArrayLists.get(position).getTime());
       holder.secondText.setText(""+Math.round(weeklyForecastListAdapterArrayLists.get(position).getTemperature())+Celius);
       holder.cloudImg.setImageResource(weeklyForecastListAdapterArrayLists.get(position).getCloudIcon());
    }

    @Override
    public int getItemCount() {
        return weeklyForecastListAdapterArrayLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView firstText,secondText;
        private ImageView cloudImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstText=itemView.findViewById(R.id.weekText);
            secondText=itemView.findViewById(R.id.tempText);
            cloudImg=itemView.findViewById(R.id.cloudImg);
        }
    }
}
