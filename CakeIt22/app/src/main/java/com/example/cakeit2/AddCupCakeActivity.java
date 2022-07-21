package com.example.cakeit2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Random;

public class AddCupCakeActivity extends AppCompatActivity {

    TextView collection;
    ImageView cupcakeImage;
    EditText cupcakeName, cupcakedescription, cupcakeprice;
    Button addCupCake;

    RecyclerView recyclerView;
    Adapter_AddCupCake adapter_addCupCake;
    ArrayList<AddCupCake_GlobalVariable> cupcakelist;

    ProgressDialog progressDialog;

    // For Image
    private static final int Gallery_Code = 1;
    Uri imageUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cup_cake);

        collection = findViewById(R.id.btn_cupcakeCollection);

        cupcakeImage = findViewById(R.id.ivInsertCupCakeImage);
        cupcakeName = findViewById(R.id.etCupCakeName);
        cupcakedescription = findViewById(R.id.etCupCakeDescription);
        cupcakeprice = findViewById(R.id.etCupCakePrice);
        addCupCake = findViewById(R.id.btnAddCupCake);

        // Setting the Adapter and RecyclerView
        cupcakelist = new ArrayList<>();
        adapter_addCupCake = new Adapter_AddCupCake(this, cupcakelist);

        progressDialog = new ProgressDialog(this);

        //---------------- Actions -------------------//

        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCupCakeActivity.this, AdminManageCupCakeActivity.class);
                startActivity(intent);
            }
        });

        cupcakeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery_Code);

            }
        });


        addCupCake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                FirebaseStorage.getInstance().getReference().child("CupCakeImage").child(imageUrl.getLastPathSegment())
                        .putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {

                                String t = task.getResult().toString();
                                String CUPCAKEIMAGE, CUPCAKENAME, CUPCAKEDESCRIPTION, CUPCAKEPRICE, CUPCAKEID;

                                // Generate Random Number for database id
                                final Random myRandom = new Random();
                                String RANDOMID = String.valueOf(myRandom.nextInt(100000));

                                CUPCAKEIMAGE = t;
                                CUPCAKENAME = cupcakeName.getText().toString();
                                CUPCAKEDESCRIPTION = cupcakedescription.getText().toString();
                                CUPCAKEPRICE = cupcakeprice.getText().toString();
                                CUPCAKEID = RANDOMID;

                                AddCupCake_GlobalVariable addCupCake_globalVariable = new AddCupCake_GlobalVariable(CUPCAKEIMAGE, CUPCAKENAME, CUPCAKEDESCRIPTION, CUPCAKEPRICE, CUPCAKEID);
                                FirebaseDatabase.getInstance().getReference("Collection").child("Cup Cakes").child(CUPCAKEID).setValue(addCupCake_globalVariable).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            Toast.makeText(AddCupCakeActivity.this, "Cup Cake successfully added", Toast.LENGTH_SHORT).show();
                                            adapter_addCupCake.notifyDataSetChanged();
                                            Intent intent = new Intent(AddCupCakeActivity.this, AdminManageCupCakeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(AddCupCakeActivity.this, "There is an error!", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                                progressDialog.dismiss();


                            }
                        });

                    }
                });

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_Code && resultCode == RESULT_OK) {
            imageUrl = data.getData();
            cupcakeImage.setImageURI(imageUrl);
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddCupCakeActivity.this, AdminHomeActivity.class);
        startActivity(intent);
        finish();
    }

}