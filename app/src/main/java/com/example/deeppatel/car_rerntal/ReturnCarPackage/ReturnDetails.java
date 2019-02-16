package com.example.deeppatel.car_rerntal;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ReturnDetails extends AppCompatActivity {

    private View circle_ele;
    private TextView car_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculation_return_activity);

        circle_ele = findViewById(R.id.DETAILS_circle);
        car_name = findViewById(R.id.DETAILS_car_name);

        //Get the car details from the calling activity
        Car car = (Car) getIntent().getSerializableExtra("Car");

        if(car != null){

            GradientDrawable bgShape = (GradientDrawable) circle_ele.getBackground();
            bgShape.setColor(car.getColor());

            car_name.setText(car.getCar_name());

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.return_details, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.payment){

            Intent toPaymentActivity = new Intent(getApplicationContext(), PaymentActivity.class);
            startActivity(toPaymentActivity);

        }

        return true;

    }
}
