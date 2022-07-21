package com.example.cakeit2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminOrdersActivity extends AppCompatActivity {

    ImageView back;

    RecyclerView recyclerView;
    Adapter_AdminOrders adapter_adminOrders;
    ArrayList<OrderCakeID_GlobalVariable> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);

        back = findViewById(R.id.btn_back);

        // Setting the RecyclerView
        recyclerView = findViewById(R.id.orderList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter_adminOrders = new Adapter_AdminOrders(this,list);
        recyclerView.setAdapter(adapter_adminOrders);

        // Fetching data from database
        FirebaseDatabase.getInstance().getReference("Orders").child("Normal Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    OrderCakeID_GlobalVariable orderCake = dataSnapshot.getValue(OrderCakeID_GlobalVariable.class);
                    list.add(orderCake);
                }
                adapter_adminOrders.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminOrdersActivity.this, "Error pO", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminOrdersActivity.this, AdminHomeActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminOrdersActivity.this, AdminHomeActivity.class);
        startActivity(intent);
    }
}