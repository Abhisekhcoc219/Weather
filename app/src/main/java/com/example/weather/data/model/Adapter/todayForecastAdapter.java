package com.example.weather.data.model.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;
import com.example.weather.data.model.forecastDataModel.todayForecastModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class todayForecastAdapter extends RecyclerView.Adapter<todayForecastAdapter.ViewHolder> {
    private ArrayList<todayForecastModel>todayForecastModelArrayList;


    public todayForecastAdapter(ArrayList<todayForecastModel> arrayList){
        this.todayForecastModelArrayList=arrayList;
    }
    @NonNull
    @Override
    public todayForecastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.today_forcast_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull todayForecastAdapter.ViewHolder holder, int position) {
        String temp=""+Math.round(todayForecastModelArrayList.get(position).getTemperature());
    holder.time.setText(todayForecastModelArrayList.get(position).getTime());
    holder.temperature.setText(temp);
    holder.Icon.setImageResource(todayForecastModelArrayList.get(position).getWeatherIcon());
    }
    @Override
    public int getItemCount() {
        return todayForecastModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView time;
        private TextView temperature;
        private ImageView Icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.todayTime);
            temperature=itemView.findViewById(R.id.todayTemperature);
            Icon=itemView.findViewById(R.id.todayCloud);
        }
    }
}
