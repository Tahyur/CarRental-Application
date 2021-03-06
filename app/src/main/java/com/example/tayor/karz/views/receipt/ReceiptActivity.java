package com.example.tayor.karz.views.receipt;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tayor.karz.BaseActivity;
import com.example.tayor.karz.Model.Reservation;
import com.example.tayor.karz.R;
import com.example.tayor.karz.views.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReceiptActivity extends BaseActivity {
private Button ok_bn;
private Reservation reservation;
private TextView customerTv,carTv,modelTv,rentDateTv,returnDateTv,billingTv,depositTv,reservationTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_receipt);
        initializeComponents();
        ok_bn = findViewById(R.id.ok);
        reservation = getIntent().getParcelableExtra("reservation");
        getUserInformation();
        ok_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homepage();
            }
        });
    }

    private void homepage() {
        Intent intent = new Intent(ReceiptActivity.this,MainActivity.class);
        startActivity(intent);
    }



    private void initializeComponents(){
        customerTv = findViewById(R.id.customer_name);
        carTv = findViewById(R.id.car_name);
        modelTv = findViewById(R.id.model_name);
        rentDateTv = findViewById(R.id.start_period);
        returnDateTv = findViewById(R.id.end_period);
        billingTv = findViewById(R.id.billing_overview);
        depositTv = findViewById(R.id.deposit);
        reservationTv = findViewById(R.id.reservation_no);
    }


    private void getUserInformation(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user").document(reservation.getUserId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isComplete()){
                            customerTv.setText(task.getResult().get("firstName")+" "+task.getResult().get("lastName"));
                            getCarInformation();
                        }
                    }
                });
    }

    private void getCarInformation() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("cars").document(reservation.getCar().getDocumentId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                carTv.setText((String)task.getResult().get("name"));
                modelTv.setText((String)task.getResult().get("model"));
                setData();
            }
        });
    }

    private void setData() {
        reservationTv.setText(reservation.getDocumentId());
        depositTv.setText(reservation.getDeposit());
    }
}
