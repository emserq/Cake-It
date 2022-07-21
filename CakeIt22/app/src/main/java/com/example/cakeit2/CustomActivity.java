package com.example.cakeit2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.net.Inet4Address;

public class CustomActivity extends AppCompatActivity {

    RelativeLayout customCake, customRollCake, customCupCake;
    private ImageView map, home, customcake, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        customCake = findViewById(R.id.btn_customCake);
        customRollCake = findViewById(R.id.btn_customRollCake);
        customCupCake = findViewById(R.id.btn_customCupcake);

        map = findViewById(R.id.customTomap);
        home = findViewById(R.id.customTohome);
        customcake = findViewById(R.id.customTocustom);
        profile = findViewById(R.id.customToprofile);

        //--------------------------Functions---------------------------//


        customCake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomActivity.this, CustomCakeActivity.class);
                startActivity(intent);
            }
        });

        customRollCake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomActivity.this, CustomRollCakeActivity.class);
                startActivity(intent);
            }
        });

        customCupCake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomActivity.this, CustomCupCakeActivity.class);
                startActivity(intent);
            }
        });


        // Navigation buttons
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        customcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomActivity.this, CustomActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

    }
}