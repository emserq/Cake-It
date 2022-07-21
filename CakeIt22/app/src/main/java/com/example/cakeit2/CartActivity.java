package com.example.cakeit2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Random;

public class CartActivity extends AppCompatActivity {

    ImageView back;
    TextView checkout, total, testing;
    EditText name, contact, address;
    RecyclerView recyclerView;
    Adapter_OrderCake adapter_orderCake;
    ArrayList<OrderCakeID_GlobalVariable> list;
    ArrayList<OrderCakeID_GlobalVariable> makeOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        back = findViewById(R.id.btn_back);

        makeOrder = new ArrayList<>();
        testing = findViewById(R.id.mycart);
        total = findViewById(R.id.tvCartTotal);

        name = findViewById(R.id.etName);
        contact = findViewById(R.id.etContact);
        address = findViewById(R.id.etAddress);
        checkout = findViewById(R.id.btn_checkout);

        // Setting the RecyclerView
        recyclerView = findViewById(R.id.cartList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter_orderCake = new Adapter_OrderCake(this,list);
        recyclerView.setAdapter(adapter_orderCake);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        // Fetching data from database
        FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("My Cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    OrderCakeID_GlobalVariable orderCake = dataSnapshot.getValue(OrderCakeID_GlobalVariable.class);
                    list.add(orderCake);
                }
                adapter_orderCake.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CartActivity.this, "Error pO", Toast.LENGTH_SHORT).show();
            }
        });

        // Getting all of the items price for the total
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("User").child(user).child("My Cart")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        int sum = 0;

                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                            Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                            Object price = map.get("price");
                            int pValue = Integer.parseInt(String.valueOf(price));
                            sum += pValue;

                            total.setText(String.valueOf(sum));

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Getting the time order
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
                String date = simpleDateFormat.format(calendar.getTime());

                String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference path = FirebaseDatabase.getInstance().getReference("User").child(user).child("My Cart");

                path.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        // Getting all the values in My Cart and store to the admin's normal orders using loop
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            OrderCakeID_GlobalVariable order = dataSnapshot.getValue(OrderCakeID_GlobalVariable.class);

                            FirebaseDatabase.getInstance().getReference("Orders")
                                    .child("Normal Orders").child(order.getOrderid()).setValue(order);

                            // Storing the date string in the admin's normal orders database in timeorder child
                            FirebaseDatabase.getInstance().getReference("Orders")
                                    .child("Normal Orders").child(order.getOrderid()).child("timeorder").setValue(date);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                // Store in the user's database to fetch by the user
                DatabaseReference toOrderPath = FirebaseDatabase.getInstance().getReference("User").child(user).child("My Cart");

                toOrderPath.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        // Getting all the values in My Cart and store to the user's normal orders using loop
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            OrderCakeID_GlobalVariable order = dataSnapshot.getValue(OrderCakeID_GlobalVariable.class);

                            FirebaseDatabase.getInstance().getReference("User").child(user).child("My Orders").child("Normal Orders")
                                    .child(order.getOrderid()).setValue(order);

                            // Storing the date string in the user's normal orders database in timeorder child
                            FirebaseDatabase.getInstance().getReference("User").child(user).child("My Orders").child("Normal Orders")
                                    .child(order.getOrderid()).child("timeorder").setValue(date);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Toast.makeText(CartActivity.this, "Your order has been placed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CartActivity.this, ProfileActivity.class);
                startActivity(intent);

                // Remove the values in cart
                path.removeValue();



            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CartActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

}