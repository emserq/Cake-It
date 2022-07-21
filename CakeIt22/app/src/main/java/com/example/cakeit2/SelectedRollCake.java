package com.example.cakeit2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class SelectedRollCake extends AppCompatActivity {

    ImageView rollcakeImage;
    TextView cakeUrl, rollcakeName, rollcakeDescription, rollcakePrice, quantity;
    Button positive, negative, addtoCartRollCake;
    ArrayList<AddRollCake_GlobalVariable> rollcakelist;

    private String USERNAME, USERADDRESS;

    int quantityCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_roll_cake);

        cakeUrl = findViewById(R.id.tvImageURL);
        rollcakeImage = findViewById(R.id.ivShowRollCakeImage);
        rollcakeName = findViewById(R.id.tvShowRollCakeName);
        rollcakeDescription = findViewById(R.id.tvShowRollCakeDescription);
        rollcakePrice = findViewById(R.id.tvShowRollCakePrice);
        String rollcakeID = getIntent().getStringExtra("rollcakeID");

        quantity = findViewById(R.id.tvQuantity);
        positive = findViewById(R.id.btnPositive);
        negative = findViewById(R.id.btnNegative);
        addtoCartRollCake = findViewById(R.id.btn_AddtoCartRollCake);

        rollcakelist = new ArrayList<>();

        // Fetching data fro database
        FirebaseDatabase.getInstance().getReference("Collection").child("Roll Cakes").child(rollcakeID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AddRollCake_GlobalVariable addRollCake_globalVariable = snapshot.getValue(AddRollCake_GlobalVariable.class);

                if (addRollCake_globalVariable != null) {

                    String CAKEIMAGE, CAKENAME, CAKEDESCRIPION, CAKEPRICE;
                    CAKEIMAGE = addRollCake_globalVariable.getImage();
                    CAKENAME = addRollCake_globalVariable.getName();
                    CAKEDESCRIPION = addRollCake_globalVariable.getDescription();
                    CAKEPRICE = addRollCake_globalVariable.getPrice();

                    Picasso.get().load(CAKEIMAGE).into(rollcakeImage);
                    cakeUrl.setText(CAKEIMAGE);
                    rollcakeName.setText(CAKENAME);
                    rollcakeDescription.setText(CAKEDESCRIPION);
                    rollcakePrice.setText(CAKEPRICE);

                    int price = Integer.parseInt(addRollCake_globalVariable.getPrice());

                    // Storing the fixed price of the item into the variable for adding/subtracting the total price
                    final int PRICE = price;

                    positive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // Creating variable to store the formula
                            int formula;

                            quantityCount++;
                            formula = quantityCount * PRICE;

                            quantity.setText("" + quantityCount);
                            rollcakePrice.setText(""+ formula);

                        }
                    });

                    negative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // Reading the updated TextView (price) to use for the formula
                            String Reader = rollcakePrice.getText().toString();
                            int tvReader = Integer.parseInt(Reader);
                            int formula;

                            // Checking the quantity to prevent 0 or negative number
                            if (quantityCount <= 1){
                                quantityCount = 1;
                            }
                            else {
                                quantityCount--;
                                formula = tvReader - PRICE;
                                quantity.setText("" + quantityCount);
                                rollcakePrice.setText(""+ formula);
                            }
                        }
                    });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addtoCartRollCake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Getting the Current user's name and address
                FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                GlobalVariableUser user = snapshot.getValue(GlobalVariableUser.class);
                                if (user != null) {

                                    String USERNAME, USERCONTACT, USERADDRESS, IMAGE, NAME, DESCRIPTION, QUANTITY, PRICE,ORDERID, ORDERSTATUS, UID, TIMEORDER, TIMERECEIVED;

                                    // Generate Random Number for database id
                                    final Random myRandom = new Random();
                                    String RANDOMID = String.valueOf(myRandom.nextInt(100000));

                                    USERNAME = user.getName();
                                    USERCONTACT = user.getContact();
                                    USERADDRESS = user.getAddress();
                                    IMAGE = cakeUrl.getText().toString();
                                    NAME = rollcakeName.getText().toString();
                                    DESCRIPTION = rollcakeDescription.getText().toString();
                                    QUANTITY = quantity.getText().toString();
                                    PRICE = rollcakePrice.getText().toString();
                                    ORDERID = RANDOMID;
                                    ORDERSTATUS = "Pending";
                                    UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    TIMEORDER = "";
                                    TIMERECEIVED = "";


                                    OrderCakeID_GlobalVariable addToCart = new OrderCakeID_GlobalVariable(USERNAME, USERCONTACT, USERADDRESS, IMAGE, NAME, DESCRIPTION, QUANTITY, PRICE, ORDERID, ORDERSTATUS, UID, TIMEORDER, TIMERECEIVED);
                                    FirebaseDatabase.getInstance().getReference("User")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("My Cart").child(ORDERID).setValue(addToCart)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if (task.isSuccessful()){
                                                        Toast.makeText(SelectedRollCake.this, NAME + " added to your cart", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(SelectedRollCake.this, HomeActivity.class);
                                                        startActivity(intent);
                                                    }

                                                }
                                            });

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

            }
        });

    }
}