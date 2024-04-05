package com.example.weather.data.model.ApiPackage;

import com.example.weather.data.model.currentWeekForecastModelClass.CurrentWeekWeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenMeteoApi {
    @GET("/v1/forecast")
    Call<CurrentWeekWeatherResponse> getCurrentWeekWeather(
            @Query("latitude") String latitude,
            @Query("longitude") String longitude,
            @Query("current") String current,
            @Query("hourly") String hourly
    );
}
