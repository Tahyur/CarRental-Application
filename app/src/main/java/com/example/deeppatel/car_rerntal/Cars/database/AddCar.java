package com.example.deeppatel.car_rerntal.Cars.database;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.deeppatel.car_rerntal.Cars.models.Car;
import com.example.deeppatel.car_rerntal.OCR.TextRecognition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddCar {

    public void addToDatabase(Car car)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> customer = new HashMap<>();
        customer.put("name", car.getName());
        customer.put("model", car.getModel());
        customer.put("mileage", car.getMileage());
        customer.put("image", car.getImage());
        customer.put("status",car.getStatus());

        // Add a new document with a generated ID
        db.collection("cars")
                .add(customer)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("db", "DocumentSnapshot added with ID: " + documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("db", "Error adding document", e);
                    }
                });

    }
}
