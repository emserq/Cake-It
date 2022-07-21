package com.example.cakeit2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeCupCake extends AppCompatActivity {

    private ImageView map, home, customcake, profile;
    CardView cake, rollcake;

    RecyclerView recyclerView;
    Adapter_AddCupCake adapter_addCupCake;
    ArrayList<AddCupCake_GlobalVariable> cupcakelist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cup_cake);

        cake = findViewById(R.id.btn_cupcakeTocake);
        rollcake = findViewById(R.id.btn_cupcakeTorollcake);

        map = findViewById(R.id.homeCupTomap);
        home = findViewById(R.id.homeCupTohomeCup);
        customcake = findViewById(R.id.homeCupTocustom);
        profile = findViewById(R.id.homeCupToprofile);

        //--------------------------Functions---------------------------//

        cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeCupCake.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        rollcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeCupCake.this, HomeRollCake.class);
                startActivity(intent);
            }
        });

        //--------------------------Bottom Navigation---------------------------//

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeCupCake.this, HomeCupCake.class);
                startActivity(intent);
            }
        });

        customcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeCupCake.this, CustomActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeCupCake.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        // Setting the RecyclerView
        recyclerView = findViewById(R.id.cupcakeList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cupcakelist = new ArrayList<>();
        adapter_addCupCake = new Adapter_AddCupCake(this,cupcakelist);
        recyclerView.setAdapter(adapter_addCupCake);

        // Fetching data fro database
        FirebaseDatabase.getInstance().getReference("Collection").child("Cup Cakes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    AddCupCake_GlobalVariable addCupCake_globalVariable = dataSnapshot.getValue(AddCupCake_GlobalVariable.class);
                    cupcakelist.add(addCupCake_globalVariable);
                }
                adapter_addCupCake.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}