package kds.shai.weatherlover;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CityExpenseDbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "CityExpense.db";
    public static final int DB_VERSION = 1;

    public CityExpenseDbHelper(Context context) {
        super(context, DB_NAME, null /*default*/, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE CityExpenses(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cityName TEXT NOT NULL" + ");";
        //run SQL against the db.
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int olderVersion, int newVersion) {
        //no problems - we can delete all the data
        String sql = "DROP TABLE CityExpenses;";
        db.execSQL(sql);

        sql = "CREATE TABLE Expenses(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cityName TEXT NOT NULL" + ");";
        db.execSQL(sql);
    }
}
