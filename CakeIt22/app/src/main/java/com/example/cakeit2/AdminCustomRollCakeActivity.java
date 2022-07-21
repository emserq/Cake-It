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

public class AdminCustomRollCakeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter_AdminCustomRollCakeOrder adapter_adminCustomRollCakeOrder;
    ArrayList<CustomRollCake_GlobalVariable> list;

    ImageView back;
    TextView cake, cupcake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_custom_roll_cake);

        cake = findViewById(R.id.rollcakeTocake);
        cupcake = findViewById(R.id.rollcakeTocupcake);

        back = findViewById(R.id.btn_back);

        // Setting the RecyclerView
        recyclerView = findViewById(R.id.customRollCakeOrderList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter_adminCustomRollCakeOrder = new Adapter_AdminCustomRollCakeOrder(this, list);
        recyclerView.setAdapter(adapter_adminCustomRollCakeOrder);

        // Fetching data
        FirebaseDatabase.getInstance().getReference("Orders").child("Custom Orders").child("Roll Cake").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    CustomRollCake_GlobalVariable customRollCake = dataSnapshot.getValue(CustomRollCake_GlobalVariable.class);
                    list.add(customRollCake);
                }
                adapter_adminCustomRollCakeOrder.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


       cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCustomRollCakeActivity.this, AdminCustomCakeOrdersActivity.class);
                startActivity(intent);
            }
        });

       cupcake.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(AdminCustomRollCakeActivity.this, AdminCustomCupCakeOrdersActivity.class);
               startActivity(intent);
           }
       });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCustomRollCakeActivity.this, AdminHomeActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminCustomRollCakeActivity.this, AdminHomeActivity.class);
        startActivity(intent);
    }

}