package com.example.weather.data.model.currentWeekForecastModelClass;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HourlyForecast {
    @SerializedName("time")
    private List<String> time;
    @SerializedName("temperature_2m")
    private List<Double> temperature2m;
    @SerializedName("weathercode")
    private List<Integer> weatherCode;

    public List<String> getTime() {
        return time;
    }

    public List<Double> getTemperature2m() {
        return temperature2m;
    }

    public List<Integer> getWeatherCode() {
        return weatherCode;
    }
}
