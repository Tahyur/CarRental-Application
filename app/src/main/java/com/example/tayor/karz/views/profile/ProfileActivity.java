package com.example.tayor.karz.views.profile;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tayor.karz.BaseActivity;
import com.example.tayor.karz.Model.User;
import com.example.tayor.karz.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ProfileActivity extends BaseActivity {
    private ImageView mImage;
    private StorageReference storageReference;
    private FirebaseStorage storage;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private EditText license, name, address, clazz, exp_date, province, zip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        initializeComponents();
        getImage();
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        getLicenseInformation();
    }

    private void getCurrentBooking() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user").whereEqualTo("userId", user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isComplete()) {
                    User rv = new User();
                    if (task.getResult().getDocuments().size() > 0) {
                        rv.getLicense().setDocumentId((String) task.getResult().getDocuments().get(0).get("licenseID"));
                   //     getLicenseInformation(rv);
                    }
                }
            }
        });
    }

    private void getLicenseInformation() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user").whereEqualTo("userId",user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isComplete()) {
//                    Log.d("reservationUser", "" + user.getUserId());
                    if (task.getResult() != null) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document != null) {
                                User user = new User();
                                user.setFirstName((String) document.get("firstName"));
                                user.setLastName((String) document.get("lastName"));
                               // user.setLicenseId((String) document.get("licenseId"));
                                populateLicenseInfo(user);
                            }
                        }
                    }
                }
            }
        });
    }

    private void populateLicenseInfo(final User user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("license").document("").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isComplete()) {
                    license.setText((String) task.getResult().get("license"));
                    license.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if(!hasFocus){
                          //     updateInformation(v,user.getLicenseId());
                            }
                        }
                    });
                    name.setEnabled(false);
                    name.setText(user.getFirstName()+" "+user.getLastName());
                    address.setText((String) task.getResult().get("address"));
                    address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if(!hasFocus){
                          //      updateInformation(v,user.getLicenseId());
                            }
                        }
                    });
                    clazz.setText((String) task.getResult().get("clazz"));
                    clazz.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if(!hasFocus){
                         //       updateInformation(v,user.getLicenseId());
                            }
                        }
                    });
                    exp_date.setText((String) task.getResult().get("exp_date"));
                    exp_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if(!hasFocus){
                          //      updateInformation(v,user.getLicenseId());
                            }
                        }
                    });
                    province.setText((String) task.getResult().get("province"));
                    province.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if(!hasFocus){
                          //      updateInformation(v,user.getLicenseId());
                            }
                        }
                    });
                    zip.setText((String) task.getResult().get("zip"));
                    zip.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if(!hasFocus){
                           //     updateInformation(v,user.getLicenseId());
                            }
                        }
                    });
                }
            }
        });
    }

    private void updateInformation(View v,String licenseId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        switch(v.getId()){
            case R.id.lic_et: 
                db.collection("license").document(licenseId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        documentSnapshot.getReference().update("license",license.getText().toString());
                    }
                });
                break;
            case R.id.address_et:
                db.collection("license").document(licenseId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        documentSnapshot.getReference().update("address",address.getText().toString());
                    }
                });
                break;
            case R.id.class_et:
                db.collection("license").document(licenseId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        documentSnapshot.getReference().update("clazz",clazz.getText().toString());
                    }
                });
                break;
            case R.id.province_et:
                db.collection("license").document(licenseId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        documentSnapshot.getReference().update("province",province.getText().toString());
                    }
                });
                break;
            case R.id.zip_et:
                db.collection("license").document(licenseId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        documentSnapshot.getReference().update("clazz",zip.getText().toString());
                    }
                });
                break;
            case R.id.expire_et:
                db.collection("license").document(licenseId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        documentSnapshot.getReference().update("exp_date",exp_date.getText().toString());
                    }
                });
                break;
        }
    }

    private void getImage() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user").whereEqualTo("userId", user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isComplete()) {
                    QuerySnapshot snapshot = task.getResult();
                    if (snapshot != null) {
                        DocumentChange dc = snapshot.getDocumentChanges().get(0);
                        if (dc != null) {
                            dc.getDocument().getReference().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isComplete()) {
                                        Picasso.get().load((String) task.getResult().get("imageUrl")).centerCrop().fit().into(mImage);
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    private void uploadImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, 1000);
    }

    private String getPathFromUri(Uri uri) {
        String result = "";
        String projection[] = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor.moveToFirst()) {
            int colIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            result = cursor.getString(colIndex);
        }
        cursor.close();
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                if (data != null) {
                    Uri resultUri = data.getData();
                    if (resultUri != null) {
                        mImage.setImageURI(resultUri);
                        String path = getPathFromUri(resultUri);
                        try {
                            File file = new File(path);
                            final Uri fileUri = Uri.fromFile(file);
                            Log.d("filename", file.getName());
                            final StorageReference imagesRef = storageReference.child("images").child(file.getName());
                            final UploadTask uploadTask = imagesRef.putFile(fileUri);

                            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful())
                                        throw task.getException();
                                    return imagesRef.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ProfileActivity.this, "image uploaded", Toast.LENGTH_SHORT).show();
                                        updateUserImage(task.getResult());
                                        Log.d("taskResult", String.valueOf(task.getResult()));
                                    }
                                }
                            });
                        } catch (Exception ex) {
                            Log.d("Error", ex.getMessage());
                        }
                    }
                }
            }
        }
    }

    private void updateUserImage(final Uri result) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query userRef = db.collection("user").whereEqualTo("userId", user.getUid());
        userRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (dc.getDocument().exists()) {
                        Log.d("documentId", dc.getDocument().getId());
                        DocumentReference documentReference = dc.getDocument().getReference();
                        if (result != null) {
                            documentReference.update("imageUrl", result.toString());
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeComponents() {
        license = findViewById(R.id.lic_et);
        name = findViewById(R.id.name_et);
        address = findViewById(R.id.address_et);
        zip = findViewById(R.id.zip_et);
        exp_date = findViewById(R.id.expire_et);
        province = findViewById(R.id.province_et);
        clazz = findViewById(R.id.class_et);
        mImage = findViewById(R.id.client_image);
    }
}
