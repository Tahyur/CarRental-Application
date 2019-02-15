package com.example.tayor.karz;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RadioButton;
import android.widget.Toast;

public class PaymentActivity extends BaseActivity {
    WebView webView;
    RadioButton terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        terms = findViewById(R.id.term_rb);
        webView = findViewById(R.id.pay_pal);

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
    }

    private void displayAlert() {

        AlertDialog alertDialog = new AlertDialog.Builder(PaymentActivity.this).create();
        alertDialog.setTitle("Alert Dialog");
        alertDialog.setMessage("Welcome to dear user.");
        //   alertDialog.setIcon(R.drawable.welcome);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.show();
    }
}
