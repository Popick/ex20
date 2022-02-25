package com.example.ex20;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class new_order_activity extends AppCompatActivity {
    Intent si;
    SQLiteDatabase db;
    HelperDB hlp;
    ContentValues cv;
    int allValid;
    EditText appetizerET,mainET,sideET,dessertET,drinkET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        si = new Intent(this, new_order_second_activity.class);
        appetizerET = (EditText) findViewById(R.id.inputappetizer);
        mainET = (EditText) findViewById(R.id.inputmdish);
        sideET = (EditText) findViewById(R.id.inputsmeal);
        dessertET = (EditText) findViewById(R.id.inputdessert);
        drinkET = (EditText) findViewById(R.id.inputdrink);

        hlp = new HelperDB(this);
        cv = new ContentValues();
    }

    public void next(View view) {
        allValid = 0;
        if (appetizerET.getText().length() == 0) {
            appetizerET.setError("Can't Be Empty");
        } else {
            allValid++;
        }
        if (mainET.getText().length() == 0) {
            mainET.setError("Can't Be Empty");
        } else {
            allValid++;
        }
        if (sideET.getText().length() == 0) {
            sideET.setError("Can't Be Empty");
        } else {
            allValid++;
        }
        if (dessertET.getText().length() == 0) {
            dessertET.setError("Can't Be Empty");
        } else {
            allValid++;
        }
        if (drinkET.getText().length() == 0) {
            drinkET.setError("Can't Be Empty");
        } else {
            allValid++;
        }
        if (allValid == 5) {
            startActivityForResult(si,1);
        }
    }
    public void back(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int source, int good, @Nullable Intent data_back) {
        super.onActivityResult(source, good, data_back);
        if (good==RESULT_OK){

            cv.put(Meals.APPETIZER, appetizerET.getText().toString());
            cv.put(Meals.MAIN, mainET.getText().toString());
            cv.put(Meals.SIDE, sideET.getText().toString());
            cv.put(Meals.DESSERT, dessertET.getText().toString());
            cv.put(Meals.DRINK,drinkET.getText().toString());

            db = hlp.getWritableDatabase();
            db.insert(Meals.TABLE_MEALS, null, cv);
            db.close();

            System.out.println("aaaaa");
            finish();
        }else{
            System.out.println("bbbbbbb");
        }
    }

}