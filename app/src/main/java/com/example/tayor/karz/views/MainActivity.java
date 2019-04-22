package com.example.tayor.karz.views;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tayor.karz.BaseActivity;
import com.example.tayor.karz.Model.Car;
import com.example.tayor.karz.Model.Reservation;
import com.example.tayor.karz.Model.User;
import com.example.tayor.karz.R;
import com.example.tayor.karz.views.car.BookCarActivity;
import com.example.tayor.karz.views.car.CarFragment;
import com.example.tayor.karz.views.history.HistoryFragment;
import com.example.tayor.karz.views.profile.ProfileActivity;
import com.example.tayor.karz.views.sign_in.SignInActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MainActivity extends BaseActivity implements
        CarFragment.OnListFragmentInteractionListener,HistoryFragment.OnHistoryListFragmentInteractionListener {
    private TextView mTextMessage;
    private TextView mOptionalMessage;
    private StorageReference storageReference;
    private FirebaseStorage storage;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private CardView cardView;
    private FrameLayout frameLayout;
    private String documentPath;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.s_list_of_available_cars);
                    mOptionalMessage.setVisibility(View.INVISIBLE);
                    if (getSupportFragmentManager().findFragmentByTag("carfrag") == null)
                        removeFragment();
                    showFragment();
                    return true;
                case R.id.history:
                    mTextMessage.setText(R.string.history);
                    mOptionalMessage.setVisibility(View.VISIBLE);
                    if (getSupportFragmentManager().findFragmentByTag("hisfrag") == null)
                        removeFragment();
                    showHistoryFragment();
                    return true;
//                case R.id.currently_booked:
//                    mTextMessage.setText(R.string.currently_booked);
//                    mOptionalMessage.setVisibility(View.INVISIBLE);
//                    removeFragment();
//                    return true;
            }
            return false;
        }
    };

    void removeFragment() {
        if (getSupportFragmentManager().findFragmentByTag("carfrag") != null)
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("carfrag")).commit();
        if (getSupportFragmentManager().findFragmentByTag("hisfrag") != null)
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("hisfrag")).commit();
    }

    void showFragment() {
        CarFragment carFragment = new CarFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, carFragment, "carfrag")
                .commit();
    }

    void showHistoryFragment() {
        HistoryFragment historyFragment = new HistoryFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, historyFragment, "hisfrag")
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();

        documentPath = getIntent().getStringExtra("documentpath");
        String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.d("absolutePath", rootPath);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("localdata").directory(new File(rootPath + "/realm")).build();
        Realm.setDefaultConfiguration(configuration);

        mTextMessage = findViewById(R.id.message);
        mOptionalMessage = findViewById(R.id.optional_text);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        cardView = findViewById(R.id.card);
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.whyte),android.graphics.PorterDuff.Mode.MULTIPLY);
        showFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_car_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.profile:
                Toast.makeText(this, "profile selected", Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("documentPath",documentPath);

                startActivity(intent);
                return true;
            case R.id.log_out:
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
                break;
//            case R.id.upload:
//                uploadImage();
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(Car car) {
        Toast.makeText(this, car.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, BookCarActivity.class);
        intent.putExtra(getString(R.string.s_car_tag), car);
        startActivity(intent);
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
                            Log.d("filename",file.getName());
                            final StorageReference imagesRef = storageReference.child("images").child(file.getName());
                            final UploadTask uploadTask = imagesRef.putFile(fileUri);

                            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if(!task.isSuccessful())
                                        throw task.getException();
                                    return  imagesRef.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(MainActivity.this, "image uploaded", Toast.LENGTH_SHORT).show();
                                        Log.d("taskResult", String.valueOf(task.getResult()));
                                    }
                                }
                            });
//                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    Toast.makeText(MainActivity.this, "upload successful", Toast.LENGTH_SHORT).show();
//
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.e("errorUploading", e.getMessage());
//                                }
//                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                                    double currentByte = (100.0 * taskSnapshot.getBytesTransferred())/ taskSnapshot.getTotalByteCount();
//                                    int current = (int)currentByte;
//                                    if(progressBar.getVisibility() == View.INVISIBLE && current != 100.0)
//                                        progressBar.setVisibility(View.VISIBLE);
//                                    progressBar.setProgress(current);
//
//                                    if(currentByte == 100.0)
//                                        progressBar.setVisibility(View.INVISIBLE);
//                                }
//                            });
                        } catch (Exception ex) {
                            Log.d("errorUploading", ex.getMessage());
                        }
                    } else{
                        Toast.makeText(this, "issues with the result URI", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(this, "intent data is null", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onHistoryListFragmentInteraction(Reservation reservation) {
        Toast.makeText(this, reservation.toString(), Toast.LENGTH_SHORT).show();
    }
}
