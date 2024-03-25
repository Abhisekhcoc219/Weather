package com.example.weather;

import android.content.Context;
import android.location.LocationManager;

public class GpsUtils {
    public static boolean isGpsEnable(Context context){
        LocationManager locationManager=(LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if(locationManager!=null){
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        return false;
    }
}
