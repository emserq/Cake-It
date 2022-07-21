package com.example.cakeit2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class AdminTotalSales extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter_TotalSales adapter_totalSales;
    ArrayList<CustomCake_GlobalVariable> list;

    TextView total, clear;
    ImageView home;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_total_sales);

        total = findViewById(R.id.tvTotalSales);
        clear = findViewById(R.id.tvClear);
        home = findViewById(R.id.btn_home);

        builder = new AlertDialog.Builder(this);

        // Setting the RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter_totalSales = new Adapter_TotalSales(this, list);
        recyclerView.setAdapter(adapter_totalSales);

        // Fetching data from database
        FirebaseDatabase.getInstance().getReference("Total Sales").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    CustomCake_GlobalVariable pickup = dataSnapshot.getValue(CustomCake_GlobalVariable.class);
                    list.add(pickup);
                }
                adapter_totalSales.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminTotalSales.this, "Error pO", Toast.LENGTH_SHORT).show();
            }
        });

        // Getting all of the items price for the total
        FirebaseDatabase.getInstance().getReference("Total Sales")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        int sum = 0;

                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                            Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                            Object price = map.get("price");
                            int pValue = Integer.parseInt(String.valueOf(price));
                            sum += pValue;

                            total.setText(String.valueOf(sum));

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder.setTitle("Sales History")
                        .setMessage("Are you sure you want to clear?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseDatabase.getInstance().getReference("Total Sales").removeValue();
                                Toast.makeText(AdminTotalSales.this, "Sales History has been cleared", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AdminTotalSales.this, AdminTotalSales.class);
                                startActivity(intent);

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
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminTotalSales.this, AdminHomeActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminTotalSales.this, AdminHomeActivity.class);
        startActivity(intent);
    }
}