package kds.shai.weatherlover;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import shay.com.weather.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherVH> {
    private Context context;
    private java.util.List<List> list;

    public WeatherAdapter(Context context, java.util.List<List> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public WeatherVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.weather_item, parent, false);
        return new WeatherVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherVH holder, int position) {
        List list1 = list.get(position);
        String temp = list1.getMain().getTemp().toString();
        String humid = list1.getMain().getHumidity().toString();
        String description = list1.getWeather().get(0).getDescription();
        String date = list1.getDtTxt();
        holder.tvDate.setText("Date and Time : " +date);
        holder.tvHumid.setText("Humidity :" +humid);
        holder.tvTemperature.setText("Temperature: " + temp);
        holder.tvDesc.setText("Description : "+description);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class WeatherVH extends RecyclerView.ViewHolder {
        TextView tvTemperature, tvHumid, tvDesc, tvDate;

        public WeatherVH(View v) {
            super(v);
            this.tvTemperature = v.findViewById(R.id.tvTemperature);
            this.tvDate = v.findViewById(R.id.tvDate);
            this.tvHumid = v.findViewById(R.id.tvHumid);
            this.tvDesc = v.findViewById(R.id.tvDesc);

        }
    }
}
