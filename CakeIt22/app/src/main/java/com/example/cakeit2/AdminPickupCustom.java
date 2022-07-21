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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdminPickupCustom extends AppCompatActivity {

    ImageView back;

    RecyclerView recyclerView;
    Adapter_AdminPickup adapter_adminPickup;
    ArrayList<CustomCake_GlobalVariable> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pickup_custom_cake);

        back = findViewById(R.id.btn_back);

        // Setting the RecyclerView
        recyclerView = findViewById(R.id.pickupList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter_adminPickup = new Adapter_AdminPickup(this, list);
        recyclerView.setAdapter(adapter_adminPickup);

        // Fetching data from database
        FirebaseDatabase.getInstance().getReference("Orders").child("Pickups").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    CustomCake_GlobalVariable pickup = dataSnapshot.getValue(CustomCake_GlobalVariable.class);
                    list.add(pickup);
                }
                adapter_adminPickup.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminPickupCustom.this, "Error pO", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPickupCustom.this, AdminHomeActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminPickupCustom.this, AdminHomeActivity.class);
        startActivity(intent);
    }
}