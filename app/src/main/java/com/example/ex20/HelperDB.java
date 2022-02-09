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
        strCreate+=" "+Users.CARD_ID+" TEXT,";
        strCreate+=" "+Users.LNAME+" TEXT,";
        strCreate+=" "+Users.FNAME+" TEXT,";
        strCreate+=" "+Users.COMPANY+" TEXT,";
        strCreate+=" "+Users.ID+" TEXT,";
        strCreate+=" "+Users.PHONE+" TEXT";
        strCreate+=");";
        db.execSQL(strCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        strDelete="DROP TABLE IF EXISTS "+Users.TABLE_USERS;
        db.execSQL(strDelete);

        onCreate(db);
    }
}
