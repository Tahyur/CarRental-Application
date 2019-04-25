package com.example.tayor.karz.views.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tayor.karz.BaseActivity;
import com.example.tayor.karz.Model.User;
import com.example.tayor.karz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class EditProfileActivity extends BaseActivity {
    private EditText firstname_et,lastname_et,email_et;
    private Button updateProfile_bt;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initialize();
        db  = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        db.collection("user").whereEqualTo("userId",mUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    firstname_et.setText((CharSequence) task.getResult().getDocuments().get(0).get("firstName"));
                    lastname_et.setText((CharSequence) task.getResult().getDocuments().get(0).get("lastName"));
                    email_et.setText(mUser.getEmail());
                }
            }
        });

        updateProfile_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCredentials();
            }
        });
    }

    private void updateCredentials() {
        db.collection("user").whereEqualTo("userId",mUser.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                DocumentChange dc = queryDocumentSnapshots.getDocumentChanges().get(0);
                if(dc != null) {
                    User user = new User();
                    user.setFirstName(firstname_et.getText().toString());
                    user.setLastName(lastname_et.getText().toString());
                    String currentUserId = String.valueOf(dc.getDocument().get("userId"));
                    if (currentUserId.equals(mUser.getUid())) {
                        dc.getDocument().getReference().update("firstName",user.getFirstName());
                        dc.getDocument().getReference().update("lastName",user.getLastName());
                    }
                }
            }
        });

        mUser.updateEmail(email_et.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(EditProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                }  else {
                    Log.d("","");
                }
            }
        });
    }

    private void initialize(){
        firstname_et = findViewById(R.id.pro_first_name);
        lastname_et = findViewById(R.id.pro_last_name);
        email_et = findViewById(R.id.pro_email_add);
        updateProfile_bt = findViewById(R.id.updateProfile);
    }

}
