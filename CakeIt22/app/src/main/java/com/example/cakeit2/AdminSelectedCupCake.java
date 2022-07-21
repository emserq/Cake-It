package com.example.cakeit2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class AdminSelectedCupCake extends AppCompatActivity {

    RelativeLayout whole;

    ImageView cakeImage, editCakeImage;
    TextView cakeName, cakeDescription, cakePrice;
    EditText editCakeName, editCakeDescription, editCakePrice;
    Button edit, delete, save;
    ArrayList<AddCupCake_GlobalVariable> list;

    ProgressDialog progressDialog;

    String viewImage;
    private  static final int Gallery_code = 1;
    Uri imageUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_selected_cup_cake);

        whole = findViewById(R.id.whole);

        cakeImage = findViewById(R.id.ivShowCupCakeImage);
        cakeName = findViewById(R.id.tvShowCupCakeName);
        cakeDescription = findViewById(R.id.tvShowCupCakeDescription);
        cakePrice = findViewById(R.id.tvShowCupCakePrice);
        String cakeID = getIntent().getStringExtra("cakeID");

        edit = findViewById(R.id.btn_Edit);
        delete = findViewById(R.id.btn_Delete);
        save = findViewById(R.id.btn_EditSave);

        editCakeImage = findViewById(R.id.ivEditCupCakeImage);
        editCakeName = findViewById(R.id.etEditCupCakeName);
        editCakeDescription = findViewById(R.id.etEditCupCakeDescription);
        editCakePrice = findViewById(R.id.etEditCupCakePrice);

        progressDialog = new ProgressDialog(this);

        list = new ArrayList<>();

        // Fetching data fro database
        FirebaseDatabase.getInstance().getReference("Collection").child("Cup Cakes").child(cakeID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AddCupCake_GlobalVariable addCupCake_globalVariable = snapshot.getValue(AddCupCake_GlobalVariable.class);

                if (addCupCake_globalVariable != null) {

                    String CAKEIMAGE, CAKENAME, CAKEDESCRIPION, CAKEPRICE;
                    CAKEIMAGE = addCupCake_globalVariable.getImage();

                    CAKENAME = addCupCake_globalVariable.getName();
                    CAKEDESCRIPION = addCupCake_globalVariable.getDescription();
                    CAKEPRICE = addCupCake_globalVariable.getPrice();
                    // Putting CAKEIMAGE url in string to display in edit mode
                    viewImage = CAKEIMAGE;

                    Picasso.get().load(CAKEIMAGE).into(cakeImage);
                    cakeName.setText(CAKENAME);
                    cakeDescription.setText(CAKEDESCRIPION);
                    cakePrice.setText(CAKEPRICE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {save.setVisibility(View.VISIBLE);
                    }
                },3500);

                Snackbar.make(whole, "Edit mode", Snackbar.LENGTH_SHORT)
                        .setTextColor(Color.WHITE)
                        .setBackgroundTint(Color.rgb(239,178,97))
                        .setDuration(3000)
                        .show();

                // Hiding the textviews and buttons
                cakeImage.setVisibility(View.INVISIBLE);
                cakeName.setVisibility(View.INVISIBLE);
                cakeDescription.setVisibility(View.INVISIBLE);
                cakePrice.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);

                // Passing the value of the textview to the editText
                Picasso.get().load(viewImage).into(editCakeImage);
                editCakeName.setText(cakeName.getText().toString());
                editCakeDescription.setText(cakeDescription.getText().toString());
                editCakePrice.setText(cakePrice.getText().toString());

                // Showing the Edittext and save button
                editCakeImage.setVisibility(View.VISIBLE);
                editCakeName.setVisibility(View.VISIBLE);
                editCakeName.setTextColor(Color.rgb(239,178,97));
                editCakeDescription.setVisibility(View.VISIBLE);
                editCakeDescription.setTextColor(Color.rgb(239,178,97));
                editCakePrice.setVisibility(View.VISIBLE);
                editCakePrice.setTextColor(Color.rgb(239,178,97));

            }
        });

        editCakeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Hide the original cake Image
                editCakeImage.setVisibility(View.VISIBLE);

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery_code);

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Collection").child("Cup Cakes").child(cakeID).removeValue();
                Toast.makeText(AdminSelectedCupCake.this, "Cupcake successfully deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminSelectedCupCake.this, AdminManageCupCakeActivity.class);
                startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setTitle("Updating...");
                progressDialog.show();

                FirebaseStorage.getInstance().getReference().child("CupCakeImage").child(imageUrl.getLastPathSegment())
                        .putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {

                                String image = task.getResult().toString();
                                String UP_CAKEIMAGE, UP_CAKENAME, UP_CAKEDESCRIPTION, UP_CAKEPRICE, UP_CAKEID;

                                // Generate Random Number for database id
                                final java.util.Random myRandom = new Random();
                                String RANDOMID = String.valueOf(myRandom.nextInt(100000));

                                UP_CAKEIMAGE = image;
                                UP_CAKENAME = editCakeName.getText().toString();
                                UP_CAKEDESCRIPTION = editCakeDescription.getText().toString();
                                UP_CAKEPRICE = editCakePrice.getText().toString();
                                UP_CAKEID = RANDOMID;

                                AddRollCake_GlobalVariable updatedCake = new AddRollCake_GlobalVariable(UP_CAKEIMAGE, UP_CAKENAME, UP_CAKEDESCRIPTION, UP_CAKEPRICE, UP_CAKEID);
                                FirebaseDatabase.getInstance().getReference("Collection").child("Cup Cakes").child(UP_CAKEID).setValue(updatedCake).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            Toast.makeText(AdminSelectedCupCake.this, "Cup Cake updated successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(AdminSelectedCupCake.this, AdminSelectedCupCake.class);
                                            startActivity(intent);
                                            finish();

                                        } else {
                                            Toast.makeText(AdminSelectedCupCake.this, "There is an error!", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                                progressDialog.dismiss();



                            }
                        });

                    }
                });
                FirebaseDatabase.getInstance().getReference("Collection").child("Cup Cakes").child(cakeID).removeValue();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_code && resultCode == RESULT_OK){
            imageUrl = data.getData();
            editCakeImage.setImageURI(imageUrl);
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminSelectedCupCake.this, AdminHomeActivity.class);
        startActivity(intent);
    }

}