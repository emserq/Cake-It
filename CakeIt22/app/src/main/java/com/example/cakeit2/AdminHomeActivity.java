package com.example.cakeit2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class AdminHomeActivity extends AppCompatActivity {

    LinearLayout orders, sales, customorders, topickup, mCake, mRollcake, mCupcake;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        builder = new AlertDialog.Builder(this);

        orders = findViewById(R.id.btn_Orders);
        sales = findViewById(R.id.btn_totalSales);
        customorders = findViewById(R.id.btn_CustomOrders);
        topickup = findViewById(R.id.btn_ToPickup);

        mCake = findViewById(R.id.btn_manageCake);
        mRollcake = findViewById(R.id.btn_manageRollCakes);
        mCupcake = findViewById(R.id.btn_manageCupcake);

        //-------------Functions-------------------//

        customorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminCustomCakeOrdersActivity.class);
                startActivity(intent);
            }
        });


        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminOrdersActivity.class);
                startActivity(intent);
            }
        });

        topickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminPickupCustom.class);
                startActivity(intent);
            }
        });

        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminTotalSales.class);
                startActivity(intent);
            }
        });

        //---------------Store-------------------//

        mCake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminManageCakeActivity.class);
                startActivity(intent);
            }
        });

        mRollcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminManageRollCakeActivity.class);
                startActivity(intent);
            }
        });

        mCupcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminManageCupCakeActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {

        builder.setTitle("Admin")
                .setMessage("Are you sure you want to logout?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(AdminHomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();

    }
}