package com.example.weather.data.model.ApiPackage;

import com.example.weather.data.model.currentForecastModelClass.CurrentWeatherForecastResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {
    @GET("data/2.5/weather")
    Call<CurrentWeatherForecastResponse> getCurrentWeatherForecast(
            @Query("lat") String latitude,
            @Query("lon") String longitude,
            @Query("units") String units,
            @Query("appid") String apiKey
    );

}
