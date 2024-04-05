package com.example.weather.data.model.currentWeekForecastModelClass;

import com.google.gson.annotations.SerializedName;

public class Currents {
    @SerializedName("time")
    private String time;
    @SerializedName("interval")
    private int interval;
    @SerializedName("temperature_2m")
    private double temperature2m;

    public String getTime() {
        return time;
    }

    public int getInterval() {
        return interval;
    }

    public double getTemperature2m() {
        return temperature2m;
    }
}
