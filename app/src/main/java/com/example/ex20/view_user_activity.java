package com.example.ex20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class view_user_activity extends AppCompatActivity {
    Intent gi;
    TextView idTV, fnameTV, lnameTV, cardTV, companyTV, phoneTV;
    Button greenBtn,redBtn;
    SQLiteDatabase db;
    HelperDB hlp;
    Intent newUserIntent,viewUserIntent;
    Cursor crsr;
    int userKeyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        gi = getIntent();
        userKeyId = gi.getIntExtra("id",0);

        redBtn = (Button) findViewById(R.id.redButton);
        greenBtn = (Button) findViewById(R.id.greenButton);

        idTV = (TextView) findViewById(R.id.view_id);
        fnameTV = (TextView) findViewById(R.id.view_fname);
        lnameTV = (TextView) findViewById(R.id.view_lname);
        cardTV = (TextView) findViewById(R.id.view_card);
        companyTV = (TextView) findViewById(R.id.view_company);
        phoneTV = (TextView) findViewById(R.id.view_phonenumber);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();

        ArrayList<String> tbl = new ArrayList<>();
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,new String[]{});
        db=hlp.getReadableDatabase();

        String[] columns = null;
        String selection = Users.KEY_ID + "=?";
        String[] selectionArgs = {""+userKeyId};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        crsr = db.query(Users.TABLE_USERS, null, selection, selectionArgs, null, null, null);
        int col1 = crsr.getColumnIndex(Users.FNAME);
        int col2 = crsr.getColumnIndex(Users.LNAME);
        int col3 = crsr.getColumnIndex(Users.COMPANY);
        int col4 = crsr.getColumnIndex(Users.CARD_ID);
        int col5 = crsr.getColumnIndex(Users.ID);
        int col6 = crsr.getColumnIndex(Users.PHONE);

        crsr.moveToFirst();
        fnameTV.setText(crsr.getString(col1));
        lnameTV.setText(crsr.getString(col2));
        companyTV.setText(crsr.getString(col3));
        cardTV.setText(crsr.getString(col4));
        idTV.setText(crsr.getString(col5));
        phoneTV.setText(crsr.getString(col6));

        crsr.close();
        db.close();

    }

    public void back(View view) {
        finish();
    }

    public void edit(View view) {

    }
}