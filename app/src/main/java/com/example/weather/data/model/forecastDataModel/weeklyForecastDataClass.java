package com.example.weather.data.model.forecastDataModel;

public class weeklyForecastDataClass {
    private String time;
    private double temperature;
    private int cloudIcon;
    public weeklyForecastDataClass(String time, double temp, int icon)
    {
        this.time=time;
        this.temperature=temp;
        this.cloudIcon=icon;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getCloudIcon() {
        return cloudIcon;
    }

    public String getTime() {
        return time;
    }
}
