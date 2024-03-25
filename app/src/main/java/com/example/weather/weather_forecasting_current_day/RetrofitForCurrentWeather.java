package com.example.weather.weather_forecasting_current_day;

import com.example.weather.current_weather_api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitForCurrentWeather {
    private static final String base_url="https://api.openweathermap.org/";
    private Retrofit retrofit;
    private static RetrofitForCurrentWeather RetrofitInstance;
    public current_weather_api weatherApi;
    private static final String apiKey="40b64a8b11dbcd8fd32742ef5f5ae055";
    RetrofitForCurrentWeather(){
        retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherApi=retrofit.create(current_weather_api.class);

    }
    public static RetrofitForCurrentWeather getInstance(){
        if(RetrofitInstance==null){
            RetrofitInstance=new RetrofitForCurrentWeather();
        }
        return RetrofitInstance;
    }

    public static String getApiKey() {
        return apiKey;
    }
}
