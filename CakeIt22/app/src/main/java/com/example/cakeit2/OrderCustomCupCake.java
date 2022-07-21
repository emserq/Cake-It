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

public class OrderCustomCupCake extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter_CustomCupCakeOrder adapter_customCupCakeOrder;
    ArrayList<CustomCupCake_GlobalVariable> list;

    ImageView back;
    TextView cake, rollcake;
    ImageButton toOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_custom_cup_cake);

        cake = findViewById(R.id.cupcakeTocake);
        rollcake = findViewById(R.id.cupcakeTorollcake);

        back = findViewById(R.id.btn_back);
        toOrder = findViewById(R.id.toOrder);

        // Setting the RecyclerView
        recyclerView =  findViewById(R.id.customCupCakeList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter_customCupCakeOrder = new Adapter_CustomCupCakeOrder(this, list);
        recyclerView.setAdapter(adapter_customCupCakeOrder);

        // Fetching data
        FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("My Orders").child("Custom Order").child("Cup Cake").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    CustomCupCake_GlobalVariable customCupCake = dataSnapshot.getValue(CustomCupCake_GlobalVariable.class);
                    list.add(customCupCake);
                }
                adapter_customCupCakeOrder.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderCustomCupCake.this, OrderCustomCake.class);
                startActivity(intent);
            }
        });

        rollcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderCustomCupCake.this, OrderCustomRollCake.class);
                startActivity(intent);
            }
        });

        toOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderCustomCupCake.this, OrdersActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderCustomCupCake.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OrderCustomCupCake.this, OrdersActivity.class);
        startActivity(intent);
    }

}