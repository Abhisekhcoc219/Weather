package com.example.weather.weather_5day_forecast;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class weather_forecast_model_class {
    @SerializedName("cod")
    private String cod;
    @SerializedName("message")
    private long message;
    @SerializedName("cnt")
    private long cnt;
    @SerializedName("list")
    private ArrayList<weather_Lists>lists;
    @SerializedName("city")
    private City city;


}
