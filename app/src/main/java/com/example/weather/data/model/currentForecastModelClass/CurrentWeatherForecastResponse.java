package com.example.weather.data.model.currentForecastModelClass;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CurrentWeatherForecastResponse {

    @SerializedName("coord")
    private Coords coords;
    @SerializedName("weather")
    private ArrayList<Weathers> arrayListWeather;
    @SerializedName("base")
    private String base;
    @SerializedName("main")
    private Main main;
    @SerializedName("visibility")
    private Long visibility;
    @SerializedName("wind")
    private Winds wind;
    @SerializedName("clouds")
    private Cloud clouds;
    @SerializedName("dt")
    private long dt;
    @SerializedName("sys")
    private Syss sys;
    @SerializedName("timezone")
    private long timezone;
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("cod")
    private long cod;

    public Coords getCoords() {
        return coords;
    }

    public ArrayList<Weathers> getArrayListWeather() {
        return arrayListWeather;
    }

    public String getBase() {
        return base;
    }

    public Main getMain() {
        return main;
    }

    public Long getVisibility() {
        return visibility;
    }

    public Winds getWind() {
        return wind;
    }

    public Cloud getClouds() {
        return clouds;
    }

    public long getDt() {
        return dt;
    }

    public Syss getSys() {
        return sys;
    }

    public long getTimezone() {
        return timezone;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getCod() {
        return cod;
    }
}


