package com.example.tayor.karz.views.license;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tayor.karz.BaseActivity;
import com.example.tayor.karz.Model.License;
import com.example.tayor.karz.R;
import com.example.tayor.karz.views.MainActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverLicenseActivity extends BaseActivity {
    private EditText license,name,address,clazz,exp_date,province,zip;
    private FirebaseUser user;
    Button finish;
    ImageView image;
    Uri imageUri;
    DatabaseReference mDatabase;
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_GALLERY_CODE = 300;
    private static final int IMAGE_PICK_CAMERA_CODE = 400;

    String [] cameraPermission;
    String [] storagePermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_license);
        user  = getIntent().getParcelableExtra("user");

        cameraPermission = new String[]
                {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};

        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        license = findViewById(R.id.lic_et);
        name = findViewById(R.id.name_et);
        address = findViewById(R.id.address_et);
        zip = findViewById(R.id.zip_et);
        exp_date = findViewById(R.id.expire_et);
        province = findViewById(R.id.province_et);
        clazz = findViewById(R.id.class_et);
        image = findViewById(R.id.img_lic);

        finish = findViewById(R.id.finish);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLicenseInfo();
            }
        });
    }

    private void getLicenseInfo(){
        String licenseNo = license.getText().toString();
        String name_ = name.getText().toString();
        String address_ = address.getText().toString();
        String zip_ = zip.getText().toString();
        String exp_date_ = exp_date.getText().toString();
        String province_ = province.getText().toString();
        String clazz_ = clazz.getText().toString();

        License license = new License();
        license.setLicense(licenseNo);
        license.setName(name_);
        license.setAddress(address_);
        license.setZip(zip_);
        license.setExp_date(exp_date_);
        license.setProvince(province_);
        license.setClazz(clazz_);
        loadMainActivity(license);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.license,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.camera:
                if(!checkCameraPermission())
                    requestCameraPermission();
                else
                    pickCamera();
                break;
            case R.id.gallery:
                if(!checkStoragePermission())
                    requestStoragePermission();
                else
                    pickGallery();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void pickGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE);
    }


    private boolean checkStoragePermission() {
        return false;
    }

    private void pickCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"img");
        values.put(MediaStore.Images.Media.DESCRIPTION,"img desc");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE);
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean cameraResult = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean storageResult = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return cameraResult && storageResult;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean cameraAccepted,writeStorageAccepted, accepted;
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0) {
                    cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    accepted = cameraAccepted && writeStorageAccepted;
                    if (accepted)
                        pickCamera();
                    else
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0) {
                    writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted)
                        pickGallery();
                    else
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    break;
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_PICK_GALLERY_CODE){

            }
            if(requestCode == IMAGE_PICK_CAMERA_CODE){

            }
        }
    }

    private void loadMainActivity(License license) {
        if(license != null) {
            // Saves the license information to the database
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("license").child(user.getUid()).setValue(license);
            Toast.makeText(this, "Registration Complete, you are logged in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DriverLicenseActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
