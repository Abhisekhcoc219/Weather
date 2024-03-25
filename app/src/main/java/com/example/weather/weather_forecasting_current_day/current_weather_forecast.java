package com.example.weather.weather_forecasting_current_day;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class current_weather_forecast {
    @SerializedName("coord")
    private Coords coords;
    @SerializedName("weather")
    private ArrayList<Weathers>arrayListWeather;
     @SerializedName("base")
    private String base;
     @SerializedName("main")
    private Main main;
     @SerializedName("visibility")
    private Long visibility;
     @SerializedName("wind")
    private Winds  wind;
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

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public void setArrayListWeather(ArrayList<Weathers> arrayListWeather) {
        this.arrayListWeather = arrayListWeather;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setVisibility(Long visibility) {
        this.visibility = visibility;
    }

    public void setWind(Winds wind) {
        this.wind = wind;
    }

    public void setClouds(Cloud clouds) {
        this.clouds = clouds;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public void setSys(Syss sys) {
        this.sys = sys;
    }

    public void setTimezone(long timezone) {
        this.timezone = timezone;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCod(long cod) {
        this.cod = cod;
    }
}

