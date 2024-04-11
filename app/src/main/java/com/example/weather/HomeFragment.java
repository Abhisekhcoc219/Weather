package com.example.weather;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.data.model.Adapter.conditionListAdapter;
import com.example.weather.data.model.Adapter.todayForecastAdapter;
import com.example.weather.data.model.Adapter.weeklyForecastListCustomAdapter;
import com.example.weather.data.model.ApiPackage.RetrofitClient;
import com.example.weather.data.model.currentForecastModelClass.CurrentWeatherForecastResponse;
import com.example.weather.data.model.currentWeekForecastModelClass.CurrentWeekWeatherResponse;
import com.example.weather.data.model.forecastDataModel.conditionDataClass;
import com.example.weather.data.model.forecastDataModel.todayForecastModel;
import com.example.weather.data.model.forecastDataModel.weeklyForecastDataClass;
import com.example.weather.data.model.mobilePermissions.GpsUtils;
import com.example.weather.data.model.mobilePermissions.InternetUtils;
import com.example.weather.data.model.mobilePermissions.WifiUtils;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerViewTodayForecast, recyclerViewWeeklyForecast, recyclerViewWeatherCondition;
    private ArrayList<weeklyForecastDataClass> arrayListWeeklyForecast;
    private ArrayList<todayForecastModel>todayForecastModelArrayList;
    private weeklyForecastListCustomAdapter weeklyForecastListCustomAdapters;
    private todayForecastAdapter todayForecastAdapter;
    private com.example.weather.data.model.Adapter.conditionListAdapter conditionListAdapter;
    private ArrayList<conditionDataClass> arrayList_conditionDataClass;
    private ImageView weather_icon;
    private double temperatures;
    private int iconsId;

    private CurrentWeatherForecastResponse currentWeatherForecastResponse;
    private static final String Celius = "°";
    private TextView cityName, current_temp, time, sunset, sunrise, weatherCondition;
    private int PERMISSION_REQUEST_CODE = 123;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private LocationManager locationManager;
    private LocationRequest locationRequest;
    private Location locations;
    private int indexNum = -1;

    private View views;

    private SwipeRefreshLayout swipeRefreshLayout;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (PERMISSION_REQUEST_CODE == requestCode) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(requireActivity(), "gps was on", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(requireActivity(), "gps was not on", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getTimes(long timestamp) {
        long offsetInMilliseconds = timestamp * 1000L;
        Date sunriseDate = new Date(offsetInMilliseconds);
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm "); // Adjust format for 12-hour clock (a for AM/PM), exclude seconds
        String fTime = formatter.format(sunriseDate);
        return fTime;
    }

    private String getSunSetAndSunRise(long sunriseTimestamp) {
        long sunriseTimeInMillis = sunriseTimestamp * 1000L;
        // Create a Date object from the timestamp
        Date sunriseDate = new Date(sunriseTimeInMillis);
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm "); // Adjust format for 12-hour clock (a for AM/PM), exclude seconds
        String formattedSunriseTime = formatter.format(sunriseDate);
        return formattedSunriseTime;
    }

    private int SetIcons(String icon) {
        Map<String, Integer> iconMap = new HashMap<>();
        iconMap.put("01d", R.drawable.weather_suns);
        iconMap.put("01n", R.drawable.moon_cloud_weather);
        iconMap.put("02d", R.drawable.cloudy_day);
        iconMap.put("02n", R.drawable.moon_cloud_weather);
        iconMap.put("03d", R.drawable.cloudy_day);
        iconMap.put("03n", R.drawable.cloudy_night);
        iconMap.put("04d", R.drawable.cloudy_day);
        iconMap.put("04n", R.drawable.cloudy_night);
        iconMap.put("09d", R.drawable.sun_shower_weather);
        iconMap.put("09n", R.drawable.rain);
        iconMap.put("10d", R.drawable.night_rain);
        iconMap.put("11d", R.drawable.thunder);
        iconMap.put("11n", R.drawable.thunderstorm_weather);
        iconMap.put("13d", R.drawable.snowflakes);
        iconMap.put("13n", R.drawable.weather_snows);
        iconMap.put("50d", R.drawable.mist_day);
        iconMap.put("50n", R.drawable.mist_night);
        Integer iconResourceId = iconMap.get(icon);
        if (iconResourceId != null) {
            return iconResourceId;
        }
        return 0;
    }

    private int getWeatherIcon(int icon) {
        Map<Integer, Integer> IconValue = new HashMap<>();
        IconValue.put(0, R.drawable.clear_sky);
        IconValue.put(1, R.drawable.clear_sky);
        IconValue.put(2, R.drawable.partly_cloud);
        IconValue.put(3, R.drawable.overcast_cloud);
        IconValue.put(45, R.drawable.fogs);
        IconValue.put(48, R.drawable.fogs);
        IconValue.put(51, R.drawable.downpour);
        IconValue.put(53, R.drawable.downpour);
        IconValue.put(55, R.drawable.downpour);
        IconValue.put(56, R.drawable.downpour);
        IconValue.put(57, R.drawable.downpour);
        IconValue.put(61, R.drawable.rain);
        IconValue.put(63, R.drawable.rain);
        IconValue.put(65, R.drawable.heavy_rain);
        IconValue.put(66, R.drawable.rain);
        IconValue.put(67, R.drawable.heavy_rain);
        IconValue.put(71, R.drawable.snows);
        IconValue.put(73, R.drawable.light_snow);
        IconValue.put(75, R.drawable.snowflakes);
        IconValue.put(77, R.drawable.snowflake);
        IconValue.put(80, R.drawable.rain);
        IconValue.put(81, R.drawable.heavy_rain);
        IconValue.put(82, R.drawable.rain);
        IconValue.put(85, R.drawable.heavy_rain);
        IconValue.put(86, R.drawable.snowflakes);
        IconValue.put(95, R.drawable.thunderstrom_day);
        IconValue.put(96, R.drawable.thunder);
        IconValue.put(99, R.drawable.thunderstorm_weather);
        Integer iconResourceId = IconValue.get(icon);
        if (iconResourceId != null) {
            return iconResourceId;
        }
        return 0;
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    private int getCurrentIndex(List<String> time24hour) {
        int currentTime = Integer.parseInt(getCurrentTime());
        int i = 0;

        while (i < 24) {
            if (currentTime == Integer.parseInt(time24hour.get(i).substring(11, 13))) {
                return i;
            }
            i++;
        }
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<String> get6DayTimeForecast(List<String> time24hour) {
        List<String> newString = new ArrayList<>();
        int i = 1;
        int j = 0;
        while (i <= time24hour.size()) {
            if (i >= time24hour.size()) {
                newString.add(j, time24hour.get(i - 1).substring(0, 10));
            }
            if (j <= 1) {
                if (j == 0) {
                    newString.add(j, "Today");
                } else {
                    newString.add(j, "Tomorrow");
                }
            } else {
                newString.add(j, getDayName(time24hour.get(i).substring(0, 10)));
            }
            i += 24;
            j++;
        }
        return newString;
    }

    private List<Double> get6DayTempForecast(List<Double> temp24Hour) {
        List<Double> newDouble = new ArrayList<>();
        int i = 1;
        int j = 0;
        while (i <= temp24Hour.size()) {
            if (i >= temp24Hour.size()) {
                newDouble.add(j, temp24Hour.get(i - 1));
            } else {
                newDouble.add(j, temp24Hour.get(i));
            }
            i += 24;
            j++;
        }
        return newDouble;
    }

    private List<Integer> get6DayWeatherCode(List<Integer> getWeatherCode) {
        List<Integer> newInteger = new ArrayList<>();
        int i = 1;
        int j = 0;
        while (i <= getWeatherCode.size()) {
            if (i >= getWeatherCode.size()) {
                newInteger.add(j, getWeatherCode.get(i - 1));
            } else {
                newInteger.add(j, getWeatherCode.get(i));
            }
            i += 24;
            j++;
        }
        return newInteger;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getDayName(String dataString) {
        String dayName = "";
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dataString, formatter);
            // Getting the day name
            dayName = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

            System.out.println("Day name: " + dayName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dayName;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getCurrentTime() {
        LocalTime currentTime = LocalTime.now();

        // Format the hour component in 24-hour format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
        String hour24Format = currentTime.format(formatter);
        return hour24Format;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        views = view;
        HomeViewInit(view);
        if (InternetUtils.isMobileDataAvailable(requireActivity())) {
            if (GpsUtils.isGpsEnable(requireActivity())) {
                Toast.makeText(requireActivity(), "already gps on", Toast.LENGTH_SHORT).show();
                getLiveLocation(view);
            } else {
                Toast.makeText(requireActivity(), "gps is not on", Toast.LENGTH_SHORT).show();
                getPermission(view);
            }
        } else {
            Toast.makeText(requireActivity(), "Please enable data", Toast.LENGTH_SHORT).show();
            if (WifiUtils.isWifiEnable(requireActivity())) {
                if (GpsUtils.isGpsEnable(requireActivity())) {
                    Toast.makeText(requireActivity(), "already gps on", Toast.LENGTH_SHORT).show();
                    getLiveLocation(view);
                } else {
                    Toast.makeText(requireActivity(), "gps is not on", Toast.LENGTH_SHORT).show();
                    getPermission(view);
                }
            } else {
                Toast.makeText(requireActivity(), "wifi is not connect to devices ", Toast.LENGTH_SHORT).show();
            }
        }

        return view;
    }

    private void fetchDataFromServer() {
        // Method to simulate data fetching from server
        // Simulate a delay for demonstration purposes
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Update your UI with refreshed data
                refreshing();
                // Notify SwipeRefreshLayout that the refresh has finished
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000); // 2 seconds delay

    }

    private void refreshing() {
        if (GpsUtils.isGpsEnable(requireActivity())) {
            getLiveLocation(views);
        } else {
            getPermission(views);
        }
    }

    private void HomeViewInit(View view) {
        cityName = view.findViewById(R.id.cityName);
        weather_icon = view.findViewById(R.id.weather_condition_cloud);
        current_temp = view.findViewById(R.id.current_Temp);
        time = view.findViewById(R.id.Time);
        sunset = view.findViewById(R.id.whenSunset);
        sunrise = view.findViewById(R.id.whenSunrise);
        weatherCondition = view.findViewById(R.id.weather_description);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
    }

    @Override
    public void onResume() {
        super.onResume();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchDataFromServer();
            }
        });
    }

    private void getPermission(View v) {
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
                    getLiveLocation(v);
                } catch (ApiException e) {
                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(requireActivity(), PERMISSION_REQUEST_CODE);
                                getLiveLocation(v);
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
    private void getLiveLocation(View v) {
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
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
            // Permissions are already granted, proceed with getting the location
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Toast.makeText(requireActivity(), "" + location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                        if (InternetUtils.isMobileDataAvailable(requireActivity()) || WifiUtils.isWifiEnable(requireActivity())) {
                            getResponseWeatherApi(location.getLatitude(), location.getLongitude(), "metric", RetrofitClient.getOpenWeatherApiKey(), v);
                            getResponseFor6Days(location.getLatitude(), location.getLongitude(), "temperature_2m", "temperature_2m,weathercode", v);
                            Toast.makeText(requireActivity(), "yesss", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireActivity(), "please enable data", Toast.LENGTH_SHORT).show();
                        }
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


    private void getResponseWeatherApi(double lat, double lon, String units, String apiKey, View v) {
        Toast.makeText(requireActivity(), "entry", Toast.LENGTH_SHORT).show();


        RetrofitClient.getOpenWeatherApi().getCurrentWeatherForecast(String.valueOf(lat), String.valueOf(lon), units, apiKey).enqueue(new Callback<CurrentWeatherForecastResponse>() {
            @Override
            public void onResponse(Call<CurrentWeatherForecastResponse> call, Response<CurrentWeatherForecastResponse> response) {
                CurrentWeatherForecastResponse weather = response.body();
                if (currentWeatherForecastResponse == null) {
                    currentWeatherForecastResponse = response.body();
                }
                Log.e("ssssssss", weather.getName());
                dataSetOnViews(weather);
                weatherConditionViewInit(v, weather);
            }

            @Override
            public void onFailure(Call<CurrentWeatherForecastResponse> call, Throwable throwable) {
//                Log.e("ffffffff",throwable.getLocalizedMessage());
            }
        });
    }

    private void dataSetOnViews(CurrentWeatherForecastResponse currentWeather) {
        if (currentWeather != null) {
            String feels_like = "feels like:- " + Math.round(currentWeather.getMain().getFeelsLike()) + Celius;
            String temp = String.valueOf(Math.round(currentWeather.getMain().getTemp())) + Celius;
            temperatures=Math.round(currentWeather.getMain().getTemp());
            iconsId=SetIcons(currentWeather.getArrayListWeather().get(0).getIcon());
            cityName.setText(currentWeather.getName());
            current_temp.setText(temp);
            time.setText(feels_like);
            sunset.setText(getSunSetAndSunRise(currentWeather.getSys().getSunset()));
            sunrise.setText(getSunSetAndSunRise(currentWeather.getSys().getSunrise()));
            weatherCondition.setText(currentWeather.getArrayListWeather().get(0).getDescription());
            weather_icon.setImageResource(SetIcons(currentWeather.getArrayListWeather().get(0).getIcon()));
        }
    }

    private void weatherConditionViewInit(View view, CurrentWeatherForecastResponse cw) {
        if (cw != null) {
            String ewind = String.valueOf(Math.round(cw.getWind().getSpeed())) + " km/h";
            double gettingActualVisibility = Math.round((cw.getVisibility() / 1000.0));
            String humidity = String.valueOf(cw.getMain().getHumidity()) + "%";
            String visiblity = String.valueOf(Math.round(gettingActualVisibility)) + " mi";
            String airPressure = String.valueOf(cw.getMain().getPressure()) + " mmHg";
//            Toast.makeText(requireActivity(), ""+(gettingActualVisibility*0.0062137), Toast.LENGTH_SHORT).show();
            recyclerViewWeatherCondition = view.findViewById(R.id.weatherCondition);
            arrayList_conditionDataClass = new ArrayList<>();
            recyclerViewWeatherCondition.setLayoutManager(new GridLayoutManager(getContext(), 2));
            arrayList_conditionDataClass.add(new conditionDataClass("E wind", R.drawable.wind, ewind));
            arrayList_conditionDataClass.add(new conditionDataClass("Humidity", R.drawable.water_drop, humidity));
            arrayList_conditionDataClass.add(new conditionDataClass("air pressure", R.drawable.air_pressure, airPressure));
            arrayList_conditionDataClass.add(new conditionDataClass("Visibility", R.drawable.eye, visiblity));
            conditionListAdapter = new conditionListAdapter(arrayList_conditionDataClass);
            recyclerViewWeatherCondition.setAdapter(conditionListAdapter);
        }
    }


    private void getResponseFor6Days(double lat, double lon, String current, String hourly, View v) {
        RetrofitClient.getOpenMeteoApi().getCurrentWeekWeather(String.valueOf(lat), String.valueOf(lon), current, hourly).enqueue(new Callback<CurrentWeekWeatherResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<CurrentWeekWeatherResponse> call, Response<CurrentWeekWeatherResponse> response) {
                CurrentWeekWeatherResponse weekWeatherResponse = response.body();
//              Log.e("TAGS",""+response.body().getHourly().getTemperature2m());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                     todayForecastViewInitAndSet(v, weekWeatherResponse);//this one today forecast view
                    }
                },800);

                weeklyForecastViewInitAndSet(v, weekWeatherResponse);
            }

            @Override
            public void onFailure(Call<CurrentWeekWeatherResponse> call, Throwable throwable) {
                Log.e("TAGF", throwable.getLocalizedMessage());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void weeklyForecastViewInitAndSet(View view, CurrentWeekWeatherResponse weekWeatherResponse) {
        arrayListWeeklyForecast = new ArrayList<>();
        recyclerViewWeeklyForecast = view.findViewById(R.id.weekly_forecast_recycler);
        recyclerViewWeeklyForecast.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String>time=get6DayTimeForecast(weekWeatherResponse.getHourly().getTime());
        List<Double>temp=get6DayTempForecast(weekWeatherResponse.getHourly().getTemperature2m());
        List<Integer>weatherCode=get6DayWeatherCode(weekWeatherResponse.getHourly().getWeatherCode());
        for(int i=0;i<time.size();i++){
            arrayListWeeklyForecast.add(i,new weeklyForecastDataClass(time.get(i),temp.get(i),getWeatherIcon(weatherCode.get(i))));
        }
//        Log.e("TAGF",weekWeatherResponse.getHourly().getTime().subList())
        weeklyForecastListCustomAdapters=new weeklyForecastListCustomAdapter(arrayListWeeklyForecast);
        recyclerViewWeeklyForecast.setAdapter(weeklyForecastListCustomAdapters);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void todayForecastViewInitAndSet(View view, CurrentWeekWeatherResponse weekWeatherResponse) {
     todayForecastModelArrayList=new ArrayList<>();
     recyclerViewTodayForecast=view.findViewById(R.id.todayForecastRecyclerView);
     recyclerViewTodayForecast.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
     List<String>time=getTodayTime(weekWeatherResponse.getHourly().getTime());
     List<Double>temp=getTodayTemperature(weekWeatherResponse.getHourly().getTemperature2m(),weekWeatherResponse.getHourly().getTime());
     List<Integer>icons=getTodayCloudIcon(weekWeatherResponse.getHourly().getWeatherCode(),weekWeatherResponse.getHourly().getTime());
    Log.e("TAGSS",""+temp.get(0));
     for (int i=0;i<time.size();i++){
     todayForecastModelArrayList.add(i, new todayForecastModel(time.get(i), temp.get(i),icons.get(i)));
     }
     todayForecastAdapter=new todayForecastAdapter(todayForecastModelArrayList);
     recyclerViewTodayForecast.setAdapter(todayForecastAdapter);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<String> getTodayTime(List<String>time){
    List<String>time24Hour=new ArrayList<>();
    int currentIndex=getCurrentIndex(time);
    int sum=(currentIndex+24);
    int j=0;
    int i=currentIndex;
    while(i<sum){
        if(j==0){
            time24Hour.add(j,"Now");
        }
        else{
            time24Hour.add(j,time.get(i).substring(11,16));
        }
        i++;
        j++;
    }
    return time24Hour;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<Double>getTodayTemperature(List<Double>temperature,List<String>t){
        List<Double>temp=new ArrayList<>();
        int currentIndex=getCurrentIndex(t);
        int sum=(currentIndex+24);
        int i=currentIndex;
        int j=0;
        while (i<sum){
            if(j==0){
                temp.add(j,temperatures);
            }
            else{
                temp.add(j,temperature.get(i));
            }
            i++;
            j++;
        }
        return temp;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<Integer>getTodayCloudIcon(List<Integer>cloudIcon, List<String>t){
        List<Integer>Icons=new ArrayList<>();
        int currentIndex=getCurrentIndex(t);
        int sum=(currentIndex+24);
        int i=currentIndex;
        int j=0;
        while (i<sum){
            if(j==0){
                Icons.add(j,iconsId);
            }
            else{
                Icons.add(j,getWeatherIcon(cloudIcon.get(i)));
            }
            i++;
            j++;
        }
        return Icons;
    }
    }