package com.example.weather.weather_5day_forecast;

import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("id")
    private long id;
    @SerializedName("main")
    private MainEnum main;
    @SerializedName("description")
    private Description description;
    @SerializedName("icon")
    private String icon;
}
