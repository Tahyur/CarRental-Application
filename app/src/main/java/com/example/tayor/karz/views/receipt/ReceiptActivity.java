package com.example.tayor.karz.views.receipt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tayor.karz.BaseActivity;
import com.example.tayor.karz.Model.Reservation;
import com.example.tayor.karz.R;
import com.example.tayor.karz.views.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        db.collection("user")
                .whereEqualTo("userId", reservation.getUserId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                customerTv.setText(document.get("firstName")+ " "+document.get("lastName"));
                                getCarInformation();
                            }
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void getCarInformation() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("cars").document(reservation.getCarId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                carTv.setText((String)task.getResult().get("name"));
                modelTv.setText((String)task.getResult().get("model"));
                setData();
            }
        });
    }

    private void setData() {
        reservationTv.setText(reservation.getId());
        rentDateTv.setText(reservation.getStartDateTime());
        returnDateTv.setText(reservation.getEndDateTime());
        billingTv.setText(reservation.getBillingOverview());
        depositTv.setText(reservation.getDeposit());
    }
}
