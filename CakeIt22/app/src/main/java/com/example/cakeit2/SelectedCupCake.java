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

public class SelectedCupCake extends AppCompatActivity {

    ImageView cupcakeImage;
    TextView cakeUrl, cupcakeName, cupcakeDescription, cupcakePrice, quantity;
    Button positive, negative, addtoCartCupCake;
    ArrayList<AddCupCake_GlobalVariable> cupcakelist;

    private String USERNAME, USERADDRESS;

    int quantityCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_cup_cake);

        cakeUrl = findViewById(R.id.tvImageURL);
        cupcakeImage = findViewById(R.id.ivShowCupCakeImage);
        cupcakeName = findViewById(R.id.tvShowCupCakeName);
        cupcakeDescription = findViewById(R.id.tvShowCupCakeDescription);
        cupcakePrice = findViewById(R.id.tvShowCupCakePrice);
        String cupcakeID = getIntent().getStringExtra("cupcakeID");

        quantity = findViewById(R.id.tvQuantity);
        positive = findViewById(R.id.btnPositive);
        negative = findViewById(R.id.btnNegative);
        addtoCartCupCake = findViewById(R.id.btn_AddtoCartCupCake);

        cupcakelist = new ArrayList<>();

        // Fetching data fro database
        FirebaseDatabase.getInstance().getReference("Collection").child("Cup Cakes").child(cupcakeID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AddCupCake_GlobalVariable addCupCake_globalVariable = snapshot.getValue(AddCupCake_GlobalVariable.class);

                if (addCupCake_globalVariable != null) {

                    String CAKEIMAGE, CAKENAME, CAKEDESCRIPION, CAKEPRICE;
                    CAKEIMAGE = addCupCake_globalVariable.getImage();
                    CAKENAME = addCupCake_globalVariable.getName();
                    CAKEDESCRIPION = addCupCake_globalVariable.getDescription();
                    CAKEPRICE = addCupCake_globalVariable.getPrice();

                    Picasso.get().load(CAKEIMAGE).into(cupcakeImage);
                    cakeUrl.setText(CAKEIMAGE);
                    cupcakeName.setText(CAKENAME);
                    cupcakeDescription.setText(CAKEDESCRIPION);
                    cupcakePrice.setText(CAKEPRICE);

                    int price = Integer.parseInt(addCupCake_globalVariable.getPrice());

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
                            cupcakePrice.setText(""+ formula);

                        }
                    });

                    negative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // Reading the updated TextView (price) to use for the formula
                            String Reader = cupcakePrice.getText().toString();
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
                                cupcakePrice.setText(""+ formula);
                            }
                        }
                    });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addtoCartCupCake.setOnClickListener(new View.OnClickListener() {
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
                                    NAME = cupcakeName.getText().toString();
                                    DESCRIPTION = cupcakeDescription.getText().toString();
                                    QUANTITY = quantity.getText().toString();
                                    PRICE = cupcakePrice.getText().toString();
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
                                                        Toast.makeText(SelectedCupCake.this, NAME + " added to your cart", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(SelectedCupCake.this, HomeActivity.class);
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