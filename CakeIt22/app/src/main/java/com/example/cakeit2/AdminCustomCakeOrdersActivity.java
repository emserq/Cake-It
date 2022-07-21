package com.example.cakeit2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminCustomCakeOrdersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter_AdminCustomCakeOrder adapter_adminCustomCakeOrder;
    ArrayList<CustomCake_GlobalVariable> list;

    ImageView back;
    TextView rollcake, cupcake;
    ImageButton toOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_custom_orders);

        rollcake = findViewById(R.id.cakeTorollcake);
        cupcake = findViewById(R.id.cakeTocupcake);

        back = findViewById(R.id.btn_back);

        // Setting the RecyclerView
        recyclerView = findViewById(R.id.customCakeOrderList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter_adminCustomCakeOrder = new Adapter_AdminCustomCakeOrder(this, list);
        recyclerView.setAdapter(adapter_adminCustomCakeOrder);

        // Fetching data
        FirebaseDatabase.getInstance().getReference("Orders").child("Custom Orders").child("Cake").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    CustomCake_GlobalVariable customCake = dataSnapshot.getValue(CustomCake_GlobalVariable.class);
                    list.add(customCake);
                }
                adapter_adminCustomCakeOrder.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        rollcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCustomCakeOrdersActivity.this, AdminCustomRollCakeActivity.class);
                startActivity(intent);
            }
        });

        cupcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCustomCakeOrdersActivity.this, AdminCustomCupCakeOrdersActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCustomCakeOrdersActivity.this, AdminHomeActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminCustomCakeOrdersActivity.this, AdminHomeActivity.class);
        startActivity(intent);
    }
}