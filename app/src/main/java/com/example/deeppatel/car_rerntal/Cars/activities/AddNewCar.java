package com.example.deeppatel.car_rerntal.Cars.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.deeppatel.car_rerntal.Cars.database.AddCar;
import com.example.deeppatel.car_rerntal.Cars.database.CarsFetcher;
import com.example.deeppatel.car_rerntal.Cars.models.Car;
import com.example.deeppatel.car_rerntal.Home;
import com.example.deeppatel.car_rerntal.R;
import com.example.deeppatel.car_rerntal.Renting_Process.SearchForCustomer;

import java.util.List;

public class AddNewCar extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap imageBitmap;
    ImageView imageView;
    EditText name,model,mileage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_car);
        imageView=findViewById(R.id.img_new_car);
    }

    public void onClick(View v) {
        switch (v.getId()){

            case R.id.car_gallery_image:

                break;

            case R.id.car_upload_image:
                dispatchTakePictureIntent();
                break;

            case R.id.btn_continue_addNewCar:
                name=findViewById(R.id.car_name);
                model=findViewById(R.id.model_name);
                mileage=findViewById(R.id.car_mileagel_name);

                Car car= new Car();
                car.setName(name.getText().toString());
                car.setModel(model.getText().toString());
                car.setMileage(Double.parseDouble(mileage.getText().toString()));
                car.setStatus(true);

                AddCar addToDB = new AddCar();
                addToDB.addToDatabase(car);
                Toast.makeText(this, car.getName()+" has been added", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getBaseContext(), Home.class);
                startActivity(i);
                break;

            case R.id.btn_cancel_addNewCar:
                Toast.makeText(getBaseContext(), "DB: Cancel ", Toast.LENGTH_SHORT).show();

//                /*******     Back to All  Car Fragment  ********/
//                Intent serachForCustomer = new Intent(this, SearchForCustomer.class);
//                serachForCustomer.putExtra("selected_car", String.valueOf("To be Solved "));
//                startActivity(serachForCustomer);
//                break;

        }

    }

    // Open Camera
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap= (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);

        }
    }
}
