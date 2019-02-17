package com.example.tayor.karz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity implements RegisterFragment.OnRegisterInteractionListener, DriverLicenseFragment.OnLicenseInteractionListener {

    FrameLayout frame;
    TextView register_tv;
    TextInputEditText username_et, password_et;
    Button login_btn;
    public static final String REGISTER_TAG = RegisterFragment.class.getSimpleName();
    public static final String LICENSE_TAG = DriverLicenseFragment.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initializeComponents();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        register_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRegistrationForm();
            }
        });
    }

    private void loadRegistrationForm() {
        RegisterFragment registerFragment = new RegisterFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(frame.getId(), registerFragment, REGISTER_TAG)
                .addToBackStack(REGISTER_TAG)
                .commit();
    }

    void initializeComponents() {
        frame = findViewById(R.id.frame);
        register_tv = findViewById(R.id.register);
        login_btn = findViewById(R.id.login);
        username_et = findViewById(R.id.username_et);
        password_et = findViewById(R.id.password_et);
    }

    private void login() {
        if (username_et.getText().toString().equals(""))
            Toast.makeText(this, "Enter your username", Toast.LENGTH_SHORT).show();
        else if (!(password_et.getText().toString().equalsIgnoreCase("")))
            Toast.makeText(this, "Password Incorrect", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "You are logged in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

//    @Override
//    public void onLoginInteraction() {
//        Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void onRegisterInteraction(int flag) {
      //  Toast.makeText(this, "Register Called", Toast.LENGTH_SHORT).show();

        switch (flag) {
            case 0:
                DriverLicenseFragment licenseFragment = new DriverLicenseFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(frame.getId(), licenseFragment, LICENSE_TAG)
                        .addToBackStack(LICENSE_TAG)
                        .commit();
                break;
            case 1:
                break;
        }
    }

    @Override
    public void onLicenseInteraction() {
        Toast.makeText(this, "Registration Complete, you are logged in", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
