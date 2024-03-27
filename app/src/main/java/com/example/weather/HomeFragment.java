package com.example.weather;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.weather_forecasting_current_day.RetrofitForCurrentWeather;
import com.example.weather.weather_forecasting_current_day.current_weather_forecast;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerViewTodayForecast, recyclerViewWeeklyForecast, recyclerViewWeatherCondition;
    private ArrayList<todayForecastDataClass> arrayList_today_forecast;
    private ArrayList<weeklyForecastDataClass> arrayList_weekly_forecast;
    private weeklyForecastListAdapter weeklyForecastListAdapters;
    private todayForecastListCustomAdapter todayForecastListCustomAdapter;
    private conditionListAdapter conditionListAdapter;
    private ArrayList<conditionDataClass> arrayList_conditionDataClass;
    private ImageView reallocations, weather_icon;
    private TextView cityName,userName,current_temp,time,sunset,sunrise,weatherCondition;
    private int PERMISSION_REQUEST_CODE = 123;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private LocationManager locationManager;
    private LocationRequest locationRequest;
    private Location locations;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        reallocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GpsUtils.isGpsEnable(requireActivity())) {
                    Toast.makeText(requireActivity(), "already gps on", Toast.LENGTH_SHORT).show();
                    getLiveLocation();
                } else {
                    getPermission();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        HomeViewInit(view);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        if (GpsUtils.isGpsEnable(requireActivity())) {
            Toast.makeText(requireActivity(), "already gps on", Toast.LENGTH_SHORT).show();
            getLiveLocation();
        } else {
            Toast.makeText(requireActivity(), "gps is not on", Toast.LENGTH_SHORT).show();
            getPermission();
        }
        recyclerInit(view);
        return view;
    }

    private void recyclerInit(View view) {
        todayForecastViewInitAndSet(view);//this one today forecast view
        weeklyForecastViewInitAndSet(view);//this one weekly forecast view
        weatherConditionViewInit(view);
    }

    private void weatherConditionViewInit(View view) {
        recyclerViewWeatherCondition = view.findViewById(R.id.weatherCondition);
        arrayList_conditionDataClass = new ArrayList<>();
        recyclerViewWeatherCondition.setLayoutManager(new GridLayoutManager(getContext(), 2));
        arrayList_conditionDataClass.add(new conditionDataClass("E wind", R.drawable.wind, "6 mi/h"));
        arrayList_conditionDataClass.add(new conditionDataClass("Humidity", R.drawable.water_drop, "51%"));
        arrayList_conditionDataClass.add(new conditionDataClass("UV", R.drawable.suns, "very weak"));
        arrayList_conditionDataClass.add(new conditionDataClass("Visibility", R.drawable.eye, "very weak"));
        conditionListAdapter = new conditionListAdapter(arrayList_conditionDataClass);
        recyclerViewWeatherCondition.setAdapter(conditionListAdapter);
    }

    private void todayForecastViewInitAndSet(View view) {
        arrayList_today_forecast = new ArrayList<>();
        recyclerViewTodayForecast = view.findViewById(R.id.today_forecast_recycler);
        recyclerViewTodayForecast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        arrayList_today_forecast.add(new todayForecastDataClass("Now", R.drawable.half_moon, "20c"));
        arrayList_today_forecast.add(new todayForecastDataClass("10:00", R.drawable.half_moon, "20c"));
        arrayList_today_forecast.add(new todayForecastDataClass("11:00", R.drawable.half_moon, "20c"));
        arrayList_today_forecast.add(new todayForecastDataClass("12:00", R.drawable.half_moon, "20c"));
        arrayList_today_forecast.add(new todayForecastDataClass("13:00", R.drawable.half_moon, "20c"));
        arrayList_today_forecast.add(new todayForecastDataClass("14:00", R.drawable.half_moon, "20c"));
        arrayList_today_forecast.add(new todayForecastDataClass("15:00", R.drawable.half_moon, "20c"));
        arrayList_today_forecast.add(new todayForecastDataClass("16:00", R.drawable.half_moon, "20c"));
        arrayList_today_forecast.add(new todayForecastDataClass("17:00", R.drawable.half_moon, "20c"));
        arrayList_today_forecast.add(new todayForecastDataClass("18:00", R.drawable.half_moon, "20c"));
        todayForecastListCustomAdapter = new todayForecastListCustomAdapter(arrayList_today_forecast);
        recyclerViewTodayForecast.setAdapter(todayForecastListCustomAdapter);
    }

    private void weeklyForecastViewInitAndSet(View view) {
        arrayList_weekly_forecast = new ArrayList<>();
        recyclerViewWeeklyForecast = view.findViewById(R.id.weekly_forecast_recycler);
        recyclerViewWeeklyForecast.setLayoutManager(new LinearLayoutManager(getContext()));
        arrayList_weekly_forecast.add(new weeklyForecastDataClass("today", "21c", R.drawable.sun));
        arrayList_weekly_forecast.add(new weeklyForecastDataClass("tomorrow", "21c", R.drawable.sun));
        arrayList_weekly_forecast.add(new weeklyForecastDataClass("Mon", "21c", R.drawable.sun));
        arrayList_weekly_forecast.add(new weeklyForecastDataClass("Tue", "21c", R.drawable.sun));
        arrayList_weekly_forecast.add(new weeklyForecastDataClass("Wed", "21c", R.drawable.sun));
        arrayList_weekly_forecast.add(new weeklyForecastDataClass("Thu", "21c", R.drawable.sun));
        weeklyForecastListAdapters = new weeklyForecastListAdapter(arrayList_weekly_forecast);
        recyclerViewWeeklyForecast.setAdapter(weeklyForecastListAdapters);
    }

    private void getPermission() {
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)
                .setFastestInterval(5000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addAllLocationRequests(Collections.singleton(locationRequest));
        builder.setAlwaysShow(true);
        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(requireActivity())
                .checkLocationSettings(builder.build());
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(requireActivity(), "Gps on", Toast.LENGTH_SHORT).show();
                    getLiveLocation();
                } catch (ApiException e) {
                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(requireActivity(), PERMISSION_REQUEST_CODE);
                            } catch (IntentSender.SendIntentException ex) {
                                break;
                            }
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLiveLocation() {
// Check if the app has permission to access the device's location
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // Request the permissions
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_REQUEST_CODE);
        } else {
            // Permissions are already granted, proceed with getting the location
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        getResponseWeatherApi(location.getLatitude(),location.getLongitude(),"metric",RetrofitForCurrentWeather.getApiKey());
                    } else {
                        // Location is null, handle the case if location is unavailable
                        Toast.makeText(requireActivity(), "Location unavailable", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(requireActivity(), new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Failed to obtain location, handle the failure
                    Toast.makeText(requireActivity(), "Failed to obtain location", Toast.LENGTH_SHORT).show();
                    Log.e("Essss", e.getLocalizedMessage());
                }
            });
        }
    }
    private void getResponseWeatherApi(double lat,double lon,String units,String apiKey){
        Toast.makeText(requireActivity(), "entry", Toast.LENGTH_SHORT).show();

        RetrofitForCurrentWeather.getInstance().weatherApi.getWeatherData(lat,lon,units,apiKey).enqueue(new Callback<current_weather_forecast>() {
            @Override
            public void onResponse(Call<current_weather_forecast> call, Response<current_weather_forecast> response) {
            current_weather_forecast weather=response.body();
                Log.e("ssssssss",weather.getName());
                dataSetOnViews(weather);
            }

            @Override
            public void onFailure(Call<current_weather_forecast> call, Throwable throwable) {
                Log.e("ffffffff",throwable.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(PERMISSION_REQUEST_CODE==requestCode){
            if(resultCode==RESULT_OK){
                Toast.makeText(requireActivity(), "gps was on", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode==RESULT_CANCELED){
                Toast.makeText(requireActivity(), "gps was not on", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void HomeViewInit(View view){
        reallocations = view.findViewById(R.id.reallocation);
        cityName = view.findViewById(R.id.cityName);
        userName=view.findViewById(R.id.UserName);
        weather_icon=view.findViewById(R.id.weather_condition_cloud);
        current_temp=view.findViewById(R.id.current_Temp);
        time=view.findViewById(R.id.Time);
        sunset=view.findViewById(R.id.whenSunset);
        sunrise=view.findViewById(R.id.whenSunrise);
        weatherCondition=view.findViewById(R.id.weather_description);
    }
    private void dataSetOnViews(current_weather_forecast weatherData){
        FirebaseUser u= FirebaseAuth.getInstance().getCurrentUser();
        if(u!=null){
        userName.setText(u.getDisplayName());
        }
        cityName.setText(weatherData.getName());
        current_temp.setText(String.valueOf(weatherData.getMain().getTemp()));
        time.setText(getTimes(weatherData.getTimezone()));
        sunset.setText(getSunSetAndSunRise(weatherData.getSys().getSunset()));
        sunrise.setText(getSunSetAndSunRise(weatherData.getSys().getSunrise()));
        weatherCondition.setText(weatherData.getArrayListWeather().get(0).getDescription());
//        setWeatherIcon(weatherData.getArrayListWeather().get(0).getIcon());
        SetIcons(weatherData.getArrayListWeather().get(0).getIcon());
    }
    private String getTimes(long timestamp){
        long offsetInMilliseconds = timestamp * 1000L;
        // Create a TimeZone object with the offset
        TimeZone timezone = TimeZone.getTimeZone(new Long(offsetInMilliseconds).toString());
        // Create a Calendar object with the current time in the specified timezone
        Calendar calendar = Calendar.getInstance(timezone);
        // Extract hour and minutes
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        String amPm=(hour<12)?"am" : "pm";
        return hour+" : "+minutes+" "+amPm;
    }
    private String getSunSetAndSunRise(long sunriseTimestamp){
        long sunriseTimeInMillis = sunriseTimestamp * 1000L;

        // Create a Date object from the timestamp
        Date sunriseDate = new Date(sunriseTimeInMillis);

        // Format the date in the desired format (adjust format as needed)
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm "); // Adjust format for 12-hour clock (a for AM/PM), exclude seconds
        String formattedSunriseTime = formatter.format(sunriseDate);
        return formattedSunriseTime;
    }
//    private void setWeatherIcon(String iconCode) {
//        String iconName = iconCode + "@2x.png";
//        String iconUrl = "https://openweathermap.org/img/wn/" + iconName;
//
//        // Download the icon using an AsyncTask or a background thread
//        new DownloadImageTask().execute(iconUrl);
//    }



//    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//
//        @Override
//        protected Bitmap doInBackground(String... urls) {
//            String url = urls[0];
//            Bitmap bitmap = null;
//            try {
//                URL imageUrl = new URL(url);
//                HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
//                connection.setDoInput(true);
//                connection.connect();
//                InputStream input = connection.getInputStream();
//                bitmap = BitmapFactory.decodeStream(input);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return bitmap;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap result) {
//            if (result != null) {
//                weather_icon.setImageBitmap(result);
//            } else {
//                // Handle the case where the image download failed
//            }
//        }
//    }
    private void SetIcons(String icon){
        Map<String,Integer>iconMap=new HashMap<>();
        iconMap.put("01d",R.drawable.sun_weather);
        iconMap.put("01n",R.drawable.fullmoon_weather);
        iconMap.put("02d",R.drawable.cloudy_weather);
        iconMap.put("02n",R.drawable.moon_cloud_weather);
        iconMap.put("03d",R.drawable.cloudd);
        iconMap.put("03n",R.drawable.cloudn);
        iconMap.put("04d",R.drawable.scattered_clouds);
        iconMap.put("04n",R.drawable.scattered_clouds);
        iconMap.put("09d",R.drawable.sun_shower_weather);
        iconMap.put("09n",R.drawable.rain_icon);
        iconMap.put("10d",R.drawable.rain);
        iconMap.put("11d",R.drawable.thunderstorm);
        iconMap.put("11n",R.drawable.thunderstorm);
        iconMap.put("13d",R.drawable.snowflake_weather);
        iconMap.put("13n",R.drawable.snowflake_weather);
        iconMap.put("50d",R.drawable.mist_weather);
        iconMap.put("50n",R.drawable.mist_weather);
        Integer iconResourceId = iconMap.get(icon);
        if(iconResourceId!=null){
            weather_icon.setImageResource(iconResourceId);
        }
    }
}
