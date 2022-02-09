package com.example.ex20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class users_screen extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;
    SQLiteDatabase db;
    HelperDB hlp;
    Intent newUserIntent,viewUserIntent;
    Cursor crsr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_screen);
        newUserIntent = new Intent(this,new_user_activity.class);
        viewUserIntent = new Intent(this,view_user_activity.class);
        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();


        lv=(ListView) findViewById(R.id.listview);
        lv.setOnItemClickListener(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        update_users();
    }

    @Override
    protected void onResume() {
        super.onResume();
        update_users();
    }

    public void update_users() {
        ArrayList<String> tbl = new ArrayList<>();
        ArrayAdapter<String> adp;
        db = hlp.getWritableDatabase();
        tbl = new ArrayList<>();

        crsr = db.query(Users.TABLE_USERS, null, null, null, null, null, null);
        int col1 = crsr.getColumnIndex(Users.KEY_ID);
        int col2 = crsr.getColumnIndex(Users.FNAME);
        int col3 = crsr.getColumnIndex(Users.LNAME);

        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            int key = crsr.getInt(col1);
            String fname = crsr.getString(col2);
            String lname = crsr.getString(col3);
            String tmp = "" + key + ". " + fname + " " + lname;
            tbl.add(tmp);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tbl);
        lv.setAdapter(adp);
    }

    public void addPerson(View view) {
        startActivity(newUserIntent);
    }

    public void back(View view) {
        finish();
    }

    public void sort(View view) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        viewUserIntent.putExtra("id",position+1);
        startActivity(viewUserIntent);
    }
}