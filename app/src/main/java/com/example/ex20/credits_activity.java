package com.example.ex20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
/**
 * @author Etay Sabag <itay45520@gmail.com>
 * @version    1.0
 * @since     25/2/2022
 *  Credits activity
 */
public class credits_activity extends AppCompatActivity {
    Intent siUsers,siCompanies,siOrder,siViewOrder,siHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        siUsers = new Intent(this,users_screen.class);
        siCompanies = new Intent(this,comp_screen.class);
        siOrder = new Intent(this,new_order_activity.class);
        siViewOrder = new Intent(this,order_screen.class);
        siHome = new Intent(this,MainActivity.class);

    }
    /**
     * Creates the menu in the activity.
     *
     * @return Returns True
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    /**
     * The function sends the user to the activity that he chose in the menu.
     *
     * @return Returns True
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        String itemName = item.getTitle().toString();
        switch (itemName) {
            case "Home":
                startActivity(siHome);
                break;
            case "Users":
                startActivity(siUsers);
                break;
            case "Restaurants":
                startActivity(siCompanies);
                break;
            case "Orders":
                startActivity(siViewOrder);
                break;
            case "Credits":
                break;
        }

        return true;
    }
}