package com.example.cakeit2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TestingLoadImage extends AppCompatActivity {

    ImageView image;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_load_image);

        image = findViewById(R.id.ivImageView);
        text = findViewById(R.id.tvTextView);

        FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Cart").child("3957").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                OrderCakeID_GlobalVariable orderCake = snapshot.getValue(OrderCakeID_GlobalVariable.class);

                if (orderCake != null) {
                    String IMAGE, NAME;

                    IMAGE = orderCake.getImageurl();
                    NAME = orderCake.getDescription();
                    Picasso.get().load(IMAGE).into(image);
                    text.setText(NAME);
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}