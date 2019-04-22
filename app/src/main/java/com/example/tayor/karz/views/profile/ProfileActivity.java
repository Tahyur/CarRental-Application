package com.example.tayor.karz.views.profile;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tayor.karz.BaseActivity;
import com.example.tayor.karz.R;
import com.example.tayor.karz.views.MainActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class ProfileActivity extends BaseActivity {
    private ImageView mImage;
    private StorageReference storageReference;
    private FirebaseStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mImage = findViewById(R.id.client_image);

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    private void uploadImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, 1000);
    }

    private String getPathFromUri(Uri uri){
        String result = "";
        String projection[] = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri,projection,null,null,null);
        if(cursor.moveToFirst()){
            int colIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            result = cursor.getString(colIndex);
        }
        cursor.close();
        return  result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == 1000){
                if(data != null) {
                    Uri resultUri = data.getData();
                    if (resultUri != null) {
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
                                        Log.d("taskResult", String.valueOf(task.getResult()));
                                    }
                                }
                            });
                        } catch (Exception ex){
                            Log.d("Error",ex.getMessage());
                        }
                    }
    }}}}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit :
                Intent intent = new Intent(ProfileActivity.this,EditProfileActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
