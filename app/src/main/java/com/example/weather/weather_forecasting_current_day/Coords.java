package com.example.weather.weather_forecasting_current_day;

import com.google.gson.annotations.SerializedName;

public class Coords {
    @SerializedName("lon")
    private double lon;
    @SerializedName("lat")
    private double lat;

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
