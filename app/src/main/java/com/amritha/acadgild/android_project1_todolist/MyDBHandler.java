package com.amritha.acadgild.android_project1_todolist;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Amritha on 3/15/18.
 */

public class MyDBHandler extends SQLiteOpenHelper {

    //information of database

    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "listDB.db";

    //Initializing TABLE NAME

    public static final String TABLE_NAME = "ToDoList";

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //creating a table

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE " + TABLE_NAME +
                        "(ID INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, dateStr INTEGER )"
        );
    }

    //upgrading a table

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //creating a method for Date format

    private long getDate(String day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();
        try {
            date = dateFormat.parse(day);
        } catch (ParseException e) {
        }
        return date.getTime();
    }

    //Inserting Lists for Table

    public void insertContact(String title, String description, String dateStr) {

        //Initializing database  to write

        SQLiteDatabase db = this.getWritableDatabase();

        //Creating Instance of Content values

        ContentValues contentValues = new ContentValues();

        //putting title,description,date inside content values

        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("dateStr", getDate(dateStr));

        db.insert(TABLE_NAME, null, contentValues);//inserting content values inside Table

        db.close();//closing database

    }

    //Updating Lists for Table

    public void updateContact(String id, String title, String description, String dateStr) {
        //Initializing database  to write

        SQLiteDatabase db = this.getWritableDatabase();

        //Creating Instance of Content values

        ContentValues contentValues = new ContentValues();

        //putting title,description,date inside content values

        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("dateStr", getDate(dateStr));

        //updating content values inside Table using particular id

        db.update(TABLE_NAME, contentValues, "id = ? ", new String[]{id});

        db.close();//closing database

    }

    //Deleting Lists for Table

    public void deleteData(String id) {

        //Initializing database  to write

        SQLiteDatabase db = this.getWritableDatabase();

        //deleting content values inside Table using particular id

        db.delete(TABLE_NAME, "id = ?", new String[]{id});

        //closing database

        db.close();
    }

    //creating cursor method

    public Cursor getData() {

        //reading database

        SQLiteDatabase db = this.getReadableDatabase();

        //getting all the data inside Table by particular order

        Cursor cur = db.rawQuery("select * from " + TABLE_NAME + " order by dateStr asc", null);

        return cur;//returning cursor
    }

    //creating cursor method having on parameter

    public Cursor getDataSpecific(String id) {

        //reading database

        SQLiteDatabase db = this.getReadableDatabase();

        //getting all the data inside Table by particular order using id

        Cursor cur = db.rawQuery("select * from " + TABLE_NAME + " WHERE id = '" + id + "' order by dateStr asc", null);

        return cur;//returning cursor

    }
}