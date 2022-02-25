package com.example.ex20;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class new_order_second_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Intent gi = getIntent();
    Spinner sp;
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    ArrayList<String> tbl;
    ArrayList<String> restIDlist;
    ArrayAdapter<String> adp;
    EditText userKeyIdET;
    String user_order_key_id,user_order_name,restaurant_order_key_id,restaurant_order_name;
    int col1,col2;
    ContentValues cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_second);

        sp = (Spinner) findViewById(R.id.spinnerComp);
        userKeyIdET = (EditText) findViewById(R.id.userKeyIdInput);

        hlp = new HelperDB(this);

        sp.setOnItemSelectedListener(this);

        db = hlp.getWritableDatabase();
        tbl = new ArrayList<>();
        restIDlist = new ArrayList<>();
        tbl.add("Choose Restaurant:");
        restIDlist.add("0");
        crsr = db.query(Companies.TABLE_COMPANIES, null, Companies.ACTIVE + "=?", new String[]{"1"}, null, null, null);

        col1 = crsr.getColumnIndex(Companies.KEY_ID);
        col2 = crsr.getColumnIndex(Companies.NAME);

        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            int key = crsr.getInt(col1);
            String name = crsr.getString(col2);
            String tmp = "" + key + ". " + name;
            tbl.add(tmp);
            restIDlist.add(key+"");
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tbl);
        sp.setAdapter(adp);

        cv = new ContentValues();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void finish(View view) {
        db = hlp.getWritableDatabase();
        crsr = db.query(Users.TABLE_USERS, null, Users.KEY_ID + "=?", new String[]{userKeyIdET.getText().toString()}, null, null, null);
        int col1 = crsr.getColumnIndex(Users.ACTIVE);
        crsr.moveToFirst();
        try {
            if (crsr.getString(col1).equals("0")){
                Toast.makeText(this,"User Don't work", Toast.LENGTH_LONG).show();
            }else{
                user_order_key_id=userKeyIdET.getText().toString();
            }
        }catch (Exception e) {
            Toast.makeText(this, "User Don't exists", Toast.LENGTH_LONG).show();
        }
        crsr.close();
        db.close();

        if(user_order_key_id!=null && restaurant_order_key_id!=null) {

            db = hlp.getWritableDatabase();
            crsr = db.query(Users.TABLE_USERS, null, Users.KEY_ID + "=?", new String[]{userKeyIdET.getText().toString()}, null, null, null);
            col1 = crsr.getColumnIndex(Users.FNAME);
            crsr.moveToFirst();
            user_order_name = crsr.getString(col1);
            crsr.close();
            db.close();


            db = hlp.getWritableDatabase();
            crsr = db.query(Companies.TABLE_COMPANIES, null, Companies.KEY_ID + "=?", new String[]{restaurant_order_key_id}, null, null, null);
            col1 = crsr.getColumnIndex(Companies.NAME);
            crsr.moveToFirst();
            restaurant_order_name = crsr.getString(col1);
            crsr.close();
            db.close();

            db = hlp.getWritableDatabase();
            cv.put(Orders.USER_ID, user_order_key_id);
            cv.put(Orders.USER_NAME, user_order_name);
            cv.put(Orders.RESTAURANT_ID, restaurant_order_key_id);
            cv.put(Orders.RESTAURANT_NAME, restaurant_order_name);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
            LocalDateTime now = LocalDateTime.now();
            cv.put(Orders.DATE,dtf.format(now));

            db = hlp.getWritableDatabase();
            db.insert(Orders.TABLE_ORDERS, null, cv);
            db.close();

            setResult(RESULT_OK, gi);
            finish();
        }
        else{
            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
        }
    }
    public void back(View view) {
        setResult(RESULT_CANCELED,gi);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(i==0){
            restaurant_order_key_id =null;
            restaurant_order_name = null;
        }else{
            restaurant_order_key_id = restIDlist.get(i);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}