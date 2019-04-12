package com.example.tayor.karz.views.sign_in;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tayor.karz.Model.User;
import com.example.tayor.karz.R;
import com.example.tayor.karz.views.license.DriverLicenseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class RegisterActivity extends AppCompatActivity {
    TextInputEditText email_et, password_et, confirm_password_et,first_name_et,last_name_et;
    Button next;
    TextView sign_in;
    private FirebaseAuth mAuth;
    private FirebaseFirestore userdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeComponents();
        mAuth = FirebaseAuth.getInstance();
        userdb = FirebaseFirestore.getInstance();
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
        first_name_et = findViewById(R.id.first_name);
        last_name_et = findViewById(R.id.last_name);
    }

    private boolean validatePassword(){
        String password = password_et.getText().toString();
        String confirmPassword = confirm_password_et.getText().toString();
        if(password.isEmpty())
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
        else if(password.length() < 6)
            Toast.makeText(this, "Password length must be greater than 6", Toast.LENGTH_SHORT).show();
        else if(!(password.equals(confirmPassword)))
            Toast.makeText(this, "Password mismatch", Toast.LENGTH_SHORT).show();
        else
            return true;
        return false;
    }

    private void nextStep() {
        final String firstName = first_name_et.getText().toString();
        final String lastName = last_name_et.getText().toString();
        if (validatePassword()) {
            try {
                mAuth.createUserWithEmailAndPassword(email_et.getText().toString(), password_et.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final User newUser = new User();
                            final FirebaseUser user = mAuth.getCurrentUser();
                            newUser.setUserId(user.getUid());
                            newUser.setFirstName(firstName);
                            newUser.setLastName(lastName);
                            Toast.makeText(getApplicationContext(), "Successfully registered " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            userdb.collection("user").add(newUser).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("documentId", documentReference.getId());
                                    Intent intent = new Intent(RegisterActivity.this, DriverLicenseActivity.class);
                                    intent.putExtra("userDocPath", documentReference.getId());
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("errorMessage", e.getMessage());
                                }
                            });

                        } else {
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (Exception ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
