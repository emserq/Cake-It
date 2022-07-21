package com.example.cakeit2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cakeit2.GlobalVariableUser;
import com.example.cakeit2.LoginActivity;
import com.example.cakeit2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText name, address, contact, email, password, confirmpassword;
    private TextView login;
    private Button signup;
    private FirebaseAuth auth;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.etName);
        address = findViewById(R.id.etAddress);
        contact = findViewById(R.id.etContact);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        confirmpassword = findViewById(R.id.etConfirmPassword);
        signup = findViewById(R.id.btnSignup);
        auth = FirebaseAuth.getInstance();
        dialog = new Dialog(this);
        login = findViewById(R.id.tvLogin);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NAME = name.getText().toString().trim();
                String ADDRESS = address.getText().toString().trim();
                String CONTACT = contact.getText().toString().trim();
                String EMAIL = email.getText().toString().trim();
                String PASSWORD = password.getText().toString().trim();
                String CONFIRMPASSWORD = confirmpassword.getText().toString().trim();

                if (TextUtils.isEmpty(NAME)){
                    name.setError("Enter you Name");
                    return;
                } else if (TextUtils.isEmpty(ADDRESS)) {
                    address.setError("Enter your Address");
                    return;
                } else if (TextUtils.isEmpty(CONTACT)) {
                    contact.setError("Enter your Phone Number");
                    return;
                } else if (TextUtils.isEmpty(EMAIL)) {
                    email.setError("Enter your Email");
                    return;
                } else if (!ValidationEmail(EMAIL)) {
                    email.setError("Invalid Email");
                    return;
                } else if (TextUtils.isEmpty(ADDRESS)) {
                    password.setError("Enter your Address");
                    return;
                } else if (PASSWORD.length() < 4) {
                    password.setError("Password is too short");
                } else if (!CONFIRMPASSWORD.equals(PASSWORD)) {
                    confirmpassword.setError("Password not match");
                } else {
                    dialog.setTitle("Loading...");
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);

                    auth.createUserWithEmailAndPassword(EMAIL, PASSWORD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                GlobalVariableUser globalVariableUser = new GlobalVariableUser(NAME, ADDRESS, CONTACT, EMAIL, PASSWORD);

                                FirebaseDatabase.getInstance().getReference("User")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(globalVariableUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignupActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(SignupActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(SignupActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    });
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }
    // Email format Checker
    private Boolean ValidationEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}