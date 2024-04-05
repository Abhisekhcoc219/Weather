package com.example.weather.data.model.currentForecastModelClass;

import com.google.gson.annotations.SerializedName;

public class Syss {
    @SerializedName("country")
    private String country;
    @SerializedName("sunrise")
    private long sunrise;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    @SerializedName("sunset")
    private long sunset;
}
