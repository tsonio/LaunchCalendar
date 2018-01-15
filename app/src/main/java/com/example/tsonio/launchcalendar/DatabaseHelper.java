package com.example.tsonio.launchcalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tsonio on 09/01/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DB_NAME = "DATES_DATABASE";
    public static final int DB_VERSION = 1;

    public DatabaseHelper(Context myContext) {
        super (myContext, DB_NAME, null, DB_VERSION);
    }

    public void onCreate (SQLiteDatabase myDB) {
        String sql;
        ContentValues records;

        sql = "CREATE TABLE " + DB_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Date TEXT);";
        //myDB.execSQL("delete from "+ DB_NAME);
        myDB.execSQL(sql);

        records = new ContentValues();

        records.put("Date","15122017");

        myDB.insert(DB_NAME, null, records);

        //myDB.execSQL("DROP TABLE IF EXISTS " + "TEST_DATABASE");

    }



    //public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
   // }
    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
        myDB.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
        onCreate(myDB);

    }
}
