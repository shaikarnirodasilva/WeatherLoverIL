package kds.shai.weatherlover;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import shay.com.weather.City;

import static kds.shai.weatherlover.FileUtils.fileExist;
import static kds.shai.weatherlover.FileUtils.readFromFile;
import static kds.shai.weatherlover.FileUtils.writeCities;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private Button btnAdd, btnDelete, btnExit, btn1, btn2, btn3, btnOk;
    private EditText etCity;
    private static SQLiteDatabase db;
    List<String> citiesList = new ArrayList<>();
    CityExpenseDAO dao = new CityExpenseDAO();


    String fileName = "Cities.txt";

    public static synchronized SQLiteDatabase getDb() {
        return db;
    }


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main2, container, false);
        findViewByIds(v);
        // If the data base doesn't exist, so we need to create one!!
        db = new CityExpenseDbHelper(getContext()).getWritableDatabase();


        //Add City//

        btnAdd.setOnClickListener(view -> {
            btnOk.setVisibility(View.VISIBLE);
            etCity.setVisibility(View.VISIBLE);
            btnAdd.setVisibility(View.GONE);
        });
        btnDelete.setOnClickListener(view -> deleteClick());
        //OK Button//

        btnOk.setOnClickListener(view -> {
            //QA
            if (etCity.getText() != null) {
                //get city name from edit text and remember it to shared prefs.
                final String cityName = etCity.getText().toString();
                write(cityName);
                checkDatabase(cityName);
                dbToList();
                //if city exists ---> go
                //If the city is not in the file --> add it to the list,file and database.
                if (!checkIfCityExists(cityName)) {
                    if (citiesList.size() >= 3) {
                        etCity.setVisibility(View.GONE);
                        btnOk.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "3 Citys Limit ONLY!Delete City Is Available", Toast.LENGTH_SHORT).show();
                    }

                    addCityToDataBase(cityName);
                    intentToRecycler();

                    //If the city already exists, go to recycler, no need to save again.
                } else {
                    buttonClick();

                }

            } else
                etCity.setError("Write City Name");
        });

        return v;
    }

    //Writes the cityName to shared prefs.
    private void write(String cityName) {
        //1) reference to the shared object (sharedPreferences)
        //singleton...? No new...?
        //allows us to Read data:
        SharedPreferences stepperValues = getContext().getSharedPreferences("cityName"/*xml file name...*/, Context.MODE_PRIVATE);
        //2) reference to the editor of the sharedPreferences
        //Writer
        SharedPreferences.Editor editor = stepperValues.edit();
        //3) editor.put...(key, value).
        editor.putString("value", cityName);
        //4) commit / apply
        //Don't forget to save!
        //Why doesn't step 3 does not save automatically?
//        editor.commit();// Main Thread. -> save();
        editor.apply();//new Thread -> save();
        //
    }


    //If city Exists in list check .
    boolean checkIfCityExists(String cityName) {
        boolean b = false;
        if (citiesList.size() == 0) {
            citiesList.add(0, cityName);


        }
        for (int i = 0; i < citiesList.size(); i++) {
            if (citiesList.get(i).toString().equals(cityName)) {
                Log.d("City", "City already exists in list, MOVING ON!");
                b = true;
            }

        }
        return b;
    }

    private void intentToRecycler() {
        Intent i = new Intent(getActivity(), RecyclerAct.class);
        startActivity(i);
    }


    //checks if the city name is already in the database, if not add it.
    private void addCityToDataBase(String cityName) {
        int k = 0;
        try {
            ArrayList<CityExpense> expenses = dao.getExpenses();
            if (expenses.size() != 0) {
                if (dao.getExpense(cityName).size() == 0) {
                    for (int i = 0; i < expenses.size(); i++) {
                        String cityName1 = expenses.get(i).getCityName();
                        if (cityName1 == null)
                            dao.addExpense(new CityExpense(i, cityName));

                    }
                    dao.addExpense(new CityExpense(dao.getExpenses().size() + 1, cityName));

                }

            } else dao.addExpense(new CityExpense(0, cityName));
            //no database yet so, write the first one .
        } catch (NullPointerException e) {
            e.printStackTrace();

        }
    }

    //load the list with db city list at the start.
    private void dbToList() {
        ArrayList<CityExpense> expenses = dao.getExpenses();
        List<String> list = new ArrayList<>();
        try {
            if (expenses.size() != 0) {
                for (int i = 0; i < expenses.size(); i++) {
                    String s = expenses.get(i).getCityName();
                    list.add(i, s);
                }
                citiesList = list;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    //if there is more than 3 cities delete it from database.
    private void checkDatabase(String cityName) {
        ArrayList<CityExpense> expenses = dao.getExpenses();
        db = new CityExpenseDbHelper(getContext()).getReadableDatabase();

        int o = 0;

        if (expenses.size() != 0) {
            ArrayList<CityExpense> expense1 = dao.getExpense(cityName);
            for (int i = 0; i < expense1.size(); i++) {

                if (expense1.size() > 1) {
                    return;
                }
            }


            for (int i = 1; i < dao.getExpenses().size(); i++) {
                if (expenses.size() > 3) {
                    if (dao.getExpense(i) != null) {
                        CityExpense expense = dao.getExpense(i);
                        dao.delete(expense);
                        expenses.remove(i);

                    }
                }
            }
        }
    }

    //findviewbyid.
    private void findViewByIds(View v) {
        btnAdd = v.findViewById(R.id.btnAdd);
        btnDelete = v.findViewById(R.id.btnDelete);
        btnExit = v.findViewById(R.id.btnExit);
        btn1 = v.findViewById(R.id.btn1);
        btn2 = v.findViewById(R.id.btn2);
        btn3 = v.findViewById(R.id.btn3);
        etCity = v.findViewById(R.id.etCity);
        btnOk = v.findViewById(R.id.btnOK);

    }

    //button click to recycler
    private void buttonClick() {
        etCity.setVisibility(View.GONE);
        btnOk.setVisibility(View.GONE);
        btn1.setVisibility(View.VISIBLE);
        btn2.setVisibility(View.VISIBLE);
        btn3.setVisibility(View.VISIBLE);
        btn1.setText(citiesList.get(0));
        btn2.setText(citiesList.get(1));
        btn3.setText(citiesList.get(2));
        btn1.setOnClickListener(view -> {
            write(btn1.getText().toString());
            intentToRecycler();
        });
        btn2.setOnClickListener(view -> {
            write(btn2.getText().toString());
            intentToRecycler();
        });
        btn3.setOnClickListener(view -> {
            write(btn3.getText().toString());
            intentToRecycler();
        });

    }

    //delete cities from db and list with city buttons.
    private void deleteClick() {
        btnDelete.setOnClickListener(view -> {

            if (citiesList.size() >= 3) {
                btn1.setText(citiesList.get(0));
                btn2.setText(citiesList.get(1));
                btn3.setText(citiesList.get(2));
                etCity.setVisibility(View.GONE);
                btnOk.setVisibility(View.GONE);
                btn1.setVisibility(View.VISIBLE);
                btn2.setVisibility(View.VISIBLE);
                btn3.setVisibility(View.VISIBLE);

            } else
                Toast.makeText(getContext(), "You have " + citiesList.size() + " cities,add cities first!", Toast.LENGTH_SHORT).show();
        });
        btn1.setOnClickListener(view -> {
            citiesList.remove(0);
            ArrayList<CityExpense> expense = dao.getExpense(btn1.getText().toString());
            for (int i = 0; i < expense.size(); i++) {
                CityExpense cityExpense = expense.get(i);
                dao.delete(cityExpense);
            }

        });
        btn2.setOnClickListener(view -> {
            citiesList.remove(1);
            ArrayList<CityExpense> expense = dao.getExpense(btn2.getText().toString());
            for (int i = 0; i < expense.size(); i++) {
                CityExpense cityExpense = expense.get(i);
                dao.delete(cityExpense);
            }
        });
        btn3.setOnClickListener(view -> {
            citiesList.remove(2);
            ArrayList<CityExpense> expense = dao.getExpense(btn3.getText().toString());
            for (int i = 0; i < expense.size(); i++) {
                CityExpense cityExpense = expense.get(i);
                dao.delete(cityExpense);
            }
        });

    }

}







