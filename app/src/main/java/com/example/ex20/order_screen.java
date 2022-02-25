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
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class order_screen extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;
    SQLiteDatabase db;
    HelperDB hlp;
    Intent viewOrderIntent;
    Cursor crsr;
    ArrayList<String> tbl;
    ArrayList<String> cardIDs;
    ArrayAdapter<String> adp;
    AlertDialog.Builder adb;
    final String[] sortAD = {"Date", "Name A→Z", "Restaurant A→Z"};
    String filter = "1", sort;
    ImageButton fltrBtn,addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ta_screen);

        viewOrderIntent = new Intent(this, view_order_activity.class);
        hlp = new HelperDB(this);


        lv = (ListView) findViewById(R.id.listview);
        fltrBtn = (ImageButton) findViewById(R.id.filterBtn);
        addBtn = (ImageButton) findViewById(R.id.addBtn);
        fltrBtn.setVisibility(View.GONE);
        addBtn.setVisibility(View.GONE);
        lv.setOnItemClickListener(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        update_Orders(filter, sort);
    }

    @Override
    protected void onResume() {
        super.onResume();
        update_Orders(filter, sort);
    }

    public void update_Orders(String filterPar, String sortPar) {
        db = hlp.getWritableDatabase();
        tbl = new ArrayList<>();
        cardIDs = new ArrayList<>();
        crsr = db.query(Orders.TABLE_ORDERS, null, null, null, null, null, sortPar);

        int col1 = crsr.getColumnIndex(Orders.KEY_ID);
        int col2 = crsr.getColumnIndex(Orders.RESTAURANT_NAME);
        int col3 = crsr.getColumnIndex(Orders.USER_NAME);
        int col4 = crsr.getColumnIndex(Orders.DATE);

        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            int key = crsr.getInt(col1);
            String rName = crsr.getString(col2);
            String uName = crsr.getString(col3);
            String date = crsr.getString(col4);
            String tmp = "" + key + ". User: " + uName + ", Shop: " + rName + ", At " + date;
            tbl.add(tmp);
            cardIDs.add(key + "");
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tbl);
        lv.setAdapter(adp);
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
                if (pos == 0) {
                    sort = Orders.KEY_ID;
                } else if (pos == 1) {
                    sort = Orders.USER_NAME;
                } else if (pos == 2) {
                    sort = Orders.RESTAURANT_NAME;
                }
                System.out.println("filter: " + filter + ", sort: " + sort);
                update_Orders(filter, sort);
            }
        });
        AlertDialog ad = adb.create();
        ad.show();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        viewOrderIntent.putExtra("id", cardIDs.get(position));
        System.out.println(tbl.get(position));

        startActivity(viewOrderIntent);
    }
}
