package com.example.weather.data.model.currentWeekForecastModelClass;

import com.google.gson.annotations.SerializedName;

public class CurrentWeekWeatherResponse {
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("generationtime_ms")
    private double generationtimeMs;
    @SerializedName("utc_offset_seconds")
    private int utcOffsetSeconds;
    @SerializedName("timezone")
    private String timezone;
    @SerializedName("timezone_abbreviation")
    private String timezoneAbbreviation;
    @SerializedName("elevation")
    private double elevation;
    @SerializedName("current_units")
    private currentUnits CurrentUnits;
    @SerializedName("current")
    private Currents current;
    @SerializedName("hourly_units")
    private hourlyUnits hourlyCurrentUnits;
    @SerializedName("hourly")
    private HourlyForecast hourly;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getGenerationtimeMs() {
        return generationtimeMs;
    }

    public int getUtcOffsetSeconds() {
        return utcOffsetSeconds;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getTimezoneAbbreviation() {
        return timezoneAbbreviation;
    }

    public double getElevation() {
        return elevation;
    }

    public currentUnits getCurrentUnits() {
        return CurrentUnits;
    }

    public Currents getCurrent() {
        return current;
    }

    public hourlyUnits getHourlyCurrentUnits() {
        return hourlyCurrentUnits;
    }

    public HourlyForecast getHourly() {
        return hourly;
    }
}

