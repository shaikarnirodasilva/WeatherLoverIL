package kds.shai.weatherlover;

import shay.com.weather.List;
import shay.com.weather.WeatherResult;

public interface WeatherListener {

        void onResult(java.util.List<List> data);
        void onError(Exception e);
    }

