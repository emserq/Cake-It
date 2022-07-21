package com.example.cakeit2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.FaceDetector;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private TextView signup;
    private Button login;
    private FirebaseAuth auth;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        login = findViewById(R.id.btnLogin);
        signup = findViewById(R.id.tvSignup);
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);


       login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Admin acount
                String admin_email = "admin";
                String admin_password = "admin123";

                String EMAIL = email.getText().toString().trim();
                String PASSWORD = password.getText().toString().trim();

                if (TextUtils.isEmpty(EMAIL)) {
                    email.setError("Enter your Email");
                    return;
                } else if (TextUtils.isEmpty(PASSWORD)) {
                    password.setError("Enter your Password");
                    return;
                } else if (EMAIL.equals(admin_email) && PASSWORD.equals(admin_password)) {

                    dialog.setMessage("Logging in as Admin...");
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);

                    Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                    startActivity(intent);
                } else {

                    dialog.setMessage("Logging in...");
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    auth.signInWithEmailAndPassword(EMAIL, PASSWORD).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    });
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });



    }

}