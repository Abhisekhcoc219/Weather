package com.example.weather.data.model.ApiPackage;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String OPEN_WEATHER_API_BASE_URL ="https://api.openweathermap.org/" ;
    private static final String OPEN_METEO_API_BASE_URL="https://api.open-meteo.com";
    private static OpenWeatherApi apiOpenWeatherService;
    private static final String GEOCODING_BASE_URL="http://api.openweathermap.org";
    private static final String openWeatherApiKey="40b64a8b11dbcd8fd32742ef5f5ae055";
    private static OpenMeteoApi apiOpenMeteoServices;
    private static GeocodingApi geocodingApi;
//    private static final String

    private static Retrofit openWeatherretrofit;
    private static Retrofit openMeteoRetrofit;
    private static Retrofit geocodingRetrofit;

    //current day forecasting
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

  //this for 24 hour and 7 day forecast
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

    //this for geocoding convert city name into latitude and longitude
    private static Retrofit getGeoLocationInstance(){
      if(geocodingRetrofit==null){
          geocodingRetrofit=new retrofit2.Retrofit.Builder()
                  .baseUrl(GEOCODING_BASE_URL)
                  .client(getOkHttpClient())
                  .addConverterFactory(GsonConverterFactory.create())
                  .build();
      }
      return geocodingRetrofit;
    }
    public static GeocodingApi getGeocodingApi(){
        if(geocodingApi==null){
            geocodingApi=RetrofitClient.getGeoLocationInstance().create(GeocodingApi.class);
        }
        return geocodingApi;
    }

}
