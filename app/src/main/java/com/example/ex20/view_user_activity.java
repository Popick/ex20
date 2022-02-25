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

public class view_user_activity extends AppCompatActivity {
    Intent gi;
    TextView idTV, fnameTV, lnameTV, companyTV, phoneTV;
    Button greenBtn, redBtn, orangeBtn;
    SQLiteDatabase db;
    HelperDB hlp;
    Intent newUserIntent, viewUserIntent;
    Cursor crsr;
    ContentValues cv;
    String userKeyId = "";
    AlertDialog.Builder adb;
    boolean isActive = true;
    int col1, col2, col3, col4, col5,col6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        gi = getIntent();
        userKeyId = gi.getStringExtra("id");


        redBtn = (Button) findViewById(R.id.redButton);
        greenBtn = (Button) findViewById(R.id.greenButton);
        orangeBtn = (Button) findViewById(R.id.orangeButton);
        idTV = (TextView) findViewById(R.id.inputid);
        fnameTV = (TextView) findViewById(R.id.inputfname);
        lnameTV = (TextView) findViewById(R.id.inputlname);
//        cardTV = (TextView) findViewById(R.id.view_card);
        companyTV = (TextView) findViewById(R.id.inputcompany);
        phoneTV = (TextView) findViewById(R.id.inputphonenumber);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();

        ArrayList<String> tbl = new ArrayList<>();
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, new String[]{});

        fillUser();
        cv = new ContentValues();

        if (!isActive){
            orangeBtn.setText("Restore Employee");
            orangeBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.restore, 0);
        }
    }

    public void fillUser(){
        String selection = Users.KEY_ID + "=?";
        String[] selectionArgs = {userKeyId};
        System.out.println(userKeyId);
        db = hlp.getReadableDatabase();
        crsr = db.query(Users.TABLE_USERS, null, selection, selectionArgs, null, null, null);
        col1 = crsr.getColumnIndex(Users.FNAME);
        col2 = crsr.getColumnIndex(Users.LNAME);
        col3 = crsr.getColumnIndex(Users.COMPANY);
        col4 = crsr.getColumnIndex(Users.ID);
        col5 = crsr.getColumnIndex(Users.PHONE);
        col6 = crsr.getColumnIndex(Users.ACTIVE);

        crsr.moveToFirst();
        fnameTV.setText(crsr.getString(col1));
        lnameTV.setText(crsr.getString(col2));
        companyTV.setText(crsr.getString(col3));
        idTV.setText(crsr.getString(col4));
        phoneTV.setText(crsr.getString(col5));
        if(crsr.getString(col6).equals("1")) isActive = true;
        else isActive = false;
        db.close();
        crsr.close();
    }

    public void back(View view) {
        if (redBtn.getText().toString().equals("BACK")) {
            finish();
        } else if (redBtn.getText().toString().equals("CANCEL")) {
            greenBtn.setText("EDIT");
            redBtn.setText("BACK");
            idTV.setEnabled(false);
            fnameTV.setEnabled(false);
            lnameTV.setEnabled(false);
            companyTV.setEnabled(false);
            phoneTV.setEnabled(false);
            orangeBtn.setEnabled(false);
            fillUser();
        }
    }


    public void editsave(View view) {
        if (greenBtn.getText().toString().equals("EDIT")) {
            greenBtn.setText("SAVE");
            redBtn.setText("CANCEL");
            idTV.setEnabled(true);
            fnameTV.setEnabled(true);
            lnameTV.setEnabled(true);
            companyTV.setEnabled(true);
            phoneTV.setEnabled(true);
            orangeBtn.setEnabled(true);
        } else if (greenBtn.getText().toString().equals("SAVE")) {
            ContentValues cv = new ContentValues();
            db = hlp.getWritableDatabase();
            cv.put(Users.FNAME, fnameTV.getText().toString());
            cv.put(Users.LNAME, lnameTV.getText().toString());
            cv.put(Users.COMPANY, companyTV.getText().toString());
            cv.put(Users.ID, idTV.getText().toString());
            cv.put(Users.PHONE, phoneTV.getText().toString());
//            db.update(Users.TABLE_USERS,cv,Users.KEY_ID, new String[]{userKeyId+""});
            db.update(Users.TABLE_USERS, cv, Users.KEY_ID + "=?", new String[]{userKeyId});

            db.close();

            Toast.makeText(this, "Saved Successfully!", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void delete(View view) {
        adb = new AlertDialog.Builder(this);
        adb.setTitle("Are You Sure?");
        if(isActive) adb.setMessage("Are you sure you want to delete the employee?");
        else adb.setMessage("Are you sure you want to restore the employee?");
        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                ContentValues cv = new ContentValues();
                if(isActive) cv.put(Users.ACTIVE, 0);
                else cv.put(Users.ACTIVE, 1);
                db = hlp.getWritableDatabase();
                db.update(Users.TABLE_USERS, cv, Users.KEY_ID + "=?", new String[]{userKeyId});
                db.close();

                Toast.makeText(view_user_activity.this, "Deleted Successfully!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });
        AlertDialog ad = adb.create();
        ad.show();
    }
}