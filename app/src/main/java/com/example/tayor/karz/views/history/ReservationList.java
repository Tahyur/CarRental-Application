package com.example.tayor.karz.views.history;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.tayor.karz.Model.Car;
import com.example.tayor.karz.Model.Reservation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class ReservationList {
    final List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> getAllReservations(final HistoryAdapter historyAdapter, FirebaseUser mUser){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("reservation").whereEqualTo("userId",mUser.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("", "listen:error", e);
                    return;
                }
                for (DocumentChange dc : snapshots.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        Reservation r = new Reservation();
                        r.setId(dc.getDocument().getId());
                        r.setCarId((String) dc.getDocument().get("carId"));
                        r.setUserId((String) dc.getDocument().get("userId"));
                        r.setHours((String) dc.getDocument().get("hours"));
                        r.setDeposit((String) dc.getDocument().get("deposit"));
                        r.setBillingOverview((String) dc.getDocument().get("billingOverview"));
                        r.setStartDateTime((String) dc.getDocument().get("startDateTime"));
                        r.setEndDateTime((String) dc.getDocument().get("endDateTime"));
                        r.setReturned((String) dc.getDocument().get("returned"));
                        Log.d("carID",r.getCarId());
                        getCarInformation(r,historyAdapter);
                    }

                    if (dc.getType() == DocumentChange.Type.REMOVED) {
                        Reservation r = new Reservation();
                        r.setId(dc.getDocument().getId());
                        reservations.remove(r);
                    }

                    if(dc.getType() == DocumentChange.Type.MODIFIED){
                        Reservation r = new Reservation();
                        r.setId(dc.getDocument().getId());
                        for (int i = 0; i < reservations.size(); i++) {
                            if (reservations.get(i).getId().equals(r.getId())) {
                                r.setId(dc.getDocument().getId());
                                r.setCarId((String) dc.getDocument().get("carId"));
                                r.setUserId((String) dc.getDocument().get("userId"));
                                r.setHours((String) dc.getDocument().get("hours"));
                                r.setDeposit((String) dc.getDocument().get("deposit"));
                                r.setBillingOverview((String) dc.getDocument().get("billingOverview"));
                                r.setStartDateTime((String) dc.getDocument().get("startDateTime"));
                                r.setEndDateTime((String) dc.getDocument().get("endDateTime"));
                                r.setReturned((String) dc.getDocument().get("returned"));
                                Log.d("","");
                                break;
                            }
                        }
                    }
                }
                historyAdapter.notifyDataSetChanged();
            }
        });
        return reservations;
    }

    private void getCarInformation(final Reservation r, final HistoryAdapter historyAdapter) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("cars").document(r.getCarId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if(task.isSuccessful()){
                   Car c = new Car();
                   c.setName((String)task.getResult().get("name"));
                   c.setModel((String) task.getResult().get("model"));
                   r.setCar(c);
                   Log.d("carData",r.getCar().getName());
                   reservations.add(r);
               }
                historyAdapter.notifyDataSetChanged();
            }
        });
    }
}
