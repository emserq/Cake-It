package com.example.cakeit2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import java.util.Calendar;
import java.util.Random;

public class ConfirmationRollCakeActivity extends AppCompatActivity {

    private DatabaseReference database;
    private FirebaseAuth auth;
    private TextView size, sponge, filling, frosting, toppings, candle, total, quantity;
    private Button positive, negative;
    private AppCompatButton placeOrder;

    private ProgressDialog progressDialog;

    int quantityCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_roll_cake);
        //changing statusbar color
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.mintpink));
        }


        progressDialog = new ProgressDialog(this);
        placeOrder = findViewById(R.id.btnPlaceOrder);
        database = FirebaseDatabase.getInstance().getReference("Custom Order")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        auth = FirebaseAuth.getInstance();

        size = findViewById(R.id.tvSize);
        sponge = findViewById(R.id.tvSponge);
        filling = findViewById(R.id.tvFilling);
        frosting = findViewById(R.id.tvFrosting);
        toppings = findViewById(R.id.tvToppings);
        candle = findViewById(R.id.tvCandle);
        total = findViewById(R.id.tvTotal);

        positive = findViewById(R.id.btnPositive);
        negative = findViewById(R.id.btnNegative);
        quantity = findViewById(R.id.tvQuantity);

        String SIZE = getIntent().getStringExtra("selectedSize");
        String SPONGE = getIntent().getStringExtra("selectedSponge");
        String FILLING = getIntent().getStringExtra("selectedFilling");
        String FROSTING = getIntent().getStringExtra("selectedFrosting");
        String TOPPINGS = getIntent().getStringExtra("selectedToppings");
        String CANDLE = getIntent().getStringExtra("selectedCandles");
        String TOTAL = getIntent().getStringExtra("total");

        size.setText(SIZE + " inches");
        sponge.setText(SPONGE);
        filling.setText(FILLING);
        frosting.setText(FROSTING);
        toppings.setText(TOPPINGS);
        candle.setText(CANDLE);
        total.setText(TOTAL);

        int nTOTAL = Integer.parseInt(TOTAL);

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creating variable to store the formula
                int formula;

                quantityCount++;
                formula = quantityCount * nTOTAL;

                quantity.setText("" + quantityCount);
                total.setText(""+ formula);

            }
        });

        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Reading the updated TextView (price) to use for the formula
                String Reader = total.getText().toString();
                int tvReader = Integer.parseInt(Reader);
                int formula;

                // Checking the quantity to prevent 0 or negative number
                if (quantityCount <= 1){
                    quantityCount = 1;
                }
                else {
                    quantityCount--;
                    formula = tvReader - nTOTAL;
                    quantity.setText("" + quantityCount);
                    total.setText(""+ formula);
                }
            }
        });

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setTitle("Loading...");
                progressDialog.show();

                // Generate Random Number for database id
                final Random myRandom = new Random();
                String RANDOMID = String.valueOf(myRandom.nextInt(100000));

                // Getting the time order
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
                String date = simpleDateFormat.format(calendar.getTime());

                // Getting the current user name and contact
                FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                GlobalVariableUser  user = snapshot.getValue(GlobalVariableUser.class);

                                if (user != null){

                                    String SIZE, SPONGE, FILLING, FROSTING, TOPPINGS, CANDLE, QUANTITY, NAME, PRICE, ORDERID, ORDERSTATUS, USERNAME, USERCONTACT, UID, TIMEORDER, TIMERECEIVED;

                                    SIZE = size.getText().toString();
                                    SPONGE = sponge.getText().toString();
                                    FILLING = filling.getText().toString();
                                    FROSTING = frosting.getText().toString();
                                    TOPPINGS = toppings.getText().toString();
                                    CANDLE = candle.getText().toString();
                                    QUANTITY = quantity.getText().toString();
                                    NAME = "Custom Rollcake";
                                    PRICE = total.getText().toString();
                                    ORDERID = RANDOMID;
                                    ORDERSTATUS = "Pending";
                                    USERNAME = user.getName();
                                    USERCONTACT = user.getContact();
                                    UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    TIMEORDER = date;
                                    TIMERECEIVED = "";

                                    CustomRollCake_GlobalVariable customRollCake = new CustomRollCake_GlobalVariable (SIZE,SPONGE,FILLING,FROSTING,TOPPINGS,CANDLE, QUANTITY, NAME, PRICE, ORDERID, ORDERSTATUS, USERNAME,USERCONTACT, UID, TIMEORDER, TIMERECEIVED);

                                    FirebaseDatabase.getInstance().getReference("Orders").child("Custom Orders").child("Roll Cake").child(ORDERID).setValue(customRollCake).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            // Storing in users database
                                            FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .child("My Orders").child("Custom Order").child("Roll Cake").child(ORDERID).setValue(customRollCake).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(ConfirmationRollCakeActivity.this, "Your custom Rollcake has been placed", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(ConfirmationRollCakeActivity.this, HomeActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }else {
                                                        Toast.makeText(ConfirmationRollCakeActivity.this, "There is an error!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                        }
                                    });

                                    // Progress dialog delay
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            progressDialog.dismiss();
                                        }
                                    }, 3000);


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