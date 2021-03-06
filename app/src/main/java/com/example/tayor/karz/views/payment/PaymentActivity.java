package com.example.tayor.karz.views.payment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.tayor.karz.BaseActivity;
import com.example.tayor.karz.Model.Reservation;
import com.example.tayor.karz.R;
import com.example.tayor.karz.views.receipt.ReceiptActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PaymentActivity extends BaseActivity {
    WebView webView;
    RadioButton terms;
    Button process;
    private Reservation reservation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        reservation = getIntent().getParcelableExtra("reservation");
        terms = findViewById(R.id.term_rb);
        webView = findViewById(R.id.pay_pal);
        process = findViewById(R.id.process);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.loadUrl("https://www.paypal.com/ca/home");
        webView.setWebViewClient(new WebViewClient());
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAlert();
            }
        });
        process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewReceipt();
            }
        });
    }
    private void viewReceipt() {
        reservation = getIntent().getParcelableExtra("reservation");
        Log.d("ReservationEntity",reservation.toString());
        persistReservationToStorage(reservation);
    }
    private void persistReservationToStorage(final Reservation reservation) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("reservation").add(reservation).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    reservation.setDocumentId(task.getResult().getId());
                    updateCarStatus();
                    Intent intent = new Intent(PaymentActivity.this,ReceiptActivity.class);
                    intent.putExtra("reservation",reservation);
                    startActivity(intent);
                    }
            }
        });
    }

    private void updateCarStatus(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("cars").document(reservation.getCar().getDocumentId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isComplete()){
                    task.getResult().getReference().update("status","false");
                }
            }
        });
    }

    private void displayAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(PaymentActivity.this).create();
        alertDialog.setTitle("Alert Dialog");
        alertDialog.setMessage("Here is our terms and conditions");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }
}
