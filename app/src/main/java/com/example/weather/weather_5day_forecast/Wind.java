package com.example.weather.weather_5day_forecast;

import com.google.gson.annotations.SerializedName;

public class Wind {
    @SerializedName("speed")
    private double speed;
    @SerializedName("deg")
    private int deg;
    @SerializedName("gust")
    private double gust;
}
