package com.example.deeppatel.car_rerntal.Cars.database;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.deeppatel.car_rerntal.Cars.CarAdapter;
import com.example.deeppatel.car_rerntal.Cars.models.Car;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.deeppatel.car_rerntal.Cars.CarEngine.carList;

public class CarsFetcher
{

    //Get All Cars from FireStore
    public  List<Car> getAllCars(final CarAdapter carsAdapter)
    {
        final List<Car> cars = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("cars").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
//                        Log.d("DB", document.toObject(Car.class).toString());
                        //Create object of a car
                        Car c = document.toObject(Car.class);
                        Log.d("CarObject", String.valueOf(task.getResult().size()));
                        //Add to List of Cars
                        cars.add(c);
                    }
                    carsAdapter.notifyDataSetChanged();

                } else {
                    Log.d("Db error", "Error getting documents: ", task.getException());
                }
            }
        });
        return cars;
    }

}



