package com.example.tayor.karz.views.history;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    public List<Reservation> getAllReservations(final HistoryAdapter historyAdapter, FirebaseUser mUser) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user").whereEqualTo("userId", mUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                 @Override
                 public void onComplete(@NonNull Task<QuerySnapshot> task) {
                     if (task.isComplete()) {
                         String docId = task.getResult().getDocuments().get(0).getId();
                         getHistoryInformation(docId,historyAdapter);
                     }
                 }
             }
        );
        historyAdapter.notifyDataSetChanged();
        return reservations;
    }
    private void getHistoryInformation(String docId, final HistoryAdapter historyAdapter){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("history").whereEqualTo("userId", docId).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w("", "listen:error", e);
                        return;
                    }
                    for (DocumentChange dc : snapshots.getDocumentChanges()) {
                        if (dc.getDocument().exists()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                Reservation r = new Reservation();
                                r.setDocumentId(dc.getDocument().getId());
                             //   r.setCarId((String) dc.getDocument().get("carId"));
                                r.setUserId((String) dc.getDocument().get("userId"));
                                r.setHours(String.valueOf(dc.getDocument().get("hours")));
                                r.setDeposit(String.valueOf(dc.getDocument().get("deposit")));
                            //    Log.d("carID", r.getCarId());
                                getCarInformation(r, historyAdapter);
                            }

                            if (dc.getType() == DocumentChange.Type.REMOVED) {
                                Reservation r = new Reservation();
                                r.setDocumentId(dc.getDocument().getId());
                                reservations.remove(r);
                            }

                            if (dc.getType() == DocumentChange.Type.MODIFIED) {
                                Reservation r = new Reservation();
                                r.setDocumentId(dc.getDocument().getId());
                                for (int i = 0; i < reservations.size(); i++) {
                                    if (reservations.get(i).getDocumentId().equals(r.getDocumentId())) {
                                        r.setDocumentId(dc.getDocument().getId());
                                     //   r.setCarId((String) dc.getDocument().get("carId"));
                                        r.setUserId((String) dc.getDocument().get("userId"));
                                        r.setHours((String) dc.getDocument().get("hours"));
                                        r.setDeposit((String) dc.getDocument().get("deposit"));
                                        Log.d("", "");
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    historyAdapter.notifyDataSetChanged();
                }
            });
    }

    private void getCarInformation(final Reservation r, final HistoryAdapter historyAdapter) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("cars").document("").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Car c = new Car();
                    c.setName((String) task.getResult().get("name"));
                    c.setModel((String) task.getResult().get("model"));
                    c.setStatus((String) task.getResult().get("status"));
                    r.setCar(c);
                    //   Log.d("carData", r.getCar().getName());
                    reservations.add(r);
                }
                historyAdapter.notifyDataSetChanged();
            }
        });
    }
}
