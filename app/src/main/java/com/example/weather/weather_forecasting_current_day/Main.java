package com.example.weather.weather_forecasting_current_day;

import com.google.gson.annotations.SerializedName;

public class Main {
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
    @SerializedName("humidity")
    private long humidity;
    @SerializedName("sea_level")
    private long seaLevel;
    @SerializedName("grnd_level")
    private long grndLevel;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public long getPressure() {
        return pressure;
    }

    public void setPressure(long pressure) {
        this.pressure = pressure;
    }

    public long getHumidity() {
        return humidity;
    }

    public void setHumidity(long humidity) {
        this.humidity = humidity;
    }

    public long getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(long seaLevel) {
        this.seaLevel = seaLevel;
    }

    public long getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(long grndLevel) {
        this.grndLevel = grndLevel;
    }
}
