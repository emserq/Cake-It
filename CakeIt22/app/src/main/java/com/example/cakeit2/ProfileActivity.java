package com.example.cakeit2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.Login;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private ImageView map, home, customcake, profile;
    private TextView name, email, contact, address;
    LinearLayout cart, orders, history, logout;
    ProgressDialog dialog;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dialog = new ProgressDialog(this);

        name = findViewById(R.id.tvProfileName);
        email = findViewById(R.id.tvProfileEmail);
        contact = findViewById(R.id.tvProfileContact);
        address = findViewById(R.id.tvProfileAddress);

        cart = findViewById(R.id.llCart);
        orders = findViewById(R.id.llOrders);

        map = findViewById(R.id.profileTomap);
        home = findViewById(R.id.profileTohome);
        customcake = findViewById(R.id.profileTocustom);
        profile = findViewById(R.id.profileToprofile);
        history = findViewById(R.id.btn_history);
        logout = findViewById(R.id.btn_logout);

        //--------------------------Functions---------------------------//

        // Fetching data fro database
        FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GlobalVariableUser globalVariableUser = snapshot.getValue(GlobalVariableUser.class);

                if (globalVariableUser != null) {

                    String NAME, EMAIL, CONTACT, ADDRESS;

                    NAME = globalVariableUser.getName();
                    EMAIL = globalVariableUser.getEmail();
                    CONTACT = globalVariableUser.getContact();
                    ADDRESS = globalVariableUser.getAddress();

                    name.setText(NAME);
                    email.setText(EMAIL);
                    contact.setText(CONTACT);
                    address.setText(ADDRESS);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProfileActivity.this, CartActivity.class);
                startActivity(intent);

            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProfileActivity.this, OrdersActivity.class);
                startActivity(intent);

            }
        });

        //-------------------------Bottom Navigator--------------------//
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        customcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, CustomActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, PurchaseHistory.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setTitle("Logging out...");
                dialog.show();

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }

        });

    }

    private void gSignout() {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}