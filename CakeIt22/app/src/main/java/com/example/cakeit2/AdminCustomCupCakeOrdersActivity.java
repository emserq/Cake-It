package com.example.cakeit2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminCustomCupCakeOrdersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter_AdminCustomCupCakeOrder adapter_adminCustomCupCakeOrder;
    ArrayList<CustomCupCake_GlobalVariable> list;

    ImageView back;
    TextView cake, rollcake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_custom_cup_cake_orders);

        cake = findViewById(R.id.cupcakeTocake);
        rollcake = findViewById(R.id.cupcakeTorollcake);

        back = findViewById(R.id.btn_back);

        // Setting the RecyclerView
        recyclerView = findViewById(R.id.customCupCakeOrderList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter_adminCustomCupCakeOrder = new Adapter_AdminCustomCupCakeOrder(this, list);
        recyclerView.setAdapter(adapter_adminCustomCupCakeOrder);

        // Fetching data
        FirebaseDatabase.getInstance().getReference("Orders").child("Custom Orders").child("Cup Cake").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    CustomCupCake_GlobalVariable customCupCake = dataSnapshot.getValue(CustomCupCake_GlobalVariable.class);
                    list.add(customCupCake);
                }
                adapter_adminCustomCupCakeOrder.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCustomCupCakeOrdersActivity.this, AdminCustomCakeOrdersActivity.class);
                startActivity(intent);
            }
        });

        rollcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCustomCupCakeOrdersActivity.this, AdminCustomRollCakeActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCustomCupCakeOrdersActivity.this, AdminHomeActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminCustomCupCakeOrdersActivity.this, AdminHomeActivity.class);
        startActivity(intent);
    }

}