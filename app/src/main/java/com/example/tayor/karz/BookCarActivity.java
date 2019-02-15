package com.example.tayor.karz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tayor.karz.Model.Car;

public class BookCarActivity extends BaseActivity {
    TextView carName,model;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_car);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeComponents();

        Car car = getIntent().getParcelableExtra(getString(R.string.s_car_tag));

        carName.setText(car.getName());
        model.setText(car.getModel());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPayment();
            }
        });

    }

    private void getPayment() {
        Intent intent = new Intent(BookCarActivity.this, PaymentActivity.class);
        startActivity(intent);
    }

    void initializeComponents(){
        carName = findViewById(R.id.car_name);
        model = findViewById(R.id.model_name);
        next = findViewById(R.id.next_btn);
    }
}
