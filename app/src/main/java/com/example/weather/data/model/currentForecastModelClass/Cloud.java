package com.example.weather.data.model.currentForecastModelClass;

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
