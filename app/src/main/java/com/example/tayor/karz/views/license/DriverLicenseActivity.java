package com.example.tayor.karz.views.license;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class DriverLicenseActivity extends BaseActivity {
    private EditText license, name, address, clazz, exp_date, province, zip;
    Button finish;
    ImageView image;
    Uri imageUri;
    FirebaseFirestore db;
    String path;
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_GALLERY_CODE = 300;
    private static final int IMAGE_PICK_CAMERA_CODE = 400;

    String[] cameraPermission;
    String[] storagePermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_license);
        db = FirebaseFirestore.getInstance();
        path = getIntent().getStringExtra("userDocPath");

//        Log.d("userDocId",path);
        cameraPermission = new String[]
                {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

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

    private void getLicenseInfo() {
        String licenseNo = license.getText().toString();
        String address_ = address.getText().toString();
        String zip_ = zip.getText().toString();
        String exp_date_ = exp_date.getText().toString();
        String province_ = province.getText().toString();
        String clazz_ = clazz.getText().toString();

        License license = new License();
        license.setLicenseNo(licenseNo);
        license.setAddress(address_);
        license.setZip(zip_);
        license.setExpDate(exp_date_);
        license.setProvince(province_);
        license.setClazz(clazz_);
        loadMainActivity(license);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.license, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.camera:
                if (!checkCameraPermission())
                    requestCameraPermission();
                else
                    pickCamera();
                break;
            case R.id.gallery:
                if (!checkStoragePermission())
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
        String[] mimeTypes = {"image/jpg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
    }

    private void pickCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "img");
        values.put(MediaStore.Images.Media.DESCRIPTION, "img desc");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean cameraResult = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean storageResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return cameraResult && storageResult;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean cameraAccepted, writeStorageAccepted, accepted;
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
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                CropImage.activity(data.getData()).setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                image.setImageURI(resultUri);

                BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
                FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance().getOnDeviceTextRecognizer();


                final Task<FirebaseVisionText> res = textRecognizer.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        StringBuilder sb = new StringBuilder();
                        for (FirebaseVisionText.TextBlock block : firebaseVisionText.getTextBlocks()) {
                            String blockText = block.getText();
                            sb.append(blockText).append("\n");
                            for (FirebaseVisionText.Line lines : block.getLines()) {
                                String lineText = lines.getText();
                                for (FirebaseVisionText.Element element : lines.getElements()) {
                                    String elementText = element.getText();
                                }
                            }
                        }
                        Log.d("blockText", "" + sb.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Error", "" + e.getMessage());
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.d("errormessage", error.getMessage());
                Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadMainActivity(License license) {
        if (license != null) {
            // Saves the license information to the database and updates user licenseId field
            db.collection("license").add(license).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d("licenseId",documentReference.getId());
                    updateUserInfo(documentReference.getId());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

    private void updateUserInfo(String licenseId) {
        Log.d("licenseId",licenseId);
        DocumentReference user = db.collection("user").document(path);
        user.update("licenseId",licenseId).addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void aVoid) {
               Intent intent = new Intent(DriverLicenseActivity.this, MainActivity.class);
               intent.putExtra("documentPath",path);
               startActivity(intent);
               finish();
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Log.e("errorDriverLicense",e.getMessage());
           }
       });

    }
}
