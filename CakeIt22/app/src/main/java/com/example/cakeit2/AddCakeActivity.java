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

public class AddCakeActivity extends AppCompatActivity {

    TextView collection;
    ImageView cakeImage;
    EditText cakeName, description, price;
    Button addCake;

    RecyclerView recyclerView;
    Adapter_AddCake adapter_addCake;
    ArrayList<AddCake_GlobalVariable> list;

    ProgressDialog progressDialog;

    // For Image
    private static final int Gallery_code = 1;
    Uri imageUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cake);

        collection = findViewById(R.id.btn_cakeCollection);

        cakeImage = findViewById(R.id.ivInsertCakeImage);
        cakeName = findViewById(R.id.etCakeName);
        description = findViewById(R.id.etDescription);
        price = findViewById(R.id.etPrice);
        addCake = findViewById(R.id.btnAddCake);

        // Setting the Adapter and RecyclerView
        list = new ArrayList<>();
        adapter_addCake = new Adapter_AddCake(this,list);

        progressDialog = new ProgressDialog(this);

        //---------------- Actions -------------------//

        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCakeActivity.this, AdminManageCakeActivity.class);
                startActivity(intent);
            }
        });

        cakeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery_code);

            }
        });

        addCake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                FirebaseStorage.getInstance().getReference().child("CakeImage").child(imageUrl.getLastPathSegment())
                        .putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {

                                String t = task.getResult().toString();
                                String CAKEIMAGE, CAKENAME, DESCRIPTION, PRICE, CAKEID;

                                // Generate Random Number for database id
                                final Random myRandom = new Random();
                                String RANDOMID = String.valueOf(myRandom.nextInt(100000));

                                CAKEIMAGE = t;
                                CAKENAME = cakeName.getText().toString();
                                DESCRIPTION = description.getText().toString();
                                PRICE = price.getText().toString();
                                CAKEID = RANDOMID;

                                AddCake_GlobalVariable addCake_globalVariable = new AddCake_GlobalVariable(CAKEIMAGE, CAKENAME, DESCRIPTION, PRICE, CAKEID);
                                FirebaseDatabase.getInstance().getReference("Collection").child("Cakes").child(CAKEID).setValue(addCake_globalVariable).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            Toast.makeText(AddCakeActivity.this, "Cake successfully added", Toast.LENGTH_SHORT).show();
                                            adapter_addCake.notifyDataSetChanged();
                                            Intent intent = new Intent(AddCakeActivity.this, AdminManageCakeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(AddCakeActivity.this, "There is an error!", Toast.LENGTH_SHORT).show();
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

        if (requestCode == Gallery_code && resultCode == RESULT_OK){
            imageUrl = data.getData();
            cakeImage.setImageURI(imageUrl);
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddCakeActivity.this, AdminHomeActivity.class);
        startActivity(intent);
        finish();
    }

}