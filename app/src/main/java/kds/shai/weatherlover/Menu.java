package kds.shai.weatherlover;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.google.gson.Gson;

import shay.com.weather.WeatherResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static kds.shai.weatherlover.FileUtils.*;

public class Menu {
    private static volatile List<shay.com.weather.List> list = null;
    private static volatile List<shay.com.weather.List> newList = null;
    private static Activity activity;

    public static void getCity(final WeatherListener listener, final String cityName) {
            cityData(cityName);
        //TODO:if there is a new information ,
        // that is not in the file,
        // that equal to database information,
        //give it to the observer interface.
        //if the list is not null --> QA.
        if (list != null) listener.onResult(list);


    }

    private static void cityData(String cityName) {
        Thread t = new Thread(() -> {
            String apiPart1 = "http://api.openweathermap.org/data/2.5/forecast?q=";
            String apiPart3 = ",il&units=metric&appid=ca9d8e28f8c047e73a75eab6a072d626";
            String address = apiPart1 + cityName + apiPart3;
            try {
                //get the json from the web as String
                String json = HTTPUtils.read(address);
                //use gson
                //use gson to convert the json to JAVA
                Gson gson = new Gson();
                //now you got a JAVA Object.
                //this object is WeatherResult:
                WeatherResult weatherResult = gson.fromJson(json, WeatherResult.class);
                list = weatherResult.getList();
                for (shay.com.weather.List item : list) {
                    System.out.println("Dtext:" + item.getDtTxt());
                    System.out.println("Description:" + item.getWeather().get(0).getDescription());
                    System.out.println("Temperature:" + item.getMain().getTemp());
                    System.out.println("Humidity:" + item.getMain().getHumidity());
                    System.out.println("____________________________________");

                }

                System.out.println(json);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(activity, "City is not right, try again.", Toast.LENGTH_SHORT).show();
                //TODO:delete the new city from database
                changeFrame(new MainFragment());
            }
        });
        t.start();
        while (list == null) {
            return;
        }
    }

    private static void changeFrame(Fragment fragment) {
        FragmentTransaction ft = ((MainActivity) activity).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.rvWeather, fragment).commit();

    }

}
