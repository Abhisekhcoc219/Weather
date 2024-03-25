package com.example.weather.weather_forecasting_current_day;

import com.google.gson.annotations.SerializedName;

public class Winds {
    @SerializedName("speed")
    private long speed;
    @SerializedName("deg")
    private long deg;
    @SerializedName("gust")
    private double gust;

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public long getDeg() {
        return deg;
    }

    public void setDeg(long deg) {
        this.deg = deg;
    }

    public double getGust() {
        return gust;
    }

    public void setGust(double gust) {
        this.gust = gust;
    }
}
