package com.example.weather.data.model.currentWeekForecastModelClass;

import com.google.gson.annotations.SerializedName;

public class currentUnits {
    @SerializedName("time")
    private String time;
    @SerializedName("interval")
    private String interval;
    @SerializedName("temperature_2m")
    private String temperature2m;

    public String getTime() {
        return time;
    }

    public String getInterval() {
        return interval;
    }

    public String getTemperature2m() {
        return temperature2m;
    }
}
