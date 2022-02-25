package com.example.ex20;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class users_screen extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;
    SQLiteDatabase db;
    HelperDB hlp;
    Intent newUserIntent, viewUserIntent;
    Cursor crsr;
    ArrayList<String> tbl;
    ArrayList<String> cardIDs;
    ArrayAdapter<String> adp;
    AlertDialog.Builder adb;
    final String[] filterAD = {"Active", "Inactive"};
    final String[] sortAD = {"Card-ID","Name A→Z", "Name Z→A","Company A→Z"};
    String filter="1",sort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ta_screen);
        newUserIntent = new Intent(this, new_user_activity.class);
        viewUserIntent = new Intent(this, view_user_activity.class);
        hlp = new HelperDB(this);



        lv = (ListView) findViewById(R.id.listview);
        lv.setOnItemClickListener(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        update_users(filter,sort);
    }

    @Override
    protected void onResume() {
        super.onResume();
        update_users(filter,sort);
    }

    public void update_users(String filterPar, String sortPar) {
        db = hlp.getWritableDatabase();
        tbl = new ArrayList<>();
        cardIDs = new ArrayList<>();
        if (filterPar != null)
            crsr = db.query(Users.TABLE_USERS, null, Users.ACTIVE + "=?", new String[]{filterPar}, null, null, sortPar);
        else
            crsr = db.query(Users.TABLE_USERS, null, null, null, null, null, sortPar);

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
            cardIDs.add(key+"");
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
        adb = new AlertDialog.Builder(this);
        adb.setTitle("Sort Employees");
        adb.setItems(sortAD, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int pos) {
                if(pos==0){
                    sort = Users.KEY_ID;
                }else if(pos==1){
                    sort = Users.FNAME;
                }else if(pos==2){
                    sort = Users.FNAME+" DESC";
                }else if(pos==3){
                    sort = Users.COMPANY;
                }
                System.out.println("filter: "+filter+", sort: "+sort);
                update_users(filter,sort);
            }
        });
        AlertDialog ad = adb.create();
        ad.show();
    }

    public void filter(View view) {
        String[] arr ={"0","0"};
        adb = new AlertDialog.Builder(this);
        adb.setTitle("Filter Employees");
        adb.setMultiChoiceItems(filterAD, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int pos, boolean isChecked) {
                if (isChecked) arr[pos] = "1";
                else arr[pos] = "0";
            }
        });
        adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if ((arr[0] + arr[1]).equals("01")) {
                    filter = "0";
                } else if ((arr[0] + arr[1]).equals("10")) {
                    filter = "1";
                } else if ((arr[0] + arr[1]).equals("11")) {
                    filter = null;
                }else{
                    filter="3";
                }
                System.out.println("filter: "+filter+", sort: "+sort);
                update_users(filter,sort);
            }
        });
        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog ad = adb.create();
        ad.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        viewUserIntent.putExtra("id", cardIDs.get(position));
        System.out.println(tbl.get(position));


        startActivity(viewUserIntent);
    }
}