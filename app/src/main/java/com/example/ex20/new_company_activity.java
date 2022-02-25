package com.example.ex20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class new_company_activity extends AppCompatActivity {

    SQLiteDatabase db;
    HelperDB hlp;

    EditText nameET, taxET, mPhoneET, sPhoneET;
    String id, a;
    int allValid;
    ContentValues cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_company);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();


        nameET = (EditText) findViewById(R.id.inputappetizer);
        taxET = (EditText) findViewById(R.id.inputtax);
        mPhoneET = (EditText) findViewById(R.id.inputmphone);
        sPhoneET = (EditText) findViewById(R.id.inputsphone);

        cv = new ContentValues();

    }

    public void add(View view) {
        allValid = 0;
        if (nameET.getText().length() == 0) {
            nameET.setError("Can't Be Empty");
        } else {
            allValid++;
        }
        if (taxET.getText().length() == 10) {
            taxET.setError("Can't Be Empty");
        } else {
            allValid++;
        }
        if (!(mPhoneET.getText().length() == 10)) {
            mPhoneET.setError("Must Be 10 Digits Long");
        } else {
            allValid++;
        }if (!(sPhoneET.getText().length() == 10)) {
            sPhoneET.setError("Must Be 10 Digits Long");
        } else {
            allValid++;
        }
        if (allValid == 4) {

            cv.put(Companies.TAX_ID, taxET.getText().toString());
            cv.put(Companies.NAME, nameET.getText().toString());
            cv.put(Companies.MAIN_PHONE, mPhoneET.getText().toString());
            cv.put(Companies.SECONDARY_PHONE, sPhoneET.getText().toString());
            cv.put(Companies.ACTIVE, "1");

            db = hlp.getWritableDatabase();
            db.insert(Companies.TABLE_COMPANIES, null, cv);
            db.close();

            Toast.makeText(this, "Saved Successfully!", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    public void back(View view) {
        finish();
    }

}