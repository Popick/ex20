package com.example.ex20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class new_user_activity extends AppCompatActivity {
    SQLiteDatabase db;
    HelperDB hlp;

    EditText idET, fnameET, lnameET, cardET, companyET, phoneET;
    String id, a;
    int sum = 0, bikoret, num, allValid;
    ContentValues cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();


        idET = (EditText) findViewById(R.id.inputid);
        fnameET = (EditText) findViewById(R.id.inputfname);
        lnameET = (EditText) findViewById(R.id.inputlname);
        cardET = (EditText) findViewById(R.id.inputcard);
        companyET = (EditText) findViewById(R.id.inputcompany);
        phoneET = (EditText) findViewById(R.id.inputphonenumber);

        cv = new ContentValues();

    }

    public void add(View view) {
        allValid = 0;
        if (fnameET.getText().length() == 0) {
            fnameET.setError("Can't Be Empty");
        } else {
            allValid++;
        }
        if (lnameET.getText().length() == 0) {
            lnameET.setError("Can't Be Empty");
        } else {
            allValid++;
        }
        if (companyET.getText().length() == 0) {
            companyET.setError("Can't Be Empty");
        } else {
            allValid++;
        }
        if (!(cardET.getText().length() == 6)) {
            cardET.setError("Must Be 6 Digits Long");
        } else {
            allValid++;
        }
        if (!idVerification()) {
            idET.setError("Invalid ID");
        } else {
            allValid++;
        }
        if (!(phoneET.getText().length() == 10)) {
            phoneET.setError("Must Be 10 Digits Long");
        } else {
            allValid++;
        }
        if (allValid == 6) {

            cv.put(Users.FNAME, fnameET.getText().toString());
            cv.put(Users.LNAME, lnameET.getText().toString());
            cv.put(Users.COMPANY, companyET.getText().toString());
            cv.put(Users.CARD_ID, cardET.getText().toString());
            cv.put(Users.ID, idET.getText().toString());
            cv.put(Users.PHONE, phoneET.getText().toString());

            db = hlp.getWritableDatabase();
            db.insert(Users.TABLE_USERS, null, cv);
            db.close();

            Toast.makeText(this, "Saved Successfully!", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    public void back(View view) {
        finish();
    }


    public boolean idVerification() {
        sum = 0;

        id = idET.getText().toString();

        if (id.length() == 9) {
            for (int i = 0; i < 8; i++) {
                if (i % 2 == 0) {
                    num = Character.getNumericValue(id.charAt(i));
                    sum = sum + num;
                } else {
                    num = Character.getNumericValue(id.charAt(i));
                    if ((num * 2) > 10) {
                        a = Integer.toString(num * 2);
                        for (int j = 0; j < 2; j++) {
                            num = Character.getNumericValue(a.charAt(j));
                            sum = sum + num;
                        }
                    } else
                        sum = sum + (num * 2);
                }
            }
            bikoret = Character.getNumericValue(id.charAt(8));

            if ((sum + bikoret) % 10 == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}