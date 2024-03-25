package com.example.weather.weather_5day_forecast;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class weather_Lists {


    @SerializedName("dt")
    private long dt;
    @SerializedName("main")
    private MainClass main;
    @SerializedName("weather")
    private ArrayList<Weather> weather;
    @SerializedName("clouds")
    private Clouds clouds;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("visibility")
    private Long visibility;
    @SerializedName("pop")
    private double pop;
    @SerializedName("snow")
    private Snow snow;
    @SerializedName("sys")
    private Sys sys;
    @SerializedName("dt_txt")
    private String dtTxt;
}
