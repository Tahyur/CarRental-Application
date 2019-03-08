package com.example.tayor.karz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInActivity extends AppCompatActivity implements RegisterFragment.OnRegisterInteractionListener, DriverLicenseFragment.OnLicenseInteractionListener {
    private FirebaseAuth mAuth;
    FrameLayout frame;
    TextView register_tv;
    TextInputEditText email_et, password_et;
    Button login_btn;
    SignInButton gSignInButton;
    GoogleSignInClient mGoogleSignInClient;
    public static final String REGISTER_TAG = RegisterFragment.class.getSimpleName();
    public static final String LICENSE_TAG = DriverLicenseFragment.class.getSimpleName();

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        autoFillEmail(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initializeComponents();
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        gSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 0);
            }
        });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 0) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            Toast.makeText(SignInActivity.this, "You are logged in " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.w("googleLoginError", "signInWithCredential:failure", task.getException());

                        }
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
        email_et = findViewById(R.id.username_et);
        password_et = findViewById(R.id.password_et);
        gSignInButton = findViewById(R.id.google_sign_in_button);
    }

    private void autoFillEmail(FirebaseUser user) {
        if (user != null) {
            email_et.setText(user.getEmail());
        }
    }

    private void login() {
        if (email_et.getText().toString().equals(""))
            Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show();
        else if (password_et.getText().toString().isEmpty())
            Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show();
        else {
            mAuth.signInWithEmailAndPassword(email_et.getText().toString(), password_et.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        Toast.makeText(SignInActivity.this, "You are logged in " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onRegisterInteraction(int flag) {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();
            switch (flag) {
                case 0:
                    DriverLicenseFragment licenseFragment = new DriverLicenseFragment(user);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(frame.getId(), licenseFragment, LICENSE_TAG)
                            .addToBackStack(LICENSE_TAG)
                            .commit();
                    break;
                case 1:
                    break;
            }
        } else{
            Toast.makeText(this, "User not registered", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLicenseInteraction() {
        Toast.makeText(this, "Registration Complete, you are logged in", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
