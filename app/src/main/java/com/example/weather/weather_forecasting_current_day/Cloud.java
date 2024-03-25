package com.example.weather.weather_forecasting_current_day;

import com.google.gson.annotations.SerializedName;

public class Cloud {
    @SerializedName("all")
    private long all;

    public long getAll() {
        return all;
    }

    public void setAll(long all) {
        this.all = all;
    }
}
