package com.example.weather.weather_5day_forecast;

import java.io.IOException;

public enum MainEnum {
    CLEAR, CLOUDS, SNOW;

    public String toValue() {
        switch (this) {
            case CLEAR: return "Clear";
            case CLOUDS: return "Clouds";
            case SNOW: return "Snow";
        }
        return null;
    }

    public static MainEnum forValue(String value) throws IOException {
        if (value.equals("Clear")) return CLEAR;
        if (value.equals("Clouds")) return CLOUDS;
        if (value.equals("Snow")) return SNOW;
        throw new IOException("Cannot deserialize MainEnum");
    }
}
