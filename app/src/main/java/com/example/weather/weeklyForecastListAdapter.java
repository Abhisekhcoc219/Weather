package com.example.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class weeklyForecastListAdapter extends RecyclerView.Adapter<weeklyForecastListAdapter.ViewHolder> {
    private ArrayList<weeklyForecastDataClass>weeklyForecastListAdapterArrayLists;

    weeklyForecastListAdapter(ArrayList<weeklyForecastDataClass>weeklyForecastDataClassArrayList){
        this.weeklyForecastListAdapterArrayLists=weeklyForecastDataClassArrayList;
    }
    @NonNull
    @Override
    public weeklyForecastListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.weekly_forecast_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull weeklyForecastListAdapter.ViewHolder holder, int position) {
       holder.firstText.setText(weeklyForecastListAdapterArrayLists.get(position).getFirstText());
       holder.secondText.setText(weeklyForecastListAdapterArrayLists.get(position).getSecondText());
       holder.cloudImg.setImageResource(weeklyForecastListAdapterArrayLists.get(position).getCloudImage());
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
