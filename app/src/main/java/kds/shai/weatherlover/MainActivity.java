package kds.shai.weatherlover;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = getApplicationContext();
        //if there's no sql data inside --> new user - go to choose 5 cities.
        //save the data to the sql.
        //See automatically the 5 cities with the weather without choosing any button.
        changeFrame(new MainFragment());
    }

    public void changeFrame(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainActivity, fragment).commit();

    }

    public static Context getAppContext() {
        return MainActivity.context;
    }
}
