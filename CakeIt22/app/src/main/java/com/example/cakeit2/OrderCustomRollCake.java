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

public class OrderCustomRollCake extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter_CustomRollCakeOrder adapter_customRollCakeOrder;
    ArrayList<CustomRollCake_GlobalVariable> list;

    ImageView back;
    TextView cake, cupcake;
    ImageButton toOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_custom_roll_cake);

        cake = findViewById(R.id.rollcakeTocake);
        cupcake = findViewById(R.id.rollcakeTocupcake);

        back = findViewById(R.id.btn_back);
        toOrder = findViewById(R.id.toOrder);

        // Setting the RecyclerView
        recyclerView = findViewById(R.id.customRollCakeList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter_customRollCakeOrder = new Adapter_CustomRollCakeOrder(this, list);
        recyclerView.setAdapter(adapter_customRollCakeOrder);

        // Fetching data
        FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("My Orders").child("Custom Order").child("Roll Cake").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    CustomRollCake_GlobalVariable customRollCake = dataSnapshot.getValue(CustomRollCake_GlobalVariable.class);
                    list.add(customRollCake);
                }
                adapter_customRollCakeOrder.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderCustomRollCake.this, OrderCustomCake.class);
                startActivity(intent);
            }
        });

        cupcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderCustomRollCake.this, OrderCustomCupCake.class);
                startActivity(intent);
            }
        });

        toOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderCustomRollCake.this, OrdersActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderCustomRollCake.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OrderCustomRollCake.this, OrdersActivity.class);
        startActivity(intent);
    }
}