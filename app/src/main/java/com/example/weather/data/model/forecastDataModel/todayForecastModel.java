package com.example.weather.data.model.forecastDataModel;

public class todayForecastModel {
    private String Time;
    private double Temperature;
    private int weatherIcon;
    todayForecastModel(){

    }
    public todayForecastModel(String time, double temp, int icon){
        this.Time=time;
        this.Temperature=temp;
        this.weatherIcon=icon;
    }

    public double getTemperature() {
        return Temperature;
    }

    public String getTime() {
        return Time;
    }

    public int getWeatherIcon() {
        return weatherIcon;
    }
}
