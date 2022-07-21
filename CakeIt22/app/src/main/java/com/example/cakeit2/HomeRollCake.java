package com.example.cakeit2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeRollCake extends AppCompatActivity {

    ImageButton map, home, custom, profile;
    CardView cake, cupcake;

    RecyclerView recyclerView;
    Adapter_AddRollCake adapter_addRollCake;
    ArrayList<AddRollCake_GlobalVariable> rollcakelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_roll_cake);

        cake = findViewById(R.id.btn_rollcakeTocake);
        cupcake = findViewById(R.id.btn_rollcakeTocupcake);

        map = findViewById(R.id.homeRollTomap);
        home = findViewById(R.id.homeRollTohome);
        custom = findViewById(R.id.homeRollTocustom);
        profile = findViewById(R.id.homeRollToprofile);

        //-------------------------Functions---------------------//

        cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeRollCake.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        cupcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeRollCake.this, HomeCupCake.class);
                startActivity(intent);
            }
        });

        //--------------------------Bottom Navigation---------------------------//


        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeRollCake.this, MapActivity.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeRollCake.this, HomeRollCake.class);
                startActivity(intent);
            }
        });

        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeRollCake.this, CustomActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeRollCake.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        // Setting the RecyclerView
        recyclerView = findViewById(R.id.rollcakeList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rollcakelist = new ArrayList<>();
        adapter_addRollCake = new Adapter_AddRollCake(this,rollcakelist);
        recyclerView.setAdapter(adapter_addRollCake);

        // Fetching data fro database
        FirebaseDatabase.getInstance().getReference("Collection").child("Roll Cakes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    AddRollCake_GlobalVariable addRollCake_globalVariable = dataSnapshot.getValue(AddRollCake_GlobalVariable.class);
                    rollcakelist.add(addRollCake_globalVariable);
                }
                adapter_addRollCake.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}