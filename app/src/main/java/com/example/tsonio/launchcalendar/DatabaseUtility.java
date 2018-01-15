package com.example.tsonio.launchcalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatDelegate;

import java.util.ArrayList;

/**
 * Created by Tsonio on 09/01/2018.
 */

public class DatabaseUtility {
    SQLiteDatabase myDB;

    public DatabaseUtility(Context con) {
        DatabaseHelper db = new DatabaseHelper (con);
        myDB = db.getWritableDatabase();

    }

    //Get day
    public ArrayList<String> getDay() {
        ArrayList<String> names = new ArrayList<String>();
        Cursor cur = null;
        String[] rows = new String[] {"Date"};


        try {
            cur = myDB.query (DatabaseHelper.DB_NAME, rows, null, null,
                    null, null, null);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        if(cur.moveToNext()) {
            while (cur.moveToNext()) {
                names.add(cur.getString(0));
            }
        }
        return names;
    }




    //Insert a new record for a date
    public boolean insertDay(String date){
        String d = date;
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date", d);
        myDB.insert(DatabaseHelper.DB_NAME, null, contentValues);
        return true;
    }

}
