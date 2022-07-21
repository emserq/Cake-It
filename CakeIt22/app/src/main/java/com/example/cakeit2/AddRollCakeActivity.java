package com.example.cakeit2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Random;

public class AddRollCakeActivity extends AppCompatActivity {

    TextView collection;
    ImageView rollcakeImage;
    EditText rollcakeName, rollcakedescription, rollcakeprice;
    Button addRollCake;

    RecyclerView recyclerView;
    Adapter_AddRollCake adapter_addRollCake;
    ArrayList<AddRollCake_GlobalVariable> rollcakelist;

    ProgressDialog progressDialog;

    // For Image
    private static final int Gallery_Code = 1;
    Uri imageUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_roll_cake);

        collection = findViewById(R.id.btn_rollcakeCollection);

        rollcakeImage = findViewById(R.id.ivInsertRollCakeImage);
        rollcakeName = findViewById(R.id.etRollCakeName);
        rollcakedescription = findViewById(R.id.etRollCakeDescription);
        rollcakeprice = findViewById(R.id.etRollCakePrice);
        addRollCake = findViewById(R.id.btnAddRollCake);

        // Setting the Adapter and RecyclerView
        rollcakelist = new ArrayList<>();
        adapter_addRollCake = new Adapter_AddRollCake(this, rollcakelist);

        progressDialog = new ProgressDialog(this);

        //---------------- Actions -------------------//

        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddRollCakeActivity.this, AdminManageRollCakeActivity.class);
                startActivity(intent);
            }
        });

        rollcakeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery_Code);

            }
        });

        addRollCake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                FirebaseStorage.getInstance().getReference().child("RollCakeImage").child(imageUrl.getLastPathSegment())
                        .putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {

                                String t = task.getResult().toString();
                                String ROLLCAKEIMAGE, ROLLCAKENAME, ROLLCAKEDESCRIPTION, ROLLCAKEPRICE, ROLLCAKEID;

                                // Generate Random Number for database id
                                final Random myRandom = new Random();
                                String RANDOMID = String.valueOf(myRandom.nextInt(100000));

                                ROLLCAKEIMAGE = t;
                                ROLLCAKENAME = rollcakeName.getText().toString();
                                ROLLCAKEDESCRIPTION = rollcakedescription.getText().toString();
                                ROLLCAKEPRICE = rollcakeprice.getText().toString();
                                ROLLCAKEID = RANDOMID;

                                AddRollCake_GlobalVariable addRollCake_globalVariable = new AddRollCake_GlobalVariable(ROLLCAKEIMAGE, ROLLCAKENAME, ROLLCAKEDESCRIPTION, ROLLCAKEPRICE, ROLLCAKEID);
                                FirebaseDatabase.getInstance().getReference("Collection").child("Roll Cakes").child(ROLLCAKEID).setValue(addRollCake_globalVariable).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            Toast.makeText(AddRollCakeActivity.this, "Roll Cake successfully added", Toast.LENGTH_SHORT).show();
                                            adapter_addRollCake.notifyDataSetChanged();
                                            Intent intent = new Intent(AddRollCakeActivity.this, AdminManageRollCakeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(AddRollCakeActivity.this, "There is an error!", Toast.LENGTH_SHORT).show();
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
            rollcakeImage.setImageURI(imageUrl);
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddRollCakeActivity.this, AdminHomeActivity.class);
        startActivity(intent);
        finish();
    }

}