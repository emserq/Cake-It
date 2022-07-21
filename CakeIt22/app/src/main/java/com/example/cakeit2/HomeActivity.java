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

public class HomeActivity extends AppCompatActivity {

    private ImageView map, home, customcake, profile;
    CardView rollcake, cupcake;

    RecyclerView recyclerView;
    Adapter_AddCake adapter_addCake;
    ArrayList<AddCake_GlobalVariable> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rollcake = findViewById(R.id.btn_cakeTorollcake);
        cupcake = findViewById(R.id.btn_cakeTocupcake);

        map = findViewById(R.id.homeTomap);
        home = findViewById(R.id.homeTohome);
        customcake = findViewById(R.id.homeTocustom);
        profile = findViewById(R.id.homeToprofile);

        //--------------------------Functions---------------------------//

        rollcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, HomeRollCake.class);
                startActivity(intent);
            }
        });

        cupcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, HomeCupCake.class);
                startActivity(intent);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });


        //--------------------------Bottom Navigation---------------------------//

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        customcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CustomActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        // Setting the RecyclerView
        recyclerView = findViewById(R.id.cakeList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter_addCake = new Adapter_AddCake(this,list);
        recyclerView.setAdapter(adapter_addCake);

        // Fetching data fro database
        FirebaseDatabase.getInstance().getReference("Collection").child("Cakes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    AddCake_GlobalVariable addCake_globalVariable = dataSnapshot.getValue(AddCake_GlobalVariable.class);
                    list.add(addCake_globalVariable);
                }
                adapter_addCake.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}