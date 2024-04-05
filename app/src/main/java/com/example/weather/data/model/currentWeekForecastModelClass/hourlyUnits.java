package com.example.weather.data.model.currentWeekForecastModelClass;

import com.google.gson.annotations.SerializedName;

public class hourlyUnits {
    @SerializedName("time")
    private String time;
    @SerializedName("temperature_2m")
    private String temperature2m;
    @SerializedName("weathercode")
    private String weathercode;
    // Other fields as necessary

    // Getters and setters


    public String getTime() {
        return time;
    }

    public String getTemperature2m() {
        return temperature2m;
    }

    public String getWeathercode() {
        return weathercode;
    }
}
