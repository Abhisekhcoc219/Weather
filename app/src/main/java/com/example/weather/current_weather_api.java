package com.example.weather;

import com.example.weather.weather_forecasting_current_day.current_weather_forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface current_weather_api {
    @GET("data/2.5/weather")
    Call<current_weather_forecast> getWeatherData(
      @Query("lat") double latitude,
      @Query("lon") double longitude,
      @Query("units") String units,
      @Query("appid") String apiKey
    );
}
