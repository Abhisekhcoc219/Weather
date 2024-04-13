package com.example.weather.data.model.ApiPackage;

import com.example.weather.data.model.geocodingLocation.locations;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodingApi {
    @GET("/geo/1.0/direct")
    Call<List<locations>> getLatLon(
      @Query("q")String city,
      @Query("limit")int limit,
      @Query("appid")String apiKey
    );
}
