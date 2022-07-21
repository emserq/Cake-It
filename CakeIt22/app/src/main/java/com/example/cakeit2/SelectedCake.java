package com.example.cakeit2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class SelectedCake extends AppCompatActivity {

    ImageView cakeImage;
    TextView cakeURL, cakeName, cakeDescription, cakePrice, quantity;
    Button positive, negative, addtoCartCake;
    ArrayList<AddCake_GlobalVariable> list;

    int quantityCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_cake);;

        cakeURL = findViewById(R.id.tvImageURL);
        cakeImage = findViewById(R.id.ivShowCakeImage);
        cakeName = findViewById(R.id.tvShowCakeName);
        cakeDescription = findViewById(R.id.tvShowCakeDescription);
        cakePrice = findViewById(R.id.tvShowCakePrice);
        String cakeID = getIntent().getStringExtra("cakeID");

        quantity = findViewById(R.id.tvQuantity);
        positive = findViewById(R.id.btnPositive);
        negative = findViewById(R.id.btnNegative);
        addtoCartCake = findViewById(R.id.btn_AddtoCartCake);

        list = new ArrayList<>();

        // Fetching data from database
        FirebaseDatabase.getInstance().getReference("Collection").child("Cakes").child(cakeID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AddCake_GlobalVariable addCake_globalVariable = snapshot.getValue(AddCake_GlobalVariable.class);

                if (addCake_globalVariable != null) {

                    String CAKEIMAGE, CAKENAME, CAKEDESCRIPION, CAKEPRICE;
                    CAKEIMAGE = addCake_globalVariable.getImage();
                    CAKENAME = addCake_globalVariable.getName();
                    CAKEDESCRIPION = addCake_globalVariable.getDescription();
                    CAKEPRICE = addCake_globalVariable.getPrice();

                    Picasso.get().load(CAKEIMAGE).into(cakeImage);
                    cakeURL.setText(CAKEIMAGE);
                    cakeName.setText(CAKENAME);
                    cakeDescription.setText(CAKEDESCRIPION);
                    cakePrice.setText(CAKEPRICE);

                    int price = Integer.parseInt(addCake_globalVariable.getPrice());

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
                            cakePrice.setText(""+ formula);

                        }
                    });

                    negative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // Reading the updated TextView (price) to use for the formula
                            String Reader = cakePrice.getText().toString();
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
                                cakePrice.setText(""+ formula);
                            }
                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addtoCartCake.setOnClickListener(new View.OnClickListener() {
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
                                    IMAGE = cakeURL.getText().toString();
                                    NAME = cakeName.getText().toString();
                                    DESCRIPTION = cakeDescription.getText().toString();
                                    QUANTITY = quantity.getText().toString();
                                    PRICE = cakePrice.getText().toString();
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
                                                        Toast.makeText(SelectedCake.this, NAME + " added to your cart", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(SelectedCake.this, HomeActivity.class);
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