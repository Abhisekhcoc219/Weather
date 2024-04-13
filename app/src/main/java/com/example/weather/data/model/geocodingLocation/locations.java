package com.example.weather.data.model.geocodingLocation;

import com.google.gson.annotations.SerializedName;

public class locations {
    @SerializedName("name")
    private String name;
    @SerializedName("lat")
    private double lat;
    @SerializedName("lon")
    private double lon;
    @SerializedName("state")
    private String state;

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getState() {
        return state;
    }
}
