package com.example.weather;

public class todayForecastDataClass {
    private String time;
    private double temperature;
    private int cloudIcon;
    todayForecastDataClass(String time,double temp,int icon){
        this.time=time;
        this.temperature=temp;
        this.cloudIcon=icon;
    }

    public String getTime() {
        return time;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getCloudIcon() {
        return cloudIcon;
    }
}
