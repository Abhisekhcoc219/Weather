package com.example.weather;

import static androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerViewTodayForecast,recyclerViewWeeklyForecast,recyclerViewWeatherCondition;
    private ArrayList<todayForecastDataClass>arrayList_today_forecast;
    private ArrayList<weeklyForecastDataClass>arrayList_weekly_forecast;
    private weeklyForecastListAdapter weeklyForecastListAdapters;
    private todayForecastListCustomAdapter todayForecastListCustomAdapter;
    private conditionListAdapter conditionListAdapter;
    private ArrayList<conditionDataClass>arrayList_conditionDataClass;
    private ImageView reallocation,weather_icon;



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_home, container, false);
        recyclerInit(view);
        return view;
    }

    private void recyclerInit(View view){
        todayForecastViewInitAndSet(view);//this one today forecast view
        weeklyForecastViewInitAndSet(view);//this one weekly forecast view
        weatherConditionViewInit(view);
    }

    private void weatherConditionViewInit(View view) {
        recyclerViewWeatherCondition=view.findViewById(R.id.weatherCondition);
        arrayList_conditionDataClass=new ArrayList<>();
        recyclerViewWeatherCondition.setLayoutManager(new GridLayoutManager(getContext(),2));
        arrayList_conditionDataClass.add(new conditionDataClass("E wind",R.drawable.wind,"6 mi/h"));
        arrayList_conditionDataClass.add(new conditionDataClass("Humidity",R.drawable.water_drop,"51%"));
        arrayList_conditionDataClass.add(new conditionDataClass("UV",R.drawable.suns,"very weak"));
        arrayList_conditionDataClass.add(new conditionDataClass("Visibility",R.drawable.eye,"very weak"));
        conditionListAdapter=new conditionListAdapter(arrayList_conditionDataClass);
        recyclerViewWeatherCondition.setAdapter(conditionListAdapter);
    }

    private void todayForecastViewInitAndSet(View view){
        arrayList_today_forecast=new ArrayList<>();
        recyclerViewTodayForecast=view.findViewById(R.id.today_forecast_recycler);
        recyclerViewTodayForecast.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        arrayList_today_forecast.add(new todayForecastDataClass("Now",R.drawable.half_moon,"20c"));
        arrayList_today_forecast.add(new todayForecastDataClass("10:00",R.drawable.half_moon,"20c"));
        arrayList_today_forecast.add(new todayForecastDataClass("11:00",R.drawable.half_moon,"20c"));
        arrayList_today_forecast.add(new todayForecastDataClass("12:00",R.drawable.half_moon,"20c"));
        arrayList_today_forecast.add(new todayForecastDataClass("13:00",R.drawable.half_moon,"20c"));
        arrayList_today_forecast.add(new todayForecastDataClass("14:00",R.drawable.half_moon,"20c"));
        arrayList_today_forecast.add(new todayForecastDataClass("15:00",R.drawable.half_moon,"20c"));
        arrayList_today_forecast.add(new todayForecastDataClass("16:00",R.drawable.half_moon,"20c"));
        arrayList_today_forecast.add(new todayForecastDataClass("17:00",R.drawable.half_moon,"20c"));
        arrayList_today_forecast.add(new todayForecastDataClass("18:00",R.drawable.half_moon,"20c"));
        todayForecastListCustomAdapter=new todayForecastListCustomAdapter(arrayList_today_forecast);
        recyclerViewTodayForecast.setAdapter(todayForecastListCustomAdapter);
    }
    private void weeklyForecastViewInitAndSet(View view){
        arrayList_weekly_forecast=new ArrayList<>();
        recyclerViewWeeklyForecast=view.findViewById(R.id.weekly_forecast_recycler);
        recyclerViewWeeklyForecast.setLayoutManager(new LinearLayoutManager(getContext()));
        arrayList_weekly_forecast.add(new weeklyForecastDataClass("today","21c",R.drawable.sun));
        arrayList_weekly_forecast.add(new weeklyForecastDataClass("tomorrow","21c",R.drawable.sun));
        arrayList_weekly_forecast.add(new weeklyForecastDataClass("Mon","21c",R.drawable.sun));
        arrayList_weekly_forecast.add(new weeklyForecastDataClass("Tue","21c",R.drawable.sun));
        arrayList_weekly_forecast.add(new weeklyForecastDataClass("Wed","21c",R.drawable.sun));
        arrayList_weekly_forecast.add(new weeklyForecastDataClass("Thu","21c",R.drawable.sun));
        weeklyForecastListAdapters=new weeklyForecastListAdapter(arrayList_weekly_forecast);
        recyclerViewWeeklyForecast.setAdapter(weeklyForecastListAdapters);
    }
}