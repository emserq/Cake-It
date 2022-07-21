package com.example.cakeit2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminManageCupCakeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter_AdminCupCakeCollection adapter_adminCupCakeCollection;
    ArrayList<AddCupCake_GlobalVariable> list;

    TextView addCupCake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_cup_cake);

        addCupCake = findViewById(R.id.btn_addCupCake);

        addCupCake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminManageCupCakeActivity.this, AddCupCakeActivity.class);
                startActivity(intent);
            }
        });

        // Setting the RecyclerView
        recyclerView = findViewById(R.id.manageCupCakeList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter_adminCupCakeCollection = new Adapter_AdminCupCakeCollection(this, list);
        recyclerView.setAdapter(adapter_adminCupCakeCollection);

        // Fetching data fro database
        FirebaseDatabase.getInstance().getReference("Collection").child("Cup Cakes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    AddCupCake_GlobalVariable addRollCake_globalVariable = dataSnapshot.getValue(AddCupCake_GlobalVariable.class);
                    list.add(addRollCake_globalVariable);
                }
                adapter_adminCupCakeCollection.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminManageCupCakeActivity.this, AdminHomeActivity.class);
        startActivity(intent);
    }

}