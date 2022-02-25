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

public class comp_screen extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;
    SQLiteDatabase db;
    HelperDB hlp;
    ImageButton addBtn;
    Intent newCompanyIntent, viewCompanyIntent;
    Cursor crsr;
    ArrayList<String> tbl;
    ArrayList<String> cardIDs;
    ArrayAdapter<String> adp;
    AlertDialog.Builder adb;
    final String[] filterAD = {"Active", "Inactive"};
    final String[] sortAD = {"Key ID","Name A→Z", "Name Z→A","Tax Number 0→9"};
    String filter="1",sort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ta_screen);
        newCompanyIntent = new Intent(this, new_company_activity.class);
        viewCompanyIntent = new Intent(this, view_company_activity.class);
        hlp = new HelperDB(this);
        addBtn = (ImageButton) findViewById(R.id.addBtn);
        addBtn.setImageResource(R.drawable.addcomp);


        lv = (ListView) findViewById(R.id.listview);
        lv.setOnItemClickListener(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        update_comps(filter,sort);
    }

    @Override
    protected void onResume() {
        super.onResume();
        update_comps(filter,sort);
    }

    public void update_comps(String filterPar, String sortPar) {
        db = hlp.getWritableDatabase();
        tbl = new ArrayList<>();
        cardIDs = new ArrayList<>();
        if (filterPar != null)
            crsr = db.query(Companies.TABLE_COMPANIES, null, Companies.ACTIVE + "=?", new String[]{filterPar}, null, null, sortPar);
        else
            crsr = db.query(Companies.TABLE_COMPANIES, null, null, null, null, null, sortPar);

        int col1 = crsr.getColumnIndex(Companies.KEY_ID);
        int col2 = crsr.getColumnIndex(Companies.NAME);

        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            int key = crsr.getInt(col1);
            String name = crsr.getString(col2);
            String tmp = "" + key + ". " + name;
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
        startActivity(newCompanyIntent);
    }

    public void back(View view) {
        finish();
    }

    public void sort(View view) {
        adb = new AlertDialog.Builder(this);
        adb.setTitle("Sort Companies");
        adb.setItems(sortAD, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int pos) {
                if(pos==0){
                    sort = Companies.KEY_ID;
                }else if(pos==1){
                    sort = Companies.NAME;
                }else if(pos==2){
                    sort = Companies.NAME+" DESC";
                }else if(pos==3){
                    sort = Companies.TAX_ID;
                }
                System.out.println("filter: "+filter+", sort: "+sort);
                update_comps(filter,sort);
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
                update_comps(filter,sort);
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
        viewCompanyIntent.putExtra("id", cardIDs.get(position));
//        System.out.println(tbl.get(position));
        startActivity(viewCompanyIntent);
    }


}