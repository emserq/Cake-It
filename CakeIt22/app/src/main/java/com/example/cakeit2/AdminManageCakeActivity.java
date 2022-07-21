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

public class AdminManageCakeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter_AdminCakeCollection adapter_adminCakeCollection;
    ArrayList<AddCake_GlobalVariable> list;

    TextView addCake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_cake);

        addCake = findViewById(R.id.btn_addCake);

        addCake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminManageCakeActivity.this, AddCakeActivity.class);
                startActivity(intent);
            }
        });

        // Setting the RecyclerView
        recyclerView = findViewById(R.id.manageCakeList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter_adminCakeCollection = new Adapter_AdminCakeCollection(this,list);
        recyclerView.setAdapter(adapter_adminCakeCollection);

        // Fetching data fro database
        FirebaseDatabase.getInstance().getReference("Collection").child("Cakes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    AddCake_GlobalVariable addCake_globalVariable = dataSnapshot.getValue(AddCake_GlobalVariable.class);
                    list.add(addCake_globalVariable);
                }
                adapter_adminCakeCollection.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminManageCakeActivity.this, AdminHomeActivity.class);
        startActivity(intent);
        finish();
    }
}