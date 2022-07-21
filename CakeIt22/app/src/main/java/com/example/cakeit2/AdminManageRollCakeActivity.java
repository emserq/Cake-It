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

public class AdminManageRollCakeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter_AdminRollCakeCollection adapter_adminRollCakeCollection;
    ArrayList<AddRollCake_GlobalVariable> list;

    TextView addCake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_roll_cake);

        addCake = findViewById(R.id.btn_addCake);

        addCake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminManageRollCakeActivity.this, AddRollCakeActivity.class);
                startActivity(intent);
            }
        });

        // Setting the RecyclerView
        recyclerView = findViewById(R.id.manageRollCakeList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter_adminRollCakeCollection = new Adapter_AdminRollCakeCollection(this,list);
        recyclerView.setAdapter(adapter_adminRollCakeCollection);

        // Fetching data fro database
        FirebaseDatabase.getInstance().getReference("Collection").child("Roll Cakes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    AddRollCake_GlobalVariable addRollCake_globalVariable = dataSnapshot.getValue(AddRollCake_GlobalVariable.class);
                    list.add(addRollCake_globalVariable);
                }
                adapter_adminRollCakeCollection.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminManageRollCakeActivity.this, AdminHomeActivity.class);
        startActivity(intent);
        finish();
    }
}