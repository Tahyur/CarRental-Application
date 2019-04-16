package com.example.deeppatel.car_rerntal.Cars.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deeppatel.car_rerntal.Cars.models.Car;
import com.example.deeppatel.car_rerntal.R;

public class EditCar extends AppCompatActivity {

    private EditText car_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);

        car_name = findViewById(R.id.EDIT_name);

        Car cars = (Car) getIntent().getSerializableExtra("Car");

        if(cars != null){

            car_name.setText(cars.getName());

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_car_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.edit_save){

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        }

        return true;

    }
}
