package com.example.weather.weather_5day_forecast;

import com.google.gson.annotations.SerializedName;

public class MainClass {
    @SerializedName("temp")
    private double temp;
    @SerializedName("feels_like")
    private double feelsLike;
    @SerializedName("temp_min")
    private double tempMin;
    @SerializedName("temp_max")
    private double tempMax;
    @SerializedName("pressure")
    private long pressure;
    @SerializedName("sea_level")
    private long seaLevel;
    @SerializedName("grnd_level")
    private long grndLevel;
    @SerializedName("humidity")
    private long humidity;
    @SerializedName("temp_kf")
    private double tempKf;
}
