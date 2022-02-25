package com.example.ex20;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HelperDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbexam.db";
    private static final int DATABASE_VERSION = 1;
    String strCreate, strDelete;

    public HelperDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public HelperDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        strCreate="CREATE TABLE "+Users.TABLE_USERS;
        strCreate+=" ("+Users.KEY_ID+" INTEGER PRIMARY KEY,";
        strCreate+=" "+Users.LNAME+" TEXT,";
        strCreate+=" "+Users.FNAME+" TEXT,";
        strCreate+=" "+Users.COMPANY+" TEXT,";
        strCreate+=" "+Users.ID+" TEXT,";
        strCreate+=" "+Users.PHONE+" TEXT,";
        strCreate+=" "+Users.ACTIVE+" INTEGER";
        strCreate+=");";
        db.execSQL(strCreate);

        strCreate="CREATE TABLE "+Companies.TABLE_COMPANIES;
        strCreate+=" ("+Companies.KEY_ID+" INTEGER PRIMARY KEY,";
        strCreate+=" "+Companies.NAME+" TEXT,";
        strCreate+=" "+Companies.TAX_ID+" TEXT,";
        strCreate+=" "+Companies.MAIN_PHONE+" TEXT,";
        strCreate+=" "+Companies.SECONDARY_PHONE+" TEXT,";
        strCreate+=" "+Companies.ACTIVE+" INTEGER";
        strCreate+=");";
        db.execSQL(strCreate);


        strCreate="CREATE TABLE "+Orders.TABLE_ORDERS;
        strCreate+=" ("+Orders.KEY_ID+" INTEGER PRIMARY KEY,";
        strCreate+=" "+Orders.RESTAURANT_ID+" TEXT,";
        strCreate+=" "+Orders.RESTAURANT_NAME+" TEXT,";
        strCreate+=" "+Orders.USER_ID+" TEXT,";
        strCreate+=" "+Orders.USER_NAME+" TEXT,";
        strCreate+=" "+Orders.DATE+" TEXT";
        strCreate+=");";
        db.execSQL(strCreate);

        strCreate="CREATE TABLE "+Meals.TABLE_MEALS;
        strCreate+=" ("+Meals.KEY_ID+" INTEGER PRIMARY KEY,";
        strCreate+=" "+Meals.APPETIZER+" TEXT,";
        strCreate+=" "+Meals.MAIN+" TEXT,";
        strCreate+=" "+Meals.SIDE+" TEXT,";
        strCreate+=" "+Meals.DESSERT+" TEXT,";
        strCreate+=" "+Meals.DRINK+" TEXT";
        strCreate+=");";
        db.execSQL(strCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        strDelete="DROP TABLE IF EXISTS "+Users.TABLE_USERS;
        db.execSQL(strDelete);
        strDelete="DROP TABLE IF EXISTS "+Companies.TABLE_COMPANIES;
        db.execSQL(strDelete);
        strDelete="DROP TABLE IF EXISTS "+Meals.TABLE_MEALS;
        db.execSQL(strDelete);
        strDelete="DROP TABLE IF EXISTS "+Orders.TABLE_ORDERS;
        db.execSQL(strDelete);

        onCreate(db);
    }
}
