package com.example.weather.weather_5day_forecast;

import com.example.weather.R;

import java.io.IOException;

public enum Description {
    BROKEN_CLOUDS, CLEAR_SKY, FEW_CLOUDS, LIGHT_SNOW, OVERCAST_CLOUDS, SNOW;

    public int toValue() {
        switch (this) {
            case BROKEN_CLOUDS: return R.drawable.broken_clouds;//broken clouds
            case CLEAR_SKY: return R.drawable.clear_sky;//"clear sky";
            case FEW_CLOUDS: return R.drawable.few_cloud;//"few clouds";
            case LIGHT_SNOW: return R.drawable.light_snow;//"light snow";
            case OVERCAST_CLOUDS: return R.drawable.overcast_cloud;//"overcast clouds";
            case SNOW: return R.drawable.snows;//"snow";
        }
        return R.drawable.d;
    }

    public static Description forValue(String value) throws IOException {
        if (value.equals("broken clouds")) return BROKEN_CLOUDS;
        if (value.equals("clear sky")) return CLEAR_SKY;
        if (value.equals("few clouds")) return FEW_CLOUDS;
        if (value.equals("light snow")) return LIGHT_SNOW;
        if (value.equals("overcast clouds")) return OVERCAST_CLOUDS;
        if (value.equals("snow")) return SNOW;
        throw new IOException("Cannot deserialize Description");
    }
}
