package com.example.ex20;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class view_order_activity extends AppCompatActivity {
    Intent gi;
    TextView appatizerTV,  mainTV, sideTV, dessertTV, drinksTV, userTV, restTV;
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    String userKeyId = "",restID,userID;
    AlertDialog.Builder adb;
    int col1, col2, col3, col4, col5,col6;
    Intent viewCompanyIntent, viewUserIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        gi = getIntent();
        userKeyId = gi.getStringExtra("id");

        viewUserIntent = new Intent(this, view_user_activity.class);
        viewCompanyIntent = new Intent(this, view_company_activity.class);


        appatizerTV = (TextView) findViewById(R.id.appatizerTV);
        mainTV = (TextView) findViewById(R.id.mainTV);
        sideTV = (TextView) findViewById(R.id.sideTV);
        dessertTV = (TextView) findViewById(R.id.dessertTV);
        drinksTV = (TextView) findViewById(R.id.drinksTV);
        userTV = (TextView) findViewById(R.id.userTV);
        restTV = (TextView) findViewById(R.id.restTV);

        hlp = new HelperDB(this);


        fillOrder();
    }



        public void fillOrder(){
            String selection = Meals.KEY_ID + "=?";
            String[] selectionArgs = {userKeyId};
            System.out.println(userKeyId);
            db = hlp.getReadableDatabase();
            crsr = db.query(Meals.TABLE_MEALS, null, selection, selectionArgs, null, null, null);
            col1 = crsr.getColumnIndex(Meals.APPETIZER);
            col2 = crsr.getColumnIndex(Meals.MAIN);
            col3 = crsr.getColumnIndex(Meals.SIDE);
            col4 = crsr.getColumnIndex(Meals.DESSERT);
            col5 = crsr.getColumnIndex(Meals.DRINK);

            crsr.moveToFirst();
            appatizerTV.setText("Appatizer: \n" + crsr.getString(col1));
            mainTV.setText("Main Dish: \n" + crsr.getString(col2));
            sideTV.setText("Side Meal: \n" + crsr.getString(col3));
            dessertTV.setText("Dessert: \n" + crsr.getString(col4));
            drinksTV.setText("Drinks: \n" + crsr.getString(col5));

            db.close();
            crsr.close();

            selection = Orders.KEY_ID + "=?";
            db = hlp.getReadableDatabase();
            crsr = db.query(Orders.TABLE_ORDERS, null, selection, selectionArgs, null, null, null);
            col1 = crsr.getColumnIndex(Orders.USER_NAME);
            col2 = crsr.getColumnIndex(Orders.USER_ID);
            col3 = crsr.getColumnIndex(Orders.RESTAURANT_NAME);
            col4 = crsr.getColumnIndex(Orders.RESTAURANT_ID);

            crsr.moveToFirst();
            userTV.setText("Worker Name: \n" + crsr.getString(col1));
            restTV.setText("Restaurant Name: \n" + crsr.getString(col3));

            viewUserIntent.putExtra("id", crsr.getString(col2));
            viewCompanyIntent.putExtra("id", crsr.getString(col4));

            db.close();
            crsr.close();



        }

        public void back(View view) {
            finish();
        }

    public void goUser(View view) {

        startActivity(viewUserIntent);


    }
    public void goRest(View view) {
        startActivity(viewCompanyIntent);

    }
}