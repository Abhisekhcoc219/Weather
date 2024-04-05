package com.example.weather.data.model.ApiPackage;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String OPEN_WEATHER_API_BASE_URL ="https://api.openweathermap.org/" ;
    private static final String OPEN_METEO_API_BASE_URL="https://api.open-meteo.com";
    private static OpenWeatherApi apiOpenWeatherService;
    private static final String openWeatherApiKey="40b64a8b11dbcd8fd32742ef5f5ae055";
    private static OpenMeteoApi apiOpenMeteoServices;

    private static Retrofit openWeatherretrofit;
    private static Retrofit openMeteoRetrofit;
    private static Retrofit getOpenWeatherApiRetrofitInstance() {
        if (openWeatherretrofit == null) {
            openWeatherretrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(OPEN_WEATHER_API_BASE_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return openWeatherretrofit;
    }
    public static OpenWeatherApi getOpenWeatherApi(){
        if(apiOpenWeatherService==null) {
            apiOpenWeatherService = RetrofitClient.getOpenWeatherApiRetrofitInstance().create(OpenWeatherApi.class);
        }
        return apiOpenWeatherService;
    }
  private static HttpLoggingInterceptor getHttpLoggingInterceptor(){

      HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
      loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
      return loggingInterceptor;
  }
  private static OkHttpClient getOkHttpClient(){
        return new OkHttpClient.Builder()
                .addInterceptor(getHttpLoggingInterceptor())
                .build();
  }
    private static Retrofit getOpenMeteoApiRetrofitInstance() {
        if (openMeteoRetrofit == null) {
            openMeteoRetrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(OPEN_METEO_API_BASE_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return openMeteoRetrofit;
    }
    public static OpenMeteoApi getOpenMeteoApi(){
        if(apiOpenMeteoServices==null) {
            apiOpenMeteoServices = RetrofitClient.getOpenMeteoApiRetrofitInstance().create(OpenMeteoApi.class);
        }
        return apiOpenMeteoServices;
    }

    public static String getOpenWeatherApiKey() {
        return openWeatherApiKey;
    }
}
