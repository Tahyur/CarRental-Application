package com.example.tayor.karz.views.sign_in;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tayor.karz.Model.User;
import com.example.tayor.karz.R;
import com.example.tayor.karz.views.license.DriverLicenseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterActivity extends AppCompatActivity {
    TextInputEditText email_et, password_et, confirm_password_et;
    Button next;
    TextView sign_in;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeComponents();
        mAuth = FirebaseAuth.getInstance();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStep();
            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToLogin();
            }
        });
    }

    private void backToLogin() {
        Intent intent = new Intent(RegisterActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    void initializeComponents() {
        next = findViewById(R.id.next);
        email_et = findViewById(R.id.reg_email_add_et);
        password_et = findViewById(R.id.reg_password_et);
        confirm_password_et = findViewById(R.id.reg_con_pass_et);
        sign_in = findViewById(R.id.sign_in_reg);
    }

    private void nextStep() {
        String email = email_et.getText().toString();
        String password = password_et.getText().toString();
        String confirmPassword = confirm_password_et.getText().toString();

        try {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setConfirmPassword(confirmPassword);
                mAuth.createUserWithEmailAndPassword(email_et.getText().toString(), password_et.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Successfully registered " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this,DriverLicenseActivity.class);
                            intent.putExtra("user",user);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
