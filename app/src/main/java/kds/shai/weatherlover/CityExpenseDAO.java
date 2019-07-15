package kds.shai.weatherlover;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class CityExpenseDAO {
    private SQLiteDatabase db = MainFragment.getDb();

    //ctor:
    public CityExpenseDAO() {
        //init the db ->
        if (db == null) {
            //init the db ->
            CityExpenseDbHelper helper = new CityExpenseDbHelper(MainActivity.getAppContext());
            db = helper.getWritableDatabase();
        } else {
            return;
        }
    }

    public CityExpense getExpense(int id) {
        String sql = "SELECT * FROM CityExpenses WHERE _id=" + id;

        int _id = 0;
        String title = null;
        try {
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();

            int cityNameIdx = cursor.getColumnIndex("cityName");
            int idIdx = cursor.getColumnIndex("_id");

            _id = cursor.getInt(idIdx);
            title = cursor.getString(cityNameIdx);

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();

        }

        return new CityExpense(_id, title);
    }

    public ArrayList<CityExpense> getExpenses() {
        String sql = "SELECT * FROM CityExpenses";
        Cursor cursor =
                db.rawQuery(sql, null);

        //empty result set:
        ArrayList<CityExpense> expenses = new ArrayList<>();

        if (!cursor.moveToNext()) return expenses;

        int cityNameIdx = cursor.getColumnIndex("cityName");
        int idIdx = cursor.getColumnIndex("_id");
        do {
            int _id = cursor.getInt(idIdx);
            String cityName = cursor.getString(cityNameIdx);

            expenses.add(new CityExpense(_id, cityName));
        } while (cursor.moveToNext());
        cursor.close();
        return expenses;
    }

    //addExpense
    public void addExpense(CityExpense expense) {
        //prawn to SQL Injection...
        String sql = String.format(
                "INSERT INTO CityExpenses(cityName)" + "VALUES('%s');", expense.getCityName());
        db.execSQL(sql); //does not return a value.
    }

    //"bur\';'DROP TABLE Expenses"
    public ArrayList<CityExpense> getExpense(String search) {
        String sql = "SELECT * FROM CityExpenses WHERE cityName LIKE '%" + search + "%';";

        String[] args = {search, search};//?, ?

        Cursor cursor =
                db.rawQuery(sql, null);

        cursor.moveToFirst();


        ArrayList<CityExpense> expenses = new ArrayList<>();
        if (!cursor.moveToNext()) return expenses;


        int tIdx = cursor.getColumnIndex("title");
        int categoryIdx = cursor.getColumnIndex("category");
        int idIdx = cursor.getColumnIndex("_id");
        do {
            int _id = cursor.getInt(idIdx);
            String cityName = cursor.getString(tIdx);
            CityExpense e = new CityExpense(_id, cityName);
            expenses.add(e);
        }
        while (cursor.moveToNext());

        cursor.close();
        return expenses;
    }

    //Update Expense
    public void updateExpense(CityExpense expense) {
        if (expense.getInt_id() == null) {
            throw new RuntimeException("No Update for you");
        }
        String sql = String.format("UPDATE CityExpenses SET cityName = '%s', WHERE _id = %d", expense.getCityName(), expense.getInt_id());
        Log.d("Expdb", sql);
        db.rawQuery(sql, null);
    }

    //Delete Expense
    public void delete(CityExpense expense) {
        Integer id = expense.getInt_id();
        String cityName = expense.getCityName();
        if (id == null) {
            throw new RuntimeException("Can't delete without id");
        }

        String sql = "DELETE FROM CityExpenses WHERE _id = " + id;

        db.execSQL(sql);
    }


}
