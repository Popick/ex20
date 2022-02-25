package com.example.ex20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Intent siUsers,siCompanies,siOrder,siViewOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        siUsers = new Intent(this,users_screen.class);
        siCompanies = new Intent(this,comp_screen.class);
        siOrder = new Intent(this,new_order_activity.class);
        siViewOrder = new Intent(this,order_screen.class);

    }

    public void usersClick(View view) {
        startActivity(siUsers);
    }
    public void companiesClick(View view) {startActivity(siCompanies);}
    public void newOrderClick(View view) {startActivity(siOrder);}
    public void viewOrdersClick(View view) {startActivity(siViewOrder);}
}

