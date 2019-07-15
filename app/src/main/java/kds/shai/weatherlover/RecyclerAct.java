package kds.shai.weatherlover;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import kds.shai.weatherlover.Menu;
import kds.shai.weatherlover.R;
import kds.shai.weatherlover.WeatherAdapter;
import kds.shai.weatherlover.WeatherListener;
import shay.com.weather.List;

public class RecyclerAct extends AppCompatActivity implements WeatherListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        String read = read();
        if (read != null)
            Menu.getCity(this, read());
    }

    @Override
    public void onResult(java.util.List<List> data) {
        RecyclerView rvWeather = findViewById(R.id.rvWeather);
        rvWeather.setAdapter(new WeatherAdapter(this, data));
        rvWeather.setLayoutManager(new LinearLayoutManager(null));
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }

    private String read() {
        //1) get a reference to the shared preferences object. (singleton...Not new... getShared)
        SharedPreferences stepperValues = getSharedPreferences("cityName"/*xml file name...*/, Context.MODE_PRIVATE);
        //2) return val
        return stepperValues.getString("value", ""/*defaultValue*/);
    }
}
